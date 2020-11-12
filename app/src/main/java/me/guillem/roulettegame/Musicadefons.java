package me.guillem.roulettegame;

import android.app.Service;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.IBinder;

public class Musicadefons extends Service {
        private static final String TAG = null;
        MediaPlayer player;
        public IBinder onBind(Intent arg0) {

            return null;
        }
        @Override
        public void onCreate() {
            super.onCreate();
            player = MediaPlayer.create(this, R.raw.music);
            player.setLooping(true); // Set looping
            player.setVolume(50,50);

        }
        public int onStartCommand(Intent intent, int flags, int startId) {
            player.start();
            return 1;
        }

        public void onStart(Intent intent, int startId) {
            // TO DO
        }
        public IBinder onUnBind(Intent arg0) {
            // TO DO Auto-generated method
            return null;
        }

        public void onStop() {
            player.pause();

        }
        public void onPause() {
            player.pause();

        }
        @Override
        public void onDestroy() {
            player.stop();
            player.release();
        }

        @Override
        public void onLowMemory() {

        }
    }