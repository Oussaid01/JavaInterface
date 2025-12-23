import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class CalendrierAct extends JPanel implements ActionListener {

    JButton buttonRetour;
    JTable table;
    JScrollPane scrollPane;
    static DefaultTableModel model;
    JLabel titleLabel;

    public CalendrierAct() {
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));

                // ===== TITLE =====
        titleLabel = new JLabel("Calendrier Activités");
        titleLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        titleLabel.setFont(new Font("Segoe UI", Font.BOLD, 18));
        titleLabel.setForeground(Color.BLUE);

        add(Box.createVerticalStrut(15));
        add(titleLabel);
        add(Box.createVerticalStrut(20));

        // ===== TABLE =====
        String[] columns = {"Activité", "Date"};
        model = new DefaultTableModel(columns, 0);
        table = new JTable(model);


        table.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        table.setRowHeight(26);
        table.getTableHeader().setFont(new Font("Segoe UI", Font.BOLD, 14));

        scrollPane = new JScrollPane(table);
        scrollPane.setAlignmentX(Component.CENTER_ALIGNMENT);
        scrollPane.setPreferredSize(new Dimension(450, 220));

        add(Box.createVerticalStrut(15));
        add(scrollPane);
        add(Box.createVerticalStrut(15));

        // ===== BUTTON =====
        buttonRetour = new JButton("Retour");
        buttonRetour.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        buttonRetour.addActionListener(this);

        Dimension buttonSize = new Dimension(130, 25);
        buttonRetour.setPreferredSize(buttonSize);
        buttonRetour.setBackground(new Color(231, 76, 60)); //red

        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        buttonPanel.add(buttonRetour);
        add(buttonPanel);
    }

    static void loadActivitiesFromDB() {
        String sql = "SELECT activity, date FROM activities";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            model.setRowCount(0); // clear table

            while (rs.next()) {
                model.addRow(new Object[]{
                        rs.getString("activity"),
                        rs.getString("date")
                });
            }

        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null,
                    "Pas d'activités",
                    "Information",
                    JOptionPane.INFORMATION_MESSAGE);
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == buttonRetour) {
            Fenetre.switchPage("Bienvenue");
        }
    }
}
