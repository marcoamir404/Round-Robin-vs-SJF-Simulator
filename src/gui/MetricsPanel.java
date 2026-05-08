package gui;

import model.Process;
import metrics.MetricsCalculator;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.border.TitledBorder;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;

public class MetricsPanel extends JPanel {

    private JTable rrTable, sjfPTable, sjfNPTable;
    private JLabel rrAvg, sjfPAvg, sjfNPAvg;
    
    private final Color ACCENT_BLUE = new Color(52, 152, 219);
    private final Color BG_LIGHT = new Color(248, 250, 252);
    private final Color HEADER_COLOR = new Color(41, 128, 185);

    public MetricsPanel() {
        setLayout(new GridLayout(3, 1, 0, 10));
        setBackground(Color.WHITE);
        setBorder(new EmptyBorder(15, 20, 15, 20));

        rrTable = createStyledTable();
        sjfPTable = createStyledTable();
        sjfNPTable = createStyledTable();

        rrAvg = createAvgLabel();
        sjfPAvg = createAvgLabel();
        sjfNPAvg = createAvgLabel();

        add(createSection("Round Robin Performance", rrTable, rrAvg));
        add(createSection("SJF Preemptive (SRTF) Performance", sjfPTable, sjfPAvg));
        add(createSection("SJF Non-Preemptive Performance", sjfNPTable, sjfNPAvg));
    }

    private JTable createStyledTable() {
        String[] cols = {"Process ID", "Waiting Time (WT)", "Turnaround Time (TAT)", "Response Time (RT)"};
        DefaultTableModel model = new DefaultTableModel(cols, 0) {
            @Override
            public boolean isCellEditable(int row, int column) { return false; }
        };

        JTable table = new JTable(model);
        table.setRowHeight(22);
        table.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        table.getTableHeader().setFont(new Font("Segoe UI", Font.BOLD, 12));
        table.getTableHeader().setBackground(new Color(44, 62, 80));
        table.getTableHeader().setForeground(Color.WHITE);
        table.setSelectionBackground(new Color(232, 241, 250));
        
        DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
        centerRenderer.setHorizontalAlignment(JLabel.CENTER);
        for (int i = 0; i < table.getColumnCount(); i++) {
            table.getColumnModel().getColumn(i).setCellRenderer(centerRenderer);
        }

        return table;
    }

    private JLabel createAvgLabel() {
        JLabel label = new JLabel("Average metrics will appear here after simulation.");
        label.setFont(new Font("Segoe UI", Font.BOLD, 13));
        label.setForeground(new Color(44, 62, 80));
        label.setBorder(new EmptyBorder(5, 10, 5, 10));
        label.setOpaque(true);
        label.setBackground(new Color(236, 240, 241));
        return label;
    }

    private JPanel createSection(String title, JTable table, JLabel avgLabel) {
        JPanel panel = new JPanel(new BorderLayout(0, 5));
        panel.setBackground(Color.WHITE);
        
        TitledBorder tb = BorderFactory.createTitledBorder(
                BorderFactory.createLineBorder(new Color(230, 230, 230)), title);
        tb.setTitleFont(new Font("Segoe UI", Font.BOLD, 14));
        tb.setTitleColor(ACCENT_BLUE);
        panel.setBorder(tb);

        JScrollPane scroll = new JScrollPane(table);
        scroll.getViewport().setBackground(Color.WHITE);
        scroll.setBorder(BorderFactory.createLineBorder(new Color(240, 240, 240)));
        scroll.setPreferredSize(new Dimension(400, 100));

        panel.add(scroll, BorderLayout.CENTER);
        panel.add(avgLabel, BorderLayout.SOUTH);

        return panel;
    }

    public void setData(List<Process> rr, List<Process> sjfP, List<Process> sjfNP) {
        fillTable(rrTable, rr);
        fillTable(sjfPTable, sjfP);
        fillTable(sjfNPTable, sjfNP);

        rrAvg.setText(buildAvgText(rr));
        sjfPAvg.setText(buildAvgText(sjfP));
        sjfNPAvg.setText(buildAvgText(sjfNP));
    }

    private void fillTable(JTable table, List<Process> list) {
        DefaultTableModel model = (DefaultTableModel) table.getModel();
        model.setRowCount(0);

        for (Process p : list) {
            model.addRow(new Object[]{
                    p.getId(),
                    p.getWaitingTime(),
                    p.getTurnaroundTime(),
                    p.getResponseTime()
            });
        }
    }

    private String buildAvgText(List<Process> list) {
        double wt = MetricsCalculator.calculateAverageWT(list);
        double tat = MetricsCalculator.calculateAverageTAT(list);
        double rt = MetricsCalculator.calculateAverageRT(list);

        return String.format("<html><font color='#2980b9'>Average WT:</font> <b>%.2f</b> &nbsp;&nbsp;|&nbsp;&nbsp; " +
                             "<font color='#2980b9'>Average TAT:</font> <b>%.2f</b> &nbsp;&nbsp;|&nbsp;&nbsp; " +
                             "<font color='#2980b9'>Average RT:</font> <b>%.2f</b></html>", wt, tat, rt);
    }
}