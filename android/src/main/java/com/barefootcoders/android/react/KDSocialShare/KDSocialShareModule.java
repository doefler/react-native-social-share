package com.barefootcoders.android.react.KDSocialShare;

import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.net.Uri;
import android.os.Build;

import com.facebook.react.bridge.ReactApplicationContext;
import com.facebook.react.bridge.ReactContextBaseJavaModule;
import com.facebook.react.bridge.ReactMethod;
import com.facebook.react.bridge.ReadableMap;
import com.facebook.react.bridge.ReadableArray;
import com.facebook.react.bridge.Callback;

import java.util.List;
import java.io.File;

public class KDSocialShareModule extends ReactContextBaseJavaModule {
  ReactApplicationContext reactContext;

  public KDSocialShareModule(ReactApplicationContext reactContext) {
    super(reactContext);
    this.reactContext = reactContext;
  }

  @Override
  public String getName() {
    return "KDSocialShare";
  }

  @ReactMethod
  public void tweet(ReadableMap options, Callback callback) {
    try {
      String shareText = options.getString("text");

      if (doesPackageExist("com.twitter.android")) {
        try {
          tweetViaTwitterComposerClass(shareText);
        } catch (Exception ex1) {
          try {
            tweetViaTwitterDefaultClass(shareText);
          } catch (Exception ex2) {
            tweetViaWebPopup(shareText);
          }
        }
      } else {
        tweetViaWebPopup(shareText);
      }
    } catch (Exception ex) {
      callback.invoke("error");
    }
  }

  @ReactMethod
  public void shareOnFacebook(ReadableMap options, Callback callback) {
    try {
      Intent shareIntent;

      if (options.hasKey("text") && doesPackageExist("com.facebook.katana")) {
        String shareText = options.getString("text");
        shareIntent = new Intent(Intent.ACTION_SEND);
        shareIntent.setType("text/plain");
        shareIntent.putExtra(Intent.EXTRA_TEXT, shareText);
        if (Build.VERSION.SDK_INT <= Build.VERSION_CODES.M || Build.VERSION.SDK_INT >= Build.VERSION_CODES.P) {
          shareIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        }
      } else if (options.hasKey("link")) {
        String shareUrl = options.getString("link");
        String sharerUrl = "https://www.facebook.com/sharer/sharer.php?u=" + Uri.encode(shareUrl);
        shareIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(sharerUrl));
        if (Build.VERSION.SDK_INT <= Build.VERSION_CODES.M || Build.VERSION.SDK_INT >= Build.VERSION_CODES.P) {
          shareIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        }
      } else {
        if (options.hasKey("text") && !doesPackageExist("com.facebook.katana")) {
          callback.invoke("error", "If text is provided to Facebook sharing, the application must be installed");
        } else {
          callback.invoke("error");
        }
        return;
      }

      reactContext.startActivity(shareIntent);
    } catch (Exception ex) {
      callback.invoke("error");
    }
  }

  private boolean doesPackageExist(String targetPackage) {
    PackageManager pm = this.reactContext.getPackageManager();
    try {
      PackageInfo info = pm.getPackageInfo(
        targetPackage,
        PackageManager.GET_META_DATA
      );
     } catch (PackageManager.NameNotFoundException e) {
       return false;
     }
     return true;
  }

  private void tweetViaTwitterComposerClass(String shareText) throws Exception {
    Intent shareIntent = new Intent(Intent.ACTION_SEND);
    shareIntent.setClassName("com.twitter.android", "com.twitter.android.composer.ComposerActivity");
    shareIntent.setType("text/*");
    shareIntent.putExtra(android.content.Intent.EXTRA_TEXT, shareText);
    shareIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
    reactContext.startActivity(shareIntent);
  }

  private void tweetViaTwitterDefaultClass(String shareText) throws Exception {
    Intent shareIntent = new Intent(Intent.ACTION_SEND);
    shareIntent.setPackage("com.twitter.android");
    shareIntent.setType("text/*");
    shareIntent.putExtra(android.content.Intent.EXTRA_TEXT, shareText);
    shareIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
    reactContext.startActivity(shareIntent);
  }

  private void tweetViaWebPopup(String shareText) throws Exception {
    String tweetUrl = "https://twitter.com/intent/tweet?text=" + Uri.encode(shareText);
    Uri uri = Uri.parse(tweetUrl);
    Intent shareIntent = new Intent(Intent.ACTION_VIEW, uri);
    shareIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
    reactContext.startActivity(shareIntent);
  }
}
