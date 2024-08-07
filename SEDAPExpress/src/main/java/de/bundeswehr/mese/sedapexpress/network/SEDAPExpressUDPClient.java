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
package de.bundeswehr.mese.sedapexpress.network;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.util.Arrays;

import de.bundeswehr.mese.sedapexpress.messages.SEDAPExpressMessage;

/**
 * UDP receiver/sender class for SEDAP-Express
 *
 * @author Volker Voß
 *
 */
public class SEDAPExpressUDPClient extends SEDAPExpressCommunicator implements Runnable {

    private DatagramSocket socket;

    private final String receiver;

    private final int port;

    private boolean status = true;

    /**
     * Instantiate a new SEDAP-Express UDP Client
     *
     * @param receiver Receiver hostname/IP to be used
     * @param port     Port to be used
     */
    public SEDAPExpressUDPClient(String receiver, final int port) {

	super();

	this.receiver = receiver;
	this.port = port;

	new Thread(this).start();

    }

    @Override
    public void run() {

	try {
	    this.socket = new DatagramSocket(this.port);
	    // this.socket.setBroadcast(true);
	    this.socket.setReuseAddress(true);

	    while (this.status) {

		final DatagramPacket packet = new DatagramPacket(new byte[65000], 65000);

		this.socket.receive(packet);

		Arrays
			.asList(new String(packet.getData(), 0, packet.getLength()).split("\n"))
			.forEach(
				 message -> {
				     System.out.println(message);

				     distributeReceivedSEDAPExpressMessage(SEDAPExpressMessage.deserialize(message));
				 });
	    }

	} catch (final IOException e) {
	    e.printStackTrace();
	}

    }

    public void stopAISUDPReceiver() {

	if (this.socket != null) {
	    this.socket.close();
	}

	this.status = false;

    }

    @Override
    public boolean sendSEDAPExpressMessage(SEDAPExpressMessage message) {

	byte[] data = SEDAPExpressMessage.serialize(message).getBytes();
	try {
	    this.socket
		    .send(
			  new DatagramPacket(data,
				  data.length, InetAddress.getByName(this.receiver), this.port));
	} catch (IOException e) {

	    e.printStackTrace();
	}

	return false;
    }

}
