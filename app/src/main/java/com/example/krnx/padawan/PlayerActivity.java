package com.example.krnx.padawan;

import android.Manifest;
import android.app.Activity;
import android.content.ContentProviderOperation;
import android.content.ContentResolver;
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
    //private static final int MY_PERMISSIONS_REQUEST_WRITE_CONTACTS = 1;
    private static final int MY_PERMISSIONS_REQUEST_WRITE_CONTACTS = 123;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_player);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                insertDummyContactWrapper();
                /* int permissionCheck = ContextCompat.checkSelfPermission(PlayerActivity.this, Manifest.permission.CAMERA);

                Log.v("Player", "Check: " + permissionCheck);
                Log.v("Player", "Rationel: " + ActivityCompat.shouldShowRequestPermissionRationale(PlayerActivity.this, Manifest.permission.WRITE_CONTACTS));
                ActivityCompat.requestPermissions(PlayerActivity.this, new String[]{Manifest.permission.CAMERA}, MY_PERMISSIONS_REQUEST_WRITE_CONTACTS);
*/
                /*
                Log.v("Player", "Check: " + permissionCheck);
                Log.v("Player", "Rationel: " + ActivityCompat.shouldShowRequestPermissionRationale(PlayerActivity.this, Manifest.permission.WRITE_CONTACTS));
                ActivityCompat.requestPermissions(PlayerActivity.this, new String[]{Manifest.permission.WRITE_CONTACTS}, MY_PERMISSIONS_REQUEST_WRITE_CONTACTS);


                if (permissionCheck != PackageManager.PERMISSION_GRANTED) {
                    if (ActivityCompat.shouldShowRequestPermissionRationale(PlayerActivity.this, Manifest.permission.WRITE_CONTACTS)) {
                        Snackbar.make(findViewById(R.id.player_layout), "Location access is required to show coffee shops nearby.", Snackbar.LENGTH_LONG)
                                .setAction("OK", new View.OnClickListener() {
                                    @Override
                                    public void onClick(View view) {
                                        ActivityCompat.requestPermissions(PlayerActivity.this, new String[]{Manifest.permission.WRITE_CONTACTS}, MY_PERMISSIONS_REQUEST_WRITE_CONTACTS);
                                    }
                                })
                                .show();
//                                v => ActivityCompat.requestPermissions(this, Manifest.permission.READ_PHONE_STATE));


//                showExplanation("Permission Needed", "Rationale", Manifest.permission.READ_PHONE_STATE, 1);
//                Toast.makeText(this, "Need permission", Toast.LENGTH_LONG).show();
                    } else {
                        //requestPermission(Manifest.permission.READ_PHONE_STATE, 1);
//                Toast.makeText(this, "Need permission2", Toast.LENGTH_LONG).show();
                        Snackbar.make(findViewById(R.id.player_layout), "Permisos sense motiu.", Snackbar.LENGTH_LONG).
                                setAction("OK", new View.OnClickListener() {
                                    @Override
                                    public void onClick(View view) {
                                        ActivityCompat.requestPermissions(PlayerActivity.this, new String[]{Manifest.permission.WRITE_CONTACTS}, MY_PERMISSIONS_REQUEST_WRITE_CONTACTS);
                                    }
                                }).show();
                    }
                } else {
                    Toast.makeText(PlayerActivity.this, "Permission (already) Granted!", Toast.LENGTH_SHORT).show();
                }*/
            }
        });


//                ActivityCompat.requestPermissions(PlayerActivity.this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, 0);







            /*if (ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
                // Should we show an explanation?
                if (ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.READ_EXTERNAL_STORAGE)) {
                    // Show an expanation to the user *asynchronously* -- don't block
                    // this thread waiting for the user's response! After the user
                    // sees the explanation, try again to request the permission.
                    Toast.makeText(this, "Need permission", Toast.LENGTH_LONG).show();
                } else {
                    ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, 0);
                }
            }*/
    }
    final private int REQUEST_CODE_ASK_PERMISSIONS = 123;

    private void insertDummyContactWrapper() {
        int hasWriteContactsPermission = checkSelfPermission(Manifest.permission.WRITE_CONTACTS);
        if (hasWriteContactsPermission != PackageManager.PERMISSION_GRANTED) {
            requestPermissions(new String[] {Manifest.permission.WRITE_CONTACTS},
                    REQUEST_CODE_ASK_PERMISSIONS);
            return;
        }
        insertDummyContact();
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String permissions[], int[] grantResults) {
        switch (requestCode) {
            case REQUEST_CODE_ASK_PERMISSIONS:
                if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    // Permission Granted
                    insertDummyContact();
                } else {
                    // Permission Denied
                    Toast.makeText(PlayerActivity.this, "WRITE_CONTACTS Denied", Toast.LENGTH_SHORT)
                            .show();
                }
                break;
            default:
                super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        }

        /*switch (requestCode) {
            case 0:
                Toast.makeText(this, "RC: " + grantResults[0], Toast.LENGTH_LONG).show();
                // If request is cancelled, the result arrays are empty.
                if ((grantResults.length > 0) && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    Intent file = new Intent(Intent.ACTION_PICK, MediaStore.Audio.Media.EXTERNAL_CONTENT_URI);
//                    file.setType("file*//*");
                    startActivityForResult(file, 0);
                } else {
                    Toast.makeText(this, "Nop!", Toast.LENGTH_LONG).show();
                }
                return;
            case 123:
                Toast.makeText(getApplicationContext(), String.valueOf(grantResults[0]), Toast.LENGTH_SHORT).show();
                if ((grantResults.length > 0) && (grantResults[0] == PackageManager.PERMISSION_GRANTED)) {
                    Toast.makeText(PlayerActivity.this, "Permission Granted!", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(PlayerActivity.this, "Permission Denied!", Toast.LENGTH_SHORT).show();
                }
                break;

        }*/
    }
    private static final String TAG = "Contacts";
    private void insertDummyContact() {
        // Two operations are needed to insert a new contact.
        ArrayList<ContentProviderOperation> operations = new ArrayList<ContentProviderOperation>(2);

        // First, set up a new raw contact.
        ContentProviderOperation.Builder op =
                ContentProviderOperation.newInsert(ContactsContract.RawContacts.CONTENT_URI)
                        .withValue(ContactsContract.RawContacts.ACCOUNT_TYPE, null)
                        .withValue(ContactsContract.RawContacts.ACCOUNT_NAME, null);
        operations.add(op.build());

        // Next, set the name for the contact.
        op = ContentProviderOperation.newInsert(ContactsContract.Data.CONTENT_URI)
                .withValueBackReference(ContactsContract.Data.RAW_CONTACT_ID, 0)
                .withValue(ContactsContract.Data.MIMETYPE,
                        ContactsContract.CommonDataKinds.StructuredName.CONTENT_ITEM_TYPE)
                .withValue(ContactsContract.CommonDataKinds.StructuredName.DISPLAY_NAME,
                        "__DUMMY CONTACT from runtime permissions sample");
        operations.add(op.build());

        // Apply the operations.
        ContentResolver resolver = getContentResolver();
        try {
            resolver.applyBatch(ContactsContract.AUTHORITY, operations);
        } catch (RemoteException e) {
            Log.d(TAG, "Could not add a new contact: " + e.getMessage());
        } catch (OperationApplicationException e) {
            Log.d(TAG, "Could not add a new contact: " + e.getMessage());
        }
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        // Si la petición se hizo correctamente y requestCode es 0
        if (resultCode == Activity.RESULT_OK && requestCode == 0) {
//            Toast.makeText(this, "File Ok", Toast.LENGTH_LONG).show();
            Uri uri = data.getData();
//            Toast.makeText(this, "File:  " + uri.toString(), Toast.LENGTH_LONG).show();
            File song = new File(uri.getPath());

//            File sdCard = Environment.getExternalStorageDirectory();
//            File song = new File(sdCard.getAbsolutePath() + "/Downloads/spinningmerkaba_-_Summertime_Featuring_JeffSpeed68.mp3");

            mediaPlayer = new MediaPlayer();
            try {
                Log.v("Player", song.getAbsolutePath());
//                Toast.makeText(this, "File:  " + song.getAbsolutePath().toString(), Toast.LENGTH_LONG).show();
                mediaPlayer.reset();
                mediaPlayer.setDataSource(this, uri);
            } catch (IOException e) {
                Toast.makeText(getApplicationContext(), "Ha fallat IO: " + e.getMessage(), Toast.LENGTH_LONG).show();
                Log.v("Player", "Ha fallat IO: " + e.getMessage());
            }
//                mediaPlayer.prepare();
            mediaPlayer.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
                public void onPrepared(MediaPlayer mp) {
                    Toast.makeText(getApplicationContext(), "Comença!", Toast.LENGTH_SHORT).show();
                    mediaPlayer.start();
                }
            });
            mediaPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
                public void onCompletion(MediaPlayer mp) {
                    Toast.makeText(getApplicationContext(), "This is the end!", Toast.LENGTH_SHORT).show();
                    //finish();
                }
            });
            mediaPlayer.prepareAsync();

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
