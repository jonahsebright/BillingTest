package com.jonahsebright.billingtest.main;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import com.google.android.material.tabs.TabLayout;
import com.jonahsebright.billingtest.R;
import com.jonahsebright.billingtest.showproducts.ProductsFragment;
import com.jonahsebright.billingtest.viewimage.ImageFragment;
import com.jonahsebright.billingtest.viewtext.TextFragment;

import java.util.ArrayList;
import java.util.Arrays;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        setupViewPager();
    }

    private void setupViewPager() {
        ViewPager viewPager = findViewById(R.id.mainViewPager);
        ArrayList<String> pageTitles = new ArrayList<>(Arrays.asList(
                "Products",
                "Text",
                "Image"
        ));
        ArrayList<Fragment> fragments = new ArrayList<>();
        fragments.add(ProductsFragment.newInstance());
        fragments.add(TextFragment.newInstance("Hello :D"));
        fragments.add(ImageFragment.newInstance());
        FragmentPagerAdapter fragmentPagerAdapter = new FragmentPagerAdapter(getSupportFragmentManager(), pageTitles, fragments);
        viewPager.setAdapter(fragmentPagerAdapter);
        TabLayout tabLayout = findViewById(R.id.tablayoutMain);
        tabLayout.setupWithViewPager(viewPager);
    }
}