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

1. Open your IDE (IntelliJ IDEA / Eclipse / NetBeans)

2. Import or open the project folder

3. Make sure the JDK is configured correctly

 Recommended:

   ```text
   Java 11+

Minimum supported:

Java 8

4. Locate the main file:

   ```text
   src/Main.java
   ```

5. Run the program normally from the IDE

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

### ▶️ Option 3: Run Using JAR File

A prebuilt executable JAR file is included inside the `files/` directory.

#### Requirements

* Java Runtime Environment (JRE) 8 or higher

#### Run Command

```bash
java -jar files/CPU-Scheduling-Simulator.jar
```

You can also launch the application by double-clicking the `.jar` file if Java is installed correctly.

---

### ▶️ Option 4: Run Using Windows Executable (.exe)

Windows users can run the application directly using the executable installer.

#### Steps

1. Open the `files/` directory

2. Locate:

   ```text
   CPU-Scheduling-Simulator.exe
   ```

3. Double-click the file

4. Follow the installation steps

5. Launch the application normally

No manual Java setup or compilation is required.

---

### ▶️ Option 5: Run Using Linux Package (.deb)

Supported Linux distributions:

* Ubuntu
* Debian
* Linux Mint

#### Installation

Run the following command:

```bash
sudo dpkg -i files/cpu-scheduling-simulator.deb
```

If dependency issues occur:

```bash
sudo apt --fix-broken install
```

#### Launching the Application

After installation, launch the application from:

* Applications Menu

Or using terminal:

```bash
cpu-scheduling-simulator
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

| No. | Name       | ID   | Contribution Area                                              |
| --- | ---------- | ---- | -------------------------------------------------------------- |
| 1   | [Member 1] | [ID] | Main GUI Architecture, Navigation System, Panels Integration   |
| 2   | [Member 2] | [ID] | Round Robin Scheduler Implementation + Ready Queue Logic       |
| 3   | [Member 3] | [ID] | SJF and SRTF Algorithms Implementation                         |
| 4   | [Member 4] | [ID] | Metrics Calculation (WT, TAT, RT) + Comparison Logic           |
| 5   | [Member 5] | [ID] | Gantt Chart Visualization + Execution Timeline UI              |
| 6   | [Member 6] | [ID] | Input Validation, Testing Scenarios, Debugging                 |
| 7   | [Member 7] | [ID] | Documentation, README, Report Writing, Screenshots Preparation |

---

## 📌 Conclusion

This project demonstrates the trade-off between:

* **Fairness (Round Robin)**
* **Efficiency (SJF)**

Round Robin is more suitable for interactive systems, while SJF is more efficient for workloads with shorter processes.

---

