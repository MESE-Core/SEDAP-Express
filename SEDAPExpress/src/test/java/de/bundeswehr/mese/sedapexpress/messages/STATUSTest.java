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

import java.util.Arrays;
import java.util.Iterator;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import de.bundeswehr.mese.sedapexpress.messages.SEDAPExpressMessage.Acknowledgement;
import de.bundeswehr.mese.sedapexpress.messages.SEDAPExpressMessage.Classification;
import de.bundeswehr.mese.sedapexpress.messages.STATUS.CommandState;
import de.bundeswehr.mese.sedapexpress.messages.STATUS.OperationalState;
import de.bundeswehr.mese.sedapexpress.messages.STATUS.TechnicalState;

/**
 *
 * @author Volker Voß
 *
 */
class STATUSTest {

    @Test
    final void testConstructorValues() {

	final STATUS status = new STATUS(
		(short) 41,
		7865454543L,
		"BB91",
		Classification.CONFIDENTIAL,
		Acknowledgement.YES,
		"93B37ACC",
		TechnicalState.Operational,
		OperationalState.Operational,
		50.0,
		75.3,
		10.8,
		34,
		CommandState.Executed_successfully,
		"10.8.0.6",
		Arrays.asList("rtsp://10.8.0.6/stream1", "rtsp://10.8.0.6/stream2"),
		"This is a sample!");

	Assertions.assertEquals((short) 41, status.getNumber());
	Assertions.assertEquals(7865454543L, status.getTime());
	Assertions.assertEquals("BB91", status.getSender());
	Assertions.assertEquals(Classification.CONFIDENTIAL, status.getClassification());
	Assertions.assertEquals(Acknowledgement.YES, status.getAcknowledgement());
	Assertions.assertEquals("93B37ACC", status.getMAC());
	Assertions.assertEquals(TechnicalState.Operational, status.getTecState());
	Assertions.assertEquals(OperationalState.Operational, status.getOpsState());
	Assertions.assertEquals(50.0, status.getAmmunitionLevel());
	Assertions.assertEquals(75.3, status.getFuelLevel());
	Assertions.assertEquals(10.8, status.getBatterieLevel());
	Assertions.assertEquals("10.8.0.6", status.getHostname());
	Assertions.assertArrayEquals(new String[] { "rtsp://10.8.0.6/stream1", "rtsp://10.8.0.6/stream2" }, status.getMediaUrls().toArray());
	Assertions.assertEquals("This is a sample!", status.getFreeText());

    }

    @Test
    final void testConstructorString() {

	String message = "STATUS;41;50505050;BB91;C;TRUE;93B37ACC;2;1;20.3;30.4;40.5;MTAuOC4wLjY=;cnRzcDovLzEwLjguMC42L3N0cmVhbTE=#cnRzcDovLzEwLjguMC42L3N0cmVhbTI=;U2FtcGxlVGV4dCE=";

	final STATUS status = new STATUS(message);

	Assertions.assertEquals((short) 0x41, status.getNumber());
	Assertions.assertEquals(0x50505050L, status.getTime());
	Assertions.assertEquals("BB91", status.getSender());
	Assertions.assertEquals(Classification.CONFIDENTIAL, status.getClassification());
	Assertions.assertEquals(Acknowledgement.YES, status.getAcknowledgement());
	Assertions.assertEquals("93B37ACC", status.getMAC());
	Assertions.assertEquals(TechnicalState.Degraded, status.getTecState());
	Assertions.assertEquals(OperationalState.Degraded, status.getOpsState());
	Assertions.assertEquals(20.3, status.getAmmunitionLevel());
	Assertions.assertEquals(30.4, status.getFuelLevel());
	Assertions.assertEquals(40.5, status.getBatterieLevel());
	Assertions.assertEquals("10.8.0.6", status.getHostname());
	Assertions.assertArrayEquals(new String[] { "rtsp://10.8.0.6/stream1", "rtsp://10.8.0.6/stream2" }, status.getMediaUrls().toArray());
	Assertions.assertEquals("SampleText!", status.getFreeText());
    }

    @Test
    final void testConstructorIterator() {

	Iterator<String> it = Arrays
		.asList(
			"41",
			"A0B0C0D0",
			"BB91",
			"C",
			"TRUE",
			"93B37ACC",
			"2",
			"1",
			"20.3",
			"30.4",
			"40.5",
			"MTAuOC4wLjY=",
			"cnRzcDovLzEwLjguMC42L3N0cmVhbTE=#cnRzcDovLzEwLjguMC42L3N0cmVhbTI=",
			"U2FtcGxlVGV4dCE=")
		.iterator();

	final STATUS status = new STATUS(it);

	Assertions.assertEquals((short) 0x41, status.getNumber());
	Assertions.assertEquals(0xA0B0C0D0L, status.getTime());
	Assertions.assertEquals("BB91", status.getSender());
	Assertions.assertEquals(Classification.CONFIDENTIAL, status.getClassification());
	Assertions.assertEquals(Acknowledgement.YES, status.getAcknowledgement());
	Assertions.assertEquals("93B37ACC", status.getMAC());
	Assertions.assertEquals(TechnicalState.Degraded, status.getTecState());
	Assertions.assertEquals(OperationalState.Degraded, status.getOpsState());
	Assertions.assertEquals(20.3, status.getAmmunitionLevel());
	Assertions.assertEquals(30.4, status.getFuelLevel());
	Assertions.assertEquals(40.5, status.getBatterieLevel());
	Assertions.assertEquals("10.8.0.6", status.getHostname());
	Assertions.assertArrayEquals(new String[] { "rtsp://10.8.0.6/stream1", "rtsp://10.8.0.6/stream2" }, status.getMediaUrls().toArray());
	Assertions.assertEquals("SampleText!", status.getFreeText());

    }
}
