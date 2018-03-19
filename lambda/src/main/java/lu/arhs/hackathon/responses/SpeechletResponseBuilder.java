package lu.arhs.hackathon.responses;

import com.amazon.speech.slu.Intent;
import com.amazon.speech.speechlet.Directive;
import com.amazon.speech.speechlet.SpeechletResponse;
import com.amazon.speech.speechlet.dialog.directives.ConfirmIntentDirective;
import com.amazon.speech.speechlet.dialog.directives.DelegateDirective;
import com.amazon.speech.speechlet.dialog.directives.DialogIntent;
import com.amazon.speech.speechlet.dialog.directives.ElicitSlotDirective;
import com.amazon.speech.ui.OutputSpeech;
import com.amazon.speech.ui.PlainTextOutputSpeech;
import com.amazon.speech.ui.Reprompt;
import com.amazon.speech.ui.SsmlOutputSpeech;

import java.util.ArrayList;
import java.util.List;

public class SpeechletResponseBuilder {

    private OutputSpeech outputSpeech;
    private Reprompt reprompt;
    private boolean shouldEndSession = true;

    private SpeechletResponseBuilder(OutputSpeech output){
        this.outputSpeech = output;
    }

    public static SpeechletResponseBuilder withPlainTextOutputSpeech(String outputSpeech){
        PlainTextOutputSpeech out = new PlainTextOutputSpeech();
        out.setText(outputSpeech);
        return new SpeechletResponseBuilder(out);
    }

    public static SpeechletResponseBuilder withSSMLOutputSpeech(String outputSpeech){
        SsmlOutputSpeech out = new SsmlOutputSpeech();
        out.setSsml(outputSpeech);
        return new SpeechletResponseBuilder(out);
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

    public static SpeechletResponse delegateDialog(Intent requestIntent){
        DialogIntent updatedIntent = new DialogIntent(requestIntent);
        DelegateDirective delegateDirective = new DelegateDirective();
        delegateDirective.setUpdatedIntent(updatedIntent);

        List<Directive> directives = new ArrayList<>();
        directives.add(delegateDirective) ;
        SpeechletResponse response =  new SpeechletResponse();
        response.setDirectives(directives);
        response.setNullableShouldEndSession(false);
        return response;
    }

    public static ConfirmationResponseBuilder confirmDialog(Intent requestIntent){

        return new ConfirmationResponseBuilder(requestIntent);
    }

    public static SpeechletResponse withDialogElcit(Intent requestIntent){
        DialogIntent updatedIntent = new DialogIntent(requestIntent);

        ElicitSlotDirective delegateDirective = new ElicitSlotDirective();
        delegateDirective.setUpdatedIntent(updatedIntent);

        List<Directive> directives = new ArrayList<>();
        directives.add(delegateDirective) ;
        SpeechletResponse response =  new SpeechletResponse();
        response.setDirectives(directives);
        response.setNullableShouldEndSession(false);
        return response;
    }

    public SpeechletResponse buildRespons(){
        SpeechletResponse response =  SpeechletResponse.newAskResponse(this.outputSpeech, this.reprompt);
        response.setNullableShouldEndSession(this.shouldEndSession);
        return response;
    }


    public static class ConfirmationResponseBuilder{
        private PlainTextOutputSpeech outputSpeech;
        private DialogIntent updatedIntent;

        public ConfirmationResponseBuilder(Intent requestIntent){
            this.updatedIntent = new DialogIntent(requestIntent);

        }

        public ConfirmationResponseBuilder withOutputSpeech(String outputSpeech){
            this.outputSpeech = new PlainTextOutputSpeech();
            this.outputSpeech.setText(outputSpeech);
            return this;
        }

        public SpeechletResponse build() {
            ConfirmIntentDirective delegateDirective = new ConfirmIntentDirective();
            delegateDirective.setUpdatedIntent(this.updatedIntent);
            List<Directive> directives = new ArrayList<>();
            directives.add(delegateDirective);
            SpeechletResponse response = new SpeechletResponse();
            response.setOutputSpeech(this.outputSpeech);
            response.setDirectives(directives);
            response.setNullableShouldEndSession(false);
            return response;
        }

    }


}
