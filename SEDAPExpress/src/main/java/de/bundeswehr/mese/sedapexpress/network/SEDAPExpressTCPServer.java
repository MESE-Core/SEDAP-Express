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

import java.net.InetSocketAddress;
import java.nio.channels.ServerSocketChannel;
import java.util.concurrent.ConcurrentLinkedDeque;
import java.util.logging.Level;
import java.util.logging.Logger;

import de.bundeswehr.mese.sedapexpress.messages.SEDAPExpressMessage;

/**
 * TCP receiver/sender class for SEDAP-Express
 *
 * @author Volker Voß
 *
 */
public class SEDAPExpressTCPServer extends SEDAPExpressCommunicator implements Runnable {

    protected static Logger logger = Logger.getLogger(Logger.GLOBAL_LOGGER_NAME);
    static {
	SEDAPExpressTCPServer.logger.setLevel(Level.ALL);
    }

    private final String intf;
    private final int port;

    private boolean status = true;

    private final ConcurrentLinkedDeque<SEDAPExpressTCPClient> clients = new ConcurrentLinkedDeque<SEDAPExpressTCPClient>();

    /**
     * Instantiate a new SEDAP-Express TCP Server on the given interface
     *
     * @param intf Interface to bind to
     * @param port Port to be used
     */
    public SEDAPExpressTCPServer(String intf, int port) {

	super();

	this.intf = intf;
	this.port = port;

	new Thread(this).start();
    }

    /**
     * Instantiate a new SEDAP-Express TCP Client
     *
     * @param port Port to be used
     */
    public SEDAPExpressTCPServer(int port) {
	this("0.0.0.0", port);
    }

    @Override
    public void run() {
	while (this.status) {

	    try (ServerSocketChannel serverSocket = ServerSocketChannel.open()) {
		serverSocket.configureBlocking(true);
		serverSocket.bind(new InetSocketAddress(this.intf, this.port));

		SEDAPExpressTCPServer.logger.logp(Level.INFO, "SEDAPExpressTCPServer", "run()", "Listening on port: " + this.port);
		SEDAPExpressTCPClient newClient = new SEDAPExpressTCPClient(serverSocket.accept(), this.clients, this.subscriptions);
		this.clients.add(newClient);

	    } catch (Exception e) {
		e.printStackTrace();
		this.status = false;
		SEDAPExpressTCPServer.logger.logp(Level.SEVERE, "SEDAPExpressTCPServer", "run()", "Could not listening on port: " + this.port, e);
	    }
	}

    }

    @Override
    public boolean sendSEDAPExpressMessage(SEDAPExpressMessage message) {

	this.clients.forEach(client -> {

	    if (client.isStatus()) {
		client.sendSEDAPExpressMessage(message);
	    }

	});

	return false;
    }
}
