package utils;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import modele.Connexion;
import modele.Etudiant;
import modele.Livre;

public class LivresEtudiants {

    public static Livre[] getLivresEmpruntEtudiants(Etudiant etu) {
        ResultSet rset; 
        try {
            rset = Connexion.executeQuery(
                    "SELECT titre,auteur, exemplaire.id_ex FROM exemplaire, livre, etu, emprunt " +
                    "WHERE emprunt.id_ex = exemplaire.id_ex AND exemplaire.id_liv = livre.id_liv " 
                            + "AND emprunt.id_et = etu.id_et AND etu.email = ?", 
                    new String[] {
                            etu.getEmail()
                    });
            ArrayList<Livre> livres = new ArrayList<Livre>();
            while (rset.next()) {
                Livre livre = new Livre(rset.getString(1), rset.getString(2), rset.getInt(3));
                livres.add(livre);
            }
            
            return (Livre[]) livres.toArray(new Livre[livres.size()]);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return new Livre[0];
    }
    
    public static Livre[] getLivresReserveEtudiants(Etudiant etu) {
        ResultSet rset; 
        try {
            rset = Connexion.executeQuery(
                    "SELECT titre,auteur FROM livre, etu, reserv " +
                    "WHERE reserv.id_liv = livre.id_liv AND reserv.id_et = etu.id_et AND etu.email = ?", 
                    new String[] {
                            etu.getEmail()
                    });
            ArrayList<Livre> livres = new ArrayList<Livre>();
            while (rset.next()) {
                Livre livre = new Livre(rset.getString(1), rset.getString(2), -1);
                livres.add(livre);
            }
            
            return (Livre[]) livres.toArray(new Livre[livres.size()]);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return new Livre[0];
    }

    private static int nbLivreEmprunte(Etudiant etu) {
        try {
            ResultSet rset = Connexion.executeQuery("SELECT COUNT(*) FROM emprunt WHERE id_et = (SELECT id_et FROM etu WHERE email = ?)", new String[] {etu.getEmail()});
            if (rset.next()) { // Si un résultat à été renvoyé
                return rset.getInt(1);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0;
    }
    
    //TODO: Remplacer nomLivre et auteurLivre par un objet de la classe Livre
    public static boolean EmprunterLivre(Etudiant etu, String nomLivre, String auteurLivre) {
        int livresEmpruntes = nbLivreEmprunte(etu);
        if (livresEmpruntes >= 5) {
            return false;
        }
        
        //TODO: L'id_ex ne doit pas être choisis tout seul mais par l'utilisateur afin de pouvoir suivre qui emprunte
        // quel exemplaire.
        try {
            Connexion.executeUpdate("INSERT INTO emprunt (id_et, id_ex) VALUES ("
                    + "(SELECT id_et FROM etu WHERE email=?), "
                    + "(SELECT id_ex FROM exemplaire, livre WHERE exemplaire.id_liv = livre.id_liv AND titre = ? AND auteur = ? "
                    + "AND id_ex NOT IN (SELECT ex.id_ex FROM exemplaire ex, emprunt em WHERE ex.id_ex = em.id_ex))"
                    + ")",  
                    new String[] {
                            etu.getEmail(),
                            nomLivre, auteurLivre
                    });
        } catch (SQLException e) {
        }
        return true;
    }
    
    private static int nbLivreReserve(Etudiant etu) {
        try {
            ResultSet rset = Connexion.executeQuery("SELECT COUNT(*) FROM reserv WHERE id_et = (SELECT id_et FROM etu WHERE email = ?)", new String[] {etu.getEmail()});
            if (rset.next()) { // Si un résultat à été renvoyé
                return rset.getInt(1);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0;
    }
    
    public static boolean ReserverLivre(Etudiant etu, String nomLivre, String auteurLivre) {
        int livresEmpruntes = nbLivreReserve(etu);
        if (livresEmpruntes >= 5) {
            return false;
        }
        
        try {
            Connexion.executeUpdate("INSERT INTO reserv (id_et, id_liv) VALUES ("
                    + "(SELECT id_et FROM etu WHERE email=?), "
                    + "(SELECT id_liv FROM livre WHERE titre = ? AND auteur = ?)", 
                    new String[] {
                            etu.getEmail(),
                            nomLivre, auteurLivre
                    });
        } catch (SQLException e) {
        }
        return true;
    }
    
    public static void supprimerEmprunt(Etudiant etu, int exemplaire) {
        try {
            Connexion.executeUpdate("DELETE FROM emprunt WHERE id_et = (SELECT id_et FROM etu WHERE email = ?) AND id_ex = ?",
                    new String[] {
                            etu.getEmail(),
                            String.valueOf(exemplaire)
                    });
        } catch (SQLException e) {
        }
    }
    
    public static void supprimerReservation(Etudiant etu, String titre, String auteur) {
        try {
            Connexion.executeUpdate("DELETE FROM reserv WHERE id_et = (SELECT id_et FROM etu WHERE email = ?) "
                    + "AND id_liv = (SELECT id_liv FROM livre WHERE titre = ? AND auteur = ?)",
                    new String[] {
                            etu.getEmail(),
                            titre, auteur
                    });
        } catch (SQLException e) {
        }
    }
}
