package modele;

public class Livre {
    private String titre;
    private String auteur;
    private int exemplaire;
    
    public Livre(String _titre, String _auteur, int _ex) {
        this.titre = _titre;
        this.auteur = _auteur;
        this.exemplaire = _ex;
    }
    
    public String toString() {
        return getTitre() + " - " + getAuteur();
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
}
