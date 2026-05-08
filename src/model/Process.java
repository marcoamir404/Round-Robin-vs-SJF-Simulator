package model;

public class Process implements Cloneable {

    private String id;
    private int arrivalTime;
    private int burstTime;

    private int remainingTime;
    private int startTime = -1;
    private int completionTime;

    private int waitingTime;
    private int turnaroundTime;
    private int responseTime;

    public Process(String id, int arrivalTime, int burstTime) {
        this.id = id;
        this.arrivalTime = arrivalTime;
        this.burstTime = burstTime;
        this.remainingTime = burstTime;
    }

    public String getId() { return id; }
    public void setId(String id) {this.id = id;}
    
    public int getArrivalTime() { return arrivalTime; }
    public void setArrivalTime(int arrivaleTime) {this.arrivalTime = arrivaleTime;}
    
    public int getBurstTime() { return burstTime; }
    public void setBurstTime(int burstTime) {this.burstTime = burstTime;}
    
    public int getRemainingTime() { return remainingTime; }
    public void setRemainingTime(int remainingTime) {
        this.remainingTime = remainingTime;
    }

    public int getStartTime() { return startTime; }
    public void setStartTime(int startTime) {
        if (this.startTime == -1) {
            this.startTime = startTime;
        }
    }
    
    public int getCompletionTime() { return completionTime; }
    public void setCompletionTime(int completionTime) {
        this.completionTime = completionTime;
    }
    
    public int getWaitingTime() { return waitingTime; }
    public int getTurnaroundTime() { return turnaroundTime; }
    public int getResponseTime() { return responseTime; }

    
    

    public void calculateMetrics() {
        this.turnaroundTime = completionTime - arrivalTime;

        this.waitingTime = turnaroundTime - burstTime;

        this.responseTime = startTime - arrivalTime;
    }

// 3lshan lma ashta8l on rr and sjf each one will have diff calculations resultrs for metrics
    @Override
    public Process clone() {
        try {
            Process cloned = (Process) super.clone();
            return cloned;
        } catch (CloneNotSupportedException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public String toString() {
        return id + " (AT=" + arrivalTime +
               ", BT=" + burstTime +
               ", RT=" + remainingTime + ")";
    }
}