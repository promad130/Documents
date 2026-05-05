[Documentations](https://javascript.info/)
# An Introduction
JavaScript, often abbreviated JS, is a programming language that is one of the core technologies of the World Wide Web, alongside HTML and CSS. 
It lets us add interactivity to pages e.g. you might have seen sliders, alerts, click interactions, popups, etc on different websites -- all of that is built using JavaScript. 

Apart from being used in the browser, it is also used in other non-browser environments as well such as **Node.js** for writing server-side code in JavaScript, Electron for writing desktop applications, React Native for mobile applications, and so on.

## A History and versions
JavaScript was initially created by Brendan Eich of NetScape and was first announced in a press release by Netscape in 1995. 
It has a bizarre history of naming; initially, it was named Mocha by the creator, which was later renamed `LiveScript`. 

In 1996, about a year later after the release, NetScape decided to rename it to JavaScript with hopes of capitalizing on the Java community (although JavaScript did not have any relationship with Java) and released Netscape 2.0 with the official support of JavaScript.

JavaScript, invented by Brendan Eich, achieved the status of an ECMA standard in 1997 and adopted the official name ECMAScript. 

This language has evolved through several versions, namely ES1, ES2, ES3, ES5, and the transformative ES6. These updates have played a crucial role in improving and standardizing JavaScript, making it widely used and valuable in the ever-changing field of web development.

[More here](https://roadmap.sh/guides/history-of-javascript)

---
# How to run JavaScript?
To run a JavaScript script, you have several options depending on your needs:
## 1. Run in a Web Browser (Client-Side)

### **Browser Console (Quick Testing)**
- **Steps:**
    
    1. Open your browser (Chrome, Firefox, etc.).
    2. Press `F12` or `Ctrl+Shift+I` (Windows) / `Cmd+Option+I` (Mac) to open **Developer Tools**.
    3. Go to the **Console** tab.
    4. Type your code (e.g., `console.log("Hello World");`) and press `Enter`.

## **Embed in an HTML File**
- **Steps:**
    1. Create a file named `index.html`:
```xml
<!DOCTYPE html> 

<html>   
	<head>    
		<script src="script.js"></script>  
	</head> 
</html>
```
1. Create a `script.js` file:
```js
console.log("Hello World");
```
2. Open `index.html` in a browser and check the console (`F12` > **Console** tab).

---
## 2. Run via Node.js (Command Line)
- **Steps:**
    
    1. Install [Node.js](https://nodejs.org/).
    2. Create a file (e.g., `script.js`) with your code:
	```js
	console.log("Hello World");
	```
    3. Open a terminal, navigate to the file’s directory, and run:
```	bash
	node script.js
```

---
## 3. Use Online Tools (No Setup and not recommended)
- **Platforms:** [JSFiddle](https://jsfiddle.net/), [CodePen](https://codepen.io/), or [OneCompiler](https://onecompiler.com/javascript).
- **Steps:** Paste your code into the editor and click **Run**.

---

## 4. VS Code with Extensions

- Install the **Code Runner** extension in VS Code.
- Open your `.js` file and press `Ctrl+Alt+N` to execute it.

---
# Lets get started
**pre-requisite: [[Introduction to Programming]] and [[Object Oriented Programming]]**
*Can also refer to [this](https://developer.mozilla.org/en-US/docs/Web/JavaScript) or [this](https://javascript.info/) javaScript Documentation for a quick look*

JavaScript uses C-like syntax for variables, loops, and conditionals, so constructs like `if`, `else`, `while`, `for`, and `switch` will look familiar.
First, let's have a [tea](Alert%20in%20JS) with [Console](console%20Class%20in%20JS).
Lets start with [[Syntax of JS]].

[[Variables and data types in JS]] 
[[Operators in JS]]
[[Control Flow Statements and functions in JS]] 
[[Data Structures in JS]] 
And with those basic elements, lets have a look at [[Ways to Iterate over Array in JS]].

Now, What is TypeScript?
[[Object Oriented Programming in JS (Including TypeScript)]]
(Add how js was initiallity ran on browsers, and introduce modules and stuff)
You might be wondering that where is all these things done? Is it in the browser or the tab or what?
Well, there is something called Window Object, which is like the ultimate boss:
[[Window Object in Web Browsers]]
[[Documents in JavaScript]] 
[[I O Operations in JS]]

Good, now with all that, lets move onto [[Asynchronous JS and event loop]] 
Also check out [[Event Loop in JS in Detail]]
Now Take a look into [[Introduction to TypeScript]]

---
# npm, a JS package Manager
***(pre-requisite: need to know basics of [[Bash]])***
**npm** stands for Node Package Manager. It is the default package manager for the JavaScript runtime environment Node.js and is a fundamental tool in modern JavaScript development.

## Key Features and Functions
- **Package Manager:** npm enables developers to easily install, update, and manage third-party libraries and code packages (called "modules") that are used in JavaScript and Node.js projects.
- **Software Registry:** npm maintains the world’s largest software registry, hosting millions of open-source and private packages that developers can use to build their applications.
- **Dependency Management:** npm automates the process of handling project dependencies. Developers specify which packages their project requires in a file called `package.json`, and npm installs all required packages with a single command (`npm install`).
- **Command Line Interface (CLI):** npm includes a CLI tool that allows developers to interact with the registry, install packages, manage project dependencies, and run scripts directly from the terminal.
- **Version Control:** npm helps manage package versions, ensuring that projects remain stable and consistent even as dependencies evolve.

## How npm Works
- When you install Node.js, npm is automatically installed.
- Developers use the CLI to install packages from the npm registry into their projects. These packages are stored in a `node_modules` folder.
- The `package.json` file in a project lists all dependencies, their versions, and other metadata about the project.
- npm also supports running scripts (such as build or test commands) defined in the `package.json` file.

## Lets get started
First, let's look at the following commands:
```bash
npm -v or npm --version
```
```bash
rubber_duck@pop-os:~/Desktop/Coding/Programming languages/JavaScript/npm$ npm -v
10.9.2
```

```bash
npm --help
```
```bash
npm <command>

Usage:

npm install        install all the dependencies in your project
npm install <foo>  add the <foo> dependency to your project
npm test           run this projects tests
npm run <foo>      run the script named <foo>
npm <command> -h   quick help on <command>
npm -l             display usage info for all commands
npm help <term>    search for help on <term>
npm help npm       more involved overview

All commands:

    access, adduser, audit, bugs, cache, ci, completion,
    config, dedupe, deprecate, diff, dist-tag, docs, doctor,
    edit, exec, explain, explore, find-dupes, fund, get, help,
    help-search, hook, init, install, install-ci-test,
    install-test, link, ll, login, logout, ls, org, outdated,
    owner, pack, ping, pkg, prefix, profile, prune, publish,
    query, rebuild, repo, restart, root, run-script, sbom,
    search, set, shrinkwrap, star, stars, start, stop, team,
    test, token, uninstall, unpublish, unstar, update, version,
    view, whoami

Specify configs in the ini-formatted file:
    /home/rubber_duck/.npmrc
or on the command line via: npm <command> --key=value

More configuration info: npm help config
Configuration fields: npm help 7 config

npm@10.9.2 /home/rubber_duck/.nvm/versions/node/v22.16.0/lib/node_modules/npm
```

### Package.json File
The **package.json** file is a core component of every Node.js and npm project. It acts as a manifest that stores essential information about the project, including its metadata, dependencies, scripts, and configuration options.

#### Key Purposes
- **Project Metadata:** Contains descriptive information such as the project’s name, version, description, author, license, and repository details.
- **Dependency Management:** Lists all the packages (dependencies and devDependencies) your project needs. This allows npm to install the correct libraries and ensure consistent builds across different environments.
- **Entry Points:** Defines the main file or entry point of the application using the "main" property, and can specify additional entry points with the "exports" field.
- **Scripts:** Allows you to define custom scripts (such as build, test, start) that can be run with npm commands.
- **Configuration:** Can include additional fields for configuration, such as specifying the module type ("type": "module" or "commonjs"), supported CPU architectures, and more

#### Example
```JSON
{
  "name": "example-project",
  "version": "1.0.0",
  "description": "A sample Node.js project",
  "main": "app.js",
  "scripts": {
    "start": "node app.js",
    "test": "jest"
  },
  "dependencies": {
    "express": "^4.17.1"
  },
  "devDependencies": {
    "jest": "^29.0.0"
  },
  "repository": {
    "type": "git",
    "url": "https://github.com/username/example-project.git"
  }
}
```

#### Why is package.json Important?
- **Reproducibility:** Ensures that anyone cloning or downloading your project can install the exact dependencies and run the application as intended.
- **Project Identification:** Provides metadata that helps others (and npm) identify and understand your project.
- **Automation:** Facilitates automation of common tasks (like building, testing, and starting the server) through npm scripts

### Creating Package.json File:
We could create it manually, but why do the manual labor?
We create a package.json file using the following command:
```bash
npm init
```
```bash
rubber_duck@pop-os:~/Desktop/Coding/Programming languages/JavaScript/npm$ npm init
This utility will walk you through creating a package.json file.
It only covers the most common items, and tries to guess sensible defaults.

See `npm help init` for definitive documentation on these fields
and exactly what they do.

Use `npm install <pkg>` afterwards to install a package and
save it as a dependency in the package.json file.

Press ^C at any time to quit.
package name: (npm) npmapp
version: (1.0.0) 
description: Simple sample app
entry point: (index.js) 
test command: 
git repository: 
keywords: 
author: Nikunj Goyal
license: (ISC) MIT
About to write to /home/rubber_duck/Desktop/Coding/Programming languages/JavaScript/npm/package.json:

{
  "name": "npmapp",
  "version": "1.0.0",
  "description": "Simple sample app",
  "main": "index.js",
  "scripts": {
    "test": "echo \"Error: no test specified\" && exit 1"
  },
  "author": "Nikunj Goyal",
  "license": "MIT"
}


Is this OK? (yes) 

```

We can also do:
```bash
npm init -y
```
```bash
npm init --yes
```

```bash
rubber_duck@pop-os:~/Desktop/Coding/Programming languages/JavaScript/npm$ npm init -y
Wrote to /home/rubber_duck/Desktop/Coding/Programming languages/JavaScript/npm/package.json:

{
  "name": "npm",
  "version": "1.0.0",
  "main": "index.js",
  "scripts": {
    "test": "echo \"Error: no test specified\" && exit 1"
  },
  "keywords": [],
  "author": "",
  "license": "ISC",
  "description": ""
}

```

### Configuring the default values for package.json file:
We do this by using `set`:
```bash
npm config set init-(what you want to change) "to what value"
```

```bash
rubber_duck@pop-os:~/Desktop/Coding/Programming languages/JavaScript/npm$ npm config set init-author-name "Rubber_Duck"
rubber_duck@pop-os:~/Desktop/Coding/Programming languages/JavaScript/npm$ npm config set init-license "MIT"
rubber_duck@pop-os:~/Desktop/Coding/Programming languages/JavaScript/npm$ npm init -y
Wrote to /home/rubber_duck/Desktop/Coding/Programming languages/JavaScript/npm/package.json:

{
  "name": "npm",
  "version": "1.0.0",
  "main": "index.js",
  "scripts": {
    "test": "echo \"Error: no test specified\" && exit 1"
  },
  "keywords": [],
  "author": "Rubber_Duck",
  "license": "MIT",
  "description": ""
}




```

We can also `get` stuff to check the values:
```bash
rubber_duck@pop-os:~/Desktop/Coding/Programming languages/JavaScript/npm$ npm config get init-license
MIT
```

And we can `delete` the default values that we changed:
```bash
rubber_duck@pop-os:~/Desktop/Coding/Programming languages/JavaScript/npm$ npm config delete init-license
rubber_duck@pop-os:~/Desktop/Coding/Programming languages/JavaScript/npm$ npm config get init-license
ISC
```

### Installing packages, removing them and updating them:
We will install `lodash` package via npm:
```bash
npm install packageName 
```

And in order to include it in package.json file:
```bash
npm install packageName --save
```

```bash
rubber_duck@pop-os:~/Desktop/Coding/Programming languages/JavaScript/npm$ npm install lodash --save

added 1 package, and audited 2 packages in 1s

found 0 vulnerabilities
```
```json
{
  "name": "npm",
  "version": "1.0.0",
  "main": "index.js",
  "scripts": {
    "test": "echo \"Error: no test specified\" && exit 1"
  },
  "keywords": [],
  "author": "Rubber_Duck",
  "license": "MIT",
  "description": "",
  "dependencies": {
    "lodash": "^4.17.21"
  }
}
```
As you can see, the package was added as a [[Dependency]] in package.json.

Also, if we want to remove a dependencies:
```bash
npm uninstall <package-name>
# This is to remove it's files, and not from the package.json
```
And:
```bash
npm uninstall <package-name> --save
#OR
npm uninstall <package-name> --save-dev
#to remove it from package.json as well
```
We can also use `remove`, `rm`, `un` in place of `uninstall`.

And in order to update any package:
```bash
npm update <package-name>
```

#### But why include the save flag?
Well it is because when you want to share projects, including the publically available packages is not a good idea, so we include them in there.
Also, when you download a package.json from a github repo, we use:
```bash
npm install
```
To get the packages that are needed for that particular javaScript project!!!

It also created `node_modules`, now lets create the entry point for this project with npm, i.e., `index.js`.
And whenever we want to use a module we imported in our package.json, then we need to create a variable and assign it that module using `require()`:
#### What is require() in Node.js?
The `require()` function is a built-in feature of Node.js used to import modules, JSON files, or other resources into your JavaScript code. It is fundamental to Node.js's module system, enabling code reuse and separation of concerns.

##### How require() Works
- **Module Loading:** When you call `require('module-name')`, Node.js locates the specified module, loads and executes its code, and then returns the module's exported objects or functions.
- **Return Value:** The result of `require()` is whatever the module exports via `module.exports` or `exports`.
- **Types of Modules:**
    - **Core Modules:** Built-in Node.js modules like `fs`, `http`, or `path` can be required by name (e.g., `require('fs')`).
    - **Local Modules:** Your own files can be imported using a relative path (e.g., `require('./myModule.js')`).
    - **Installed Modules:** Packages installed via npm can be required by their package name (e.g., `require('express')`).

##### Example Usage
```js
// Importing a built-in module 
const fs = require('fs'); 
// Importing a local module 
const myModule = require('./myModule.js'); 
// Importing a JSON file 
const config = require('./config.json');`
```
##### What Happens Internally
When you use `require()`:
- Node.js resolves the path to the module.
- It checks if the module has already been loaded and cached; if so, it returns the cached version.
- If not, Node.js reads and executes the module file, then returns the `exports` object

#### And using the imported module:
We use it via the variable through which we imported it.
For example:
```JS
const lodashModule = require('lodash');

const numbers = [33,56,44,89,1645,51,222,2]

lodashModule.each(numbers, function(numbers, i){console.log(numbers)})
```

#### Running the file:
We run shit using:
```JSON
node fileName.js
```

And our project gives:
```bash
rubber_duck@pop-os:~/Desktop/Coding/Programming languages/JavaScript/npm$ node index.js 
33
56
44
89
1645
51
222
2
```

### Development Dependencies (dev-dependencies)
When we create a project, it undergoes through the two major phases:
- **Development**
	- This is the phase where programmers write, edit, and test code.
	- The development environment is used for building new features, fixing bugs, and experimenting.
	- Developers often use tools for debugging, logging, and live reloading that are not present in production.
	- Testing is also part of development, but it usually happens in separate environments (like "development" or "staging") before the code is considered ready for production.
- **Production**
	- Production is the live environment where the final, tested version of the software runs and is used by actual users.
	- Only stable, thoroughly tested code is deployed to production.
	- The focus here is on performance, reliability, and security—users interact with this version, so errors can have real-world consequences.
	- Any issues found in production are typically fixed first in development, then tested, and finally deployed to production
Hence we have a separate category for the dependencies needed only during the development phase of the software. 

An overview of development dependencies:
- **Purpose:**  
    Dev dependencies help developers write, test, and maintain code, but are not part of the final product delivered to users[2](https://docs.npmjs.com/specifying-dependencies-and-devdependencies-in-a-package-json-file/)[3](https://dev.to/syed_ammar/lets-understand-the-different-types-of-npm-dependencies-bo8)[5](https://www.warp.dev/terminus/npm-install-dev-dependencies).
- **Examples:**
    - Testing frameworks (e.g., Jest, Mocha)
    - Code linters (e.g., ESLint, Prettier)
    - Build tools (e.g., Webpack, Babel)
- **Production vs. Development:**
    - **dependencies:** Needed for your app to run in production.
    - **devDependencies:** Only needed for development and testing, not required in production

#### Creating dev-dependencies
```bash
npm install <package-name> --save-dev
```
OR
```bash
npm install <package-name> -D
```

For example:
```bash
rubber_duck@pop-os:~/Desktop/Coding/Programming languages/JavaScript/npm$ npm install gulp gulp-sass --save-dev

added 155 packages, and audited 157 packages in 16s

14 packages are looking for funding
  run `npm fund` for details

found 0 vulnerabilities

```
We are installing `gulp` package, and in gulp and we are also installing the `sass` plugin for the gulp.
And a new key is created in our `package.json` called `devDependencies`:
```JSON
{
  "name": "npm",
  "version": "1.0.0",
  "main": "index.js",
  "scripts": {
    "test": "echo \"Error: no test specified\" && exit 1"
  },
  "keywords": [],
  "author": "Rubber_Duck",
  "license": "MIT",
  "description": "",
  "dependencies": {
    "lodash": "^4.17.21"
  },
  "devDependencies": {
    "gulp": "^5.0.1",
    "gulp-sass": "^6.0.1"
  }
}
```

And now if we want to install only production dependencies when we import a project from github:
```bash
npm install --production
```

## Semantic Versioning
**Semantic Versioning** is a standardized way of assigning version numbers to software releases, making it clear what kind of changes have been made and what users can expect regarding compatibility.
A semantic version number is written as:
```text
MAJOR.MINOR.PATCH
```

### What Each Number Means

| Segment | When to Increment | What It Means                                           |
| ------- | ----------------- | ------------------------------------------------------- |
| MAJOR   | Breaking changes  | Incompatible API changes; users may need to update code |
| MINOR   | New features      | Backward-compatible new features or deprecations        |
| PATCH   | Bug fixes         | Backward-compatible bug fixes or small improvements     |

- **MAJOR**: Incremented when you make incompatible API changes (breaking changes).
- **MINOR**: Incremented when you add functionality in a backward-compatible manner.
- **PATCH**: Incremented when you make backward-compatible bug fixes

Ok, so we can see in `package.json` that the version of dependencies have `^` before the version, and it has a special meaning:
- `^`: means install the latest Minor version of the given Major version
- `~`: means install the latest Patch of the given Minor version
- `*`: writing only this without mentioning the version number, then it means to install the absolute latest version, and it is not a good idea as it is usually a breaking update, i.e., working, and syntax are changed.
- Writing nothing but just the version number will install that particular version

## Local and Global Modules
npm allows you to install packages either **locally** (for a specific project) or **globally** (system-wide). Till now we were only working with local modules, i.e., in our project folder that we opened in the terminal:
```bash
rubber_duck@pop-os:~/Desktop/Coding/Programming languages/JavaScript/npm$
```
But the global modules are installed on your system.

Syntax to install one:
```bash
npm install -g <package-name>
```
and Syntax to locate all global modules:
```bash
npm root -g
```

And since it might be intuitive that `g` means global, we can only guess what would represent local:
```bash
rubber_duck@pop-os:~/Desktop/Coding/Programming languages/JavaScript/npm$ npm root -g
/home/rubber_duck/.nvm/versions/node/v22.16.0/lib/node_modules
rubber_duck@pop-os:~/Desktop/Coding/Programming languages/JavaScript/npm$ npm root -l
/home/rubber_duck/Desktop/Coding/Programming languages/JavaScript/npm/node_modules
```

A important and useful global module to have is `nodemon`, this manages, executes and watches our package for everything:
```bash
rubber_duck@pop-os:~/Desktop/Coding/Programming languages/JavaScript/npm$ nodemon
[nodemon] 3.1.10
[nodemon] to restart at any time, enter `rs`
[nodemon] watching path(s): *.*
[nodemon] watching extensions: js,mjs,cjs,json
[nodemon] starting `node index.js`
33
56
44
89
1645
51
222
2
[nodemon] clean exit - waiting for changes before restart

```
and if we make a change in our project and then save it, it reloads and executes it again:
```bash
rubber_duck@pop-os:~/Desktop/Coding/Programming languages/JavaScript/npm$ nodemon
[nodemon] 3.1.10
[nodemon] to restart at any time, enter `rs`
[nodemon] watching path(s): *.*
[nodemon] watching extensions: js,mjs,cjs,json
[nodemon] starting `node index.js`
33
56
44
89
1645
51
222
2
[nodemon] clean exit - waiting for changes before restart
[nodemon] restarting due to changes...
[nodemon] starting `node index.js`
33
56
44
89
1645
51
222
23
[nodemon] clean exit - waiting for changes before restart
```
**(`ctrl+c` to exit.)**

Another important global module to have is `live-server`, and what it does is that it helps us to load the directory we are working in to launch on the browser using the local host:
```bash
rubber_duck@pop-os:~/Desktop/Coding/Programming languages/JavaScript/npm$ npm stall -g live-server
npm warn deprecated source-map-url@0.4.1: See https://github.com/lydell/source-map-url#deprecated
npm warn deprecated urix@0.1.0: Please see https://github.com/lydell/urix#deprecated
npm warn deprecated resolve-url@0.2.1: https://github.com/lydell/resolve-url#deprecated
npm warn deprecated source-map-resolve@0.5.3: See https://github.com/lydell/source-map-resolve#deprecated
npm warn deprecated uuid@3.4.0: Please upgrade  to version 7 or higher.  Older versions may use Math.random() in certain circumstances, which is known to be problematic.  See https://v8.dev/blog/math-random for details.
npm warn deprecated opn@6.0.0: The package has been renamed to `open`

added 192 packages in 9s

3 packages are looking for funding
  run `npm fund` for details
```
[[Deprecated Dependencies]]

And we can launch it using:
```bash
live-server
```
If there is a html page, it will load it, if not, then the directory will appear in your browser.
Again:**(`ctrl+c` to exit.)**

## Listing all installed local modules:
Lists all the local packages installed, and we can change the viability level:
```bash
rubber_duck@pop-os:~/Desktop/Coding/Programming languages/JavaScript/npm$ npm list
npm@1.0.0 /home/rubber_duck/Desktop/Coding/Programming languages/JavaScript/npm
├── gulp-sass@6.0.1
├── gulp@5.0.1
└── lodash@4.17.21

rubber_duck@pop-os:~/Desktop/Coding/Programming languages/JavaScript/npm$ npm list --depth 1
npm@1.0.0 /home/rubber_duck/Desktop/Coding/Programming languages/JavaScript/npm
├─┬ gulp-sass@6.0.1
│ ├── lodash.clonedeep@4.5.0
│ ├── picocolors@1.1.1
│ ├── plugin-error@1.0.1
│ ├── replace-ext@2.0.0
│ ├── strip-ansi@6.0.1
│ └── vinyl-sourcemaps-apply@0.2.1
├─┬ gulp@5.0.1
│ ├── glob-watcher@6.0.0
│ ├── gulp-cli@3.1.0
│ ├── undertaker@2.0.0
│ └── vinyl-fs@4.0.2
└── lodash@4.17.21

rubber_duck@pop-os:~/Desktop/Coding/Programming languages/JavaScript/npm$ npm list --depth 2
npm@1.0.0 /home/rubber_duck/Desktop/Coding/Programming languages/JavaScript/npm
├─┬ gulp-sass@6.0.1
│ ├── lodash.clonedeep@4.5.0
│ ├── picocolors@1.1.1
│ ├─┬ plugin-error@1.0.1
│ │ ├── ansi-colors@1.1.0
│ │ ├── arr-diff@4.0.0
│ │ ├── arr-union@3.1.0
│ │ └── extend-shallow@3.0.2
│ ├── replace-ext@2.0.0
│ ├─┬ strip-ansi@6.0.1
│ │ └── ansi-regex@5.0.1
│ └─┬ vinyl-sourcemaps-apply@0.2.1
│   └── source-map@0.5.7
├─┬ gulp@5.0.1
│ ├─┬ glob-watcher@6.0.0
│ │ ├── async-done@2.0.0
│ │ └── chokidar@3.6.0
│ ├─┬ gulp-cli@3.1.0
│ │ ├── @gulpjs/messages@1.1.0
│ │ ├── chalk@4.1.2
│ │ ├── copy-props@4.0.0
│ │ ├── gulplog@2.2.0
│ │ ├── interpret@3.1.1
│ │ ├── liftoff@5.0.1
│ │ ├── mute-stdout@2.0.0
│ │ ├── replace-homedir@2.0.0
│ │ ├── semver-greatest-satisfied-range@2.0.0
│ │ ├── string-width@4.2.3
│ │ ├── v8flags@4.0.1
│ │ └── yargs@16.2.0
│ ├─┬ undertaker@2.0.0
│ │ ├── bach@2.0.1
│ │ ├── fast-levenshtein@3.0.0
│ │ ├── last-run@2.0.0
│ │ └── undertaker-registry@2.0.0
│ └─┬ vinyl-fs@4.0.2
│   ├── fs-mkdirp-stream@2.0.1
│   ├── glob-stream@8.0.3
│   ├── graceful-fs@4.2.11
│   ├── iconv-lite@0.6.3
│   ├── is-valid-glob@1.0.0
│   ├── lead@4.0.0
│   ├── normalize-path@3.0.0
│   ├── resolve-options@2.0.0
│   ├── stream-composer@1.0.2
│   ├── streamx@2.22.1
│   ├── to-through@3.0.0
│   ├── value-or-function@4.0.0
│   ├── vinyl-sourcemap@2.0.0
│   └── vinyl@3.0.1
└── lodash@4.17.21

rubber_duck@pop-os:~/Desktop/Coding/Programming languages/JavaScript/npm$ npm list --depth 3
npm@1.0.0 /home/rubber_duck/Desktop/Coding/Programming languages/JavaScript/npm
├─┬ gulp-sass@6.0.1
│ ├── lodash.clonedeep@4.5.0
│ ├── picocolors@1.1.1
│ ├─┬ plugin-error@1.0.1
│ │ ├─┬ ansi-colors@1.1.0
│ │ │ └── ansi-wrap@0.1.0
│ │ ├── arr-diff@4.0.0
│ │ ├── arr-union@3.1.0
│ │ └─┬ extend-shallow@3.0.2
│ │   ├── assign-symbols@1.0.0
│ │   └── is-extendable@1.0.1
│ ├── replace-ext@2.0.0
│ ├─┬ strip-ansi@6.0.1
│ │ └── ansi-regex@5.0.1
│ └─┬ vinyl-sourcemaps-apply@0.2.1
│   └── source-map@0.5.7
├─┬ gulp@5.0.1
│ ├─┬ glob-watcher@6.0.0
│ │ ├─┬ async-done@2.0.0
│ │ │ ├── end-of-stream@1.4.4
│ │ │ ├── once@1.4.0
│ │ │ └── stream-exhaust@1.0.2
│ │ └─┬ chokidar@3.6.0
│ │   ├── anymatch@3.1.3
│ │   ├── braces@3.0.3
│ │   ├── UNMET OPTIONAL DEPENDENCY fsevents@~2.3.2
│ │   ├── glob-parent@5.1.2
│ │   ├── is-binary-path@2.1.0
│ │   ├── is-glob@4.0.3
│ │   ├── normalize-path@3.0.0 deduped
│ │   └── readdirp@3.6.0
│ ├─┬ gulp-cli@3.1.0
│ │ ├── @gulpjs/messages@1.1.0
│ │ ├─┬ chalk@4.1.2
│ │ │ ├── ansi-styles@4.3.0
│ │ │ └── supports-color@7.2.0
│ │ ├─┬ copy-props@4.0.0
│ │ │ ├── each-props@3.0.0
│ │ │ └── is-plain-object@5.0.0
│ │ ├─┬ gulplog@2.2.0
│ │ │ └── glogg@2.2.0
│ │ ├── interpret@3.1.1
│ │ ├─┬ liftoff@5.0.1
│ │ │ ├── extend@3.0.2
│ │ │ ├── findup-sync@5.0.0
│ │ │ ├── fined@2.0.0
│ │ │ ├── flagged-respawn@2.0.0
│ │ │ ├── is-plain-object@5.0.0 deduped
│ │ │ ├── rechoir@0.8.0
│ │ │ └── resolve@1.22.10
│ │ ├── mute-stdout@2.0.0
│ │ ├── replace-homedir@2.0.0
│ │ ├─┬ semver-greatest-satisfied-range@2.0.0
│ │ │ └── sver@1.8.4
│ │ ├─┬ string-width@4.2.3
│ │ │ ├── emoji-regex@8.0.0
│ │ │ ├── is-fullwidth-code-point@3.0.0
│ │ │ └── strip-ansi@6.0.1 deduped
│ │ ├── v8flags@4.0.1
│ │ └─┬ yargs@16.2.0
│ │   ├── cliui@7.0.4
│ │   ├── escalade@3.2.0
│ │   ├── get-caller-file@2.0.5
│ │   ├── require-directory@2.1.1
│ │   ├── string-width@4.2.3 deduped
│ │   ├── y18n@5.0.8
│ │   └── yargs-parser@20.2.9
│ ├─┬ undertaker@2.0.0
│ │ ├─┬ bach@2.0.1
│ │ │ ├── async-done@2.0.0 deduped
│ │ │ ├── async-settle@2.0.0
│ │ │ └── now-and-later@3.0.0
│ │ ├─┬ fast-levenshtein@3.0.0
│ │ │ └── fastest-levenshtein@1.0.16
│ │ ├── last-run@2.0.0
│ │ └── undertaker-registry@2.0.0
│ └─┬ vinyl-fs@4.0.2
│   ├─┬ fs-mkdirp-stream@2.0.1
│   │ ├── graceful-fs@4.2.11 deduped
│   │ └── streamx@2.22.1 deduped
│   ├─┬ glob-stream@8.0.3
│   │ ├── @gulpjs/to-absolute-glob@4.0.0
│   │ ├── anymatch@3.1.3 deduped
│   │ ├── fastq@1.19.1
│   │ ├── glob-parent@6.0.2
│   │ ├── is-glob@4.0.3 deduped
│   │ ├── is-negated-glob@1.0.0
│   │ ├── normalize-path@3.0.0 deduped
│   │ └── streamx@2.22.1 deduped
│   ├── graceful-fs@4.2.11
│   ├─┬ iconv-lite@0.6.3
│   │ └── safer-buffer@2.1.2
│   ├── is-valid-glob@1.0.0
│   ├── lead@4.0.0
│   ├── normalize-path@3.0.0
│   ├─┬ resolve-options@2.0.0
│   │ └── value-or-function@4.0.0 deduped
│   ├─┬ stream-composer@1.0.2
│   │ └── streamx@2.22.1 deduped
│   ├─┬ streamx@2.22.1
│   │ ├── bare-events@2.5.4
│   │ ├── fast-fifo@1.3.2
│   │ └── text-decoder@1.2.3
│   ├─┬ to-through@3.0.0
│   │ └── streamx@2.22.1 deduped
│   ├── value-or-function@4.0.0
│   ├─┬ vinyl-sourcemap@2.0.0
│   │ ├── convert-source-map@2.0.0
│   │ ├── graceful-fs@4.2.11 deduped
│   │ ├── now-and-later@3.0.0 deduped
│   │ ├── streamx@2.22.1 deduped
│   │ ├── vinyl-contents@2.0.0
│   │ └── vinyl@3.0.1 deduped
│   └─┬ vinyl@3.0.1
│     ├── clone@2.1.2
│     ├── remove-trailing-separator@1.1.0
│     ├── replace-ext@2.0.0 deduped
│     └── teex@1.0.1
└── lodash@4.17.21

rubber_duck@pop-os:~/Desktop/Coding/Programming languages/JavaScript/npm$ npm list --depth 4
npm@1.0.0 /home/rubber_duck/Desktop/Coding/Programming languages/JavaScript/npm
├─┬ gulp-sass@6.0.1
│ ├── lodash.clonedeep@4.5.0
│ ├── picocolors@1.1.1
│ ├─┬ plugin-error@1.0.1
│ │ ├─┬ ansi-colors@1.1.0
│ │ │ └── ansi-wrap@0.1.0
│ │ ├── arr-diff@4.0.0
│ │ ├── arr-union@3.1.0
│ │ └─┬ extend-shallow@3.0.2
│ │   ├── assign-symbols@1.0.0
│ │   └─┬ is-extendable@1.0.1
│ │     └── is-plain-object@2.0.4
│ ├── replace-ext@2.0.0
│ ├─┬ strip-ansi@6.0.1
│ │ └── ansi-regex@5.0.1
│ └─┬ vinyl-sourcemaps-apply@0.2.1
│   └── source-map@0.5.7
├─┬ gulp@5.0.1
│ ├─┬ glob-watcher@6.0.0
│ │ ├─┬ async-done@2.0.0
│ │ │ ├─┬ end-of-stream@1.4.4
│ │ │ │ └── once@1.4.0 deduped
│ │ │ ├─┬ once@1.4.0
│ │ │ │ └── wrappy@1.0.2
│ │ │ └── stream-exhaust@1.0.2
│ │ └─┬ chokidar@3.6.0
│ │   ├─┬ anymatch@3.1.3
│ │   │ ├── normalize-path@3.0.0 deduped
│ │   │ └── picomatch@2.3.1
│ │   ├─┬ braces@3.0.3
│ │   │ └── fill-range@7.1.1
│ │   ├── UNMET OPTIONAL DEPENDENCY fsevents@~2.3.2
│ │   ├─┬ glob-parent@5.1.2
│ │   │ └── is-glob@4.0.3 deduped
│ │   ├─┬ is-binary-path@2.1.0
│ │   │ └── binary-extensions@2.3.0
│ │   ├─┬ is-glob@4.0.3
│ │   │ └── is-extglob@2.1.1
│ │   ├── normalize-path@3.0.0 deduped
│ │   └─┬ readdirp@3.6.0
│ │     └── picomatch@2.3.1 deduped
│ ├─┬ gulp-cli@3.1.0
│ │ ├── @gulpjs/messages@1.1.0
│ │ ├─┬ chalk@4.1.2
│ │ │ ├─┬ ansi-styles@4.3.0
│ │ │ │ └── color-convert@2.0.1
│ │ │ └─┬ supports-color@7.2.0
│ │ │   └── has-flag@4.0.0
│ │ ├─┬ copy-props@4.0.0
│ │ │ ├─┬ each-props@3.0.0
│ │ │ │ ├── is-plain-object@5.0.0 deduped
│ │ │ │ └── object.defaults@1.1.0
│ │ │ └── is-plain-object@5.0.0
│ │ ├─┬ gulplog@2.2.0
│ │ │ └─┬ glogg@2.2.0
│ │ │   └── sparkles@2.1.0
│ │ ├── interpret@3.1.1
│ │ ├─┬ liftoff@5.0.1
│ │ │ ├── extend@3.0.2
│ │ │ ├─┬ findup-sync@5.0.0
│ │ │ │ ├── detect-file@1.0.0
│ │ │ │ ├── is-glob@4.0.3 deduped
│ │ │ │ ├── micromatch@4.0.8
│ │ │ │ └── resolve-dir@1.0.1
│ │ │ ├─┬ fined@2.0.0
│ │ │ │ ├── expand-tilde@2.0.2
│ │ │ │ ├── is-plain-object@5.0.0 deduped
│ │ │ │ ├── object.defaults@1.1.0 deduped
│ │ │ │ ├── object.pick@1.3.0
│ │ │ │ └── parse-filepath@1.0.2
│ │ │ ├── flagged-respawn@2.0.0
│ │ │ ├── is-plain-object@5.0.0 deduped
│ │ │ ├─┬ rechoir@0.8.0
│ │ │ │ └── resolve@1.22.10 deduped
│ │ │ └─┬ resolve@1.22.10
│ │ │   ├── is-core-module@2.16.1
│ │ │   ├── path-parse@1.0.7
│ │ │   └── supports-preserve-symlinks-flag@1.0.0
│ │ ├── mute-stdout@2.0.0
│ │ ├── replace-homedir@2.0.0
│ │ ├─┬ semver-greatest-satisfied-range@2.0.0
│ │ │ └─┬ sver@1.8.4
│ │ │   └── semver@6.3.1
│ │ ├─┬ string-width@4.2.3
│ │ │ ├── emoji-regex@8.0.0
│ │ │ ├── is-fullwidth-code-point@3.0.0
│ │ │ └── strip-ansi@6.0.1 deduped
│ │ ├── v8flags@4.0.1
│ │ └─┬ yargs@16.2.0
│ │   ├─┬ cliui@7.0.4
│ │   │ ├── string-width@4.2.3 deduped
│ │   │ ├── strip-ansi@6.0.1 deduped
│ │   │ └── wrap-ansi@7.0.0
│ │   ├── escalade@3.2.0
│ │   ├── get-caller-file@2.0.5
│ │   ├── require-directory@2.1.1
│ │   ├── string-width@4.2.3 deduped
│ │   ├── y18n@5.0.8
│ │   └── yargs-parser@20.2.9
│ ├─┬ undertaker@2.0.0
│ │ ├─┬ bach@2.0.1
│ │ │ ├── async-done@2.0.0 deduped
│ │ │ ├─┬ async-settle@2.0.0
│ │ │ │ └── async-done@2.0.0 deduped
│ │ │ └─┬ now-and-later@3.0.0
│ │ │   └── once@1.4.0 deduped
│ │ ├─┬ fast-levenshtein@3.0.0
│ │ │ └── fastest-levenshtein@1.0.16
│ │ ├── last-run@2.0.0
│ │ └── undertaker-registry@2.0.0
│ └─┬ vinyl-fs@4.0.2
│   ├─┬ fs-mkdirp-stream@2.0.1
│   │ ├── graceful-fs@4.2.11 deduped
│   │ └── streamx@2.22.1 deduped
│   ├─┬ glob-stream@8.0.3
│   │ ├─┬ @gulpjs/to-absolute-glob@4.0.0
│   │ │ └── is-negated-glob@1.0.0 deduped
│   │ ├── anymatch@3.1.3 deduped
│   │ ├─┬ fastq@1.19.1
│   │ │ └── reusify@1.1.0
│   │ ├─┬ glob-parent@6.0.2
│   │ │ └── is-glob@4.0.3 deduped
│   │ ├── is-glob@4.0.3 deduped
│   │ ├── is-negated-glob@1.0.0
│   │ ├── normalize-path@3.0.0 deduped
│   │ └── streamx@2.22.1 deduped
│   ├── graceful-fs@4.2.11
│   ├─┬ iconv-lite@0.6.3
│   │ └── safer-buffer@2.1.2
│   ├── is-valid-glob@1.0.0
│   ├── lead@4.0.0
│   ├── normalize-path@3.0.0
│   ├─┬ resolve-options@2.0.0
│   │ └── value-or-function@4.0.0 deduped
│   ├─┬ stream-composer@1.0.2
│   │ └── streamx@2.22.1 deduped
│   ├─┬ streamx@2.22.1
│   │ ├── bare-events@2.5.4
│   │ ├── fast-fifo@1.3.2
│   │ └─┬ text-decoder@1.2.3
│   │   └── b4a@1.6.7
│   ├─┬ to-through@3.0.0
│   │ └── streamx@2.22.1 deduped
│   ├── value-or-function@4.0.0
│   ├─┬ vinyl-sourcemap@2.0.0
│   │ ├── convert-source-map@2.0.0
│   │ ├── graceful-fs@4.2.11 deduped
│   │ ├── now-and-later@3.0.0 deduped
│   │ ├── streamx@2.22.1 deduped
│   │ ├─┬ vinyl-contents@2.0.0
│   │ │ ├── bl@5.1.0
│   │ │ └── vinyl@3.0.1 deduped
│   │ └── vinyl@3.0.1 deduped
│   └─┬ vinyl@3.0.1
│     ├── clone@2.1.2
│     ├── remove-trailing-separator@1.1.0
│     ├── replace-ext@2.0.0 deduped
│     └─┬ teex@1.0.1
│       └── streamx@2.22.1 deduped
└── lodash@4.17.21


```

## npm Scripts
**npm scripts** are custom commands defined in the `scripts` section of your project's `package.json` file. They allow you to automate common development tasks—such as building, testing, linting, and running your application—using simple terminal commands.

---
### How npm Scripts Work
- In your `package.json`, the `scripts` field is an object where each key is the script name and each value is the shell command to execute.
- You run scripts with:
```text
npm run <script-name>
```
Some scripts, like `start` and `test`, can be run with `npm start` or `npm test` (no `run` needed).    

---
### Basic Examples
```json
"scripts": 
{   
	"start": "node server.js",  
	"dev": "nodemon server.js",  
	"test": "mocha",  
	"lint": "eslint ." 
}
```
- `npm start` runs your app.    
- `npm run dev` starts a development server with auto-reload.
- `npm test` runs your test suite.
- `npm run lint` checks your code for style and errors.

---
### Advanced Usage
- **Chaining Commands:**  
    Run multiple commands in sequence:
```json
"build": "npm run lint && npm run test && node build.js"
```    
- **Script Composition:**  
    Use one npm script inside another for modularity:
```json
"dev": "npm run build && npm start"
```
This runs `build` first, then `start`.
    
- **Lifecycle Hooks:**  
    npm supports special scripts that run automatically at certain stages, like:
    - `preinstall`, `postinstall`
    - `pretest`, `posttest`
    - `prebuild`, `postbuild`  
        These run before or after the main script of the same name.
    
- **Passing Arguments:**  
    Pass arguments to your scripts:
```text
npm run build -- --watch
```
The `--` makes sure anything after it is passed to your command

---

## **Lifecycle Script Examples**

|Script Name|When It Runs|
|---|---|
|preinstall|Before installing dependencies|
|install|While installing dependencies|
|postinstall|After installing dependencies|
|pretest|Before running tests|
|test|When you run `npm test`|
|posttest|After running tests|
|prebuild|Before build script|
|build|When you run `npm run build`|
|postbuild|After build script|

---

## **Sample `package.json` Scripts Section**
```json
"scripts": {   
	"prebuild": "echo 'Preparing build...'",  
	"build": "webpack --config webpack.config.js",  
	"postbuild": "npm run test",  
	"start": "node server.js",  
	"test": "jest" 
}
```
Running `npm run build` will execute `prebuild`, then `build`, then `postbuild` in order.

---
# Fetch API
***Use JSON Placeholder to practice around with API and stuff***
## Fetch API Overview

The Fetch API is a modern JavaScript interface for making HTTP requests to servers and handling responses. It replaces the older XMLHttpRequest (XHR) approach and is designed to be more flexible, powerful, and easier to use, especially for asynchronous operations.
## Key Features
- **Promise-Based:** Fetch uses Promises, making it easier to write and manage asynchronous code with `.then()` or `async/await`.
- **Global Availability:** The [[fetch()]] function is available globally in browsers (both in the `window` and `worker` contexts) and in Node.js (via Undici).
- **Flexible Requests:** Supports all HTTP methods (GET, POST, PUT, DELETE, etc.) and allows custom headers and request bodies.
- **Modern Syntax:** Cleaner and more readable than XHR, especially with async/await.
- **Handles Various Response Types:** Easily extract response data as JSON, text, Blob, etc..
- **CORS Support:** Integrated with Cross-Origin Resource Sharing for secure cross-site requests.
## How Fetch Works
- **Request:** Call `fetch(url, options)` where `url` is the resource and `options` is an optional object to configure method, headers, body, etc..
- **Response:** Returns a Promise that resolves to a `Response` object, from which you can extract data using methods like `.json()`, `.text()`, etc..
- **Error Handling:** Fetch only rejects the Promise on network errors, not on HTTP error status codes (e.g., 404 or 500). You must check `response.ok` or `response.status` to handle such cases.


---
# Frameworks in JS
A JavaScript (JS) framework is a collection of pre-written JavaScript code and tools that provides developers with a structured way to build web applications more efficiently.
Instead of starting every project from scratch, a JS framework offers templates, reusable components, and a set of rules or guidelines to organize your code and handle common tasks—such as rendering user interfaces, managing data, handling user input, and routing between pages.

**Analogy:**  
Think of a JS framework as a set of blueprints and pre-fabricated building blocks for constructing a house. Instead of making every brick and window from scratch, you use standardized parts and follow a proven plan, allowing you to build faster, avoid mistakes, and ensure everything fits together well.

**Difference from a Library:**  
A JS library (like jQuery) provides specific functions you can use as needed, but doesn’t dictate how you structure your overall application. A framework, on the other hand, provides both tools and a structured way to use them, guiding the overall architecture of your project

But first, from what we have learnt, we all know that node js is kind of one of the most important concepots we need, and we have covered the basic defination of it, but [what actually is nodeJs](What%20is%20NodeJs)?

---
## [[Introduction to VueJs]]
---
## [[Introduction to Express]]
---
## [[Introduction to Zod]]