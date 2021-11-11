package nl.bioinf;

import org.apache.commons.cli.*;
import weka.core.Instances;
import weka.core.converters.ConverterUtils;

import java.io.IOException;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;

public class CLIHandler {
    CommandLine cmd;
    Options options = buildOptions();
    HelpFormatter formatter = new HelpFormatter();
    ArrayList<LinkedHashMap<String, String>> variables = new ArrayList<>();
    String outputFormat = null;
    String outputPath = null;


    /**
     * @return Option format which will be used for commandline argument parsing
     */
    private static Options buildOptions(){
        String format = "Age,Sex,RestingBP,FastingBS,Cholesterol,FastingBS,RestingBS,RestingECG," +
                "MaxHR,ExcerciseAngina,Oldpeak,ASY,ATA,NAP,TA,ST_Slope_Flat,ST_Slope_Down,ST_Slope_Up";
        Options options = new Options();
        options.addOption(new Option("f", "infile", true,
                "Get instances from file instead of parsing as arguments"));
        options.addOption( new Option("v",
                "variables", true,
                "Provide arguments trough commandline in following format (commas as separators and without spaces): " + format));
        options.addOption(new Option("o", "output", true, "If path is provided, output file is created"));
        return options;
    }

    /**
     * @param args Parsed command line arguments
     */
    public CLIHandler(String[] args) {
        try {
            CommandLineParser parser = new DefaultParser();
            cmd = parser.parse(options, args);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        validateArgs();
    }

    /**
     * Initialises arguments so it can be accessed
     */
    private void validateArgs(){
        if (this.cmd.getOptionValue("variables") != null){
            parseVariables();
        }


        if (this.cmd.getOptionValue("output") != null){
            parseOutput();
        }
    }

    /**
     * Main method for parsing to file. Method gets format and sets this to variable, for easy later access.
     */
    private void parseOutput() {
        String filepath = this.cmd.getOptionValue("output");
        String format = filepath.substring(filepath.lastIndexOf(".") + 1);
        System.out.println("format = " + format);
        this.outputPath = filepath;


        switch (format) {
            case "arff" -> this.outputFormat = "arff";
            case "csv" -> this.outputFormat = "csv";
            default -> {
                this.outputFormat = "arff";
                System.err.println("No format recognised in output string, default to .arff");
            }
        }
    }

    /**
     * Adds commandline arguments to class variable as hashmap so it can be easily processed.
     */
    private void parseVariables(){

        String variableString = this.cmd.getOptionValue("variables");
        String[] splitted = variableString.split(",");
        if (splitted.length != 16){
            formatter.printHelp("variables", this.options);
            throw new IllegalArgumentException("Something went wrong while parsing provided variables trough commandline.");
        }
        this.variables.add(getHashmap(splitted));
    }

    /**
     * @param splitted Accepts the provided arguments in String[] format splitted on commas
     * @return linkedhashmap with names of attributes as keys
     */
    private LinkedHashMap<String, String> getHashmap(String[] splitted){
        LinkedHashMap<String, String> variables = new LinkedHashMap<>();

        String[] variableNames = ("Age,Sex,RestingBP,Cholesterol,FastingBS,RestingECG,MaxHR,ExerciseAngina," +
                "Oldpeak,ASY,ATA,NAP,TA,ST_Slope_Flat,ST_Slope_Down,ST_Slope_Up").split(",");
        for (int i = 0; i < variableNames.length; i++){
            variables.put(variableNames[i], splitted[i]);
        }
        return variables;
    }




    // example commandline: -c 22,F,140,120,FALSE,Normal,180,FALSE,0,TRUE,FALSE,FALSE,FALSE,TRUE,FALSE,FALSE
    public static void main(String[] args) {
        CLIHandler cli = new CLIHandler(args);
    }
}
