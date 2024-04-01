import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Objects;

public class GUIHome implements ActionListener {
    JFrame homeFrame = new JFrame("Westminster Shopping Manager");
    JLabel title1 = new JLabel("Westminster Shopping Manager");
    JLabel userIdLabel = new JLabel("User ID: ");
    JLabel passwordLabel = new JLabel("Password: ");
    JButton loginBtn = new JButton("LOGIN");
    JTextField userIdField = new JTextField();
    JPasswordField passwordField = new JPasswordField();
    HashMap<String,String> loginData = new HashMap<String,String>();
    ArrayList<User> userData = new ArrayList<>();
    ImageIcon bgImage = new ImageIcon("BgImegas/Bgimg1.png");
    JLabel bgImgPanel = new JLabel(bgImage);

    public GUIHome(){

    }

    public GUIHome(ArrayList<User> loginInformation, boolean showGUI){
        userData = loginInformation;
        homeFrame.setSize(800,600);
        homeFrame.setVisible(true);
        homeFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        homeFrame.setLocationRelativeTo(null);
        homeFrame.setLayout(null);
        homeFrame.setResizable(false);
        if (showGUI) {
            showGUI();
        }

        bgImgPanel.setSize(800,600);
        bgImgPanel.setLocation(0,0);


        title1.setBounds(160,80,500,40);
        title1.setFont(new Font("Arial",Font.BOLD,32));
        homeFrame.add(title1);

        userIdLabel.setBounds(100,220,80,30);
        homeFrame.add(userIdLabel);

        passwordLabel.setBounds(100,290,80,30);
        homeFrame.add(passwordLabel);


        loginBtn.setBounds(100,370,100,30);
        loginBtn.addActionListener(this);
        homeFrame.add(loginBtn);



        userIdField.setBounds(180,220,200,30);
        homeFrame.add(userIdField);


        passwordField.setBounds(180,290,200,30);
        homeFrame.add(passwordField);
        homeFrame.add(bgImgPanel);



    }
    public void showGUI(){
        homeFrame.setVisible(true);
    }
    private String NowLogged;

    public String getNowLogged() {
        return NowLogged;
    }

    public void setNowLogged(String nowLogged) {
        NowLogged = nowLogged;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        WestminsterShoppingManager manager = new WestminsterShoppingManager();
        if(e.getSource()==loginBtn){
            String userName = userIdField.getText();
            String password = String.valueOf(passwordField.getPassword());
            for(int i = 0; i < userData.size(); i++){
                if(userData.get(i).getUsername().equals(userName)){
                    if(userData.get(i).getPassword().equals(password)) {
                        userData.get(i).addLoginCount();
                        homeFrame.dispose();
                        setNowLogged(userName);
                        ProductPage productPage = new ProductPage(true);
                    }
                } else {

                }
            }
        }
    }
}
