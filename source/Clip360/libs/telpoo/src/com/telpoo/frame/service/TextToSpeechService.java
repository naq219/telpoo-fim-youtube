package com.telpoo.frame.service;

import java.util.Locale;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.speech.tts.TextToSpeech;
import android.widget.Toast;

public class TextToSpeechService extends Service implements TextToSpeech.OnInitListener {
	public TextToSpeech tts;
	Intent i;
	@Override
	public IBinder onBind(Intent arg0) {
		// TODO Auto-generated method stub
		return null;
	}
	
	@Override
	public void onCreate() {
		// TODO Auto-generated method stub
		super.onCreate();
		tts = new TextToSpeech(getBaseContext(), this);
	}

	@Override
	public int onStartCommand(Intent intent, int flags, int startId) {
		// TODO Auto-generated method stub
		i = intent;
		speak(i.getStringExtra("text"));
		return super.onStartCommand(intent, flags, startId);
	}
	
	
	@Override
	public void onInit(int code) {
		if (code==TextToSpeech.SUCCESS) {
			tts.setLanguage(Locale.UK);
			
			if(i!=null)
			{
				speak(i.getStringExtra("text"));
				
				
			}
			
		} else {
			tts = null;
			Toast.makeText(this, "Failed to initialize TTS engine.", Toast.LENGTH_SHORT).show();
		}
		
	}
	
	private void speak(String str)
	{
		tts.speak(str, TextToSpeech.QUEUE_FLUSH, null);
	}
	@Override
	public void onDestroy() {
		// TODO Auto-generated method stub
		if (tts != null) {
			tts.stop();
			tts.shutdown();
        }
		super.onDestroy();
	}
}
