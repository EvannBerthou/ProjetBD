package vue;

import java.awt.event.ActionEvent;
import javax.swing.JOptionPane;

import modele.Etudiant;
import modele.UpdateListe;
import utils.LivresEtudiants;

public class PopupAjoutReservation extends PopupLivreEtudiant {

	PanelBibliothecaireEtudiant pbe = null;
	Etudiant etu;
	UpdateListe parent;
	
	public PopupAjoutReservation(PanelBibliothecaireEtudiant _pbe) {
		super(_pbe.getEtuSelectionne());
		this.pbe = _pbe;
		this.parent = _pbe;
		etu = pbe.getEtuSelectionne();
		if (etu != null) {
			setTitle("Ajout reservation pour " + etu.toString());
		}
	}
	
	public PopupAjoutReservation(Etudiant _etu, UpdateListe _panel) {
        super(_etu);
        this.etu = _etu;
        this.parent = _panel;
        setTitle("Ajout reservation pour " + etu.toString());
    }

	public void actionPerformed(ActionEvent e) {
		int row = tableLivres.getSelectedRow();
		String titre = tableLivres.getValueAt(row, 0).toString();
		String auteur = tableLivres.getValueAt(row, 1).toString();

		if (LivresEtudiants.ReserverLivre(etu, titre, auteur) == false) {
			JOptionPane.showMessageDialog(null, "Erreur dans la réservation du livre", "Erreur", JOptionPane.INFORMATION_MESSAGE);
		}
		
		parent.mettreAJourLivres();
		dispose();
	}
}
