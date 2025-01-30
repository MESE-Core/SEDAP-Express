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

import javax.crypto.KeyGenerator;

import org.bouncycastle.crypto.digests.SHA256Digest;
import org.bouncycastle.crypto.macs.HMac;
import org.bouncycastle.crypto.prng.SP800SecureRandomBuilder;
import org.bouncycastle.jcajce.SecretKeyWithEncapsulation;
import org.bouncycastle.jcajce.spec.KEMExtractSpec;
import org.bouncycastle.jcajce.spec.KEMGenerateSpec;
import org.bouncycastle.pqc.jcajce.spec.KyberParameterSpec;

public class KyberUtils {

    private static SecureRandom random = new SP800SecureRandomBuilder().buildHMAC(new HMac(new SHA256Digest()), null, true);
    static {
	Security.addProvider(new org.bouncycastle.jce.provider.BouncyCastleProvider());
	Security.addProvider(new org.bouncycastle.pqc.jcajce.provider.BouncyCastlePQCProvider());
    }

    /**
     * Generates a key pair for the KyberKEM process
     * 
     * @param kyberParameterSpec
     * 
     * @return KeyPair for KyberKEM
     * 
     * @throws NoSuchAlgorithmException
     * @throws NoSuchProviderException
     * @throws InvalidAlgorithmParameterException
     */
    public static KeyPair generateKeyPair(KyberParameterSpec kyberParameterSpec) throws NoSuchAlgorithmException, NoSuchProviderException, InvalidAlgorithmParameterException {

	KeyPairGenerator keyPairGenerator = KeyPairGenerator.getInstance("Kyber", "BCPQC");
	keyPairGenerator.initialize(kyberParameterSpec, KyberUtils.random);

	return keyPairGenerator.generateKeyPair();
    }

    /**
     * Calculates the shared secret key with encapsulation
     * 
     * @param publicKey
     * 
     * @return shared secret key with encapsulation
     * 
     * @throws NoSuchAlgorithmException
     * @throws NoSuchProviderException
     * @throws InvalidAlgorithmParameterException
     */
    public static SecretKeyWithEncapsulation generateSharedSecretKeyWithEncapsulation(PublicKey publicKey, int bitSize) throws NoSuchAlgorithmException, NoSuchProviderException, InvalidAlgorithmParameterException {

	KeyGenerator keyGenerator = KeyGenerator.getInstance("Kyber", "BCPQC");
	KEMGenerateSpec kemGenerateSpec = new KEMGenerateSpec(publicKey, "Secret", bitSize);
	keyGenerator.init(kemGenerateSpec);

	return (SecretKeyWithEncapsulation) keyGenerator.generateKey();
    }

    /**
     * Calculates the shared secret key from encapsulation
     * 
     * @param privateKey
     * @param encapsulation
     * 
     * @return the shared secret key
     * 
     * @throws NoSuchAlgorithmException
     * @throws NoSuchProviderException
     * @throws InvalidAlgorithmParameterException
     */
    public static byte[] generateSharedSecretKeyFromEncapsulation(PrivateKey privateKey, byte[] encapsulation, int bitSize) throws NoSuchAlgorithmException, NoSuchProviderException, InvalidAlgorithmParameterException {

	KEMExtractSpec kemExtractSpec = new KEMExtractSpec(privateKey, encapsulation, "Secret", bitSize);
	KeyGenerator keyGenerator = KeyGenerator.getInstance("Kyber", "BCPQC");
	keyGenerator.init(kemExtractSpec);

	return ((SecretKeyWithEncapsulation) keyGenerator.generateKey()).getEncoded();
    }

}
