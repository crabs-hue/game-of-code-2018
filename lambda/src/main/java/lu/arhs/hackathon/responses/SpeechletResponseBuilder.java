package lu.arhs.hackathon.responses;

import com.amazon.speech.slu.Intent;
import com.amazon.speech.speechlet.Directive;
import com.amazon.speech.speechlet.SpeechletResponse;
import com.amazon.speech.speechlet.dialog.directives.ConfirmIntentDirective;
import com.amazon.speech.speechlet.dialog.directives.DelegateDirective;
import com.amazon.speech.speechlet.dialog.directives.DialogIntent;
import com.amazon.speech.speechlet.dialog.directives.ElicitSlotDirective;
import com.amazon.speech.ui.PlainTextOutputSpeech;
import com.amazon.speech.ui.Reprompt;

import java.util.ArrayList;
import java.util.List;

public class SpeechletResponseBuilder {

    private PlainTextOutputSpeech outputSpeech;
    private Reprompt reprompt;
    private boolean shouldEndSession = true;
    private List<Directive> directives = new ArrayList<>();

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

    public SpeechletResponseBuilder withDelegateDialog(Intent requestIntent){
        DialogIntent updatedIntent = new DialogIntent(requestIntent);
        DelegateDirective delegateDirective = new DelegateDirective();
        delegateDirective.setUpdatedIntent(updatedIntent);


        this.directives.add(delegateDirective) ;
        return this;
    }

    public SpeechletResponseBuilder withConfirmDialog(Intent requestIntent){
        DialogIntent updatedIntent = new DialogIntent(requestIntent);

        ConfirmIntentDirective delegateDirective = new ConfirmIntentDirective();
        delegateDirective.setUpdatedIntent(updatedIntent);


        this.directives.add(delegateDirective) ;
        return this;
    }

    public SpeechletResponseBuilder withDialogElcit(Intent requestIntent){
        DialogIntent updatedIntent = new DialogIntent(requestIntent);

        ElicitSlotDirective delegateDirective = new ElicitSlotDirective();
        delegateDirective.setUpdatedIntent(updatedIntent);


        this.directives.add(delegateDirective) ;
        return this;
    }

    public SpeechletResponse buildRespons(){
        SpeechletResponse response =  SpeechletResponse.newAskResponse(this.outputSpeech, this.reprompt);
        response.setDirectives(this.directives);
        response.setShouldEndSession(this.shouldEndSession);
        return response;
    }


}
