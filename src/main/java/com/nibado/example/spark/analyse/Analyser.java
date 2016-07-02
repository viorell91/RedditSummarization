/*
package com.nibado.example.spark.analyse;

import com.nibado.example.spark.Comment;
import com.nibado.example.spark.Mappers;

import java.io.Serializable;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Analyser implements Serializable {


    static int lines_with_tldr=0;
    static int loop=0;
    static int valid_tldr=0;
    public AnalysedComment analyse(Comment comment) {

        String validTldr="empty";

        Pattern c = Pattern.compile("(.+?)tl;dr", Pattern.DOTALL);
        if(comment.isDeleted()) {
            throw new RuntimeException("Can't analyse deleted comments");
        }
        String currentLine= comment.getComment();
        if(currentLine.contains("tl;dr")) {
            ++loop;
          String tldrStart = currentLine.substring(currentLine.indexOf("tl;dr") + 5, currentLine.length());
            if (tldrStart.length() > 5) {
                Matcher comment_match = c.matcher(currentLine);
                if (comment_match.find() && comment_match.group(1).length() > 50) {
                  validTldr = tldrStart;
                  String commentExtract = comment_match.group(1);
                    comment.setComment(commentExtract);

                }
            }

        }
        System.out.println(loop);
        return AnalysedComment.of(comment,validTldr);

    }
}
*/
