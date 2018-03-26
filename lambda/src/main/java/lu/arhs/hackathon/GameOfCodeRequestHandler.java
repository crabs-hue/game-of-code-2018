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
        supportedApplicationIds.add("amzn1.ask.skill.47cee236-2bbd-4000-8e59-3a3b1380b50d");
    }


    public GameOfCodeRequestHandler(){super(new GameOfCodeSpeechlet(), supportedApplicationIds);}
}
