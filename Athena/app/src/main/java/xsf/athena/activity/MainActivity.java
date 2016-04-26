package xsf.athena.activity;

import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.view.MenuItem;

import xsf.athena.R;
import xsf.athena.activity.base.BaseActvity;
import xsf.athena.fragment.StudyFragment;
import xsf.athena.utils.FragmentUtil;
import xsf.athena.utils.LogUtil;


public class MainActivity extends BaseActvity {
    private static String TAG = "MainActvity";
    private static int mSelectMenuIndex = 0;
    private DrawerLayout mDrawerLayout;
    private NavigationView mNavigationView;
    private ActionBarDrawerToggle mActionBarDrawerToggle;
    private FragmentManager mFragmentManager;
    private Fragment mDefaultFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    protected int setLayoutResourceId() {
        return R.layout.activity_main;
    }

    @Override
    protected void init() {
        mFragmentManager = getSupportFragmentManager();
        //TODO 注册双击退出


    }

    @Override
    protected void initView() {
      //  initToolBar();
        setToobarTitle(getString(R.string.navigation_main));

        mDrawerLayout = IfindViewById(R.id.drawer_layout);
        mNavigationView = IfindViewById(R.id.navigation_view);
        mActionBarDrawerToggle = new ActionBarDrawerToggle(this, mDrawerLayout, mToolbar, R.string.drawer_open, R.string.drawer_close);
        mActionBarDrawerToggle.syncState();//该方法会自动和actionBar关联, 将开关的图片显示在了action上，如果不设置，也可以有抽屉的效果，不过是默认的图标
        mDrawerLayout.addDrawerListener(mActionBarDrawerToggle);

        handleNavigationItemClick();
        initDefalueFragment();


    }

    /**
     * 第一个默认加载的fragment
     */
    private void initDefalueFragment() {
        mDefaultFragment = FragmentUtil.createFragment(StudyFragment.class);
        mFragmentManager.beginTransaction().add(R.id.frame_content, mDefaultFragment).commit();
        mNavigationView.getMenu().getItem(0).setChecked(true);
        // TODO: 2016/4/23

    }

    /**
     * 处理侧滑栏
     */
    private void handleNavigationItemClick() {
        mNavigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.nvItem_main:
                        setToobarTitle(getString(R.string.navigation_main));
                        switchFragment(StudyFragment.class);
                        //ToastUtil.showWithImg("点到我啦", Toast.LENGTH_SHORT);
                        break;

                }
                item.setChecked(true);
                mDrawerLayout.closeDrawers();
                return false;//返回值表示该Item是否处于选中状态
            }
        });
    }

    //防止被重复实例化
    private void switchFragment(Class<?> clazz) {
        Fragment switchTo = FragmentUtil.createFragment(clazz);
        if (switchTo.isAdded()) {
            LogUtil.d("already add");
            mFragmentManager.beginTransaction().hide(mDefaultFragment).show(switchTo).commitAllowingStateLoss();
        } else {
            LogUtil.d("not add");
            mFragmentManager.beginTransaction().hide(mDefaultFragment).add(R.id.frame_content, switchTo).commitAllowingStateLoss();
        }
        mDefaultFragment = switchTo;


    }


}

