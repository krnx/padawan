package com.example.krnx.padawan;

import android.Manifest;
import android.app.Activity;
import android.content.ContentProviderOperation;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.content.OperationApplicationException;
import android.content.pm.PackageManager;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.os.RemoteException;
import android.provider.ContactsContract;
import android.provider.MediaStore;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

/**
 * Created by arnau on 05/07/16.
 */
public class PlayerActivity extends BaseActivity {
    private MediaPlayer mediaPlayer = new MediaPlayer();
    final private int REQUEST_CODE_EXTERNAL_STORAGE = 123;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_player);
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                searchSong();
            }
        });
    }
    private void searchSong(){
//        Intent file = new Intent(Intent.ACTION_PICK, MediaStore.Audio.Media.EXTERNAL_CONTENT_URI);
        Intent file = new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Audio.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(file, REQUEST_CODE_EXTERNAL_STORAGE);
    }

    /*private void checkStoragePermissions() {
        int hasStoragePermissions = ActivityCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE);
        if (hasStoragePermissions != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[] {Manifest.permission.WRITE_EXTERNAL_STORAGE},
                    REQUEST_CODE_EXTERNAL_STORAGE);
            return;
        }
        searchSong();
    }*/

    /*@Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        switch (requestCode) {
            case REQUEST_CODE_EXTERNAL_STORAGE:
                if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    // Permission Granted
                    searchSong();
                } else {
                    // Permission Denied
                    Toast.makeText(PlayerActivity.this, "WRITE_STORAGE Denied", Toast.LENGTH_SHORT)
                            .show();
                }
                break;
            default:
                super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        }
    }*/

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        // Si la petición se hizo correctamente y requestCode es REQUEST_CODE_EXTERNAL_STORAGE
        if (resultCode == Activity.RESULT_OK && requestCode == REQUEST_CODE_EXTERNAL_STORAGE) {
            Uri uriSound = data.getData();
//            String path = uriSound.getPath();
            mediaPlayer = new MediaPlayer();
            try {
                Log.v("Player", "setDataSource amb URI: " + uriSound.toString());
                mediaPlayer.reset();
                mediaPlayer.setDataSource(this, uriSound);
//                mediaPlayer.prepare();
            } catch (IOException e) {
                Toast.makeText(getApplicationContext(), "Ha fallat IO: " + e.getMessage(), Toast.LENGTH_LONG).show();
                Log.v("Player", "Ha fallat IO: " + e.getMessage());
                e.printStackTrace();
            }
            mediaPlayer.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
                public void onPrepared(MediaPlayer mp) {
                    Toast.makeText(getApplicationContext(), "Comença!", Toast.LENGTH_SHORT).show();
                    mediaPlayer.start();
                }
            });
            mediaPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
                public void onCompletion(MediaPlayer mp) {
                    Toast.makeText(getApplicationContext(), "This is the end!", Toast.LENGTH_SHORT).show();
                    mediaPlayer.seekTo(0);
                    //finish();
                }
            });
            mediaPlayer.prepareAsync();

//            mediaPlayer.pause();
//            mediaPlayer.stop();
//            mediaPlayer.release();
        } else
            Toast.makeText(this, "File Failed", Toast.LENGTH_LONG).show();
    }

    private void releaseMediaPlayer() {
        if (mediaPlayer != null) {
            mediaPlayer.release();
            mediaPlayer = null;
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        releaseMediaPlayer();
    }

    @Override
    protected void onPause() {
        super.onPause();
        if (mediaPlayer.getAudioSessionId() != 0) {
            mediaPlayer.pause();
            if (isFinishing()) {
                mediaPlayer.stop();
                mediaPlayer.release();
            }
        }
    }

}
