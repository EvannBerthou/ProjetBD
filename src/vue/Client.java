package vue;

import java.io.IOException;
import java.sql.SQLException;
import javax.swing.JFrame;
import javax.swing.JTabbedPane;

import modele.Connexion;

public class Client extends JFrame {
	private static final long serialVersionUID = -978164249698330936L;
	
	public Client() throws ClassNotFoundException, SQLException, IOException {
		super("Titre");
	    //"jdbc:oracle:thin:@madere:1521:info"
		new Connexion("jdbc:sqlite:sample.db", "eberthou", "azerty");

		PanelBibliothecaireEtudiant panelEtudiant = new PanelBibliothecaireEtudiant();
		PanelLivres panelLivres = new PanelLivres();

        JTabbedPane tabbedPane = new JTabbedPane();
        tabbedPane.addTab("Etudiant", null, panelEtudiant, "Does nothing");
        tabbedPane.addTab("Livres", null, panelLivres, "Does nothing");
        
		setContentPane(tabbedPane);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setVisible(true);
        setSize(1000, 600);
	}
	
	public static void main (String args []) throws SQLException, ClassNotFoundException, IOException {
		new Client();
	}
}