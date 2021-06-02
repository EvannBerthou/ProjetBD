package vue;

import java.awt.BorderLayout;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

import modele.Livre;
import utils.Bouton;

public class PanelListeReservation extends JPanel {

    public PanelListeReservation(JList listeReservations, ActionListener listener) {
        setLayout(new BorderLayout());
        JScrollPane scrollReservations = new JScrollPane(listeReservations);
        add(Bouton.JLabelWithButton("Livres réservés", "lres", listener), BorderLayout.NORTH);
        add(scrollReservations, BorderLayout.CENTER);
        JButton supprimerReservBouton = new JButton("Supprimer");
        supprimerReservBouton.setActionCommand("supprimer-reservation");
        supprimerReservBouton.addActionListener(listener);
        add(supprimerReservBouton, BorderLayout.SOUTH);
    }
    
}
