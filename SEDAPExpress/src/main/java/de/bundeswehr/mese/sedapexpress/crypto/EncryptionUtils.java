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

import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.Security;
import java.util.Base64;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.spec.SecretKeySpec;

public class EncryptionUtils {

    static {
	Security.addProvider(new org.bouncycastle.jce.provider.BouncyCastleProvider());
    }

    /**
     * Encrypts a message with AES128/256 ECB/PKCS7Padding (NIST SP 800-38A)
     *
     * @param originalData plain data which should be encrypted
     * @param key          the 128 or 256 bit wide encryption key
     *
     * @return encrypted data
     *
     * @throws NoSuchPaddingException
     * @throws NoSuchAlgorithmException
     * @throws BadPaddingException
     * @throws IllegalBlockSizeException
     * @throws InvalidKeyException
     */
    public static String encrypt_AES_ECB(String originalData, byte[] key) throws NoSuchAlgorithmException, NoSuchPaddingException, IllegalBlockSizeException, BadPaddingException, InvalidKeyException {

	SecretKeySpec secretKey = new SecretKeySpec(key, "AES");
	Cipher cipher = Cipher.getInstance("AES/ECB/PKCS7Padding");
	cipher.init(Cipher.ENCRYPT_MODE, secretKey);
	byte[] encryptedBytes = cipher.doFinal(originalData.getBytes());

	return Base64.getEncoder().encodeToString(encryptedBytes);
    }

    /**
     * Decrypts a message with AES128/256 ECB/PKCS7Padding (NIST SP 800-38A)
     *
     * @param encryptedData encrypted data which should be decrypted
     * @param key           the 128 or 256 bit wide decryption key
     *
     * @return decrypted data
     *
     * @throws NoSuchPaddingException
     * @throws NoSuchAlgorithmException
     * @throws BadPaddingException
     * @throws IllegalBlockSizeException
     * @throws InvalidKeyException
     */
    public static String decrypt_AES_ECB(String encryptedData, byte[] key) throws NoSuchAlgorithmException, NoSuchPaddingException, InvalidKeyException, IllegalBlockSizeException, BadPaddingException {

	SecretKeySpec secretKey = new SecretKeySpec(key, "AES");
	Cipher cipher = Cipher.getInstance("AES/ECB/PKCS7Padding");
	cipher.init(Cipher.DECRYPT_MODE, secretKey);
	byte[] decodedBytes = Base64.getDecoder().decode(encryptedData);
	byte[] decryptedBytes = cipher.doFinal(decodedBytes);

	return new String(decryptedBytes);
    }

    /**
     * Encrypts a message with AES128/256 CFB/NoPadding (NIST SP 800-38A)
     *
     * @param originalData plain data which should be encrypted
     * @param key          the 128 or 256 bit wide encryption key
     *
     * @return encrypted data
     *
     * @throws NoSuchPaddingException
     * @throws NoSuchAlgorithmException
     * @throws BadPaddingException
     * @throws IllegalBlockSizeException
     * @throws InvalidKeyException
     */
    public static String encrypt_AES_CFB(String originalData, byte[] key) throws NoSuchAlgorithmException, NoSuchPaddingException, IllegalBlockSizeException, BadPaddingException, InvalidKeyException {

	SecretKeySpec secretKey = new SecretKeySpec(key, "AES");
	Cipher cipher = Cipher.getInstance("AES/CFB/NoPadding");
	cipher.init(Cipher.ENCRYPT_MODE, secretKey);
	byte[] encryptedBytes = cipher.doFinal(originalData.getBytes());

	return Base64.getEncoder().encodeToString(encryptedBytes);
    }

    /**
     * Decrypts a message with AES128/256 CFB/NoPadding (NIST SP 800-38A)
     *
     * @param encryptedData encrypted data which should be decrypted
     * @param key           the 128 or 256 bit wide decryption key
     *
     * @return decrypted data
     *
     * @throws NoSuchPaddingException
     * @throws NoSuchAlgorithmException
     * @throws BadPaddingException
     * @throws IllegalBlockSizeException
     * @throws InvalidKeyException
     */
    public static String decrypt_AES_CFB(String encryptedData, byte[] key) throws NoSuchAlgorithmException, NoSuchPaddingException, InvalidKeyException, IllegalBlockSizeException, BadPaddingException {

	SecretKeySpec secretKey = new SecretKeySpec(key, "AES");
	Cipher cipher = Cipher.getInstance("AES/CFB/NoPadding");
	cipher.init(Cipher.DECRYPT_MODE, secretKey);
	byte[] decodedBytes = Base64.getDecoder().decode(encryptedData);
	byte[] decryptedBytes = cipher.doFinal(decodedBytes);

	return new String(decryptedBytes);
    }

    /**
     * Encrypts a message with AES128/256 CTR/NoPadding (NIST SP 800-38A)
     *
     * @param originalData plain data which should be encrypted
     * @param key          the 128 or 256 bit wide encryption key
     *
     * @return encrypted data
     *
     * @throws NoSuchPaddingException
     * @throws NoSuchAlgorithmException
     * @throws BadPaddingException
     * @throws IllegalBlockSizeException
     * @throws InvalidKeyException
     */
    public static String encrypt_AES_CTR(String originalData, byte[] key) throws NoSuchAlgorithmException, NoSuchPaddingException, IllegalBlockSizeException, BadPaddingException, InvalidKeyException {

	SecretKeySpec secretKey = new SecretKeySpec(key, "AES");
	Cipher cipher = Cipher.getInstance("AES/CTR/NoPadding");
	cipher.init(Cipher.ENCRYPT_MODE, secretKey);
	byte[] encryptedBytes = cipher.doFinal(originalData.getBytes());

	return Base64.getEncoder().encodeToString(encryptedBytes);
    }

    /**
     * Decrypts a message with AES128/256 CTR/NoPadding (NIST SP 800-38A)
     *
     * @param encryptedData encrypted data which should be decrypted
     * @param key           the 128 or 256 bit wide decryption key
     *
     * @return decrypted data
     *
     * @throws NoSuchPaddingException
     * @throws NoSuchAlgorithmException
     * @throws BadPaddingException
     * @throws IllegalBlockSizeException
     * @throws InvalidKeyException
     */
    public static String decrypt_AES_CTR(String encryptedData, byte[] key) throws NoSuchAlgorithmException, NoSuchPaddingException, InvalidKeyException, IllegalBlockSizeException, BadPaddingException {

	SecretKeySpec secretKey = new SecretKeySpec(key, "AES");
	Cipher cipher = Cipher.getInstance("AES/CTR/NoPadding");
	cipher.init(Cipher.DECRYPT_MODE, secretKey);
	byte[] decodedBytes = Base64.getDecoder().decode(encryptedData);
	byte[] decryptedBytes = cipher.doFinal(decodedBytes);

	return new String(decryptedBytes);
    }
}
