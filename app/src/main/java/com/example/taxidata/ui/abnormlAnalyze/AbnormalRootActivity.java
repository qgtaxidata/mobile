package com.example.taxidata.ui.abnormlAnalyze;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.ViewPager;

import com.example.taxidata.R;
import com.example.taxidata.base.BaseActivity;
import com.example.taxidata.widget.DisableScrollViewPager;
import com.google.android.material.tabs.TabLayout;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * @author: ODM
 * @date: 2019/8/20
 */
public class AbnormalRootActivity extends BaseActivity {

    private static final String TAG = "AbnormalRootActivity";
    @BindView(R.id.tl_abnormal)
    TabLayout tlAbnormal;
    @BindView(R.id.vp_abnormal)
    DisableScrollViewPager vpAbnormal;
    List<Fragment> fragmentList;
    VpAdapter vpAdapter;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_abnormal_root);
        ButterKnife.bind(this);
        initViews();
    }

    protected void initViews() {
        fragmentList = new ArrayList<>();
        fragmentList.add(new AbnormalAnalyzeFragment());
        fragmentList.add(new AbnormalDistributionFragment());
        vpAdapter = new VpAdapter(getSupportFragmentManager() ,fragmentList);
        vpAbnormal.setAdapter(vpAdapter);
        tlAbnormal.setupWithViewPager(vpAbnormal);
        //手动添加Tab标题 ,必须在setupwidthViewPager之后,否则不行
        if(tlAbnormal != null) {
            tlAbnormal.getTabAt(0).setText("异常分析");
            tlAbnormal.getTabAt(1).setText("异常分布");
        }
    }

    /**
     * view pager adapter
     */
    private static class VpAdapter extends FragmentPagerAdapter {
        private List<Fragment> fragmentList;

        public VpAdapter(FragmentManager fm, List<Fragment> data) {
            super(fm);
            this.fragmentList = data;
        }

        @Override
        public int getCount() {
            return fragmentList.size();
        }

        @Override
        public Fragment getItem(int position) {
            return fragmentList.get(position);
        }
    }
}
