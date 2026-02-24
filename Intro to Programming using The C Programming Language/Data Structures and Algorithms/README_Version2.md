# Linear Time Sorting Algorithms - Complete Guide

**Author**: Comprehensive Tutorial for Beginners  
**Language**: C++  
**Topics**: Counting Sort, Radix Sort, Bucket Sort, Probabilistic Analysis

---

## 📚 Table of Contents

1. **[Motivation](01_motivation.md)** - Why we need to break O(n log n)
2. **[Compositional Sorting](02_compositional_sorting.md)** - Building sorted arrays step-by-step
3. **[Stability Requirement](03_stability_requirement.md)** - Why order preservation is critical
4. **[Radix Sort](04_radix_sort.md)** - Digit-by-digit sorting
5. **[Stability Failure Trace](05_stability_failure_trace.md)** - What happens when stability breaks
6. **[Counting Sort (Basic)](06_counting_sort_basic.md)** - Frequency-based sorting
7. **[Counting Sort (Stable)](07_counting_sort_stable.md)** - The prefix sum trick
8. **[Complexity Summary](08_complexity_summary.md)** - All time/space bounds
9. **[Bucket Sort (Concept)](09_bucket_sort_concept.md)** - Distribution-based sorting
10. **[Bucket Sort (Analysis)](10_bucket_sort_analysis.md)** - Rigorous probabilistic proof
11. **[Complete Demo](11_complete_demo.md)** - Full working implementation

---

## 🎯 Learning Objectives

By the end of this tutorial, you will understand:

✅ Why comparison-based sorting has a lower bound of Ω(n log n)  
✅ How to bypass this using integer keys and structure  
✅ The absolute necessity of stability in compositional sorting  
✅ **Counting Sort**: O(n + k) time using frequency counts  
✅ **Radix Sort**: O(d(n + k)) time processing digits  
✅ **Bucket Sort**: Expected O(n) time for uniform distributions  
✅ How to use indicator random variables and linearity of expectation  
✅ Rigorous proof that E[T(n)] = Θ(n) for Bucket Sort  

---

## 🚀 Quick Start

### Compile and Run

```bash
# Compile the complete demo
g++ -std=c++17 -o linear_sort 11_complete_demo.md

# Note: The .md file contains valid C++ code blocks
# Extract the code block first, or use the code directly

# Run
./linear_sort
```

### Or Extract Code

```bash
# Extract C++ code from markdown
sed -n '/```cpp/,/```/p' 11_complete_demo.md | sed '1d;$d' > linear_sort.cpp
g++ -std=c++17 -o linear_sort linear_sort.cpp
./linear_sort
```

---

## 📊 Algorithm Comparison

| Algorithm | Time | Space | Stable? | Best Use Case |
|-----------|------|-------|---------|---------------|
| **Counting Sort** | Θ(n+k) | O(n+k) | ✅ | Small integer range |
| **Radix Sort** | Θ(d(n+k)) | O(n+k) | ✅ | Fixed-length integers |
| **Bucket Sort** | Θ(n) avg | O(n) | ⚠️ | Uniform distribution |
| Merge Sort | Θ(n log n) | O(n) | ✅ | General purpose |
| Quick Sort | Θ(n log n) avg | O(log n) | ❌ | General (fast avg) |

---

## 🧮 Key Mathematical Results

### Counting Sort
```
Time: Θ(n + k)
Linear when k = O(n)
```

### Radix Sort
```
Time: Θ(d(n + k))
For d constant and k = O(n): Θ(n)
```

### Bucket Sort
```
E[nᵢ²] = 2 - 1/n
E[Σ nᵢ²] = 2n - 1
E[T(n)] = Θ(n)
```

---

## 💡 Key Insights

### 1. The Prefix Sum Trick (Counting Sort)

```cpp
// Convert counts to cumulative
for (int i = 1; i <= k; i++) {
    count[i] += count[i - 1];
}

// Now count[i] tells us how many elements ≤ i
// This determines exact placement positions!
```

### 2. Right-to-Left Placement (Stability)

```cpp
// Process BACKWARDS to maintain stability
for (int i = n - 1; i >= 0; i--) {
    int pos = count[arr[i]] - 1;
    output[pos] = arr[i];
    count[arr[i]]--;
}
```

### 3. Indicator Random Variables (Bucket Sort)

```cpp
// Xⱼᵢ = 1 if element j falls into bucket i
// E[Xⱼᵢ] = 1/n (uniform distribution)
// E[Xⱼᵢ × Xₖᵢ] = 1/n² (independence, j ≠ k)
// E[nᵢ²] = Σ E[Xⱼᵢ²] + Σⱼ Σₖ≠ⱼ E[Xⱼᵢ × Xₖᵢ]
//        = n(1/n) + n(n-1)(1/n²)
//        = 1 + (1 - 1/n)
//        = 2 - 1/n
```

---

## 🔬 Practice Problems

### Beginner
1. Implement Counting Sort for negative numbers (hint: offset)
2. Sort an array of strings of equal length using Radix Sort
3. Verify stability by adding IDs to elements

### Intermediate
4. Implement Radix Sort using base 256 (process bytes)
5. Analyze Bucket Sort worst case (all elements in one bucket)
6. Adapt Bucket Sort for integers in range [0, m]

### Advanced
7. Prove that Radix Sort is stable (formal proof)
8. Implement in-place Counting Sort (space O(1))
9. Design a hybrid algorithm: Bucket Sort + Quick Sort

---

## 📖 Reading Order

**For complete beginners**:
1. → 2 → 3 → 6 → 4 → 7 → 8 → 11

**For those familiar with sorting**:
1 → 4 → 5 → 7 → 9 → 10

**For quick reference**:
8 (Complexity Summary) → 11 (Complete Demo)

---

## 🛠️ Prerequisites

- Basic C++ knowledge (vectors, loops, functions)
- Understanding of Big-O notation
- Familiarity with basic sorting (bubble, insertion, merge)
- (Optional) Basic probability for Bucket Sort analysis

---

## 📝 Notes

- All code is **fully tested** and **production-ready**
- Complexity analysis is **rigorous** and **mathematically proven**
- Examples are **detailed** with **step-by-step traces**
- **No bullshit**, just clear explanations

---

## 🙏 Acknowledgments

Based on classic algorithms from:
- **CLRS** (Introduction to Algorithms)
- **MIT 6.006** (Introduction to Algorithms)
- **Knuth** (The Art of Computer Programming, Vol. 3)

---

## 📜 License

Educational use - feel free to learn, modify, and share!

---

**Happy Learning!** 🚀

If you understand these algorithms, you're ready for:
- Advanced data structures (heaps, trees, graphs)
- Complexity theory (NP-completeness)
- Probabilistic algorithms
- Algorithm design and analysis

Keep coding! 💻