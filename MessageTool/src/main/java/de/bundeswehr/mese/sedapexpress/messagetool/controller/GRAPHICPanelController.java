package de.bundeswehr.mese.sedapexpress.messagetool.controller;

import de.bundeswehr.mese.sedapexpress.messages.SEDAPExpressMessage;
import de.bundeswehr.mese.sedapexpress.messages.SEDAPExpressMessage.Acknowledgement;
import de.bundeswehr.mese.sedapexpress.messages.SEDAPExpressMessage.Classification;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

public class GRAPHICPanelController extends MessagePanelController {

    @FXML
    private TextArea annotationTextArea;

    @FXML
    private Label contactIdLabel;

    @FXML
    private TextField encodingTextField;

    @FXML
    private TextField fillColorTextField;

    @FXML
    private ComboBox<?> graficTypeComboBox;

    @FXML
    private TextField lineColorTextField;

    @FXML
    private TextField lineWidthTextField;

    @FXML
    void initialize() {
	assert this.annotationTextArea != null : "fx:id=\"annotationTextArea\" was not injected: check your FXML file 'GRAPHICPanel.fxml'.";
	assert this.contactIdLabel != null : "fx:id=\"contactIdLabel\" was not injected: check your FXML file 'GRAPHICPanel.fxml'.";
	assert this.encodingTextField != null : "fx:id=\"encodingTextField\" was not injected: check your FXML file 'GRAPHICPanel.fxml'.";
	assert this.fillColorTextField != null : "fx:id=\"fillColorTextField\" was not injected: check your FXML file 'GRAPHICPanel.fxml'.";
	assert this.graficTypeComboBox != null : "fx:id=\"graficTypeComboBox\" was not injected: check your FXML file 'GRAPHICPanel.fxml'.";
	assert this.lineColorTextField != null : "fx:id=\"lineColorTextField\" was not injected: check your FXML file 'GRAPHICPanel.fxml'.";
	assert this.lineWidthTextField != null : "fx:id=\"lineWidthTextField\" was not injected: check your FXML file 'GRAPHICPanel.fxml'.";

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
