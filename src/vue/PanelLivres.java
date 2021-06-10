package vue;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import javax.swing.*;
import javax.swing.border.EmptyBorder;

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
		cRecherche.weightx = 1;
		cRecherche.weighty = 1;
		
		JButton supprLivre = new JButton("Supprimer le Livre");
		cRecherche.gridx = 0;
		cRecherche.gridy = 0;
		cRecherche.anchor = GridBagConstraints.EAST;
		supprLivre.setPreferredSize(new Dimension(180, 30));
		supprLivre.setActionCommand("supLivre");
		supprLivre.addActionListener(this);
		recherche.add(supprLivre,cRecherche);
		
		JButton supprExemp = new JButton("Supprimer un exemplaire");
		cRecherche.gridx = 0;
		cRecherche.gridy = 1;
		cRecherche.anchor = GridBagConstraints.EAST;
		supprExemp.setPreferredSize(new Dimension(180, 30));
		supprExemp.setActionCommand("supExempl");
		supprExemp.addActionListener(this);
		recherche.add(supprExemp,cRecherche);
		
		JLabel titre = new JLabel("Titre ");
		cRecherche.gridx = 1;
		cRecherche.gridy = 0;
		cRecherche.anchor = GridBagConstraints.EAST;
		recherche.add(titre,cRecherche);
		
		titreField = new JTextField(20);
		cRecherche.gridx = 2;
		cRecherche.gridy = 0;
		cRecherche.anchor = GridBagConstraints.WEST;
		recherche.add(titreField,cRecherche);
		
		JLabel auteur = new JLabel("Auteur ");
		cRecherche.gridx = 3;
		cRecherche.gridy = 0;
		cRecherche.anchor = GridBagConstraints.EAST;
		recherche.add(auteur,cRecherche);
		
		auteurField = new JTextField(20);
		cRecherche.gridx = 4;
		cRecherche.gridy = 0;
		cRecherche.anchor = GridBagConstraints.WEST;
		recherche.add(auteurField,cRecherche);
		
		JButton auteurRecherche = new JButton("Rechercher");
		cRecherche.gridx = 4;
		cRecherche.gridy = 1;
		cRecherche.anchor = GridBagConstraints.EAST;
		auteurRecherche.setActionCommand("rchr");
		auteurRecherche.addActionListener(this);
		recherche.add(auteurRecherche,cRecherche);
		
		JButton livreRetard = new JButton("Livres en retard");
		cRecherche.gridx = 5;
		cRecherche.gridy = 0;
		cRecherche.anchor = GridBagConstraints.EAST;
		livreRetard.setPreferredSize(new Dimension(180, 30));
		livreRetard.setActionCommand("lvrRetard");
		livreRetard.addActionListener(this);
		recherche.add(livreRetard,cRecherche);
		
		JButton livreAjout = new JButton("Ajouter un livre");
		cRecherche.gridx = 5;
		cRecherche.gridy = 1;
		cRecherche.anchor = GridBagConstraints.EAST;
		livreAjout.setPreferredSize(new Dimension(180, 30));
		livreAjout.setActionCommand("ajLivre");
		livreAjout.addActionListener(this);
		recherche.add(livreAjout,cRecherche);
		
		recherche.setBorder(new EmptyBorder(10, 10, 0, 10));
		add(recherche,BorderLayout.NORTH);
		
		//Panel Liste *******************************************************************
		
		JPanel listeLivre = new JPanel(new BorderLayout());
		
		modeleLivre= new ModeleTableLivres(false);
		
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
		
		tableLivres = new JTable(new ModeleTableRetard());
		tableLivres.setRowHeight(25);
		
		JScrollPane scrollPane = new JScrollPane(tableLivres,
				ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED,
				ScrollPaneConstants.HORIZONTAL_SCROLLBAR_AS_NEEDED);
		
		popup.add(scrollPane,BorderLayout.CENTER);
		
		popup.setVisible(true);
        popup.setSize(500, 400);
	}
	
	/**
	 * Mezthode pour recharger la liste des livre
	 */
	public void reload() {
		ArrayList<JTableLivre> arrayTitres = new ArrayList<JTableLivre>();
		ArrayList<String> arrayAuteurs = new ArrayList<String>();
		ArrayList<Integer> arrayExemplaire = new ArrayList<Integer>();
		
		try {
			ResultSet rset = Connexion.executeQuery("SELECT * FROM livre order by titre asc");
			while(rset.next()) {
				arrayTitres.add(new JTableLivre(rset.getInt(1),rset.getString(2)));
				arrayAuteurs.add(rset.getString(3));
				arrayExemplaire.add(Livre.nbExemplaire(rset.getString(1), false));
			}
			rset.close();
		}catch(SQLException e) {
			System.out.println(e);	
		}
		
		JTableLivre[] titres = arrayTitres.toArray(new JTableLivre[0]);
		String[] auteurs = arrayAuteurs.toArray(new String[0]);
		Integer[] exemplaire = arrayExemplaire.toArray(new Integer[0]);
		modeleLivre.setAllValue(titres,auteurs,exemplaire);
		
	}
	
	/**
	 * Methode qui met a jour la liste de livre en fonction de la recherche par auteur
	 */
	public void recherche() {
		ArrayList<JTableLivre> arrayTitres = new ArrayList<JTableLivre>();
		ArrayList<String> arrayAuteurs = new ArrayList<String>();
		ArrayList<Integer> arrayExemplaire = new ArrayList<Integer>();
		
		try {
			ResultSet rset = Connexion.executeQuery("SELECT * FROM Livre WHERE lower(auteur) LIKE lower(?) AND lower(titre) LIKE lower(?) ORDER BY titre ASC", new String[] { "%" + auteurField.getText() + "%", "%" + titreField.getText() + "%"});
			while(rset.next()) {
				arrayTitres.add(new JTableLivre(rset.getInt(1),rset.getString(2)));
				arrayAuteurs.add(rset.getString(3));
			}			
			rset.close();
			for (JTableLivre livre : arrayTitres) {
				ResultSet exmplaire = Connexion.executeQuery("SELECT count(id_ex) FROM exemplaire WHERE id_liv = ? ", new String[] { String.valueOf(livre.getId()) });
				if (exmplaire.next()) {
					arrayExemplaire.add(exmplaire.getInt(1));
				}
				exmplaire.close();
			}
		}catch(SQLException e) {
			System.out.println(e);	
		}
		
		JTableLivre[] titres = arrayTitres.toArray(new JTableLivre[0]);
		String[] auteurs = arrayAuteurs.toArray(new String[0]);
		Integer[] exemplaire = arrayExemplaire.toArray(new Integer[0]);
		modeleLivre.setAllValue(titres,auteurs,exemplaire);

		auteurField.setText("");
		titreField.setText("");
	}
	
	/**
	 * Methode pour supprimer un livre
	 */
	public void suppimerLivre() {
		if(tableLivres.getSelectedRowCount() == 0) {
			return;
		}
		int row = tableLivres.getSelectedRow();
		
		JTableLivre livre = (JTableLivre) tableLivres.getValueAt(row,0);
		
		try {
			ResultSet rset = Connexion.executeQuery("SELECT id_ex FROM exemplaire WHERE id_liv=" + livre.getId());
			while(rset.next()) {
				Connexion.executeUpdate("DELETE FROM emprunt WHERE id_ex= ?", new String[] {rset.getString(1)});
			}
			Connexion.executeUpdate("DELETE FROM exemplaire WHERE id_liv = ?", new String[]  {String.valueOf(livre.getId())});
			Connexion.executeUpdate("DELETE FROM livre WHERE id_liv = ?", new String[] { String.valueOf(livre.getId())});
			rset.close();
		}catch(Exception e) {
		}
		
		reload();
		
	}
	
	/**
	 * Methode pour supprimer un exemplaire
	 */
	public void suppimerExemplaire() {
		if(tableLivres.getSelectedRowCount() == 0) {
			return;
		}
		int row = tableLivres.getSelectedRow();
		JTableLivre livre = (JTableLivre) tableLivres.getValueAt(row,0);

		new PopupSupprExemplaire(livre.getId());
	}
	
	@Override
    public void actionPerformed(ActionEvent e) {
		String action = e.getActionCommand();
		switch (action) {
		case "rchr": recherche(); break;
		case "lvrRetard": panelLivreRetard(); break;
		case "ajLivre": panelAjoutLivres(); break;
		case "supLivre": suppimerLivre(); break;
		case "supExempl": suppimerExemplaire(); break;
		}
	}
}
