
---
**Source:**[[1.2 - complex_variables_mcgrawhill_e8.pdf]]
***Topic(from Source):*** Ch-3 Elementary Functions
***What is covered?*** [[Exponential Functions]], [[Logarithmic Functions]], [[Trigonometric Functions]], [[Hyperbolic Functions]], [[Inverses]]

---
Now let us discuss some basic function types in complex numbers. Ofcourse this assumes that one knows about [[Complex Numbers Introduction]], and [[Analytical Functions in Complex]]. We shall look at:
- Exponential Functions 
- Logarithmic functions
- Trigonometric Functions in Complex

---
# Exponential Functions

We write an exponential function of $e^z$ as $e^x\times e^{iy}$, and following the Euler's Formula:$$e^z = e^xe^{iy}=e^x(cos(y)+i(sin(y)))$$
Also we can assume another complex number $Z$ such that:$$Z = e^{z} = \rho \times e^{i\phi}$$
Where:
- $\rho$ is $e^x$
- $\phi$ is $y$
Which tells us that:$$|Z| = |e^z|=e^x,\quad arg(Z)=y+2n\pi\quad n\in(0,\pm1,\pm2,....)$$
---
# Logarithmic Functions
So, now let's change the roles:$$e^w=z\implies w = ln(z)$$
Lets take $w = u+iv$ and $z=re^{i\Phi}$:$$e^ue^{iv}=re^{i\Phi}$$
So we get:$$u=ln(r)\qquad v = \Phi+2n\pi\quad(n=0,\pm1,\pm2,....)$$$$w = ln(r)+i(\Phi+2n\pi)\implies ln(z)=ln(re^{i\Phi})=ln(r)+i(\Phi+2n\pi)$$
Note that when we are given a form of a complex number like $z^c$, both z and c might be complex, then we prefer to write that is this form:
$$z^c=e^{c\times ln(z)}$$
We always take the principal value of the argument while solving.

---
# Trigonometric Functions in Complex 

We can write sine and cosine in terms of complex numbers as:
$$sin(z)=\frac{e^{iz}-e^{-iz}}{2i}\qquad cos(z) = \frac{e^{iz}+e^{-iz}}{2}$$
Also here are some trigonometric identity:
$$sin(z_1+z_2) = sin(z_1)cos(z_2)+cos(z_1)sin(z_2)$$$$cos(z_1+z_2) = cos(z_1)cos(z_2)-sin(z_1)sin(z_2)$$
---
# Hyperbolic Functions

The hyperbolic functions are:
$$sinh(z)=\frac{e^z-e^{-z}}{2},\quad cosh(z)=\frac{e^z+e^{-z}}{2}$$
Derivative is quite intuitive:
$$\frac{d}{dz}sinh(z) = cosh(z),\quad\frac{d}{dz}cosh(z) = sinh(z)$$
Also hyperbolic sine and cosine is quite related to the complex sine and cosine:
$$-i(sinh(iz))=sin(z),\quad cosh(iz)=cos(z)$$
$$-i\;sin(iz)=sinh(z),\quad cos(iz)=cosh(z)$$
![[Pasted image 20250410085812.png]]
Where $z = x+iy$.

---
# Inverse Trigonometric and Hyperbolic Functions

Inverses of trigonometric and hyperbolic functions are described in terms of logarithms.

So, in order to determine the inverse sine function:
$$sin^{-1}(z) = w \implies z = sin(w)$$$$\text{We know: }sin(z) = \frac{e^{iz}-e^{-iz}}{2i}$$$$\implies z = \frac{e^{iw}-e^{-iw}}{2i}\implies (e^{iw})^2-2iz(e^{iw})-1=0$$
Upon solving we get:
$$e^{iw}=iz \;\pm\sqrt{1-z^2}$$
For simplicity or more general solution, we say :
solution is $e^{iw}=iz \;+\sqrt{1-z^2}$ where $\sqrt{1-z^2}$ is a multi-valued function, i,e, it has multiple values(i.e,$\pm\sqrt{1-z^2}$).

So taking `natural log` on both sides:
$$sin^{-1}(z)=-i\;ln(iz+\sqrt{1-z^2})$$
However, for a given $w$, there would be multiple values in the domain $D$ of $z$ that maps on that value.

Hence the inverse functions are multiple valued function in complex numbers!

---
Follow up: [[Complex Integrals]] 