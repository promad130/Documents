**Source:** [[1.2 - complex_variables_mcgrawhill_e8.pdf]]
***Topic(from Source):*** Ch-2 Analytical Functions
***What is covered?*** [[Functions(Mathematical)]], [[Mapping]],[[Image,Range and Domain]],[[Inverse Mapping]], [[Limits(mathematical)]], [[Things that involve Infinity]], [[Continuity]], [[Derivatives]],[[Cauchy-Riemann Equations]], [[Analytical Function]], [[Harmonic functions]]

---
After covering the basics of complex numbers in [[Complex Numbers Introduction]], now it is time to look at functions of complex variables and how to work with them!

---
# Functions of Complex Variables

Let $S$ be a set of complex numbers. A function $f$ defined on $S$ is a rule that assigns to each $z$ in $S$ a complex number $w$. The number $w$ is called $\text{the value of } f \text{ at } z$ and is denoted by $f(z)$ ; i.e., $w = f (z)$. The set $S$ is called the *domain of definition of $f$*.

So suppose:
$$w = u + iy\qquad z = x + iy$$
Now, our function would be:
$$f(z) = f(x + iy) = u + i y$$
Where we can say:
$$u = u(x,y)\qquad v = v(x,y)$$
i.e., both $u$ and $v$ are functions of $x$ and $y$ of the input variable $z$.
If polar coordinates are considered:
$$z = re^{i\theta}\implies u + iv = f(re^{i\theta})$$
$$\implies u = u(r,\theta) \quad v = v(r,\theta)$$
---
For example:
if $f(z) = z^2$, then:
$$f(x+iy) = (x+iy)^2 = (x^2-y^2) + i(2xy)$$

So we get:
$$f(z) = w = u(x,y) + iv(x,y)$$
Where:
$$u(x,y) = x^2+y^2\qquad v(x,y) = 2xy$$
---
Now lets look at the polynomial functions:
for $n\ge0$, for $(a_0,a_1,a_2,a_3,a_4,....,a_n) \in C$:
$$P(z) = a_0+a_1z+a_2z^2+a_3z^3+....+a_nz^n$$
is a *polynomial complex function* of degree $n$, we can also divide two polynomials to get *rational functions* and are defined as:
$$f(z) = \left(\frac{P(z)}{Q(z)}\right) \qquad \forall \quad z:Q(z)\ne0$$
---
# Mapping

When we analyze real valued function, we can draw graphs of them, but when the functions are complex and so are the variables, we cannot draw a graph as the plot is on a plane, not on a line unlike real valued functions.
So, in order to have sense of how the function is behaving, we plot them ( i.e., $w=f(z)\text{ and }z$ ) on two separate planes.

This process of presenting the pair z(x,y) and w(u,v) is also knows an *MAPPING or transformation*.

## Image, range and domain

The *image* of a point z in the domain of definition S is the point $w = f (z)$, and collection of all images of points in set T is called image of T.
Collection of images of all points in the domain of a function $f(z)$ is called Range of $f(z)$.

## Inverse Mapping

The inverse image of $w = f(x+iy)$ is all the points $z$ that are in domain definition of $z$ that has that particular $w$ as image.
There could be:
- Multiple such $z$
- only one such $z$
- None at all

It is obvious that the third condition occurs when the given $w$ is not in range of $f$.

---
## Example of mapping

Let us map the function:
$$f(z) = z^2$$
From previous discussions, we can say we have:
$$w = u(x,y)+i(v(x,y))$$
where;
$$u(x,y) = x^2-y^2\qquad v(x,y)=2xy$$
So, we can think of this like transformation from $xy-plane$ to $uv-plane$.
Now we can transform this by first taking something const and analyse how things work, and take other things const and then determine the graph in both planes and their connection!

First taking:
$$u = c_1$$
(Where const is positive),So we get:
$$x^2-y^2=c_1\quad \forall\quad c_1\gt0$$
and by the second relation we get:
$$v = \pm2y\sqrt{y^2+c_1}$$
By looking at this, it is evident that for right wing of the hyperbola, the point in $uv-plane$ moves upward as the point moves upward in the right wing, and vice-versa for left wing.

Now we can show that the things would behave the same direction-wise when we take:
$$v = c_2\quad\forall\quad c_2\gt0$$
And we get:
![[Pasted image 20250403034538.png]]

And just like this, taking a few things constant, we can define the mapping for any region given!!!

By referring to the given transformation, if we want to find out the transformation / mapping into a square with;
$$u=1,u=2,v=1,v=2$$
, then we get something like:
![[Pasted image 20250403040851.png]]

(Fun Fact, just place the planes on top of each other with some gap and origin aligning, and we got ourselves a vector field!!!! But here the vector multiplication rules are different)

However, we could also use polar coordinates in both planes to show the relation and find out the mapping.

---
## Using Polar coordinates to do mapping:

So, taking the same example, we get the relation;
$$w=f(re^{i\theta}) = \rho e^{i\phi} = z^2$$
$$\implies p = r^2\quad \phi = 2\theta$$
Taking $r = r_0$, we get a circle in $x-y$ which maps on $u-v$ such that moving from $0$ to $\frac{\pi}{2}$ in first leads to movement from $0$ to $\pi$ in the second.

Hence the mapping is not one-to-one and covers the entire region in both planes!

![[Pasted image 20250403040518.png]]

---
# Limits in Complex Numbers

Limits in complex numbers mean the same as that in calculus, i.e., for a function $f$ defined in a deleted neighborhood of $z_0$ has a limit of $w_0$,i.e., approaches the complex number $w_0$ as $z$ approaches $z_0$;
$$\lim_{z \to z_0} f(z) = w_0$$
This statement eventually means:
$$|f(z)-w_0|<\epsilon\quad\forall\quad 0<|z-z_0|<\delta$$
Where:
$$0<\epsilon\text{ and }0<\delta$$
But why can't we have two limits for one function at a particular point?
Let's assume $w_1$ and $w_2$ be two limits of $f(z)$, so we get:
$$|f(z)-w_1|<\epsilon\quad\forall\quad 0<|z-z_0|<\delta_1$$
$$|f(z)-w_2|<\epsilon\quad\forall\quad 0<|z-z_0|<\delta_2$$
Subtracting both the limits:
$$|w_1-w_2|=\left||f(z)-w_1|-|f(z)-w_2|\right|$$
using triangle inequality:
$$|w_1-w_2|=\left||f(z)-w_1|-|f(z)-w_2|\right| \le |f(z)-w_1|+|f(z)-w_2|$$
$$\implies |w_1-w_2|\le2\epsilon$$
Where $\epsilon$ is any constant, and hence two limits cannot exists!!!

## Theorems on Limits

### 1)

Suppose:
$$f(z) = u(x,y)+i(v(x,y))$$
And:
$$\lim_{z \to z_0} f(z) = w_0$$
Where:
$$w_0 = a_0 + ib_0$$
Then:
$$\lim_{(x,y) \to (x_0,y_0)} u(x,y) = a_0$$
and 
$$\lim_{(x,y) \to (x_0,y_0)} v(x,y) = b_0$$

Proof of this theorem should be quite intuitive as would have same approach as the one taken in proving that only one limit can exist for a particular point of a function, but still can be referred to on Pg.No, 61 of the referred PDF.
### 2)

Suppose we have:
$$\lim_{z \to z_0} f(z) = w_1\qquad \lim_{z \to z_0} F(z) = w_2$$
Then:
$$\lim_{z \to z_0} \left(f(z) \pm F(z)\right) = w_1\pm w_2$$
$$\lim_{z \to z_0} f(z)\times F(z) = w_1\times w_2$$
$$\lim_{z \to z_0} \frac{f(z)}{F(z)} = \frac{w_1}{w_2} \qquad \forall\quad w_2 \ne 0$$
Holds true.

---
# Complex Plane involving Infinity

Sometimes, we might come across limits involving $\infty$, so we include the complex plane with infinity point, and that plane is called *extended complex plane*.

A way to imagine this is suppose there is a sphere of unit radius on the origin of the complex plane, and each and every point on the sphere represents a point on our 2D argand plane.
Let the north pole of the sphere be denoted by $N$.
So, upon drawing a line from $N$ to any point on argand plane(let's say $z$), then the point $P$ where it intersects the sphere would represent that point $z$ on the argand plane.
![[Pasted image 20250403080319.png]]
Now it is intuitive that how we would define the $\infty$ on the argand plane, and the representation of it is the north pole $N$ itself.

The sphere is known as the **Riemann sphere**, and the correspondence is called a **stereographic projection**.

---
# Some more theorems

## 1)
$$\lim_{z \to z_0}f(z) = \infty\text{ if and only if }\lim_{z \to z_0}\frac{1}{f(z)} = 0$$
Proof:
$$|f(z)|\gt\frac{1}{\epsilon}\quad\forall\quad 0\lt|z-z_0|\lt\delta$$
would be true for the given initial limit. If the later is true, then we get a limit which can be expressed numerically as $0$, so we get;
Let $\frac{1}{f(z)} = g(z)$, then; 
$$|g(z)-0|\lt\epsilon\quad\forall\quad 0\lt|z-z_0|\lt\delta$$
And this is equivalent to the thing we wanted for the first theorem.

## 2)
$$\lim_{z\to\infty}f(z) = w_0\text{ is true if and only if }\lim_{\frac{1}{z}\to 0}f\left(\frac{1}{z}\right) = w_0$$
Proof;
We want to show;
$$|f(z)-w_0|\lt\epsilon\quad\forall\quad|z|\gt\frac{1}{\delta}$$
$$\text{Let }\frac{1}{z} = Z.\text{So we get limit that tends to }0, i.e.,Z\to0$$
$$\left|f\left(\frac{1}{Z}\right)-w_0\right|\lt\epsilon\quad\forall\quad|Z-0|\lt\delta$$
Hence we can say:
$$\lim_{z\to0}f\left(\frac{1}{z}\right) = w_0$$
## 3)
$$\lim_{z\to\infty}f(z)=\infty\quad\text{If and only if }\lim_{z\to0}\frac{1}{f\left(\frac{1}{z}\right)}=0$$
Proof;
This is combination of the above two theorems.

---
# Continuity

The definition of continuity is same as what the name implies, i.e., the function shall continue without any breaks in-between, so for continuity condition for complex numbers would be;
- $\lim_{z\to z_0}f(z)$ should exist
- $f(z)$ at $z=z_0$ should exist(let's say $f(z_0) = w_0$)
- $\lim_{z\to z_0}f(z) = w_0 = f(z_0)$
We can it is continuous in an region $R$ if it is continuous at all points in $R$.

## Some Properties
If there are two functions which are continuous in region $R$, then:
- So is their sum
- So is their product
- So is their quotient if the denominator is non-zero
(same as that mentioned in limits)
Note that polynomial functions are always continuous!

## Theorem A
A composition of continuous functions is itself continuous.
## Theorem B
If a function $f (z)$ is continuous and nonzero at a point $z_0$ , then $f (z) \ne 0$ throughout some neighborhood of that point.

![[Pasted image 20250403174701.png]]

## Theorem C
If a function $f$ is continuous throughout a region $R$ that is both closed and bounded, there exists a non-negative number real number $M$ such that:
$$|f(z)|\lt M\quad \text{for all points in }R$$

---
# Derivatives

Derivatives of complex function $f$ for a defined neighborhood $|z-z_0|\lt\epsilon$ around $z_0$ is the limit:
$$f^{'}(x) = \lim_{z\to z_0}\frac{f(z)-f(z_0)}{z-z_0}$$
,and the function is differentiable if this limit exists.
Now expressing the variable $z$ in terms of a new complex variables $\Delta z$:
$$\Delta z = z-z_0 \quad (z\ne z_0)$$
We get:
$$f'(z) = \lim_{\Delta z\to0}\frac{f(z_0+\Delta z)-f(z_0)}{\Delta z}$$
![[Pasted image 20250403191834.png]]
And writing $f(z_0+\Delta z)-f(z_0) = \Delta w$:
$$f'(z) = \lim_{\Delta z\to0}\frac{\Delta w}{\Delta z}=\frac{dw}{dz}$$
----
NOTE: In order to check weather the limit for derivative exists or not, it would be valid no matter from where z approaches what it is approaching. In 2D planes with real numbers, there were only two directions, but here they can be infinite. 

So if we find that the value changes if we change direction of approach, then the function is not differentiable.

For Example:
![[Pasted image 20250403192203.png]]

![[Pasted image 20250403192216.png]]
![[Pasted image 20250403192231.png]]
![[Pasted image 20250403192248.png]]


-------

## Differentiation formulas:
Here are the **basic differentiation formulas** in calculus:
### **1. Derivative of a Constant**
$$\frac{d}{dx}[c] = 0$$
Where $c$ is a constant.
### **2. Power Rule**
$$\frac{d}{dx}[x^n] = n \cdot x^{n-1}$$
Where $n$ is any real number.
### **3. Sum Rule**
$$\frac{d}{dx}[f(x) + g(x)] = f'(x) + g'(x)$$
### **4. Difference Rule**
$$\frac{d}{dx}[f(x) - g(x)] = f'(x) - g'(x)$$
### **5. Product Rule**
$$\frac{d}{dx}[f(x) \cdot g(x)] = f'(x)g(x) + f(x)g'(x)$$
### **6. Quotient Rule**
$$\frac{d}{dx}\left[\frac{f(x)}{g(x)}\right] = \frac{f'(x)g(x) - f(x)g'(x)}{[g(x)]^2}$$
Where $g(x) \neq 0$.

### **7. Chain Rule**
$$\frac{d}{dx}[f(g(x))] = f'(g(x)) \cdot g'(x)$$

### **8. Derivative of Exponential Functions**
- For $e^x$:
  $$\frac{d}{dx}[e^x] = e^x$$
- For $a^x$:
  $$  \frac{d}{dx}[a^x] = a^x \ln(a), \quad a > 0$$
### **9. Derivative of Logarithmic Functions**
- For natural logarithm:
  $$
  \frac{d}{dx}[\ln(x)] = \frac{1}{x}, \; x > 0
  $$
- For logarithm with base $a$:
  $$
  \frac{d}{dx}[\log_a(x)] = \frac{1}{x \ln(a)}, \; x > 0, a > 0
  $$
### **10. Derivatives of Trigonometric Functions**
- Sine:
$$\frac{d}{dx}[\sin(x)] = \cos(x)$$
- Cosine:
  $$\frac{d}{dx}[\cos(x)] = -\sin(x)$$
- Tangent:
$$\frac{d}{dx}[\tan(x)] = \sec^2(x), \; x \neq (2n+1)\frac{\pi}{2}$$
### **11. Derivatives of Inverse Trigonometric Functions**
- Arc-sine:
$$\frac{d}{dx}[\arcsin(x)] = \frac{1}{\sqrt{1-x^2}}, \quad|x| < 1 $$
- Arc-cosine:
$$ \frac{d}{dx}[\arccos(x)] = -\frac{1}{\sqrt{1-x^2}},\quad |x| < 1 $$
- Arc-tangent:
$$\frac{d}{dx}[\arctan(x)] = \frac{1}{1+x^2}$$
---
# Note:
Using triangular formula:
$$tan^{-1}(x)=cot^{-1}\left(\frac{1}{x}\right)$$$$sin^{-1}(x)=cosec^{-1}\left(\frac{1}{x}\right)$$$$cos^{-1}(x)=sec^{-1}\left(\frac{1}{x}\right)$$
This applies for all $x>0$.

Hyperbolic trigonometric functions are mathematical analogs of ordinary trigonometric functions, but they are defined using hyperbolas instead of circles. Below is a detailed explanation of their definitions, properties, and applications.

## **Definitions**
Hyperbolic functions are expressed in terms of exponential functions:

1. **Hyperbolic Sine ($\sinh x$)**:
   $$
   \sinh x = \frac{e^x - e^{-x}}{2}
   $$
2. **Hyperbolic Cosine ($\cosh x$)**:
   $$
   \cosh x = \frac{e^x + e^{-x}}{2}
   $$
3. **Hyperbolic Tangent ($\tanh x$)**:
   $$
   \tanh x = \frac{\sinh x}{\cosh x} = \frac{e^x - e^{-x}}{e^x + e^{-x}}
   $$
4. **Other Hyperbolic Functions**:
   - **Cosecant ($\text{csch } x$)**: $$ \text{csch } x = \frac{1}{\sinh x} $$
   - **Secant ($\text{sech } x$)**: $$ \text{sech } x = \frac{1}{\cosh x} $$
   - **Cotangent ($\coth x$)**: $$ \coth x = \frac{\cosh x}{\sinh x} = \frac{e^x + e^{-x}}{e^x - e^{-x}}$$

## **Properties**
Hyperbolic functions share similarities with trigonometric functions, including identities and symmetry:

1. **Odd-Even Properties**:
   - $\sinh(-x) = -\sinh(x)$ (odd function)
   - $\cosh(-x) = \cosh(x)$ (even function).

2. **Key Identities**:
   - Hyperbolic Pythagorean Identity:$$\cosh^2(x) - \sinh^2(x) = 1$$
   - Double Angle Formulas:
     - $\sinh(2x) = 2\sinh(x)\cosh(x)$
     - $\cosh(2x) = \cosh^2(x) + \sinh^2(x)$

3. **Relation to Trigonometric Functions**:
   Hyperbolic functions can be expressed using imaginary arguments of trigonometric functions:
   - $\sinh(x) = -i\sin(ix)$
   - $\cosh(x) = \cos(ix)$

4. **Derivatives**:
   - $d/dx(\sinh(x)) = \cosh(x)$
   - $d/dx(\cosh(x)) = \sinh(x)$


----
# Cauchy-Riemann Equations 

Always checking for the discontinuity of the derivative limits can be a tedious job, so now we would find a relation that the partial derivatives of:
$$f(z) = u(x,y)+i(v(x,y))$$
must satisfy at $z=z_0$ for derivative of the function to exist.

We start by defining the following:
$$\Delta z= \Delta x+i\Delta y = z-z_0$$
$$z_0 = x_0+iy_0$$
$$\Delta w = f(z)-f(z_0) = [u(z)+i(v(z))-\left(u(z_0)+i(v(z_0))\right)]$$
$$\implies \Delta w = f(z_0+\Delta z)-f(z_0) = u(z_0+\Delta z)-u(z_0)+i(v(z_0+\Delta z)-v(z_0))$$
$$\implies \Delta w = u(x_0+\Delta x_0,y_0+\Delta y)-u(x_0,y_0)+i(v(x_0+\Delta x_0,y_0+\Delta y)-v(x_0,y_0))$$
Assuming the derivative exists:
$$f'(z_0)=\lim_{\Delta z\to0}\frac{\Delta w}{\Delta z}$$
This can be written as:
$$f'(z) = \lim_{(\Delta x,\Delta y)\to(0,0)}Re\left(\frac{\Delta w}{\Delta z}\right) + i\lim_{(\Delta x,\Delta y)\to(0,0)}Im\left(\frac{\Delta w}{\Delta z}\right) $$

Now as the limit exists, it should approach the same point from all directions, so first going along the Real Line of argand plane; i.e., $z$ is purely real, making $\Delta z$ purely real.
We get:
$$\frac{\Delta w}{\Delta z} = \frac{u(x_0+\Delta x_0,y_0+\Delta y)-u(x_0,y_0)}{\Delta x} + i\frac{(v(x_0+\Delta x_0,y_0+\Delta y)-v(x_0,y_0))}{\Delta x}$$

And if we approach via imaginary axis, we get:
$$\frac{\Delta w}{\Delta z} = \left(\frac{(v(x_0+\Delta x_0,y_0+\Delta y)-v(x_0,y_0))}{\Delta y}\right)-i\left(\frac{u(x_0+\Delta x_0,y_0+\Delta y)-u(x_0,y_0)}{\Delta y}\right)$$
In these approaches, we get the derivative as:
$$\text{For the purely real z, (equivalent to) paritial derivative w.r.t. Re(z) = x}$$
$$f'(z) = \frac{dw}{dz} = u_x(x,y)+iv_x(x,y)$$
$$\text{For the purely imaginary z, (equivalent to) paritial derivative w.r.t. Im(z) = y}$$
$$f'(z) = \frac{dw}{dz} = v_y(x,y)-i(u_y(x,y))$$
As the function is differentiable and the limit for the derivative exists, both the results should be same, i.e., for a complex function to be differentiable at $z = z_0$,it should follow:
$$u_x(x_0,y_0) = v_y(x_0,y_0)$$
$$v_x(x_0,y_0) = -u_y(x_0,y_0)$$
![[Pasted image 20250403203347.png]]

Even though it looks as if Cauchy-Riemann theorem is sufficient to check the differentiability  of a complex function, it cannot tell if the neighborhood is continuous or not, i.e., differentiable for not. 
It was derived assuming that the function itself is derivative, so it ensures that every function that is differentiable will also satisfy this, but not the other way around.
As a result, it is possible for a function to satisfy Cauchy-Riemann at a point, and yet not be differentiable at it!

Hence for function to be differentiable, it has to satisfy Cauchy-Riemann theorem, and $f'(x)$ should exist in the neighborhood of $z_0$(as assumed by the Cauchy-Riemann Theorem).

For example, $f(z) = |z|^2$, upon finding the limits of this function, we get:
$$\lim_{\Delta z\to0}\frac{\Delta w}{\Delta z}=\lim_{\Delta z\to0}\frac{|z_0+\Delta z|^2-|z_0|^2}{\Delta z}=\lim_{\Delta z\to0}\frac{|z_0+\Delta z|\left(\overline{|z_0+\Delta z|}\right)-z_0\left(\overline{z_0}\right)}{\Delta z}$$
$$\implies = \lim_{\Delta z\to0}\left(\overline{z}+\overline{\Delta z}+z\frac{\overline{\Delta z}}{\Delta z}\right)$$

Upon approaching from real axis, we get:
$$\frac{\Delta w}{\Delta z}=\overline{z}+\Delta z+z$$
And from the imaginary axis:
$$\frac{\Delta w}{\Delta z}=\overline{z}-\Delta z-z$$

Even though at $z(0,0)$, both would reach $0$, in the deleted neighborhood of $z_0$, the limit does not exist. 
The Cauchy-Riemann theorem is also satisfied at this point, so it is not the sole condition that can tell for sure that the function is differentiable at $z_0$ or not!

## Sufficient Condition for Differentiability:
![[Pasted image 20250403235453.png]]
Proof;
(write later)
The image shows a mathematical derivation of linear approximation in the context of complex analysis. This is a fundamental concept in calculus that allows us to approximate functions by their tangent planes at a given point.

The linear approximation shown in the image works as follows:

For a complex function w = u + iv (where u and v are real functions of x and y), we want to approximate the change in w when moving from a point (x₀, y₀) to a nearby point (x₀ + Δx, y₀ + Δy).

First, the changes in u and v are defined as:
- Δu = u(x₀ + Δx, y₀ + Δy) - u(x₀, y₀)
- Δv = v(x₀ + Δx, y₀ + Δy) - v(x₀, y₀)

When the first-order partial derivatives of u and v are continuous at (x₀, y₀), we can express these changes as:
- Δu = u_x(x₀, y₀)Δx + u_y(x₀, y₀)Δy + ε₁Δx + ε₂Δy
- Δv = v_x(x₀, y₀)Δx + v_y(x₀, y₀)Δy + ε₃Δx + ε₄Δy

Where:
- u_x and u_y are the partial derivatives of u with respect to x and y
- v_x and v_y are the partial derivatives of v with respect to x and y
- ε₁, ε₂, ε₃, and ε₄ are error terms that approach zero as (Δx, Δy) approaches (0, 0)

The first two terms in each equation represent the linear part of the approximation - essentially the tangent plane to the graph of the function at the point (x₀, y₀). The error terms capture the difference between the actual function and this linear approximation.

When we substitute these expressions into Δw = Δu + iΔv, we get equation (4), which shows how the change in the complex function w can be linearly approximated.

This approximation becomes increasingly accurate as the distance from the point (x₀, y₀) decreases, making it a powerful tool for analyzing complex functions near a given point.
![[Pasted image 20250404011244.png]]
![[Pasted image 20250404011306.png]]
![[Pasted image 20250404011322.png]]

---
## Defining the theorem in Polar Coordinates:

Now defining the sufficient condition for differentiability in polar coordinate system:

We can express $z$ in polar form as:
$$z = re^{i\theta}$$
So if a function of $z$:
$$f(re^{i\theta})=w=\rho e^{i\phi}$$
is differentiable at $z = z_0$, then we know:
$$f'(z)=u_x(x,y)+i(v_x(x,y))$$
Now in order to express in polar coordinates:
$$\frac{\partial u}{\partial r} = \frac{\partial u}{\partial x}\frac{\partial x}{\partial r}+\frac{\partial u}{\partial y}\frac{\partial y}{\partial r}$$
And we use the transformation:
$$x = r\;cos(\theta)\qquad y=r\;sin(\theta)$$
Hence we get:
$$u_r=u_xcos(\theta)+u_ysin(\theta)\qquad u_{\theta}=-u_xr\;sin(\theta)+u_yr\;cos(\theta)$$
$$v_r=v_xcos(\theta)+v_ysin(\theta)\qquad v_{\theta}=-v_xr\;sin(\theta)+v_yr\;cos(\theta)$$
And from the Cauchy-Riemann theorem, it is clear that:
$$u_x = v_y \qquad v_x=-u_y$$
So we get:
$$v_r=-u_y\;cos(\theta)+u_x sin(\theta),\quad v_θ = u_y\;r(sin (θ)) + u_x\;r(cos (θ))$$
and comparing this to:
$$u_r=u_xcos(\theta)+u_ysin(\theta)\qquad u_{\theta}=-u_xr\;sin(\theta)+u_yr\;cos(\theta)$$
we get this theorem: ![[Pasted image 20250404031138.png]]![[Pasted image 20250404031056.png]]

---
# Analytical function

Suppose we have a complex function $f(z)$, and it is differentiable at $z = z_0$, then it is analytical at that point if it is differentiable on all points in the neighborhood of that point, then it is called an **Analytical Function**.

A function analytical at all points in the domain of the function is called an **Exact function**.

If a function is analytical at some point in every neighborhood of a point $z_0$ except that point itself, then it is called **singularity of the function/singular point**

## Properties of analytical functions

Suppose two functions $f(z)\; and\; g(z)$ are analytical in domain D, then:
- So is $f(z)\pm g(z)$
- So is $f(z)g(z)$
- So is $\left(\frac{f(z)}{g(z)}\right)$ given that $g(z)$ does not go $0\text{ in }D$
- So is composition of them: $f[g(x)]$ or $g[f(x)]$

![[Pasted image 20250410003439.png]]

---
# Harmonic Functions 

Harmonic functions in mathematics means the ones which satisfy the Laplace equation:
$$H_{xx}(x,y)+H_{yy}(x,y) = 0$$
They play important role in the field of both math and physics.

So if a function $f(z)$ is analytical in domain $D$, then it's components:
$$f(z) = u(x,y)+i(v(x,y))\implies u(x,y)\text{ and }v(x,y)$$
are Harmonic in that Domain $D$.

But if a function is harmonic, and it satisfies Cauchy-Riemann:
$$u_x=v_y\qquad-u_y=v_x$$
Then it is called **Harmonic Conjugate**, i.e., $v$ is harmonic conjugate of $u$.

And hence:
$$\text{If a function is analytical}\implies \text{It is harmonic}$$
$$\text{if a function is harmonic}\implies\text{It may be analytical}$$
$$\text{if a function is harmonic and harmonic conjugate}\implies\text{It is analytical}$$




---
Follow up: [[Elementary Complex Function]] 


