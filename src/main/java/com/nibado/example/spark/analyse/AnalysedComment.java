/*
package com.nibado.example.spark.analyse;

import com.nibado.example.spark.Comment;
import com.nibado.example.spark.Mappers;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;
import java.util.Locale;

import static com.nibado.example.spark.Mappers.toDateString;

public class AnalysedComment implements Serializable {
    public static final long serialVersionUID = 1L;
    public static final double SENTIMENT_CUTOFF = 0.1;
    private String subReddit;
    private String author;
    private long timeStamp;
    private String[] body;
    private int words;
    private String comment;
    private String tldr;

    public static AnalysedComment of(Comment comment,String tldr) {
        AnalysedComment newComment = new AnalysedComment();

       // newComment.subReddit = comment.getSubReddit();
        newComment.author = comment.getAuthor();
        //newComment.timeStamp = comment.getTimeStamp();
       // newComment.words = comment.getBody().length;
        newComment.comment = comment.getComment();
        newComment.tldr = tldr;

        return newComment;
    }



    public String getSubReddit() {
        return subReddit;
    }

    public String getAuthor() {
        return author;
    }

    public long getTimeStamp() {
        return timeStamp;
    }

    public String[] getBody() {
        return body;
    }

    public LocalDateTime getDateTime() {
        return LocalDateTime.ofInstant(new Date(timeStamp * 1000).toInstant(), ZoneId.systemDefault());
    }

    @Override
    public String toString() {
        return String.format(Locale.ROOT, "%s %s %s %s", toDateString(timeStamp), comment, getTldr(), words);
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
}
*/
