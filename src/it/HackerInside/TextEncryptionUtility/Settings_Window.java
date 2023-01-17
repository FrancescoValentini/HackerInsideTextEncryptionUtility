package it.HackerInside.TextEncryptionUtility;

import java.awt.EventQueue;

import javax.swing.JFrame;
import java.awt.Color;
import javax.swing.JPanel;
import javax.swing.JPasswordField;

import java.awt.BorderLayout;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

import java.awt.Font;
import javax.swing.JComboBox;
import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JSpinner;
import javax.swing.SpinnerNumberModel;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JTabbedPane;
import java.awt.event.ActionListener;
import java.security.InvalidKeyException;
import java.security.Key;
import java.security.KeyStoreException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.UnrecoverableKeyException;
import java.security.cert.CertificateException;
import java.util.Arrays;
import java.util.Enumeration;
import java.util.prefs.Preferences;
import java.awt.event.ActionEvent;
import javax.swing.border.TitledBorder;


import javax.swing.UIManager;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.io.IOException;

import javax.swing.event.ChangeListener;


import javax.swing.event.ChangeEvent;
import javax.swing.border.EtchedBorder;
import javax.swing.event.PopupMenuListener;
import javax.swing.event.PopupMenuEvent;

public class Settings_Window {

	private JFrame frmSettings;
	private JTextField txtbAliasChiave;
	private JTextField txtbKCV;
	private SecretKey sk;
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Settings_Window window = new Settings_Window();
					window.frmSettings.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public Settings_Window() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		
		frmSettings = new JFrame();
		frmSettings.setResizable(false);
		frmSettings.setTitle("HackerInside Text Encryption Utility | Settings");
		frmSettings.getContentPane().setBackground(Color.DARK_GRAY);
		frmSettings.setBackground(Color.DARK_GRAY);
		frmSettings.setBounds(100, 100, 473, 420);
		//frmSettings.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		JTabbedPane tabbedPane = new JTabbedPane(JTabbedPane.TOP);
		frmSettings.getContentPane().add(tabbedPane, BorderLayout.CENTER);
		
		JPanel panel = new JPanel();
		panel.setBackground(Color.DARK_GRAY);
		tabbedPane.addTab("General", null, panel, null);
		panel.setLayout(null);
		

		
		JCheckBox chckbLineWrapping = new JCheckBox("TextArea Wrapping");
		chckbLineWrapping.setBackground(Color.WHITE);
		chckbLineWrapping.setBounds(10, 99, 153, 23);
		panel.add(chckbLineWrapping);
		
		JSpinner spinnerSpacing = new JSpinner();
		spinnerSpacing.setBackground(Color.WHITE);
		spinnerSpacing.setModel(new SpinnerNumberModel(0, 0, 10, 1));
		spinnerSpacing.setFont(new Font("Tahoma", Font.PLAIN, 14));
		spinnerSpacing.setBounds(93, 67, 51, 20);
		panel.add(spinnerSpacing);
		
		JLabel lblSpacing = new JLabel("SPACING:");
		lblSpacing.setForeground(Color.WHITE);
		lblSpacing.setFont(new Font("Tahoma", Font.PLAIN, 15));
		lblSpacing.setBounds(10, 70, 87, 14);
		panel.add(lblSpacing);
		
		JLabel lblEncoding = new JLabel("ENCODING:");
		lblEncoding.setForeground(Color.WHITE);
		lblEncoding.setFont(new Font("Tahoma", Font.PLAIN, 15));
		lblEncoding.setBounds(10, 22, 87, 14);
		panel.add(lblEncoding);
		
		JComboBox cmbEncoding = new JComboBox();
		cmbEncoding.setBackground(Color.WHITE);
		cmbEncoding.setToolTipText("");
		cmbEncoding.setFont(new Font("Tahoma", Font.PLAIN, 15));
		cmbEncoding.setBounds(93, 11, 200, 36);
		panel.add(cmbEncoding);

		cmbEncoding.setModel(new DefaultComboBoxModel(new String[] {"Base64", "Base58", "Hex", "PGP Wordlist"}));
		
		JButton btnSaveSettings = new JButton("SAVE");
		btnSaveSettings.setBackground(Color.WHITE);
		btnSaveSettings.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				save(cmbEncoding.getSelectedIndex(),(int) spinnerSpacing.getValue(),chckbLineWrapping.isSelected());
			}
		});
		btnSaveSettings.setFont(new Font("Tahoma", Font.BOLD, 14));
		btnSaveSettings.setBounds(181, 158, 89, 36);
		panel.add(btnSaveSettings);
		
		// Load prefs
		try {
			Preferences prefs = Preferences.userNodeForPackage(it.HackerInside.TextEncryptionUtility.TextEncryptionUtil_Main.class);
			
			cmbEncoding.setSelectedIndex(prefs.getInt("encoding", 0));
			spinnerSpacing.setValue(prefs.getInt("spacing", 0));
			
			JPanel panel_1 = new JPanel();
			panel_1.setBackground(Color.DARK_GRAY);
			tabbedPane.addTab("FILL", null, panel_1, null);
			panel_1.setLayout(null);
			
			JPanel panel_2 = new JPanel();
			panel_2.setLayout(null);
			panel_2.setForeground(Color.WHITE);
			panel_2.setBorder(new TitledBorder(new EtchedBorder(EtchedBorder.LOWERED, new Color(255, 255, 255), new Color(160, 160, 160)), "KEY", TitledBorder.LEADING, TitledBorder.TOP, null, new Color(255, 255, 255)));
			panel_2.setBackground(Color.DARK_GRAY);
			panel_2.setBounds(10, 11, 432, 116);
			panel_1.add(panel_2);
			
			JTextArea txtbSecretKey = new JTextArea();
			txtbSecretKey.setForeground(Color.WHITE);
			txtbSecretKey.setBackground(Color.RED);
			txtbSecretKey.addKeyListener(new KeyAdapter() {
				@Override
				public void keyTyped(KeyEvent e) {
					try {
						if(txtbSecretKey.getText().length() == 64) {
							txtbKCV.setText(bytesToHex(kcvAES(hexStringToByteArray(txtbSecretKey.getText()))).substring(0, 6));
						}else {
							txtbKCV.setText("N/A");
						}
						
					} catch (InvalidKeyException | NoSuchAlgorithmException | NoSuchPaddingException
							| IllegalBlockSizeException | BadPaddingException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
				}
			});
			txtbSecretKey.setLineWrap(true);
			txtbSecretKey.setFont(new Font("Arial", Font.BOLD, 14));
			txtbSecretKey.setEditable(false);
			txtbSecretKey.setBounds(10, 21, 412, 84);
			panel_2.add(txtbSecretKey);
			
			JCheckBox chckbxChiaveEsterna = new JCheckBox("USER DEFINED KEY");
			chckbxChiaveEsterna.setBackground(Color.WHITE);
			chckbxChiaveEsterna.addChangeListener(new ChangeListener() {
				public void stateChanged(ChangeEvent e) {
					if(chckbxChiaveEsterna.isSelected()) {
						txtbSecretKey.setEditable(true);
					}else {
						txtbSecretKey.setEditable(false);
					}
				}
			});
			chckbxChiaveEsterna.setBounds(183, 218, 154, 23);
			panel_1.add(chckbxChiaveEsterna);
			
			JButton btnGeneraChiave = new JButton("RANDOM KEY");
			btnGeneraChiave.setBackground(Color.WHITE);
			btnGeneraChiave.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					try {
						sk = AES256.generateAESKey();
						txtbSecretKey.setText(bytesToHex(sk.getEncoded()));
					} catch (NoSuchAlgorithmException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
					
					try {
						if(txtbSecretKey.getText().length() == 64) {
							txtbKCV.setText(bytesToHex(kcvAES(hexStringToByteArray(txtbSecretKey.getText()))).substring(0, 6));
						}else {
							txtbKCV.setText("N/A");
						}
						
					} catch (InvalidKeyException | NoSuchAlgorithmException | NoSuchPaddingException
							| IllegalBlockSizeException | BadPaddingException e2) {
						// TODO Auto-generated catch block
						e2.printStackTrace();
					}
				}
			});
			btnGeneraChiave.setBounds(22, 218, 148, 35);
			panel_1.add(btnGeneraChiave);
			
			JLabel lblAliasChiave = new JLabel("ALIAS:");
			lblAliasChiave.setForeground(Color.WHITE);
			lblAliasChiave.setFont(new Font("Tahoma", Font.PLAIN, 14));
			lblAliasChiave.setBounds(10, 138, 102, 23);
			panel_1.add(lblAliasChiave);
			
			txtbAliasChiave = new JTextField();
			txtbAliasChiave.setBackground(Color.WHITE);
			txtbAliasChiave.setFont(new Font("Tahoma", Font.PLAIN, 14));
			txtbAliasChiave.setColumns(10);
			txtbAliasChiave.setBounds(112, 138, 330, 23);
			panel_1.add(txtbAliasChiave);
			
			JButton btnImportaChiave = new JButton("FILL");
			btnImportaChiave.setBackground(Color.WHITE);
			btnImportaChiave.setFont(new Font("Tahoma", Font.BOLD, 14));
			btnImportaChiave.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					if(!txtbAliasChiave.getText().toString().isEmpty()) {
						String walletpwd = passwordInput("Keystore Password");
						if(chckbxChiaveEsterna.isSelected()) {
							SecretKey originalKey = new SecretKeySpec(hexStringToByteArray(txtbSecretKey.getText().toString()), 0,hexStringToByteArray(txtbSecretKey.getText().toString()).length , "AES");
							try {
								KeyStoreUtils.addSecretKey(TextEncryptionUtil_Main.ks, walletpwd, txtbAliasChiave.getText().toString(), originalKey);
							} catch (KeyStoreException e1) {
								// TODO Auto-generated catch block
								e1.printStackTrace();
							}
						}else {
							try {
								KeyStoreUtils.addSecretKey(TextEncryptionUtil_Main.ks, walletpwd, txtbAliasChiave.getText().toString(), sk);
							} catch (KeyStoreException e1) {
								// TODO Auto-generated catch block
								e1.printStackTrace();
							}
						}
						try {
							KeyStoreUtils.saveKeyStore(TextEncryptionUtil_Main.ks, walletpwd, TextEncryptionUtil_Main.keyStoreFile);
						} catch (KeyStoreException | NoSuchAlgorithmException | CertificateException | IOException e1) {
							// TODO Auto-generated catch block
							e1.printStackTrace();
						}
						JOptionPane.showMessageDialog(null, "Key Added!");
						try {
							TextEncryptionUtil_Main.ks = KeyStoreUtils.loadKeyStore(walletpwd,TextEncryptionUtil_Main.keyStoreFile); // Reload Keystore
						} catch (NoSuchAlgorithmException | CertificateException | KeyStoreException | IOException e1) {
							// TODO Auto-generated catch block
							e1.printStackTrace();
						}
					}else {
						JOptionPane.showMessageDialog(null, "Empty key alias!");
					}

				}
			});
			btnImportaChiave.setBounds(22, 264, 148, 35);
			panel_1.add(btnImportaChiave);
			
			JLabel lblKcv = new JLabel("KCV:");
			lblKcv.setForeground(Color.WHITE);
			lblKcv.setFont(new Font("Tahoma", Font.PLAIN, 14));
			lblKcv.setBounds(10, 172, 102, 23);
			panel_1.add(lblKcv);
			
			txtbKCV = new JTextField();
			txtbKCV.setBackground(Color.WHITE);
			txtbKCV.setFont(new Font("Tahoma", Font.PLAIN, 14));
			txtbKCV.setEditable(false);
			txtbKCV.setColumns(10);
			txtbKCV.setBounds(112, 172, 225, 23);
			panel_1.add(txtbKCV);
			
			JButton btnCalcKCV = new JButton("CALC");
			btnCalcKCV.setBackground(Color.WHITE);
			btnCalcKCV.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					try {
						if(txtbSecretKey.getText().length() == 64) {
							txtbKCV.setText(bytesToHex(kcvAES(hexStringToByteArray(txtbSecretKey.getText()))).substring(0, 6));
						}else {
							txtbKCV.setText("N/A");
						}
						
					} catch (InvalidKeyException | NoSuchAlgorithmException | NoSuchPaddingException
							| IllegalBlockSizeException | BadPaddingException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
				}
			});
			btnCalcKCV.setBounds(347, 172, 95, 23);
			panel_1.add(btnCalcKCV);
			
			JPanel panel_3 = new JPanel();
			panel_3.setBackground(Color.DARK_GRAY);
			tabbedPane.addTab("KEYS", null, panel_3, null);
			panel_3.setLayout(null);
			
			JPanel panel3432 = new JPanel();
			panel3432.setLayout(null);
			panel3432.setForeground(Color.WHITE);
			panel3432.setBorder(new TitledBorder(new EtchedBorder(EtchedBorder.LOWERED, new Color(255, 255, 255), new Color(160, 160, 160)), "KEYS", TitledBorder.LEADING, TitledBorder.TOP, null, new Color(255, 255, 255)));
			panel3432.setBackground(Color.DARK_GRAY);
			panel3432.setBounds(22, 11, 407, 220);
			panel_3.add(panel3432);
			
			JComboBox cmbBoxKeyWallet_1_1 = new JComboBox();
			cmbBoxKeyWallet_1_1.setBackground(Color.WHITE);
			cmbBoxKeyWallet_1_1.addPopupMenuListener(new PopupMenuListener() {
				public void popupMenuCanceled(PopupMenuEvent e) {
				}
				public void popupMenuWillBecomeInvisible(PopupMenuEvent e) {
				}
				public void popupMenuWillBecomeVisible(PopupMenuEvent e) {
					try {
						updateKeysList(cmbBoxKeyWallet_1_1);
					} catch (Exception e2) {
						// TODO Auto-generated catch block
						e2.printStackTrace();
					}
				}
			});
			cmbBoxKeyWallet_1_1.setFont(new Font("Tahoma", Font.PLAIN, 13));
			cmbBoxKeyWallet_1_1.setBounds(139, 30, 241, 20);
			panel3432.add(cmbBoxKeyWallet_1_1);
			
			JLabel lblKeysWallet_1_1 = new JLabel("Key Alias:");
			lblKeysWallet_1_1.setForeground(Color.WHITE);
			lblKeysWallet_1_1.setFont(new Font("Tahoma", Font.PLAIN, 14));
			lblKeysWallet_1_1.setBounds(22, 23, 78, 33);
			panel3432.add(lblKeysWallet_1_1);
			
			JButton btnDelete = new JButton("DELETE");
			btnDelete.setBackground(Color.WHITE);
			btnDelete.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					if(TextEncryptionUtil_Main.twoFactor()) {
						String alias = cmbBoxKeyWallet_1_1.getSelectedItem().toString();
						try {
							KeyStoreUtils.deleteSecretKey(TextEncryptionUtil_Main.ks,new String(txtbKeyStoreKey.getPassword()),alias);
							KeyStoreUtils.saveKeyStore(TextEncryptionUtil_Main.ks,new String(txtbKeyStoreKey.getPassword()),TextEncryptionUtil_Main.keyStoreFile);
							JOptionPane.showMessageDialog(null, "Key: " + alias +" Deleted!");
						} catch (KeyStoreException e1) {
							// TODO Auto-generated catch block
							e1.printStackTrace();
						} catch (NoSuchAlgorithmException e1) {
							// TODO Auto-generated catch block
							e1.printStackTrace();
						} catch (CertificateException e1) {
							// TODO Auto-generated catch block
							e1.printStackTrace();
						} catch (IOException e1) {
							// TODO Auto-generated catch block
							e1.printStackTrace();
						}
						
					}else {
						JOptionPane.showMessageDialog(null, "Wrong OTP");
					}
				}
			});
			btnDelete.setBounds(32, 159, 91, 23);
			panel3432.add(btnDelete);
			
			JLabel lblKeysWallet_1_1_1 = new JLabel("KeyStore Key:");
			lblKeysWallet_1_1_1.setForeground(Color.WHITE);
			lblKeysWallet_1_1_1.setFont(new Font("Tahoma", Font.PLAIN, 14));
			lblKeysWallet_1_1_1.setBounds(22, 53, 101, 33);
			panel3432.add(lblKeysWallet_1_1_1);
			
			txtbKeyStoreKey = new JPasswordField();
			txtbKeyStoreKey.setBackground(Color.WHITE);
			txtbKeyStoreKey.setBounds(139, 61, 241, 20);
			panel3432.add(txtbKeyStoreKey);
			
			JButton btnShow = new JButton("SHOW");
			btnShow.setBackground(Color.WHITE);
			btnShow.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					String alias = cmbBoxKeyWallet_1_1.getSelectedItem().toString();
					try {
						SecretKey sk = KeyStoreUtils.getSecretKey(TextEncryptionUtil_Main.ks,new String(txtbKeyStoreKey.getPassword()),alias);
						txtbAesKey.setText(bytesToHex(sk.getEncoded()));
					} catch (UnrecoverableKeyException | KeyStoreException | NoSuchAlgorithmException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
					
					try {
						if(txtbAesKey.getText().length() == 64) {
							txtbKCV_1.setText(bytesToHex(kcvAES(hexStringToByteArray(txtbAesKey.getText()))).substring(0, 6));
						}else {
							txtbKCV_1.setText("N/A");
						}
						
					} catch (InvalidKeyException | NoSuchAlgorithmException | NoSuchPaddingException
							| IllegalBlockSizeException | BadPaddingException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
				}
			});
			btnShow.setBounds(283, 159, 97, 23);
			panel3432.add(btnShow);
			
			JLabel lblKeysWallet_1_1_1_1 = new JLabel("AES KEY:");
			lblKeysWallet_1_1_1_1.setForeground(Color.WHITE);
			lblKeysWallet_1_1_1_1.setFont(new Font("Tahoma", Font.BOLD, 14));
			lblKeysWallet_1_1_1_1.setBounds(22, 90, 78, 33);
			panel3432.add(lblKeysWallet_1_1_1_1);
			
			txtbAesKey = new JTextField();
			txtbAesKey.setBackground(Color.WHITE);
			txtbAesKey.setForeground(Color.RED);
			txtbAesKey.setFont(new Font("Tahoma", Font.BOLD, 14));
			txtbAesKey.setEditable(false);
			txtbAesKey.setColumns(10);
			txtbAesKey.setBounds(98, 97, 282, 20);
			panel3432.add(txtbAesKey);
			
			txtbKCV_1 = new JTextField();
			txtbKCV_1.setBackground(Color.WHITE);
			txtbKCV_1.setFont(new Font("Tahoma", Font.PLAIN, 14));
			txtbKCV_1.setEditable(false);
			txtbKCV_1.setColumns(10);
			txtbKCV_1.setBounds(98, 128, 163, 23);
			panel3432.add(txtbKCV_1);
			
			JLabel lblKcv_1 = new JLabel("KCV:");
			lblKcv_1.setForeground(Color.WHITE);
			lblKcv_1.setFont(new Font("Tahoma", Font.PLAIN, 14));
			lblKcv_1.setBounds(22, 128, 78, 23);
			panel3432.add(lblKcv_1);
			if(prefs.getBoolean("lineWrap", false)) {
				chckbLineWrapping.setSelected(true);
			}else {
				chckbLineWrapping.setSelected(false);
			}
		}catch (Exception e) {
			e.printStackTrace();
		}

		
	}
	
	public static void save(int encoding, int spacing, boolean lineWrap) {
		Preferences prefs = Preferences.userNodeForPackage(it.HackerInside.TextEncryptionUtility.TextEncryptionUtil_Main.class);
		prefs.putInt("encoding", encoding);
		prefs.putInt("spacing", spacing);
		prefs.putBoolean("lineWrap", lineWrap);
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
	
	
	
	private static final char[] HEX_ARRAY = "0123456789ABCDEF".toCharArray();
	private JPasswordField txtbKeyStoreKey;
	private JTextField txtbAesKey;
	private JTextField txtbKCV_1;
	public static String bytesToHex(byte[] bytes) {
	    char[] hexChars = new char[bytes.length * 2];
	    for (int j = 0; j < bytes.length; j++) {
	        int v = bytes[j] & 0xFF;
	        hexChars[j * 2] = HEX_ARRAY[v >>> 4];
	        hexChars[j * 2 + 1] = HEX_ARRAY[v & 0x0F];
	    }
	    return new String(hexChars);
	}
	
	public static byte[] kcvAES(byte[] keyBytes) throws NoSuchAlgorithmException, NoSuchPaddingException, InvalidKeyException, IllegalBlockSizeException, BadPaddingException
	{
		byte[] binaryZeroes = new byte[16];
		Arrays.fill(binaryZeroes, (byte) 01);
		final SecretKey key = new SecretKeySpec(keyBytes, "AES");
		final Cipher cipher = Cipher.getInstance("AES");
		cipher.init(Cipher.ENCRYPT_MODE, key);
		byte[] cipherText = cipher.doFinal(binaryZeroes);
		return cipherText;
	}
	public static String passwordInput(String title) {
		JPasswordField pwd = new JPasswordField(10);
		int action = JOptionPane.showConfirmDialog(null, pwd,title,JOptionPane.OK_CANCEL_OPTION);
		if(action < 0)JOptionPane.showMessageDialog(null,"Cancel, X or escape key selected");
		return new String(pwd.getPassword());
	}
	public static void updateKeysList(JComboBox<String> jcombo) throws Exception { // Aggiorna la lista delle chiavi
		jcombo.removeAllItems();
		for(Enumeration<String> e = KeyStoreUtils.showAliases(TextEncryptionUtil_Main.ks); e.hasMoreElements();) {
			String element = e.nextElement();
			Key k = KeyStoreUtils.getKey(TextEncryptionUtil_Main.ks,TextEncryptionUtil_Main.keyStorePassword,element);
			if(k instanceof SecretKey)
				jcombo.addItem(element);
		}

	}
}
