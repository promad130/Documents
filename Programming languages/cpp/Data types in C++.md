**Expected to know:** [[Introduction to Programming]]
**Tags:** [[Data Types and Constants]]
## Primitive Data Types

C++ has several built-in primitive (fundamental) data types:

| Type                  | Keyword   | Size         | Example                           |
| --------------------- | --------- | ------------ | --------------------------------- |
| Integer               | `int`     | 4 bytes      | `int age = 25;`                   |
| Character             | `char`    | 1 byte       | `char grade = 'A';`               |
| Boolean               | `bool`    | 1 byte       | `bool isActive = true;`           |
| Floating Point        | `float`   | 4 bytes      | `float price = 10.5f;`            |
| Double Floating Point | `double`  | 8 bytes      | `double pi = 3.14159;`            |
| Void                  | `void`    | N/A          | Used for functions with no return |
| Wide Character        | `wchar_t` | 2 or 4 bytes | `wchar_t letter = L'好';`          |

## Modified Types
You can use modifiers like `short`, `long`, `signed`, `unsigned`:
- `short int` (2 bytes)
- `long int` (4-8 bytes)
- `long long int` (8 bytes)
- `unsigned int` (0 to 4,294,967,295)

## String Data Type

**String is NOT primitive** - it's an **object-oriented** type from the Standard Template Library (STL). You must include `<string>` to use it:
```cpp
#include <string> 
std::string name = "John"; 
std::string city = "Mumbai";
```

## Using Float and Double with std::cout

```cpp
#include <iostream>
using namespace std;

int main() {
    float price = 99.99f;
    double precision = 3.14159265359;
    
    cout << "Float: " << price << endl;
    cout << "Double: " << precision << endl;
    
    return 0;
}

```

By default, `std::cout` displays floating-point numbers with only **6 total digits** (before and after the decimal point), and it automatically rounds the last digit. This causes precision loss for numbers with more digits.​

Without precision control:
```cpp
double pi = 3.14159265359; 
cout << pi;  // Output: 3.14159 (only 6 digits total)
```
### `std::fixed` Manipulator
Forces **fixed-point notation** instead of scientific notation. It ensures the number displays with a decimal point and the exact number of decimal places you specify.
### `std::setprecision(n)` Manipulator
Sets how many digits to display:
- **Without `fixed`**: `n` = total significant digits (before + after decimal)
- **With `fixed`**: `n` = digits **after** the decimal point only

```cpp
#include <iostream>
#include <iomanip>  // Required for setprecision and fixed
using namespace std;

int main() {
    double price = 99.99;
    double pi = 3.14159265359;
    
    // 2 decimal places
    cout << fixed << setprecision(2) << price << endl;  // 99.99
    
    // 10 decimal places
    cout << fixed << setprecision(10) << pi << endl;  // 3.1415926536
    
    return 0;
}
```