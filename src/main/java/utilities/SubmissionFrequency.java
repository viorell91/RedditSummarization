package utilities;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.*;
import java.util.*;

/**
 * Created by shahbaz on 7/9/16.
 */
public class SubmissionFrequency {

    private static Map<String, Integer> submissionFrequencyOfTerms = new HashMap<>(); // Document frequency of a term

    public void parseFiles(String filePath) throws IOException {
        ObjectMapper objectMapper = new ObjectMapper();
        JsonNode node;

        String[] stopwords = {"a", "about", "above", "above", "across", "after", "afterwards", "again", "against", "all", "almost",
                "alone", "along", "already", "also", "although", "always", "am", "among", "amongst", "amoungst", "amount", "an", "and",
                "another", "any", "anyhow", "anyone", "anything", "anyway", "anywhere", "are", "around", "as", "at", "back", "be", "became",
                "because", "become", "becomes", "becoming", "been", "before", "beforehand", "behind", "being", "below", "beside", "besides",
                "between", "beyond", "bill", "both", "bottom", "but", "by", "call", "can", "cannot", "cant", "co", "con", "could", "couldnt",
                "cry", "de", "describe", "detail", "do", "done", "down", "due", "during", "each", "eg", "eight", "either", "eleven", "else",
                "elsewhere", "empty", "enough", "etc", "even", "ever", "every", "everyone", "everything", "everywhere", "except", "few",
                "fifteen", "fify", "fill", "find", "fire", "first", "five", "for", "former", "formerly", "forty", "found", "four", "from",
                "front", "full", "further", "get", "give", "go", "had", "has", "hasnt",
                "have", "he", "hence", "her", "here", "hereafter", "hereby", "herein", "hereupon", "hers", "herself",
                "him", "himself", "his", "how", "however", "hundred", "ie", "if", "in", "inc", "indeed", "interest", "into",
                "is", "it", "its", "itself", "keep", "last", "latter", "latterly", "least", "less", "ltd", "made", "many",
                "may", "me", "meanwhile", "might", "mill", "mine", "more", "moreover", "most", "mostly", "move", "much", "must",
                "my", "myself", "name", "namely", "neither", "never", "nevertheless", "next", "nine", "no", "nobody", "none",
                "noone", "nor", "not", "nothing", "now", "nowhere", "of", "off", "often", "on", "once", "one", "only", "onto",
                "or", "other", "others", "otherwise", "our", "ours", "ourselves", "out", "over", "own", "part", "per", "perhaps",
                "please", "put", "rather", "re", "same", "see", "seem", "seemed", "seeming", "seems", "serious", "several", "she",
                "should", "show", "side", "since", "sincere", "six", "sixty", "so", "some", "somehow", "someone", "something",
                "sometime", "sometimes", "somewhere", "still", "such", "system", "take", "ten", "than", "that", "the", "their",
                "them", "themselves", "then", "thence", "there", "thereafter", "thereby", "therefore", "therein", "thereupon",
                "these", "they", "thickv", "thin", "third", "this", "those", "though", "three", "through", "throughout", "thru",
                "thus", "to", "together", "too", "top", "toward", "towards", "twelve", "twenty", "two", "un", "under", "until",
                "up", "upon", "us", "very", "via", "was", "we", "well", "were", "what", "whatever", "when", "whence", "whenever",
                "where", "whereafter", "whereas", "whereby", "wherein", "whereupon", "wherever", "whether", "which", "while",
                "whither", "who", "whoever", "whole", "whom", "whose", "why", "will", "with", "within", "without", "would", "yet",
                "you", "your", "yours", "yourself", "yourselves", "1", "2", "3", "4", "5", "6", "7", "8", "9", "10", "1.", "2.", "3.", "4.", "5.", "6.", "11",
                "7.", "8.", "9.", "12", "13", "14", "A", "B", "C", "D", "E", "F", "G", "H", "I", "J", "K", "L", "M", "N", "O", "P", "Q", "R", "S", "T", "U", "V", "W", "X", "Y", "Z",
                "terms", "CONDITIONS", "conditions", "values", "interested.", "care", "sure", ".", "!", "@", "#", "$", "%", "^", "&", "*", "(", ")", "{", "}", "[", "]", ":", ";", ",", "<", ".", ">", "/", "?", "_", "-", "+", "=",
                "a", "b", "c", "d", "e", "f", "g", "h", "i", "j", "k", "l", "m", "n", "o", "p", "q", "r", "s", "t", "u", "v", "w", "x", "y", "z",
                "contact", "grounds", "buyers", "tried", "said,", "plan", "value", "principle.", "forces", "sent:", "is,", "was", "like",
                "discussion", "tmus", "diffrent.", "layout", "area.", "thanks", "thankyou", "hello", "bye", "rise", "fell", "fall", "psqft.", "http://", "km", "miles"};

        List<String> stopWords = Arrays.asList(stopwords);


        try (BufferedReader bufferedReader = new BufferedReader(new FileReader(filePath))) {
            int i = 0;
            String sCurrentLine;
            while ((sCurrentLine = bufferedReader.readLine()) != null) {

                Set<String> uniqueTermsInDocument = new HashSet<>();
                node = objectMapper.readValue(sCurrentLine, JsonNode.class);

                String submission = node.get("submission").asText();
                String[] words = submission.replaceAll("[\\W&&[^\\s]]", "").split("\\W+");

                for (String term : words) {
                    term = term.intern();
                    if (!stopWords.contains(term)) {

                        if (!uniqueTermsInDocument.contains(term)) {
                            uniqueTermsInDocument.add(term);

                            if (!submissionFrequencyOfTerms.containsKey(term)) {
                                submissionFrequencyOfTerms.put(term, 1);
                            } else {
                                submissionFrequencyOfTerms.put(term, submissionFrequencyOfTerms.get(term) + 1);
                            }
                        }
                    }
                }
                if (++i % 10000 == 0) {
                    System.out.println("Finished indexing " + i + " documents");
                }


            }

        }

        // Write IdfScores























    }


    // Serializing document frequencies for terms
    public void generateSubmissionFrequencies() throws IOException{
        FileOutputStream fosDF = new FileOutputStream("/home/shahbaz/submissionFrequencies.ser");
        ObjectOutputStream oosDF = new ObjectOutputStream(fosDF);

        oosDF.writeObject(submissionFrequencyOfTerms);

        oosDF.reset();
        oosDF.close();
        System.out.println("Finished saving submission frequencies for the collection");
    }



    public void writeIDFScores(String filepath){

        try {
            BufferedWriter writer= new BufferedWriter(new FileWriter(filepath));






        } catch (IOException e) {
            e.printStackTrace();
        }

    }



    public static void main(String[] args) {

        SubmissionFrequency sf = new SubmissionFrequency();
        String filepath= "/home/shahbaz/submissionsTf.jsonl";
        try {
            sf.parseFiles(filepath);
            sf.generateSubmissionFrequencies();
        } catch (IOException e) {
            System.out.println("File not found");
        }

    }

}
