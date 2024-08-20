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

import java.io.BufferedReader;
import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.StandardSocketOptions;
import java.nio.ByteBuffer;
import java.nio.channels.Channels;
import java.nio.channels.SocketChannel;
import java.nio.channels.spi.SelectorProvider;
import java.nio.charset.StandardCharsets;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentLinkedDeque;
import java.util.logging.Level;
import java.util.logging.Logger;

import de.bundeswehr.mese.sedapexpress.messages.SEDAPExpressMessage;
import de.bundeswehr.mese.sedapexpress.messages.SEDAPExpressMessage.MessageType;
import de.bundeswehr.mese.sedapexpress.processing.SEDAPExpressSubscriber;

/**
 * TCP receiver/sender class for SEDAP-Express
 *
 * @author Volker Voß
 *
 */
public class SEDAPExpressTCPClient extends SEDAPExpressCommunicator implements Runnable {

    protected static Logger logger = Logger.getLogger(Logger.GLOBAL_LOGGER_NAME);
    static {
	SEDAPExpressTCPClient.logger.setLevel(Level.ALL);
    }

    private SocketChannel socket;

    private final String host;

    private final int port;

    private boolean status = true;

    private final ConcurrentLinkedDeque<SEDAPExpressTCPClient> clients;

    /**
     * Instantiate a new SEDAP-Express TCP Server
     *
     * @param host Host/IP to be used
     * @param port Port to be used
     */
    public SEDAPExpressTCPClient(String host, int port) {

	super();

	this.host = host;
	this.port = port;

	this.clients = new ConcurrentLinkedDeque<>();
	this.subscriptions = new ConcurrentHashMap<MessageType, Set<SEDAPExpressSubscriber>>();

	new Thread(this).start();
    }

    /**
     * Instantiate a new SEDAP-Express TCP Server
     *
     * @param socket        SocketChannel to be used
     * @param clients       Client list to be used
     * @param subscriptions Subscription list to be used
     */
    protected SEDAPExpressTCPClient(SocketChannel socket,
	    ConcurrentLinkedDeque<SEDAPExpressTCPClient> clients,
	    ConcurrentHashMap<MessageType, Set<SEDAPExpressSubscriber>> subscriptions) {

	super();

	this.socket = socket;

	this.host = ""; // Unused
	this.port = 0; // Unused

	this.clients = clients;
	this.subscriptions = subscriptions;

	new Thread(this).start();
    }

    @Override
    public void run() {
	while (this.status) {
	    try {
		// No socket given from server
		if (this.socket == null) {
		    this.socket = SelectorProvider.provider().openSocketChannel();

		    // Empfangspuffer auf 1MB setzen
		    this.socket.setOption(StandardSocketOptions.SO_RCVBUF, 1024 * 1024);
		    // Sendenpuffer auf 1MB setzen
		    this.socket.setOption(StandardSocketOptions.SO_SNDBUF, 1024 * 1024);

		    // socket.setOption(StandardSocketOptions.SO_LINGER, 1000);

		    // Blocking access
		    this.socket.configureBlocking(true);

		    this.socket.connect(new InetSocketAddress(this.host, this.port));

		    SEDAPExpressTCPClient.logger.logp(Level.INFO, "SEDAPExpressTCPClient", "run()", "Connected to " + this.socket.getRemoteAddress());

		} else {

		    if (this.socket.isConnected()) {
			SEDAPExpressTCPClient.logger.logp(Level.INFO, "SEDAPExpressTCPClient", "run()", "Connected to " + this.socket.getRemoteAddress());
		    } else {
			SEDAPExpressTCPClient.logger.logp(Level.INFO, "SEDAPExpressTCPClient", "run()", "Disconnected");
		    }
		}

		try (final BufferedReader br = new BufferedReader(Channels.newReader(this.socket, StandardCharsets.ISO_8859_1))) {

		    while (this.status) {
			String message = br.readLine();

			try {
			    if (message != null) {
				distributeReceivedSEDAPExpressMessage(SEDAPExpressMessage.deserialize(message));
			    } else {
				break;
			    }
			} catch (Exception e) {
			    SEDAPExpressTCPClient.logger.log(Level.SEVERE, "SEDAPExpressTCPClient, could not deserialize message: " + message, e);
			    this.status = this.socket.isConnected();
			    e.printStackTrace();
			}
		    }
		} catch (Exception e) {
		    SEDAPExpressTCPClient.logger.log(Level.SEVERE, "SEDAPExpressTCPClient: " + e.getLocalizedMessage(), e);
		    this.status = false;
		    if (this.clients != null) {
			this.clients.remove(this);
		    }
		}

	    } catch (Exception e) {

		SEDAPExpressTCPClient.logger.log(Level.SEVERE, "SEDAPExpressTCPClient: " + e.getLocalizedMessage(), e);
		this.status = false;
		if (this.clients != null) {
		    this.clients.remove(this);
		}
	    } finally {
		try {
		    this.socket.close();
		} catch (IOException e) {
		}
	    }
	}
    }

    @Override
    public boolean sendSEDAPExpressMessage(SEDAPExpressMessage message) {

	try {
	    this.socket.write(ByteBuffer.wrap(SEDAPExpressMessage.serialize(message).getBytes()));
	} catch (IOException e) {

	    e.printStackTrace();
	}
	return false;
    }

    public boolean isStatus() {

	return this.status;
    }

    @Override
    public void stopCommunicator() {

	this.status = false;

	try {
	    this.socket.close();
	} catch (IOException e) {
	}

	SEDAPExpressTCPClient.logger.logp(Level.INFO, "SEDAPExpressTCPClient", "stopCommunicator()", "Communicator stopped");
    }
}
