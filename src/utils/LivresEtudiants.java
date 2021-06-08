package utils;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import modele.Connexion;
import modele.Etudiant;
import modele.Livre;
import vue.PopupChoixExemplaire;
/**
 * 
 * @author Evann,Benjamin
 *
 */
public class LivresEtudiants {
    /**
     * retourne les livre emprunter par l'étudiant 
     * @param etu étdudiant
     * @return liste de livre emprunté
     */
    public static Livre[] getLivresEmpruntEtudiants(Etudiant etu) {
        ResultSet rset; 
        try {
            rset = Connexion.executeQuery(
                    "SELECT livre.id_liv, titre,auteur, exemplaire.id_ex, "
                    + "ROUND(date_retour - sysdate) "
                    + "FROM exemplaire, livre, etu, emprunt " 
                    + "WHERE emprunt.id_ex = exemplaire.id_ex AND exemplaire.id_liv = livre.id_liv " 
                            + "AND emprunt.id_et = etu.id_et AND etu.email = ?", 
                    new String[] {
                            etu.getEmail()
                    });
            ArrayList<Livre> livres = new ArrayList<Livre>();
            while (rset.next()) {
                Livre livre = new Livre(rset.getInt(1), rset.getString(2), rset.getString(3), rset.getInt(4), rset.getInt(5));
                livres.add(livre);
            }
            rset.close();
            return (Livre[]) livres.toArray(new Livre[livres.size()]);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return new Livre[0];
    }
    
    /**
     * retourn les livres reservé par l'édtudiant 
     * @param etu étudiant
     * @return liste des livre emprunté par l'etudiant
     */
    public static Livre[] getLivresReserveEtudiants(Etudiant etu) {
        ResultSet rset; 
        try {
            rset = Connexion.executeQuery(
                    "SELECT livre.id_liv,titre,auteur, ROUND(date_fin_res - sysdate) "
                    + "FROM livre, etu, reserv "
                    + "WHERE reserv.id_liv = livre.id_liv AND reserv.id_et = etu.id_et AND etu.email = ? "
                    + "AND ROUND(date_fin_res - sysdate) >= 0",
                    new String[] {
                            etu.getEmail()
                    });
            ArrayList<Livre> livres = new ArrayList<Livre>();
            while (rset.next()) {
                Livre livre = new Livre(rset.getInt(1), rset.getString(2), rset.getString(3), -1, rset.getInt(4));
                if (livre.getTempsRestant() < 0) {
                    supprimerReservation(etu, rset.getString(1));
                } else {
                    livres.add(livre);
                }
            }
            rset.close();
            return (Livre[]) livres.toArray(new Livre[livres.size()]);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return new Livre[0];
    }

    /**
     * retourne le nombre de livres emprunté par l'etudiant 
     * @param etu étudiant 
     * @return nombre de livres emprunté par l'étudiant 
     */
    private static int nbLivreEmprunte(Etudiant etu) {
        try {
            ResultSet rset = Connexion.executeQuery("SELECT COUNT(*) FROM emprunt WHERE id_et = (SELECT id_et FROM etu WHERE email = ?)", new String[] {etu.getEmail()});
            if (rset.next()) {
                int res = rset.getInt(1);
                rset.close();
                return res;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0;
    }
    
    /**
     * verifi si l'étudiant peut emprunter un livre 
     * @param etu étudiant 
     * @param id_ex identifiant de l'exemplaire
     * @return retourne un boolean 
     */
    public static boolean EmprunterLivre(Etudiant etu, String id_ex) {
        int livresEmpruntes = nbLivreEmprunte(etu);
        if (livresEmpruntes >= 5) {
            return false;
        }
        
        try {
            Connexion.executeUpdate("INSERT INTO emprunt (id_et, id_ex) VALUES ("
                    + "(SELECT id_et FROM etu WHERE email=?), ?)",  
                    new String[] {
                            etu.getEmail(), id_ex
                    });
        } catch (Exception e) {
        }
        return true;
    }
    
    /**
     * retourne le nombre de livre reserver par l'étudiant 
     * @param etu édtudiant 
     * @return retourne un int pour le nombre de livres réservé par l'étudiant 
     */
    private static int nbLivreReserve(Etudiant etu) {
        try {
            ResultSet rset = Connexion.executeQuery("SELECT COUNT(*) FROM reserv "
                    + "WHERE id_et = (SELECT id_et FROM etu WHERE email = ?) ",
                    new String[] {etu.getEmail()});
            if (rset.next()) {
                int res = rset.getInt(1);
                rset.close();
                return res;
            }
            rset.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0;
    }
    
    /**
     * indique si un étudiant peut résever un livre 
     * @param etu étudiant 
     * @param id_liv identifiant du livre
     * @return retourn un boolean 
     */
    public static boolean ReserverLivre(Etudiant etu, String id_liv) {
        int livresEmpruntes = nbLivreReserve(etu);
        if (livresEmpruntes >= 5) {
            return false;
        }

        try {
            ResultSet rset = Connexion.executeQuery("SELECT id_liv FROM reserv WHERE id_et = (SELECT id_et FROM etu WHERE email = ?)",
                    new String[] { etu.getEmail() });
            while (rset.next()) {
                if (rset.getString(1).equals(id_liv)) {
                    rset.close();
                    return false;
                }
            }
            rset.close();
        } catch (SQLException e) {
        }
        
        try {
            Connexion.executeUpdate("INSERT INTO reserv (id_et, id_liv) VALUES ((SELECT id_et FROM etu WHERE email = ?), ?)",
                    new String[] { etu.getEmail(), id_liv });
        } catch (Exception e) {
        }
        return true;
    }
    
    /**
     * supprime un emprunt
     * @param etu étudiant 
     * @param exemplaire exemplaire supprimer de la liste des emprunts
     */
    public static void supprimerEmprunt(Etudiant etu, int exemplaire) {
        try {
            Connexion.executeUpdate("DELETE FROM emprunt WHERE id_et = (SELECT id_et FROM etu WHERE email = ?) AND id_ex = ?",
                    new String[] {
                            etu.getEmail(),
                            String.valueOf(exemplaire)
                    });
        } catch (Exception e) {
        }
    }
    
    /**
     * supprime une reservation
     * @param etu étudiant 
     * @param id identifiant 
     */
    public static void supprimerReservation(Etudiant etu, String id) {
        try {
            Connexion.executeUpdate("DELETE FROM reserv WHERE id_et = (SELECT id_et FROM etu WHERE email = ?) "
                    + "AND id_liv = ?", new String[] { etu.getEmail(), id });
        } catch (Exception e) {
        }
    }

    /**
     * modifi la date de retour 15 jour apres la precedente  
     * @param etu étudiant 
     * @param livre livre 
     */
    public static void relancerLivre(Etudiant etu, Livre livre) {
        try {
            Connexion.executeUpdate("UPDATE emprunt SET date_retour = "
                    + "(SELECT (date_retour + 15) FROM emprunt WHERE id_et = (SELECT id_et FROM etu WHERE email = ?) AND id_ex = ?) "
                    + "WHERE id_et = (SELECT id_et FROM etu WHERE email = ?) AND id_ex = ?", 
                    new String[] { etu.getEmail(), String.valueOf(livre.getExemplaire()), etu.getEmail(), String.valueOf(livre.getExemplaire()) });
        } catch (Exception e) {
        }
    }
}
