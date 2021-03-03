# LambdaNetInferenceTool
This is the source code repo for JetBrains coding challenge, which runs inference using a published pre-trained LambdaNet model. The source code for the LambdaNet model can be found at this [*LambdaNet*](https://github.com/MrVPlusOne/LambdaNet) reop, and is based on the ICLR paper [*LambdaNet: Probabilistic Type Inference using Graph Neural Networks*](https://openreview.net/forum?id=Hkx6hANtwH).
## Instructions to run
1. Download the pre compiled jar file: [*LambdaNetInferenceTool-assembly-0.1.jar*](https://1drv.ms/u/s!ArGynxZpZDs1jbQ7XK_cqrrMoxiLTw?e=HoNPVQ).
2. Run the following command `java -jar LambdaNetInferenceTool-assembly-0.1.jar ` with the parameters `path to pretrained model` (a trained model is available at the [this link (predicts user defined type)](https://drive.google.com/file/d/1NvEVQ4-5tC3Nc-Mzpu3vYeyEcaM_zEgV/view?usp=sharing)), `path to parsingFromFile.js`, and `a typescript file` for which you wish to predict types.
    1. example: `java -jar LambdaNetInferenceTool-assembly-0.1.jar models scripts/ts/parsingFromFile.js stack.ts`
## Instructions to compile jar file
1. Install `sbt`
   1. Find more information below
2. Clone the repo
3. Run the following command under project root: `sbt clean assembly`
    1. This will produce a `jar` file under the output directory
    2. Follow the instructions to run(given above).
## Install sbt
https://www.scala-sbt.org/1.x/docs/Setup.html
### macOS:
1. Using homebrew 
   1. `brew install sbt`
2. Other methods
   1. Refer to https://www.scala-sbt.org/1.x/docs/Installing-sbt-on-Mac.html
### Windows:
1. Using Scoop:
   1. `scoop install sbt`
2. Using Chocolatey
   1. `choco install sbt`
3. Other methods
   1. Refer to https://www.scala-sbt.org/1.x/docs/Installing-sbt-on-Windows.html
### Linux:
1. Other methods 
   1. Refer to https://www.scala-sbt.org/1.x/docs/Installing-sbt-on-Linux.html