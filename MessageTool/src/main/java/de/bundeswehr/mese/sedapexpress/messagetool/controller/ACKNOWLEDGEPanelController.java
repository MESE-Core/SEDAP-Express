package de.bundeswehr.mese.sedapexpress.messagetool.controller;

import de.bundeswehr.mese.sedapexpress.messages.SEDAPExpressMessage;
import de.bundeswehr.mese.sedapexpress.messages.SEDAPExpressMessage.Acknowledgement;
import de.bundeswehr.mese.sedapexpress.messages.SEDAPExpressMessage.Classification;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

public class ACKNOWLEDGEPanelController extends MessagePanelController {

    @FXML
    private Label nameLabel;

    @FXML
    private TextField nameTextField;

    @FXML
    private Label numberLabel;

    @FXML
    private TextField numberTextField;

    @FXML
    private Label recipientIdLabel;

    @FXML
    private TextField recipientTextField;

    @FXML
    void initialize() {
	assert this.nameLabel != null : "fx:id=\"nameLabel\" was not injected: check your FXML file 'ACKNOWLEDGEPanel.fxml'.";
	assert this.nameTextField != null : "fx:id=\"nameTextField\" was not injected: check your FXML file 'ACKNOWLEDGEPanel.fxml'.";
	assert this.numberLabel != null : "fx:id=\"numberLabel\" was not injected: check your FXML file 'ACKNOWLEDGEPanel.fxml'.";
	assert this.numberTextField != null : "fx:id=\"numberTextField\" was not injected: check your FXML file 'ACKNOWLEDGEPanel.fxml'.";
	assert this.recipientIdLabel != null : "fx:id=\"recipientIdLabel\" was not injected: check your FXML file 'ACKNOWLEDGEPanel.fxml'.";
	assert this.recipientTextField != null : "fx:id=\"recipientTextField\" was not injected: check your FXML file 'ACKNOWLEDGEPanel.fxml'.";

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
