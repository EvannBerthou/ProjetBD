package vue;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.HashMap;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;

public class PanelBibliothecaireEtudiant extends JPanel implements ActionListener {

    
    HashMap<String, JTextField> textFields = new HashMap<String, JTextField>();
    
    /**
     * Permet de créer un couple JTextField avec un nom au dessus
     * @param name Le texte a afficher au dessus du JLabel
     * @return Un JPanel avec un JLabel et un JTextField
     */
    public JPanel textFieldWithName(String name) {
        JPanel panel = new JPanel(new BorderLayout());
        panel.add(new JLabel(name), BorderLayout.NORTH);
        JTextField tf = new JTextField(10);
        textFields.put(name, tf);
        panel.add(tf, BorderLayout.SOUTH);
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
    
    public PanelBibliothecaireEtudiant() {
        setLayout(new BorderLayout(20, 20));
        JPanel listeEtudiants = new JPanel(new BorderLayout());
        JPanel panelInfoEtudiant = new JPanel(new BorderLayout(20, 20));
        
        // Panel WEST (liste des étudiants)
        JList<String> listeEtudiats = new JList<String>(new String[] {"test1", "test2"});
        JScrollPane scroll = new JScrollPane(listeEtudiats);

        listeEtudiants.add(JLabelWithButton("Liste étudiants", "letu"), BorderLayout.NORTH);
        listeEtudiants.add(scroll, BorderLayout.CENTER);
        
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
        
        add(listeEtudiants, BorderLayout.WEST);
        add(panelInfoEtudiant, BorderLayout.CENTER);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        String action = e.getActionCommand();
        switch (action) {
        case "letu": System.out.println("letu"); break;
        case "lemp": System.out.println("lemp"); break;
        case "lres": System.out.println("lres"); break;
        case "save": System.out.println("save"); break;
        case "suppr": System.out.println("suppr"); break;
        }
        
    }
    
}
