# React Native Bottom Tab Navigation - Complete Guide

## Table of Contents
1. [Installation](#installation)
2. [Core Concepts](#core-concepts)
3. [Basic Setup](#basic-setup)
4. [Tab Configuration](#tab%20configuration)
5. [Icons and Styling](#icons-and-styling)
6. [Tab Options](#tab-options)
7. [Navigation Methods](#navigation-methods)
8. [Passing Parameters](#passing-parameters)
9. [Nested Navigation](#nested-navigation)
10. [Custom Tab Bar](#custom-tab-bar)
11. [Common Patterns](#common-patterns)

---

## Installation

Install required packages:

```bash
npm install @react-navigation/native
npm install @react-navigation/bottom-tabs
npm install react-native-screens react-native-safe-area-context
npm install react-native-vector-icons  # For icons
```

---

## Core Concepts

### Bottom Tab Navigator

A tab bar at the bottom of the screen that lets you switch between different routes [web:157]. Each tab represents a different screen, and tapping a tab switches to that screen immediately.

**Key Features:**
- Always visible at the bottom
- Quick switching between main sections
- Lazy loading - screens mount only when first accessed [web:157]
- Remembers scroll position when switching tabs

### createBottomTabNavigator

Creates a tab navigator instance. Call this **once outside your component** to create `Tab` object.

```javascript
import { createBottomTabNavigator } from '@react-navigation/bottom-tabs';

const Tab = createBottomTabNavigator();
```

### Tab.Navigator

Container component that holds all tab screens. Similar to `Stack.Navigator` but for tabs.

**Props:**
- `initialRouteName` - The tab shown first
- `screenOptions` - Default options for all tabs
- `tabBarOptions` - Customization for the tab bar appearance (deprecated in v6, use `screenOptions`)

### Tab.Screen

Defines individual tabs. Each requires:

- `name` (required) - Tab identifier
- `component` (required) - Screen component to render
- `options` - Tab-specific configuration

---

## Basic Setup

```javascript
import { NavigationContainer } from '@react-navigation/native';
import { createBottomTabNavigator } from '@react-navigation/bottom-tabs';
import HomeScreen from './screens/HomeScreen';
import ProfileScreen from './screens/ProfileScreen';
import SettingsScreen from './screens/SettingsScreen';

const Tab = createBottomTabNavigator();

export default function App() {
  return (
    <NavigationContainer>
      <Tab.Navigator>
        <Tab.Screen name="Home" component={HomeScreen} />
        <Tab.Screen name="Profile" component={ProfileScreen} />
        <Tab.Screen name="Settings" component={SettingsScreen} />
      </Tab.Navigator>
    </NavigationContainer>
  );
}
```

This creates a simple tab bar with three tabs at the bottom [web:157].

---

## Tab Configuration

### Setting Initial Tab

```javascript
<Tab.Navigator initialRouteName="Home">
  <Tab.Screen name="Home" component={HomeScreen} />
  <Tab.Screen name="Profile" component={ProfileScreen} />
</Tab.Navigator>
```

### Global Tab Bar Styling

Apply options to all tabs using `screenOptions`:

```javascript
<Tab.Navigator
  screenOptions={{
    tabBarActiveTintColor: '#e91e63',
    tabBarInactiveTintColor: 'gray',
    tabBarStyle: {
      backgroundColor: '#fff',
      borderTopWidth: 0,
      elevation: 5,
    },
    tabBarLabelStyle: {
      fontSize: 12,
      fontWeight: 'bold',
    },
    headerShown: false,
  }}
>
  <Tab.Screen name="Home" component={HomeScreen} />
  <Tab.Screen name="Profile" component={ProfileScreen} />
</Tab.Navigator>
```

### Individual Tab Options

```javascript
<Tab.Screen 
  name="Home" 
  component={HomeScreen}
  options={{
    tabBarLabel: 'Home',
    tabBarBadge: 3,  // Shows notification badge
    headerShown: true,
    title: 'Home Screen',
  }}
/>
```

---

## Icons and Styling

### Adding Icons to Tabs

Use `tabBarIcon` option to add icons [web:208]:

```javascript
import Icon from 'react-native-vector-icons/Ionicons';

<Tab.Navigator
  screenOptions={({ route }) => ({
    tabBarIcon: ({ focused, color, size }) => {
      let iconName;

      if (route.name === 'Home') {
        iconName = focused ? 'home' : 'home-outline';
      } else if (route.name === 'Profile') {
        iconName = focused ? 'person' : 'person-outline';
      } else if (route.name === 'Settings') {
        iconName = focused ? 'settings' : 'settings-outline';
      }

      return <Icon name={iconName} size={size} color={color} />;
    },
    tabBarActiveTintColor: 'tomato',
    tabBarInactiveTintColor: 'gray',
  })}
>
  <Tab.Screen name="Home" component={HomeScreen} />
  <Tab.Screen name="Profile" component={ProfileScreen} />
  <Tab.Screen name="Settings" component={SettingsScreen} />
</Tab.Navigator>
```

### Icon Props

The `tabBarIcon` function receives an object with:

- `focused` (boolean) - Whether the tab is active
- `color` (string) - Icon color based on active/inactive state
- `size` (number) - Icon size (default: 24)

### Custom Icon Per Tab

```javascript
<Tab.Screen 
  name="Home" 
  component={HomeScreen}
  options={{
    tabBarIcon: ({ color, size }) => (
      <Icon name="home" color={color} size={size} />
    ),
  }}
/>

<Tab.Screen 
  name="Profile" 
  component={ProfileScreen}
  options={{
    tabBarIcon: ({ color, size }) => (
      <Icon name="person" color={color} size={size} />
    ),
  }}
/>
```

### Using Images as Icons

```javascript
<Tab.Screen 
  name="Home" 
  component={HomeScreen}
  options={{
    tabBarIcon: ({ focused }) => (
      <Image
        source={focused ? require('./home-active.png') : require('./home.png')}
        style={{ width: 24, height: 24 }}
      />
    ),
  }}
/>
```

---

## Tab Options

### Common Tab Options

| Option | Type | Description |
|--------|------|-------------|
| `tabBarLabel` | string/function | Label text for the tab |
| `tabBarIcon` | function | Icon component for the tab |
| `tabBarBadge` | number/string | Badge on tab (for notifications) |
| `tabBarBadgeStyle` | object | Style for badge |
| `tabBarActiveTintColor` | string | Color when tab is active |
| `tabBarInactiveTintColor` | string | Color when tab is inactive |
| `tabBarActiveBackgroundColor` | string | Background when active |
| `tabBarInactiveBackgroundColor` | string | Background when inactive |
| `tabBarStyle` | object | Style for entire tab bar |
| `tabBarLabelStyle` | object | Style for tab labels |
| `tabBarIconStyle` | object | Style for tab icons |
| `tabBarButton` | function | Custom touchable component |
| `tabBarShowLabel` | boolean | Show/hide labels |
| `headerShown` | boolean | Show/hide header |
| `unmountOnBlur` | boolean | Unmount screen when leaving tab |

### Tab Bar Styling Options

```javascript
<Tab.Navigator
  screenOptions={{
    tabBarStyle: {
      backgroundColor: '#fff',
      borderTopWidth: 0,
      elevation: 10,
      height: 60,
      paddingBottom: 5,
    },
    tabBarLabelStyle: {
      fontSize: 12,
      fontWeight: '600',
    },
    tabBarIconStyle: {
      marginTop: 5,
    },
  }}
>
  {/* Tab screens */}
</Tab.Navigator>
```

### Badge for Notifications

```javascript
<Tab.Screen 
  name="Messages" 
  component={MessagesScreen}
  options={{
    tabBarBadge: 5,
    tabBarBadgeStyle: {
      backgroundColor: 'red',
      color: 'white',
    },
  }}
/>
```

### Hide Tab Label

```javascript
<Tab.Screen 
  name="Home" 
  component={HomeScreen}
  options={{
    tabBarShowLabel: false,
  }}
/>
```

### Dynamic Tab Options

```javascript
<Tab.Screen 
  name="Messages" 
  component={MessagesScreen}
  options={({ route }) => ({
    tabBarBadge: route.params?.unreadCount || undefined,
    tabBarLabel: route.params?.customLabel || 'Messages',
  })}
/>
```

---

## Navigation Methods

Tab navigator navigation object has the same methods as stack navigator, plus tab-specific ones.

### Basic Navigation

```javascript
// Switch to a tab
navigation.navigate('Profile');

// Jump to a tab (tab-specific method)
navigation.jumpTo('Settings');
```

### Difference: navigate vs jumpTo

- **`navigate('TabName')`** - Switches to tab, maintains its navigation state
- **`jumpTo('TabName')`** - Switches to tab, tab-specific method (preferred for tabs)

Both work the same for basic tab switching [web:157].

### Navigate with Params

```javascript
navigation.navigate('Profile', { userId: 123 });
```

### Get Current Tab State

```javascript
import { useRoute } from '@react-navigation/native';

function MyComponent() {
  const route = useRoute();
  const isFocused = navigation.isFocused(); // Check if current tab is active

  return <Text>{isFocused ? 'Active' : 'Inactive'}</Text>;
}
```

### Programmatically Switch Tabs

```javascript
function HomeScreen({ navigation }) {
  const goToProfile = () => {
    navigation.navigate('Profile', { userId: 42 });
  };

  return (
    <Button title="View Profile" onPress={goToProfile} />
  );
}
```

---

## Passing Parameters

### Sending Parameters

```javascript
navigation.navigate('Profile', { 
  userId: 123,
  userName: 'John Doe'
});
```

### Receiving Parameters

```javascript
function ProfileScreen({ route, navigation }) {
  const { userId, userName } = route.params || {};

  return (
    <View>
      <Text>User ID: {userId}</Text>
      <Text>Name: {userName}</Text>
    </View>
  );
}
```

### Update Tab Badge from Screen

```javascript
function MessagesScreen({ navigation }) {
  const [unreadCount, setUnreadCount] = useState(5);

  useEffect(() => {
    navigation.setOptions({
      tabBarBadge: unreadCount > 0 ? unreadCount : undefined,
    });
  }, [unreadCount, navigation]);

  return <View>...</View>;
}
```

---

## Nested Navigation

Most apps combine Tab Navigator with Stack Navigator [web:212]. Common pattern: Each tab contains its own stack of screens [web:209].

### Tab with Nested Stacks

```javascript
import { createNativeStackNavigator } from '@react-navigation/native-stack';
import { createBottomTabNavigator } from '@react-navigation/bottom-tabs';

const Stack = createNativeStackNavigator();
const Tab = createBottomTabNavigator();

// Home Stack
function HomeStack() {
  return (
    <Stack.Navigator>
      <Stack.Screen name="HomeMain" component={HomeScreen} />
      <Stack.Screen name="Details" component={DetailsScreen} />
      <Stack.Screen name="Profile" component={ProfileScreen} />
    </Stack.Navigator>
  );
}

// Settings Stack
function SettingsStack() {
  return (
    <Stack.Navigator>
      <Stack.Screen name="SettingsMain" component={SettingsScreen} />
      <Stack.Screen name="Account" component={AccountScreen} />
      <Stack.Screen name="Privacy" component={PrivacyScreen} />
    </Stack.Navigator>
  );
}

// Tab Navigator with Stacks
export default function App() {
  return (
    <NavigationContainer>
      <Tab.Navigator>
        <Tab.Screen 
          name="Home" 
          component={HomeStack}
          options={{
            headerShown: false,  // Hide tab navigator header
          }}
        />
        <Tab.Screen 
          name="Settings" 
          component={SettingsStack}
          options={{
            headerShown: false,
          }}
        />
      </Tab.Navigator>
    </NavigationContainer>
  );
}
```

### Navigation Between Tabs and Stacks

```javascript
// From HomeScreen (inside Home tab stack)
function HomeScreen({ navigation }) {
  return (
    <View>
      {/* Navigate within same tab stack */}
      <Button 
        title="Go to Details" 
        onPress={() => navigation.navigate('Details')}
      />

      {/* Navigate to different tab */}
      <Button 
        title="Go to Settings Tab" 
        onPress={() => navigation.navigate('Settings')}
      />

      {/* Navigate to specific screen in different tab */}
      <Button 
        title="Go to Account in Settings" 
        onPress={() => navigation.navigate('Settings', {
          screen: 'Account'
        })}
      />
    </View>
  );
}
```

### Nested Navigation with Parameters

```javascript
// Navigate to specific screen in another tab with params
navigation.navigate('Profile', {
  screen: 'UserDetails',
  params: { userId: 123 }
});
```

---

## Custom Tab Bar

### Basic Custom Tab Bar

Create a completely custom tab bar component [web:207]:

```javascript
import { View, Text, TouchableOpacity } from 'react-native';

function MyTabBar({ state, descriptors, navigation }) {
  return (
    <View style={{ flexDirection: 'row', backgroundColor: '#fff' }}>
      {state.routes.map((route, index) => {
        const { options } = descriptors[route.key];
        const label = options.tabBarLabel || route.name;
        const isFocused = state.index === index;

        const onPress = () => {
          const event = navigation.emit({
            type: 'tabPress',
            target: route.key,
            canPreventDefault: true,
          });

          if (!isFocused && !event.defaultPrevented) {
            navigation.navigate(route.name);
          }
        };

        return (
          <TouchableOpacity
            key={index}
            onPress={onPress}
            style={{
              flex: 1,
              padding: 15,
              alignItems: 'center',
              backgroundColor: isFocused ? '#e0e0e0' : '#fff',
            }}
          >
            <Text style={{ color: isFocused ? '#000' : '#666' }}>
              {label}
            </Text>
          </TouchableOpacity>
        );
      })}
    </View>
  );
}

// Use custom tab bar
<Tab.Navigator tabBar={props => <MyTabBar {...props} />}>
  <Tab.Screen name="Home" component={HomeScreen} />
  <Tab.Screen name="Profile" component={ProfileScreen} />
</Tab.Navigator>
```

### Custom Tab Bar with Icons

```javascript
import Icon from 'react-native-vector-icons/Ionicons';

function MyTabBar({ state, descriptors, navigation }) {
  const icons = {
    Home: 'home',
    Profile: 'person',
    Settings: 'settings',
  };

  return (
    <View style={{
      flexDirection: 'row',
      backgroundColor: '#fff',
      height: 60,
      borderTopWidth: 1,
      borderTopColor: '#e0e0e0',
    }}>
      {state.routes.map((route, index) => {
        const isFocused = state.index === index;
        const iconName = icons[route.name];

        const onPress = () => {
          const event = navigation.emit({
            type: 'tabPress',
            target: route.key,
            canPreventDefault: true,
          });

          if (!isFocused && !event.defaultPrevented) {
            navigation.navigate(route.name);
          }
        };

        return (
          <TouchableOpacity
            key={index}
            onPress={onPress}
            style={{
              flex: 1,
              justifyContent: 'center',
              alignItems: 'center',
            }}
          >
            <Icon 
              name={iconName} 
              size={24} 
              color={isFocused ? '#007AFF' : '#8E8E93'} 
            />
            <Text style={{
              fontSize: 10,
              color: isFocused ? '#007AFF' : '#8E8E93',
              marginTop: 4,
            }}>
              {route.name}
            </Text>
          </TouchableOpacity>
        );
      })}
    </View>
  );
}
```

### Floating Action Button in Tab Bar

```javascript
function MyTabBar({ state, descriptors, navigation }) {
  return (
    <View>
      <View style={{ flexDirection: 'row', backgroundColor: '#fff' }}>
        {/* First 2 tabs */}
        {state.routes.slice(0, 2).map((route, index) => (
          <TouchableOpacity key={index} style={{ flex: 1 }}>
            <Text>{route.name}</Text>
          </TouchableOpacity>
        ))}

        {/* Empty space for FAB */}
        <View style={{ flex: 1 }} />

        {/* Last 2 tabs */}
        {state.routes.slice(2).map((route, index) => (
          <TouchableOpacity key={index + 2} style={{ flex: 1 }}>
            <Text>{route.name}</Text>
          </TouchableOpacity>
        ))}
      </View>

      {/* Floating Action Button */}
      <TouchableOpacity
        style={{
          position: 'absolute',
          bottom: 20,
          left: '50%',
          marginLeft: -30,
          width: 60,
          height: 60,
          borderRadius: 30,
          backgroundColor: '#007AFF',
          justifyContent: 'center',
          alignItems: 'center',
        }}
        onPress={() => navigation.navigate('Add')}
      >
        <Icon name="add" size={30} color="#fff" />
      </TouchableOpacity>
    </View>
  );
}
```

---

## Common Patterns

### Hide Tab Bar on Specific Screens

```javascript
<Tab.Screen 
  name="Home" 
  component={HomeStack}
  options={({ route }) => ({
    tabBarStyle: ((route) => {
      const routeName = getFocusedRouteNameFromRoute(route) ?? '';

      if (routeName === 'Details') {
        return { display: 'none' };
      }
      return;
    })(route),
  })}
/>
```

You need to import helper:
```javascript
import { getFocusedRouteNameFromRoute } from '@react-navigation/native';
```

### Reset Tab Stack on Tab Press

Reset stack to initial screen when tab is pressed again:

```javascript
<Tab.Screen
  name="Home"
  component={HomeStack}
  listeners={({ navigation }) => ({
    tabPress: (e) => {
      // Reset stack to initial screen
      navigation.reset({
        index: 0,
        routes: [{ name: 'HomeMain' }],
      });
    },
  })}
/>
```

### Prevent Tab Change

```javascript
<Tab.Screen
  name="Checkout"
  component={CheckoutScreen}
  listeners={({ navigation }) => ({
    tabPress: (e) => {
      e.preventDefault();
      Alert.alert('Complete checkout first');
    },
  })}
/>
```

### Tab Badge from Global State

```javascript
import { useSelector } from 'react-redux';

function App() {
  const unreadMessages = useSelector(state => state.messages.unreadCount);

  return (
    <Tab.Navigator>
      <Tab.Screen 
        name="Messages" 
        component={MessagesScreen}
        options={{
          tabBarBadge: unreadMessages > 0 ? unreadMessages : undefined,
        }}
      />
    </Tab.Navigator>
  );
}
```

### Conditional Tabs Based on Auth

```javascript
function App() {
  const isLoggedIn = useAuth();

  return (
    <NavigationContainer>
      <Tab.Navigator>
        <Tab.Screen name="Home" component={HomeScreen} />
        <Tab.Screen name="Search" component={SearchScreen} />

        {isLoggedIn ? (
          <>
            <Tab.Screen name="Messages" component={MessagesScreen} />
            <Tab.Screen name="Profile" component={ProfileScreen} />
          </>
        ) : (
          <Tab.Screen name="Login" component={LoginScreen} />
        )}
      </Tab.Navigator>
    </NavigationContainer>
  );
}
```

### Tab Icon with Badge

```javascript
<Tab.Screen
  name="Notifications"
  component={NotificationsScreen}
  options={{
    tabBarIcon: ({ color, size }) => (
      <View>
        <Icon name="notifications" color={color} size={size} />
        {unreadCount > 0 && (
          <View style={{
            position: 'absolute',
            right: -6,
            top: -3,
            backgroundColor: 'red',
            borderRadius: 10,
            width: 20,
            height: 20,
            justifyContent: 'center',
            alignItems: 'center',
          }}>
            <Text style={{ color: 'white', fontSize: 10 }}>
              {unreadCount}
            </Text>
          </View>
        )}
      </View>
    ),
  }}
/>
```

### Deep Linking to Tab Screens

```javascript
const linking = {
  prefixes: ['myapp://'],
  config: {
    screens: {
      Home: 'home',
      Profile: {
        path: 'profile/:userId',
        parse: {
          userId: (userId) => `${userId}`,
        },
      },
    },
  },
};

<NavigationContainer linking={linking}>
  <Tab.Navigator>
    {/* tabs */}
  </Tab.Navigator>
</NavigationContainer>
```

### Tab Screen Lifecycle

```javascript
function HomeScreen({ navigation }) {
  useEffect(() => {
    const unsubscribe = navigation.addListener('focus', () => {
      // Screen came into focus - refresh data
      fetchData();
    });

    return unsubscribe;
  }, [navigation]);

  useEffect(() => {
    const unsubscribe = navigation.addListener('blur', () => {
      // Screen left focus - cleanup
      cleanup();
    });

    return unsubscribe;
  }, [navigation]);

  return <View>...</View>;
}
```

---

## Quick Reference

### Tab Navigator Setup Cheat Sheet

```javascript
// Basic setup
const Tab = createBottomTabNavigator();

<Tab.Navigator
  initialRouteName="Home"
  screenOptions={{
    tabBarActiveTintColor: '#007AFF',
    tabBarInactiveTintColor: 'gray',
    headerShown: false,
  }}
>
  <Tab.Screen name="Home" component={HomeScreen} />
  <Tab.Screen name="Profile" component={ProfileScreen} />
</Tab.Navigator>
```

### Tab with Icons Template

```javascript
import Icon from 'react-native-vector-icons/Ionicons';

<Tab.Navigator
  screenOptions={({ route }) => ({
    tabBarIcon: ({ focused, color, size }) => {
      const icons = {
        Home: focused ? 'home' : 'home-outline',
        Profile: focused ? 'person' : 'person-outline',
        Settings: focused ? 'settings' : 'settings-outline',
      };

      return <Icon name={icons[route.name]} size={size} color={color} />;
    },
    tabBarActiveTintColor: 'tomato',
    tabBarInactiveTintColor: 'gray',
  })}
>
  <Tab.Screen name="Home" component={HomeScreen} />
  <Tab.Screen name="Profile" component={ProfileScreen} />
  <Tab.Screen name="Settings" component={SettingsScreen} />
</Tab.Navigator>
```

### Nested Stack in Tab Template

```javascript
const HomeStack = createNativeStackNavigator();

function HomeStackScreen() {
  return (
    <HomeStack.Navigator>
      <HomeStack.Screen name="Home" component={HomeScreen} />
      <HomeStack.Screen name="Details" component={DetailsScreen} />
    </HomeStack.Navigator>
  );
}

<Tab.Navigator>
  <Tab.Screen 
    name="HomeTab" 
    component={HomeStackScreen}
    options={{ headerShown: false }}
  />
</Tab.Navigator>
```

---

## Best Practices

1. **Use 3-5 tabs maximum** - Too many tabs hurt UX; use drawer for more options
2. **Keep tab icons consistent** - Use same icon set across all tabs
3. **Label your tabs** - Don't rely only on icons, add text labels
4. **Nest stacks in tabs** - Each tab should have its own navigation stack
5. **Hide tab bar when needed** - Hide on detail screens for better focus
6. **Use badges wisely** - Only for actionable notifications (messages, updates)
7. **Maintain tab state** - Keep scroll position and data when switching tabs
8. **Lazy load tab screens** - Default behavior, don't change unless necessary
9. **Use meaningful names** - Tab screen names should be clear and consistent
10. **Test on both platforms** - Tab bars look different on iOS vs Android

---

## Common Issues

### "Tab bar covers content"

Add padding/margin at bottom of screen content:

```javascript
<View style={{ paddingBottom: 60 }}>
  {/* content */}
</View>
```

Or use `useSafeAreaInsets`:

```javascript
import { useSafeAreaInsets } from 'react-native-safe-area-context';

function Screen() {
  const insets = useSafeAreaInsets();

  return (
    <View style={{ paddingBottom: insets.bottom }}>
      {/* content */}
    </View>
  );
}
```

### "Tab badge not updating"

Use `setOptions` to update dynamically:

```javascript
useEffect(() => {
  navigation.setOptions({
    tabBarBadge: count > 0 ? count : undefined,
  });
}, [count, navigation]);
```

### "Can't navigate to nested screen"

Use this syntax for nested navigation:

```javascript
navigation.navigate('TabName', {
  screen: 'ScreenName',
  params: { /* params */ }
});
```

### "Tab bar not hiding"

Use `getFocusedRouteNameFromRoute` helper:

```javascript
import { getFocusedRouteNameFromRoute } from '@react-navigation/native';

options={({ route }) => ({
  tabBarStyle: ((route) => {
    const routeName = getFocusedRouteNameFromRoute(route);
    if (routeName === 'Details') {
      return { display: 'none' };
    }
    return;
  })(route),
})}
```

---

This guide covers beginner to intermediate React Native bottom tab navigation concepts. For advanced topics like custom animations, gestures, and platform-specific configurations, refer to the official React Navigation documentation.