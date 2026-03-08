

## The Concept

Instead of sorting everything at once, we sort **one component at a time**.

### Example: Sorting Birthdates

Given dates: `(Day, Month, Year)`

```
Original:
15-03-1995
22-01-1995
15-01-1996
```

**Step 1**: Sort by **Day** only
```
15-03-1995
15-01-1996
22-01-1995
```

**Step 2**: Sort by **Month** (keeping day order when months are equal)
```
15-01-1996
15-01-1995  ← Wrong! We lost the year ordering
22-01-1995
15-03-1995
```

**Wait!** This doesn't work correctly. We need **stability**.

## The Correct Approach: Least-to-Most Significant

Sort from **least significant** to **most significant** component:

**Step 1**: Sort by **Day**
```
15-03-1995
15-01-1996
22-01-1995
```

**Step 2**: Sort by **Month** (stable sort)
```
15-01-1996
22-01-1995
15-03-1995
```

**Step 3**: Sort by **Year** (stable sort)
```
15-03-1995  ← Correct!
22-01-1995
15-01-1996
```

## Why This Order?

When we sort by a **more significant component**, we need the **less significant components to remain in order** among equal elements.

**Analogy**: Alphabetizing names
1. Sort by last name
2. Among same last names, first names are already sorted (if stable)

## Mathematical Representation

For a tuple `(a₁, a₂, ..., aₐ)`:

Sort in order: `aₐ → aₐ₋₁ → ... → a₁`

Where `a₁` is **most significant**.

## C++ Demo: Manual Compositional Sort

```cpp
#include <iostream>
#include <vector>
#include <algorithm>
using namespace std;

struct Date {
    int day, month, year;
};

// We'll use stable_sort for each component
void compositionalSort(vector<Date>& dates) {
    // Step 1: Sort by day (least significant for our ordering)
    stable_sort(dates.begin(), dates.end(), [](const Date& a, const Date& b) {
        return a.day < b.day;
    });
    
    cout << "After sorting by day:\n";
    for (auto& d : dates) cout << d.day << "-" << d.month << "-" << d.year << "\n";
    
    // Step 2: Sort by month
    stable_sort(dates.begin(), dates.end(), [](const Date& a, const Date& b) {
        return a.month < b.month;
    });
    
    cout << "\nAfter sorting by month:\n";
    for (auto& d : dates) cout << d.day << "-" << d.month << "-" << d.year << "\n";
    
    // Step 3: Sort by year (most significant)
    stable_sort(dates.begin(), dates.end(), [](const Date& a, const Date& b) {
        return a.year < b.year;
    });
    
    cout << "\nAfter sorting by year:\n";
    for (auto& d : dates) cout << d.day << "-" << d.month << "-" << d.year << "\n";
}

int main() {
    vector<Date> dates = {
        {15, 3, 1995},
        {22, 1, 1995},
        {15, 1, 1996},
        {10, 1, 1995}
    };
    
    compositionalSort(dates);
    return 0;
}
```

**Output**:
```
After sorting by day:
10-1-1995
15-3-1995
15-1-1996
22-1-1995

After sorting by month:
10-1-1995
15-1-1996
22-1-1995
15-3-1995

After sorting by year:
10-1-1995
15-1-1996  ← WRONG! Should be after 22-1-1995
22-1-1995
15-3-1995
```

**Wait, this is still wrong!** We'll fix this in the next section by understanding **stability**.

---

**Next**: Why stability is absolutely critical.