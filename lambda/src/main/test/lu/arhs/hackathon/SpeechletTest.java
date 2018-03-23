package lu.arhs.hackathon;

import com.amazon.speech.json.SpeechletRequestEnvelope;
import com.amazon.speech.speechlet.IntentRequest;
import org.junit.Test;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;

import static org.junit.Assert.assertNotNull;

public class SpeechletTest {
    private static final String ENVELOP = "";



    @Test
    public void listIteratorTest()throws Exception{
        File initialFile = new File("/Users/danielb/game-of-code-2018/lambda/src/main/test/resources/no_location_event_test.json");
        InputStream targetStream = new FileInputStream(initialFile);
        SpeechletRequestEnvelope<IntentRequest> env = (SpeechletRequestEnvelope<IntentRequest>)SpeechletRequestEnvelope.fromJson(targetStream);
        assertNotNull(env);
    }

}



