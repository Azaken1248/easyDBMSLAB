import java.util.Set;
import java.util.List;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;
import javax.swing.table.DefaultTableModel;

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
        JTable resultsTable;
        DefaultTableModel tableModel;

        public static String[] getCols() {
                Object[] cols;
                String[] ret_cols;

                JSONReader reader = new JSONReader();

                String data = reader.readJsonFromFile("received_data.json");

                Set<String> cols_set = reader.getKeysFromJSON(data);

                cols = cols_set.toArray();
                ret_cols = new String[cols.length];

                for (int i = 0; i < cols.length; i++) {
                        String col = cols[i].toString();
                        ret_cols[i] = col;
                }

                return ret_cols;
        }

        public static Object[][] getData(String[] cols) {
                Object[][] dataVals = null;

                JSONReader reader = new JSONReader();

                String data = reader.readJsonFromFile("received_data.json");

                List<Object> vals = reader.getArrayValues(data, cols[0]);

                dataVals = new Object[cols.length][vals.size()];

                for (int i = 0; i < cols.length; i++) {
                        vals = reader.getArrayValues(data, cols[i]);
                        for (int j = 0; j < vals.size(); j++) {
                                String check = vals.get(j).toString();
                                check = check.replace("[", "").replace("]", "");
                                check = check.replace("\"", "");
                                check = check.replace(" ", "");
                                dataVals[i][j] = (Object) check;
                        }
                }

                return dataVals;
        }

        public static Object[][] transposeArray(Object[][] array) {
                int rows = array.length;
                int cols = array[0].length;

                Object[][] transposedArray = new Object[cols][rows];

                for (int i = 0; i < rows; i++) {
                        for (int j = 0; j < cols; j++) {
                                transposedArray[j][i] = array[i][j];
                        }
                }

                return transposedArray;
        }

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
                footer.setBorder(BorderFactory.createEmptyBorder(120, 90, 10, 90));
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
                queryField.setBorder(BorderFactory.createCompoundBorder(new EmptyBorder(10, 10, 0, 10),
                                new LineBorder(Color.white, 3, true)));
                queryField.setFont(new Font("Calibri", 0, 20));

                executeButton = new JButton("           Execute           ");
                executeButton.setFocusable(false);
                executeButton.setBackground(Color.black);
                executeButton.setForeground(Color.white);
                executeButton.setSize(new Dimension(100, 100));
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

                                String[] cols = getCols();
                                Object[][] data = getData(cols);

                                data = transposeArray(data);

                                new ResultsWindow(cols, data);

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
                        executeButton.setBackground(Color.black);
                        executeButton.setForeground(Color.white);
                }
        }
}
