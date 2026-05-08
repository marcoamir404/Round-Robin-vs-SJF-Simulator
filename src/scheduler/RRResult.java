package scheduler;

import java.util.List;

public class RRResult {

    public List<GanttRecord> gantt;
    public List<List<String>> queueSnapshots;

    public RRResult(List<GanttRecord> gantt, List<List<String>> queueSnapshots) {
        this.gantt = gantt;
        this.queueSnapshots = queueSnapshots;
    }
}