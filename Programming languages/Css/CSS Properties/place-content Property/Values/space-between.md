When you use `space-between` with the `place-content` property in CSS (for Grid or multi-line Flexbox layouts), it distributes the available extra space **evenly between the lines or rows** of the container, with no space at the start or end edges.

## How `space-between` Works in `place-content`

- **In Grid or multi-line Flexbox containers:**  
    The lines (rows or columns) are pushed apart so that the remaining space is placed _only_ between them, not before the first or after the last line.
    
- **Result:**  
    The first line is flush with the start edge, the last line is flush with the end edge, and all other lines are spaced evenly in between.
    

## Example

css

`.container {   display: grid;  grid-template-rows: repeat(3, 50px);  height: 300px;  place-content: space-between; }`

In this example, the three rows will be spread out so that the space between each is equal, and there is no extra space at the top or bottom of the container.

## Visual Summary

|Value|Effect|
|---|---|
|space-between|Evenly distributes extra space between lines/rows, with no space at the container edges|

## Reference

- "space-between: Distributes items evenly with equal space between them." [6](https://tillitsdone.com/blogs/css-property-place-content/)
    
- "Distribute available extra space evenly between the elements inside the container on both axis." [2](https://www.w3schools.com/cssref/css_pr_place-content.php)
    
- "The first value is the align-content property value, the second the justify-content one." [1](https://developer.mozilla.org/en-US/docs/Web/CSS/place-content)
    

**In summary:**  
Using `space-between` in `place-content` ensures that lines or rows are spaced evenly apart, with no extra space at the start or end of the container.

### Citations:

1. [https://developer.mozilla.org/en-US/docs/Web/CSS/place-content](https://developer.mozilla.org/en-US/docs/Web/CSS/place-content)
2. [https://www.w3schools.com/cssref/css_pr_place-content.php](https://www.w3schools.com/cssref/css_pr_place-content.php)
3. [https://developer.mozilla.org/en-US/docs/Web/CSS/align-content](https://developer.mozilla.org/en-US/docs/Web/CSS/align-content)
4. [https://tailwindcss.com/docs/place-content](https://tailwindcss.com/docs/place-content)
5. [https://www.tutorialspoint.com/css/css_place-content.htm](https://www.tutorialspoint.com/css/css_place-content.htm)
6. [https://tillitsdone.com/blogs/css-property-place-content/](https://tillitsdone.com/blogs/css-property-place-content/)
7. [https://www.w3schools.com/css/tryit.asp?filename=trycss3_flexbox_align-content_space-between](https://www.w3schools.com/css/tryit.asp?filename=trycss3_flexbox_align-content_space-between)
8. [https://stackoverflow.com/questions/27539262/whats-the-difference-between-align-content-and-align-items](https://stackoverflow.com/questions/27539262/whats-the-difference-between-align-content-and-align-items)

---

Answer from Perplexity: [pplx.ai/share](https://www.perplexity.ai/search/pplx.ai/share)