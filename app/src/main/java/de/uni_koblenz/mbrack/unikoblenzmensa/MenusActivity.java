package de.uni_koblenz.mbrack.unikoblenzmensa;

import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.*;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class MenusActivity extends AppCompatActivity {
    public static final int MONDAY = 0;
    public static final int TUESDAY = 1;
    public static final int WEDNESDAY = 2;
    public static final int THURSDAY = 3;
    public static final int FRIDAY = 4;

    private Toolbar toolbar;
    private TabLayout tabLayout;
    private ViewPager viewPager;

    private MenuItemAdapter mondayMenuItemAdapter;
    private MenuItemAdapter tuesdayMenuItemAdapter;
    private MenuItemAdapter wednesdayMenuItemAdapter;
    private MenuItemAdapter thursdayMenuItemAdapter;
    private MenuItemAdapter fridayMenuItemAdapter;
    public List<MenuItemAdapter> allMenuItemAdapters;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menus);

        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        setupAdapters();

        viewPager = (ViewPager) findViewById(R.id.viewpager);
        setupViewPager(viewPager);

        tabLayout = (TabLayout) findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(viewPager);

        updateMenus();
    }

    private void setupViewPager(ViewPager viewPager) {
        ViewPagerAdapter viewPagerAdapter = new ViewPagerAdapter(getSupportFragmentManager());

        Bundle mondayBundle = new Bundle();
        Bundle tuesdayBundle = new Bundle();
        Bundle wednesdayBundle = new Bundle();
        Bundle thursdayBundle = new Bundle();
        Bundle fridayBundle = new Bundle();

        mondayBundle.putInt("day", MONDAY);
        tuesdayBundle.putInt("day", TUESDAY);
        wednesdayBundle.putInt("day", WEDNESDAY);
        thursdayBundle.putInt("day", THURSDAY);
        fridayBundle.putInt("day", FRIDAY);

        MenuFragment mondayFragment = new MenuFragment();
        MenuFragment tuesdayFragment = new MenuFragment();
        MenuFragment wednesdayFragment = new MenuFragment();
        MenuFragment thursdayFragment = new MenuFragment();
        MenuFragment fridayFragment = new MenuFragment();

        mondayFragment.setArguments(mondayBundle);
        tuesdayFragment.setArguments(tuesdayBundle);
        wednesdayFragment.setArguments(wednesdayBundle);
        thursdayFragment.setArguments(thursdayBundle);
        fridayFragment.setArguments(fridayBundle);


        viewPagerAdapter.addFragment(mondayFragment, "mon");
        viewPagerAdapter.addFragment(tuesdayFragment, "tue");
        viewPagerAdapter.addFragment(wednesdayFragment, "wed");
        viewPagerAdapter.addFragment(thursdayFragment, "thu");
        viewPagerAdapter.addFragment(fridayFragment, "fri");

        viewPager.setAdapter(viewPagerAdapter);

        updateTabSelection();
    }

    private void setupAdapters() {
        mondayMenuItemAdapter = new MenuItemAdapter(this, -1, new ArrayList<MenuItem>());
        tuesdayMenuItemAdapter = new MenuItemAdapter(this, -1, new ArrayList<MenuItem>());
        wednesdayMenuItemAdapter = new MenuItemAdapter(this, -1, new ArrayList<MenuItem>());
        thursdayMenuItemAdapter = new MenuItemAdapter(this, -1, new ArrayList<MenuItem>());
        fridayMenuItemAdapter = new MenuItemAdapter(this, -1, new ArrayList<MenuItem>());

        allMenuItemAdapters = new ArrayList<>();
        allMenuItemAdapters.add(mondayMenuItemAdapter);
        allMenuItemAdapters.add(tuesdayMenuItemAdapter);
        allMenuItemAdapters.add(wednesdayMenuItemAdapter);
        allMenuItemAdapters.add(thursdayMenuItemAdapter);
        allMenuItemAdapters.add(fridayMenuItemAdapter);
    }

    @Override
    public boolean onCreateOptionsMenu(android.view.Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(android.view.MenuItem item) {
        switch (item.getItemId()) {
            case R.id.refresh:
                updateMenus();
                updateTabSelection();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private void updateMenus() {
        System.out.println("Getting menus");
        MenuTask menuTask = new MenuTask(allMenuItemAdapters);
        menuTask.execute();
    }

    private void updateTabSelection() {
        viewPager.setCurrentItem(getCurrentDay());
    }

    private int getCurrentDay() {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(new Date());
        int dayOfWeek = calendar.get(Calendar.DAY_OF_WEEK);

        int fragmentIndex;
        if (dayOfWeek != Calendar.SATURDAY && dayOfWeek != Calendar.SUNDAY) {
            fragmentIndex = dayOfWeek - 2;
        } else {
            fragmentIndex = FRIDAY;
        }

        return fragmentIndex;
    }
}
