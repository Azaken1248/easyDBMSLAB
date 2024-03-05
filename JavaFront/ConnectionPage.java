import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;

import java.awt.*;
import java.awt.event.*;

import Connectionclient.Client;

public class ConnectionPage implements ActionListener, MouseListener {
        Dimension screenSize;
        ImageIcon icon;
        JFrame window;
        JPanel header, body, footer;
        JLabel welcomeLabel, descriptorLabel, hostLabel, userLabel, passLabel, conLabel;
        JTextField userField, hostField;
        JPasswordField passField;
        JButton connectButton;

        ConnectionPage() {
                icon = new ImageIcon("./Assets/icon.png");

                screenSize = Toolkit.getDefaultToolkit().getScreenSize();
                int screenWidth = (int) screenSize.getWidth();
                int screenHeight = (int) screenSize.getHeight();

                window = new JFrame("SQL App");
                window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                window.setIconImage(icon.getImage());
                window.setSize(screenWidth / 2, screenHeight / 2);
                window.setResizable(false);
                window.getContentPane().setBackground(Color.black);
                window.setLayout(new BorderLayout());
                window.setLocationRelativeTo(null);

                header = new JPanel();
                header.setBackground(Color.black);
                header.setBorder(BorderFactory.createEmptyBorder(30, 50, 30, 50));
                header.setLayout(new FlowLayout());

                body = new JPanel();
                body.setBackground(Color.black);
                body.setBorder(BorderFactory.createCompoundBorder(new EmptyBorder(10, 90, 10, 90),
                                new LineBorder(Color.white, 3, true)));
                body.setLayout(new FlowLayout());

                footer = new JPanel();
                footer.setBackground(Color.black);
                footer.setLayout(new FlowLayout());

                welcomeLabel = new JLabel("Welcome To SQL Connector");
                welcomeLabel.setBackground(Color.black);
                welcomeLabel.setForeground(Color.white);
                welcomeLabel.setFont(new Font("Calibri", 0, 32));

                descriptorLabel = new JLabel(
                                "                                Enter Connection Detail                                ");
                descriptorLabel.setBackground(Color.black);
                descriptorLabel.setForeground(Color.white);
                descriptorLabel.setBorder(new EmptyBorder(10, 10, 10, 10));
                descriptorLabel.setFont(new Font("Calibri", 0, 24));

                hostLabel = new JLabel("Hostname : ");
                hostLabel.setBackground(Color.black);
                hostLabel.setForeground(Color.white);
                hostLabel.setBorder(new EmptyBorder(10, 10, 10, 10));
                hostLabel.setFont(new Font("Calibri", 0, 20));

                userLabel = new JLabel("Username : ");
                userLabel.setBackground(Color.black);
                userLabel.setForeground(Color.white);
                userLabel.setBorder(new EmptyBorder(10, 10, 10, 10));
                userLabel.setFont(new Font("Calibri", 0, 20));

                passLabel = new JLabel("Password :  ");
                passLabel.setBackground(Color.black);
                passLabel.setForeground(Color.white);
                passLabel.setBorder(new EmptyBorder(10, 10, 10, 10));
                passLabel.setFont(new Font("Calibri", 0, 20));

                conLabel = new JLabel("");
                conLabel.setBackground(Color.black);
                conLabel.setForeground(Color.white);
                conLabel.setBorder(new EmptyBorder(10, 10, 10, 10));
                conLabel.setFont(new Font("Calibri", 0, 20));

                hostField = new JTextField(30);
                hostField.setBackground(Color.black);
                hostField.setForeground(Color.white);
                hostField.setCaretColor(Color.white);
                hostField.setBorder(BorderFactory.createCompoundBorder(new EmptyBorder(10, 10, 10, 10),
                                new LineBorder(Color.white, 3, true)));
                hostField.setFont(new Font("Calibri", 0, 20));

                userField = new JTextField(30);
                userField.setBackground(Color.black);
                userField.setForeground(Color.white);
                userField.setCaretColor(Color.white);
                userField.setBorder(BorderFactory.createCompoundBorder(new EmptyBorder(10, 10, 10, 10),
                                new LineBorder(Color.white, 3, true)));
                userField.setFont(new Font("Calibri", 0, 20));

                passField = new JPasswordField(30);
                passField.setBackground(Color.black);
                passField.setForeground(Color.white);
                passField.setCaretColor(Color.white);
                passField.setBorder(BorderFactory.createCompoundBorder(new EmptyBorder(10, 10, 30, 10),
                                new LineBorder(Color.white, 3, true)));
                passField.setFont(new Font("Calibri", 0, 20));

                connectButton = new JButton("           Connect           ");
                connectButton.setFocusable(false);
                connectButton.setBackground(Color.white);
                connectButton.setForeground(Color.black);
                connectButton.setBorder(BorderFactory.createCompoundBorder(new EmptyBorder(10, 10, 0, 10),
                                new LineBorder(Color.white, 2, true)));
                connectButton.setFont(new Font("Calibri", 0, 20));
                // connectButton.setSize(20, 20);

                connectButton.addMouseListener(this);
                connectButton.addActionListener(this);

                header.add(welcomeLabel);

                body.add(descriptorLabel);
                body.add(hostLabel);
                body.add(hostField);
                body.add(userLabel);
                body.add(userField);
                body.add(passLabel);
                body.add(passField);
                body.add(connectButton);

                footer.add(conLabel);

                window.add(header, BorderLayout.NORTH);
                window.add(body, BorderLayout.CENTER);
                window.add(footer, BorderLayout.SOUTH);

                window.setVisible(true);

        }

        @Override
        public void actionPerformed(ActionEvent e) {
                if (e.getSource() == connectButton) {
                        conLabel.setText("Connecting...");

                        String host, username, password;

                        host = hostField.getText();
                        username = userField.getText();
                        password = String.valueOf(passField.getPassword());

                        System.out.println("host: " + host + "\nusername: " + username + "\npassword: " + password);

                        int code = Client.connectToDB(host, username, password);

                        if (code != 200) {
                                conLabel.setText("Falied to Establish Connection (" + code + ")");
                                conLabel.setForeground(Color.red);
                        } else {
                                conLabel.setText("Connection Successful!");
                                conLabel.setForeground(Color.green);
                        }

                        /*
                         * if (conLabel.getText().equals("Connection Successful!")) {
                         * new QueryPage();
                         * window.dispose();
                         * }
                         */
                }
        }

        @Override
        public void mouseClicked(MouseEvent e) {
                if (e.getSource() == connectButton) {
                        connectButton.setBackground(Color.white);
                        connectButton.setForeground(Color.black);
                }
        }

        @Override
        public void mousePressed(MouseEvent e) {
                if (e.getSource() == connectButton) {
                        connectButton.setBackground(Color.white);
                        connectButton.setForeground(Color.black);
                }
        }

        @Override
        public void mouseReleased(MouseEvent e) {
                if (e.getSource() == connectButton) {
                        connectButton.setBackground(Color.white);
                        connectButton.setForeground(Color.black);
                }
        }

        @Override
        public void mouseEntered(MouseEvent e) {
                if (e.getSource() == connectButton) {
                        connectButton.setBackground(Color.white);
                        connectButton.setForeground(Color.black);
                }
        }

        @Override
        public void mouseExited(MouseEvent e) {
                if (e.getSource() == connectButton) {
                        connectButton.setBackground(Color.white);
                        connectButton.setForeground(Color.black);
                }
        }
}
