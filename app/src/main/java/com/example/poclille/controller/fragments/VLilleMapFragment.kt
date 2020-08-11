package com.example.poclille.controller.fragments

import android.Manifest
import android.app.Dialog
import android.content.Context
import android.content.pm.PackageManager
import android.location.Location
import android.location.LocationListener
import android.location.LocationManager
import android.os.Bundle
import android.view.*
import android.widget.Button
import android.widget.NumberPicker
import androidx.core.app.ActivityCompat
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.poclille.R
import com.example.poclille.controller.VLilleListAdapter
import com.example.poclille.model.VLilleViewModel
import kotlinx.android.synthetic.main.fragment_v_lille_map.*
import kotlinx.android.synthetic.main.fragment_v_lille_map.view.*


class VLilleMapFragment : Fragment(), LocationListener, VLilleViewModel.Callbacks {
    private var locationManager: LocationManager? = null
    private lateinit var mView:View
    private lateinit var mAdapter:VLilleListAdapter
    private var mDistance:Int=500

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        mView=inflater.inflate(R.layout.fragment_v_lille_map, container, false)
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
        mView.FragmentVlilleMapTitle.text=getString(R.string.vlille_nearby,distance)
        locationManager = mView.context.getSystemService(Context.LOCATION_SERVICE) as LocationManager
        if (ActivityCompat.checkSelfPermission(mView.context,Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED
            && ActivityCompat.checkSelfPermission(mView.context,Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED
        ) {
            //permission not granted --> should not happen
        }
        else{
            locationManager!!.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0f, this)
            val location: Location? = locationManager!!.getLastKnownLocation(LocationManager.GPS_PROVIDER)
            if (location != null) {
                VLilleViewModel.getVlilleListFromLocation(this,location,distance)
            }
        }
    }

    override fun onPreExecute() {
        mView.FragmentVlilleMapProgressBar.visibility=View.VISIBLE
    }

    override fun onPostExecute() {
        mView.FragmentVlilleMapProgressBar.visibility=View.GONE
        configureRecyclerView()
    }

    //configure Recycler view with the list of vlille
    private fun configureRecyclerView() {
        mAdapter = VLilleListAdapter(VLilleViewModel.list_vlille_position)
        val viewManager=LinearLayoutManager(context)
        val itemDecor = DividerItemDecoration(context, (viewManager as LinearLayoutManager).orientation)
        FragmentVlilleMapRecyclerView.apply {
            setHasFixedSize(true)
            layoutManager = viewManager
            adapter=mAdapter
            addItemDecoration(itemDecor)
        }
    }

    //show dialog with number picker to select distance
    private fun show() {
        //create dialog
        val dialog = Dialog(mView.context,R.style.Theme_Dialog)
        dialog.setContentView(R.layout.dialog_choose_distance)

        //configure number picker
        val np = dialog.findViewById(R.id.numberPicker) as NumberPicker
        val minValue=100; val maxValue=1500; val step=100
        configureNumberPicker(np,minValue,maxValue,step)

        dialog.findViewById<Button>(R.id.dialogChooseDistanceButton).setOnClickListener {
            //clear recyclerView
            VLilleViewModel.list_vlille_position.clear()
            //remove recycler view
            if(this::mAdapter.isInitialized) mAdapter.notifyDataSetChanged()
            mDistance=np.value*step+minValue
            getLocation(mDistance)
            dialog.dismiss() }
        dialog.show()
    }

    private fun configureNumberPicker(np: NumberPicker, minValue: Int, maxValue: Int, step: Int) {
        val numberValues = arrayOfNulls<String>(maxValue / minValue)
        for (i in numberValues.indices) {
            numberValues[i] = (step + i * step).toString()
        }
        np.minValue = 0;
        np.maxValue = 14;
        np.displayedValues = numberValues;
        np.wrapSelectorWheel = false
    }


}