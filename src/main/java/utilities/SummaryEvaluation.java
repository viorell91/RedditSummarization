package utilities;



import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

/**
 * Created by shahbaz on 7/14/16.
 */
public class SummaryEvaluation {

    public static void main(String[] args) {
        String inputfile="/home/shahbaz/submissionSummary.jsonl";
        ObjectMapper mapper= new ObjectMapper();
        JsonNode node=null;
        double counter=0;
        double matchedcases =0;
        try {
            BufferedReader reader = new BufferedReader(new FileReader(inputfile));
            String currentLine="";
            while((currentLine = reader.readLine()) != null){
                node = mapper.readValue(currentLine,JsonNode.class);
                String tldr= node.get("tldr").asText().toLowerCase();
                String result = node.get("max_TFIDF_TERM").asText().toLowerCase();
                if(tldr.contains(result)){
                    ++matchedcases;
                }
                ++counter;
            }

            System.out.println("Total Submissions is "+counter);
            System.out.println("Matched TF_IDF term in "+matchedcases);
            System.out.println("Percentage is " + ((matchedcases/counter)*100)+"%");

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}
