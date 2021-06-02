package vue;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.ScrollPaneConstants;

import modele.Connexion;
import modele.ModeleTableExemplaires;

public class PopupSupprExemplaire extends JDialog implements ActionListener {

	/**
	 *Tableau des exmplaire
	 **/
	JTable tableLivresExemplaire;

	/**
	 * Bouton pour supprimer l'exemplaire choisie
	 **/
	JButton suppr;

	/**
	 * l'Id du livre dont on vuet supprimer l'exemplaire
	 */
	int id;

	/**
	 * Modele du tableau
	 */
	ModeleTableExemplaires modele;

	public PopupSupprExemplaire(int parId) {
		id = parId;
		setLayout(new BorderLayout());

		modele = new ModeleTableExemplaires(""+id);

		tableLivresExemplaire = new JTable(modele);
		tableLivresExemplaire.setRowHeight(25);
		tableLivresExemplaire.setAutoCreateRowSorter(true);

		JScrollPane scrollPane = new JScrollPane(tableLivresExemplaire,
				ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED,
				ScrollPaneConstants.HORIZONTAL_SCROLLBAR_AS_NEEDED);

		suppr = new JButton("Supprimer");
		suppr.addActionListener(this);
		suppr.setActionCommand("suppr");
		add(scrollPane,BorderLayout.CENTER);
		add(suppr,BorderLayout.SOUTH);

		setVisible(true);
		setSize(500, 400);
	}

	public void reload() {
		ArrayList<String> arrayExemplaire = new ArrayList<String>();

		try {
			ResultSet rset = Connexion.executeQuery("SELECT id_ex FROM exemplaire WHERE id_liv ="  + id +  " AND id_ex NOT IN (SELECT id_ex FROM emprunt)");
			if(rset == null) {
				return;
			}
			while(rset.next()) {
				arrayExemplaire.add(rset.getString(1));
			}
			rset.close();
		}catch(SQLException e) {
			System.out.println(e);  
		}

		String[] exemplaire = arrayExemplaire.toArray(new String[0]);
		modele.setAllValue(exemplaire);
	}

	public void supprimer(){
		if(tableLivresExemplaire.getSelectedRow() == -1){
			return;
		}
		try {
			Connexion.executeUpdate("DELETE FROM exemplaire WHERE id_ex = "+ tableLivresExemplaire.getValueAt( tableLivresExemplaire.getSelectedRow(),0));
		}catch(SQLException e) {
			System.out.println(e);	
		}
		reload();
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		String action = e.getActionCommand();
		switch (action) {
		case "suppr": supprimer(); break;
		}
	}
}
