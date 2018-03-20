package lu.arhs.hackathon.intentHandlers;

import com.amazon.speech.json.SpeechletRequestEnvelope;
import com.amazon.speech.speechlet.IntentRequest;
import com.amazon.speech.speechlet.SpeechletResponse;

public interface ResultListHandler {

    SpeechletResponse iterateOverList(SpeechletRequestEnvelope<IntentRequest> requestEnvelope);
}
