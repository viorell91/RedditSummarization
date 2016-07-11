package summaries;

import java.util.List;

/**
 * Created by shahbaz on 7/9/16.
 */
public class SubmissionSummary {

    private String author;
    private String submission;
    private List<String> summary;
    private String tldr;
    private String MAX_TFIDF_TERM;

    public int getSummaryLength() {
        return summaryLength;
    }

    public void setSummaryLength(int summaryLength) {
        this.summaryLength = summaryLength;
    }

    private int summaryLength;

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getSubmission() {
        return submission;
    }

    public void setSubmission(String submission) {
        this.submission = submission;
    }

    public List<String> getSummary() {
        return summary;
    }

    public void setSummary(List<String> summary) {
        this.summary = summary;
    }

    public String getTldr() {
        return tldr;
    }

    public void setTldr(String tldr) {
        this.tldr = tldr;
    }
    public String getMAX_TFIDF_TERM() {
        return MAX_TFIDF_TERM;
    }

    public void setMAX_TFIDF_TERM(String MAX_TFIDF_TERM) {
        this.MAX_TFIDF_TERM = MAX_TFIDF_TERM;
    }

    @Override
    public String toString() {
        return String.format( "%s %s %s %s %s %d",author,submission,summary,tldr, MAX_TFIDF_TERM, summaryLength);
    }

}
