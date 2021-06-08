package vue;

import java.awt.BorderLayout;
import java.awt.GridLayout;
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
public class PanelListeEmprunt extends JPanel {

    /**
     * initialisation du panel de la liste des emprunt 
     * @param listeEmprunts des étudiants
     * @param listener gestion d'évenement 
     */
    public PanelListeEmprunt(JList listeEmprunts, ActionListener listener) {
        setLayout(new BorderLayout());
        JScrollPane scrollEmprunts = new JScrollPane(listeEmprunts);
        add(Bouton.JLabelWithButton("Livres empruntés", "lemp", listener), BorderLayout.NORTH);
        add(scrollEmprunts, BorderLayout.CENTER);
        JButton supprimerEmpruntBouton = new JButton("Supprimer");
        supprimerEmpruntBouton.setActionCommand("supprimer-emprunt");
        supprimerEmpruntBouton.addActionListener(listener);
        JButton relanceBouton = new JButton("Relancer");
        relanceBouton.setActionCommand("relance");
        relanceBouton.addActionListener(listener);
        JPanel panelBoutons = new JPanel(new GridLayout(1,2));
        panelBoutons.add(relanceBouton);
        panelBoutons.add(supprimerEmpruntBouton);
        add(panelBoutons, BorderLayout.SOUTH);
    }
    
}
