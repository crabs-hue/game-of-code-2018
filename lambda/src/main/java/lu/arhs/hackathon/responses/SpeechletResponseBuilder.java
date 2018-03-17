package lu.arhs.hackathon.responses;

import com.amazon.speech.speechlet.SpeechletResponse;
import com.amazon.speech.ui.PlainTextOutputSpeech;
import com.amazon.speech.ui.Reprompt;

public class SpeechletResponseBuilder {

    private PlainTextOutputSpeech outputSpeech;
    private Reprompt reprompt;
    private boolean shouldEndSession = true;

    private SpeechletResponseBuilder(String stringOutput){
        this.outputSpeech = new PlainTextOutputSpeech();
        this.outputSpeech.setText(stringOutput);
    }

    public static SpeechletResponseBuilder withOutputSpeech(String outputSpeech){

        return new SpeechletResponseBuilder(outputSpeech);
    }


    public SpeechletResponseBuilder withRepromptOutputSpeech(String repromptText){
        PlainTextOutputSpeech repromptOutputSpeech = new PlainTextOutputSpeech();
        repromptOutputSpeech.setText(repromptText);
        this.reprompt = new Reprompt();
        this.reprompt.setOutputSpeech(repromptOutputSpeech);
        return this;

    }

    public SpeechletResponseBuilder withShouldEndSession(boolean shouldEndSession){
        this.shouldEndSession = shouldEndSession;
        return  this;
    }

    public SpeechletResponse buildRespons(){
        SpeechletResponse response =  SpeechletResponse.newAskResponse(this.outputSpeech, this.reprompt);
        response.setShouldEndSession(this.shouldEndSession);
        return response;
    }


}
