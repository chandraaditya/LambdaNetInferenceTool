
This is the source code repo for JetBrains coding challenge, which runs inference using a published pre-trained LambdaNet model. The source code for the LambdaNet model can be found at this [*LambdaNet*](https://github.com/MrVPlusOne/LambdaNet) reop. And is based on the ICLR paper [*LambdaNet: Probabilistic Type Inference using Graph Neural Networks*](https://openreview.net/forum?id=Hkx6hANtwH). 

## Instructions to run
1. Download the `LambdaNetInferenceTool-assembly-0.1.jar` found in the `output` directory.
2. Then run the following command `java -jar LambdaNetInferenceTool-assembly-0.1.jar ` with the parameters `1. path to pretrained model` (a trained model is available at the [this link (predicts user defined type)](https://drive.google.com/file/d/1NvEVQ4-5tC3Nc-Mzpu3vYeyEcaM_zEgV/view?usp=sharing)), `2. path to parsingFromFile.js`, and `3. a typescript file` for which you wish to predict types.
    1. example: `java -jar LambdaNetInferenceTool-assembly-0.1.jar models scripts/ts/parsingFromFile.js stack.ts`

## Instructions to recompile jar file

1. Install `sbt`
2. Clone the repo
3. Run the following command: `sbt clean assembly`
    1. This will produce a `jar` file under the output directory
    2. Follow the instructions to run(given above).
    # LambdaNetInferenceTool
