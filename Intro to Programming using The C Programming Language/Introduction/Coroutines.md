**Expected to know:**
**Topics Covered:**
**Tags:**

Before we talk about coroutines, first lets have a look at two most important functions, `setJump` and `longJump`:
## setjmp and longjmp in C

**setjmp** and **longjmp** are two functions in C that provide a mechanism for non-local jumps, allowing a program to jump back to a specific point in the call stack, bypassing the usual function call and return sequence. This is often used for error handling, recovery from exceptional conditions, or implementing simple exception handling mechanisms in C.

---

## How They Work

- **setjmp(jmp_buf env):**
    
    - Saves the current execution context (including stack pointer, program counter, and registers) into a buffer of type `jmp_buf`.
        
    - Returns `0` when called directly.
        
    - If control is later transferred back to this point using `longjmp`, `setjmp` returns a non-zero value (the value passed to `longjmp`)[1](https://www.tutorialspoint.com/c_standard_library/c_macro_setjmp.htm)[2](https://en.wikipedia.org/wiki/Setjmp.h)[3](https://www.embecosm.com/appnotes/ean9/html/ch04s01s02.html).
        
- **longjmp(jmp_buf env, int val):**
    
    - Restores the saved environment from the buffer set by `setjmp`.
        
    - Causes `setjmp` to return again, this time with the value `val` (if `val` is zero, it is converted to one)[1](https://www.tutorialspoint.com/c_standard_library/c_macro_setjmp.htm)[2](https://en.wikipedia.org/wiki/Setjmp.h)[3](https://www.embecosm.com/appnotes/ean9/html/ch04s01s02.html).
        
    - Effectively jumps back to the point where `setjmp` was called, skipping any code in between.
        

---

## Basic Example
```c
#include <stdio.h> 
#include <setjmp.h> 

jmp_buf env; 

void error_handler() 
{     
	printf("Error occurred! Jumping back.\n");    
	longjmp(env, 1); // Jump back to setjmp 
} 

int main() 
{     
	if (setjmp(env) == 0) 
	{        
		printf("Normal execution.\n");        
		error_handler(); // This will trigger longjmp        
		printf("This line will not be executed.\n");    
	} else 
	{        
		printf("Returned via longjmp.\n");    
	}    
	
	return 0; 
}
```
**Output:**
```text
Normal execution. 
Error occurred! Jumping back. 
Returned via longjmp.
```
---
## Typical Use Cases
- **Error Handling:**  
    Used to handle errors or exceptional conditions, especially in deeply nested function calls where unwinding the stack manually would be cumbersome.
- **Signal Handling:**  
    Allows a program to recover from signals like segmentation faults or bus errors by jumping out of a signal handler.
- **Embedded Systems:**  
    Used for robust recovery from hardware faults or critical errors.

---
## Important Details and Cautions
- **Stack Unwinding:**  
    `longjmp` does not call destructors or release resources allocated after the corresponding `setjmp`, so you must manually handle resource cleanup[7](https://wiki.sei.cmu.edu/confluence/display/c/MSC22-C.+Use+the+setjmp\(\),+longjmp\(\)+facility+securely)[5](https://dev.to/pauljlucas/setjmp-longjmp-and-exception-handling-in-c-1h7h)[8](https://learn.microsoft.com/en-us/cpp/cpp/using-setjmp-longjmp?view=msvc-170).
- **Scope:**  
    Only jump to a `setjmp` if the function that called `setjmp` is still active on the call stack. Jumping to a `setjmp` in a function that has already returned leads to undefined behavior[7](https://wiki.sei.cmu.edu/confluence/display/c/MSC22-C.+Use+the+setjmp\(\),+longjmp\(\)+facility+securely)[5](https://dev.to/pauljlucas/setjmp-longjmp-and-exception-handling-in-c-1h7h).
- **Non-volatile Variables:**  
    Do not access local (non-volatile) variables after a `longjmp` if they were modified after `setjmp`—their values are indeterminate[7](https://wiki.sei.cmu.edu/confluence/display/c/MSC22-C.+Use+the+setjmp\(\),+longjmp\(\)+facility+securely).    
- **Alternatives:**  
    In C++, prefer using exceptions rather than `setjmp`/`longjmp` due to proper resource management and destructor calls[8](https://learn.microsoft.com/en-us/cpp/cpp/using-setjmp-longjmp?view=msvc-170).

---
## Summary Table

| Function | Purpose                                | Return Value                                      |
| -------- | -------------------------------------- | ------------------------------------------------- |
| setjmp   | Saves current execution environment    | 0 (direct call), non-zero (via longjmp)           |
| longjmp  | Restores saved environment, jumps back | Does not return; causes setjmp to return non-zero |

# Coroutines
Now seeing what we discussed about changing state and giving these functions importance before the topic, you must have gotten the basic idea of what coroutines is.

## Coroutines

**Coroutines** are programming constructs that enable functions to pause execution and later resume from where they left off, supporting cooperative multitasking and asynchronous programming. Unlike traditional subroutines or functions, which run from start to finish, coroutines can suspend their execution (often using keywords like `yield` or `await`) and transfer control back to the caller or another coroutine, then resume later with their local state preserved[1](https://en.wikipedia.org/wiki/Coroutine)[9](https://en.wikipedia.org/wiki/Coroutines)[8](https://www.wikiwand.com/en/articles/Coroutine).

## Key Characteristics

- **Suspend and Resume**: Coroutines can pause execution at certain points and resume later, maintaining their internal state between suspensions[1](https://en.wikipedia.org/wiki/Coroutine)[9](https://en.wikipedia.org/wiki/Coroutines).
    
- **Cooperative Multitasking**: Unlike threads, which are managed by the operating system and can be preempted at any time, coroutines yield control explicitly, allowing multiple tasks to interleave efficiently within a single thread[5](https://techhype.io/articles/what-are-coroutines/?amp=1)[6](https://dotcommagazine.com/2023/12/coroutines-top-ten-things-you-need-to-know-2/)[7](https://startup-house.com/glossary/what-is-coroutine).
    
- **Lightweight**: Coroutines are much lighter than threads, enabling thousands of coroutines to run concurrently with minimal overhead[2](https://kotlinlang.org/docs/coroutines-basics.html)[4](https://developer.android.com/kotlin/coroutines)[6](https://dotcommagazine.com/2023/12/coroutines-top-ten-things-you-need-to-know-2/).
    
- **Asynchronous Programming**: They simplify writing asynchronous, non-blocking code, making it easier to handle tasks like I/O operations, network requests, and event loops without blocking the main thread[4](https://developer.android.com/kotlin/coroutines)[5](https://techhype.io/articles/what-are-coroutines/?amp=1)[7](https://startup-house.com/glossary/what-is-coroutine).
    

## How Coroutines Work

- **Persistence of State**: Local variables within a coroutine persist across suspensions, so when the coroutine resumes, it continues as if it was never interrupted[9](https://en.wikipedia.org/wiki/Coroutines)[8](https://www.wikiwand.com/en/articles/Coroutine).
    
- **Control Flow**: Control can be transferred between coroutines (or between a coroutine and its caller) using language-specific constructs. For example, in Python, `yield` is used; in Kotlin, functions can be marked as `suspend`[2](https://kotlinlang.org/docs/coroutines-basics.html)[5](https://techhype.io/articles/what-are-coroutines/?amp=1)[7](https://startup-house.com/glossary/what-is-coroutine).
    
- **Types**:
    
    - _Asymmetric coroutines_: Yield control only to their caller.
        
    - _Symmetric coroutines_: Can yield control to any other coroutine specified by the programmer[8](https://www.wikiwand.com/en/articles/Coroutine)[9](https://en.wikipedia.org/wiki/Coroutines).
        

## Practical Uses

- **Asynchronous I/O**: Handling network or file operations without blocking the main thread[5](https://techhype.io/articles/what-are-coroutines/?amp=1)[7](https://startup-house.com/glossary/what-is-coroutine).
    
- **Concurrent Tasks**: Running multiple operations concurrently, such as animations, background calculations, or data processing[4](https://developer.android.com/kotlin/coroutines)[5](https://techhype.io/articles/what-are-coroutines/?amp=1).
    
- **Event Loops and Iterators**: Implementing event-driven systems or custom iterators[1](https://en.wikipedia.org/wiki/Coroutine)[9](https://en.wikipedia.org/wiki/Coroutines).
    
- **Simplifying Callback Chains**: Writing sequential-looking code for asynchronous operations, avoiding "callback hell"[4](https://developer.android.com/kotlin/coroutines)[5](https://techhype.io/articles/what-are-coroutines/?amp=1).
    

## Example (Kotlin Coroutine)
```kotlin
import kotlinx.coroutines.*

fun main() = runBlocking {
    launch { // launches a new coroutine
        delay(1000L)
        println("World!")
    }
    println("Hello") // prints before "World!"
}

```
In this example, `launch` creates a coroutine that suspends for 1 second before printing "World!", while the main coroutine continues and prints "Hello" first[2](https://kotlinlang.org/docs/coroutines-basics.html)[5](https://techhype.io/articles/what-are-coroutines/?amp=1).

## Advantages
- **Improved Code Readability**: Asynchronous code can be written in a sequential style[5](https://techhype.io/articles/what-are-coroutines/?amp=1)[7](https://startup-house.com/glossary/what-is-coroutine).
- **Resource Efficiency**: Lower overhead compared to threads, enabling high concurrency[4](https://developer.android.com/kotlin/coroutines)[6](https://dotcommagazine.com/2023/12/coroutines-top-ten-things-you-need-to-know-2/).    
- **Simplified Error Handling**: Structured concurrency and built-in cancellation support in modern coroutine implementations[4](https://developer.android.com/kotlin/coroutines).
