package modele;

import java.sql.ResultSet;
import java.sql.SQLException;

public class Etudiant {
    private String nom;
    private String prenom;
    private String email;
    private String mdp;
    
    public Etudiant(String _nom, String _prenom, String _email) {
        this.nom = _nom;
        this.prenom = _prenom;
        this.email = _email;
        this.mdp = null;
    }
    
    public Etudiant(String _nom, String _prenom, String _email, String _mdp) {
        this.nom = _nom;
        this.prenom = _prenom;
        this.email = _email;
        this.mdp = _mdp;
    }
    
    public String getNom() {
        return nom;
    }
    
    public String getPrenom() {
        return prenom;
    }
    
    public String getEmail() {
        return email;
    }
    
    public String getMdp() {
        return mdp;
    }
    
    public String toString() {
        return nom + " " + prenom;
    }

    //TODO; Trouver un moyen de gérer le cas où deux étudiants ont le même nom
    //  Peut-être afficher un panel avec un choix avec tous les étudiants
    public static Etudiant getEtudiantByName(String name) {
        String[] parts = name.split(" ");
        String nom = parts[0];
        String prenom = parts[1];
        try {
            ResultSet rset = Connexion.executeQuery("SELECT nom,prenom,email FROM etu WHERE nom = ? AND prenom = ?", new String[] {nom, prenom});
            
            if (rset != null) {
                return new Etudiant(rset.getString(1), rset.getString(2), rset.getString(3));
            }
            
            System.err.println("Etudiant inconnu");
            return null;
            
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }
}
