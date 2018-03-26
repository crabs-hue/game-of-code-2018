package lu.arhs.hackathon;

import com.amazon.speech.json.SpeechletRequestEnvelope;
import com.amazon.speech.slu.Intent;
import com.amazon.speech.speechlet.Directive;
import com.amazon.speech.speechlet.IntentRequest;
import com.amazon.speech.speechlet.Session;
import com.amazon.speech.speechlet.SpeechletResponse;
import com.amazon.speech.speechlet.dialog.directives.DelegateDirective;
import lu.arhs.hackathon.intentHandlers.EventIntentHandler;
import lu.arhs.hackathon.responses.SpeechletResponseBuilder;
import org.junit.Test;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.util.ArrayList;

import static org.junit.Assert.*;

public class EventDialogTest {
    private static final String ENVELOP = "";



    @Test
    public void emptyEventRequest() throws Exception{
        File  initFile = new File("/Users/danielb/game-of-code-2018/lambda/src/main/test/resources/no_location_event_test.json");

        InputStream targetStream = new FileInputStream(initFile);
        SpeechletRequestEnvelope<IntentRequest> env = (SpeechletRequestEnvelope<IntentRequest>)SpeechletRequestEnvelope.fromJson(targetStream);
        IntentRequest request = env.getRequest();
        // session cen be used to save data
        Session session = env.getSession();
        Intent intent = request.getIntent();
        SpeechletResponse reference = SpeechletResponseBuilder.delegateDialog(intent);

        EventIntentHandler handler = new EventIntentHandler();

        SpeechletResponse resp = handler.processIntent(env);

        assertNull(resp.getOutputSpeech());

        boolean obj = resp.getDirectives().get(0) instanceof Directive;

        assertTrue(obj);

    }

    @Test
    public void withLocationEventRequestTest()throws Exception{
        File initialFile = new File("/Users/danielb/game-of-code-2018/lambda/src/main/test/resources/with_location_event_test.json");
        InputStream targetStream = new FileInputStream(initialFile);
        SpeechletRequestEnvelope<IntentRequest> env = (SpeechletRequestEnvelope<IntentRequest>)SpeechletRequestEnvelope.fromJson(targetStream);
        IntentRequest request = env.getRequest();
        // session cen be used to save data
        Session session = env.getSession();
        Intent intent = request.getIntent();
        String test = String.format("I found %d events around %s. Would you like to have a look on the first one?", 0,"merl");
        //SpeechletResponse reference = SpeechletResponseBuilder.confirmDialog(intent).withOutputSpeech(outputText).build();
        EventIntentHandler handler = new EventIntentHandler();

        SpeechletResponse resp = handler.processIntent(env);

        assertEquals(test, resp.getOutputSpeech().toString());
    }

}



