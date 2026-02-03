**Expected to know:**
**Topics Covered:**
**Tags:**

So what happens is that the things that are common to all the objects of a class are stores at a single memory in order to optimize stuff.

Now how does array work in a class, and how is memory allocated to all objects?

lets say we have a class:

```c
#include <iostream>
#include <string>
using namespace std;

class shop
{
	int counter;
	
    struct item
    {
        string name;
        int ID;
        int price;
        int quantity;
    };
	
    struct item items[100];
	
public:
	
	void initializeCounter(int no)
	{
		counter = no;
	}
	
    void getInp(struct item *i)
    {
        cout << "Enter the name of the item: ";
        cin >> i->name;
        cout << "Enter the ID of the item: ";
        cin >> i->ID;
        cout << "Enter the price of the item: ";
        cin >> i->price;
        cout << "Enter the quantity of the item: ";
        cin >> i->quantity;
    }
	
    void getPrice(int ID)
    {
        for (int i = 0; i < 100; i++)
        {
            if(items[i].ID == ID)
            {
                cout<<"The cost of "<<ID<<" is:\n"<<items[i].price<<endl;
                return;
            }
        }
        cout<<"Item not found"<<endl;
        
        return;
    }
    void setPrice(int ID);
	
};

void shop::setPrice(int ID)
{
    for (int i = 0; i < 100; i++)
    {
        if(items[i].ID == ID)
        {
            cout<<"Enter the new price of the item: ";
            cin>>items[i].price;
            return;
        }
    }
    cout<<"Item not found"<<endl;
    return;
}

```


Summarizing the above class:
- Each object of that class has an identity called a counter.
- each shop will have a 100 items, each has it's own ID, Name, Price and Quantity as mentioned in the struct `item`
- It has 4 public functions:
	- `initializeCounter()` sets the counter number of each object to the given value.
	- `getInp()` takes in the user input for all items
	- `getPrice()` shows the price of the item with the given ID
	- `setPrice()` can change or assign the price to an item of a particular item ID

Now in this class, if I give in the input, in which the price or any other attribute of an item in our class is same in value, then instead of storing the same value of each unique attribute in our array of item in different places, it stores them at the same address, i.e., it all refers to a single address where that value is stored.

And whenever, a change is made in the value of even a one item's attribute such that it's value changes to something that is actually new, then and then only a new memory space is used up in the heap of memory for that value.