package modele;

import java.lang.reflect.Array;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import javax.swing.table.*;

import constante.IdConnexion;
import modele.Livre;

/** Classe pour d�finir le style de la table du panelLivres
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
	public ModeleTableLivres(boolean exclureEmprunte) {
		setColumnCount(nCol);
		setRowCount(nRow);
		
		
		ArrayList<JTableLivre> arrayTitres = new ArrayList<JTableLivre>();
		ArrayList<String> arrayAuteurs = new ArrayList<String>();
		ArrayList<Integer> arrayExemplaire = new ArrayList<Integer>();
		
		try {
			ResultSet result = Connexion.executeQuery("SELECT * FROM livre order by titre asc");
			while(result.next()) {
				arrayTitres.add(new JTableLivre(result.getInt(1),result.getString(2)));
				arrayAuteurs.add(result.getString(3));
				arrayExemplaire.add(Livre.nbExemplaire(result.getString(1), exclureEmprunte));
			}
		}catch(SQLException e) {
			System.out.println(e);	
		}
		
		JTableLivre[] titres = arrayTitres.toArray(new JTableLivre[0]);
		String[] auteurs = arrayAuteurs.toArray(new String[0]);
		Integer[] exemplaire = arrayExemplaire.toArray(new Integer[0]);
		setAllValue(titres,auteurs,exemplaire);
		
		
		String[] colName = new String[] {"Titre","Auteur","Exemplaire"};
		setColumnIdentifiers(colName);
	}

	
	/** Methode pour changer toute les lignes, chaque liste doivent faire la m�me tailles, sinon les �l�ments de trop ne seront pas afficher
	 * 
	 * @param parTitres Liste des titres
	 * @param parAuteurs Listes des auteurs
	 * @param parExemplaires Liste des nombre d'exemplaire 
	 */
	public void setAllValue(JTableLivre[] parTitres,String[] parAuteurs, Integer[] parExemplaires){
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
	 * Methode pour rendre les cellules in�ditable
	 */
	public boolean isCellEditable(int row, int column) {
	       return false;
	}
}
