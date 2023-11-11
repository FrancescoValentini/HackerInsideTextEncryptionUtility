package it.HackerInside.TextEncryptionUtility;



import java.security.*;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.X509EncodedKeySpec;
import javax.crypto.KeyAgreement;

import java.util.*;
import java.nio.ByteBuffer;
import javax.crypto.*;
import javax.crypto.spec.SecretKeySpec;
import org.apache.commons.codec.binary.Base32;


public class Ecdh {
	KeyPairGenerator kpg;
	KeyPair kp;
	byte[] ourPk;
	public Ecdh(int bitSize) throws NoSuchAlgorithmException {
		kpg = KeyPairGenerator.getInstance("EC");
		kpg.initialize(bitSize);
		kp = kpg.generateKeyPair();
		ourPk = kp.getPublic().getEncoded();
	}

	public String getPublicKey() {
		Base32 base32 = new Base32();
		return base32.encodeAsString(ourPk).replace('=', '9');
	}

	public byte[] generateSharedSecret(String otherPkBase64) throws NoSuchAlgorithmException, InvalidKeySpecException, InvalidKeyException {
		Base32 base32 = new Base32();
		
		otherPkBase64 = otherPkBase64.replace('9', '=');
		byte[] otherPk = base32.decode(otherPkBase64);

		KeyFactory kf = KeyFactory.getInstance("EC");
		X509EncodedKeySpec pkSpec = new X509EncodedKeySpec(otherPk);
		PublicKey otherPublicKey = kf.generatePublic(pkSpec);

		// Perform key agreement
		KeyAgreement ka = KeyAgreement.getInstance("ECDH");
		ka.init(kp.getPrivate());
		ka.doPhase(otherPublicKey, true);

		// Read shared secret
		byte[] sharedSecret = ka.generateSecret();

		// Derive a key from the shared secret and both public keys
		MessageDigest hash = MessageDigest.getInstance("SHA-256");
		hash.update(sharedSecret);
		// Simple deterministic ordering
		List<ByteBuffer> keys = Arrays.asList(ByteBuffer.wrap(ourPk), ByteBuffer.wrap(otherPk));
		Collections.sort(keys);
		hash.update(keys.get(0));
		hash.update(keys.get(1));

		byte[] derivedKey = hash.digest();


		return derivedKey;
	}


	private static final char[] HEX_ARRAY = "0123456789ABCDEF".toCharArray();
	public static String bytesToHex(byte[] bytes) {
		char[] hexChars = new char[bytes.length * 2];
		for (int j = 0; j < bytes.length; j++) {
			int v = bytes[j] & 0xFF;
			hexChars[j * 2] = HEX_ARRAY[v >>> 4];
			hexChars[j * 2 + 1] = HEX_ARRAY[v & 0x0F];
		}
		return new String(hexChars);
	}

	public static byte[] hexStringToByteArray(String s) {
		int len = s.length();
		byte[] data = new byte[len / 2];
		for (int i = 0; i < len; i += 2) {
			data[i / 2] = (byte) ((Character.digit(s.charAt(i), 16) << 4)
					+ Character.digit(s.charAt(i+1), 16));
		}
		return data;
	}

	public static byte[] kcvAES(byte[] keyBytes) throws NoSuchAlgorithmException, NoSuchPaddingException, InvalidKeyException, IllegalBlockSizeException, BadPaddingException{
		byte[] binaryZeroes = new byte[16];
		Arrays.fill(binaryZeroes, (byte) 01);
		final SecretKey key = new SecretKeySpec(keyBytes, "AES");
		final Cipher cipher = Cipher.getInstance("AES");
		cipher.init(Cipher.ENCRYPT_MODE, key);
		byte[] cipherText = cipher.doFinal(binaryZeroes);
		return cipherText;
	}
}