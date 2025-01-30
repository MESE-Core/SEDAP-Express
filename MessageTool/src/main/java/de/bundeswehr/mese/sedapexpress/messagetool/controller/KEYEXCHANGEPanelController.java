package de.bundeswehr.mese.sedapexpress.messagetool.controller;

import java.math.BigInteger;
import java.security.PublicKey;

import javax.crypto.SecretKey;

import de.bundeswehr.mese.sedapexpress.messages.KEYEXCHANGE;
import de.bundeswehr.mese.sedapexpress.messages.KEYEXCHANGE.AlgorithmType;
import de.bundeswehr.mese.sedapexpress.messages.SEDAPExpressMessage.Acknowledgement;
import de.bundeswehr.mese.sedapexpress.messages.SEDAPExpressMessage.Classification;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.control.Tooltip;
import javafx.util.Callback;

public class KEYEXCHANGEPanelController extends MessagePanelController {

    @FXML
    private ComboBox<AlgorithmType> algorithmComboBox;

    @FXML
    private TextField contactIdTextField;

    @FXML
    private TextField encryptetSecretKeyTextField;

    @FXML
    private ComboBox<Integer> keyLenghtComboBox;

    @FXML
    private TextField naturalNumberTextField;

    @FXML
    private ComboBox<Integer> phaseComboBox;

    @FXML
    private TextField primeTextField;

    @FXML
    private TextField publicKeyTextField;

    @FXML
    void initialize() {
	assert this.algorithmComboBox != null : "fx:id=\"algorithmComboBox\" was not injected: check your FXML file 'KEYEXCHANGEPanel.fxml'.";
	assert this.contactIdTextField != null : "fx:id=\"contactIdTextField\" was not injected: check your FXML file 'KEYEXCHANGEPanel.fxml'.";
	assert this.encryptetSecretKeyTextField != null : "fx:id=\"encryptetSecretKeyTextField\" was not injected: check your FXML file 'KEYEXCHANGEPanel.fxml'.";
	assert this.keyLenghtComboBox != null : "fx:id=\"keyLenghtComboBox\" was not injected: check your FXML file 'KEYEXCHANGEPanel.fxml'.";
	assert this.naturalNumberTextField != null : "fx:id=\"naturalNumberTextField\" was not injected: check your FXML file 'KEYEXCHANGEPanel.fxml'.";
	assert this.phaseComboBox != null : "fx:id=\"phaseComboBox\" was not injected: check your FXML file 'KEYEXCHANGEPanel.fxml'.";
	assert this.primeTextField != null : "fx:id=\"primeTextField\" was not injected: check your FXML file 'KEYEXCHANGEPanel.fxml'.";
	assert this.publicKeyTextField != null : "fx:id=\"publicKeyTextField\" was not injected: check your FXML file 'KEYEXCHANGEPanel.fxml'.";

	// TOOLTIPS
	Tooltip tooltipPhase = new Tooltip("0 = Exchange the public variables and public key (DH only)\n1 = Exchange public key(s) (ECDH and Kyber)\n2 = Shared key successfully generated (MAC should also already generated with that key)");
	Tooltip tooltipKeyLenght = new Tooltip("128-1024 Bit Length of the key (Phase 0, DH only)");
	Tooltip tooltipPrime = new Tooltip("HexString Publicly known prime number (> 3000 bits / 375 byte) (Phase 0, DH only)");
	Tooltip tooltipNaturalNumbber = new Tooltip("HexString Publicly known natural number smaller than p (Phase 0, DH only)");
	Tooltip tooltipPublicKey = new Tooltip("BASE64 The public key of the sender (Phase 1)");
	Tooltip tooltipEncrypytedSecretKey = new Tooltip("BASE64 The shared secret encrypted with public key of the recipient (Kyber only)");

	this.phaseComboBox.setTooltip(tooltipPhase);
	this.keyLenghtComboBox.setTooltip(tooltipKeyLenght);
	this.primeTextField.setTooltip(tooltipPrime);
	this.naturalNumberTextField.setTooltip(tooltipNaturalNumbber);
	this.publicKeyTextField.setTooltip(tooltipPublicKey);
	this.encryptetSecretKeyTextField.setTooltip(tooltipEncrypytedSecretKey);

	// AlgorithmComboBox
	this.algorithmComboBox.setItems(FXCollections.observableArrayList(AlgorithmType.values()));
	this.algorithmComboBox.setCellFactory(new Callback<ListView<AlgorithmType>, ListCell<AlgorithmType>>() {
	    @Override
	    public ListCell<AlgorithmType> call(ListView<AlgorithmType> l) {
		return new ListCell<AlgorithmType>() {
		    @Override
		    protected void updateItem(AlgorithmType item, boolean empty) {
			super.updateItem(item, empty);
			if (item == null || empty) {
			    setGraphic(null);
			} else {
			    setText(item.name());
			}
		    }
		};
	    }
	});
	this.algorithmComboBox.setButtonCell(new ListCell<>() {
	    protected void updateItem(AlgorithmType item, boolean empty) {
		super.updateItem(item, empty);
		if (item == null || empty) {
		    setGraphic(null);
		} else {
		    setText(item.name());
		}
	    }
	});
	this.algorithmComboBox.getSelectionModel().select(0);

	this.algorithmComboBox.getSelectionModel().selectedIndexProperty().addListener((observable, oldValue, newValue) -> {
	    // ###################################################### ab hier geht es weiter

	    switch (newValue.intValue()) {
	    case 0:
	    }

	    if (newValue.intValue() <= 1) {
		this.keyLenghtComboBox.setDisable(false);
		this.primeTextField.setDisable(false);
		// this.primeTextField.setText(null);
		this.primeNumber = null;
		this.naturalNumberTextField.setDisable(false);
		// this.naturalNumberTextField.setText(null);
		this.naturalNumber = null;
	    } else {
		this.keyLenghtComboBox.setDisable(true);
		this.primeTextField.setDisable(true);
		this.naturalNumberTextField.setDisable(true);
	    }
	});

	// Phase
	ObservableList<Integer> phase = FXCollections.observableArrayList(0, 1, 2);
	this.phaseComboBox.setItems(phase);
	this.phaseComboBox.getSelectionModel().select(0);

	// KeyLength
	ObservableList<Integer> keyLength = FXCollections.observableArrayList(128, 256);
	this.keyLenghtComboBox.setItems(keyLength);
	this.keyLenghtComboBox.getSelectionModel().select(0);
	// Prime
	this.primeTextField.textProperty().addListener((observable, oldValue, newValue) -> {
	    if (!newValue.isEmpty()) {
		getPrime(this.primeTextField.getText());
	    }
	});
	// Natural Number
	this.naturalNumberTextField.setTextFormatter(MessagePanelController.createHexFormatter());
	this.naturalNumberTextField.textProperty().addListener((observable, oldValue, newValue) -> {
	    if (!newValue.isEmpty()) {
		try {
		    this.primeNumber = new BigInteger(newValue, 16);
		} catch (NumberFormatException e) {
		    System.out.println("Ungültige Eingabe");
		}
	    } else {
		this.primeNumber = null;
	    }
	});

	// Public Key

	// Encrypted Secret Key

    }

    // private AlgorithmType algorithm;
    private Integer phase;

    // private Integer keyLength;
    private BigInteger primeNumber;
    private BigInteger naturalNumber;
    private PublicKey publicKey;
    private SecretKey encryptedKey;

    private void getPrime(String input) {

    }

//   Integer algorithm, Integer phase, Integer keyLength, BigInteger primeNumber, BigInteger naturalNumber, PublicKey publicKey, SecretKey encryptedKey) {
    @Override
    public KEYEXCHANGE createMessage(Short number, Long time, String sender, Classification classification, Acknowledgement acknowledgement, String mac) {

	KEYEXCHANGE keyexchange = new KEYEXCHANGE(number, time, sender, classification, acknowledgement, mac, this.contactIdTextField.getText(), this.algorithmComboBox.getValue(), this.phaseComboBox.getValue(),
		this.keyLenghtComboBox.getValue(), this.primeNumber, this.naturalNumber, this.publicKey, this.encryptedKey);

	return keyexchange;
    }

    @Override
    public boolean isValidFilled() {

	return true;
    }
}
