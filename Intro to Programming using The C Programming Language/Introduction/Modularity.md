**Topics Covered:**
**Tags:**

Modularity in programming is a design approach that breaks down complex software systems into smaller, independent, and interchangeable units called modules. Each module encapsulates specific functionality, promoting organized, maintainable, and reusable code. This technique addresses challenges like scalability and collaboration in large projects.

## Declaration VS Definition
- **Declaration:** Says that something exists, somewhere
	A **declaration** informs the compiler about the existence or type of a variable, function, or data structure, but does not allocate memory or provide the implementation. 
	Declarations are used to make program elements visible to other parts of the code, often across different modules. In modular programming, declarations are typically found in header files or module interfaces, allowing other modules to know about the existence and type of functions or variables they can use.
- **Definition:** Tells what that thing is
	A **definition** is a statement that creates a variable, function, or data structure by specifying its type and allocating memory or providing the actual implementation. 
	In modular programming, a definition gives the module or program the actual existence of a function or variable within the codebase.

So a **declaration** can either be one of the two things about it's **definition**
- *extern*: Definition is somewhere else, either in this compilation unit or another
- *static*: definition is in this compilation unit, and can't be seen outside of it

# Modularity in C
A module is a self-contained piece of a larger program. The modules interface consists of:
- *externally visible*:
	- functions to be invoked
	- typedefs and perhaps global variables
	- cpp macros
- *internal functions, types, global variables*
	- that clients should not look at

We implement those modules using C header files. 
For example, the module *foo* has the interface `foo.h`. Clients of *foo* can use it with `#include "foo.h"` . *The header file includes no definitions*, but only external declarations. The implementation is typically done in *foo.c*, which also includes *foo.h*, which contains no external declarations, but only definitions and internal declarations.

Example: 
This is a header for a linked list:
`LL.h`
```C
// Note that the definition of the struct is in the header file!
struct node {
	void *element;
	struct node *next;
};

extern struct node *Push(struct node *head, void *element);
```

`LL.c`
```C
#include "ll.h"
#include <stdio.h>
#include <stdlib.h>
#include <assert.h>

struct node* Push(struct node *head, void *element) {
	//implementation here
}
```

`myScript.c`
```c
#include "ll.h"

int main(int argc, char **argv) {
	struct node *list = NULL;
	char *hi = "hello";
	char *bye = "goodybe";
	list = Push(list, (void *) hi);
	list = Push(list, (void *) bye);
	return 0;
}
```