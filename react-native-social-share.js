/**
 * @providesModule react-native-social-share
 */

var KDSocialShare = require('react-native').NativeModules.KDSocialShare;

module.exports = {
  shareOnTwitter: function(params, callback) {
    if (!(params['link'] || params['text'])) {
      callback("missing_link_or_text");
    } else {
      return KDSocialShare.tweet(params, callback);
    }
  },
  shareOnFacebook: function(params, callback) {
    if (!(params['link'] || params['text'])) {
      callback("missing_link_or_text");
    } else {
      return KDSocialShare.shareOnFacebook(params, callback);
    }
  }
};
