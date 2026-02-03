## Table of Contents
1. [Introduction to I/O](#introduction-to-io)
2. [Abstract Data Type (ADT) Perspective](#abstract-data-type-perspective)
3. [Stream Concept](#stream-concept)
4. [Stream Objects](#stream-objects)
5. [Byte Streams](#byte-streams)
6. [Character Streams (Readers and Writers)](#character-streams)
7. [File Class](#file-class)
8. [FileInputStream](#fileinputstream)
9. [InputStreamReader](#inputstreamreader)
10. [BufferedReader](#bufferedreader)
11. [Wrapping Streams](#wrapping-streams)
12. [File Output Streams](#file-output-streams)
13. [Advanced I/O Concepts](#advanced-io-concepts)
14. [Complete Working Examples](#complete-working-examples)

---
## Introduction to I/O
### What is I/O?
**I/O (Input/Output)** is the mechanism by which Java programs **read data from external sources** (input) and **write data to external destinations** (output).

### Types of I/O Sources:
- **Files** on disk
- **Console** (keyboard input, screen output)
- **Network sockets**
- **Pipes**
- **Memory buffers**

### Key Concepts:
- **Input**: Reading data FROM an external source INTO the program
- **Output**: Writing data FROM the program TO an external destination
- **Stream**: A sequence of bytes flowing in one direction

---
## Abstract Data Type (ADT) Perspective
### Stream as an ADT
#### Data
- **Sequential bytes** in a specific order
- **Position indicator**: Current location in the stream
- **Status information**: End-of-file, error status, etc.

#### Operations
```
read()           - Retrieve the next byte/character
write(data)      - Send data to the stream
available()      - Check how many bytes are ready
close()          - Release resources
flush()          - Force pending data to be sent
skip(n)          - Skip n bytes
mark()           - Mark current position
reset()          - Return to marked position
```

### Stream ADT Specification
```
ADT Stream {
  Data: 
    - sequence of bytes
    - current position
    - end-of-stream flag
    
  Operations:
    - read(): byte              // Read next byte, return -1 if EOF
    - write(byte): void         // Write byte to stream
    - available(): int          // Number of bytes available
    - close(): void             // Close stream and free resources
    - skip(long): long          // Skip n bytes, return skipped count
    - flush(): void             // Force data to be written
}
```

## Stream Concept
### What is a Stream?
A **stream** is an **abstract representation of a sequence of data** flowing from a source to a destination, one item at a time.

### Key Characteristics
1. **Sequential**: Data flows in order
2. **Unidirectional**: Either input OR output (not both simultaneously)
3. **Serial**: One byte/character at a time
4. **Buffered**: Often data is buffered for efficiency

### Two Types of Streams

#### 1. Byte Streams
- **Unit**: Individual bytes (0-255)
- **Classes**: InputStream, OutputStream
- **Use**: Binary data, images, compiled code
- **Superclasses**: java.io.InputStream, java.io.OutputStream

#### 2. Character Streams
- **Unit**: Individual characters (supports Unicode)
- **Classes**: Reader, Writer
- **Use**: Text data, files with different character encodings
- **Superclasses**: java.io.Reader, java.io.Writer

### Stream Hierarchy
```
Object
├── InputStream (abstract class)
│   ├── FileInputStream
│   ├── ByteArrayInputStream
│   ├── FilterInputStream (abstract)
│   │   ├── BufferedInputStream
│   │   └── DataInputStream
│   └── PipedInputStream
│
├── OutputStream (abstract class)
│   ├── FileOutputStream
│   ├── ByteArrayOutputStream
│   ├── FilterOutputStream (abstract)
│   │   ├── BufferedOutputStream
│   │   └── DataOutputStream
│   └── PipedOutputStream
│
├── Reader (abstract class)
│   ├── FileReader
│   ├── CharArrayReader
│   ├── InputStreamReader
│   ├── BufferedReader
│   └── PushbackReader
│
└── Writer (abstract class)
    ├── FileWriter
    ├── CharArrayWriter
    ├── OutputStreamWriter
    ├── BufferedWriter
    └── PrintWriter
```

---
## Stream Objects
### Base Classes for Byte Stream
#### InputStream (Abstract Class)
**Purpose**: Base class for all byte input streams

**Key Methods**:
```java
int read()                          // Read single byte, return -1 if EOF
int read(byte[] b)                  // Read array of bytes, return count
int read(byte[] b, int off, int len) // Read len bytes into array at offset
int available()                     // Number of bytes available
void close()                        // Close stream
long skip(long n)                   // Skip n bytes
boolean markSupported()             // Check if mark/reset supported
void mark(int readlimit)            // Mark current position
void reset()                        // Reset to marked position
```

#### OutputStream (Abstract Class)
**Purpose**: Base class for all byte output streams
**Key Methods**:
```java
void write(int b)                   // Write single byte
void write(byte[] b)                // Write array of bytes
void write(byte[] b, int off, int len) // Write len bytes from array
void flush()                        // Force pending data to be written
void close()                        // Close stream
```

### Base Classes for Character Streams
#### Reader (Abstract Class)
**Purpose**: Base class for all character input streams

**Key Methods**:
```java
int read()                          // Read single character
int read(char[] cbuf)               // Read array of characters
int read(char[] cbuf, int off, int len) // Read len chars into array
int read(java.nio.CharBuffer target) // Read into CharBuffer
boolean ready()                     // Check if data available
void close()                        // Close reader
long skip(long n)                   // Skip n characters
boolean markSupported()             // Check if mark/reset supported
void mark(int readlimit)            // Mark current position
void reset()                        // Reset to marked position
```

#### Writer (Abstract Class)

**Purpose**: Base class for all character output streams

**Key Methods**:
```java
void write(int c)                   // Write single character
void write(char[] cbuf)             // Write array of characters
void write(char[] cbuf, int off, int len) // Write len chars from array
void write(String str)              // Write string
void write(String str, int off, int len) // Write portion of string
void flush()                        // Force pending data to be written
void close()                        // Close writer
Writer append(char c)               // Append character
Writer append(CharSequence csq)     // Append character sequence
```

### Example: Basic Stream Operations

```java
import java.io.*;

public class BasicStreamOperations {
    public static void main(String[] args) {
        // Byte stream example
        try (InputStream in = System.in;
             OutputStream out = System.out) {
            
            byte[] buffer = new byte[10];
            int bytesRead = in.read(buffer);  // Read bytes
            System.out.println("Read " + bytesRead + " bytes");
            
            out.write("Hello".getBytes());    // Write bytes
            out.flush();                      // Force write
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
```

---
## Byte Streams
### FileInputStream
**Purpose**: Read bytes from a file
**ADT Operations**: read(), available(), close()

**Constructors**:
```java
FileInputStream(String filename)        // Create stream from filename
FileInputStream(File file)              // Create stream from File object
FileInputStream(FileDescriptor fd)      // Create stream from descriptor
```

**Key Methods**:
```java
int read()                              // Read single byte (-1 if EOF)
int read(byte[] b)                      // Read up to b.length bytes
int read(byte[] b, int off, int len)   // Read len bytes into array
int available()                         // Bytes available without blocking
void close()                            // Close file
long skip(long n)                       // Skip n bytes
```

### FileInputStream Example

```java
import java.io.*;

public class FileInputStreamDemo {
    public static void main(String[] args) {
        try (FileInputStream fis = new FileInputStream("input.txt")) {
            
            // Method 1: Read single bytes
            int byteData;
            System.out.println("Reading single bytes:");
            while ((byteData = fis.read()) != -1) {
                System.out.print((char) byteData);
            }
            System.out.println();
            
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
```

### FileInputStream with Buffering
Reading byte-by-byte is inefficient. Use an array buffer:

```java
import java.io.*;

public class FileInputStreamBuffer {
    public static void main(String[] args) {
        try (FileInputStream fis = new FileInputStream("input.txt")) {
            
            byte[] buffer = new byte[1024];  // 1KB buffer
            int bytesRead;
            
            System.out.println("File contents:");
            while ((bytesRead = fis.read(buffer)) != -1) {
                System.out.write(buffer, 0, bytesRead);
            }
            System.out.println();
            
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
```

### FileOutputStream
**Purpose**: Write bytes to a file

**Constructors**:
```java
FileOutputStream(String filename)       // Create/overwrite file
FileOutputStream(File file)             // Create/overwrite file
FileOutputStream(String filename, boolean append) // Append mode
FileOutputStream(File file, boolean append) // Append mode
```

**Example**:
```java
import java.io.*;

public class FileOutputStreamDemo {
    public static void main(String[] args) {
        String data = "Hello, World!\nJava I/O Example";
        
        try (FileOutputStream fos = new FileOutputStream("output.txt")) {
            fos.write(data.getBytes());     // Write string as bytes
            fos.flush();                    // Ensure data is written
            System.out.println("File written successfully");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
```

### Copying Files Using Byte Streams
```java
import java.io.*;

public class FileCopy {
    public static void main(String[] args) {
        try (FileInputStream fis = new FileInputStream("source.txt");
             FileOutputStream fos = new FileOutputStream("destination.txt")) {
            
            byte[] buffer = new byte[4096];
            int bytesRead;
            long totalBytes = 0;
            
            while ((bytesRead = fis.read(buffer)) != -1) {
                fos.write(buffer, 0, bytesRead);
                totalBytes += bytesRead;
            }
            
            System.out.println("Copied " + totalBytes + " bytes");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
```

---

## Character Streams (Readers and Writers)
### Why Character Streams?
1. **Unicode Support**: Handles characters from any language
2. **Encoding**: Automatically converts bytes to characters
3. **Convenience**: String operations instead of byte arrays
4. **Clarity**: More intuitive for text processing

### FileReader
**Purpose**: Read characters from a file
**ADT Operations**: read(), ready(), close()

**Constructors**:
```java
FileReader(String filename)             // Use default charset
FileReader(File file)                   // Use default charset
FileReader(FileDescriptor fd)           // Read from descriptor
FileReader(String name, Charset cs)     // Specify charset
FileReader(File file, Charset cs)       // Specify charset
```

**Example**:
```java
import java.io.*;

public class FileReaderDemo {
    public static void main(String[] args) {
        try (FileReader fr = new FileReader("input.txt")) {
            
            int charData;
            System.out.println("File contents:");
            while ((charData = fr.read()) != -1) {
                System.out.print((char) charData);
            }
            System.out.println();
            
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
```

### FileReader with Character Array
```java
import java.io.*;

public class FileReaderBuffer {
    public static void main(String[] args) {
        try (FileReader fr = new FileReader("input.txt")) {
            
            char[] buffer = new char[1024];
            int charsRead;
            
            while ((charsRead = fr.read(buffer)) != -1) {
                System.out.print(new String(buffer, 0, charsRead));
            }
            
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
```

### FileWriter
**Purpose**: Write characters to a file
**Constructors**:
```java
FileWriter(String filename)             // Create/overwrite
FileWriter(File file)                   // Create/overwrite
FileWriter(String filename, boolean append) // Append mode
FileWriter(String name, Charset cs)     // Specify charset
```

**Example**:
```java
import java.io.*;

public class FileWriterDemo {
    public static void main(String[] args) {
        String[] lines = {
            "Line 1: Hello",
            "Line 2: World",
            "Line 3: Java I/O"
        };
        
        try (FileWriter fw = new FileWriter("output.txt")) {
            
            for (String line : lines) {
                fw.write(line);
                fw.write("\n");              // Write newline
            }
            fw.flush();
            System.out.println("File written");
            
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
```

---
## File Class
### What is File Class?
The **File class** represents a file or directory **path** in the file system. It doesn't contain the file data itself.

### Key Methods
```java
// Constructor
File(String pathname)                   // Create from path string
File(File parent, String child)         // Create from parent and child
File(String parent, String child)       // Create from path strings

// Path Information
String getPath()                        // Get path string
String getAbsolutePath()                // Get absolute path
String getCanonicalPath()               // Get canonical path
String getName()                        // Get filename only
String getParent()                      // Get parent directory path
File getParentFile()                    // Get parent as File object
File getAbsoluteFile()                  // Get absolute File object

// File Status
boolean exists()                        // Check if file/directory exists
boolean isFile()                        // Check if it's a file
boolean isDirectory()                   // Check if it's a directory
boolean isAbsolute()                    // Check if path is absolute
boolean canRead()                       // Check if readable
boolean canWrite()                      // Check if writable
boolean canExecute()                    // Check if executable

// File Properties
long length()                           // File size in bytes
long lastModified()                     // Last modification time
boolean isHidden()                      // Check if hidden

// File Operations
boolean createNewFile()                 // Create empty file
boolean mkdir()                         // Create directory
boolean mkdirs()                        // Create directories recursively
boolean delete()                        // Delete file/directory
boolean renameTo(File dest)             // Rename file
boolean setReadable(boolean)            // Set readable permission
boolean setWritable(boolean)            // Set writable permission
boolean setLastModified(long time)      // Set modification time

// Directory Listing
String[] list()                         // List files in directory
File[] listFiles()                      // List files as File objects
```

### File Class Examples

```java
import java.io.*;

public class FileClassDemo {
    public static void main(String[] args) {
        // Create File object
        File file = new File("myfile.txt");
        
        // Check file status
        if (file.exists()) {
            System.out.println("File exists");
            System.out.println("Absolute path: " + file.getAbsolutePath());
            System.out.println("File size: " + file.length() + " bytes");
            System.out.println("Can read: " + file.canRead());
            System.out.println("Can write: " + file.canWrite());
        } else {
            System.out.println("File does not exist");
        }
        
        // Create new file
        try {
            if (file.createNewFile()) {
                System.out.println("File created");
            } else {
                System.out.println("File already exists");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        
        // List directory contents
        File dir = new File(".");
        System.out.println("\nDirectory contents:");
        File[] files = dir.listFiles();
        if (files != null) {
            for (File f : files) {
                String type = f.isDirectory() ? "[DIR]" : "[FILE]";
                System.out.println(type + " " + f.getName());
            }
        }
    }
}
```

### Recursive Directory Listing

```java
import java.io.*;

public class DirectoryLister {
    static void listDir(File dir, int indent) {
        String indentation = "  ".repeat(indent);
        
        File[] files = dir.listFiles();
        if (files == null) return;
        
        for (File file : files) {
            if (file.isDirectory()) {
                System.out.println(indentation + "[DIR] " + file.getName());
                listDir(file, indent + 1);  // Recurse into directory
            } else {
                System.out.println(indentation + "[FILE] " + file.getName() + 
                                  " (" + file.length() + " bytes)");
            }
        }
    }
    
    public static void main(String[] args) {
        File dir = new File(".");
        System.out.println("Directory Structure:");
        listDir(dir, 0);
    }
}
```

---

## InputStreamReader
BufferedReader### What is InputStreamReader?
**InputStreamReader** is a **bridge** between byte streams and character streams. It converts bytes (from a ByteStream) into characters using a specified charset (encoding).

### Purpose
- Adapt byte streams to character streams
- Handle character encoding/decoding
- Support different character sets (UTF-8, ISO-8859-1, etc.)

### Constructors

```java
InputStreamReader(InputStream in)           // Use default charset
InputStreamReader(InputStream in, String charsetName) // Specify encoding
InputStreamReader(InputStream in, Charset cs)  // Use Charset object
```

### Methods
```java
String getEncoding()                    // Get charset name
// Inherited from Reader:
int read()                              // Read single character
int read(char[] cbuf)                   // Read array of characters
```

### InputStreamReader Example
```java
import java.io.*;
import java.nio.charset.StandardCharsets;

public class InputStreamReaderDemo {
    public static void main(String[] args) {
        // InputStreamReader wraps FileInputStream to read characters
        try (InputStreamReader isr = new InputStreamReader(
                new FileInputStream("input.txt"),
                StandardCharsets.UTF_8)) {
            
            int charData;
            System.out.println("File contents:");
            while ((charData = isr.read()) != -1) {
                System.out.print((char) charData);
            }
            System.out.println();
            System.out.println("Encoding: " + isr.getEncoding());
            
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
```

### Why Use InputStreamReader?
Consider reading from System.in (which is a byte stream)
```java
import java.io.*;

public class ReadingFromConsole {
    public static void main(String[] args) {
        // Without InputStreamReader - byte stream
        // int byteData = System.in.read();  // Gets byte, not character
        
        // With InputStreamReader - character stream
        try (InputStreamReader isr = new InputStreamReader(System.in)) {
            System.out.print("Enter a character: ");
            int charData = isr.read();
            System.out.println("You entered: " + (char) charData);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
```

---
## BufferedReader
### What is BufferedReader?
**BufferedReader** wraps a Reader to provide **buffering and line-reading capabilities**.

### Purposes
1. **Buffering**: Improves performance by reading chunks of data
2. **Line reading**: Read entire lines with newline handling
3. **Marking**: Support mark and reset operations
4. **Efficiency**: Reduce system calls

### Constructors

```java
BufferedReader(Reader in)               // Default buffer size (8192)
BufferedReader(Reader in, int sz)       // Specify buffer size
```

### Key Methods

```java
int read()                              // Read single character
int read(char[] cbuf)                   // Read array of characters
String readLine()                       // Read line (without newline)
boolean ready()                         // Check if data available
void mark(int readAheadLimit)           // Mark position
void reset()                            // Reset to marked position
long skip(long n)                       // Skip characters
Stream<String> lines()                  // Stream of lines
```

### BufferedReader Example

```java
import java.io.*;

public class BufferedReaderDemo {
    public static void main(String[] args) {
        // BufferedReader wraps InputStreamReader
        try (BufferedReader br = new BufferedReader(
                new InputStreamReader(
                    new FileInputStream("input.txt")))) {
            
            String line;
            int lineNumber = 1;
            
            System.out.println("File contents:");
            while ((line = br.readLine()) != null) {
                System.out.println(lineNumber + ": " + line);
                lineNumber++;
            }
            
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
```

### Reading from Console with BufferedReader
```java
import java.io.*;

public class ConsoleInput {
    public static void main(String[] args) {
        try (BufferedReader br = new BufferedReader(
                new InputStreamReader(System.in))) {
            
            System.out.print("Enter your name: ");
            String name = br.readLine();
            
            System.out.print("Enter your age: ");
            int age = Integer.parseInt(br.readLine());
            
            System.out.println("\nHello " + name + ", you are " + age + " years old");
            
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
```

### BufferedReader with Lines Stream (Java 8+)

```java
import java.io.*;

public class BufferedReaderStream {
    public static void main(String[] args) {
        try (BufferedReader br = new BufferedReader(
                new FileReader("input.txt"))) {
            
            System.out.println("Line count: " + 
                br.lines().count());  // Count lines
            
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
```

---
## Wrapping Streams
### What is Stream Wrapping?
**Stream wrapping** is passing a stream to the constructor of another stream to **add functionality**.

### Wrapping Layers

```
Logical Layers         Classes
─────────────────────────────────
Application          
                     BufferedReader
Buffering            BufferedInputStream
                     
Character/Byte       InputStreamReader
Conversion           FileReader
                     
File Access          FileInputStream
                     FileOutputStream
                     
Operating System     File System
```

### Common Wrapping Patterns
#### Pattern 1: FileInputStream → InputStreamReader → BufferedReader
```java
BufferedReader br = new BufferedReader(
    new InputStreamReader(
        new FileInputStream("file.txt")));
```

#### Pattern 2: FileInputStream → BufferedInputStream
```java
BufferedInputStream bis = new BufferedInputStream(
    new FileInputStream("file.txt"));
```

#### Pattern 3: FileOutputStream → BufferedOutputStream

```java
BufferedOutputStream bos = new BufferedOutputStream(
    new FileOutputStream("file.txt"));
```

#### Pattern 4: FileOutputStream → OutputStreamWriter → BufferedWriter

```java
BufferedWriter bw = new BufferedWriter(
    new OutputStreamWriter(
        new FileOutputStream("file.txt"), 
        StandardCharsets.UTF_8));
```

### Example: Nested Wrapping

```java
import java.io.*;
import java.nio.charset.StandardCharsets;

public class NestedWrappingDemo {
    public static void main(String[] args) {
        try (BufferedReader br = new BufferedReader(
                new InputStreamReader(
                    new FileInputStream("input.txt"),
                    StandardCharsets.UTF_8))) {
            
            // Each layer adds functionality:
            // - FileInputStream: reads bytes from file
            // - InputStreamReader: converts bytes to chars (UTF-8)
            // - BufferedReader: buffers chars and provides readLine()
            
            String line;
            while ((line = br.readLine()) != null) {
                System.out.println(line);
            }
            
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
```

---

## File Output Streams
### FileOutputStream Revisited

Writing to files using FileOutputStream:

```java
import java.io.*;

public class FileOutputStreamWriting {
    public static void main(String[] args) {
        byte[] data = "Hello, World!".getBytes();
        
        try (FileOutputStream fos = new FileOutputStream("output.bin")) {
            
            // Write all bytes
            fos.write(data);
            
            // Write single byte
            fos.write(13);  // Carriage return
            fos.write(10);  // Line feed
            
            fos.flush();
            System.out.println("Data written successfully");
            
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
```

### BufferedOutputStream
Improves performance by buffering writes:

```java
import java.io.*;

public class BufferedOutputStreamDemo {
    public static void main(String[] args) {
        try (BufferedOutputStream bos = new BufferedOutputStream(
                new FileOutputStream("output.txt"))) {
            
            String text = "Buffered output example";
            bos.write(text.getBytes());
            bos.write('\n');
            bos.flush();  // Force write
            
            System.out.println("Written to file");
            
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
```

### PrintStream and PrintWriter

**PrintStream**: Byte stream with print/println methods

```java
import java.io.*;

public class PrintStreamDemo {
    public static void main(String[] args) {
        try (PrintStream ps = new PrintStream(
                new FileOutputStream("output.txt"))) {
            
            ps.println("Hello, World!");
            ps.println("Number: " + 42);
            ps.printf("Formatted: %d + %d = %d\n", 1, 2, 3);
            
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
```

**PrintWriter**: Character stream with print/println methods

```java
import java.io.*;

public class PrintWriterDemo {
    public static void main(String[] args) {
        try (PrintWriter pw = new PrintWriter(
                new FileWriter("output.txt"))) {
            
            pw.println("Hello, World!");
            pw.println("Number: " + 42);
            pw.printf("Formatted: %d + %d = %d\n", 1, 2, 3);
            
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
```

---
## Advanced I/O Concepts
### 1. DataInputStream and DataOutputStream

Read/write primitive types:

```java
import java.io.*;

public class DataStreamDemo {
    public static void main(String[] args) throws IOException {
        // Writing
        try (DataOutputStream dos = new DataOutputStream(
                new FileOutputStream("data.bin"))) {
            dos.writeInt(42);
            dos.writeDouble(3.14);
            dos.writeBoolean(true);
            dos.writeUTF("Hello");
        }
        
        // Reading
        try (DataInputStream dis = new DataInputStream(
                new FileInputStream("data.bin"))) {
            int num = dis.readInt();
            double pi = dis.readDouble();
            boolean flag = dis.readBoolean();
            String text = dis.readUTF();
            
            System.out.println(num + ", " + pi + ", " + flag + ", " + text);
        }
    }
}
```

### 2. ByteArrayInputStream and ByteArrayOutputStream

Work with memory instead of files:

```java
import java.io.*;

public class ByteArrayStreamDemo {
    public static void main(String[] args) throws IOException {
        // Write to memory
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        baos.write("Hello, World!".getBytes());
        
        // Get bytes from memory
        byte[] data = baos.toByteArray();
        System.out.println("Size: " + data.length);
        
        // Read from memory
        ByteArrayInputStream bais = new ByteArrayInputStream(data);
        int byteData;
        while ((byteData = bais.read()) != -1) {
            System.out.print((char) byteData);
        }
    }
}
```

### 3. RandomAccessFile

Read/write at specific positions:

```java
import java.io.*;

public class RandomAccessFileDemo {
    public static void main(String[] args) {
        try (RandomAccessFile raf = new RandomAccessFile("random.dat", "rw")) {
            
            // Write data
            raf.writeInt(100);
            raf.writeDouble(3.14);
            raf.writeUTF("Java");
            
            // Seek to beginning
            raf.seek(0);
            
            // Read data
            System.out.println("Int: " + raf.readInt());
            System.out.println("Double: " + raf.readDouble());
            System.out.println("String: " + raf.readUTF());
            
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
```

---

## Complete Working Examples

### Example 1: File Copy Utility

```java
import java.io.*;

public class FileCopyUtility {
    static void copyFile(String src, String dest) throws IOException {
        try (FileInputStream fis = new FileInputStream(src);
             FileOutputStream fos = new FileOutputStream(dest)) {
            
            byte[] buffer = new byte[4096];
            int bytesRead;
            long totalBytes = 0;
            
            long startTime = System.currentTimeMillis();
            
            while ((bytesRead = fis.read(buffer)) != -1) {
                fos.write(buffer, 0, bytesRead);
                totalBytes += bytesRead;
            }
            
            long endTime = System.currentTimeMillis();
            
            System.out.printf("Copied %d bytes in %d ms%n", 
                totalBytes, endTime - startTime);
        }
    }
    
    public static void main(String[] args) {
        try {
            copyFile("source.txt", "destination.txt");
            System.out.println("File copy completed successfully");
        } catch (IOException e) {
            System.err.println("Error: " + e.getMessage());
        }
    }
}
```

### Example 2: Text File Processor

```java
import java.io.*;
import java.util.*;

public class TextFileProcessor {
    public static void main(String[] args) {
        String inputFile = "input.txt";
        String outputFile = "processed.txt";
        
        try (BufferedReader br = new BufferedReader(new FileReader(inputFile));
             BufferedWriter bw = new BufferedWriter(new FileWriter(outputFile))) {
            
            String line;
            int lineCount = 0;
            int wordCount = 0;
            int charCount = 0;
            
            while ((line = br.readLine()) != null) {
                lineCount++;
                charCount += line.length();
                wordCount += line.split("\\s+").length;
                
                // Process line: convert to uppercase
                String processed = line.toUpperCase();
                bw.write(processed);
                bw.newLine();
            }
            
            System.out.println("Statistics:");
            System.out.println("Lines: " + lineCount);
            System.out.println("Words: " + wordCount);
            System.out.println("Characters: " + charCount);
            System.out.println("Processed file written to " + outputFile);
            
        } catch (IOException e) {
            System.err.println("Error: " + e.getMessage());
        }
    }
}
```

### Example 3: CSV File Reader

```java
import java.io.*;
import java.util.*;

public class CSVReader {
    static List<String[]> readCSV(String filename) throws IOException {
        List<String[]> records = new ArrayList<>();
        
        try (BufferedReader br = new BufferedReader(new FileReader(filename))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] fields = line.split(",");
                records.add(fields);
            }
        }
        
        return records;
    }
    
    public static void main(String[] args) {
        try {
            List<String[]> data = readCSV("data.csv");
            
            System.out.println("CSV Data:");
            System.out.println("Total records: " + data.size());
            
            for (int i = 0; i < data.size(); i++) {
                System.out.printf("Record %d: %s%n", i + 1, 
                    Arrays.toString(data.get(i)));
            }
            
        } catch (IOException e) {
            System.err.println("Error: " + e.getMessage());
        }
    }
}
```

### Example 4: Directory Synchronizer

```java
import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;

public class DirectorySynchronizer {
    static void syncFiles(String srcDir, String destDir) throws IOException {
        File src = new File(srcDir);
        File dest = new File(destDir);
        
        if (!dest.exists()) {
            dest.mkdirs();
        }
        
        File[] files = src.listFiles();
        if (files == null) return;
        
        for (File file : files) {
            if (file.isFile()) {
                String destPath = destDir + File.separator + file.getName();
                copyFile(file.getAbsolutePath(), destPath);
                System.out.println("Synced: " + file.getName());
            } else if (file.isDirectory()) {
                String subDestDir = destDir + File.separator + file.getName();
                syncFiles(file.getAbsolutePath(), subDestDir);
            }
        }
    }
    
    static void copyFile(String src, String dest) throws IOException {
        try (FileInputStream fis = new FileInputStream(src);
             FileOutputStream fos = new FileOutputStream(dest)) {
            byte[] buffer = new byte[4096];
            int bytesRead;
            while ((bytesRead = fis.read(buffer)) != -1) {
                fos.write(buffer, 0, bytesRead);
            }
        }
    }
    
    public static void main(String[] args) {
        try {
            syncFiles("source_dir", "backup_dir");
            System.out.println("Directory synchronization complete");
        } catch (IOException e) {
            System.err.println("Error: " + e.getMessage());
        }
    }
}
```

---

## Best Practices

1. **Always close resources**: Use try-with-resources
2. **Buffer when possible**: Use Buffered streams for better performance
3. **Choose the right stream type**: Use Character streams for text, Byte streams for binary
4. **Handle exceptions**: Always catch IOException
5. **Use flush()**: Ensure data is written before closing
6. **Check file existence**: Use File.exists() before reading
7. **Use absolute paths carefully**: Prefer relative paths when possible

# Why not use scanner then?
## Quick Answer

**Scanner is convenient but has limitations. ** Different classes exist because they serve different purposes:

- **Scanner**:  Easy parsing, user input, tokenizing
- **BufferedReader**:  Fast text reading, large files
- **FileInputStream**: Binary data, raw bytes
- **FileReader**: Simple text reading

Let me prove this with examples! 

---

## Performance Comparison

### Benchmark: Scanner vs BufferedReader

````java name=PerformanceBenchmark.java
import java.io.*;
import java.util.Scanner;

public class PerformanceBenchmark {
    public static void main(String[] args) throws IOException {
        
        // Create a large test file
        System.out.println("Creating test file with 100,000 lines...\n");
        FileWriter fw = new FileWriter("large_file.txt");
        for (int i = 0; i < 100_000; i++) {
            fw.write("This is line number " + i + " with some text content\n");
        }
        fw.close();
        
        System.out.println("=== Performance Comparison ===\n");
        
        // Test 1: Scanner
        long start = System.currentTimeMillis();
        Scanner scanner = new Scanner(new File("large_file.txt"));
        int scannerLines = 0;
        while (scanner.hasNextLine()) {
            scanner.nextLine();
            scannerLines++;
        }
        scanner.close();
        long scannerTime = System.currentTimeMillis() - start;
        
        System.out.println("Scanner:");
        System.out.println("  Lines read: " + scannerLines);
        System.out.println("  Time: " + scannerTime + " ms");
        
        // Test 2: BufferedReader
        start = System.currentTimeMillis();
        BufferedReader br = new BufferedReader(new FileReader("large_file.txt"));
        int brLines = 0;
        while (br.readLine() != null) {
            brLines++;
        }
        br.close();
        long brTime = System.currentTimeMillis() - start;
        
        System.out.println("\nBufferedReader:");
        System.out.println("  Lines read: " + brLines);
        System.out.println("  Time: " + brTime + " ms");
        
        // Comparison
        System.out.println("\n═══════════════════════════════════════");
        System.out.println("BufferedReader is " + (scannerTime / Math.max(1, brTime)) + "x FASTER");
        System.out.println("Speed difference: " + (scannerTime - brTime) + " ms");
        
        System.out.println("\n💡 For large files, BufferedReader is MUCH faster!");
        
        // Cleanup
        new File("large_file.txt").delete();
    }
}
````

**Typical Output:**
```
=== Performance Comparison ===

Scanner:
  Lines read: 100000
  Time: 850 ms

BufferedReader: 
  Lines read: 100000
  Time: 120 ms

═══════════════════════════════════════
BufferedReader is 7x FASTER
Speed difference: 730 ms

💡 For large files, BufferedReader is MUCH faster!
```

---

## Feature Comparison

### What Each Class Can Do

````java name=FeatureComparison.java
import java.io.*;
import java.util.Scanner;

public class FeatureComparison {
    public static void main(String[] args) throws IOException {
        
        // Create test file
        FileWriter fw = new FileWriter("test_features.txt");
        fw.write("42 3.14 true Hello\n");
        fw.write("100 2.718 false World\n");
        fw.close();
        
        System.out.println("=== Feature Comparison ===\n");
        System.out.println("File contents:  '42 3.14 true Hello'");
        System.out.println();
        
        // Feature 1: Scanner - Easy parsing
        System.out. println("1. Scanner - Parse different types:");
        Scanner scanner = new Scanner(new File("test_features. txt"));
        int num = scanner.nextInt();
        double dbl = scanner.nextDouble();
        boolean bool = scanner.nextBoolean();
        String str = scanner.next();
        
        System.out.println("   int: " + num);
        System.out.println("   double: " + dbl);
        System.out.println("   boolean: " + bool);
        System.out.println("   String: " + str);
        System.out.println("   ✅ Scanner AUTO-PARSES different types!");
        scanner.close();
        
        System.out.println();
        
        // Feature 2: BufferedReader - Only strings
        System.out.println("2. BufferedReader - Returns only strings:");
        BufferedReader br = new BufferedReader(new FileReader("test_features.txt"));
        String line = br.readLine();
        
        System.out.println("   Read:  '" + line + "'");
        System.out.println("   Type: String");
        System.out.println("   ⚠️  Need to manually parse:  Integer. parseInt(), etc.");
        
        // Manual parsing required
        String[] parts = line.split(" ");
        int manualInt = Integer.parseInt(parts[0]);
        double manualDbl = Double.parseDouble(parts[1]);
        
        System.out.println("   After manual parsing:");
        System.out.println("     int: " + manualInt);
        System.out.println("     double: " + manualDbl);
        
        br.close();
        
        System.out.println();
        System.out.println("═══════════════════════════════════════");
        System.out.println("Scanner:  Convenient parsing");
        System.out.println("BufferedReader: Faster, but manual parsing");
        
        new File("test_features.txt").delete();
    }
}
````

---

## When to Use Each Class

### Use Case 1: User Input (Console)

````java name=UserInputComparison.java
import java. io.*;
import java.util.Scanner;

public class UserInputComparison {
    public static void main(String[] args) throws IOException {
        
        System.out.println("=== Reading User Input ===\n");
        
        // ✅ Scanner - BEST for user input
        System.out.println("1. Using Scanner (RECOMMENDED):");
        System.out.println("   Scanner scanner = new Scanner(System.in);");
        System.out.println("   int age = scanner.nextInt();");
        System.out.println("   String name = scanner.nextLine();");
        System.out.println();
        System.out.println("   ✅ Easy to use");
        System.out.println("   ✅ Automatic type parsing");
        System.out.println("   ✅ Built for interactive input");
        
        System.out.println();
        
        // ⚠️ BufferedReader - More complex
        System.out.println("2. Using BufferedReader:");
        System.out.println("   BufferedReader br = new BufferedReader(");
        System.out.println("       new InputStreamReader(System. in)");
        System.out.println("   );");
        System.out.println("   String ageStr = br.readLine();");
        System.out.println("   int age = Integer.parseInt(ageStr);");
        System.out.println();
        System.out.println("   ⚠️  More code");
        System.out.println("   ⚠️  Manual parsing required");
        System.out.println("   ✅ Faster (but doesn't matter for user input)");
        
        System.out.println();
        System.out.println("═══════════════════════════════════════");
        System.out.println("Verdict: Use Scanner for user input!  🎯");
    }
}
````

### Use Case 2: Reading Large Text Files

````java name=LargeFileReading.java
import java. io.*;
import java.util. Scanner;

public class LargeFileReading {
    public static void main(String[] args) throws IOException {
        
        System. out.println("=== Reading Large Text Files ===\n");
        
        // Create large file
        FileWriter fw = new FileWriter("large. txt");
        for (int i = 0; i < 1000; i++) {
            fw.write("Line " + i + "\n");
        }
        fw. close();
        
        // ❌ Scanner - Slower
        System.out.println("1. Using Scanner:");
        long start = System.currentTimeMillis();
        Scanner scanner = new Scanner(new File("large.txt"));
        int count1 = 0;
        while (scanner.hasNextLine()) {
            String line = scanner.nextLine();
            count1++;
        }
        scanner.close();
        long time1 = System.currentTimeMillis() - start;
        
        System.out.println("   Lines:  " + count1);
        System.out.println("   Time: " + time1 + " ms");
        System.out.println("   ❌ Slower due to parsing overhead");
        
        System.out.println();
        
        // ✅ BufferedReader - Faster
        System. out.println("2. Using BufferedReader:");
        start = System.currentTimeMillis();
        BufferedReader br = new BufferedReader(new FileReader("large. txt"));
        int count2 = 0;
        while (br.readLine() != null) {
            count2++;
        }
        br.close();
        long time2 = System. currentTimeMillis() - start;
        
        System.out. println("   Lines: " + count2);
        System.out. println("   Time: " + time2 + " ms");
        System.out.println("   ✅ Faster - optimized for text");
        
        System.out. println();
        System.out. println("═══════════════════════════════════════");
        System.out.println("Verdict: Use BufferedReader for large files! 🎯");
        
        new File("large.txt").delete();
    }
}
````

### Use Case 3: Reading Binary Files

````java name=BinaryFileReading.java
import java. io.*;
import java.util.Scanner;

public class BinaryFileReading {
    public static void main(String[] args) throws IOException {
        
        System. out.println("=== Reading Binary Files ===\n");
        
        // Create binary file
        FileOutputStream fos = new FileOutputStream("binary.dat");
        fos.write(new byte[]{10, 20, 30, 40, 50});
        fos.close();
        
        // ❌ Scanner - NOT designed for binary
        System.out.println("1. Using Scanner:");
        System.out.println("   ❌ Scanner is for TEXT, not binary data");
        System.out.println("   ❌ Will try to parse as text (wrong!)");
        
        System.out.println();
        
        // ✅ FileInputStream - Correct for binary
        System.out. println("2. Using FileInputStream:");
        FileInputStream fis = new FileInputStream("binary.dat");
        System.out.print("   Bytes: ");
        int b;
        while ((b = fis.read()) != -1) {
            System.out. print(b + " ");
        }
        fis.close();
        System.out.println();
        System.out.println("   ✅ Reads raw bytes correctly");
        
        System.out.println();
        System.out.println("═══════════════════════════════════════");
        System.out.println("Verdict: Use InputStream for binary files! 🎯");
        
        new File("binary.dat").delete();
    }
}
````

### Use Case 4: Parsing Structured Data

````java name=ParsingStructuredData.java
import java.io.*;
import java. util.Scanner;

public class ParsingStructuredData {
    public static void main(String[] args) throws IOException {
        
        System.out.println("=== Parsing Structured Data ===\n");
        
        // Create data file
        FileWriter fw = new FileWriter("data.txt");
        fw.write("John 25 75000. 50\n");
        fw.write("Jane 30 85000.75\n");
        fw.write("Bob 28 65000.00\n");
        fw.close();
        
        System.out.println("File:  'John 25 75000.50'");
        System.out.println();
        
        // ✅ Scanner - Easy parsing
        System.out.println("1. Using Scanner:");
        Scanner scanner = new Scanner(new File("data.txt"));
        
        int personCount = 0;
        while (scanner.hasNext()) {
            String name = scanner.next();
            int age = scanner.nextInt();
            double salary = scanner.nextDouble();
            
            personCount++;
            System.out.println("   Person " + personCount + ": " + name + 
                             ", Age: " + age + ", Salary: $" + salary);
        }
        scanner.close();
        System.out.println("   ✅ Automatic type conversion");
        System.out.println("   ✅ Clean code");
        
        System.out.println();
        
        // ⚠️ BufferedReader - Manual parsing
        System.out.println("2. Using BufferedReader:");
        BufferedReader br = new BufferedReader(new FileReader("data. txt"));
        
        String line;
        personCount = 0;
        while ((line = br.readLine()) != null) {
            String[] parts = line.split(" ");
            String name = parts[0];
            int age = Integer.parseInt(parts[1]);
            double salary = Double.parseDouble(parts[2]);
            
            personCount++;
            System.out.println("   Person " + personCount + ": " + name + 
                             ", Age: " + age + ", Salary: $" + salary);
        }
        br. close();
        System.out. println("   ⚠️  Manual parsing required");
        System.out.println("   ✅ Faster for large files");
        
        System.out.println();
        System.out.println("═══════════════════════════════════════");
        System.out.println("Verdict: Scanner for convenience, BufferedReader for speed");
        
        new File("data.txt").delete();
    }
}
````

---

## Detailed Comparison Table

### Complete Feature Matrix

````java name=ComparisonMatrix.java
public class ComparisonMatrix {
    public static void main(String[] args) {
        
        System. out.println("=== Complete Comparison Matrix ===\n");
        
        System.out.println("Feature                    | Scanner | BufferedReader | FileInputStream");
        System.out.println("────────────────────────────────────────────────────────────────────────");
        System.out.println("Speed                      | Slow    | Fast           | Fast");
        System.out.println("Ease of use                | Easy    | Medium         | Complex");
        System.out.println("Parse integers             | ✅ Yes  | ❌ No          | ❌ No");
        System.out.println("Parse doubles              | ✅ Yes  | ❌ No          | ❌ No");
        System.out.println("Parse booleans             | ✅ Yes  | ❌ No          | ❌ No");
        System.out.println("Read by line               | ✅ Yes  | ✅ Yes         | ❌ No");
        System.out.println("Read by word/token         | ✅ Yes  | ❌ No          | ❌ No");
        System.out.println("Custom delimiters          | ✅ Yes  | ❌ No          | ❌ No");
        System.out.println("Regular expressions        | ✅ Yes  | ❌ No          | ❌ No");
        System.out.println("Read binary data           | ❌ No   | ❌ No          | ✅ Yes");
        System.out.println("Read text files            | ✅ Yes  | ✅ Yes         | ⚠️  Bytes");
        System.out.println("User input (console)       | ✅ Best | ⚠️  OK         | ❌ No");
        System.out.println("Large file performance     | ❌ Slow | ✅ Fast        | ✅ Fast");
        System.out.println("Memory efficient           | ❌ No   | ✅ Yes         | ✅ Yes");
        System.out.println("Exception handling         | Minimal | Standard       | Standard");
        System.out.println("Buffered by default        | ✅ Yes  | ✅ Yes         | ❌ No");
        System.out.println();
        
        System.out. println("═══════════════════════════════════════════════════════════════════════");
        System.out.println();
        
        System.out.println("WHEN TO USE:");
        System.out.println("─────────────────────────────────────────────────────────────────────");
        System.out. println();
        
        System.out. println("Scanner:");
        System.out.println("  ✅ Reading user input from console");
        System.out. println("  ✅ Parsing mixed data types (int, double, String)");
        System.out. println("  ✅ Small files with structured data");
        System.out. println("  ✅ When convenience > performance");
        System.out.println("  ✅ Tokenizing text with delimiters");
        System.out.println("  ❌ Large files (slow)");
        System.out. println("  ❌ Binary files");
        System.out.println();
        
        System.out. println("BufferedReader:");
        System.out.println("  ✅ Reading large text files");
        System.out. println("  ✅ Line-by-line processing");
        System.out.println("  ✅ When performance matters");
        System.out.println("  ✅ Production code with big files");
        System.out. println("  ❌ Need automatic type parsing");
        System.out.println("  ❌ Reading user input (overkill)");
        System.out.println();
        
        System.out.println("FileInputStream:");
        System.out.println("  ✅ Reading binary files (images, audio, video)");
        System.out.println("  ✅ Reading bytes directly");
        System.out. println("  ✅ Network sockets");
        System.out. println("  ✅ Custom encoding needs (with InputStreamReader)");
        System.out.println("  ❌ Simple text reading (use BufferedReader)");
        System.out.println("  ❌ Parsing structured data (use Scanner)");
    }
}
````

---

## Real-World Examples

### Example 1: Reading CSV File

````java name=CSVReading.java
import java.io.*;
import java.util.Scanner;

public class CSVReading {
    public static void main(String[] args) throws IOException {
        
        // Create CSV file
        FileWriter fw = new FileWriter("data.csv");
        fw.write("Name,Age,Salary\n");
        fw.write("John,25,75000.50\n");
        fw.write("Jane,30,85000.75\n");
        fw.write("Bob,28,65000.00\n");
        fw.close();
        
        System.out.println("=== Reading CSV File ===\n");
        
        // Method 1: Scanner with delimiter
        System.out.println("1. Scanner (with comma delimiter):");
        Scanner scanner = new Scanner(new File("data.csv"));
        scanner.useDelimiter(",|\\n");  // Comma or newline
        
        scanner.next(); scanner.next(); scanner.next(); // Skip header
        
        while (scanner.hasNext()) {
            String name = scanner.next();
            if (scanner.hasNextInt()) {
                int age = scanner.nextInt();
                double salary = scanner.nextDouble();
                System. out.println("   " + name + " - Age: " + age + ", Salary: $" + salary);
            }
        }
        scanner.close();
        System.out.println("   ✅ Easy delimiter handling");
        
        System.out.println();
        
        // Method 2: BufferedReader with split
        System.out.println("2. BufferedReader (with split):");
        BufferedReader br = new BufferedReader(new FileReader("data.csv"));
        
        br.readLine(); // Skip header
        
        String line;
        while ((line = br.readLine()) != null) {
            String[] parts = line.split(",");
            String name = parts[0];
            int age = Integer.parseInt(parts[1]);
            double salary = Double.parseDouble(parts[2]);
            System.out.println("   " + name + " - Age: " + age + ", Salary: $" + salary);
        }
        br.close();
        System.out.println("   ✅ Faster for large CSVs");
        
        System.out.println();
        System.out.println("═══════════════════════════════════════");
        System.out.println("Both work!  Scanner easier, BufferedReader faster");
        
        new File("data.csv").delete();
    }
}
````

### Example 2: Log File Processing

````java name=LogFileProcessing.java
import java. io.*;
import java.util. Scanner;

public class LogFileProcessing {
    public static void main(String[] args) throws IOException {
        
        // Create large log file
        System.out.println("Creating log file with 50,000 entries...\n");
        FileWriter fw = new FileWriter("app.log");
        for (int i = 0; i < 50_000; i++) {
            fw.write("2025-01-" + (i % 30 + 1) + " ERROR: Connection timeout on port 8080\n");
        }
        fw.close();
        
        System.out.println("=== Processing Log File ===\n");
        
        // ❌ Scanner - Slow for large logs
        System.out.println("1. Scanner:");
        long start = System.currentTimeMillis();
        Scanner scanner = new Scanner(new File("app.log"));
        int errorCount1 = 0;
        while (scanner.hasNextLine()) {
            String line = scanner.nextLine();
            if (line. contains("ERROR")) {
                errorCount1++;
            }
        }
        scanner.close();
        long time1 = System. currentTimeMillis() - start;
        
        System.out. println("   Errors found: " + errorCount1);
        System.out.println("   Time: " + time1 + " ms");
        System.out.println("   ⚠️  Slow for production logs");
        
        System. out.println();
        
        // ✅ BufferedReader - Fast for large logs
        System.out.println("2. BufferedReader:");
        start = System.currentTimeMillis();
        BufferedReader br = new BufferedReader(new FileReader("app.log"));
        int errorCount2 = 0;
        String line;
        while ((line = br.readLine()) != null) {
            if (line.contains("ERROR")) {
                errorCount2++;
            }
        }
        br.close();
        long time2 = System.currentTimeMillis() - start;
        
        System.out.println("   Errors found: " + errorCount2);
        System.out.println("   Time: " + time2 + " ms");
        System.out.println("   ✅ Production-ready performance");
        
        System.out.println();
        System.out.println("═══════════════════════════════════════");
        System.out.println("Performance gain: " + (time1 / Math.max(1, time2)) + "x faster");
        System.out.println("💡 For log processing, always use BufferedReader!");
        
        new File("app.log").delete();
    }
}
````

### Example 3: Interactive Console Program

````java name=InteractiveProgram.java
import java. io.*;
import java.util. Scanner;

public class InteractiveProgram {
    public static void main(String[] args) throws IOException {
        
        System.out.println("=== Interactive Console Program ===\n");
        
        System.out.println("Scenario: Building a calculator\n");
        
        // ✅ Scanner - Perfect for interactive input
        System.out.println("Using Scanner (RECOMMENDED):");
        System.out.println("─────────────────────────────────────");
        System.out.println("Code:");
        System.out.println("  Scanner sc = new Scanner(System.in);");
        System.out.println("  System.out.print(\"Enter number: \");");
        System.out.println("  int num = sc.nextInt();  // Direct conversion!");
        System.out.println();
        System.out.println("Benefits:");
        System.out.println("  ✅ One line to get an integer");
        System.out. println("  ✅ No manual parsing");
        System.out.println("  ✅ Handles spaces automatically");
        System.out.println("  ✅ Built-in type validation");
        
        System.out.println();
        
        // ⚠️ BufferedReader - More work
        System.out.println("Using BufferedReader:");
        System.out.println("─────────────────────────────────────");
        System.out.println("Code:");
        System.out.println("  BufferedReader br = new BufferedReader(");
        System.out.println("      new InputStreamReader(System.in)");
        System.out.println("  );");
        System.out.println("  System.out.print(\"Enter number: \");");
        System.out. println("  String input = br.readLine();");
        System.out.println("  int num = Integer.parseInt(input);  // Manual conversion");
        System.out.println();
        System.out.println("Drawbacks:");
        System.out. println("  ⚠️  More code");
        System.out.println("  ⚠️  Manual parsing required");
        System.out.println("  ⚠️  Need try-catch for NumberFormatException");
        
        System.out.println();
        System.out.println("═══════════════════════════════════════");
        System.out.println("Verdict: Scanner wins for interactive programs!  🏆");
    }
}
````

---

## Memory Usage Comparison

````java name=MemoryUsage.java
import java.io.*;
import java. util.Scanner;

public class MemoryUsage {
    public static void main(String[] args) throws IOException {
        
        System.out.println("=== Memory Usage ===\n");
        
        // Create test file
        FileWriter fw = new FileWriter("memory_test.txt");
        for (int i = 0; i < 10000; i++) {
            fw.write("Line " + i + "\n");
        }
        fw.close();
        
        Runtime runtime = Runtime.getRuntime();
        
        // Test Scanner memory
        System.gc();
        long memBefore1 = runtime.totalMemory() - runtime.freeMemory();
        
        Scanner scanner = new Scanner(new File("memory_test.txt"));
        while (scanner.hasNextLine()) {
            scanner.nextLine();
        }
        scanner.close();
        
        long memAfter1 = runtime.totalMemory() - runtime.freeMemory();
        long scannerMem = (memAfter1 - memBefore1) / 1024;
        
        System.out. println("Scanner memory usage: ~" + scannerMem + " KB");
        System.out.println("  ⚠️  Higher overhead (tokenizing, parsing)");
        
        System.out.println();
        
        // Test BufferedReader memory
        System.gc();
        long memBefore2 = runtime. totalMemory() - runtime.freeMemory();
        
        BufferedReader br = new BufferedReader(new FileReader("memory_test.txt"));
        while (br.readLine() != null) {
            // Just read
        }
        br.close();
        
        long memAfter2 = runtime.totalMemory() - runtime.freeMemory();
        long brMem = (memAfter2 - memBefore2) / 1024;
        
        System.out.println("BufferedReader memory usage: ~" + brMem + " KB");
        System.out.println("  ✅ Lower overhead (just buffering)");
        
        System.out.println();
        System.out.println("═══════════════════════════════════════");
        System.out.println("BufferedReader is more memory efficient");
        
        new File("memory_test. txt").delete();
    }
}
````

---

## Decision Flowchart (Code)

````java name=DecisionFlowchart.java
public class DecisionFlowchart {
    public static void main(String[] args) {
        
        System.out.println("=== WHICH CLASS SHOULD I USE? ===\n");
        
        System.out.println("START: What are you reading?");
        System.out. println();
        
        System.out. println("┌─────────────────────────────────────┐");
        System.out. println("│ Is it user input from console?     │");
        System.out. println("└─────────────┬───────────────────────┘");
        System.out.println("              │");
        System.out. println("        YES ──┤");
        System.out.println("              │");
        System.out.println("              ↓");
        System.out. println("         Use Scanner");
        System.out. println("         scanner.nextInt(), nextLine(), etc.");
        System.out.println("         ✅ Easy, convenient");
        System.out.println();
        
        System.out. println("              │");
        System.out. println("        NO ───┤");
        System.out.println("              │");
        System.out.println("              ↓");
        System.out.println("┌─────────────────────────────────────┐");
        System.out.println("│ Is it a binary file?                │");
        System.out. println("│ (image, audio, video, . class, etc.) │");
        System.out.println("└─────────────┬───────────────────────┘");
        System.out.println("              │");
        System.out.println("        YES ──┤");
        System.out.println("              │");
        System.out.println("              ↓");
        System.out.println("         Use FileInputStream");
        System.out. println("         fis.read(buffer)");
        System.out. println("         ✅ Reads raw bytes");
        System.out. println();
        
        System. out.println("              │");
        System.out.println("        NO ───┤ (It's a text file)");
        System.out.println("              │");
        System.out.println("              ↓");
        System.out.println("┌─────────────────────────────────────┐");
        System.out.println("│ Is the file large (>1 MB)?          │");
        System.out. println("└─────────────┬───────────────────────┘");
        System.out.println("              │");
        System.out. println("        YES ──┤");
        System.out.println("              │");
        System.out.println("              ↓");
        System.out.println("         Use BufferedReader");
        System.out.println("         br.readLine()");
        System.out.println("         ✅ Fast, efficient");
        System.out.println();
        
        System.out.println("              │");
        System.out.println("        NO ───┤ (Small file)");
        System.out.println("              │");
        System.out.println("              ↓");
        System.out.println("┌─────────────────────────────────────┐");
        System.out. println("│ Need to parse different data types? │");
        System.out.println("│ (int, double, boolean, etc.)        │");
        System.out. println("└─────────────┬───────────────────────┘");
        System.out. println("              │");
        System.out.println("        YES ──┤");
        System.out.println("              │");
        System.out.println("              ↓");
        System.out.println("         Use Scanner");
        System.out.println("         scanner.nextInt(), nextDouble()");
        System.out.println("         ✅ Convenient parsing");
        System.out.println();
        
        System.out.println("              │");
        System.out.println("        NO ───┤ (Just reading text)");
        System.out.println("              │");
        System.out.println("              ↓");
        System.out.println("         Use BufferedReader or Scanner");
        System.out. println("         Either works fine!");
        System.out.println("         BufferedReader slightly faster");
    }
}
````

---

## Summary

### Quick Reference Card

````java name=QuickReferenceCard. java
import java.io.*;
import java.util.Scanner;

public class QuickReferenceCard {
    public static void main(String[] args) throws IOException {
        
        System.out.println("╔═══════════════════════════════════════════════════════════════════╗");
        System.out. println("║                    JAVA I/O QUICK REFERENCE                       ║");
        System.out.println("╚═══════════════════════════════════════════════════════════════════╝");
        System.out.println();
        
        System.out.println("1.  SCANNER");
        System.out.println("   Use for: User input, small files, parsing mixed types");
        System.out.println("   Example: Scanner sc = new Scanner(System.in);");
        System.out.println("            int age = sc.nextInt();");
        System.out.println("   Pros: ✅ Easy parsing, ✅ Convenient");
        System.out.println("   Cons: ❌ Slower, ❌ More memory");
        System.out.println();
        
        System.out.println("2. BUFFEREDREADER");
        System.out.println("   Use for: Large files, log processing, production code");
        System.out. println("   Example: BufferedReader br = new BufferedReader(");
        System.out.println("                new FileReader(\"file.txt\")");
        System.out.println("            );");
        System.out. println("            String line = br.readLine();");
        System.out.println("   Pros: ✅ Fast, ✅ Memory efficient");
        System.out. println("   Cons: ❌ Manual parsing, ❌ More code");
        System.out. println();
        
        System. out.println("3. FILEINPUTSTREAM");
        System.out.println("   Use for: Binary files, raw bytes, network streams");
        System.out.println("   Example: FileInputStream fis = new FileInputStream(\"image.png\");");
        System.out.println("            byte[] buffer = new byte[1024];");
        System.out. println("            fis.read(buffer);");
        System.out.println("   Pros: ✅ Binary data, ✅ Raw bytes");
        System.out.println("   Cons: ❌ Not for text (need InputStreamReader)");
        System.out.println();
        
        System.out.println("═══════════════════════════════════════════════════════════════════");
        System.out.println();
        
        System.out.println("CHEAT SHEET:");
        System.out.println("┌────────────────────────┬─────────────────────────────────┐");
        System.out.println("│ Task                   │ Best Choice                     │");
        System.out.println("├────────────────────────┼─────────────────────────────────┤");
        System.out.println("│ Console input          │ Scanner                         │");
        System.out.println("│ Parse int/double       │ Scanner                         │");
        System.out.println("│ Small text file        │ Scanner OR BufferedReader       │");
        System.out.println("│ Large text file        │ BufferedReader                  │");
        System.out.println("│ CSV parsing            │ Scanner (easy) / BR (fast)      │");
        System.out. println("│ Log file processing    │ BufferedReader                  │");
        System.out.println("│ Binary file            │ FileInputStream                 │");
        System.out.println("│ Image/Audio/Video      │ FileInputStream                 │");
        System.out.println("│ Custom encoding        │ FileInputStream + InputStreamReader │");
        System.out.println("│ Network socket         │ InputStream + InputStreamReader │");
        System.out.println("└────────────────────────┴─────────────────────────────────┘");
    }
}
````

### The Bottom Line

**Why not just use Scanner?**

1. **Performance**: BufferedReader is **5-10x faster** for large files
2. **Memory**:  BufferedReader uses less memory
3. **Binary data**: Scanner can't handle binary files at all
4. **Production code**: BufferedReader is standard in enterprise applications
5. **Encoding control**: Need FileInputStream + InputStreamReader for specific encodings
6. **Network streams**: Can only use InputStream (no File equivalent)

**When Scanner IS the right choice:**
- ✅ User input from console
- ✅ Small files with mixed data types
- ✅ Quick prototypes and learning
- ✅ When convenience matters more than speed

**Different tools for different jobs! ** 🛠️

Each class exists for a reason - use the right tool for your specific need! 