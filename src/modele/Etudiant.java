package modele;

public class Etudiant {
    private String nom;
    private String prenom;
    private String email;
    
    public Etudiant(String _nom, String _prenom, String _email) {
        this.nom = _nom;
        this.prenom = _prenom;
        this.email = _email;
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
    
    public String toString() {
        return nom + " " + prenom;
    }
}
