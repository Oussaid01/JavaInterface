import javax.swing.*;
import java.awt.*;
import java.awt.event.*;


public class Fenetre extends JFrame implements ActionListener {

    static CardLayout cardLayout;
    static JPanel mainPanel;
    static Fenetre instance;

    public Fenetre() {
        instance = this; 

        this.setSize(600, 300);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setTitle("Bienvenue");

        cardLayout = new CardLayout();
        mainPanel = new JPanel(cardLayout);

        Bienvenue BienvenuePage = new Bienvenue();
        Login LoginPage = new Login();
        Inscription InscriptionPage = new Inscription();
        Choix ChoixPage = new Choix();
        AddAct AddActPage = new AddAct();
        Profile ProfilePage = new Profile();
        CalendrierAct CalendrierActPage = new CalendrierAct();

        mainPanel.add(BienvenuePage, "Bienvenue");
        mainPanel.add(LoginPage, "Login");
        mainPanel.add(InscriptionPage, "Inscription");
        mainPanel.add(ChoixPage, "Choix");
        mainPanel.add(AddActPage, "Add Activity");
        mainPanel.add(ProfilePage, "Profile");
        mainPanel.add(CalendrierActPage, "Calendrier Activités");

        this.setContentPane(mainPanel);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
    }

    public static void switchPage(String pageName) {
        cardLayout.show(mainPanel, pageName);   
        instance.setTitle(pageName);
        if (pageName.equals("Inscription") | pageName.equals("Add Activity")){
            instance.setSize(600, 420);
        }else if (pageName.equals("Profile")){
            instance.setSize(600, 340);
        }
        else{
            instance.setSize(600, 300);
        }
    }

    public static void main(String[] args) {
        DBInit.init();
        Fenetre f = new Fenetre();
        f.setVisible(true);
    }
}
