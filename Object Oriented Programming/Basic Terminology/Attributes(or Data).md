**Expected to know:** [[Variables and their scope]], [[Class or Type]],
**Topics Covered:**
**Tags:** [[Variables and their scope]]

This is the variables for a particular class, which gives the class it's attributes, i.e., which gives the class its attributes. 

Just like in procedural programming, they can store values with/on which we can work. By default, everything in a class is private. 
So upon defining something like this:

```c
class trial
{
	int hello;
	int a;
};

int main()
{
	trial FirstObject;
	int b = FirstObject.a;
}
```

Would throw an error as we cannot access `a` as it is private.

However, we can change it's accessibility by using access modifiers.(covered later on)

---

