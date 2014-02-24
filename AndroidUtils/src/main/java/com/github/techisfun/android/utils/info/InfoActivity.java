package com.github.techisfun.android.utils.info;

import com.github.techisfun.android.utils.R;

import android.app.Activity;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.text.SpannableString;
import android.text.TextUtils;
import android.text.style.BulletSpan;
import android.text.util.Linkify;
import android.util.Log;
import android.view.MenuItem;
import android.widget.TextView;

import java.util.Calendar;

/**
 * Created by Andrea Maglie on 24/02/14.
 */
public class InfoActivity extends Activity {

    private static final String TAG = InfoActivity.class.getCanonicalName();

    private TextView infoAppVersion, infoAppLibs;

    private TextView infoAppAuthor;

    private TextView infoAppAuthorEmail;

    private TextView infoAppLicenses;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.info_activity);
        //getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        //getSupportActionBar().setHomeButtonEnabled(true);

        infoAppVersion = (TextView) findViewById(R.id.info_app_version);
        infoAppLibs = (TextView) findViewById(R.id.info_app_libs);
        infoAppAuthor = (TextView) findViewById(R.id.info_app_author);
        infoAppAuthorEmail = (TextView) findViewById(R.id.info_app_author_email);
        infoAppLicenses = (TextView) findViewById(R.id.info_app_licenses);

        // app version
        try {
            String version = getPackageManager().getPackageInfo(
                    getPackageName(), 0).versionName;
            infoAppVersion.setText("Version " + version);
        } catch (PackageManager.NameNotFoundException e) {
            Log.d(getClass().getSimpleName(), "cannot get version name", e);
        }

        // app author
        CharSequence author = infoAppAuthor.getText();
        int year = Calendar.getInstance().get(Calendar.YEAR);
        infoAppAuthor.setText("\u00A9 " + year + " " + author);

        // author email
        Linkify.addLinks(infoAppAuthorEmail, Linkify.EMAIL_ADDRESSES);

        // library list
        String[] appLibsArray = getResources().getStringArray(
                R.array.app_libs_array);

        CharSequence appLibsList = "";
        for (String value : appLibsArray) {
            SpannableString spannable = new SpannableString(value + "\n");
            spannable.setSpan(new BulletSpan(15), 0, value.length(), 0);
            appLibsList = TextUtils.concat(appLibsList, spannable);
        }

        infoAppLibs.setText(appLibsList);
        Linkify.addLinks(infoAppLibs, Linkify.WEB_URLS);

        // licenses list
        String[] appLicensesArray = getResources().getStringArray(
                R.array.app_licenses_array);

        CharSequence appLicensesList = "";
        for (String value : appLicensesArray) {
            SpannableString spannable = new SpannableString(value + "\n");
            spannable.setSpan(new BulletSpan(15), 0, value.length(), 0);
            appLicensesList = TextUtils.concat(appLicensesList, spannable);
        }

        infoAppLicenses.setText(appLicensesList);
        Linkify.addLinks(infoAppLicenses, Linkify.WEB_URLS);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                return true;
            default:
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    private void getMetaData() {
        try {
            ApplicationInfo ai = getPackageManager().getApplicationInfo(getPackageName(), PackageManager.GET_META_DATA);
            Bundle bundle = ai.metaData;
            String myApiKey = bundle.getString("my_api_key");
        } catch (PackageManager.NameNotFoundException e) {
            Log.e(TAG, "Failed to load meta-data, NameNotFound: " + e.getMessage());
        } catch (NullPointerException e) {
            Log.e(TAG, "Failed to load meta-data, NullPointer: " + e.getMessage());
        }
    }
}
