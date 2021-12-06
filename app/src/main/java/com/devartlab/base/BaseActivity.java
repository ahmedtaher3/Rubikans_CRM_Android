package com.devartlab.base;

import android.annotation.TargetApi;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.view.inputmethod.InputMethodManager;

import androidx.annotation.LayoutRes;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.databinding.ViewDataBinding;

import com.devartlab.data.shared.DataManager;
import com.devartlab.utils.InternetConnectionDetector;
import com.devartlab.utils.LocaleUtils;

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


}
