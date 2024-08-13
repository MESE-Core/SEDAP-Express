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
package de.bundeswehr.mese.sedapexpress.sample.tcpserver;

import java.util.Arrays;

import de.bundeswehr.mese.sedapexpress.messages.CONTACT;
import de.bundeswehr.mese.sedapexpress.messages.HEARTBEAT;
import de.bundeswehr.mese.sedapexpress.messages.OWNUNIT;
import de.bundeswehr.mese.sedapexpress.messages.SEDAPExpressMessage;
import de.bundeswehr.mese.sedapexpress.messages.SEDAPExpressMessage.MessageType;
import de.bundeswehr.mese.sedapexpress.messages.STATUS;
import de.bundeswehr.mese.sedapexpress.network.SEDAPExpressCommunicator;
import de.bundeswehr.mese.sedapexpress.network.SEDAPExpressTCPServer;
import de.bundeswehr.mese.sedapexpress.processing.SEDAPExpressSubscriber;

public class SampleTCPServer implements SEDAPExpressSubscriber {

    private final SEDAPExpressCommunicator communicator;

    private final String senderId;

    private short numberSTATUS = 0;

    /**
     * Instantiate a sample TCP server
     *
     */
    public SampleTCPServer() {

	this.communicator = new SEDAPExpressTCPServer("0.0.0.0", 50000);

	this.communicator.subscribeMessages(this, MessageType.OWNUNIT, MessageType.CONTACT, MessageType.HEARTBEAT, MessageType.STATUS);
	this.senderId = this.communicator.createSenderId();

	// Sample thread as example for how producing messages
	new Thread(() -> {

	    final STATUS status = new STATUS(this.numberSTATUS++,
		    System.currentTimeMillis(),
		    this.senderId,
		    SEDAPExpressMessage.CONFIDENTIAL,
		    SEDAPExpressMessage.ACKNOWLEDGE_NO,
		    null,
		    STATUS.TECSTATUS_Operational,
		    STATUS.OPSSTATUS_Operational,
		    50.0,
		    75.3,
		    10.8,
		    "10.8.0.6",
		    Arrays.asList("rtsp://10.8.0.6/stream1", "rtsp://10.8.0.6/stream2"),
		    "This is a sample!");

	    this.communicator.sendSEDAPExpressMessage(status);

	    if (this.numberSTATUS == 7f) {
		this.numberSTATUS = 0;
	    }

	}).start();

    }

    @Override
    public void processSEDAPExpressMessage(SEDAPExpressMessage message) {

	System.out.println("Received: " + message); // Use e.g. a Logger or output in a HMI

	switch (message) {

	case OWNUNIT ownunitMessage -> {
	    // Write here your own code, e.g. distribute it to the other connected clients
	    // and/or process it
	}

	case CONTACT contactMessage -> {

	    // Write here your own code, e.g. distribute it to the other connected clients
	    // and/or process it
	}

	case HEARTBEAT heartbeat -> {

	    // Write here your own code, e.g. distribute it to the other connected clients
	    // and/or process it
	    this.communicator.sendSEDAPExpressMessage(new HEARTBEAT());
	    System.out.println("Answered: HEARTBEAT");
	}

	case STATUS status -> {
	    // Write here your own code, e.g. distribute it to the other connected clients
	    // and/or process it
	}

	default -> throw new IllegalArgumentException("Unexpected value: " + message);

	}

    }

    public static void main(String[] args) {
	new SampleTCPServer();

	try {
	    Thread.sleep(Integer.MAX_VALUE);
	} catch (InterruptedException e) {

	}
    }

}
