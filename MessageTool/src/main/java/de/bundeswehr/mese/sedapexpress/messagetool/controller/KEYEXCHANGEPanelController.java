package de.bundeswehr.mese.sedapexpress.messagetool.controller;

import de.bundeswehr.mese.sedapexpress.messages.SEDAPExpressMessage;
import de.bundeswehr.mese.sedapexpress.messages.SEDAPExpressMessage.Acknowledgement;
import de.bundeswehr.mese.sedapexpress.messages.SEDAPExpressMessage.Classification;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;

public class KEYEXCHANGEPanelController extends MessagePanelController {

    @FXML
    private ComboBox<?> algorithmComboBox;

    @FXML
    private TextField contactIdTextField;

    @FXML
    private TextField encryptetSecretKeyTextField;

    @FXML
    private ComboBox<?> keyLenghtComboBox;

    @FXML
    private TextField naturalNumberTextField;

    @FXML
    private ComboBox<?> phaseComboBox;

    @FXML
    private TextField primeTextField;

    @FXML
    private TextField publicKeyTextField;

    @FXML
    void initialize() {
	assert this.algorithmComboBox != null : "fx:id=\"algorithmComboBox\" was not injected: check your FXML file 'KEYEXCHANGEPanel.fxml'.";
	assert this.contactIdTextField != null : "fx:id=\"contactIdTextField\" was not injected: check your FXML file 'KEYEXCHANGEPanel.fxml'.";
	assert this.encryptetSecretKeyTextField != null : "fx:id=\"encryptetSecretKeyTextField\" was not injected: check your FXML file 'KEYEXCHANGEPanel.fxml'.";
	assert this.keyLenghtComboBox != null : "fx:id=\"keyLenghtComboBox\" was not injected: check your FXML file 'KEYEXCHANGEPanel.fxml'.";
	assert this.naturalNumberTextField != null : "fx:id=\"naturalNumberTextField\" was not injected: check your FXML file 'KEYEXCHANGEPanel.fxml'.";
	assert this.phaseComboBox != null : "fx:id=\"phaseComboBox\" was not injected: check your FXML file 'KEYEXCHANGEPanel.fxml'.";
	assert this.primeTextField != null : "fx:id=\"primeTextField\" was not injected: check your FXML file 'KEYEXCHANGEPanel.fxml'.";
	assert this.publicKeyTextField != null : "fx:id=\"publicKeyTextField\" was not injected: check your FXML file 'KEYEXCHANGEPanel.fxml'.";

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
