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

import java.util.Iterator;
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


        List<Event> events = GraphRepository.getEvents(12.5, 12.5, 2);

        session.setAttribute("list",events);

        int count = 0;
        Iterator<Event> iter = events.listIterator(count);
        Event event = iter.next();
        String eventList = String.format("This first Event, %s and takes place in %s at %t", event.getDescription(),event.getLan(), event.getStart());
        if (iter.hasNext()){
            event = iter.next();
            count = events.indexOf(event);
            eventList += String.format("the next Event, %s and takes place in %s at %t", event.getDescription(),event.getLan(), event.getStart());
            session.setAttribute("count", count);
        }

        String outputText = String.format("I found %d events, %d", eventList);

        String repromtText = String.format("Last chance to check on the events in %s", location);


        return SpeechletResponseBuilder.withOutputSpeech(outputText).withRepromptOutputSpeech(repromtText ).withShouldEndSession(false).buildRespons();
    }


    public SpeechletResponse iterateOverList(SpeechletRequestEnvelope<IntentRequest> requestEnvelope){
        IntentRequest request = requestEnvelope.getRequest();
        log.info("iterateOverList requestId={}, sessionId={}", request.getRequestId(),
                requestEnvelope.getSession().getSessionId());

        Session session = requestEnvelope.getSession();
        Intent intent = request.getIntent();

        String outSpeech = "No more events found. What else can I do for you?";
        List<Event> list = (List<Event>)session.getAttribute("list");
        int count = (int) session.getAttribute("count");
        Iterator<Event> iter = list.listIterator(0);
        if (iter.hasNext()){
            Event event = iter.next();
            count = list.indexOf(event);
            outSpeech = String.format("This Event, %s and takes place in %s at %t", event.getDescription(),event.getLan(), event.getStart());
            session.setAttribute("count", count);
        }

        String repromtText = String.format("Select this event or take a look at the next one");

        return SpeechletResponseBuilder.withOutputSpeech(outSpeech).withRepromptOutputSpeech(repromtText ).withShouldEndSession(false).withDelegateDialog(intent).buildRespons();

    }

}
