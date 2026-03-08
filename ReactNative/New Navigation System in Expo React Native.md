## Part 1: What is Navigation?

### The Fundamental Concept

**Navigation** = Moving between different screens in your app.

Think of it like a book:
- Each **screen** = one page
- **Navigation** = flipping between pages
- **Navigator** = the book's binding that holds pages together

In mobile apps, you need to:
1. **Show** a screen
2. **Hide** a screen
3. **Remember** where you came from (so you can go back)

---

## Part 2: The Navigation Stack

### What is a Stack?

A **stack** is like a pile of plates:

```
┌─────────────┐  ← Screen 3 (Top - Currently Visible)
├─────────────┤  ← Screen 2 (Hidden underneath)
├─────────────┤  ← Screen 1 (Hidden underneath)
└─────────────┘  ← Bottom of stack
```

**Stack Operations:**

1. **PUSH** = Add a new screen on top
   ```
   You're on Screen 1 → Press button → Screen 2 pushes on top
   
   Before:              After:
   ┌─────────────┐     ┌─────────────┐
   │  Screen 1   │     │  Screen 2   │ ← New top
   └─────────────┘     ├─────────────┤
                       │  Screen 1   │
                       └─────────────┘
   ```

2. **POP** = Remove the top screen (go back)
   ```
   You're on Screen 2 → Press back → Screen 2 pops off
   
   Before:              After:
   ┌─────────────┐     ┌─────────────┐
   │  Screen 2   │     │  Screen 1   │ ← Back on top
   ├─────────────┤     └─────────────┘
   │  Screen 1   │
   └─────────────┘
   ```

3. **REPLACE** = Swap the top screen
   ```
   You're on Login → Login succeeds → Replace with Home
   
   Before:              After:
   ┌─────────────┐     ┌─────────────┐
   │   Login     │     │    Home     │ ← Can't go back to Login
   └─────────────┘     └─────────────┘
   ```

**Why Stack Matters:**
- Remembers navigation history
- Enables back button
- Manages screen lifecycle (mount/unmount)

---

## Part 3: Types of Navigation

### 1. Stack Navigation (Most Common)

**What it does:** Screens stack on top of each other vertically.

**Visual:**
```
Screen A → (tap button) → Screen B appears on top
                         [← Back button automatically added]
```

**Real-world use:**
- Home → Product Details → Reviews
- Settings → Privacy Settings → Change Password
- Feed → Post Details → Comments

**Code:**
```typescript name=app/_layout.tsx
import { Stack } from 'expo-router';
// Stack - The component that creates stack navigation
// expo-router - The library that handles navigation

export default function RootLayout() {
  return (
    <Stack>
      {/* Each Stack.Screen represents one screen in your stack */}
      <Stack.Screen name="index" />
      <Stack.Screen name="details" />
    </Stack>
  );
}
```

**Keyword Breakdown:**
- `Stack` - Container that manages the stack of screens
- `Stack.Screen` - Defines a single screen in the stack
- `name` - File name (without .tsx) that this screen represents
- `options` - Configuration for this screen (header, title, etc.)

---

### 2. Tab Navigation (Bottom Tabs)

**What it does:** Multiple screens accessible via tabs at the bottom.

**Visual:**
```
┌──────────────────────────────┐
│                              │
│     Current Tab Content      │
│                              │
├──────────────────────────────┤
│ [Home] [Search] [Profile]    │ ← Tabs always visible
└──────────────────────────────┘
```

**Stack behavior:**
- Each tab has its OWN independent stack
- Switching tabs doesn't lose your position

**Example:**
```
Tab 1 (Home):     Tab 2 (Search):    Tab 3 (Profile):
┌���────────┐       ┌─────────┐        ┌─────────┐
│Post Det │       │Results  │        │Profile  │
├─────────┤       └─────────┘        └─────────┘
│Feed     │
└─────────┘

When you switch from Tab 1 to Tab 2:
- Tab 1 keeps "Post Details" on its stack
- Tab 2 shows "Results"
- Go back to Tab 1, you're still on "Post Details"
```

**Real-world use:**
- Instagram: Feed | Search | Reels | Shop | Profile
- Twitter: Home | Search | Notifications | Messages

**Code:**
```typescript name=app/(tabs)/_layout.tsx
import { Tabs } from 'expo-router';
// Tabs - Component that creates bottom tab navigation

import { Ionicons } from '@expo/vector-icons';
// Ionicons - Icon library (optional, for tab icons)

export default function TabLayout() {
  return (
    <Tabs>
      <Tabs.Screen 
        name="home"
        // name - The file name this tab points to (home.tsx)
        
        options={{
          // options - Configuration object for this tab
          
          title: 'Home',
          // title - Text shown in the tab label
          
          tabBarIcon: ({ color, size }) => (
            // tabBarIcon - Function that returns the icon component
            // color - Automatically provided (active/inactive color)
            // size - Automatically provided icon size
            
            <Ionicons name="home" size={size} color={color} />
          ),
        }}
      />
      
      <Tabs.Screen 
        name="search"
        options={{
          title: 'Search',
          tabBarIcon: ({ color, size }) => (
            <Ionicons name="search" size={size} color={color} />
          ),
        }}
      />
    </Tabs>
  );
}
```

**Keyword Breakdown:**
- `Tabs` - Container for tab navigation
- `Tabs.Screen` - One tab in the tab bar
- `tabBarIcon` - Visual icon for the tab
- `color` - Changes based on active/inactive state
- `size` - Standard size for consistency

---

### 3. Drawer Navigation (Side Menu)

**What it does:** Screens accessible via a side drawer that slides in.

**Visual:**
```
Closed:                    Open (swipe or tap menu):
┌──────────────────┐      ┌────────┬──────────────┐
�� [☰] Home Screen  │      │ Home   │              │
│                  │  →   │ About  │ Home Screen  │
│                  │      │ Settings│             │
└──────────────────┘      └────────┴──────────────┘
```

**Real-world use:**
- Gmail: Inbox | Sent | Drafts | Trash
- Settings apps with many sections
- Apps with multiple main sections

**Code:**
```typescript name=app/_layout.tsx
import { Drawer } from 'expo-router/drawer';
// Drawer - Component for drawer/side-menu navigation

export default function DrawerLayout() {
  return (
    <Drawer>
      <Drawer.Screen 
        name="home"
        options={{
          drawerLabel: 'Home',
          // drawerLabel - Text shown in the drawer
          
          title: 'Home Screen',
          // title - Text shown in the header when this screen is active
          
          drawerIcon: ({ color, size }) => (
            <Ionicons name="home" size={size} color={color} />
          ),
        }}
      />
      
      <Drawer.Screen 
        name="settings"
        options={{
          drawerLabel: 'Settings',
          title: 'App Settings',
        }}
      />
    </Drawer>
  );
}
```

**Keyword Breakdown:**
- `Drawer` - Container for drawer navigation
- `Drawer.Screen` - One item in the drawer menu
- `drawerLabel` - Text in the drawer sidebar
- `drawerIcon` - Icon in the drawer sidebar

---

## Part 4: The Router Object

### What is `router`?

`router` is an **object** that lets you navigate programmatically (with code, not just links).

**Think of it as a remote control for your app's navigation.**

```typescript
import { router } from 'expo-router';
// router - Object with methods to control navigation
```

### Router Methods

#### 1. `router.push()`
**What:** Add a new screen on top of the stack (you can go back).

```typescript
router.push('/details');

// Stack before:        Stack after:
// ┌─────────┐         ┌─────────┐
// │  Home   │         │ Details │ ← New screen added
// └─────────┘         ├─────────┤
//                     │  Home   │ ← Still in stack
//                     └─────────┘
```

**Use when:** User action that they might want to go back from.

```typescript name=app/index.tsx
import { Button } from 'react-native';
import { router } from 'expo-router';

export default function Home() {
  return (
    <Button 
      title="View Details" 
      onPress={() => router.push('/details')}
      // When pressed, navigate to /details screen
    />
  );
}
```

---

#### 2. `router.replace()`
**What:** Swap the current screen (can't go back).

```typescript
router.replace('/home');

// Stack before:        Stack after:
// ┌─────────┐         ┌─────────┐
// │  Login  │         │  Home   │ ← Login removed, Home replaces it
// └─────────┘         └─────────┘
```

**Use when:** Login → Home (can't go back to login after logging in).

```typescript name=app/login.tsx
import { Button } from 'react-native';
import { router } from 'expo-router';

function handleLogin() {
  // ... login logic ...
  
  router.replace('/home');
  // User can't press back to return to login
}
```

---

#### 3. `router.back()`
**What:** Go back to previous screen.

```typescript
router.back();

// Stack before:        Stack after:
// ┌─────────┐         ┌─────────┐
// │ Details │         │  Home   │ ← Back to home
// ├─────────┤         └─────────┘
// │  Home   │
// └─────────┘
```

**Use when:** Custom back button, cancel action.

```typescript name=app/details.tsx
import { Button } from 'react-native';
import { router } from 'expo-router';

export default function Details() {
  return (
    <Button 
      title="Cancel" 
      onPress={() => router.back()}
      // Go back to previous screen
    />
  );
}
```

---

#### 4. `router.canGoBack()`
**What:** Check if there's a previous screen.

```typescript
if (router.canGoBack()) {
  router.back();
} else {
  router.replace('/home');
}
```

**Use when:** Handling back button safely.

---

#### 5. `router.setParams()`
**What:** Update current screen's parameters without navigating.

```typescript
router.setParams({ sort: 'newest' });
// Changes URL from /posts to /posts?sort=newest
// Screen re-renders with new params
```

---

### Passing Data with Router

#### Method 1: URL Parameters

```typescript
// Navigate with data
router.push('/users/123');
// or
router.push({
  pathname: '/users/[id]',
  params: { id: '123' }
});

// Receive data
import { useLocalSearchParams } from 'expo-router';

export default function UserScreen() {
  const { id } = useLocalSearchParams();
  // id = '123'
  
  return <Text>User ID: {id}</Text>;
}
```

#### Method 2: Query Parameters

```typescript
// Navigate with multiple parameters
router.push({
  pathname: '/search',
  params: { 
    query: 'react native',
    filter: 'recent',
    page: '1'
  }
});
// URL: /search?query=react+native&filter=recent&page=1

// Receive data
export default function SearchScreen() {
  const { query, filter, page } = useLocalSearchParams();
  // query = 'react native'
  // filter = 'recent'
  // page = '1'
}
```

---

## Part 5: The Link Component

### What is `<Link>`?

`<Link>` is a **component** for navigation (like an `<a>` tag in HTML).

**Router vs Link:**
- `router.push()` - Imperative (do it now with code)
- `<Link>` - Declarative (let user tap to navigate)

```typescript name=app/index.tsx
import { Link } from 'expo-router';
import { Text } from 'react-native';

export default function Home() {
  return (
    <Link href="/about">
      {/* href - Where to navigate (like <a href="..."> in HTML) */}
      <Text>Go to About</Text>
    </Link>
  );
}
```

### Link with Custom Styling

```typescript
import { Link } from 'expo-router';
import { Pressable, Text } from 'react-native';

export default function Home() {
  return (
    <Link href="/profile" asChild>
      {/* asChild - Use the child component as the touchable */}
      <Pressable style={{ padding: 20, backgroundColor: 'blue' }}>
        <Text style={{ color: 'white' }}>Profile</Text>
      </Pressable>
    </Link>
  );
}
```

**Keyword Breakdown:**
- `href` - Destination path
- `asChild` - Makes Link use child component's touchable behavior
- Without `asChild` - Link creates its own touchable wrapper

---

## Part 6: Layout Files (`_layout.tsx`)

### What is `_layout.tsx`?

A **layout file** defines HOW child screens are displayed and navigated.

**Think of it as a picture frame:**
- The **frame** = `_layout.tsx` (defines structure)
- The **picture** = screen content (changes)

### When Layout is REQUIRED

```
app/
├── _layout.tsx         ← REQUIRED (root layout, always needed)
└── about.tsx
```

**Without root layout:** App won't work. No navigation structure.

```typescript name=app/_layout.tsx
import { Stack } from 'expo-router';

export default function RootLayout() {
  return <Stack />; 
  // Creates a stack navigator for all screens
}
```

---

### When Layout Changes Behavior

#### Example: Creating Tabs

```
app/
└── (tabs)/
    ├── _layout.tsx     ← NEEDED (defines tab behavior)
    ├── home.tsx
    ├── search.tsx
    └── profile.tsx
```

**Without `(tabs)/_layout.tsx`:**
- `home.tsx`, `search.tsx`, `profile.tsx` would be regular stack screens
- No bottom tabs would appear

**With `(tabs)/_layout.tsx`:**
```typescript name=app/(tabs)/_layout.tsx
import { Tabs } from 'expo-router';

export default function TabLayout() {
  return (
    <Tabs>
      {/* Now home, search, profile appear as tabs */}
      <Tabs.Screen name="home" />
      <Tabs.Screen name="search" />
      <Tabs.Screen name="profile" />
    </Tabs>
  );
}
```

**Result:** Bottom tab bar appears with 3 tabs.

---

### When Layout is NOT Needed

```
app/
├── _layout.tsx         ← Root Stack
└── users/
    ├── index.tsx       ← /users
    └── [id].tsx        ← /users/123
```

**No need for `users/_layout.tsx` because:**
- Root layout already provides Stack navigation
- `index.tsx` and `[id].tsx` automatically use that Stack
- They'll stack on top with back button working

---

## Part 7: Nested Navigation

### What is Nested Navigation?

**Nesting** = Putting one navigator inside another.

**Example:** Tabs inside a Stack

```
Stack (Root)
├── Login Screen (no tabs)
├── Register Screen (no tabs)
└── Tab Navigator
    ├── Home Tab (has its own stack)
    │   ├── Feed
    │   └── Post Details
    ├── Search Tab (has its own stack)
    │   ├── Search
    │   └── Results
    └── Profile Tab (has its own stack)
        ├── Profile
        └── Edit Profile
```

**File Structure:**
```
app/
├── _layout.tsx                    ← Root Stack
├── login.tsx
├── register.tsx
└── (tabs)/                        ← Tabs nested in Stack
    ├── _layout.tsx                
    ├── (home)/                    ← Stack nested in Tab
    │   ├── _layout.tsx
    │   ├── index.tsx              ← Feed
    │   └── post-details.tsx
    ├── (search)/                  ← Stack nested in Tab
    │   ├── _layout.tsx
    │   ├── index.tsx              ← Search
    │   └── results.tsx
    └── (profile)/                 ← Stack nested in Tab
        ├── _layout.tsx
        ├── index.tsx              ← Profile
        └── edit.tsx
```

### Root Stack Layout

```typescript name=app/_layout.tsx
import { Stack } from 'expo-router';

export default function RootLayout() {
  return (
    <Stack>
      <Stack.Screen name="login" />
      <Stack.Screen name="register" />
      <Stack.Screen 
        name="(tabs)" 
        options={{ headerShown: false }}
        // headerShown: false - Hide header for tab screens
        // (tabs will have their own headers)
      />
    </Stack>
  );
}
```

### Tab Layout (Nested in Stack)

```typescript name=app/(tabs)/_layout.tsx
import { Tabs } from 'expo-router';

export default function TabLayout() {
  return (
    <Tabs>
      <Tabs.Screen name="(home)" options={{ title: 'Home' }} />
      <Tabs.Screen name="(search)" options={{ title: 'Search' }} />
      <Tabs.Screen name="(profile)" options={{ title: 'Profile' }} />
    </Tabs>
  );
}
```

### Stack Inside a Tab

```typescript name=app/(tabs)/(home)/_layout.tsx
import { Stack } from 'expo-router';

export default function HomeStackLayout() {
  return (
    <Stack>
      <Stack.Screen name="index" options={{ title: 'Feed' }} />
      <Stack.Screen name="post-details" options={{ title: 'Post' }} />
    </Stack>
  );
}
```

**Navigation Flow:**

```
User on Feed (Home Tab):
Tab Bar: [Home] Search Profile

↓ Tap on post

Post Details (still in Home Tab):
Tab Bar: [Home] Search Profile
[← Back] button appears

↓ Press back

Feed (Home Tab):
Tab Bar: [Home] Search Profile

↓ Switch to Search Tab

Search (Search Tab):
Tab Bar: Home [Search] Profile
(Feed and Post Details still in Home Tab's stack, preserved)

↓ Switch back to Home Tab

Post Details (still there!):
Tab Bar: [Home] Search Profile
(You return to Post Details, not Feed)
```

---

## Part 8: Route Groups `(folder)`

### What are Route Groups?

**Route Groups** = Folders wrapped in parentheses `(name)` that DON'T affect the URL.

**Without Route Groups:**
```
app/
└── tabs/
    ├── home.tsx       → URL: /tabs/home ❌
    └── profile.tsx    → URL: /tabs/profile ❌
```

**With Route Groups:**
```
app/
└── (tabs)/
    ├── home.tsx       → URL: /home ✅
    └── profile.tsx    → URL: /profile ✅
```

**Why:** Keep code organized without messy URLs.

### Use Case: Organizing Auth Screens

```
app/
├── _layout.tsx
├── (auth)/                    ← Group auth screens
│   ├── login.tsx              → URL: /login (not /auth/login)
│   ├── register.tsx           → URL: /register
│   └── forgot-password.tsx    → URL: /forgot-password
└── (app)/                     ← Group main app screens
    ├── home.tsx               → URL: /home (not /app/home)
    └── settings.tsx           → URL: /settings
```

**Benefit:** Clean separation in code, clean URLs for users.

---

## Part 9: Dynamic Routes `[param]`

### What are Dynamic Routes?

**Dynamic Routes** = URLs with variable segments.

**Static Route:**
```
app/
└── profile.tsx    → /profile (always the same)
```

**Dynamic Route:**
```
app/
└── users/
    └── [id].tsx   → /users/1, /users/2, /users/999 (any number)
```

### Implementation

```typescript name=app/users/[id].tsx
import { useLocalSearchParams } from 'expo-router';
// useLocalSearchParams - Hook to get URL parameters

import { View, Text } from 'react-native';

export default function UserProfile() {
  const { id } = useLocalSearchParams<{ id: string }>();
  // id - The value from the URL
  // <{ id: string }> - TypeScript type for safety
  
  // URL: /users/123
  // id = '123'
  
  return (
    <View>
      <Text>User ID: {id}</Text>
    </View>
  );
}
```

### Navigating to Dynamic Routes

```typescript name=app/index.tsx
import { router } from 'expo-router';
import { Button } from 'react-native';

export default function Home() {
  return (
    <Button 
      title="View User 123" 
      onPress={() => router.push('/users/123')}
      // or
      // onPress={() => router.push({
      //   pathname: '/users/[id]',
      //   params: { id: '123' }
      // })}
    />
  );
}
```

### Multiple Dynamic Segments

```
app/
└── posts/
    └── [category]/
        └── [slug].tsx

URLs:
- /posts/tech/intro-to-react  → category='tech', slug='intro-to-react'
- /posts/food/best-pizza      → category='food', slug='best-pizza'
```

```typescript name=app/posts/[category]/[slug].tsx
export default function Post() {
  const { category, slug } = useLocalSearchParams<{ 
    category: string;
    slug: string;
  }>();
  
  return (
    <Text>
      Category: {category}
      Slug: {slug}
    </Text>
  );
}
```

---

## Part 10: Complete Real-World Example

### App: Social Media Clone

**Features:**
- Login/Register (no tabs)
- Main app with tabs (Feed, Search, Profile)
- Each tab has its own navigation
- View posts, users, comments

**File Structure:**
```
app/
├── _layout.tsx                         ← Root Stack
├── index.tsx                           ← Landing page
│
├── (auth)/                             ← Auth screens (no tabs)
│   ├── login.tsx
│   └── register.tsx
│
└── (main)/                             ← Main app with tabs
    ├── _layout.tsx                     ← Tab Navigator
    │
    ├── (feed)/                         ← Feed tab with stack
    │   ├── _layout.tsx
    │   ├── index.tsx                   ← Feed
    │   ├── post-[id].tsx               ← Single post
    │   └── comments-[id].tsx           ← Comments
    │
    ├── (search)/                       ← Search tab with stack
    │   ├── _layout.tsx
    │   ├── index.tsx                   ← Search
    │   └── results.tsx                 ← Results
    │
    └── (profile)/                      ← Profile tab with stack
        ├── _layout.tsx
        ├── index.tsx                   ← Own profile
        ├── edit.tsx                    ← Edit profile
        └── user-[id].tsx               ← Other user's profile
```

### Root Layout

```typescript name=app/_layout.tsx
import { useEffect } from 'react';
import { Stack, useRouter, useSegments } from 'expo-router';

// Mock auth hook
function useAuth() {
  // In real app: check AsyncStorage, API, etc.
  const [user, setUser] = useState(null);
  return { user, loading: false };
}

export default function RootLayout() {
  const { user, loading } = useAuth();
  const segments = useSegments();
  // segments - Array of current route segments
  // e.g., ['(main)', '(feed)', 'index'] when on feed
  
  const router = useRouter();

  useEffect(() => {
    if (loading) return; // Wait for auth check
    
    const inAuthGroup = segments[0] === '(auth)';
    const inMainGroup = segments[0] === '(main)';

    if (!user && !inAuthGroup) {
      // Not logged in, not on auth screen → redirect to login
      router.replace('/(auth)/login');
    } else if (user && inAuthGroup) {
      // Logged in, on auth screen → redirect to app
      router.replace('/(main)/(feed)');
    }
  }, [user, loading, segments]);

  return (
    <Stack screenOptions={{ headerShown: false }}>
      {/* screenOptions - Default options for all screens */}
      {/* headerShown: false - Hide header (tabs will show their own) */}
      
      <Stack.Screen name="index" />
      <Stack.Screen name="(auth)" />
      <Stack.Screen name="(main)" />
    </Stack>
  );
}
```

**What happens:**
1. App loads
2. Check if user is logged in
3. If not → redirect to login
4. If yes → redirect to feed
5. Constantly monitor (via `useEffect`) and redirect as needed

---

### Tab Layout

```typescript name=app/(main)/_layout.tsx
import { Tabs } from 'expo-router';
import { Ionicons } from '@expo/vector-icons';

export default function MainLayout() {
  return (
    <Tabs
      screenOptions={{
        // screenOptions - Default options for ALL tabs
        
        tabBarActiveTintColor: '#007AFF',
        // tabBarActiveTintColor - Color when tab is active
        
        tabBarInactiveTintColor: '#8E8E93',
        // tabBarInactiveTintColor - Color when tab is inactive
        
        headerShown: false,
        // headerShown - Hide tab headers (stacks inside will show their own)
        
        tabBarStyle: {
          backgroundColor: '#FFFFFF',
          borderTopWidth: 1,
          borderTopColor: '#E5E5EA',
        },
        // tabBarStyle - Style the tab bar container
      }}
    >
      <Tabs.Screen 
        name="(feed)"
        options={{
          title: 'Feed',
          // title - Text under tab icon
          
          tabBarIcon: ({ color, size, focused }) => (
            // focused - Boolean, true if this tab is active
            
            <Ionicons 
              name={focused ? 'home' : 'home-outline'}
              // Show solid icon when active, outline when inactive
              size={size} 
              color={color} 
            />
          ),
        }}
      />
      
      <Tabs.Screen 
        name="(search)"
        options={{
          title: 'Search',
          tabBarIcon: ({ color, size, focused }) => (
            <Ionicons 
              name={focused ? 'search' : 'search-outline'} 
              size={size} 
              color={color} 
            />
          ),
        }}
      />
      
      <Tabs.Screen 
        name="(profile)"
        options={{
          title: 'Profile',
          tabBarIcon: ({ color, size, focused }) => (
            <Ionicons 
              name={focused ? 'person' : 'person-outline'} 
              size={size} 
              color={color} 
            />
          ),
        }}
      />
    </Tabs>
  );
}
```

---

### Feed Stack Layout

```typescript name=app/(main)/(feed)/_layout.tsx
import { Stack } from 'expo-router';

export default function FeedLayout() {
  return (
    <Stack
      screenOptions={{
        headerStyle: { backgroundColor: '#FFFFFF' },
        // headerStyle - Style the header bar
        
        headerTintColor: '#000000',
        // headerTintColor - Color for back button and title
        
        headerShadowVisible: false,
        // headerShadowVisible - Show/hide shadow under header
      }}
    >
      <Stack.Screen 
        name="index" 
        options={{ 
          title: 'Feed',
          headerLargeTitle: true,
          // headerLargeTitle - iOS large title (scrolls up)
        }} 
      />
      
      <Stack.Screen 
        name="post-[id]" 
        options={{ 
          title: 'Post',
          headerBackTitle: 'Back',
          // headerBackTitle - Custom back button text (iOS)
        }} 
      />
      
      <Stack.Screen 
        name="comments-[id]" 
        options={{ 
          title: 'Comments',
          presentation: 'modal',
          // presentation - How screen appears
          // 'modal' - Slides up from bottom
          // 'card' - Slides from right (default)
          // 'transparentModal' - Modal with transparent background
        }} 
      />
    </Stack>
  );
}
```

---

### Feed Screen

```typescript name=app/(main)/(feed)/index.tsx
import { View, FlatList, Text, Pressable } from 'react-native';
import { router } from 'expo-router';

// Mock data
const posts = [
  { id: '1', title: 'First Post', author: 'Alice' },
  { id: '2', title: 'Second Post', author: 'Bob' },
];

export default function Feed() {
  return (
    <FlatList
      data={posts}
      renderItem={({ item }) => (
        <Pressable 
          onPress={() => router.push(`/post-${item.id}`)}
          // Navigate to /post-1, /post-2, etc.
          // This matches app/(main)/(feed)/post-[id].tsx
        >
          <View style={{ padding: 20, borderBottomWidth: 1 }}>
            <Text style={{ fontSize: 18, fontWeight: 'bold' }}>
              {item.title}
            </Text>
            <Text style={{ color: '#666' }}>
              by {item.author}
            </Text>
          </View>
        </Pressable>
      )}
      keyExtractor={(item) => item.id}
    />
  );
}
```

---

### Post Detail Screen

```typescript name=app/(main)/(feed)/post-[id].tsx
import { View, Text, Button } from 'react-native';
import { useLocalSearchParams, router } from 'expo-router';

export default function PostDetail() {
  const { id } = useLocalSearchParams<{ id: string }>();
  // id from URL: /post-1 → id = '1'

  // In real app: fetch post data using this id
  const post = {
    id,
    title: 'Post Title',
    content: 'Post content goes here...',
    comments: 42,
  };

  return (
    <View style={{ padding: 20 }}>
      <Text style={{ fontSize: 24, fontWeight: 'bold' }}>
        {post.title}
      </Text>
      
      <Text style={{ marginTop: 10 }}>
        {post.content}
      </Text>
      
      <Button 
        title={`View ${post.comments} Comments`}
        onPress={() => router.push(`/comments-${id}`)}
        // Navigate to comments screen for this post
      />
      
      <Button 
        title="Go Back"
        onPress={() => router.back()}
        // Go back to feed
      />
    </View>
  );
}
```

**Navigation Stack at this point:**
```
┌─────────────────┐  ← Post Detail (current screen)
├─────────────────┤
│  Feed           │  ← Can go back here
└─────────────────┘
```

---

### Comments Screen (Modal)

```typescript name=app/(main)/(feed)/comments-[id].tsx
import { View, Text, FlatList, Button } from 'react-native';
import { useLocalSearchParams, router } from 'expo-router';

export default function Comments() {
  const { id } = useLocalSearchParams<{ id: string }>();

  const comments = [
    { id: '1', text: 'Great post!', author: 'Charlie' },
    { id: '2', text: 'Thanks for sharing', author: 'Diana' },
  ];

  return (
    <View style={{ flex: 1 }}>
      <View style={{ padding: 20, borderBottomWidth: 1 }}>
        <Text style={{ fontSize: 20, fontWeight: 'bold' }}>
          Comments for Post {id}
        </Text>
        
        <Button 
          title="Close"
          onPress={() => router.back()}
          // Dismiss modal, return to post detail
        />
      </View>
      
      <FlatList
        data={comments}
        renderItem={({ item }) => (
          <View style={{ padding: 15, borderBottomWidth: 1 }}>
            <Text style={{ fontWeight: 'bold' }}>{item.author}</Text>
            <Text>{item.text}</Text>
          </View>
        )}
        keyExtractor={(item) => item.id}
      />
    </View>
  );
}
```

**Navigation Stack:**
```
┌─────────────────┐  ← Comments Modal (slides up from bottom)
├─────────────────┤
│  Post Detail    │  ← Still visible behind modal (slightly)
├─────────────────┤
│  Feed           │
└─────────────────┘
```

---

### Profile Stack

```typescript name=app/(main)/(profile)/_layout.tsx
import { Stack } from 'expo-router';

export default function ProfileLayout() {
  return (
    <Stack>
      <Stack.Screen 
        name="index" 
        options={{ title: 'Profile' }} 
      />
      
      <Stack.Screen 
        name="edit" 
        options={{ 
          title: 'Edit Profile',
          presentation: 'modal',
        }} 
      />
      
      <Stack.Screen 
        name="user-[id]" 
        options={{ title: 'User Profile' }} 
      />
    </Stack>
  );
}
```

---

### Profile Screen

```typescript name=app/(main)/(profile)/index.tsx
import { View, Text, Button } from 'react-native';
import { router } from 'expo-router';

export default function Profile() {
  const currentUser = {
    id: '123',
    name: 'John Doe',
    bio: 'Software Developer',
  };

  return (
    <View style={{ padding: 20 }}>
      <Text style={{ fontSize: 24, fontWeight: 'bold' }}>
        {currentUser.name}
      </Text>
      
      <Text style={{ marginTop: 10 }}>
        {currentUser.bio}
      </Text>
      
      <Button 
        title="Edit Profile"
        onPress={() => router.push('/(main)/(profile)/edit')}
        // Full path to edit screen
      />
      
      <Button 
        title="View Another User"
        onPress={() => router.push('/user-456')}
        // Navigate to another user's profile
      />
      
      <Button 
        title="Logout"
        onPress={() => {
          // Clear auth token
          router.replace('/(auth)/login');
          // Replace so user can't go back
        }}
      />
    </View>
  );
}
```

---

## Part 11: Navigation Stack Visualization

### Scenario: User Journey

**1. User opens app (not logged in)**
```
Stack:
┌─────────────┐
│   Login     │ ← Redirected here
└─────────────┘
```

**2. User logs in**
```
router.replace('/(main)/(feed)') executed

Stack:
┌─────────────┐
│   Feed      │ ← Login removed, can't go back
└─────────────┘

Tab Bar: [Feed] Search Profile
```

**3. User taps on a post**
```
router.push('/post-1') executed

Stack:
┌─────────────┐
│  Post 1     │ ← Pushed on top
├─────────────┤
│   Feed      │ ← Still in memory
└─────────────┘

Tab Bar: [Feed] Search Profile
```

**4. User opens comments**
```
router.push('/comments-1') executed
(presentation: 'modal' in layout)

Stack:
┌─────────────┐
│ Comments    │ ← Modal on top
├─────────────┤
│  Post 1     │
├─────────────┤
│   Feed      │
└─────────────┘

Tab Bar: [Feed] Search Profile
```

**5. User closes comments (back)**
```
router.back() executed

Stack:
┌─────────────┐
│  Post 1     │ ← Comments removed
├─────────────┤
│   Feed      │
└─────────────┘

Tab Bar: [Feed] Search Profile
```

**6. User switches to Search tab**
```
Tap on Search tab

Feed Stack (preserved):  Search Stack (activated):
┌─────────────┐         ┌─────────────┐
│  Post 1     │         │  Search     │ ← Now visible
├─────────────┤         └─────────────┘
│   Feed      │
└─────────────┘

Tab Bar: Feed [Search] Profile
```

**7. User switches back to Feed tab**
```
Tap on Feed tab

Feed Stack (restored):
┌─────────────┐
│  Post 1     │ ← Still here! Not reset
├─────────────┤
│   Feed      │
└─────────────┘

Tab Bar: [Feed] Search Profile
```

**8. User presses back button**
```
router.back() executed

Stack:
┌─────────────┐
│   Feed      │ ← Back to feed
└─────────────┘

Tab Bar: [Feed] Search Profile
```

---

## Part 12: Key Concepts Summary

### 1. Stack Navigation
- **Purpose:** Linear navigation (forward/back)
- **Stack operations:** Push, pop, replace
- **Use for:** Most app flows

### 2. Tab Navigation
- **Purpose:** Parallel sections of app
- **Each tab:** Independent stack
- **Use for:** Main app sections

### 3. Drawer Navigation
- **Purpose:** Side menu access
- **Use for:** Apps with many sections

### 4. Router
- **Methods:** `push()`, `replace()`, `back()`, `canGoBack()`, `setParams()`
- **Purpose:** Programmatic navigation

### 5. Link
- **Component:** `<Link href="...">` 
- **Purpose:** Declarative navigation

### 6. Layouts
- **File:** `_layout.tsx`
- **Purpose:** Define navigation structure
- **Required:** Root layout always, others when changing behavior

### 7. Route Groups
- **Syntax:** `(name)/`
- **Purpose:** Organize files without affecting URLs

### 8. Dynamic Routes
- **Syntax:** `[param].tsx`
- **Purpose:** URLs with variables
- **Hook:** `useLocalSearchParams()`

---

## Part 13: Common Patterns

### Pattern 1: Protected Routes

```typescript name=app/_layout.tsx
const { user } = useAuth();
const segments = useSegments();
const router = useRouter();

useEffect(() => {
  const inAuthGroup = segments[0] === '(auth)';
  
  if (!user && !inAuthGroup) {
    router.replace('/(auth)/login');
  } else if (user && inAuthGroup) {
    router.replace('/(app)/home');
  }
}, [user, segments]);
```

### Pattern 2: Conditional Tab Rendering

```typescript name=app/(tabs)/_layout.tsx
const { user } = useAuth();

<Tabs>
  <Tabs.Screen name="home" />
  <Tabs.Screen name="search" />
  
  {user?.isAdmin && (
    <Tabs.Screen name="admin" />
  )}
</Tabs>
```

### Pattern 3: Nested Stack in Tab

```
(tabs)/
├── _layout.tsx          → Tabs
└── (profile)/
    ├── _layout.tsx      → Stack
    ├── index.tsx        → Profile home
    ├── edit.tsx         → Edit (push)
    └── settings.tsx     → Settings (push)
```
