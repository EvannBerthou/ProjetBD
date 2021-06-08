package modele;

/**
 *  
 * @author Evann 
 */
public class JTableLivre {
	
	/**
	 * Id du livre
	 */
	int id;
	
	/**
	 * Nom du Livre
	 */
	String nom;
	
	/**Le constructeur de la classe
	 * 
	 * @param parId l'id du livre
	 * @param parNom le nom du livre
	 */
	public JTableLivre(int parId, String parNom) {
		id = parId;
		nom = parNom;
	}
	
	/**
	 * Methode toString
	 * 
	 * @return le nom du livre
	 */
	public String toString() {
		return nom;
	}
	
	/**
	 * Methode pour obtenir l'id
	 * 
	 * @return l'id du livre
	 */
	public int getId() {
		return id;
	}
}
