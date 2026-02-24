# Bucket Sort: Probabilistic Analysis

## Goal

Prove that the **expected running time** of Bucket Sort is **Θ(n)** when input is uniformly distributed.

---

## The Question

**Running time**: T(n) = Θ(n) + Θ(Σ nᵢ²)

Where nᵢ = number of elements in bucket i.

**We need to find**: E[Σ nᵢ²] (expected value of the sum of squares)

---

## Step 1: Indicator Random Variables

### Definition

For each element j and bucket i, define:

**Xⱼᵢ** = indicator random variable

```
Xⱼᵢ = 1  if element j falls into bucket i
Xⱼᵢ = 0  otherwise
```

### Number of Elements in Bucket i

```
nᵢ = Σ(j=0 to n-1) Xⱼᵢ
```

This is the **sum** of all indicators for bucket i.

### Example

n = 5, bucket i = 2:
- X₀₂ = 0 (element 0 not in bucket 2)
- X₁₂ = 1 (element 1 in bucket 2)
- X₂₂ = 0
- X₃₂ = 1 (element 3 in bucket 2)
- X₄₂ = 0

n₂ = 0 + 1 + 0 + 1 + 0 = **2 elements** in bucket 2

---

## Step 2: Expected Value of Xⱼᵢ

### Probability that element j falls into bucket i

Since input is **uniformly distributed** over [0, 1):
- Each bucket covers range **1/n**
- Probability element falls into any specific bucket = **1/n**

```
P(Xⱼᵢ = 1) = 1/n
P(Xⱼᵢ = 0) = 1 - 1/n = (n-1)/n
```

### Expected value

```
E[Xⱼᵢ] = 1 × P(Xⱼᵢ = 1) + 0 × P(Xⱼᵢ = 0)
       = 1 × (1/n)
       = 1/n
```

---

## Step 3: Square of Bucket Size

We need **E[nᵢ²]** (expected value of the square of bucket size).

### Expand nᵢ²

```
nᵢ = Σ(j=0 to n-1) Xⱼᵢ

nᵢ² = (Σ(j=0 to n-1) Xⱼᵢ)²
    = (X₀ᵢ + X₁ᵢ + X₂ᵢ + ... + Xₙ₋₁,ᵢ)²
```

### Expand the square

```
nᵢ² = Σ(j=0 to n-1) Σ(k=0 to n-1) Xⱼᵢ × Xₖᵢ
```

**Split into two cases**:

1. **Diagonal terms** (j = k): Σ(j=0 to n-1) Xⱼᵢ²
2. **Off-diagonal terms** (j ≠ k): Σ(j≠k) Xⱼᵢ × Xₖᵢ

```
nᵢ² = Σ(j=0 to n-1) Xⱼᵢ² + Σ(j=0 to n-1) Σ(k≠j) Xⱼᵢ × Xₖᵢ
```

---

## Step 4: Linearity of Expectation

Take expectation of both sides:

```
E[nᵢ²] = E[Σ(j=0 to n-1) Xⱼᵢ²] + E[Σ(j=0 to n-1) Σ(k≠j) Xⱼᵢ × Xₖᵢ]
```

By **linearity of expectation**:

```
E[nᵢ²] = Σ(j=0 to n-1) E[Xⱼᵢ²] + Σ(j=0 to n-1) Σ(k≠j) E[Xⱼᵢ × Xₖᵢ]
```

---

## Step 5: Compute E[Xⱼᵢ²]

Since Xⱼᵢ is 0 or 1:

```
Xⱼᵢ² = Xⱼᵢ  (because 0² = 0 and 1² = 1)
```

Therefore:

```
E[Xⱼᵢ²] = E[Xⱼᵢ] = 1/n
```

**Sum over all j**:

```
Σ(j=0 to n-1) E[Xⱼᵢ²] = n × (1/n) = 1
```

---

## Step 6: Compute E[Xⱼᵢ × Xₖᵢ] for j ≠ k

### Independence

Element j and element k fall into buckets **independently**.

```
E[Xⱼᵢ × Xₖᵢ] = E[Xⱼᵢ] × E[Xₖᵢ]  (by independence)
             = (1/n) × (1/n)
             = 1/n²
```

### Count off-diagonal terms

For each j, there are **(n - 1)** values of k where k ≠ j.

Total off-diagonal terms = n × (n - 1)

**Sum**:

```
Σ(j=0 to n-1) Σ(k≠j) E[Xⱼᵢ × Xₖᵢ] = n(n-1) × (1/n²)
                                    = (n² - n) / n²
                                    = (n² - n) / n²
                                    = 1 - 1/n
```

---

## Step 7: Combine Results

```
E[nᵢ²] = Σ(j=0 to n-1) E[Xⱼᵢ²] + Σ(j=0 to n-1) Σ(k≠j) E[Xⱼᵢ × Xₖᵢ]
       = 1 + (1 - 1/n)
       = 2 - 1/n
```

**Key result**: 

```
E[nᵢ²] = 2 - 1/n
```

---

## Step 8: Total Expected Sorting Time

### Sum over all buckets

```
E[Σ(i=0 to n-1) nᵢ²] = Σ(i=0 to n-1) E[nᵢ²]  (linearity of expectation)
                      = n × (2 - 1/n)
                      = 2n - 1
                      = Θ(n)
```

### Total running time

```
E[T(n)] = Θ(n) + Θ(E[Σ nᵢ²])
        = Θ(n) + Θ(2n - 1)
        = Θ(n) + Θ(n)
        = Θ(n)  ✅
```

---

## Final Proof Summary

**Theorem**: Bucket Sort runs in expected time **Θ(n)** on uniformly distributed input.

**Proof**:

1. Running time: T(n) = Θ(n) + Θ(Σ nᵢ²)

2. Define indicator variables: Xⱼᵢ = 1 if element j in bucket i

3. Bucket size: nᵢ = Σⱼ Xⱼᵢ

4. Square: nᵢ² = Σⱼ Xⱼᵢ² + Σⱼ Σₖ≠ⱼ Xⱼᵢ × Xₖᵢ

5. Expectations:
   - E[Xⱼᵢ] = 1/n
   - E[Xⱼᵢ²] = 1/n
   - E[Xⱼᵢ × Xₖᵢ] = 1/n² (independence)

6. Compute:
   - E[nᵢ²] = n(1/n) + n(n-1)(1/n²) = 1 + (1 - 1/n) = 2 - 1/n

7. Total:
   - E[Σ nᵢ²] = n(2 - 1/n) = 2n - 1 = Θ(n)

8. Conclusion: E[T(n)] = Θ(n)  **QED** ∎

---

## Numerical Example

For **n = 100**:

```
E[nᵢ²] = 2 - 1/100 = 1.99

E[Σ nᵢ²] = 100 × 1.99 = 199

Expected sorting time ≈ 100 + 199 = 299 operations
Compare to QuickSort: 100 log₂(100) ≈ 664 operations
```

Bucket Sort is **~2.2× faster** on average for uniform input!

---

## Important Caveats

### Worst Case: Θ(n²)

If all elements fall into **one bucket**:
- n₀ = n, all other nᵢ = 0
- Σ nᵢ² = n²
- Time: Θ(n²)

**When does this happen?**
- Non-uniform distribution
- Adversarial input

### The Assumption

**Critical**: Input must be **uniformly distributed**.

If not uniform, Bucket Sort can be **worse** than comparison-based sorts!

---

## C++ Demo: Expected Behavior

```cpp
#include <iostream>
#include <vector>
#include <cmath>
#include <random>
using namespace std;

void analyzeBuckets(vector<double>& arr) {
    int n = arr.size();
    vector<int> bucketSizes(n, 0);
    
    // Distribute
    for (int i = 0; i < n; i++) {
        int bucketIndex = (int)(n * arr[i]);
        if (bucketIndex == n) bucketIndex = n - 1;
        bucketSizes[bucketIndex]++;
    }
    
    // Compute sum of squares
    int sumOfSquares = 0;
    for (int i = 0; i < n; i++) {
        sumOfSquares += bucketSizes[i] * bucketSizes[i];
    }
    
    double expected = 2.0 - 1.0 / n;
    double totalExpected = n * expected;
    
    cout << "n = " << n << "\n";
    cout << "E[nᵢ²] = " << expected << "\n";
    cout << "E[Σ nᵢ²] = " << totalExpected << "\n";
    cout << "Actual Σ nᵢ² = " << sumOfSquares << "\n";
    cout << "Ratio: " << (double)sumOfSquares / totalExpected << "\n";
}

int main() {
    random_device rd;
    mt19937 gen(rd());
    uniform_real_distribution<> dis(0.0, 1.0);
    
    // Test with different sizes
    for (int n : {10, 100, 1000, 10000}) {
        vector<double> arr(n);
        for (int i = 0; i < n; i++) {
            arr[i] = dis(gen);
        }
        
        analyzeBuckets(arr);
        cout << "\n";
    }
    
    return 0;
}
```

**Sample Output**:
```
n = 10
E[nᵢ²] = 1.9
E[Σ nᵢ²] = 19
Actual Σ nᵢ² = 22
Ratio: 1.15789

n = 100
E[nᵢ²] = 1.99
E[Σ nᵢ²] = 199
Actual Σ nᵢ² = 203
Ratio: 1.02010

n = 1000
E[nᵢ²] = 1.999
E[Σ nᵢ²] = 1999
Actual Σ nᵢ² = 1987
Ratio: 0.993997

n = 10000
E[nᵢ²] = 1.9999
E[Σ nᵢ²] = 19999
Actual Σ nᵢ² = 20043
Ratio: 1.00220
```

Notice: As **n increases**, actual value converges to **expected value**!

---

**This completes the probabilistic analysis.** You now understand:

✅ How to use indicator random variables
✅ Linearity of expectation
✅ Independence of random variables
✅ Computing E[nᵢ²] = 2 - 1/n
✅ Final proof: E[T(n)] = Θ(n)

---