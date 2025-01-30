package de.bundeswehr.mese.sedapexpress.messagetool.controller;

import de.bundeswehr.mese.sedapexpress.messages.GENERIC;
import de.bundeswehr.mese.sedapexpress.messages.GENERIC.ContentType;
import de.bundeswehr.mese.sedapexpress.messages.SEDAPExpressMessage.Acknowledgement;
import de.bundeswehr.mese.sedapexpress.messages.SEDAPExpressMessage.Classification;
import de.bundeswehr.mese.sedapexpress.messages.SEDAPExpressMessage.DataEncoding;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextArea;
import javafx.scene.control.Tooltip;

public class GENERICPanelController extends MessagePanelController {

    @FXML
    private ComboBox<ContentType> contentComboBox;

    @FXML
    private ComboBox<DataEncoding> encodingComboBox;

    @FXML
    private TextArea contentTeatArea;

    @FXML
    void initialize() {
	assert this.contentComboBox != null : "fx:id=\"contentComboBox\" was not injected: check your FXML file 'GENERICPanel.fxml'.";
	assert this.contentTeatArea != null : "fx:id=\"contentTeatArea\" was not injected: check your FXML file 'GENERICPanel.fxml'.";
	assert this.encodingComboBox != null : "fx:id=\"encodingComboBox\" was not injected: check your FXML file 'GENERICPanel.fxml'.";

	Tooltip tooltipContent = new Tooltip("Choose trype of content");
	Tooltip tooltipEncoding = new Tooltip("Content can be BASE64 encoded or not encoded");
	Tooltip tooltipTextArea = new Tooltip("Any content in printable ASCII or BASE64 encoded");

	this.contentComboBox.setItems(FXCollections.observableArrayList(ContentType.values()));
	this.contentComboBox.getSelectionModel().select(0);
	this.contentComboBox.setTooltip(tooltipContent);
	this.encodingComboBox.setItems(FXCollections.observableArrayList(DataEncoding.values()));
	this.encodingComboBox.getSelectionModel().select(0);
	this.encodingComboBox.setTooltip(tooltipEncoding);
	this.contentTeatArea.setTooltip(tooltipTextArea);
    }
    // private String contentType;
    // private String encoding;
    // private String content;

    @Override
    public GENERIC createMessage(Short number, Long time, String sender, Classification classification, Acknowledgement acknowledgement, String mac) {
// TODO:  GENERIC muss angepasst werden, content und encoding sind String, sollten enum sein
	GENERIC generic = new GENERIC(number, time, sender, classification, acknowledgement, mac, this.contentComboBox.getValue(), this.encodingComboBox.getValue(), this.contentTeatArea.getText());

	return generic;
    }

    @Override
    public boolean isValidFilled() {

	return true;
    }
}
