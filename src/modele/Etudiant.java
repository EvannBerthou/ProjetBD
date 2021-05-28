package modele;

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
}
