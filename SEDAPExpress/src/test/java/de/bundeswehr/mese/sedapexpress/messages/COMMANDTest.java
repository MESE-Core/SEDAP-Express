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

/**
 *
 * @author Volker Voß
 *
 */
class COMMANDTest {

    @Test
    final void testConstructorValues() {

	final COMMAND command = new COMMAND(
		(short) 55,
		641244434L,
		"8F3A",
		Classification.SECRET,
		Acknowledgement.YES,
		"4389F10D",
		"7D31",
		0x1111,
		COMMAND.CMDTYPE_Sync_time,
		Arrays.asList("10.8.0.6"));

	Assertions.assertEquals((short) 55, command.getNumber());
	Assertions.assertEquals(641244434L, command.getTime());
	Assertions.assertEquals("8F3A", command.getSender());
	Assertions.assertEquals(Classification.SECRET, command.getClassification());
	Assertions.assertEquals(Acknowledgement.YES, command.getAcknowledgement());
	Assertions.assertEquals("4389F10D", command.getMAC());
	Assertions.assertEquals("7D31", command.getRecipient());
	Assertions.assertEquals(0x1111, command.getCmdId());
	Assertions.assertEquals(COMMAND.CMDTYPE_Sync_time, command.getCmdType());
	Assertions.assertEquals("10.8.0.6", command.getCmdTypeDependentParameters().getFirst());
    }

    @Test
    final void testConstructorString() {

	String message = "COMMAND;55;1B351C87;5BCD;S;TRUE;4389F10D;7D31;1111;0D;hold-engagement;1000";

	COMMAND command = new COMMAND(message);

	Assertions.assertEquals((short) 0x55, command.getNumber());
	Assertions.assertEquals(0x1B351C87L, command.getTime());
	Assertions.assertEquals("5BCD", command.getSender());
	Assertions.assertEquals(Classification.SECRET, command.getClassification());
	Assertions.assertEquals(Acknowledgement.YES, command.getAcknowledgement());
	Assertions.assertEquals("4389F10D", command.getMAC());
	Assertions.assertEquals("7D31", command.getRecipient());
	Assertions.assertEquals(0x1111, command.getCmdId());
	Assertions.assertEquals(COMMAND.CMDTYPE_Engagement, command.getCmdType());
	Assertions.assertEquals(COMMAND.CMDTYPE_ENGAGEMENT_CMD_Hold, command.getCmdTypeDependentParameters().getFirst());
	Assertions.assertEquals("1000", command.getCmdTypeDependentParameters().getLast());

	message = "COMMAND;29;661D44C0;E4B3;C;TRUE;;Drone1;;FF;OPEN_BAY";

	command = new COMMAND(message);

	Assertions.assertEquals((short) 0x29, command.getNumber());
	Assertions.assertEquals(0x661D44C0L, command.getTime());
	Assertions.assertEquals("E4B3", command.getSender());
	Assertions.assertEquals(Classification.CONFIDENTIAL, command.getClassification());
	Assertions.assertEquals(Acknowledgement.YES, command.getAcknowledgement());
	Assertions.assertEquals(null, command.getMAC());
	Assertions.assertEquals("Drone1", command.getRecipient());
	Assertions.assertEquals(0xFF, command.getCmdType());
	Assertions.assertEquals("OPEN_BAY", command.getCmdTypeDependentParameters().getFirst());

    }

    @Test
    final void testConstructorIterator() {

	Iterator<String> it = Arrays
		.asList(
			"55",
			"1B351C87",
			"5BCD",
			"S",
			"TRUE",
			"4389F10D",
			"7D31",
			"2892",
			"4",
			"10.0.0.1")
		.iterator();

	final COMMAND command = new COMMAND(it);

	Assertions.assertEquals((short) 0x55, command.getNumber());
	Assertions.assertEquals(0x1B351C87L, command.getTime());
	Assertions.assertEquals("5BCD", command.getSender());
	Assertions.assertEquals(Classification.SECRET, command.getClassification());
	Assertions.assertEquals(Acknowledgement.YES, command.getAcknowledgement());
	Assertions.assertEquals("4389F10D", command.getMAC());
	Assertions.assertEquals("7D31", command.getRecipient());
	Assertions.assertEquals(0x2892, command.getCmdId());
	Assertions.assertEquals(COMMAND.CMDTYPE_Sync_time, command.getCmdType());
	Assertions.assertEquals("10.0.0.1", command.getCmdTypeDependentParameters().getFirst());
    }

}
