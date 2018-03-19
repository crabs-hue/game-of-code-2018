package lu.arhs.hackathon;

import com.amazon.speech.json.SpeechletRequestEnvelope;
import com.amazon.speech.slu.ConfirmationStatus;
import com.amazon.speech.slu.Intent;
import com.amazon.speech.speechlet.*;
import com.amazon.speech.ui.PlainTextOutputSpeech;
import lu.arhs.hackathon.domain.Event;
import lu.arhs.hackathon.domain.Parking;
import lu.arhs.hackathon.intentHandlers.EventIntentHandler;
import lu.arhs.hackathon.repository.GraphRepository;
import lu.arhs.hackathon.responses.SpeechletResponseBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Iterator;
import java.util.List;

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
                List<Event> list = GraphRepository.getEvents(49.600690970137855, 6.113794412913669, 2);
                int count = 0;
                Iterator<Event> iter = list.listIterator(count);
                Event event = iter.next();
                String eventList = String.format("This first Event, is %s and takes place in %s at %s, ", event.getDescription(),event.getLocality(), event.getStart());
                if (iter.hasNext()){
                    event = iter.next();
                    count = list.indexOf(event);
                    eventList += String.format("the next Event, is  %s and takes place in %s at %s", event.getDescription(),event.getLocality(), event.getStart());
                    session.setAttribute("count", count);
                }

                String outputText = String.format("I found %d events, %s", list.size(), eventList);

                String repromtText = String.format("Last chance to check on the events");
                return SpeechletResponseBuilder.withOutputSpeech(outputText).withRepromptOutputSpeech(repromtText).withShouldEndSession(false).buildRespons();

            case "ParkingIntent":
                List<Parking> parkings = GraphRepository.getParkings(49.600690970137855, 6.113794412913669, 2);


                String outputText2 = String.format("There are %d parking places around.", parkings.size());

                String repromtText2 = String.format("Last chance to check on the events");
                return SpeechletResponseBuilder.withOutputSpeech(outputText2).withRepromptOutputSpeech(repromtText2).withShouldEndSession(false).buildRespons();

            case "BusIntent":

            case "AMAZON.HelpIntent":
                return getHelp();
            case "AMAZON.StopIntent":
            case "AMAZON.CancelIntent":
                PlainTextOutputSpeech outputSpeech = new PlainTextOutputSpeech();
                outputSpeech.setText("Goodbye");
                return SpeechletResponse.newTellResponse(outputSpeech);
            default:
                String errorSpeech = "This is unsupported.  Please try something else.";
                return SpeechletResponseBuilder.withOutputSpeech(errorSpeech).withRepromptOutputSpeech(errorSpeech).buildRespons();

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
