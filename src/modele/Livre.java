package modele;

import java.sql.ResultSet;
import java.sql.SQLException;

public class Livre {
    private int id;
    private String titre;
    private String auteur;
    private int exemplaire;
    private Integer tempsRestant;
    
    public Livre(int _id, String _titre, String _auteur, int _ex, Integer _tempsRestant) {
        this.id = _id;
        this.titre = _titre;
        this.auteur = _auteur;
        this.exemplaire = _ex;
        this.tempsRestant = _tempsRestant;
    }
    
    public String toString() {
        String str = getTitre() + " - " + getAuteur();
        
        if (tempsRestant >= 0) {
            str += "(" + String.valueOf(tempsRestant) + " jours restants)";
        } else {
            str += "(" + String.valueOf(-tempsRestant) + " jours de retard)";
        }
        return str;
    }

    public int getId() {
        return id;
    }
    
    public int getExemplaire() {
        return exemplaire;
    }

    public String getAuteur() {
        return auteur;
    }

    public String getTitre() {
        return titre;
    }
    
    public int getTempsRestant() {
        return tempsRestant;
    }
    
    public static int nbExemplaire(String livId, boolean exclureEmprunts) {
        try {
            if (exclureEmprunts) {
                String sql = "SELECT a.num - b.num FROM "
                        + "(SELECT COUNT(*) num FROM exemplaire WHERE id_liv = ?) a, "
                        + "(SELECT COUNT(*) num FROM emprunt,exemplaire WHERE emprunt.id_ex = exemplaire.id_ex AND id_liv = ?) b";
                ResultSet rset = Connexion.executeQuery(sql, new String[] { livId, livId });

                if (rset.next()) {
                	int res = rset.getInt(1);
                	rset.close();
                	return res;
                }
                rset.close();
            } else {
                String sql = "SELECT count(id_ex) FROM exemplaire WHERE id_liv = ? ";
                ResultSet rset = Connexion.executeQuery(sql, new String[] { livId });
                if (rset.next()) {
                	int res = rset.getInt(1);
                	rset.close();
                	return res;
                }
                rset.close();
            }
        } catch (SQLException e) {
        }

        return -1;
    }
}
