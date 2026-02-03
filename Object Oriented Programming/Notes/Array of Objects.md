**Expected to know:**
**Topics Covered:**
**Tags:**

We can create an array of objects as well. For example we have Employee class like this:

```c
#include <iostream>
#include <string>
using namespace std;
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
		cout<<id<<endl;
	}
	static void GetCount(void)
	{
		cout<<count<<endl;
	}
};
//making the programme aware of this static variable
int Employee:: count; //default value is 0

int main()
{
	Employee A,B,C,D;
	
}
```

So, if I want to store data of like 1000 employees, then rather than creating each object individually, it would be sensible to make an array instead:

```c

int main()
{
	Employee employees[1000];
	for(int i = 0; i<employees.length();i++)
	{
		employees[i].GetData();
	}
}
```