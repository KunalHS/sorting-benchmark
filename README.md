# Sorting Algorithm Benchmark

This project investigates the behavior and performance optimizations of the Java Just-In-Time (JIT) compiler using benchmarking experiments on four different sorting techniques:

- `JavaSort` (Sequential using `Arrays.sort`)
- `JavaSortParallel` (Parallel using `Arrays.parallelSort`)
- `MergeSort` (Custom sequential merge sort)
- `MergeSortParallel` (Parallelized version of merge sort using ForkJoinPool)

## Warm-up Strategy

> _Note: Warming up refers to executing the Java program beforehand to allow the JVM to become familiar with the behavior of the functions. This gives the JVM an opportunity to apply optimizations such as Just-In-Time (JIT) compilation, which can significantly improve the program's performance during actual benchmarking.

To ensure reliable benchmarking results, multiple warm-up strategies were employed across experiments. These warm-up phases allow the JVM to perform optimizations such as JIT compilation and thread setup.

---

## Experiments and Observations

### **Experiment 1: No Warm-up**
- **Result**: Highly inconsistent timings.
- **Observation**: For each method, the initial array sizes—despite being small—took significantly longer to sort compared to larger arrays. This is likely due to JVM startup overhead and JIT compilation.

![No Warm Up](images/No%20Warmup.png)

---

### **Experiment 2: Warm-up with All Methods**
- **Result**: Slightly improved consistency.
- **Observation**: Results were more stable than in Experiment 1. However, the initial arrays still showed unusually high sort times, indicating the need for more fine-grained warm-up.

![Warmup with All Methods](images/Warmup%20with%20All%20Methods.png)

---

### **Experiment 3: Warm-up with All Methods and All Sizes**
- **Result**: Less fluctuation in results.
- **Observation**: The overall consistency improved, but the first few arrays for each method and size still exhibited unexpectedly high execution times.

![Warmup with All Methods and All Sizes](images/Warmup%20All%20Methods%20and%20Sizes.png)

---

### **Experiment 4: Individual Warm-up per Method and Size Before Execution**
- **Result**: Highly consistent timings.
- **Observation**: This approach significantly reduced warm-up-related noise. The results showed a clear and proportionate increase in execution time with array size, particularly visible on a logarithmic scale.

![Individual Warm-up per Method and Size Before Execution](images/Warmup%20before%20each%20Method%20and%20Size.png)

---

### **Experiment 5: Averaged Results from Experiment 4 (5 Runs per Configuration)**
- **Result**: Most stable and reliable benchmark.
- **Observation**: This experiment produced the most accurate and interpretable results. A strong correlation between input size and execution time was observed, especially when plotted on a log scale.

![ Averaged Results from Experiment 4 (5 Runs per Configuration)](images/Warmup%20before%20each%20Method%20and%20Size%20and%20Average.png)

---

## Final Insights

- **Sequential vs Parallel Performance**:
    - For small array sizes, **sequential sorting (JavaSort and MergeSort)** consistently outperformed parallel methods.
    - This is due to the **overhead associated with thread creation** in parallel sorting algorithms, which outweighs their benefits for smaller inputs.
    - For larger arrays, **parallel methods (JavaSortParallel and MergeSortParallel)** showed superior performance, leveraging multi-threading effectively.

- **Impact of Warm-up**:-
    - Experiments with warm-ups performed better and gave closer results to theory as compared to without warm-up experiments.
    - This is result of the fact that JVM learns and performs optimizations for regularly performed operations.
    - Java's **JIT compiler forgets over time**, it only remembers recent operations and optimizes for them. 
    - This can be concluded from the difference of results from Experiment-3 and Experiment-4, where essentially the point where the warm-up was done changes.

---

## Quantitative Results

- [Google Sheets](https://docs.google.com/spreadsheets/d/1VmpvKN_oIM7SJOgQA81mz0F-zYDrC1Ysi5jLdbkQwRw/edit?usp=sharing)

---
