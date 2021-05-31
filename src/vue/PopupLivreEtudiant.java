package vue;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
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
import utils.LivresEtudiants;

public abstract class PopupLivreEtudiant extends JDialog implements ActionListener {
    
    JTable tableLivres;
    
    public PopupLivreEtudiant(PanelBibliothecaireEtudiant pbe) {
        Etudiant etu = pbe.getEtuSelectionne();
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
        /*
        ajoutBouton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int row = tableLivres.getSelectedRow();
                String titre = (String) tableLivres.getValueAt(row, 0);
                String auteur = (String) tableLivres.getValueAt(row, 1);

                Etudiant etu = pbe.getEtuSelectionne();
                    if (LivresEtudiants.ReserverLivre(etu, titre, auteur) == false) {
                        JOptionPane.showMessageDialog(null, etu.toString()
                        + " a déjà réservé 5 livres.", "Erreur", JOptionPane.INFORMATION_MESSAGE);
                }
                pbe.mettreAJoutEmpruntsReservations();
                dispose();
            }
        });*/
        add(ajoutBouton, BorderLayout.SOUTH);

        setVisible(true);
        setBackground(java.awt.Color.red);
        setSize(500, 400);
    }
}
