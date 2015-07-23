//
//  KDSocialShare.m
//  ReactNativeSocialShare
//
//  Created by Kim Døfler Sand Laursen on 25-04-15.
//  Copyright (c) 2015 Facebook. All rights reserved.
//

#import "KDSocialShare.h"
#import "RCTConvert.h"
#import <Social/Social.h>

@implementation KDSocialShare

// Expose this module to the React Native bridge
RCT_EXPORT_MODULE()

RCT_EXPORT_METHOD(tweet:(NSDictionary *)options
                  callback: (RCTResponseSenderBlock)callback)
{
  if([SLComposeViewController isAvailableForServiceType:SLServiceTypeTwitter]) {
    NSString *serviceType = SLServiceTypeTwitter;
    SLComposeViewController *composeCtl = [SLComposeViewController composeViewControllerForServiceType:serviceType];
    
    if (options[@"link"]){
      NSString *link = [RCTConvert NSString:options[@"link"]];
      [composeCtl addURL:[NSURL URLWithString:link]];
    }
    
    if (options[@"image"]){
      [composeCtl addImage: [UIImage imageNamed: options[@"image"]]];
    } else if (options[@"imagelink"]){
      NSString *imagelink = [RCTConvert NSString:options[@"imagelink"]];
      UIImage *image = [UIImage imageWithData:[NSData dataWithContentsOfURL:[NSURL URLWithString:imagelink]]];
      [composeCtl addImage:image];
    }
    
    if (options[@"text"]){
      NSString *text = [RCTConvert NSString:options[@"text"]];
      [composeCtl setInitialText:text];
    }
    
    [composeCtl setCompletionHandler:^(SLComposeViewControllerResult result) {
      if (result == SLComposeViewControllerResultDone) {
        // Sent
        callback(@[@"success"]);
      }
      else if (result == SLComposeViewControllerResultCancelled){
        // Cancelled
        callback(@[@"cancelled"]);
      }
    }];
    
    UIViewController *ctrl = [[[[UIApplication sharedApplication] delegate] window] rootViewController];
    [ctrl presentViewController:composeCtl animated:YES completion: nil];
  }
  else{
    callback(@[@"not_available"]);
  }
}

RCT_EXPORT_METHOD(shareOnFacebook:(NSDictionary *)options
                  callback: (RCTResponseSenderBlock)callback)
{
  if([SLComposeViewController isAvailableForServiceType:SLServiceTypeFacebook]) {
    NSString *serviceType = SLServiceTypeFacebook;
    SLComposeViewController *composeCtl = [SLComposeViewController composeViewControllerForServiceType:serviceType];
    
    if (options[@"link"]){
      NSString *link = [RCTConvert NSString:options[@"link"]];
      [composeCtl addURL:[NSURL URLWithString:link]];
    }

    if (options[@"image"]){
      [composeCtl addImage: [UIImage imageNamed: options[@"image"]]];
    } else if (options[@"imagelink"]){
      NSString *imagelink = [RCTConvert NSString:options[@"imagelink"]];
      UIImage *image = [UIImage imageWithData:[NSData dataWithContentsOfURL:[NSURL URLWithString:imagelink]]];
      [composeCtl addImage:image];
    }
    
    if (options[@"text"]){
      NSString *text = [RCTConvert NSString:options[@"text"]];
      [composeCtl setInitialText:text];
    }
    
    [composeCtl setCompletionHandler:^(SLComposeViewControllerResult result) {
      if (result == SLComposeViewControllerResultDone) {
        // Sent
        callback(@[@"success"]);
      }
      else if (result == SLComposeViewControllerResultCancelled){
        // Cancelled
        callback(@[@"cancelled"]);
      }
    }];
    
    UIViewController *ctrl = [[[[UIApplication sharedApplication] delegate] window] rootViewController];
    [ctrl presentViewController:composeCtl animated:YES completion: nil];
  }
  else{
    callback(@[@"not_available"]);
  }
}



@end