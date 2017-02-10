/**
 * @providesModule react-native-device-info
 */

var KDSocialShare = require('react-native').NativeModules.KDSocialShare;

module.exports = {
  shareOnTwitter: function(params, callback) {
    return KDSocialShare.tweet(params, callback);
  },
  shareOnFacebook: function(params, callback) {
    return KDSocialShare.shareOnFacebook(params, callback);
  }
};
