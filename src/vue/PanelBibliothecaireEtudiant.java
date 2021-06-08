package vue;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;

import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;

import modele.Connexion;
import modele.Etudiant;
import modele.Livre;
import modele.UpdateListe;
import utils.Bouton;
import utils.LivresEtudiants;

/**
 * 
 * @author Evann
 *
 */
public class PanelBibliothecaireEtudiant extends JPanel implements ActionListener, UpdateListe {    
    /**
     * hashmap de stokage des JTextFied 
     */
    HashMap<String, JTextField> textFields = new HashMap<String, JTextField>();
    
    /**
     * etudiant selectione
     */
    Etudiant etuSelectionne;
    /**
     * liste d'etudiants
     */
    JList<Etudiant> listeEtudiants;
    /**
     * liste des emprunts 
     */
    JList<Livre> listeEmprunts;
    /**
     * liste des reservation
     */
    JList<Livre> listeReservations;
    /**
     * Permet de créer un couple JTextField avec un nom au dessus
     * @param name Le texte a afficher au dessus du JLabel
     * @return Un JPanel avec un JLabel et un JTextField
     */
    public JPanel textFieldWithName(String name) {
        JPanel panel = new JPanel(new BorderLayout());
        panel.add(new JLabel(name), BorderLayout.WEST);
        JTextField tf = new JTextField(10);
        textFields.put(name, tf);
        panel.add(tf, BorderLayout.CENTER);
        return panel;
    }
    
    /**
     * 
     * @return liste d'étudiants
     */
    private Etudiant[] getEtudiants() {
        ArrayList<Etudiant> etus = new ArrayList<Etudiant>();
        ResultSet rset = null;
        try {
            rset = Connexion.executeQuery("SELECT nom,prenom,email FROM etu");
            while (rset.next()) {
                String nom = rset.getString(1);
                String prenom = rset.getString(2);
                String email = rset.getString(3);
                etus.add(new Etudiant(nom, prenom, email));
            }
            rset.close();
        } catch (SQLException e) {
            e.printStackTrace();
            return new Etudiant[0];
        }

        return (Etudiant[]) etus.toArray(new Etudiant[etus.size()]);
    }
    
    /**
     *  
     *  initialisation du panel de la bibliotécaire 
     */
    public PanelBibliothecaireEtudiant() {
        setLayout(new BorderLayout(20, 20));
        JPanel panelListeEtudiants = new JPanel(new BorderLayout());
        JPanel panelInfoEtudiant = new JPanel(new BorderLayout(20, 20));
        
        // Panel WEST (liste des étudiants)
        listeEtudiants = new JList<Etudiant>();
        listeEtudiants.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        
        mettreAJourListeEtudiants();
        JButton supprimerEtudiantBouton = new JButton("Supprimer");
        supprimerEtudiantBouton.setActionCommand("supprimer-etudiant");
        supprimerEtudiantBouton.addActionListener(this);
        panelListeEtudiants.add(supprimerEtudiantBouton, BorderLayout.SOUTH);
        
        
        listeEtudiants.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent evt) {
                JList<Etudiant> list = (JList<Etudiant>)evt.getSource();
                Etudiant selected = (Etudiant) list.getSelectedValue();
                changerEtudiantSelectionne(selected);
            };
        });

        JScrollPane scrollListeEtudiants = new JScrollPane(listeEtudiants);
        panelListeEtudiants.add(Bouton.JLabelWithButton("Liste étudiants", "letu", this), BorderLayout.NORTH);
        panelListeEtudiants.add(scrollListeEtudiants, BorderLayout.CENTER);
        
        // Panel CENTRE (informations de l'étudiants sélectionné)
        JPanel infos = new JPanel();
        infos.add(textFieldWithName("Nom"));
        infos.add(textFieldWithName("Prénom"));
        infos.add(textFieldWithName("Email"));
        infos.add(textFieldWithName("Mdp"));
        JButton enrengistrerButton = new JButton("Enrengister");
        enrengistrerButton.setActionCommand("save");
        enrengistrerButton.addActionListener(this);
        infos.add(enrengistrerButton);
        
        // Panels emprunt et panel réservation
        JPanel panel = new JPanel(new GridLayout(1,2, 20, 20));

        listeEmprunts = new JList<Livre>();
        listeReservations = new JList<Livre>();
        PanelListeEmprunt panelListeEmprunts = new PanelListeEmprunt(listeEmprunts, this);
        PanelListeReservation panelListeReservation = new PanelListeReservation(listeReservations, this);
            
        panel.add(panelListeEmprunts);
        panel.add(panelListeReservation);

        panelInfoEtudiant.add(infos, BorderLayout.NORTH);   
        panelInfoEtudiant.add(panel, BorderLayout.CENTER);
        
        add(panelListeEtudiants, BorderLayout.WEST);
        add(panelInfoEtudiant, BorderLayout.CENTER);
    }

    public void actionPerformed(ActionEvent e) {       
        String action = e.getActionCommand();
        switch (action) {
        case "letu": new PopupNouveauEtudiant(this); break;
        case "lemp": new PopupAjoutEmprunt(this); break;
        case "lres": new PopupAjoutReservation(this); break;
        case "save": changerInformationsEtudiant(); break;
        case "supprimer-emprunt": supprimerEmprunt(); break;
        case "supprimer-reservation": supprimerReservation(); break;
        case "supprimer-etudiant": supprimerEtudiant(); break;
        case "relance": relancerLivre(); break;
        }
    }
    
    /**
     * methode prenant en charge l'ajout d'étudiants dans la base de donné
     * @param etu etudiant a ajouté
     * @return retourne un boolean indiquant la bonne resuite de la commande 
     */
    boolean ajouterEtudiant(Etudiant etu) {
        System.out.println("Ajout de " + etu.toString());
        try {
            Connexion.executeUpdate("INSERT INTO etu(nom,prenom,email,mdp) VALUES (?, ?, ?, ?)",
                    new String[] {
                            etu.getNom(), etu.getPrenom(), etu.getEmail(), etu.getMdp()
                    }
            );
            return true;
        } catch (Exception e) {
            return false;
        }
    }
    
    /**
     * changement d'étudiant sélectioné
     * @param etu nouvel etudiant a afficher
     */
    private void changerEtudiantSelectionne(Etudiant etu) {
        textFields.get("Nom").setText(etu.getNom());
        textFields.get("Prénom").setText(etu.getPrenom());
        textFields.get("Email").setText(etu.getEmail());
        etuSelectionne = etu;
        mettreAJourLivres();
    }
    
    /**
     * chagement des information de l'étudiant prenom, nom, email, mot de passe
     */
    private void changerInformationsEtudiant() {
        if (getEtuSelectionne() == null) {
            return;
        }
        
        try {
            Connexion.executeUpdate("UPDATE etu SET nom = ?, prenom = ?, email = ? WHERE email = ?", new String[] {
                    textFields.get("Nom").getText(),
                    textFields.get("Prénom").getText(),
                    textFields.get("Email").getText(),
                    getEtuSelectionne().getEmail()
            });
            
            if (!textFields.get("Mdp").getText().isEmpty()) {
                Connexion.executeUpdate("UPDATE etu SET mdp = ? WHERE email = ?", new String[] {
                        textFields.get("Mdp").getText(),
                        getEtuSelectionne().getEmail()
                });
            }
            
            mettreAJourListeEtudiants();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    /**
     * mise a jout des livres à afficher
     */
    public void mettreAJourLivres() {
        if (getEtuSelectionne() == null) {
            listeEmprunts.setModel(new DefaultListModel<Livre>());
            listeReservations.setModel(new DefaultListModel<Livre>());
            return;
        }
        
        Livre[] emprunts = LivresEtudiants.getLivresEmpruntEtudiants(getEtuSelectionne());
        Livre[] reservations = LivresEtudiants.getLivresReserveEtudiants(getEtuSelectionne());

        DefaultListModel<Livre> model = new DefaultListModel<Livre>();
        for (Livre emprunt : emprunts) {
            model.addElement(emprunt);
        }
        listeEmprunts.setModel(model);
        
        model = new DefaultListModel<Livre>();
        for (Livre reserv : reservations) {
            model.addElement(reserv);
        }
        listeReservations.setModel(model);
    }
    
    /**
     * mise a jour des livre à ajouter coté étudiant 
     */
    void mettreAJourListeEtudiants() {        
        int index = listeEtudiants.getSelectedIndex();
        DefaultListModel<Etudiant> model = new DefaultListModel<Etudiant>();
        Etudiant[] etus = getEtudiants();
        for (Etudiant etu : etus) {
            model.addElement(etu);
        }
        listeEtudiants.setModel(model);
        listeEtudiants.setSelectedIndex(index);
    }
    
    /**
     * suppression d'un emprunt
     */
    private void supprimerEmprunt() {
        Livre livre = listeEmprunts.getSelectedValue();
        LivresEtudiants.supprimerEmprunt(getEtuSelectionne(), livre.getExemplaire());
        mettreAJourLivres();
    }
    
    /**
     * suppression d'une réservaion 
     */
    private void supprimerReservation() {
        Livre livre = listeReservations.getSelectedValue();
        if (livre == null) return;
        
        LivresEtudiants.supprimerReservation(getEtuSelectionne(), String.valueOf(livre.getId()));
        mettreAJourLivres();
    }
    
    /**
     * suppression d'un étudiant  
     */
    private void supprimerEtudiant() {
        if (getEtuSelectionne() == null) {
            return;
        }
        getEtuSelectionne().supprimer();
        etuSelectionne = null;
        mettreAJourListeEtudiants();
        mettreAJourLivres();
        
        textFields.get("Nom").setText("");
        textFields.get("PrÃ©nom").setText("");
        textFields.get("Email").setText("");
    }
    
    
    /**
     * relance l'étudiant en cas de retard 
     */
    private void relancerLivre() {
        Livre livre = listeEmprunts.getSelectedValue();
        if (livre == null) return;
        
        LivresEtudiants.relancerLivre(etuSelectionne, livre);
        mettreAJourLivres();
    }

    Etudiant getEtuSelectionne() {
        return etuSelectionne;
    }
}
