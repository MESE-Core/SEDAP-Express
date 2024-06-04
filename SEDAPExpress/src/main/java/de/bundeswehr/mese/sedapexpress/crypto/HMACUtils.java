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

import org.bouncycastle.crypto.CipherParameters;
import org.bouncycastle.crypto.engines.AESLightEngine;
import org.bouncycastle.crypto.macs.CMac;
import org.bouncycastle.crypto.macs.GMac;
import org.bouncycastle.crypto.modes.GCMBlockCipher;
import org.bouncycastle.crypto.params.KeyParameter;
import org.bouncycastle.crypto.params.ParametersWithIV;

import de.bundeswehr.mese.sedapexpress.messages.CONTACT;
import de.bundeswehr.mese.sedapexpress.messages.OWNUNIT;
import de.bundeswehr.mese.sedapexpress.messages.SEDAPExpressMessage;

public class HMACUtils {

    static {
	Security.addProvider(new org.bouncycastle.jce.provider.BouncyCastleProvider());
    }

    /**
     *
     * @param key
     *            Key for creating the HMAC
     * @param message
     *            SEDAPExpress message
     *
     * @return SEDAPExpress message with set HMAC field
     */
    public static SEDAPExpressMessage setHMACFieldWith32BitCMAC(final byte[] key, final SEDAPExpressMessage message) {

	message.setHMAC(HMACUtils.calc32BitCMAC(key, message));
	return message;

    }

    /**
     *
     * @param key
     *            Key for creating the HMAC
     * @param initVector
     *            IV for creating the HMAC
     * @param message
     *            SEDAPExpress message
     *
     * @return SEDAPExpress message with set HMAC field
     */
    public static SEDAPExpressMessage setHMACFieldWith32BitGMAC(final byte[] key, final byte[] initVector, final SEDAPExpressMessage message) {

	message.setHMAC(HMACUtils.calc32BitGMAC(key, initVector, message));
	return message;
    }

    /**
     *
     * @param key
     * @param message
     * @return
     */
    public static int calc32BitCMAC(final byte[] key, final SEDAPExpressMessage message) {

	final CipherParameters cipherParameters = new KeyParameter(key);

	message.setHMAC(0);
	final byte[] content = message.toString().getBytes();

	final CMac cmac = new CMac(new AESLightEngine(), 32);
	cmac.init(cipherParameters);
	cmac.update(content, 0, content.length);
	final byte[] generatedCMac = new byte[cmac.getMacSize()];
	cmac.doFinal(generatedCMac, 0);

	return (generatedCMac[0] << 24) + (generatedCMac[1] << 16) + (generatedCMac[2] << 8) + generatedCMac[3];
    }

    /**
     *
     * @param key
     * @param initVector
     * @param message
     * @return
     */
    public static int calc32BitGMAC(final byte[] key, final byte[] initVector, final SEDAPExpressMessage message) {

	final CipherParameters cipherParameters = new KeyParameter(key);
	final ParametersWithIV cipherParametersWithInitVector = new ParametersWithIV(cipherParameters, initVector);

	message.setHMAC(0);
	final byte[] content = message.toString().getBytes();

	final GMac gmac = new GMac(GCMBlockCipher.newInstance(new AESLightEngine()), 32);
	gmac.init(cipherParametersWithInitVector);
	gmac.update(content, 0, content.length);
	final byte[] generatedGMac = new byte[gmac.getMacSize()];
	gmac.doFinal(generatedGMac, 0);

	return (generatedGMac[0] << 24) + (generatedGMac[1] << 16) + (generatedGMac[2] << 8) + generatedGMac[3];
    }

    /**
     *
     * @param key
     * @param message
     *
     * @return Status if the message is authentic
     */
    public static boolean isMessageAuthentic(final byte[] key, final SEDAPExpressMessage message) {

	return message.getHMAC() == HMACUtils.calc32BitCMAC(key, message);

    }

    /**
     *
     * @param key
     * @param initVector
     * @param message
     *
     * @return Status if the message is authentic
     */
    public static boolean isMessageAuthentic(final byte[] key, final byte[] initVector, final SEDAPExpressMessage message) {

	if (initVector == null) {
	    return message.getHMAC() == HMACUtils.calc32BitCMAC(key, message);
	} else {
	    return message.getHMAC() == HMACUtils.calc32BitGMAC(key, initVector, message);
	}

    }

    public static void main(final String[] args) {

	final CONTACT con = new CONTACT("CONTACT;01;18FC9EC4BFC;43962;P;;8888;0;54.46372567240966;9.922788177159617;;;;;;;;");
	System.err.println(con);

	final OWNUNIT own = new OWNUNIT("OWNUNIT;5E;02;18FC9EC4BAC;R;True;;53.32;8.11;0.0;5.5;21.0;22.0;33.0;44.0;FGS Bayern;sfspfclff------");
	System.err.println(own);
    }

}
