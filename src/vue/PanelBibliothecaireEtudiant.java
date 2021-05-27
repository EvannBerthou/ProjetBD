package vue;

import java.awt.BorderLayout;
import java.awt.GridLayout;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;

public class PanelBibliothecaireEtudiant extends JPanel {

    /**
     * Permet de créer un couple JTextField avec un nom au dessus
     * @param name Le texte a afficher au dessus du JLabel
     * @return Un JPanel avec un JLabel et un JTextField
     */
    public JPanel textFieldWithName(String name) {
        JPanel panel = new JPanel(new BorderLayout());
        panel.add(new JLabel(name), BorderLayout.NORTH);
        panel.add(new JTextField(10), BorderLayout.SOUTH);
        return panel;
    }
    
    public PanelBibliothecaireEtudiant() {
        setLayout(new BorderLayout(20, 20));
        JPanel listeEtudiants = new JPanel(new BorderLayout());
        JPanel panelInfoEtudiant = new JPanel(new BorderLayout(20, 20));
        
        // Panel WEST (liste des étudiants)
        JList<String> listeEtudiats = new JList<String>(new String[] {"test1", "test2"});
        JScrollPane scroll = new JScrollPane(listeEtudiats);
        listeEtudiants.add(new JLabel("Liste Etudiants"), BorderLayout.NORTH);
        listeEtudiants.add(scroll, BorderLayout.CENTER);
        
        // Panel CENTRE (informations de l'étudiants sélectionné)
        JPanel infos = new JPanel();
        infos.add(textFieldWithName("Nom"));
        infos.add(textFieldWithName("Prénom"));
        infos.add(textFieldWithName("Email"));
        infos.add(textFieldWithName("Mdp"));
        infos.add(new JButton("Enrengister"));
        
        JPanel panel = new JPanel(new GridLayout(1,2));

        //TODO: Charger les livres empruntés depuis la BD
        JPanel empruntsPanel = new JPanel(new BorderLayout());
        JList<String> emprunts = new JList<String>(new String[] {"test1", "test2"});
        JScrollPane scrollEmprunts = new JScrollPane(emprunts);
        empruntsPanel.add(new JLabel("Livres empruntés"), BorderLayout.NORTH);
        empruntsPanel.add(scrollEmprunts, BorderLayout.CENTER);
        
        //TODO: Charger les livres réservés depuis la BD
        JPanel reservationPanel = new JPanel(new BorderLayout());
        JList<String> reservations = new JList<String>(new String[] {"test1", "test2"});
        JScrollPane scrollReservations = new JScrollPane(reservations);
        reservationPanel.add(new JLabel("Livres empruntés"), BorderLayout.NORTH);
        reservationPanel.add(scrollReservations, BorderLayout.CENTER);

        panel.add(empruntsPanel);
        panel.add(reservationPanel);
        
        // Bouton au sud
        JButton bouton = new JButton("Supprimer l'étudiant");

        panelInfoEtudiant.add(infos, BorderLayout.NORTH);   
        panelInfoEtudiant.add(panel, BorderLayout.CENTER);
        panelInfoEtudiant.add(bouton, BorderLayout.SOUTH);
        
        add(listeEtudiants, BorderLayout.WEST);
        add(panelInfoEtudiant, BorderLayout.CENTER);
    }
    
}
