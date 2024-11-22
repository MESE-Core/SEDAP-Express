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
package de.bundeswehr.sedap.express.mockup;

import java.awt.Color;
import java.awt.Font;
import java.io.File;
import java.io.IOException;
import java.net.NetworkInterface;
import java.text.SimpleDateFormat;
import java.util.Enumeration;
import java.util.Locale;
import java.util.TimeZone;
import java.util.concurrent.ConcurrentHashMap;

import de.bundeswehr.mese.sedapexpress.controls.SEDAPExpressLoggingArea;
import de.bundeswehr.mese.sedapexpress.messages.ACKNOWLEDGE;
import de.bundeswehr.mese.sedapexpress.messages.COMMAND;
import de.bundeswehr.mese.sedapexpress.messages.CONTACT;
import de.bundeswehr.mese.sedapexpress.messages.EMISSION;
import de.bundeswehr.mese.sedapexpress.messages.GENERIC;
import de.bundeswehr.mese.sedapexpress.messages.GRAPHIC;
import de.bundeswehr.mese.sedapexpress.messages.HEARTBEAT;
import de.bundeswehr.mese.sedapexpress.messages.KEYEXCHANGE;
import de.bundeswehr.mese.sedapexpress.messages.METEO;
import de.bundeswehr.mese.sedapexpress.messages.OWNUNIT;
import de.bundeswehr.mese.sedapexpress.messages.RESEND;
import de.bundeswehr.mese.sedapexpress.messages.SEDAPExpressMessage;
import de.bundeswehr.mese.sedapexpress.messages.SEDAPExpressMessage.DeleteFlag;
import de.bundeswehr.mese.sedapexpress.messages.SEDAPExpressMessage.MessageType;
import de.bundeswehr.mese.sedapexpress.messages.STATUS;
import de.bundeswehr.mese.sedapexpress.messages.TEXT;
import de.bundeswehr.mese.sedapexpress.messages.TIMESYNC;
import de.bundeswehr.mese.sedapexpress.network.SEDAPExpressCommunicator;
import de.bundeswehr.mese.sedapexpress.network.SEDAPExpressTCPClient;
import de.bundeswehr.mese.sedapexpress.network.SEDAPExpressTCPServer;
import de.bundeswehr.mese.sedapexpress.network.SEDAPExpressUDPClient;
import de.bundeswehr.mese.sedapexpress.processing.SEDAPExpressInputLoggingSubscriber;
import de.bundeswehr.mese.sedapexpress.processing.SEDAPExpressOutputLoggingSubscriber;
import de.bundeswehr.mese.sedapexpress.processing.SEDAPExpressSubscriber;
import gov.nasa.worldwind.Model;
import gov.nasa.worldwind.WorldWind;
import gov.nasa.worldwind.avlist.AVKey;
import gov.nasa.worldwind.awt.WorldWindowGLJPanel;
import gov.nasa.worldwind.formats.shapefile.ShapefileLayerFactory;
import gov.nasa.worldwind.formats.shapefile.ShapefileLayerFactory.CompletionCallback;
import gov.nasa.worldwind.geom.Angle;
import gov.nasa.worldwind.geom.Position;
import gov.nasa.worldwind.layers.Layer;
import gov.nasa.worldwind.layers.RenderableLayer;
import gov.nasa.worldwind.render.BasicShapeAttributes;
import gov.nasa.worldwind.render.Material;
import gov.nasa.worldwind.render.ShapeAttributes;
import gov.nasa.worldwind.symbology.BasicTacticalSymbolAttributes;
import gov.nasa.worldwind.symbology.SymbologyConstants;
import gov.nasa.worldwind.symbology.TacticalSymbolAttributes;
import gov.nasa.worldwind.symbology.milstd2525.MilStd2525TacticalSymbol;
import gov.nasa.worldwind.view.orbit.BasicOrbitView;
import gov.nasa.worldwind.view.orbit.BasicOrbitViewLimits;
import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.embed.swing.SwingNode;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.TitledPane;
import javafx.stage.Stage;
import javafx.util.Callback;

public class SECMockUpHMI extends Application implements SEDAPExpressSubscriber, SEDAPExpressInputLoggingSubscriber, SEDAPExpressOutputLoggingSubscriber {

    @FXML
    private Button activateButton;

    @FXML
    private Button deactivateButton;

    @FXML
    private CheckBox authenticationCheckBox;

    @FXML
    private CheckBox encryptedCheckBox;

    @FXML
    private SEDAPExpressLoggingArea inputLoggingArea;

    @FXML
    private SEDAPExpressLoggingArea outputLoggingArea;

    @FXML
    private TextArea keyTextField;

    @FXML
    private CheckBox protobufCheckBox;

    @FXML
    private Button tcpActivateButton;

    @FXML
    private Button tcpDeactivateButton;

    @FXML
    private Button tcpClientActivateButton;

    @FXML
    private Button tcpClientDeactivateButton;

    @FXML
    private TextField tcpClientIPTextField;

    @FXML
    private TextField tcpClientPortTextField;

    @FXML
    private ComboBox<NetworkInterface> tcpInterfaceComboBox;

    @FXML
    private TextField tcpPortTextField;

    @FXML
    private TextField udpIPTextField;

    @FXML
    private TextField udpPortTextField;

    @FXML
    private Button udpActivateButton;

    @FXML
    private Button udpDeactivateButton;

    @FXML
    private TitledPane tcpClientPane;

    @FXML
    private TitledPane tcpPane;

    @FXML
    private TitledPane udpPane;

    @FXML
    private SwingNode mapPane;

    protected WorldWindowGLJPanel wwPanel;

    protected Font labelFont = Font.decode("Verdana-BOLD-12");
    protected Font annotationFont = Font.decode("Verdana-BOLD-12");

    protected Material labelMaterial = Material.MAGENTA;
    protected Color labelColor = Color.magenta;

    protected RenderableLayer contactsLayer = new RenderableLayer();
    protected RenderableLayer emissionsLayer = new RenderableLayer();

    // Some constants
    public static final double nauticalMilesToMeter = 1852d;
    public static final double milesToMeter = 1609.34d;

    public static final double metersPerSecondToKnots = 3600d / SECMockUpHMI.nauticalMilesToMeter;
    public static final double knotsToMetersPerSecond = 1d / SECMockUpHMI.metersPerSecondToKnots;
    public static final double meterToNauticalMiles = 1 / SECMockUpHMI.nauticalMilesToMeter;
    public static final double meterToMiles = 1 / SECMockUpHMI.milesToMeter;

    public static final double meterToCable = 100d / SECMockUpHMI.nauticalMilesToMeter;
    public static final double metersToYards = 1.0936132983377078d;
    public static final double metersToKiloYards = SECMockUpHMI.metersToYards / 1000d;
    public static final double yardsToMeter = 1d / SECMockUpHMI.metersToYards;
    public static final double kiloyardsToMeter = 1d / SECMockUpHMI.metersToKiloYards;

    public static final double nauticalMilesToYards = SECMockUpHMI.nauticalMilesToMeter * SECMockUpHMI.metersToYards;

    public static final double feetToMeter = 1d / SECMockUpHMI.metersToYards / 3;
    public static final double meterToFeet = 1d / SECMockUpHMI.feetToMeter;
    public static final double nauticalMilesToDegrees = 1 / 60d;
    public static final double meterToDegrees = SECMockUpHMI.meterToNauticalMiles * SECMockUpHMI.nauticalMilesToDegrees;
    public static final double flightLevelToMeter = SECMockUpHMI.feetToMeter * 100;
    public static final double meterToFlightLevel = SECMockUpHMI.meterToFeet / 100;

    public static final String standardSIDC = "suup-----------";

    private static SimpleDateFormat sdf = new SimpleDateFormat("ddHHmmLLLyy", Locale.ENGLISH);
    static {
	SECMockUpHMI.sdf.setTimeZone(TimeZone.getTimeZone("UTC"));
    }

    private SEDAPExpressCommunicator communicator;

    @FXML
    void initialize() {
	assert this.authenticationCheckBox != null : "fx:id=\"authenticationCheckBox\" was not injected: check your FXML file 'SECMockUpHMI.fxml'.";
	assert this.encryptedCheckBox != null : "fx:id=\"encryptedCheckBox\" was not injected: check your FXML file 'SECMockUpHMI.fxml'.";
	assert this.inputLoggingArea != null : "fx:id=\"inputLoggingArea\" was not injected: check your FXML file 'SECMockUpHMI.fxml'.";
	assert this.keyTextField != null : "fx:id=\"keyTextField\" was not injected: check your FXML file 'SECMockUpHMI.fxml'.";
	assert this.mapPane != null : "fx:id=\"mapPane\" was not injected: check your FXML file 'SECMockUpHMI.fxml'.";
	assert this.outputLoggingArea != null : "fx:id=\"outputLoggingArea\" was not injected: check your FXML file 'SECMockUpHMI.fxml'.";
	assert this.protobufCheckBox != null : "fx:id=\"protobufCheckBox\" was not injected: check your FXML file 'SECMockUpHMI.fxml'.";
	assert this.tcpActivateButton != null : "fx:id=\"tcpActivateButton\" was not injected: check your FXML file 'SECMockUpHMI.fxml'.";
	assert this.tcpClientActivateButton != null : "fx:id=\"tcpClientActivateButton\" was not injected: check your FXML file 'SECMockUpHMI.fxml'.";
	assert this.tcpClientDeactivateButton != null : "fx:id=\"tcpClientDeactivateButton\" was not injected: check your FXML file 'SECMockUpHMI.fxml'.";
	assert this.tcpClientIPTextField != null : "fx:id=\"tcpClientIPTextField\" was not injected: check your FXML file 'SECMockUpHMI.fxml'.";
	assert this.tcpClientPane != null : "fx:id=\"tcpClientPane\" was not injected: check your FXML file 'SECMockUpHMI.fxml'.";
	assert this.tcpClientPortTextField != null : "fx:id=\"tcpClientPortTextField\" was not injected: check your FXML file 'SECMockUpHMI.fxml'.";
	assert this.tcpDeactivateButton != null : "fx:id=\"tcpDeactivateButton\" was not injected: check your FXML file 'SECMockUpHMI.fxml'.";
	assert this.tcpInterfaceComboBox != null : "fx:id=\"tcpInterfaceComboBox\" was not injected: check your FXML file 'SECMockUpHMI.fxml'.";
	assert this.tcpPane != null : "fx:id=\"tcpPane\" was not injected: check your FXML file 'SECMockUpHMI.fxml'.";
	assert this.tcpPortTextField != null : "fx:id=\"tcpPortTextField\" was not injected: check your FXML file 'SECMockUpHMI.fxml'.";
	assert this.udpActivateButton != null : "fx:id=\"udpActivateButton\" was not injected: check your FXML file 'SECMockUpHMI.fxml'.";
	assert this.udpDeactivateButton != null : "fx:id=\"udpDeactivateButton\" was not injected: check your FXML file 'SECMockUpHMI.fxml'.";
	assert this.udpIPTextField != null : "fx:id=\"udpIPTextField\" was not injected: check your FXML file 'SECMockUpHMI.fxml'.";
	assert this.udpPane != null : "fx:id=\"udpPane\" was not injected: check your FXML file 'SECMockUpHMI.fxml'.";
	assert this.udpPortTextField != null : "fx:id=\"udpPortTextField\" was not injected: check your FXML file 'SECMockUpHMI.fxml'.";

	// TiledPanes
	this.tcpClientPane.expandedProperty().addListener((s, o, n) -> {
	    if (n) {
		this.tcpPane.setExpanded(false);
		this.udpPane.setExpanded(false);
	    }
	});
	this.tcpPane.expandedProperty().addListener((s, o, n) -> {
	    if (n) {
		this.tcpClientPane.setExpanded(false);
		this.udpPane.setExpanded(false);
	    }
	});
	this.udpPane.expandedProperty().addListener((s, o, n) -> {
	    if (n) {
		this.tcpClientPane.setExpanded(false);
		this.tcpPane.setExpanded(false);
	    }
	});

	// Network Interfaces
	this.tcpInterfaceComboBox.setItems(FXCollections.observableArrayList());
	this.tcpInterfaceComboBox.setButtonCell(new ListCell<>() {
	    protected void updateItem(NetworkInterface item, boolean empty) {
		super.updateItem(item, empty);
		if (item == null || empty) {
		    setGraphic(null);
		} else {
		    final StringBuilder ips = new StringBuilder();
		    item.getInetAddresses().asIterator().forEachRemaining(ip -> ips.append("," + ip.toString()));
		    setText(ips.toString().substring(2) + " (" + item.getDisplayName() + ")");
		}
	    }
	});
	this.tcpInterfaceComboBox.setCellFactory(new Callback<ListView<NetworkInterface>, ListCell<NetworkInterface>>() {
	    @Override
	    public ListCell<NetworkInterface> call(ListView<NetworkInterface> l) {
		return new ListCell<NetworkInterface>() {
		    @Override
		    protected void updateItem(NetworkInterface item, boolean empty) {
			super.updateItem(item, empty);
			if (item == null || empty) {
			    setGraphic(null);
			} else {
			    final StringBuilder ips = new StringBuilder();
			    item.getInetAddresses().asIterator().forEachRemaining(ip -> ips.append("," + ip.toString().substring(1)));
			    setText(ips.toString().substring(1) + " (" + item.getDisplayName() + ")");
			}
		    }
		};
	    }
	});

	try {

	    final Enumeration<NetworkInterface> enumInterf = NetworkInterface.getNetworkInterfaces();

	    while (enumInterf.hasMoreElements()) {

		final NetworkInterface networkInterface = enumInterf.nextElement();
		if (networkInterface.isUp() && networkInterface.supportsMulticast() && !networkInterface.getInterfaceAddresses().isEmpty()) {
		    this.tcpInterfaceComboBox.getItems().add(networkInterface);
		}
	    }
	} catch (final IOException e) {

	}

	if (this.tcpInterfaceComboBox.getItems().size() >= 1) {
	    this.tcpInterfaceComboBox.getSelectionModel().select(0);
	}

	// World Wind Initalisation
	WorldWind.getNetworkStatus().setOfflineMode(true);

	WorldWind.getDataFileStore().getEntries();

	final Model model = (Model) WorldWind.createConfigurationComponent(AVKey.MODEL_CLASS_NAME);

	this.wwPanel = new WorldWindowGLJPanel();

	this.wwPanel.getView().setEyePosition(gov.nasa.worldwind.geom.Position.fromDegrees(53.5356d, 8.156, 0));
	((BasicOrbitView) this.wwPanel.getView()).setZoom(1200000d);
	((BasicOrbitView) this.wwPanel.getView()).setFieldOfView(Angle.fromDegrees(45));
	this.wwPanel.setModel(model);

	final BasicOrbitViewLimits limits = new BasicOrbitViewLimits();
	limits.setZoomLimits(200, Double.MAX_VALUE);

	((BasicOrbitView) this.wwPanel.getView()).setOrbitViewLimits(limits);

	model.getLayers().remove(2);
	model.getLayers().remove(2);
	model.getLayers().remove(2);
	model.getLayers().remove(2);
	model.getLayers().remove(2);
	model.getLayers().remove(2);
	model.getLayers().remove(2);
	model.getLayers().remove(2);
	model.getLayers().remove(2);

	ShapefileLayerFactory factory = new ShapefileLayerFactory();
	ShapeAttributes attrs = new BasicShapeAttributes();
	attrs.setOutlineMaterial(new Material(Color.red));
	attrs.setOutlineWidth(2);
	attrs.setDrawInterior(false);
	attrs.setEnableAntialiasing(true);
	factory.setNormalShapeAttributes(attrs);
	factory.setHighlightShapeAttributes(attrs);
	factory.createFromShapefileSource(new File("./libs/country_shapes.shp"), new CompletionCallback() {

	    @Override
	    public void exception(Exception e) {
		System.err.println(e.getLocalizedMessage());
	    }

	    @Override
	    public void completion(Object result) {

		model.getLayers().addLast((Layer) result);

	    }
	});

	factory = new ShapefileLayerFactory();
	attrs = new BasicShapeAttributes();
	attrs.setOutlineMaterial(new Material(Color.green));
	attrs.setOutlineWidth(2);
	attrs.setDrawInterior(false);
	attrs.setEnableAntialiasing(true);
	factory.setNormalShapeAttributes(attrs);
	factory.setHighlightShapeAttributes(attrs);
	factory.createFromShapefileSource(new File("./libs/cables.shp"), new CompletionCallback() {

	    @Override
	    public void exception(Exception e) {
		System.err.println(e.getLocalizedMessage());
	    }

	    @Override
	    public void completion(Object result) {

		model.getLayers().addLast((Layer) result);

	    }
	});
	model.getLayers().addLast(this.contactsLayer);
	model.getLayers().addLast(this.emissionsLayer);

	this.mapPane.setContent(this.wwPanel);
    }

    @FXML
    void tcpClientConnect(ActionEvent event) {

	this.communicator = new SEDAPExpressTCPClient(this.tcpClientIPTextField.getText(), Integer.parseInt(this.tcpClientPortTextField.getText()));
	this.communicator.subscripeForInputLogging(this);
	this.communicator.subscripeForOutputLogging(this);

	if (this.communicator.connect()) {
	    this.tcpClientPane.setCollapsible(false);
	    this.tcpPane.setCollapsible(false);
	    this.udpPane.setCollapsible(false);
	    this.tcpClientActivateButton.setDisable(true);
	    this.tcpClientDeactivateButton.setDisable(false);

	    this.communicator.subscribeMessages(this, MessageType.values());
	} else {
	    this.communicator.unsubscripeForInputLogging(this);
	    this.communicator.unsubscripeForOutputLogging(this);
	}
    }

    @FXML
    void tcpClientDisconnect(ActionEvent event) {

	this.communicator.stopCommunicator();

	this.communicator.unsubscribeAll(this);
	this.communicator.unsubscripeForInputLogging(this);
	this.communicator.unsubscripeForOutputLogging(this);

	this.tcpClientPane.setCollapsible(true);
	this.tcpPane.setCollapsible(true);
	this.udpPane.setCollapsible(true);
	this.tcpClientActivateButton.setDisable(false);
	this.tcpClientDeactivateButton.setDisable(true);
    }

    @FXML
    void tcpConnect(ActionEvent event) {

	this.communicator = new SEDAPExpressTCPServer(this.tcpInterfaceComboBox.getSelectionModel().getSelectedItem().getInetAddresses().nextElement().getHostAddress(), Integer.parseInt(this.tcpClientPortTextField.getText()));
	this.communicator.subscripeForInputLogging(this);
	this.communicator.subscripeForOutputLogging(this);

	if (this.communicator.connect()) {
	    this.tcpClientPane.setCollapsible(false);
	    this.tcpPane.setCollapsible(false);
	    this.udpPane.setCollapsible(false);
	    this.tcpActivateButton.setDisable(true);
	    this.tcpDeactivateButton.setDisable(false);

	    this.communicator.subscribeMessages(this, MessageType.values());
	} else {
	    this.communicator.unsubscripeForInputLogging(this);
	    this.communicator.unsubscripeForOutputLogging(this);
	}
    }

    @FXML
    void tcpDisconnect(ActionEvent event) {

	this.communicator.stopCommunicator();

	this.communicator.unsubscribeAll(this);
	this.communicator.unsubscripeForInputLogging(this);
	this.communicator.unsubscripeForOutputLogging(this);

	this.tcpClientPane.setCollapsible(true);
	this.tcpPane.setCollapsible(true);
	this.udpPane.setCollapsible(true);
	this.tcpActivateButton.setDisable(false);
	this.tcpDeactivateButton.setDisable(true);
    }

    @FXML
    void udpConnect(ActionEvent event) {

	this.communicator = new SEDAPExpressUDPClient(this.udpIPTextField.getText(), Integer.parseInt(this.udpPortTextField.getText()));
	this.communicator.subscripeForInputLogging(this);
	this.communicator.subscripeForOutputLogging(this);

	if (this.communicator.connect()) {
	    this.tcpClientPane.setCollapsible(false);
	    this.tcpPane.setCollapsible(false);
	    this.udpPane.setCollapsible(false);
	    this.udpActivateButton.setDisable(true);
	    this.udpDeactivateButton.setDisable(false);

	    this.communicator.subscribeMessages(this, MessageType.values());

	} else {
	    this.communicator.unsubscripeForInputLogging(this);
	    this.communicator.unsubscripeForOutputLogging(this);
	}
    }

    @FXML
    void udpDisconnect(ActionEvent event) {

	this.communicator.stopCommunicator();

	this.communicator.unsubscribeAll(this);
	this.communicator.unsubscripeForInputLogging(this);
	this.communicator.unsubscripeForOutputLogging(this);

	this.tcpClientPane.setCollapsible(true);
	this.tcpPane.setCollapsible(true);
	this.udpPane.setCollapsible(true);
	this.udpActivateButton.setDisable(false);
	this.udpDeactivateButton.setDisable(true);
    }

    @Override
    public void processSEDAPExpressInputLoggingMessage(String message) {
	this.inputLoggingArea.log(message);
    }

    @Override
    public void processSEDAPExpressOutputLoggingMessage(String message) {
	this.outputLoggingArea.log(message);
    }

    private ConcurrentHashMap<String, MilStd2525TacticalSymbol> contacts = new ConcurrentHashMap<>();

    @Override
    public void processSEDAPExpressMessage(SEDAPExpressMessage message) {

	switch (message) {

	case ACKNOWLEDGE acknowledge -> {
	}

	case COMMAND command -> {
	}

	case CONTACT contact -> {

	    MilStd2525TacticalSymbol pp = null;

	    if (this.contacts.containsKey(contact.getContactID())) {

		if (contact.getDeleteFlag() == DeleteFlag.FALSE) {

		    // Get existing symbol and just updating it
		    pp = this.contacts.get(contact.getContactID());
		} else {

		    // Remove symbol
		    this.contactsLayer.removeRenderable(pp);
		    this.contacts.remove(contact.getContactID());

		    return;
		}

	    } else if (contact.getDeleteFlag() == DeleteFlag.FALSE) {

		// Create new symbol with some default attributes
		if (contact.getSIDC() != null)
		    pp = new MilStd2525TacticalSymbol(String.valueOf(contact.getSIDC()), null);
		else
		    pp = new MilStd2525TacticalSymbol(SECMockUpHMI.standardSIDC, Position.fromDegrees(0.0, 0.0, 0.0));
		pp.setAltitudeMode(WorldWind.ABSOLUTE);
		pp.setShowLocation(false);
		pp.setShowGraphicModifiers(true);
		pp.setShowTextModifiers(true);
		pp.setModifier(SymbologyConstants.SHOW_FILL, true);
		pp.setModifier(SymbologyConstants.UNIQUE_DESIGNATION, String.valueOf(contact.getContactID()));

		final TacticalSymbolAttributes attrs = new BasicTacticalSymbolAttributes();
		attrs.setTextModifierFont(this.labelFont);
		attrs.setTextModifierMaterial(this.labelMaterial);
		attrs.setScale(0.2);
		pp.setAttributes(attrs);

		final TacticalSymbolAttributes highAttrs = new BasicTacticalSymbolAttributes();
		highAttrs.setTextModifierFont(this.labelFont);
		highAttrs.setTextModifierMaterial(this.labelMaterial);

		pp.setHighlightAttributes(attrs);

		this.contactsLayer.addRenderable(pp);
		this.contacts.put(contact.getContactID(), pp);

	    } else {

		// Unknow and should be deleted, so no further actions have to be done
		return;
	    }

	    if (contact.getSIDC() != null)
		pp.setIdentifier(String.valueOf(contact.getSIDC()));

	    pp.setModifier(SymbologyConstants.DATE_TIME_GROUP, SECMockUpHMI.sdf.format(contact.getTime()).toUpperCase());

	    Position pos;
	    if (contact.getAltitude() != null)
		pos = Position.fromDegrees(contact.getLatitude(), contact.getLongitude(), contact.getAltitude());
	    else
		pos = Position.fromDegrees(contact.getLatitude(), contact.getLongitude());
	    pp.setPosition(pos);

	    if ((pos.getLatitude().getDegrees() >= 0) && (pos.getLongitude().getDegrees() >= 0)) {
		pp.setModifier(SymbologyConstants.HIGHER_FORMATION, (Math.round(pos.getLatitude().getDegrees() * 100d) / 100d) + "N" + (Math.round(pos.getLongitude().getDegrees() * 100d) / 100d) + "E");
	    } else if ((pos.getLatitude().getDegrees() >= 0) && (pos.getLongitude().getDegrees() < 0)) {
		pp.setModifier(SymbologyConstants.HIGHER_FORMATION, (Math.round(pos.getLatitude().getDegrees() * 100d) / 100d) + "N" + (Math.round(pos.getLongitude().getDegrees() * 100d) / 100d) + "W");
	    } else if ((pos.getLatitude().getDegrees() < 0) && (pos.getLongitude().getDegrees() >= 0)) {
		pp.setModifier(SymbologyConstants.HIGHER_FORMATION, (Math.round(pos.getLatitude().getDegrees() * 100d) / 100d) + "S" + (Math.round(pos.getLongitude().getDegrees() * 100d) / 100d) + "E");
	    } else {
		pp.setModifier(SymbologyConstants.HIGHER_FORMATION, (Math.round(pos.getLatitude().getDegrees() * 100d) / 100d) + "S" + (Math.round(pos.getLongitude().getDegrees() * 100d) / 100d) + "W");
	    }

	    if (contact.getAltitude() != null) {
		if (pos.getAltitude() < -1.0) {
		    pp.setModifier(SymbologyConstants.ALTITUDE_DEPTH, Math.round(pos.getAltitude()) + "m");
		} else if (Math.round(pos.getAltitude()) == 0) {
		    pp.setModifier(SymbologyConstants.ALTITUDE_DEPTH, null);
		} else if (pos.getAltitude() > 500) {
		    pp.setModifier(SymbologyConstants.ALTITUDE_DEPTH, Math.round((pos.getAltitude() / 100d) * SECMockUpHMI.meterToFeet) + "hft");
		} else {
		    pp.setModifier(SymbologyConstants.ALTITUDE_DEPTH, Math.round(pos.getAltitude() * SECMockUpHMI.meterToFeet) + "ft");
		}
	    }

	    if (contact.getSpeed() != null && contact.getSpeed() > 1) {
		pp.setModifier(SymbologyConstants.SPEED, Math.round(contact.getSpeed() * SECMockUpHMI.metersPerSecondToKnots));
		pp.setModifier(SymbologyConstants.SPEED_LEADER_SCALE, Math.log10(contact.getSpeed()) / 1.5);
	    } else if (contact.getSpeed() > 0) {
		pp.setModifier(SymbologyConstants.SPEED, Math.round(contact.getSpeed() * SECMockUpHMI.metersPerSecondToKnots));
		pp.setModifier(SymbologyConstants.SPEED_LEADER_SCALE, 1);
	    } else {
		pp.setModifier(SymbologyConstants.SPEED, null);
		pp.setModifier(SymbologyConstants.SPEED_LEADER_SCALE, 0);
	    }

	    if (contact.getCourse() != null)
		pp.setModifier(SymbologyConstants.DIRECTION_OF_MOVEMENT, Angle.fromDegrees(contact.getCourse()));

	    if (contact.getMMSI() != null && !contact.getMMSI().isBlank() && contact.getICAO() != null && !contact.getICAO().isBlank()) {
		pp.setModifier(SymbologyConstants.IFF_SIF, contact.getMMSI() + "/" + contact.getICAO());
	    } else if (contact.getMMSI() != null && !contact.getMMSI().isBlank()) {
		pp.setModifier(SymbologyConstants.IFF_SIF, contact.getMMSI());
	    } else if (contact.getICAO() != null && !contact.getICAO().isBlank()) {
		pp.setModifier(SymbologyConstants.IFF_SIF, contact.getICAO());
	    }

	    if (contact.getName() != null)
		pp.setModifier(SymbologyConstants.TYPE, contact.getName());

	    if (contact.getComment() != null)
		pp.setModifier(SymbologyConstants.STAFF_COMMENTS, contact.getComment());

	    if (contact.getSource() != null)
		pp.setModifier(SymbologyConstants.ADDITIONAL_INFORMATION, contact.getSource());

	}

	case EMISSION emision -> {
	}
	case GENERIC generic -> {
	}
	case GRAPHIC graphic -> {
	}
	case HEARTBEAT heartbeat -> {
	}
	case KEYEXCHANGE keyexchange -> {
	}
	case METEO meteo -> {
	}
	case OWNUNIT ownunit -> {
	}
	case RESEND resend -> {
	}
	case STATUS status -> {
	}
	case TEXT text -> {

	}
	case TIMESYNC timesync -> {
	    // Ignore, is already done by SEDAPExpressCommunicator.TimeSyncRunable
	}
	default -> throw new IllegalArgumentException("Unexpected value: " + message.getMessageType());
	}

    }

    @Override
    public void start(Stage primaryStage) throws Exception {

	try {
	    FXMLLoader loader = new FXMLLoader(getClass().getResource("SECMockUpHMI.fxml"));

	    Parent root = loader.load();
	    Scene scene = new Scene(root);

	    primaryStage.setTitle("SEDAP-Express Connector (MockUp) v1.0 - (C)2024, Federal Armed Forces of Germany");
	    primaryStage.setScene(scene);
	    primaryStage.show();

	    // If you close the application close all connections gracefully
	    primaryStage.setOnCloseRequest(event -> {
		if (SECMockUpHMI.this.communicator != null) {
		    SECMockUpHMI.this.communicator.stopCommunicator();
		}
		System.exit(0);

	    });
	} catch (Exception e) {
	    e.printStackTrace();
	}
    }

    public static void main(String[] args) {

	Application.launch(args);
    }

}
