package vue;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;
import javax.swing.JTextField;

import modele.Connexion;
import modele.Etudiant;

public class ConnexionEtudiant extends JPanel implements ActionListener {

    Client client;
    
    /**
     * panel positionner au nord de la JFrame
     */
    private JPanel panelNorth= new JPanel();
    /**
     * panel positionner au centre de la JFrame
     */
    private JPanel panelCenter=new JPanel(new GridLayout(3,2,5,5));
    /**
     * Panel positionner au sud de la JFrame
     */
    private JPanel panelSud = new JPanel();


    private JLabel labelid= new JLabel("Identifiant (nom.prenom)");
    private JLabel labelPwd= new JLabel("Mot de passe");

    private JTextField textFieldId = new JTextField(10);
    private JTextField textFieldPwd = new JTextField(10);

    private JButton buttonConnexion = new JButton("Connexion");


    /**
     * methode d'initialisation du panel de connexion
     */
    public ConnexionEtudiant (Client _client){
        this.client = _client;
        this.setLayout(new BorderLayout());

        /**
         * ajout des elements du panel nord
         */
        this.panelNorth.add(new JLabel("Connexion"));

        /**
         * ajout des elements du panel centre
         */
        this.panelCenter.add(this.labelid);
        this.panelCenter.add(this.textFieldId);
        this.panelCenter.add(this.labelPwd);
        this.panelCenter.add(this.textFieldPwd);

        /**
         * ajout des elements du panel sud
         */
        this.buttonConnexion.addActionListener(this);
        this.panelSud.add(this.buttonConnexion);


        /**
         * ajout des JPanel sur le JPanel maitre
         */
        this.add(this.panelNorth,BorderLayout.NORTH);
        this.add(this.panelCenter,BorderLayout.CENTER);
        this.add(this.panelSud,BorderLayout.SOUTH);
    }
        

    @Override
    public void actionPerformed(ActionEvent e) {
        if (textFieldId.getText().equals("BIBLIO") && textFieldPwd.getText().equals("MDP")) {
            PanelBibliothecaireEtudiant panelBibliothecaire = new PanelBibliothecaireEtudiant();
            PanelLivres panelLivres = new PanelLivres();

            JTabbedPane tabbedPane = new JTabbedPane();
            tabbedPane.addTab("Bibliothécaire", null, panelBibliothecaire, "Gestion des étudiants");
            tabbedPane.addTab("Livres", null, panelLivres, "Gestion des livres");
            client.setContentPane(tabbedPane);
        } else {
            String[] parts = textFieldId.getText().split("\\.");
            if (parts.length != 2) return;
            String nom = parts[0];
            String prenom = parts[1];
            try {
                ResultSet rset = Connexion.executeQuery("SELECT COUNT(*), prenom, nom, email FROM etu "
                        + "WHERE LOWER(nom) = ? AND LOWER(prenom) = ? AND LOWER(mdp) = ?",
                        new String[] { nom, prenom, textFieldPwd.getText() });
                if (rset.getInt(1) == 1) {
                    Etudiant etu = new Etudiant(rset.getString(2), rset.getString(3), rset.getString(4));
                    PanelEtudiant panelEtudiant = new PanelEtudiant(etu);
                    client.setContentPane(panelEtudiant);
                } else {
                    System.out.println("Compte inexistant");
                }
            } catch (SQLException e1) {
            }
        }
        client.setSize(1000, 500);
        client.setVisible(true);
    }    
}
