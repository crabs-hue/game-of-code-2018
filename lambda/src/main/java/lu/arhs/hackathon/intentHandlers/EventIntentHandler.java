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

import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedHashMap;
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

        session.setAttribute("list",events);
        session.setAttribute("count",0);

        String outputText = String.format("I found %d events, should we have a look on them?", events.size());

        String repromtText = String.format("Last chance to check on the events in %s", location);


        return SpeechletResponseBuilder.withOutputSpeech(outputText).withRepromptOutputSpeech(repromtText ).withShouldEndSession(false).withDialogElcit(intent).buildRespons();
    }


    public SpeechletResponse iterateOverList(SpeechletRequestEnvelope<IntentRequest> requestEnvelope){
        IntentRequest request = requestEnvelope.getRequest();
        log.info("iterateOverList requestId={}, sessionId={}", request.getRequestId(),
                requestEnvelope.getSession().getSessionId());

        Session session = requestEnvelope.getSession();
        Intent intent = request.getIntent();

        String outSpeech = "No more events found. What else can I do for you?";
        List<LinkedHashMap> list = (ArrayList<LinkedHashMap>)session.getAttribute("list");
        int count = (int) session.getAttribute("count");
        Iterator<LinkedHashMap> iter = list.listIterator(count);
        if (iter.hasNext()){
            LinkedHashMap event = iter.next();
            count = list.indexOf(event);
            outSpeech = String.format("This Event, %s and takes place in %s at %t", event.get("description"),event.get("lan"), event.get("start"));
            session.setAttribute("count", count);
        }

        String repromtText = String.format("Select this event or take a look at the next one");

        return SpeechletResponseBuilder.withOutputSpeech(outSpeech).withRepromptOutputSpeech(repromtText ).withShouldEndSession(false).withDelegateDialog(intent).buildRespons();

    }

}
