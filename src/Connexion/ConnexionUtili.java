package Connexion;
import TestMysql.*;


import java.sql.*; 
import java.util.List;

import javax.swing.JOptionPane;

import java.util.ArrayList;
public class ConnexionUtili {
	private String url ="jdbc:mysql://localhost/java_tuto ?useUnicode=true &useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false& serverTimezone=UTC"  ;
	private String utilisateur = "Fatou";
    private String motDePasse = "FatouDiallo";
    static final String InsertionSQL="INSERT INTO Utilisateur "+ " (nom, prenom, age) VALUES "+ " (?, ?, ?);";
	static final String DeleteSQL= "DELETE FROM Utilisateur where id= ?"; 
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
	public List<Utilisateur> listerUsers(){
		List<Utilisateur> listUsers=new ArrayList<Utilisateur>();
		try(Connection connection=getConnection(); 
			PreparedStatement ps=connection.prepareStatement("select * from Utilisateur")){
			ResultSet rep=ps.executeQuery();
			while(rep.next()) {
				Utilisateur user=new Utilisateur();
				user.setId(rep.getInt("id"));
				user.setNom(rep.getString("nom"));
				user.setPrenom(rep.getString("prenom"));
				user.setAge(rep.getString("age"));
				listUsers.add(user);
			}
			
			
			
		}
		catch(Exception e ) {
			e.printStackTrace();
		}
		return listUsers;
		
		
	}
	public Utilisateur rechercherById(int id){
		Utilisateur user=new Utilisateur();
		try(Connection connection=getConnection();
			PreparedStatement ps=connection.prepareStatement("select * from Utilisateur where id=?")){
			ps.setInt(1, id);
			ResultSet rep=ps.executeQuery();
			while(rep.next()) {
				
				user.setId(rep.getInt("id"));
				user.setNom(rep.getString("nom"));
				user.setPrenom(rep.getString("prenom"));
				user.setAge(rep.getString("age"));
				
			}
			
			
			
		}
		catch(Exception e ) {
			e.printStackTrace();
		}
		return user;
		
		
	}
	public boolean supprimerByID(int id) {
		    try(Connection connection=getConnection();
			PreparedStatement ps = connection.prepareStatement(DeleteSQL)){
		    ps.setInt(1, id);
		    return ps.executeUpdate() >0;

		    } catch (Exception e) {
		    	e.printStackTrace();
		    	return false;
		    }
		
	}
	
	/*public boolean modifierById (Utilisateur user, int id) {
		int valID=user.getId();
		String valNom= user.getNom();
		String valPrenom= user.getPrenom();
		String valAge= user.getAge();
		String UpdateSQL= "Update Utilisateur set Nom='"+valNom+"',prenom='"+valPrenom+"',age='"+valAge+"' where id='"+valID+"' ";
		try(Connection connection=getConnection();
				PreparedStatement ps =connection.prepareStatement(UpdateSQL)){
					ps.setInt(1, id); 
					return ps.executeUpdate()>0;
		} catch (Exception e) {
	    	e.printStackTrace();
	    	return false;	
		}
	}*/
	
	public boolean ajouter(Utilisateur user) {
		try(Connection connection =getConnection();
			PreparedStatement ps=connection.prepareStatement(InsertionSQL)){
			ps.setString(1, user.getNom());
			ps.setString(2, user.getPrenom());
			ps.setString(3, user.getAge());
			return ps.executeUpdate()>0;
			
			
		}
		catch(Exception e) {
			e.printStackTrace();
			return false;
		}
	} 
	
	

}
