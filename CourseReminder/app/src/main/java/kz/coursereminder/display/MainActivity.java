package kz.coursereminder.display;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;

import kz.coursereminder.R;
import kz.coursereminder.classes.ViewStatePagerAdapter;

public class MainActivity extends AppCompatActivity {

    /**
     * FragmentStatePagerAdapter and viewpager object
     */
    private ViewStatePagerAdapter mViewStatePagerAdapter;
    private ViewPager mViewPager;

    /**
     * Bottom Navigation bar listener
     */
    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.navigation_home:
                    mViewPager.setCurrentItem(0);
                    setTitle("Home");
                    return true;
                case R.id.navigation_dashboard:
                    mViewPager.setCurrentItem(1);
                    setTitle("Dashboard");
                    return true;
                case R.id.navigation_calendar:
                    setTitle("Calendar");
                    mViewPager.setCurrentItem(2);
                    return true;
                case R.id.navigation_notifications:
                    setTitle("Notifications");
                    mViewPager.setCurrentItem(3);
                    return true;

            }
            return false;
        }
    };

    /**
     * On create method
     *
     * @param savedInstanceState something
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // add fragments to fragment manager
        mViewStatePagerAdapter = new ViewStatePagerAdapter(getSupportFragmentManager());
        mViewPager = findViewById(R.id.page);
        setUpViewPager(mViewPager);

        // set up bottom navigation bar
        BottomNavigationView navigation = findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
        mViewPager.setOffscreenPageLimit(3);
        // set bar to be home
        setTitle("Home");
    }

    /**
     * Sets up the fragments
     *
     * @param mViewPager the viewpager for  fragments
     */
    private void setUpViewPager(ViewPager mViewPager) {
        ViewStatePagerAdapter adapter = new ViewStatePagerAdapter(getSupportFragmentManager());
        adapter.addFragment(new Home());
        adapter.addFragment(new DashBoard());
        adapter.addFragment(new Calendar());
        adapter.addFragment(new Notifications());
        mViewPager.setAdapter(adapter);
    }

    /**
     * Add icons to action bar
     *
     * @param menu the menu
     * @return ?
     */
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_activity_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    /**
     * action bar icon button listener
     * @param item action bar
     * @return selected item
     */
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.search:
                // TODO implement search
                break;
            case R.id.add:
                // TODO implement add course work
                break;
            case R.id.settings:
                // TODO implement settings
                break;
        }
        return super.onOptionsItemSelected(item);
    }

}