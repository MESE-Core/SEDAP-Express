/**
 * Note: This license has also been called the “Simplified BSD License” and the “FreeBSD License”.
 *
 * Copyright 2024 MESE POC: Volker Voß, Federal Armed Forces of Germany
 *
 * Redistribution and use in source and binary forms, with or without modification, are permitted
 * provided that the following conditions are met:
 *
 * 1. Redistributions of source code must retain the above copyright notice, this list of
 * conditions and the following disclaimer.
 *
 * 2. Redistributions in binary form must reproduce the above copyright notice, this list of
 * conditions and the following disclaimer in the documentation and/or other materials provided with
 * the distribution.
 *
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS “AS IS” AND ANY EXPRESS OR
 * IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE IMPLIED WARRANTIES OF MERCHANTABILITY AND
 * FITNESS FOR A PARTICULAR PURPOSEnARE DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT HOLDER OR
 * CONTRIBUTORS BEn LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR
 * CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR
 * SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON ANY
 * THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING NEGLIGENCE OR
 * OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY
 * OF SUCH DAMAGE.
 */
package de.bundeswehr.mese.sedapexpress.messagetool;

import java.io.IOException;
import java.util.Arrays;

import de.bundeswehr.mese.sedapexpress.messages.SEDAPExpressMessage;
import de.bundeswehr.mese.sedapexpress.messages.SEDAPExpressMessage.Acknowledgement;
import de.bundeswehr.mese.sedapexpress.messages.SEDAPExpressMessage.Classification;
import de.bundeswehr.mese.sedapexpress.messagetool.controller.ACKNOWLEDGEPanelController;
import de.bundeswehr.mese.sedapexpress.messagetool.controller.COMMANDPanelController;
import de.bundeswehr.mese.sedapexpress.messagetool.controller.CONTACTPanelController;
import de.bundeswehr.mese.sedapexpress.messagetool.controller.EMISSIONPanelController;
import de.bundeswehr.mese.sedapexpress.messagetool.controller.GENERICPanelController;
import de.bundeswehr.mese.sedapexpress.messagetool.controller.GRAPHICPanelController;
import de.bundeswehr.mese.sedapexpress.messagetool.controller.HEARTBEATPanelController;
import de.bundeswehr.mese.sedapexpress.messagetool.controller.KEYEXCHANGEPanelController;
import de.bundeswehr.mese.sedapexpress.messagetool.controller.METEOPanelController;
import de.bundeswehr.mese.sedapexpress.messagetool.controller.MessagePanelController;
import de.bundeswehr.mese.sedapexpress.messagetool.controller.OWNUNITPanelController;
import de.bundeswehr.mese.sedapexpress.messagetool.controller.RESENDPanelController;
import de.bundeswehr.mese.sedapexpress.messagetool.controller.STATUSPanelController;
import de.bundeswehr.mese.sedapexpress.messagetool.controller.TEXTPanelController;
import de.bundeswehr.mese.sedapexpress.messagetool.controller.TIMESYNCPanelController;
import de.bundeswehr.mese.sedapexpress.network.SEDAPExpressCommunicator;
import de.bundeswehr.mese.sedapexpress.network.SEDAPExpressTCPClient;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

public class MessageTool extends Application {

    @FXML
    private CheckBox authenticationCheckBox;

    @FXML
    private Button connectButton;

    @FXML
    private Button disconnectButton;

    @FXML
    private CheckBox encryptedCheckBox;

    @FXML
    private TextArea inputLogTextArea;

    @FXML
    private TextField ipTextField;

    @FXML
    private TextArea keyTextField;

    @FXML
    private AnchorPane messageBorderPane;

    @FXML
    private TextArea outputLogTextArea;

    @FXML
    private TextField portTextField;

    @FXML
    private CheckBox protobufCheckBox;

    @FXML
    private Label numberLabel;

    @FXML
    private TextField numberTextField;

    @FXML
    private Label timeLabel;

    @FXML
    private TextField timeTextField;

    @FXML
    private CheckBox autoTimeCheckBox;

    @FXML
    private Label senderLabel;

    @FXML
    private TextField senderTextField;

    @FXML
    private ComboBox<Classification> classificationComboBox;

    @FXML
    private ComboBox<Acknowledgement> acknowledgmentComboBox;

    @FXML
    private Label macLabel;

    @FXML
    private TextField macTextField;

    @FXML
    private Button copyClipboardButton;

    @FXML
    private Button sendMessageButton;

    private SEDAPExpressCommunicator communicator;

    @FXML
    void connectToSEDAPExpress(ActionEvent event) {

	this.communicator = new SEDAPExpressTCPClient(this.ipTextField.getText(), Integer.parseInt(this.portTextField.getText()));

	if (this.communicator.connect()) {
	    this.connectButton.setDisable(true);
	    this.disconnectButton.setDisable(false);
	} else {
	    this.inputLogTextArea.appendText(this.communicator.getLastException().getLocalizedMessage() + "\n");
	}
    }

    @FXML
    void disconnectFromSEDAPExpress(ActionEvent event) {
	this.connectButton.setDisable(false);
	this.disconnectButton.setDisable(true);
	this.communicator.stopCommunicator();
    }

    @FXML
    void createOWNUNIT(ActionEvent event) {
	this.messageBorderPane.getChildren().clear();
	this.messageBorderPane.getChildren().add(this.ownunitPanel);

	this.currentController = this.ownunitController;
    }

    @FXML
    void createCONTACT(ActionEvent event) {
	this.messageBorderPane.getChildren().clear();
	this.messageBorderPane.getChildren().add(this.contactPanel);

	this.currentController = this.contactController;
    }

    @FXML
    void createEMISSION(ActionEvent event) {
	this.messageBorderPane.getChildren().clear();
	this.messageBorderPane.getChildren().add(this.emissionPanel);

	this.currentController = this.emissionController;
    }

    @FXML
    void createMETEO(ActionEvent event) {
	this.messageBorderPane.getChildren().clear();
	this.messageBorderPane.getChildren().add(this.meteoPanel);

	this.currentController = this.meteoController;
    }

    @FXML
    void createTEXT(ActionEvent event) {
	this.messageBorderPane.getChildren().clear();
	this.messageBorderPane.getChildren().add(this.textPanel);

	this.currentController = this.textController;
    }

    @FXML
    void createCOMMAND(ActionEvent event) {
	this.messageBorderPane.getChildren().clear();
	this.messageBorderPane.getChildren().add(this.commandPanel);

	this.currentController = this.commandController;
    }

    @FXML
    void createSTATUS(ActionEvent event) {
	this.messageBorderPane.getChildren().clear();
	this.messageBorderPane.getChildren().add(this.statusPanel);

	this.currentController = this.statusController;
    }

    @FXML
    void createGRAPHIC(ActionEvent event) {
	this.messageBorderPane.getChildren().clear();
	this.messageBorderPane.getChildren().add(this.graphicPanel);

	this.currentController = this.graphicController;
    }

    @FXML
    void createKEYEXCHANGE(ActionEvent event) {
	this.messageBorderPane.getChildren().clear();
	this.messageBorderPane.getChildren().add(this.keyexchangePanel);

	this.currentController = this.keyexchangeController;
    }

    @FXML
    void createACKNOWLEDGE(ActionEvent event) {
	this.messageBorderPane.getChildren().clear();
	this.messageBorderPane.getChildren().add(this.acknowledgePanel);

	this.currentController = this.acknowledgeController;
    }

    @FXML
    void createRESEND(ActionEvent event) {
	this.messageBorderPane.getChildren().clear();
	this.messageBorderPane.getChildren().add(this.resendPanel);

	this.currentController = this.resendController;
    }

    @FXML
    void createHEARTBEAT(ActionEvent event) {
	this.messageBorderPane.getChildren().clear();
	this.messageBorderPane.getChildren().add(this.heartbeatPanel);

	this.currentController = this.heartbeatController;
    }

    @FXML
    void createTIMESYNC(ActionEvent event) {
	this.messageBorderPane.getChildren().clear();
	this.messageBorderPane.getChildren().add(this.timesyncPanel);

	this.currentController = this.timesyncController;
    }

    @FXML
    void createGENERIC(ActionEvent event) {
	this.messageBorderPane.getChildren().clear();
	this.messageBorderPane.getChildren().add(this.genericPanel);

	this.currentController = this.genericController;
    }

    @FXML
    void sendMessage(ActionEvent event) {

	try {
	    this.communicator.sendSEDAPExpressMessage(this.currentController.createMessage(null, null, null, null, null, null));
	} catch (IOException e) {
	}
    }

    @FXML
    void copyToClipboard(ActionEvent event) {

	Short number;
	if (SEDAPExpressMessage.matchesPattern(SEDAPExpressMessage.NUMBER_MATCHER, this.numberTextField.getText())) {
	    number = Short.parseShort(this.numberTextField.getText());
	} else {
	    number = null;
	}
	Long time;
	if (SEDAPExpressMessage.matchesPattern(SEDAPExpressMessage.POS_INTEGER_MATCHER, this.timeTextField.getText())) {
	    time = Long.parseLong(this.timeTextField.getText());
	} else {
	    time = null;
	}

	SEDAPExpressMessage message = this.currentController.createMessage(number, time, this.senderTextField.getText(), this.classificationComboBox.getSelectionModel().getSelectedItem(),
		this.acknowledgmentComboBox.getSelectionModel().getSelectedItem(), this.macTextField.getText());

	System.out.println(message);
    }

    private GridPane ownunitPanel;
    private GridPane contactPanel;
    private GridPane emissionPanel;
    private GridPane meteoPanel;
    private GridPane textPanel;
    private GridPane graphicPanel;
    private GridPane commandPanel;
    private GridPane statusPanel;
    private GridPane acknowledgePanel;
    private GridPane resendPanel;
    private GridPane genericPanel;
    private GridPane heartbeatPanel;
    private GridPane timesyncPanel;
    private GridPane keyexchangePanel;

    private OWNUNITPanelController ownunitController;
    private CONTACTPanelController contactController;
    private EMISSIONPanelController emissionController;
    private METEOPanelController meteoController;
    private TEXTPanelController textController;
    private GRAPHICPanelController graphicController;
    private COMMANDPanelController commandController;
    private STATUSPanelController statusController;
    private ACKNOWLEDGEPanelController acknowledgeController;
    private RESENDPanelController resendController;
    private GENERICPanelController genericController;
    private HEARTBEATPanelController heartbeatController;
    private TIMESYNCPanelController timesyncController;
    private KEYEXCHANGEPanelController keyexchangeController;

    private MessagePanelController currentController;

    @FXML
    void initialize() {
	assert this.authenticationCheckBox != null : "fx:id=\"authenticationCheckBox\" was not injected: check your FXML file 'MessageTool.fxml'.";
	assert this.connectButton != null : "fx:id=\"connectButton\" was not injected: check your FXML file 'MessageTool.fxml'.";
	assert this.disconnectButton != null : "fx:id=\"disconnectButton\" was not injected: check your FXML file 'MessageTool.fxml'.";
	assert this.encryptedCheckBox != null : "fx:id=\"encryptedCheckBox\" was not injected: check your FXML file 'MessageTool.fxml'.";
	assert this.inputLogTextArea != null : "fx:id=\"inputLogTextArea\" was not injected: check your FXML file 'MessageTool.fxml'.";
	assert this.ipTextField != null : "fx:id=\"ipTextField\" was not injected: check your FXML file 'MessageTool.fxml'.";
	assert this.keyTextField != null : "fx:id=\"keyTextField\" was not injected: check your FXML file 'MessageTool.fxml'.";
	assert this.messageBorderPane != null : "fx:id=\"messageBorderPane\" was not injected: check your FXML file 'MessageTool.fxml'.";
	assert this.outputLogTextArea != null : "fx:id=\"outputLogTextArea\" was not injected: check your FXML file 'MessageTool.fxml'.";
	assert this.portTextField != null : "fx:id=\"portTextField\" was not injected: check your FXML file 'MessageTool.fxml'.";
	assert this.protobufCheckBox != null : "fx:id=\"protobufCheckBox\" was not injected: check your FXML file 'MessageTool.fxml'.";

	this.classificationComboBox.setItems(FXCollections.observableList(Arrays.asList(Classification.values())));
	this.classificationComboBox.getSelectionModel().select(1);
	this.acknowledgmentComboBox.setItems(FXCollections.observableList(Arrays.asList(Acknowledgement.values())));
	this.acknowledgmentComboBox.getSelectionModel().select(0);

	this.timeTextField.disableProperty().bind(this.autoTimeCheckBox.selectedProperty().not());
	new Thread(() -> {
	    while (true) {
		if (this.autoTimeCheckBox.isSelected()) {
		    Platform.runLater(() -> {
			this.timeTextField.setText(String.valueOf(System.currentTimeMillis()));
		    });
		}
		try {
		    Thread.sleep(1000);
		} catch (InterruptedException e) {
		}
	    }
	}).start();

	new Thread(() -> {
	    while (true) {
		if ((this.currentController != null) && this.currentController.isValidFilled()) {
		    this.copyClipboardButton.setDisable(false);
		    this.sendMessageButton.setDisable(false);
		} else {
		    this.copyClipboardButton.setDisable(true);
		    this.sendMessageButton.setDisable(true);
		}
		try {
		    Thread.sleep(1000);
		} catch (InterruptedException e) {
		}
	    }
	}).start();

	try

	{

	    FXMLLoader loader = new FXMLLoader(getClass().getResource("panels/OWNUNITPanel.fxml"));
	    this.ownunitController = new OWNUNITPanelController();
	    loader.setController(this.ownunitController);
	    this.ownunitPanel = loader.load();

	    loader = new FXMLLoader(getClass().getResource("panels/CONTACTPanel.fxml"));
	    this.contactController = new CONTACTPanelController();
	    loader.setController(this.contactController);
	    this.contactPanel = loader.load();

	    loader = new FXMLLoader(getClass().getResource("panels/EMISSIONPanel.fxml"));
	    this.emissionController = new EMISSIONPanelController();
	    loader.setController(this.emissionController);
	    this.emissionPanel = loader.load();

	    loader = new FXMLLoader(getClass().getResource("panels/METEOPanel.fxml"));
	    this.meteoController = new METEOPanelController();
	    loader.setController(this.meteoController);
	    this.meteoPanel = loader.load();

	    loader = new FXMLLoader(getClass().getResource("panels/TEXTPanel.fxml"));
	    this.textController = new TEXTPanelController();
	    loader.setController(this.textController);
	    this.textPanel = loader.load();

	    loader = new FXMLLoader(getClass().getResource("panels/COMMANDPanel.fxml"));
	    this.commandController = new COMMANDPanelController();
	    loader.setController(this.commandController);
	    this.commandPanel = loader.load();

	    loader = new FXMLLoader(getClass().getResource("panels/STATUSPanel.fxml"));
	    this.statusController = new STATUSPanelController();
	    loader.setController(this.statusController);
	    this.statusPanel = loader.load();

	    loader = new FXMLLoader(getClass().getResource("panels/GRAPHICPanel.fxml"));
	    this.graphicController = new GRAPHICPanelController();
	    loader.setController(this.graphicController);
	    this.graphicPanel = loader.load();

	    loader = new FXMLLoader(getClass().getResource("panels/KEYEXCHANGEPanel.fxml"));
	    this.keyexchangeController = new KEYEXCHANGEPanelController();
	    loader.setController(this.keyexchangeController);
	    this.keyexchangePanel = loader.load();

	    loader = new FXMLLoader(getClass().getResource("panels/ACKNOWLEDGEPanel.fxml"));
	    this.acknowledgeController = new ACKNOWLEDGEPanelController();
	    loader.setController(this.acknowledgeController);
	    this.acknowledgePanel = loader.load();

	    loader = new FXMLLoader(getClass().getResource("panels/RESENDPanel.fxml"));
	    this.resendController = new RESENDPanelController();
	    loader.setController(this.resendController);
	    this.resendPanel = loader.load();

	    loader = new FXMLLoader(getClass().getResource("panels/HEARTBEATPanel.fxml"));
	    this.heartbeatController = new HEARTBEATPanelController();
	    loader.setController(this.heartbeatController);
	    this.heartbeatPanel = loader.load();

	    loader = new FXMLLoader(getClass().getResource("panels/HEARTBEATPanel.fxml"));
	    this.timesyncController = new TIMESYNCPanelController();
	    loader.setController(this.timesyncController);
	    this.timesyncPanel = loader.load();

	    loader = new FXMLLoader(getClass().getResource("panels/GENERICPanel.fxml"));
	    this.genericController = new GENERICPanelController();
	    loader.setController(this.genericController);
	    this.genericPanel = loader.load();

	} catch (final Exception ex) {
	    ex.printStackTrace();
	}
    }

    public static void main(String[] args) {

	Application.launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {

	try {
	    FXMLLoader loader = new FXMLLoader(getClass().getResource("MessageTool.fxml"));

	    Parent root = loader.load();
	    Scene scene = new Scene(root);

	    primaryStage.setScene(scene);
	    primaryStage.show();

	} catch (Exception e) {
	    e.printStackTrace();
	}
    }

}
