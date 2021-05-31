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
		String titre = tableLivres.getValueAt(row, 0).toString();
		String auteur = tableLivres.getValueAt(row, 1).toString();

		Etudiant etu = pbe.getEtuSelectionne();
		if (LivresEtudiants.ReserverLivre(etu, titre, auteur) == false) {
			JOptionPane.showMessageDialog(null, "Erreur dans la réservation du livre", "Erreur", JOptionPane.INFORMATION_MESSAGE);
		}
		pbe.mettreAJoutEmpruntsReservations();
		dispose();
	}
}
