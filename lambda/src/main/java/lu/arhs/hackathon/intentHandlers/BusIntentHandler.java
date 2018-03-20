package lu.arhs.hackathon.intentHandlers;

import com.amazon.speech.json.SpeechletRequestEnvelope;
import com.amazon.speech.speechlet.IntentRequest;
import com.amazon.speech.speechlet.SpeechletResponse;
import lu.arhs.hackathon.responses.SpeechletResponseBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class BusIntentHandler implements IntentHanderInterface{

    private static final Logger log = LoggerFactory.getLogger(BusIntentHandler.class);

    @Override
    public SpeechletResponse processIntent(SpeechletRequestEnvelope<IntentRequest> requestEnvelope) {
        return SpeechletResponseBuilder.withPlainTextOutputSpeech("I cant't do this yet").withShouldEndSession(true).buildRespons();
    }
}
