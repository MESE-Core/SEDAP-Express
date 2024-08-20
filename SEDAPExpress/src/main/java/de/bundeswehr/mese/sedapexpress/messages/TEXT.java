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

public class TEXT extends SEDAPExpressMessage {

    private static final long serialVersionUID = 1140074068439618568L;

    public static final Integer TYPE_Undefined = 0;
    public static final Integer TYPE_Alert = 1;
    public static final Integer TYPE_Warning = 2;
    public static final Integer TYPE_Notice = 3;
    public static final Integer TYPE_Chat = 4;

    private Integer type;

    private String encoding;

    private String textContent;

    private String recipient;

    public Integer getType() {
	return this.type;
    }

    public void setType(Integer type) {
	this.type = type;
    }

    public String getEncoding() {
	return this.encoding;
    }

    public void setEncoding(String encoding) {
	this.encoding = encoding;
    }

    public String getTextContent() {
	return this.textContent;
    }

    public void setTextContent(String text) {
	this.textContent = text;
    }

    public String getRecipient() {
	return this.recipient;
    }

    public void setRecipient(String recipient) {
	this.recipient = recipient;
    }

    /**
     *
     * @param number
     * @param time
     * @param sender
     * @param classification
     * @param acknowledgement
     * @param mac
     * @param type
     * @param encoding
     * @param textContent
     * @param recipient
     */
    public TEXT(Short number, Long time, String sender, Character classification, Boolean acknowledgement, String mac,
	    Integer type, String encoding, String textContent, String recipient) {

	super(number, time, sender, classification, acknowledgement, mac);

	this.type = type;
	this.encoding = encoding;
	this.textContent = textContent;
	this.recipient = recipient;
    }

    /**
     *
     * @param message
     */
    public TEXT(String message) {

	this(SEDAPExpressMessage.splitMessage(message.substring(message.indexOf(';') + 1)).iterator());
    }

    /**
     *
     * @param message
     */
    public TEXT(Iterator<String> message) {

	super(message);

	String value;

	// Recipient
	if (message.hasNext()) {
	    value = message.next();
	    if (SEDAPExpressMessage.matchesPattern(SEDAPExpressMessage.SENDER_MATCHER, value)) {
		this.recipient = value;
	    } else if (!value.isBlank()) {
		this.recipient = value;
		SEDAPExpressMessage.logger
			.logp(
			      Level.INFO,
			      "SEDAPExpressMessage",
			      "SEDAPExpressMessage(Iterator<String> message)",
			      "Optional field \"recipient\" contains not a valid number, but free text is allowed!",
			      value);
	    }
	} else {
	    SEDAPExpressMessage.logger
		    .logp(
			  Level.SEVERE,
			  "TEXT",
			  "TEXT(Iterator<String> message)",
			  "Incomplete message!");
	}

	// Type
	if (message.hasNext()) {
	    value = message.next();
	    if (value.isBlank()) {
		SEDAPExpressMessage.logger
			.logp(
			      Level.INFO,
			      "TEXT",
			      "TEXT(Iterator<String> message)",
			      "Optional field \"type\" is empty!");
	    } else if (SEDAPExpressMessage.matchesPattern(SEDAPExpressMessage.TEXTTYPE_MATCHER, value)) {
		this.type = Integer.valueOf(value);
	    } else {
		SEDAPExpressMessage.logger
			.logp(
			      Level.SEVERE,
			      "TEXT",
			      "TEXT(Iterator<String> message)",
			      "TEXT field \"type\" invalid value: \"" + value + "\"");
	    }
	} else {
	    SEDAPExpressMessage.logger
		    .logp(
			  Level.SEVERE,
			  "TEXT",
			  "TEXT(Iterator<String> message)",
			  "Incomplete message!");
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
			      Level.INFO,
			      "TEXT",
			      "TEXT(Iterator<String> message)",
			      "TEXT field \"encoding\" invalid value: \"" + value + "\"");
	    }
	} else {
	    SEDAPExpressMessage.logger
		    .logp(
			  Level.SEVERE,
			  "TEXT",
			  "TEXT(Iterator<String> message)",
			  "Incomplete message!");
	}

	// Text
	if (message.hasNext()) {
	    value = message.next();
	    if (value.isBlank()) {
		SEDAPExpressMessage.logger
			.logp(
			      Level.SEVERE,
			      "TEXT",
			      "TEXT(Iterator<String> message)",
			      "Mandatory field \"text\" is empty!");
	    } else {
		if (SEDAPExpressMessage.ENCODING_BASE64.equals(this.encoding)) {
		    try {
			this.textContent = new String(Base64.decode(value));
		    } catch (DecoderException e) {
			SEDAPExpressMessage.logger
				.logp(
				      Level.SEVERE,
				      "TEXT",
				      "TEXT(Iterator<String> message)",
				      "Mandatory field \"text\" could not be decoded from Base64!");
		    }

		} else {
		    this.textContent = value;
		}
	    }
	} else {
	    SEDAPExpressMessage.logger
		    .logp(
			  Level.SEVERE,
			  "TEXT",
			  "TEXT(Iterator<String> message)",
			  "Incomplete message!");
	}

    }

    @Override
    public boolean equals(Object obj) {

	if (obj == null) {
	    return false;
	} else if (!(obj instanceof TEXT)) {
	    return false;
	} else {
	    return super.equals(obj) &&
		    (this.type == ((TEXT) obj).type) &&

		    (((this.encoding == null) && (((TEXT) obj).encoding == null)) ||
			    ((this.encoding != null) && this.encoding.equals(((TEXT) obj).encoding)))
		    &&

		    (((this.textContent == null) && (((TEXT) obj).textContent == null)) ||
			    ((this.textContent != null) && this.textContent.equals(((TEXT) obj).textContent)))
		    &&

		    (((this.recipient == null) && (((TEXT) obj).recipient == null)) ||
			    ((this.recipient != null) && this.recipient.equals(((TEXT) obj).recipient)));
	}
    }

    @Override
    public int hashCode() {
	return super.hashCode();
    }

    @Override
    public String toString() {

	return serializeHeader()
		.append((this.type != null) ? this.type : "")
		.append(";")
		.append((this.encoding != null) ? this.encoding : "")
		.append(";")
		.append((this.textContent != null) ? (SEDAPExpressMessage.ENCODING_BASE64.equals(this.encoding) ? Base64.toBase64String(this.textContent.getBytes()) : "") : "")
		.append(";")
		.append((this.recipient != null) ? this.recipient : "")
		.toString();
    }

}
