# WekaWrapper
This tool makes it possible to predict patient risk of heart disease using various variables. Predictions are made 
using a Weka exported AdaboostM1 machine learning model. 
This tool is usable in two ways:
1. Standalone tool  
2. Use as an API

The model itself needs 15 parameters, these parameters are further explained in *Attribute values*.


## Arguments
| Short name | Long name  | Usage                                                                                                               | Example                                                                          |
|------------|------------|---------------------------------------------------------------------------------------------------------------------|----------------------------------------------------------------------------------|
| -f         | -infile    | Parses instances from provided file.    The provided instances will be classified                                   | -f./data/heart_unknown.arff                                                      |
| -v         | -variables | Provide instance variable as a string.    These should be in a specific order (order can be found below this table) | -v 22,F,140,120,FALSE,Normal,180,FALSE,0,TRUE,FALSE,FALSE,FALSE,TRUE,FALSE,FALSE |
| -o         | -output    | If provided, instances will be written to arff or csv. Depending on file name                                       | -o ./data/output_example.csv                                                     |

variable order: *Age,Sex,RestingBP,Cholesterol,FastingBS,RestingECG,MaxHR,ExerciseAngina,
Oldpeak,ASY,ATA,NAP,TA,ST_Slope_Flat,ST_Slope_Down,ST_Slope_Up*  
Note: no spaces should be used only seperation with commas

## Attribute values
These are the needed input variables. **It is important to use the exact order displayed below.**   Variable string
should use commas as separators between values. No spaces should be used. An example string:

### Example: 

    22,F,140,120,FALSE,Normal,180,FALSE,0,TRUE,FALSE,FALSE,FALSE,TRUE,FALSE,FALSE 

### Order: 
    Age,Sex,RestingBP,Cholesterol,FastingBS,RestingECG,MaxHR,ExerciseAngina,
    Oldpeak,ASY,ATA,NAP,TA,ST_Slope_Flat,ST_Slope_Down,ST_Slope_Up


| Index | Name            | Type                                                                                                                  | Description                                |
|-------|-----------------|-----------------------------------------------------------------------------------------------------------------------|--------------------------------------------|
| 0     | Age             | numeric                                                                                                               | Age of patient in years                    |
| 1     | Sex             | string M: male, F: female                                                                                             | Sex of patient                             |
| 2     | RestingBP       | numeric                                                                                                               | Resting blood pressure in mm/hg            |
| 3     | Cholesterol     | numeric                                                                                                               | Serum cholesterol in mm/dl                 |
| 4     | FastingBS       | string TRUE, FALSE                                                                                                    | Fasting blood sugar > 120 mg/dl            |
| 5     | RestingECG      | string Normal: Normal, ST: having ST-T wave abnormality, LVH: showing left ventricular hypertrophy by Estes’ criteria | Resting ECG results                        |
| 6     | MaxHR           | numeric                                                                                                               | Maximum heart rate achieved                |
| 7     | ExcerciseAngina | string TRUE, FALSE                                                                                                    | Patient has exercise angina                |
| 8     | Oldpeak         | numeric valued in depression                                                                                          | Oldpeak = st                               |
| 9     | ASY             | string TRUE, FALSE                                                                                                    | Patient has asymptotic pain                |
| 10    | ATA             | string TRUE, FALSE                                                                                                    | Patient has atypical angina                |
| 11    | NAP             | string TRUE, FALSE                                                                                                    | Patient has non-anginal pain               |
| 12    | TA              | string TRUE, FALSE                                                                                                    | Patient has typical angina                 |
| 13    | ST_Slope_Flat   | string TRUE, FALSE                                                                                                    | The peak of the exercise ST is flat        |
| 14    | ST_Slope_Down   | string TRUE, FALSE                                                                                                    | The peak of the exercise ST is downsloping |
| 15    | ST_Slope_UP     | string TRUE, FALSE                                                                                                    | The peak of the exercise ST is upsloping   |



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

### WekaWrapper.run()  
Will classify all instances currently added to wrapper.instances.  
Returns instances object containing all predictions. 

## Further info
data contains 3 example files:   
**heart_cleaned.arff** Data used to create the model.  
**heart_unknown.arff** Example which can be used to try the example.   
**output.arff** Example generated output 

## Contact
Email: **s.j.bouwman@st.hanze.nl**