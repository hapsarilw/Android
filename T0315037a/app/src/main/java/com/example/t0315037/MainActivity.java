package com.example.t0315037;

import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.os.Bundle;

public class MainActivity extends AppCompatActivity implements FragmentListener {

    protected FragmentManager fragmentManager;
    protected MainFragment mainFragment;
    protected SecondFragment secondFragment;
    protected ResultDialogFragment resultDialogFragment;
    protected androidx.appcompat.widget.Toolbar toolbar;
    protected DrawerLayout drawer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        this.mainFragment = MainFragment.newInstance();
        this.secondFragment = SecondFragment.newInstance();
        this.fragmentManager = this.getSupportFragmentManager();

        this.toolbar = this.findViewById(R.id.toolbar);
        this.setSupportActionBar(toolbar);

        this.drawer = this.findViewById(R.id.drawer_layout);

        ActionBarDrawerToggle abdt = new ActionBarDrawerToggle(this, drawer, toolbar, R.string.openDrawer, R.string.closeDrawer);
        drawer.addDrawerListener(abdt);
        abdt.syncState();

        FragmentTransaction ft = this.fragmentManager.beginTransaction();


        ft.add(R.id.fragment_container, this.mainFragment).commit();
    }

    @Override
    public void changePage(int page) {
        FragmentTransaction ft = this.fragmentManager.beginTransaction();
        if (page == 1) {
            if (this.mainFragment.isAdded()) {
                ft.show(this.mainFragment);
                this.drawer.closeDrawers();
            } else {
                ft.add(R.id.fragment_container, this.mainFragment);
                this.drawer.closeDrawers();
            }
            if (this.secondFragment.isAdded()) {
                ft.hide(this.secondFragment);
            }
        } else if (page == 2) {
            if (this.secondFragment.isAdded()) {
                ft.show(this.secondFragment);
                this.drawer.closeDrawers();
            } else {
                ft.add(R.id.fragment_container, this.secondFragment).addToBackStack(null);
                this.drawer.closeDrawers();
            }
            if (this.mainFragment.isAdded()) {
                ft.hide(this.mainFragment);
            }
        }
        ft.commit();
    }

    @Override
    public void setText() {
        this.resultDialogFragment = ResultDialogFragment.newInstance(mainFragment.getText());
        this.resultDialogFragment.show(fragmentManager, "dialog");
    }

    @Override
    public void closeApplication() {
        this.moveTaskToBack(true);
        this.finish();
    }
}
