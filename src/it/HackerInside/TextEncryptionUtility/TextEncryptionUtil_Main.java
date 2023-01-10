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
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.security.Key;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.cert.CertificateException;
import java.util.Enumeration;
import java.util.Random;
import java.util.Scanner;
import java.util.prefs.Preferences;
import java.awt.event.MouseWheelEvent;
import javax.swing.event.PopupMenuListener;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.event.PopupMenuEvent;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.Toolkit;
import javax.swing.JPopupMenu;
import java.awt.Component;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class TextEncryptionUtil_Main {

	private JFrame frmHackerinsideTextEncryption;
	// Impostazioni KeyStore
	public static String keyStoreFile = "KeyStore.jks";
	public static KeyStore ks;
	public static boolean keystoreExist = false;
	static String keyStorePassword = "";
	private static Preferences prefs = Preferences.userNodeForPackage(it.HackerInside.TextEncryptionUtility.TextEncryptionUtil_Main.class);

	/**
	 * Launch the application.
	*/
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
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
			String password = passwordInput("Inserisci la password");
			keyStorePassword = password;
			try {
				ks = KeyStoreUtils.loadKeyStore(password,keyStoreFile);
				keystoreExist = true;
			} catch (NoSuchAlgorithmException | CertificateException | KeyStoreException | IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				JOptionPane.showMessageDialog(null, e.toString());
				System.exit(-1);;
			}
		}else {
			JOptionPane.showMessageDialog(null, "KeyStore not found, a new one will be created.");
			try {
				keyStoreWizard();
			} catch (KeyStoreException | NoSuchAlgorithmException | CertificateException | IOException e) {
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
		frmHackerinsideTextEncryption.setResizable(false);
		frmHackerinsideTextEncryption.setBounds(100, 100, 1048, 737);
		frmHackerinsideTextEncryption.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		

		JPanel panel = new JPanel();
		panel.setForeground(Color.WHITE);
		panel.setBackground(Color.DARK_GRAY);
		frmHackerinsideTextEncryption.getContentPane().add(panel, BorderLayout.CENTER);
		panel.setLayout(null);

		JPanel panel_1 = new JPanel();
		panel_1.setBackground(Color.DARK_GRAY);
		panel_1.setForeground(Color.WHITE);
		panel_1.setBorder(new TitledBorder(new EtchedBorder(EtchedBorder.LOWERED, new Color(255, 255, 255), new Color(160, 160, 160)), "Key Settings", TitledBorder.LEADING, TitledBorder.TOP, null, Color.WHITE));
		panel_1.setBounds(10, 11, 1012, 65);
		panel.add(panel_1);
		panel_1.setLayout(null);

		JLabel lblNewLabel = new JLabel("KEY ID:");
		lblNewLabel.setFont(new Font("Tahoma", Font.PLAIN, 18));
		lblNewLabel.setForeground(Color.WHITE);
		lblNewLabel.setBounds(10, 27, 74, 14);
		panel_1.add(lblNewLabel);

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
		scrollPane.setBounds(10, 87, 1012, 600);
		panel.add(scrollPane);
		
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
		cmbKID.setBounds(79, 17, 252, 36);
		panel_1.add(cmbKID);

		JButton btnEncrypt = new JButton("ENCRYPT");
		btnEncrypt.setBackground(Color.WHITE);
		btnEncrypt.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String kid = cmbKID.getSelectedItem().toString();
				try {
					String encrypted = encrypt(txtbData.getText().toString(),kid);
					// Check spacing
					
					int spacing = prefs.getInt("spacing", 0); // Spazio
					if(spacing>0)
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
		btnEncrypt.setBounds(341, 17, 98, 36);
		panel_1.add(btnEncrypt);

		JButton btnDecrypt = new JButton("DECRYPT");
		btnDecrypt.setBackground(Color.WHITE);
		btnDecrypt.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String kid = cmbKID.getSelectedItem().toString();
				try {
					txtbData.setText(decrypt(txtbData.getText().toString().replace(" ", ""),kid)); // Decripta (e rimuove gli spazi dal testo codificato)
				} catch(Exception e1) {
					e1.printStackTrace();
					JOptionPane.showMessageDialog(null,e1.getMessage());
				}
			}
		});
		btnDecrypt.setFont(new Font("Tahoma", Font.BOLD, 13));
		btnDecrypt.setBounds(449, 17, 98, 36);
		panel_1.add(btnDecrypt);

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
		btnZeroize.setBounds(904, 17, 98, 36);
		panel_1.add(btnZeroize);

		JButton btnSettings = new JButton("");
		btnSettings.setBackground(Color.WHITE);
		btnSettings.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Settings_Window settings = new Settings_Window();
				settings.main(null);

			}
		});
		btnSettings.setToolTipText("User Settings");
		btnSettings.setBounds(850, 17, 40, 36);
		panel_1.add(btnSettings);
		btnSettings.setIcon(new ImageIcon(TextEncryptionUtil_Main.class.getResource("/it/HackerInside/TextEncryptionUtility/res/icons8-support-30.png")));
		
		JLabel lblFrancescoValentini = new JLabel("Francesco Valentini - 2023");
		lblFrancescoValentini.setForeground(Color.WHITE);
		lblFrancescoValentini.setFont(new Font("Tahoma", Font.PLAIN, 14));
		lblFrancescoValentini.setBounds(613, 28, 173, 14);
		panel_1.add(lblFrancescoValentini);

		
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
		String encrypted = "";
		if(encoding == 0)
			encrypted = AES256.encryptDecryptString(Cipher.ENCRYPT_MODE, text, key,"base64");
		else if(encoding == 1)
			encrypted = AES256.encryptDecryptString(Cipher.ENCRYPT_MODE, text, key,"base58");
		else if(encoding == 2)
			encrypted = AES256.encryptDecryptString(Cipher.ENCRYPT_MODE, text, key,"hex");
		return encrypted;
	}
	
	public static String decrypt(String text, String KID) throws Exception { // Decrypt Text
		SecretKey key = KeyStoreUtils.getSecretKey(ks, keyStorePassword, KID);
		int encoding = prefs.getInt("encoding", 0);
		String decrypted = "";
		if(encoding == 0)
			decrypted = AES256.encryptDecryptString(Cipher.DECRYPT_MODE, text, key,"base64");
		else if(encoding == 1)
			decrypted = AES256.encryptDecryptString(Cipher.DECRYPT_MODE, text, key,"base58");
		else if(encoding == 2)
			decrypted = AES256.encryptDecryptString(Cipher.DECRYPT_MODE, text, key,"hex");
		
		
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
				KeyStoreUtils.saveKeyStore(ks,"0000",keyStoreFile);
				overwriteFileWithRandom(keyStoreFile,Files.size( Paths.get(keyStoreFile)));
				
				
			} catch (Exception e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			JOptionPane.showMessageDialog(null, "ZEROIZED!");
		}else {
			JOptionPane.showMessageDialog(null, "Wrong OTP");
		}
		
	}
	
	   public static boolean twoFactor() { // Codice One Time
		    char[] chars = "ABCDEFGHJKLMNOPQRSTUVWXYZ0123456789".toCharArray();
		    Random rnd = new Random();
		    StringBuilder sb = new StringBuilder();
		    for (int i = 0; i < 5; i++)
		        sb.append(chars[rnd.nextInt(chars.length)]);

		    String generated = sb.toString();
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
	   
	public static void overwriteFileWithRandom(String fileName, long size) throws IOException { // Sovrascrive un file con caratteri random 
	    SecureRandom random = new SecureRandom();
	    FileOutputStream fos = new FileOutputStream(fileName);
	    byte[] buffer = new byte[1024];
	    while (size > 0) {
	        random.nextBytes(buffer);
	        int bytesToWrite = (int) Math.min(buffer.length, size);
	        fos.write(buffer, 0, bytesToWrite);
	        size -= bytesToWrite;
	    }
	    fos.close();
	}
	
	public static void keyStoreWizard() throws KeyStoreException, NoSuchAlgorithmException, CertificateException, IOException { // Configurazione iniziale di un nuovo keystore
		String pwd = passwordInput("New KeyStore password");
		ks  = KeyStore.getInstance("JCEKS");
		ks.load(null, pwd.toCharArray());
		KeyStoreUtils.saveKeyStore(ks,pwd,keyStoreFile);
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
