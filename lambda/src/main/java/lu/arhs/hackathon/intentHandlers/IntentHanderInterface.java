package lu.arhs.hackathon.intentHandlers;

import com.amazon.speech.json.SpeechletRequestEnvelope;
import com.amazon.speech.speechlet.IntentRequest;
import com.amazon.speech.speechlet.SpeechletResponse;

public interface IntentHanderInterface {
    SpeechletResponse processIntent(SpeechletRequestEnvelope<IntentRequest> requestEnvelope);

}
