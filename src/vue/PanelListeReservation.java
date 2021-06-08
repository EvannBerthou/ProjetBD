package vue;

import java.awt.BorderLayout;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

import modele.Livre;
import utils.Bouton;

/**
 * 
 * @author Evann 
 *
 */
public class PanelListeReservation extends JPanel {

    /**
     * initisalition du panel liste reservation
     * @param listeReservations liste des reservation 
     * @param listener gestion d'evenement 
     */
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
