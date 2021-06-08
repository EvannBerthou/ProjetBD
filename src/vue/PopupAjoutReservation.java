package vue;

import java.awt.event.ActionEvent;
import javax.swing.JOptionPane;
import javax.swing.JTable;

import modele.Etudiant;
import modele.JTableLivre;
import modele.ModeleTableLivres;
import modele.UpdateListe;
import utils.LivresEtudiants;

/**
 * 
 * @author Evann
 *
 */
public class PopupAjoutReservation extends PopupLivreEtudiant {

	/**
	 * panel biblioth�caire 
	 */
	PanelBibliothecaireEtudiant pbe = null;
	/**
	 * �tudiant 
	 */
	Etudiant etu;
	UpdateListe parent;
	
	/**
	 * initialisation du panel ajout reservation
	 * @param _pbe panel de la biblioth�caire 
	 */
	public PopupAjoutReservation(PanelBibliothecaireEtudiant _pbe) {
		super(_pbe.getEtuSelectionne());
		this.pbe = _pbe;
		this.parent = _pbe;
		etu = pbe.getEtuSelectionne();
		if (etu != null) {
			setTitle("Ajout reservation pour " + etu.toString());
		}
	}
	
	/**
	 * initialisation du panel ajout de reservation 
	 * @param _etu �tudiant
	 * @param _panel panel 
	 */
	public PopupAjoutReservation(Etudiant _etu, UpdateListe _panel) {
        super(_etu);
        this.etu = _etu;
        this.parent = _panel;
        setTitle("Ajout reservation pour " + etu.toString());
    }

	/**
	 * gestion des evenement 
	 * @param e evenement 
	 */
	public void actionPerformed(ActionEvent e) {
	    if (e.getSource() == rechercheBouton) {
	        tableLivres.setModel(new ModeleTableLivres(true, tfTitre.getText(), tfAuteur.getText()));
	    } else {
	        int row = tableLivres.getSelectedRow();
	        JTableLivre livre = (JTableLivre) tableLivres.getValueAt(row, 0);
	        String id = String.valueOf(livre.getId());
	        if (LivresEtudiants.ReserverLivre(etu, id) == false) {
	            JOptionPane.showMessageDialog(null, "Erreur dans la r�servation du livre", "Erreur", JOptionPane.INFORMATION_MESSAGE);
	        }
	        parent.mettreAJourLivres();
	        dispose();
	    }
	}
}
