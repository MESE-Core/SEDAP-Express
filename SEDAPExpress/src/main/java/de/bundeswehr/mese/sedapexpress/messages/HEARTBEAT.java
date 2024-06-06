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

public class HEARTBEAT extends SEDAPExpressMessage {

    private static final long serialVersionUID = 3957328245063360161L;

    private String recipient;

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
     * @param hmac
     */
    public HEARTBEAT(Short number, Long time, String sender, Character classification, Boolean acknowledgement, Integer hmac) {

	super(number, time, sender, classification, acknowledgement, hmac);

	this.recipient = null;
    }

    /**
     *
     * @param number
     * @param time
     * @param sender
     * @param classification
     * @param acknowledgement
     * @param hmac
     * @param recipient
     */
    public HEARTBEAT(Short number, Long time, String sender, Character classification, Boolean acknowledgement, Integer hmac, String recipient) {

	super(number, time, sender, classification, acknowledgement, hmac);

	this.recipient = recipient;
    }

    /**
     *
     * @param message
     */
    public HEARTBEAT() {

	super();
    }

    /**
     *
     * @param message
     */
    public HEARTBEAT(String message) {

	this(SEDAPExpressMessage.splitMessage(message.substring(message.indexOf(';') + 1)).iterator());
    }

    /**
     *
     * @param number
     * @param time
     * @param sender
     * @param classification
     * @param acknowledgement
     * @param hmac
     * @param recipient
     */
    public HEARTBEAT(Short number, Long time, String sender, Character classification,
	    Boolean acknowledgement, int hmac, String recipient) {
	super(number, time, sender, classification, acknowledgement, hmac);

	this.recipient = recipient;
    }

    /**
     *
     * @param message
     */
    public HEARTBEAT(Iterator<String> message) {

	super(message);

	String value;

	// Recipient
	if (message.hasNext()) {
	    value = message.next();
	    if (value.isBlank()) {
		SEDAPExpressMessage.logger
			.logp(
			      Level.INFO,
			      "SEDAPExpressMessage",
			      "SEDAPExpressMessage(Iterator<String> message)",
			      "Optional field \"recipient\" is empty!");
	    } else if (SEDAPExpressMessage.matchesPattern(SEDAPExpressMessage.SENDER_MATCHER, value)) {
		this.recipient = String.valueOf(Integer.parseInt(value, 16));
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
			  Level.INFO,
			  "SEDAPExpressMessage",
			  "SEDAPExpressMessage(Iterator<String> message)",
			  "Optional field \"recipient\" is empty!");
	}
    }

    @Override
    public boolean equals(Object obj) {

	if (obj == null) {
	    return false;
	} else if (!(obj instanceof HEARTBEAT)) {
	    return false;
	} else {
	    return super.equals(obj) &&

		    (((this.recipient == null) && (((HEARTBEAT) obj).recipient == null)) ||
			    ((this.recipient != null) && this.recipient.equals(((HEARTBEAT) obj).recipient)));

	}
    }

    @Override
    public int hashCode() {
	return super.hashCode();
    }

    @Override
    public String toString() {

	return serializeHeader()
		.append(this.recipient != null ? this.recipient : "")
		.toString();

    }

}
