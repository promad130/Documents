![[Prisma ORM#2. Prisma Client (Auto-generated & Type-safe)]]

Here are the core methods, classes, and syntax you need to master, utilizing the `User` and `Listing` schema we discussed earlier.

### **1. Connection Management**

Prisma automatically connects to your database on the first query, but in microservice architectures or serverless environments, you often need manual control.

- **`prisma.$connect()`**: Explicitly establishes the database connection.

- **`prisma.$disconnect()`**: Closes the connection gracefully (crucial when tearing down a Node.js process).

### **2. Reading Data (The `find` methods)**

These are the methods you will use most frequently to retrieve data from your PostgreSQL database.

- **`findUnique`**: Retrieves a single record by a unique identifier (`@id` or `@unique`). It is highly optimized.
    
    ```TypeScript
    const user = await prisma.user.findUnique({
      where: { email: "nikunj@example.com" }
    });
    ```
    
- **`findFirst`**: Retrieves the first record that matches a set of criteria. Useful when the filter isn't guaranteed to be unique.
    
    ```TypeScript
    const availableListing = await prisma.listing.findFirst({
      where: { isAvailable: true }
    });
    ```
    
- **`findMany`**: Retrieves a list of records.
    
    ```TypeScript
    const allUsers = await prisma.user.findMany();
    ```

### **3. Filtering, Sorting, and Pagination**

You rarely want _all_ records. You pass an options object into your `find` methods to shape the query.

- **`where`**: The filtering engine. You can use operators like `contains`, `startsWith`, `gt` (greater than), and `lt` (less than).
    
    ```TypeScript
    const searchResults = await prisma.listing.findMany({
      where: {
        title: { contains: "Drill" },
        isAvailable: true
      }
    });
    ```
    
- **`orderBy`**: Sorts the results.
    
    ```TypeScript
    const newestListings = await prisma.listing.findMany({
      orderBy: { id: 'desc' } // or 'asc'
    });
    ```
    
- **`take` and `skip`**: Used for pagination.
    
    ```TypeScript
    const pageTwo = await prisma.listing.findMany({
      skip: 10, // Skip the first 10 records
      take: 10  // Take the next 10
    });
    ```
    

### **4. Updating and Deleting Data**

- **`update`**: Updates a single existing record. You must provide a unique `where` clause and the `data` to change.
    
    ```TypeScript
    const markedRented = await prisma.listing.update({
      where: { id: 42 },
      data: { isAvailable: false }
    });
    ```
    
- **`delete`**: Removes a single record.
    
    ```TypeScript
    const deletedUser = await prisma.user.delete({
      where: { email: "nikunj@example.com" }
    });
    ```
    
- **`updateMany` / `deleteMany`**: Affects multiple records at once based on a condition.
    
    ```TypeScript
    // Delete all unavailable listings
    const cleanup = await prisma.listing.deleteMany({
      where: { isAvailable: false }
    });
    ```

### **5. Relational Queries (The `include` keyword)**

This is where Prisma outshines traditional SQL strings. If you want to fetch a record _and_ its related data in one go (e.g., getting a listing and the user who posted it), you use `include`.

```TypeScript
const listingWithProviderInfo = await prisma.listing.findUnique({
  where: { id: 105 },
  include: {
    provider: true // This fetches the entire User object associated with this Listing
  }
});

// You can now access: listingWithProviderInfo.provider.name
```

