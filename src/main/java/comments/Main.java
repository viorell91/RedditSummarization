package comments;

import org.apache.spark.api.java.JavaRDD;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Main {
	//comment
    private static final Logger LOG = LoggerFactory.getLogger(Main.class);
    private SparkFacade facade;
    private double totalComments;

    //Trying to push to git
    public void run(String inputFilePath, String outputFilePath) {

        facade = new SparkFacade("RedditAnalysis","yarn-client");
        facade.init();
        JavaRDD<Comment> comments = facade.asCommentStream(inputFilePath,true);

        facade.writeComments(comments, outputFilePath);
        totalComments=facade.countComments(inputFilePath);
        System.out.println("Total submissions read "+ totalComments);
    }

    public static void main(String[]args) {

        new Main().run(args[0],args[1]);

    }
}
