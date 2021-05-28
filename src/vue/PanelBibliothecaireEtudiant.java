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
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;

import modele.Connexion;
import modele.Etudiant;

public class PanelBibliothecaireEtudiant extends JPanel implements ActionListener {    
    HashMap<String, JTextField> textFields = new HashMap<String, JTextField>();
    
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
    
    public JPanel JLabelWithButton(String label, String btnAction) {
        JPanel panel = new JPanel(new BorderLayout(30,30));
        panel.add(new JLabel(label), BorderLayout.WEST);
        JButton button = new JButton("+");
        button.setActionCommand(btnAction);
        button.addActionListener(this);
        panel.add(button, BorderLayout.EAST);
        return panel;
    }
    
    private Etudiant[] getEtudiants() {
        ArrayList<Etudiant> etus = new ArrayList<Etudiant>();
        ResultSet result = null;
        try {
            result = Connexion.executeQuery("SELECT nom,prenom,email FROM etu");
            while (result.next()) {
                String nom = result.getString(1);
                String prenom = result.getString(2);
                String email = result.getString(3);
                etus.add(new Etudiant(nom, prenom, email));
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }

        return (Etudiant[]) etus.toArray(new Etudiant[etus.size()]);
    }
    
    public PanelBibliothecaireEtudiant() {
        setLayout(new BorderLayout(20, 20));
        JPanel panelListeEtudiants = new JPanel(new BorderLayout());
        JPanel panelInfoEtudiant = new JPanel(new BorderLayout(20, 20));
        
        // Panel WEST (liste des étudiants)
        DefaultListModel<String> model = new DefaultListModel<String>();
        Etudiant[] etus = getEtudiants();
        for (Etudiant etu : etus) {
            model.addElement(etu.toString());
        }
        JList<String> listeEtudiants = new JList<String>(model);
        listeEtudiants.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        
        listeEtudiants.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent evt) {
                JList list = (JList)evt.getSource();
                String selected = (String) list.getSelectedValue();
                changerEtudiantSelectionne(Etudiant.getEtudiantByName(selected));
            };
        });

        JScrollPane scroll = new JScrollPane(listeEtudiants);
        panelListeEtudiants.add(JLabelWithButton("Liste étudiants", "letu"), BorderLayout.NORTH);
        panelListeEtudiants.add(scroll, BorderLayout.CENTER);
        
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
        
        JPanel panel = new JPanel(new GridLayout(1,2, 20, 20));

        //TODO: Charger les livres empruntés depuis la BD
        JPanel empruntsPanel = new JPanel(new BorderLayout());
        JList<String> emprunts = new JList<String>(new String[] {"test1", "test2"});
        JScrollPane scrollEmprunts = new JScrollPane(emprunts);
        empruntsPanel.add(JLabelWithButton("Livres empruntés", "lemp"), BorderLayout.NORTH);
        empruntsPanel.add(scrollEmprunts, BorderLayout.CENTER);
        
        //TODO: Charger les livres réservés depuis la BD
        JPanel reservationPanel = new JPanel(new BorderLayout());
        JList<String> reservations = new JList<String>(new String[] {"test1", "test2"});
        JScrollPane scrollReservations = new JScrollPane(reservations);
        reservationPanel.add(JLabelWithButton("Livres réservés", "lres"), BorderLayout.NORTH);
        reservationPanel.add(scrollReservations, BorderLayout.CENTER);

        panel.add(empruntsPanel);
        panel.add(reservationPanel);
        
        // Bouton au sud
        JButton bouton = new JButton("Supprimer l'étudiant");
        bouton.addActionListener(this);
        bouton.setActionCommand("suppr");

        panelInfoEtudiant.add(infos, BorderLayout.NORTH);   
        panelInfoEtudiant.add(panel, BorderLayout.CENTER);
        panelInfoEtudiant.add(bouton, BorderLayout.SOUTH);
        
        add(panelListeEtudiants, BorderLayout.WEST);
        add(panelInfoEtudiant, BorderLayout.CENTER);
    }

    @Override
    public void actionPerformed(ActionEvent e) {       
        String action = e.getActionCommand();
        switch (action) {
        case "letu": afficherPanelAjoutEtudiant(); break;
        case "lemp": afficherPanelAjoutLivre("emprunt"); break;
        case "lres": afficherPanelAjoutLivre("reservation"); break;
        case "save": System.out.println("save"); break;
        case "suppr": System.out.println("suppr"); break;
        }
    }
    
    public void afficherPanelAjoutLivre(String type) {
        JDialog popup = new JDialog();
        
        popup.setTitle("Ajout " + type + " pour " + "Nom " + "Prénom");

        popup.setLayout(new BorderLayout());
        
        JPanel panelNord = new JPanel();
        panelNord.add(new JLabel("Titre"));
        panelNord.add(new JTextField(20));
        panelNord.add(new JButton("Rechercher"));
        

        JList<String> livres = new JList<String>(new String[] {"test1 - auteur 1", "test2 - auteur 2"});
        JScrollPane scrollLivres = new JScrollPane(livres);
        
        popup.add(panelNord, BorderLayout.NORTH);
        popup.add(scrollLivres, BorderLayout.CENTER);
        JButton ajoutBouton = new JButton("Ajouter");
        popup.add(ajoutBouton, BorderLayout.SOUTH);
        
        popup.setVisible(true);
        popup.setBackground(java.awt.Color.red);
        popup.setSize(500, 400);
    }
    
    private void ajouterEtudiant(Etudiant etu) {
        System.out.println("Ajout de " + etu.toString());
        try {
            Connexion.executeUpdate("INSERT INTO etu(nom,prenom,email,mdp) VALUES (?, ?, ?, ?)",
                    new String[] {
                            etu.getNom(), etu.getPrenom(), etu.getEmail(), etu.getMdp()
                    }
            );
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    
    public void afficherPanelAjoutEtudiant() {
        JDialog popup = new JDialog();
        
        popup.setTitle("Ajout d'un étudiant");

        popup.setLayout(new GridLayout(5,2));
        popup.add(new JLabel("Nom"));
        JTextField tfNom = new JTextField(10);
        popup.add(tfNom);
        
        popup.add(new JLabel("Prénom"));
        JTextField tfPrenom = new JTextField(10);
        popup.add(tfPrenom);
        
        popup.add(new JLabel("Email"));
        JTextField tfMail = new JTextField(10);
        popup.add(tfMail);
        
        popup.add(new JLabel("Mot de passe"));
        JTextField tfMdp = new JTextField(10);
        popup.add(tfMdp);

        
        JButton annulerBouton = new JButton("Annuler");
        annulerBouton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                popup.dispose();
            }
        });
        
        JButton ajouterBouton = new JButton("Ajouter");
        ajouterBouton.addActionListener(new ActionListener() {
            @Override
            //TODO: Vérifier que les textfields ne sont pas vide
            public void actionPerformed(ActionEvent e) {
                String nom = tfNom.getText();
                String prenom = tfPrenom.getText();
                String mail = tfMail.getText();
                String mdp = tfMdp.getText();
                Etudiant etu = new Etudiant(nom, prenom, mail, mdp);
                //TODO: Afficher un message de succès ou d'erreur
                ajouterEtudiant(etu);
            }
        });
        
       
        popup.add(annulerBouton);
        popup.add(ajouterBouton); 
        
        popup.setVisible(true);
        popup.setBackground(java.awt.Color.red);
        popup.setSize(300, 150);
    } 
    
    private void changerEtudiantSelectionne(Etudiant etu) {
        textFields.get("Nom").setText(etu.getNom());
        textFields.get("Prénom").setText(etu.getPrenom());
        textFields.get("Email").setText(etu.getEmail());
    }
}
