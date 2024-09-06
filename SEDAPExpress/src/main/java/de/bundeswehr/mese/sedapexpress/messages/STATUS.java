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
package de.bundeswehr.mese.sedapexpress.messages;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.logging.Level;

import org.bouncycastle.util.encoders.Base64;
import org.bouncycastle.util.encoders.DecoderException;

public class STATUS extends SEDAPExpressMessage {

    private static final long serialVersionUID = -6681575300891302102L;

    public enum TechnicalState {

	Off_absent(0),
	Initializing(1),
	Degraded(2),
	Operational(3),
	Fault(4);

	public static TechnicalState valueOfTechnicalState(int state) {
	    return switch (state) {
	    case 0 -> Off_absent;
	    case 1 -> Initializing;
	    case 2 -> Degraded;
	    case 3 -> Operational;
	    case 4 -> Fault;
	    default -> Off_absent;
	    };
	}

	int value;

	TechnicalState(int status) {
	    this.value = status;
	}

    }

    public enum OperationalState {

	Not_operational(0),
	Degraded(1),
	Operational(2);

	public static OperationalState valueOfOperationalState(int state) {
	    return switch (state) {
	    case 0 -> Not_operational;
	    case 1 -> Degraded;
	    case 2 -> Operational;
	    default -> Not_operational;
	    };
	}

	int value;

	OperationalState(int status) {
	    this.value = status;
	}

    }

    public enum CommandState {

	Undefined(0),
	Executed_successfully(1),
	Partially_executed_successfully(2),
	Executed_not_successfully(3),
	Execution_not_possible(4),
	Will_execute_at(5);

	public static CommandState valueOfMessageType(int state) {
	    return switch (state) {
	    case 0 -> Undefined;
	    case 1 -> Executed_successfully;
	    case 2 -> Partially_executed_successfully;
	    case 3 -> Executed_not_successfully;
	    case 4 -> Execution_not_possible;
	    case 5 -> Will_execute_at;
	    default -> Undefined;
	    };
	}

	int value;

	CommandState(int status) {
	    this.value = status;
	}
    }

    private TechnicalState tecState;
    private OperationalState opsState;

    private Double ammunitionLevel;
    private Double fuelLevel;
    private Double batterieLevel;

    private Integer cmdId;
    private CommandState cmdState;

    private String hostname;

    private List<String> mediaUrls;

    private String freeText;

    public TechnicalState getTecState() {
	return this.tecState;
    }

    public void setTecState(TechnicalState tecState) {
	this.tecState = tecState;
    }

    public OperationalState getOpsState() {
	return this.opsState;
    }

    public void setOpsState(OperationalState opsState) {
	this.opsState = opsState;
    }

    public Double getAmmunitionLevel() {
	return this.ammunitionLevel;
    }

    public void setAmmunitionLevel(Double ammunitionLevel) {
	this.ammunitionLevel = ammunitionLevel;
    }

    public Double getFuelLevel() {
	return this.fuelLevel;
    }

    public void setFuelLevel(Double fuelLevel) {
	this.fuelLevel = fuelLevel;
    }

    public Double getBatterieLevel() {
	return this.batterieLevel;
    }

    public void setBatterieLevel(Double batterieLevel) {
	this.batterieLevel = batterieLevel;
    }

    public Integer getCmdId() {
	return this.cmdId;
    }

    public void setCmdId(Integer cmdId) {
	this.cmdId = cmdId;
    }

    public CommandState getCmdState() {
	return this.cmdState;
    }

    public void setCmdState(CommandState cmdState) {
	this.cmdState = cmdState;
    }

    public String getHostname() {
	return this.hostname;
    }

    public void setHostname(String hostname) {
	this.hostname = hostname;
    }

    public List<String> getMediaUrls() {
	return this.mediaUrls;
    }

    public void setMediaUrls(List<String> mediaUrls) {
	this.mediaUrls = mediaUrls;
    }

    public String getFreeText() {
	return this.freeText;
    }

    public void setFreeText(String freeText) {
	this.freeText = freeText;
    }

    /**
     * Instantiate a new STATUS message
     *
     * @param number
     * @param time
     * @param sender
     * @param classification
     * @param acknowledgement
     * @param mac
     * @param tecState
     * @param opsState
     * @param ammunitionLevel
     * @param fuelLevel
     * @param batterieLevel
     * @param cmdId
     * @param cmdState
     * @param hostname
     * @param mediaUrls
     * @param freeText
     */
    public STATUS(Short number, Long time, String sender, Classification classification, Acknowledgement acknowledgement, String mac,
	    TechnicalState tecState, OperationalState opsState, Double ammunitionLevel, Double fuelLevel, Double batterieLevel,
	    Integer cmdId, CommandState cmdState,
	    String hostname, List<String> mediaUrls, String freeText) {
	super(number, time, sender, classification, acknowledgement, mac);
	this.tecState = tecState;
	this.opsState = opsState;
	this.ammunitionLevel = ammunitionLevel;
	this.cmdId = cmdId;
	this.cmdState = cmdState;
	this.fuelLevel = fuelLevel;
	this.batterieLevel = batterieLevel;
	this.hostname = hostname;
	this.mediaUrls = mediaUrls;
	this.freeText = freeText;
    }

    /**
     * Instantiate a new STATUS message from a serialized message
     *
     * @param message
     */
    public STATUS(String message) {

	this(SEDAPExpressMessage.splitMessage(message.substring(message.indexOf(';') + 1)).iterator());
    }

    /**
     * Instantiate a new STATUS message from a paramter list
     *
     * @param message
     */
    public STATUS(Iterator<String> message) {

	super(message);

	String value;

	// TecState
	if (message.hasNext()) {
	    value = message.next();
	    if (SEDAPExpressMessage.matchesPattern(SEDAPExpressMessage.TECSTATUS_MATCHER, value)) {
		this.tecState = TechnicalState.valueOfTechnicalState(Integer.parseInt(value));
	    } else if (!value.isBlank()) {
		SEDAPExpressMessage.logger
			.logp(
			      Level.SEVERE,
			      "STATUS",
			      "STATUS(Iterator<String> message)",
			      "Optional field \"tecState\" contains not a valid number!",
			      value);
	    }
	}

	// OpsState
	if (message.hasNext()) {
	    value = message.next();
	    if (SEDAPExpressMessage.matchesPattern(SEDAPExpressMessage.OPSSTATUS_MATCHER, value)) {
		this.opsState = OperationalState.valueOfOperationalState(Integer.parseInt(value));
	    } else if (!value.isBlank()) {
		SEDAPExpressMessage.logger
			.logp(
			      Level.SEVERE,
			      "STATUS",
			      "STATUS(Iterator<String> message)",
			      "Optional field \"opsState\" contains not a valid number!",
			      value);
	    }
	}

	// AmmunitionLevel
	if (message.hasNext()) {
	    value = message.next();
	    if (SEDAPExpressMessage.matchesPattern(SEDAPExpressMessage.PERCENT_MATCHER, value)) {
		this.ammunitionLevel = Double.parseDouble(value);
	    } else if (!value.isBlank()) {
		SEDAPExpressMessage.logger
			.logp(
			      Level.SEVERE,
			      "STATUS",
			      "STATUS(Iterator<String> message)",
			      "Optional field \"ammunitionLevel\" contains not a valid number!",
			      value);
	    }
	}

	// FuelLevel
	if (message.hasNext()) {
	    value = message.next();
	    if (SEDAPExpressMessage.matchesPattern(SEDAPExpressMessage.PERCENT_MATCHER, value)) {
		this.fuelLevel = Double.parseDouble(value);
	    } else if (!value.isBlank()) {
		SEDAPExpressMessage.logger
			.logp(
			      Level.SEVERE,
			      "STATUS",
			      "STATUS(Iterator<String> message)",
			      "Optional field \"fuelLevel\" contains not a valid number!",
			      value);
	    }
	}

	// BatterieLevel
	if (message.hasNext()) {
	    value = message.next();
	    if (SEDAPExpressMessage.matchesPattern(SEDAPExpressMessage.PERCENT_MATCHER, value)) {
		this.batterieLevel = Double.parseDouble(value);
	    } else if (!value.isBlank()) {
		SEDAPExpressMessage.logger
			.logp(
			      Level.SEVERE,
			      "STATUS",
			      "STATUS(Iterator<String> message)",
			      "Optional field \"batterieLevel\" contains not a valid number!",
			      value);
	    }
	}

	// Hostname
	if (message.hasNext()) {
	    value = message.next();
	    if (value.isBlank()) {
		SEDAPExpressMessage.logger
			.logp(
			      Level.INFO,
			      "STATUS",
			      "STATUS(Iterator<String> message)",
			      "Optional field \"hostname\" is empty!");
	    } else {
		try {
		    this.hostname = new String(Base64.decode(value));
		} catch (DecoderException e) {
		    SEDAPExpressMessage.logger
			    .logp(
				  Level.SEVERE,
				  "STATUS",
				  "STATUS(Iterator<String> message)",
				  "Optional field \"hostname\" could not be decoded from Base64!");
		}
	    }
	}

	// Media
	if (message.hasNext()) {
	    value = message.next();
	    if (value.isBlank()) {
		SEDAPExpressMessage.logger
			.logp(
			      Level.INFO,
			      "STATUS",
			      "STATUS(Iterator<String> message)",
			      "Optional field \"mediaUrls\" is empty!");
	    } else {
		try {

		    this.mediaUrls = new LinkedList<>();
		    String[] urls = value.split("#");
		    for (String url : urls) {
			this.mediaUrls.add(new String(Base64.decode(url)));
		    }
		} catch (DecoderException e) {
		    SEDAPExpressMessage.logger
			    .logp(
				  Level.SEVERE,
				  "STATUS",
				  "STATUS(Iterator<String> message)",
				  "Optional field \"mediaUrls\" could not be decoded from Base64!");
		}
	    }
	}

	// FreeText
	if (message.hasNext()) {
	    value = message.next();
	    if (value.isBlank()) {
		SEDAPExpressMessage.logger
			.logp(
			      Level.INFO,
			      "STATUS",
			      "STATUS(Iterator<String> message)",
			      "Optional field \"freeText\" is empty!");
	    } else {
		try {
		    this.freeText = new String(Base64.decode(value));
		} catch (DecoderException e) {
		    SEDAPExpressMessage.logger
			    .logp(
				  Level.SEVERE,
				  "STATUS",
				  "STATUS(Iterator<String> message)",
				  "Optional field \"freeText\" could not be decoded from Base64!");
		}
	    }
	}

    }

    @Override
    public boolean equals(Object obj) {
	if (obj == null) {
	    return false;
	} else if (!(obj instanceof STATUS)) {
	    return false;
	} else {
	    return super.equals(obj) &&

		    (this.tecState == ((STATUS) obj).tecState) &&
		    (this.opsState == ((STATUS) obj).opsState) &&

		    (this.ammunitionLevel == ((STATUS) obj).ammunitionLevel) &&
		    (this.fuelLevel == ((STATUS) obj).fuelLevel) &&
		    (this.batterieLevel == ((STATUS) obj).batterieLevel) &&

		    (((this.hostname == null) && (((STATUS) obj).hostname == null)) ||
			    ((this.hostname != null) && this.hostname.equals(((STATUS) obj).hostname)))
		    &&

		    (((this.mediaUrls == null) && (((STATUS) obj).mediaUrls == null)) ||
			    ((this.mediaUrls != null) && this.mediaUrls.equals(((STATUS) obj).mediaUrls)))
		    &&

		    (((this.freeText == null) && (((STATUS) obj).freeText == null)) ||
			    ((this.freeText != null) && this.freeText.equals(((STATUS) obj).freeText)));

	}
    }

    @Override
    public int hashCode() {
	return super.hashCode();
    }

    @Override
    public String toString() {

	StringBuilder urls = new StringBuilder();
	if (this.mediaUrls != null) {
	    this.mediaUrls.forEach(entry -> urls.append(Base64.toBase64String(entry.getBytes()) + "#"));
	}

	return serializeHeader()

		.append((this.tecState != null) ? this.tecState : "")
		.append(";")
		.append((this.opsState != null) ? this.opsState : "")
		.append(";")
		.append((this.ammunitionLevel != null) ? SEDAPExpressMessage.numberFormatter.format(this.ammunitionLevel) : "")
		.append(";")
		.append((this.fuelLevel != null) ? SEDAPExpressMessage.numberFormatter.format(this.fuelLevel) : "")
		.append(";")
		.append((this.batterieLevel != null) ? SEDAPExpressMessage.numberFormatter.format(this.batterieLevel) : "")
		.append(";")
		.append((this.cmdId != null) ? this.cmdId : "")
		.append(";")
		.append((this.cmdState != null) ? this.cmdState : "")
		.append(";")
		.append((this.hostname != null) ? Base64.toBase64String(this.hostname.getBytes()) : "")
		.append(";")
		.append((this.mediaUrls != null) ? urls.subSequence(0, urls.length() - 1) : "")
		.append(";")
		.append((this.freeText != null) ? Base64.toBase64String(this.freeText.getBytes()) : "")
		.toString();
    }
}
