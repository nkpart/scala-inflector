package prohax

import scala.util.matching.Regex

object Bootstrap {
  implicit def regexToRule(r: Regex) = Rule.r(r)
  
  def defineInflections_! = { 
  Inflector.inflections(inflect => {
    inflect.plural("$".r, "s")
    inflect.plural("(?i)s$".r, "s")
    inflect.plural("(?i)(ax|test)is$".r, "$1es")
    inflect.plural("(?i)(octop|vir)us$".r, "$1i")
    inflect.plural("(?i)(alias|status)$".r, "$1es")
    inflect.plural("(?i)(bu)s$".r, "$1ses")
    inflect.plural("(?i)(buffal|tomat)o$".r, "$1oes")
    inflect.plural("(?i)([ti])um$".r, "$1a")
    inflect.plural("(?i)sis$".r, "ses")
    inflect.plural("(?i)(?:([^f])fe|([lr])f)$".r, "$1$2ves")
    inflect.plural("(?i)(hive)$".r, "$1s")
    inflect.plural("(?i)([^aeiouy]|qu)y$".r, "$1ies")
    inflect.plural("(?i)(x|ch|ss|sh)$".r, "$1es")
    inflect.plural("(?i)(matr|vert|ind)(?:ix|ex)$".r, "$1ices")
    inflect.plural("(?i)([m|l])ouse$".r, "$1ice")
    inflect.plural("(?i)^(ox)$".r, "$1en")
    inflect.plural("(?i)(quiz)$".r, "$1zes")
    // 
    // inflect.singular(/s$/i, '')
    // inflect.singular(/(n)ews$/i, '\1ews')
    // inflect.singular(/([ti])a$/i, '\1um')
    // inflect.singular(/((a)naly|(b)a|(d)iagno|(p)arenthe|(p)rogno|(s)ynop|(t)he)ses$/i, '\1\2sis')
    // inflect.singular(/(^analy)ses$/i, '\1sis')
    // inflect.singular(/([^f])ves$/i, '\1fe')
    // inflect.singular(/(hive)s$/i, '\1')
    // inflect.singular(/(tive)s$/i, '\1')
    // inflect.singular(/([lr])ves$/i, '\1f')
    // inflect.singular(/([^aeiouy]|qu)ies$/i, '\1y')
    // inflect.singular(/(s)eries$/i, '\1eries')
    // inflect.singular(/(m)ovies$/i, '\1ovie')
    // inflect.singular(/(x|ch|ss|sh)es$/i, '\1')
    // inflect.singular(/([m|l])ice$/i, '\1ouse')
    // inflect.singular(/(bus)es$/i, '\1')
    // inflect.singular(/(o)es$/i, '\1')
    // inflect.singular(/(shoe)s$/i, '\1')
    // inflect.singular(/(cris|ax|test)es$/i, '\1is')
    // inflect.singular(/(octop|vir)i$/i, '\1us')
    // inflect.singular(/(alias|status)es$/i, '\1')
    // inflect.singular(/^(ox)en/i, '\1')
    // inflect.singular(/(vert|ind)ices$/i, '\1ex')
    // inflect.singular(/(matr)ices$/i, '\1ix')
    // inflect.singular(/(quiz)zes$/i, '\1')
    // inflect.singular(/(database)s$/i, '\1')

    inflect.irregular("person", "people")
    inflect.irregular("man", "men")
    inflect.irregular("child", "children")
    inflect.irregular("sex", "sexes")
    inflect.irregular("move", "moves")
    inflect.irregular("cow", "kine")

    inflect.uncountable("equipment","information","rice", "money", "species", "series", "fish", "sheep")    
  })
  }
}
