package lu.arhs.hackathon.intentHandlers;

import com.amazon.speech.json.SpeechletRequestEnvelope;
import com.amazon.speech.slu.Intent;
import com.amazon.speech.speechlet.IntentRequest;
import com.amazon.speech.speechlet.Session;
import com.amazon.speech.speechlet.SpeechletResponse;
import lu.arhs.hackathon.GameOfCodeSpeehlet;
import lu.arhs.hackathon.domain.Event;
import lu.arhs.hackathon.repository.GraphRepository;
import lu.arhs.hackathon.responses.SpeechletResponseBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

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
            location = "Merl";

        }

        List<Event> events = GraphRepository.getEvents(12.5, 12.5, 5);


        String outputText = String.format("I found %d events, should we have a look on the first one?", events.size());

        String repromtText = String.format("Last chance to check on the events in %s", location);


        return SpeechletResponseBuilder.withOutputSpeech(outputText).withRepromptOutputSpeech(repromtText ).withShouldEndSession(false).buildRespons();
    }
}
