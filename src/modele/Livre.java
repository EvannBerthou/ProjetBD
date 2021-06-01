package modele;

import java.sql.ResultSet;
import java.sql.SQLException;

public class Livre {
    private int id;
    private String titre;
    private String auteur;
    private int exemplaire;
    
    public Livre(int _id, String _titre, String _auteur, int _ex) {
        this.id = _id;
        this.titre = _titre;
        this.auteur = _auteur;
        this.exemplaire = _ex;
    }
    
    public String toString() {
        return getTitre() + " - " + getAuteur();
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
    
    public static int nbExemplaire(String livId, boolean exclureEmprunts) {
        try {
            if (exclureEmprunts) {
                String sql = "SELECT a.num - b.num FROM "
                        + "(SELECT COUNT(*) num FROM exemplaire WHERE id_liv = ?) a, "
                        + "(SELECT COUNT(*) num FROM emprunt,exemplaire WHERE emprunt.id_ex = exemplaire.id_ex AND id_liv = ?) b";
                        //+ "(SELECT COUNT(*) num FROM reserv WHERE id_liv = ?) c";
                return Connexion.executeQuery(sql, new String[] { livId, livId }).getInt(1);
            } else {

                String sql = "SELECT count(id_ex) FROM exemplaire WHERE id_liv = ? ";
                return Connexion.executeQuery(sql, new String[] { livId }).getInt(1);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return 0;
    }

    public static String getIdByTitre(String titre) {
        try {
            return Connexion.executeQuery("SELECT id_liv FROM livre WHERE titre = ?", new String[] { titre }).getString(1);
        } catch (SQLException e) {
            e.printStackTrace();
            return "";
        }
    }
}
