package com.example.krnx.padawan;

import android.Manifest;
import android.app.Activity;
import android.content.ContentProviderOperation;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.content.OperationApplicationException;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.drawable.Drawable;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
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
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.material.joanbarroso.flipper.CoolImageFlipper;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;

/**
 * Created by arnau on 05/07/16.
 */
public class PlayerActivity extends BaseActivity implements View.OnClickListener {
    private MediaPlayer mediaPlayer = new MediaPlayer();
    final private int REQUEST_CODE_EXTERNAL_STORAGE = 123;

    private CoolImageFlipper imatge;

    private Boolean isPrepared = false;

    private String path;
    private Uri uriSound;
    private TextView player_pantalla;
    private ImageView player_previous;
    private ImageView player_rew;
    private ImageView player_play_pause;
    private ImageView player_ff;
    private FloatingActionButton player_load;

    private SeekBar seekbar;
    private double startTime = 0;
    private double finalTime = 0;
    private Handler myHandler = new Handler();
    public static int oneTimeOnly = 0;

    @Override
    public void onClick(View view) {
        playPauseSong(view);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_player);

        imatge = new CoolImageFlipper(getApplicationContext());

        seekbar = (SeekBar) findViewById(R.id.seekBar);
        seekbar.setClickable(false);

        player_pantalla = (TextView) findViewById(R.id.player_pantalla);
        player_previous = (ImageView) findViewById(R.id.player_previous);
        player_rew = (ImageView) findViewById(R.id.player_rew);
        player_play_pause = (ImageView) findViewById(R.id.player_play_pause);
        player_ff = (ImageView) findViewById(R.id.player_ff);
        player_load = (FloatingActionButton) findViewById(R.id.player_load);

        player_previous.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                previousSong();
            }
        });
        player_rew.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                rewSong();
            }
        });

        player_play_pause.setOnClickListener(this);

        /*player_play_pause.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                playPauseSong(view);
            }
        });*/

        player_ff.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ffSong();
            }
        });
        player_load.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                searchSong();
            }
        });
    }

    private void previousSong() {
        mediaPlayer.seekTo(0);
    }

    private void rewSong() {
        if (mediaPlayer.getCurrentPosition() - 5000 > 0)
            mediaPlayer.seekTo(mediaPlayer.getCurrentPosition() - 5000);
        else
            mediaPlayer.seekTo(0);
    }

    private void playPauseSong(View view) {
        if (isPrepared) {
            if (mediaPlayer.isPlaying()) {
                imatge.flipImage(getDrawable(android.R.drawable.ic_media_play), (ImageView) view);
                mediaPlayer.pause();
            } else {
                imatge.flipImage(getDrawable(android.R.drawable.ic_media_pause), (ImageView) view);
                mediaPlayer.start();

                finalTime = mediaPlayer.getDuration();
                startTime = mediaPlayer.getCurrentPosition();

                if (oneTimeOnly == 0) {
                    seekbar.setMax((int) finalTime);
                    oneTimeOnly = 1;
                }
                /*tx2.setText(String.format("%d min, %d sec",
                        TimeUnit.MILLISECONDS.toMinutes((long) finalTime),
                        TimeUnit.MILLISECONDS.toSeconds((long) finalTime) -
                                TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes((long) finalTime)))
                );*/

                /*tx1.setText(String.format("%d min, %d sec",
                        TimeUnit.MILLISECONDS.toMinutes((long) startTime),
                        TimeUnit.MILLISECONDS.toSeconds((long) startTime) -
                                TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes((long) startTime)))
                );*/

                seekbar.setProgress((int) startTime);
                myHandler.postDelayed(UpdateSongTime, 100);

            }
        } else {
            searchSong();
        }
    }

    private void searchSong() {

        if (isPrepared) {
            mediaPlayer.stop();
            mediaPlayer.release();
            mediaPlayer = null;
        } else {
            Intent file = new Intent(Intent.ACTION_PICK, MediaStore.Audio.Media.EXTERNAL_CONTENT_URI);
//            Intent file = new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Audio.Media.EXTERNAL_CONTENT_URI);
            startActivityForResult(file, REQUEST_CODE_EXTERNAL_STORAGE);
        }
    }

    private void ffSong() {
        if (mediaPlayer.getCurrentPosition() + 5000 < mediaPlayer.getDuration())
            mediaPlayer.seekTo(mediaPlayer.getCurrentPosition() + 5000);
        else
            mediaPlayer.stop();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        // Si la petición se hizo correctamente y requestCode es REQUEST_CODE_EXTERNAL_STORAGE
        if (resultCode == Activity.RESULT_OK && requestCode == REQUEST_CODE_EXTERNAL_STORAGE) {
            uriSound = data.getData();
            path = uriSound.getPath();
            mediaPlayer = new MediaPlayer();
            try {
                Log.v("Player", "setDataSource amb URI: " + uriSound.toString());
                mediaPlayer.reset();
                mediaPlayer.setDataSource(this, uriSound);
//                mediaPlayer.prepare();
            } catch (IOException e) {
                Log.v("Player", "Ha fallat IO: " + e.getMessage());
                e.printStackTrace();
            }
            mediaPlayer.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
                public void onPrepared(MediaPlayer mp) {
                    Toast.makeText(getApplicationContext(), R.string.playback_start, Toast.LENGTH_SHORT).show();
                    player_pantalla.setText(getRealPathFromURI(getApplicationContext(), uriSound));
                    isPrepared = true;
                }
            });
            mediaPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
                public void onCompletion(MediaPlayer mp) {
                    Toast.makeText(getApplicationContext(), R.string.playback_end, Toast.LENGTH_SHORT).show();
                    mediaPlayer.seekTo(0);
                }
            });
            mediaPlayer.prepareAsync();


//            mediaPlayer.release();
        } else
            Toast.makeText(this, R.string.playback_error, Toast.LENGTH_LONG).show();
    }

    private Runnable UpdateSongTime = new Runnable() {
        public void run() {
            startTime = mediaPlayer.getCurrentPosition();
//            tx1.setText(String.format("%d min, %d sec", TimeUnit.MILLISECONDS.toMinutes((long) startTime), TimeUnit.MILLISECONDS.toSeconds((long) startTime) - TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS. toMinutes((long) startTime))));
            seekbar.setProgress((int) startTime);
            myHandler.postDelayed(this, 100);
        }
    };

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


    public String getRealPathFromURI(Context context, Uri contentUri) {
        Cursor cursor = null;
        try {
            String[] proj = {MediaStore.Images.Media.DATA};
            cursor = context.getContentResolver().query(contentUri, proj, null, null, null);
            int column_index = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
            cursor.moveToFirst();
            return cursor.getString(column_index);
        } finally {
            if (cursor != null) {
                cursor.close();
            }
        }
    }


}
