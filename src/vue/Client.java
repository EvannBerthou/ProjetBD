package vue;

import java.io.IOException;
import java.sql.DriverManager;
import java.sql.SQLException;

import javax.swing.JFrame;

import modele.Connexion;

public class Client extends JFrame {
	private static final long serialVersionUID = -978164249698330936L;
	
	public Client() throws ClassNotFoundException, SQLException, IOException {
		super("Titre");
		DriverManager.registerDriver(new oracle.jdbc.driver.OracleDriver());
		new Connexion("jdbc:oracle:thin:@madere:1521:info", "eberthou", "azerty");
		//new Connexion("jdbc:sqlite:sample.db", "eberthou", "azerty");

		ConnexionEtudiant connexion = new ConnexionEtudiant(this);
        
		setContentPane(connexion);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setVisible(true);
        setSize(400, 200);
	}
	
	public static void main (String args []) throws SQLException, ClassNotFoundException, IOException {
		new Client();
	}
}