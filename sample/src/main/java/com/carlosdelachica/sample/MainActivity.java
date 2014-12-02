package com.carlosdelachica.sample;

import android.content.res.ColorStateList;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.RippleDrawable;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

import com.carlosdelachica.easyrecycleradapters.sample.R;

import butterknife.ButterKnife;
import butterknife.InjectView;

public class MainActivity extends ActionBarActivity{

    @InjectView(R.id.toolbar)
    Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.inject(this);
        initToolbar();
        if (savedInstanceState == null) {
            navigateToFragment(new MainFragment());
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
            getSupportActionBar().setDisplayHomeAsUpEnabled(false);
        }
        ColorStateList myColorStateList = new ColorStateList(
                new int[][]{
                        new int[]{android.R.attr.state_pressed}, //1
                        new int[]{android.R.attr.state_focused}, //2
                        new int[]{android.R.attr.state_focused, android.R.attr.state_pressed} //3
                },
                new int[] {
                        Color.RED, //1
                        Color.GREEN, //2
                        Color.BLUE //3
                }
        );
        new RippleDrawable(myColorStateList, null, new ColorDrawable(getResources().getColor(R.color.accentColor)));
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.standalone:
                navigateToFragment(new StandaloneFragment());
                return true;
            case R.id.BaseRecyclerFragment:
                navigateToFragment(new MainFragment());
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

}
