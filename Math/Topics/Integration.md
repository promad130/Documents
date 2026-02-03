Integration is one of the most fundamental and challenging topics in calculus, forming a crucial component of mathematics syllabus. This comprehensive guide presents all essential integration formulas, methods, and techniques.

# Basic Formulas
First lets revise the basics
## Power Rule and Basic Function
$$ \int 1 \; dx = x + C $$
$$ \int k \; dx = kx + C $$(where k is constant) 
$$ \int x^n \; dx = \frac{x^{n+1}}{n+1} + C $$ (n ≠ -1) 
$$ \int \frac{1}{x} \; dx = \ln|x| + C $$



You're absolutely correct to question this! There are actually **two equivalent ways** to write the integration by parts formula, and I can see where the confusion comes from. Let me clarify both forms:

## The Two Forms of Integration by Parts

## Form 1: The Standard Form (what I stated)

∫u dv=uv−∫v du\int u \, dv = uv - \int v \, du∫udv=uv−∫vdu

## Form 2: The Expanded Form (what you're thinking of)

∫u(x)⋅v(x) dx=u(x)∫v(x) dx−∫[u′(x)∫v(x) dx]dx\int u(x) \cdot v(x) \, dx = u(x) \int v(x) \, dx - \int \left[ u'(x) \int v(x) \, dx \right] dx∫u(x)⋅v(x)dx=u(x)∫v(x)dx−∫[u′(x)∫v(x)dx]dx

**Both formulas are correct!** They're just different ways of expressing the same concept.[cuemath+2](https://www.cuemath.com/calculus/integration-by-parts/)

## Why Both Forms Are Valid

The key difference is in notation:

**In Form 1:**

- uuu is a function
    
- dvdvdv represents v′(x)dxv'(x) dxv′(x)dx (the derivative of some function times dx)
    
- When we integrate dvdvdv, we get vvv
    

**In Form 2:**

- Both u(x)u(x)u(x) and v(x)v(x)v(x) are explicitly functions
    
- We're integrating the product u(x)⋅v(x)u(x) \cdot v(x)u(x)⋅v(x)
    
- u′(x)u'(x)u′(x) is the derivative of u(x)u(x)u(x)
    

## Derivation Shows They're Equivalent

Starting from the product rule of differentiation:[cuemath+1](https://www.cuemath.com/integration-of-uv-formula/)  
ddx(uv)=udvdx+vdudx\frac{d}{dx}(uv) = u \frac{dv}{dx} + v \frac{du}{dx}dxd(uv)=udxdv+vdxdu

Rearranging:  
udvdx=ddx(uv)−vdudxu \frac{dv}{dx} = \frac{d}{dx}(uv) - v \frac{du}{dx}udxdv=dxd(uv)−vdxdu

Integrating both sides:  
∫udvdxdx=uv−∫vdudxdx\int u \frac{dv}{dx} dx = uv - \int v \frac{du}{dx} dx∫udxdvdx=uv−∫vdxdudx

This gives us:  
∫u dv=uv−∫v du\int u \, dv = uv - \int v \, du∫udv=uv−∫vdu (Form 1)

If we let dv=v(x)dxdv = v(x) dxdv=v(x)dx in Form 1, then integrating dvdvdv gives us ∫v(x)dx\int v(x) dx∫v(x)dx, and we get:  
∫u(x)⋅v(x)dx=u(x)∫v(x)dx−∫u′(x)(∫v(x)dx)dx\int u(x) \cdot v(x) dx = u(x) \int v(x) dx - \int u'(x) \left(\int v(x) dx\right) dx∫u(x)⋅v(x)dx=u(x)∫v(x)dx−∫u′(x)(∫v(x)dx)dx (Form 2)

## Which Form to Use?

- **Form 1** (∫u dv=uv−∫v du\int u \, dv = uv - \int v \, du∫udv=uv−∫vdu) is more commonly used because it's more compact and easier to apply
    
- **Form 2** is more explicit about what each part represents and might be clearer for beginners
    

Both are mathematically equivalent and will give you the same answer when applied correctly. The choice often depends on teaching preference and what feels more intuitive to the student.[cuemath](https://www.cuemath.com/integration-of-uv-formula/)

So you're not wrong - you were thinking of the expanded form of the same formula! Both representations are valid and used in different textbooks and contexts.

1. [https://www.cuemath.com/calculus/integration-by-parts/](https://www.cuemath.com/calculus/integration-by-parts/)
2. [https://byjus.com/maths/integration-by-parts/](https://byjus.com/maths/integration-by-parts/)
3. [https://www.cuemath.com/integration-of-uv-formula/](https://www.cuemath.com/integration-of-uv-formula/)
4. [https://www.geeksforgeeks.org/maths/integration-by-parts/](https://www.geeksforgeeks.org/maths/integration-by-parts/)
5. [https://byjus.com/integration-by-parts-formula/](https://byjus.com/integration-by-parts-formula/)
6. [https://www.youtube.com/watch?v=B0VA8OAFYHo](https://www.youtube.com/watch?v=B0VA8OAFYHo)
7. [https://tutorial.math.lamar.edu/classes/calcii/integrationbyparts.aspx](https://tutorial.math.lamar.edu/classes/calcii/integrationbyparts.aspx)
8. [https://allen.in/jee/maths/integration-by-parts](https://allen.in/jee/maths/integration-by-parts)
9. [https://www.youtube.com/watch?v=4WYJG-WKxPg](https://www.youtube.com/watch?v=4WYJG-WKxPg)
10. [https://www.youtube.com/watch?v=-JrmyEPa56I](https://www.youtube.com/watch?v=-JrmyEPa56I)
11. [https://www.youtube.com/watch?v=sWSLLO3DS1I](https://www.youtube.com/watch?v=sWSLLO3DS1I)
12. [https://www.mathcentre.ac.uk/resources/uploaded/mc-ty-parts-2009-1.pdf](https://www.mathcentre.ac.uk/resources/uploaded/mc-ty-parts-2009-1.pdf)
13. [https://en.wikipedia.org/wiki/Integration_by_parts](https://en.wikipedia.org/wiki/Integration_by_parts)
14. [https://www.shaalaa.com/concept-notes/methods-of-integration-integration-by-parts_89](https://www.shaalaa.com/concept-notes/methods-of-integration-integration-by-parts_89)
15. [https://www.youtube.com/watch?v=q_BP4LyvdOA](https://www.youtube.com/watch?v=q_BP4LyvdOA)
16. [https://www.khanacademy.org/math/ap-calculus-bc/bc-integration-new/bc-6-11/v/deriving-integration-by-parts-formula](https://www.khanacademy.org/math/ap-calculus-bc/bc-integration-new/bc-6-11/v/deriving-integration-by-parts-formula)