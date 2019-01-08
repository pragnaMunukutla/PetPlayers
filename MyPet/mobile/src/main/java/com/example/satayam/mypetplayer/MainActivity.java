package com.example.satayam.mypetplayer;

import android.Manifest;
import android.annotation.TargetApi;
import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.media.ThumbnailUtils;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.provider.MediaStore;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.ShareCompat;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.webkit.PermissionRequest;
import android.widget.ListView;
import android.widget.Toast;


import com.karumi.dexter.Dexter;
import com.karumi.dexter.MultiplePermissionsReport;
import com.karumi.dexter.PermissionToken;
import com.karumi.dexter.listener.DexterError;
import com.karumi.dexter.listener.PermissionDeniedResponse;
import com.karumi.dexter.listener.PermissionGrantedResponse;
import com.karumi.dexter.listener.PermissionRequestErrorListener;
import com.karumi.dexter.listener.multi.MultiplePermissionsListener;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    private ArrayList<MediaFileInfo> mediaList = new ArrayList<MediaFileInfo>();
    private RecyclerView mRecyclerView;
    private VideoListAdapter vAdapter;

    private void showToast(int messageId) {
        Toast.makeText(getApplicationContext(), getString(messageId), Toast.LENGTH_LONG).show();
    }

    /**
     * Showing Alert Dialog with Settings option
     * Navigates user to app settings
     * NOTE: Keep proper title and message depending on your app
     */
    private void showSettingsDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
        builder.setTitle("Need Permissions");
        builder.setMessage("This app needs permission to use this feature. You can grant them in app settings.");
        builder.setPositiveButton("GOTO SETTINGS", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
                openSettings();
            }
        });
        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });
        builder.show();
    }

    // navigating user to app settings
    private void openSettings() {
        Intent intent = new Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
        Uri uri = Uri.fromParts("package", getPackageName(), null);
        intent.setData(uri);
        startActivityForResult(intent, 101);
    }

    /**
     * Requesting multiple permissions (storage and location) at once
     * This uses multiple permission model from dexter
     * On permanent denial opens settings dialog
     */
    private void requestPermission() {
        Dexter.withActivity(this)
                .withPermissions(
                        Manifest.permission.READ_EXTERNAL_STORAGE,
                        Manifest.permission.WRITE_EXTERNAL_STORAGE,
                        Manifest.permission.INTERNET)
                .withListener(new MultiplePermissionsListener() {
                    @Override
                    public void onPermissionsChecked(MultiplePermissionsReport report) {
                        // check if all permissions are granted
                        if (report.areAllPermissionsGranted()) {
                            new MediaAsyncTask().execute();
                            //Toast.makeText(getApplicationContext(), "All permissions are granted!", Toast.LENGTH_SHORT).show();
                        }

                        // check for permanent denial of any permission
                        if (report.isAnyPermissionPermanentlyDenied()) {
                            // show alert dialog navigating to Settings
                            showSettingsDialog();
                        } else {
                            List<PermissionDeniedResponse> list = report.getDeniedPermissionResponses();
                            for (int i = 0; i < list.size(); i++) {
                                String pname = list.get(i).getPermissionName();
                                if (pname.equals(Manifest.permission.READ_EXTERNAL_STORAGE)) {
                                    finish();
                                }
                            }
                        }
                    }

                    @Override
                    public void onPermissionRationaleShouldBeShown(List<com.karumi.dexter.listener.PermissionRequest> permissions, PermissionToken token) {
                        token.continuePermissionRequest();
                    }
                }).
                withErrorListener(new PermissionRequestErrorListener() {
                    @Override
                    public void onError(DexterError error) {
                        Toast.makeText(getApplicationContext(), "Error occurred! " + error.toString(), Toast.LENGTH_SHORT).show();
                    }
                })
                .onSameThread()
                .check();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        /* Initialize recyclerview */
        mRecyclerView = (RecyclerView) findViewById(R.id.recycler_view);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        requestPermission();
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions,
                                           @NonNull int[] grantResults) {
        if (grantResults.length == 0) {
            // Empty results are triggered if a permission is requested while another request was already
            // pending and can be safely ignored in this case.
            return;
        }
        if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
            new MediaAsyncTask().execute();
            //initFlavourVideoList();
        } else {
            showToast(R.string.storage_permission_denied);
            finish();
        }
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();
        if (id == R.id.nav_vocab) {
            // Handle the vocab action
            startActivity(new Intent(getApplicationContext(),VocabListActivity.class));
        } else if (id == R.id.nav_share) {
            // Handle the share action
            // TODO(MUVEN) - write the share intent message properly.
            ShareCompat.IntentBuilder.from(this)
                    .setType("text/plain")
                    .setChooserTitle("Share URL")
                    .setText("Download PetPlayer http://play.google.com/store/apps/details?id=" + this.getPackageName())
                    .startChooser();
        } else if (id == R.id.nav_rateus) {
            // Handle the rateus action
        }
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    private void parseAllVideo() {
        try {
            String name = null;
            String[] thumbColumns = {MediaStore.Video.Thumbnails.DATA, MediaStore.Video.Thumbnails.VIDEO_ID};
            String[] proj = {MediaStore.Video.Media._ID,
                    MediaStore.Video.Media.DATA,
                    MediaStore.Video.Media.DISPLAY_NAME,
                    MediaStore.Video.Media.SIZE,
                    MediaStore.Video.Media.DURATION} ;
            Cursor videocursor = getContentResolver().query(MediaStore.Video.Media.EXTERNAL_CONTENT_URI,
                    proj, null, null, null);
            int count = videocursor.getCount();
            Log.d("MuVen No of video", "" + count);
            if(count == 0)
                return;

            for (int i = 0; i < count; i++) {
                MediaFileInfo mediaFileInfo = new MediaFileInfo();
                int video_column_index = videocursor.getColumnIndexOrThrow(MediaStore.Video.Media.DISPLAY_NAME);
                videocursor.moveToPosition(i);
                String title = videocursor.getString(video_column_index);
                mediaFileInfo.setFileName(title);

                video_column_index = videocursor.getColumnIndexOrThrow(MediaStore.Video.Media.DATA);
                videocursor.moveToPosition(i);
                String filepath = videocursor.getString(video_column_index);
                mediaFileInfo.setFilePath(filepath);

                video_column_index = videocursor.getColumnIndexOrThrow(MediaStore.Video.Media.DURATION);
                videocursor.moveToPosition(i);
                String duration = videocursor.getString(video_column_index);
                duration = Util.convertTime(duration);
                mediaFileInfo.setFileDuration(duration);

                Uri uri = Uri.fromFile(new File(filepath));
                Bitmap bmThumbnail = ThumbnailUtils.
                        extractThumbnail(ThumbnailUtils.createVideoThumbnail(filepath,
                                MediaStore.Video.Thumbnails.MINI_KIND), 80, 50);
                if(bmThumbnail != null) {
                    mediaFileInfo.setImageBitmap(bmThumbnail);
                }

                //Log.d("MuVen title ", "" + title + " filepath:"+filepath+ " duration:"+duration);
                mediaList.add(mediaFileInfo);
            }

            videocursor.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public class MediaAsyncTask extends AsyncTask<Void, Void, Integer> {

        @Override
        protected void onPreExecute() {}

        @Override
        protected Integer doInBackground(Void...params) {
            Integer result = 0;
            try {
                mediaList = new ArrayList<MediaFileInfo>();
                parseAllVideo();
                result =1;
            }catch (Exception e) {
                e.printStackTrace();
                result =0;
            }
            return result; //"Failed to fetch data!";
        }

        @Override
        protected void onPostExecute(Integer result) {
            if (result == 1) {
                if (result == 1) {
                    findViewById(R.id.loadingPanel).setVisibility(View.GONE);
                    vAdapter = new VideoListAdapter(MainActivity.this, mediaList);
                    mRecyclerView.setAdapter(vAdapter);
                } else {
                    //Log.e(TAG, "Failed to fetch data!");
                }
            }
        }
    }
}
