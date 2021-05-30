package vue;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import javax.swing.*;
import javax.swing.border.EmptyBorder;

import constante.IdConnexion;
import modele.*;

/**Class de la page de recherche de livre pour le/la bibliothècaire
 * 
 * @author jules
 *
 */
public class PanelLivres extends JPanel implements ActionListener{
	
	/**
	 * Le tableau ou sont afficher les livres
	 */
	JTable tableLivres;
	
	/**
	 * Le Modele du tableau tableLibre
	 */
	ModeleTableLivres modeleLivre;
	
	/**
	 *  Le Textfiled pour la recherche par auteur
	 */
	JTextField auteurField;
	
	/**
	 *  Le Textfiled pour la recherche par titre
	 */
	JTextField titreField;
	
	/**
	 * Le constructeur de la classe PanelLivres
	 */
	public PanelLivres() {
		setLayout(new BorderLayout(20, 20));
		
		//Panel Recherche ***************************************************************
		
		JPanel recherche = new JPanel(new GridBagLayout());
		GridBagConstraints cRecherche = new GridBagConstraints();
		
		JLabel titre = new JLabel("Titre ");
		cRecherche.gridx = 0;
		cRecherche.gridy = 0;
		cRecherche.anchor = GridBagConstraints.EAST;
		cRecherche.weightx = 1;
		cRecherche.weighty = 1;
		recherche.add(titre,cRecherche);
		
		titreField = new JTextField(20);
		cRecherche.gridx = 1;
		cRecherche.gridy = 0;
		cRecherche.anchor = GridBagConstraints.WEST;
		cRecherche.weightx = 1;
		cRecherche.weighty = 1;
		recherche.add(titreField,cRecherche);
		
		JButton titreRecherche = new JButton("Rechercher");
		cRecherche.gridx = 1;
		cRecherche.gridy = 1;
		cRecherche.anchor = GridBagConstraints.WEST;
		cRecherche.weightx = 1;
		cRecherche.weighty = 1;
		titreRecherche.setActionCommand("rchrTitre");
		titreRecherche.addActionListener(this);
		recherche.add(titreRecherche,cRecherche);
		
		JLabel auteur = new JLabel("Auteur ");
		cRecherche.gridx = 2;
		cRecherche.gridy = 0;
		cRecherche.anchor = GridBagConstraints.EAST;
		cRecherche.weightx = 1;
		cRecherche.weighty = 1;
		recherche.add(auteur,cRecherche);
		
		auteurField = new JTextField(20);
		cRecherche.gridx = 3;
		cRecherche.gridy = 0;
		cRecherche.anchor = GridBagConstraints.WEST;
		cRecherche.weightx = 1;
		cRecherche.weighty = 1;
		recherche.add(auteurField,cRecherche);
		
		JButton auteurRecherche = new JButton("Rechercher");
		cRecherche.gridx = 3;
		cRecherche.gridy = 1;
		cRecherche.anchor = GridBagConstraints.WEST;
		cRecherche.weightx = 1;
		cRecherche.weighty = 1;
		auteurRecherche.setActionCommand("rchrAuteur");
		auteurRecherche.addActionListener(this);
		recherche.add(auteurRecherche,cRecherche);
		
		JButton livreRetard = new JButton("Livres en retard");
		cRecherche.gridx = 4;
		cRecherche.gridy = 0;
		cRecherche.anchor = GridBagConstraints.EAST;
		cRecherche.weightx = 1;
		cRecherche.weighty = 1;
		livreRetard.setPreferredSize(new Dimension(150, 30));
		livreRetard.setActionCommand("lvrRetard");
		livreRetard.addActionListener(this);
		recherche.add(livreRetard,cRecherche);
		
		JButton livreAjout = new JButton("Ajouter un livre");
		cRecherche.gridx = 4;
		cRecherche.gridy = 1;
		cRecherche.anchor = GridBagConstraints.EAST;
		cRecherche.weightx = 1;
		cRecherche.weighty = 1;
		livreAjout.setPreferredSize(new Dimension(150, 30));
		livreAjout.setActionCommand("ajLivre");
		livreAjout.addActionListener(this);
		recherche.add(livreAjout,cRecherche);
		
		recherche.setBorder(new EmptyBorder(10, 10, 0, 10));
		add(recherche,BorderLayout.NORTH);
		
		//Panel Liste *******************************************************************
		
		JPanel listeLivre = new JPanel(new BorderLayout());
		
		modeleLivre= new ModeleTableLivres();
		
		tableLivres = new JTable(modeleLivre);
		tableLivres.setRowHeight(25);
		tableLivres.setAutoCreateRowSorter(true); 
		
		JScrollPane scrollPane = new JScrollPane(tableLivres,
				ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED,
				ScrollPaneConstants.HORIZONTAL_SCROLLBAR_AS_NEEDED);
		
		listeLivre.add(scrollPane);
		
		add(listeLivre,BorderLayout.CENTER);
	}
	
	/**
	 * Methode pour instancier le popup AjoutLivre
	 */
	public void panelAjoutLivres(){
		PopupAjoutLivre popup = new PopupAjoutLivre();
	}
	
	/**
	 * Methode pour creer le popup LivreRetard
	 */
	public void panelLivreRetard(){
		JDialog popup = new JDialog();
		popup.setTitle("Ajouter un livres, ou des exemplaires");
		popup.setLayout(new BorderLayout());
		
		JTable tableLivres = new JTable(new ModeleTableRetard());
		tableLivres.setRowHeight(25);
		
		JScrollPane scrollPane = new JScrollPane(tableLivres,
				ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED,
				ScrollPaneConstants.HORIZONTAL_SCROLLBAR_AS_NEEDED);
		
		popup.add(scrollPane,BorderLayout.CENTER);
		
		popup.setVisible(true);
        popup.setSize(500, 400);
	}
	
	/**
	 * Methode qui met a jour la liste de livre en fonction de la recherche par titre
	 */
	public void rechercheTitre() {
		ArrayList<String> arrayTitres = new ArrayList<String>();
		ArrayList<String> arrayAuteurs = new ArrayList<String>();
		ArrayList<Integer> arrayExemplaire = new ArrayList<Integer>();
		
		try {
			ResultSet result = Connexion.executeQuery("SELECT * FROM Livre WHERE titre LIKE '%" + titreField.getText() + "%' ORDER BY titre ASC");
			while(result.next()) {
				arrayTitres.add(result.getString(2));
				arrayAuteurs.add(result.getString(3));
				ResultSet exmplaire = Connexion.executeQuery("SELECT count(id_ex) FROM exemplaire WHERE id_liv ="+result.getString(1));
				arrayExemplaire.add(exmplaire.getInt(1));
			}
			
		}catch(SQLException e) {
			System.out.println(e);	
		}
		
		String[] titres = arrayTitres.toArray(new String[0]);
		String[] auteurs = arrayAuteurs.toArray(new String[0]);
		Integer[] exemplaire = arrayExemplaire.toArray(new Integer[0]);
		modeleLivre.setAllValue(titres,auteurs,exemplaire);
		
		titreField.setText("");
	}
	
	/**
	 * Methode qui met a jour la liste de livre en fonction de la recherche par auteur
	 */
	public void rechercheAuteur() {
		ArrayList<String> arrayTitres = new ArrayList<String>();
		ArrayList<String> arrayAuteurs = new ArrayList<String>();
		ArrayList<Integer> arrayExemplaire = new ArrayList<Integer>();
		
		try {
			ResultSet result = Connexion.executeQuery("SELECT * FROM Livre WHERE auteur LIKE '%" + auteurField.getText() + "%' ORDER BY titre ASC");
			while(result.next()) {
				arrayTitres.add(result.getString(2));
				arrayAuteurs.add(result.getString(3));
				ResultSet exmplaire = Connexion.executeQuery("SELECT count(id_ex) FROM exemplaire WHERE id_liv ="+result.getString(1));
				arrayExemplaire.add(exmplaire.getInt(1));
			}
		}catch(SQLException e) {
			System.out.println(e);	
		}
		
		String[] titres = arrayTitres.toArray(new String[0]);
		String[] auteurs = arrayAuteurs.toArray(new String[0]);
		Integer[] exemplaire = arrayExemplaire.toArray(new Integer[0]);
		modeleLivre.setAllValue(titres,auteurs,exemplaire);

		auteurField.setText("");
	}
	
	@Override
    public void actionPerformed(ActionEvent e) {
		String action = e.getActionCommand();
		switch (action) {
		case "rchrTitre": rechercheTitre(); break;
		case "rchrAuteur": rechercheAuteur(); break;
		case "lvrRetard": panelLivreRetard(); break;
		case "ajLivre":panelAjoutLivres(); break;
		}
	}
}
