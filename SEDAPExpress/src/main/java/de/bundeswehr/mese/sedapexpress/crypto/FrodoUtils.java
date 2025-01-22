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
import java.security.InvalidAlgorithmParameterException;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.security.SecureRandom;
import java.security.Security;

import org.bouncycastle.asn1.ASN1Encoding;
import org.bouncycastle.crypto.AsymmetricCipherKeyPair;
import org.bouncycastle.crypto.SecretWithEncapsulation;
import org.bouncycastle.crypto.agreement.ECDHCBasicAgreement;
import org.bouncycastle.crypto.digests.SHA256Digest;
import org.bouncycastle.crypto.ec.CustomNamedCurves;
import org.bouncycastle.crypto.generators.ECKeyPairGenerator;
import org.bouncycastle.crypto.generators.KDF2BytesGenerator;
import org.bouncycastle.crypto.params.ECDomainParameters;
import org.bouncycastle.crypto.params.ECKeyGenerationParameters;
import org.bouncycastle.crypto.params.KDFParameters;
import org.bouncycastle.crypto.params.KeyParameter;
import org.bouncycastle.pqc.crypto.frodo.FrodoKEMExtractor;
import org.bouncycastle.pqc.crypto.frodo.FrodoKEMGenerator;
import org.bouncycastle.pqc.crypto.frodo.FrodoKeyGenerationParameters;
import org.bouncycastle.pqc.crypto.frodo.FrodoKeyPairGenerator;
import org.bouncycastle.pqc.crypto.frodo.FrodoKeyParameters;
import org.bouncycastle.pqc.crypto.frodo.FrodoParameters;
import org.bouncycastle.pqc.crypto.frodo.FrodoPublicKeyParameters;
import org.bouncycastle.pqc.crypto.util.SubjectPublicKeyInfoFactory;
import org.bouncycastle.util.Arrays;
import org.bouncycastle.util.BigIntegers;
import org.bouncycastle.util.Strings;
import org.bouncycastle.util.encoders.Hex;

public class FrodoUtils {

    private static SecureRandom random = new SecureRandom();
    static {
	Security.addProvider(new org.bouncycastle.jce.provider.BouncyCastleProvider());
	Security.addProvider(new org.bouncycastle.pqc.jcajce.provider.BouncyCastlePQCProvider());
    }

    public static AsymmetricCipherKeyPair generateKeyPair() throws NoSuchAlgorithmException, NoSuchProviderException, InvalidAlgorithmParameterException {

	ECDomainParameters params = new ECDomainParameters(CustomNamedCurves.getByName("Curve25519"));

	ECKeyPairGenerator ecKpGen = new ECKeyPairGenerator();
	ECKeyGenerationParameters genParameters = new ECKeyGenerationParameters(params, FrodoUtils.random);
	ecKpGen.init(genParameters);

	return ecKpGen.generateKeyPair();
    }

    public static AsymmetricCipherKeyPair generateFrodoKeyPair(FrodoParameters frodoParameters) {

	FrodoKeyGenerationParameters genParam = new FrodoKeyGenerationParameters(FrodoUtils.random, frodoParameters);
	FrodoKeyPairGenerator frodoKpGen = new FrodoKeyPairGenerator();
	frodoKpGen.init(genParam);

	return frodoKpGen.generateKeyPair();
    }

    public static void main(String[] arg) throws Exception {

	// one key pair for the initiator
	AsymmetricCipherKeyPair initKeyPair = FrodoUtils.generateKeyPair();

	// one key pair for the recipient
	AsymmetricCipherKeyPair recKeyPair = FrodoUtils.generateKeyPair();

	// You will just create a Frodo key pair for the recipient.
	AsymmetricCipherKeyPair recFrodoKP = FrodoUtils.generateFrodoKeyPair(FrodoParameters.frodokem640aes);

	FrodoPublicKeyParameters pubParameters = (FrodoPublicKeyParameters) recFrodoKP.getPublic();

	byte[] publicKeyAsASN1 = SubjectPublicKeyInfoFactory.createSubjectPublicKeyInfo(pubParameters).getEncoded(ASN1Encoding.BER);
	System.out.println(new String(publicKeyAsASN1));

	// The agreement step the ECDHC Basic Agreement (cofactor Diffie-Hellman)
	ECDHCBasicAgreement agreement = new ECDHCBasicAgreement();

	agreement.init(initKeyPair.getPrivate());

	BigInteger initAgreed = agreement.calculateAgreement(recKeyPair.getPublic());

	byte[] initZ = BigIntegers.asUnsignedByteArray(agreement.getFieldSize(), initAgreed);

	FrodoKEMGenerator frodoEncCipher = new FrodoKEMGenerator(FrodoUtils.random);
	SecretWithEncapsulation initEnc = frodoEncCipher.generateEncapsulated(pubParameters);

	byte[] initT = initEnc.getSecret();

	// introduce the KDF - X9.63 style using SHA-256.
	KDF2BytesGenerator initKdf = new KDF2BytesGenerator(new SHA256Digest());

	// the second parameter is just the regular user keying materiel
	initKdf.init(new KDFParameters(Arrays.concatenate(initZ, initT), Strings.toByteArray("Hybrid Exchange")));

	// Generate
	byte[] initBuf = new byte[32];
	initKdf.generateBytes(initBuf, 0, initBuf.length);
	KeyParameter initKey = new KeyParameter(initBuf);
	System.out.println("Initiator Generated hybrid key: " + Hex.toHexString(initKey.getKey()));

	// Recipient Side.
	// Do the agreement
	ECDHCBasicAgreement recAgree = new ECDHCBasicAgreement();

	recAgree.init(recKeyPair.getPrivate());

	BigInteger recAgreed = recAgree.calculateAgreement(initKeyPair.getPublic());

	byte[] recZ = BigIntegers.asUnsignedByteArray(recAgree.getFieldSize(), recAgreed);

	// now extract the KEM secret.
	FrodoKEMExtractor frodoDecCipher = new FrodoKEMExtractor((FrodoKeyParameters) recFrodoKP.getPrivate());
	byte[] recT = frodoDecCipher.extractSecret(initEnc.getEncapsulation());

	// introduce the recipient KDF - again X9.63 style using SHA-256.
	KDF2BytesGenerator recKdf = new KDF2BytesGenerator(new SHA256Digest());

	// the second parameter is just the regular user keying material
	recKdf.init(new KDFParameters(Arrays.concatenate(recZ, recT), Strings.toByteArray("Hybrid Exchange")));

	// Generate the recipient secret key.
	byte[] recBuf = new byte[32];
	recKdf.generateBytes(recBuf, 0, recBuf.length);
	KeyParameter recKey = new KeyParameter(initBuf);
	System.out.println("Recipient Generated hybrid key: " + Hex.toHexString(recKey.getKey()));
    }

}
