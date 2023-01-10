package it.HackerInside.TextEncryptionUtility;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.security.Key;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.UnrecoverableKeyException;
import java.security.cert.CertificateException;
import java.util.Enumeration;

import javax.crypto.SecretKey;

public class KeyStoreUtils {
	   public static void saveKeyStore(KeyStore ks, String masterPassword,String filename) throws KeyStoreException, NoSuchAlgorithmException, CertificateException, IOException {
		   try (FileOutputStream fos = new FileOutputStream(filename)) {
			    ks.store(fos, masterPassword.toCharArray());
			    fos.close();
			}
	   }
	   
	   public static KeyStore loadKeyStore(String masterPassword,String filename) throws NoSuchAlgorithmException, CertificateException, FileNotFoundException, IOException, KeyStoreException {
		   KeyStore ks  = KeyStore.getInstance("JCEKS");
		   ks.load(new FileInputStream(filename), masterPassword.toCharArray());
		   return ks;
	   }
	   
	   public static void addSecretKey(KeyStore ks, String masterPassword, String alias, SecretKey key) throws KeyStoreException {
		  KeyStore.SecretKeyEntry secret = new KeyStore.SecretKeyEntry(key);
		  KeyStore.ProtectionParameter password= new KeyStore.PasswordProtection(masterPassword.toCharArray());
		  ks.setEntry(alias, secret, password);
	   }
	   public static SecretKey getSecretKey(KeyStore ks, String masterPassword, String alias) throws KeyStoreException, UnrecoverableKeyException, NoSuchAlgorithmException {
		  return (SecretKey) ks.getKey(alias, masterPassword.toCharArray());
	   }
	   
	   public static Key getKey(KeyStore ks, String masterPassword, String alias) throws KeyStoreException, UnrecoverableKeyException, NoSuchAlgorithmException {
		  return ks.getKey(alias, masterPassword.toCharArray());
	   }
	   
	   public static void deleteSecretKey(KeyStore ks, String masterPassword, String alias) throws KeyStoreException {
		   ks.deleteEntry(alias);
	   }
	   public static Enumeration<String> showAliases(KeyStore ks) throws KeyStoreException{
		   return ks.aliases();
	   }
}
