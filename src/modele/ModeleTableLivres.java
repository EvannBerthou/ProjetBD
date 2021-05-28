package modele;

import javax.swing.table.*;
/** Classe pour définir le style de la table du panelLivres
 * 
 * @author jules
 *
 */
public class ModeleTableLivres extends DefaultTableModel{
	int nCol = 3; 
	int nRow = 20;
	
	/**
	 * Constructeur de la classe
	 */
	public ModeleTableLivres() {
		setColumnCount(nCol);
		setRowCount(nRow);
		
		String[] titres = new String[] {"Titre1","Titre2","Titre3","Titre4"};
		String[] auteurs = new String[] {"Auteur1","Auteur2","Auteur3","Auteur4"};
		int[] exemplaire = new int[] {1,2,3,4};
		setAllValue(titres,auteurs,exemplaire);
		
		String[] colName = new String[] {"Titre","Auteur","Exemplaire"};
		
		setColumnIdentifiers(colName);
	}
	
	/** Methode pour changer toute les lignes, chaque liste doivent faire la même tailles, sinon les éléments de trop ne seront pas afficher
	 * 
	 * @param parTitres Liste des titres
	 * @param parAuteurs Listes des auteurs
	 * @param parExemplaires Liste des nombre d'exemplaire 
	 */
	public void setAllValue(String[] parTitres,String[] parAuteurs, int[] parExemplaires){
		int length = parTitres.length;
		if(length > parAuteurs.length) length = parAuteurs.length;
		if(length > parExemplaires.length) length = parExemplaires.length;
		nRow = length;
		setRowCount(length);
		for (int i=0;i<length;i++) {
			setValueAt(parTitres[i],i,0);
			this.isCellEditable(i,0);
			setValueAt(parAuteurs[i],i,1);
			this.isCellEditable(i,1);
			setValueAt(parExemplaires[i],i,2);
			this.isCellEditable(i,2);
		}
	}
	
	/**
	 * Methode pour rendre les cellules inéditable
	 */
	public boolean isCellEditable(int row, int column) {
	       return false;
	    }
}
