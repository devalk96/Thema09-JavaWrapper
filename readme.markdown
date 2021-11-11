# WekaWrapper
This weka wrapper makes is possible to use the generated model in a CLI. 
This makes it possible to classify your own provided instances.


## Arguments
| Short name | Long name  | Usage                                                                                                               | Example                                                                          |
|------------|------------|---------------------------------------------------------------------------------------------------------------------|----------------------------------------------------------------------------------|
| -f         | -infile    | Parses instances from provided file.    The provided instances will be classified                                   | -f./data/heart_unknown.arff                                                      |
| -v         | -variables | Provide instance variable as a string.    These should be in a specific order (order can be found below this table) | -v 22,F,140,120,FALSE,Normal,180,FALSE,0,TRUE,FALSE,FALSE,FALSE,TRUE,FALSE,FALSE |
| -o         | -output    | If provided, instances will be written to arff or csv. Depending on file name                                       | -o ./data/output_example.csv                                                     |

variable order: *Age,Sex,RestingBP,Cholesterol,FastingBS,RestingECG,MaxHR,ExerciseAngina,
Oldpeak,ASY,ATA,NAP,TA,ST_Slope_Flat,ST_Slope_Down,ST_Slope_Up*  
Note: no spaces should be used only seperation with commas

## Example
        Example argument string:
        -f ./data/heart_unknown.arff -v 22,F,140,120,FALSE,Normal,180,FALSE,0,TRUE,FALSE,FALSE,FALSE,TRUE,FALSE,FALSE -o ./data/output.arff

## As API
The WekaWrapper itself can be used as an API.
Instances can be parsed from file or from linkedhashmap.
After loading/parsing the instances it is possible to perform the classificaton with 
the method WekaWrapper.run(). This will return the results as an instances object.

### WekaWrapper.instances.addInstances(linkedhashmap)
linkedhashmap needs the following keys/values: 
*Age,Sex,RestingBP,Cholesterol,FastingBS,RestingECG,MaxHR,ExerciseAngina,
Oldpeak,ASY,ATA,NAP,TA,ST_Slope_Flat,ST_Slope_Down,ST_Slope_Up*

### WekaWrapper.intances.addInstancesFromFile(filepath)
Creates an instances object and adds to instances.
A valid example can be found in data/heart_unknown.arff.

## Further info
data contains 3 example files:   
**heart_cleaned.arff** Data used to create the model.  
**heart_unknown.arff** Example which can be used to try the example.   
**output.arff** Example generated output 
## Contact
Email: **s.j.bouwman@st.hanze.nl**