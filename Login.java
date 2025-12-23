import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.sql.*;

public class Login extends JPanel implements ActionListener {

    JLabel l1, l2, titleLabel;
    JButton buttonOk, buttonAnnuler;
    JTextField userTextField;
    JPasswordField passTextField;
    static String userName;

    public Login() {
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));

        // ===== TITLE =====
        titleLabel = new JLabel("Login");
        titleLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        titleLabel.setFont(new Font("Segoe UI", Font.BOLD, 18));
        titleLabel.setForeground(new Color(45, 45, 45));
        titleLabel.setForeground(Color.BLUE);

        add(Box.createVerticalStrut(15));
        add(titleLabel);
        add(Box.createVerticalStrut(20));

        Font labelFont = new Font("Segoe UI", Font.PLAIN, 14);

        // ===== USER ROW =====
        l1 = new JLabel("User : ");
        l1.setFont(labelFont);
        userTextField = new JTextField(20);
        userTextField.setFont(labelFont);

        JPanel userPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        userPanel.add(l1);
        userPanel.add(userTextField);

        // ===== PASSWORD ROW =====
        l2 = new JLabel("Pass : ");
        l2.setFont(labelFont);
        passTextField = new JPasswordField(20);
        passTextField.setFont(labelFont);

        JPanel passPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        passPanel.add(l2);
        passPanel.add(passTextField);

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
        add(userPanel);
        add(passPanel);
        add(Box.createVerticalStrut(20));
        add(buttonPanel);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == buttonOk) {
            String username = userTextField.getText().trim();
            char[] passwordChars = passTextField.getPassword();
            String password = new String(passwordChars).trim();

            // Check for empty fields
            if (username.isEmpty() || password.isEmpty()) {
                JOptionPane.showMessageDialog(this,
                        "Tous les champs sont obligatoires",
                        "Erreur",
                        JOptionPane.ERROR_MESSAGE);
            } else {
                try (Connection conn = DBConnection.getConnection()) {
                    if (conn != null) {
                        String sql = "SELECT * FROM user WHERE username = ? AND password = ?";
                        PreparedStatement pstmt = conn.prepareStatement(sql);
                        pstmt.setString(1, username);
                        pstmt.setString(2, password);

                        ResultSet rs = pstmt.executeQuery();
                        if (rs.next()) {
                            // Login successful
                            userName = userTextField.getText().trim();
                            JOptionPane.showMessageDialog(this,
                                    "Connexion réussie",
                                    "Information",
                                    JOptionPane.INFORMATION_MESSAGE);
                            Fenetre.switchPage("Choix");
                        } else {
                            // Login failed
                            JOptionPane.showMessageDialog(this,
                                    "User ou Pass incorrect",
                                    "Erreur",
                                    JOptionPane.ERROR_MESSAGE);
                        }

                        rs.close();
                        pstmt.close();
                    } else {
                        JOptionPane.showMessageDialog(this,
                                "Impossible de se connecter à la base de données",
                                "Erreur",
                                JOptionPane.ERROR_MESSAGE);
                    }
                } catch (SQLException ex) {
                    ex.printStackTrace();
                    JOptionPane.showMessageDialog(this,
                            "Erreur lors de la vérification des identifiants",
                            "Erreur",
                            JOptionPane.ERROR_MESSAGE);
                }
            }
        }

        if (e.getSource() == buttonAnnuler) {
            userTextField.setText("");
            passTextField.setText("");
            Bienvenue.ResetGr();
            Fenetre.switchPage("Bienvenue");
        }
    }
}
