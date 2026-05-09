package gui;

import scheduler.GanttRecord;
import javax.swing.*;
import java.awt.*;
import java.awt.geom.Line2D;
import java.awt.geom.RoundRectangle2D;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class GanttChartPanel extends JPanel {

    private List<GanttRecord> records;
    private final int SCALE = 50; 
    private final int START_X = 50;
    private final int CHART_HEIGHT = 45;
    private final int CHART_Y = 30;

    private Map<String, Color> colorMap = new HashMap<>();
    private final Color[] COLORS = {
            new Color(52, 152, 219),  
            new Color(46, 204, 113),   
            new Color(155, 89, 182),   
            new Color(241, 196, 15),  
            new Color(230, 126, 34),   
            new Color(26, 188, 156),   
            new Color(231, 76, 60),    
            new Color(127, 140, 141),  
            new Color(52, 73, 94),     
            new Color(22, 160, 133),   
            new Color(39, 174, 96),    
            new Color(192, 57, 43)     
    };
    private int colorIndex = 0;
    
    public GanttChartPanel(String title) {
        setBackground(Color.WHITE);
        setLayout(null);
    }

    public void setData(List<GanttRecord> records) {
        this.records = records;
        if (records == null || records.isEmpty()) return;

        
        int totalTime = records.get(records.size() - 1).end;
        int calculatedWidth = START_X + (totalTime * SCALE) + 100;

        
        setPreferredSize(new Dimension(calculatedWidth, 100));
        
        revalidate();
        repaint();    
    }

    private Color getColor(String processId) {
        if (!colorMap.containsKey(processId)) {
            colorMap.put(processId, COLORS[colorIndex % COLORS.length]);
            colorIndex++;
        }
        return colorMap.get(processId);
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        if (records == null || records.isEmpty()) return;

        Graphics2D g2 = (Graphics2D) g;
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        int totalTime = records.get(records.size() - 1).end;
        int endX = START_X + (totalTime * SCALE);

        
        g2.setFont(new Font("Segoe UI", Font.BOLD, 11));
        for (int t = 0; t <= totalTime; t++) {
            int x = START_X + (t * SCALE);
            
            
            g2.setColor(new Color(240, 242, 245));
            g2.drawLine(x, 10, x, CHART_Y + CHART_HEIGHT);

            
            g2.setColor(new Color(150, 160, 170));
            g2.drawLine(x, CHART_Y + CHART_HEIGHT, x, CHART_Y + CHART_HEIGHT + 5);
            
            g2.setColor(new Color(100, 110, 120));
            String timeStr = String.valueOf(t);
            int strWidth = g2.getFontMetrics().stringWidth(timeStr);
            g2.drawString(timeStr, x - (strWidth / 2), CHART_Y + CHART_HEIGHT + 20);
        }

        
        g2.setColor(new Color(200, 210, 220));
        g2.setStroke(new BasicStroke(2f));
        g2.draw(new Line2D.Double(START_X, CHART_Y + CHART_HEIGHT, endX, CHART_Y + CHART_HEIGHT));

        
        for (GanttRecord r : records) {
            int x = START_X + (r.start * SCALE);
            int width = (r.end - r.start) * SCALE;
            Color c = getColor(r.processId);

            g2.setPaint(new GradientPaint(x, CHART_Y, c, x, CHART_Y + CHART_HEIGHT, c.darker()));
            g2.fill(new RoundRectangle2D.Double(x + 1, CHART_Y, width - 2, CHART_HEIGHT - 2, 6, 6));

            g2.setColor(Color.WHITE);
            g2.setFont(new Font("Segoe UI", Font.BOLD, 13));
            int textX = x + (width - g2.getFontMetrics().stringWidth(r.processId)) / 2;
            g2.drawString(r.processId, textX, CHART_Y + 28);
        }
    }
}