package lu.arhs.hackathon;

import com.amazon.speech.json.SpeechletRequestEnvelope;
import com.amazon.speech.slu.ConfirmationStatus;
import com.amazon.speech.slu.Intent;
import com.amazon.speech.speechlet.*;
import com.amazon.speech.ui.PlainTextOutputSpeech;
import lu.arhs.hackathon.domain.Event;
import lu.arhs.hackathon.domain.Parking;
import lu.arhs.hackathon.intentHandlers.*;
import lu.arhs.hackathon.repository.GraphRepository;
import lu.arhs.hackathon.repository.GraphRepository;
import lu.arhs.hackathon.responses.SpeechletResponseBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.*;

public class GameOfCodeSpeehlet implements SpeechletV2 {

    private static final Logger log = LoggerFactory.getLogger(GameOfCodeSpeehlet.class);

    /**
     * The key to get the item from the intent.
     */
    private static final String ITEM_SLOT = "Item";

    @Override
    public void onSessionStarted(SpeechletRequestEnvelope<SessionStartedRequest> requestEnvelope) {
        log.info("onSessionStarted requestId={}, sessionId={}", requestEnvelope.getRequest().getRequestId(),
                requestEnvelope.getSession().getSessionId());

        // any initialization logic goes here
    }

    @Override
    public SpeechletResponse onLaunch(SpeechletRequestEnvelope<LaunchRequest> requestEnvelope) {
        log.info("onLaunch requestId={}, sessionId={}", requestEnvelope.getRequest().getRequestId(),
                requestEnvelope.getSession().getSessionId());

        String speechOutput =
                "Welcome to the lifestyle app of Luxembourg... Now, what can I help you with?";
        // If the user either does not reply to the welcome message or says
        // something that is not understood, they will be prompted again with this text.
        String repromptText = "Now, what can I help you with?";

        // Here we are prompting the user for input
        return SpeechletResponseBuilder.withPlainTextOutputSpeech(speechOutput).withRepromptOutputSpeech(repromptText).withShouldEndSession(false).buildRespons();
    }

    @Override
    public SpeechletResponse onIntent(SpeechletRequestEnvelope<IntentRequest> requestEnvelope) {
        IntentRequest request = requestEnvelope.getRequest();
        log.info("onIntent requestId={}, sessionId={}", request.getRequestId(),
                requestEnvelope.getSession().getSessionId());
        // session cen be used to save data
        Session session = requestEnvelope.getSession();


        Intent intent = request.getIntent();
        String intentName = (intent != null) ? intent.getName() : "s";
        log.info("onIntent intentname={}, sessionId={}", intentName,
                requestEnvelope.getSession().getSessionId());

        IntentHanderInterface handler = null;
        switch (intentName) {

            case "EventIntent":
                handler = new EventIntentHandler();
                return handler.processIntent(requestEnvelope);

            case "ParkingIntent":
                handler = new ParkingIntentHandler();
                return handler.processIntent(requestEnvelope);
            case "BusIntent":
                handler = new BusIntentHandler();
                return handler.processIntent(requestEnvelope);

            case "AMAZON.NextIntent":
                handler = new NextIntentHandler();
                return handler.processIntent(requestEnvelope);

            case "AMAZON.YesIntent":
            case "AMAZON.NoIntent":
            case "AMAZON.LoopOnIntent":
            case "AMAZON.HelpIntent":
                return getHelp();
            case "AMAZON.StopIntent":
            case "AMAZON.CancelIntent":
                PlainTextOutputSpeech outputSpeech = new PlainTextOutputSpeech();
                outputSpeech.setText("Goodbye");
                return SpeechletResponse.newTellResponse(outputSpeech);
            default:
                String errorSpeech3 = "This is unsupported.  Please try something else.";
                return SpeechletResponseBuilder.withPlainTextOutputSpeech(errorSpeech3).withRepromptOutputSpeech(errorSpeech3).buildRespons();

        }
    }


    @Override
    public void onSessionEnded(SpeechletRequestEnvelope<SessionEndedRequest> requestEnvelope) {
        log.info("onSessionEnded requestId={}, sessionId={}", requestEnvelope.getRequest().getRequestId(),
                requestEnvelope.getSession().getSessionId());

        // any cleanup logic goes here
    }


    private SpeechletResponse getHelp(){
        String speechOutput =
                "You can ask questions about minecraft such as, what's "
                        + "the recipe for a chest, or, you can say exit... "
                        + "Now, what can I help you with?";
        String repromptText =
                "You can say things like, what's the recipe for a"
                        + " chest, or you can say exit... Now, what can I help you with?";
        return SpeechletResponseBuilder.withPlainTextOutputSpeech(speechOutput).withRepromptOutputSpeech(repromptText).buildRespons();
    }

}
