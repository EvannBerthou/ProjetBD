package utils;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import modele.Connexion;
import modele.Etudiant;

public class LivresEtudiants {

    public static String[] getLivresEmpruntEtudiants(Etudiant etu) {
        ResultSet rset; 
        try {
            rset = Connexion.executeQuery(
                    "SELECT titre,auteur FROM exemplaire, livre, etu, emprunt " +
                    "WHERE emprunt.id_ex = exemplaire.id_ex AND exemplaire.id_liv = livre.id_liv " 
                            + "AND emprunt.id_et = etu.id_et AND etu.email = ?", 
                    new String[] {
                            etu.getEmail()
                    });
            ArrayList<String> livres = new ArrayList<String>();
            while (rset.next()) {
                livres.add(rset.getString(1) + " - " + rset.getString(2));
            }
            
            return (String[]) livres.toArray(new String[livres.size()]);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return new String[0];
    }
    
    public static String[] getLivresReserveEtudiants(Etudiant etu) {
        ResultSet rset; 
        try {
            rset = Connexion.executeQuery(
                    "SELECT titre,auteur FROM livre, etu, reserv " +
                    "WHERE reserv.id_liv = livre.id_liv AND reserv.id_et = etu.id_et AND etu.email = ?", 
                    new String[] {
                            etu.getEmail()
                    });
            ArrayList<String> livres = new ArrayList<String>();
            while (rset.next()) {
                livres.add(rset.getString(1) + " - " + rset.getString(2));
            }
            
            return (String[]) livres.toArray(new String[livres.size()]);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return new String[0];
    }

    public static void EmprunterLivre(Etudiant etu, String nomLivre, String auteurLivre) {
        try {
            System.out.println(nomLivre + " " + auteurLivre);
            Connexion.executeUpdate("INSERT INTO emprunt (id_et, id_ex) VALUES ("
                    + "(SELECT id_et FROM etu WHERE email=?), "
                    + "(SELECT id_ex FROM exemplaire, livre WHERE exemplaire.id_liv = livre.id_liv AND titre=? AND auteur=?)"
                    + ")",  
                    new String[] {
                            etu.getEmail(),
                            nomLivre, auteurLivre
                    });
        } catch (SQLException e) {
        }
    }
    
    public static void ReserverLivre(Etudiant etu, String nomLivre, String auteurLivre) {
        try {
            System.out.println(nomLivre + " " + auteurLivre);
            Connexion.executeUpdate("INSERT INTO reserv (id_et, id_liv) VALUES ("
                    + "(SELECT id_et FROM etu WHERE email=?), "
                    + "(SELECT id_liv FROM livre WHERE titre=? AND auteur=?)"
                    + ")",  
                    new String[] {
                            etu.getEmail(),
                            nomLivre, auteurLivre
                    });
        } catch (SQLException e) {
        }
    }
}
