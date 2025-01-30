package de.bundeswehr.mese.sedapexpress.messagetool.controller;

import de.bundeswehr.mese.sedapexpress.messages.HEARTBEAT;
import de.bundeswehr.mese.sedapexpress.messages.SEDAPExpressMessage;
import de.bundeswehr.mese.sedapexpress.messages.SEDAPExpressMessage.Acknowledgement;
import de.bundeswehr.mese.sedapexpress.messages.SEDAPExpressMessage.Classification;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.scene.control.Tooltip;

public class HEARTBEATPanelController extends MessagePanelController {

    @FXML
    private TextField contactIdTextField;

    @FXML
    void initialize() {
	assert this.contactIdTextField != null : "fx:id=\"contactIdTextField\" was not injected: check your FXML file 'HEARTBEATPanel.fxml'.";
	
	Tooltip tooltipContactID = new Tooltip("You can use a freely selected textual identifiers, as explained in the table from chapter IV.1.1. SEDAPExpress ICD");
	contactIdTextField.setTooltip(tooltipContactID);
	
    }

    @Override
    public HEARTBEAT createMessage(Short number, Long time, String sender,
	    Classification classification, Acknowledgement acknowledgement, String mac) {

    	HEARTBEAT hearbeat = new HEARTBEAT( number,  time,  sender,  classification,  acknowledgement,  mac, 
    			this.contactIdTextField.getText());
   
    	return hearbeat;
    }

    @Override
    public boolean isValidFilled() {

    	return true;
    }
}
