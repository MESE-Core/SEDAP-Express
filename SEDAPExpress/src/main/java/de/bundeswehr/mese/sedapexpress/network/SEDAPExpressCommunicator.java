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

import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.HexFormat;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

import de.bundeswehr.mese.sedapexpress.messages.SEDAPExpressMessage;
import de.bundeswehr.mese.sedapexpress.messages.SEDAPExpressMessage.MessageType;
import de.bundeswehr.mese.sedapexpress.processing.SEDAPExpressSubscriber;

/**
 *
 * @author Volker Voß
 *
 */
public abstract class SEDAPExpressCommunicator {

    public abstract boolean sendSEDAPExpressMessage(SEDAPExpressMessage message);

    protected ConcurrentHashMap<MessageType, Set<SEDAPExpressSubscriber>> subscriptions = new ConcurrentHashMap<>();

    /**
     * Subscribe one or more message types
     *
     * @param subscriber the subscriber for the message types
     * @param clazzes    Collection of message types which should be subscribed
     */
    public void subscribeMessages(SEDAPExpressSubscriber subscriber, Collection<MessageType> clazzes) {

	clazzes.forEach(clazz -> {

	    this.subscriptions.computeIfAbsent(clazz, key -> Collections.synchronizedSet(new HashSet<SEDAPExpressSubscriber>()));

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
	    this.subscriptions.get(message.getMessageType()).forEach(subscriber -> subscriber.processSEDAPExpressMessage(message));
	}
    }

    public String createSenderId() {

	return HexFormat.of().toHexDigits((short) Math.round(Math.random() * 65535));
    }

    public abstract void stopCommunicator();
}
