package vue;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

import modele.Etudiant;
import modele.Livre;
import modele.LivreRenderer;
import modele.UpdateListe;
import utils.LivresEtudiants;

/**
 * 
 * @author Benjamin
 *
 */
public class PanelEtudiant extends JPanel implements ActionListener, UpdateListe {
    private static final long serialVersionUID = -2386593521069160406L;

    /**
     * Jbutton de reservation
     */
    JButton reserverBouton = new JButton("R�server un livre");; 
    /**
     * Jbuton de suppression de r�servation 
     */
    JButton supprimerBouton = new JButton("Supprimer r�servation");
    /**
     * �tudiant connect�
     */
    Etudiant etu;
    
    /**
     * liste des emprunt de l'�tudiant 
     */
    JList<Livre> listeEmprunts = new JList<Livre>();
    /**
     * Liste des r�servation de l'�tudiant
     */
    JList<Livre> listeReservations = new JList<Livre>();

    
    /**
     * initialisation du panel etudiant 
     * @param _etu �tudiant conn�ct�
     */
    public PanelEtudiant(Etudiant _etu) {
        this.etu = _etu;
        setLayout(new GridLayout(1,2));


        // Emprunts
        JPanel panelEmprunts = new JPanel(new BorderLayout());
        listeEmprunts.setCellRenderer(new LivreRenderer());
        JScrollPane scrollEmprunts = new JScrollPane(listeEmprunts);
        panelEmprunts.add(new JLabel("Livres emprunt�s"), BorderLayout.NORTH);
        panelEmprunts.add(scrollEmprunts, BorderLayout.CENTER);

        // Reservations
        JPanel panelReservation = new JPanel(new BorderLayout());
        JPanel boutonsSud = new JPanel(new GridLayout(1,2));
        reserverBouton.addActionListener(this);
        supprimerBouton.addActionListener(this);
        boutonsSud.add(reserverBouton);
        boutonsSud.add(supprimerBouton);
        JScrollPane scrollReservation = new JScrollPane(listeReservations);
        panelReservation.add(new JLabel("Livres r�serv�s"), BorderLayout.NORTH);
        panelReservation.add(scrollReservation, BorderLayout.CENTER);
        panelReservation.add(boutonsSud, BorderLayout.SOUTH);

        mettreAJourLivres();

        add (panelEmprunts);
        add (panelReservation);
    }

    /**
     * mise a jour de l'affichage des livres 
     */
    public void mettreAJourLivres() {
        Livre[] emprunts = LivresEtudiants.getLivresEmpruntEtudiants(etu);
        Livre[] reservations = LivresEtudiants.getLivresReserveEtudiants(etu);
        
        DefaultListModel<Livre> model = new DefaultListModel<Livre>();
        for (Livre emprunt : emprunts) {
            model.addElement(emprunt);
        }
        
        listeEmprunts.setModel(model);
    
        model = new DefaultListModel<Livre>();
        for (Livre reserv : reservations) {
            model.addElement(reserv);
        }
        
        listeReservations.setModel(model);
    }

    /**
     * gestion des evenement 
     * @param e evenement 
     */
    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == reserverBouton) {
            new PopupAjoutReservation(etu, this);
        } else if (e.getSource() == supprimerBouton) {
            Livre livre = listeReservations.getSelectedValue();
            LivresEtudiants.supprimerReservation(etu, String.valueOf(livre.getId()));
            mettreAJourLivres();
        }
    }
}
