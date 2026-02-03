![[Object Oriented Programming#Encapsulation]]

Now what if in a [[Class or Type]], I don't want to let the users or anyone access the variables directly, I want them to access them in a particular way that I want, so that is when I apply a method called Encapsulation.

So in the class of 'Employee':
```C++
class Employee
{
	public:
	string Name;
	string Company;
	int Age;

	void IntroduceYourself(){
		cout<<"Name - "<< Name<<endl;
	}
	Employee(string name, string company, int age){
	Name = name;
	Company = company;
	Age = age;
	}
};
```
For example I dont want to give user the access to the members of the class directly, they can have access, but just not directly in this case.

In order to achive this goal, I encapsulate the class `Employee` by making functions/ methods called Getter method and setter methods.

So first, I make the ,embers private:
```
class Employee
{
	private:
	string Name;
	string Company;
	int Age;
	public:
	void SetName(string name)
	{
		Name = name;
	}
	string getName()
	{
		return Name;
	}
	void IntroduceYourself(){
		cout<<"Name - "<< Name<<endl;
	}
	Employee(string name, string company, int age){
	Name = name;
	Company = company;
	Age = age;
	}
};
```
So here, the function GetName() and SetName() are the Getter and Setter functions respectively.

So for example, if the age is more than 18, then and then only I want my user to be able to change the name:
class Employee
```

{
	private:
	string Name;
	string Company;
	int Age;
	public:
	void SetName(string name)
	{
		Name = name;
	}
	string getName()
	{
		return Name;
	}

	int GetAge(){return Age;}
	void SetAge(int age)
	{
		if(Age>=18)
		{
			Age = age;
		}
	}

	void IntroduceYourself(){
		cout<<"Name - "<< Name<<endl;
	}
	Employee(string name, string company, int age){
	Name = name;
	Company = company;
	Age = age;
	}
};
```
So here we have granted the user the access,  but a limited access.

