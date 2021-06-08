package modele;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * 
 * @author Benjamin
 *
 */
public class Etudiant {
    /**
     * nom de l'etudiant 
     */
    private String nom;
    /**
     * prenom de l'etudiant 
     */
    private String prenom;
    /**
     * email de l'etudiant 
     */
    private String email;
    /**
     * mot de passe de létudiant 
     */
    private String mdp;
    
    /**
     * initialisation d'un nouvel étudiant  
     * @param _nom nom de l'étudiant passé en parametre 
     * @param _prenom prenom de l'étudiant passé en parametre 
     * @param _email email de l'étudiant passé en parametre
     */
    public Etudiant(String _nom, String _prenom, String _email) {
        this.nom = _nom;
        this.prenom = _prenom;
        this.email = _email;
        this.mdp = null;
    }
    
    /**
     * initialisation d'un nouvel étudiant
     * @param _nom nom de l'étudiant passé en parametre 
     * @param _prenom prenom de l'étudiant passé en parametre
     * @param _email email de l'étudiant passé en parametre
     * @param _mdp mot de passe de l'étudiant passé en parametre 
     */
    public Etudiant(String _nom, String _prenom, String _email, String _mdp) {
        this.nom = _nom;
        this.prenom = _prenom;
        this.email = _email;
        this.mdp = _mdp;
    }
    
    /**
     * retourne le nom de l'étudiant
     * @return nom 
     */
    public String getNom() {
        return nom;
    }
    
    /**
     * retourne le prenom de l'étudiant
     * @return prenom 
     */
    public String getPrenom() {
        return prenom;
    }
    
    /**
     * retourne l'email de l'étudiant
     * @return email
     */
    public String getEmail() {
        return email;
    }
    
    /**
     * retourne le mot de passe de l'étudiant
     * @return mot de passe
     */
    public String getMdp() {
        return mdp;
    }
    
    /**
     * retourne le nom et le prenom de l'étudiant
     * @return nom + prenom de l'étudiant
     */
    public String toString() {
        return nom + " " + prenom;
    }
    
    /**
     * supprime un étudiant de la base de donné
     * l'étudiant est supprimer des table emprunt, reserv et etu 
     */
    public void supprimer() {
        try {
            String[] params = new String[] { getEmail() };
            Connexion.executeUpdate("DELETE FROM emprunt WHERE id_et = (SELECT id_et FROM etu WHERE email = ?)", params);
            Connexion.executeUpdate("DELETE FROM reserv WHERE id_et = (SELECT id_et FROM etu WHERE email = ?)", params);
            Connexion.executeUpdate("DELETE FROM etu WHERE email = ?", params);
        } catch (Exception e) {
        }
    }
}
