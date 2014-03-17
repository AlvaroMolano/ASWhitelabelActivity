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

**Note:** AppID and AppToken are required. These can be obtained from AirService



Showing ASWhiteLabelActivity within your application with a single venue for your venue alias = "my-venue".

```java
import com.airservice.whitelabel.ASOptions;
import com.airservice.whitelabel.ASWhitelabelActivity;

ASOptions options = new ASOptions(appID, appToken);
options.setVenueAlias("my-venue");

Intent intent = new Intent(this, ASWhitelabelActivity.class);
ASWhitelabelActivity.setOptions(intent, options);
startActivity(intent);
```

Showing ASWhiteLabelActivity within your application for multiple venues within your filter group provided by AirService ie. "Example Pizza Chain" = "EX01"

```java
import com.airservice.whitelabel.ASOptions;
import com.airservice.whitelabel.ASWhitelabelActivity;

ASOptions options = new ASOptions(appID, appToken);
options.setFilter("EX01");
options.setBrandColor("444444"); //your default brand hex color

Intent intent = new Intent(this, ASWhitelabelActivity.class);
ASWhitelabelActivity.setOptions(intent, options);
startActivity(intent);
```


## Author

danielbowden, www.airservice.com

## License

ASWhitelabelActivity is available under the MIT license. See the LICENSE file for more info.

