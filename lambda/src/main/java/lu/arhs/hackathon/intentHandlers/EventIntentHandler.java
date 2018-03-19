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



    public SpeechletResponse askForLocality(SpeechletRequestEnvelope<IntentRequest> requestEnvelope){
        IntentRequest request = requestEnvelope.getRequest();
        log.info("getEvent requestId={}, sessionId={}", request.getRequestId(),
                requestEnvelope.getSession().getSessionId());

        Session session = requestEnvelope.getSession();
        Intent intent = request.getIntent();

        return SpeechletResponseBuilder.delegateDialog(intent);
    }


    public SpeechletResponse getEvents(SpeechletRequestEnvelope<IntentRequest> requestEnvelope){
        IntentRequest request = requestEnvelope.getRequest();
        log.info("getEvent requestId={}, sessionId={}", request.getRequestId(),
                requestEnvelope.getSession().getSessionId());

        Session session = requestEnvelope.getSession();
        Intent intent = request.getIntent();
        String location = intent.getSlot("location").getValue();


        List<Event> events = GraphRepository.getEvents(12.5, 12.5, 2);

        session.setAttribute("list",events);
        session.setAttribute("count",0);

        String outputText = String.format("I found %d events around %s. Would you like to have a look on the first one?", events.size(),location);


        return SpeechletResponseBuilder.confirmDialog(intent).withOutputSpeech(outputText).build();
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
            outSpeech = String.format("<speak>This Event, <emphasis>%s</emphasis>  and takes place in %s at %s<speak>", event.get("description"),event.get("locality"), event.get("start"));
            session.setAttribute("count", count);
        }

        String repromtText = String.format("Select this event or take a look at the next one");

        return SpeechletResponseBuilder.withSSMLOutputSpeech(outSpeech).withRepromptOutputSpeech(repromtText ).withShouldEndSession(false).buildRespons();

    }

}
