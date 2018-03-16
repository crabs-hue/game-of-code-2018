package lu.arhs.hackathon;

import com.amazon.speech.speechlet.lambda.SpeechletRequestStreamHandler;

import java.util.HashSet;
import java.util.Set;

public class GameOfCodeRequestHandler extends SpeechletRequestStreamHandler {
    private static final Set<String> supportedApplicationIds;
    static {
        /*
         * This Id can be found on https://developer.amazon.com/edw/home.html#/ "Edit" the relevant
         * Alexa Skill and put the relevant Application Ids in this Set.
         */
        supportedApplicationIds = new HashSet<String>();
        supportedApplicationIds.add("amzn1.ask.skill.da3cd19f-72c6-4d81-a535-26034c0017ca");
    }


    public GameOfCodeRequestHandler(){super(new GameOfCodeSpeehlet(), supportedApplicationIds);}
}
