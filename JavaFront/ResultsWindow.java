import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;
import javax.swing.table.TableModel;

import java.awt.*;
import java.awt.event.*;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class ResultsWindow implements ActionListener, MouseListener {
    /*
     * public static void main(String[] args) {
     * JSONReader reader = new JSONReader();
     * String data = reader.readJsonFromFile("received_data.json");
     * // System.out.println(data);
     * Set<String> d = reader.getKeysFromJSON(data);
     * List<Object> v = reader.getArrayValues(data, "Database");
     * System.out.println(d.toString());
     * System.out.println(v.toString());
     * }
     */

    Dimension screenSize;
    ImageIcon icon;
    JFrame window;
    JPanel headerPanel, tablePanel, exportPanel;
    JLabel headerLabel;
    JScrollPane tablePane;
    JTable resultsTable;
    JButton exportButton;

    private void writeTableDataToCSV(JTable table, File file) throws IOException {
        TableModel model = table.getModel();
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(file))) {
            // Write column headers
            for (int i = 0; i < model.getColumnCount(); i++) {
                bw.write(model.getColumnName(i));
                if (i < model.getColumnCount() - 1) {
                    bw.write(",");
                }
            }
            bw.newLine();

            // Write rows
            for (int i = 0; i < model.getRowCount(); i++) {
                for (int j = 0; j < model.getColumnCount(); j++) {
                    bw.write(model.getValueAt(i, j).toString());
                    if (j < model.getColumnCount() - 1) {
                        bw.write(",");
                    }
                }
                bw.newLine();
            }
        }
    }

    ResultsWindow(String[] cols, Object[][] data) {

        icon = new ImageIcon("./assets/icon.png");

        window = new JFrame("Results Fetched");
        window.setIconImage(icon.getImage());
        window.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        window.getContentPane().setBackground(Color.black);
        window.setLayout(new BorderLayout());
        window.setResizable(false);

        headerPanel = new JPanel();
        headerPanel.setBackground(Color.black);
        headerPanel.setBorder(BorderFactory.createEmptyBorder(30, 50, 20, 50));
        headerPanel.setLayout(new FlowLayout());

        tablePanel = new JPanel();
        tablePanel.setBackground(Color.black);
        tablePanel.setBorder(new EmptyBorder(0, 90, 10, 90));
        tablePanel.setLayout(new FlowLayout());

        exportPanel = new JPanel();
        exportPanel.setBackground(Color.black);
        exportPanel.setBorder(BorderFactory.createEmptyBorder(10, 90, 10, 90));
        exportPanel.setLayout(new FlowLayout());

        headerLabel = new JLabel("Results Fetched");
        headerLabel.setBackground(Color.black);
        headerLabel.setForeground(Color.white);
        headerLabel.setFont(new Font("Calibri", 0, 32));

        resultsTable = new JTable(data, cols);
        resultsTable.setBackground(Color.black);
        resultsTable.setForeground(Color.white);
        resultsTable.setEnabled(false);

        tablePane = new JScrollPane(resultsTable);
        tablePane.getViewport().setView(resultsTable);
        tablePane.getViewport().setBackground(null);

        exportButton = new JButton("                      Export  To CSV                      ");
        exportButton.setFocusable(false);
        exportButton.setBackground(Color.black);
        exportButton.setForeground(Color.white);
        exportButton.setSize(new Dimension(100, 100));
        exportButton.setBorder(BorderFactory.createCompoundBorder(new EmptyBorder(10, 10, 10, 10),
                new LineBorder(Color.white, 2, true)));
        exportButton.setFont(new Font("Calibri", 0, 20));

        exportButton.addActionListener(this);

        exportButton.addMouseListener(this);

        tablePanel.add(tablePane);

        exportPanel.add(exportButton);

        window.add(headerPanel, BorderLayout.NORTH);
        window.add(tablePanel, BorderLayout.CENTER);
        window.add(exportPanel, BorderLayout.SOUTH);

        headerPanel.add(headerLabel);

        window.pack();
        window.setVisible(true);

    }

    @Override
    public void actionPerformed(ActionEvent e) {
        JFileChooser fileChooser = new JFileChooser();
        int returnValue = fileChooser.showSaveDialog(null);
        if (returnValue == JFileChooser.APPROVE_OPTION) {
            File selectedFile = fileChooser.getSelectedFile();
            try {
                writeTableDataToCSV(resultsTable, selectedFile);
                JOptionPane.showMessageDialog(null, "Exported successfully!");
            } catch (IOException ex) {
                ex.printStackTrace();
                JOptionPane.showMessageDialog(null, "An error occurred during the export!");
            }
        }

    }

    @Override
    public void mouseClicked(MouseEvent e) {
        if (e.getSource() == exportButton) {
            exportButton.setBackground(Color.white);
            exportButton.setForeground(Color.black);
        }
    }

    @Override
    public void mousePressed(MouseEvent e) {
        if (e.getSource() == exportButton) {
            exportButton.setBackground(Color.white);
            exportButton.setForeground(Color.black);
        }
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        if (e.getSource() == exportButton) {
            exportButton.setBackground(Color.white);
            exportButton.setForeground(Color.black);
        }
    }

    @Override
    public void mouseEntered(MouseEvent e) {
        if (e.getSource() == exportButton) {
            exportButton.setBackground(Color.white);
            exportButton.setForeground(Color.black);
        }
    }

    @Override
    public void mouseExited(MouseEvent e) {
        if (e.getSource() == exportButton) {
            exportButton.setBackground(Color.black);
            exportButton.setForeground(Color.white);
        }
    }
    /*
     * public static void main(String[] args) {
     * new ResultsWindow("", null);
     * }
     */

}
