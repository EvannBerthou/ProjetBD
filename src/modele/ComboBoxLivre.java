package modele;

/** Class pour pouvoir choisir les livres par titres mais obtenir leur id si necessaire 
 * 
 * @author jules
 *
 */
public class ComboBoxLivre{

	/**
	 * Titre du livre
	 */
	private String titre;
	/**
	 * Id du livre
	 */
	private int id;

	/**Constructeur de la classe
	 * 
	 * @param parTitre Titre du livre
	 * @param parId Id du livre
	 */
	public ComboBoxLivre(String parTitre, int parId) {
		titre = parTitre;
		id = parId;
	}
	
	/**Methode pour obtenir l'Id du livre
	 * 
	 * @return l'id
	 */
	public int getId() {
		return id;
	}

	/**
	 * Methode toString
	 */
	public String toString(){
		return titre;
	}
}