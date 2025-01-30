package de.bundeswehr.mese.sedapexpress.messagetool.controller;

import de.bundeswehr.mese.sedapexpress.messages.METEO;
import de.bundeswehr.mese.sedapexpress.messages.SEDAPExpressMessage;
import de.bundeswehr.mese.sedapexpress.messages.SEDAPExpressMessage.Acknowledgement;
import de.bundeswehr.mese.sedapexpress.messages.SEDAPExpressMessage.Classification;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.TextFormatter;
import javafx.scene.image.ImageView;

public class METEOPanelController extends MessagePanelController {

    @FXML
    private Label airTemperatureLabel;

    @FXML
    private TextField airTemperatureTextField;

    @FXML
    private Label cloudCoverLabel;

    @FXML
    private TextField cloudCoverTextField;

    @FXML
    private Label cloudHeightLabel;

    @FXML
    private TextField cloudHeightTextField;

    @FXML
    private Label contactIdLabel5;

    @FXML
    private Label dewPointLabel;

    @FXML
    private TextField dewPointTextField;

    @FXML
    private Label humidityRelLabel;

    @FXML
    private TextField humidityRelTextField;

    @FXML
    private Label pressureLabel;

    @FXML
    private TextField pressureTextField;

    @FXML
    private Label speedThroughWaterLabel;

    @FXML
    private TextField speedThroughWaterTextfield;

    @FXML
    private Label visibilityLabel;

    @FXML
    private TextField visibilityTextField;

    @FXML
    private Label waterDepthLabel;

    @FXML
    private TextField waterDepthTextField;

    @FXML
    private Label waterDirectionLabel;

    @FXML
    private TextField waterDirectionTextField;

    @FXML
    private Label waterSpeedLabel;

    @FXML
    private TextField waterSpeedTextField;

    @FXML
    private Label waterTemperatureLabel;

    @FXML
    private TextField waterTemperatureTextField;

    @FXML
    private Label windDirectionLabel;

    @FXML
    private TextField windDirectionTextField;

    @FXML
    private Label windSpeedLabel;

    @FXML
    private TextField windSpeedTextField;

    @FXML
    void initialize() {
	assert this.airTemperatureLabel != null : "fx:id=\"airTemperatureLabel\" was not injected: check your FXML file 'METEOPanel.fxml'.";
	assert this.airTemperatureTextField != null : "fx:id=\"airTemperatureTextField\" was not injected: check your FXML file 'METEOPanel.fxml'.";
	assert this.cloudCoverLabel != null : "fx:id=\"cloudCoverLabel\" was not injected: check your FXML file 'METEOPanel.fxml'.";
	assert this.cloudCoverTextField != null : "fx:id=\"cloudCoverTextField\" was not injected: check your FXML file 'METEOPanel.fxml'.";
	assert this.cloudHeightLabel != null : "fx:id=\"cloudHeightLabel\" was not injected: check your FXML file 'METEOPanel.fxml'.";
	assert this.cloudHeightTextField != null : "fx:id=\"cloudHeightTextField\" was not injected: check your FXML file 'METEOPanel.fxml'.";
	assert this.contactIdLabel5 != null : "fx:id=\"contactIdLabel5\" was not injected: check your FXML file 'METEOPanel.fxml'.";
	assert this.dewPointLabel != null : "fx:id=\"dewPointLabel\" was not injected: check your FXML file 'METEOPanel.fxml'.";
	assert this.dewPointTextField != null : "fx:id=\"dewPointTextField\" was not injected: check your FXML file 'METEOPanel.fxml'.";
	assert this.humidityRelLabel != null : "fx:id=\"humidityRelLabel\" was not injected: check your FXML file 'METEOPanel.fxml'.";
	assert this.humidityRelTextField != null : "fx:id=\"humidityRelTextField\" was not injected: check your FXML file 'METEOPanel.fxml'.";
	assert this.pressureLabel != null : "fx:id=\"pressureLabel\" was not injected: check your FXML file 'METEOPanel.fxml'.";
	assert this.pressureTextField != null : "fx:id=\"pressureTextField\" was not injected: check your FXML file 'METEOPanel.fxml'.";
	assert this.speedThroughWaterLabel != null : "fx:id=\"speedThroughWaterLabel\" was not injected: check your FXML file 'METEOPanel.fxml'.";
	assert this.speedThroughWaterTextfield != null : "fx:id=\"speedThroughWaterTextfield\" was not injected: check your FXML file 'METEOPanel.fxml'.";
	assert this.visibilityLabel != null : "fx:id=\"visibilityLabel\" was not injected: check your FXML file 'METEOPanel.fxml'.";
	assert this.visibilityTextField != null : "fx:id=\"visibilityTextField\" was not injected: check your FXML file 'METEOPanel.fxml'.";
	assert this.waterDepthLabel != null : "fx:id=\"waterDepthLabel\" was not injected: check your FXML file 'METEOPanel.fxml'.";
	assert this.waterDepthTextField != null : "fx:id=\"waterDepthTextField\" was not injected: check your FXML file 'METEOPanel.fxml'.";
	assert this.waterDirectionLabel != null : "fx:id=\"waterDirectionLabel\" was not injected: check your FXML file 'METEOPanel.fxml'.";
	assert this.waterDirectionTextField != null : "fx:id=\"waterDirectionTextField\" was not injected: check your FXML file 'METEOPanel.fxml'.";
	assert this.waterSpeedLabel != null : "fx:id=\"waterSpeedLabel\" was not injected: check your FXML file 'METEOPanel.fxml'.";
	assert this.waterSpeedTextField != null : "fx:id=\"waterSpeedTextField\" was not injected: check your FXML file 'METEOPanel.fxml'.";
	assert this.waterTemperatureLabel != null : "fx:id=\"waterTemperatureLabel\" was not injected: check your FXML file 'METEOPanel.fxml'.";
	assert this.waterTemperatureTextField != null : "fx:id=\"waterTemperatureTextField\" was not injected: check your FXML file 'METEOPanel.fxml'.";
	assert this.windDirectionLabel != null : "fx:id=\"windDirectionLabel\" was not injected: check your FXML file 'METEOPanel.fxml'.";
	assert this.windDirectionTextField != null : "fx:id=\"windDirectionTextField\" was not injected: check your FXML file 'METEOPanel.fxml'.";
	assert this.windSpeedLabel != null : "fx:id=\"windSpeedLabel\" was not injected: check your FXML file 'METEOPanel.fxml'.";
	assert this.windSpeedTextField != null : "fx:id=\"windSpeedTextField\" was not injected: check your FXML file 'METEOPanel.fxml'.";

	
	// SpeedThrougWater
    speedThroughWaterTextfield.setTextFormatter(new TextFormatter<>(change -> {
 		String newText = change.getControlNewText();
 		if(newText.matches("\\d*\\.?\\d*")) {
 			return change;
 		}
 		return null;
 	}));
    speedThroughWaterTextfield.focusedProperty().addListener((observable, oldValue, newValue) -> {
        if (!newValue) {  // Wenn der Fokus verloren geht
        	String text = speedThroughWaterTextfield.getText();
            if(text.isEmpty()) {
            	speedThroughWater=null;
            }else {
            	if (text.startsWith(".")) {
            		text = "0" + text;
            	}
            	if (!text.contains(".")) {
            		text = text + ".0";
            	}
            	if (text.endsWith(".")) {
            		text = text + "0";
            	}
            	speedThroughWaterTextfield.setText(text);
        		speedThroughWater= Double.parseDouble(text);
     			System.out.println("Wert: " + speedThroughWater);
        	}
        }
    });
    speedThroughWaterLabel.setVisible(false);
    
	// WaterSpeed
    waterSpeedTextField.setTextFormatter(new TextFormatter<>(change -> {
 		String newText = change.getControlNewText();
 		if(newText.matches("\\d*\\.?\\d*")) {
 			return change;
 		}
 		return null;
 	}));
    waterSpeedTextField.focusedProperty().addListener((observable, oldValue, newValue) -> {
        if (!newValue) {  // Wenn der Fokus verloren geht
            String text = waterSpeedTextField.getText();
            if(text.isEmpty()) {
            	speedThroughWater=null;
            }else {
            	if (text.startsWith(".")) {
            		text = "0" + text;
            	}
            	if (!text.contains(".")) {
            		text = text + ".0";
            	}
            	if (text.endsWith(".")) {
            		text = text + "0";
            	}
            	waterSpeedTextField.setText(text);
            	waterSpeed= Double.parseDouble(text);
     			System.out.println("Wert: " + waterSpeed);
        	}
        }
    });
    waterSpeedLabel.setVisible(false);
    
	// WaterDirection
    waterDirectionTextField.setTextFormatter(new TextFormatter<>(change -> {
 		String newText = change.getControlNewText();
 		if(newText.matches("\\d*\\.?\\d*")) {
 			return change;
 		}
 		return null;
 	}));
    waterDirectionTextField.focusedProperty().addListener((observable, oldValue, newValue) -> {
        if (!newValue) {  // Wenn der Fokus verloren geht
            String text = waterDirectionTextField.getText();
            if(text.isEmpty()) {
            	waterDirection=null;
            }else {
            	if (text.startsWith(".")) {
            		text = "0" + text;
            	}
            	if (!text.contains(".")) {
            		text = text + ".0";
            	}
            	if (text.endsWith(".")) {
            		text = text + "0";
            	}
            	waterDirectionTextField.setText(text);
            	waterDirection= Double.parseDouble(text);
        	}
        }
    });
    waterDirectionLabel.setVisible(false);
    
	// WaterTemperature
    waterTemperatureTextField.setTextFormatter(new TextFormatter<>(change -> {
 		String newText = change.getControlNewText();
 		if(newText.matches("\\d*\\.?\\d*")) {
 			return change;
 		}
 		return null;
 	}));
    waterTemperatureTextField.focusedProperty().addListener((observable, oldValue, newValue) -> {
        if (!newValue) {  // Wenn der Fokus verloren geht
            String text = waterTemperatureTextField.getText();
            if(text.isEmpty()) {
            	waterTemperature=null;
            }else {
            	if (text.startsWith(".")) {
            		text = "0" + text;
            	}
            	if (!text.contains(".")) {
            		text = text + ".0";
            	}
            	if (text.endsWith(".")) {
            		text = text + "0";
            	}
            	waterTemperatureTextField.setText(text);
            	waterTemperature= Double.parseDouble(text);
        	}
        }
    });
    waterTemperatureLabel.setVisible(false);
    
	// WaterDepth
    waterDepthTextField.setTextFormatter(new TextFormatter<>(change -> {
 		String newText = change.getControlNewText();
 		if(newText.matches("\\d*\\.?\\d*")) {
 			return change;
 		}
 		return null;
 	}));
    waterDepthTextField.focusedProperty().addListener((observable, oldValue, newValue) -> {
        if (!newValue) {  // Wenn der Fokus verloren geht
            String text = waterDepthTextField.getText();
            if(text.isEmpty()) {
            	waterDepth=null;
            }else {
            	if (text.startsWith(".")) {
            		text = "0" + text;
            	}
            	if (!text.contains(".")) {
            		text = text + ".0";
            	}
            	if (text.endsWith(".")) {
            		text = text + "0";
            	}
            	waterDepthTextField.setText(text);
            	waterDepth= Double.parseDouble(text);
        	}
        }
    });
    waterDepthLabel.setVisible(false);
    
	// AirTemperatur
    airTemperatureTextField.setTextFormatter(new TextFormatter<>(change -> {
 		String newText = change.getControlNewText();
 		if(newText.matches("\\d*\\.?\\d*")) {
 			return change;
 		}
 		return null;
 	}));
    airTemperatureTextField.focusedProperty().addListener((observable, oldValue, newValue) -> {
        if (!newValue) {  // Wenn der Fokus verloren geht
            String text = airTemperatureTextField.getText();
            if(text.isEmpty()) {
            	airTemperature=null;
            }else {
            	if (text.startsWith(".")) {
            		text = "0" + text;
            	}
            	if (!text.contains(".")) {
            		text = text + ".0";
            	}
            	if (text.endsWith(".")) {
            		text = text + "0";
            	}
            	airTemperatureTextField.setText(text);
            	airTemperature= Double.parseDouble(text);
        	}
        }
    });
    airTemperatureLabel.setVisible(false);
    
	// DewPoint
    dewPointTextField.setTextFormatter(new TextFormatter<>(change -> {
 		String newText = change.getControlNewText();
 		if(newText.matches("\\d*\\.?\\d*")) {
 			return change;
 		}
 		return null;
 	}));
    dewPointTextField.focusedProperty().addListener((observable, oldValue, newValue) -> {
        if (!newValue) {  // Wenn der Fokus verloren geht
            String text = dewPointTextField.getText();
            if(text.isEmpty()) {
            	dewPoint=null;
            }else {
            	if (text.startsWith(".")) {
            		text = "0" + text;
            	}
            	if (!text.contains(".")) {
            		text = text + ".0";
            	}
            	if (text.endsWith(".")) {
            		text = text + "0";
            	}
            	dewPointTextField.setText(text);
            	dewPoint= Double.parseDouble(text);
        	}
        }
    });
    dewPointLabel.setVisible(false);
    
	// Humidity Rel
    humidityRelTextField.setTextFormatter(new TextFormatter<>(change -> {
 		String newText = change.getControlNewText();
 		if(newText.matches("\\d*\\.?\\d*")) {
 			return change;
 		}
 		return null;
 	}));
    humidityRelTextField.focusedProperty().addListener((observable, oldValue, newValue) -> {
        if (!newValue) {  // Wenn der Fokus verloren geht
            String text = humidityRelTextField.getText();
            if(text.isEmpty()) {
            	humidityRel=null;
            }else {
            	if (text.startsWith(".")) {
            		text = "0" + text;
            	}
            	if (!text.contains(".")) {
            		text = text + ".0";
            	}
            	if (text.endsWith(".")) {
            		text = text + "0";
            	}
            	humidityRelTextField.setText(text);
            	humidityRel= Double.parseDouble(text);
        	}
        }
    });
    humidityRelLabel.setVisible(false);
    
	// Pressure Text
    pressureTextField.setTextFormatter(new TextFormatter<>(change -> {
 		String newText = change.getControlNewText();
 		if(newText.matches("\\d*\\.?\\d*")) {
 			return change;
 		}
 		return null;
 	}));
    pressureTextField.focusedProperty().addListener((observable, oldValue, newValue) -> {
        if (!newValue) {  // Wenn der Fokus verloren geht
            String text = pressureTextField.getText();
            if(text.isEmpty()) {
            	pressure=null;
            }else {
            	if (text.startsWith(".")) {
            		text = "0" + text;
            	}
            	if (!text.contains(".")) {
            		text = text + ".0";
            	}
            	if (text.endsWith(".")) {
            		text = text + "0";
            	}
            	pressureTextField.setText(text);
            	pressure= Double.parseDouble(text);
        	}
        }
    });
    pressureLabel.setVisible(false);
    
	// Wind Speed
    windSpeedTextField.setTextFormatter(new TextFormatter<>(change -> {
 		String newText = change.getControlNewText();
 		if(newText.matches("\\d*\\.?\\d*")) {
 			return change;
 		}
 		return null;
 	}));
    windSpeedTextField.focusedProperty().addListener((observable, oldValue, newValue) -> {
        if (!newValue) {  // Wenn der Fokus verloren geht
            String text = windSpeedTextField.getText();
            if(text.isEmpty()) {
            	windSpeed=null;
            }else {
            	if (text.startsWith(".")) {
            		text = "0" + text;
            	}
            	if (!text.contains(".")) {
            		text = text + ".0";
            	}
            	if (text.endsWith(".")) {
            		text = text + "0";
            	}
            	windSpeedTextField.setText(text);
            	windSpeed= Double.parseDouble(text);
        	}
        }
    });
    windSpeedLabel.setVisible(false);
    
	// Wind Direction
    windDirectionTextField.setTextFormatter(new TextFormatter<>(change -> {
 		String newText = change.getControlNewText();
 		if(newText.matches("\\d*\\.?\\d*")) {
 			return change;
 		}
 		return null;
 	}));
    windDirectionTextField.focusedProperty().addListener((observable, oldValue, newValue) -> {
        if (!newValue) {  // Wenn der Fokus verloren geht
            String text = windDirectionTextField.getText();
            if(text.isEmpty()) {
            	windDirection=null;
            }else {
            	if (text.startsWith(".")) {
            		text = "0" + text;
            	}
            	if (!text.contains(".")) {
            		text = text + ".0";
            	}
            	if (text.endsWith(".")) {
            		text = text + "0";
            	}
            	windDirectionTextField.setText(text);
            	windDirection= Double.parseDouble(text);
        	}
        }
    });
    windDirectionLabel.setVisible(false);
    
	// Visibility
    visibilityTextField.setTextFormatter(new TextFormatter<>(change -> {
 		String newText = change.getControlNewText();
 		if(newText.matches("\\d*\\.?\\d*")) {
 			return change;
 		}
 		return null;
 	}));
    visibilityTextField.focusedProperty().addListener((observable, oldValue, newValue) -> {
        if (!newValue) {  // Wenn der Fokus verloren geht
            String text = visibilityTextField.getText();
            if(text.isEmpty()) {
            	visibility=null;
            }else {
            	if (text.startsWith(".")) {
            		text = "0" + text;
            	}
            	if (!text.contains(".")) {
            		text = text + ".0";
            	}
            	if (text.endsWith(".")) {
            		text = text + "0";
            	}
            	visibilityTextField.setText(text);
            	visibility= Double.parseDouble(text);
        	}
        }
    });
    visibilityLabel.setVisible(false);
    
	// Cloud Height
    cloudHeightTextField.setTextFormatter(new TextFormatter<>(change -> {
 		String newText = change.getControlNewText();
 		if(newText.matches("\\d*\\.?\\d*")) {
 			return change;
 		}
 		return null;
 	}));
    cloudHeightTextField.focusedProperty().addListener((observable, oldValue, newValue) -> {
        if (!newValue) {  // Wenn der Fokus verloren geht
            String text = cloudHeightTextField.getText();
            if(text.isEmpty()) {
            	cloudHeight=null;
            }else {
            	if (text.startsWith(".")) {
            		text = "0" + text;
            	}
            	if (!text.contains(".")) {
            		text = text + ".0";
            	}
            	if (text.endsWith(".")) {
            		text = text + "0";
            	}
            	cloudHeightTextField.setText(text);
            	cloudHeight= Double.parseDouble(text);
        	}
        }
    });
    cloudHeightLabel.setVisible(false);
    
	// Cloud Cover
    cloudCoverTextField.setTextFormatter(new TextFormatter<>(change -> {
 		String newText = change.getControlNewText();
 		if(newText.matches("\\d*\\.?\\d*")) {
 			return change;
 		}
 		return null;
 	}));
    cloudCoverTextField.focusedProperty().addListener((observable, oldValue, newValue) -> {
        if (!newValue) {  // Wenn der Fokus verloren geht
            String text = cloudCoverTextField.getText();
            if(text.isEmpty()) {
            	cloudCover=null;
            }else {
            	if (text.startsWith(".")) {
            		text = "0" + text;
            	}
            	if (!text.contains(".")) {
            		text = text + ".0";
            	}
            	if (text.endsWith(".")) {
            		text = text + "0";
            	}
            	cloudCoverTextField.setText(text);
            	cloudCover= Double.parseDouble(text);
        	}
        }
    });
    cloudCoverLabel.setVisible(false);
    
    }

    Double speedThroughWater, waterSpeed, waterDirection,waterTemperature, waterDepth, airTemperature, dewPoint, humidityRel, 
    pressure,  windSpeed,  windDirection,visibility,  cloudHeight,  cloudCover;
    
    @Override
    public METEO createMessage(Short number, Long time, String sender,
	    Classification classification, Acknowledgement acknowledgement, String mac) {

    	METEO meteo = new METEO(number, time, sender,
		classification, acknowledgement, mac,
		this.speedThroughWater,
		this.waterSpeed,
		this.waterDirection,
		this.waterTemperature,
		this.waterDepth,
		this.airTemperature,
		this.dewPoint,
		this.humidityRel,
		this.pressure,
		this.windSpeed,
		this.windDirection,
		this.visibility,
		this.cloudHeight,
		this.cloudCover
		);
		
	return meteo;
    }

    @Override
    public boolean isValidFilled() {

    	return true;
    }
}
