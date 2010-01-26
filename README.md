## Installing

Clone and enter the project directory:

    $ ./sbt
    > publish-local
    
## Depending On

In your sbt project file, add this:

    val inflector = "prohax" %% "inflector" % "1.0"
    
## Use

Early in your lifecycle, configure the inflector with the default ruleset.

    prohax.Bootstrap.defineInflections_!
    
    
Later, import the implicit and use it!

    import prohax.Inflector._
    "mouse".pluralize // == "mice"
    
## Repl session example

    scala> prohax.Bootstrap.defineInflections_!
    res0: prohax.Inflections = prohax.Inflections@7d41cd01

    scala> import prohax.Inflector._
    import prohax.Inflector._

    scala> "mouse".pluralize
    res1: String = mice
  