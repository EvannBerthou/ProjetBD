package vue;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
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
import modele.Livre;
import modele.ModeleTableLivres;
import utils.LivresEtudiants;

public class PopupAjoutEmprunt extends PopupLivreEtudiant {

    PanelBibliothecaireEtudiant pbe;
        
    public PopupAjoutEmprunt(PanelBibliothecaireEtudiant _pbe) {
        super(_pbe);
        this.pbe = _pbe;
        Etudiant etu = pbe.getEtuSelectionne();
        if (etu != null) {
            setTitle("Ajout emprunt pour " + etu.toString());
        }
    }
    
    public void actionPerformed(ActionEvent e) {
        int row = tableLivres.getSelectedRow();
        String titre = (String) tableLivres.getValueAt(row, 0);
        String auteur = (String) tableLivres.getValueAt(row, 1);

        String id = Livre.getIdByTitre(titre);
        if (Livre.nbExemplaire(id, true) == 0) {
            JOptionPane.showMessageDialog(null, titre + " ne possède plus d'exemplaire disponible", 
                    "Erreur", JOptionPane.INFORMATION_MESSAGE);
            return;
        }
        
        Etudiant etu = pbe.getEtuSelectionne();
            if (LivresEtudiants.EmprunterLivre(etu, titre, auteur) == false) {
                JOptionPane.showMessageDialog(null, etu.toString() 
                        + " a déjà réservé 5 livres.", "Erreur", JOptionPane.INFORMATION_MESSAGE);
        }
        pbe.mettreAJoutEmpruntsReservations();
        dispose();
    }
}
