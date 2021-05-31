package vue;

import java.awt.BorderLayout;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

import modele.Livre;
import utils.Bouton;

public class PanelListeEmprunt extends JPanel {

    public PanelListeEmprunt(JList listeEmprunts, ActionListener listener) {
        setLayout(new BorderLayout());
        JScrollPane scrollEmprunts = new JScrollPane(listeEmprunts);
        add(Bouton.JLabelWithButton("Livres empruntés", "lemp", listener), BorderLayout.NORTH);
        add(scrollEmprunts, BorderLayout.CENTER);
        JButton supprimerEmpruntBouton = new JButton("Supprimer");
        supprimerEmpruntBouton.setActionCommand("supprimer-emprunt");
        supprimerEmpruntBouton.addActionListener(listener);
        add(supprimerEmpruntBouton, BorderLayout.SOUTH);
    }
    
}
