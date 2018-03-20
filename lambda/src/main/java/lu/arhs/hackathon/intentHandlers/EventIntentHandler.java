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

import java.util.*;

public class EventIntentHandler implements IntentHanderInterface, ResultListHandler{
    private static final Logger log = LoggerFactory.getLogger(EventIntentHandler.class);


    @Override
    public SpeechletResponse processIntent(SpeechletRequestEnvelope<IntentRequest> requestEnvelope){
        IntentRequest request = requestEnvelope.getRequest();
        log.info("processEventIntent requestId={}, sessionId={}", request.getRequestId(),
                requestEnvelope.getSession().getSessionId());
        // session cen be used to save data
        Session session = requestEnvelope.getSession();


        Intent intent = request.getIntent();
        String intentName = (intent != null) ? intent.getName() : "s";
        log.info("processEventIntent intentname={}, sessionId={}", intentName,
                requestEnvelope.getSession().getSessionId());

        switch (intent.getConfirmationStatus()){
            case NONE:

                if (null == intent.getSlot("location").getValue()){
                    return this.askForLocality(requestEnvelope);
                }
                return this.getEvents(requestEnvelope);
            case CONFIRMED:
                List<HashMap> list = (List<HashMap>) session.getAttribute("list");
                if ( null != list && !list.isEmpty()){
                    //if ("EVENT".equals(list.get(0).get("type"))){
                    return this.iterateOverList(requestEnvelope);
                    //  }
                }
                return SpeechletResponseBuilder.withPlainTextOutputSpeech("This should not be poosible").withShouldEndSession(true).buildRespons();
            case DENIED:
                return SpeechletResponseBuilder.withPlainTextOutputSpeech("I cant't do this yet").withShouldEndSession(true).buildRespons();
            default:
                return SpeechletResponseBuilder.withPlainTextOutputSpeech("I did not understand").withShouldEndSession(true).buildRespons();
        }

    }



    private SpeechletResponse askForLocality(SpeechletRequestEnvelope<IntentRequest> requestEnvelope){
        IntentRequest request = requestEnvelope.getRequest();
        log.info("askForLocality requestId={}, sessionId={}", request.getRequestId(),
                requestEnvelope.getSession().getSessionId());

        Session session = requestEnvelope.getSession();
        Intent intent = request.getIntent();

        return SpeechletResponseBuilder.delegateDialog(intent);
    }


    private SpeechletResponse getEvents(SpeechletRequestEnvelope<IntentRequest> requestEnvelope){
        IntentRequest request = requestEnvelope.getRequest();
        log.info("getEvent requestId={}, sessionId={}", request.getRequestId(),
                requestEnvelope.getSession().getSessionId());

        Session session = requestEnvelope.getSession();
        Intent intent = request.getIntent();
        String location = intent.getSlot("location").getValue();


        List<Event> events = GraphRepository.getEvents(49.600690970137855, 6.113794412913669, 2);

        session.setAttribute("list",events);
        session.setAttribute("count",0);

        String outputText = String.format("I found %d events around %s. Would you like to have a look on the first one?", events.size(),location);


        return SpeechletResponseBuilder.confirmDialog(intent).withOutputSpeech(outputText).build();
    }


    @Override
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
            outSpeech = String.format("<speak>This Event, <emphasis>%s</emphasis>  and takes place in %s at %s</speak>", event.get("description"),event.get("locality"), event.get("start"));
            session.setAttribute("count", count);
        }

        String repromtText = String.format("Select this event or take a look at the next one");

        return SpeechletResponseBuilder.withSSMLOutputSpeech(outSpeech).withRepromptOutputSpeech(repromtText ).withShouldEndSession(false).buildRespons();

    }

}
