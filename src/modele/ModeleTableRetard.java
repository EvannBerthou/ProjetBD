package modele;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import javax.swing.table.DefaultTableModel;

import constante.IdConnexion;

public class ModeleTableRetard extends DefaultTableModel{
	/**
	 * nombre de colonne initial
	 */
	int nCol = 20;
	/**
	 * nombre de ligne initial
	 */
	int nRow = 3;
	
	/**
	 * Le constructeur de la classe ModeleTableRetard
	 */
	public ModeleTableRetard() {
		setColumnCount(nCol);
		setRowCount(nRow);
		
		ArrayList<String> arrayTitres = new ArrayList<String>();
		ArrayList<String> arrayEtudiant = new ArrayList<String>();
		ArrayList<String> arrayEmail = new ArrayList<String>();
		
		try {
		    //TODO: Marche pour SQLite mais pas pour oracle
			ResultSet result = Connexion.executeQuery("SELECT prenom,nom,email,titre from emprunt e, etu, livre l,exemplaire ex where etu.id_et = e.id_et AND l.id_liv = ex.id_liv AND ex.id_ex = e.id_ex AND e.date_retour <  DATE()"); 
			while(result.next()) {
				arrayEtudiant.add(result.getString(1) + " " + result.getString(2));
				arrayEmail.add(result.getString(3));
				arrayTitres.add(result.getString(4));
			}
			
		}catch(SQLException e) {
			System.out.println(e);	
		}
		
		String[] titres = arrayTitres.toArray(new String[0]);
		String[] etudiant = arrayEtudiant.toArray(new String[0]);
		String[] email = arrayEmail.toArray(new String[0]);
		
		setAllValue(titres,etudiant,email);
		
		String[] colName = new String[] {"Titre","Etudiant","Adresse Email"};
		setColumnIdentifiers(colName);
	}

	
	/** Methode pour changer toute les lignes, chaque liste doivent faire la m�me tailles, sinon les �l�ments de trop ne seront pas afficher
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
	 * Methode pour rendre les cellules in�ditable
	 */
	public boolean isCellEditable(int row, int column) {
		return false;
	}
}
