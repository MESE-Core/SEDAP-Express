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

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class ECDHUtilsTest {

    @Test
    void testKeyGeneration() throws InvalidParameterSpecException, NoSuchAlgorithmException, NoSuchProviderException, InvalidAlgorithmParameterException, InvalidKeyException, InvalidKeySpecException {

	// Client Key (private / public)
	KeyPair clientKeyPair = ECDHUtils.generateKeyPair();

	// Server Key (private / public)
	KeyPair serverKeyPair = ECDHUtils.generateKeyPair();

	// Client
	byte[] secretClient = ECDHUtils.getSharedSecret(clientKeyPair.getPrivate(), serverKeyPair.getPublic());

	// Server
	byte[] secretServer = ECDHUtils.getSharedSecret(serverKeyPair.getPrivate(), clientKeyPair.getPublic());

	Assertions.assertArrayEquals(secretClient, secretServer);

	System.out.println("Public key client: " + HexFormat.of().withUpperCase().formatHex(clientKeyPair.getPublic().getEncoded()));
	System.out.println("Public key server: " + HexFormat.of().withUpperCase().formatHex(serverKeyPair.getPublic().getEncoded()));
	System.out.println();
	System.out.println("Secret client: " + HexFormat.of().withUpperCase().formatHex(secretClient));
	System.out.println("Secret sever:  " + HexFormat.of().withUpperCase().formatHex(secretServer));
    }
}
