package com.devartlab.base;

import android.annotation.TargetApi;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;

import androidx.annotation.LayoutRes;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.databinding.ViewDataBinding;

import com.bumptech.glide.Glide;
import com.devartlab.R;
import com.devartlab.data.shared.DataManager;
import com.devartlab.databinding.DialogWelcomePostBinding;
import com.devartlab.utils.InternetConnectionDetector;
import com.devartlab.utils.LocaleUtils;
import com.squareup.picasso.Picasso;

import java.util.Locale;


public abstract class BaseActivity <T extends ViewDataBinding> extends AppCompatActivity implements BaseFragment.Callback {


    private T mViewDataBinding;


    /**
     * @return layout resource id
     */
    public abstract
    @LayoutRes
    int getLayoutId();

    /**
     * Override for set view model
     *
     * @return view model instance
     */


    @Override
    public void onFragmentAttached() {

    }


    @Override
    public void onFragmentDetached(String tag) {

    }

    public BaseActivity() {
        LocaleUtils.updateConfig(this);
    }


    private DataManager dataManager;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        performDataBinding();

        dataManager = ((BaseApplication) getApplication()).getDataManager();

        if (dataManager.getLang() != null) {
            if (dataManager.getLang().equals("ar")) {
                LocaleUtils.setLocale(new Locale("ar"));
                LocaleUtils.updateConfig(getApplication(), getBaseContext().getResources().getConfiguration());

            } else {
                LocaleUtils.setLocale(new Locale("en"));
                LocaleUtils.updateConfig(getApplication(), getBaseContext().getResources().getConfiguration());
            }
        }


    }

    public T getViewDataBinding() {
        return mViewDataBinding;
    }

    public void hideKeyboard() {
        View view = this.getCurrentFocus();
        if (view != null) {
            InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
            if (imm != null) {
                imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
            }
        }
    }

    @TargetApi(Build.VERSION_CODES.M)
    public boolean hasPermission(String permission) {
        return Build.VERSION.SDK_INT < Build.VERSION_CODES.M ||
                checkSelfPermission(permission) == PackageManager.PERMISSION_GRANTED;
    }


    public boolean isNetworkConnected() {
        return InternetConnectionDetector.IsInternetAvailable(getApplicationContext());
    }

    @TargetApi(Build.VERSION_CODES.M)
    public void requestPermissionsSafely(String[] permissions, int requestCode) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            requestPermissions(permissions, requestCode);
        }
    }



    private void performDataBinding() {
        mViewDataBinding = DataBindingUtil.setContentView(this, getLayoutId());
        mViewDataBinding.executePendingBindings();
    }

    public void openWebPage(String url) {

        Uri uri = Uri.parse(url);
        Intent intent = new Intent(Intent.ACTION_VIEW, uri);
        startActivity(intent);
    }

    public void showWelcomePostDialog(String image) {
        Dialog dialog = new Dialog(this);
        DialogWelcomePostBinding binding = DataBindingUtil.inflate(dialog.getLayoutInflater(), R.layout.dialog_welcome_post, null, false);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setCancelable(false);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.getWindow().setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.WRAP_CONTENT);
        dialog.setContentView(binding.getRoot());
        Picasso.get()
                .load("https://t4e.4eshopping.com"+image)
                .centerCrop()
                .resize(1024, 1024)
                .error(android.R.drawable.stat_notify_error)
                .into(binding.ivPost);
        binding.ivCancelDialog.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
        dialog.show();
    }
}
