import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.sql.*;

public class Inscription extends JPanel implements ActionListener {

    private JLabel l1, l2, l3, l4, l5, titleLabel;
    private JButton buttonOk, buttonAnnuler;
    private JTextField nomTextField, prenomTextField, emailTextField;
    private JPasswordField passTextField;
    private JComboBox<String> club;

    public Inscription() {
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));

        // ===== TITLE =====
        titleLabel = new JLabel("Inscription");
        titleLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        titleLabel.setFont(new Font("Segoe UI", Font.BOLD, 18));
        titleLabel.setForeground(new Color(45, 45, 45));
        titleLabel.setForeground(Color.BLUE);

        add(Box.createVerticalStrut(15));
        add(titleLabel);
        add(Box.createVerticalStrut(20));

        Font labelFont = new Font("Segoe UI", Font.PLAIN, 14);

        // ===== NOM =====
        JPanel nomPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        l1 = new JLabel("Nom : ");
        l1.setFont(labelFont);
        nomTextField = new JTextField(20);
        nomPanel.add(l1);
        nomPanel.add(nomTextField);

        // ===== PRENOM =====
        JPanel prenomPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        l2 = new JLabel("Prénom : ");
        l2.setFont(labelFont);
        prenomTextField = new JTextField(20);
        prenomPanel.add(l2);
        prenomPanel.add(prenomTextField);

        // ===== PASSWORD =====
        JPanel passPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        l3 = new JLabel("Mot de passe : ");
        l3.setFont(labelFont);
        passTextField = new JPasswordField(20);
        passPanel.add(l3);
        passPanel.add(passTextField);

        // ===== EMAIL =====
        JPanel emailPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        l4 = new JLabel("E-mail : ");
        l4.setFont(labelFont);
        emailTextField = new JTextField(20);
        emailPanel.add(l4);
        emailPanel.add(emailTextField);

        // ===== CLUB =====
        JPanel clubPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        l5 = new JLabel("Club : ");
        l5.setFont(labelFont);
        club = new JComboBox<>();

        try (Connection conn = DBConnection.getConnection();
             Statement stmt = conn.createStatement()) {

            ResultSet rs = stmt.executeQuery("SELECT name FROM club;");
            while (rs.next()) {
                club.addItem(rs.getString("name"));
            }
            rs.close();

        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this,
                    "Impossible de charger les clubs",
                    "Erreur",
                    JOptionPane.ERROR_MESSAGE);
        }

        clubPanel.add(l5);
        clubPanel.add(club);

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

        // ===== ADD TO MAIN PANEL =====
        add(nomPanel);
        add(prenomPanel);
        add(passPanel);
        add(emailPanel);
        add(clubPanel);
        add(Box.createVerticalStrut(20));
        add(buttonPanel);
    }

    @Override
    public void actionPerformed(ActionEvent e) {

        if (e.getSource() == buttonOk) {
            String nom = nomTextField.getText().trim();
            String prenom = prenomTextField.getText().trim();
            String pass = new String(passTextField.getPassword()).trim();
            String email = emailTextField.getText().trim();
            String clubName = (String) club.getSelectedItem();

            if (nom.isEmpty() || prenom.isEmpty() || pass.isEmpty() || email.isEmpty() || clubName == null) {
                JOptionPane.showMessageDialog(this,
                        "Tous les champs sont obligatoires",
                        "Erreur",
                        JOptionPane.ERROR_MESSAGE);
                return;
            }

            try (Connection conn = DBConnection.getConnection()) {

                String sql = "INSERT INTO user (username, password, email, club) VALUES (?, ?, ?, ?)";
                PreparedStatement pstmt = conn.prepareStatement(sql);

                pstmt.setString(1, nom + " " + prenom);
                pstmt.setString(2, pass);
                pstmt.setString(3, email);
                pstmt.setString(4, clubName);
                pstmt.executeUpdate();

                JOptionPane.showMessageDialog(this,
                        "Inscription réussie",
                        "Information",
                        JOptionPane.INFORMATION_MESSAGE);

                resetChamp();
                Fenetre.switchPage("Calendrier Activités");
                CalendrierAct.loadActivitiesFromDB();

            } catch (SQLException ex) {
                JOptionPane.showMessageDialog(this,
                        "Utilisateur existant",
                        "Erreur",
                        JOptionPane.ERROR_MESSAGE);
            }
        }

        if (e.getSource() == buttonAnnuler) {
            resetChamp();
            Fenetre.switchPage("Bienvenue");
        }
    }

    private void resetChamp() {
        nomTextField.setText("");
        prenomTextField.setText("");
        passTextField.setText("");
        emailTextField.setText("");
        if (club.getItemCount() > 0) club.setSelectedIndex(0);
    }
}
