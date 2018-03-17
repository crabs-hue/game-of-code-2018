package lu.arhs.hackathon;

import com.amazon.speech.json.SpeechletRequestEnvelope;
import com.amazon.speech.slu.ConfirmationStatus;
import com.amazon.speech.slu.Intent;
import com.amazon.speech.speechlet.*;
import com.amazon.speech.ui.PlainTextOutputSpeech;
import lu.arhs.hackathon.domain.Event;
import lu.arhs.hackathon.intentHandlers.EventIntentHandler;
import lu.arhs.hackathon.responses.SpeechletResponseBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

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
        return SpeechletResponseBuilder.withOutputSpeech(speechOutput).withRepromptOutputSpeech(repromptText).withShouldEndSession(false).buildRespons();
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

        switch (intentName) {
            case "EventIntent":

                    EventIntentHandler handler = new EventIntentHandler();
                    return handler.getEvents(requestEnvelope);



            case "AMAZON.NextIntent":

                List<LinkedHashMap> list = (ArrayList<LinkedHashMap>) session.getAttribute("list");
                if ( null != list && !list.isEmpty()) {
                    if ("Event".equals(list.get(0).get("type"))) {
                        EventIntentHandler handler1 = new EventIntentHandler();
                        return handler1.iterateOverList(requestEnvelope);
                    }
                }
                String errorSpeech = "This list is curently unsupported.  Please try something else.";
                return SpeechletResponseBuilder.withOutputSpeech(errorSpeech).withRepromptOutputSpeech(errorSpeech).buildRespons();


            case "AMAZON.YesIntent":
                List<LinkedHashMap> elist = (ArrayList<LinkedHashMap>) session.getAttribute("list");
                if ( null != elist && !elist.isEmpty()) {
                    if ("Event".equals(elist.get(0).get("type"))) {
                        EventIntentHandler handler2 = new EventIntentHandler();
                        return handler2.iterateOverList(requestEnvelope);
                    }
                }
                String errorSpeech2 = "This list is curently unsupported.  Please try something else.";
                return SpeechletResponseBuilder.withOutputSpeech(errorSpeech2).withRepromptOutputSpeech(errorSpeech2).buildRespons();


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
                return SpeechletResponseBuilder.withOutputSpeech(errorSpeech3).withRepromptOutputSpeech(errorSpeech3).buildRespons();

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
        return SpeechletResponseBuilder.withOutputSpeech(speechOutput).withRepromptOutputSpeech(repromptText).buildRespons();
    }

}
