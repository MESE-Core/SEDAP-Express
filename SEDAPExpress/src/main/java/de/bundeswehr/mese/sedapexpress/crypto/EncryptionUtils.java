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
