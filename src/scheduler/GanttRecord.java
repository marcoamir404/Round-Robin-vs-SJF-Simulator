package scheduler;

public class GanttRecord {
   public String processId;
   public int start;
   public int end;

    public GanttRecord(String processId, int start, int end) {
        this.processId = processId;
        this.start = start;
        this.end = end;
    }
}