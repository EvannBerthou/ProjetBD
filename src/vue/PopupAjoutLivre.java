package vue;

import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JTextField;

public class PopupAjoutLivre extends JDialog implements ActionListener{
	
	public PopupAjoutLivre() {
		setTitle("Ajouter un livres, ou des exemplaires");
		setLayout(new GridBagLayout());
		
		GridBagConstraints c = new GridBagConstraints();
		
		JLabel nouveauLivre = new JLabel("Nouveau livre :");
		c.gridx = 0;
		c.gridy = 0;
		c.anchor = GridBagConstraints.EAST;
		c.weightx = 1;
		c.weighty = 1;
		add(nouveauLivre,c);
		
		JLabel titre = new JLabel("Titre ");
		c.gridx = 0;
		c.gridy = 1;
		c.anchor = GridBagConstraints.EAST;
		c.weightx = 1;
		c.weighty = 1;
		add(titre,c);
		
		JTextField titreField = new JTextField(20);
		c.gridx = 1;
		c.gridy = 1;
		c.anchor = GridBagConstraints.WEST;
		c.weightx = 1;
		c.weighty = 1;
		add(titreField,c);
		
		JLabel auteur = new JLabel("Auteur ");
		c.gridx = 0;
		c.gridy = 2;
		c.anchor = GridBagConstraints.EAST;
		c.weightx = 1;
		c.weighty = 1;
		add(auteur,c);
		
		JTextField auteurField = new JTextField(20);
		c.gridx = 1;
		c.gridy = 2;
		c.anchor = GridBagConstraints.WEST;
		c.weightx = 1;
		c.weighty = 1;
		add(auteurField,c);
		
		JLabel exemplaire = new JLabel("Nombre d'exemplaire ");
		c.gridx = 0;
		c.gridy = 3;
		c.anchor = GridBagConstraints.EAST;
		c.weightx = 1;
		c.weighty = 1;
		add(exemplaire,c);
		
		JTextField exmplaireField = new JTextField(8);
		c.gridx = 1;
		c.gridy = 3;
		c.anchor = GridBagConstraints.WEST;
		c.weightx = 1;
		c.weighty = 1;
		add(exmplaireField,c);
		
		JButton livreAjout = new JButton("Ajouter");
		c.gridx = 1;
		c.gridy = 4;
		c.anchor = GridBagConstraints.WEST;
		c.weightx = 1;
		c.weighty = 1;
		livreAjout.setActionCommand("lvrAjout");
		livreAjout.addActionListener(this);
		add(livreAjout,c);
		
		/* ****************************************************** */
		
		JLabel nExemplaire = new JLabel("Nouveaux exemplaires :");
		c.gridx = 0;
		c.gridy = 5;
		c.anchor = GridBagConstraints.EAST;
		c.weightx = 1;
		c.weighty = 1;
		add(nExemplaire,c);
		
		JLabel livres = new JLabel("Livres ");
		c.gridx = 0;
		c.gridy = 6;
		c.anchor = GridBagConstraints.EAST;
		c.weightx = 1;
		c.weighty = 1;
		add(livres,c);
		
		String[] livresListe = getAllLivre();
		JComboBox<String> livreField = new JComboBox<String>(livresListe);
		c.gridx = 1;
		c.gridy = 6;
		c.anchor = GridBagConstraints.WEST;
		c.weightx = 1;
		c.weighty = 1;
		livreField.setPreferredSize(new Dimension(200, 20));
		add(livreField,c);
		
		JLabel nombres = new JLabel("Nombre ");
		c.gridx = 0;
		c.gridy = 7;
		c.anchor = GridBagConstraints.EAST;
		c.weightx = 1;
		c.weighty = 1;
		add(nombres,c);
		
		JTextField nombresField = new JTextField(8);
		c.gridx = 1;
		c.gridy = 7;
		c.anchor = GridBagConstraints.WEST;
		c.weightx = 1;
		c.weighty = 1;
		add(nombresField,c);
		
		JButton ExmpAjout = new JButton("Ajouter");
		c.gridx = 1;
		c.gridy = 8;
		c.anchor = GridBagConstraints.WEST;
		c.weightx = 1;
		c.weighty = 1;
		ExmpAjout.setActionCommand("ExmpAjout");
		ExmpAjout.addActionListener(this);
		add(ExmpAjout,c);
		
		setVisible(true);
	    setSize(500, 400);
	}
	
	@Override
    public void actionPerformed(ActionEvent e) {
		
	}
	
	public String[] getAllLivre() {
		return new String[]{"Livre1","Livre2","Livre3","Livre4"};
	}
}
