package nl.bioinf;

import weka.core.Attribute;
import weka.core.DenseInstance;
import weka.core.Instances;
import weka.core.converters.ConverterUtils;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.Objects;
import java.util.zip.ZipException;

public class InstanceManager {
    Instances dataRaw;
    ArrayList<Attribute> attributes;

    /**
     * Constructor
     */
    public InstanceManager() {
        this.attributes = getAttributes();
        this.dataRaw = new Instances("FromConsole", attributes,15);
    }

    public Instances getInstances(){
        if (this.dataRaw.classIndex() == -1)
            this.dataRaw.setClassIndex(this.dataRaw.numAttributes() - 1);
        return dataRaw;
    }

    /**
     * @param name Name of attribute
     * @return attribute object with FALSE and TRUE string options
     */
    private Attribute createFalseTrueAttribute(String name){
        ArrayList<String> newArray = new ArrayList<String>();
        newArray.add("FALSE");
        newArray.add("TRUE");
        return new Attribute(name, newArray);
    }

    /**
     * Creates a temp file and appends file 2 to file 1, thereafter tmp file is deleted and result is returned
     * @param data1 file 1
     * @param data2 file 2
     * @return file 1 and 2 combined
     */
    private Instances merge(Instances data1, Instances data2)
            throws Exception
    {
        String tempPath = "./data/tmp.arff";
        try {
            ConverterUtils.DataSink.write(tempPath, data1);
        }
        catch (Exception e) {
            System.err.println("Failed to save tmpfile to: " + tempPath);
            e.printStackTrace();
        }


        FileWriter fw = new FileWriter(tempPath, true);
        BufferedWriter bw = new BufferedWriter(fw);

        for (int i = 0; i < data2.numInstances(); i++){
            String instanceString = data2.instance(i).toString();
            bw.write(instanceString);
            bw.newLine();
        }
        bw.close();

        //Get merged file and delete tmp
        Instances mergedInstances = loadArff(tempPath);
        deleteFile(tempPath);
        return mergedInstances;
    }

    /**
     * Clears the instances
     */
    public void clearInstances(){
        dataRaw.clear();
    }

    /**
     * @param filetype working file extentions are arff and csv. If other is provided sderr is written
     * @param filepath path to file
     * @param instances instances to be written to file
     */
    public void writeInstanceToFile(String filetype, String filepath, Instances instances){
        if (Objects.equals(filetype, "arff") || Objects.equals(filetype,"csv")){
            try {
                writetoFile(filepath, instances);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        else {
            System.err.println("Not written to file as format " + filetype + " is not compatible");
        }
    }

    /**
     * Writes instance to file. Both CSV and ARFF files work. Currently, writing to CSV prints a bunch of error lines
     * but output is still provided (known bug in Datasink).
     * @param path path to file
     * @param instances instances to write to file
     */
    private void writetoFile(String path, Instances instances) throws Exception {
        String format = path.substring(path.lastIndexOf(".") + 1);
        if (format.contains("/")) {
            path = path + ".arff";
        }

        try {
            ConverterUtils.DataSink.write(path, instances);
        } catch (Exception e) {
            System.err.println("Write error");
        }
    }


    /**
     * Used to remove temporary generated files.
     * @param filepath path to file
     */
    private void deleteFile(String filepath){
        File file = new File(filepath);

        if (!file.delete()) {
            System.err.println("Failed to delete tempfile at: " + file.getName());
        }
    }

    public void addInstancesFromFile(String filepath){
        try {
            Instances arff = loadArff(filepath);
            try {
                dataRaw = merge(dataRaw, arff);
            } catch (Exception e) {
                e.printStackTrace();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    /**
     * @param datafile Load arff file and convert to instances
     * @return instances
     */
    private Instances loadArff(String datafile) throws IOException {
        try {
            ConverterUtils.DataSource source = new ConverterUtils.DataSource(datafile);
            Instances data = source.getDataSet();

            if (data.classIndex() == -1)
                data.setClassIndex(data.numAttributes() - 1);
            return data;
        } catch (Exception e) {
            throw new IOException("could not read from file");
        }
    }

    /**
     * @param instancedata Demands linkedhashmap which will be parsed to an instance and added to instances
     */
    public void addInstances(LinkedHashMap<String, String> instancedata){
        double[] instanceValue1 = new double[this.dataRaw.numAttributes()];
        instanceValue1[0] = Double.parseDouble(instancedata.get("Age"));
        instanceValue1[1] = this.attributes.get(1).indexOfValue(instancedata.get("Sex"));
        instanceValue1[2] = Double.parseDouble(instancedata.get("RestingBP"));
        instanceValue1[3] = Double.parseDouble(instancedata.get("Cholesterol"));
        instanceValue1[4] = this.attributes.get(4).indexOfValue(instancedata.get("FastingBS"));
        instanceValue1[5] = this.attributes.get(5).indexOfValue(instancedata.get("RestingECG"));
        instanceValue1[6] = Double.parseDouble(instancedata.get("MaxHR"));
        instanceValue1[7] = this.attributes.get(7).indexOfValue(instancedata.get("ExerciseAngina"));
        instanceValue1[8] = Double.parseDouble(instancedata.get("Oldpeak"));
        instanceValue1[9] = this.attributes.get(9).indexOfValue(instancedata.get("ASY"));
        instanceValue1[10] = this.attributes.get(10).indexOfValue(instancedata.get("ATA"));
        instanceValue1[11] = this.attributes.get(11).indexOfValue(instancedata.get("NAP"));
        instanceValue1[12] = this.attributes.get(12).indexOfValue(instancedata.get("TA"));
        instanceValue1[13] = this.attributes.get(13).indexOfValue(instancedata.get("ST_Slope_Flat"));
        instanceValue1[14] = this.attributes.get(14).indexOfValue(instancedata.get("ST_Slope_Down"));
        instanceValue1[15] = this.attributes.get(15).indexOfValue(instancedata.get("ST_Slope_Up"));

        this.dataRaw.add(new DenseInstance(1, instanceValue1));
    }

    /**
     * @return generated attributes as Arraylist<Attribute>
     */
    public ArrayList<Attribute> getAttributes(){
        ArrayList<Attribute> attributes = new ArrayList<Attribute>(15);


        // Age Numeric
        Attribute age = new Attribute("Age");
        attributes.add(age);

        ArrayList<String> sexes = new ArrayList<>();
        sexes.add("F");
        sexes.add("M");
        Attribute sex = new Attribute("Sex", sexes);
        attributes.add(sex);

        attributes.add(new Attribute("RestingBP"));
        attributes.add(new Attribute("Cholesterol"));

        attributes.add(createFalseTrueAttribute("FastingBS"));

        ArrayList<String> restingECGValues = new ArrayList<>();
        restingECGValues.add("LVH");
        restingECGValues.add("Normal");
        restingECGValues.add("ST");
        attributes.add(new Attribute("RestingECG", restingECGValues));

        attributes.add(new Attribute("MaxHR"));
        attributes.add(createFalseTrueAttribute("ExerciseAngina"));
        attributes.add(new Attribute("Oldpeak"));
        //PainType
        attributes.add(createFalseTrueAttribute("ASY"));
        attributes.add(createFalseTrueAttribute("ATA"));
        attributes.add(createFalseTrueAttribute("NAP"));
        attributes.add(createFalseTrueAttribute("TA"));

        //ST_Slope
        attributes.add(createFalseTrueAttribute("ST_Slope_Flat"));
        attributes.add(createFalseTrueAttribute("ST_Slope_Down"));
        attributes.add(createFalseTrueAttribute("ST_Slope_Up"));

        attributes.add(createFalseTrueAttribute("HeartDisease"));
        return attributes;
    }
}
