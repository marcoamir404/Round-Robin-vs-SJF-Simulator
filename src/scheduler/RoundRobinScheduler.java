package scheduler;

import model.Process;
import java.util.*;

public class RoundRobinScheduler {

    public static RRResult schedule(List<Process> processes, int quantum) {

        Queue<Process> queue = new LinkedList<>();
        List<GanttRecord> gantt = new ArrayList<>();
        List<List<String>> queueSnapshots = new ArrayList<>();

        // 3lshan n sort them according to arraivingTime
        processes.sort(Comparator.comparingInt(Process::getArrivalTime));

        int time = 0;
        int index = 0;
        
        // loop 3lshan n insert all the processes at time 0
        while (index < processes.size() && processes.get(index).getArrivalTime() <= time) {
            queue.add(processes.get(index));
            index++;
        }

        // 🔥 أول snapshot
        queueSnapshots.add(queueToList(queue));
        
        // 3lshan lw there is no process at time 0, we get the arrivalTime of first process
        if (queue.isEmpty() && index < processes.size()) {
            time = processes.get(index).getArrivalTime();
            queue.add(processes.get(index));
            index++;
        }
        
        
        // loop with function to execute el process w t add the new processes if they just arrived
        while (!queue.isEmpty()) {

            Process current = queue.poll();

            if (current.getStartTime() == -1) {
                current.setStartTime(time);
            }

            int execTime = Math.min(quantum, current.getRemainingTime());

            int start = time;
            time += execTime;
            int end = time;

            gantt.add(new GanttRecord(current.getId(), start, end));

            current.setRemainingTime(current.getRemainingTime() - execTime);

            while (index < processes.size() && processes.get(index).getArrivalTime() <= time) {
                queue.add(processes.get(index));
                index++;
            }

            // to check if the process is terminated or not 
            if (current.getRemainingTime() > 0) {
                queue.add(current);
            } else {
                current.setCompletionTime(time);
                current.calculateMetrics();
            }

            // 🔥 snapshot بعد update
            queueSnapshots.add(queueToList(queue));

            // 3lshan lw fe gap between current time and the next arriving process, its executes the process and update the current time
            if (queue.isEmpty() && index < processes.size()) {
                time = processes.get(index).getArrivalTime();
                queue.add(processes.get(index));
                index++;
            }
        }

        return new RRResult(gantt, queueSnapshots);
    }

    // 🔷 helper
    private static List<String> queueToList(Queue<Process> queue) {
        List<String> list = new ArrayList<>();
        for (Process p : queue) {
            list.add(p.getId());
        }
        return list;
    }
}