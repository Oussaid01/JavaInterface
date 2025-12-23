import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class Bienvenue extends JPanel implements ActionListener {

    JRadioButton r1, r2;
    JButton buttonOk, buttonAnnuler;
    static ButtonGroup group;
    JLabel titleLabel;

    public Bienvenue() {
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));

        // ===== TITLE =====
        titleLabel = new JLabel("Bienvenue");
        titleLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        titleLabel.setFont(new Font("Segoe UI", Font.BOLD, 18));
        titleLabel.setForeground(new Color(45, 45, 45));
        titleLabel.setForeground(Color.BLUE);

        add(Box.createVerticalStrut(15));
        add(titleLabel);
        add(Box.createVerticalStrut(20));

        // ===== RADIO BUTTONS =====
        r1 = new JRadioButton("S'inscrire");
        r2 = new JRadioButton("Se connecter");

        r1.setFont(new Font("Segoe UI", Font.PLAIN, 16));
        r2.setFont(new Font("Segoe UI", Font.PLAIN, 16));

        group = new ButtonGroup();
        group.add(r1);
        group.add(r2);

        r1.setAlignmentX(Component.CENTER_ALIGNMENT);
        r2.setAlignmentX(Component.CENTER_ALIGNMENT);

        add(r1);
        add(Box.createVerticalStrut(10));
        add(r2);
        add(Box.createVerticalStrut(25));

        // ===== BUTTONS =====
        buttonOk = new JButton("OK");
        buttonAnnuler = new JButton("Annuler");

        Dimension buttonSize = new Dimension(160, 35);
        buttonOk.setPreferredSize(buttonSize);
        buttonAnnuler.setPreferredSize(buttonSize);
        buttonOk.setBackground(new Color(46, 204, 113)); //green
        buttonAnnuler.setBackground(new Color(231, 76, 60)); //red



        buttonOk.addActionListener(this);
        buttonAnnuler.addActionListener(this);

        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 15, 0));
        buttonPanel.add(buttonOk);
        buttonPanel.add(buttonAnnuler);

        add(buttonPanel);
    }

    static void ResetGr() {
        group.clearSelection();
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == buttonOk) {
            if (r1.isSelected()) {
                Fenetre.switchPage("Inscription");
            } else if (r2.isSelected()) {
                Fenetre.switchPage("Login");
            } else {
                JOptionPane.showMessageDialog(this,
                        "Aucune option choisie",
                        "Erreur",
                        JOptionPane.ERROR_MESSAGE);
            }
        }

        if (e.getSource() == buttonAnnuler) {
            ResetGr();
        }
    }
}
