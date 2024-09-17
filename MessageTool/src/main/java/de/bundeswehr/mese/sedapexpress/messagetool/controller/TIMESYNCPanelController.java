package de.bundeswehr.mese.sedapexpress.messagetool.controller;

import de.bundeswehr.mese.sedapexpress.messages.SEDAPExpressMessage;
import de.bundeswehr.mese.sedapexpress.messages.SEDAPExpressMessage.Acknowledgement;
import de.bundeswehr.mese.sedapexpress.messages.SEDAPExpressMessage.Classification;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

public class TIMESYNCPanelController extends MessagePanelController {

    @FXML
    private Label recipientLabel;

    @FXML
    private TextField recipientTextField;

    @FXML
    private TextField timestampTextField;

    @FXML
    private Label timesyncLabel;

    @FXML
    void initialize() {
	assert this.recipientLabel != null : "fx:id=\"recipientLabel\" was not injected: check your FXML file 'TIMESYNCPanel.fxml'.";
	assert this.recipientTextField != null : "fx:id=\"recipientTextField\" was not injected: check your FXML file 'TIMESYNCPanel.fxml'.";
	assert this.timestampTextField != null : "fx:id=\"timestampTextField\" was not injected: check your FXML file 'TIMESYNCPanel.fxml'.";
	assert this.timesyncLabel != null : "fx:id=\"timesyncLabel\" was not injected: check your FXML file 'TIMESYNCPanel.fxml'.";

    }

    @Override
    public SEDAPExpressMessage createMessage(Short number, Long time, String sender,
	    Classification classification, Acknowledgement acknowledgement, String mac) {

	return null;
    }

    @Override
    public boolean isValidFilled() {

	return true;
    }
}
