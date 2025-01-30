package de.bundeswehr.mese.sedapexpress.messagetool.controller;

import java.util.ArrayList;
import java.util.Arrays;


import de.bundeswehr.mese.sedapexpress.messages.COMMAND;
import de.bundeswehr.mese.sedapexpress.messages.COMMAND.CommandFlag;
import de.bundeswehr.mese.sedapexpress.messages.COMMAND.CommandType;
import de.bundeswehr.mese.sedapexpress.messages.SEDAPExpressMessage.Acknowledgement;
import de.bundeswehr.mese.sedapexpress.messages.SEDAPExpressMessage.Classification;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.Tooltip;
import javafx.util.Callback;

public class COMMANDPanelController extends MessagePanelController {

	private Short commandID;

    @FXML
    private ComboBox<COMMAND.CommandFlag> cmdFlagComboBox;

    @FXML
    private TextArea addParametersTextArea;

    @FXML
    private Label cmdFlagLabel;

    @FXML
    private TextField cmdIDTextField;

    @FXML
    private ComboBox<COMMAND.CommandType> cmdTypeComboBox;

    @FXML
    private Label recipientLabel;

    @FXML
    private TextField recipientTextField;
  
    @FXML
    private Label cmdTypeLabel;

    @FXML
    void initialize() {
        assert cmdFlagComboBox != null : "fx:id=\"cmdFlagComboBox\" was not injected: check your FXML file 'COMMANDPanel.fxml'.";
        assert addParametersTextArea != null : "fx:id=\"addParametersTextArea\" was not injected: check your FXML file 'COMMANDPanel.fxml'.";
        assert cmdFlagLabel != null : "fx:id=\"cmdFlagLabel\" was not injected: check your FXML file 'COMMANDPanel.fxml'.";
        assert cmdIDTextField != null : "fx:id=\"cmdIDTextField\" was not injected: check your FXML file 'COMMANDPanel.fxml'.";
        assert cmdTypeComboBox != null : "fx:id=\"cmdTypeComboBox\" was not injected: check your FXML file 'COMMANDPanel.fxml'.";
        assert cmdTypeLabel != null : "fx:id=\"cmdTypeLabel\" was not injected: check your FXML file 'COMMANDPanel.fxml'.";
        assert recipientLabel != null : "fx:id=\"recipientLabel\" was not injected: check your FXML file 'COMMANDPanel.fxml'.";
        assert recipientTextField != null : "fx:id=\"recipientTextField\" was not injected: check your FXML file 'COMMANDPanel.fxml'.";


    // TOOLTIPS
    Tooltip tooltipID = new Tooltip("Hexadecimal identifier of the command (0000 means all last commands)");
    Tooltip tooltipRecipient = new Tooltip("You can use a freely selected textual identifiers, as explained in the table from chapter IV.1.1.");
    Tooltip tooltipAdditional = new Tooltip("additional cmdType-dependent parameters, see chapter 2.7 SEDAP Express ICD");
    
    this.addParametersTextArea.setTooltip(tooltipAdditional);
    this.cmdIDTextField.setTooltip(tooltipID);
    this.recipientTextField.setTooltip(tooltipRecipient);
    
    // CommandTyp ComboBox
	this.cmdTypeComboBox.setItems(FXCollections.observableArrayList(CommandType.values()));
	this.cmdTypeComboBox.setCellFactory(new Callback<ListView<CommandType>,ListCell<CommandType>>(){
		@Override
		public ListCell<CommandType> call (ListView<CommandType> l){
			return new ListCell<CommandType>() {
				@Override
				protected void updateItem(CommandType item, boolean empty) {
					super.updateItem(item,  empty);
					if(item == null || empty) {
						setGraphic(null);
					}else {
						setText(item.name());
					}
				}
			};
		}
	});
	this.cmdTypeComboBox.setButtonCell(new ListCell<>() {
	    protected void updateItem(CommandType item, boolean empty) {
	    	super.updateItem(item, empty);
	    	if (item == null || empty) {
	    		setGraphic(null);
	    	} else {
	    		setText(item.name());
	    	}
	    }
	});
	this.cmdTypeComboBox.getSelectionModel().select(1);
	this.cmdTypeLabel.setVisible(false);
	
	 // CommandFlag ComboBox
	this.cmdFlagComboBox.setItems(FXCollections.observableArrayList(CommandFlag.values()));
	this.cmdFlagComboBox.setCellFactory(new Callback<ListView<CommandFlag>,ListCell<CommandFlag>>(){
		@Override
		public ListCell<CommandFlag> call (ListView<CommandFlag> l){
			return new ListCell<CommandFlag>() {
				@Override
				protected void updateItem(CommandFlag item, boolean empty) {
					super.updateItem(item,  empty);
					if(item == null || empty) {
						setGraphic(null);
					}else {
						setText(item.name());
					}
				}
			};
		}
	});
	this.cmdFlagComboBox.setButtonCell(new ListCell<>() {
	    protected void updateItem(CommandFlag item, boolean empty) {
	    	super.updateItem(item, empty);
	    	if (item == null || empty) {
	    		setGraphic(null);
	    	} else {
	    		setText(item.name());
	    	}
	    }
	});
	this.cmdFlagComboBox.getSelectionModel().select(1);
	this.cmdFlagLabel.setVisible(false);
	
	recipientTextField.textProperty().addListener((observable, oldValue,newValue) -> {
		if(!newValue.equals("")) {
			recipientLabel.setVisible(false);
		}else {
			recipientLabel.setVisible(true);
		}
		});
	
	cmdIDTextField.setTextFormatter(MessagePanelController.createHexFormatter());
	cmdIDTextField.textProperty().addListener((observable, oldValue,newValue) -> {
		 if (!newValue.isEmpty()) {
             try {
            	 commandID = Short.parseShort(newValue, 16);
             } catch (NumberFormatException e) {
                 System.out.println("Ungültige Eingabe");
             }
         } else {
        	 commandID = null;
         }
     });
		
    }
 
    @Override //SEDAPExpressMessage
    public COMMAND createMessage(Short number, Long time, String sender, Classification classification, Acknowledgement acknowledgement, String mac) {
   		
    	  ArrayList<String> lines = new ArrayList<>(
    		    Arrays.asList(addParametersTextArea.getText().replace(System.lineSeparator(),";").replace("\n",";").split(";")));
    	// letztes ";" abtrennen
    
    	COMMAND command = new COMMAND(number,
    			time,
    			sender,
    			classification,
    			acknowledgement,
    			mac,
    			this.recipientTextField.getText(),
    			this.commandID != null ? commandID : 0,
    			this.cmdFlagComboBox.getValue(),
    			this.cmdTypeComboBox.getValue(),
    			lines);
    	return command;
    }

    @Override
    public boolean isValidFilled() {

    	return (!this.cmdFlagLabel.isVisible() && !this.cmdTypeLabel.isVisible() && !this.recipientLabel.isVisible());
    }
}
