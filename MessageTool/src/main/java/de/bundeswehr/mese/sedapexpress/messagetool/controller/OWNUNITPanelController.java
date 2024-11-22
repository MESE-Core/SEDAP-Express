package de.bundeswehr.mese.sedapexpress.messagetool.controller;

import de.bundeswehr.mese.sedapexpress.messages.SEDAPExpressMessage;
import de.bundeswehr.mese.sedapexpress.messages.SEDAPExpressMessage.Acknowledgement;
import de.bundeswehr.mese.sedapexpress.messages.SEDAPExpressMessage.Classification;
import de.bundeswehr.mese.sedapexpress.messages.SIDCCodes;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

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

	this.sidcDimComboBox.setItems(FXCollections.observableList(MessagePanelController.dimensionsList));
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
	this.sidcComboBox.getSelectionModel().select(33);
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
