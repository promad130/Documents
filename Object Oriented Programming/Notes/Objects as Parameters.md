**Expected to know:**
**Topics Covered:**
**Tags:**

Consider a class of complex numbers that can perform a complex caluclation given as:

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

	void SetDataAsSum(Complex c1, Complex c2)
	{
		real = c1.real + c2.real;
		imag = c1.imag + c2.imag;
	}
};

int main()
{
	
}
```

So here, we have made a class for a complex number, and what this will do is it will take in the value of the complex number, and create a complex number.

Now we can see that `SetDataAsSum()` is a function that takes in the objects of the class it belongs to and then woks on them.