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

public class COMMAND extends SEDAPExpressMessage {

    private static final long serialVersionUID = -5662357406861380560L;

    public static final Integer CMDTYPE_Poweroff = 0;
    public static final Integer CMDTYPE_Restart = 1;
    public static final Integer CMDTYPE_Standby = 2;
    public static final Integer CMDTYPE_Wake_up = 3;
    public static final Integer CMDTYPE_Sync_time = 4;
    public static final Integer CMDTYPE_Send_status = 5;
    public static final Integer CMDTYPE_Move = 6;
    public static final Integer CMDTYPE_Rotate = 7;
    public static final Integer CMDTYPE_Scan_area = 8;
    public static final Integer CMDTYPE_Take_photo = 9;
    public static final Integer CMDTYPE_Make_video = 10;
    public static final Integer CMDTYPE_Switch_on_video_stream = 11;
    public static final Integer CMDTYPE_Switch_off_video_stream = 12;
    public static final Integer CMDTYPE_Engagement = 13;
    public static final Integer CMDTYPE_Generic_action = 255;

    public static final String CMDTYPE_ENGAGEMENT_CMD_Start = "start-engagement";
    public static final String CMDTYPE_ENGAGEMENT_CMD_Hold = "hold-engagement";
    public static final String CMDTYPE_ENGAGEMENT_CMD_Stop = "stop-engagement";

    private String recipient;

    private Integer cmdId;

    private Integer cmdType;

    private List<String> cmdTypeDependentParameters;

    public String getRecipient() {
	return this.recipient;
    }

    public Integer getCmdId() {
	return this.cmdId;
    }

    public void setCmdId(Integer cmdId) {
	this.cmdId = cmdId;
    }

    public void setRecipient(String recipient) {
	this.recipient = recipient;
    }

    public Integer getCmdType() {
	return this.cmdType;
    }

    public void setCmdType(Integer cmdType) {
	this.cmdType = cmdType;
    }

    public List<String> getCmdTypeDependentParameters() {
	return this.cmdTypeDependentParameters;
    }

    public void setCmdTypeDependentParameters(List<String> cmdTypeDependentParameters) {
	this.cmdTypeDependentParameters = cmdTypeDependentParameters;
    }

    /**
     * Instantiate a new COMMAND message
     *
     * @param number
     * @param time
     * @param sender
     * @param classification
     * @param acknowledgement
     * @param mac
     * @param recipient
     * @param cmdId
     * @param cmdType
     * @param cmdTypeDependentParameters
     */
    public COMMAND(Short number, Long time, String sender, Character classification, Boolean acknowledgement, String mac, String recipient,
	    Integer cmdId, Integer cmdType, List<String> cmdTypeDependentParameters) {

	super(number, time, sender, classification, acknowledgement, mac);

	this.recipient = recipient;
	this.cmdId = cmdId;
	this.cmdType = cmdType;
	this.cmdTypeDependentParameters = cmdTypeDependentParameters;
    }

    /**
     * Instantiate a new COMMAND message from a serialized message
     *
     * @param message
     */
    public COMMAND(String message) {

	this(SEDAPExpressMessage.splitMessage(message.substring(message.indexOf(';') + 1)).iterator());
    }

    /**
     * Instantiate a new COMMAND message from a paramter list
     *
     * @param message
     */
    public COMMAND(Iterator<String> message) {

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
			      "COMMAND",
			      "COMMAND(Iterator<String> message)",
			      "Optional field \"recipient\" contains not a valid number, but free text is allowed!",
			      value);
	    }
	} else {
	    SEDAPExpressMessage.logger
		    .logp(
			  Level.SEVERE,
			  "COMMAND",
			  "COMMAND(Iterator<String> message)",
			  "Incomplete message!");
	}

	// CmdID
	if (message.hasNext()) {
	    value = message.next();
	    if (SEDAPExpressMessage.matchesPattern(SEDAPExpressMessage.SENDER_MATCHER, value)) {
		this.cmdId = Integer.valueOf(value, 16);
	    } else if (!value.isBlank()) {
		this.recipient = value;
		SEDAPExpressMessage.logger
			.logp(
			      Level.INFO,
			      "COMMAND",
			      "COMMAND(Iterator<String> message)",
			      "Optional field \"CmdID\" contains not a valid number!",
			      value);
	    }
	} else {
	    SEDAPExpressMessage.logger
		    .logp(
			  Level.SEVERE,
			  "COMMAND",
			  "COMMAND(Iterator<String> message)",
			  "Incomplete message!");
	}

	// CmdType
	if (message.hasNext()) {
	    value = message.next();
	    if (value.isBlank()) {
		SEDAPExpressMessage.logger
			.logp(
			      Level.SEVERE,
			      "COMMAND",
			      "COMMAND(Iterator<String> message)",
			      "Mandatory field \"cmdType\" is empty!");
	    } else if (SEDAPExpressMessage.matchesPattern(SEDAPExpressMessage.CMDTYPE_MATCHER, value)) {
		this.cmdType = Integer.valueOf(value, 16);
	    } else {
		SEDAPExpressMessage.logger
			.logp(
			      Level.SEVERE,
			      "COMMAND",
			      "COMMAND(Iterator<String> message)",
			      "Mandatory field \"cmdType\" contains invalid value!",
			      value);
	    }
	}

	// CmdTypeDependentParameters
	if (message.hasNext()) {
	    value = message.next();
	    if (value.isBlank()) {
		SEDAPExpressMessage.logger
			.logp(
			      Level.INFO,
			      "COMMAND",
			      "COMMAND(Iterator<String> message)",
			      "Optional field \"cmdTypeDependentParameters\" is empty!");
	    } else {
		// Put all remaining elements into the list
		this.cmdTypeDependentParameters = new LinkedList<>();
		this.cmdTypeDependentParameters.add(value);
		while (message.hasNext()) {
		    this.cmdTypeDependentParameters.add(message.next());
		}
	    }
	}

    }

    @Override
    public boolean equals(Object obj) {

	if (obj == null) {
	    return false;
	} else if (!(obj instanceof COMMAND)) {
	    return false;
	} else {
	    return super.equals(obj) &&

		    (((this.recipient == null) && (((COMMAND) obj).recipient == null)) ||
			    ((this.recipient != null) && this.recipient.equals(((COMMAND) obj).recipient)))
		    &&

		    (this.cmdType == (((COMMAND) obj).cmdType)) &&

		    (((this.cmdTypeDependentParameters == null) && (((COMMAND) obj).cmdTypeDependentParameters == null)) ||
			    ((this.cmdTypeDependentParameters != null) && this.cmdTypeDependentParameters.equals(((COMMAND) obj).cmdTypeDependentParameters)));

	}
    }

    @Override
    public int hashCode() {
	return super.hashCode();
    }

    @Override
    public String toString() {

	if (this.cmdTypeDependentParameters != null) {

	    StringBuilder parameters = new StringBuilder();
	    this.cmdTypeDependentParameters.forEach(entry -> parameters.append(entry + ";"));

	    return serializeHeader()
		    .append((this.recipient != null) ? this.recipient : "")
		    .append(";")
		    .append((this.cmdId != null) ? SEDAPExpressMessage.HEXFOMATER.toHighHexDigit(this.cmdId) : "")
		    .append(";")
		    .append((this.cmdType != null) ? SEDAPExpressMessage.HEXFOMATER.toHighHexDigit(this.cmdType) : "")
		    .append(";")
		    .append((this.cmdTypeDependentParameters != null) ? parameters : "")
		    .toString();
	} else {
	    return serializeHeader()
		    .append((this.recipient != null) ? this.recipient : "")
		    .append(";")
		    .append((this.cmdId != null) ? this.cmdId : "")
		    .append(";")
		    .append((this.cmdType != null) ? this.cmdType : "")
		    .toString();
	}

    }

}
