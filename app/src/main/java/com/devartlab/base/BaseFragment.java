package com.devartlab.base;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.LayoutRes;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.databinding.ViewDataBinding;
import androidx.fragment.app.Fragment;

import com.devartlab.R;
import com.devartlab.ui.main.MainActivity;
import com.devartlab.ui.main.ui.callmanagement.CallManagementActivity;
import com.devartlab.ui.main.ui.callmanagement.employee.EmployeeReportActivity;
import com.devartlab.ui.main.ui.callmanagement.plan.PlanFragment;
import com.devartlab.ui.main.ui.contactlist.ui.main.ContactsActivity;
import com.devartlab.ui.main.ui.devartlink.DevartLinkActivity;
import com.devartlab.ui.main.ui.eShopping.main.Home4EShoppingActivity;
import com.devartlab.ui.main.ui.eShopping.orientationVideos.OrientationVideosActivity;
import com.devartlab.ui.main.ui.employeeservices.EmployeeServicesActivity;
import com.devartlab.ui.main.ui.employeeservices.SelfServiceActivity;
import com.devartlab.ui.main.ui.market.MarketRequestTypesActivity;
import com.mapbox.mapboxsdk.Mapbox;


public abstract class BaseFragment<T extends ViewDataBinding> extends Fragment {

    private BaseActivity mActivity;
    Intent intent;

    private View mRootView;
    private T mViewDataBinding;

    public abstract
    @LayoutRes
    int getLayoutId();

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof BaseActivity) {
            BaseActivity activity = (BaseActivity) context;
            this.mActivity = activity;
            activity.onFragmentAttached();
        }
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(false);
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        mViewDataBinding = DataBindingUtil.inflate(inflater, getLayoutId(), container, false);
        mRootView = mViewDataBinding.getRoot();
        return mRootView;
    }

    @Override
    public void onDetach() {
        mActivity = null;
        super.onDetach();
    }

    @Override
    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        Mapbox.getInstance(mActivity, getString(R.string.mapbox_access_token));
        mViewDataBinding.setLifecycleOwner(this);
        mViewDataBinding.executePendingBindings();
    }

    public BaseActivity getBaseActivity() {
        return mActivity;
    }

    public T getViewDataBinding() {
        return mViewDataBinding;
    }

    public void hideKeyboard() {
        if (mActivity != null) {
            mActivity.hideKeyboard();
        }
    }

    public boolean isNetworkConnected() {
        return mActivity != null && mActivity.isNetworkConnected();
    }


    public interface Callback {

        void onFragmentAttached();

        void onFragmentDetached(String tag);
    }
    public void openWebPage(String url) {
        Uri uri = Uri.parse(url);
        Intent intent = new Intent(Intent.ACTION_VIEW, uri);
        mActivity.startActivity(intent);
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
                intent.putExtra("PAGE_NUMBER", "4");
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
        }
    }
}