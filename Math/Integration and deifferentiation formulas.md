Here is the complete list of differential and integration formulas formatted for Obsidian using LaTeX math syntax, with inline formulas wrapped in single dollar signs `$...$` for inline and double dollar signs `$$...$$` for display math blocks.

---

## DIFFERENTIATION FORMULAS

## 1. Basic Formulas

- `$ \frac{d}{dx}(c) = 0 $` (where c is constant)
    
- `$ \frac{d}{dx}(x) = 1 $`
    
- `$ \frac{d}{dx}(cx) = c $` (where c is constant)
    
- `$ \frac{d}{dx}(x^n) = nx^{n-1} $`
    
- `$ \frac{d}{dx}\left(\frac{1}{x}\right) = -\frac{1}{x^2} $`
    
- `$ \frac{d}{dx}(\sqrt{x}) = \frac{1}{2\sqrt{x}} $`
    

## 2. Differentiation Rules

- Sum Rule: `$ \frac{d}{dx}(f \pm g) = f' \pm g' $`
    
- Product Rule: `$ \frac{d}{dx}(fg) = f'g + fg' $`
    
- Quotient Rule: `$ \frac{d}{dx}\left(\frac{f}{g}\right) = \frac{gf' - fg'}{g^2} $`
    
- Chain Rule: `$ \frac{d}{dx}[f(g(x))] = f'(g(x)) \cdot g'(x) $`
    

## 3. Trigonometric Derivatives

- `$ \frac{d}{dx}(\sin x) = \cos x $`
    
- `$ \frac{d}{dx}(\cos x) = -\sin x $`
    
- `$ \frac{d}{dx}(\tan x) = \sec^2 x $`
    
- `$ \frac{d}{dx}(\cot x) = -\csc^2 x $`
    
- `$ \frac{d}{dx}(\sec x) = \sec x \tan x $`
    
- `$ \frac{d}{dx}(\csc x) = -\csc x \cot x $`
    

## 4. Inverse Trigonometric Derivatives

- `$ \frac{d}{dx}(\sin^{-1} x) = \frac{1}{\sqrt{1-x^2}} , \quad |x| < 1 $`
    
- `$ \frac{d}{dx}(\cos^{-1} x) = \frac{-1}{\sqrt{1-x^2}} , \quad |x| < 1 $`
    
- `$ \frac{d}{dx}(\tan^{-1} x) = \frac{1}{1+x^2} $`
    
- `$ \frac{d}{dx}(\cot^{-1} x) = \frac{-1}{1+x^2} $`
    
- `$ \frac{d}{dx}(\sec^{-1} x) = \frac{1}{|x|\sqrt{x^2-1}} , \quad |x| > 1 $`
    
- `$ \frac{d}{dx}(\csc^{-1} x) = \frac{-1}{|x|\sqrt{x^2-1}} , \quad |x| > 1 $`
    

## 5. Exponential & Logarithmic Derivatives

- `$ \frac{d}{dx}(e^x) = e^x $`
    
- `$ \frac{d}{dx}(a^x) = a^x \ln a $`
    
- `$ \frac{d}{dx}(\ln x) = \frac{1}{x} , \quad x > 0 $`
    
- `$ \frac{d}{dx}(\log_a x) = \frac{1}{x \ln a} , \quad x > 0 $`
    

## 6. Hyperbolic Derivatives

- `$ \frac{d}{dx}(\sinh x) = \cosh x $`
    
- `$ \frac{d}{dx}(\cosh x) = \sinh x $`
    
- `$ \frac{d}{dx}(\tanh x) = \text{sech}^2 x $`
    
- `$ \frac{d}{dx}(\coth x) = -\text{csch}^2 x $`
    
- `$ \frac{d}{dx}(\text{sech } x) = -\text{sech } x \tanh x $`
    
- `$ \frac{d}{dx}(\text{csch } x) = -\text{csch } x \coth x $`
    

## 7. Inverse Hyperbolic Derivatives

- `$ \frac{d}{dx}(\sinh^{-1} x) = \frac{1}{\sqrt{x^2+1}} $`
    
- `$ \frac{d}{dx}(\cosh^{-1} x) = \frac{1}{\sqrt{x^2-1}} , \quad x > 1 $`
    
- `$ \frac{d}{dx}(\tanh^{-1} x) = \frac{1}{1-x^2} , \quad |x| < 1 $`
    
- `$ \frac{d}{dx}(\coth^{-1} x) = \frac{1}{1-x^2} , \quad |x| > 1 $`
    
- `$ \frac{d}{dx}(\text{sech}^{-1} x) = \frac{-1}{x \sqrt{1-x^2}} , \quad 0 < x < 1 $`
    
- `$ \frac{d}{dx}(\text{csch}^{-1} x) = \frac{-1}{|x| \sqrt{1+x^2}} , \quad x \neq 0 $`
    

---

## INTEGRATION FORMULAS

## 1. Basic Formulas

- `$ \int k \, dx = kx + C $` (constant k)
    
- `$ \int x^n \, dx = \frac{x^{n+1}}{n+1} + C , \quad n \neq -1 $`
    
- `$ \int \frac{1}{x} \, dx = \ln|x| + C $`
    
- `$ \int \frac{1}{\sqrt{x}} \, dx = 2\sqrt{x} + C $`
    
- `$ \int \sqrt{x} \, dx = \frac{2}{3} x^{\frac{3}{2}} + C $`
    

## 2. Integration Rules

- `$ \int [f(x) \pm g(x)] \, dx = \int f(x) \, dx \pm \int g(x) \, dx $`
    
- `$ \int k f(x) \, dx = k \int f(x) \, dx $`
    
- Integration by Parts: `$ \int u \, dv = uv - \int v \, du $`
    
- Integration by Substitution: `$ \int f(g(x)) g'(x) \, dx = \int f(u) \, du $`
    

## 3. Trigonometric Integrals

- `$ \int \sin x \, dx = -\cos x + C $`
    
- `$ \int \cos x \, dx = \sin x + C $`
    
- `$ \int \tan x \, dx = - \ln|\cos x| + C = \ln|\sec x| + C $`
    
- `$ \int \cot x \, dx = \ln|\sin x| + C $`
    
- `$ \int \sec x \, dx = \ln|\sec x + \tan x| + C $`
    
- `$ \int \csc x \, dx = \ln|\csc x - \cot x| + C $`
    
- `$ \int \sec^2 x \, dx = \tan x + C $`
    
- `$ \int \csc^2 x \, dx = -\cot x + C $`
    
- `$ \int \sec x \tan x \, dx = \sec x + C $`
    
- `$ \int \csc x \cot x \, dx = -\csc x + C $`
    

## 4. Inverse Trigonometric Integrals

- `$ \int \frac{1}{\sqrt{1 - x^2}} \, dx = \sin^{-1} x + C $`
    
- `$ \int \frac{-1}{\sqrt{1 - x^2}} \, dx = \cos^{-1} x + C $`
    
- `$ \int \frac{1}{1 + x^2} \, dx = \tan^{-1} x + C $`
    
- `$ \int \frac{-1}{1 + x^2} \, dx = \cot^{-1} x + C $`
    
- `$ \int \frac{1}{x \sqrt{x^2 - 1}} \, dx = \sec^{-1} x + C $`
    
- `$ \int \frac{-1}{x \sqrt{x^2 - 1}} \, dx = \csc^{-1} x + C $`
    

## 5. Exponential & Logarithmic Integrals

- `$ \int e^x \, dx = e^x + C $`
    
- `$ \int a^x \, dx = \frac{a^x}{\ln a} + C , \quad a > 0 , a \neq 1 $`
    
- `$ \int \ln x \, dx = x \ln x - x + C $`
    
- `$ \int \frac{1}{x \ln a} \, dx = \log_a x + C $`
    

## 6. Hyperbolic Integrals

- `$ \int \sinh x \, dx = \cosh x + C $`
    
- `$ \int \cosh x \, dx = \sinh x + C $`
    
- `$ \int \tanh x \, dx = \ln|\cosh x| + C $`
    
- `$ \int \coth x \, dx = \ln|\sinh x| + C $`
    
- `$ \int \text{sech } x \, dx = \tan^{-1} (\sinh x) + C $`
    
- `$ \int \text{csch } x \, dx = \ln|\tanh(x/2)| + C $`
    
- `$ \int \text{sech}^2 x \, dx = \tanh x + C $`
    
- `$ \int \text{csch}^2 x \, dx = -\coth x + C $`
    
- `$ \int \text{sech } x \tanh x \, dx = - \text{sech } x + C $`
    
- `$ \int \text{csch } x \coth x \, dx = - \text{csch } x + C $`
    

## 7. Special Integration Formulas (Important for JEE)

- `$ \int \frac{1}{\sqrt{a^2 - x^2}} \, dx = \sin^{-1} \left(\frac{x}{a}\right) + C $`
    
- `$ \int \frac{1}{a^2 + x^2} \, dx = \frac{1}{a} \tan^{-1} \left(\frac{x}{a} \right) + C $`
    
- `$ \int \frac{1}{x^2 - a^2} \, dx = \frac{1}{2a} \ln \left|\frac{x - a}{x + a}\right| + C $`
    
- `$ \int \frac{1}{\sqrt{x^2 + a^2}} \, dx = \ln | x + \sqrt{x^2 + a^2} | + C $`
    
- `$ \int \frac{1}{\sqrt{x^2 - a^2}} \, dx = \ln | x + \sqrt{x^2 - a^2} | + C $`
    
- `$ \int \sqrt{a^2 - x^2} \, dx = \frac{x}{2} \sqrt{a^2 - x^2} + \frac{a^2}{2} \sin^{-1} \left(\frac{x}{a}\right) + C $`
    

## 8. Definite Integration Properties

- `$ \int_a^b f(x) \, dx = -\int_b^a f(x) \, dx $`
    
- `$ \int_a^a f(x) \, dx = 0 $`
    
- `$ \int_a^c f(x) \, dx = \int_a^b f(x) \, dx + \int_b^c f(x) \, dx $`
    
- `$ \int_0^a f(x) \, dx = \int_0^a f(a - x) \, dx $`
    
- `$ \int_{-a}^a f(x) \, dx = 2 \int_0^a f(x) \, dx $` if f(x) is even
    
- `$ \int_{-a}^a f(x) \, dx = 0 $` if f(x) is odd
    

## 9. Important Limits

- `$ \lim_{x \to 0} \frac{\sin x}{x} = 1 $`
    
- `$ \lim_{x \to 0} \frac{1 - \cos x}{x^2} = \frac{1}{2} $`
    
- `$ \lim_{x \to 0} \frac{\tan x}{x} = 1 $`
    
- `$ \lim_{x \to 0} \frac{e^x - 1}{x} = 1 $`
    
- `$ \lim_{x \to 0} \frac{\ln(1+x)}{x} = 1 $`
    
- `$ \lim_{x \to \infty} \left(1 + \frac{1}{x}\right)^x = e $`
    

## 10. Integration Techniques

- Substitution method
    
- Integration by parts: `$ \int u \, dv = uv - \int v \, du $`
    
- Partial fractions
    
- Trigonometric substitutions:
    
    - For `$ \sqrt{a^2 - x^2} $` use `$ x = a \sin \theta $`
        
    - For `$ \sqrt{x^2 + a^2} $` use `$ x = a \tan \theta $`
        
    - For `$ \sqrt{x^2 - a^2} $` use `$ x = a \sec \theta $`
        

---

This formatting uses Markdown with LaTeX math blocks compatible with Obsidian's MathJax support. Inline formulas are enclosed with `$...$`, block formulas with `$$...$$`, and sets are organized with bullet points for easy readability in Obsidian.

If needed, the entire list can be copied as one note in Obsidian and rendered properly.

1. [https://www.reddit.com/r/ObsidianMD/comments/onw4ak/how_to_write_mathematical_formulas_in_obsidian/](https://www.reddit.com/r/ObsidianMD/comments/onw4ak/how_to_write_mathematical_formulas_in_obsidian/)
2. [https://forum.obsidian.md/t/suggestion-for-enhancing-mathematical-and-scientific-notation-support-in-obsidian/85641](https://forum.obsidian.md/t/suggestion-for-enhancing-mathematical-and-scientific-notation-support-in-obsidian/85641)
3. [https://www.youtube.com/watch?v=AaCVP7zqOMU](https://www.youtube.com/watch?v=AaCVP7zqOMU)
4. [https://help.obsidian.md/syntax](https://help.obsidian.md/syntax)
5. [https://forum.obsidian.md/t/i-need-help-regarding-how-to-write-equation/65240](https://forum.obsidian.md/t/i-need-help-regarding-how-to-write-equation/65240)
6. [https://www.youtube.com/watch?v=FA0z7oR7OWc](https://www.youtube.com/watch?v=FA0z7oR7OWc)
7. [https://www.obsidianstats.com/tags/math](https://www.obsidianstats.com/tags/math)
8. [https://help.obsidian.md/advanced-syntax](https://help.obsidian.md/advanced-syntax)