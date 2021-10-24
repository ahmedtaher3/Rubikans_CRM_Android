package com.devartlab.ui.main.ui.site;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.MenuItem;
import android.view.View;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.RelativeLayout;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;

import com.devartlab.R;

public class SiteActivity extends AppCompatActivity {


    WebView wb;
    RelativeLayout loading;
    Toolbar toolbar;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_devart);
        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Devart Lab");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);


        loading = (RelativeLayout) findViewById(R.id.loading);
        wb = (WebView) findViewById(R.id.mWebView);
        wb.setWebViewClient(new MyBrowser());

        wb.getSettings().setAppCacheMaxSize(5 * 1024 * 1024);
        wb.getSettings().setAppCachePath(getApplicationContext().getCacheDir().getAbsolutePath());
        wb.getSettings().setAllowFileAccess(true);
        wb.getSettings().setAppCacheEnabled(true);
        wb.getSettings().setJavaScriptEnabled(true);
        wb.getSettings().setCacheMode(WebSettings.LOAD_DEFAULT);

        if (!isNetworkAvailable()) { // loading offline
            wb.getSettings().setCacheMode(WebSettings.LOAD_CACHE_ELSE_NETWORK);
        }

        wb.loadUrl("http://devartlab.com/en-eg/");


    }

    private boolean isNetworkAvailable() {
        ConnectivityManager connectivityManager = (ConnectivityManager) getSystemService(CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnected();
    }

    private class MyBrowser extends WebViewClient {

        @Override
        public void onReceivedError(WebView mWebView, int i, String s, String d1) {
            //    Toasty.error(getApplicationContext(),"No Internet Connection!").show();
            mWebView.loadUrl("file:///android_asset/net_error.html");
        }

        @Override
        public void onPageStarted(WebView view, String url, Bitmap favicon) {
            super.onPageStarted(view, url, favicon);
            loading.setVisibility(View.VISIBLE);

        }

        @Override
        public void onPageFinished(WebView view, final String url) {
            super.onPageFinished(view, url);
            loading.setVisibility(View.GONE);

        }

        @Override
        public boolean shouldOverrideUrlLoading(WebView view, String url) {

            if (url.contains("youtube.com") || url.contains("play.google.com") || url.contains("google.com/maps") || url.contains("facebook.com") || url.contains("twitter.com") || url.contains("instagram.com")) {
                startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(url)));
                return true;
            } else if (url.startsWith("mailto")) {
                handleMailToLink(url);
                return true;
            } else if (url.startsWith("tel:")) {
                startActivity(new Intent(Intent.ACTION_DIAL, Uri.parse(url)));
                return true;
            } else if (url.startsWith("sms:")) {
                // Handle the sms: link
                handleSMSLink(url);

                // Return true means, leave the current web view and handle the url itself
                return true;
            } else if (url.contains("geo:")) {
                Uri gmmIntentUri = Uri.parse(url);
                Intent mapIntent = new Intent(Intent.ACTION_VIEW, gmmIntentUri);
                mapIntent.setPackage("com.google.android.apps.maps");
                if (mapIntent.resolveActivity(getPackageManager()) != null) {
                    startActivity(mapIntent);
                }
                return true;
            }

            view.loadUrl(url);
            view.loadUrl(url);
            return true;
        }
    }

    protected void handleSMSLink(String url) {
        /*
            If you want to ensure that your intent is handled only by a text messaging app (and not
            other email or social apps), then use the ACTION_SENDTO action
            and include the "smsto:" data scheme
        */

        // Initialize a new intent to send sms message
        Intent intent = new Intent(Intent.ACTION_SENDTO);

        // Extract the phoneNumber from sms url
        String phoneNumber = url.split("[:?]")[1];

        if (!TextUtils.isEmpty(phoneNumber)) {
            // Set intent data
            // This ensures only SMS apps respond
            intent.setData(Uri.parse("smsto:" + phoneNumber));

            // Alternate data scheme
            //intent.setData(Uri.parse("sms:" + phoneNumber));
        } else {
            // If the sms link built without phone number
            intent.setData(Uri.parse("smsto:"));

            // Alternate data scheme
            //intent.setData(Uri.parse("sms:" + phoneNumber));
        }


        // Extract the sms body from sms url
        if (url.contains("body=")) {
            String smsBody = url.split("body=")[1];

            // Encode the sms body
            try {
                smsBody = URLDecoder.decode(smsBody, "UTF-8");
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }

            if (!TextUtils.isEmpty(smsBody)) {
                // Set intent body
                intent.putExtra("sms_body", smsBody);
            }
        }

        if (intent.resolveActivity(getPackageManager()) != null) {
            // Start the sms app
            startActivity(intent);
        } else {
            Toast.makeText(getApplicationContext(), "No SMS app found.", Toast.LENGTH_SHORT).show();
        }
    }

    protected void handleMailToLink(String url) {
        // Initialize a new intent which action is send
        Intent intent = new Intent(Intent.ACTION_SENDTO);

        // For only email app handle this intent
        intent.setData(Uri.parse("mailto:"));

        String mString = "";
        // Extract the email address from mailto url
        String to = url.split("[:?]")[1];
        if (!TextUtils.isEmpty(to)) {
            String[] toArray = to.split(";");
            // Put the primary email addresses array into intent
            intent.putExtra(Intent.EXTRA_EMAIL, toArray);
            mString += ("TO : " + to);
        }

        // Extract the cc
        if (url.contains("cc=")) {
            String cc = url.split("cc=")[1];
            if (!TextUtils.isEmpty(cc)) {
                //cc = cc.split("&")[0];
                cc = cc.split("&")[0];
                String[] ccArray = cc.split(";");
                // Put the cc email addresses array into intent
                intent.putExtra(Intent.EXTRA_CC, ccArray);
                mString += ("\nCC : " + cc);
            }
        } else {
            mString += ("\n" + "No CC");
        }

        // Extract the bcc
        if (url.contains("bcc=")) {
            String bcc = url.split("bcc=")[1];
            if (!TextUtils.isEmpty(bcc)) {
                //cc = cc.split("&")[0];
                bcc = bcc.split("&")[0];
                String[] bccArray = bcc.split(";");
                // Put the bcc email addresses array into intent
                intent.putExtra(Intent.EXTRA_BCC, bccArray);
                mString += ("\nBCC : " + bcc);
            }
        } else {
            mString += ("\n" + "No BCC");
        }

        // Extract the subject
        if (url.contains("subject=")) {
            String subject = url.split("subject=")[1];
            if (!TextUtils.isEmpty(subject)) {
                subject = subject.split("&")[0];
                // Encode the subject
                try {
                    subject = URLDecoder.decode(subject, "UTF-8");
                } catch (UnsupportedEncodingException e) {
                    e.printStackTrace();
                }
                // Put the mail subject into intent
                intent.putExtra(Intent.EXTRA_SUBJECT, subject);
                mString += ("\nSUBJECT : " + subject);
            }
        } else {
            mString += ("\n" + "No SUBJECT");
        }

        // Extract the body
        if (url.contains("body=")) {
            String body = url.split("body=")[1];
            if (!TextUtils.isEmpty(body)) {
                body = body.split("&")[0];
                // Encode the body text
                try {
                    body = URLDecoder.decode(body, "UTF-8");
                } catch (UnsupportedEncodingException e) {
                    e.printStackTrace();
                }
                // Put the mail body into intent
                intent.putExtra(Intent.EXTRA_TEXT, body);
                mString += ("\nBODY : " + body);
            }
        } else {
            mString += ("\n" + "No BODY");
        }

        // Email address not null or empty
        if (!TextUtils.isEmpty(to)) {
            if (intent.resolveActivity(getPackageManager()) != null) {
                // Finally, open the mail client activity
                startActivity(intent);
            } else {
                Toast.makeText(getApplicationContext(), "No email client found.", Toast.LENGTH_SHORT).show();
            }
        }

    }




    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
             finish();
                break;
            default:
                return false;
        }
        return true;
    }

}
