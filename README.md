ASWhitelabelActivity
====================

Implement a whitelabel version of AirService in your Android app

## Usage
For an example see example/ directory.

Import ASWhiteLabelActivity as a Library Project. See the following links for examples on how to do this.

* [https://developer.android.com/tools/projects/projects-eclipse.html#ReferencingLibraryProject](https://developer.android.com/tools/projects/projects-eclipse.html#ReferencingLibraryProject)

* [http://www.jetbrains.com/idea/webhelp/configuring-module-dependencies-and-libraries.html](http://www.jetbrains.com/idea/webhelp/configuring-module-dependencies-and-libraries.html)

If setup correctly you should see something similar to this in your project.properties file

```
android.library.reference.1=../ASWhitelabelActivity
```

Remember to add the activity to your AndroidManifest.xml

```
<activity android:name="com.airservice.whitelabel.ASWhitelabelActivity"
                  android:theme="@android:style/Theme.Translucent.NoTitleBar"
                  android:label="whitelabeldemo"/>
```

Showing ASWhiteLabelActivity within your application for your venueID = 24.

```java
import com.airservice.whitelabel.ASWhitelabelActivity;

        Intent intent = new Intent(this, ASWhitelabelActivity.class);
        ASWhitelabelActivity.populateParameters(intent, 24);
        startActivity(intent);
```

Or you can instead pass venueID as an extra on the intent

```java
intent.putExtra(ASWhitelabelActivity.AS_EXTRA_VENUE_ID, 24);
```

**Note:** venueID is required. Either by populateParameters(Intent intent, int venueID) or putExtra.

## Author

danielbowden, www.airservice.com

## License

ASWhitelabelActivity is available under the MIT license. See the LICENSE file for more info.

