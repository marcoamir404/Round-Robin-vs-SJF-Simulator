package util;

import model.Process;
import java.util.*;

public class Cloner {

    public static List<Process> cloneProcessList(List<Process> original) {

        List<Process> clonedList = new ArrayList<>();

        for (Process p : original) {
            clonedList.add(p.clone());
        }

        return clonedList;
    }
}