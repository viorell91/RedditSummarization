package utilities;

import com.fasterxml.jackson.core.JsonFactory;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import jdk.nashorn.internal.parser.JSONParser;
import summaries.SubmissionSummary;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

/**
 * Created by shahbaz on 7/9/16.
 */
public class Summarizer {

        private static final Pattern END_OF_SENTENCE = Pattern.compile("(?<=\\.)\\s+");
        private static final double NUMBER_OF_SUBMISSIONS = 740386;
        private static Map<String, Integer> submissionFrequencyOfTerms = new HashMap<>(); // Document frequency of a term


        private static final String inputfilepath = "/home/shahbaz/submissionsTf.jsonl";
        private static final String serializedSubmissionFreq = "/home/shahbaz/submissionFrequencies.ser";
        private static final String outputfilepath = "/home/shahbaz/submissionSummary.jsonl";

    public void generateSummaries(String filePath) throws IOException {


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

        ObjectMapper writeMapper= new ObjectMapper();
        BufferedWriter  writer = new BufferedWriter(new FileWriter(outputfilepath));
        try (BufferedReader bufferedReader = new BufferedReader(new FileReader(filePath))) {

            ObjectMapper objectMapper = new ObjectMapper();


            LoadStats stats = new LoadStats();
            submissionFrequencyOfTerms = stats.loadSubmissionFrequencies(serializedSubmissionFreq);
            int i = 0;
            String sCurrentLine;
            while ((sCurrentLine = bufferedReader.readLine()) != null) {
                double MAX_TFIDF_SCORE= 0.0;
                String MAX_TFIDF_TERM = null;
                List<String> summary;
                JsonNode node;
                SubmissionSummary submissionSummary = new SubmissionSummary();
                Map<String,Integer> termfrequencies = new HashMap<>();
                node = objectMapper.readValue(sCurrentLine, JsonNode.class);
                String author= node.get("author").asText();
                String tldr = node.get("tldr").asText();
                String submission = node.get("submission").asText();
                // [^a-zA-Z'-]|-.*-|^-|-$
                //[\W&&[^\s]]
                String[] words = submission.replaceAll("[\\W&&[^\\s]]", "").split("\\W+");

               // Calculate TF
                for (String term : words) {
                    term = term.intern();
                    if (!stopWords.contains(term)) {

                        if(!termfrequencies.containsKey(term)){
                            termfrequencies.put(term,1);
                        }
                        else {
                            termfrequencies.put(term,termfrequencies.get(term)+1);
                        }
                    }
                }

                // For each term in tf , calculate idf to get tfidf score

                for (Map.Entry<String, Integer> entry : termfrequencies.entrySet()) {

                    double tfIdfScore= entry.getValue()*calculateIDF(entry.getKey(),submissionFrequencyOfTerms);
                    if(tfIdfScore>MAX_TFIDF_SCORE){
                        MAX_TFIDF_SCORE=tfIdfScore;
                        MAX_TFIDF_TERM=entry.getKey();
                    }
                }
                summary=getSentencesContaining(submission,MAX_TFIDF_TERM);
                if (++i % 10000 == 0) {
                    System.out.println("Finished indexing " + i + " documents");
                }

                if(summary.size()>0){
                    // Write the Summary object for each submission
                    int lengthofsummary = summary.stream().collect(Collectors.joining()).replaceAll("[\\W&&[^\\s]]", "").split("\\W+").length;

                    submissionSummary.setAuthor(author);
                    submissionSummary.setSubmission(submission);
                    submissionSummary.setSummary(summary);
                    submissionSummary.setTldr(tldr);
                    submissionSummary.setMAX_TFIDF_TERM(MAX_TFIDF_TERM);
                    submissionSummary.setSummaryLength(lengthofsummary);
                    writer.write(writeMapper.writeValueAsString(submissionSummary));
                    writer.newLine();
                }


            }
            bufferedReader.close();
        }

        writer.flush();
        writer.close();
    }




        public double calculateIDF(String term, Map<String,Integer> submissionFrequencyOfTerms){

            return Math.log(1+NUMBER_OF_SUBMISSIONS/submissionFrequencyOfTerms.get(term));


        }



        private String readFileContent(String filePath)throws IOException {
            Path path = Paths.get(filePath);
            String content = Files.lines(path)
                    .filter(line->line.length()>4)
                    .map(String::trim)
                    .collect(Collectors.joining());
            return content;
        }

        public List<String> getSentencesContaining(String text, String word){
            final String localWord= word.toLowerCase();

            return END_OF_SENTENCE.splitAsStream(text)
                    .filter(s ->  s.toLowerCase().contains(localWord))
                    .collect(Collectors.toList());

        }


        public static void main(String[] args) {
            Summarizer summarizer = new Summarizer();
            try {

                long startTime = System.currentTimeMillis();
                summarizer.generateSummaries(inputfilepath);
                long endTime = System.currentTimeMillis();
                long totalTime = endTime - startTime;
                System.out.println("Time taken" + (new SimpleDateFormat("mm:ss:SSS")).format(new Date(totalTime)));


            } catch (IOException e) {
                e.printStackTrace();
            }


        }




    }


