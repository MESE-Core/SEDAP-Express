package de.bundeswehr.mese.sedapexpress.messagetool.controller;

import de.bundeswehr.mese.sedapexpress.messages.SEDAPExpressMessage;
import de.bundeswehr.mese.sedapexpress.messages.SEDAPExpressMessage.Acknowledgement;
import de.bundeswehr.mese.sedapexpress.messages.SEDAPExpressMessage.Classification;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextArea;

public class GENERICPanelController extends MessagePanelController {

    @FXML
    private ComboBox<?> contentComboBox;

    @FXML
    private TextArea contentTeatArea;

    @FXML
    private ComboBox<?> encodingComboBox;

    @FXML
    void initialize() {
	assert this.contentComboBox != null : "fx:id=\"contentComboBox\" was not injected: check your FXML file 'GENERICPanel.fxml'.";
	assert this.contentTeatArea != null : "fx:id=\"contentTeatArea\" was not injected: check your FXML file 'GENERICPanel.fxml'.";
	assert this.encodingComboBox != null : "fx:id=\"encodingComboBox\" was not injected: check your FXML file 'GENERICPanel.fxml'.";

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
