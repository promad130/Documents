
---
**Source:** [[1.2 - complex_variables_mcgrawhill_e8.pdf]]
***Topic(from Source):*** Ch-4 Integrals
***What is covered?*** [[Derivatives]], [[Integration]], [[Contours]], [[Arc and Tangents,Normal, etc.]],[[#Contour integral]],[[#Upper Bounds for Moduli of contour integrals]], [[Antiderivatives]]

---
In order to introduce integrals in complex numbers in a simplified way, let's first look at the derivatives again!
# Derivatives
Now first consider a complex function:$$f(z) = w = u(x,y)+iv(x,y)$$
Now let $z$ be a function of time:$$z(t) = x(t)+iy(t)$$
This gives us the function as:$$w(t) = u(t)+iv(t)$$
And now the derivative is quite intuitive as:$$\frac{d}{dt}w(t)=w'(t)=u'(t)+iv'(t)$$

---
# Integration
Well what integration in complex function represents in 3D is quite tricky to say, but we shall look at this after a while. First lets look at the basics of it:
$$\int_{a}^{b}{w(t)}dt = \int_{a}^{b}{u(t)}dt+i\int_{a}^{b}{v(t)}dt$$

This integral exists when the function $w(t)$ is piecewise continuous on an interval $[a,b]$,i.e., the functions $u(t)$ and $v(t)$ are piecewise continuous on the interval $[a,b].$

Although it might be discontinuous on some points in that interval, then we can just use this property:$$\int_{a}^{b}{w(t)}dt = \int_{a}^{c}{w(t)}dt+\int_{c}^{b}{w(t)}dt\qquad \text{,where }c\in[a,b]$$
And for a function that is continuous in the interval $[a,b]$, we can say:$$W'(t)=w(t),\;U'(t)=u(t),\;V'(t)=v(t)$$
However, it is not necessary that a function has to be differentiable for it to be integrable. Both of them are two different properties of a function! Yet they are connected but not rely on each other for existence. 

---
# Contours 
Integrals of complex numbers are defined on curves in the complex plane that goes from one limit to other rather than a straight line that goes from one limit to other.

## Arc
A set of points z=(x,y) in a complex plane is called an arc if:$$x=x(t)\qquad y=y(t), \quad (a\le t\le b)$$$$\text{where: }z = x(t)+iy(t)$$
This arc is denoted by $C$, and it is a **simple arc or Jordan Arc** if it does not cross itself! i.e., $z(t_1)\ne z(t_2),\text{where}\;t_1\ne t_2$.
If an arc is simple but $z(a) = z(b)$, then it is called a **simple closed curve or Jordan Curve**

Such a curve follows Right Hand Thumb Rule when it comes to orientation, so for *anticlockwise orientation* of it, it is said to be in a *positive orientation*.

For example; 
- A unit circle $z=e^{i\theta}, \theta\in[0,2\pi]$ is a positively orientated simple closed curve, and so is $z = z_0+re^{i\theta}, \theta\in[0,2\pi]$.
- However, the arc $z=e^{-i\theta},\theta\in[0,2\pi]$ is a negatively orientated simple closed curve.
Now lets take $z=e^{i2\theta},\theta\in[0,2\pi]$ as an example, so this traverses the same points as that of $z=e^{i\theta}, \theta\in[0,2\pi]$, the difference is that the first function traverses it twice!

So we parameterize the variable in order to change the interval, i.e.,:$$t=\phi(\tau),\tau\in[\alpha,\beta]$$
which maps $t$ into the range $[a,b]$, and is differentiable and continuous in the given domain $[\alpha,\beta]$.
![[Pasted image 20250410235906.png]]
This also assumes that $\phi'(\tau)\gt 0$ to ensure that the sufficient condition for the range of $t$ is satisfied.

This gives;$$z = z(t),t\in[a,b]\implies z[\phi(\tau)]\implies Z(\tau),\tau\in[\alpha,\beta]$$
## Arc Length 

Suppose now that the derivative of $z$ is:$$z'(t)=x'(t)+iy'(t)$$
So the function was differentiable and continuous in the domain of $t$, and the modulus of it is also integrable over the domain of $t$, giving the arc length of $C$:
$$L = \int_{a}^{b}{|z'(t)|}dt$$
Upon using the parametric form:
$$L=\int_{\alpha}^{\beta}|z'[\phi(\tau)]|\;\phi'(\tau)\;d\tau$$$$Z'(\tau) = z'[\phi(\tau)]\;\phi'(\tau)$$$$L=\int_{\alpha}^{\beta}Z'(\tau)\;d\tau$$
## Tangent Vector
A unit Tangent vector if $|z'(t)|$ does not vanish is:$$T = \frac{z'(t)}{|z'(t)|}$$
Also:
![[Pasted image 20250411001722.png]]
![[Pasted image 20250411001743.png]]

## What is a contour? 

A contour is an arc consisting of a finite number of smooth arcs joined end to end. Hence is a function $z(t)$ is a contour, then it is continuous and it's derivative $z'(t)$ is piece-wise continuous.

When initial and final values of $z(t)$ are same, then it is called a *simple closed contour*.

### Contour integral

Now lets look at the integration of complex valued functions $f$ that depends on a complex variable $z$.

Such an integral is defined on a contour $C$ which has a start point $z = z_1$, and end point $z = z_2$, and the integral is:$$\int_C{f(z)}dz,\qquad\int_{z_1}^{z_2}f(z)dz$$The latter is used if the integral does not depend on which contour is taken as the path.

Suppose that the contour is defined by $z = z(t),t\in[a,b]$, then:$$\int_C{f(z)}dz=\int_{a}^{b}f[z(t)]\;z'(t)\;dt$$
(Note that $z'(t)$ is piece-wise continuous) 
Now if we take $-C$:
![[Pasted image 20250411004235.png]]
Then we get the integral as:
$$\int_{C}f(z)dz = \int_{-b}^{-a}f[z(-t)]\;d(z(-t)) = -\int_{-b}^{-a}f[z(-t)]\;z'(-t)\;dt$$
Upon solving, we can see that both of them are same, as $t$ is just evaluated as $-t$(look at the domain of $-t$):
$$\int_Cf(z)dz = \int_{-C}f(z)dz$$
Hence, the direction of contour is of no concern in contour integration.
Also keep in mind:
![[Pasted image 20250411005157.png]]

---
# Upper Bounds for Moduli of contour integrals
Now lets take a function $w(t)$, then the contour integral of this function has this relation:$$\left|\int_{C}w(z)\;dz\right|\le\int_{C}|w(z)|\;dz$$
Proof:
![[Pasted image 20250411075143.png]]

Also, let $C$ be a contour of length $L$, and suppose that the function is defined $f(z)$ be piecewise continuous on that contour, and let $M$ be a non-negative number such that:$$|f(z)|\le M$$
Then we can say:$$\left|\int_Cf(z)\;dz\right|\le ML$$

---
# Antiderivatives
Although the value of a contour integral of a function f (z) from a fixed point $z_1$ to a fixed point $z_2$ depends, in general, on the path that is taken, there are certain functions whose integrals from $z_1$ to $z_2$ have values that are independent of path. Existence of antiderivative is one way to know if a function depends on the contour taken.

Let a function $f(z)$ have a function $F(z)$ such that:$$F'(z) = f(z)$$
The function $F(z)$ is the antiderivative of the function $f(z)$, and $f(z)$ is independent of the path it takes, i.e., the contour it takes:$$\int_Cf(z)\;dz=F(z_2)-F(z_1)$$
And the integral around a closed contour is zero.
### Proof:


---
### Note:Simply Connected Domains
A domain $D$ is called a simply connected if all the simple closed contours in that domain contains points that are all in $D$, i.e., there are no specific points that are removed from the domain.
Simply put, the region enclosed by the contour should contain all the points of the domain. 
Intuitively this means that there are no holes in the domain $D$. 

And the Domains that are not simple connected, are called **multiply connected domain**.

---
# Cauchy-Goursat Theorem:

Now we shall look at another theorem that will tell to us if the given function depends on the contour taken or not.
So if we have a simple closed contour $C$:$$\int_{C}f(z)\;dz=0$$
then the domain of $f(z)$ is simply connected, and the function is analytic function in the domain.

So if a function has a simply connected domain and is an exact function, then it must posses an antiderivative function.

## Extensions and application of Cauchy-Goursat Theorem:

Let $C$ be a simple closed contour described in the counterclockwise direction, and let $C_k$ for $(k=0,1,2,.....)$ be simple closed contours in the interior of $C$, then we can say:
$$\int_Cf(z)\;dz+\sum_{k}\int_{C_k}f(z)\;dz = 0$$
This fact is a corollary of something called **Principal of Deformation of Paths**.
Let $C_1$,$C_2$ be anticlockwise oriented simple closed contours, where $C_1$ is in the interior of $C_2$,
So if function $f$ is analytic in the closed region consisting of these contours and all the points between them, then:
$$\int_{C_1}f(z)\;dz = \int_{C_2}f(z)\;dz$$
---
# Cauchy Integral Theorem 
It states that:
Let $f$ be a analytical function everywhere inside and on a simple closed contour $C$ oriented in the anticlockwise direction. If $z_0$ is any point inside the contour $C$, then:
$$f(z_0)=\frac{1}{2\pi i}\int_{C}\frac{f(z)}{z-z_0}dz$$
## It's extension:
 We can also say:
 $$f^{(n)}(z_0)=\frac{n!}{2\pi i}\int_C\frac{f(z)}{(z-z_0)^{n+1}}dz$$
What does it say about a function?
- If f (z) is analytic at a given point then all it’s derivatives of all order are analytic at that point.
- If a function $f (z) = u + iv$ is analytic at a given point then all the derivatives of u and v of all order exist and continuous at that point.
- Another application of Cauchy's integral formula is what we call Morera's Theorem.
	Let $f (z)$ be continuous on a domain $D$. If $\int_{C}f(z) = 0$ for every closed contour $C$ in $D$, then $f(z)$ is analytic throughout $D$.
- Sometimes exact integral is not possible, so we use some approximations and inequalities like:
	- If f is analytic inside and on a positively oriented circle $C_R$ of radius $R$, centered at $z_0$, if $M_R$ denotes the maximum value of $|f(z)|$ on $C_R$, then:$$\left|f^{(n)}(z_0)\right|\le \frac{n!M_R}{R^n}$$
	Where $n$ is a non-negative number.
	- This is called **Cauchy's Inequality**.
---
# Liouville's Theorem 

It states that:
	***An entire function f, bounded in the complex plane is constant.***

Proof is quite intuitive, and uses one of the applications of the Cauchy's Integral Formula.

Suppose we have $M$ such that in entire domain $D$ of a function $f$, $|f(z)|\le M$.
Now according to Cauchy's Inequality:$$\left|f'(z)\right|\le \frac{1!M_R}{R^1}=\frac{M_R}{R}$$
Now, as $M$ is the absolute maximum value of the function $f(z)$ we can say: $$M_R\le M$$
Thus:$$|f'(z)|\le \frac{M}{R}$$
Now we take the Radius $R$ to be really large, and hence we get the first derivative as zero. 





---
Follow up: [[Laurent Series and Residue]] 