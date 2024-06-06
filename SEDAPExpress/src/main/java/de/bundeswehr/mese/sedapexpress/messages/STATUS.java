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
import java.util.logging.Level;

import org.bouncycastle.util.encoders.Base64;
import org.bouncycastle.util.encoders.DecoderException;

public class STATUS extends SEDAPExpressMessage {

    private static final long serialVersionUID = -6681575300891302102L;

    private Integer tecStatus;
    private Integer opsStatus;

    private Double ammunitionLevel;
    private Double fuelLevel;
    private Double batterieLevel;

    private String hostname;

    private String mediaUrls;

    private String encoding;
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

    public String getHostname() {
	return this.hostname;
    }

    public void setHostname(String hostname) {
	this.hostname = hostname;
    }

    public String getMediaUrls() {
	return this.mediaUrls;
    }

    public void setMediaUrls(String mediaUrls) {
	this.mediaUrls = mediaUrls;
    }

    public String getEncoding() {
	return this.encoding;
    }

    public void setEncoding(String encoding) {
	this.encoding = encoding;
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
     * @param hmac
     * @param tecStatus
     * @param opsStatus
     * @param ammunitionLevel
     * @param fuelLevel
     * @param batterieLevel
     * @param hostname
     * @param mediaUrls
     * @param encoding
     * @param freeText
     */
    public STATUS(Short number, Long time, String sender, Character classification, Boolean acknowledgement, Integer hmac,
	    Integer tecStatus, Integer opsStatus, Double ammunitionLevel, Double fuelLevel, Double batterieLevel,
	    String hostname, String mediaUrls, String encoding, String freeText) {
	super(number, time, sender, classification, acknowledgement, hmac);
	this.tecStatus = tecStatus;
	this.opsStatus = opsStatus;
	this.ammunitionLevel = ammunitionLevel;
	this.fuelLevel = fuelLevel;
	this.batterieLevel = batterieLevel;
	this.hostname = hostname;
	this.mediaUrls = mediaUrls;
	this.encoding = encoding;
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
				  "Optional field \"hostname\" could not decoded from Base64!");
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
		    this.mediaUrls = new String(Base64.decode(value));
		} catch (DecoderException e) {
		    SEDAPExpressMessage.logger
			    .logp(
				  Level.SEVERE,
				  "STATUS",
				  "STATUS(Iterator<String> message)",
				  "Optional field \"mediaUrls\" could not decoded from Base64!");
		}
	    }
	}

	// Encoding
	if (message.hasNext()) {
	    value = message.next();
	    if ("base64".equalsIgnoreCase(value)) {
		this.encoding = SEDAPExpressMessage.ENCODING_BASE64;
	    } else if ("none".equalsIgnoreCase(value) || value.isBlank()) {
		this.encoding = SEDAPExpressMessage.ENCODING_NONE;
	    } else {
		SEDAPExpressMessage.logger
			.logp(
			      Level.SEVERE,
			      "STATUS",
			      "STATUS(Iterator<String> message)",
			      "STATUS field \"encoding\" invalid value: \"" + value + "\"");
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
				  "Optional field \"freeText\" could not decoded from Base64!");
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

		    (((this.encoding == null) && (((STATUS) obj).encoding == null)) ||
			    ((this.encoding != null) && this.encoding.equals(((STATUS) obj).encoding)))
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
		.append((this.hostname != null) ? this.hostname : "")
		.append(";")
		.append((this.mediaUrls != null) ? Base64.toBase64String(this.mediaUrls.getBytes()) : "")
		.append(";")
		.append((this.encoding != null) ? this.encoding : "")
		.append(";")
		.append((this.freeText != null) ? this.freeText : "")
		.toString();
    }
}
