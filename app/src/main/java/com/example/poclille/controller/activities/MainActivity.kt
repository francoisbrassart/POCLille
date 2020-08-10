package com.example.poclille.controller.activities

import android.os.Bundle
import android.view.MenuItem
import android.widget.FrameLayout
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import androidx.fragment.app.Fragment
import com.example.poclille.R
import com.example.poclille.controller.fragments.RoubaixListFragment
import com.example.poclille.controller.fragments.RoubaixRandomFragment
import com.example.poclille.controller.fragments.VLilleListFragment
import com.example.poclille.controller.fragments.VLilleMapFragment
import com.google.android.material.navigation.NavigationView

class MainActivity : AppCompatActivity(), NavigationView.OnNavigationItemSelectedListener {
    private lateinit var mToolbar: Toolbar
    private lateinit var mDrawerLayout: DrawerLayout
    private lateinit var mNavigationView: NavigationView

    private lateinit var mVLilleListFragment: Fragment
    private lateinit var mVLilleMapFragment:Fragment
    private lateinit var mRoubaixListFragment: Fragment
    private lateinit var mRoubaixRandomFragment: Fragment


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        configureToolbar()
        configureDrawerLayout()
        configureNavigationView()
        //get choice from intent (0-> vlille, 1->roubaix frescoes)
        val choice=intent.getIntExtra("choice",0)
        configureFragments(savedInstanceState,choice)
    }

    private fun configureToolbar(){
        mToolbar=findViewById(R.id.activity_main_toolbar)
        setSupportActionBar(mToolbar)
    }

    private fun configureDrawerLayout(){
        mDrawerLayout=findViewById(R.id.activity_main_drawer_layout)
        //configuration of toggle (hamburger icon to open drawer)
        val toggle= ActionBarDrawerToggle(this,mDrawerLayout,mToolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close)
        mDrawerLayout.addDrawerListener(toggle)
        toggle.syncState()
    }

    private fun configureNavigationView() {
        mNavigationView = findViewById(R.id.activity_main_nav_view)
        mNavigationView.setNavigationItemSelectedListener(this)
    }


    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            //Vlille list
            R.id.activity_main_drawer_vlille_list -> {
                if(!this::mVLilleListFragment.isInitialized) mVLilleListFragment=VLilleListFragment()
                showFragment(mVLilleListFragment)
                title="VLille"
            }
            //vlille nearby
            R.id.activity_main_drawer_vlille_map -> {
                if(!this::mVLilleMapFragment.isInitialized) mVLilleMapFragment= VLilleMapFragment()
                showFragment(mVLilleMapFragment)
                title="VLille"
            }
            //roubaix frescoes list
            R.id.activity_main_drawer_roubaix_list -> {
                if(!this::mRoubaixListFragment.isInitialized) mRoubaixListFragment=RoubaixListFragment()
                showFragment(mRoubaixListFragment)
                title="Street Art"
            }
            //roubaix frescoes random
            R.id.activity_main_drawer_roubaix_random -> {
                if(!this::mRoubaixRandomFragment.isInitialized) mRoubaixRandomFragment=RoubaixRandomFragment()
                showFragment(mRoubaixRandomFragment)
                title="Street Art"
            }
            else -> {
            }
        }
        mDrawerLayout.closeDrawer(GravityCompat.START);
        return true
    }

    //configure first fragment to be displayed
    private fun configureFragments(savedInstanceState: Bundle?,choice:Int){
        if (findViewById<FrameLayout>(R.id.activity_main_frame_layout) != null) {
            if (savedInstanceState != null) return;
            if (choice==0){
                mVLilleListFragment=VLilleListFragment()
                supportFragmentManager.beginTransaction().add(R.id.activity_main_frame_layout,mVLilleListFragment).commit()
                title="VLille"
            }
            else if (choice==1){
                mRoubaixListFragment=RoubaixListFragment()
                supportFragmentManager.beginTransaction().add(R.id.activity_main_frame_layout,mRoubaixListFragment).commit()
                title="Street Art"
            }
        }
    }

    //show fragment in parameter
    private fun showFragment(fragment: Fragment){
        supportFragmentManager.beginTransaction().replace(R.id.activity_main_frame_layout,fragment).commit()
    }

}