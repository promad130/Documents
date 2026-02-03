# React Native Stack Navigation - Complete Guide

## Table of Contents
1. [Installation](#installation)
2. [Core Concepts](#core-concepts)
3. [Basic Setup](#basic-setup)
4. [Navigation Methods](#navigation-methods)
5. [Passing Parameters](#passing%20parameters)
6. [Screen Configuration](#screen-configuration)
7. [Navigation Props](#navigation-props)
8. [Header Customization](#header-customization)
9. [Dynamic Options](#dynamic-options)
10. [useNavigation Hook](#usenavigation-hook)
11. [Common Patterns](#common-patterns)

---

## Installation

Install required packages:

```bash
npm install @react-navigation/native
npm install @react-navigation/native-stack
npm install react-native-screens react-native-safe-area-context
```

---

## Core Concepts

### NavigationContainer

Wraps your entire app and manages navigation state. This is the root component that must wrap all navigators.

```javascript
import { NavigationContainer } from '@react-navigation/native';

function App() {
  return (
    <NavigationContainer>
      {/* Your navigators go here */}
    </NavigationContainer>
  );
}
```

### createNativeStackNavigator

Creates a stack navigator instance. Call this **once outside your component** to create `Stack` object.

```javascript
import { createNativeStackNavigator } from '@react-navigation/native-stack';

const Stack = createNativeStackNavigator();
```

### Stack.Navigator

Container component that defines navigation structure. All `Stack.Screen` components must be children of this.

**Props:**
- `initialRouteName` - The first screen shown when app starts
- `screenOptions` - Default options applied to all screens

```javascript
<Stack.Navigator initialRouteName="Home" screenOptions={{ headerShown: false }}>
  {/* Stack.Screen components */}
</Stack.Navigator>
```

### Stack.Screen

Defines individual screens in the stack. Each requires:

- `name` (required) - String identifier for navigation
- `component` (required) - React component to render
- `options` - Configuration for this specific screen

---

## Basic Setup

```javascript
import { NavigationContainer } from '@react-navigation/native';
import { createNativeStackNavigator } from '@react-navigation/native-stack';
import HomeScreen from './screens/HomeScreen';
import ProfileScreen from './screens/ProfileScreen';

const Stack = createNativeStackNavigator();

export default function App() {
  return (
    <NavigationContainer>
      <Stack.Navigator initialRouteName="Home">
        <Stack.Screen name="Home" component={HomeScreen} />
        <Stack.Screen name="Profile" component={ProfileScreen} />
        <Stack.Screen name="Settings" component={SettingsScreen} />
      </Stack.Navigator>
    </NavigationContainer>
  );
}
```

---

## Navigation Methods

Every screen component automatically receives a `navigation` prop, i.e.,:
```JavaScript
function HomeScreen ({Navigation})
{
	return (
		<Layout>
			// Contents here
		</Layout>
	);
}
```

with these methods:

### navigate(name, params)

Goes to a screen by name. If already on that screen, does nothing.

```javascript
// Simple navigation
navigation.navigate('Profile');

// With parameters
navigation.navigate('Profile', { userId: 123, name: 'John' });
```

### push(name, params)

Always adds a new instance of a screen on top, even if you're already on it.

```javascript
// Creates duplicate screen
navigation.push('Profile', { userId: 456 });
```

**Difference from navigate:**
- `navigate`: Goes to existing screen or creates new
- `push`: Always creates new instance

### pop(count)

Removes screens from the stack and goes back.

```javascript
navigation.pop();    // Go back 1 screen
navigation.pop(2);   // Go back 2 screens
```

### goBack()

Goes to the previous screen.

```javascript
navigation.goBack();
```

### popToTop()

Returns to the first screen in the stack.

```javascript
navigation.popToTop();
```

### replace(name, params)

Replaces current screen with new one (no back button).

```javascript
// Good for login flow - prevent going back to login after success
navigation.replace('Home');
```

### reset()

Resets navigation state completely.

```javascript
navigation.reset({
  index: 0,
  routes: [{ name: 'Home' }],
});
```

---

## Passing Parameters

### Sending Parameters

Pass data as second argument to navigation methods:

```javascript
navigation.navigate('Profile', {
  userId: 123,
  userName: 'John Doe',
  age: 25
});
```

### Receiving Parameters

Access parameters using `route.params`:

```javascript
function ProfileScreen({ route, navigation }) {
  const { userId, userName, age } = route.params;

  return (
    <View>
      <Text>User ID: {userId}</Text>
      <Text>Name: {userName}</Text>
      <Text>Age: {age}</Text>
    </View>
  );
}
```

### Default Parameter Values

Use destructuring with defaults:

```javascript
function ProfileScreen({ route }) {
  const { userId = 0, userName = 'Guest' } = route.params || {};

  return <Text>{userName}</Text>;
}
```

### Updating Parameters

Update params of current screen:

```javascript
navigation.setParams({ userId: 456 });
```

### Passing Params Back

Update previous screen's params:

```javascript
// Screen A
navigation.navigate('ScreenB', { 
  onGoBack: (data) => {
    console.log(data);
  }
});

// Screen B
route.params.onGoBack('some data');
navigation.goBack();
```

---

## Screen Configuration

### Individual Screen Options

Use `options` prop on `Stack.Screen`:

```javascript
<Stack.Screen 
  name="Profile" 
  component={ProfileScreen}
  options={{
    title: 'User Profile',
    headerStyle: {
      backgroundColor: '#f4511e',
    },
    headerTintColor: '#fff',
    headerTitleStyle: {
      fontWeight: 'bold',
    },
  }}
/>
```

### Global Screen Options

Apply options to all screens using `screenOptions`:

```javascript
<Stack.Navigator
  screenOptions={{
    headerStyle: {
      backgroundColor: '#f4511e',
    },
    headerTintColor: '#fff',
    headerShown: true,
  }}
>
  <Stack.Screen name="Home" component={HomeScreen} />
  <Stack.Screen name="Profile" component={ProfileScreen} />
</Stack.Navigator>
```

### Common Options

| Option | Type | Description |
|--------|------|-------------|
| `title` | string | Header title text |
| `headerShown` | boolean | Show/hide header |
| `headerStyle` | object | Style for header container |
| `headerTitleStyle` | object | Style for title text |
| `headerTintColor` | string | Color for back button & title |
| `headerBackTitle` | string | Custom back button text (iOS) |
| `headerLeft` | function | Custom left header component |
| `headerRight` | function | Custom right header component |
| `headerTitle` | function | Custom title component |
| `headerTransparent` | boolean | Transparent header |
| `animationEnabled` | boolean | Enable/disable transitions |
| `presentation` | string | Screen presentation style |

---

## Navigation Props

Every screen receives two props automatically:

### navigation

Contains navigation methods and state.

```javascript
function HomeScreen({ navigation }) {
  // navigation.navigate()
  // navigation.goBack()
  // navigation.setOptions()
  // etc.
}
```

### route

Contains route information and parameters.

```javascript
function ProfileScreen({ route }) {
  console.log(route.name);    // 'Profile'
  console.log(route.params);  // { userId: 123 }
  console.log(route.key);     // Unique identifier
}
```

---

## Header Customization

### Setting Title from Screen

```javascript
<Stack.Screen 
  name="Profile" 
  component={ProfileScreen}
  options={{ title: 'My Profile' }}
/>
```

### Dynamic Title Based on Params

```javascript
<Stack.Screen 
  name="Profile" 
  component={ProfileScreen}
  options={({ route }) => ({ 
    title: route.params.userName || 'Profile'
  })}
/>
```

### Custom Header Buttons

```javascript
<Stack.Screen 
  name="Home" 
  component={HomeScreen}
  options={({ navigation }) => ({
    headerRight: () => (
      <Button
        onPress={() => navigation.navigate('Settings')}
        title="Settings"
      />
    ),
  })}
/>
```

### Setting Options from Screen Component

Use `navigation.setOptions()` inside component:

```javascript
function ProfileScreen({ navigation }) {
  useEffect(() => {
    navigation.setOptions({
      title: 'Updated Title',
      headerRight: () => (
        <Button title="Save" onPress={handleSave} />
      ),
    });
  }, [navigation]);

  return <View>...</View>;
}
```

---

## Dynamic Options

### Options as Function

Access `navigation` and `route` in options:

```javascript
<Stack.Screen 
  name="Details" 
  component={DetailsScreen}
  options={({ route, navigation }) => ({
    title: route.params.itemName,
    headerRight: () => (
      <Button 
        title="Update"
        onPress={() => navigation.setParams({ itemName: 'New Name' })}
      />
    ),
  })}
/>
```

### Updating Options Based on State

```javascript
function EditScreen({ navigation }) {
  const [hasChanges, setHasChanges] = useState(false);

  useEffect(() => {
    navigation.setOptions({
      headerLeft: () => (
        hasChanges ? (
          <Button title="Cancel" onPress={handleCancel} />
        ) : (
          <Button title="Back" onPress={() => navigation.goBack()} />
        )
      ),
    });
  }, [hasChanges, navigation]);

  return <View>...</View>;
}
```

---

## useNavigation Hook

Access navigation object in nested components without prop drilling.

### Basic Usage

```javascript
import { useNavigation } from '@react-navigation/native';

function BackButton() {
  const navigation = useNavigation();

  return (
    <Button 
      title="Go Back" 
      onPress={() => navigation.goBack()}
    />
  );
}
```

### Use in Nested Components

```javascript
function HomeScreen() {
  return (
    <View>
      <Header />
      <Content />
      <Footer />
    </View>
  );
}

function Footer() {
  const navigation = useNavigation();

  return (
    <Button 
      title="Go to Settings"
      onPress={() => navigation.navigate('Settings')}
    />
  );
}
```

### useRoute Hook

Access route object in nested components:

```javascript
import { useRoute } from '@react-navigation/native';

function UserInfo() {
  const route = useRoute();
  const { userId } = route.params;

  return <Text>User ID: {userId}</Text>;
}
```

---

## Common Patterns

### Preventing Back Navigation

```javascript
// Login screen - prevent going back after login
function LoginScreen({ navigation }) {
  const handleLogin = async () => {
    await login();
    navigation.replace('Home'); // Can't go back to login
  };

  return <Button title="Login" onPress={handleLogin} />;
}
```

### Confirmation Before Going Back

```javascript
import { useEffect } from 'react';
import { Alert } from 'react-native';

function EditScreen({ navigation }) {
  const [hasUnsavedChanges, setHasUnsavedChanges] = useState(false);

  useEffect(() => {
    const unsubscribe = navigation.addListener('beforeRemove', (e) => {
      if (!hasUnsavedChanges) {
        return;
      }

      e.preventDefault(); // Prevent default back behavior

      Alert.alert(
        'Discard changes?',
        'You have unsaved changes. Are you sure to discard them?',
        [
          { text: "Don't leave", style: 'cancel' },
          {
            text: 'Discard',
            style: 'destructive',
            onPress: () => navigation.dispatch(e.data.action),
          },
        ]
      );
    });

    return unsubscribe;
  }, [navigation, hasUnsavedChanges]);

  return <View>...</View>;
}
```

### Passing Callbacks

```javascript
// List screen
function ListScreen({ navigation }) {
  const [items, setItems] = useState([]);

  const addItem = (newItem) => {
    setItems([...items, newItem]);
  };

  return (
    <View>
      <Button 
        title="Add Item"
        onPress={() => navigation.navigate('Add', { onAdd: addItem })}
      />
    </View>
  );
}

// Add screen
function AddScreen({ route, navigation }) {
  const handleSave = () => {
    const newItem = { id: 1, name: 'New Item' };
    route.params.onAdd(newItem);
    navigation.goBack();
  };

  return <Button title="Save" onPress={handleSave} />;
}
```

### Conditional Navigation

```javascript
function HomeScreen({ navigation }) {
  const isLoggedIn = useSelector(state => state.auth.isLoggedIn);

  const handleProfilePress = () => {
    if (isLoggedIn) {
      navigation.navigate('Profile');
    } else {
      navigation.navigate('Login');
    }
  };

  return <Button title="Profile" onPress={handleProfilePress} />;
}
```

### Nested Stacks

```javascript
// Auth Stack
function AuthStack() {
  return (
    <Stack.Navigator>
      <Stack.Screen name="Login" component={LoginScreen} />
      <Stack.Screen name="Signup" component={SignupScreen} />
    </Stack.Navigator>
  );
}

// Main Stack
function MainStack() {
  return (
    <Stack.Navigator>
      <Stack.Screen name="Home" component={HomeScreen} />
      <Stack.Screen name="Profile" component={ProfileScreen} />
    </Stack.Navigator>
  );
}

// Root
function App() {
  const isLoggedIn = useAuth();

  return (
    <NavigationContainer>
      {isLoggedIn ? <MainStack /> : <AuthStack />}
    </NavigationContainer>
  );
}
```

---

## Quick Reference

### Navigation Methods Cheat Sheet

```javascript
// Go to screen
navigation.navigate('ScreenName', { param: value });

// Always create new instance
navigation.push('ScreenName', { param: value });

// Go back
navigation.goBack();
navigation.pop();
navigation.pop(2);

// Go to first screen
navigation.popToTop();

// Replace current screen
navigation.replace('ScreenName');

// Reset navigation state
navigation.reset({
  index: 0,
  routes: [{ name: 'Home' }],
});

// Update current screen params
navigation.setParams({ param: newValue });

// Update screen options
navigation.setOptions({ title: 'New Title' });
```

### Screen Component Structure

```javascript
function MyScreen({ navigation, route }) {
  // Access params
  const { paramName } = route.params || {};

  // Navigate
  const goToNext = () => {
    navigation.navigate('Next', { data: 'value' });
  };

  // Update header
  useEffect(() => {
    navigation.setOptions({
      title: 'Custom Title',
      headerRight: () => <Button title="Action" onPress={handleAction} />,
    });
  }, [navigation]);

  return <View>...</View>;
}
```

---

## Best Practices

1. **Use `replace` for auth flows** - Prevent users from going back to login after successful authentication
2. **Define navigation types** - Use TypeScript for type-safe navigation
3. **Keep params serializable** - Pass JSON-serializable data only (no functions, classes)
4. **Use callbacks for data updates** - Pass callback functions through params for screen-to-screen communication
5. **Set initial params** - Define default params in `initialParams` prop
6. **Memoize options functions** - Prevent unnecessary re-renders when using dynamic options
7. **Handle deep linking** - Configure linking for opening specific screens from URLs
8. **Use navigation events** - Listen to focus/blur events for side effects

```javascript
// Example: Navigation events
useEffect(() => {
  const unsubscribe = navigation.addListener('focus', () => {
    // Screen came into focus - refresh data
    fetchData();
  });

  return unsubscribe;
}, [navigation]);
```

---

## Troubleshooting

### "Undefined is not an object (evaluating 'navigation.navigate')"

- Component is not a screen component
- Use `useNavigation` hook instead
- Or pass navigation as prop from parent

### "A navigator can only contain 'Screen' components"

- Don't add other components directly inside `Stack.Navigator`
- Only use `Stack.Screen` as children

### Params not updating

- Use `setParams` to update current screen params
- Pass new params when navigating to existing screen
- Use navigation state management for global state

---

This guide covers beginner to intermediate React Native navigation concepts. For advanced topics like custom navigators, deep linking configuration, and performance optimization, refer to the official React Navigation documentation.