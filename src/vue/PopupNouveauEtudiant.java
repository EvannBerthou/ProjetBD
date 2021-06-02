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

public class PopupNouveauEtudiant extends JDialog implements ActionListener {

    JTextField tfNom = new JTextField(10);
    JTextField tfPrenom = new JTextField(10);
    JTextField tfMail = new JTextField(10);
    JTextField tfMdp = new JTextField(10);
    JButton ajouterBouton = new JButton("Ajouter");
    JButton annulerBouton = new JButton("Annuler");

    PanelBibliothecaireEtudiant pbe;
    
    public PopupNouveauEtudiant(PanelBibliothecaireEtudiant _pbe) {
        this.pbe = _pbe;
        setTitle("Ajout d'un Ã©tudiant");

        setLayout(new GridLayout(5, 2));
        add(new JLabel("Nom"));
        add(tfNom);

        add(new JLabel("PrÃ©nom"));
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

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == ajouterBouton) {
            Etudiant etu = getEtudiant();
            
            String message = "Etudiant " + etu.getNom() + " " + etu.getPrenom() + " ajouté.";
            String titre = "Succès";
            
            if (etu != null && pbe.ajouterEtudiant(etu)) {
                dispose();
            } else {
                message = "Erreur dans l'ajout de l'étudiant.";
                titre = "Erreur";
            }
            
            JOptionPane.showMessageDialog(null, message, titre, JOptionPane.INFORMATION_MESSAGE);
            pbe.mettreAJourListeEtudiants();
        } else if (e.getSource() == annulerBouton){
            dispose();
        }
    }
}
