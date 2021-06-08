package vue;

import java.awt.BorderLayout;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.ScrollPaneConstants;

import modele.Etudiant;
import modele.ModeleTableLivres;
/**
 * 
 * @author Benjamin
 *
 */
public abstract class PopupLivreEtudiant extends JDialog implements ActionListener {
    
    /**
     * Jtable de livres
     */
    JTable tableLivres;
    /**
     * bouton de recherche 
     */
    JButton rechercheBouton = new JButton("Rechercher");
    /**
     * Jtextfield du titre du livre 
     */
    JTextField tfTitre = new JTextField(10);
    /**
     * Jtestfield de l'autreur du livre 
     */
    JTextField tfAuteur = new JTextField(10);
    
    /**
     * initialisation du panel étudiant 
     * @param etu etudiant connecté
     */
    public PopupLivreEtudiant(Etudiant etu) {
        if (etu == null) {
            JOptionPane.showMessageDialog(null, "Aucun étudiant sélectionné.", "Erreur", JOptionPane.INFORMATION_MESSAGE);
            return;
        }

        setLayout(new BorderLayout());

        JPanel panelNord = new JPanel();
        JPanel titre = new JPanel();
        titre.add(new JLabel("Titre"));
        titre.add(tfTitre);

        JPanel auteur = new JPanel();
        auteur.add(new JLabel("Auteur"));
        auteur.add(tfAuteur);
        
        panelNord.add(titre);
        panelNord.add(auteur);
        panelNord.add(rechercheBouton);
        rechercheBouton.addActionListener(this);


        tableLivres = new JTable(new ModeleTableLivres(true));
        tableLivres.setRowHeight(25);
        tableLivres.setAutoCreateRowSorter(true);

        JScrollPane scrollPane = new JScrollPane(tableLivres,
                ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED,
                ScrollPaneConstants.HORIZONTAL_SCROLLBAR_AS_NEEDED);


        add(panelNord, BorderLayout.NORTH);
        add(scrollPane, BorderLayout.CENTER);
        JButton ajoutBouton = new JButton("Ajouter");
        ajoutBouton.addActionListener(this);
        add(ajoutBouton, BorderLayout.SOUTH);

        setVisible(true);
        setBackground(java.awt.Color.red);
        setSize(600, 600);
    }
}
