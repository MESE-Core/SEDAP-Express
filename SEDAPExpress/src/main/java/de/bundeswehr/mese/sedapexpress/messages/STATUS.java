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

    public static final int TECSTATUS_Off_absent = 0;
    public static final int TECSTATUS_Initializing = 1;
    public static final int TECSTATUS_Degraded = 2;
    public static final int TECSTATUS_Operational = 3;
    public static final int TECSTATUS_Fault = 4;

    public static final int OPSSTATUS_Not_operational = 0;
    public static final int OPSSTATUS_Degraded = 1;
    public static final int OPSSTATUS_Operational = 2;

    public static final int CMDSTATUS_Undefined = 0;
    public static final int CMDSTATUS_Executed_successfully = 1;
    public static final int CMDSTATUS_Partially_executed_successfully = 2;
    public static final int CMDSTATUS_Executed_not_successfully = 3;
    public static final int CMDSTATUS_Execution_not_possible = 4;
    public static final int CMDSTATUS_Will_execute_at = 5;

    private Integer tecStatus;
    private Integer opsStatus;

    private Double ammunitionLevel;
    private Double fuelLevel;
    private Double batterieLevel;

    private Integer cmdId;
    private Integer cmdState;

    private String hostname;

    private List<String> mediaUrls;

    private String freeText;

    public Integer getTecStatus() {
	return this.tecStatus;
    }

    public void setTecStatus(Integer tecStatus) {
	this.tecStatus = tecStatus;
    }

    public Integer getOpsStatus() {
	return this.opsStatus;
    }

    public void setOpsStatus(Integer opsStatus) {
	this.opsStatus = opsStatus;
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

    public Integer getCmdState() {
	return this.cmdState;
    }

    public void setCmdState(Integer cmdState) {
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
     *
     * @param number
     * @param time
     * @param sender
     * @param classification
     * @param acknowledgement
     * @param mac
     * @param tecStatus
     * @param opsStatus
     * @param ammunitionLevel
     * @param fuelLevel
     * @param batterieLevel
     * @param hostname
     * @param mediaUrls
     * @param freeText
     */
    public STATUS(Byte number, Long time, String sender, Character classification, Boolean acknowledgement, String mac,
	    Integer tecStatus, Integer opsStatus, Double ammunitionLevel, Double fuelLevel, Double batterieLevel,
	    String hostname, List<String> mediaUrls, String freeText) {
	super(number, time, sender, classification, acknowledgement, mac);
	this.tecStatus = tecStatus;
	this.opsStatus = opsStatus;
	this.ammunitionLevel = ammunitionLevel;
	this.fuelLevel = fuelLevel;
	this.batterieLevel = batterieLevel;
	this.hostname = hostname;
	this.mediaUrls = mediaUrls;
	this.freeText = freeText;
    }

    /**
     *
     * @param message
     */
    public STATUS(String message) {

	this(SEDAPExpressMessage.splitMessage(message.substring(message.indexOf(';') + 1)).iterator());
    }

    /**
     *
     * @param message
     */
    public STATUS(Iterator<String> message) {

	super(message);

	String value;

	// TecStatus
	if (message.hasNext()) {
	    value = message.next();
	    if (SEDAPExpressMessage.matchesPattern(SEDAPExpressMessage.TECSTATUS_MATCHER, value)) {
		this.tecStatus = Integer.parseInt(value);
	    } else if (!value.isBlank()) {
		SEDAPExpressMessage.logger
			.logp(
			      Level.SEVERE,
			      "STATUS",
			      "STATUS(Iterator<String> message)",
			      "Optional field \"tecStatus\" contains not a valid number!",
			      value);
	    }
	}

	// OpsStatus
	if (message.hasNext()) {
	    value = message.next();
	    if (SEDAPExpressMessage.matchesPattern(SEDAPExpressMessage.OPSSTATUS_MATCHER, value)) {
		this.opsStatus = Integer.parseInt(value);
	    } else if (!value.isBlank()) {
		SEDAPExpressMessage.logger
			.logp(
			      Level.SEVERE,
			      "STATUS",
			      "STATUS(Iterator<String> message)",
			      "Optional field \"opsStatus\" contains not a valid number!",
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

		    (this.tecStatus == ((STATUS) obj).tecStatus) &&
		    (this.opsStatus == ((STATUS) obj).opsStatus) &&

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
	this.mediaUrls.forEach(entry -> urls.append(Base64.toBase64String(entry.getBytes()) + "#"));

	return serializeHeader()

		.append((this.tecStatus != null) ? this.tecStatus : "")
		.append(";")
		.append((this.opsStatus != null) ? this.opsStatus : "")
		.append(";")
		.append((this.ammunitionLevel != null) ? this.ammunitionLevel : "")
		.append(";")
		.append((this.fuelLevel != null) ? this.fuelLevel : "")
		.append(";")
		.append((this.batterieLevel != null) ? this.batterieLevel : "")
		.append(";")
		.append((this.cmdId != null) ? this.cmdId : "")
		.append(";")
		.append((this.cmdState != null) ? this.cmdState : "")
		.append(";")
		.append((this.hostname != null) ? this.hostname : "")
		.append(";")
		.append((this.mediaUrls != null) ? urls.subSequence(0, urls.length() - 1) : "")
		.append(";")
		.append((this.freeText != null) ? Base64.toBase64String(this.freeText.getBytes()) : "")
		.toString();
    }
}
