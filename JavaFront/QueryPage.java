import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;

import Connectionclient.Client;

import java.awt.*;
import java.awt.event.*;

//import Connectionclient.Client;

public class QueryPage implements ActionListener, MouseListener {
        Dimension screenSize;
        ImageIcon icon;
        JFrame window;
        JPanel header, body, footer;
        JLabel welcomeLabel, descriptorLabel, queryLabel, displayLabel;
        JTextField queryField;
        JButton executeButton;

        QueryPage() {
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
                footer.setBorder(BorderFactory.createEmptyBorder(100, 90, 10, 90));
                footer.setLayout(new FlowLayout());

                welcomeLabel = new JLabel("Welcome To SQL Connector");
                welcomeLabel.setBackground(Color.black);
                welcomeLabel.setForeground(Color.white);
                welcomeLabel.setFont(new Font("Calibri", 0, 32));

                descriptorLabel = new JLabel(
                                "                                    Enter Query                                  ");
                descriptorLabel.setBackground(Color.black);
                descriptorLabel.setForeground(Color.white);
                descriptorLabel.setBorder(new EmptyBorder(10, 10, 10, 10));
                descriptorLabel.setFont(new Font("Calibri", 0, 24));

                queryLabel = new JLabel("Enter Query : ");
                queryLabel.setBackground(Color.black);
                queryLabel.setForeground(Color.white);
                queryLabel.setBorder(new EmptyBorder(10, 10, 10, 10));
                queryLabel.setFont(new Font("Calibri", 0, 20));

                displayLabel = new JLabel("");
                displayLabel.setBackground(Color.black);
                displayLabel.setForeground(Color.white);
                displayLabel.setBorder(new EmptyBorder(10, 10, 10, 10));
                displayLabel.setFont(new Font("Calibri", 0, 20));

                queryField = new JTextField(30);
                queryField.setBackground(Color.black);
                queryField.setForeground(Color.white);
                queryField.setCaretColor(Color.white);
                queryField.setBorder(BorderFactory.createCompoundBorder(new EmptyBorder(10, 10, 10, 10),
                                new LineBorder(Color.white, 3, true)));
                queryField.setFont(new Font("Calibri", 0, 20));

                executeButton = new JButton("           Execute           ");
                executeButton.setFocusable(false);
                executeButton.setBackground(Color.white);
                executeButton.setForeground(Color.black);
                executeButton.setBorder(BorderFactory.createCompoundBorder(new EmptyBorder(10, 10, 10, 10),
                                new LineBorder(Color.white, 2, true)));
                executeButton.setFont(new Font("Calibri", 0, 20));
                // executeButton.setSize(20, 20);

                executeButton.addMouseListener(this);
                executeButton.addActionListener(this);

                header.add(welcomeLabel);

                body.add(descriptorLabel);
                body.add(queryLabel);
                body.add(queryField);
                body.add(executeButton);

                footer.add(displayLabel);

                window.add(header, BorderLayout.NORTH);
                window.add(body, BorderLayout.CENTER);
                window.add(footer, BorderLayout.SOUTH);

                window.setVisible(true);

        }

        @Override
        public void actionPerformed(ActionEvent e) {
                if (e.getSource() == executeButton) {
                        displayLabel.setText("Executing...");

                        String query = queryField.getText();

                        int code = Client.sendQuery(query);

                        if (code != 200) {
                                displayLabel.setForeground(Color.red);
                                displayLabel.setText("Query execution Failed!! (CODE: " + code + ")");
                        } else {
                                displayLabel.setForeground(Color.green);
                                displayLabel.setText("Query Sent Succesfully!");
                        }

                        String results = Client.retrieveResults();
                        if (!(results.equals(""))) {
                                displayLabel.setForeground(Color.green);
                                displayLabel.setText("Query Executed Succesfully!");
                                System.out.println(results);
                        }

                }
        }

        @Override
        public void mouseClicked(MouseEvent e) {
                if (e.getSource() == executeButton) {
                        executeButton.setBackground(Color.white);
                        executeButton.setForeground(Color.black);
                }
        }

        @Override
        public void mousePressed(MouseEvent e) {
                if (e.getSource() == executeButton) {
                        executeButton.setBackground(Color.white);
                        executeButton.setForeground(Color.black);
                }
        }

        @Override
        public void mouseReleased(MouseEvent e) {
                if (e.getSource() == executeButton) {
                        executeButton.setBackground(Color.white);
                        executeButton.setForeground(Color.black);
                }
        }

        @Override
        public void mouseEntered(MouseEvent e) {
                if (e.getSource() == executeButton) {
                        executeButton.setBackground(Color.white);
                        executeButton.setForeground(Color.black);
                }
        }

        @Override
        public void mouseExited(MouseEvent e) {
                if (e.getSource() == executeButton) {
                        executeButton.setBackground(Color.white);
                        executeButton.setForeground(Color.black);
                }
        }
}
