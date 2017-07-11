# Util Classes
This project is destinated just to contain util classes for Android

# ManageDataJson.java
This class was made just to store a list of objects in a text file as a Json.

First you need to add the Gson lib in your project gradle:
```sh
#Always replace with the last version
compile 'com.google.code.gson:gson:2.8.1' 
```

In your Android Manifest file put:
```xml
<uses-permission android:name="android.permission.READ_EXTERNAL_STORAGE" />
<uses-permission android:name="android.permission.WRITE_EXTERNAL_STORAGE" />
```

To use the class, initialize it passing the file path and the activity:
```java
ManageDataJson.initialize("FILE_PATH", this);
```

After this, just record and read the objects:
- Record:
```java
ManageDataJson.recordNewData(yourObject);
```
- Read:
```java
List<YourObject> objects = ManageDataJson.getData();
```

Remember that the list just store one type of object, but this object can be from any type. So, if you want to use more than one type, use more than one list storing more than one file :)
