package gui;

import model.Process;
import scheduler.*;
import util.*;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.List;

public class MainFrame extends JFrame {

    private CardLayout cardLayout;
    private JPanel mainPanel;

    private InputPanel inputPanel;
    private JPanel ganttPanel;

    private GanttChartPanel rrPanel;
    private GanttChartPanel sjfPPanel;
    private GanttChartPanel sjfNPPanel;

    private MetricsPanel metricsPanel;
    private ComparisonPanel comparisonPanel;

    private ReadyQueuePanel readyQueuePanel;

    private final Color PRIMARY_BLUE = new Color(44, 62, 80);
    private final Color NAV_BG = new Color(245, 247, 250);
    private final Color TEXT_DARK = new Color(44, 62, 80);
    private final Color HOVER_COLOR = new Color(41, 128, 185);
    private final Font MAIN_FONT = new Font("Segoe UI", Font.PLAIN, 14);
    private final Font BOLD_FONT = new Font("Segoe UI", Font.BOLD, 14);

    public MainFrame() {
        setTitle("CPU Scheduling Simulator");
        setMinimumSize(new Dimension(1050, 700));
        setPreferredSize(new Dimension(1050, 700));
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());
        getContentPane().setBackground(Color.WHITE);

        add(createTopContainer(), BorderLayout.NORTH);

        cardLayout = new CardLayout();
        mainPanel = new JPanel(cardLayout);
        mainPanel.setBackground(Color.WHITE);

        inputPanel = new InputPanel();
        inputPanel.setRunListener((p, q) -> runSimulation(p, q));
        mainPanel.add(inputPanel, "INPUT");

        ganttPanel = createGanttView();
        mainPanel.add(ganttPanel, "GANTT");

        metricsPanel = new MetricsPanel();
        mainPanel.add(metricsPanel, "METRICS");

        comparisonPanel = new ComparisonPanel();
        mainPanel.add(comparisonPanel, "COMPARE");

        add(mainPanel, BorderLayout.CENTER);
        pack();
    }

    private JPanel createTopContainer() {
        JPanel container = new JPanel(new BorderLayout());
        container.setBackground(Color.WHITE);
        container.add(createHeader(), BorderLayout.NORTH);
        container.add(createNavbar(), BorderLayout.CENTER);
        container.setBorder(BorderFactory.createMatteBorder(0, 0, 1, 0, new Color(220, 225, 230)));
        return container;
    }

    private JPanel createHeader() {
        JPanel panel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        panel.setBackground(PRIMARY_BLUE);
        
        panel.setBorder(new EmptyBorder(8, 20, 8, 20));

        JLabel title = new JLabel("CPU Scheduling Simulator");
        title.setForeground(Color.WHITE);
        title.setFont(new Font("Segoe UI", Font.BOLD, 20));

        panel.add(title);
        return panel;
    }

    private JPanel createNavbar() {
        
        JPanel panel = new JPanel(new FlowLayout(FlowLayout.CENTER, 30, 5));
        panel.setBackground(NAV_BG);
        panel.setBorder(new EmptyBorder(2, 0, 2, 0));

        panel.add(createNavButton("Input", "INPUT"));
        panel.add(createNavButton("Gantt Charts", "GANTT"));
        panel.add(createNavButton("Metrics", "METRICS"));
        panel.add(createNavButton("Comparison", "COMPARE"));

        return panel;
    }

    private JButton createNavButton(String text, String card) {
        JButton btn = new JButton(text);
        btn.setFocusPainted(false);
        btn.setBorderPainted(false);
        btn.setContentAreaFilled(false);
        btn.setForeground(TEXT_DARK);
        btn.setFont(BOLD_FONT);
        btn.setCursor(new Cursor(Cursor.HAND_CURSOR));
        
        btn.setBorder(BorderFactory.createEmptyBorder(5, 15, 5, 15));

        btn.addMouseListener(new MouseAdapter() {
            public void mouseEntered(MouseEvent e) { btn.setForeground(HOVER_COLOR); }
            public void mouseExited(MouseEvent e) { btn.setForeground(TEXT_DARK); }
        });

        btn.addActionListener(e -> cardLayout.show(mainPanel, card));
        return btn;
    }

    private JPanel createGanttView() {
        JPanel panel = new JPanel(new BorderLayout(10, 10));
        panel.setBackground(Color.WHITE);
        
        panel.setBorder(new EmptyBorder(10, 15, 5, 15));

        
        readyQueuePanel = new ReadyQueuePanel();
        
        readyQueuePanel.setPreferredSize(new Dimension(0, 90));
        readyQueuePanel.setBackground(Color.WHITE);
        panel.add(readyQueuePanel, BorderLayout.NORTH);

        
        JPanel ganttContainer = new JPanel();
        
        ganttContainer.setLayout(new GridLayout(3, 1, 0, 10));
        ganttContainer.setBackground(Color.WHITE);

        rrPanel = new GanttChartPanel("");
        sjfPPanel = new GanttChartPanel("");
        sjfNPPanel = new GanttChartPanel("");

        ganttContainer.add(createGanttRow("Round Robin", rrPanel));
        ganttContainer.add(createGanttRow("SJF Preemptive (SRTF)", sjfPPanel));
        ganttContainer.add(createGanttRow("SJF Non-Preemptive", sjfNPPanel));

        panel.add(ganttContainer, BorderLayout.CENTER);
        return panel;
    }

    private JPanel createGanttRow(String title, GanttChartPanel chartPanel) {
        JPanel row = new JPanel(new BorderLayout(10, 0));
        row.setBackground(Color.WHITE);
        
        row.setBorder(new EmptyBorder(5, 5, 5, 5));

        JLabel label = new JLabel("<html><b>" + title + "</b></html>");
        label.setPreferredSize(new Dimension(120, 30));
        label.setForeground(TEXT_DARK);

        JScrollPane scroll = wrapWithScroll(chartPanel);
        
        
        scroll.setPreferredSize(new Dimension(800, 135)); 

        row.add(label, BorderLayout.WEST);
        row.add(scroll, BorderLayout.CENTER);

        return row;
    }

    private JScrollPane wrapWithScroll(JPanel panel) {
        JScrollPane scroll = new JScrollPane(panel);
        scroll.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
        scroll.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_NEVER);
        scroll.getHorizontalScrollBar().setUnitIncrement(16);
        scroll.setBorder(BorderFactory.createLineBorder(new Color(235, 238, 242)));
        return scroll;
    }

    private void runSimulation(List<Process> processes, int quantum) {
        try {
            Validator.validateAll(processes, quantum);
            List<Process> rrList = Cloner.cloneProcessList(processes);
            List<Process> sjfPList = Cloner.cloneProcessList(processes);
            List<Process> sjfNPList = Cloner.cloneProcessList(processes);

            RRResult rrResult = RoundRobinScheduler.schedule(rrList, quantum);
            List<GanttRecord> rr = rrResult.gantt;
            List<GanttRecord> sjfP = SJFSchedulerPreemptive.schedule(sjfPList);
            List<GanttRecord> sjfNP = SJFSchedulerNonPreemptive.schedule(sjfNPList);

            rrPanel.setData(rr);
            sjfPPanel.setData(sjfP);
            sjfNPPanel.setData(sjfNP);

            readyQueuePanel.setQueue(rrResult.queueSnapshots);
            metricsPanel.setData(rrList, sjfPList, sjfNPList);
            comparisonPanel.setData(rrList, sjfPList, sjfNPList, quantum);
            
            cardLayout.show(mainPanel, "GANTT");
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, ex.getMessage(), "Simulation Error", JOptionPane.ERROR_MESSAGE);
        }
    }
}