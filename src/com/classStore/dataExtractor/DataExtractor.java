package com.classStore.dataExtractor;
import com.opencsv.CSVWriter;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import java.io.*;

public class DataExtractor {
    public static void main(String[] args) {
        org.json.simple.parser.JSONParser myParser = new JSONParser();
        String requiredKeys[] = {"sourceApplication","country","historyType","dataFilename","sourceType","version","informationDate","conform_input_data_size","raw_format","conform_output_data_size","enceladus_info_date","conform_record_count"};
        String[] myData = new String[12];

        try
        {
            //Parsing the Json file
            Object obj = myParser.parse(new FileReader(".\\myJSONFile\\SVN390D_20210420_INFO.json"));

            JSONObject myJsonObject = (JSONObject) obj;

            //Getting the metadata object
            JSONObject myMetaData  = (JSONObject) myJsonObject.get("metadata");

            //Getting the desired fields from the metadata object
            System.out.println("Extracting Data");
            for(int i = 0 ; i < requiredKeys.length;i++)
            {
                 String tempVal= String.valueOf(myMetaData.get(requiredKeys[i]));

                if(tempVal.equalsIgnoreCase("null"))
                {
                    //Get additionalInfo object
                    JSONObject additionalInfoObj  = (JSONObject) myMetaData.get("additionalInfo");

                    //Getting the desired fields from the additionalInfoObj object
                    tempVal= String.valueOf(additionalInfoObj.get(requiredKeys[i]));
                    myData[i] = tempVal;
                }
                else{myData[i] = tempVal;}
                System.out.println(myData[i]);
            }
            //Write data to CSV
            CSVWriter write = new CSVWriter(new FileWriter("CSVSave//mainCSV.csv"));

            //Write column names
            write.writeNext(requiredKeys);

            //Write extracted data.
            write.writeNext(myData);

            write.flush();
            System.out.println("Data has been saved to CSV File");
        }
        catch(FileNotFoundException e){e.printStackTrace();}
        catch(IOException e){e.printStackTrace();}
        //catch(ParseException e){e.printStackTrace();}
        catch(Exception e){e.printStackTrace();}
    }
}

