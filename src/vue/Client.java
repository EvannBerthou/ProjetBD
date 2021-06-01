package vue;

import java.io.IOException;
import java.sql.SQLException;
import javax.swing.JFrame;
import javax.swing.JTabbedPane;

import modele.Connexion;
import modele.Etudiant;

public class Client extends JFrame {
	private static final long serialVersionUID = -978164249698330936L;
	
	public Client() throws ClassNotFoundException, SQLException, IOException {
		super("Titre");
	    //"jdbc:oracle:thin:@madere:1521:info"
		new Connexion("jdbc:sqlite:sample.db", "eberthou", "azerty");

        PanelBibliothecaireEtudiant panelBibliothecaire = new PanelBibliothecaireEtudiant();
		PanelEtudiant panelEtudiant = new PanelEtudiant(new Etudiant("Dupont","Tom","mail1@mail.com"));
		PanelLivres panelLivres = new PanelLivres();

        JTabbedPane tabbedPane = new JTabbedPane();
        tabbedPane.addTab("Etudiant", null, panelEtudiant, "Gestion des emprunts et réservations");
        tabbedPane.addTab("Bibliothécaire", null, panelBibliothecaire, "Gestion des étudiants");
        tabbedPane.addTab("Livres", null, panelLivres, "Gestion des livres");
        
		setContentPane(tabbedPane);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setVisible(true);
        setSize(1000, 600);
	}
	
	public static void main (String args []) throws SQLException, ClassNotFoundException, IOException {
		new Client();
	}
}