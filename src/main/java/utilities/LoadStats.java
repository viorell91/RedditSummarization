package utilities;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by shahbaz on 7/9/16.
 */
public class LoadStats {

    private static Map<String,Integer> submissionFrequencies=new HashMap<>();
    private static final double NUMBER_OF_SUBMISSIONS = 740386;

    public Map<String,Integer> loadSubmissionFrequencies(String filepath){
        try {

            FileInputStream fis = new FileInputStream(filepath);
            ObjectInputStream ois = new ObjectInputStream(fis);

            submissionFrequencies = (Map<String, Integer>) ois.readObject();
            ois.close();
            fis.close();

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return submissionFrequencies;
    }



}
