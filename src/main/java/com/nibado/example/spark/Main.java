package com.nibado.example.spark;

//import com.nibado.example.spark.analyse.AnalysedComment;
//import com.nibado.example.spark.analyse.Analyser;
import org.apache.spark.api.java.JavaRDD;
import org.apache.spark.rdd.RDD;
import org.apache.spark.storage.StorageLevel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import scala.Tuple2;

import java.io.*;
import java.lang.reflect.Constructor;
import java.util.List;
import java.util.Properties;

public class Main {
    private static final Logger LOG = LoggerFactory.getLogger(Main.class);
    private Properties config;
    private SparkFacade facade;



    public void run(String inputFilePath, String outputFilePath) {

       // checkConfig(outputFilePath);
        facade = new SparkFacade("RedditAnalysis","yarn-client");
        facade.init();
       // Analyser analyser = createAnalyser();
        JavaRDD<Comment> comments = facade.asCommentStream(inputFilePath,true);
        facade.writeResults(comments, outputFilePath);

    }



    private void checkConfig(String outputFilePath) {

        File output = new File(outputFilePath);

        output.mkdirs();

        if(!output.exists()) {
            configException("Unable to create output dir", output);
        }
        if(output.isFile()) {
            configException("Output directory is a file", output);
        }
        if(output.listFiles().length > 0) {
            LOG.warn("Output directory {} is not empty", output.getAbsolutePath());
        }
        LOG.info("Writing to   {}", config.getProperty("output"));
    }

    private void configException(String message, File file) {
        String exception = String.format(message, file.getAbsoluteFile());
        LOG.error(exception);
        throw new RuntimeException(exception);
    }


    /*public Analyser createAnalyser() {
        return new Analyser();
    }*/



    public static void main(String[]args) {


        new Main().run(args[0],args[1]);
    }
}
