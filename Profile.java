import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.*;
import java.sql.*;

public class Profile extends JPanel implements ActionListener {

    JButton buttonRetour;
    JTable table;
    JScrollPane scrollPane;
    static DefaultTableModel model;
    JLabel titleLabel;

    public Profile() {
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));

        // ===== TITLE =====
        titleLabel = new JLabel("Mon Profile");
        titleLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        titleLabel.setFont(new Font("Segoe UI", Font.BOLD, 26));
        titleLabel.setForeground(Color.BLUE);

        add(Box.createVerticalStrut(15));
        add(titleLabel);
        add(Box.createVerticalStrut(20));

        // ===== TABLE =====
        String[] columns = {"Information", "Valeur"};
        model = new DefaultTableModel(columns, 0);
        table = new JTable(model);

        table.setFont(new Font("Segoe UI", Font.PLAIN, 15));
        table.setRowHeight(30);
        table.getTableHeader().setFont(new Font("Segoe UI", Font.BOLD, 16));

        scrollPane = new JScrollPane(table);
        scrollPane.setAlignmentX(Component.CENTER_ALIGNMENT);
        scrollPane.setPreferredSize(new Dimension(460, 170));

        add(scrollPane);
        add(Box.createVerticalStrut(15));

        // ===== BUTTON =====
        buttonRetour = new JButton("Retour");
        buttonRetour.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        buttonRetour.addActionListener(this);
        Dimension buttonSize = new Dimension(130, 25);
        buttonRetour.setPreferredSize(buttonSize);
        buttonRetour.setBackground(new Color(231, 76, 60)); //red

        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        buttonPanel.add(buttonRetour);
        add(buttonPanel);
    }

    static void loadProfileFromDB() {
        model.setRowCount(0);

        if (Login.userName == null) {
            JOptionPane.showMessageDialog(null,
                    "Aucun utilisateur connecté",
                    "Erreur",
                    JOptionPane.ERROR_MESSAGE);
            return;
        }

        String sql = """
            SELECT username, email, club
            FROM user
            WHERE username = ?;
        """;

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, Login.userName);
            ResultSet rs = pstmt.executeQuery();

            if (rs.next()) {
                model.addRow(new Object[]{"Nom d'utilisateur", rs.getString("username")});
                model.addRow(new Object[]{"Email", rs.getString("email")});
                model.addRow(new Object[]{"Club", rs.getString("club")});
            }

            rs.close();

        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null,
                    "Erreur lors du chargement du profil",
                    "Erreur",
                    JOptionPane.ERROR_MESSAGE);
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == buttonRetour) {
            Fenetre.switchPage("Choix");
        }
    }
}
