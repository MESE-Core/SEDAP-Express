package de.bundeswehr.mese.sedapexpress.messagetool.controller;

import java.net.URL;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.ResourceBundle;

import de.bundeswehr.mese.sedapexpress.messages.EMISSION;
import de.bundeswehr.mese.sedapexpress.messages.SEDAPExpressMessage.Acknowledgement;
import de.bundeswehr.mese.sedapexpress.messages.SEDAPExpressMessage.Classification;
import de.bundeswehr.mese.sedapexpress.messages.SEDAPExpressMessage.DeleteFlag;
import de.bundeswehr.mese.sedapexpress.messages.SIDCCodes;
import de.bundeswehr.mese.sedapexpress.messagetool.MessageTool;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.TextFormatter;
import javafx.scene.control.Tooltip;

public class EMISSIONPanelController extends MessagePanelController {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;



    @FXML
    private Label altitudeLabel;

    @FXML
    private TextField altitudeTextField;

    @FXML
    private Label bandwidthLabel;

    @FXML
    private TextField bandwithTextField;

    @FXML
    private Label bearingLabel;

    @FXML
    private TextField bearingTextField;

    @FXML
    private TextArea commentTextArea;

    @FXML
    private Label contactIdLabel;

    @FXML
    private ComboBox<DeleteFlag> deleteFlagComboBox;

    @FXML
    private TextField emissionIdTextField;

    @FXML
    private Label emitterAltLabel;

    @FXML
    private TextField emitterAltTextField;

    @FXML
    private Label emitterLatLabel;

    @FXML
    private TextField emitterLatTextField;

    @FXML
    private Label emitterLongLabel;

    @FXML
    private TextField emitterLongTextField;

    @FXML
    private Label freqAgilityLabel;

    @FXML
    private ComboBox<String> frequencAgilityComboBox;

    @FXML
    private Label frequenciesLabel;

    @FXML
    private TextField frequenciesTextField;

    @FXML
    private ComboBox<String> functionComboBox;

    @FXML
    private Label functionLabel;

    @FXML
    private Label latitudeLabel;

    @FXML
    private TextField latitudeTextField;

    @FXML
    private Label longitudeLabel;

    @FXML
    private TextField longitudeTextField;

    @FXML
    private Label powerLabel;

    @FXML
    private TextField powerTextField;

    @FXML
    private ComboBox<String> prfAgilityComboBox;

    @FXML
    private Label prfAgilityLabel;

    @FXML
    private ComboBox<String> sidcComboBox;

    @FXML
    private ComboBox<String> sidcDimComboBox;

    @FXML
    private ComboBox<String> sidcIDComboBox;

    @FXML
    private TextField spotNumberTextField;

    @FXML
    private Label spotnumberLabel;

    @FXML
    void initialize() {
        assert altitudeLabel != null : "fx:id=\"altitudeLabel\" was not injected: check your FXML file 'EMISSIONPanel.fxml'.";
        assert altitudeTextField != null : "fx:id=\"altitudeTextField\" was not injected: check your FXML file 'EMISSIONPanel.fxml'.";
        assert bandwidthLabel != null : "fx:id=\"bandwidthLabel\" was not injected: check your FXML file 'EMISSIONPanel.fxml'.";
        assert bandwithTextField != null : "fx:id=\"bandwithTextField\" was not injected: check your FXML file 'EMISSIONPanel.fxml'.";
        assert bearingLabel != null : "fx:id=\"bearingLabel\" was not injected: check your FXML file 'EMISSIONPanel.fxml'.";
        assert bearingTextField != null : "fx:id=\"bearingTextField\" was not injected: check your FXML file 'EMISSIONPanel.fxml'.";
        assert commentTextArea != null : "fx:id=\"commentTextArea\" was not injected: check your FXML file 'EMISSIONPanel.fxml'.";
        assert contactIdLabel != null : "fx:id=\"contactIdLabel\" was not injected: check your FXML file 'EMISSIONPanel.fxml'.";
        assert deleteFlagComboBox != null : "fx:id=\"deleteFlagComboBox\" was not injected: check your FXML file 'EMISSIONPanel.fxml'.";
        assert emissionIdTextField != null : "fx:id=\"emissionIdTextField\" was not injected: check your FXML file 'EMISSIONPanel.fxml'.";
        assert emitterAltLabel != null : "fx:id=\"emitterAltLabel\" was not injected: check your FXML file 'EMISSIONPanel.fxml'.";
        assert emitterAltTextField != null : "fx:id=\"emitterAltTextField\" was not injected: check your FXML file 'EMISSIONPanel.fxml'.";
        assert emitterLatLabel != null : "fx:id=\"emitterLatLabel\" was not injected: check your FXML file 'EMISSIONPanel.fxml'.";
        assert emitterLatTextField != null : "fx:id=\"emitterLatTextField\" was not injected: check your FXML file 'EMISSIONPanel.fxml'.";
        assert emitterLongLabel != null : "fx:id=\"emitterLongLabel\" was not injected: check your FXML file 'EMISSIONPanel.fxml'.";
        assert emitterLongTextField != null : "fx:id=\"emitterLongTextField\" was not injected: check your FXML file 'EMISSIONPanel.fxml'.";
        assert freqAgilityLabel != null : "fx:id=\"freqAgilityLabel\" was not injected: check your FXML file 'EMISSIONPanel.fxml'.";
        assert frequencAgilityComboBox != null : "fx:id=\"frequencAgilityComboBox\" was not injected: check your FXML file 'EMISSIONPanel.fxml'.";
        assert frequenciesLabel != null : "fx:id=\"frequenciesLabel\" was not injected: check your FXML file 'EMISSIONPanel.fxml'.";
        assert frequenciesTextField != null : "fx:id=\"frequenciesTextField\" was not injected: check your FXML file 'EMISSIONPanel.fxml'.";
        assert functionComboBox != null : "fx:id=\"functionComboBox\" was not injected: check your FXML file 'EMISSIONPanel.fxml'.";
        assert functionLabel != null : "fx:id=\"functionLabel\" was not injected: check your FXML file 'EMISSIONPanel.fxml'.";
        assert latitudeLabel != null : "fx:id=\"latitudeLabel\" was not injected: check your FXML file 'EMISSIONPanel.fxml'.";
        assert latitudeTextField != null : "fx:id=\"latitudeTextField\" was not injected: check your FXML file 'EMISSIONPanel.fxml'.";
        assert longitudeLabel != null : "fx:id=\"longitudeLabel\" was not injected: check your FXML file 'EMISSIONPanel.fxml'.";
        assert longitudeTextField != null : "fx:id=\"longitudeTextField\" was not injected: check your FXML file 'EMISSIONPanel.fxml'.";
        assert powerLabel != null : "fx:id=\"powerLabel\" was not injected: check your FXML file 'EMISSIONPanel.fxml'.";
        assert powerTextField != null : "fx:id=\"powerTextField\" was not injected: check your FXML file 'EMISSIONPanel.fxml'.";
        assert prfAgilityComboBox != null : "fx:id=\"prfAgilityComboBox\" was not injected: check your FXML file 'EMISSIONPanel.fxml'.";
        assert prfAgilityLabel != null : "fx:id=\"prfAgilityLabel\" was not injected: check your FXML file 'EMISSIONPanel.fxml'.";
        assert sidcComboBox != null : "fx:id=\"sidcComboBox\" was not injected: check your FXML file 'EMISSIONPanel.fxml'.";
        assert sidcDimComboBox != null : "fx:id=\"sidcDimComboBox\" was not injected: check your FXML file 'EMISSIONPanel.fxml'.";
        assert sidcIDComboBox != null : "fx:id=\"sidcIDComboBox\" was not injected: check your FXML file 'EMISSIONPanel.fxml'.";
        assert spotNumberTextField != null : "fx:id=\"spotNumberTextField\" was not injected: check your FXML file 'EMISSIONPanel.fxml'.";
        assert spotnumberLabel != null : "fx:id=\"spotnumberLabel\" was not injected: check your FXML file 'EMISSIONPanel.fxml'.";

	Tooltip tooltipEmissionID = new Tooltip("A positive identification unique number of the emission chosen by the sender of this message. This number should also be unique in terms of contact numbers.");
	Tooltip tooltipDeleteFlag = new Tooltip("Contact has to be removed");
	
	
	// EmissionID
	emissionIdTextField.setTooltip(tooltipEmissionID);
	emissionIdTextField.textProperty().addListener((observable, oldValue,newValue) -> {
		if(!newValue.equals("")) {
			contactIdLabel.setVisible(false);
		}else {
			contactIdLabel.setVisible(true);
		}
		});
	emissionIdTextField.setTextFormatter(new TextFormatter<>(change -> {
		String newText = change.getControlNewText();
		if(newText.matches("\\d*")) {
			return change;
		}
		return null;
	}));
	
	// DeleteFlaGComboBo
	this.deleteFlagComboBox.setItems(FXCollections.observableList(Arrays.asList(DeleteFlag.values())));
	this.deleteFlagComboBox.getSelectionModel().select(0);
	this.deleteFlagComboBox.setTooltip(tooltipDeleteFlag);		
	
	// Sensor Latitude
	latitudeTextField.setTextFormatter(MessagePanelController.createLatitudeFormatter());
	latitudeTextField.textProperty().addListener((observable, oldValue, newValue) -> {
		if (!newValue.isEmpty() && !newValue.equals("-")) {
			try {
		        double value = Double.parseDouble(newValue);
		        latitudeTextField.setText(latitudeFormat.format(value));
		        } catch (NumberFormatException e) {
		        // Ignorieren, da der Filter ungültige Eingaben bereits verhindert
		        }
		    }else if (newValue.isEmpty()) {
		       	if(oldValue != null && oldValue.length() > 1 && latitudeTextField.getSelection().getLength() == 0) {
		       		latitudeTextField.setText(oldValue);
		       	}  
		    }
		});
	latitudeTextField.focusedProperty().addListener((observable, oldValue,newValue) -> {
		if(!newValue) {
			latitudeLabel.setVisible(!validateAndFormatLatField(latitudeTextField));	
		}
	});
			
	// Sensor Longitude
	longitudeTextField.setTextFormatter(MessagePanelController.createLongitudeFormatter());
	longitudeTextField.textProperty().addListener((observable, oldValue, newValue) -> {
		if (!newValue.isEmpty() && !newValue.equals("-")) {
			try {
				double value = Double.parseDouble(newValue);    
		        longitudeTextField.setText(longitudeFormat.format(value));
		    } catch (NumberFormatException e) {
		                // Ignorieren, da der Filter ungültige Eingaben bereits verhindert
		    	}
		    }else if (newValue.isEmpty()) {
		    	if(oldValue != null && oldValue.length() > 1 && longitudeTextField.getSelection().getLength() == 0) {
		    		longitudeTextField.setText(oldValue);
		        }  
		    }
		 });
			
	longitudeTextField.focusedProperty().addListener((observable, oldValue,newValue) -> {
		if(!newValue) {
			longitudeLabel.setVisible(!validateAndFormatLongField(longitudeTextField));	}
			});
	
	// Sensor Altitude
	altitudeTextField.setTextFormatter(new TextFormatter<>(change -> {
	String newText = change.getControlNewText();
	if(newText.matches("\\d*")) {
		return change;
		}
	return null;
	}));
	altitudeLabel.setVisible(false);

	// Emitter Latitude
	emitterLatTextField.setTextFormatter(MessagePanelController.createLatitudeFormatter());
	emitterLatTextField.textProperty().addListener((observable, oldValue, newValue) -> {
		if (!newValue.isEmpty() && !newValue.equals("-")) {
			try {
		        double value = Double.parseDouble(newValue);
		        emitterLatTextField.setText(latitudeFormat.format(value));
		        } catch (NumberFormatException e) {
		        // Ignorieren, da der Filter ungültige Eingaben bereits verhindert
		        }
		    }else if (newValue.isEmpty()) {
		       	if(oldValue != null && oldValue.length() > 1 && emitterLatTextField.getSelection().getLength() == 0) {
		       		emitterLatTextField.setText(oldValue);
		       	}  
		    }
		});
	
	emitterLatTextField.focusedProperty().addListener((observable, oldValue,newValue) -> {
		if(!newValue) {
			validateAndFormatLatField(emitterLatTextField);	
		}
	});
	
	emitterLatLabel.setVisible(false);
	
	
	// Emitter Longitude
	emitterLongTextField.setTextFormatter(MessagePanelController.createLongitudeFormatter());
	emitterLongTextField.textProperty().addListener((observable, oldValue, newValue) -> {
		if (!newValue.isEmpty() && !newValue.equals("-")) {
			try {
				double value = Double.parseDouble(newValue);    
				emitterLongTextField.setText(longitudeFormat.format(value));
		    } catch (NumberFormatException e) {
		                // Ignorieren, da der Filter ungültige Eingaben bereits verhindert
		    	}
		    }else if (newValue.isEmpty()) {
		    	if(oldValue != null && oldValue.length() > 1 && emitterLongTextField.getSelection().getLength() == 0) {
		    		emitterLongTextField.setText(oldValue);
		        }  
		    }
		 });
			
	emitterLongTextField.focusedProperty().addListener((observable, oldValue,newValue) -> {
		if(!newValue) {
			validateAndFormatLongField(emitterLongTextField);	
			}
		});
	
	emitterLongLabel.setVisible(false);
	
	// Emitter Altitude
	emitterAltTextField.setTextFormatter(new TextFormatter<>(change -> {
	String newText = change.getControlNewText();
	if(newText.matches("\\d*")) {
		return change;
		}
	return null;
	}));
	emitterAltLabel.setVisible(false);

	// Bearing
	bearingTextField.setTextFormatter(MessagePanelController.createBearingFormatter());
	bearingTextField.textProperty().addListener((observable, oldValue, newValue) -> {
		if (!newValue.isEmpty() && !newValue.equals("")) {
            try {
                double value = Double.parseDouble(newValue);
                bearingTextField.setText(bearingFormat.format(value));
            } catch (NumberFormatException e) {
                // Ignorieren, da der Filter ungültige Eingaben bereits verhindert
            }
        }else if (newValue.isEmpty()) {
        	if(oldValue != null && oldValue.length() > 1 && bearingTextField.getSelection().getLength() == 0) {
        		bearingTextField.setText(oldValue);
        	}
        }
    });
	
	bearingTextField.focusedProperty().addListener((observable, oldValue,newValue) -> {
		if(!newValue) {
			bearingLabel.setVisible(!validateAndFormatCourseField(bearingTextField));
		}
	});
	
	// Frequency
	frequenciesTextField.setTextFormatter(new TextFormatter<>(change -> {
		String newText = change.getControlNewText();
		if(newText.matches("[0-9#]*")) {
			return change;
		}
		return null;
	}));
	frequenciesLabel.setVisible(false);
	
	frequenciesTextField.focusedProperty().addListener((observable, oldValue,newValue) -> {
		if(!newValue) {
			frequenciesInput(frequenciesTextField.getText());	
		}
	});
	// Bandwidth
	bandwithTextField.setTextFormatter(new TextFormatter<>(change -> {
		String newText = change.getControlNewText();
		if(newText.matches("\\d*")) {
			return change;
		}
		return null;
	}));
	bandwidthLabel.setVisible(false);
	
	// Power
	powerTextField.setTextFormatter(new TextFormatter<>(change -> {
		String newText = change.getControlNewText();
		if(newText.matches("\\d*")) {
			return change;
		}
		return null;
	}));	
	powerLabel.setVisible(false);
	
	// Frequency Agility
	this.frequencAgilityComboBox.setItems(FXCollections.observableList(MessageTool.frequAgilityList));
	this.frequencAgilityComboBox.getSelectionModel().select(0);
	this.freqAgilityLabel.setVisible(false);
	// PRF Agility
	this.prfAgilityComboBox.setItems(FXCollections.observableList(MessageTool.prfAgilityList));
	this.prfAgilityComboBox.getSelectionModel().select(0);
	this.prfAgilityLabel.setVisible(false);
	
	// Function
	this.functionComboBox.setItems(FXCollections.observableList(MessageTool.FunctionList));
	this.functionComboBox.getSelectionModel().select(0);
	this.functionLabel.setVisible(false);
	
	// SpotNumber
	this.spotnumberLabel.setVisible(false);
	
	// SIDC ComboBoxen
	this.sidcIDComboBox.setItems(FXCollections.observableList(MessagePanelController.identitiesList));
	this.sidcIDComboBox.getSelectionModel().select(0);
	this.sidcDimComboBox.setItems(FXCollections.observableList(MessagePanelController.DimensionsList));
	this.sidcDimComboBox.getSelectionModel().selectedIndexProperty().addListener((observable, o, n) -> {
	    switch ((int) n) {
	    case 0 -> this.sidcComboBox.setItems(null);
	    case 1 -> this.sidcComboBox.setItems(FXCollections.observableList(SIDCCodes.spaceCodesList));
	    case 2 -> this.sidcComboBox.setItems(FXCollections.observableList(SIDCCodes.airCodesList));
	    case 3 -> this.sidcComboBox.setItems(FXCollections.observableList(SIDCCodes.surfaceCodesList));
	    case 4 -> this.sidcComboBox.setItems(FXCollections.observableList(SIDCCodes.subsurfaceCodesList));
	    case 5 -> this.sidcComboBox.setItems(FXCollections.observableList(SIDCCodes.groundCodesList));
	    default -> throw new IllegalArgumentException();
	    }
	    this.sidcComboBox.getSelectionModel().select(0);
	});
	this.sidcDimComboBox.getSelectionModel().select(2);
	this.sidcComboBox.getSelectionModel().select(5);
	setSIDC();
	
    }
    
    private List<Double> frequencies= new ArrayList<>();;
    private char[] sidc;
    
    private void setSIDC() {
    	StringBuilder sb = new StringBuilder(MessagePanelController.getDimensionCharFromIndex(this.sidcDimComboBox.getSelectionModel().getSelectedIndex()) 
    			+ " " + this.sidcComboBox.getSelectionModel().getSelectedItem());
    	sb.setCharAt(1, MessagePanelController.getIdentiy(sidcIDComboBox.getValue()));
    	int pipeIndex = sb.indexOf("|");
    	    if (pipeIndex != -1) {
    	        sb.setLength(pipeIndex);
    	    }
    	sb.append("-----");
    	String tmp = sb.toString();
    	this.sidc = tmp.toCharArray();
    }
    
    private void frequenciesInput(String input) {
        String[] numbers = input.split("#");
        frequencies.clear();
        for (String number : numbers) {
            if (!number.isEmpty()) {
            	frequencies.add(Double.parseDouble(number));
            }
        }
    }
    
    private boolean validateAndFormatCourseField(TextField textField) {
    	try {
    		double value = Double.parseDouble(textField.getText().replace(',', '.'));
    		if(value >= 0.0 && value <= 359.99) {
    			textField.setText(String.format("%06.2f",value).replace(',', '.'));
    			return true;
    		}else {
    			textField.setText("");
    			return false;
    		}
    		}catch (NumberFormatException ex) {
    			textField.setText("");
    			return false;
    		}
    	}
    
    private boolean validateAndFormatLatField(TextField textField) {
    	try {
    		double value = Double.parseDouble(textField.getText().replace(',', '.'));
    		if(value >= -90 && value <= 90) {
    			textField.setText(latitudeFormat.format(value).replace(',', '.'));
    			return true;
    		}else {
    			textField.setText("");
    			return false;
    		}
    		}catch (NumberFormatException ex) {
    			textField.setText("");
    			return false;
    		}
    	}
    	
    private boolean validateAndFormatLongField(TextField textField) {
    	try {
    		double value = Double.parseDouble(textField.getText().replace(',', '.'));
    		if(value >= -180 && value <= 180) {
    			textField.setText(longitudeFormat.format(value).replace(',', '.'));
    			return true;
    		}else {
    			textField.setText("");
    			return false;
    		}
    		}catch (NumberFormatException ex) {
    			textField.setText("");
    			return false;
    		}
    	}

    
    DecimalFormat latitudeFormat  = new DecimalFormat("00.0000");
    DecimalFormat longitudeFormat = new DecimalFormat("000.0000");
    DecimalFormat bearingFormat   = new DecimalFormat("000.0000");
    
 
    @Override
    public EMISSION createMessage(Short number, Long time, String sender, Classification classification, Acknowledgement acknowledgement, String mac) {

    	EMISSION emission = new EMISSION(number, time, sender,
		classification, acknowledgement, mac,
		this.emissionIdTextField.getText(),
		this.deleteFlagComboBox.getValue(),
		Double.parseDouble(this.latitudeTextField.getText()),
		Double.parseDouble(this.longitudeTextField.getText()),
		this.altitudeTextField.getText().isEmpty() ? 0.0 : Double.parseDouble(this.altitudeTextField.getText()),
		this.emitterLatTextField.getText().isEmpty() ? 0.0 : Double.parseDouble(this.emitterLatTextField.getText()),
		this.emitterLongTextField.getText().isEmpty() ? 0.0 : Double.parseDouble(this.emitterLongTextField.getText()),
		this.emitterAltTextField.getText().isEmpty() ? 0.0 : Double.parseDouble(this.emitterAltTextField.getText()),
		this.bearingTextField.getText().isEmpty() ? 0.0 : Double.parseDouble(this.bearingTextField.getText()),
		this.frequencies.isEmpty() ? null : this.frequencies,
		this.bandwithTextField.getText().isEmpty() ? 0.0 : Double.parseDouble(this.bandwithTextField.getText()),
		this.powerTextField.getText().isEmpty() ? 0.0 : Double.parseDouble(this.powerTextField.getText()),
    	MessageTool.getFrequAgility(this.frequencAgilityComboBox.getValue()),
    	MessageTool.getPrfAgility(this.prfAgilityComboBox.getValue()),
    	MessageTool.getFunction(this.functionComboBox.getValue()),
    	this.spotNumberTextField.getText().isEmpty() ? 0 : Integer.parseInt(this.spotNumberTextField.getText()),
    	this.sidc,
    	this.commentTextArea.getText());
						
		return emission;
    }

    @Override
    public boolean isValidFilled() {
    	// nur Pflichtfelder zur Überprüfung, ab wann eine Nachricht gesendet werden kann
    	return (!contactIdLabel.isVisible() && !latitudeLabel.isVisible() && !longitudeLabel.isVisible() && !bearingLabel.isVisible());
    }

}
