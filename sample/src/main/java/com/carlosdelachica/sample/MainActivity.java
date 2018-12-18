package com.carlosdelachica.sample;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import com.carlosdelachica.easyrecycleradapters.sample.R;
import com.carlosdelachica.sample.fragments.CustomViewHolderFactoryFragment;
import com.carlosdelachica.sample.fragments.FullCustomizationEasyRecyclerViewFragment;
import com.carlosdelachica.sample.fragments.MultiViewEasyAdapterFragment;
import com.carlosdelachica.sample.fragments.SimpleViewEasyAdapterFragment;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity {

    @BindView(R.id.toolbar)
    Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        initToolbar();
        if (savedInstanceState == null) {
            navigateToFragment(new SimpleViewEasyAdapterFragment());
        }
    }

    private void navigateToFragment(Fragment fragment) {
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.container, fragment)
                .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN)
                .commit();
    }

    private void initToolbar() {
        if (toolbar != null) {
            setSupportActionBar(toolbar);
            ActionBar supportActionBar = getSupportActionBar();
            assert supportActionBar != null;
            supportActionBar.setDisplayHomeAsUpEnabled(false);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        setTitle(item.getTitle());
        switch (item.getItemId()) {
            case R.id.simpleViewTypeFragment:
                navigateToFragment(new SimpleViewEasyAdapterFragment());
                return true;
            case R.id.multiViewTypeFragment:
                navigateToFragment(new MultiViewEasyAdapterFragment());
                return true;
            case R.id.fullCustomizationFragment:
                navigateToFragment(new FullCustomizationEasyRecyclerViewFragment());
                return true;
            case R.id.customFactoryFragment:
                navigateToFragment(new CustomViewHolderFactoryFragment());
            default:
                return super.onOptionsItemSelected(item);
        }
    }

}
