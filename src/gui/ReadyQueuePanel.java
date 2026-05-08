package gui;

import javax.swing.*;
import javax.swing.border.TitledBorder;
import java.awt.*;
import java.util.List;

public class ReadyQueuePanel extends JPanel {

    private JPanel queueContainer;
    private final Color ACCENT_BLUE = new Color(52, 152, 219);
    private final Color SNAPSHOT_BG = new Color(248, 250, 252);
    private final Color TEXT_DARK = new Color(44, 62, 80);

    public ReadyQueuePanel() {
        setLayout(new BorderLayout());
        setBackground(Color.WHITE);
        
        
        TitledBorder titledBorder = BorderFactory.createTitledBorder(
                BorderFactory.createLineBorder(new Color(230, 235, 240), 1),
                " Ready Queue Progression (Round Robin Snapshots) "
        );
        titledBorder.setTitleFont(new Font("Segoe UI", Font.BOLD, 12));
        titledBorder.setTitleColor(ACCENT_BLUE);
        setBorder(titledBorder);

        queueContainer = new JPanel();
        queueContainer.setLayout(new FlowLayout(FlowLayout.LEFT, 15, 5));
        queueContainer.setBackground(Color.WHITE);

        JScrollPane scroll = new JScrollPane(queueContainer);
        scroll.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
        scroll.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_NEVER);
        scroll.setBorder(null); 
        scroll.getHorizontalScrollBar().setUnitIncrement(20);

        add(scroll, BorderLayout.CENTER);
    }

    public void setQueue(List<List<String>> snapshots) {
        queueContainer.removeAll();

        if (snapshots == null || snapshots.isEmpty()) {
            JLabel placeholder = new JLabel("Run simulation to see queue snapshots...");
            placeholder.setForeground(Color.LIGHT_GRAY);
            placeholder.setFont(new Font("Segoe UI", Font.ITALIC, 13));
            queueContainer.add(placeholder);
        } else {
            for (int i = 0; i < snapshots.size(); i++) {
                queueContainer.add(createSnapshotBox(snapshots.get(i), i + 1));

                
                if (i != snapshots.size() - 1) {
                    JLabel arrow = new JLabel(" \u203A ");
                    arrow.setFont(new Font("Segoe UI", Font.BOLD, 20));
                    arrow.setForeground(new Color(180, 190, 200));
                    queueContainer.add(arrow);
                }
            }
        }

        queueContainer.revalidate();
        queueContainer.repaint();
    }

    private JPanel createSnapshotBox(List<String> snapshot, int stepNum) {
        JPanel wrapper = new JPanel(new BorderLayout(0, 2));
        wrapper.setOpaque(false);

        JLabel stepLabel = new JLabel("Step " + stepNum);
        stepLabel.setFont(new Font("Segoe UI", Font.BOLD, 10));
        stepLabel.setForeground(new Color(150, 160, 170));
        stepLabel.setHorizontalAlignment(SwingConstants.CENTER);
        wrapper.add(stepLabel, BorderLayout.NORTH);

        JPanel box = new JPanel(new FlowLayout(FlowLayout.LEFT, 4, 4));
        box.setBackground(SNAPSHOT_BG);
        box.setBorder(BorderFactory.createLineBorder(new Color(210, 225, 240), 1, true));

        if (snapshot.isEmpty()) {
            JLabel empty = new JLabel("Empty");
            empty.setFont(new Font("Segoe UI", Font.ITALIC, 11));
            empty.setForeground(Color.GRAY);
            box.add(empty);
        } else {
            for (String p : snapshot) {
                box.add(createProcessBadge(p));
            }
        }

        wrapper.add(box, BorderLayout.CENTER);
        return wrapper;
    }

    private JLabel createProcessBadge(String processId) {
        JLabel label = new JLabel(processId);
        label.setOpaque(true);
        label.setBackground(ACCENT_BLUE);
        label.setForeground(Color.WHITE);
        label.setFont(new Font("Segoe UI", Font.BOLD, 11));
        label.setHorizontalAlignment(SwingConstants.CENTER);
        
        label.setBorder(BorderFactory.createEmptyBorder(2, 6, 2, 6));
        
        return label;
    }
}