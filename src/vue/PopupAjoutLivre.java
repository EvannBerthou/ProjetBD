package vue;

import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JSpinner;
import javax.swing.JTextField;
import javax.swing.SpinnerModel;
import javax.swing.SpinnerNumberModel;

import constante.IdConnexion;
import modele.ComboBoxLivre;
import modele.Connexion;

/**Class pour le popup d'ajout de livre et d'exemplaire
 * 
 * @author jules
 *
 */
public class PopupAjoutLivre extends JDialog implements ActionListener{

	/**
	 * TextField du titre du nouveau livre
	 */
	private JTextField titreField;

	/**
	 * TextField du nom de l'auteur du livre 
	 */
	private JTextField auteurField;

	/**
	 * TextField du nombre d'exemplaire du nouveu livre
	 */
	private JSpinner exemplaireField;

	/**
	 * TextField du nombre d'exemplaire a ajouter
	 */
	private JSpinner nombresField;

	/**
	 * ComboBox du choix du livre
	 */
	private JComboBox<ComboBoxLivre> livreField;

	/**
	 * Constructeur de la classe
	 */
	public PopupAjoutLivre() {
		setTitle("Ajouter un livres, ou des exemplaires");
		setLayout(new GridBagLayout());

		GridBagConstraints c = new GridBagConstraints();

		JLabel nouveauLivre = new JLabel("Nouveau livre :");
		c.gridx = 0;
		c.gridy = 0;
		c.anchor = GridBagConstraints.EAST;
		add(nouveauLivre,c);

		JLabel titre = new JLabel("Titre ");
		c.gridx = 0;
		c.gridy = 1;
		c.anchor = GridBagConstraints.EAST;
		add(titre,c);

		titreField = new JTextField(20);
		c.gridx = 1;
		c.gridy = 1;
		c.anchor = GridBagConstraints.WEST;
		add(titreField,c);

		JLabel auteur = new JLabel("Auteur ");
		c.gridx = 0;
		c.gridy = 2;
		c.anchor = GridBagConstraints.EAST;
		add(auteur,c);

		auteurField = new JTextField(20);
		c.gridx = 1;
		c.gridy = 2;
		c.anchor = GridBagConstraints.WEST;
		add(auteurField,c);

		JLabel exemplaire = new JLabel("Nombre d'exemplaire ");
		c.gridx = 0;
		c.gridy = 3;
		c.anchor = GridBagConstraints.EAST;
		add(exemplaire,c);

		SpinnerModel model = new SpinnerNumberModel(1,1,200,1); 
		exemplaireField = new JSpinner(model);
		c.gridx = 1;
		c.gridy = 3;
		c.anchor = GridBagConstraints.WEST;
		add(exemplaireField,c);

		JButton livreAjout = new JButton("Ajouter");
		c.gridx = 1;
		c.gridy = 4;
		c.anchor = GridBagConstraints.WEST;
		livreAjout.setActionCommand("lvrAjout");
		livreAjout.addActionListener(this);
		add(livreAjout,c);

		/* ****************************************************** */

		JLabel nExemplaire = new JLabel("Nouveaux exemplaires :");
		c.gridx = 0;
		c.gridy = 5;
		c.anchor = GridBagConstraints.EAST;
		add(nExemplaire,c);

		JLabel livres = new JLabel("Livres ");
		c.gridx = 0;
		c.gridy = 6;
		c.anchor = GridBagConstraints.EAST;
		add(livres,c);

		ComboBoxLivre[] livresListe = getAllLivre();
		livreField = new JComboBox<ComboBoxLivre>(livresListe);
		c.gridx = 1;
		c.gridy = 6;
		c.anchor = GridBagConstraints.WEST;
		livreField.setEditable(false);
		livreField.setPreferredSize(new Dimension(200, 20));
		add(livreField,c);

		JLabel nombres = new JLabel("Nombre ");
		c.gridx = 0;
		c.gridy = 7;
		c.anchor = GridBagConstraints.EAST;
		add(nombres,c);

		SpinnerModel model2 = new SpinnerNumberModel(1,1,200,1); 
		nombresField = new JSpinner(model2);
		c.gridx = 1;
		c.gridy = 7;
		c.anchor = GridBagConstraints.WEST;
		add(nombresField,c);

		JButton ExmpAjout = new JButton("Ajouter");
		c.gridx = 1;
		c.gridy = 8;
		c.anchor = GridBagConstraints.WEST;
		ExmpAjout.setActionCommand("ExmpAjout");
		ExmpAjout.addActionListener(this);
		add(ExmpAjout,c);

		setVisible(true);
		setSize(500, 400);
	}

	/**
	 * Methode pour ajouter un, ou plusieur exemplaire
	 */
	public void ajoutExemplaire() {
		int id = (livreField.getItemAt(livreField.getSelectedIndex())).getId();
		try {
			ResultSet max = Connexion.executeQuery("SELECT MAX(id_ex) FROM exemplaire");
			int index = max.getInt(1) + 1;
			for(int i=0; i<(int)nombresField.getValue();i++) {
				Connexion.executeUpdate("INSERT into exemplaire values(" + index + "," + id + ")");
				index++;
			}
		}catch(SQLException e) {
			System.out.println(e);
		}
		
		livreField.setSelectedIndex(0);
		nombresField.setValue(1);
	}

	/**
	 * Methode pour ajouter un livre
	 */
	public void ajoutLivre() {
		try {
			ResultSet max = Connexion.executeQuery("SELECT MAX(id_liv) FROM livre");
			int index = max.getInt(1) + 1;
			Connexion.executeUpdate("INSERT into livre values(" + index + ",'" + titreField.getText() + "','" + auteurField.getText() + "')");
			max = Connexion.executeQuery("SELECT MAX(id_ex) FROM exemplaire");
			int indexEx = max.getInt(1)+1;
			for(int i=0; i<(int)exemplaireField.getValue();i++) {
				Connexion.executeUpdate("INSERT into exemplaire values(" + indexEx + "," + index + ")");
				indexEx++;
			}
		}catch(SQLException e) {
			System.out.println(e);	
		}
		
		exemplaireField.setValue(1);
		titreField.setText("");
		auteurField.setText("");
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		String action = e.getActionCommand();
		switch (action) {
		case "ExmpAjout": ajoutExemplaire(); break;
		case "lvrAjout": ajoutLivre(); break;
		}
	}

	/**
	 * Methode pour obtenir la liste de tout les livre, pour les comboBox
	 * 
	 * @return la liste 
	 */
	public ComboBoxLivre[] getAllLivre() {
		ArrayList<ComboBoxLivre> livres = new ArrayList<ComboBoxLivre>();

		try {
			ResultSet result = Connexion.executeQuery("SELECT titre,id_liv FROM Livre order by titre asc");
			while(result.next()) {
				ComboBoxLivre element = new ComboBoxLivre(result.getString(1),result.getInt(2));
				livres.add(element);
			}

		}catch(SQLException e) {
			System.out.println(e);	
		}

		return livres.toArray(new ComboBoxLivre[0]);
	}
}
