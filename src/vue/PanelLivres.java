package vue;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

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
		
		JTextField titreField = new JTextField(20);
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
		
		JTextField auteurField = new JTextField(20);
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
		
		JTable tableLivres = new JTable(new ModeleTableLivres());
		tableLivres.setRowHeight(25);
		
		JScrollPane scrollPane = new JScrollPane(tableLivres,
				ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED,
				ScrollPaneConstants.HORIZONTAL_SCROLLBAR_AS_NEEDED);
		
		listeLivre.add(scrollPane);
		
		add(listeLivre,BorderLayout.CENTER);
	}
	
	public void panelAjoutLivres(){
		PopupAjoutLivre popup = new PopupAjoutLivre();
	}
	
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
	
	@Override
    public void actionPerformed(ActionEvent e) {
		String action = e.getActionCommand();
		switch (action) {
		case "rchrTitre": break;
		case "rchrAuteur": break;
		case "lvrRetard": panelLivreRetard(); break;
		case "ajLivre":panelAjoutLivres(); break;
		}
	}
}
