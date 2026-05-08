# CPU Scheduling Simulator

## Round Robin vs SJF Comparison Project

---

## 📌 Project Description

This project is a GUI-based CPU Scheduling Simulator developed using Java Swing. It implements and compares two scheduling algorithms:

* **Round Robin (RR)**
* **Shortest Job First (SJF - Non-preemptive)**

The simulator allows users to input processes dynamically, visualize execution using Gantt charts, and analyze performance using standard scheduling metrics such as:

* Waiting Time (WT)
* Turnaround Time (TAT)
* Response Time (RT)

The goal of the project is to study the trade-offs between fairness and efficiency in CPU scheduling.

---

## ⚙️ Features

* Dynamic process input (Arrival Time, Burst Time)
* Time Quantum input for Round Robin
* Gantt Chart visualization with grid lines
* Ready Queue visualization
* Metrics calculation (WT, TAT, RT + averages)
* Comparison between algorithms
* Input validation (invalid values, duplicates)

---

## 🛠️ Requirements

* Java Development Kit (JDK) **8 or higher**
* Java Swing (built-in, no extra installation required)
* Any Java IDE (recommended):

  * IntelliJ IDEA
  * Eclipse
  * NetBeans

---

## 🚀 How to Build and Run

### ▶️ Option 1: Using an IDE

1. Open your IDE (IntelliJ / Eclipse / NetBeans)
2. Import the project folder
3. Make sure the JDK is set (Java 8+)
4. Locate the main file:

   ```
   src/Main.java
   ```
5. Run the program

---

### ▶️ Option 2: Using Command Line

1. Navigate to the project root directory:

   ```bash
   cd project-root
   ```

2. Compile the project:

   ```bash
   javac -d out src/**/*.java
   ```

3. Run the program:

   ```bash
   java -cp out Main
   ```

---

## 🖥️ Java Version

* Recommended: **Java 11 or higher**
* Minimum supported: **Java 8**

---

## 🎨 GUI Technology

* Built using **Java Swing**
* No JavaFX dependency required

---

## 📂 Project Structure

```
project-root/
│
├── src/
│   ├── model/
│   ├── scheduler/
│   ├── metrics/
│   ├── gui/
│   ├── util/
│   └── Main.java
│
├── screenshots/
├── test-cases/
├── files/
├── README.md
└── .gitignore
```

---

## 📊 Algorithms Implemented

### Round Robin

* Time-sliced scheduling
* Ensures fairness
* Performance depends on quantum size

### SJF (Non-preemptive)

* Selects shortest job first
* Minimizes waiting time
* May cause delays for long processes

---

## 🧪 Test Scenarios

The project includes multiple test scenarios:

* Basic mixed workload
* Short-job-heavy case
* Fairness case
* Long-job sensitivity case
* Input validation case

---

## ⚠️ Assumptions

* All processes are CPU-bound
* Context switching overhead is ignored
* SJF is implemented as non-preemptive
* Single-core CPU environment

---

## 🚫 Limitations

* No real-time context switching cost
* No multi-core support
* Ready Queue visualization is simplified
* No preemptive SJF (SRTF) implementation

---

## 👥 Team Members

| No. | Name        | ID   | Contribution     |
| --- | ----------- | ---- | ---------------- |
| 1   | [Your Name] | [ID] | UI + Integration |
| 2   | [Member 2]  | [ID] | Algorithms       |
| 3   | [Member 3]  | [ID] | Metrics          |
| 4   | [Member 4]  | [ID] | Testing          |
| 5   | [Member 5]  | [ID] | Documentation    |

---

## 📸 Screenshots

(Add screenshots here)

* Input Panel
* Gantt Charts
* Metrics View
* Comparison View

---

## 📌 Conclusion

This project demonstrates the trade-off between:

* **Fairness (Round Robin)**
* **Efficiency (SJF)**

Round Robin is more suitable for interactive systems, while SJF is more efficient for workloads with shorter processes.

---

