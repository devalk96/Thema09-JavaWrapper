package nl.bioinf;

import weka.classifiers.meta.CostSensitiveClassifier;
import weka.core.Instance;
import weka.core.Instances;
import weka.core.converters.ConverterUtils;

import java.util.ArrayList;
import java.util.LinkedHashMap;

public class WekaWrapper {
    InstanceManager instances = new InstanceManager();

    /**
     * @param unknownInstances Instances who will be classified
     * @return results of classification
     */
    private Instances run(Instances unknownInstances){
        try {
            CostSensitiveClassifier csc = loadClassifier();
            return classifyNewInstance(csc, unknownInstances);

        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * @param model Model that will be used to classify
     * @param unknownInstances unclassified instances as Instances object
     * @return classified instances as Instances object
     */
    private Instances classifyNewInstance(CostSensitiveClassifier model, Instances unknownInstances) throws Exception {
        Instances labeled = new Instances(unknownInstances);

        for (int i = 0; i < unknownInstances.numInstances(); i++) {
            double clsLabel = model.classifyInstance(unknownInstances.instance(i));
            labeled.instance(i).setClassValue(clsLabel);
        }
        return labeled;
    }

    /**
     * Parses model from file
     * @return CostSensitiveClassifier object
     */
    private CostSensitiveClassifier loadClassifier() throws Exception {
        // deserialize model
        String modelFile = "src/main/resources/adaboost.model";
        return (CostSensitiveClassifier) weka.core.SerializationHelper.read(modelFile);
    }

    /**
     * print results to system out
     * @param results Instances object
     */
    private static void echoResults(Instances results){
        System.out.println("Results:");
        if (results.numInstances() == 0){
            System.out.println("Empty, no input provided");
        }
        for (int i = 0; i < results.numInstances(); i++){
            Instance instance = results.instance(i);
            boolean result = instance.classValue() != 0 ;
            System.out.println("Instance: " + i + " has heart disease according to model: " + result);
        }
    }

    public static void main(String[] args) {
        CLIHandler commandline = new CLIHandler(args);
        WekaWrapper runner = new WekaWrapper();
        if (!commandline.variables.isEmpty()){
            for (LinkedHashMap<String, String> instance: commandline.variables){
                runner.instances.addInstances(instance);
        }}
        if (commandline.cmd.getOptionValue("infile") != null) {
            runner.instances.addInstancesFromFile(commandline.cmd.getOptionValue("infile"));
        }
        Instances res = runner.run(runner.instances.getInstances());

        if (commandline.cmd.getOptionValue("output") != null){
            runner.instances.writeInstanceToFile(commandline.outputFormat, commandline.outputPath, res);
        }
        echoResults(res);
        }

    }
