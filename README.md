# React Native Social Share

Use the built-in share view from iOS to let the user share on Facebook and Twitter.
It will use the user's existing account without having to get new authorizations.
You can even preset text, image and link for the share view.

In other words a React Native wrapper for the `SLComposeViewController`

I developed this module for our upcoming artboost app.
https://artboost.com


![Animation](https://raw.githubusercontent.com/doefler/react-native-social-share/master/animation-looping.gif)

## Getting started

1. `npm install react-native-social-share --save`
2. `react-native link`
3. In XCode, go to your project's `Build Phases` ➜ `Link Binary With Libraries` phase
4. Add `Social.framework` to ➜ `Link Binary With Libraries` build phase of your project (click the '+' and search for 'social').
5. Run your project (`Cmd+R`)

Now you can implement the share popups in your react native code.

## Example of implementation

First you should make the native implementation available in the react code by inserting the following line in the top of the file
```
import {
  shareOnFacebook,
  shareOnTwitter,
} from 'react-native-social-share';
```
After doing that you will be able to popup the share views from your own functions. I made two examples below, one for Facebook and one for Twitter
```
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
```
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

![Screenshot](https://raw.githubusercontent.com/doefler/react-native-social-share/master/still.png)
