**Expected to know:** [[Complex Integrals]] 
**Topics Covered:** Taylor Series, 
**Tags:**

# Taylor Series

An approximation formula used quite often:
$$f(x) = \sum_{n=0}^{\infty}c_n(x-x_0)^n,\quad c_n = \frac{f^n(x_0)}{n!}$$
This gives famous stuff like:
![[Pasted image 20250505001122.png]]

However, there are some functions whose Taylor series does not exit, for example $\sqrt{x}$ at $x=0$.

## Which functions are representable by the Taylor series?

For that lets look at the complex form of it:

Suppose that a **function f is analytic throughout a disk**, $C: |z-z_0|\lt R_0$, centered at $z_0$ with radius $R_0$, then there exist a power series which converges to $f(z)$ in $C$;$$f(z) = \sum_{n=0}^{\infty}c_n(z-z_0)^n,\quad c_n = \frac{f^n(z_0)}{n!}$$
Hence for a function to be represented by a Taylor series, it has to be analytical at the point, hence, in the previous example, the function $\sqrt{x}$ has to be analytical at $x=0$ which it ain't.

We put $z_0$ in the Taylor series, and we get something called a Maclaurin Series.
And note that the function f is analytic throughout a disk!!!!
## Some Important Taylor Series Expansion:

![[Pasted image 20250505004033.png]]
![[Pasted image 20250505004050.png]]
![[Pasted image 20250505004100.png]]
![[Pasted image 20250505004112.png]]
![[Pasted image 20250505004123.png]]
![[Pasted image 20250505004131.png]]
![[Pasted image 20250505004152.png]]
![[Pasted image 20250505004210.png]]
![[Pasted image 20250505004231.png]]
![[Pasted image 20250505004240.png]]
![[Pasted image 20250505004248.png]]
![[Pasted image 20250505004258.png]]

# Laurent Series

Suppose $f (z)$ is analytic in an annulus $R_1 < |z − z_0 | < R_2$. Then $f (z)$ has a **Laurent series** expansion in the annulus given by:
$$f(z) = \sum_{n=0}^{\infty}a_n(z-z_0)^n+\sum_{n=1}^{\infty}\frac{b_n}{(z-z_0)^n}$$
Where:$$a_n=\frac{1}{2\pi i}\int_C\frac{f(z)}{(z-z_0)^{n+1}}\qquad b_n=\frac{1}{2\pi i}\int_C\frac{f(z)}{(z-z_0)^{-n+1}}$$
and $C$ is any positively oriented simple closed contour around $z_0$ in $R_1 < |z − z_0 | < R_2.$

The first part with $a_n$ is called the **analytic part** and the second part is called the **principal part** of the **Laurent series**.

Tip: Use Cauchy Integral Formulas to find the coefficient $a_n$

## Isolated Singularity 

We know what singularity is: "**A point $z_0$ in the domain of $f(z)$ is called a singular point if $f$ fails to be analytical at the point $z_0$ but each neighborhood of $z_0$ has some point at which $f(z)$ is analytical.**" 

Isolated Singularity is somewhat same, the only difference is that here, all the points in all the neighborhood of $z_0$ are analytical here, only $z = z_0$ is not.
![[Pasted image 20250505095336.png]]

## Residue
![[Pasted image 20250505095442.png]]


Hence it can be used to find closed integrals of functions as:
$$Res_{z = z_0}\ f(z) = b_1 = \frac{1}{2\pi i}\int_Cf(z)dz$$
### Cauchy's Residue Theorem
![[Pasted image 20250505100848.png]]

Logically speaking, using Cauchy-Goursat Theorem, we can say:
$$∮Cf(z)dz−∑k=1n∮Ckf(z)dz=0$$
And by finding the residue, we get the above theorem.

### Residue at infinity

Complex functions are not analytical at infinity, as:
Consider a simple example from the search results - a non-constant polynomial like $f(z) = az + b$:

1. This function is analytic everywhere in the complex plane, including $|z| > R₁$
2. However, $g(w) = f(1/w) = a(1/w) + b = (bw + a)/w$
3. The function $g(w)$ has a pole at $w = 0$ (not a removable singularity)
4. Therefore, $g$ is not analytic at $w = 0$, so f is not analytic at infinity.

The key issue is the behavior as $|z| → ∞$. For a function to be analytic at infinity, it must approach a finite limit as $|z| → ∞$, which polynomials don't do.

Hence, infinity is an isolated singularity for our function. 
So we have to find residue at infinity, which is given by:

If there is a positive number $R_1$ such that the function $f$ is analytic for $R_1 < |z| < ∞$, then the residue of $f$ at $z = ∞$ is:$$Res_{z=\infty}f(z)=-Res_{z=0}\left[\frac{1}{z^2}f\left(\frac{1}{z}\right)\right]$$
This is sometimes easier to use rather than the Cauchy's residue theorem to find the integrals:

![[Pasted image 20250505110113.png]]

## Poles and singularities

- If the principal part has no terms for $z = z_0$, then it is a **removable singularity**.

- Principal part contains finitely many nonzero terms for $z= z_0$, then it called a **pole**. If the last coefficient of principal part is $b_n$, where $n\ge 1$, then $n$ is called the **order of the pole**.
	- for $n= 1$, the pole is called a **simple pole**.
- If the principal part contains infinitely many nonzero terms, then $z = z_0$ is called an **essential singularity**.
![[Pasted image 20250505114028.png]]
![[Pasted image 20250505114305.png]]
