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
import android.widget.Switch;

import androidx.annotation.LayoutRes;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.databinding.ViewDataBinding;

import com.bumptech.glide.Glide;
import com.devartlab.R;
import com.devartlab.data.shared.DataManager;
import com.devartlab.databinding.DialogWelcomePostBinding;
import com.devartlab.ui.main.MainActivity;
import com.devartlab.ui.main.ui.callmanagement.CallManagementActivity;
import com.devartlab.ui.main.ui.callmanagement.employee.EmployeeReportActivity;
import com.devartlab.ui.main.ui.callmanagement.plan.PlanFragment;
import com.devartlab.ui.main.ui.contactlist.ui.main.ContactsActivity;
import com.devartlab.ui.main.ui.devartlink.DevartLinkActivity;
import com.devartlab.ui.main.ui.devartlink.faq.FAQActivity;
import com.devartlab.ui.main.ui.eShopping.main.Home4EShoppingActivity;
import com.devartlab.ui.main.ui.eShopping.orientationVideos.OrientationVideosActivity;
import com.devartlab.ui.main.ui.employeeservices.EmployeeServicesActivity;
import com.devartlab.ui.main.ui.employeeservices.SelfServiceActivity;
import com.devartlab.ui.main.ui.employeeservices.home.SelfServiceHomeFragment;
import com.devartlab.ui.main.ui.market.MarketRequestTypesActivity;
import com.devartlab.utils.InternetConnectionDetector;
import com.devartlab.utils.LocaleUtils;
import com.squareup.picasso.Picasso;

import java.util.Locale;


public abstract class BaseActivity<T extends ViewDataBinding> extends AppCompatActivity implements BaseFragment.Callback {


    private T mViewDataBinding;
    Intent intent;

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

    public void showWelcomePostDialog(String image, String webLink) {
        Dialog dialog = new Dialog(this);
        DialogWelcomePostBinding binding = DataBindingUtil.inflate(dialog.getLayoutInflater(), R.layout.dialog_welcome_post, null, false);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setCancelable(true);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.getWindow().setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.WRAP_CONTENT);
        dialog.setContentView(binding.getRoot());
        Picasso.get()
                .load(image)
                .centerCrop()
                .resize(800, 800)
                .error(android.R.drawable.stat_notify_error)
                .into(binding.ivPost);
        binding.ivCancelDialog.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
        binding.ivPost.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openWebPage(webLink);
            }
        });
        dialog.show();
    }

    public void meuNav(int id, Context context) {
        switch (id) {
            case 1:
                context.startActivity(new Intent(context, MainActivity.class));
                break;
            case 2:
            case 8:
                intent = new Intent(context, CallManagementActivity.class);
                intent.putExtra("pageFragment", "PlanFragment");
                startActivity(intent);
                break;
            case 3:
                intent = new Intent(context, SelfServiceActivity.class);
                intent.putExtra("pageFragment", "SelfServiceHomeFragment");
                startActivity(intent);
                break;
            case 4:
                context.startActivity(new Intent(context, DevartLinkActivity.class));
                break;
            case 5:
                intent = new Intent(context, CallManagementActivity.class);
                intent.putExtra("pageFragment", "HomeFragment");
                startActivity(intent);
                break;
            case 6:
                intent = new Intent(context, CallManagementActivity.class);
                intent.putExtra("pageFragment", "ReportFragment");
                startActivity(intent);
                break;
            case 7:
                context.startActivity(new Intent(context, EmployeeReportActivity.class));
                break;
            case 10:
                context.startActivity(new Intent(context, MarketRequestTypesActivity.class));
                break;
            case 12:
                intent = new Intent(context, EmployeeServicesActivity.class);
                intent.putExtra("pageFragment", "AttendanceFragment");
                startActivity(intent);
                break;
            case 14:
                context.startActivity(new Intent(context, ContactsActivity.class));
                break;
            case 15:
                intent = new Intent(context, CallManagementActivity.class);
                intent.putExtra("pageFragment", "DVReportFragment");
                startActivity(intent);
                break;
            case 16:
                intent = new Intent(context, SelfServiceActivity.class);
                intent.putExtra("pageFragment", "MealsFragment");
                startActivity(intent);
                break;
            case 17:
                intent = new Intent(context, CallManagementActivity.class);
                intent.putExtra("pageFragment", "ListFragment");
                startActivity(intent);
                break;
            case 19:
                intent = new Intent(context, EmployeeServicesActivity.class);
                intent.putExtra("pageFragment", "EmployeeSalaryFragment");
                startActivity(intent);
                break;
            case 20:
                intent = new Intent(context, EmployeeServicesActivity.class);
                intent.putExtra("pageFragment", "ShowAllWorkFromHomeFragment");
                startActivity(intent);
                break;
            case 21:
                intent = new Intent(context, CallManagementActivity.class);
                intent.putExtra("pageFragment", "SyncFragment");
                startActivity(intent);
                break;
            case 22:
                intent = new Intent(context, EmployeeServicesActivity.class);
                intent.putExtra("pageFragment", "WorkFromHomeFragment");
                startActivity(intent);
                break;
            case 23:
                context.startActivity(new Intent(context, Home4EShoppingActivity.class));
                break;
            case 26:
                context.startActivity(new Intent(context, OrientationVideosActivity.class));
                break;
            case 31:
                context.startActivity(new Intent(context, FAQActivity.class));
                break;
        }
    }
}
