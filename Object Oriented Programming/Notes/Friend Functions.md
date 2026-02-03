**Expected to know:**
**Topics Covered:**
**Tags:**

First lets take the class Complex Again:

```c
#include <iostream>
#include <string>
using namespace std;

class Complex
{
	int real, imag;
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

Now creating a two complex number:

```c
int main(
Complex c1,c2;
c1.setData(1,3);
c2.setData(5,7);
)
```

So now what if we want to create a function outside the class and want only that function to be able to access the private members of our class complex.

Lets say I create a function called `SumComplex()`

```c
Complex SumComplex(Complex c1,Complex c2)
{
	Complex sum;
	sum.setData((c1.real + c2.real),(c1.imag+c2.imag));
	return sum;
}
```

So this would originally throw an error, as we are trying to access the private members of both the parameters `c1`, and `c2`.

This is the function that we want to have the access of the private members and methods of our class, so we use something called `friend` in our class.

```c
#include <iostream>
#include <string>
using namespace std;

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

```

and now we can use `SumComplex()` without any issue or errors, so if I wanted to have sum of `c1` and `c2` defined earlier;

```c
int main()
{
	Complex c1,c2;
	c1.setData(1,3);
	c2.setData(5,7);
	
	Complex sum = SumCompelx(c1,c2);
	return 0;
}
```

---
# How is it different from member functions?

One might argue that how is it different from:

```c
#include <iostream>
#include <string>
using namespace std;

class Complex
{
	int real, imag;

	public:
	void setData(int r, int z)
	{
		real = r;
		imag = z;
	}
	Complex SumNumber(Complex c1,Complex c2);
	void showData()
	{
		cout << real << " + i" << imag << endl;
	}
};

Complex Complex:: SumNumber(Complex c1,Complex c2)
{
	Complex temp;
	temp.real = c1.real + c2.real;
	temp.imag = c1.imag + c2.imag;
	return temp;
}

int main()
{
	Complex c1,c2;
	c1.setData(3,4);
	c2.setData(5,6);	

	Complex sum;
	sum = sum.SumNumber(c1,c2);
	cout << "Sum of two complex numbers is: ";
	sum.showData();
	return 0;

}
```

But a friend function is not a member function, i.e., it can be called independently as it is not a pointer to any object of the class, which gives more scope and flexibility to the programmer and in the code it simplifies things at a higher level.

Hence a friend function is:
- Not in scope of a class
- Since not in scope of class, cannot be called via object of a class
- It has to be invoked independently without the help of any object of the class
- Can be declared inside either public or private part of a class
- Cannot access the members directly by the name, and need a object to access them, as an attribute (members of  a class) is of an object, not a independent property