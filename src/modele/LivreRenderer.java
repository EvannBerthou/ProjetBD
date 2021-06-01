package modele;

import java.awt.Color;
import java.awt.Component;

import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.ListCellRenderer;

public class LivreRenderer extends JLabel implements ListCellRenderer{

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
