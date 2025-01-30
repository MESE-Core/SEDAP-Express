package de.bundeswehr.mese.sedapexpress.messagetool.controller;

import java.util.Arrays;

import de.bundeswehr.mese.sedapexpress.messages.RESEND;
import de.bundeswehr.mese.sedapexpress.messages.SEDAPExpressMessage.Acknowledgement;
import de.bundeswehr.mese.sedapexpress.messages.SEDAPExpressMessage.Classification;
import de.bundeswehr.mese.sedapexpress.messages.SEDAPExpressMessage.MessageType;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.Tooltip;

public class RESENDPanelController extends MessagePanelController {

	 @FXML
	    private Label contactIdLabel;

	    @FXML
	    private TextField contactIdTextField;

	    @FXML
	    private ComboBox<MessageType> nameComboBox;

	   
	    @FXML
	    private Label numberLabel;

	    @FXML
	    private TextField numberTextField;

	    @FXML
	    void initialize() {
	        assert contactIdLabel != null : "fx:id=\"contactIdLabel\" was not injected: check your FXML file 'RESENDPanel.fxml'.";
	        assert contactIdTextField != null : "fx:id=\"contactIdTextField\" was not injected: check your FXML file 'RESENDPanel.fxml'.";
	        assert nameComboBox != null : "fx:id=\"nameComboBox\" was not injected: check your FXML file 'RESENDPanel.fxml'.";
	        assert numberLabel != null : "fx:id=\"numberLabel\" was not injected: check your FXML file 'RESENDPanel.fxml'.";
	        assert numberTextField != null : "fx:id=\"numberTextField\" was not injected: check your FXML file 'RESENDPanel.fxml'.";


	Tooltip tooltipID = new Tooltip("You can use a freely selected textual identifiers, as explained in the table from chapter IV.1.1.");
	Tooltip tooltipName = new Tooltip("The name of the message which should resend");
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
	
	this.nameComboBox.setItems(FXCollections.observableList(Arrays.asList(MessageType.values())));
	this.nameComboBox.getSelectionModel().select(1);
	this.nameComboBox.setTooltip(tooltipName);
	
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
		}else{
			numberTextField.setText("");
			numberLabel.setVisible(true);	
			}});
	
	
	numberTextField.setTooltip(tooltipNumber);
	//numberTextField.setTextFormatter(MessagePanelController.createHexFormatter());	
	
    }
    //private Short numberOfTheMissingMessage;

    @Override
    public RESEND createMessage(Short number, Long time, String sender,
	    Classification classification, Acknowledgement acknowledgement, String mac) {

    	RESEND resend = new RESEND(number, time, sender,
    			classification, acknowledgement, mac,
    			this.contactIdTextField.getText(),
    			this.nameComboBox.getValue(),
    			Short.parseShort(this.numberTextField.getText(),16)
    			);
    			
	return resend;
    }

    @Override
    public boolean isValidFilled() {

    	return !contactIdLabel.isVisible() && !numberLabel.isVisible();
    }
}
