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

import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.KeyPair;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.InvalidParameterSpecException;
import java.util.HexFormat;

import javax.crypto.spec.DHParameterSpec;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class DHUtilsTest {

    @Test
    void testKeyGeneration() throws InvalidParameterSpecException, NoSuchAlgorithmException, NoSuchProviderException, InvalidAlgorithmParameterException, InvalidKeyException, InvalidKeySpecException {

	DHParameterSpec params = DHUtils.generateVariable(256);

	// Client Key (private / public)
	KeyPair clientKeyPair = DHUtils.generateKeyPair(params);

	// Server Key (private / public)
	KeyPair serverKeyPair = DHUtils.generateKeyPair(params);

	// Client
	byte[] secretClient = DHUtils.getSharedSecret(clientKeyPair.getPrivate(), serverKeyPair.getPublic());

	// Server
	byte[] secretServer = DHUtils.getSharedSecret(serverKeyPair.getPrivate(), clientKeyPair.getPublic());

	Assertions.assertArrayEquals(secretClient, secretServer);

	System.out.println("p: " + params.getP());
	System.out.println("g: " + params.getG());
	System.out.println();
	System.out.println("Public key client: " + HexFormat.of().withUpperCase().formatHex(clientKeyPair.getPublic().getEncoded()));
	System.out.println("Public key server: " + HexFormat.of().withUpperCase().formatHex(serverKeyPair.getPublic().getEncoded()));
	System.out.println();
	System.out.println("Secret client: " + HexFormat.of().withUpperCase().formatHex(secretClient));
	System.out.println("Secret sever:  " + HexFormat.of().withUpperCase().formatHex(secretServer));

    }
}
