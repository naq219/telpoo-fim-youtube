//package com.telpoo.frame.sharing;
//
//import java.io.File;
//
//import com.telpoo.frame.delegate.Idelegate;
//
//import twitter4j.StatusUpdate;
//import twitter4j.Twitter;
//import twitter4j.TwitterFactory;
//import twitter4j.conf.Configuration;
//import twitter4j.conf.ConfigurationBuilder;
//import android.app.Activity;
//import android.content.Context;
//
//public class TwitterHelper {
//
//	public static boolean postToTwitterWithImage(final String imageUrl, final String message, String OAuthConsumerKey, String OAuthConsumerSecret,
//			String OAuthAccessToken, String OAuthAccessTokenSecret) {
//
//		ConfigurationBuilder configurationBuilder = new ConfigurationBuilder();
//		configurationBuilder.setOAuthConsumerKey(OAuthConsumerKey);
//		configurationBuilder.setOAuthConsumerSecret(OAuthConsumerSecret);
//		configurationBuilder.setOAuthAccessToken(OAuthAccessToken);
//		configurationBuilder.setOAuthAccessTokenSecret(OAuthAccessTokenSecret);
//		Configuration configuration = configurationBuilder.build();
//		final Twitter twitter = new TwitterFactory(configuration).getInstance();
//
//		final File file = new File(imageUrl);
//
//		boolean success = true;
//		double x = Math.random();
//		try {
//			
//			if (file.exists()) {
//				StatusUpdate status = new StatusUpdate(message + x);
//				status.setMedia(file);
//				twitter.updateStatus(status);
//			} else {
//				System.out.println("----- Invalid File ----------");
//				success = false;
//			}
//		} catch (Exception e) {
//			e.printStackTrace();
//			success = false;
//		}
//
//		return success;
//	}
//
//}
