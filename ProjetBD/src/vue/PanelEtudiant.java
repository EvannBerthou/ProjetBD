package vue;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.GridLayout;

import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

public class PanelEtudiant extends JPanel {
	private static final long serialVersionUID = -2386593521069160406L;

	public PanelEtudiant() {
		setLayout(new GridLayout(1,2));
		JPanel emprunts = new JPanel(new BorderLayout());
		JList liste = new JList(new String[] {"test1", "test2"});
		JScrollPane scroll = new JScrollPane(liste);
		emprunts.add(new JLabel("Livres empruntés"), BorderLayout.NORTH);
		emprunts.add(scroll, BorderLayout.CENTER);
		
		
		JPanel reservations = new JPanel(new BorderLayout());
		JList liste2 = new JList(new String[] {"test1", "test2"});
		JScrollPane scroll2 = new JScrollPane(liste2);
		reservations.add(new JLabel("Livres réversés"), BorderLayout.NORTH);
		reservations.add(scroll2, BorderLayout.CENTER);

		add(emprunts);
		add(reservations);
	}
}
