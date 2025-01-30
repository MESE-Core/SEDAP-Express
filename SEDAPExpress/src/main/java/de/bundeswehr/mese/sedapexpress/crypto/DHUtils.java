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

import java.math.BigInteger;
import java.security.AlgorithmParameterGenerator;
import java.security.AlgorithmParameters;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.SecureRandom;
import java.security.Security;
import java.security.spec.InvalidParameterSpecException;

import javax.crypto.KeyAgreement;
import javax.crypto.spec.DHParameterSpec;

import org.bouncycastle.crypto.digests.SHA256Digest;
import org.bouncycastle.crypto.macs.HMac;
import org.bouncycastle.crypto.prng.SP800SecureRandomBuilder;
import org.bouncycastle.jce.provider.BouncyCastleProvider;

public class DHUtils {

    private static SecureRandom random = new SP800SecureRandomBuilder().buildHMAC(new HMac(new SHA256Digest()), null, true);
    static {
	Security.addProvider(new org.bouncycastle.jce.provider.BouncyCastleProvider());
    }

    /**
     * Generate key generation Diffie-Hellman-Merkel parameters ("p" and "g" variables)
     *
     * @param bitLength
     *
     * @return Diffie-Hellman-Merkel parameters
     * 
     * @throws InvalidParameterSpecException
     * @throws NoSuchProviderException
     * @throws NoSuchAlgorithmException
     */
    public static DHParameterSpec generateVariables(Integer bitLength) throws InvalidParameterSpecException, NoSuchAlgorithmException, NoSuchProviderException {

	AlgorithmParameterGenerator algGen = AlgorithmParameterGenerator.getInstance("DH", BouncyCastleProvider.PROVIDER_NAME);
	algGen.init(bitLength, DHUtils.random);
	AlgorithmParameters algoParams = algGen.generateParameters();

	return algoParams.getParameterSpec(DHParameterSpec.class);

    }

    /**
     * Generate key generation Diffie-Hellman-Merkel parameters using given "p" and "g" variables
     *
     * @param bitLength
     * @param p
     * @param g
     *
     * @return Diffie-Hellman-Merkel parameters
     * 
     * @throws InvalidParameterSpecException
     * @throws NoSuchProviderException
     * @throws NoSuchAlgorithmException
     */
    public static DHParameterSpec generateVariables(Integer bitLength, BigInteger p, BigInteger g) throws InvalidParameterSpecException, NoSuchAlgorithmException, NoSuchProviderException {

	return new DHParameterSpec(p, g, bitLength);

    }

    /**
     * Generates a key pair for the Diffie-Hellman-Merkle process
     * 
     * @param parameterSpec
     *
     * @return Key pair
     *
     * @throws NoSuchAlgorithmException
     * @throws NoSuchProviderException
     * @throws InvalidParameterSpecException
     * @throws InvalidAlgorithmParameterException
     */
    public static KeyPair generateKeyPair(DHParameterSpec parameterSpec) throws NoSuchAlgorithmException, NoSuchProviderException, InvalidParameterSpecException, InvalidAlgorithmParameterException {

	KeyPairGenerator clientKeyPairGenerator = KeyPairGenerator.getInstance("DH", BouncyCastleProvider.PROVIDER_NAME);
	clientKeyPairGenerator.initialize(parameterSpec);

	return clientKeyPairGenerator.generateKeyPair();

    }

    /**
     * Calculates the shared secret key
     *
     * @param ownSecretKey   the own secret key
     * @param otherPublicKey the public key of the other side
     * 
     * @return the shared secret key
     *
     * @throws NoSuchProviderException
     * @throws NoSuchAlgorithmException
     * @throws InvalidKeyException
     */
    public static byte[] getSharedSecretKey(PrivateKey ownSecretKey, PublicKey otherPublicKey) throws NoSuchAlgorithmException, NoSuchProviderException, InvalidKeyException {

	KeyAgreement serverKeyAgreement = KeyAgreement.getInstance("DH", BouncyCastleProvider.PROVIDER_NAME);
	serverKeyAgreement.init(ownSecretKey);
	serverKeyAgreement.doPhase(otherPublicKey, true);

	return serverKeyAgreement.generateSecret();
    }

}
