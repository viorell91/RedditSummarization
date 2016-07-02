package submissions;

import comments.Comment;
import comments.SparkFacade;
import org.apache.spark.api.java.JavaRDD;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created by shahbaz on 7/2/16.
 */
public class Main {

    private static final Logger LOG = LoggerFactory.getLogger(Main.class);
    private SparkFacade facade;
    private String statisticsFilePath = "/user/bd-ss16-g2/statistics/comments.txt";

    public void run(String inputFilePath, String outputFilePath) {

        facade = new SparkFacade("RedditAnalysis","yarn-client");
        facade.init();

        JavaRDD<Submission> submissions = facade.asSubmissionStream(inputFilePath,true);
        // long validCommentsCount=comments.count();

        facade.writeSubmissions(submissions, outputFilePath);

        //facade.writeStatistics(outputFilePath,validCommentsCount);

    }

    public static void main(String[]args) {

        new Main().run(args[0],args[1]);

    }
}
