package de.bundeswehr.mese.sedapexpress.messagetool.controller;

import de.bundeswehr.mese.sedapexpress.messages.SEDAPExpressMessage;
import de.bundeswehr.mese.sedapexpress.messages.SEDAPExpressMessage.Acknowledgement;
import de.bundeswehr.mese.sedapexpress.messages.SEDAPExpressMessage.Classification;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
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
    private ImageView pressureLabel;

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

    }

    @Override
    public SEDAPExpressMessage createMessage(Short number, Long time, String sender,
	    Classification classification, Acknowledgement acknowledgement, String mac) {

	return null;
    }

    @Override
    public boolean isValidFilled() {

	return true;
    }
}
