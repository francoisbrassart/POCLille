package com.example.poclille.controller.activities

import android.Manifest
import android.content.pm.PackageManager
import android.os.Bundle
import android.view.MenuItem
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.core.view.GravityCompat
import androidx.fragment.app.Fragment
import com.example.poclille.R
import com.example.poclille.controller.fragments.RoubaixListFragment
import com.example.poclille.controller.fragments.RoubaixRandomFragment
import com.example.poclille.controller.fragments.VLilleListFragment
import com.example.poclille.controller.fragments.VLilleMapFragment
import com.google.android.material.navigation.NavigationView
import kotlinx.android.synthetic.main.activity_main.*


class MainActivity : AppCompatActivity(), NavigationView.OnNavigationItemSelectedListener {
    private lateinit var mVLilleListFragment: Fragment
    private lateinit var mVLilleMapFragment:Fragment
    private lateinit var mRoubaixListFragment: Fragment
    private lateinit var mRoubaixRandomFragment: Fragment
    public val MY_PERMISSIONS_REQUEST_LOCATION:Int=99


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
        setSupportActionBar(ActivityMainToolbar)
    }

    private fun configureDrawerLayout(){
        //configuration of toggle (hamburger icon to open drawer)
        val toggle= ActionBarDrawerToggle(this,ActivityMainDrawerLayout,ActivityMainToolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close)
        ActivityMainDrawerLayout.addDrawerListener(toggle)
        toggle.syncState()
    }

    private fun configureNavigationView() {
        ActivityMainNavView.setNavigationItemSelectedListener(this)
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
                if(checkLocationPermission(MY_PERMISSIONS_REQUEST_LOCATION)){
                goToVlilleMap()}
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
        ActivityMainDrawerLayout.closeDrawer(GravityCompat.START);
        return true
    }

    //configure first fragment to be displayed
    private fun configureFragments(savedInstanceState: Bundle?,choice:Int){
        if (ActivityMainFrameLayout!= null) {
            if (savedInstanceState != null) return;
            if (choice==0){
                mVLilleListFragment=VLilleListFragment()
                supportFragmentManager.beginTransaction().add(R.id.ActivityMainFrameLayout,mVLilleListFragment).commit()
                title="VLille"
            }
            else if (choice==1){
                mRoubaixListFragment=RoubaixListFragment()
                supportFragmentManager.beginTransaction().add(R.id.ActivityMainFrameLayout,mRoubaixListFragment).commit()
                title="Street Art"
            }
        }
    }

    //show fragment in parameter
    private fun showFragment(fragment: Fragment){
        supportFragmentManager.beginTransaction().replace(R.id.ActivityMainFrameLayout,fragment).commit()
    }


    private fun checkLocationPermission(permissionCode:Int): Boolean {
        return if (ContextCompat.checkSelfPermission(this,Manifest.permission.ACCESS_FINE_LOCATION)!= PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(this, arrayOf(Manifest.permission.ACCESS_FINE_LOCATION),permissionCode); false
        } else {
            true
        }
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        if(requestCode==MY_PERMISSIONS_REQUEST_LOCATION){
            if(grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) goToVlilleMap()
        }
    }

    private fun goToVlilleMap(){
        if(!this::mVLilleMapFragment.isInitialized) mVLilleMapFragment= VLilleMapFragment()
        showFragment(mVLilleMapFragment)
        title="VLille"
    }
}