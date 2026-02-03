CSS Grid Layout is enabled by setting the `display` property to either `grid` or `inline-grid`. When you use `display: grid` on an element, it becomes a grid container, and its direct children become grid items that participate in a two-dimensional grid layout system[3](https://www.w3schools.com/css/css_grid.asp)[4](https://developer.mozilla.org/en-US/docs/Web/CSS/CSS_grid_layout/Basic_concepts_of_grid_layout)[5](https://www.w3schools.com/css/css_display_visibility.asp)[7](https://www.smashingmagazine.com/2019/05/display-grid-subgrid/)[8](https://www.joshwcomeau.com/css/interactive-guide-to-grid/). This allows you to control both rows and columns, making it much more powerful for complex layouts compared to one-dimensional systems like Flexbox.

**Example:**

css

`.container {   display: grid;  grid-template-columns: 1fr 1fr 1fr;  grid-template-rows: 100px 100px; }`

In this example, `.container` is a grid container because of `display: grid`, and you can then use various grid-specific properties (like `grid-template-columns`, `grid-template-rows`, `gap`, etc.) to define the structure and behavior of the grid