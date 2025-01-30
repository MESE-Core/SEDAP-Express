package de.bundeswehr.mese.sedapexpress.messagetool.controller;

import java.text.DecimalFormat;

import de.bundeswehr.mese.sedapexpress.messages.OWNUNIT;
import de.bundeswehr.mese.sedapexpress.messages.SEDAPExpressMessage;
import de.bundeswehr.mese.sedapexpress.messages.SEDAPExpressMessage.Acknowledgement;
import de.bundeswehr.mese.sedapexpress.messages.SEDAPExpressMessage.Classification;
import de.bundeswehr.mese.sedapexpress.messages.SIDCCodes;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.TextFormatter;
import javafx.scene.control.Tooltip;

public class OWNUNITPanelController extends MessagePanelController {

    @FXML
    private TextField altitudeTextField;

    @FXML
    private Label courseLabel;

    @FXML
    private TextField courseTextField;

    @FXML
    private Label headingLabel;

    @FXML
    private TextField headingTextField;

    @FXML
    private Label latitudeLabel;

    @FXML
    private TextField latitudeTextField;

    @FXML
    private Label longitudeLabel;

    @FXML
    private TextField longitudeTextField;

    @FXML
    private TextField nameTextField;

    @FXML
    private Label pitchLabel;

    @FXML
    private TextField pitchTextField;

    @FXML
    private Label rollLabel;

    @FXML
    private TextField rollTextField;

    @FXML
    private ComboBox<String> sidcComboBox;

    @FXML
    private ComboBox<String> sidcDimComboBox;

    @FXML
    private TextField speedTextField;

    @FXML
    void initialize() {
	assert this.altitudeTextField != null : "fx:id=\"altitudeTextField\" was not injected: check your FXML file 'OWNUNITPanel.fxml'.";
	assert this.courseLabel != null : "fx:id=\"courseLabel\" was not injected: check your FXML file 'OWNUNITPanel.fxml'.";
	assert this.courseTextField != null : "fx:id=\"courseTextField\" was not injected: check your FXML file 'OWNUNITPanel.fxml'.";
	assert this.headingLabel != null : "fx:id=\"headingLabel\" was not injected: check your FXML file 'OWNUNITPanel.fxml'.";
	assert this.headingTextField != null : "fx:id=\"headingTextField\" was not injected: check your FXML file 'OWNUNITPanel.fxml'.";
	assert this.latitudeLabel != null : "fx:id=\"latitudeLabel\" was not injected: check your FXML file 'OWNUNITPanel.fxml'.";
	assert this.latitudeTextField != null : "fx:id=\"latitudeTextField\" was not injected: check your FXML file 'OWNUNITPanel.fxml'.";
	assert this.longitudeLabel != null : "fx:id=\"longitudeLabel\" was not injected: check your FXML file 'OWNUNITPanel.fxml'.";
	assert this.longitudeTextField != null : "fx:id=\"longitudeTextField\" was not injected: check your FXML file 'OWNUNITPanel.fxml'.";
	assert this.nameTextField != null : "fx:id=\"nameTextField\" was not injected: check your FXML file 'OWNUNITPanel.fxml'.";
	assert this.pitchLabel != null : "fx:id=\"pitchLabel\" was not injected: check your FXML file 'OWNUNITPanel.fxml'.";
	assert this.pitchTextField != null : "fx:id=\"pitchTextField\" was not injected: check your FXML file 'OWNUNITPanel.fxml'.";
	assert this.rollLabel != null : "fx:id=\"rollLabel\" was not injected: check your FXML file 'OWNUNITPanel.fxml'.";
	assert this.rollTextField != null : "fx:id=\"rollTextField\" was not injected: check your FXML file 'OWNUNITPanel.fxml'.";
	assert this.sidcComboBox != null : "fx:id=\"sidcComboBox\" was not injected: check your FXML file 'OWNUNITPanel.fxml'.";
	assert this.sidcDimComboBox != null : "fx:id=\"sidcDimComboBox\" was not injected: check your FXML file 'OWNUNITPanel.fxml'.";
	assert this.speedTextField != null : "fx:id=\"speedTextField\" was not injected: check your FXML file 'OWNUNITPanel.fxml'.";

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
	
	Tooltip tooltipLat = new Tooltip("Values from (-) 0.0 - 89.99 are legal.\n" + "The input must be made with a decimal point. \n" + "Example: 23.0");
	Tooltip tooltipLong = new Tooltip("Values from (-)0.0 - 179.99 are legal\n" + "The input must be made with a decimal point. \n" + "Example: 23.0");
	Tooltip tooltipAlt = new Tooltip("Value in Meter");
	Tooltip tooltipSOG = new Tooltip("Speed over ground, value in meter/second");
	Tooltip tooltipCOG = new Tooltip("Course over ground, value 0.0 - 359.99 are legal.\n" + "The input must be made with a decimal point. \n" + "Example: 23.0");
	Tooltip tooltipHeading = new Tooltip("Heading of own unit, value 0.0 - 359.99 are legal.\n" + "The input must be made with a decimal point. \n" + "Example: 23.0");
	Tooltip tooltipRoll = new Tooltip("Roll of own unit, value 0.0 - 359.99 are legal.\n" + "The input must be made with a decimal point. \n" + "Example: 23.0");
	Tooltip tooltipPitch = new Tooltip("Pitch of own unit, value 0.0 - 359.99 are legal.\n" +  "The input must be made with a decimal point. \n" + "Example: 23.0");
	Tooltip tooltipName = new Tooltip("Name of onw unit, all values legal");
	
	latitudeTextField.setTooltip(tooltipLat);
	longitudeTextField.setTooltip(tooltipLong);
	altitudeTextField.setTooltip(tooltipAlt);
	speedTextField.setTooltip(tooltipSOG);
	courseTextField.setTooltip(tooltipCOG);
	headingTextField.setTooltip(tooltipHeading);
	rollTextField.setTooltip(tooltipRoll);
	pitchTextField.setTooltip(tooltipPitch);
	nameTextField.setTooltip(tooltipName);
	
	//SIDC Combo-Boxen
	this.sidcDimComboBox.getSelectionModel().select(2);
	this.sidcComboBox.getSelectionModel().select(33);
	setSIDC();
	
	// Latitude
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
	
	// Longitude
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
			longitudeLabel.setVisible(!validateAndFormatLongField(longitudeTextField));
		}
	});
	
	// Altitude
	altitudeTextField.setTextFormatter(new TextFormatter<>(change -> {
		String newText = change.getControlNewText();
		if(newText.matches("\\d*")) {
			return change;
		}
		return null;
	}));
	
	// Speed
	speedTextField.setTextFormatter(new TextFormatter<>(change -> {
		String newText = change.getControlNewText();
		if(newText.matches(SEDAPExpressMessage.DOUBLE_MATCHER.toString())|| change.isDeleted()) {
			return change;
		}
		return null;
	}));
	
	// Kurs
	courseTextField.setTextFormatter(MessagePanelController.createBearingFormatter());
	courseTextField.textProperty().addListener((observable, oldValue, newValue) -> {
        if (!newValue.isEmpty() && !newValue.equals("")) {
            try {
                double value = Double.parseDouble(newValue);
                courseTextField.setText(bearingFormat.format(value));
            } catch (NumberFormatException e) {
                // Ignorieren, da der Filter ungültige Eingaben bereits verhindert
            }
        }else if (newValue.isEmpty()) {
        	if(oldValue != null && oldValue.length() > 1 && courseTextField.getSelection().getLength() == 0) {
        		courseTextField.setText(oldValue);
        	}  
        }
    });
	
	courseTextField.focusedProperty().addListener((observable, oldValue,newValue) -> {
		if(!newValue) {
			courseLabel.setVisible(!validateAndFormatCourseField(courseTextField));
		}
	});
	
	// Heading
	headingTextField.setTextFormatter(MessagePanelController.createBearingFormatter());
	headingTextField.textProperty().addListener((observable, oldValue, newValue) -> {
		if (!newValue.isEmpty() && !newValue.equals("")) {
            try {
                double value = Double.parseDouble(newValue);
                headingTextField.setText(bearingFormat.format(value));
            } catch (NumberFormatException e) {
                // Ignorieren, da der Filter ungültige Eingaben bereits verhindert
            }
        }else if (newValue.isEmpty()) {
        	if(oldValue != null && oldValue.length() > 1 && headingTextField.getSelection().getLength() == 0) {
        		 headingTextField.setText(oldValue);
        	}
        }
    });
	
	headingTextField.focusedProperty().addListener((observable, oldValue,newValue) -> {
		if(!newValue) {
			headingLabel.setVisible(!validateAndFormatCourseField(headingTextField));
		}
	});
	
	// Roll
	rollTextField.setTextFormatter(MessagePanelController.createBearingFormatter());
	rollTextField.textProperty().addListener((observable, oldValue,newValue) -> {
		if (!newValue.isEmpty() && !newValue.equals("")) {
            try {
                double value = Double.parseDouble(newValue);
                rollTextField.setText(bearingFormat.format(value));
            } catch (NumberFormatException e) {
                // Ignorieren, da der Filter ungültige Eingaben bereits verhindert
            }
        }else if (newValue.isEmpty()) {
        	if(oldValue != null && oldValue.length() > 1 && rollTextField.getSelection().getLength() == 0) {
        		rollTextField.setText(oldValue);
        	}
        }
    });
	rollTextField.focusedProperty().addListener((observable, oldValue,newValue) -> {
		if(!newValue) {
			rollLabel.setVisible(!validateAndFormatCourseField(rollTextField));
		}
	});
	
	// Pitch
	pitchTextField.setTextFormatter(MessagePanelController.createBearingFormatter());
	pitchTextField.textProperty().addListener((observable, oldValue,newValue) -> {
		if (!newValue.isEmpty() && !newValue.equals("")) {
            try {
                double value = Double.parseDouble(newValue);
                pitchTextField.setText(bearingFormat.format(value));
            } catch (NumberFormatException e) {
                // Ignorieren, da der Filter ungültige Eingaben bereits verhindert
            }
        }else if (newValue.isEmpty()) {
        	if(oldValue != null && oldValue.length() > 1 && pitchTextField.getSelection().getLength() == 0) {
        		pitchTextField.setText(oldValue);
        	}
        }
    });
	pitchTextField.focusedProperty().addListener((observable, oldValue,newValue) -> {
		if(!newValue) {
			pitchLabel.setVisible(!validateAndFormatCourseField(pitchTextField));
		}
	});

 
	sidcDimComboBox.valueProperty().addListener((observable, oldvalue, newValue) -> {
		if(newValue != null) {
			setSIDC();
		}else {
			this.sidc = null;
			}		
		});
    
	sidcComboBox.valueProperty().addListener((observable, oldvalue, newValue) -> {
		if(newValue != null) {
			setSIDC();
		}else {
			this.sidc = null;
			}		
		});
	
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
    
    private void setSIDC() {
    	StringBuilder sb = new StringBuilder(MessagePanelController.getDimensionCharFromIndex(this.sidcDimComboBox.getSelectionModel().getSelectedIndex()) 
    			+ " " + this.sidcComboBox.getSelectionModel().getSelectedItem());
    	sb.setCharAt(1, MessagePanelController.getIdentiy(sidcComboBox.getValue()));
    	int pipeIndex = sb.indexOf("|");
    	    if (pipeIndex != -1) {
    	        sb.setLength(pipeIndex);
    	    }
    	sb.append("-----");
    	String tmp = sb.toString();
    	this.sidc = tmp.toCharArray();
    }
    
    DecimalFormat latitudeFormat  = new DecimalFormat("00.0000");
    DecimalFormat longitudeFormat = new DecimalFormat("000.0000");
    DecimalFormat bearingFormat   = new DecimalFormat("000.0000");
    
    private char[] sidc;
   
    @Override
    public OWNUNIT createMessage(Short number, Long time, String sender,
	    Classification classification, Acknowledgement acknowledgement, String mac) {

    	OWNUNIT ownunit = new OWNUNIT(number, time, sender,
    			classification, acknowledgement, mac,
    			Double.parseDouble(this.latitudeTextField.getText()),
    			Double.parseDouble(this.longitudeTextField.getText()),
    			this.altitudeTextField.getText().isEmpty() ? 0.0 : Double.parseDouble(this.altitudeTextField.getText()),
    			this.speedTextField.getText().isEmpty() ? 0.0 :Double.parseDouble(this.speedTextField.getText()),
    			this.courseTextField.getText().isEmpty() ? 0.0 :Double.parseDouble(this.courseTextField.getText()),
    			this.headingTextField.getText().isEmpty() ? 0.0 :Double.parseDouble(this.headingTextField.getText()),
    			this.rollTextField.getText().isEmpty() ? 0.0 :Double.parseDouble(this.rollTextField.getText()),
    			this.pitchTextField.getText().isEmpty() ? 0.0 :Double.parseDouble(this.pitchTextField.getText()),
    			this.nameTextField.getText(),
    			this.sidc);
    			
	return ownunit;
    }

    @Override
    public boolean isValidFilled() {
    	// nur Pflichtfelder zur Überprüfung, ab wann eine Nachricht gesendet werden kann
    	return (!latitudeLabel.isVisible() && !longitudeLabel.isVisible());
    			//&& !courseLabel.isVisible() && !headingLabel.isVisible()
    			//&& !rollLabel.isVisible() && !pitchLabel.isVisible());
    }
    
}
