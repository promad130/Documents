# Java File I/O: Quick Reference & Common Patterns

## Stream Decision Tree

```
Need to read/write?
├─ READ
│  ├─ Binary Data (images, exe, etc.)
│  │  └─ FileInputStream → BufferedInputStream
│  │
│  └─ Text Data (files, console)
│     ├─ Simple characters
│     │  └─ FileReader
│     │
│     └─ Full lines or complex parsing
│        └─ FileInputStream → InputStreamReader → BufferedReader
│
└─ WRITE
   ├─ Binary Data
   │  └─ FileOutputStream → BufferedOutputStream
   │
   └─ Text Data
      ├─ Simple characters
      │  └─ FileWriter
      │
      └─ Formatted output
         └─ BufferedWriter or PrintWriter
```

---

## Common I/O Patterns

### Pattern 1: Read Text File Line by Line (MOST COMMON)
```java
try (BufferedReader br = new BufferedReader(new FileReader("file.txt"))) {
    String line;
    while ((line = br.readLine()) != null) {
        process(line);
    }
}
```

### Pattern 2: Read Entire Text File
```java
try (BufferedReader br = new BufferedReader(new FileReader("file.txt"))) {
    List<String> lines = br.lines().collect(Collectors.toList());
}
```

### Pattern 3: Read Binary File
```java
try (FileInputStream fis = new FileInputStream("file.bin")) {
    byte[] buffer = new byte[4096];
    int bytesRead;
    while ((bytesRead = fis.read(buffer)) != -1) {
        process(buffer, bytesRead);
    }
}
```

### Pattern 4: Write Text File
```java
try (BufferedWriter bw = new BufferedWriter(new FileWriter("file.txt"))) {
    bw.write("Line 1");
    bw.newLine();
    bw.write("Line 2");
}
```

### Pattern 5: Copy File
```java
try (FileInputStream fis = new FileInputStream("source.txt");
     FileOutputStream fos = new FileOutputStream("dest.txt")) {
    byte[] buffer = new byte[4096];
    int bytesRead;
    while ((bytesRead = fis.read(buffer)) != -1) {
        fos.write(buffer, 0, bytesRead);
    }
}
```

### Pattern 6: Read Console Input
```java
try (BufferedReader br = new BufferedReader(new InputStreamReader(System.in))) {
    System.out.print("Enter: ");
    String input = br.readLine();
}
```

### Pattern 7: Write with Different Encoding
```java
try (BufferedWriter bw = new BufferedWriter(
        new OutputStreamWriter(
            new FileOutputStream("file.txt"),
            StandardCharsets.UTF_8))) {
    bw.write("UTF-8 content");
}
```

---

## Class Comparison Matrix

| Class | Input/Output | Data Type | Buffered | Methods | Use For |
|-------|--------------|-----------|----------|---------|---------|
| FileInputStream | Input | Bytes | No | read(), available() | Reading bytes from file |
| FileOutputStream | Output | Bytes | No | write(), flush() | Writing bytes to file |
| FileReader | Input | Characters | No | read() | Reading characters from file |
| FileWriter | Output | Characters | No | write(), flush() | Writing characters to file |
| BufferedInputStream | Input | Bytes | Yes | read(), available() | Faster byte input |
| BufferedOutputStream | Output | Bytes | Yes | write(), flush() | Faster byte output |
| BufferedReader | Input | Characters | Yes | read(), readLine() | Fast character input, line-based |
| BufferedWriter | Output | Characters | Yes | write(), newLine() | Fast character output |
| InputStreamReader | Bridge | Bytes→Characters | No | read() | Convert bytes to characters |
| OutputStreamWriter | Bridge | Characters→Bytes | No | write() | Convert characters to bytes |
| PrintWriter | Output | Characters | Yes | println(), printf() | Formatted text output |
| PrintStream | Output | Bytes | Yes | println(), printf() | Formatted byte output |

---

## Exception Handling

### Always Use Try-With-Resources

**BAD** (resource leak):
```java
BufferedReader br = new BufferedReader(new FileReader("file.txt"));
String line = br.readLine();  // If exception here, br is not closed!
br.close();
```

**GOOD** (automatic resource management):
```java
try (BufferedReader br = new BufferedReader(new FileReader("file.txt"))) {
    String line = br.readLine();  // Exception or not, br is always closed
}
```

### Handling IOException

```java
try (BufferedReader br = new BufferedReader(new FileReader("file.txt"))) {
    String line = br.readLine();
} catch (FileNotFoundException e) {
    System.err.println("File not found: " + e.getMessage());
} catch (IOException e) {
    System.err.println("I/O error: " + e.getMessage());
    e.printStackTrace();
}
```

---

## File Class Quick Reference

```java
// Creation
File file = new File("path/to/file.txt");

// Check existence/type
file.exists()                   // boolean
file.isFile()                   // boolean
file.isDirectory()              // boolean

// Path information
file.getAbsolutePath()          // String
file.getName()                  // String (filename only)
file.getParent()                // String (parent path)

// Properties
file.length()                   // long (bytes)
file.lastModified()             // long (timestamp)
file.canRead()                  // boolean
file.canWrite()                 // boolean

// Operations
file.createNewFile()            // boolean
file.delete()                   // boolean
file.mkdir()                    // boolean
file.mkdirs()                   // boolean (recursive)
file.renameTo(newFile)          // boolean

// Listing
file.list()                     // String[] (filenames)
file.listFiles()                // File[] (File objects)
```

---

## Charset and Encoding

### Default Charset
```java
System.out.println(Charset.defaultCharset());  // Get default
```

### Common Charsets
```java
import java.nio.charset.StandardCharsets;

StandardCharsets.UTF_8          // Unicode
StandardCharsets.ISO_8859_1     // Latin-1
StandardCharsets.US_ASCII       // ASCII only
StandardCharsets.UTF_16         // 16-bit Unicode
```

### Using Specific Charset
```java
// Reading with UTF-8
try (InputStreamReader isr = new InputStreamReader(
        new FileInputStream("file.txt"),
        StandardCharsets.UTF_8)) {
    // Read using UTF-8 encoding
}

// Writing with UTF-8
try (OutputStreamWriter osw = new OutputStreamWriter(
        new FileOutputStream("file.txt"),
        StandardCharsets.UTF_8)) {
    // Write using UTF-8 encoding
}
```

---

## Performance Tips

1. **Use Buffered Streams**: BufferedInputStream/BufferedOutputStream are much faster
   ```java
   // Slow
   FileInputStream fis = new FileInputStream("file.bin");
   
   // Fast
   BufferedInputStream bis = new BufferedInputStream(
       new FileInputStream("file.bin"));
   ```

2. **Choose Appropriate Buffer Size**: Default 8192 bytes is usually fine
   ```java
   BufferedInputStream bis = new BufferedInputStream(fis, 65536);  // 64KB buffer
   ```

3. **Minimize Object Creation**: Reuse buffers in loops
   ```java
   byte[] buffer = new byte[4096];
   int bytesRead;
   while ((bytesRead = fis.read(buffer)) != -1) {
       // Reuse same buffer
   }
   ```

4. **Use FileChannel for Large Files** (Java NIO)
   ```java
   try (FileChannel src = new FileInputStream(source).getChannel();
        FileChannel dst = new FileOutputStream(dest).getChannel()) {
       dst.transferFrom(src, 0, src.size());
   }
   ```

---

## ADT Operations Summary

```
InputStream ADT Operations:
├─ read()                    Read one byte, return -1 if EOF
├─ read(byte[] b)            Read up to b.length bytes
├─ read(byte[], int, int)    Read from array at offset
├─ available()               Bytes available without blocking
├─ skip(long n)              Skip n bytes
├─ close()                   Close stream
├─ mark(int)                 Mark current position
├─ reset()                   Return to marked position
└─ markSupported()           Check if marking supported

Reader ADT Operations:
├─ read()                    Read one character
├─ read(char[] c)            Read characters into array
├─ read(char[], int, int)    Read from array at offset
├─ ready()                   Check if data available
├─ skip(long n)              Skip n characters
├─ close()                   Close reader
├─ mark(int)                 Mark current position
├─ reset()                   Return to marked position
└─ markSupported()           Check if marking supported

OutputStream ADT Operations:
├─ write(int b)              Write one byte
├─ write(byte[] b)           Write byte array
├─ write(byte[], int, int)   Write from array at offset
├─ flush()                   Force pending data to be sent
└─ close()                   Close stream

Writer ADT Operations:
├─ write(int c)              Write one character
├─ write(char[] c)           Write character array
├─ write(char[], int, int)   Write from array at offset
├─ write(String str)         Write string
├─ write(String, int, int)   Write from string at offset
├─ append(char c)            Append character
├─ append(CharSequence)      Append character sequence
├─ flush()                   Force pending data to be sent
└─ close()                   Close writer
```

---

## Complete Example: Log File Analyzer

```java
import java.io.*;
import java.util.*;

public class LogAnalyzer {
    static class LogEntry {
        LocalDateTime timestamp;
        String level;       // INFO, WARN, ERROR
        String message;
    }
    
    static List<LogEntry> parseLogFile(String filename) throws IOException {
        List<LogEntry> entries = new ArrayList<>();
        
        try (BufferedReader br = new BufferedReader(
                new FileReader(filename))) {
            String line;
            while ((line = br.readLine()) != null) {
                LogEntry entry = parseLine(line);
                if (entry != null) {
                    entries.add(entry);
                }
            }
        }
        return entries;
    }
    
    static LogEntry parseLine(String line) {
        // Example: "2025-12-16 04:11:00 INFO: Application started"
        if (!line.contains(":")) return null;
        
        LogEntry entry = new LogEntry();
        String[] parts = line.split(" ", 3);
        if (parts.length < 3) return null;
        
        entry.level = parts[2];
        entry.message = parts.length > 3 ? parts[3] : "";
        return entry;
    }
    
    static void generateReport(List<LogEntry> entries, String outputFile) 
            throws IOException {
        try (PrintWriter pw = new PrintWriter(
                new FileWriter(outputFile))) {
            
            Map<String, Integer> counts = new HashMap<>();
            for (LogEntry entry : entries) {
                counts.put(entry.level, counts.getOrDefault(entry.level, 0) + 1);
            }
            
            pw.println("=== Log Analysis Report ===");
            pw.println("Total entries: " + entries.size());
            pw.println("\nLevel counts:");
            for (Map.Entry<String, Integer> e : counts.entrySet()) {
                pw.printf("  %s: %d%n", e.getKey(), e.getValue());
            }
        }
    }
    
    public static void main(String[] args) {
        try {
            List<LogEntry> entries = parseLogFile("app.log");
            System.out.println("Parsed " + entries.size() + " log entries");
            
            generateReport(entries, "report.txt");
            System.out.println("Report generated");
            
        } catch (IOException e) {
            System.err.println("Error: " + e.getMessage());
        }
    }
}
```

---

## Troubleshooting Common Issues

### Issue: FileNotFoundException

**Cause**: File doesn't exist
```java
// Check before reading
if (new File("file.txt").exists()) {
    // Read file
}

// Or create parent directories
File file = new File("path/to/file.txt");
file.getParentFile().mkdirs();
```

### Issue: Resource Leak

**Solution**: Always use try-with-resources
```java
// Wrong
FileReader fr = new FileReader("file.txt");
BufferedReader br = new BufferedReader(fr);
// Exception here → resources not closed

// Right
try (BufferedReader br = new BufferedReader(new FileReader("file.txt"))) {
    // Automatically closed
}
```

### Issue: Encoding Problems

**Solution**: Specify charset explicitly
```java
try (BufferedReader br = new BufferedReader(
        new InputStreamReader(
            new FileInputStream("file.txt"),
            StandardCharsets.UTF_8))) {
    // Now reads UTF-8 correctly
}
```

### Issue: Data Not Written to File

**Solution**: Call flush()
```java
FileWriter fw = new FileWriter("file.txt");
fw.write("data");
fw.flush();  // Force write to disk
// fw.close() also flushes automatically
```

---

## Modern Java I/O (NIO) Overview

For advanced I/O operations, use java.nio (New I/O):

```java
import java.nio.file.*;

// Read entire file
String content = Files.readString(Paths.get("file.txt"));

// Write entire file
Files.writeString(Paths.get("file.txt"), "content");

// List files
Files.list(Paths.get("."))
    .filter(Files::isRegularFile)
    .forEach(System.out::println);

// Copy file
Files.copy(Paths.get("source.txt"), Paths.get("dest.txt"));

// Delete file
Files.delete(Paths.get("file.txt"));
```

---

## Key Takeaways

1. **Use try-with-resources** for automatic resource management
2. **Choose the right stream**: ByteStreams for binary, CharacterStreams for text
3. **Buffer your streams** for better performance
4. **Handle exceptions** - IOException is checked, must be caught
5. **Close streams** - especially important for file I/O
6. **Specify charset** - don't rely on default encoding
7. **Wrap streams** - to add functionality (buffering, encoding, etc.)
8. **Check file operations** - exists(), canRead(), etc. before use
9. **Use appropriate classes**:
   - FileInputStream/FileOutputStream for raw file bytes
   - FileReader/FileWriter for text files
   - BufferedReader/BufferedWriter for line-based processing
   - InputStreamReader/OutputStreamWriter for encoding conversion
10. **Consider NIO** - java.nio.file for modern, powerful I/O operations
