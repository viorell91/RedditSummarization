package com.nibado.example.spark;

//import com.nibado.example.spark.analyse.AnalysedComment;
//import com.nibado.example.spark.analyse.Analyser;
import org.apache.commons.io.FilenameUtils;
import org.apache.spark.SparkConf;
import org.apache.spark.api.java.JavaRDD;
import org.apache.spark.api.java.JavaSparkContext;
import org.apache.spark.rdd.RDD;
import org.apache.spark.sql.SQLContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.apache.spark.sql.SQLContext.implicits$;
import scala.Tuple2;

import java.io.*;
import java.util.List;

import static com.nibado.example.spark.Mappers.toDayOfWeek;
import static java.util.Arrays.asList;

public class SparkFacade {
    private static final Logger LOG = LoggerFactory.getLogger(SparkFacade.class);

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


    }

   /* public JavaRDD<AnalysedComment> asAnalysedCommentStream(String file) {
        return sc.objectFile(file);
    }
*/


    /* public RDD<AnalysedComment> asAnalysedCommentStream(String inputfile, String outputfile,Analyser analyser) {
         JavaRDD<Comment> original_comments = asCommentStream(inputfile,true);
         return original_comments
                 .map(analyser::analyse)
                 .rdd();

        //return sc.objectFile(outputfile);
    }
*/

    public void writeResults(JavaRDD<Comment> comments, String path) {

       ++counter;
        File f = new File(path,""+counter);

        sqlContext.createDataFrame(comments,Comment.class).write().json(f.getAbsolutePath());
        //comments.saveAsTextFile(f.getAbsolutePath());
    }

    public JavaRDD<Comment> asCommentStream(String file, boolean filterDeleted) {
        return sc.textFile(file)
                .map(Mappers::toComment)
                .filter(c -> (!filterDeleted || !c.isDeleted()) && c.getTldr()!=null);

    }

   /* public void toObjectFile(String inFile, String outFile, Analyser analyser) {
        asCommentStream(inFile, true)
            .map(analyser::analyse)
            .saveAsObjectFile(outFile);
    }*/

}
