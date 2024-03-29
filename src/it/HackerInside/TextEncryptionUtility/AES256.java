package it.HackerInside.TextEncryptionUtility;

import java.io.BufferedInputStream;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.KeySpec;
import java.util.Base64;

import javax.crypto.Cipher;
import javax.crypto.CipherInputStream;
import javax.crypto.CipherOutputStream;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.GCMParameterSpec;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.PBEKeySpec;
import javax.crypto.spec.SecretKeySpec;

import org.apache.commons.codec.binary.Base32;

import technology.zeroalpha.security.pgpwordlist.InvalidHexValueException;
import technology.zeroalpha.security.pgpwordlist.PGPWordListConverter;

public class AES256 {
	public static void encrypt(InputStream in, OutputStream out, SecretKeySpec keySpec,byte[] iv) throws Exception{

		//System.out.println(">>>>>>>>written"+Arrays.toString(iv));

		Cipher cipher = Cipher.getInstance("AES/GCM/NoPadding"); //"DES/ECB/PKCS5Padding";"AES/CBC/PKCS5Padding"
		IvParameterSpec ivSpec = new IvParameterSpec(iv);
		GCMParameterSpec gcmParameterSpec = new GCMParameterSpec(16 * 8, iv);
		cipher.init(Cipher.ENCRYPT_MODE, keySpec,gcmParameterSpec);    	

		out = new CipherOutputStream(out, cipher);
		byte[] buf = new byte[1024];
		int numRead = 0;
		while ((numRead = in.read(buf)) >= 0) {
			out.write(buf, 0, numRead);
		}
		out.close();
	}


	public static void decrypt(InputStream in, OutputStream out, SecretKeySpec keySpec,byte[] iv) throws Exception{
		//System.out.println(">>>>>>>>red"+Arrays.toString(iv));

		Cipher cipher = Cipher.getInstance("AES/GCM/NoPadding"); //"DES/ECB/PKCS5Padding";"AES/CBC/PKCS5Padding"
		IvParameterSpec ivSpec = new IvParameterSpec(iv);
		GCMParameterSpec gcmParameterSpec = new GCMParameterSpec(16 * 8, iv);
		cipher.init(Cipher.DECRYPT_MODE, keySpec,gcmParameterSpec);    

		in = new CipherInputStream(in, cipher);
		byte[] buf = new byte[1024];
		int numRead = 0;
		while ((numRead = in.read(buf)) >= 0) {
			//System.out.println(Main.bytesToHex(buf));
			out.write(buf, 0, numRead);
		}
		out.close();
	}
	
	public static void encrypt(InputStream in, OutputStream out, SecretKey key,byte[] iv) throws Exception{

		//System.out.println(">>>>>>>>written"+Arrays.toString(iv));

		Cipher cipher = Cipher.getInstance("AES/GCM/NoPadding"); //"DES/ECB/PKCS5Padding";"AES/CBC/PKCS5Padding"
		IvParameterSpec ivSpec = new IvParameterSpec(iv);
		GCMParameterSpec gcmParameterSpec = new GCMParameterSpec(16 * 8, iv);
		cipher.init(Cipher.ENCRYPT_MODE, key,gcmParameterSpec);    	

		out = new CipherOutputStream(out, cipher);
		byte[] buf = new byte[1024];
		int numRead = 0;
		while ((numRead = in.read(buf)) >= 0) {
			out.write(buf, 0, numRead);
		}
		out.close();
	}


	public static void decrypt(InputStream in, OutputStream out, SecretKey key,byte[] iv) throws Exception{

		Cipher cipher = Cipher.getInstance("AES/GCM/NoPadding"); //"DES/ECB/PKCS5Padding";"AES/CBC/PKCS5Padding"
		IvParameterSpec ivSpec = new IvParameterSpec(iv);
		GCMParameterSpec gcmParameterSpec = new GCMParameterSpec(16 * 8, iv);
		cipher.init(Cipher.DECRYPT_MODE, key, gcmParameterSpec);

		in = new CipherInputStream(in, cipher);
		byte[] buf = new byte[1024];
		int numRead = 0;
		while ((numRead = in.read(buf)) >= 0) {
			out.write(buf, 0, numRead);
		}
		out.close();
	}


	public static String encryptDecryptString(int mode, String input, SecretKey key) throws Exception {

		BufferedInputStream is; 
		ByteArrayOutputStream  os = new ByteArrayOutputStream();
		if(mode==Cipher.ENCRYPT_MODE){
			is = new BufferedInputStream(new ByteArrayInputStream(input.getBytes()));
			SecureRandom r = new SecureRandom();
			byte[] iv = new byte[16];
			r.nextBytes(iv);
			os.write(iv); //write IV as a prefix
			os.flush();
			encrypt(is, os, key,iv);
			System.out.println("Generated IV: " + bytesToHex(iv));
			return bytesToHex(os.toByteArray());
		}
		else if(mode==Cipher.DECRYPT_MODE){
			is = new BufferedInputStream(new ByteArrayInputStream(hexStringToByteArray(input)));
			byte[] iv = new byte[16];
			is.read(iv);
			decrypt(is, os,key,iv);
			System.out.println("Decoded IV: " + bytesToHex(iv));
			return new String(os.toByteArray());
		}
		else throw new Exception("unknown mode");
		
	}
	
	public static String encryptDecryptString(int mode, String input, SecretKey key,String encoding,boolean compression) throws Exception {

		BufferedInputStream is; 
		ByteArrayOutputStream  os = new ByteArrayOutputStream();
		if(mode==Cipher.ENCRYPT_MODE){
			byte[] ptData;
			if(compression) { // Compressione
				ptData = GZIPCompression.compress(input);
			}else {
				ptData = input.getBytes();
			}
			is = new BufferedInputStream(new ByteArrayInputStream(ptData));
			SecureRandom r = new SecureRandom();
			byte[] iv = new byte[16];
			r.nextBytes(iv);
			os.write(iv); //write IV as a prefix
			os.flush();
			encrypt(is, os, key,iv);
			System.out.println("Generated IV: " + bytesToHex(iv));
			if(encoding.equalsIgnoreCase("hex")) { //Hex Encoding
				return bytesToHex(os.toByteArray());
			}else if(encoding.equalsIgnoreCase("base64")) { // Base64 Encoding
				return Base64.getEncoder().encodeToString(os.toByteArray());
			}else if(encoding.equalsIgnoreCase("base58")) {// Base58 Encoding
				return Base58.encode(os.toByteArray());
			}else if(encoding.equalsIgnoreCase("pgpWordlist")) {// PGP Wordlist encoding
				PGPWordListConverter pgpWordListConverter = new PGPWordListConverter();
				try {
					return pgpWordListConverter.hexToPGPWordlist(bytesToHex(os.toByteArray()));
					//return t.hexToPGPWordlist(bytesToHex(os.toByteArray()));
				} catch (InvalidHexValueException e1) {

					e1.printStackTrace();
				}
			}else if(encoding.equalsIgnoreCase("base36")) {// Base36 Encoding
				return Base36.encode(os.toByteArray());
			
			}else if(encoding.equalsIgnoreCase("base32")) {// Base32 Encoding
				Base32 base32 = new Base32();
				return base32.encodeAsString(os.toByteArray());
			}else if(encoding.equalsIgnoreCase("base32-c")) {// Base32-C Encoding
				Base32 base32 = new Base32();
				return base32.encodeAsString(os.toByteArray()).replace('=', '9');
			}
			else {
				return bytesToHex(os.toByteArray());
			}
			
			return "";
			
		}
		else if(mode==Cipher.DECRYPT_MODE){
			byte[] decodedData = null;
			if(encoding.equalsIgnoreCase("hex")) { //Hex Decoding
				decodedData = hexStringToByteArray(input);
			}else if(encoding.equalsIgnoreCase("base64")) { // Base64 Decoding
				decodedData = Base64.getDecoder().decode(input);
			}else if(encoding.equalsIgnoreCase("base58")) {// Base58 Decoding
				decodedData = Base58.decode(input);
			}else if(encoding.equalsIgnoreCase("pgpWordlist")) {// PGP Wordlist decoding
				PGPWordListConverter pgpWordListConverter = new PGPWordListConverter();
				try {
					decodedData = hexStringToByteArray(pgpWordListConverter.pgpWordlistToHex(input));
				} catch (InvalidHexValueException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}else if(encoding.equalsIgnoreCase("base36")) {// Base36 Decoding
				decodedData = Base36.decode(input);
			}else if(encoding.equalsIgnoreCase("base32")) {// Base32 Encoding
				Base32 base32 = new Base32();
				decodedData = base32.decode(input);
			}else if(encoding.equalsIgnoreCase("base32-c")) { // Base32-C
				Base32 base32 = new Base32();
				input = input.replace('9', '=');
				decodedData = base32.decode(input);
			}

			is = new BufferedInputStream(new ByteArrayInputStream(decodedData));
			byte[] iv = new byte[16];
			is.read(iv);
			decrypt(is, os,key,iv);
			System.out.println("Decoded IV: " + bytesToHex(iv));
			
			if(compression) { // Compressione
				return GZIPCompression.decompress(os.toByteArray());
			}else {
				return new String(os.toByteArray());
			}
			
		}
		else throw new Exception("unknown mode");
		
	}
	
	
    public static SecretKeySpec genSecKey(String pwd,byte[] salt) throws NoSuchAlgorithmException, InvalidKeySpecException {
        SecretKeyFactory factory = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA256");
        KeySpec spec = new PBEKeySpec(pwd.toCharArray(), salt, 65536, 256);
        SecretKey tmp = factory.generateSecret(spec);
        return new SecretKeySpec(tmp.getEncoded(), "AES");
    }
    public static SecretKey generateAESKey() throws NoSuchAlgorithmException {
    	SecureRandom rand = new SecureRandom();
    	KeyGenerator generator = KeyGenerator.getInstance("AES");
    	generator.init(256,rand); // The AES key size in number of bits
    	return generator.generateKey();
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
}
