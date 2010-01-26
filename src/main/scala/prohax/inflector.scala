package prohax

import collection.mutable.ArrayBuffer
import util.matching.Regex
import util.control.Breaks._

sealed trait Rule {
  def fold[X](s: String => X, r: Regex => X): X
}
object Rule {
  def s(s: String) = new Rule {
    def fold[X](fs: String => X, r: Regex => X): X = fs(s)
    override def toString = "Rule:s %s" format s
  }
  def r(r: Regex) = new Rule {
    def fold[X](s: String => X, fr: Regex => X): X = fr(r)
    override def toString = "Rule:r %s" format r.toString
  }
}

class Inflections {
  val plurals = new ArrayBuffer[(Rule, String)]
  val singulars = new ArrayBuffer[(Rule, String)]
  val uncountables = new ArrayBuffer[String]
  val humans = new ArrayBuffer[(Rule, String)]
  
  def plural(rule: Rule, replacement: String) {
    rule.fold(s => uncountables -= s, _ => ())
    uncountables -= replacement
    plurals insert (0, (rule, replacement))
  }
  
  def singular(rule: Rule, replacement: String) {
    rule.fold(s => uncountables -= s, _ => ())
    uncountables -= replacement
    singulars insert (0, (rule, replacement))
  }
  
  def irregular(s: String, p: String) {
    uncountables -= s
    uncountables -= p
    if (s.take(1).toUpperCase == p.take(1).toUpperCase) {
      plural(Rule.r("(?i)(%s)%s$" format (s.take(1), s.drop(1)) r), "$1" + p.drop(1))
      plural(Rule.r("(?i)(%s)%s$" format (p.take(1), p.drop(1)) r), "$1" + p.drop(1))
      singular(Rule.r("(?i)(%s)%s$" format (p.take(1), p.drop(1)) r), "$1" + s.drop(1))
    } else {
      plural(Rule.r("%s(?i)%s$" format (s.take(1).toUpperCase, s.drop(1)) r), p.take(1).toUpperCase + p.drop(1))
      plural(Rule.r("%s(?i)%s$" format (s.take(1).toLowerCase, s.drop(1)) r), p.take(1).toLowerCase + p.drop(1))

      plural(Rule.r("%s(?i)%s$" format (p.take(1).toUpperCase, p.drop(1)) r), p.take(1).toUpperCase + p.drop(1))
      plural(Rule.r("%s(?i)%s$" format (p.take(1).toLowerCase, p.drop(1)) r), p.take(1).toLowerCase + p.drop(1))
            
      singular(Rule.r("%s(?i)%s$" format (p.take(1).toUpperCase, p.drop(1)) r), s.take(1).toUpperCase + s.drop(1))
      singular(Rule.r("%s(?i)%s$" format (p.take(1).toLowerCase, p.drop(1)) r), s.take(1).toLowerCase + s.drop(1))
    }
  }
  
  def uncountable(s: String*) {
    uncountables ++= s
  }
  
  def human(rule: Rule, replacement: String) {
    humans.insert(0, (rule, replacement))
  }
  
  // TODO: scoping
  def clear {
    List(plurals, singulars, uncountables) foreach (_.clear)
  }
}

trait InflectorString {
  val word: String
  
  def pluralize(implicit inflections: Inflections): String = {
    if (word.isEmpty || inflections.uncountables.contains(word.toLowerCase)) {
      word
    } else {
      var result = word
      breakable {
        inflections.plurals.foreach { case (rule, replacement) => 
          if (rule.fold(s => result contains s, r => r findFirstIn result isDefined)) {
            result = rule.fold(s => result replaceAll (s, replacement), r => r replaceAllIn (result, replacement))
            break
          }
        }
      }
      result
    }
  }
}

object Inflector {
  private lazy val instance = new Inflections
  
  implicit def inflections: Inflections = instance
  
  implicit def stringAdditionsTo(s: String) = new InflectorString { val word = s }
  implicit def stringAdditionsFrom(is: InflectorString) = is.word
  
  def configure(f: (Inflections => Unit)) = {
    f(instance)
    instance
  }
}