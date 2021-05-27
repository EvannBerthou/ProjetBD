package vue;
import java.awt.Color;
import java.io.IOException;
import java.sql.SQLException;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

import modele.Connexion;

public class Client extends JFrame {
	private static final long serialVersionUID = -978164249698330936L;
	Connexion conn;
	
	public Client() throws ClassNotFoundException, SQLException, IOException {
		super("Titre");
	    //"jdbc:oracle:thin:@madere:1521:info"
		conn = new Connexion("jdbc:sqlite:sample.db", "eberthou", "azerty");
		PanelBibliothecaireEtudiant panel = new PanelBibliothecaireEtudiant();
		setContentPane(panel);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setVisible(true);
		setBackground(Color.red);
        setSize(1000, 600);
	}
	
	public static void main (String args []) throws SQLException, ClassNotFoundException, IOException {
		new Client();
	}
}