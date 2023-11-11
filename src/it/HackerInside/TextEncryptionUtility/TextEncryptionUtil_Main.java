package it.HackerInside.TextEncryptionUtility;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JPasswordField;

import java.awt.BorderLayout;
import javax.swing.border.TitledBorder;


import java.awt.Color;
import javax.swing.border.EtchedBorder;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

import java.awt.Font;
import javax.swing.JComboBox;
import javax.swing.JFileChooser;
import javax.swing.JButton;
import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.swing.ImageIcon;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.JScrollPane;
import java.awt.event.MouseWheelListener;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.security.Key;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.security.SecureRandom;
import java.security.Security;
import java.security.cert.CertificateException;
import java.util.Enumeration;
import java.util.Scanner;
import java.util.prefs.Preferences;
import java.awt.event.MouseWheelEvent;
import javax.swing.event.PopupMenuListener;
import javax.swing.filechooser.FileNameExtensionFilter;

import org.bouncycastle.jce.provider.BouncyCastleProvider;

import javax.swing.event.PopupMenuEvent;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.Toolkit;
import javax.swing.JPopupMenu;
import java.awt.Component;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;

public class TextEncryptionUtil_Main {

	private JFrame frmHackerinsideTextEncryption;
	// Impostazioni KeyStore
	public static String keyStoreFile = "KeyStore.bcfks";
	public static KeyStore ks;
	public static boolean keystoreExist = false;
	static String keyStorePassword = "";
	private static Preferences prefs = Preferences.userNodeForPackage(it.HackerInside.TextEncryptionUtility.TextEncryptionUtil_Main.class);

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		Security.addProvider(new BouncyCastleProvider());
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					if(args.length == 1) { // Consente di specificare un keystore diverso da quello di default
						System.out.println("Using Keystore: " + args[0]);
						keyStoreFile = args[0];
					}else {
						System.out.println("Using default keystore: " + keyStoreFile); // Se non viene specificato niente utilizza il keystore di default
					}
					TextEncryptionUtil_Main window = new TextEncryptionUtil_Main();
					window.frmHackerinsideTextEncryption.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}

			}
		});
	}

	/**
	 * Create the application.
	 */
	public TextEncryptionUtil_Main() {
		initialize();
		if(new File(keyStoreFile).exists()) { // OPEN KEYSTORE
			String password = passwordInput("KeyStore Password");
			keyStorePassword = password;
			try {
				ks = KeyStoreUtils.loadKeyStore(password,keyStoreFile);
				keystoreExist = true;
			} catch (NoSuchAlgorithmException | CertificateException | KeyStoreException | IOException | NoSuchProviderException e) {
				e.printStackTrace();
				JOptionPane.showMessageDialog(null, e.toString());
				System.exit(-1);;
			}
		}else {
			JOptionPane.showMessageDialog(null, "KeyStore not found, a new one will be created.");
			try {
				keyStoreWizard();
			} catch (KeyStoreException | NoSuchAlgorithmException | CertificateException | IOException | NoSuchProviderException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

	public static String passwordInput(String title) {
		JPasswordField pwd = new JPasswordField(10);
		int action = JOptionPane.showConfirmDialog(null, pwd,title,JOptionPane.OK_CANCEL_OPTION);
		if(action < 0)JOptionPane.showMessageDialog(null,"Cancel, X or escape key selected");
		return new String(pwd.getPassword());
	}
	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {

		frmHackerinsideTextEncryption = new JFrame();
		frmHackerinsideTextEncryption.setIconImage(Toolkit.getDefaultToolkit().getImage(TextEncryptionUtil_Main.class.getResource("/it/HackerInside/TextEncryptionUtility/res/data-encryption.png")));
		frmHackerinsideTextEncryption.setTitle("HackerInside Text Encryption Utility | Main");
		frmHackerinsideTextEncryption.setBounds(100, 100, 1048, 737);
		frmHackerinsideTextEncryption.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);


		JPanel panel = new JPanel();
		panel.setForeground(Color.WHITE);
		panel.setBackground(Color.DARK_GRAY);
		frmHackerinsideTextEncryption.getContentPane().add(panel, BorderLayout.CENTER);

		JPanel panel_1 = new JPanel();
		panel_1.setBackground(Color.DARK_GRAY);
		panel_1.setForeground(Color.WHITE);
		panel_1.setBorder(new TitledBorder(new EtchedBorder(EtchedBorder.LOWERED, new Color(255, 255, 255), new Color(160, 160, 160)), "Key Settings", TitledBorder.LEADING, TitledBorder.TOP, null, Color.WHITE));

		JLabel lblNewLabel = new JLabel("KEY ID:");
		lblNewLabel.setFont(new Font("Tahoma", Font.PLAIN, 18));
		lblNewLabel.setForeground(Color.WHITE);

		JTextArea txtbData = new JTextArea();
		txtbData.setLineWrap(true);
		txtbData.setForeground(Color.BLACK);
		txtbData.setToolTipText("");
		txtbData.setFont(new Font("Monospaced", Font.PLAIN, 14));


		JScrollPane scrollPane = new JScrollPane(txtbData, JScrollPane.VERTICAL_SCROLLBAR_ALWAYS, JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
		scrollPane.addMouseWheelListener(new MouseWheelListener() {
			public void mouseWheelMoved(MouseWheelEvent e) {
				if (e.isControlDown()) { // Zoom con rotella del mouse
					txtbData.setFont(new java.awt.Font(txtbData.getFont().getFontName(), txtbData.getFont().getStyle(),
							e.getUnitsToScroll() > 0 ? txtbData.getFont().getSize() - 2 
									: txtbData.getFont().getSize() + 2));
				}
			}
		});

		JComboBox cmbKID = new JComboBox();
		cmbKID.setBackground(Color.WHITE);
		cmbKID.addPopupMenuListener(new PopupMenuListener() {
			public void popupMenuCanceled(PopupMenuEvent e) {
			}
			public void popupMenuWillBecomeInvisible(PopupMenuEvent e) {
			}
			public void popupMenuWillBecomeVisible(PopupMenuEvent e) {
				try {
					updateKeysList(cmbKID); // Aggiorna la lista delle chiavi
				} catch (Exception e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				} 
			}
		});
		cmbKID.setToolTipText("KeyStore Key name");
		cmbKID.setFont(new Font("Tahoma", Font.PLAIN, 16));

		JButton btnEncrypt = new JButton("ENCRYPT");
		btnEncrypt.setBackground(Color.WHITE);
		btnEncrypt.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String kid = cmbKID.getSelectedItem().toString();
				try {
					String encrypted = encrypt(txtbData.getText().toString(),kid);
					// Check spacing

					int spacing = prefs.getInt("spacing", 0); // Spazio
					int encoding = prefs.getInt("encoding", 0); //Encoding
					if(spacing>0 && encoding !=3) // Spaziatura non disponibile per codifica PGP Wordlist
						txtbData.setText(addSpaces(encrypted,spacing));
					else
						txtbData.setText(encrypted);
				} catch (Exception e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
		});
		btnEncrypt.setFont(new Font("Tahoma", Font.BOLD, 13));

		JButton btnDecrypt = new JButton("DECRYPT");
		btnDecrypt.setBackground(Color.WHITE);
		btnDecrypt.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String kid = cmbKID.getSelectedItem().toString();
				String encryptedData = "";
				int encoding = prefs.getInt("encoding", 0);
				encryptedData = txtbData.getText().toString();
				
				encryptedData = encryptedData.replace("\n", ""); 
				encryptedData = encryptedData.replace("\r", "");

				if(encoding !=3)  // Rimuove gli spazi se e solo se l'opzione di codifica non è PGP Wordlist
					encryptedData = encryptedData.replace(" ", "");

				try {
					txtbData.setText(decrypt(encryptedData,kid)); // Decripta (e rimuove gli spazi dal testo codificato)
				} catch(Exception e1) {
					e1.printStackTrace();
					JOptionPane.showMessageDialog(null,e1.getMessage());
				}
			}
		});
		btnDecrypt.setFont(new Font("Tahoma", Font.BOLD, 13));

		JButton btnZeroize = new JButton("ZEROIZE");
		btnZeroize.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					zeroize(); // EMERGENCY ZEROIZE
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
		});
		btnZeroize.setToolTipText("EMERGENCY ZEROIZE");
		btnZeroize.setForeground(Color.WHITE);
		btnZeroize.setBackground(Color.RED);
		btnZeroize.setFont(new Font("Tahoma", Font.BOLD, 14));

		JButton btnSettings = new JButton("");
		btnSettings.setBackground(Color.WHITE);
		btnSettings.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Settings_Window settings = new Settings_Window();
				settings.main(null);

			}
		});
		btnSettings.setToolTipText("User Settings");
		btnSettings.setIcon(new ImageIcon(TextEncryptionUtil_Main.class.getResource("/it/HackerInside/TextEncryptionUtility/res/icons8-support-30.png")));

		JLabel lblFrancescoValentini = new JLabel("Francesco Valentini - 2023");
		lblFrancescoValentini.setForeground(Color.WHITE);
		lblFrancescoValentini.setFont(new Font("Tahoma", Font.PLAIN, 14));


		// TextArea prefs
		txtbData.setWrapStyleWord(prefs.getBoolean("lineWrap", false));
		txtbData.setLineWrap(prefs.getBoolean("lineWrap", false));

		JPopupMenu popupMenu = new JPopupMenu();
		addPopup(txtbData, popupMenu);

		JButton btnOpenFile = new JButton("Open File"); // Bottone del menù contestuale per aprire un file.
		btnOpenFile.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				JFileChooser chooser = new JFileChooser();
				int returnVal = chooser.showOpenDialog(null);
				if(returnVal == JFileChooser.APPROVE_OPTION) {
					txtbData.setText(readTextFile(chooser.getSelectedFile()));
				}

			}
		});
		popupMenu.add(btnOpenFile);

		JButton btnSaveFile = new JButton("Save File");
		btnSaveFile.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				JFrame parentFrame = new JFrame();
				JFileChooser fileChooser = new JFileChooser();
				FileNameExtensionFilter filter = new FileNameExtensionFilter("Text File", "TXT");
				fileChooser.setFileFilter(filter);
				int userSelection = fileChooser.showSaveDialog(parentFrame);

				if (userSelection == JFileChooser.APPROVE_OPTION) {
					writeTextToFile(fileChooser.getSelectedFile(),txtbData.getText().toString());
				}

			}
		});
		popupMenu.add(btnSaveFile);
		GroupLayout gl_panel = new GroupLayout(panel);
		gl_panel.setHorizontalGroup(
			gl_panel.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_panel.createSequentialGroup()
					.addGap(10)
					.addGroup(gl_panel.createParallelGroup(Alignment.TRAILING)
						.addComponent(scrollPane, Alignment.LEADING)
						.addComponent(panel_1, Alignment.LEADING, GroupLayout.DEFAULT_SIZE, 1012, Short.MAX_VALUE))
					.addGap(10))
		);
		gl_panel.setVerticalGroup(
			gl_panel.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_panel.createSequentialGroup()
					.addGap(11)
					.addComponent(panel_1, GroupLayout.PREFERRED_SIZE, 65, GroupLayout.PREFERRED_SIZE)
					.addGap(11)
					.addComponent(scrollPane, GroupLayout.DEFAULT_SIZE, 600, Short.MAX_VALUE)
					.addContainerGap())
		);
		GroupLayout gl_panel_1 = new GroupLayout(panel_1);
		gl_panel_1.setHorizontalGroup(
			gl_panel_1.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_panel_1.createSequentialGroup()
					.addGap(4)
					.addGroup(gl_panel_1.createParallelGroup(Alignment.LEADING)
						.addGroup(gl_panel_1.createSequentialGroup()
							.addComponent(lblNewLabel, GroupLayout.DEFAULT_SIZE, 74, Short.MAX_VALUE)
							.addGap(247))
						.addGroup(gl_panel_1.createSequentialGroup()
							.addGap(69)
							.addComponent(cmbKID, 0, 252, Short.MAX_VALUE)))
					.addGap(10)
					.addComponent(btnEncrypt, GroupLayout.DEFAULT_SIZE, 98, Short.MAX_VALUE)
					.addGap(10)
					.addComponent(btnDecrypt, GroupLayout.DEFAULT_SIZE, 98, Short.MAX_VALUE)
					.addGap(66)
					.addComponent(lblFrancescoValentini, GroupLayout.DEFAULT_SIZE, 173, Short.MAX_VALUE)
					.addGap(64)
					.addComponent(btnSettings, GroupLayout.PREFERRED_SIZE, 40, GroupLayout.PREFERRED_SIZE)
					.addGap(14)
					.addComponent(btnZeroize, GroupLayout.PREFERRED_SIZE, 102, GroupLayout.PREFERRED_SIZE))
		);
		gl_panel_1.setVerticalGroup(
			gl_panel_1.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_panel_1.createSequentialGroup()
					.addGap(1)
					.addGroup(gl_panel_1.createParallelGroup(Alignment.LEADING)
						.addGroup(gl_panel_1.createSequentialGroup()
							.addGap(10)
							.addComponent(lblNewLabel, GroupLayout.PREFERRED_SIZE, 14, GroupLayout.PREFERRED_SIZE))
						.addComponent(cmbKID, GroupLayout.PREFERRED_SIZE, 36, GroupLayout.PREFERRED_SIZE)))
				.addGroup(gl_panel_1.createSequentialGroup()
					.addGap(1)
					.addComponent(btnEncrypt, GroupLayout.PREFERRED_SIZE, 36, GroupLayout.PREFERRED_SIZE))
				.addGroup(gl_panel_1.createSequentialGroup()
					.addGap(1)
					.addComponent(btnDecrypt, GroupLayout.PREFERRED_SIZE, 36, GroupLayout.PREFERRED_SIZE))
				.addGroup(gl_panel_1.createSequentialGroup()
					.addGap(12)
					.addComponent(lblFrancescoValentini, GroupLayout.PREFERRED_SIZE, 14, GroupLayout.PREFERRED_SIZE))
				.addGroup(gl_panel_1.createSequentialGroup()
					.addGap(1)
					.addComponent(btnSettings, GroupLayout.PREFERRED_SIZE, 36, GroupLayout.PREFERRED_SIZE))
				.addGroup(gl_panel_1.createSequentialGroup()
					.addGap(1)
					.addComponent(btnZeroize, GroupLayout.PREFERRED_SIZE, 36, GroupLayout.PREFERRED_SIZE))
		);
		panel_1.setLayout(gl_panel_1);
		panel.setLayout(gl_panel);
	}

	public static void updateKeysList(JComboBox<String> jcombo) throws Exception { // Aggiorna la lista delle chiavi
		jcombo.removeAllItems();
		for(Enumeration<String> e = KeyStoreUtils.showAliases(ks); e.hasMoreElements();) {
			String element = e.nextElement();
			Key k = KeyStoreUtils.getKey(ks,keyStorePassword,element);
			if(k instanceof SecretKey)
				jcombo.addItem(element);
		}

	}

	public static String encrypt(String text, String KID) throws Exception { // Encrypt text
		SecretKey key = KeyStoreUtils.getSecretKey(ks, keyStorePassword, KID);
		int encoding = prefs.getInt("encoding", 0);
		boolean compression = prefs.getBoolean("compression", false);
		String encrypted = "";
		if(encoding == 0)
			encrypted = AES256.encryptDecryptString(Cipher.ENCRYPT_MODE, text, key,"base64",compression);
		else if(encoding == 1)
			encrypted = AES256.encryptDecryptString(Cipher.ENCRYPT_MODE, text, key,"base58",compression);
		else if(encoding == 2)
			encrypted = AES256.encryptDecryptString(Cipher.ENCRYPT_MODE, text, key,"hex",compression);
		else if(encoding == 3)
			encrypted = AES256.encryptDecryptString(Cipher.ENCRYPT_MODE, text, key,"pgpWordlist",compression);
		else if(encoding == 4)
			encrypted = AES256.encryptDecryptString(Cipher.ENCRYPT_MODE, text, key,"base36",compression);
		else if(encoding == 5)
			encrypted = AES256.encryptDecryptString(Cipher.ENCRYPT_MODE, text, key,"base32",compression);
		else if(encoding == 6)
			encrypted = AES256.encryptDecryptString(Cipher.ENCRYPT_MODE, text, key,"base32-c",compression);


		return encrypted;
	}

	public static String decrypt(String text, String KID) throws Exception { // Decrypt Text
		SecretKey key = KeyStoreUtils.getSecretKey(ks, keyStorePassword, KID);
		int encoding = prefs.getInt("encoding", 0);
		boolean compression = prefs.getBoolean("compression", false);
		String decrypted = "";
		if(encoding == 0)
			decrypted = AES256.encryptDecryptString(Cipher.DECRYPT_MODE, text, key,"base64",compression);
		else if(encoding == 1)
			decrypted = AES256.encryptDecryptString(Cipher.DECRYPT_MODE, text, key,"base58",compression);
		else if(encoding == 2)
			decrypted = AES256.encryptDecryptString(Cipher.DECRYPT_MODE, text, key,"hex",compression);
		else if(encoding == 3)
			decrypted = AES256.encryptDecryptString(Cipher.DECRYPT_MODE, text, key,"pgpWordlist",compression);
		else if(encoding == 4)
			decrypted = AES256.encryptDecryptString(Cipher.DECRYPT_MODE, text, key,"base36",compression);
		else if(encoding == 5)
			decrypted = AES256.encryptDecryptString(Cipher.DECRYPT_MODE, text, key,"base32",compression);
		else if(encoding == 6)
			decrypted = AES256.encryptDecryptString(Cipher.DECRYPT_MODE, text, key,"base32-c",compression);
		return decrypted;
	}

	public static String addSpaces(String s, int n) {
		StringBuilder sb = new StringBuilder();
		int pos = 0;
		for (char c : s.toCharArray()) {
			if (pos > 0 && pos % n == 0) {
				sb.append(" ");
			}
			sb.append(c);
			pos++;
		}
		return sb.toString();
	}

	public static void zeroize() throws IOException { // Emergency Zeroize
		if(twoFactor()) {
			try {
				Enumeration<String> aliases = ks.aliases();
				while (aliases.hasMoreElements()) {
					String alias = aliases.nextElement();
					ks.deleteEntry(alias);

				}
				KeyStoreUtils.saveKeyStore(ks,randomString(128),keyStoreFile);
				zeroize(keyStoreFile);
				ks = null;

			} catch (Exception e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			JOptionPane.showMessageDialog(null, "ZEROIZED!");

		}else {
			JOptionPane.showMessageDialog(null, "Wrong OTP");
		}

	}
	
	public static String randomString(int len) { // Genera stringhe casuali
		char[] chars = "ABCDEFGHJKLMNOPQRSTUVWXYZ0123456789".toCharArray();
		SecureRandom rnd = new SecureRandom();
		StringBuilder sb = new StringBuilder();
		for (int i = 0; i < len; i++)
			sb.append(chars[rnd.nextInt(chars.length)]);
		
		return sb.toString();
	}

	public static boolean twoFactor() { // Codice One Time
		String generated = randomString(5);
		JTextField pwd = new JTextField(10);
		JOptionPane.showMessageDialog(null,"OTP: " + generated);
		int action = JOptionPane.showConfirmDialog(null, pwd,"OTP",JOptionPane.OK_CANCEL_OPTION);
		if(action < 0)JOptionPane.showMessageDialog(null,"Cancel, X or escape key selected");

		String otp = new String(pwd.getText().toUpperCase());
		if(generated.equals(otp))
			return true;
		else
			return false;
	}

	public static void zeroize(String fileName) throws IOException { // sovrascrive il file più volte con pattern diversi
		File file = new File(fileName);
		RandomAccessFile raf = new RandomAccessFile(file, "rws");
		long length = raf.length();
		raf.seek(0);
		SecureRandom random = new SecureRandom();
		byte[] data = new byte[1024];
		for (int j = 0; j < 3; j++) {
			for (long i = 0; i < length; i += 1024) {
				if (j % 2 == 0) {
					raf.writeByte(0);
				} else {
					random.nextBytes(data);
					raf.write(data);
				}
			}
			raf.seek(0);
		}
		raf.close();
		file.delete();
	}


	public static void keyStoreWizard() throws KeyStoreException, NoSuchAlgorithmException, CertificateException, IOException, NoSuchProviderException { // Configurazione iniziale di un nuovo keystore
		String pwd = passwordInput("New KeyStore password");
		
		ks = KeyStoreUtils.newKeystore(pwd, keyStoreFile);
		
		JOptionPane.showMessageDialog(null,"KeyStore initialized successfully.");
	}

	private static void addPopup(Component component, final JPopupMenu popup) { // Menù contestuale textArea principale
		component.addMouseListener(new MouseAdapter() {
			public void mousePressed(MouseEvent e) {
				if (e.isPopupTrigger()) {
					showMenu(e);
				}
			}
			public void mouseReleased(MouseEvent e) {
				if (e.isPopupTrigger()) {
					showMenu(e);
				}
			}
			private void showMenu(MouseEvent e) {
				popup.show(e.getComponent(), e.getX(), e.getY());
			}
		});
	}

	private static String readTextFile(File f) {
		String data = "";
		try {
			Scanner s = new Scanner(f);
			while(s.hasNextLine()) {
				data = data + s.nextLine();
			}
			s.close();
		} catch (FileNotFoundException e) {
			JOptionPane.showMessageDialog(null,e.getMessage());
			e.printStackTrace();
		}
		return data;
	}

	private static void writeTextToFile(File f,String data) {
		try {
			FileWriter myWriter = new FileWriter(f);
			myWriter.write(data);
			myWriter.close();

		} catch (IOException e) {
			JOptionPane.showMessageDialog(null,e.getMessage());
			e.printStackTrace();
		}
		return;
	}
}
