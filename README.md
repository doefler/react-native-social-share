# React Native Social Share

Use the build in share view from iOS to let the user share on Facebook and Twitter.
It will use the users existing account without having to get new authorizations.
You can even preset text, image and link for the share view.

In other words a React Native wrapper for the `SLComposeViewController`

I developed this module for our upcoming artboost app.
https://artboost.com


![Animation](https://raw.githubusercontent.com/doefler/react-native-social-share/master/animation-looping.gif)

## Getting started

1. `npm install react-native-social-share --save`
2. In XCode, in the project navigator right click `Libraries` ➜ `Add Files to [your project's name]`
3. Go to `node_modules` ➜ `react-native-social-share`➜ iOS and add `KDSocialShare.h` and `KDSocialShare.m` 
4. Go to your project's `Build Phases` ➜ `Link Binary With Libraries` phase
5. Add `Social.framework` to ➜ `Link Binary With Libraries` build phase of your project (click the '+' and search for 'social').
6. Run your project (`Cmd+R`)

Now you can implement the share popups in your react native code.

## Example of implementation

First you should make the native implementation available in the react code by inserting the following line in the top of the file
```
var KDSocialShare = require('NativeModules').KDSocialShare;
```
After doing that you will be able to popup the share views from your own functions. I made two examples below one for Facebook and one for Twitter
```
  tweet : function() {

    KDSocialShare.tweet({
        'text':'Global democratized marketplace for art',
        'link':'https://artboost.com/',
        'imagelink':'https://artboost.com/apple-touch-icon-144x144.png',
      },
      (results) => {
        console.log(results);
      }
    );
  },

  shareOnFacebook : function() {

    KDSocialShare.shareOnFacebook({
        'text':'Global democratized marketplace for art',
        'link':'https://artboost.com/',
        'imagelink':'https://artboost.com/apple-touch-icon-144x144.png',
      },
      (results) => {
        console.log(results);
      }
    );
  },
```

The two implementations take the following paramters

- `KDSocialShare.shareOnFacebook(options [object], callback [function])`
- `KDSocialShare.tweet(options [object], callback [function])`

#### IMPORTANT Both the options object and the callback function needs to be set. The options object can be empty though if you do not want to preset any of the possible options. 

### Options
The options object lets you prepopulate the share view for the user. You can use the following parameters:

| Parameter     | Desciption    | 
| ------------- | ------------- |
| text      | Sets the initial text of the message on the SLComposeViewController instance.  |
| imagelink      | Adds an image file from the given publicly available URL as attachments to the message.  |
| link      | Adds a URL to the message. The method automatically handles the URL shortening.  |


### Callback
The callback function runs when the native environment has information for the react environment

| Callback     | Desciption    | 
| ------------- | ------------- |
| "success"      | Native call made by the viewController - SLComposeViewControllerResultDone – The user sent the composed message by touching the Send button. |
| "cancelled"      | Native call made by the viewController - SLComposeViewControllerResultCancelled – The user cancelled the composition session by touching the Cancel button.  |
| "not_available"      | The selected service eg. Facebook, is not available. This can be because the user has not signed in to Facebook on the device or maybe there is no internet access. |

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

var KDSocialShare = require('NativeModules').KDSocialShare;


var ReactNativeSocialShare = React.createClass({

  tweet : function() {

    KDSocialShare.tweet({
        'text':'Global democratized marketplace for art',
        'link':'https://artboost.com/',
        'imagelink':'https://artboost.com/apple-touch-icon-144x144.png',
      },
      (results) => {
        console.log(results);
      }
    );
  },

  shareOnFacebook : function() {

    KDSocialShare.shareOnFacebook({
        'text':'Global democratized marketplace for art',
        'link':'https://artboost.com/',
        'imagelink':'https://artboost.com/apple-touch-icon-144x144.png',
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

        <TouchableHighlight onPress={this.shareOnFacebook}>
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
