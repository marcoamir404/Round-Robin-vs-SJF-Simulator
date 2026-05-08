package metrics;

import model.Process;
import java.util.*;

public class MetricsCalculator {

    public static double calculateAverageWT(List<Process> processes) {
        int sum = 0;
        for (Process p : processes) {
            sum += p.getWaitingTime();
        }
        return (double) sum / processes.size();
    }

    public static double calculateAverageTAT(List<Process> processes) {
        int sum = 0;
        for (Process p : processes) {
            sum += p.getTurnaroundTime();
        }
        return (double) sum / processes.size();
    }

    public static double calculateAverageRT(List<Process> processes) {
        int sum = 0;
        for (Process p : processes) {
            sum += p.getResponseTime();
        }
        return (double) sum / processes.size();
    }

    public static List<String[]> generateTableData(List<Process> processes) {
        List<String[]> table = new ArrayList<>();

        for (Process p : processes) {
            table.add(new String[]{
                    p.getId(),
                    String.valueOf(p.getArrivalTime()),
                    String.valueOf(p.getBurstTime()),
                    String.valueOf(p.getWaitingTime()),
                    String.valueOf(p.getTurnaroundTime()),
                    String.valueOf(p.getResponseTime())
            });
        }

        return table;
    }

}