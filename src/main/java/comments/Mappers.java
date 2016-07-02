package comments;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import submissions.Submission;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Consists of functions to create Comment and Submission object from the data set
 */
public class Mappers {
    private static final ObjectMapper TO_JSON = new ObjectMapper();
    private static final SimpleDateFormat DATE_FORMAT = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    public static Comment toComment(String s) {
        final JsonNode node;
        Pattern c = Pattern.compile("(.+?)tl;dr", Pattern.DOTALL);
        try {
            node = TO_JSON.readValue(s,JsonNode.class);
        }
        catch(IOException e) {
            throw new RuntimeException(e);
        }
        Comment comment = new Comment();
        if((node.get("created_utc")!=null) && (node.get("author")!=null) && (node.get("body")!=null) ) {


           // comment.setTimeStamp(toDateString(Long.parseLong(node.get("created_utc").asText())));
            comment.setAuthor(node.get("author").asText().toLowerCase());
            comment.setDeleted("[deleted]".equals(comment.getAuthor()));
            if (comment.isDeleted()) {
                comment.setComment(null);
                comment.setTldr(null);
            } else {
               // comment.setComment(node.get("body").asText());
                String currentLine = node.get("body").asText().toLowerCase();
                if (currentLine.contains("tl;dr") || currentLine.contains("tldr")) {
                    String tldrStart = currentLine.substring(currentLine.indexOf("tl;dr") + 5, currentLine.length());
                    if (tldrStart.length() > 5) {
                        Matcher comment_match = c.matcher(currentLine);
                        if (comment_match.find() && comment_match.group(1).length() > 50) {
                            String commentExtract = comment_match.group(1);
                            comment.setComment(commentExtract);
                            comment.setTldr(tldrStart);
                            Comment.setValidComments();

                        }
                    }

                }


            }
        }
        return comment;
    }

    public static Submission toSubmission(String s){

        final JsonNode node;
        Pattern c = Pattern.compile("(.+?)tl;dr", Pattern.DOTALL);
        try {
            node = TO_JSON.readValue(s,JsonNode.class);
        }
        catch(IOException e) {
            throw new RuntimeException(e);
        }
        Submission submission = new Submission();
        if((node.get("created_utc")!=null) && (node.get("author")!=null) && (node.get("selftext")!=null) ){

           // submission.setTimeStamp(toDateString(Long.parseLong(node.get("created_utc").asText())));
            submission.setAuthor(node.get("author").asText().toLowerCase());
            submission.setDeleted("[deleted]".equals(submission.getAuthor()));
            if(submission.isDeleted()) {
                submission.setSubmission(null);
                submission.setTldr(null);
            }
            else {
                // submission.setComment(node.get("selftext").asText());
                String currentLine = node.get("selftext").asText().toLowerCase();
                if (currentLine.contains("tl;dr")|| currentLine.contains("tldr")) {
                    String tldrStart = currentLine.substring(currentLine.indexOf("tl;dr") + 5, currentLine.length());
                    if (tldrStart.length() > 5) {
                        Matcher submission_match = c.matcher(currentLine);
                        if (submission_match.find() && submission_match.group(1).length() > 50) {
                            String submissionExtract = submission_match.group(1);
                            submission.setSubmission(submissionExtract);
                            submission.setTldr(tldrStart);
                            //Submission.setValidComments();

                        }
                    }

                }

            }
        }


        return submission;

    }


    public static String toDayOfWeek(LocalDateTime dt) {
        String day = dt.getDayOfWeek().toString();

        return day;
    }

    public static String toDateString(long timeStamp) {
        return DATE_FORMAT.format(new Date(timeStamp * 1000));
    }

    public static String[] toWords(String s) {
        s = s.substring(s.lastIndexOf("|") + 1);
        s = s.toLowerCase();
        s = s.replaceAll("[^a-z ]+", " ");

        return s.split("\\s+");
    }
}
