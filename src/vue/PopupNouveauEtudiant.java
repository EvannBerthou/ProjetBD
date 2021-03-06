package vue;

import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;

import modele.Etudiant;
/**
 * 
 * @author Evann
 *
 */
public class PopupNouveauEtudiant extends JDialog implements ActionListener {

    /**
     * textfield nom du nouvel ?tudiant 
     */
    JTextField tfNom = new JTextField(10);
    /**
     * textfield prenom du nouvel ?tudiant
     */
    JTextField tfPrenom = new JTextField(10);
    /**
     * textfield mail du nouvel ?tudiant 
     */
    JTextField tfMail = new JTextField(10);
    /**
     * textfied mot de passe du nouvel ?tudiant 
     */
    JTextField tfMdp = new JTextField(10);
    /**
     * bouton d'ajout du nouvel ?tudiant 
     */
    JButton ajouterBouton = new JButton("Ajouter");
    /**
     * bouton d'annulation 
     */
    JButton annulerBouton = new JButton("Annuler");

    /**
     * panel de la biblioth?caire 
     */
    PanelBibliothecaireEtudiant pbe;
    
    /**
     * initialisation du panel pour un nouvel ?tudiant 
     * @param _pbe Le panel de la biblioth?caire
     */
    public PopupNouveauEtudiant(PanelBibliothecaireEtudiant _pbe) {
        this.pbe = _pbe;
        setTitle("Ajout d'un ?tudiant");

        setLayout(new GridLayout(5, 2));
        add(new JLabel("Nom"));
        add(tfNom);

        add(new JLabel("Pr?nom"));
        new JTextField(10);
        add(tfPrenom);

        add(new JLabel("Email"));
        add(tfMail);

        add(new JLabel("Mot de passe"));
        add(tfMdp);

        annulerBouton.addActionListener(this);
        ajouterBouton.addActionListener(this);

        add(annulerBouton);
        add(ajouterBouton);

        setVisible(true);
        setBackground(java.awt.Color.red);
        setSize(300, 150);
    }
    /**
     * retourne un nouvel ?tudiant 
     * @return etudiant 
     */
    public Etudiant getEtudiant() {
        String nom = tfNom.getText();
        String prenom = tfPrenom.getText();
        String mail = tfMail.getText();
        String mdp = tfMdp.getText();

        if (nom.isEmpty() || prenom.isEmpty()|| mail.isEmpty() || mdp.isEmpty())
            return null;

        Etudiant etu = new Etudiant(nom, prenom, mail, mdp);
        return etu;
    }

    /**
     * gestion des evenement 
     * @param e evenement 
     */
    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == ajouterBouton) {
            Etudiant etu = getEtudiant();
            
            String message = "Etudiant " + etu.getNom() + " " + etu.getPrenom() + " ajout?.";
            String titre = "Succ?s";
            
            if (etu != null && pbe.ajouterEtudiant(etu)) {
                dispose();
            } else {
                message = "Erreur dans l'ajout de l'?tudiant.";
                titre = "Erreur";
            }
            
            JOptionPane.showMessageDialog(null, message, titre, JOptionPane.INFORMATION_MESSAGE);
            pbe.mettreAJourListeEtudiants();
        } else if (e.getSource() == annulerBouton){
            dispose();
        }
    }
}
