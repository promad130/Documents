**Expected to know:** [[Object Oriented Programming]]
**Topics Covered:**
**Tags:**

## **Static Variables /Class Variables**

Now, lets say I want to create a variable which should remain same in all the objects of a created class, and should be aware of the objects created under that class.
Then that is when we create something called a "Static Attribute".

```c
class Employee
{
	int id;
	int count;
	
	public:
	void SetData(void)
	{
		cout<<"Enter the ID"<<endl;
		cin>>id;
		count++;
	}
	void GetData(void)
	{
		cout<<"Here is the ID;\n";
		cout<<id;
	}
};
```

Now in order to keep note of number of employees created, i'll make the variable called "`count`" static;

```c
class Employee
{
	int id;
	static int count; //initializing a static variable
	
	public:
	void SetData(void)
	{
		cout<<"Enter the ID"<<endl;
		cin>>id;
		count++;
	}
	void GetData(void)
	{
		cout<<"Here is the ID;\n";
		cout<<id<<endl<<"Here is the employee number"<<count;
	}
};
//making the programme aware of this static variable
int Employee:: count; //default value is 0

```

Now that the `count` is static, it will automatically be initialized from `0`.
Let's keep the count of number of variables;

- As the static variable is a property of a class, when initializing an object, if the value of this variable is affected, then it would be changed in the root class as well
- i.e., if I create an object of class Employee, and run `SetData()`, then the value of `count` would change from `0` to `1` (due to `count++;`), and upon creating next object and calling `GetData()` as soon as the object is created(i.e., without calling `SetData()`), we will find that the value of count is `1`, and not `0` even though it's initial value in the class was `0`.
- This happened as count is `static` and affecting a static variable in a single object will change it's value in the root class and all the objects created.

If you declare a static member variable inside a class but do not define it outside the class, you will encounter a linker error `if you try to use it`. This is because the declaration inside the class only tells the compiler that the variable exists, but it does not allocate memory for it.

In C++, static member variables must be both declared inside the class and defined outside the class. The declaration inside the class is used for syntax checking and to inform the compiler that the variable exists, while the definition outside the class allocates memory for the variable.
(add example and be more clear abt what is written or trying to be explained)

---
## **Static Methods(functions)/Class Methods**

*Now why do we a static function?* Why would I need a function that remains the same for all methods and classes? isn't that a property of a function by default? Once defined cannot change and if changed, would be changed for all the objects?

But a static function is a function that can access only a static members.

so in our previous code, we make some modifications and introduce a new static function;

```c
class Employee
{
	int id;
	static int count; //initializing a static variable
	bool hasCalled = false;
	public:
	void SetData(void)
	{
		if(hasCalled)
		{
			cout<<"Has already assigned an ID, cannot change it!"<<endl;
			return;
		}else{
		cout<<"Enter the ID"<<endl;
		cin>>id;
		count++;
		hasCalled = true;}
	}
	void GetData(void)
	{
		cout<<"Here is the ID;\n";
		cout<<id<<endl<<"Here is the employee number"<<count;
	}
	static void GetCount(void)
	{
		cout<<count<<endl;
	}
};
//making the programme aware of this static variable
int Employee:: count; //default value is 0

```

So the static function can only access the static variables, if it tries to access any other variable, then it would throw an error.

Whenever using the static method, we have to use a scope resolution operator:

```c
int main()
{
	Employee Rakesh;
	Rakesh.SetData();
	Employee::GetCount();
}
```

Or we can call it via an object of that class:
```c
Rakesh.GetCount();
```