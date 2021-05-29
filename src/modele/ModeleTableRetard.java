package modele;

import javax.swing.table.DefaultTableModel;

public class ModeleTableRetard extends DefaultTableModel{
	int nCol = 20;
	int nRow = 3;
	
	public ModeleTableRetard() {
		setColumnCount(nCol);
		setRowCount(nRow);
		
		String[] colName = new String[] {"Titre","Etudiant","Adresse Email"};
		
		String[] titres = new String[] {"Titre1","Titre2","Titre3","Titre4"};
		String[] auteurs = new String[] {"Etudiant1","Etudiant2","Etudiant3","Etudiant4"};
		String[] exemplaire = new String[] {"Email1","Email2","Email3","Email4"};
		setAllValue(titres,auteurs,exemplaire);
		
		setColumnIdentifiers(colName);
	}

	
	/** Methode pour changer toute les lignes, chaque liste doivent faire la même tailles, sinon les éléments de trop ne seront pas afficher
	 * 
	 * @param parTitres Liste des titres
	 * @param parEtudiant Liste des etudiant
	 * @param parAdresseEmail Liste des qdresse email
	 */
	public void setAllValue(String[] parTitres,String[] parEtudiant, String[] parAdresseEmail){
		int length = parTitres.length;
		if(length > parEtudiant.length) length = parEtudiant.length;
		if(length > parAdresseEmail.length) length = parAdresseEmail.length;
		nRow = length;
		setRowCount(length);
		for (int i=0;i<length;i++) {
			setValueAt(parTitres[i],i,0);
			this.isCellEditable(i,0);
			setValueAt(parEtudiant[i],i,1);
			this.isCellEditable(i,1);
			setValueAt(parAdresseEmail[i],i,2);
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
