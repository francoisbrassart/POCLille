package com.example.poclille.controller.fragments

import android.Manifest
import android.app.Dialog
import android.content.Context
import android.content.pm.PackageManager
import android.location.Location
import android.location.LocationListener
import android.location.LocationManager
import android.os.Bundle
import android.util.Log
import android.view.*
import android.widget.Button
import android.widget.NumberPicker
import android.widget.ProgressBar
import android.widget.TextView
import androidx.core.app.ActivityCompat
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.poclille.R
import com.example.poclille.controller.VLilleListAdapter
import com.example.poclille.model.VLilleViewModel


class VLilleMapFragment : Fragment(), LocationListener, VLilleViewModel.Callbacks {
    private var locationManager: LocationManager? = null
    private lateinit var mView:View
    private lateinit var mRecyclerView: RecyclerView
    private lateinit var mAdapter:VLilleListAdapter
    private lateinit var mProgressBar: ProgressBar
    private lateinit var mTitleTextView:TextView
    private var mDistance:Int=500

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        mView=inflater.inflate(R.layout.fragment_v_lille_map, container, false)
        mProgressBar=mView.findViewById(R.id.fragment_vlille_map_progress_bar)
        mTitleTextView=mView.findViewById(R.id.fragment_vlille_map_title)
        getLocation(mDistance)
        setHasOptionsMenu(true)

        return mView
    }

    //create toolbar menu
    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.vlille_map_fragment_menu_toolbar,menu);
    }

    //configure toolbar actions
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when(item.itemId){
            R.id.menu_activity_main_settings-> {
                show();true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    override fun onLocationChanged(p0: Location) {
    }

    //Get current location and call API to get list of VLille nearby
    private fun getLocation(distance:Int){
        mTitleTextView.text=getString(R.string.vlille_nearby,distance)
        locationManager = mView.context.getSystemService(Context.LOCATION_SERVICE) as LocationManager
        if (ActivityCompat.checkSelfPermission(mView.context, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(mView.context, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            //permission not granted !!!
            Log.v("position", "permission not granted")
        }
        locationManager!!.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0f, this)
        val location: Location? = locationManager!!.getLastKnownLocation(LocationManager.GPS_PROVIDER)
        if (location != null) {
            VLilleViewModel.getVlilleListFromLocation(this,location,distance)
        }
    }

    override fun onPreExecute() {
        mProgressBar.visibility=View.VISIBLE
    }

    override fun onPostExecute() {
        mProgressBar.visibility=View.GONE
        configureRecylcerView()
    }

    //configure Recycler view with the list of vlille
    private fun configureRecylcerView() {
        mAdapter = VLilleListAdapter(VLilleViewModel.list_vlille_position)
        val viewManager=LinearLayoutManager(context)
        val itemDecor = DividerItemDecoration(context, (viewManager as LinearLayoutManager).orientation)
        mRecyclerView = mView.findViewById<RecyclerView>(R.id.fragment_vlille_map_recycler_view).apply {
            setHasFixedSize(true)
            layoutManager = viewManager
            adapter=mAdapter
            addItemDecoration(itemDecor)
        }
    }

    //show dialog with number picker to select distance
    private fun show() {
        //create dialog
        val d = Dialog(mView.context,R.style.Theme_Dialog)
        d.setContentView(R.layout.dialog_choose_distance)
        val b1: Button = d.findViewById(R.id.dialog_choose_distance_confirm_btn) as Button

        //configure number picker
        val np = d.findViewById(R.id.numberPicker1) as NumberPicker

        val minValue = 100
        val maxValue = 1500
        val step = 100

        val numberValues = arrayOfNulls<String>(maxValue / minValue)
        for (i in numberValues.indices) {
            numberValues[i] = (step + i * step).toString()
        }
        np.minValue = 0;
        np.maxValue = 14;
        np.displayedValues = numberValues;
        np.wrapSelectorWheel = false

        b1.setOnClickListener {
            //clear recyclerView
            VLilleViewModel.list_vlille_position.clear()
            mAdapter.notifyDataSetChanged()
            mDistance=np.value*step+minValue
            getLocation(mDistance)
            d.dismiss() }
        d.show()
    }

}