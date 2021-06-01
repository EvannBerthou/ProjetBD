package vue;

import java.awt.BorderLayout;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.ScrollPaneConstants;

import modele.Etudiant;
import modele.ModeleTableLivres;

public abstract class PopupLivreEtudiant extends JDialog implements ActionListener {
    
    JTable tableLivres;
    
    public PopupLivreEtudiant(Etudiant etu) {
        if (etu == null) {
            JOptionPane.showMessageDialog(null, "Aucun étudiant sélectionné.", "Erreur", JOptionPane.INFORMATION_MESSAGE);
            return;
        }

        setLayout(new BorderLayout());

        JPanel panelNord = new JPanel();
        panelNord.add(new JLabel("Titre"));
        panelNord.add(new JTextField(20));
        panelNord.add(new JButton("Rechercher"));


        tableLivres = new JTable(new ModeleTableLivres(true));
        tableLivres.setRowHeight(25);
        tableLivres.setAutoCreateRowSorter(true);

        JScrollPane scrollPane = new JScrollPane(tableLivres,
                ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED,
                ScrollPaneConstants.HORIZONTAL_SCROLLBAR_AS_NEEDED);


        add(panelNord, BorderLayout.NORTH);
        add(scrollPane, BorderLayout.CENTER);
        JButton ajoutBouton = new JButton("Ajouter");
        ajoutBouton.addActionListener(this);
        add(ajoutBouton, BorderLayout.SOUTH);

        setVisible(true);
        setBackground(java.awt.Color.red);
        setSize(500, 400);
    }
}
