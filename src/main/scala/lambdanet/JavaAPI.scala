package lambdanet

import ammonite.{ops => amm}
import amm.{Path, PathError, RelPath}
import funcdiff.{ParamCollection, SimpleMath}
import lambdanet.TypeInferenceService.ModelConfig

import scala.io.StdIn

object JavaAPI {
  def pwd: Path = amm.pwd

  def relPath(path: String): RelPath =
    RelPath(path)

  def absPath(path: String): Path =
    Path(path)

  def joinPath(head: Path, tail: String): Path = head / RelPath(tail)

  def defaultModelConfig: ModelConfig = ModelConfig()

  def predictionService(model: Model, numOfThreads: Int, predictTopK: Int) =
    model.PredictionService(numOfThreads, predictTopK)

  def readLine(): String = StdIn.readLine()

  def parseFile(path: Path): String = "odn"

  def getNumParams(paramPath: Path): Number = {
  SimpleMath.withErrorMessage("Error loading weights\n")
    {
      val pc = announced("Load model parameters")(
        ParamCollection.fromFile(paramPath)
      )
      pc.allParams.length
    }
  }

  def main(args: Array[String]): Unit = {
    println("This is a test main function.")
  }
}
