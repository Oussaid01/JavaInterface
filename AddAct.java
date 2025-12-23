import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class AddAct extends JPanel implements ActionListener {

    JLabel l1, l2, l3, titleLabel;
    JButton buttonOk, buttonAnnuler;
    JTextField ActTextField, typeTextField, dateTextField;

    public AddAct() {
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));

        // ===== TITLE =====
        titleLabel = new JLabel("Ajouter Une Activité");
        titleLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        titleLabel.setFont(new Font("Segoe UI", Font.BOLD, 18));
        titleLabel.setForeground(new Color(45, 45, 45));
        titleLabel.setForeground(Color.BLUE);

        add(Box.createVerticalStrut(15));
        add(titleLabel);
        add(Box.createVerticalStrut(20));

        Font labelFont = new Font("Segoe UI", Font.PLAIN, 14);

        // ===== ACTIVITE =====
        l1 = new JLabel("Activité : ");
        l1.setFont(labelFont);
        ActTextField = new JTextField(20);
        ActTextField.setFont(labelFont);

        JPanel actPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        actPanel.add(l1);
        actPanel.add(ActTextField);

        // ===== TYPE =====
        l2 = new JLabel("Type : ");
        l2.setFont(labelFont);
        typeTextField = new JTextField(20);
        typeTextField.setFont(labelFont);

        JPanel typePanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        typePanel.add(l2);
        typePanel.add(typeTextField);

        // ===== DATE =====
        l3 = new JLabel("Date : ");
        l3.setFont(labelFont);
        dateTextField = new JTextField(20);
        dateTextField.setFont(labelFont);

        JPanel datePanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        datePanel.add(l3);
        datePanel.add(dateTextField);

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

        // ===== ADD COMPONENTS =====
        add(Box.createVerticalGlue());
        add(actPanel);
        add(typePanel);
        add(datePanel);
        add(Box.createVerticalStrut(20));
        add(buttonPanel);
    }

    @Override
    public void actionPerformed(ActionEvent e) {

        // ===== ANNULER =====
        if (e.getSource() == buttonAnnuler) {
            resetChamp();
            Fenetre.switchPage("Choix");
            return;
        }

        // ===== OK =====
        if (e.getSource() == buttonOk) {
            String activity = ActTextField.getText().trim();
            String type = typeTextField.getText().trim();
            String date = dateTextField.getText().trim();

            // Check mandatory fields
            if (activity.isEmpty() || type.isEmpty() || date.isEmpty()) {
                JOptionPane.showMessageDialog(this,
                        "Tous les champs sont obligatoires",
                        "Erreur",
                        JOptionPane.ERROR_MESSAGE);
                return;
            }

            String sql = "INSERT INTO activities (activity, type, date) VALUES (?, ?, ?)";

            try (Connection conn = DBConnection.getConnection();
                 PreparedStatement pstmt = conn.prepareStatement(sql)) {

                pstmt.setString(1, activity);
                pstmt.setString(2, type);
                pstmt.setString(3, date);
                pstmt.executeUpdate();

                JOptionPane.showMessageDialog(this,
                        "Activité ajoutée avec succès",
                        "Information",
                        JOptionPane.INFORMATION_MESSAGE);

                resetChamp();

            } catch (SQLException ex) {
                ex.printStackTrace();
                JOptionPane.showMessageDialog(this,
                        "Erreur lors de l'ajout de l'activité",
                        "Erreur",
                        JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    // ===== RESET FIELDS =====
    private void resetChamp() {
        ActTextField.setText("");
        typeTextField.setText("");
        dateTextField.setText("");
    }
}
