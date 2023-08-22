package it.HackerInside.TextEncryptionUtility;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.security.Key;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.security.Security;
import java.security.UnrecoverableKeyException;
import java.security.cert.CertificateException;
import java.util.Enumeration;
import org.bouncycastle.jce.provider.BouncyCastleProvider;

import javax.crypto.SecretKey;
import javax.swing.JOptionPane;

public class KeyStoreUtils {
	
	static {
		Security.addProvider(new BouncyCastleProvider());
	}
	
	public static KeyStore newKeystore(String masterPassword, String filename) throws KeyStoreException, NoSuchAlgorithmException, CertificateException, IOException, NoSuchProviderException { // Configurazione iniziale di un nuovo keystore
		KeyStore ks = KeyStore.getInstance("BCFKS", "BC");
		ks.load(null, masterPassword.toCharArray());
		KeyStoreUtils.saveKeyStore(ks,masterPassword,filename);
		return ks;
	}
   public static void saveKeyStore(KeyStore ks, String masterPassword, String filename) throws KeyStoreException, NoSuchAlgorithmException, CertificateException, IOException {
       try (FileOutputStream fos = new FileOutputStream(filename)) {
           ks.store(fos, masterPassword.toCharArray());
       }
   }

   public static KeyStore loadKeyStore(String masterPassword, String filename) throws NoSuchAlgorithmException, CertificateException, FileNotFoundException, IOException, KeyStoreException, NoSuchProviderException {
       KeyStore ks = KeyStore.getInstance("BCFKS", "BC");
       ks.load(new FileInputStream(filename), masterPassword.toCharArray());
       return ks;
   }

   public static void addSecretKey(KeyStore ks, String masterPassword, String alias, SecretKey key) throws KeyStoreException {
      KeyStore.SecretKeyEntry secret = new KeyStore.SecretKeyEntry(key);
      KeyStore.ProtectionParameter password = new KeyStore.PasswordProtection(masterPassword.toCharArray());
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
