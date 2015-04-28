/**
 * Sample React Native App
 * https://github.com/facebook/react-native
 */
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
