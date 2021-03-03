package solution;

import ammonite.ops.Path;
import lambdanet.*;
import lambdanet.train.TopNDistribution;
import lambdanet.translation.PredicateGraph;
import scala.collection.immutable.Map;

import java.io.IOException;
import java.lang.instrument.Instrumentation;
import java.nio.file.Files;

public class TypePredictionTool {

    public static void main(String[] args) {
        if (args.length != 3) {
            printUsage();
            return;
        }

        JavaAPI$ javaAPI$ = JavaAPI$.MODULE$;
        TypeInferenceService$ typeInferenceService$ = TypeInferenceService$.MODULE$;

        String modelPath = args[0];
        String parsingFromFileInputString = args[1];
        String inputTSFilePathInputString = args[2];

        Path workDir = javaAPI$.pwd();

        Path modelDirectory = generatePath(modelPath, javaAPI$, workDir);
        Path parsingFromFilePath = generatePath(parsingFromFileInputString, javaAPI$, workDir);
        Path inputTSFilePath = generatePath(inputTSFilePathInputString, javaAPI$, workDir);

        modelDirectory = javaAPI$.joinPath(modelDirectory, "newParsing-GAT1-fc2-newSim-decay-6");
        Path paramPath = javaAPI$.joinPath(modelDirectory, "params.serialized");
        Path modelCachePath = javaAPI$.joinPath(modelDirectory, "model.serialized");
        TypeInferenceService.ModelConfig modelConfig = javaAPI$.defaultModelConfig();
        Path parsedReposDir = javaAPI$.joinPath(workDir, "");

        String fileName = java.nio.file.Path.of(inputTSFilePath.toString()).getFileName().toString();

        Number numberOfParameters = javaAPI$.getNumParams(paramPath);

        long beforeLoadingModel;
        long afterLoadingModel;
        Runtime.getRuntime().gc();
        beforeLoadingModel = Runtime.getRuntime().totalMemory() - Runtime.getRuntime().freeMemory();
        Model model = typeInferenceService$.loadModel(paramPath, modelCachePath, modelConfig, 8, parsedReposDir);
        Runtime.getRuntime().gc();
        afterLoadingModel = Runtime.getRuntime().totalMemory() - Runtime.getRuntime().freeMemory();
        long totalMemoryUsageByModel = afterLoadingModel - beforeLoadingModel;

        Model.PredictionService predictionService = javaAPI$.predictionService(model, 8, 5);

        String[] skipSet = {"node_modules"};

        Map<PredicateGraph.PNode, TopNDistribution<PredicateGraph.PType>> results = predictionService.predictOnFile(inputTSFilePath, parsingFromFilePath, skipSet);

        try {
            long modelSize = Files.size(java.nio.file.Path.of(modelCachePath.toString()));
            long paramsSize = Files.size(java.nio.file.Path.of(paramPath.toString()));
            long modelSizeMB = modelSize / 1024 / 1024;
            long paramsSizeMB = paramsSize / 1024 / 1024;
            long modelSizeInMB = modelSizeMB + paramsSizeMB;
            System.out.println("\n===================================================");
            System.out.printf("Parameters size on disk: %02dMB%n", paramsSizeMB);
            System.out.printf("Pre trained model size on disk: %02dMB%n", modelSizeMB);
            System.out.printf("Total model and parameters size on disk: %02dMB%n", modelSizeInMB);
            System.out.println("===================================================");
        } catch (IOException e) {
            System.err.println("Unable to load model.serialized and/or params.serialized to calculate disk size of the model");
            return;
        }

        totalMemoryUsageByModel = totalMemoryUsageByModel / 1024 / 1024;
        System.out.println("\n===================================================");
        System.out.printf("Pre trained model size on RAM: %02dMB%n", totalMemoryUsageByModel);
        System.out.println("===================================================\n");

        System.out.println("===================================================");
        System.out.println("Total number of trainable parameters: " + numberOfParameters.toString());
        System.out.println("===================================================\n");

        System.out.println("===================================================");
        new TypeInferenceService.CustomPredictionResults(results).prettyPrint();
        System.out.println("===================================================");

    }

    public static void printUsage() {
        System.out.println("Usage: [pre-trained model path] [parsingFromFile.ts] [input typescript file]");
    }

    public static Path generatePath(String inputPath, JavaAPI$ api, Path workDir) {
        return inputPath.startsWith("/") ?
                api.absPath(inputPath) :
                api.joinPath(workDir, inputPath);
    }

}