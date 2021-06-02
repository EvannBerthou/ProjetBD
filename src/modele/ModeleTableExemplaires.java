package modele;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import javax.swing.table.DefaultTableModel;

public class ModeleTableExemplaires extends DefaultTableModel {
    int nCol = 3; 
    int nRow = 20;
    
    /**
     * Constructeur de la classe
     */
    public ModeleTableExemplaires(String id_liv) {
        setColumnCount(nCol);
        setRowCount(nRow);
        
        ArrayList<String> arrayExemplaire = new ArrayList<String>();
        
        try {
            ResultSet rset = Connexion.executeQuery("SELECT id_ex FROM exemplaire WHERE id_liv = ? "
                    + "AND id_ex NOT IN (SELECT id_ex FROM emprunt) "
                    + "AND id_liv NOT IN (SELECT id_liv FROM reserv)", new String[] {id_liv});
            while(rset.next()) {             
                arrayExemplaire.add(rset.getString(1));
            }
            rset.close();
        }catch(SQLException e) {
            System.out.println(e);  
        }
        
        String[] exemplaire = arrayExemplaire.toArray(new String[0]);
        setAllValue(exemplaire);
        
        
        String[] colName = new String[] {"Exemplaire"};
        setColumnIdentifiers(colName);
    }

    
    /** 
     * 
     * @param parExemplaires Liste des id d'exemplaire 
     */
    public void setAllValue(String[] parExemplaires){
        int length = parExemplaires.length;
        nRow = length;
        setRowCount(length);
        for (int i=0;i<length;i++) {
            setValueAt(parExemplaires[i],i,0);
            this.isCellEditable(i,0);
        }
    }
    
    /**
     * Methode pour rendre les cellules inéditable
     */
    public boolean isCellEditable(int row, int column) {
        return false;
    }
}
