package modele;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * 
 * @author Evann
 *
 */
public class Livre {
    /**
     * identifiant du livre 
     */
    private int id;
    /**
     * titre du livre
     */
    private String titre;
    /**
     * auteur du livre
     */
    private String auteur;
    /**
     * exemplaire du livre 
     */
    private int exemplaire;
    /**
     * temps restant avant de rendre le livre 
     */
    private Integer tempsRestant;
    
    /**
     * initialisation d'un livre
     * @param _id identifiant du livre 
     * @param _titre titre du livre 
     * @param _auteur auteur du livre
     * @param _ex exemplaire du livre 
     * @param _tempsRestant temps restant du livre 
     */
    public Livre(int _id, String _titre, String _auteur, int _ex, Integer _tempsRestant) {
        this.id = _id;
        this.titre = _titre;
        this.auteur = _auteur;
        this.exemplaire = _ex;
        this.tempsRestant = _tempsRestant;
    }
    
    /**
     * Methode toString
     */
    public String toString() {
        String str = getTitre() + " - " + getAuteur();
        
        if (tempsRestant >= 0) {
            str += "(" + String.valueOf(tempsRestant) + " jours restants)";
        } else {
            str += "(" + String.valueOf(-tempsRestant) + " jours de retard)";
        }
        return str;
    }

    /**
     * retourne l'identifiant du livre 
     * @return l'id du livre  
     */
    public int getId() {
        return id;
    }
    
    /**
     * retourne l'exemplaire du livre
     * @return l'exemplaire du livre 
     */
    public int getExemplaire() {
        return exemplaire;
    }

    /**
     * 
     * @return retourne l'auteur du livre
     */
    public String getAuteur() {
        return auteur;
    }
    
    /**
     * 
     * @return le titre du livre 
     */
    public String getTitre() {
        return titre;
    }
    
    /**
     * 
     * @return temps restant du livre avant la rente 
     */
    public int getTempsRestant() {
        return tempsRestant;
    }
    
    /**
     * retourne le nombre d'exemplaire d'un meme livre 
     * @param livId idetifiant du livre 
     * @param exclureEmprunts 
     * @return int le nombre d'exemplaire du livre 
     */
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
