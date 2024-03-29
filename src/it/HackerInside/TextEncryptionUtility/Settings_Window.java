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
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.security.UnrecoverableKeyException;
import java.security.cert.CertificateException;
import java.security.spec.InvalidKeySpecException;
import java.util.Arrays;
import java.util.Enumeration;
import java.util.prefs.Preferences;
import java.awt.event.ActionEvent;
import javax.swing.border.TitledBorder;
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
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.LayoutStyle.ComponentPlacement;

public class Settings_Window {

	private JFrame frmSettings;
	private JTextField txtbAliasChiave;
	private JTextField txtbKCV;
	private SecretKey sk;
	private Ecdh ec;
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



		JCheckBox chckbLineWrapping = new JCheckBox("TextArea Wrapping");
		chckbLineWrapping.setBackground(Color.WHITE);

		JSpinner spinnerSpacing = new JSpinner();
		spinnerSpacing.setBackground(Color.WHITE);
		spinnerSpacing.setModel(new SpinnerNumberModel(0, 0, 10, 1));
		spinnerSpacing.setFont(new Font("Tahoma", Font.PLAIN, 14));

		JLabel lblSpacing = new JLabel("SPACING:");
		lblSpacing.setForeground(Color.WHITE);
		lblSpacing.setFont(new Font("Tahoma", Font.PLAIN, 15));

		JLabel lblEncoding = new JLabel("ENCODING:");
		lblEncoding.setForeground(Color.WHITE);
		lblEncoding.setFont(new Font("Tahoma", Font.PLAIN, 15));

		JComboBox cmbEncoding = new JComboBox();
		cmbEncoding.setBackground(Color.WHITE);
		cmbEncoding.setToolTipText("");
		cmbEncoding.setFont(new Font("Tahoma", Font.PLAIN, 15));

		cmbEncoding.setModel(new DefaultComboBoxModel(new String[] {"Base64", "Base58", "Hex", "PGP Word list", "Base36", "Base32", "Base32-C"}));

		JCheckBox chckbCompression = new JCheckBox("GZIP Compression");
		chckbCompression.setBackground(Color.WHITE);
		
		JButton btnSaveSettings = new JButton("SAVE");
		btnSaveSettings.setBackground(Color.WHITE);
		btnSaveSettings.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				save(cmbEncoding.getSelectedIndex(),(int) spinnerSpacing.getValue(),chckbLineWrapping.isSelected(),chckbCompression.isSelected());
			}
		});
		btnSaveSettings.setFont(new Font("Tahoma", Font.BOLD, 14));

		// Load prefs
		try {
			Preferences prefs = Preferences.userNodeForPackage(it.HackerInside.TextEncryptionUtility.TextEncryptionUtil_Main.class);

			cmbEncoding.setSelectedIndex(prefs.getInt("encoding", 0));
			spinnerSpacing.setValue(prefs.getInt("spacing", 0));
			GroupLayout gl_panel = new GroupLayout(panel);
			gl_panel.setHorizontalGroup(
				gl_panel.createParallelGroup(Alignment.LEADING)
					.addGroup(gl_panel.createSequentialGroup()
						.addGap(10)
						.addGroup(gl_panel.createParallelGroup(Alignment.LEADING)
							.addComponent(lblEncoding, GroupLayout.PREFERRED_SIZE, 87, GroupLayout.PREFERRED_SIZE)
							.addGroup(gl_panel.createSequentialGroup()
								.addGap(83)
								.addComponent(cmbEncoding, GroupLayout.PREFERRED_SIZE, 200, GroupLayout.PREFERRED_SIZE))))
					.addGroup(gl_panel.createSequentialGroup()
						.addGap(10)
						.addGroup(gl_panel.createParallelGroup(Alignment.LEADING)
							.addComponent(lblSpacing, GroupLayout.PREFERRED_SIZE, 87, GroupLayout.PREFERRED_SIZE)
							.addGroup(gl_panel.createSequentialGroup()
								.addGap(83)
								.addComponent(spinnerSpacing, GroupLayout.PREFERRED_SIZE, 51, GroupLayout.PREFERRED_SIZE))))
					.addGroup(gl_panel.createSequentialGroup()
						.addGap(10)
						.addComponent(chckbCompression, GroupLayout.DEFAULT_SIZE, 153, Short.MAX_VALUE)
						.addGap(289))
					.addGroup(gl_panel.createSequentialGroup()
						.addGap(10)
						.addComponent(chckbLineWrapping, GroupLayout.DEFAULT_SIZE, 153, Short.MAX_VALUE)
						.addGap(289))
					.addGroup(gl_panel.createSequentialGroup()
						.addGap(181)
						.addComponent(btnSaveSettings, GroupLayout.PREFERRED_SIZE, 89, GroupLayout.PREFERRED_SIZE))
			);
			gl_panel.setVerticalGroup(
				gl_panel.createParallelGroup(Alignment.LEADING)
					.addGroup(gl_panel.createSequentialGroup()
						.addGap(11)
						.addGroup(gl_panel.createParallelGroup(Alignment.LEADING)
							.addGroup(gl_panel.createSequentialGroup()
								.addGap(11)
								.addComponent(lblEncoding, GroupLayout.PREFERRED_SIZE, 14, GroupLayout.PREFERRED_SIZE))
							.addComponent(cmbEncoding, GroupLayout.PREFERRED_SIZE, 36, GroupLayout.PREFERRED_SIZE))
						.addGap(20)
						.addGroup(gl_panel.createParallelGroup(Alignment.LEADING)
							.addGroup(gl_panel.createSequentialGroup()
								.addGap(3)
								.addComponent(lblSpacing, GroupLayout.PREFERRED_SIZE, 14, GroupLayout.PREFERRED_SIZE))
							.addComponent(spinnerSpacing, GroupLayout.PREFERRED_SIZE, 20, GroupLayout.PREFERRED_SIZE))
						.addGap(7)
						.addComponent(chckbCompression)
						.addGap(7)
						.addComponent(chckbLineWrapping)
						.addGap(11)
						.addComponent(btnSaveSettings, GroupLayout.PREFERRED_SIZE, 36, GroupLayout.PREFERRED_SIZE))
			);
			panel.setLayout(gl_panel);
			


			JPanel panel_1 = new JPanel();
			panel_1.setBackground(Color.DARK_GRAY);
			tabbedPane.addTab("FILL", null, panel_1, null);

			JPanel panel_2 = new JPanel();
			panel_2.setLayout(null);
			panel_2.setForeground(Color.WHITE);
			panel_2.setBorder(new TitledBorder(new EtchedBorder(EtchedBorder.LOWERED, new Color(255, 255, 255), new Color(160, 160, 160)), "KEY", TitledBorder.LEADING, TitledBorder.TOP, null, new Color(255, 255, 255)));
			panel_2.setBackground(Color.DARK_GRAY);

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

			JLabel lblAliasChiave = new JLabel("ALIAS:");
			lblAliasChiave.setForeground(Color.WHITE);
			lblAliasChiave.setFont(new Font("Tahoma", Font.PLAIN, 14));

			txtbAliasChiave = new JTextField();
			txtbAliasChiave.setBackground(Color.WHITE);
			txtbAliasChiave.setFont(new Font("Tahoma", Font.PLAIN, 14));
			txtbAliasChiave.setColumns(10);

			JButton btnImportaChiave = new JButton("FILL");
			btnImportaChiave.setBackground(Color.WHITE);
			btnImportaChiave.setFont(new Font("Tahoma", Font.BOLD, 14));
			btnImportaChiave.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					if(!txtbAliasChiave.getText().toString().isEmpty()) {
						String walletpwd = passwordInput("Keystore Password");
						if(chckbxChiaveEsterna.isSelected() && !walletpwd.equalsIgnoreCase("")) {
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
						
						if(!walletpwd.equalsIgnoreCase("")) {
							try {
								TextEncryptionUtil_Main.ks = KeyStoreUtils.loadKeyStore(walletpwd,TextEncryptionUtil_Main.keyStoreFile); // Reload Keystore
								TextEncryptionUtil_Main.keyStorePassword = walletpwd;
							} catch (NoSuchAlgorithmException | CertificateException | KeyStoreException | IOException | NoSuchProviderException e1) {
								// TODO Auto-generated catch block
								e1.printStackTrace();
							}
							
						}else {
							JOptionPane.showMessageDialog(null, "Empty keystore password!");
						}

					}else {
						JOptionPane.showMessageDialog(null, "Empty key alias!");
					}

				}
			});

			JLabel lblKcv = new JLabel("KCV:");
			lblKcv.setForeground(Color.WHITE);
			lblKcv.setFont(new Font("Tahoma", Font.PLAIN, 14));

			txtbKCV = new JTextField();
			txtbKCV.setBackground(Color.WHITE);
			txtbKCV.setFont(new Font("Tahoma", Font.PLAIN, 14));
			txtbKCV.setEditable(false);
			txtbKCV.setColumns(10);

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
			GroupLayout gl_panel_1 = new GroupLayout(panel_1);
			gl_panel_1.setHorizontalGroup(
				gl_panel_1.createParallelGroup(Alignment.LEADING)
					.addGroup(gl_panel_1.createSequentialGroup()
						.addGap(10)
						.addComponent(panel_2, GroupLayout.PREFERRED_SIZE, 432, GroupLayout.PREFERRED_SIZE))
					.addGroup(gl_panel_1.createSequentialGroup()
						.addGap(10)
						.addComponent(lblAliasChiave, GroupLayout.PREFERRED_SIZE, 102, GroupLayout.PREFERRED_SIZE)
						.addComponent(txtbAliasChiave, GroupLayout.PREFERRED_SIZE, 330, GroupLayout.PREFERRED_SIZE))
					.addGroup(gl_panel_1.createSequentialGroup()
						.addGap(10)
						.addComponent(lblKcv, GroupLayout.PREFERRED_SIZE, 102, GroupLayout.PREFERRED_SIZE)
						.addComponent(txtbKCV, GroupLayout.PREFERRED_SIZE, 225, GroupLayout.PREFERRED_SIZE)
						.addGap(10)
						.addComponent(btnCalcKCV, GroupLayout.PREFERRED_SIZE, 95, GroupLayout.PREFERRED_SIZE))
					.addGroup(gl_panel_1.createSequentialGroup()
						.addGap(22)
						.addComponent(btnGeneraChiave, GroupLayout.PREFERRED_SIZE, 148, GroupLayout.PREFERRED_SIZE)
						.addGap(13)
						.addComponent(chckbxChiaveEsterna, GroupLayout.PREFERRED_SIZE, 154, GroupLayout.PREFERRED_SIZE))
					.addGroup(gl_panel_1.createSequentialGroup()
						.addGap(22)
						.addComponent(btnImportaChiave, GroupLayout.PREFERRED_SIZE, 148, GroupLayout.PREFERRED_SIZE))
			);
			gl_panel_1.setVerticalGroup(
				gl_panel_1.createParallelGroup(Alignment.LEADING)
					.addGroup(gl_panel_1.createSequentialGroup()
						.addGap(11)
						.addComponent(panel_2, GroupLayout.PREFERRED_SIZE, 116, GroupLayout.PREFERRED_SIZE)
						.addGap(11)
						.addGroup(gl_panel_1.createParallelGroup(Alignment.LEADING)
							.addComponent(lblAliasChiave, GroupLayout.PREFERRED_SIZE, 23, GroupLayout.PREFERRED_SIZE)
							.addComponent(txtbAliasChiave, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
						.addGap(11)
						.addGroup(gl_panel_1.createParallelGroup(Alignment.LEADING)
							.addComponent(lblKcv, GroupLayout.PREFERRED_SIZE, 23, GroupLayout.PREFERRED_SIZE)
							.addComponent(txtbKCV, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
							.addComponent(btnCalcKCV))
						.addGap(23)
						.addGroup(gl_panel_1.createParallelGroup(Alignment.LEADING)
							.addComponent(btnGeneraChiave, GroupLayout.PREFERRED_SIZE, 35, GroupLayout.PREFERRED_SIZE)
							.addComponent(chckbxChiaveEsterna))
						.addGap(11)
						.addComponent(btnImportaChiave, GroupLayout.PREFERRED_SIZE, 35, GroupLayout.PREFERRED_SIZE))
			);
			panel_1.setLayout(gl_panel_1);

			JPanel panel_3 = new JPanel();
			panel_3.setBackground(Color.DARK_GRAY);
			tabbedPane.addTab("KEYS", null, panel_3, null);

			JPanel panel3432 = new JPanel();
			panel3432.setLayout(null);
			panel3432.setForeground(Color.WHITE);
			panel3432.setBorder(new TitledBorder(new EtchedBorder(EtchedBorder.LOWERED, new Color(255, 255, 255), new Color(160, 160, 160)), "KEYS", TitledBorder.LEADING, TitledBorder.TOP, null, new Color(255, 255, 255)));
			panel3432.setBackground(Color.DARK_GRAY);

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
			GroupLayout gl_panel_3 = new GroupLayout(panel_3);
			gl_panel_3.setHorizontalGroup(
				gl_panel_3.createParallelGroup(Alignment.LEADING)
					.addGroup(gl_panel_3.createSequentialGroup()
						.addGap(22)
						.addComponent(panel3432, GroupLayout.DEFAULT_SIZE, 407, Short.MAX_VALUE)
						.addGap(23))
			);
			gl_panel_3.setVerticalGroup(
				gl_panel_3.createParallelGroup(Alignment.LEADING)
					.addGroup(gl_panel_3.createSequentialGroup()
						.addGap(11)
						.addComponent(panel3432, GroupLayout.PREFERRED_SIZE, 220, GroupLayout.PREFERRED_SIZE))
			);
			panel_3.setLayout(gl_panel_3);

			JPanel panel_4 = new JPanel();
			panel_4.setBackground(Color.DARK_GRAY);
			tabbedPane.addTab("ECDH", null, panel_4, null);

			JPanel panel_5 = new JPanel();
			panel_5.setBorder(new TitledBorder(new EtchedBorder(EtchedBorder.LOWERED, new Color(255, 255, 255), new Color(160, 160, 160)), "MY PUBLIC KEY", TitledBorder.LEADING, TitledBorder.TOP, null, Color.WHITE));
			panel_5.setBackground(Color.DARK_GRAY);
			panel_5.setLayout(new BorderLayout(0, 0));

			JTextArea txtbMyPublicKey = new JTextArea();
			txtbMyPublicKey.setEditable(false);
			txtbMyPublicKey.setLineWrap(true);
			panel_5.add(txtbMyPublicKey, BorderLayout.CENTER);

			JPanel panel_5_1 = new JPanel();
			panel_5_1.setBorder(new TitledBorder(new EtchedBorder(EtchedBorder.LOWERED, new Color(255, 255, 255), new Color(160, 160, 160)), "OTHER PUBLIC KEY", TitledBorder.LEADING, TitledBorder.TOP, null, new Color(255, 255, 255)));
			panel_5_1.setBackground(Color.DARK_GRAY);
			panel_5_1.setLayout(new BorderLayout(0, 0));

			JTextArea txtbOtherPublicKey = new JTextArea();
			txtbOtherPublicKey.setWrapStyleWord(true);
			txtbOtherPublicKey.setLineWrap(true);
			panel_5_1.add(txtbOtherPublicKey, BorderLayout.CENTER);

			JPanel panel_5_1_1 = new JPanel();
			panel_5_1_1.setBorder(new TitledBorder(new EtchedBorder(EtchedBorder.LOWERED, new Color(255, 255, 255), new Color(160, 160, 160)), "KEY", TitledBorder.LEADING, TitledBorder.TOP, null, new Color(255, 255, 255)));
			panel_5_1_1.setBackground(Color.DARK_GRAY);
			panel_5_1_1.setLayout(new BorderLayout(0, 0));

			JTextArea txtbGeneratedECKey = new JTextArea();
			txtbGeneratedECKey.setEditable(false);
			txtbGeneratedECKey.setWrapStyleWord(true);
			txtbGeneratedECKey.setLineWrap(true);
			txtbGeneratedECKey.setForeground(Color.RED);
			panel_5_1_1.add(txtbGeneratedECKey, BorderLayout.CENTER);
			JLabel lblNewLabel = new JLabel("KCV:");
			lblNewLabel.setForeground(Color.WHITE);
			lblNewLabel.setFont(new Font("Tahoma", Font.BOLD, 14));

			JLabel txtbKCVEC = new JLabel("---");
			txtbKCVEC.setForeground(Color.WHITE);
			txtbKCVEC.setFont(new Font("Tahoma", Font.BOLD, 14));

			JButton btnCalculateECDH = new JButton("2 - CALC");
			btnCalculateECDH.setBackground(Color.WHITE);
			btnCalculateECDH.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {

					// calcola il segreto condiviso (ECDH)
					try {
						byte[] derivedKey = ec.generateSharedSecret(txtbOtherPublicKey.getText());
						txtbGeneratedECKey.setText(bytesToHex(derivedKey));
						txtbKCVEC.setText(bytesToHex(ec.kcvAES(derivedKey)).substring(0, 6));
					} catch (InvalidKeyException | NoSuchAlgorithmException | InvalidKeySpecException | NoSuchPaddingException | IllegalBlockSizeException | BadPaddingException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}

				}
			});

			btnInitECDH = new JButton("1 - INIT");
			btnInitECDH.setBackground(Color.WHITE);
			btnInitECDH.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					// Inizializza ecdh
					try {
						ec = new Ecdh(384);
						txtbMyPublicKey.setText(ec.getPublicKey());

					} catch (NoSuchAlgorithmException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
				}
			});



			JButton btnKeystoreAdd = new JButton("3 - ADD");
			btnKeystoreAdd.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					String walletpwd = passwordInput("Keystore Password");
					String keyAlias = textInput("Key Alias");
					SecretKey originalKey = new SecretKeySpec(hexStringToByteArray(txtbGeneratedECKey.getText().toString()), 0,hexStringToByteArray(txtbGeneratedECKey.getText().toString()).length , "AES");
					try {
						KeyStoreUtils.addSecretKey(TextEncryptionUtil_Main.ks, walletpwd, keyAlias.toString(), originalKey);
						KeyStoreUtils.saveKeyStore(TextEncryptionUtil_Main.ks, walletpwd, TextEncryptionUtil_Main.keyStoreFile);
					} catch (KeyStoreException | NoSuchAlgorithmException | CertificateException | IOException e1) {
						// TODO Auto-generated catch block
						JOptionPane.showMessageDialog(null, e1.toString());
						e1.printStackTrace();
					}
					JOptionPane.showMessageDialog(null, "Key Added!");
				}
			});
			btnKeystoreAdd.setBackground(Color.WHITE);
			GroupLayout gl_panel_4 = new GroupLayout(panel_4);
			gl_panel_4.setHorizontalGroup(
				gl_panel_4.createParallelGroup(Alignment.LEADING)
					.addGroup(gl_panel_4.createSequentialGroup()
						.addGap(10)
						.addGroup(gl_panel_4.createParallelGroup(Alignment.LEADING)
							.addGroup(gl_panel_4.createSequentialGroup()
								.addGap(39)
								.addComponent(txtbKCVEC, GroupLayout.PREFERRED_SIZE, 74, GroupLayout.PREFERRED_SIZE))
							.addComponent(lblNewLabel, GroupLayout.PREFERRED_SIZE, 46, GroupLayout.PREFERRED_SIZE)))
					.addGroup(gl_panel_4.createSequentialGroup()
						.addGap(76)
						.addComponent(btnInitECDH, GroupLayout.DEFAULT_SIZE, 89, Short.MAX_VALUE)
						.addGap(10)
						.addComponent(btnCalculateECDH, GroupLayout.DEFAULT_SIZE, 89, Short.MAX_VALUE)
						.addGap(10)
						.addComponent(btnKeystoreAdd, GroupLayout.DEFAULT_SIZE, 89, Short.MAX_VALUE)
						.addGap(89))
					.addGroup(gl_panel_4.createSequentialGroup()
						.addContainerGap()
						.addComponent(panel_5, GroupLayout.DEFAULT_SIZE, 432, Short.MAX_VALUE)
						.addContainerGap())
					.addGroup(gl_panel_4.createSequentialGroup()
						.addContainerGap()
						.addComponent(panel_5_1, GroupLayout.DEFAULT_SIZE, 432, Short.MAX_VALUE)
						.addContainerGap())
					.addGroup(gl_panel_4.createSequentialGroup()
						.addContainerGap()
						.addComponent(panel_5_1_1, GroupLayout.DEFAULT_SIZE, 432, Short.MAX_VALUE)
						.addContainerGap())
			);
			gl_panel_4.setVerticalGroup(
				gl_panel_4.createParallelGroup(Alignment.LEADING)
					.addGroup(gl_panel_4.createSequentialGroup()
						.addGap(5)
						.addComponent(panel_5, GroupLayout.PREFERRED_SIZE, 83, GroupLayout.PREFERRED_SIZE)
						.addGap(1)
						.addComponent(panel_5_1, GroupLayout.PREFERRED_SIZE, 94, GroupLayout.PREFERRED_SIZE)
						.addPreferredGap(ComponentPlacement.RELATED)
						.addComponent(panel_5_1_1, GroupLayout.PREFERRED_SIZE, 62, GroupLayout.PREFERRED_SIZE)
						.addGap(11)
						.addGroup(gl_panel_4.createParallelGroup(Alignment.LEADING)
							.addComponent(txtbKCVEC, GroupLayout.PREFERRED_SIZE, 14, GroupLayout.PREFERRED_SIZE)
							.addComponent(lblNewLabel, GroupLayout.PREFERRED_SIZE, 14, GroupLayout.PREFERRED_SIZE))
						.addGap(20)
						.addGroup(gl_panel_4.createParallelGroup(Alignment.LEADING)
							.addComponent(btnInitECDH, GroupLayout.PREFERRED_SIZE, 52, GroupLayout.PREFERRED_SIZE)
							.addComponent(btnCalculateECDH, GroupLayout.PREFERRED_SIZE, 52, GroupLayout.PREFERRED_SIZE)
							.addComponent(btnKeystoreAdd, GroupLayout.PREFERRED_SIZE, 52, GroupLayout.PREFERRED_SIZE)))
			);
			panel_4.setLayout(gl_panel_4);
			
			if(prefs.getBoolean("lineWrap", false)) {
				chckbLineWrapping.setSelected(true);
			}else {
				chckbLineWrapping.setSelected(false);
			}
			
			if(prefs.getBoolean("compression", false)) {
				chckbCompression.setSelected(true);
			}else {
				chckbCompression.setSelected(false);
			}
		}catch (Exception e) {
			e.printStackTrace();
		}


	}

	public static void save(int encoding, int spacing, boolean lineWrap,boolean compression) {
		Preferences prefs = Preferences.userNodeForPackage(it.HackerInside.TextEncryptionUtility.TextEncryptionUtil_Main.class);
		prefs.putInt("encoding", encoding);
		prefs.putInt("spacing", spacing);
		prefs.putBoolean("lineWrap", lineWrap);
		prefs.putBoolean("compression", compression);
		
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
	private JButton btnInitECDH;
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

	public static String textInput(String title) {
		JTextField text = new JTextField();
		int action = JOptionPane.showConfirmDialog(null, text,title,JOptionPane.OK_CANCEL_OPTION);
		if(action < 0)JOptionPane.showMessageDialog(null,"Cancel, X or escape key selected");
		return new String(text.getText().toString());
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
