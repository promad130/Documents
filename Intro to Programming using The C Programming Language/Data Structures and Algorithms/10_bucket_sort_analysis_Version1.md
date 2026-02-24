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

We need **E