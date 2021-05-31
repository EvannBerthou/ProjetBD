package modele;

import java.lang.reflect.Array;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import javax.swing.table.*;

import constante.IdConnexion;
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
		
		
		ArrayList<String> arrayTitres = new ArrayList<String>();
		ArrayList<String> arrayAuteurs = new ArrayList<String>();
		ArrayList<Integer> arrayExemplaire = new ArrayList<Integer>();
		
		String sql;
		if (!exclureEmprunte) {
		    sql = "SELECT count(id_ex) FROM exemplaire WHERE id_liv = ? ";
		} else {
		    sql = "SELECT a.num - b.num - c.num FROM "
                    + "(SELECT COUNT(*) num FROM exemplaire WHERE id_liv = ?) a, "
                    + "(SELECT COUNT(*) num FROM emprunt,exemplaire WHERE emprunt.id_ex = exemplaire.id_ex AND id_liv = ?) b,"
                    + "(SELECT COUNT(*) num FROM reserv WHERE id_liv = ?) c";
		}

		try {
			ResultSet result = Connexion.executeQuery("SELECT * FROM livre WHERE id_liv");
			while(result.next()) {
				arrayTitres.add(result.getString(2));
				arrayAuteurs.add(result.getString(3));
				ResultSet exmplaire = Connexion.executeQuery(sql, new String[] { result.getString(1), result.getString(1), result.getString(1)});
				arrayExemplaire.add(exmplaire.getInt(1));
			}
		}catch(SQLException e) {
			System.out.println(e);	
		}
		
		String[] titres = arrayTitres.toArray(new String[0]);
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
	public void setAllValue(String[] parTitres,String[] parAuteurs, Integer[] parExemplaires){
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
