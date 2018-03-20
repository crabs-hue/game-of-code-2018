package lu.arhs.hackathon.intentHandlers;

import com.amazon.speech.json.SpeechletRequestEnvelope;
import com.amazon.speech.slu.Intent;
import com.amazon.speech.speechlet.IntentRequest;
import com.amazon.speech.speechlet.Session;
import com.amazon.speech.speechlet.SpeechletResponse;
import lu.arhs.hackathon.responses.SpeechletResponseBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;

public class NextIntentHandler implements IntentHanderInterface{

    private static final Logger log = LoggerFactory.getLogger(NextIntentHandler.class);

    @Override
    public SpeechletResponse processIntent(SpeechletRequestEnvelope<IntentRequest> requestEnvelope) {
        IntentRequest request = requestEnvelope.getRequest();
        log.info("processEventIntent requestId={}, sessionId={}", request.getRequestId(),
                requestEnvelope.getSession().getSessionId());
        // session cen be used to save data
        Session session = requestEnvelope.getSession();


        Intent intent = request.getIntent();
        String intentName = (intent != null) ? intent.getName() : "s";
        log.info("processEventIntent intentname={}, sessionId={}", intentName,
                requestEnvelope.getSession().getSessionId());

        List<LinkedHashMap> list = (ArrayList<LinkedHashMap>) session.getAttribute("list");
        if ( null != list && !list.isEmpty()) {
            if ("EVENT".equals(list.get(0).get("type"))) {
                EventIntentHandler handler = new EventIntentHandler();
                return handler.iterateOverList(requestEnvelope);
            }
        }
        String errorSpeech = "This list is curently unsupported.  Please try something else.";
        return SpeechletResponseBuilder.withPlainTextOutputSpeech(errorSpeech).withRepromptOutputSpeech(errorSpeech).buildRespons();

    }
}
