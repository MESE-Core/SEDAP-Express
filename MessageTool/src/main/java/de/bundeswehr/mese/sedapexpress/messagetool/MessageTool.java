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

import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
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
import de.bundeswehr.mese.sedapexpress.network.SEDAPExpressCommunicator;
import de.bundeswehr.mese.sedapexpress.network.SEDAPExpressTCPClient;
import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.control.Spinner;
import javafx.scene.control.SpinnerValueFactory;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.Tooltip;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;
import javafx.util.Callback;

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
    private Label labelMessage;

    @FXML
    private TextField numberTextField;

    @FXML
    private Label timeLabel;

    @FXML
    private TextField timeTextField;

    @FXML
    private CheckBox autoTimeCheckBox;

    @FXML
    private DatePicker datePicker;
    
    @FXML
    private Spinner<Integer> hourSpinner;
    
    @FXML
    private Spinner<Integer> minuteSpinner;
    
    @FXML
    private Spinner<Integer> secondSpinner;
    
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

    @FXML
    private HBox hBoxTime;
    
    private SEDAPExpressCommunicator communicator;
    
    private static Stage mainStage;

    @FXML
    void connectToSEDAPExpress(ActionEvent event) {

	this.communicator = new SEDAPExpressTCPClient(this.ipTextField.getText(),
		Integer.parseInt(this.portTextField.getText()));

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
	this.labelMessage.setText("OWNUNIT");
    }

    @FXML
    void createCONTACT(ActionEvent event) {
	this.messageBorderPane.getChildren().clear();
	this.messageBorderPane.getChildren().add(this.contactPanel);

	this.currentController = this.contactController;
	this.labelMessage.setText("CONTACT");
    }

    @FXML
    void createEMISSION(ActionEvent event) {
	this.messageBorderPane.getChildren().clear();
	this.messageBorderPane.getChildren().add(this.emissionPanel);

	this.currentController = this.emissionController;
	this.labelMessage.setText("EMISSION");
    }

    @FXML
    void createMETEO(ActionEvent event) {
	this.messageBorderPane.getChildren().clear();
	this.messageBorderPane.getChildren().add(this.meteoPanel);

	this.currentController = this.meteoController;
	this.labelMessage.setText("METEO");
    }

    @FXML
    void createTEXT(ActionEvent event) {
	this.messageBorderPane.getChildren().clear();
	this.messageBorderPane.getChildren().add(this.textPanel);

	this.currentController = this.textController;
	this.labelMessage.setText("TEXT");
    }

    @FXML
    void createCOMMAND(ActionEvent event) {
	this.messageBorderPane.getChildren().clear();
	this.messageBorderPane.getChildren().add(this.commandPanel);

	this.currentController = this.commandController;
	this.labelMessage.setText("COMMAND");
    }

    @FXML
    void createSTATUS(ActionEvent event) {
	this.messageBorderPane.getChildren().clear();
	this.messageBorderPane.getChildren().add(this.statusPanel);

	this.currentController = this.statusController;
	this.labelMessage.setText("STATUS");
    }

    @FXML
    void createGRAPHIC(ActionEvent event) {
	this.messageBorderPane.getChildren().clear();
	this.messageBorderPane.getChildren().add(this.graphicPanel);

	this.currentController = this.graphicController;
	this.labelMessage.setText("GRAPHIC");
    }

    @FXML
    void createKEYEXCHANGE(ActionEvent event) {
	this.messageBorderPane.getChildren().clear();
	this.messageBorderPane.getChildren().add(this.keyexchangePanel);

	this.currentController = this.keyexchangeController;
	this.labelMessage.setText("KEYEXCHANGE");
    }

    @FXML
    void createACKNOWLEDGE(ActionEvent event) {
	this.messageBorderPane.getChildren().clear();
	this.messageBorderPane.getChildren().add(this.acknowledgePanel);

	this.currentController = this.acknowledgeController;
	this.labelMessage.setText("ACKNOWLEDGE");
    }

    @FXML
    void createRESEND(ActionEvent event) {
	this.messageBorderPane.getChildren().clear();
	this.messageBorderPane.getChildren().add(this.resendPanel);

	this.currentController = this.resendController;
	this.labelMessage.setText("RESEND");
    }

    @FXML
    void createHEARTBEAT(ActionEvent event) {
	this.messageBorderPane.getChildren().clear();
	this.messageBorderPane.getChildren().add(this.heartbeatPanel);

	this.currentController = this.heartbeatController;
	this.labelMessage.setText("HEARTBEAT");
    }

    @FXML
    void createGENERIC(ActionEvent event) {
	this.messageBorderPane.getChildren().clear();
	this.messageBorderPane.getChildren().add(this.genericPanel);

	this.currentController = this.genericController;
	this.labelMessage.setText("GENERIC");
    }

    @FXML
    void sendMessage(ActionEvent event) {

    }

    @FXML
    void copyToClipboard(ActionEvent event) {

	Short number;
	if (SEDAPExpressMessage.matchesPattern(SEDAPExpressMessage.NUMBER_MATCHER, this.numberTextField.getText())) {
	    number = Short.parseShort(this.numberTextField.getText(),16);
	} else {
	    number = null;
	}
	Long time;
	if (SEDAPExpressMessage.matchesPattern(SEDAPExpressMessage.POS_INTEGER_MATCHER, this.timeTextField.getText())) {
	    time = Long.parseLong(this.timeTextField.getText());
	} else {
	    time = null;
	}

	SEDAPExpressMessage message = this.currentController
		.createMessage(
			       number,
			       time,
			       this.senderTextField.getText(),
			       this.classificationComboBox.getSelectionModel().getSelectedItem(),
			       this.acknowledgmentComboBox.getSelectionModel().getSelectedItem(),
			       this.macTextField.getText());
				
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
    private KEYEXCHANGEPanelController keyexchangeController;
    
    private int hours = 0;
    private int minutes = 0;
    private int seconds = 0;
    long messageSendTime = 0;
    LocalDate selectedDate;
    Instant instant;
    
    
    // FrequencAgility
    public static final int FREQAGIL_STABLEFIXED = 0;
    public static final int FREQAGIL_AGILE = 1;
    public static final int FREQAGIL_PERIODIC = 2;
    public static final int FREQAGIL_HOPPER = 3;
    public static final int FREQAGIL_BATCHHOPPER = 4;
    public static final int FREQAGIL_UNKNWON = 5;

    public static final List<String> frequAgilityList = new ArrayList<>(Arrays
	    .asList(
		    "Stable fixed",
		    "Agile",
		    "Pereodic",
		    "Hopper",
		    "Batch hopper",
		    "unknown"));

    public static final int getFrequAgility(String ident) {
    	return switch (ident) {
 	    case "Stable fixed" -> FREQAGIL_STABLEFIXED;
 	    case "Agile" -> FREQAGIL_AGILE;
 	    case "Periodic" -> FREQAGIL_PERIODIC;
 	    case "Hopper" -> FREQAGIL_HOPPER;
 	    case "Batch hopper" -> FREQAGIL_BATCHHOPPER;
 	    case "unknown" -> FREQAGIL_UNKNWON;
 	    default -> FREQAGIL_UNKNWON;
 	    };
    }
    
    // PRFAgility
    public static final int PRFAGIL_FIXEDPERIODIC = 0;
    public static final int PRFAGIL_STAGGERED = 1;
    public static final int PRFAGIL_JITTERED = 2;
    public static final int PRFAGIL_WOBBULATED = 3;
    public static final int PRFAGIL_SLIDING = 4;
    public static final int PRFAGIL_DWELLSWITCH = 5;
    public static final int PRFAGIL_UNKNOWNPRF = 6;
    public static final int PRFAGIL_CW = 7;
    
    public static final List<String> prfAgilityList = new ArrayList<>(Arrays
	    .asList(
		    "Fixed periodic",
		    "Staggered",
		    "Jittered",
		    "Wobbulated",
		    "Sliding",
		    "Dwell switch",
		    "unknown PRF",
		    "CW"));

    public static final int getPrfAgility(String ident) {
    	return switch (ident) {
 	    case "Fixed periodic" -> PRFAGIL_FIXEDPERIODIC;
 	    case "Staggered" -> PRFAGIL_STAGGERED;
 	    case "Jittered" -> PRFAGIL_JITTERED;
 	    case "Wobbulated" -> PRFAGIL_WOBBULATED;
 	    case "Sliding" -> PRFAGIL_SLIDING;
 	    case "Dwell switch" -> PRFAGIL_DWELLSWITCH;
 	    case "unknown PRF" -> PRFAGIL_UNKNOWNPRF;
 	    case "CW" -> PRFAGIL_CW;
 	    default -> PRFAGIL_UNKNOWNPRF;
 	    };
    }
    
    
    // Content Type
    public static final List<String> contentTypeList = new ArrayList<>(Arrays
    	    .asList(
    		    "SEDAP",
    		    "ASCII",
    		    "NMEA",
    		    "XML",
    		    "JSON",
    		    "BINARY"));
    
   
    public static final int FUNCTION_Unknown = 0;
    public static final int FUNCTION_ESM_Beacon_Transponder = 1;
    public static final int FUNCTION_ESM_Navigation = 2;
    public static final int FUNCTION_ESM_Voice_Communication = 3;
    public static final int FUNCTION_ESM_Data_Communication = 4;
    public static final int FUNCTION_ESM_Radar = 5;
    public static final int FUNCTION_ESM_Iff = 6;
    public static final int FUNCTION_ESM_Guidance = 7;
    public static final int FUNCTION_ESM_Weapon = 8;
    public static final int FUNCTION_ESM_Jammer = 9;
    public static final int FUNCTION_ESM_Natural = 10;
    public static final int FUNCTION_ACOUSTIC_Object = 11;
    public static final int FUNCTION_ACOUSTIC_Submarine = 12;
    public static final int FUNCTION_ACOUSTIC_Variable_Depth_Sonar = 13;
    public static final int FUNCTION_ACOUSTIC_Array_Sonar = 14;
    public static final int FUNCTION_ACOUSTIC_Active_Sonar = 15;
    public static final int FUNCTION_ACOUSTIC_Torpedo_Sonar = 16;
    public static final int FUNCTION_ACOUSTIC_Sono_Buoy = 17;
    public static final int FUNCTION_ACOUSTIC_Decoy_Signal = 18;
    public static final int FUNCTION_ACOUSTIC_Hit_Noise = 19;
    public static final int FUNCTION_ACOUSTIC_Propeller_Noise = 20;
    public static final int FUNCTION_ACOUSTIC_Underwater_Telephone = 21;
    public static final int FUNCTION_ACOUSTIC_Communication = 22;
    public static final int FUNCTION_ACOUSTIC_Noise = 23;
    public static final int FUNCTION_LASER_Range_Finder = 24;
    public static final int FUNCTION_LASER_Designator = 25;
    public static final int FUNCTION_LASER_Beam_Rider = 26;
    public static final int FUNCTION_LASER_Dazzler = 27;
    public static final int FUNCTION_LASER_Lidar = 28;

    
    
    public static final int getFunction(String ident) {
    	return switch (ident) {
    	case "Unknown" -> FUNCTION_Unknown;
    	case "ESM Beacon/Transponder" -> FUNCTION_ESM_Beacon_Transponder;
    	case "ESM Navigation" -> FUNCTION_ESM_Navigation;
    	case "ESM Voice Communication" -> FUNCTION_ESM_Voice_Communication;
    	case "ESM Data Communication" -> FUNCTION_ESM_Data_Communication;
    	case "ESM Radar" -> FUNCTION_ESM_Radar;
    	case "ESM IFF/ADS-B" -> FUNCTION_ESM_Iff;
    	case "ESM Guidance" -> FUNCTION_ESM_Guidance;
    	case "ESM Weapon" -> FUNCTION_ESM_Weapon;
    	case "ESM Jammer" -> FUNCTION_ESM_Jammer;
    	case "ESM Natural" -> FUNCTION_ESM_Natural;
    	case "ACOUSTIC Object" -> FUNCTION_ACOUSTIC_Object;
    	case "ACOUSTIC Submarine" -> FUNCTION_ACOUSTIC_Submarine;
    	case "ACOUSTIC Variable Depth Sonar" -> FUNCTION_ACOUSTIC_Variable_Depth_Sonar;
    	case "ACOUSTIC Array Sonar" -> FUNCTION_ACOUSTIC_Array_Sonar;
    	case "ACOUSTIC Active Sonar" -> FUNCTION_ACOUSTIC_Active_Sonar;
    	case "ACOUSTIC Torpedo Sonar" -> FUNCTION_ACOUSTIC_Torpedo_Sonar;
    	case "ACOUSTIC Sono Buoy" -> FUNCTION_ACOUSTIC_Sono_Buoy;
    	case "ACOUSTIC Decoy Signal" -> FUNCTION_ACOUSTIC_Decoy_Signal;
    	case "ACOUSTIC Hit Noise" -> FUNCTION_ACOUSTIC_Hit_Noise;
    	case "ACOUSTIC Propeller Noise" -> FUNCTION_ACOUSTIC_Propeller_Noise;
    	case "ACOUSTIC Underwater Telephone" -> FUNCTION_ACOUSTIC_Underwater_Telephone;
    	case "ACOUSTIC Communication" -> FUNCTION_ACOUSTIC_Communication;
    	case "ACOUSTIC Noise" -> FUNCTION_ACOUSTIC_Noise;
    	case "LASER Range Finder" -> FUNCTION_LASER_Range_Finder;
    	case "LASER Designator" -> FUNCTION_LASER_Designator;
    	case "LASER Beam Rider" -> FUNCTION_LASER_Beam_Rider;
    	case "LASER Dazzler" -> FUNCTION_LASER_Dazzler;
    	case "LASER Lidar" -> FUNCTION_LASER_Lidar;
 	    default -> FUNCTION_Unknown;
 	    };
    }
    public static final List<String> FunctionList = new ArrayList<>(Arrays
    	    .asList(
    	    		"Unknown",
    	    		"ESM Beacon/Transponder",
    	    		"ESM Navigation",
    	    		"ESM Voice Communication",
    	    		"ESM Data Communication",
    	    		"ESM Radar",
    	    		"ESM IFF/ADS-B",
    	    		"ESM Guidance",
    	    		"ESM Weapon",
    	    		"ESM Jammer",
    	    		"ESM Natural",
    	    		"ACOUSTIC Object",
    	    		"ACOUSTIC Submarine",
    	    		"ACOUSTIC Variable Depth Sonar",
    	    		"ACOUSTIC Array Sonar",
    	    		"ACOUSTIC Active Sonar",
    	    		"ACOUSTIC Torpedo Sonar",
    	    		"ACOUSTIC Sono Buoy",
    	    		"ACOUSTIC Decoy Signal",
    	    		"ACOUSTIC Hit Noise",
    	    		"ACOUSTIC Propeller Noise",
    	    		"ACOUSTIC Underwater Telephone",
    	    		"ACOUSTIC Communication",
    	    		"ACOUSTIC Noise",
    	    		"LASER Range Finder",
    	    		"LASER Designator",
    	    		"LASER Beam Rider",
    	    		"LASER Dazzler",
    	    		"LASER Lidar"));
    
    private MessagePanelController currentController;

    @FXML
    void initialize() {
	assert this.authenticationCheckBox != null : "fx:id=\"authenticationCheckBox\" was not injected: check your FXML file 'MessageTool.fxml'.";
	assert autoTimeCheckBox != null : "fx:id=\"autoTimeCheckBox\" was not injected: check your FXML file 'MessageTool.fxml'.";
	assert this.connectButton != null : "fx:id=\"connectButton\" was not injected: check your FXML file 'MessageTool.fxml'.";
	assert this.disconnectButton != null : "fx:id=\"disconnectButton\" was not injected: check your FXML file 'MessageTool.fxml'.";
	assert this.encryptedCheckBox != null : "fx:id=\"encryptedCheckBox\" was not injected: check your FXML file 'MessageTool.fxml'.";
    assert hBoxTime != null : "fx:id=\"hBoxTime\" was not injected: check your FXML file 'MessageTool.fxml'.";
	assert this.inputLogTextArea != null : "fx:id=\"inputLogTextArea\" was not injected: check your FXML file 'MessageTool.fxml'.";
	assert this.ipTextField != null : "fx:id=\"ipTextField\" was not injected: check your FXML file 'MessageTool.fxml'.";
	assert this.keyTextField != null : "fx:id=\"keyTextField\" was not injected: check your FXML file 'MessageTool.fxml'.";
	assert macTextField != null : "fx:id=\"macTextField\" was not injected: check your FXML file 'MessageTool.fxml'.";
	assert this.messageBorderPane != null : "fx:id=\"messageBorderPane\" was not injected: check your FXML file 'MessageTool.fxml'.";
	assert labelMessage != null : "fx:id=\"labelMessage\" was not injected: check your FXML file 'MessageTool.fxml'.";
	assert this.outputLogTextArea != null : "fx:id=\"outputLogTextArea\" was not injected: check your FXML file 'MessageTool.fxml'.";
	assert this.portTextField != null : "fx:id=\"portTextField\" was not injected: check your FXML file 'MessageTool.fxml'.";
	assert this.protobufCheckBox != null : "fx:id=\"protobufCheckBox\" was not injected: check your FXML file 'MessageTool.fxml'.";
	assert senderTextField != null : "fx:id=\"senderTextField\" was not injected: check your FXML file 'MessageTool.fxml'.";
	assert datePicker != null : "fx:id=\"datePicker\" was not injected: check your FXML file 'MessageTool.fxml'.";
	assert hourSpinner != null : "fx:id=\"hourSpinner\" was not injected: check your FXML file 'MessageTool.fxml'.";
	assert minuteSpinner != null : "fx:id=\"minuteSpinner\" was not injected: check your FXML file 'MessageTool.fxml'.";  
	assert secondSpinner != null : "fx:id=\"secondSpinner\" was not injected: check your FXML file 'MessageTool.fxml'.";
	
	Tooltip tooltipNumber = new Tooltip("This is a hexadecimal string (00 - 7F) representation of a 7-bit sequential number. Each type of message has its own \r\n"
			+ "counter that starts again with zero after reaching 127/7F. A reconnect does not resets the counters! The \r\n"
			+ "limitation to 7-bit or a max of 127 prevents problems with signed/unsigned byte formats");
	Tooltip tooltipTime = new Tooltip("A hexadecimal string representation of a 64-bit Unix time stamp with milliseconds");
	
	Tooltip tooltipSender = new Tooltip("In most cases, you should use a hexadecimal string representation of a 16-bit unsigned integer, but you can \r\n"
			+ "also use freely selected textual identifiers.\r\n"
			+ "This field won’t be changed, even if a message has been forwarded or relayed. This sender identification can \r\n"
			+ "be chosen randomly by the participants themselves or permanently assigned by a responsible institution when \r\n"
			+ "preparing a specific use/network. If information of a sub-system has to be forwarded the sender identification \r\n"
			+ "should be the source of the original information, in that case of the sub-system");
	
	Tooltip tooltipClasification = new Tooltip("Describes the classification or security level of the content. Possible values are P=public, U=unclassified,\r\n"
			+ "R=restricted, C=confidential, S=secret, T=top secret\r\n"
			+ "");
	Tooltip tooltipAcknowledgement = new Tooltip("TRUE=request an acknowledgement, FALSE/Nothing=No acknowledgement");
	Tooltip tooltipMac = new Tooltip("A 32/128/256-bit wide hash-based message authentication code for verification");
	
	numberTextField.setTooltip(tooltipNumber);
	timeTextField.setTooltip(tooltipTime);
	senderTextField.setTooltip(tooltipSender);
	classificationComboBox.setTooltip(tooltipClasification);
	acknowledgmentComboBox.setTooltip(tooltipAcknowledgement);
	macTextField.setTooltip(tooltipMac);
	
	numberTextField.textProperty().addListener((observable, oldValue,newValue) -> {
		if(!newValue.equals("")) {
			if (!newValue.equals(newValue.toUpperCase())) {
				numberTextField.setText(newValue.toUpperCase());
		    }
			if(MessagePanelController.isValid7BitHex(newValue)== true) {
				numberLabel.setVisible(false);
			}else {
				numberLabel.setVisible(true);	
			}
		}else{
			numberTextField.setText("");
			numberLabel.setVisible(true);	
		}});
			
	senderLabel.setVisible(false);
	macLabel.setVisible(false);
	//numberTextField.setTextFormatter(MessagePanelController.createHexFormatter());
	/*
	senderTextField.textProperty().addListener((observable, oldValue,newValue) -> {
		if(!newValue.equals("")) {
			senderLabel.setVisible(false);
			if (!newValue.equals(newValue)) {
				senderTextField.setText(newValue);
		    	}		
			}else {
				senderLabel.setVisible(true);
			}
		});
		*/
	//TODO: Validator für MAC
	/*
	macTextField.textProperty().addListener((observable, oldValue,newValue) -> {
		if(!newValue.equals("")) {
			macLabel.setVisible(false);
			if (!newValue.equals(newValue)) {
				macTextField.setText(newValue);
		    	}		
			}else{
				macLabel.setVisible(true);
			}
		});
	*/
	datePicker.valueProperty().addListener((obs, oldValue, newValue) -> {
         if (newValue == null) {
        	 
         } else {
        	 this.selectedDate = newValue;
         }
         generateTime();
     });
	
	
	hourSpinner.valueProperty().addListener((obs, oldValue, newValue) -> {
         if (newValue == null) {
        	 hourSpinner.getValueFactory().setValue(oldValue);
         } else if (newValue < 0) {
        	 hourSpinner.getValueFactory().setValue(0);
         } else if (newValue > 23) {
        	 hourSpinner.getValueFactory().setValue(23);
         }
         this.hours = hourSpinner.getValue();
         generateTime();
     });
	
	minuteSpinner.valueProperty().addListener((obs, oldValue, newValue) -> {
        if (newValue == null) {
        	minuteSpinner.getValueFactory().setValue(oldValue);
        } else if (newValue < 0) {
        	minuteSpinner.getValueFactory().setValue(0);
        } else if (newValue > 59) {
        	minuteSpinner.getValueFactory().setValue(59);
        }
        this.minutes=minuteSpinner.getValue();
        generateTime();
    });
	
	secondSpinner.valueProperty().addListener((obs, oldValue, newValue) -> {
        if (newValue == null) {
        	secondSpinner.getValueFactory().setValue(oldValue);
        } else if (newValue < 0) {
        	secondSpinner.getValueFactory().setValue(0);
        } else if (newValue > 59) {
        	secondSpinner.getValueFactory().setValue(59);
        }
        this.seconds=secondSpinner.getValue();
        generateTime();
    });
	
	autoTimeCheckBox.setOnAction(event -> {
		if(autoTimeCheckBox.isSelected()) {
			this.timeLabel.setVisible(false);
		}else {
			if(this.messageSendTime == 0) {
    			this.timeLabel.setVisible(true);
    			this.timeTextField.setText("0");
    		}else {
    			this.timeLabel.setVisible(false);
    			generateTime();
    		}
		}
    });
	
	SpinnerValueFactory<Integer> valueFactoryHour = new SpinnerValueFactory.IntegerSpinnerValueFactory(0, 23,0);
	SpinnerValueFactory<Integer> valueFactoryMinute = new SpinnerValueFactory.IntegerSpinnerValueFactory(0, 59,0);
	SpinnerValueFactory<Integer> valueFactorySecond = new SpinnerValueFactory.IntegerSpinnerValueFactory(0, 59,0);
	
	valueFactoryHour.setWrapAround(true);
	valueFactoryMinute.setWrapAround(true);
	valueFactorySecond.setWrapAround(true);
	
	hourSpinner.setValueFactory(valueFactoryHour);
	minuteSpinner.setValueFactory(valueFactoryMinute);
	secondSpinner.setValueFactory(valueFactorySecond);
	        
      
	this.classificationComboBox.setItems(FXCollections.observableArrayList(Classification.values()));
	this.classificationComboBox.setCellFactory(new Callback<ListView<Classification>,ListCell<Classification>>(){
		@Override
		public ListCell<Classification> call (ListView<Classification> l){
			return new ListCell<Classification>() {
				@Override
				protected void updateItem(Classification item, boolean empty) {
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
	this.classificationComboBox.setButtonCell(new ListCell<>() {
	    protected void updateItem(Classification item, boolean empty) {
	    	super.updateItem(item, empty);
	    	if (item == null || empty) {
	    		setGraphic(null);
	    	} else {
	    		setText(item.name());
	    	}
	    }
	});
	
	this.classificationComboBox.getSelectionModel().select(1);
	this.acknowledgmentComboBox.setItems(FXCollections.observableArrayList(Acknowledgement.values()));
	this.acknowledgmentComboBox.getSelectionModel().select(0);
	
	this.timeLabel.setVisible(false);
	//this.timeTextField.disableProperty().bind(this.autoTimeCheckBox.selectedProperty().not());
	
	new Thread(() -> {
		try {
    		Thread.sleep(500);
    	} catch (InterruptedException e) {
    	}
		while (true) {
	    	
	    	if (this.autoTimeCheckBox.isSelected()) {
	    		this.timeTextField.setText(String.valueOf(System.currentTimeMillis()));	
	    	}
	    	try {
	    		Thread.sleep(1000);
	    	} catch (InterruptedException e) {
	    	}
	    	}
		}).start();

	
	new Thread(() -> {
	    while (true) {
	    	
	    	if ((this.currentController != null) && this.currentController.isValidFilled()&& this.isValidFilled()) {
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

	    loader = new FXMLLoader(getClass().getResource("panels/GENERICPanel.fxml"));
	    this.genericController = new GENERICPanelController();
	    loader.setController(this.genericController);
	    this.genericPanel = loader.load();

	} catch (final Exception ex) {
	    ex.printStackTrace();
	}
    }
    //TextFormatter<Integer> textFormatter = new TextFormatter<>(MessagePanelController.hexConverter, null, MessagePanelController.hexFilter);
    public static void main(String[] args) {

	Application.launch(args);
//	COMMAND command = new COMMAND(this.numberCOMMAND,
//		System.currentTimeMillis(),
//		this.senderId,
//		SEDAPExpressMessage.RESTRICTED,
//		SEDAPExpressMessage.ACKNOWLEDGE_YES,
//		null,
//		"7D31",
//		COMMAND.CMDTYPE_SYNC_TIME,
//		Arrays.asList("10.8.0.6"));
//
//	this.communicator.sendSEDAPExpressMessage(command);
//
//	if (this.numberSTATUS++ == 255) {
//	    this.numberSTATUS = 0;
//	}

//	STATUS status = new STATUS(this.numberSTATUS,
//		System.currentTimeMillis(),
//		this.senderId,
//		SEDAPExpressMessage.CONFIDENTIAL,
//		SEDAPExpressMessage.ACKNOWLEDGE_NO,
//		null,
//		STATUS.TECSTATUS_OPERATIONAL,
//		STATUS.OPSSTATUS_OPERATIONAL,
//		50.0,
//		75.3,
//		10.8,
//		"10.8.0.6",
//		Arrays.asList("rtsp://10.8.0.6/stream1", "rtsp://10.8.0.6/stream2"),
//		"This is a sample!");

//	this.communicator.sendSEDAPExpressMessage(status);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
    	MessageTool.mainStage = primaryStage;
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
    
    private void generateTime() {
    	if(selectedDate != null) {
    		LocalDateTime dateTime = LocalDateTime.of(selectedDate.getYear(), selectedDate.getMonth(), selectedDate.getDayOfMonth(),
                this.hours, this.minutes, this.seconds);
    		this.messageSendTime = (dateTime.toEpochSecond(ZoneOffset.UTC)*1000);
    	
    		if (!this.autoTimeCheckBox.isSelected()) {
	    		this.timeTextField.setText(String.valueOf(this.messageSendTime));
	    		this.timeLabel.setVisible(false);
    		}
    	}
    }
    
    private boolean isValidFilled() {
    	return (!numberLabel.isVisible() && !timeLabel.isVisible());
    }

    public static Stage getMainStage(){
				return mainStage;
			}
}
