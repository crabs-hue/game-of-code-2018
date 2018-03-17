package lu.arhs.hackathon.intentHandlers;

import com.amazon.speech.json.SpeechletRequestEnvelope;
import com.amazon.speech.slu.Intent;
import com.amazon.speech.speechlet.IntentRequest;
import com.amazon.speech.speechlet.Session;
import com.amazon.speech.speechlet.SpeechletResponse;
import lu.arhs.hackathon.GameOfCodeSpeehlet;
import lu.arhs.hackathon.responses.SpeechletResponseBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class EventIntentHandler {
    private static final Logger log = LoggerFactory.getLogger(EventIntentHandler.class);


    public SpeechletResponse getEvents(SpeechletRequestEnvelope<IntentRequest> requestEnvelope){
        IntentRequest request = requestEnvelope.getRequest();
        log.info("getEvent requestId={}, sessionId={}", request.getRequestId(),
                requestEnvelope.getSession().getSessionId());

        Session session = requestEnvelope.getSession();
        Intent intent = request.getIntent();
        String location = intent.getSlot("location").getValue();

        if (null == location){

        }

        String outputText = String.format("I found %d events, should we have a look on the first one?", 10);

        String repromtText = String.format("Last chance to check on the events in %s", location);


        return SpeechletResponseBuilder.withOutputSpeech(outputText).withRepromptOutputSpeech(repromtText ).withShouldEndSession(false).buildRespons();
    }
}
