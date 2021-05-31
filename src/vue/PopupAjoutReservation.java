package vue;

import java.awt.event.ActionEvent;
import javax.swing.JOptionPane;

import modele.Etudiant;
import utils.LivresEtudiants;

public class PopupAjoutReservation extends PopupLivreEtudiant {

    PanelBibliothecaireEtudiant pbe;
    
    public PopupAjoutReservation(PanelBibliothecaireEtudiant _pbe) {
        super(_pbe);
        this.pbe = _pbe;
        Etudiant etu = pbe.getEtuSelectionne();
        if (etu != null) {
            setTitle("Ajout reservation pour " + etu.toString());
        }
    }
    
    public void actionPerformed(ActionEvent e) {
        int row = tableLivres.getSelectedRow();
        String titre = (String) tableLivres.getValueAt(row, 0);
        String auteur = (String) tableLivres.getValueAt(row, 1);

        Etudiant etu = pbe.getEtuSelectionne();
            if (LivresEtudiants.ReserverLivre(etu, titre, auteur) == false) {
                JOptionPane.showMessageDialog(null, etu.toString()
                + " a déjà réservé 5 livres.", "Erreur", JOptionPane.INFORMATION_MESSAGE);
        }
        pbe.mettreAJoutEmpruntsReservations();
        dispose();
    }
}
