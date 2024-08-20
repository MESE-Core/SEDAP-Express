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

class TEXTTest {

    @Test
    final void testConstructorValues() {

	final COMMAND command = new COMMAND(
		(short) 55,
		641244434L,
		"8F3A",
		SEDAPExpressMessage.SECRET,
		SEDAPExpressMessage.ACKNOWLEDGE_YES,
		"4389F10D",
		"7D31",
		0x1111,
		COMMAND.CMDTYPE_Sync_time,
		Arrays.asList("10.8.0.6"));

	Assertions.assertEquals((short) 55, command.getNumber());
	Assertions.assertEquals(641244434L, command.getTime());
	Assertions.assertEquals("8F3A", command.getSender());
	Assertions.assertEquals(SEDAPExpressMessage.SECRET, command.getClassification());
	Assertions.assertEquals(SEDAPExpressMessage.ACKNOWLEDGE_YES, command.getAcknowledgement());
	Assertions.assertEquals("4389F10D", command.getMAC());
	Assertions.assertEquals("7D31", command.getRecipient());
	Assertions.assertEquals(0x1111, command.getCmdId());
	Assertions.assertEquals(COMMAND.CMDTYPE_Sync_time, command.getCmdType());
	Assertions.assertEquals("10.8.0.6", command.getCmdTypeDependentParameters().getFirst());
    }

    @Test
    final void testConstructorString() {

	String message = "TEXT;D3;661D44D2;324E;S;TRUE;;;1;NONE;\"This is an alert!\"";

	TEXT text = new TEXT(message);

	Assertions.assertEquals((short) 0xD3, text.getNumber());
	Assertions.assertEquals(0x661D44D2L, text.getTime());
	Assertions.assertEquals("324E", text.getSender());
	Assertions.assertEquals(SEDAPExpressMessage.SECRET, text.getClassification());
	Assertions.assertEquals(SEDAPExpressMessage.ACKNOWLEDGE_YES, text.getAcknowledgement());
	Assertions.assertNull(text.getMAC());
	Assertions.assertNull(text.getRecipient());
	Assertions.assertEquals(TEXT.TYPE_Alert, text.getType());
	Assertions.assertEquals(TEXT.ENCODING_NONE, text.getEncoding());
	Assertions.assertEquals("\"This is an alert!\"", text.getTextContent());

	message = "TEXT;D4;661D458E;324E;C;TRUE;;;2;NONE;\"This is a warning!\"";

	text = new TEXT(message);

	Assertions.assertEquals((short) 0xD4, text.getNumber());
	Assertions.assertEquals(0x661D458EL, text.getTime());
	Assertions.assertEquals("324E", text.getSender());
	Assertions.assertEquals(SEDAPExpressMessage.CONFIDENTIAL, text.getClassification());
	Assertions.assertEquals(SEDAPExpressMessage.ACKNOWLEDGE_YES, text.getAcknowledgement());
	Assertions.assertNull(text.getMAC());
	Assertions.assertNull(text.getRecipient());
	Assertions.assertEquals(TEXT.TYPE_Warning, text.getType());
	Assertions.assertEquals(TEXT.ENCODING_NONE, text.getEncoding());
	Assertions.assertEquals("\"This is a warning!\"", text.getTextContent());

	message = "TEXT;D5;661D6565;324E;R;;;;3;;\"This is a notice!\"";

	text = new TEXT(message);

	Assertions.assertEquals((short) 0xD5, text.getNumber());
	Assertions.assertEquals(0x661D6565L, text.getTime());
	Assertions.assertEquals("324E", text.getSender());
	Assertions.assertEquals(SEDAPExpressMessage.RESTRICTED, text.getClassification());
	Assertions.assertEquals(SEDAPExpressMessage.ACKNOWLEDGE_NO, text.getAcknowledgement());
	Assertions.assertNull(text.getMAC());
	Assertions.assertNull(text.getRecipient());
	Assertions.assertEquals(TEXT.TYPE_Notice, text.getType());
	Assertions.assertEquals(TEXT.ENCODING_NONE, text.getEncoding());
	Assertions.assertEquals("\"This is a notice!\"", text.getTextContent());

	message = "TEXT;D6;661D7032;324E;U;;;E4F1;4;BASE64;IlRoaXMgaXMgYSBjaGF0IG1lc3NhZ2UhIg==";

	text = new TEXT(message);

	Assertions.assertEquals((short) 0xD6, text.getNumber());
	Assertions.assertEquals(0x661D7032L, text.getTime());
	Assertions.assertEquals("324E", text.getSender());
	Assertions.assertEquals(SEDAPExpressMessage.UNCLAS, text.getClassification());
	Assertions.assertEquals(SEDAPExpressMessage.ACKNOWLEDGE_NO, text.getAcknowledgement());
	Assertions.assertNull(text.getMAC());
	Assertions.assertEquals("E4F1", text.getRecipient());
	Assertions.assertEquals(TEXT.TYPE_Chat, text.getType());
	Assertions.assertEquals(TEXT.ENCODING_BASE64, text.getEncoding());
	Assertions.assertEquals("\"This is a chat message!\"", text.getTextContent());
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
	Assertions.assertEquals(SEDAPExpressMessage.SECRET, command.getClassification());
	Assertions.assertEquals(SEDAPExpressMessage.ACKNOWLEDGE_YES, command.getAcknowledgement());
	Assertions.assertEquals("4389F10D", command.getMAC());
	Assertions.assertEquals("7D31", command.getRecipient());
	Assertions.assertEquals(0x2892, command.getCmdId());
	Assertions.assertEquals(COMMAND.CMDTYPE_Sync_time, command.getCmdType());
	Assertions.assertEquals("10.0.0.1", command.getCmdTypeDependentParameters().getFirst());
    }
}
