package scheduler;

import model.Process;
import java.util.*;

public class SJFSchedulerPreemptive {

    public static List<GanttRecord> schedule(List<Process> processes) {

        List<GanttRecord> gantt = new ArrayList<>();


        int time = 0;
        int completed = 0;
        int n = processes.size();

        Process current = null;
        int startTime = 0;

        while (completed < n) {

            Process shortest = null;

            for (Process p : processes) {
                if (p.getArrivalTime() <= time && p.getRemainingTime() > 0) {
                    if (shortest == null || p.getRemainingTime() < shortest.getRemainingTime()) {
                        shortest = p;
                    }
                }
            }

            if (shortest == null) {
                time++;
                continue;
            }

            if (current != shortest) {
                if (current != null) {
                    gantt.add(new GanttRecord(current.getId(), startTime, time));
                }
                current = shortest;
                startTime = time;

                if (current.getStartTime() == -1) {
                    current.setStartTime(time);
                }
            }

            current.setRemainingTime(current.getRemainingTime() - 1);
            time++;

            if (current.getRemainingTime() == 0) {
                current.setCompletionTime(time);
                current.calculateMetrics();
                completed++;
                
                gantt.add(new GanttRecord(current.getId(), startTime, time));
                current = null;
            }
        }

        return gantt;
    }
}