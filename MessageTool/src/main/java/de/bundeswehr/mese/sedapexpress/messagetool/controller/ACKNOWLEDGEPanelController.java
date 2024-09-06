package de.bundeswehr.mese.sedapexpress.messagetool.controller;

import de.bundeswehr.mese.sedapexpress.messages.SEDAPExpressMessage;
import de.bundeswehr.mese.sedapexpress.messages.SEDAPExpressMessage.Acknowledgement;
import de.bundeswehr.mese.sedapexpress.messages.SEDAPExpressMessage.Classification;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

public class ACKNOWLEDGEPanelController extends MessagePanelController {

    @FXML
    private Label contactIdLabel;

    @FXML
    private TextField contactIdTextField;

    @FXML
    private Label nameLabel;

    @FXML
    private TextField nameTextField;

    @FXML
    private TextField numberTextField;

    @FXML
    void initialize() {
	assert this.contactIdLabel != null : "fx:id=\"contactIdLabel\" was not injected: check your FXML file 'ACKNOWLEDGEPanel.fxml'.";
	assert this.contactIdTextField != null : "fx:id=\"contactIdTextField\" was not injected: check your FXML file 'ACKNOWLEDGEPanel.fxml'.";
	assert this.nameLabel != null : "fx:id=\"nameLabel\" was not injected: check your FXML file 'ACKNOWLEDGEPanel.fxml'.";
	assert this.nameTextField != null : "fx:id=\"nameTextField\" was not injected: check your FXML file 'ACKNOWLEDGEPanel.fxml'.";
	assert this.numberTextField != null : "fx:id=\"numberTextField\" was not injected: check your FXML file 'ACKNOWLEDGEPanel.fxml'.";

    }

    @Override
    public SEDAPExpressMessage createMessage(Short number, Long time, String sender, Classification classification, Acknowledgement acknowledgement, String mac) {

	return null;
    }

    @Override
    public boolean isValidFilled() {

	return true;
    }
}
