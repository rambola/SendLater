package android.rr.sendlater;

import android.Manifest;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.provider.Settings;
import android.rr.sendlater.adapter.MyViewPagerAdapter;
import android.rr.sendlater.presenter.MainActivityPresenter;
import android.rr.sendlater.utils.SendLaterConstants;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.ActivityCompat;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.LinearLayout;
import android.widget.Toast;

import java.util.Objects;

public class MainActivity extends AppCompatActivity implements
        MainActivityPresenter.MainActivityView {

    private MainActivityPresenter mMainActivityPresenter;
    private FloatingActionButton mBaseFab;
    private FloatingActionButton mCreateNewFab;
    private FloatingActionButton mRemainingFab;
    private FloatingActionButton mSentFab;
    private LinearLayout mCreateNewLayout;
    private LinearLayout mRemainingLayout;
    private LinearLayout mSentLayout;
    private ViewPager mViewPager;

    private final String TAG = MainActivity.class.getSimpleName();
    private boolean isFabMenuShown = false;
    private final String[] mPermissionsRequired = {Manifest.permission.READ_CONTACTS,
            Manifest.permission.SEND_SMS};
    private SharedPreferences mPermissionStatus;
    private boolean mSentToSettings = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        boolean areRequiredPermissionsGranted = checkForNeededPermissions();
        mPermissionStatus = getSharedPreferences("mPermissionStatus",MODE_PRIVATE);

        if (areRequiredPermissionsGranted) {
            goAheadWithApp();
        } else { getRequiredPermissions(); }
    }

    private void initViews() {
        mBaseFab = findViewById(R.id.baseFloatingActionButton);
        mCreateNewFab = findViewById(R.id.createNewFab);
        mRemainingFab = findViewById(R.id.remainingFab);
        mSentFab = findViewById(R.id.sentFab);
        mCreateNewLayout = findViewById(R.id.createNewLayout);
        mRemainingLayout = findViewById(R.id.remainingLayout);
        mSentLayout = findViewById(R.id.sentLayout);
        mViewPager = findViewById(R.id.viewpager);
        mViewPager.setAdapter(new MyViewPagerAdapter(getSupportFragmentManager()));
        mViewPager.addOnPageChangeListener(mMainActivityPresenter);
    }


    private boolean checkForNeededPermissions () {
        return (ActivityCompat.checkSelfPermission(MainActivity.this,
                mPermissionsRequired[0]) == PackageManager.PERMISSION_GRANTED &&
                ActivityCompat.checkSelfPermission(MainActivity.this,
                        mPermissionsRequired[1]) == PackageManager.PERMISSION_GRANTED);
    }

    private void getRequiredPermissions () {
        if(ActivityCompat.shouldShowRequestPermissionRationale(MainActivity.this,
                mPermissionsRequired[0]) || ActivityCompat.shouldShowRequestPermissionRationale(
                MainActivity.this, mPermissionsRequired[1])) {
            //Show Information about why you need the permission
            AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
            builder.setTitle("Need Multiple Permissions");
            builder.setMessage("This app needs Contacts and SMS permissions.");
            builder.setPositiveButton("Grant", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    dialog.cancel();
                    ActivityCompat.requestPermissions(MainActivity.this,
                            mPermissionsRequired, SendLaterConstants.REQUEST_PERMISSIONS_CALL_BACK);
                }
            });
            builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    dialog.cancel();
                    finishAffinity();
                }
            });
            builder.show();
        } else if (mPermissionStatus.getBoolean(mPermissionsRequired[0],false)) {
            //Previously Permission Request was cancelled with 'Dont Ask Again',
            // Redirect to Settings after showing Information about why you need the permission
            AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
            builder.setTitle("Need Multiple Permissions");
            builder.setMessage("This app needs Contacts and SMS permissions.");
            builder.setPositiveButton("Grant", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    dialog.cancel();
                    mSentToSettings = true;
                    Intent intent = new Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
                    Uri uri = Uri.fromParts("package", getPackageName(), null);
                    intent.setData(uri);
                    startActivityForResult(intent, SendLaterConstants.REQUEST_PERMISSION_SETTING);
                    Toast.makeText(getBaseContext(), "Go to Permissions to Grant  Contacts and SMS permissions ", Toast.LENGTH_LONG).show();
                }
            });
            builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    dialog.cancel();
                    finishAffinity();
                }
            });
            builder.show();
        }  else {
            //just request the permission
            ActivityCompat.requestPermissions(MainActivity.this, mPermissionsRequired,
                    SendLaterConstants.REQUEST_PERMISSIONS_CALL_BACK);
        }

        SharedPreferences.Editor editor = mPermissionStatus.edit();
        editor.putBoolean(mPermissionsRequired[0], true);
        editor.apply();
    }

    private void goAheadWithApp () {
        mMainActivityPresenter = new MainActivityPresenter(MainActivity.this,
                getApplicationContext());

        initViews();

        mBaseFab.setOnClickListener(mMainActivityPresenter);
        mCreateNewFab.setOnClickListener(mMainActivityPresenter);
        mRemainingFab.setOnClickListener(mMainActivityPresenter);
        mSentFab.setOnClickListener(mMainActivityPresenter);
    }

    public void changeActionBarTitle (String title) {
        Objects.requireNonNull(getSupportActionBar()).setTitle(title);
    }

    @Override
    public void isFabMenuShown(boolean isFabMenuShown) {
        this.isFabMenuShown = isFabMenuShown;
    }

    @Override
    public void closeFabMenu() {
        mMainActivityPresenter.closeFABMenu(
                mBaseFab, mCreateNewLayout, mRemainingLayout, mSentLayout);
    }

    @Override
    public void showFabMenu() {
        mMainActivityPresenter.showFABMenu(
                mBaseFab, mCreateNewLayout, mRemainingLayout, mSentLayout);
    }

    @Override
    public void showThisFragment(int fragmentPos) {
        if (fragmentPos != mViewPager.getCurrentItem())
            mViewPager.setCurrentItem(fragmentPos, true);
        mMainActivityPresenter.closeFABMenu(
                mBaseFab, mCreateNewLayout, mRemainingLayout, mSentLayout);

    }

    @Override
    public void onBackPressed() {
        if (isFabMenuShown)
            mMainActivityPresenter.closeFABMenu(
                    mBaseFab, mCreateNewLayout, mRemainingLayout, mSentLayout);
        else
            super.onBackPressed();
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions,
                                           @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if(requestCode == SendLaterConstants.REQUEST_PERMISSIONS_CALL_BACK) {
            //check if all permissions are granted
            boolean allGranted = false;
            for(int i=0;i<grantResults.length;i++){
                if(grantResults[i]==PackageManager.PERMISSION_GRANTED) {
                    allGranted = true;
                } else {
                    allGranted = false;
                    break;
                }
            }

            if(allGranted){
                goAheadWithApp();
            } else if(ActivityCompat.shouldShowRequestPermissionRationale(MainActivity.this,
                    mPermissionsRequired[0]) || ActivityCompat.shouldShowRequestPermissionRationale(
                            MainActivity.this, mPermissionsRequired[1])) {
                AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
                builder.setTitle("Need Multiple Permissions");
                builder.setMessage("This app needs Contacts and SMS permissions.");
                builder.setPositiveButton("Grant", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                        ActivityCompat.requestPermissions(MainActivity.this,
                                mPermissionsRequired, SendLaterConstants.REQUEST_PERMISSIONS_CALL_BACK);
                    }
                });
                builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                        finishAffinity();
                    }
                });
                builder.show();
            } else {
                Toast.makeText(getBaseContext(),"Required Permissions are not granted.",Toast.LENGTH_LONG).show();
                finishAffinity();
            }
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        Log.e(TAG, "onActivityResult ");
        if (requestCode == SendLaterConstants.REQUEST_PERMISSION_SETTING) {
            if (ActivityCompat.checkSelfPermission(MainActivity.this,
                    mPermissionsRequired[0]) == PackageManager.PERMISSION_GRANTED &&
                    ActivityCompat.checkSelfPermission(MainActivity.this,
                            mPermissionsRequired[1]) == PackageManager.PERMISSION_GRANTED) {
                goAheadWithApp();
            } else  {
                Toast.makeText(getBaseContext(),"Required Permissions are not granted.",Toast.LENGTH_LONG).show();
                finishAffinity();
            }
        }
    }

}