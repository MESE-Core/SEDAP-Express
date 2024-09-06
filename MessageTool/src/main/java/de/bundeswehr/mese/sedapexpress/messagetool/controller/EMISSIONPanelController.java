package de.bundeswehr.mese.sedapexpress.messagetool.controller;

import java.net.URL;
import java.util.Arrays;
import java.util.ResourceBundle;

import de.bundeswehr.mese.sedapexpress.messages.SEDAPExpressMessage;
import de.bundeswehr.mese.sedapexpress.messages.SEDAPExpressMessage.Acknowledgement;
import de.bundeswehr.mese.sedapexpress.messages.SEDAPExpressMessage.Classification;
import de.bundeswehr.mese.sedapexpress.messages.SIDCCodes;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

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
    private TextField bandwithTextField;

    @FXML
    private TextField bearingTextField;

    @FXML
    private TextArea commentTextArea;

    @FXML
    private Label contactIdLabel;

    @FXML
    private TextField contactIdTextField;

    @FXML
    private Label courseLabel;

    @FXML
    private ComboBox<String> deleteFlagComboBox;

    @FXML
    private TextField emitterAltTextField;

    @FXML
    private TextField emitterLatTextField;

    @FXML
    private TextField emitterLongTextField;

    @FXML
    private TextField freqAgilityTextField;

    @FXML
    private TextField frequenciesTextField;

    @FXML
    private TextField functionTextField;

    @FXML
    private Label headingLabel;

    @FXML
    private Label heightLabel;

    @FXML
    private Label latitudeLabel;

    @FXML
    private TextField latitudeTextField;

    @FXML
    private Label lengthLabel;

    @FXML
    private Label longitudeLabel;

    @FXML
    private TextField longitudeTextField;

    @FXML
    private Label pitchLabel;

    @FXML
    private TextField powerTextField;

    @FXML
    private TextField prfAgilityTextField;

    @FXML
    private Label relatveXdistanceLabel;

    @FXML
    private Label relatveYdistanceLabel;

    @FXML
    private Label relatveZdistanceLabel;

    @FXML
    private Label rollLabel;

    @FXML
    private ComboBox<String> sidcComboBox;

    @FXML
    private ComboBox<String> sidcDimComboBox;

    @FXML
    private ComboBox<String> sidcIDComboBox;

    @FXML
    private Label speedLabel;

    @FXML
    private TextField spotNumberTextField;

    @FXML
    private Label widthLabel;

    @FXML
    void initialize() {
	assert this.altitudeLabel != null : "fx:id=\"altitudeLabel\" was not injected: check your FXML file 'EMISSIONPanel.fxml'.";
	assert this.altitudeTextField != null : "fx:id=\"altitudeTextField\" was not injected: check your FXML file 'EMISSIONPanel.fxml'.";
	assert this.bandwithTextField != null : "fx:id=\"bandwithTextField\" was not injected: check your FXML file 'EMISSIONPanel.fxml'.";
	assert this.bearingTextField != null : "fx:id=\"bearingTextField\" was not injected: check your FXML file 'EMISSIONPanel.fxml'.";
	assert this.commentTextArea != null : "fx:id=\"commentTextArea\" was not injected: check your FXML file 'EMISSIONPanel.fxml'.";
	assert this.contactIdLabel != null : "fx:id=\"contactIdLabel\" was not injected: check your FXML file 'EMISSIONPanel.fxml'.";
	assert this.contactIdTextField != null : "fx:id=\"contactIdTextField\" was not injected: check your FXML file 'EMISSIONPanel.fxml'.";
	assert this.courseLabel != null : "fx:id=\"courseLabel\" was not injected: check your FXML file 'EMISSIONPanel.fxml'.";
	assert this.deleteFlagComboBox != null : "fx:id=\"deleteFlagComboBox\" was not injected: check your FXML file 'EMISSIONPanel.fxml'.";
	assert this.emitterAltTextField != null : "fx:id=\"emitterAltTextField\" was not injected: check your FXML file 'EMISSIONPanel.fxml'.";
	assert this.emitterLatTextField != null : "fx:id=\"emitterLatTextField\" was not injected: check your FXML file 'EMISSIONPanel.fxml'.";
	assert this.emitterLongTextField != null : "fx:id=\"emitterLongTextField\" was not injected: check your FXML file 'EMISSIONPanel.fxml'.";
	assert this.freqAgilityTextField != null : "fx:id=\"freqAgilityTextField\" was not injected: check your FXML file 'EMISSIONPanel.fxml'.";
	assert this.frequenciesTextField != null : "fx:id=\"frequenciesTextField\" was not injected: check your FXML file 'EMISSIONPanel.fxml'.";
	assert this.functionTextField != null : "fx:id=\"functionTextField\" was not injected: check your FXML file 'EMISSIONPanel.fxml'.";
	assert this.headingLabel != null : "fx:id=\"headingLabel\" was not injected: check your FXML file 'EMISSIONPanel.fxml'.";
	assert this.heightLabel != null : "fx:id=\"heightLabel\" was not injected: check your FXML file 'EMISSIONPanel.fxml'.";
	assert this.latitudeLabel != null : "fx:id=\"latitudeLabel\" was not injected: check your FXML file 'EMISSIONPanel.fxml'.";
	assert this.latitudeTextField != null : "fx:id=\"latitudeTextField\" was not injected: check your FXML file 'EMISSIONPanel.fxml'.";
	assert this.lengthLabel != null : "fx:id=\"lengthLabel\" was not injected: check your FXML file 'EMISSIONPanel.fxml'.";
	assert this.longitudeLabel != null : "fx:id=\"longitudeLabel\" was not injected: check your FXML file 'EMISSIONPanel.fxml'.";
	assert this.longitudeTextField != null : "fx:id=\"longitudeTextField\" was not injected: check your FXML file 'EMISSIONPanel.fxml'.";
	assert this.pitchLabel != null : "fx:id=\"pitchLabel\" was not injected: check your FXML file 'EMISSIONPanel.fxml'.";
	assert this.powerTextField != null : "fx:id=\"powerTextField\" was not injected: check your FXML file 'EMISSIONPanel.fxml'.";
	assert this.prfAgilityTextField != null : "fx:id=\"prfAgilityTextField\" was not injected: check your FXML file 'EMISSIONPanel.fxml'.";
	assert this.relatveXdistanceLabel != null : "fx:id=\"relatveXdistanceLabel\" was not injected: check your FXML file 'EMISSIONPanel.fxml'.";
	assert this.relatveYdistanceLabel != null : "fx:id=\"relatveYdistanceLabel\" was not injected: check your FXML file 'EMISSIONPanel.fxml'.";
	assert this.relatveZdistanceLabel != null : "fx:id=\"relatveZdistanceLabel\" was not injected: check your FXML file 'EMISSIONPanel.fxml'.";
	assert this.rollLabel != null : "fx:id=\"rollLabel\" was not injected: check your FXML file 'EMISSIONPanel.fxml'.";
	assert this.sidcComboBox != null : "fx:id=\"sidcComboBox\" was not injected: check your FXML file 'EMISSIONPanel.fxml'.";
	assert this.sidcDimComboBox != null : "fx:id=\"sidcDimComboBox\" was not injected: check your FXML file 'EMISSIONPanel.fxml'.";
	assert this.sidcIDComboBox != null : "fx:id=\"sidcIDComboBox\" was not injected: check your FXML file 'EMISSIONPanel.fxml'.";
	assert this.speedLabel != null : "fx:id=\"speedLabel\" was not injected: check your FXML file 'EMISSIONPanel.fxml'.";
	assert this.spotNumberTextField != null : "fx:id=\"spotNumberTextField\" was not injected: check your FXML file 'EMISSIONPanel.fxml'.";
	assert this.widthLabel != null : "fx:id=\"widthLabel\" was not injected: check your FXML file 'EMISSIONPanel.fxml'.";

	this.deleteFlagComboBox
		.setItems(
			  FXCollections
				  .observableList(
						  Arrays
							  .asList(
								  "yes",
								  "no")));
	this.deleteFlagComboBox.getSelectionModel().select(1);

	this.sidcIDComboBox.setItems(FXCollections.observableList(SEDAPExpressMessage.identitiesList));
	this.sidcIDComboBox.getSelectionModel().select(3);
	this.sidcDimComboBox.setItems(FXCollections.observableList(SEDAPExpressMessage.dimensionsList));
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
	});
	this.sidcDimComboBox.getSelectionModel().select(2);
	this.sidcComboBox.getSelectionModel().select(5);

    }

    @Override
    public SEDAPExpressMessage createMessage(Short number, Long time, String sender, Classification classification, Acknowledgement acknowledgement, String mac) {

	return null;
    }

    @Override
    public boolean isValidFilled() {

	return true;
    }

}
