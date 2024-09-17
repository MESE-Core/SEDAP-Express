package de.bundeswehr.mese.sedapexpress.messagetool.controller;

import java.util.Arrays;

import de.bundeswehr.mese.sedapexpress.messages.SEDAPExpressMessage.Acknowledgement;
import de.bundeswehr.mese.sedapexpress.messages.SEDAPExpressMessage.Classification;
import de.bundeswehr.mese.sedapexpress.messages.STATUS;
import de.bundeswehr.mese.sedapexpress.messages.STATUS.CommandState;
import de.bundeswehr.mese.sedapexpress.messages.STATUS.OperationalState;
import de.bundeswehr.mese.sedapexpress.messages.STATUS.TechnicalState;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

public class STATUSPanelController extends MessagePanelController {

    @FXML
    private TextField ammunitionNameTextField;

    @FXML
    private TextField ammunitionLevelTextField;

    @FXML
    private Label ammunitionLevelLabel;

    @FXML
    private Label batterieLevelLabel;

    @FXML
    private TextField batterieNameTextField;

    @FXML
    private TextField batterieLevelTextField;

    @FXML
    private TextField ipHostTextField;

    @FXML
    private Label cmdIdLabel;

    @FXML
    private TextField cmdIdTextField;

    @FXML
    private ComboBox<CommandState> cmdStateComboBox;

    @FXML
    private Label fuelLevelLabel;

    @FXML
    private TextField fuelNameTextField;

    @FXML
    private TextField fuelLevelTextField;

    @FXML
    private Label ipHostLabel;

    @FXML
    private Label mediaLabel;

    @FXML
    private TextField mediaTextField;

    @FXML
    private ComboBox<TechnicalState> tecStateComboBox;

    @FXML
    private ComboBox<OperationalState> opsStateComboBox;

    @FXML
    private Label tecStatusLabel;

    @FXML
    private TextArea textTextArea;

    @FXML
    void initialize() {
	assert this.ammunitionLevelTextField != null : "fx:id=\"ammunitionLevelTextField\" was not injected: check your FXML file 'STATUSPanel.fxml'.";
	assert this.ipHostTextField != null : "fx:id=\"ipHostTextField\" was not injected: check your FXML file 'STATUSPanel.fxml'.";
	assert this.ammunitionLevelLabel != null : "fx:id=\"ammunitionLevelLabel\" was not injected: check your FXML file 'STATUSPanel.fxml'.";
	assert this.batterieLevelLabel != null : "fx:id=\"batterieLevelLabel\" was not injected: check your FXML file 'STATUSPanel.fxml'.";
	assert this.batterieLevelTextField != null : "fx:id=\"batterieLevelTextField\" was not injected: check your FXML file 'STATUSPanel.fxml'.";
	assert this.cmdIdLabel != null : "fx:id=\"cmdIdLabel\" was not injected: check your FXML file 'STATUSPanel.fxml'.";
	assert this.cmdIdTextField != null : "fx:id=\"cmdIdTextBox\" was not injected: check your FXML file 'STATUSPanel.fxml'.";
	assert this.cmdStateComboBox != null : "fx:id=\"cmdStateComboBox\" was not injected: check your FXML file 'STATUSPanel.fxml'.";
	assert this.fuelLevelLabel != null : "fx:id=\"fuelLevelLabel\" was not injected: check your FXML file 'STATUSPanel.fxml'.";
	assert this.fuelLevelTextField != null : "fx:id=\"fuelLevelTextField\" was not injected: check your FXML file 'STATUSPanel.fxml'.";
	assert this.ipHostLabel != null : "fx:id=\"ipHostLabel\" was not injected: check your FXML file 'STATUSPanel.fxml'.";
	assert this.mediaLabel != null : "fx:id=\"mediaLabel\" was not injected: check your FXML file 'STATUSPanel.fxml'.";
	assert this.mediaTextField != null : "fx:id=\"mediaTextField\" was not injected: check your FXML file 'STATUSPanel.fxml'.";
	assert this.opsStateComboBox != null : "fx:id=\"optStateComboBox\" was not injected: check your FXML file 'STATUSPanel.fxml'.";
	assert this.tecStateComboBox != null : "fx:id=\"tecStateComboBox\" was not injected: check your FXML file 'STATUSPanel.fxml'.";
	assert this.tecStatusLabel != null : "fx:id=\"tecStatusLabel\" was not injected: check your FXML file 'STATUSPanel.fxml'.";
	assert this.textTextArea != null : "fx:id=\"textTextArea\" was not injected: check your FXML file 'STATUSPanel.fxml'.";

	this.tecStateComboBox.setItems(FXCollections.observableList(Arrays.asList(TechnicalState.values())));
	this.opsStateComboBox.setItems(FXCollections.observableList(Arrays.asList(OperationalState.values())));

	this.cmdStateComboBox.setItems(FXCollections.observableList(Arrays.asList(CommandState.values())));

    }

    @Override
    public STATUS createMessage(Short number, Long time, String sender,
	    Classification classification, Acknowledgement acknowledgement, String mac) {

	return new STATUS(number, time, sender,
		classification, acknowledgement, mac,
		this.tecStateComboBox.getValue(),
		this.opsStateComboBox.getValue(),
		(this.ammunitionNameTextField.getText().isBlank()) ? null : this.ammunitionNameTextField.getText(),
		(this.ammunitionLevelTextField.getText().isBlank()) ? null : Double.valueOf(this.ammunitionLevelTextField.getText()),
		(this.fuelNameTextField.getText().isBlank()) ? null : this.fuelNameTextField.getText(),
		(this.fuelLevelTextField.getText().isBlank()) ? null : Double.valueOf(this.fuelLevelTextField.getText()),
		(this.batterieNameTextField.getText().isBlank()) ? null : this.batterieNameTextField.getText(),
		(this.batterieLevelTextField.getText().isBlank()) ? null : Double.valueOf(this.batterieLevelTextField.getText()),
		(this.cmdIdTextField.getText().isBlank()) ? null : Integer.valueOf(this.cmdIdTextField.getText()),
		this.cmdStateComboBox.getValue(),
		this.ipHostTextField.getText(),
		this.mediaTextField.getText(),
		this.textTextArea.getText());

    }

    @Override
    public boolean isValidFilled() {

	return true;
    }

}
