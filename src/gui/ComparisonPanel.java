package gui;

import model.Process;
import metrics.MetricsCalculator;

import javax.swing.*;
import javax.swing.border.CompoundBorder;
import javax.swing.border.EmptyBorder;
import javax.swing.border.TitledBorder;
import java.awt.*;
import java.util.List;

public class ComparisonPanel extends JPanel {

    private JTextPane textPane;

    public ComparisonPanel() {
        setLayout(new BorderLayout());
        setBackground(Color.WHITE);
        
        TitledBorder titledBorder = BorderFactory.createTitledBorder(
                BorderFactory.createLineBorder(new Color(230, 235, 240), 1),
                " Comparative Scheduling Analysis "
        );
        titledBorder.setTitleFont(new Font("Segoe UI", Font.BOLD, 14));
        titledBorder.setTitleColor(new Color(41, 128, 185));
        setBorder(new CompoundBorder(new EmptyBorder(15, 20, 15, 20), titledBorder));

        textPane = new JTextPane();
        textPane.setContentType("text/html");
        textPane.setEditable(false);
        textPane.setBackground(new Color(250, 252, 255));

        JScrollPane scroll = new JScrollPane(textPane);
        scroll.setBorder(null);
        scroll.getVerticalScrollBar().setUnitIncrement(16);

        add(scroll, BorderLayout.CENTER);
    }

    public void setData(List<Process> rr, List<Process> sjfP, List<Process> sjfNP, int quantum) {
        double rrWT = MetricsCalculator.calculateAverageWT(rr);
        double sjfPWT = MetricsCalculator.calculateAverageWT(sjfP);
        double sjfNPWT = MetricsCalculator.calculateAverageWT(sjfNP);

        double rrRT = MetricsCalculator.calculateAverageRT(rr);
        double sjfPRT = MetricsCalculator.calculateAverageRT(sjfP);
        double sjfNPRT = MetricsCalculator.calculateAverageRT(sjfNP);

        StringBuilder html = new StringBuilder();
        html.append("<h2 style='color: #2980b9; margin-bottom: 0;'>📊 Comparison Analysis</h2>");
        html.append("<hr color='#ecf0f1'>");

        html.append("<b>🔹 Waiting & Response Time:</b><br>");

        // 🔷 Waiting Time (3 algorithms)
        if (sjfPWT <= rrWT && sjfPWT <= sjfNPWT)
            html.append("&bull; <i>SJF Preemptive (SRTF)</i> achieved the <b>lowest average waiting time</b>.<br>");
        else if (sjfNPWT <= rrWT)
            html.append("&bull; <i>SJF Non-Preemptive</i> achieved the <b>lowest average waiting time</b>.<br>");
        else
            html.append("&bull; <i>Round Robin</i> achieved the <b>lowest average waiting time</b>.<br>");


        // 🔷 Response Time (3 algorithms)
        if (sjfPRT <= rrRT && sjfPRT <= sjfNPRT)
            html.append("&bull; <i>SJF Preemptive (SRTF)</i> provided the <b>fastest response</b> to new processes.<br>");
        else if (rrRT <= sjfNPRT)
            html.append("&bull; <i>Round Robin</i> provided <b>better responsiveness</b> than SJF Non-Preemptive.<br>");
        else
            html.append("&bull; <i>SJF Non-Preemptive</i> showed slower response due to delayed execution.<br>");


        // =========================
        // 🔷 FAIRNESS (3 algorithms)
        // =========================
        double rrSpread = maxWT(rr) - minWT(rr);
        double sjfNPSpread = maxWT(sjfNP) - minWT(sjfNP);
        double sjfPSpread = maxWT(sjfP) - minWT(sjfP);

        html.append("<br><b>⚖️ Fairness & Distribution:</b><br>");

        if (rrSpread <= sjfNPSpread && rrSpread <= sjfPSpread) {
            html.append("&bull; <font color='#27ae60'><b>Round Robin</b></font> is the most fair; waiting times are evenly distributed.<br>");
        } else if (sjfNPSpread <= sjfPSpread) {
            html.append("&bull; <font color='#e67e22'><b>SJF Non-Preemptive</b></font> shows less fairness due to prioritizing short jobs.<br>");
        } else {
            html.append("&bull; <font color='#e67e22'><b>SJF Preemptive</b></font> may cause starvation for long processes.<br>");
        }


        // =========================
        // 🔷 QUANTUM
        // =========================
        html.append("<br><b>⏱ Quantum Effect (Q=" + quantum + "):</b><br>");

        if (quantum <= 2) {
            html.append("&bull; Low quantum: High responsiveness but potential <b>context-switch overhead</b>.<br>");
        } else if (quantum >= 6) {
            html.append("&bull; High quantum: Round Robin behaves similar to <b>FCFS</b>.<br>");
        } else {
            html.append("&bull; Balanced quantum: Good trade-off between fairness and efficiency.<br>");
        }


        // =========================
        // 🔷 FINAL RESULT
        // =========================
        String bestWT =
                (sjfPWT <= rrWT && sjfPWT <= sjfNPWT) ? "SJF Preemptive"
                : (sjfNPWT <= rrWT ? "SJF Non-Preemptive" : "Round Robin");

        html.append("<br><b>🏁 Final Result:</b><br>");
        html.append("&bull; Best Overall Waiting Time: <b>" + bestWT + "</b><br>");

        html.append("</body></html>");

        textPane.setText(html.toString());
        textPane.setCaretPosition(0);
    }

    private double maxWT(List<Process> list) {
        return list.stream().mapToDouble(Process::getWaitingTime).max().orElse(0);
    }

    private double minWT(List<Process> list) {
        return list.stream().mapToDouble(Process::getWaitingTime).min().orElse(0);
    }
}