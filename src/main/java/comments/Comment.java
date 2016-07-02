package comments;

import java.io.Serializable;
import java.util.Locale;

/**
 * Comment POJO
 */
public class Comment implements Serializable {
    public static final long serialVersionUID = 1L;
    private String author;
    private String timeStamp;
 //   private String[] body;
    private String comment;
    private String tldr;
    private boolean deleted;
    private static int validComments=0;

    public static int getValidComments() {
        return validComments;
    }

    public static void setValidComments() {

       ++Comment.validComments;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public String getTldr() {
        return tldr;
    }

    public void setTldr(String tldr) {
        this.tldr = tldr;
    }

    public String getAuthor() {
        return author;
    }

    public String getTimeStamp() {
        return timeStamp;
    }

    public boolean isDeleted() {
        return deleted;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public void setTimeStamp(String timeStamp) {
        this.timeStamp = timeStamp;
    }

    public void setDeleted(boolean deleted) {
        this.deleted = deleted;
    }

    @Override
    public String toString() {
        return String.format(Locale.ROOT, "%s %s %s %s", timeStamp,comment,tldr,validComments);
    }


}
