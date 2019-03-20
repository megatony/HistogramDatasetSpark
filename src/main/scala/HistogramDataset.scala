import java.io.{BufferedWriter, File, FileWriter}
import org.apache.spark.{SparkConf, SparkContext, sql}
import org.apache.spark.sql.{DataFrame, SQLContext, SparkSession}
import vegas._
import vegas.sparkExt._

object HistogramDataset {
  val PATH = "src\\main\\resources\\"
  val FILENAME = "demo_preprocessed_glasses.csv"
  val OUTPUT_FILE_NAME = "histogram.html"
  val COLUMN_NAME = "activity_type"

  var conf = new SparkConf().setAppName("Histogram Dataset").setMaster("local[*]")
  val sc = new SparkContext(conf)
  val sqlContext = new SQLContext(sc)

  def main(args: Array[String]): Unit = {
    val dataFrame = getReportDataFrame()
                    .groupBy(COLUMN_NAME)
                    .count()

    val htmlText = plotDataFrameAndGetPageHtml("Samples group by activities", COLUMN_NAME, "count", dataFrame)
    createHtmlFile(htmlText)
  }

  def plotDataFrameAndGetPageHtml(titleName : String, xAxisValue : String, yAxisValue : String, dataFrame: DataFrame) : String = {
      Vegas(titleName).
      withDataFrame(dataFrame).
      mark(Bar).
      encodeX(xAxisValue, Nominal).
      encodeY(yAxisValue, Quant).html.pageHTML()
  }

  def createHtmlFile(htmlString : String) = {
    val file = new File(PATH + OUTPUT_FILE_NAME)
    val bw = new BufferedWriter(new FileWriter(file))
    bw.write(htmlString)
    bw.close()
  }

  def getReportDataFrame() : DataFrame = {
    sqlContext.read.format("com.databricks.spark.csv")
      .option("treatEmptyValuesAsNulls", "true")
      .option("header", "true")
      .option("inferSchema", "true")
      .option("dateFormat", "yyyy-MM-dd HH:mm")
      .option("mode","DROPMALFORMED")
      .load(PATH + FILENAME)
  }

}
