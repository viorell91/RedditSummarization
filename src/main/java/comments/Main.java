package comments;

import org.apache.spark.api.java.JavaRDD;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import submissions.Submission;

public class Main {
    private static final Logger LOG = LoggerFactory.getLogger(Main.class);
    private SparkFacade facade;
    private String statisticsFilePath = "/user/bd-ss16-g2/statistics/comments.txt";

    public void run(String inputFilePath, String outputFilePath) {

        facade = new SparkFacade("RedditAnalysis","yarn-client");
        facade.init();
        JavaRDD<Comment> comments = facade.asCommentStream(inputFilePath,true);

       // long validCommentsCount=comments.count();

        facade.writeComments(comments, outputFilePath);

        //facade.writeStatistics(outputFilePath,validCommentsCount);

    }

    public static void main(String[]args) {

        new Main().run(args[0],args[1]);

    }
}
