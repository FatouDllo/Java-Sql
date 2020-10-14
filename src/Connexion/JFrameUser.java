package Connexion;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JTable;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import com.jgoodies.forms.layout.FormLayout;
import com.jgoodies.forms.layout.ColumnSpec;
import com.jgoodies.forms.layout.RowSpec;

import TestMysql.Utilisateur;

import com.jgoodies.forms.layout.FormSpecs;
import javax.swing.border.TitledBorder;
import javax.swing.table.DefaultTableModel;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;

import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class JFrameUser extends JFrame {

	private JPanel contentPane;
	private JTable tableUtilisateur;
	private JTextField textFieldID;
	private JTextField textFieldNom;
	private JTextField textFieldPrenom;
	private JTextField textFieldAge;
	private ConnexionUtili userDao=new ConnexionUtili();
	private JButton btnSupprimer;
	private JButton btnModifier;

	
	
	private String url ="jdbc:mysql://localhost/java_tuto ?useUnicode=true &useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false& serverTimezone=UTC"  ;
	private String utilisateur = "Fatou";
    private String motDePasse = "FatouDiallo";
    protected Connection getConnection () {
		Connection connection =null; 
		try {
			Class.forName("com.mysql.cj.jdbc.Driver"); 
			connection = DriverManager.getConnection(url, utilisateur , motDePasse);
		}catch (Exception e) {
			e.printStackTrace();
			System.out.println("La connexion a échouée");
			
		}
		return connection; 
	}
	
	
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					JFrameUser frame = new JFrameUser();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public JFrameUser() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 658, 463);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		tableUtilisateur = new JTable();
		tableUtilisateur.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				int ligne=tableUtilisateur.getSelectedRow();
				int id=Integer.parseInt(tableUtilisateur.getValueAt(ligne, 0).toString());
				Utilisateur user= userDao.rechercherById(id);
				textFieldID.setText(String.valueOf(user.getId()));
				textFieldNom.setText(String.valueOf(user.getNom()));
				textFieldPrenom.setText(String.valueOf(user.getPrenom()));
				textFieldAge.setText(String.valueOf(user.getAge()));
			}
		});
		tableUtilisateur.setBounds(20, 11, 409, 171);
		contentPane.add(tableUtilisateur);
		
		JPanel panel = new JPanel();
		panel.setBorder(new TitledBorder(null, "Information", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		panel.setBounds(20, 193, 416, 222);
		contentPane.add(panel);
		panel.setLayout(new FormLayout(new ColumnSpec[] {
				FormSpecs.RELATED_GAP_COLSPEC,
				FormSpecs.DEFAULT_COLSPEC,
				FormSpecs.RELATED_GAP_COLSPEC,
				FormSpecs.DEFAULT_COLSPEC,
				FormSpecs.RELATED_GAP_COLSPEC,
				ColumnSpec.decode("default:grow"),},
			new RowSpec[] {
				FormSpecs.RELATED_GAP_ROWSPEC,
				FormSpecs.DEFAULT_ROWSPEC,
				FormSpecs.RELATED_GAP_ROWSPEC,
				FormSpecs.DEFAULT_ROWSPEC,
				FormSpecs.RELATED_GAP_ROWSPEC,
				FormSpecs.DEFAULT_ROWSPEC,
				FormSpecs.RELATED_GAP_ROWSPEC,
				FormSpecs.DEFAULT_ROWSPEC,
				FormSpecs.RELATED_GAP_ROWSPEC,
				FormSpecs.DEFAULT_ROWSPEC,
				FormSpecs.RELATED_GAP_ROWSPEC,
				FormSpecs.DEFAULT_ROWSPEC,
				FormSpecs.RELATED_GAP_ROWSPEC,
				FormSpecs.DEFAULT_ROWSPEC,}));
		
		JLabel lblID = new JLabel("Id");
		panel.add(lblID, "2, 2, 3, 1");
		
		textFieldID = new JTextField();
		panel.add(textFieldID, "6, 2, fill, default");
		textFieldID.setColumns(10);
		
		JLabel lblNom = new JLabel("Nom");
		panel.add(lblNom, "2, 4");
		
		textFieldNom = new JTextField();
		panel.add(textFieldNom, "6, 4, fill, default");
		textFieldNom.setColumns(10);
		
		JLabel lblPrenom = new JLabel("Prenom");
		panel.add(lblPrenom, "2, 6");
		
		textFieldPrenom = new JTextField();
		panel.add(textFieldPrenom, "6, 6, fill, default");
		textFieldPrenom.setColumns(10);
		
		JLabel lblAge = new JLabel("Age");
		panel.add(lblAge, "2, 8");
		
		textFieldAge = new JTextField();
		panel.add(textFieldAge, "6, 8, fill, default");
		textFieldAge.setColumns(10);
		
		JButton btnAjouter = new JButton("Ajouter");
		btnAjouter.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					Utilisateur user=new Utilisateur();
					user.setNom(textFieldNom.getText());
					user.setPrenom(textFieldPrenom.getText());
					user.setAge(textFieldAge.getText());
					if(userDao.ajouter(user)) {
						JOptionPane.showMessageDialog(null, "Ajout reussi avec success");
						remplir();
						
					}
					else {
						JOptionPane.showMessageDialog(null, "Ajout non reussi ");
						
					}
				}catch(Exception e1) {
					JOptionPane.showMessageDialog(null, e1);
					
				}
			}
			
		});
		panel.add(btnAjouter, "2, 12");
		
		btnSupprimer = new JButton("Supprimer");
		btnSupprimer.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					int ligne=tableUtilisateur.getSelectedRow();
					int id=Integer.parseInt(tableUtilisateur.getValueAt(ligne, 0).toString());
					if (JOptionPane.showConfirmDialog (null,"Voulez-vous supprimer cet utilisateur ?", "Suppression",
							JOptionPane.YES_NO_OPTION ) == JOptionPane.OK_OPTION) {
						if (userDao.supprimerByID(id)) {
						JOptionPane.showMessageDialog(null, "Suppresion reussi avec success");
						remplir();
						}else 
							JOptionPane.showMessageDialog(null, "Suppresion non reussi");
						}
				}catch(Exception e1) {
					JOptionPane.showMessageDialog(null, e1);
					
				}
			
			}
		});
		
		btnModifier = new JButton("Modifier");
		btnModifier.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				int valID=Integer.parseInt(textFieldID.getText());
				String valNom=textFieldNom.getText();
				String valPrenom=textFieldPrenom.getText();
				String valAge=textFieldAge.getText();
				String SQLUpdate="update utilisateur set nom='"+valNom+"',prenom='"+valPrenom+"',age='"+valAge+"' where id='"+valID+"' ";
				if (JOptionPane.showConfirmDialog (null,"Voulez-vous modifier cet utilisateur ?", "Modification",
						JOptionPane.YES_NO_OPTION ) == JOptionPane.OK_OPTION) {
					try(Connection connection=getConnection();
							PreparedStatement ps=connection.prepareStatement(SQLUpdate))
					{
						ps.execute();
						JOptionPane.showMessageDialog(null, "MODIFICATION REUSSI AVEC SUCCESS");
						remplir();
						
					}
					catch(Exception e1) {
						JOptionPane.showMessageDialog(null, e1);
					}
				}
			}
			/*try {
				int ligne=tableUtilisateur.getSelectedRow();
				int id=Integer.parseInt(tableUtilisateur.getValueAt(ligne, 0).toString());
				Utilisateur user=new Utilisateur();
				if (JOptionPane.showConfirmDialog (null,"Voulez-vous modifier cet utilisateur ?", "Modification",
						JOptionPane.YES_NO_OPTION ) == JOptionPane.OK_OPTION) {
					if(userDao.modifierById(user,id)) {
						JOptionPane.showMessageDialog(null, "Modification reussi avec success");
					}else {
						JOptionPane.showMessageDialog(null, "Modification non reussi");
					}
				}
			}catch(Exception e1) {
				JOptionPane.showMessageDialog(null, e1);
				
			}
		
			}*/
		});
		panel.add(btnModifier, "2, 14");
		panel.add(btnSupprimer, "4, 14");
		remplir(); 
	}
		private void remplir() {
			DefaultTableModel dtm=new DefaultTableModel();
			dtm.addColumn("ID");
			dtm.addColumn("Nom");
			dtm.addColumn("Prenom");
			dtm.addColumn("Age");
			for(Utilisateur user : userDao.listerUsers()) {
				dtm.addRow(new Object[] {user.getId(), user.getNom(), user.getPrenom(), user.getAge() });
			}
			this.tableUtilisateur.setModel(dtm);
		}
		}
