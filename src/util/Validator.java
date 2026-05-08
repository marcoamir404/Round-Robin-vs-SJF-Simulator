package util;

import model.Process;
import java.util.*;

public class Validator {

    public static void validateProcesses(List<Process> processes) {

        if (processes == null || processes.isEmpty()) {
            throw new IllegalArgumentException("Process list cannot be empty.");
        }

        Set<String> ids = new HashSet<>();

        for (Process p : processes) {

            if (ids.contains(p.getId())) {
                throw new IllegalArgumentException("Duplicate Process ID: " + p.getId());
            }
            ids.add(p.getId());

            if (p.getArrivalTime() < 0) {
                throw new IllegalArgumentException("Arrival time cannot be negative for " + p.getId());
            }

            if (p.getBurstTime() <= 0) {
                throw new IllegalArgumentException("Burst time must be > 0 for " + p.getId());
            }
        }
    }

        public static void validateQuantum(int quantum) {
        if (quantum <= 0) {
            throw new IllegalArgumentException("Quantum must be greater than 0.");
        }
    }

    public static void validateAll(List<Process> processes, int quantum) {
        validateProcesses(processes);
        validateQuantum(quantum);
    }
}