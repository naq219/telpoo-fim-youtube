package com.telpoo.clip360.utils;

import java.io.File;

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.graphics.Bitmap.CompressFormat;
import android.widget.ImageView;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.nostra13.universalimageloader.cache.disc.impl.UnlimitedDiscCache;
import com.nostra13.universalimageloader.cache.disc.naming.HashCodeFileNameGenerator;
import com.nostra13.universalimageloader.cache.memory.impl.UsingFreqLimitedMemoryCache;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.download.BaseImageDownloader;
import com.nostra13.universalimageloader.utils.StorageUtils;
import com.telpoo.clip360.R;
import com.telpoo.frame.utils.ViewUtils;

public class Utils1 {
	public static void hilightButton(ImageView[] imageViews){
		
		ViewUtils.HighlightImageView(imageViews, Color.argb(200, 42, 18, 191));
	}
	
	public static void loadAd(Activity homeActivity){
		AdView adView=(AdView) homeActivity.findViewById(R.id.adView);
		AdRequest adRequest=new AdRequest.Builder().build();
		adView.loadAd(adRequest);
	}
	
	
	
	public static ImageLoaderConfiguration imageLoaderCf(int chance, Context context) {
		DisplayImageOptions defaultOptions = null;
		switch (chance) {
		case 0: // if no photo
			defaultOptions = new DisplayImageOptions.Builder().cacheInMemory(true).cacheOnDisc(true).showImageOnFail(R.drawable.defaultitem)
			.build();

			break;

		case 1:// no catche
			defaultOptions = new DisplayImageOptions.Builder().cacheInMemory(true).cacheOnDisc(true).showImageOnFail(R.drawable.ic_launcher)

			.build();

			break;

		}

		File cacheDir = StorageUtils.getCacheDirectory(context);

		ImageLoaderConfiguration config = new ImageLoaderConfiguration.Builder(context)
				.memoryCacheExtraOptions(480, 480)
				// width, height
				.discCacheExtraOptions(480, 480, CompressFormat.JPEG, 75, null)
				// .discCacheExtraOptions(480, 800, CompressFormat.JPEG, 75) //
				// width, height, compress format, quality
				.threadPoolSize(5).threadPriority(Thread.MIN_PRIORITY + 2).denyCacheImageMultipleSizesInMemory().memoryCache(new UsingFreqLimitedMemoryCache(2 * 1024 * 1024))
				// 2 Mb
				.discCache(new UnlimitedDiscCache(cacheDir)).discCacheFileNameGenerator(new HashCodeFileNameGenerator()).imageDownloader(new BaseImageDownloader(context))
				.defaultDisplayImageOptions(defaultOptions).build();

		return config;

	}

}
