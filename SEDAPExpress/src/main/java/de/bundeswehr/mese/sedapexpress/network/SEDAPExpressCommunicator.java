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
import java.lang.ProcessBuilder.Redirect;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.HexFormat;
import java.util.Set;
import java.util.TimeZone;
import java.util.concurrent.ConcurrentHashMap;
import java.util.logging.Level;
import java.util.logging.Logger;

import de.bundeswehr.mese.sedapexpress.messages.SEDAPExpressMessage;
import de.bundeswehr.mese.sedapexpress.messages.SEDAPExpressMessage.Acknowledgement;
import de.bundeswehr.mese.sedapexpress.messages.SEDAPExpressMessage.MessageType;
import de.bundeswehr.mese.sedapexpress.messages.TIMESYNC;
import de.bundeswehr.mese.sedapexpress.processing.SEDAPExpressSubscriber;

/**
 *
 * @author Volker Voß
 *
 */
public abstract class SEDAPExpressCommunicator {

    protected static Logger logger = Logger.getLogger(Logger.GLOBAL_LOGGER_NAME);
    static {
	SEDAPExpressCommunicator.logger.setLevel(Level.ALL);
    }

    public short timesyncNumber = 0;

    public abstract boolean sendSEDAPExpressMessage(SEDAPExpressMessage message) throws IOException;

    protected ConcurrentHashMap<MessageType, Set<SEDAPExpressSubscriber>> subscriptions = new ConcurrentHashMap<>();

    /**
     * Subscribe one or more message types
     *
     * @param subscriber the subscriber for the message types
     * @param clazzes    Collection of message types which should be subscribed
     */
    public void subscribeMessages(SEDAPExpressSubscriber subscriber, Collection<MessageType> clazzes) {

	clazzes.forEach(clazz -> {

	    this.subscriptions.computeIfAbsent(clazz,
		    key -> Collections.synchronizedSet(new HashSet<SEDAPExpressSubscriber>()));

	    this.subscriptions.get(clazz).add(subscriber);
	});

    }

    /**
     * Subscribe one or more message types
     *
     * @param subscriber the subscriber for the message types
     * @param clazzes    Collection of message types which should be subscribed
     */
    public void subscribeMessages(SEDAPExpressSubscriber subscriber, MessageType... clazzes) {

	subscribeMessages(subscriber, Arrays.asList(clazzes));

    }

    /**
     * Unsubscribe one or more message types
     *
     * @param subscriber the original subscriber of the message types
     * @param clazzes    Collection of message types which should be unsubscribe
     */
    public void unsubscribeMessages(SEDAPExpressSubscriber subscriber, Collection<MessageType> clazzes) {
	clazzes.forEach(clazz ->

	this.subscriptions.computeIfPresent(clazz, (key, value) -> {
	    value.remove(subscriber);
	    return value;
	}));
    }

    /**
     * Unsubscribe one or more message types
     *
     * @param subscriber the original subscriber of the message types
     * @param clazzes    Arrays of message types which should be unsubscribe
     */
    public void unsubscribeMessages(SEDAPExpressSubscriber subscriber, MessageType... clazzes) {

	unsubscribeMessages(subscriber, Arrays.asList(clazzes));
    }

    /**
     * Distribute a message to the subscribers
     *
     * @param message
     */
    protected void distributeReceivedSEDAPExpressMessage(SEDAPExpressMessage message) {

	if ((message != null) && this.subscriptions.containsKey(message.getMessageType())) {
	    this.subscriptions.get(message.getMessageType())
		    .forEach(subscriber -> subscriber.processSEDAPExpressMessage(message));
	}
    }

    /**
     * Let the communicator establish a connection
     * 
     * @return Result of the connection attempt
     */
    public abstract boolean connect();

    class TimeSyncRunnable extends Thread implements SEDAPExpressSubscriber {

	TIMESYNC timesyncAnswer;

	@Override
	public void run() {
	    SEDAPExpressCommunicator.logger.logp(Level.INFO, "SEDAPExpressCommunicator", "doTimesync()",
		    "Started time synchronization process...");

	    this.timesyncAnswer = null;

	    if (SEDAPExpressCommunicator.this.timesyncNumber == 0x7F)
		SEDAPExpressCommunicator.this.timesyncNumber = 0;

	    try {
		sendSEDAPExpressMessage(
			new TIMESYNC(SEDAPExpressCommunicator.this.timesyncNumber++, System.currentTimeMillis(),
				SEDAPExpressCommunicator.this.createSenderId(), null, Acknowledgement.NO, null));
	    } catch (IOException e) {

		SEDAPExpressCommunicator.logger.logp(Level.SEVERE, "SEDAPExpressCommunicator", "doTimesync()",
			"Could not send TIMESYNC message", e);
	    }

	    while (this.timesyncAnswer == null) {
		try {
		    Thread.sleep(1);
		} catch (InterruptedException e) {
		}
	    }

	    // Setting system time if the rights of the user permits it
	    final Calendar calendar = Calendar.getInstance(TimeZone.getDefault());

	    // Windows
	    if (System.getProperty("os.name").startsWith("Windows")) {
		try {

		    final Process p1 = new ProcessBuilder("cmd", "/C", "time",
			    calendar.get(Calendar.HOUR_OF_DAY) + ":" + calendar.get(Calendar.MINUTE) + ":"
				    + calendar.get(Calendar.SECOND) + "." + (calendar.get(Calendar.MILLISECOND) / 10))
			    .redirectError(Redirect.DISCARD).redirectOutput(Redirect.DISCARD).start();
		    p1.waitFor();

		    final Process p2 = new ProcessBuilder("cmd", "/C", "date",
			    calendar.get(Calendar.DAY_OF_MONTH) + "-" + (calendar.get(Calendar.MONTH) + 1) + "-"
				    + calendar.get(Calendar.YEAR))
			    .redirectError(Redirect.DISCARD).redirectOutput(Redirect.DISCARD).start();
		    p2.waitFor();

		    if ((p1.exitValue() == 0) && (p2.exitValue() == 0)) {
			SEDAPExpressCommunicator.logger.logp(Level.INFO, "SEDAPExpressCommunicator", "doTimesync()",
				"New system time: " + new SimpleDateFormat("MMM dd yyy HH:mm:ss.SSS")
					.format(System.currentTimeMillis()) + "\"");
			SEDAPExpressCommunicator.logger.logp(Level.INFO, "SEDAPExpressCommunicator", "doTimesync()",
				"Time sync successfully!");
		    } else {

			SEDAPExpressCommunicator.logger.logp(Level.SEVERE, "SEDAPExpressCommunicator", "doTimesync()",
				"Could not set time! No standard Windows or no rights!?");
			// System.err.println("Exitcode1: " + p1.exitValue());
			// System.err.println("Exitcode2: " + p2.exitValue());
		    }

		} catch (final Exception e1) {

		    SEDAPExpressCommunicator.logger.logp(Level.SEVERE, "SEDAPExpressCommunicator", "doTimesync()",
			    "Could not set time! No standard Windows or no rights!?");

		}

	    } else { // Unixodide e.g. Linux, BSD
		try {

		    final Process p = new ProcessBuilder("/usr/bin/date", "-s",
			    new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSZ").format(calendar.getTimeInMillis()))
			    .redirectError(Redirect.DISCARD).redirectOutput(Redirect.DISCARD).start();
		    p.waitFor();

		    if (p.exitValue() == 0) {
			SEDAPExpressCommunicator.logger.logp(Level.INFO, "SEDAPExpressCommunicator", "doTimesync()",
				"New system time: " + new SimpleDateFormat("MMM dd yyy HH:mm:ss.SSS")
					.format(System.currentTimeMillis()) + "\"");
			SEDAPExpressCommunicator.logger.logp(Level.INFO, "SEDAPExpressCommunicator", "doTimesync()",
				"Time sync successfully!");
		    } else {

			SEDAPExpressCommunicator.logger.logp(Level.SEVERE, "SEDAPExpressCommunicator", "doTimesync()",
				"Could not set time! No standard Linux or no rights!?");
		    }

		} catch (final Exception e2) {

		    SEDAPExpressCommunicator.logger.logp(Level.SEVERE, "SEDAPExpressCommunicator", "doTimesync()",
			    "Could not set time! No standard Linux or no rights!?");

		}
	    }
	}

	@Override
	public void processSEDAPExpressMessage(SEDAPExpressMessage message) {
	    this.timesyncAnswer = (TIMESYNC) message;
	}
    };

    /**
     * Do a time synchronization via TIMESYNC message. (Please notice, that this
     * requiries the right on the system to change the system time, which is
     * disabled by default on windows system)
     */
    public void doTimesync() {

	new TimeSyncRunnable().start();
    }

    public String createSenderId() {

	return HexFormat.of().toHexDigits((short) Math.round(Math.random() * 65535));
    }

    public abstract void stopCommunicator();

    public abstract Exception getLastException();

}
