package utils;

import java.awt.BorderLayout;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class Bouton {

    public static JPanel JLabelWithButton(String label, String btnAction, ActionListener listener) {
        JPanel panel = new JPanel(new BorderLayout(30,30));
        panel.add(new JLabel(label), BorderLayout.WEST);
        JButton button = new JButton("+");
        button.setActionCommand(btnAction);
        button.addActionListener(listener);
        panel.add(button, BorderLayout.EAST);
        return panel;
    }
    
}
