package de.bundeswehr.mese.sedapexpress.messagetool.controller;

import java.util.Arrays;


import de.bundeswehr.mese.sedapexpress.messages.ACKNOWLEDGE;
import de.bundeswehr.mese.sedapexpress.messages.SEDAPExpressMessage;
import de.bundeswehr.mese.sedapexpress.messages.SEDAPExpressMessage.Acknowledgement;
import de.bundeswehr.mese.sedapexpress.messages.SEDAPExpressMessage.Classification;
import de.bundeswehr.mese.sedapexpress.messages.SEDAPExpressMessage.MessageType;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.Tooltip;


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
    private Label numberLabel;
    
    @FXML
    private TextField numberTextField;
    
    @FXML
    private ComboBox<MessageType> nameComboBox;
  
    @FXML
    void initialize() {
	assert this.contactIdLabel != null : "fx:id=\"contactIdLabel\" was not injected: check your FXML file 'ACKNOWLEDGEPanel.fxml'.";
	assert this.contactIdTextField != null : "fx:id=\"contactIdTextField\" was not injected: check your FXML file 'ACKNOWLEDGEPanel.fxml'.";
	assert this.nameLabel != null : "fx:id=\"nameLabel\" was not injected: check your FXML file 'ACKNOWLEDGEPanel.fxml'.";
	assert this.nameTextField != null : "fx:id=\"nameTextField\" was not injected: check your FXML file 'ACKNOWLEDGEPanel.fxml'.";
	assert numberLabel != null : "fx:id=\"numberLabel\" was not injected: check your FXML file 'ACKNOWLEDGEPanel.fxml'.";
	assert this.numberTextField != null : "fx:id=\"numberTextField\" was not injected: check your FXML file 'ACKNOWLEDGEPanel.fxml'.";
	assert nameComboBox != null : "fx:id=\"nameComboBox\" was not injected: check your FXML file 'ACKNOWLEDGEPanel.fxml'.";
	 
	Tooltip tooltipID = new Tooltip("In most cases, you should use a hexadecimal string representation of a 16-bit unsigned integer, \r\n"
			+ "but you can also use freely selected textual identifiers");
	Tooltip tooltipName = new Tooltip("The name of the message which should be acknowledged");
	Tooltip tooltipNumber = new Tooltip("hexadecimal string \r\n"
			+  "representation of an 8-bit \r\n"
			+  "00 - FF");
	
	contactIdTextField.textProperty().addListener((observable, oldValue,newValue) -> {
		if(!newValue.equals("")) {
			contactIdLabel.setVisible(false);
		}else {
			contactIdLabel.setVisible(true);
		}
		});
	
	contactIdTextField.setTooltip(tooltipID);
	nameLabel.setVisible(false);
	nameTextField.setTooltip(tooltipName);
	
	numberTextField.textProperty().addListener((observable, oldValue,newValue) -> {
		if(!newValue.equals("")) {
			if (!newValue.equals(newValue.toUpperCase())) {
				numberTextField.setText(newValue.toUpperCase());
		    }
			if(isValid8BitHex(newValue)== true) {
				numberLabel.setVisible(false);
			}else {
				numberLabel.setVisible(true);	
			}
			}});
	
	
	numberTextField.setTooltip(tooltipNumber);
	numberTextField.setTextFormatter(MessagePanelController.createHexFormatter());
	
	this.nameComboBox.setItems(FXCollections.observableList(Arrays.asList(MessageType.values())));
	this.nameComboBox.getSelectionModel().select(1);
	
    }
        
    MessageType nameOfTheMessage;
    boolean isValid = false;
    
    @Override
    public SEDAPExpressMessage createMessage(Short number, Long time, String sender, Classification classification, Acknowledgement acknowledgement, String mac) {

    	ACKNOWLEDGE acknonwledge = new ACKNOWLEDGE( number,  time,  sender,  classification,  acknowledgement,  mac,
    		this.contactIdTextField.getText(),
    		this.nameComboBox.getValue(),
    		this.numberTextField.getText().isEmpty() ? 0 : Short.valueOf(this.numberTextField.getText()));
    	return acknonwledge;
    }

    @Override
    public boolean isValidFilled() {

    	return (!contactIdLabel.isVisible() && !nameLabel.isVisible() && !numberLabel.isVisible());
    }
}
