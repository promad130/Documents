# Summary of Bounds: Time and Space Complexity

## Counting Sort

### Time Complexity

**Stable Version**:
```
Step 1 (Count):      O(n)
Step 2 (Prefix sum): O(k)
Step 3 (Place):      O(n)
────────────────────────────
Total:               Θ(n + k)
```

**When is it linear?**
- If **k = O(n)**, then Θ(n + k) = Θ(n)
- **Best case**: k is small (e.g., sorting digits 0-9)
- **Worst case**: k is huge (e.g., sorting 64-bit integers)

### Space Complexity

- **Count array**: O(k)
- **Output array**: O(n)
- **Total**: **O(n + k)**

### Stability

✅ **Stable** (with prefix sum and right-to-left placement)

---

## Radix Sort

### Time Complexity

For **d digits**, each in range **[0, k-1]**:

```
Number of passes: d
Each pass:        Θ(n + k)  (using Counting Sort)
────────────────────────────
Total:            Θ(d(n + k))
```

### When is it linear?

**Case 1: Fixed number of digits**
- If **d is constant** (e.g., 32-bit integers)
- Θ(d(n + k)) = Θ(n + k)
- If k = O(n), then **Θ(n)**

**Case 2: Variable length numbers**
- If numbers are in range **[0, nᶜ]** for constant c
- We can use base **n**: d = logₙ(nᶜ) = c
- Θ(c(n + n)) = **Θ(n)**

**Example**: Sorting 1 million 32-bit integers
- n = 10⁶
- Use base 2⁸ = 256: d = 32/8 = 4 digits
- k = 256
- Time: Θ(4(10⁶ + 256)) ≈ **Θ(10⁶)** = linear!

### Space Complexity

- **Counting Sort space**: O(n + k) per pass
- **Total**: **O(n + k)**

### Stability

✅ **Stable** (requires stable sub-sort)

---

## Bucket Sort

### Time Complexity

**Average case**: **Θ(n)** (for uniformly distributed input)

**Breakdown**:
```
Create buckets:         O(n)
Distribute elements:    O(n)
Sort each bucket:       O(Σ n²ᵢ)  where nᵢ = elements in bucket i
Concatenate:            O(n)
────────────────────────────────
Total:                  O(n + Σ n²ᵢ)
```

**Expected value** (proven later):
```
E[Σ n²ᵢ] = n × E[n²ᵢ] = n × (2 - 1/n) ≈ 2n

Total expected: E[T(n)] = Θ(n)
```

**Worst case**: **Θ(n²)** (all elements in one bucket)

### Space Complexity

- **Buckets**: O(n)
- **Total**: **O(n)**

### Stability

⚠️ **Depends** on the sorting algorithm used for individual buckets
- If buckets are sorted with **Insertion Sort**: ✅ Stable
- If buckets are sorted with **Quick Sort**: ❌ Unstable

---

## Comparison Table

| Algorithm | Time (Best) | Time (Average) | Time (Worst) | Space | Stable? | Use Case |
|-----------|-------------|----------------|--------------|-------|---------|----------|
| **Counting Sort** | Θ(n+k) | Θ(n+k) | Θ(n+k) | O(n+k) | ✅ | Small range integers |
| **Radix Sort** | Θ(d(n+k)) | Θ(d(n+k)) | Θ(d(n+k)) | O(n+k) | ✅ | Fixed-length integers/strings |
| **Bucket Sort** | Θ(n) | Θ(n) | Θ(n²) | O(n) | ⚠️ | Uniformly distributed floats |
| **Merge Sort** | Θ(n log n) | Θ(n log n) | Θ(n log n) | O(n) | ✅ | General comparison sorting |
| **Quick Sort** | Θ(n log n) | Θ(n log n) | Θ(n²) | O(log n) | ❌ | General (average fast) |
| **Heap Sort** | Θ(n log n) | Θ(n log n) | Θ(n log n) | O(1) | ❌ | In-place sorting |

---

## When to Use Each Algorithm

### Counting Sort
✅ **Use when**:
- Elements are integers in a **small known range** [0, k]
- k = O(n)
- Need **stable** sorting

❌ **Avoid when**:
- Range k is very large (wastes space)
- Elements are floats or arbitrary objects

### Radix Sort
✅ **Use when**:
- Sorting **fixed-length integers or strings**
- Number of digits d is small
- Need **linear time** and **stability**

❌ **Avoid when**:
- Variable-length strings (use comparison-based sort)
- Digits d is large (becomes slower than O(n log n))

### Bucket Sort
✅ **Use when**:
- Input is **uniformly distributed** (e.g., random floats in [0, 1))
- Need **expected linear time**

❌ **Avoid when**:
- Input is **not uniform** (degrades to O(n²))
- Need **guaranteed worst-case** performance

---

## Breaking the O(n log n) Barrier

**Comparison-based sorting lower bound**: Ω(n log n)

**How linear sorts bypass it**:
1. **Don't use comparisons** (use integer keys)
2. **Exploit structure** (digits, range, distribution)
3. **Trade space** (use O(n + k) or O(n) extra memory)

**The catch**: They're **not general-purpose**. Each works only under specific conditions.

---

**Next**: Bucket Sort concept and probabilistic analysis.