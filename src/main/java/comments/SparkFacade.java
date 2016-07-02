package comments;

import org.apache.spark.SparkConf;
import org.apache.spark.api.java.JavaRDD;
import org.apache.spark.api.java.JavaSparkContext;
import org.apache.spark.sql.SQLContext;
import submissions.Submission;

import java.io.*;
import java.nio.file.*;


public class SparkFacade {

    private SparkConf config;
    private JavaSparkContext sc;
    private static int counter=0;
    private SQLContext sqlContext;

    public SparkFacade(String appName, String master) {
        config = new SparkConf().setAppName(appName).setMaster(master);
    }

    public void init() {
        sc = new JavaSparkContext(config);
        sqlContext= new SQLContext(sc);
       // new File(path).mkdirs();
    }

    public void writeComments(JavaRDD<Comment> comments, String path) {

       ++counter;
        File f = new File(path,""+counter);

        sqlContext.createDataFrame(comments,Comment.class).write().json(f.getAbsolutePath());
        //comments.saveAsTextFile(f.getAbsolutePath());
    }

    public void writeSubmissions(JavaRDD<Submission> submissions, String path) {

        ++counter;
        File f = new File(path,""+counter);

        sqlContext.createDataFrame(submissions,Submission.class).write().json(f.getAbsolutePath());
        //comments.saveAsTextFile(f.getAbsolutePath());
    }



    public void writeStatistics(String path,long commentsCount){

        try {

            File file= new File(path,"/comments.txt");
            if(file.getParentFile()!=null && !file.getParentFile().mkdirs()){
                System.out.println(file.getParentFile());
                System.out.println("Directory does not exist");
                boolean directoryCreated = file.mkdirs();
                System.out.println(directoryCreated);
                System.out.println(file.getAbsoluteFile());
                if(directoryCreated){
                    BufferedWriter writer= new BufferedWriter(new FileWriter(file.getAbsolutePath()));
                    writer.write(String.valueOf(commentsCount));
                    writer.flush();
                    writer.close();
                }
            }

        } catch (IOException e) {

            e.printStackTrace();
        }
    }
    public JavaRDD<Comment> asCommentStream(String file, boolean filterDeleted) {
        return sc.textFile(file+"/*")
                .map(Mappers::toComment)
                .filter(c -> (!filterDeleted || !c.isDeleted()) && c.getTldr()!=null);

    }

    public JavaRDD<Submission> asSubmissionStream(String file, boolean filterDeleted) {
        return sc.textFile(file, 315)
                .map(Mappers::toSubmission)
                .filter(c -> (!filterDeleted || !c.isDeleted()) && c.getTldr() != null);

    }


}
