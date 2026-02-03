**Expected to know:**
**Topics Covered:**
**Tags:**

The functions written in the class.

Works k=just like normal functions.

They are also referred to as Member Functions


However, whenever writing the body of a class, i.e., for example when we write a function in it, then it is not a compulsion to write it in the class itself, we can just declare it like this:

```c
class cat
{
	private:
		int age;
		int DOB;
		int siblings;
		string Name;
	public:
		void setData(int Age, int DoB, int Siblings, string name);
		getData()
		{
			cout<<age<<endl;
			cout<<DOB<<endl;
		}
};


```

and then we can implement function;
```c
void cat :: setData(int Age, int DoB, int Siblings, string name)
{
	age = Age;
	DOB = DoB;
	siblings = Siblings;
	Name = name;
}
```

Now why did we use the keyword `void` before writing cat?  (Will be looked upon later)
And what is ::?




We can also nest the methods inside a class:

When there are  multiple functions to work with in a particular class, then it can be tedious t=to call each and every function everytime we want to use them all together, for example in this:

```c
#include <iostream>
#include <string>
using namespace std;

class binary
{//by Default everything private
	string str;
public:
	void read(void);
	void chk_Bin(void);
	void ones_compliment(void);
	void display(void);
}

void binary:: read(void)
{
	cout<<"Enter the binary number"<<endl;
	cin>>s;
}

void binary:: chk_Bin(void)
{
	for(int i = 0;i<s.length();i++)
	{
		if(s.at(i) != 0 && s.at(i)!=1) //string.at(i) works same as string[i]
		{
			cout<<"Incorrect Binary Number"<<endl;
			exit(0); //exists the code with error as return is flase, i.e., 0
		}
	}
	
}

void binary:: ones_compliment(void)
{
	for(int i = 0; i < s.length(); i++)
	{
		if(s.at(i)=='0')
		{
			s.at(i) = '1';
			
		}else if(s.at(i) == '1'){
			s.at(i) = '0';
		}else
		{
			cout<<"Incorrect Binary Number"<<endl;
			exit(0); //exists the code with error as return is flase, i.e., 0
		}
	}
}

void binary:: display(void)
{
	for(int i=0;i<s.length();i++)
	{
		cout<<"Here is your Binary Number\n"<<s.at(i);
	}
	cout<<endl;
}


int main()
{
	//Here i want to do a series of tasks
	binary Number; //instance of the object
	//task 1
	Number.read();
	//task 2
	Number.chk_Bin();
	//task 3
	Number.display();
	//task 3
	Number.ones_compliment();
	//task4
	Number.display();
	
}
```

Now instead of mentioning all functions individually, we can use then all together by nesting them, as we know that they will appear in grp for sure:

```c
void binary:: read(void)
{
	cout<<"Enter the binary number"<<endl;
	cin>>s;
	
	chk_Bin();
}
```

So whenever, read() is called on any object, chk_Bin() function will automatically be enforced.