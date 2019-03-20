name := "HistogramDatasetSpark"

version := "0.1"

scalaVersion := "2.11.8"

libraryDependencies += "org.apache.spark" %% "spark-core" % "2.4.0"
libraryDependencies += "org.apache.spark" %% "spark-sql" % "2.4.0"
libraryDependencies += "org.apache.spark" %% "spark-hive" % "2.4.0" % "provided"
libraryDependencies += "org.apache.commons" % "commons-csv" % "1.2"
libraryDependencies += "org.vegas-viz" %% "vegas" % "0.3.9"
libraryDependencies += "org.vegas-viz" %% "vegas-spark" % "0.3.9"

