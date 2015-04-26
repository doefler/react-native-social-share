# React Native Social Share

Use the build in share view from iOS to let the user share on Facebook and Twitter.
It will use the users existing account without having to get new authorizations.
You can even preset text, image and link for the share view.

In other words a React Native wrapper for the `SLComposeViewController`


![Animation](https://github.com/doefler/react-native-social-share/blob/master/animation.gif))
![Screenshot](https://github.com/doefler/react-native-social-share/blob/master/still.png))

## Getting started

1. `npm install react-native-social-share --save`
2. In XCode, in the project navigator right click `Libraries` ➜ `Add Files to [your project's name]`
3. Go to `node_modules` ➜ `react-native-social-share`➜ iOS and add `KDSocialShare.h` and `KDSocialShare.m` 
4. Go to your project's `Build Phases` ➜ `Link Binary With Libraries` phase
5. Add `Social.framework` to ➜ `Link Binary With Libraries` build phase of your project (click the '+' and search for 'social').
6. Run your project (`Cmd+R`)

- Now you can implement the share popups in your react native code.

## Example of implementation

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

`KDSocialShare.shareOnFacebook(options [object], callback [function])`
`KDSocialShare.tweet(options [object], callback [function])`

Both the options object and the callback function needs to be set. The options object can be empty though if you do not want to preset any of the possible options. 

text: - Sets the initial text of the message on the SLComposeViewController instance.
imagelink: - Adds image files as attachments to the message.
link: - Adds a URL to the message. The method automatically handles the URL shortening.

SLComposeViewControllerResultCancelled – The user cancelled the composition session by touching the Cancel button.
SLComposeViewControllerResultDone – The user sent the composed message by touching the Send button.
