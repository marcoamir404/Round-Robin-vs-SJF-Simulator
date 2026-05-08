package scheduler;

import model.Process;
import java.util.*;

public class SJFSchedulerNonPreemptive {

    public static List<GanttRecord> schedule(List<Process> processes) {

        List<GanttRecord> gantt = new ArrayList<>();

        processes.sort(Comparator.comparingInt(Process::getArrivalTime));

        int time = 0;
        int completed = 0;
        int n = processes.size();

        boolean[] isCompleted = new boolean[n];

        while (completed < n) {

            Process shortest = null;
            int index = -1;

            for (int i = 0; i < n; i++) {
                Process p = processes.get(i);

                if (!isCompleted[i] && p.getArrivalTime() <= time) {
                    if (shortest == null || p.getBurstTime() < shortest.getBurstTime()) {
                        shortest = p;
                        index = i;
                    }
                }
            }

            if (shortest == null) {
                time++;
                continue;
            }

           
            shortest.setStartTime(time);

            int start = time;
            time += shortest.getBurstTime();
            int end = time;

            gantt.add(new GanttRecord(shortest.getId(), start, end));

            shortest.setCompletionTime(time);
            shortest.calculateMetrics();

            isCompleted[index] = true;
            completed++;
        }

        return gantt;
    }
}