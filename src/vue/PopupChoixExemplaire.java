package vue;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.ScrollPaneConstants;

import modele.Etudiant;
import modele.ModeleTableExemplaires;
import utils.LivresEtudiants;

/**
 * 
 * @author Evann
 *
 */
public class PopupChoixExemplaire extends JDialog implements ActionListener {
    
    /**
     * JTable de livres
     */
    JTable tableLivres;
    /**
     * panel de la bibliothécaire 
     */
    PanelBibliothecaireEtudiant pbe;
    /**
     * étudiant 
     */
    Etudiant etu;

    /**
     * initialisation du panel choix exemplaire 
     * @param _pbe panel de la bibliothécaire
     * @param _etu etudiant
     * @param id_liv identifiant du livre 
     */
    public PopupChoixExemplaire(PanelBibliothecaireEtudiant _pbe, Etudiant _etu, String id_liv) {
        this.pbe = _pbe;
        this.etu = _etu;
        
        setLayout(new BorderLayout());
        tableLivres = new JTable(new ModeleTableExemplaires(id_liv));
        tableLivres.setRowHeight(25);
        tableLivres.setAutoCreateRowSorter(true);
        
        JScrollPane scrollPane = new JScrollPane(tableLivres,
                ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED,
                ScrollPaneConstants.HORIZONTAL_SCROLLBAR_AS_NEEDED);

        add(scrollPane, BorderLayout.CENTER);
        
        JButton selectionnerBouton = new JButton("Selectionner");
        selectionnerBouton.addActionListener(this);
        
        add(selectionnerBouton, BorderLayout.SOUTH);
        
        setVisible(true);
        setSize(500, 400);
    }

    /**
     * gestion des evenement
     * @param e evenement 
     */
    @Override
    public void actionPerformed(ActionEvent e) {
        String selectedEx = (String) tableLivres.getValueAt(tableLivres.getSelectedRow(), 0);

        if (selectedEx != null) {
            if (LivresEtudiants.EmprunterLivre(etu, selectedEx) == false) {
                JOptionPane.showMessageDialog(null, etu.toString() 
                        + " a déjà réservé 5 livres.", "Erreur", JOptionPane.INFORMATION_MESSAGE);
            }
        }
        pbe.mettreAJourLivres();
        dispose();
    }
    
}
