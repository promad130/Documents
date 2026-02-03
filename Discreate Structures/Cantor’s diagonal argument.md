 This argument, published by **Georg Cantor** in 1891, fundamentally changed our understanding of infinity by showing that not all infinite sets are the same size.
## The Diagonal Argument: Proving RR is Uncountable
Now let's explore Cantor's ingenious proof. The strategy is **proof by contradiction**: we'll assume the real numbers _are_ countable and derive a contradiction.​

### Step 1: The Setup
Since we're assuming RR is countable, then the subset (all real numbers between 0 and 1) must also be countable. This means we could theoretically list *all* real numbers in some sequence:
![[Pasted image 20251029204737.png]]
where each $d_{ij}$ is a digit from $0$ to $9$, representing the decimal expansion of each real number.

### Step 2: Constructing the Diagonal Number
Here's where the magic happens. Cantor constructs a **new real number** r by looking at the **diagonal** of this list and systematically changing each digit:​​
$$r = 0.d_1 d_2 d_3 d_4 d_5 \ldots$$
where each digit $d_i$ is chosen according to this rule:
$$d_i = \begin{cases} 4 & \text{if } d_{ii} \neq 4 \\ 5 & \text{if } d_{ii} = 4 \end{cases}$$
This means:
- $d_1$ differs from $d_{11}$ (the first digit of $r_1$)
- $d_2$ differs from $d_{22}$ (the second digit of $r_2$)
- $d_3$ differs from $d_{33}$ (the third digit of $r_3$)
- And so on...
![[Pasted image 20251029205320.png]]


### Step 3: The Contradiction
The number $r$ we constructed has a crucial property: **it differs from every number in our supposedly complete list**:​​
- $r≠r_1$ because they differ in the 1st decimal place
- $r≠r_2$ because they differ in the 2nd decimal place
- $r≠r_3$ because they differ in the 3rd decimal place
- In general, $r≠r_n$ for any n because they differ in the $n-th$ decimal place

Hence, we have now constructed a number that doesn't match to any other, but wait, our list was supposed to have no more numbers, hence we arrived an contradiction!