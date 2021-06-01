package vue;

import java.awt.event.ActionEvent;
import javax.swing.JOptionPane;

import modele.Etudiant;
import modele.JTableLivre;
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
		JTableLivre livre = (JTableLivre) tableLivres.getValueAt(row, 0);
		String id = String.valueOf(livre.getId());
		if (LivresEtudiants.ReserverLivre(etu, id) == false) {
			JOptionPane.showMessageDialog(null, "Erreur dans la réservation du livre", "Erreur", JOptionPane.INFORMATION_MESSAGE);
		}
		
		parent.mettreAJourLivres();
		dispose();
	}
}
