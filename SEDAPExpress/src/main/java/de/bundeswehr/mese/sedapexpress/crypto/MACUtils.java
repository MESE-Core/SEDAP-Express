/*******************************************************************************
 * Copyright (C)2012-2024, German Federal Armed Forces - All rights reserved.
 *
 * MUKdo II
 * Wibbelhofstraße 3
 * 26384 Wilhelmshaven
 * Germany
 *
 * This source code is part of the MEDAS/SEDAP Project.
 * Person of contact (POC): Volker Voß, MUKdo II A, Wilhelmshaven
 *
 * Unauthorized use, modification, redistributing, copying, selling and
 * printing of this file in source and binary form including accompanying
 * materials is STRICTLY prohibited.
 *
 * This source code and it's parts is classified as OFFEN / NATO UNCLASSIFIED!
 *******************************************************************************/
package de.bundeswehr.mese.sedapexpress.crypto;

import java.security.Security;
import java.util.HexFormat;
import java.util.zip.Adler32;

import org.bouncycastle.crypto.CipherParameters;
import org.bouncycastle.crypto.digests.SHA256Digest;
import org.bouncycastle.crypto.engines.AESLightEngine;
import org.bouncycastle.crypto.macs.CMac;
import org.bouncycastle.crypto.macs.GMac;
import org.bouncycastle.crypto.macs.HMac;
import org.bouncycastle.crypto.modes.GCMBlockCipher;
import org.bouncycastle.crypto.params.KeyParameter;
import org.bouncycastle.crypto.params.ParametersWithIV;

import de.bundeswehr.mese.sedapexpress.messages.CONTACT;
import de.bundeswehr.mese.sedapexpress.messages.OWNUNIT;
import de.bundeswehr.mese.sedapexpress.messages.SEDAPExpressMessage;

public class MACUtils {

    static {
	Security.addProvider(new org.bouncycastle.jce.provider.BouncyCastleProvider());
    }

    public static HexFormat hexFormatter = HexFormat.of().withUpperCase();

    /**
     *
     * @param key     Key for creating the MAC
     * @param message SEDAPExpress message
     *
     * @return SEDAPExpress message with set HMAC field
     */
    public static SEDAPExpressMessage setMACFieldWith32BitHMAC(final byte[] key, final SEDAPExpressMessage message) {

	message.setMAC(MACUtils.calc32BitHMAC(key, message));
	return message;

    }

    /**
     *
     * @param key     Key for creating the MAC
     * @param message SEDAPExpress message
     *
     * @return SEDAPExpress message with set HMAC field
     */
    public static SEDAPExpressMessage setMACFieldWith32BitCMAC(final byte[] key, final SEDAPExpressMessage message) {

	message.setMAC(MACUtils.calc32BitCMAC(key, message));
	return message;

    }

    /**
     *
     * @param key        Key for creating the MAC
     * @param initVector IV for creating the MAC
     * @param message    SEDAPExpress message
     *
     * @return SEDAPExpress message with set HMAC field
     */
    public static SEDAPExpressMessage setMACFieldWith32BitGMAC(final byte[] key, final byte[] initVector, final SEDAPExpressMessage message) {

	message.setMAC(MACUtils.calc32BitGMAC(key, initVector, message));
	return message;
    }

    public static String calcAdler32Checksum(String str) {
	Adler32 adler = new Adler32();
	adler.update(str.getBytes());
	return MACUtils.hexFormatter.toHexDigits(adler.getValue());
    }

    public static String calc32BitHMAC(final byte[] key, final SEDAPExpressMessage message) {

	return MACUtils.calcAdler32Checksum(MACUtils.calcHMAC(key, message));
    }

    /**
     *
     * @param key
     * @param message
     *
     * @return the HMAC of the message
     */
    public static String calcHMAC(final byte[] key, final SEDAPExpressMessage message) {

	message.setMAC("0000");

	final byte[] content = message.toString().getBytes();

	HMac hmac = new HMac(new SHA256Digest());
	hmac.init(new KeyParameter(key));
	hmac.update(content, 0, content.length);
	final byte[] generatedHMac = new byte[hmac.getMacSize()];
	hmac.doFinal(generatedHMac, 0);
	return MACUtils.hexFormatter.formatHex(generatedHMac);

    }

    public static String calc32BitCMAC(final byte[] key, final SEDAPExpressMessage message) {

	return MACUtils.calcAdler32Checksum(MACUtils.calcCMAC(key, message));
    }

    /**
     *
     * @param key
     * @param message
     *
     * @return the CMAC of the messaga
     */
    public static String calcCMAC(final byte[] key, final SEDAPExpressMessage message) {

	message.setMAC("0000");

	final byte[] content = message.toString().getBytes();

	final CipherParameters cipherParameters = new KeyParameter(key);
	final CMac cmac = new CMac(new AESLightEngine(), 32);
	cmac.init(cipherParameters);
	cmac.update(content, 0, content.length);
	final byte[] generatedCMac = new byte[cmac.getMacSize()];
	cmac.doFinal(generatedCMac, 0);

	return MACUtils.hexFormatter.formatHex(generatedCMac);
    }

    public static String calc32BitGMAC(final byte[] key, final byte[] initVector, final SEDAPExpressMessage message) {

	return MACUtils.calcAdler32Checksum(MACUtils.calcGMAC(key, initVector, message));
    }

    /**
     *
     * @param key
     * @param initVector
     * @param message
     *
     * @return the GMAC of the message
     */
    public static String calcGMAC(final byte[] key, final byte[] initVector, final SEDAPExpressMessage message) {

	message.setMAC("0000");

	final byte[] content = message.toString().getBytes();

	final CipherParameters cipherParameters = new KeyParameter(key);
	final ParametersWithIV cipherParametersWithInitVector = new ParametersWithIV(cipherParameters, initVector);

	final GMac gmac = new GMac(GCMBlockCipher.newInstance(new AESLightEngine()), 32);
	gmac.init(cipherParametersWithInitVector);
	gmac.update(content, 0, content.length);
	final byte[] generatedGMac = new byte[gmac.getMacSize()];
	gmac.doFinal(generatedGMac, 0);

	return MACUtils.hexFormatter.formatHex(generatedGMac);
    }

    /**
     *
     * @param key
     * @param message
     *
     * @return Status if the message is authentic
     */
    public static boolean isMessageAuthentic(final byte[] key, final SEDAPExpressMessage message) {

	return MACUtils.calcHMAC(key, message).equals(message.getMAC()) ||
		MACUtils.calcCMAC(key, message).equals(message.getMAC()) ||

		MACUtils.calc32BitHMAC(key, message).equals(message.getMAC()) ||
		MACUtils.calc32BitCMAC(key, message).equals(message.getMAC());

    }

    /**
     *
     * @param key        En-/Decryption key
     * @param initVector Initialisation vector (sometimes called IV), could be null
     * @param message    Message to prove
     *
     * @return Status if the message is authentic
     */
    public static boolean isMessageAuthentic(final byte[] key, final byte[] initVector, final SEDAPExpressMessage message) {

	if (initVector == null) {

	    return MACUtils.calcHMAC(key, message).equals(message.getMAC()) ||
		    MACUtils.calcCMAC(key, message).equals(message.getMAC()) ||

		    MACUtils.calc32BitHMAC(key, message).equals(message.getMAC()) ||
		    MACUtils.calc32BitCMAC(key, message).equals(message.getMAC());
	} else {

	    return MACUtils.calcGMAC(key, initVector, message).equals(message.getMAC()) ||
		    MACUtils.calc32BitGMAC(key, initVector, message).equals(message.getMAC());
	}

    }

    public static void main(final String[] args) {

	final CONTACT con = new CONTACT("CONTACT;01;18FC9EC4BFC;43962;P;;8888;0;54.46372567240966;9.922788177159617;;;;;;;;");
	System.err.println(con);

	final OWNUNIT own = new OWNUNIT("OWNUNIT;5E;02;18FC9EC4BAC;R;True;;53.32;8.11;0.0;5.5;21.0;22.0;33.0;44.0;FGS Bayern;sfspfclff------");
	System.err.println(own);
    }

}
