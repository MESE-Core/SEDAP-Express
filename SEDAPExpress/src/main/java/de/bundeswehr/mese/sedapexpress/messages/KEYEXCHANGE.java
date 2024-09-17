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

import java.math.BigInteger;
import java.security.PublicKey;
import java.util.Iterator;
import java.util.logging.Level;

import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;

import org.bouncycastle.asn1.ASN1Sequence;
import org.bouncycastle.asn1.x509.SubjectPublicKeyInfo;
import org.bouncycastle.jcajce.provider.asymmetric.dh.BCDHPublicKey;
import org.bouncycastle.util.encoders.Base64;
import org.bouncycastle.util.encoders.DecoderException;

public class KEYEXCHANGE extends SEDAPExpressMessage {

    private static final long serialVersionUID = -6681575300891302102L;

    private Integer algorithm;
    private Integer phase;

    private Integer keyLength;
    private BigInteger primeNumber;
    private BigInteger naturalNumber;

    private PublicKey publicKey;

    private SecretKey encryptedKey;

    public Integer getAlgorithm() {
	return this.algorithm;
    }

    public void setAlgorithm(Integer algorithm) {
	this.algorithm = algorithm;
    }

    public Integer getPhase() {
	return this.phase;
    }

    public void setPhase(Integer phase) {
	this.phase = phase;
    }

    public Integer getKeyLength() {
	return this.keyLength;
    }

    public void setKeyLength(Integer keyLength) {
	this.keyLength = keyLength;
    }

    public BigInteger getPrimeNumber() {
	return this.primeNumber;
    }

    public void setPrimeNumber(BigInteger primeNumber) {
	this.primeNumber = primeNumber;
    }

    public BigInteger getNaturalNumber() {
	return this.naturalNumber;
    }

    public void setNaturalNumber(BigInteger naturalNumber) {
	this.naturalNumber = naturalNumber;
    }

    public PublicKey getPublicKey() {
	return this.publicKey;
    }

    public void setPublicKey(PublicKey publicKey) {
	this.publicKey = publicKey;
    }

    public SecretKey getEncryptedKey() {
	return this.encryptedKey;
    }

    public void setEncryptedKey(SecretKey encryptedKey) {
	this.encryptedKey = encryptedKey;
    }

    /**
     *
     * @param number
     * @param time
     * @param sender
     * @param classification
     * @param acknowledgement
     * @param mac
     * @param algorithm
     * @param phase
     * @param keyLength
     * @param primeNumber
     * @param naturalNumber
     * @param publicKey
     * @param encryptedKey
     */
    public KEYEXCHANGE(Short number, Long time, String sender, Classification classification, Acknowledgement acknowledgement, String mac, Integer algorithm, Integer phase, Integer keyLength, BigInteger primeNumber,
	    BigInteger naturalNumber, PublicKey publicKey, SecretKey encryptedKey) {
	super(number, time, sender, classification, acknowledgement, mac);
	this.algorithm = algorithm;
	this.phase = phase;
	this.keyLength = keyLength;
	this.primeNumber = primeNumber;
	this.naturalNumber = naturalNumber;
	this.publicKey = publicKey;
	this.encryptedKey = encryptedKey;
    }

    /**
     *
     * @param message
     */
    public KEYEXCHANGE(String message) {

	this(SEDAPExpressMessage.splitMessage(message.substring(message.indexOf(';') + 1)).iterator());
    }

    /**
     *
     * @param message
     */
    public KEYEXCHANGE(Iterator<String> message) {

	super(message);

	String value;

	// Algorithm
	if (message.hasNext()) {
	    value = message.next();
	    if ("0".equals(value) || "1".equals(value) || "2".equals(value) || "3".equals(value) || "4".equals(value)) {
		this.algorithm = Integer.parseInt(value);
	    } else if (value.isBlank()) {
		SEDAPExpressMessage.logger.logp(Level.SEVERE, "KEYEXCHANGE", "KEYEXCHANGE(Iterator<String> message)", "Mandatory field \"Algorithm\" contains not a valid number!", value);
	    }
	} else {
	    SEDAPExpressMessage.logger.logp(Level.SEVERE, "KEYEXCHANGE", "KEYEXCHANGE(Iterator<String> message)", "Incomplete message!");
	}

	// Phase
	if (message.hasNext()) {
	    value = message.next();
	    if ("0".equals(value) || "1".equals(value) || "2".equals(value) || "3".equals(value)) {
		this.phase = Integer.parseInt(value);
	    } else if (value.isBlank()) {
		SEDAPExpressMessage.logger.logp(Level.SEVERE, "KEYEXCHANGE", "KEYEXCHANGE(Iterator<String> message)", "Mandatory field \"Phase\" contains not a valid number!", value);
	    }
	} else {
	    SEDAPExpressMessage.logger.logp(Level.SEVERE, "KEYEXCHANGE", "KEYEXCHANGE(Iterator<String> message)", "Incomplete message!");
	}

	// KeyLength
	if (message.hasNext()) {
	    value = message.next();
	    if ("128".equals(value) || "256".equals(value)) {
		this.keyLength = Integer.parseInt(value);
	    } else if (!value.isBlank() && (this.phase == 0)) {
		SEDAPExpressMessage.logger.logp(Level.SEVERE, "KEYEXCHANGE", "KEYEXCHANGE(Iterator<String> message)", "Mandatory (phase 0) field \"KeyLength\" contains not a valid number!", value);
	    } else if (!value.isBlank()) {
		SEDAPExpressMessage.logger.logp(Level.WARNING, "KEYEXCHANGE", "KEYEXCHANGE(Iterator<String> message)", "Optional (not in phase 0) field \"KeyLength\" contains not a valid number!", value);
	    } else if (this.phase == 0) {
		SEDAPExpressMessage.logger.logp(Level.SEVERE, "KEYEXCHANGE", "KEYEXCHANGE(Iterator<String> message)", "Mandatory (phase 0) field \"KeyLength\" is empty!");
	    } else {
		SEDAPExpressMessage.logger.logp(Level.SEVERE, "KEYEXCHANGE", "KEYEXCHANGE(Iterator<String> message)", "Optional (not in phase 0) field \"KeyLength\" is empty!");
	    }
	} else {
	    SEDAPExpressMessage.logger.logp(Level.SEVERE, "KEYEXCHANGE", "KEYEXCHANGE(Iterator<String> message)", "Incomplete message!");
	}

	// PrimeNumber
	if (message.hasNext()) {
	    value = message.next();
	    if (SEDAPExpressMessage.matchesPattern(SEDAPExpressMessage.BIGINTEGER_MATCHER, value)) {
		this.primeNumber = new BigInteger(value, 16);
	    } else if (!value.isBlank() && (this.phase == 0)) {
		SEDAPExpressMessage.logger.logp(Level.SEVERE, "KEYEXCHANGE", "KEYEXCHANGE(Iterator<String> message)", "Mandatory (phase 0) field \"PrimeNumber\" contains not a valid number!", value);
	    } else if (!value.isBlank()) {
		SEDAPExpressMessage.logger.logp(Level.WARNING, "KEYEXCHANGE", "KEYEXCHANGE(Iterator<String> message)", "Optional (not in phase 0) field \"PrimeNumber\" contains not a valid number!", value);
	    } else if (this.phase == 0) {
		SEDAPExpressMessage.logger.logp(Level.SEVERE, "KEYEXCHANGE", "KEYEXCHANGE(Iterator<String> message)", "Mandatory (phase 0) field \"PrimeNumber\" is empty!");
	    } else {
		SEDAPExpressMessage.logger.logp(Level.INFO, "KEYEXCHANGE", "KEYEXCHANGE(Iterator<String> message)", "Optional (not in phase 0) field \"PrimeNumber\" is empty!");
	    }
	} else {
	    SEDAPExpressMessage.logger.logp(Level.SEVERE, "KEYEXCHANGE", "KEYEXCHANGE(Iterator<String> message)", "Incomplete message!");
	}

	// NaturalNumber
	if (message.hasNext()) {
	    value = message.next();
	    if (SEDAPExpressMessage.matchesPattern(SEDAPExpressMessage.BIGINTEGER_MATCHER, value)) {
		this.naturalNumber = new BigInteger(value, 16);
	    } else if (!value.isBlank() && (this.phase == 0)) {
		SEDAPExpressMessage.logger.logp(Level.SEVERE, "KEYEXCHANGE", "KEYEXCHANGE(Iterator<String> message)", "Mandatory (phase 0) field \"NaturalNumber\" contains not a valid number!", value);
	    } else if (!value.isBlank()) {
		SEDAPExpressMessage.logger.logp(Level.WARNING, "KEYEXCHANGE", "KEYEXCHANGE(Iterator<String> message)", "Optional (not in phase 0) field \"NaturalNumber\" contains not a valid number!", value);
	    } else if (this.phase == 0) {
		SEDAPExpressMessage.logger.logp(Level.SEVERE, "KEYEXCHANGE", "KEYEXCHANGE(Iterator<String> message)", "Mandatory (phase 0) field \"NaturalNumber\" is empty!");
	    } else {
		SEDAPExpressMessage.logger.logp(Level.INFO, "KEYEXCHANGE", "KEYEXCHANGE(Iterator<String> message)", "Optional (not in phase 0) field \"NaturalNumber\" is empty!");
	    }
	} else {
	    SEDAPExpressMessage.logger.logp(Level.SEVERE, "KEYEXCHANGE", "KEYEXCHANGE(Iterator<String> message)", "Incomplete message!");
	}

	// PublicKey
	if (message.hasNext()) {
	    value = message.next();
	    if (value.isBlank() && (this.phase == 1)) {
		SEDAPExpressMessage.logger.logp(Level.SEVERE, "KEYEXCHANGE", "KEYEXCHANGE(Iterator<String> message)", "Mandatory (phase 1) field \"PublicKey\" is empty!");

	    } else if (value.isBlank()) {
		SEDAPExpressMessage.logger.logp(Level.INFO, "KEYEXCHANGE", "KEYEXCHANGE(Iterator<String> message)", "Optional (not phase 1) field \"PublicKey\" is empty!");

	    } else {
		try {
		    this.publicKey = new BCDHPublicKey(SubjectPublicKeyInfo.getInstance(ASN1Sequence.getInstance(Base64.decode(value))));
		} catch (DecoderException e) {

		    if (this.phase == 1) {
			SEDAPExpressMessage.logger.logp(Level.SEVERE, "KEYEXCHANGE", "KEYEXCHANGE(Iterator<String> message)", "Madatory (phase 1) field \"PublicKey\" could not be decoded from Base64!");
		    } else {
			SEDAPExpressMessage.logger.logp(Level.WARNING, "KEYEXCHANGE", "KEYEXCHANGE(Iterator<String> message)", "Optional (not in phase 1) field \"PublicKey\" could not be decoded from Base64!");
		    }
		}
	    }
	}

	// SecretKey
	if (message.hasNext()) {
	    value = message.next();
	    if (value.isBlank() && (this.phase == 2) && (this.algorithm > 1)) {
		SEDAPExpressMessage.logger.logp(Level.SEVERE, "KEYEXCHANGE", "KEYEXCHANGE(Iterator<String> message)", "Mandatory (Kyber, phase 2) field \"EncryptedKey\" is empty!");

	    } else if (value.isBlank()) {
		SEDAPExpressMessage.logger.logp(Level.INFO, "KEYEXCHANGE", "KEYEXCHANGE(Iterator<String> message)", "Optional (not phase 2 or Kyber) field \"EncryptedKey\" is empty!");

	    } else {
		try {
		    this.encryptedKey = new SecretKeySpec(Base64.decode(value), "DH");
		} catch (DecoderException e) {

		    if (this.phase == 1) {
			SEDAPExpressMessage.logger.logp(Level.SEVERE, "KEYEXCHANGE", "KEYEXCHANGE(Iterator<String> message)", "Madatory (Kyber, phase 2) field \"EncryptedKey\" could not be decoded from Base64!");
		    } else {
			SEDAPExpressMessage.logger.logp(Level.WARNING, "KEYEXCHANGE", "KEYEXCHANGE(Iterator<String> message)", "Optional (not in phase 1) field \"EncryptedKey\" could not be decoded from Base64!");
		    }
		}
	    }
	}
    }

    @Override
    public boolean equals(Object obj) {
	if (obj == null) {
	    return false;
	} else if (!(obj instanceof KEYEXCHANGE)) {
	    return false;
	} else {
	    return (super.equals(obj) &&

		    (this.algorithm == ((KEYEXCHANGE) obj).algorithm) && (this.phase == ((KEYEXCHANGE) obj).phase) &&

		    (this.keyLength == ((KEYEXCHANGE) obj).keyLength) && (this.primeNumber == ((KEYEXCHANGE) obj).primeNumber) && (this.naturalNumber == ((KEYEXCHANGE) obj).naturalNumber) &&

		    (((this.publicKey == null) && (((KEYEXCHANGE) obj).publicKey == null)) || ((this.publicKey != null) && this.publicKey.equals(((KEYEXCHANGE) obj).publicKey))) &&

		    ((this.encryptedKey == null) && (((KEYEXCHANGE) obj).encryptedKey == null))) || ((this.encryptedKey != null) && this.encryptedKey.equals(((KEYEXCHANGE) obj).encryptedKey));

	}
    }

    @Override
    public int hashCode() {
	return super.hashCode();
    }

    @Override
    public String toString() {

	return SEDAPExpressMessage.removeSemicolons(serializeHeader()

		.append((this.algorithm != null) ? this.algorithm : "").append(";").append((this.phase != null) ? this.phase : "").append(";").append((this.keyLength != null) ? this.keyLength : "").append(";")
		.append((this.primeNumber != null) ? this.primeNumber : "").append(";").append((this.naturalNumber != null) ? this.naturalNumber : "").append(";")
		.append((this.publicKey != null) ? SEDAPExpressMessage.HEXFOMATER.formatHex(this.publicKey.getEncoded()) : "").append(";")
		.append((this.publicKey != null) ? SEDAPExpressMessage.HEXFOMATER.formatHex(this.encryptedKey.getEncoded()) : "").toString());
    }
}
