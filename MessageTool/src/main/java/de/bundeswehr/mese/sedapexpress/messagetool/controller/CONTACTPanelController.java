package de.bundeswehr.mese.sedapexpress.messagetool.controller;

import java.util.Arrays;

import de.bundeswehr.mese.sedapexpress.messages.CONTACT.DeleteFlag;
import de.bundeswehr.mese.sedapexpress.messages.CONTACT.Source;
import de.bundeswehr.mese.sedapexpress.messages.SEDAPExpressMessage;
import de.bundeswehr.mese.sedapexpress.messages.SEDAPExpressMessage.Acknowledgement;
import de.bundeswehr.mese.sedapexpress.messages.SEDAPExpressMessage.Classification;
import de.bundeswehr.mese.sedapexpress.messages.SIDCCodes;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

public class CONTACTPanelController extends MessagePanelController {

    @FXML
    private Label altitudeLabel;

    @FXML
    private TextField altitudeTextField;

    @FXML
    private TextArea commentTextArea;

    @FXML
    private Label contactIdLabel;

    @FXML
    private TextField contactIdTextField;

    @FXML
    private Label courseLabel;

    @FXML
    private TextField courseTextField;

    @FXML
    private ComboBox<DeleteFlag> deleteFlagComboBox;

    @FXML
    private Label headingLabel;

    @FXML
    private TextField headingTextField;

    @FXML
    private Label heightLabel;

    @FXML
    private TextField heightTextField;

    @FXML
    private Label icaoLabel;

    @FXML
    private TextField icaoTextField;

    @FXML
    private Label latitudeLabel;

    @FXML
    private TextField latitudeTextField;

    @FXML
    private Label lengthLabel;

    @FXML
    private TextField lengthTextField;

    @FXML
    private Button loadImageButton;

    @FXML
    private Label longitudeLabel;

    @FXML
    private TextField longitudeTextField;

    @FXML
    private Label mmsiLabel;

    @FXML
    private TextField mmsiTextField;

    @FXML
    private TextField nameTextField;

    @FXML
    private Label pitchLabel;

    @FXML
    private TextField pitchTextField;

    @FXML
    private Label relatveXdistanceLabel;

    @FXML
    private TextField relatveXdistanceTextField;

    @FXML
    private Label relatveYdistanceLabel;

    @FXML
    private TextField relatveYdistanceTextField;

    @FXML
    private Label relatveZdistanceLabel;

    @FXML
    private TextField relatveZdistanceTextField;

    @FXML
    private Label rollLabel;

    @FXML
    private TextField rollTextField;

    @FXML
    private ComboBox<String> sidcComboBox;

    @FXML
    private ComboBox<String> sidcDimComboBox;

    @FXML
    private ComboBox<String> sidcIDComboBox;

    @FXML
    private ComboBox<Source> sourceComboBox;

    @FXML
    private Label speedLabel;

    @FXML
    private TextField speedTextField;

    @FXML
    private Label widthLabel;

    @FXML
    private TextField widthTextField;

    @FXML
    void loadImage(ActionEvent event) {

    }

    @FXML
    void initialize() {
	assert this.altitudeLabel != null : "fx:id=\"altitudeLabel\" was not injected: check your FXML file 'CONTACTPanel.fxml'.";
	assert this.altitudeTextField != null : "fx:id=\"altitudeTextField\" was not injected: check your FXML file 'CONTACTPanel.fxml'.";
	assert this.commentTextArea != null : "fx:id=\"commentTextArea\" was not injected: check your FXML file 'CONTACTPanel.fxml'.";
	assert this.contactIdLabel != null : "fx:id=\"contactIdLabel\" was not injected: check your FXML file 'CONTACTPanel.fxml'.";
	assert this.contactIdTextField != null : "fx:id=\"contactIdTextField\" was not injected: check your FXML file 'CONTACTPanel.fxml'.";
	assert this.courseLabel != null : "fx:id=\"courseLabel\" was not injected: check your FXML file 'CONTACTPanel.fxml'.";
	assert this.courseTextField != null : "fx:id=\"courseTextField\" was not injected: check your FXML file 'CONTACTPanel.fxml'.";
	assert this.deleteFlagComboBox != null : "fx:id=\"deleteFlagComboBox\" was not injected: check your FXML file 'CONTACTPanel.fxml'.";
	assert this.headingLabel != null : "fx:id=\"headingLabel\" was not injected: check your FXML file 'CONTACTPanel.fxml'.";
	assert this.headingTextField != null : "fx:id=\"headingTextField\" was not injected: check your FXML file 'CONTACTPanel.fxml'.";
	assert this.heightLabel != null : "fx:id=\"heightLabel\" was not injected: check your FXML file 'CONTACTPanel.fxml'.";
	assert this.heightTextField != null : "fx:id=\"heightTextField\" was not injected: check your FXML file 'CONTACTPanel.fxml'.";
	assert this.icaoLabel != null : "fx:id=\"icaoLabel\" was not injected: check your FXML file 'CONTACTPanel.fxml'.";
	assert this.icaoTextField != null : "fx:id=\"icaoTextField\" was not injected: check your FXML file 'CONTACTPanel.fxml'.";
	assert this.latitudeLabel != null : "fx:id=\"latitudeLabel\" was not injected: check your FXML file 'CONTACTPanel.fxml'.";
	assert this.latitudeTextField != null : "fx:id=\"latitudeTextField\" was not injected: check your FXML file 'CONTACTPanel.fxml'.";
	assert this.lengthLabel != null : "fx:id=\"lengthLabel\" was not injected: check your FXML file 'CONTACTPanel.fxml'.";
	assert this.lengthTextField != null : "fx:id=\"lengthTextField\" was not injected: check your FXML file 'CONTACTPanel.fxml'.";
	assert this.loadImageButton != null : "fx:id=\"loadImageButton\" was not injected: check your FXML file 'CONTACTPanel.fxml'.";
	assert this.longitudeLabel != null : "fx:id=\"longitudeLabel\" was not injected: check your FXML file 'CONTACTPanel.fxml'.";
	assert this.longitudeTextField != null : "fx:id=\"longitudeTextField\" was not injected: check your FXML file 'CONTACTPanel.fxml'.";
	assert this.mmsiLabel != null : "fx:id=\"mmsiLabel\" was not injected: check your FXML file 'CONTACTPanel.fxml'.";
	assert this.mmsiTextField != null : "fx:id=\"mmsiTextField\" was not injected: check your FXML file 'CONTACTPanel.fxml'.";
	assert this.nameTextField != null : "fx:id=\"nameTextField\" was not injected: check your FXML file 'CONTACTPanel.fxml'.";
	assert this.pitchLabel != null : "fx:id=\"pitchLabel\" was not injected: check your FXML file 'CONTACTPanel.fxml'.";
	assert this.pitchTextField != null : "fx:id=\"pitchTextField\" was not injected: check your FXML file 'CONTACTPanel.fxml'.";
	assert this.relatveXdistanceLabel != null : "fx:id=\"relatveXdistanceLabel\" was not injected: check your FXML file 'CONTACTPanel.fxml'.";
	assert this.relatveXdistanceTextField != null : "fx:id=\"relatveXdistanceTextField\" was not injected: check your FXML file 'CONTACTPanel.fxml'.";
	assert this.relatveYdistanceLabel != null : "fx:id=\"relatveYdistanceLabel\" was not injected: check your FXML file 'CONTACTPanel.fxml'.";
	assert this.relatveYdistanceTextField != null : "fx:id=\"relatveYdistanceTextField\" was not injected: check your FXML file 'CONTACTPanel.fxml'.";
	assert this.relatveZdistanceLabel != null : "fx:id=\"relatveZdistanceLabel\" was not injected: check your FXML file 'CONTACTPanel.fxml'.";
	assert this.relatveZdistanceTextField != null : "fx:id=\"relatveZdistanceTextField\" was not injected: check your FXML file 'CONTACTPanel.fxml'.";
	assert this.rollLabel != null : "fx:id=\"rollLabel\" was not injected: check your FXML file 'CONTACTPanel.fxml'.";
	assert this.rollTextField != null : "fx:id=\"rollTextField\" was not injected: check your FXML file 'CONTACTPanel.fxml'.";
	assert this.sidcComboBox != null : "fx:id=\"sidcComboBox\" was not injected: check your FXML file 'CONTACTPanel.fxml'.";
	assert this.sidcDimComboBox != null : "fx:id=\"sidcDimComboBox\" was not injected: check your FXML file 'CONTACTPanel.fxml'.";
	assert this.sidcIDComboBox != null : "fx:id=\"sidcIDComboBox\" was not injected: check your FXML file 'CONTACTPanel.fxml'.";
	assert this.sourceComboBox != null : "fx:id=\"sourceComboBox\" was not injected: check your FXML file 'CONTACTPanel.fxml'.";
	assert this.speedLabel != null : "fx:id=\"speedLabel\" was not injected: check your FXML file 'CONTACTPanel.fxml'.";
	assert this.speedTextField != null : "fx:id=\"speedTextField\" was not injected: check your FXML file 'CONTACTPanel.fxml'.";
	assert this.widthLabel != null : "fx:id=\"widthLabel\" was not injected: check your FXML file 'CONTACTPanel.fxml'.";
	assert this.widthTextField != null : "fx:id=\"widthTextField\" was not injected: check your FXML file 'CONTACTPanel.fxml'.";

	this.sourceComboBox.setItems(FXCollections.observableList(Arrays.asList(Source.values())));

	this.deleteFlagComboBox.setItems(FXCollections.observableList(Arrays.asList(DeleteFlag.values())));
	this.deleteFlagComboBox.getSelectionModel().select(1);

	this.sidcIDComboBox.setItems(FXCollections.observableList(SEDAPExpressMessage.identitiesList));
	this.sidcIDComboBox.getSelectionModel().select(6);
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
    public SEDAPExpressMessage createMessage(Short number, Long time, String sender,
	    Classification classification, Acknowledgement acknowledgement, String mac) {

	return null;
    }

    @Override
    public boolean isValidFilled() {

	return true;
    }

}
