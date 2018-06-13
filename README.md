# React Native Social Share

Use the built-in share view from iOS and Android to let the user share on Facebook and Twitter.
It will use the user's existing account without having to get new authorizations.
You can even preset text, image and link for the share view.

In other words a React Native wrapper for the `SLComposeViewController`

__Support for Android__

27 Feb 2017 - @minhtule has made improvements to sharing on Android 

10 Feb 2017 - @Jberlinsky has added support for Android

Let me know how it works.



![Animation](https://raw.githubusercontent.com/doefler/react-native-social-share/master/assets/animation-looping.gif)


## Getting started

1. `npm install react-native-social-share --save`
2. `react-native link`
3. In XCode, in the project navigator right click `Libraries` ➜ `Add Files to [your project's name]`
4. Go to `node_modules` ➜ `react-native-social-share`➜ iOS and add `KDSocialShare.h` and `KDSocialShare.m` 
5. Go to your project's `Build Phases` ➜ `Link Binary With Libraries` phase
6. Add `Social.framework` to ➜ `Link Binary With Libraries` build phase of your project (click the '+' and search for 'social').
7. Add 'LSApplicationQueriesSchemes' key (Type: Array) with items (Type: String) 'fb' and 'twitter'  to `Info.plist` of your project 
8. Run your project (`Cmd+R`)

Now you can implement the share popups in your react native code.

## Example of implementation

First you should make the native implementation available in the react code by inserting the following line in the top of the file
``` JavaScript
import {
  shareOnFacebook,
  shareOnTwitter,
} from 'react-native-social-share';
```
After doing that you will be able to popup the share views from your own functions. I made two examples below, one for Facebook and one for Twitter
``` JavaScript
  tweet : function() {

    shareOnTwitter({
        'text':'Global democratized marketplace for art',
        'link':'https://artboost.com/',
        'imagelink':'https://artboost.com/apple-touch-icon-144x144.png',
        //or use image
        'image': 'artboost-icon',
      },
      (results) => {
        console.log(results);
      }
    );
  },

  facebookShare : function() {

    shareOnFacebook({
        'text':'Global democratized marketplace for art',
        'link':'https://artboost.com/',
        'imagelink':'https://artboost.com/apple-touch-icon-144x144.png',
        //or use image
        'image': 'artboost-icon',
      },
      (results) => {
        console.log(results);
      }
    );
  },
```

The two implementations take the following parameters

- `shareOnFacebook(options [object], callback [function])`
- `shareOnTwitter(options [object], callback [function])`

#### IMPORTANT Both the options object and the callback function needs to be set. The options object can be empty though if you do not want to preset any of the possible options.

### Options
The options object lets you pre-populate the share view for the user. You can use the following parameters:

| Parameter     | Desciption    |
| ------------- | ------------- |
| text      | Sets the initial text of the message on the SLComposeViewController instance.  |
| imagelink      | Adds an image file from the given publicly available URL as attachments to the message.  |
| image     | Adds an image file from the xcode image assets.  image takes priority over imagelink. Only one out of two will load.  |
| link      | Adds a URL to the message. The method automatically handles the URL shortening.  |

**At least the `text` or `link` parameter must be specified**

### Special Case: Facebook on Android

Due to [various known problems](http://stackoverflow.com/questions/23541823/how-to-share-text-and-image-on-facebook-using-intent) with Facebook's implementation of Android Intents, sharing with Facebook on Android can only be done in two ways:

1. If the user has the Facebook application installed, and the `text` parameter is provided; or
2. If the `link` parameter is provided.

Only one of the `link` or `text` parameter can be passed to the `shareWithFacebook` method on Android devices. Image parameters are ignored entirely.

We recommend using the [official Facebook SDK](https://developers.facebook.com/docs/sharing/android) to perform more complex sharing operations on Android.


### Callback
The callback function runs when the native environment has information for the react environment. **Note that some callbacks are only available on iOS due to platform limitations**

| Callback     | Desciption    | iOS | Android |
| ------------- | ------------- | ----| ------- |
| "success"      | Native call made by the viewController - SLComposeViewControllerResultDone – The user sent the composed message by touching the Send button. | Yes | No |
| "cancelled"      | Native call made by the viewController - SLComposeViewControllerResultCancelled – The user cancelled the composition session by touching the Cancel button.  | Yes | No |
| "not_available"      | The selected service eg. Facebook, is not available. This can be because the user has not signed in to Facebook on the device or maybe there is no internet access. | Yes | No (Android functionality falls back to web views) |
| "missing\_link\_or\_text"      | Neither the `link` nor `text` parameter was provided | Yes | Yes |

You can use these callbacks to present alerts to the user. For example tell the user to login to a certain service.


## The full example code
``` JavaScript
'use strict';

var React = require('react-native');
var {
  AppRegistry,
  StyleSheet,
  Text,
  View,
  TouchableHighlight,
} = React;

import {
  shareOnFacebook,
  shareOnTwitter,
} from 'react-native-social-share';


var ReactNativeSocialShare = React.createClass({

  tweet : function() {

    shareOnTwitter({
        'text':'Global democratized marketplace for art',
        'link':'https://artboost.com/',
        'imagelink':'https://artboost.com/apple-touch-icon-144x144.png',
        //or use image
        'image': 'artboost-icon',
      },
      (results) => {
        console.log(results);
      }
    );
  },

  facebookShare : function() {

    shareOnFacebook({
        'text':'Global democratized marketplace for art',
        'link':'https://artboost.com/',
        'imagelink':'https://artboost.com/apple-touch-icon-144x144.png',
        //or use image
        'image': 'artboost-icon',
      },
      (results) => {
        console.log(results);
      }
    );
  },


  render: function() {
    return (
      <View style={styles.container}>
        <Text style={styles.welcome}>
          Twitter and Facebook share
        </Text>

        <Text style={styles.instructions}>
          Try tapping one of the buttons
        </Text>

        <View style={styles.seperator}/>

        <TouchableHighlight onPress={this.tweet}>
          <View style={{alignItems: 'center',justifyContent:'center', width: 150, height: 50,backgroundColor:'#00aced'}}>
           <Text style={{color:'#ffffff',fontWeight:'800',}}>Share on Twitter</Text>
          </View>
        </TouchableHighlight>

        <View style={styles.seperator}/>

        <TouchableHighlight onPress={this.facebookShare}>
          <View style={{alignItems: 'center',justifyContent:'center', width: 150, height: 50,backgroundColor:'#3b5998'}}>
           <Text style={{color:'#ffffff',fontWeight:'800',}}>Share on Facebook</Text>
          </View>
        </TouchableHighlight>
      </View>


    );
  }
});

var styles = StyleSheet.create({
  container: {
    flex: 1,
    justifyContent: 'center',
    alignItems: 'center',
    backgroundColor: '#F5FCFF',
  },
  welcome: {
    fontSize: 20,
    textAlign: 'center',
    margin: 10,
  },
  seperator:{
    marginBottom: 20
  }
});

AppRegistry.registerComponent('ReactNativeSocialShare', () => ReactNativeSocialShare);

```

## Done

![Screenshot](https://raw.githubusercontent.com/doefler/react-native-social-share/master/assets/still.png)


## Who is using it

* [ec-deploy-mobile](https://github.com/artyomtrityak/ec-deploy-mobile) by [artyomtrityak](https://github.com/artyomtrityak)
* [react-native-recipes-app](https://github.com/push23/react-native-recipes-app) by [push23](https://github.com/push23)
* [drumpfgenerator-mobile](https://github.com/si74/drumpfgenerator-mobile) by [si74](https://github.com/si74)
* [best_quotes_native_app](https://github.com/anamariasosam/best_quotes_native_app) by [anamariasosam](https://github.com/anamariasosam)
* [Concert](https://github.com/aakashsigdel/Concert) by [aakashsigdel](https://github.com/aakashsigdel)
* [Synerzip-HRMS-iOS](https://github.com/Synerzip/Synerzip-HRMS-iOS) by [Synerzip](https://github.com/Synerzip)
* [slight-note](https://github.com/Roy9102/slight-note) by [Roy9102](https://github.com/Roy9102)
* [Water](https://github.com/jvt/Water) by [jvt](https://github.com/jvt)
* [smstet](https://github.com/phanthoa/smstet) by [phanthoa](https://github.com/phanthoa)
* [PilotCalendarFinancial](https://github.com/kenvandemar/PilotCalendarFinancial) by [kenvandemar](https://github.com/kenvandemar)
* [devine-v0-app](https://github.com/davidsims9t/devine-v0-app) by [davidsims9t](https://github.com/davidsims9t)
* [rebus](https://github.com/michaelgena/rebus) by [michaelgena](https://github.com/michaelgena)
* [NeedlApp](https://github.com/ghamaide/NeedlApp) by [ghamaide](https://github.com/ghamaide)
* [randomSofar](https://github.com/conorcussell/randomSofar) by [conorcussell](https://github.com/conorcussell)
* [react-native-create-caps](https://github.com/sozkahya/react-native-create-caps) by [sozkahya](https://github.com/sozkahya)
* [DocLibs](https://github.com/IceNeoMax/DocLibs) by [IceNeoMax](https://github.com/IceNeoMax)
* [rebby](https://github.com/michaelgena/rebby) by [michaelgena](https://github.com/michaelgena)
* [PilotFinancialCalendarRedux](https://github.com/kenvandemar/PilotFinancialCalendarRedux) by [kenvandemar](https://github.com/kenvandemar)
* [client-fashion-spotting-app](https://github.com/hmm29/client-fashion-spotting-app) by [hmm29](https://github.com/hmm29)


Your contributions and suggestions are welcome.
