---

---

---
**Source:** [[1.2 - complex_variables_mcgrawhill_e8.pdf]]
***Topic(From Source):*** Ch-1 Complex Variables 
***What's Covered?*** [[Introduction to topic]], [[Algebraic Properties]], [[Inverses]], [[Complex as Vectors]], [[Triangular Inequality(Vectors and Moduli)]], [[Complex Conjugates]], [[Exponential Form]]

--- 

Usually even roots of negative numbers are said to be non-existent, so this is where we  introduce complex numbers. 
We say let:
$$
i = \sqrt{-1}
$$
and then we are now able to solve the problems that involve the terms which were otherwise non-existent.

## Introduction to Complex Numbers

Complex numbers are mathematical expressions that combine a *real part* and an *imaginary part*. They are written in the form:

$$
z = a + bi
$$

where:
- $a$ is the real part ($Re(z)$),
- $b$ is the imaginary part ($Im(z)$),
- $i$ is the imaginary unit, defined as: $$i^2 = -1$$

### Components of Complex Numbers
1. **Real Part**: Represented by $a$, it is a standard real number.
2. **Imaginary Part**: Represented by $b$, it is multiplied by $\$i$, which satisfies $i^2 = -1$.

For example:
- In $z = 3 + 4i$, the real part is 3, and the imaginary part is 4.

### Origin and Historical Context
The concept of complex numbers arose from solving equations that involve the square root of negative numbers. Italian mathematician Gerolamo Cardano first introduced them in the 16th century while solving cubic equations. René Descartes later coined the term "imaginary" for these numbers. Carl Friedrich Gauss further developed their geometric representation in the complex plane during the 18th century.

### Properties of Complex Numbers
- Complex numbers are denoted by $$\mathbb{C}$$
- They allow solutions to all polynomial equations, as stated by the Fundamental Theorem of Algebra.
- Operations such as addition, subtraction, multiplication, and division follow specific rules based on $$i^2 = -1$$

### Representation in the Complex Plane
Complex numbers can be visualized on a Cartesian plane:
- The horizontal axis represents the real part.
- The vertical axis represents the imaginary part.

For example, $$z = 3 + 4i$$ corresponds to the point $$(3, 4)$$ in this plane. This plane that contains complex numbers is also called "Argand plane"

### Importance and Applications
Complex numbers are essential in various fields, including:
- **Mathematics**: Solving polynomial equations.
- **Engineering**: Representing electromagnetic waves and electric currents.
- **Physics**: Modeling oscillations and wave phenomena.

In summary, complex numbers extend real numbers to include solutions for equations involving negative square roots, making them indispensable in both theoretical and applied sciences.


# Basic Mathematical operations in Complex Numbers

Now lets have a look at basic mathematical properties and functions of Complex Numbers:

## Sum and Products

*Complex numbers* can be defined as ordered pairs (x, y) of real numbers that are to be interpreted as points in *complex plane*.
Let's say we have two complex numbers:
$$z_1 = x _1+ iy_1$$$$z_2 = x_2 + iy_2$$
So the *SUM* is:
$$z_3 = z_1 + z_2 = (x_1 + x_2) + i(y_1 + y_2)$$

And the *PRODUCT* is:
$$z_3 = z_1 \times z_2 = (x_1x_2 - y_1y_2) + i(x_1y_2 + x_2y_1)$$

## Basic Algebraic Properties

### The Commutative Law;
$$z_1 + z_2 = z_2 + z_1$$
$$z_1z_2 = z_2z_1$$
### The associative Laws;
$$(z_1 + z_2 ) + z_3 = z_1 + (z_2 + z_3),$$$$(z_1 z_2 )z_3 = z_1 (z_2 z_3 )$$

### The inverse
#### - Additive inverse

Additive inverse of $z$ is $-z$

#### - Multiplicative inverse 

Multiplicative inverse of;
$$z = x + iy$$
is;
$$z^{-1} = \frac{x^2}{x^2+y^2} + i\frac{-y}{x^2 + y^2}$$
---
# Solving the fractions of Complex Numbers

## 1:
Suppose we are given
$$\frac{z_1}{z_2}$$
So this can be solved in terms of:
$$z_1\times z_2^{-1}$$
So we get something like:
$$\frac{z_1}{z_2} = z_1\times z_2^{-1} = \left(\left(x_1\times\frac{x_2}{x_2^2 + y_2^2}\right )-\left(y_1\times \frac{-y_2}{x_2^2 + y_2^2} \right)\right) + i\left(\left(x_1\times \frac{(-y_2)}{x_2^2 + y_2^2}\right)+\frac{x_2}{x_2^2 + y_2^2}\times y_1\right)$$ which gives:
$$\frac{z_1}{z_2}= \left(\frac{x_1x_2}{x_2^2 + y_2^2}+\frac{y_1y_2}{x_2^2 + y_2^2} \right) + i\left(\frac{x_2y_1}{x_2^2 + y_2^2} - \frac{x_1y_2}{x_2^2 + y_2^2}\right)$$

And this simplifies to:
$$\frac{z_1}{z_2} = \frac{(x_1+iy_1)(x_2-iy_2)}{(x_2+iy_2)(x_2-iy_2)}$$

## 2:
So for three complex numbers:
$$z_1,z_2,z_3$$
And the calculation:
$$\frac{z_1+ z_2}{z_3} = (z_1+z_2)z_3^{-1} = z_1z_3^{-1} + z_2z_3^{-1} = \frac{z_1}{z_3} + \frac{z_2}{z_3}$$
---
# Associating complex numbers as Vectors;

![[Pasted image 20250401215537.png]]

Often a complex number $z$ is referred to as a point in *argand plane*.

So when we do something like:
$$z_3 = z_1+z_2$$
Something like this happens in Argand Plane:

![[Pasted image 20250401220456.png]]

And now it is intuitive that what happens when we subtract two complex numbers.

## Vector Moduli

Now lets talk about the distance of a point from the origin in Argand plane:
$$|z| = \sqrt{x^2 + y^2}$$
Now then it would be intuitive of how distance between two points looks like! And that the subtraction of two complex numbers is just the distance between them.

As the complex numbers have imaginary numbers, it is meaningless to compare them directly:
$$z_1 = x_1+iy_1$$
$$z_2 = x_2+iy_2$$
So;
$$z_1 < z_2$$
$$x_1 + iy_1 < x_2 + iy_2$$
Hence there is no ground on which we can compare as comparing complex and real numbers makes no sense!

However, we can compare their modulo, and that is how we get **Triangle Inequality Theorems**

## Triangle Inequality theorems:

As we know that in a triangle, sum of two sides is always greater than the third side, we get:

![[Pasted image 20250401221953.png]] ![[Pasted image 20250401222005.png]]


So we get:

$$|z_1 + z_2| \le |z_1| + |z_2|$$
$$|z_1 - z_2| \le |z_1| + |z_2|$$
$$|z_1 + z_2| \ge ||z_1| - |z_2||$$

So upon combining them:
$$||z_1|-|z_2||\le |z_1 \pm z_2| \le |z_1|+|z_2|$$

---
# Complex Conjugates;

The complex conjugate, or simply the conjugate, of a complex number $z = x + iy$ is defined as the complex number $x − iy$ and is denoted by $z$ ; that is,
$$\bar{z} = \overline{x + iy} = x-iy$$
![[Pasted image 20250401222827.png]]

## Properties:

1) $\overline{z_1 + z_2} = \overline{z_1} + \overline{z_2}$ 
2) $\overline{z_1z_2} = \overline{z_1}\times \overline{z_2}$
3) $\overline{\left(\frac{z_1}{z_2}\right)} = \frac{\left (\overline{z_1}\right)}{\left (\overline{z_2}\right)}$ 

The MOST IMPORTANT PROPERTY:

$$z\times \overline{z} = |z|^2$$

---
# Exponential Form

Let $r$ and $θ$ be polar coordinates of the point $(x, y)$ that corresponds to a nonzero complex number $z = x + iy$. Since $x = r cos(\theta)$ and $y = r sin(\theta)$ , the number z can be written in polar form as;
$$z = r\left(cos(\theta) + i(sin(\theta))\right)$$
Now we can write argument(angle with real axis) of complex number $z$ as:
$$arg (z) = Arg (z) + 2n\pi \qquad (n = 0, \pm 1, \pm 2, \pm 3, \pm 4,.....)$$
Where the principal value of argument is represented by $Arg(z)$ such that:
$$Arg(z) \in (-\pi,\pi]$$
Where;
$$Arg(z) = tan^{-1}\left (\frac{y}{x}\right)$$
![[Pasted image 20250401232838.png]]

By using Taylor Series;
$$f(x) = f(a) + f'(a)(x-a) + \frac{f''(a)}{2!}(x-a)^2 + \cdots + \frac{f^{(n)}(a)}{n!}(x-a)^n + \cdots$$
We can show that:
$$
e^{ix}  = cos(x) + i(sin(x))
$$
So, any complex number $z = x + iy$, we can represent it as:
$$z = |z|e^{i(Arg(z))}$$

Some Example:
Something like:
$$|z-z_o| = k$$
where $k$ is a constant represents a circle with $radius = k$ and center is $z_o$ .
So in Polar form, we get:
$$z = z_o + ke^{i\theta} \qquad \forall \quad \theta \in [0,2\pi] $$
![[Pasted image 20250401234357.png]]

## Products and Powers 

When we use exponential form, it is easy to calculate products and powers of complex number as:
![[Pasted image 20250401234740.png]]
![[Pasted image 20250401234807.png]]
![[Pasted image 20250401234831.png]]

---
# Roots of Complex Numbers

---
Note;
In exponential form, the complex numbers are same if and only if:
$$z_1 = r_1e^{i\theta_1}$$
$$z_1 = r_2e^{i\theta_2}$$
$$r_1 = r_2 \qquad \theta_1 = \theta_2 + 2n\pi \quad (k = 0,\pm1,\pm2,....)$$
---
So for a complex number $z_o$ , if we want to find the $n^{th}$ root of that complex number:
$$z = z_o^{\frac{1}{n}} \implies z^n = z_o$$
Let:
$$z_o = r_o\times e^{i\theta}$$
$$z = r\times e^{i\phi}$$
So, we get:
$$r = r_o^{\frac{1}{n}} \qquad n\phi = \theta + 2k\pi \quad (k = 0,\pm1,\pm2,....)$$
$$r = r_o^{\frac{1}{n}} \qquad \phi = \frac{\theta + 2k\pi}{n} \quad (k = 0,1,2,..., (n-1))$$
And this is called $n^{th}$ root of c complex number.

![[Pasted image 20250402133713.png]]

![[Pasted image 20250402133747.png]]

---
# **Regions in Complex Plane**

Now that the basic things about complex numbers are completed, now we shall look into the depths of regions in complex plane or collection of points in $z$. 

The terminology formed here and the theorems we find here would then form the basic structure for understanding limit, continuity and derivatives of complex functions.

## Neighborhood of Complex Number ($z_o$)

$$|z-z_o|<\epsilon \qquad (1)$$
Gives us all points $z$ lying inside but not on a circle centered at $z_o$ and with a specified positive radius $\epsilon$.

When the value of $\epsilon$ is understood or is immaterial in the discussion, the set (1) is often referred to as just a *neighborhood*.
And:
$$0<|z-z_o|<\epsilon \qquad (2)$$
is referred to as *Deleted Neighborhood of $z_o$.

![[Pasted image 20250402135231.png]]

## Interior, exterior and boundary points + Open and closed sets 

Consider these regions:
$$|z|<1\qquad |z|\le 1$$
So, A point $z_o$ is said to be an *interior point* of a set S whenever there is *some neighborhood* of $z_o$ that contains *only points of S*.

A point $z_o$ is said to be an *exterior point* of a set S whenever there is *some neighborhood* of $z_o$ that contains *no points* of S.

And if $z_o$ is neither of mentioned, then it would be a *boundary point*, i.e., some neighborhood of $z_o$ *consists of both*, points inside S and outside S.
The totality of boundary points is called the *boundary of S*, in our *example*, *first set* does not have any boundary points, hence no boundary, but for the *second set*, there are boundary points and it has the boundary $|z| = 1$.

A set is called **OPEN SET** when it **contains none of it's boundary points**, i.e., has no boundary points.
While a set is called **CLOSED SET** when it **contains all of it's boundary points**, i.e., has all the boundary points inside the set.
So in our examples, first set is a Open Set, while the second one is a Closed Set.

Some sets are **neither closed nor open sets**. 
For a set to be not open, there must be a boundary point that is contained in the set; and if a set is not closed, there exists a boundary point not contained in the set. 
Observe that the punctured disk $0 < |z| ≤ 1$ is neither open nor closed. 
The set of all complex numbers is, on the other hand, both open and closed since it has no boundary points.

## Connected Sets, Domains and regions

An open set S is connected if each pair of points $z_1$ and $z_2$ in it can be joined by a polygonal line, consisting of a finite number of line segments joined end to end, that lies entirely in S. The open set $|z| < 1$ is connected. 
The annulus $1 < |z| < 2$ is, of course, open and it is also connected. 

![[Pasted image 20250402153850.png]]

A nonempty open set that is connected is called a ***domain***. Note that any neighborhood is a domain. *A domain together with* some, none, or all of its boundary points is referred to as a *region.*

## Bounded and unbounded regions, accumulation 
A *set S is bounded* if every point of S lies inside some circle |z| = R; *otherwise, it is unbounded*. Both of the sets ( $|z|<1\quad|z|\le1$ )are bounded regions, and the half plane $Re(z) ≥ 0$ is unbounded.

A point $z_o$ is said to be an ***accumulation point*** of a set S if each delete neighborhood of $z_o$ contains at least one point of S.
Evidently, a point $z_o$ is *not an accumulation point* of a set S whenever there exists some deleted neighborhood of $z_o$ that does not contain at least one point of S.

So by this logic, it can be said that all interior and boundary points are accumulation points, and all exterior points are not an accumulation point, so for a set to be closed, it should have all of it's accumulation points!!!!

---
Follow up next: [[Analytical Functions in Complex]] 