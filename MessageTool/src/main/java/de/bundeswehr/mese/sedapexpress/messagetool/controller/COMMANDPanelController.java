package de.bundeswehr.mese.sedapexpress.messagetool.controller;

import de.bundeswehr.mese.sedapexpress.messages.SEDAPExpressMessage;
import de.bundeswehr.mese.sedapexpress.messages.SEDAPExpressMessage.Acknowledgement;
import de.bundeswehr.mese.sedapexpress.messages.SEDAPExpressMessage.Classification;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

public class COMMANDPanelController extends MessagePanelController {

    @FXML
    private TextField CmdIDTextField;

    @FXML
    private TextArea addParametersTextArea;

    @FXML
    private ComboBox<?> cmdTypeComboBox;

    @FXML
    private Label contactIdLabel;

    @FXML
    private TextField contactIdTextField;

    @FXML
    private Label latitudeLabel;

    @FXML
    void initialize() {
	assert this.CmdIDTextField != null : "fx:id=\"CmdIDTextField\" was not injected: check your FXML file 'COMMANDPanel.fxml'.";
	assert this.addParametersTextArea != null : "fx:id=\"addParametersTextArea\" was not injected: check your FXML file 'COMMANDPanel.fxml'.";
	assert this.cmdTypeComboBox != null : "fx:id=\"cmdTypeComboBox\" was not injected: check your FXML file 'COMMANDPanel.fxml'.";
	assert this.contactIdLabel != null : "fx:id=\"contactIdLabel\" was not injected: check your FXML file 'COMMANDPanel.fxml'.";
	assert this.contactIdTextField != null : "fx:id=\"contactIdTextField\" was not injected: check your FXML file 'COMMANDPanel.fxml'.";
	assert this.latitudeLabel != null : "fx:id=\"latitudeLabel\" was not injected: check your FXML file 'COMMANDPanel.fxml'.";

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
