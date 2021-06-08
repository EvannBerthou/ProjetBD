package modele;

import java.awt.Color;
import java.awt.Component;

import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.ListCellRenderer;

/**
 * 
 * @author Benjamin
 *
 */
public class LivreRenderer extends JLabel implements ListCellRenderer{

    /**
     * methode pour modifier l'affichage du livre dans la vue 
     * @param list Jlist
     * @param value valeur de l'objet selectioner 
     * @param index index de l'element selecioner 
     * @param isSelected boolean de selection
     * @param cellHasFocus boolean de posseion du focus
     * @return retourne un Component
     */
    @Override
    public Component getListCellRendererComponent(JList list, Object value, int index, boolean isSelected,
            boolean cellHasFocus) {
        Livre livre = (Livre)value;
        if (livre.getTempsRestant() < 0) {
            setForeground(Color.RED);
        } else {
            setForeground(Color.BLACK);
        }
        setText(livre.toString());
        return this;
    }


}
