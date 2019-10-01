# SmartCredentials Android Coding Standards

## 1. Code
### 1.1 File naming
Class names are written in __UpperCamelCase__.

For classes that extend an Android component, the name of the class should end with the name of the component. 
E.g.: 
`SignInActivity`, `SignInFragment`, `ImageUploaderService`, `ChangePasswordDialog`.

### 1.2 Style
The coding standards of the SmartCredentials library are inspired from the official Android code style guidelines: [http://source.android.com/source/code-style.html](http://source.android.com/source/code-style.html).

### 1.3 Line Length
Stick within the 100 char line limit. Use line breaks to split up code according to the style guidelines.

### 1.4 Indentation and white spaces
Use 4 white space indents for blocks and never tabs. One can set the IDE to convert tabs to white spaces, when used. Also, use 8 white spaces indents for line wraps. 

__Do:__
```java
Intent intent = 
        new Intent(AnActivity.this, AnotherActivity.class);
```

__Don't:__
```java
Intent intent = 
    new Intent(AnActivity.this, AnotherActivity.class);
```

Code should not have any trailing whitespace to avoid creating unnecessary diff issues.

### 1.5 Handling exceptions

#### 1.5.1 Don't ignore exceptions
You must never do the following:
 
```java
void setServerPort(String value) {
    try {
        serverPort = Integer.parseInt(value);
    } catch (NumberFormatException e) { 
        // ignored
    }
}
```

_Anytime somebody has an empty catch clause they should have a creepy feeling. There are definitely times when it is actually the correct thing to do, but at least you have to think about it. In Java you can't escape the creepy feeling._ - James Gosling

See alternatives to ignoring exceptions [here](https://source.android.com/setup/contribute/code-style#dont-ignore-exceptions).

#### 1.5.2 Don't catch generic exceptions:
You should not do the following:

```java
try {
	someComplicatedIOFunction();        // may throw IOException
	someComplicatedParsingFunction();   // may throw ParsingException
	someComplicatedSecurityFunction();  // may throw SecurityException
} catch (Exception e) {                 // I'll just catch all exceptions
	handleError();                      // with one generic handler!
}
```

See the reason why and some alternatives [here](https://source.android.com/setup/contribute/code-style#dont-catch-generic-exception).

### 1.6 Imports

#### 1.6.1 Unused imports
Remove any unused imports.

#### 1.6.2 Fully qualify imports

__Do:__ `import foo.Bar;`

__Don't:__ `import foo.*;`

#### 1.6.3 Import ordering
If you are using an IDE such as Android Studio, you don't have to worry about this because your IDE is already obeying these rules. If not, have a look below.

The ordering of import statements is:

1. Android imports
2. Imports from third parties (com, junit, net, org)
3. java and javax
4. Same project imports

To exactly match the IDE settings, the imports should be:

* Alphabetically ordered within each grouping, with capital letters before lower case letters.
* There should be a blank line between each major grouping.

### 1.7 Attributes definition and naming
Attributes should be defined at the __top of the file__ and they should follow the naming rules listed below.

* Private, non-static field names start with __m__.
* Private, static field names start with __s__.
* Other fields start with a lower case letter.
* Static final fields (constants) are ALL_CAPS_WITH_UNDERSCORES.

E.g.: 
```java
public class MyClass {
    public static final int SOME_CONSTANT = 10;
    public int publicField;
    private static MyClass sSingleton;
    int mPackagePrivate;
    private int mPrivate;
    protected int mProtected;
}
```

### 1.8 Use standard brace style

Braces go on the same line as the code before them.

```java
class MyClass {
    int func() {
        if (something) {
            // ...
        } else if (somethingElse) {
            // ...
        } else {
            // ...
        }
    }
}
```

Braces around the statements are required unless the condition and the body fit on one line. If the condition and the body fit on one line and that line is shorter than the max line length, then braces are not required.

__Do:__
```java
if (condition) body();
```

__Don't:__
```java
if (condition)
    body();
```

### 1.9 Annotations

#### 1.9.1 Annotations practices

According to the Android code style guide, the standard practices for some of the predefined annotations in Java are:

* `@Override`: The @Override annotation __must be used__ whenever a method overrides the declaration or implementation from a super-class. For example, if you use the @inheritdocs Javadoc tag, and derive from a class (not an interface), you must also annotate that the method @Overrides the parent class's method.

* `@SuppressWarnings`: The @SuppressWarnings annotation should only be used under circumstances where it is impossible to eliminate a warning. If a warning passes this "impossible to eliminate" test, the @SuppressWarnings annotation must be used, so as to ensure that all warnings reflect actual problems in the code.

#### 1.9.2 Annotations style

__Classes, Methods and Constructors__

When annotations are applied to a class, method, or constructor, they are listed after the documentation block and should appear as __one annotation per line__ .

```java
/* This is the documentation block about the class */
@AnnotationA
@AnnotationB
public class MyAnnotatedClass { 
   ...
}
```

__Fields__

Annotations applying to fields should be listed __on the same line__, unless the line reaches the maximum line length.

```java
@Nullable @Mock DataManager mDataManager;
```

### 1.10 Treat acronyms as words

| __Do__           | __Don't__        |
| ---------------- | ---------------- |
| `XmlHttpRequest` | `XMLHTTPRequest` |
| `getCustomerId`  | `getCustomerID`  |
| `String url`     | `String URL`     |
| `long id`        | `long ID`        |

### 1.11 Class member ordering

There is no single correct solution for this but using a __logical__ and __consistent__ order will improve code learnability and readability. It is recommendable to use the following order:

1. Constants
2. Fields
3. Constructors
4. Override methods and callbacks (public or private)
5. Public methods
6. Private methods
7. Inner classes or interfaces

Example:

```java
public class MainActivity extends Activity {

    private static final String TAG = MainActivity.class.getSimpleName();

    private String mTitle;
    private TextView mTextViewTitle;

    @Override
    public void onCreate() {
        ...
    }

    public void setTitle(String title) {
    	mTitle = title;
    }

    private void setUpView() {
        ...
    }

    static class AnInnerClass {

    }

}
```

If your class is extending an __Android component__ such as an Activity or a Fragment, it is a good practice to order the override methods so that they __match the component's lifecycle__. For example, if you have an Activity that implements `onCreate()`, `onDestroy()`, `onPause()` and `onResume()`, then the correct order is:

```java
public class MainActivity extends Activity {

    @Override
    public void onCreate() {}

    @Override
    public void onResume() {}

    @Override
    public void onPause() {}

    @Override
    public void onDestroy() {}

}
```

### 1.12 Use TODO comments
Use TODO comments for code that is temporary, a short-term solution, or good-enough but not perfect. TODOs should include the string TODO in all caps, followed by a colon:

```java
// TODO: Change this to use a flag instead of a constant.
```

## 2. Resources

### 2.1 File naming
Resources file names are written in __lowercase_underscore__.

The Android build tools merge resources from a library module with those of a dependent app module.

To avoid resource conflicts for common resource IDs, use the following naming scheme, applying the `sc` prefix to resource namings:

#### 2.1.1 Drawable files

Naming conventions for drawables:

| Asset Type   | Prefix               |		Example                    | 
|------------- | -------------------- |------------------------------- |
| Action bar   | `sc_ab_`             | `sc_ab_stacked.9.png`          |
| Button       | `sc_btn_`	          | `sc_btn_send_pressed.9.png`    |
| Dialog       | `sc_dialog_`         | `sc_dialog_top.9.png`          |
| Divider      | `sc_divider_`        | `sc_divider_horizontal.9.png`  |
| Icon         | `sc_ic_`	          | `sc_ic_star.png`               |
| Menu         | `sc_menu_`           | `sc_menu_submenu_bg.9.png`     |
| Notification | `sc_notification_`	  | `sc_notification_bg.9.png`     |
| Tabs         | `sc_tab_`            | `sc_tab_pressed.9.png`         |

Naming conventions for icons:

| Asset Type                      | Prefix             	  | Example                         |
| ------------------------------- | --------------------- | ------------------------------- |
| Icons                           | `sc_ic_`              | `sc_ic_star.png`                |
| Launcher icons                  | `sc_ic_launcher`      | `sc_ic_launcher_calendar.png`   |
| Menu icons and Action Bar icons | `sc_ic_menu`          | `sc_ic_menu_archive.png`        |
| Status bar icons                | `sc_ic_stat_notify`   | `sc_ic_stat_notify_msg.png`     |
| Tab icons                       | `sc_ic_tab`           | `sc_ic_tab_recent.png`          |
| Dialog icons                    | `sc_ic_dialog`        | `sc_ic_dialog_info.png`         |

Naming conventions for selector states:

| State	       | Suffix          | Example                        |
|------------- |---------------- |------------------------------- |
| Normal       | `_normal`       | `sc_btn_order_normal.9.png`    |
| Pressed      | `_pressed`      | `sc_btn_order_pressed.9.png`   |
| Focused      | `_focused`      | `sc_btn_order_focused.9.png`   |
| Disabled     | `_disabled`     | `sc_btn_order_disabled.9.png`  |
| Selected     | `_selected`     | `sc_btn_order_selected.9.png`  |

#### 2.1.2 Layout files

Layout files should match the name of the Android components that they are intended for, but moving the top level component name to the beginning, after the library prefix. For example, if we are creating a layout for the `SignInActivity`, the name of the layout file should be `sc_activity_sign_in.xml`.

| Component        | Class Name             | Layout Name                      |
| ---------------- | ---------------------- | -------------------------------- |
| Activity         | `UserProfileActivity`  | `sc_activity_user_profile.xml`   |
| Fragment         | `SignUpFragment`       | `sc_fragment_sign_up.xml`        |
| Dialog           | `ChangePasswordDialog` | `sc_dialog_change_password.xml`  |
| AdapterView item | ---                    | `sc_item_person.xml`             |
| Partial layout   | ---                    | `sc_partial_stats_bar.xml`       |

#### 2.1.3 Menu files

Similar to layout files, menu files should match the name of the component. For example, if we are defining a menu file that is going to be used in the `UserActivity`, then the name of the file should be `sc_activity_user.xml`

A good practice is to not include the word `menu` as part of the name because these files are already located in the `menu` directory.

### 2.2 Resources naming

Resource IDs and names are written in __lowercase_underscore__.

#### 2.2.1 ID naming

IDs should be prefixed with the libray prefix and then the name of the element in lowercase underscore. For example:

| Element              | Prefix                 |
| -------------------- | ---------------------- |
| `TextView`           | `sc_text_`             |
| `ImageView`          | `sc_image_`            |
| `Button`             | `sc_button_`           |
| `Menu`               | `sc_menu_`             |

Image view example:

```xml
<ImageView
    android:id="@+id/sc_image_profile"
    android:layout_width="wrap_content"
    android:layout_height="wrap_content" />
```

Menu example:

```xml
<menu>
    <item
        android:id="@+id/sc_menu_done"
        android:title="Done" />
</menu>
```
#### 2.2.2 Strings

String names start with the library prefix and a word that identifies the section they belong to. For example `sc_registration_email_hint` or `sc_registration_name_hint`. If a string __doesn't belong__ to any section, then you should follow the rules below:

| Prefix                  | Description                            |
| ----------------------- | -------------------------------------- |
| `sc_error_`             | An error message                       |
| `sc_msg_`               | A regular information message          |
| `sc_title_`             | A title, i.e. a dialog title           |
| `sc_action_`            | An action such as "Save" or "Create"   |

#### 2.2.3 Styles and Themes

Unlike the rest of resources, style names are written in __UpperCamelCase__.

### 2.3 Use self-closing tags
When an XML element doesn't have any content, you __must__ use self closing tags.

__Do:__
```xml
<TextView
    android:id="@+id/sc_text_view"
    android:layout_width="wrap_content"
    android:layout_height="wrap_content" />
```

__Don't:__
```xml
<TextView
    android:id="@+id/sc_text_view"
    android:layout_width="wrap_content"
    android:layout_height="wrap_content" >
</TextView>
```

### 2.4 Structure
As a general rule you should try to group similar attributes together. A good way of ordering the most common attributes is:

1. View Id
2. Style
3. Layout width and layout height
4. Other layout attributes, sorted alphabetically
5. Remaining attributes, sorted alphabetically

## 3. Documentation

### 3.1 Javadoc
Every file should have a copyright statement at the top, followed by package and import statements (each block separated by a blank line) and finally the class or interface declaration. In the Javadoc comments, describe what the class or interface does.

//TODO: replace with real SmartCredentials license
```java
/*
 * Copyright 2018 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package de.telekom.smartcredentials;

import android.os.Foo;
import android.view.Bar;

import java.util.Arrays;
import java.util.List;

/**
 * Does X and Y and provides an abstraction for Z.
 */
public class Foo {
    ...
}
```

Every class and nontrivial public method you write must contain a Javadoc comment with at least one sentence describing what the class or method does. This sentence should start with a third person descriptive verb.
```java
/** Returns the correctly rounded positive square root of a double value. */
static double sqrt(double a) {
    ...
}
```

or 

```java
/**
 * Constructs a new String by converting the specified array of
 * bytes using the platform's default character encoding.
 */
public String(byte[] bytes) {
    ...
}
```

### 3.2 Comments
Use in-line commenting to help the next developer who might be editing your code, even if it seems obvious now. Inline comments should appear on the line above the code your are commenting.
Comment Java code using ```// comment``` and XML view elements using ```<!-- comment -->```.

## 4. Version control
No commented out code must be committed unless you have a very good reason that is clearly described in a comment by the code you are ommitting.
