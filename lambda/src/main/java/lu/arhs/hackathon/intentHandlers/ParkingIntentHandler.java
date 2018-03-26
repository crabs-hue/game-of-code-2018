package lu.arhs.hackathon.intentHandlers;

import com.amazon.speech.json.SpeechletRequestEnvelope;
import com.amazon.speech.slu.Intent;
import com.amazon.speech.speechlet.IntentRequest;
import com.amazon.speech.speechlet.Session;
import com.amazon.speech.speechlet.SpeechletResponse;
import lu.arhs.hackathon.domain.Parking;
import lu.arhs.hackathon.repository.GraphRepository;
import lu.arhs.hackathon.responses.SpeechletResponseBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

public class ParkingIntentHandler implements IntentHanderInterface{

    private static final Logger log = LoggerFactory.getLogger(ParkingIntentHandler.class);

    @Override
    public SpeechletResponse processIntent(SpeechletRequestEnvelope<IntentRequest> requestEnvelope) {
        IntentRequest request = requestEnvelope.getRequest();
        log.info("processIntent requestId={}, sessionId={}", request.getRequestId(),
                requestEnvelope.getSession().getSessionId());
        // session cen be used to save data
        Session session = requestEnvelope.getSession();


        Intent intent = request.getIntent();
        String intentName = (intent != null) ? intent.getName() : "s";
        log.info("processIntent intentname={}, sessionId={}", intentName,
                requestEnvelope.getSession().getSessionId());

        List<Parking> parkings = GraphRepository.getParkings(49.600690970137855, 6.113794412913669, 2);


        String outputText2 = String.format("There are %d parking places around.", parkings.size());

        String repromtText2 = String.format("Last chance to check on the events");
        return SpeechletResponseBuilder.withPlainTextOutputSpeech(outputText2).withRepromptOutputSpeech(repromtText2).withShouldEndSession(false).buildRespons();

    }
}
