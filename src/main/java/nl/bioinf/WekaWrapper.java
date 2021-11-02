package nl.bioinf;

import weka.classifiers.trees.J48;
import weka.classifiers.meta.CostSensitiveClassifier;
import weka.core.Instances;
import weka.core.converters.ConverterUtils;

import java.io.IOException;

public class WekaWrapper {
    private final String modelFile = "src/main/resources/adaboost.model";

    public static void main(String[] args) {
        WekaWrapper runner = new WekaWrapper();
        runner.start();
    }

    private void start() {
        String datafile = "data/heart_cleaned.arff";
        String unknownFile = "data/heart_unknown.arff";
        try {
            CostSensitiveClassifier csc = loadClassifier();
            Instances instances = loadArff(datafile);
            Instances unknownInstances = loadArff(unknownFile);

//            Instances instances = loadArff(datafile);
//            printInstances(instances);
//            J48 j48 = buildClassifier(instances);
//            saveClassifier(j48);
//            J48 fromFile = loadClassifier();
//            Instances unknownInstances = loadArff(unknownFile);
//            System.out.println("\nunclassified unknownInstances = \n" + unknownInstances);
            classifyNewInstance(csc, unknownInstances);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void classifyNewInstance(CostSensitiveClassifier tree, Instances unknownInstances) throws Exception {
        // create copy
        Instances labeled = new Instances(unknownInstances);
        // label instances
        for (int i = 0; i < unknownInstances.numInstances(); i++) {
            double clsLabel = tree.classifyInstance(unknownInstances.instance(i));
            labeled.instance(i).setClassValue(clsLabel);
        }
        System.out.println("\nNew, labeled = \n" + labeled);
    }

    private CostSensitiveClassifier loadClassifier() throws Exception {
        // deserialize model
        return (CostSensitiveClassifier) weka.core.SerializationHelper.read(modelFile);
    }

    private void saveClassifier(J48 j48) throws Exception {
        //post 3.5.5
        // serialize model
        weka.core.SerializationHelper.write(modelFile, j48);

        // serialize model pre 3.5.5
//        ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(modelFile));
//        oos.writeObject(j48);
//        oos.flush();
//        oos.close();
    }

    private Instances loadArff(String datafile) throws IOException {
        try {
            ConverterUtils.DataSource source = new ConverterUtils.DataSource(datafile);
            Instances data = source.getDataSet();
            // setting class attribute if the data format does not provide this information
            // For example, the XRFF format saves the class attribute information as well
            if (data.classIndex() == -1)
                data.setClassIndex(data.numAttributes() - 1);
            return data;
        } catch (Exception e) {
            throw new IOException("could not read from file");
        }
    }


}
