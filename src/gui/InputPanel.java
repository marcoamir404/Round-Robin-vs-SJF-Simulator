package gui;

import model.Process;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.List;

public class InputPanel extends JPanel {

    private JTable table;
    private DefaultTableModel model;
    private JTextField quantumField;
    private JTextField numProcessesField;
    
    
    private final Color BG_COLOR = new Color(245, 247, 250);
    private final Color TEXT_DARK = new Color(44, 62, 80);
    private final Color ACCENT_BLUE = new Color(44, 62, 80);
    private final Color ACCENT_BLUE_HOVER = new Color(41, 128, 185);
    private final Color SUCCESS_GREEN = new Color(46, 204, 113);
    private final Color SUCCESS_GREEN_HOVER = new Color(39, 174, 96);
    private final Color DANGER_RED = new Color(231, 76, 60);
    private final Color DANGER_RED_HOVER = new Color(192, 57, 43);
    private final Color BORDER_COLOR = new Color(220, 225, 230);
    
    
    public interface RunListener {
        void onRun(List<Process> processes, int quantum);
    }

    private RunListener runListener;

    public void setRunListener(RunListener listener) {
        this.runListener = listener;
    }

    public InputPanel() {
        setLayout(new BorderLayout(20, 20));
        setBorder(new EmptyBorder(25, 30, 25, 30));
        setBackground(BG_COLOR);

        add(createTopPanel(), BorderLayout.NORTH);
        add(createTablePanel(), BorderLayout.CENTER);
        add(createBottomPanel(), BorderLayout.SOUTH);
    }

    private JPanel createTopPanel() {
        JPanel mainTopPanel = new JPanel(new BorderLayout(0, 20));
        mainTopPanel.setOpaque(false);

        
        JLabel title = new JLabel("Process Input Configuration");
        title.setFont(new Font("Segoe UI", Font.BOLD, 24));
        title.setForeground(TEXT_DARK);

        
        JPanel controlsPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 15, 0));
        controlsPanel.setOpaque(false);

        JLabel numLabel = new JLabel("No. of Processes:");
        numLabel.setFont(new Font("Segoe UI", Font.BOLD, 14));
        numLabel.setForeground(TEXT_DARK);
        
        numProcessesField = createStyledTextField(5);

        JLabel qLabel = new JLabel("Time Quantum:");
        qLabel.setFont(new Font("Segoe UI", Font.BOLD, 14));
        qLabel.setForeground(TEXT_DARK);
        
        quantumField = createStyledTextField(5);

        JButton generateBtn = createHoverButton("Generate Table", SUCCESS_GREEN, SUCCESS_GREEN_HOVER);
        generateBtn.addActionListener(e -> generateProcesses());

        controlsPanel.add(numLabel);
        controlsPanel.add(numProcessesField);
        controlsPanel.add(qLabel);
        controlsPanel.add(quantumField);
        controlsPanel.add(generateBtn);

        mainTopPanel.add(title, BorderLayout.NORTH);
        mainTopPanel.add(controlsPanel, BorderLayout.CENTER);

        return mainTopPanel;
    }

    private JTextField createStyledTextField(int columns) {
        JTextField textField = new JTextField(columns);
        textField.setFont(new Font("Segoe UI", Font.PLAIN, 15));
        textField.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(BORDER_COLOR, 1, true),
                BorderFactory.createEmptyBorder(6, 10, 6, 10)
        ));
        return textField;
    }


    private JScrollPane createTablePanel() {

        String[] columns = {"Process ID", "Arrival Time", "Burst Time"};

        model = new DefaultTableModel(columns, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return column != 0; 
            }
        };

        table = new JTable(model);
        table.setRowHeight(35); 
        table.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        table.setShowVerticalLines(false);
        table.setGridColor(new Color(235, 235, 235));
        table.setIntercellSpacing(new Dimension(0, 0));
        table.setSelectionBackground(ACCENT_BLUE);
        table.setSelectionForeground(Color.WHITE);

        DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
        centerRenderer.setHorizontalAlignment(JLabel.CENTER);
        for (int i = 0; i < table.getColumnCount(); i++) {
            table.getColumnModel().getColumn(i).setCellRenderer(centerRenderer);
        }

        
        JTableHeader header = table.getTableHeader();
        header.setFont(new Font("Segoe UI", Font.BOLD, 15));
        header.setBackground(new Color(41, 128, 185)); 
        header.setForeground(Color.WHITE);
        header.setPreferredSize(new Dimension(100, 45));
        header.setBorder(BorderFactory.createEmptyBorder());

        JScrollPane scrollPane = new JScrollPane(table);
        scrollPane.getViewport().setBackground(Color.WHITE);
        scrollPane.setBorder(BorderFactory.createLineBorder(BORDER_COLOR, 1, true));
        
        return scrollPane;
    }


    private JPanel createBottomPanel() {
        JPanel panel = new JPanel(new FlowLayout(FlowLayout.RIGHT, 15, 0));
        panel.setOpaque(false);
        panel.setBorder(new EmptyBorder(10, 0, 0, 0));

        JButton clearBtn = createHoverButton("Clear Data", DANGER_RED, DANGER_RED_HOVER);
        JButton runBtn = createHoverButton("Run Simulation", ACCENT_BLUE, ACCENT_BLUE_HOVER);

        clearBtn.addActionListener(e -> clearTable());

        runBtn.addActionListener(e -> {
            try {
                List<Process> processes = getProcesses();
                int quantum = getQuantum();

                if (runListener != null) {
                    runListener.onRun(processes, quantum);
                }

            } catch (Exception ex) {
                showError(ex.getMessage());
            }
        });

        panel.add(clearBtn);
        panel.add(runBtn);

        return panel;
    }

  
    private JButton createHoverButton(String text, Color bgColor, Color hoverColor) {
        JButton btn = new JButton(text);
        btn.setFocusPainted(false);
        btn.setBorderPainted(false);
        btn.setOpaque(true);
        btn.setBackground(bgColor);
        btn.setForeground(Color.WHITE);
        btn.setFont(new Font("Segoe UI", Font.BOLD, 14));
        btn.setCursor(new Cursor(Cursor.HAND_CURSOR));
        btn.setBorder(new EmptyBorder(10, 20, 10, 20)); 

        btn.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                btn.setBackground(hoverColor);
            }

            @Override
            public void mouseExited(MouseEvent e) {
                btn.setBackground(bgColor);
            }
        });

        return btn;
    }


    private void generateProcesses() {
        try {
            int numProcesses = Integer.parseInt(numProcessesField.getText().trim());
            
            if (numProcesses <= 0) {
                showError("Number of processes must be greater than 0.");
                return;
            }

            model.setRowCount(0);

            for (int i = 1; i <= numProcesses; i++) {
                model.addRow(new Object[]{"P" + i, "", ""});
            }
            
        } catch (NumberFormatException ex) {
            showError("Please enter a valid integer for the number of processes.");
        }
    }
    

    private void clearTable() {
        model.setRowCount(0);
        numProcessesField.setText("");
        quantumField.setText("");
    }

    
    public List<Process> getProcesses() {
        List<Process> processes = new ArrayList<>();

        for (int i = 0; i < model.getRowCount(); i++) {
            String id = model.getValueAt(i, 0).toString();

            try {
                int at = Integer.parseInt(model.getValueAt(i, 1).toString().trim());
                int bt = Integer.parseInt(model.getValueAt(i, 2).toString().trim());

                processes.add(new Process(id, at, bt));

            } catch (Exception e) {
                throw new IllegalArgumentException("Invalid or missing input in row for " + id);
            }
        }

        return processes;
    }

    public int getQuantum() {
        try {
            return Integer.parseInt(quantumField.getText().trim());
        } catch (Exception e) {
            throw new IllegalArgumentException("Invalid time quantum value.");
        }
    }


    private void showError(String message) {
        UIManager.put("OptionPane.messageFont", new Font("Segoe UI", Font.PLAIN, 14));
        JOptionPane.showMessageDialog(this, message, "Input Error", JOptionPane.ERROR_MESSAGE);
    }
}