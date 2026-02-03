**Expected to know:**
**Topics Covered:**
**Tags:**

Now that we have created a friend function in a class, which have access to the privates of our class but is not a member of that class, we will now look at the Friend classes and Member Friend Functions.

In order to understand what friend class means and why we need it, first lets make a class:

```c
class Complex
{
	int real, imag;
public:
	void setData(int r, int z)
	{
		real = r;
		imag = z;
	}
	friend Complex SumComplex(Complex c1, Complex c2);	
	void printNumber(void)
	{
		cout<<"Number is:"<<real<<" + i"<<imag<<endl;
	}
	
};

Complex SumComplex(Complex c1, Complex c2)
{
	Complex c3;
	c3.setData(c1.real +  c2.real, c2.imag + c1.imag);
	return c3;
}

```

Now lets create another class called calculator:

```c
class Calculator
{
	public:
		int add (int a, int b)
		{
			return (a+b);
		}
};
```

Now what if I wanted a function in our calculator class to access the private members of our complex class? Or maybe allow only a few/selected functions of our Calculator class to access the private members of our Complex class?

# Declaring individual function of another class as friend 

So we first pre-declare the class;

```c
class calculator; //predeclaring the class

class Complex
{
	int real, imag;
	 
public:
	void setData(int r, int z)
	{
		real = r;
		imag = z;
	}
	friend Complex SumComplex(Complex c1, Complex c2);	
	void printNumber(void)
	{
		cout<<"Number is:"<<real<<" + i"<<imag<<endl;
	}
	
};
class calculator
{
	 public:
		int add (int a, int b)
		{
			return (a+b);
		}
		
		
};
```

Now I want to declare a function `SumComplex()` inside the calculator class which shoudl be able to access the members of the class Complex:

```c
Complex SumComplex(Complex c1, Complex c2)
		{
			Complex c3;
			c3.setData(c1.real +  c2.real, c2.imag + c1.imag);
			return c3;
		}
```


so for that we declare the function a friend in the complex class:

```c
class calculator; //predeclaring the class

class Complex
{
	int real, imag;
	friend Complex calculator:: SumComplex(Complex c1, Complex c2);
public:
	void setData(int r, int z)
	{
		real = r;
		imag = z;
	}
	friend Complex SumComplex(Complex c1, Complex c2);	
	void printNumber(void)
	{
		cout<<"Number is:"<<real<<" + i"<<imag<<endl;
	}
	
};
class calculator
{
	 public:
		int add (int a, int b)
		{
			return (a+b);
		}
		Complex SumComplex(Complex c1, Complex c2)
		{
			Complex c3;
			c3.setData(c1.real +  c2.real, c2.imag + c1.imag);
			return c3;
		}

		
};
```

But here, we would encounter an issue as even though the compiler knows that the class calculator exists, it does not know about the functions in the class Calculator, so we change the code and :

```c
#include <iostream>
using namespace std;

class Complex;

class calculator
{
	 public:
		int add (int a, int b)
		{
			return (a+b);
		}
		Complex SumComplex(Complex c1, Complex c2)
		{
			Complex c3;
			c3.setData(c1.real +  c2.real, c2.imag + c1.imag);
			return c3;
		}
};


class Complex
{
	int real, imag;
	friend Complex calculator::SumComplex(Complex c1, Complex c2);
public:
	void setData(int r, int z)
	{
		real = r;
		imag = z;
	}
	void printNumber(void)
	{
		cout<<"Number is:"<<real<<" + i"<<imag<<endl;
	}
};

```

But now, there would still be an issue, as the same issue that happened with the calculator class will happen again as the compiler only knows of the complex class, not of the contents within it, so here we configure our code again and we implement our function of `SumComplex()` later:

```c
#include <iostream>
using namespace std;

class Complex;

class calculator
{
	 public:
		int add (int a, int b)
		{
			return (a+b);
		}
		Complex SumComplex(Complex c1, Complex c2);
};


class Complex
{
	int real, imag;
	friend Complex calculator::SumComplex(Complex c1, Complex c2);
public:
	void setData(int r, int z)
	{
		real = r;
		imag = z;
	}
	void printNumber(void)
	{
		cout<<"Number is:"<<real<<" + i"<<imag<<endl;
	}
};


Complex calculator::SumComplex(Complex c1, Complex c2)
{
	Complex c3;
	c3.setData(c1.real +  c2.real, c2.imag + c1.imag);
	return c3;
}
```

And that is how we make an individual function of a class have access to members of another class.

# Making a whole class a friend 

If we don't mind other function of the class calculator having access to class complex, then we can make the whole class calculator as a friend rather than making individual function as a friend:

```c
#include <iostream>
using namespace std;

class Complex;

class Calculator
{
	int ans;
	public:
		void ViewAns(void)
		{
			cout<<ans<<endl;
		}
		int add(int a, int b)
		{
			return a+b;
		}
		
		Complex SumComplex(Complex c1, Complex c2);
};

class Complex
{
	int real;
	int imag;
	
	friend class Calculator;
	
	public:
		void setData(int r, int z)
		{
			real = r;
			imag = z;
		}
};

Complex Calculator:: SumComplex(Complex c1,Complex c2)
{
	Complex c3;
	c3.setData(c1.real +  c2.real, c2.imag + c1.imag);
	return c3;
}
```