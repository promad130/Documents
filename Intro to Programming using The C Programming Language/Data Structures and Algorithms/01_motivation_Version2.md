# The Motivation: Breaking the O(n log n) Barrier

## The Problem

**Standard comparison-based sorting** (like QuickSort, MergeSort, HeapSort) have a **proven lower bound** of **Ω(n log n)** comparisons.

**Why?** Because they rely on pairwise comparisons to determine order.

## The Insight

What if we **don't compare elements directly**?

### Example: Sorting Birthdates

You have 1000 people with birthdates in format `(Day, Month, Year)`:

```
Person A: 15-03-1995
Person B: 22-03-1995
Person C: 15-01-1995
```

**Traditional approach**: Compare dates directly → O(n log n)

**Linear approach**: Exploit the **structure** of the data:
- Days: 1-31 (31 possible values)
- Months: 1-12 (12 possible values)
- Years: limited range (e.g., 1900-2025)

**Key Idea**: When the range of values `k` is small compared to `n`, we can sort in **O(n + k)** time!


---

**Next**: We'll build sorted arrays progressively using compositional sorting.