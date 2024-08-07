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
package de.bundeswehr.mese.sedapexpress.crypto;

import java.security.InvalidAlgorithmParameterException;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.SecureRandom;
import java.security.Security;
import java.security.spec.AlgorithmParameterSpec;
import java.util.Base64;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.spec.SecretKeySpec;

import org.bouncycastle.jcajce.SecretKeyWithEncapsulation;
import org.bouncycastle.jcajce.spec.KEMExtractSpec;
import org.bouncycastle.jcajce.spec.KEMGenerateSpec;
import org.bouncycastle.jce.provider.BouncyCastleProvider;
import org.bouncycastle.pqc.jcajce.provider.BouncyCastlePQCProvider;
import org.bouncycastle.pqc.jcajce.spec.KyberParameterSpec;
import org.bouncycastle.util.encoders.Hex;

public class Kyber {

    private static final String KEM_ALGORITHM = "Kyber";
    private static final AlgorithmParameterSpec KEM_PARAMETER_SPEC = KyberParameterSpec.kyber512;
    private static final String PROVIDER = "BCPQC";
    private static final String ENCRYPTION_ALGORITHM = "AES";
    private static final String MODE_PADDING = "AES/ECB/PKCS5Padding"; // ECB mode with PKCS5 padding

    public static String encrypt(String plainText, byte[] key) throws Exception {
	SecretKeySpec secretKey = new SecretKeySpec(key, Kyber.ENCRYPTION_ALGORITHM);
	Cipher cipher = Cipher.getInstance(Kyber.MODE_PADDING);
	cipher.init(Cipher.ENCRYPT_MODE, secretKey);
	byte[] encryptedBytes = cipher.doFinal(plainText.getBytes());
	return Base64.getEncoder().encodeToString(encryptedBytes);
    }

    public static String decrypt(String encryptedText, byte[] key) throws Exception {
	SecretKeySpec secretKey = new SecretKeySpec(key, Kyber.ENCRYPTION_ALGORITHM);
	Cipher cipher = Cipher.getInstance(Kyber.MODE_PADDING);
	cipher.init(Cipher.DECRYPT_MODE, secretKey);
	byte[] decodedBytes = Base64.getDecoder().decode(encryptedText);
	byte[] decryptedBytes = cipher.doFinal(decodedBytes);
	return new String(decryptedBytes);
    }

    private static KeyPair generateKeyPair()
	    throws NoSuchAlgorithmException, NoSuchProviderException, InvalidAlgorithmParameterException {
	// generate key pair
	KeyPairGenerator keyPairGenerator = KeyPairGenerator.getInstance(Kyber.KEM_ALGORITHM, Kyber.PROVIDER);
	keyPairGenerator.initialize(Kyber.KEM_PARAMETER_SPEC, new SecureRandom());
	return keyPairGenerator.generateKeyPair();
    }

    private static SecretKeyWithEncapsulation generateSecretKeySender(PublicKey publicKey)
	    throws NoSuchAlgorithmException, NoSuchProviderException, InvalidAlgorithmParameterException {

	// You will request a key generator
	KeyGenerator keyGenerator = KeyGenerator.getInstance(Kyber.KEM_ALGORITHM, Kyber.PROVIDER);
	// You will set up a KEM Generate Spec with the public key
	KEMGenerateSpec kemGenerateSpec = new KEMGenerateSpec(publicKey, "Secret");
	// Now you can initialize the key generator with the kem generate spec and
	// generate out share secret
	keyGenerator.init(kemGenerateSpec);
	return (SecretKeyWithEncapsulation) keyGenerator.generateKey();
    }

    private static SecretKeyWithEncapsulation generateSecretKeyReciever(PrivateKey privateKey, byte[] encapsulation)
	    throws NoSuchAlgorithmException, NoSuchProviderException, InvalidAlgorithmParameterException {

	// You will set up a KEM Extract Spec with the receiver private key - in this
	// case you need the encapsulation
	// generated by the receiver as well.
	KEMExtractSpec kemExtractSpec = new KEMExtractSpec(privateKey, encapsulation, "Secret");

	// You will request a key generator
	KeyGenerator keyGenerator = KeyGenerator.getInstance(Kyber.KEM_ALGORITHM, Kyber.PROVIDER);
	// Now you can initialize the key generator with the kem extract spec and
	// retrieve the KEM secret.
	keyGenerator.init(kemExtractSpec);

	return (SecretKeyWithEncapsulation) keyGenerator.generateKey();
    }

    public static void main(String[] args) throws Exception {

	// Let's add the required providers for this exercise
	// the regular Bouncy Castle provider for ECDHC
	Security.addProvider(new BouncyCastleProvider());
	// the Bouncy Castle post quantum provider for the PQC KEM.
	Security.addProvider(new BouncyCastlePQCProvider());

	// Generating a key pair for receiver
	KeyPair keyPair = Kyber.generateKeyPair();

	System.out.println("KEM Algorithm: " + keyPair.getPublic().getAlgorithm());
	System.out.println("Public Key length: " + keyPair.getPublic().getEncoded().length);
	System.out.println("Private Key length: " + keyPair.getPrivate().getEncoded().length);

	SecretKeyWithEncapsulation initKeyWithEnc = Kyber.generateSecretKeySender(keyPair.getPublic());
	byte[] encapsulation = initKeyWithEnc.getEncapsulation();

	System.out.println("Shared Secret created by Sender: " + Hex.toHexString(initKeyWithEnc.getEncoded()));
	System.out.println("Length of encapsulated shared secret: " + encapsulation.length);

	String originalText = "This is a secret message.";
	System.out.println("Original Text: " + originalText);

	String encryptedText = Kyber.encrypt(originalText, initKeyWithEnc.getEncoded());
	System.out.println("Encrypted Text: " + encryptedText);

	SecretKeyWithEncapsulation recKeyWithEnc = Kyber.generateSecretKeyReciever(keyPair.getPrivate(), encapsulation);

	System.out.println("Shared Secret decapsulated by Receiver: " + Hex.toHexString(recKeyWithEnc.getEncoded()));

	String decryptedText = Kyber.decrypt(encryptedText, recKeyWithEnc.getEncoded());
	System.out.println("Decrypted Text: " + decryptedText);
    }

}