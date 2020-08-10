package com.example.poclille.controller.fragments

import android.content.Intent
import android.graphics.Bitmap
import android.net.Uri
import android.os.Bundle
import android.view.*
import android.widget.ImageView
import android.widget.ProgressBar
import android.widget.TextView
import androidx.fragment.app.Fragment
import com.example.poclille.R
import com.example.poclille.model.Fresco
import com.example.poclille.model.RoubaixViewModel
import java.util.*

class RoubaixDetailFragment(fresco: Fresco) : Fragment(),RoubaixViewModel.Callbacks {

    private lateinit var mNameTextView:TextView
    private lateinit var mStreetAddressTextView: TextView
    private lateinit var mDescriptionTextView:TextView
    private lateinit var mImg:ImageView
    private val mFresco=fresco
    private lateinit var mProgressBar:ProgressBar

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view=inflater.inflate(R.layout.fragment_roubaix_detail, container, false)
        setHasOptionsMenu(true)
        updateUI(view)
        return view
    }

    //add Menu on toolbar
    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.roubaix_detail_fragment_menu_toolbar,menu);
    }

    //configure toolbar actions
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when(item.itemId){
            R.id.menu_activity_main_share-> {
                sendLocation();true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    //update UI and request image from API
    private fun updateUI(view:View){
        mNameTextView=view.findViewById(R.id.fragement_roubaix_detail_name)
        mStreetAddressTextView=view.findViewById(R.id.fragement_roubaix_detail_street_address)
        mDescriptionTextView=view.findViewById(R.id.fragement_roubaix_detail_description)
        mImg=view.findViewById(R.id.fragment_roubaix_detail_img)
        mProgressBar=view.findViewById(R.id.fragment_roubaix_detail_progress_bar)

        mNameTextView.text=mFresco.name
        mStreetAddressTextView.text=mFresco.streetAdress
        mDescriptionTextView.text=mFresco.description

        RoubaixViewModel.getImageFromUrl(this,mFresco.urlPhoto)
    }

    //Share Location of current fresco
    private fun sendLocation(){
        val uri: String = java.lang.String.format(Locale.ENGLISH, "geo:%f,%f?q=%f,%f()", mFresco.location[0], mFresco.location[1], mFresco.location[0], mFresco.location[1])
        val intent = Intent(Intent.ACTION_VIEW, Uri.parse(uri))
        context!!.startActivity(intent)
    }

    override fun onPreExecute() {
        mProgressBar.visibility=View.VISIBLE
    }

    override fun onPostExecute() {
    }

    override fun onPostExecuteImg(result: Bitmap) {
        mProgressBar.visibility=View.GONE
        mImg.setImageBitmap(result)
    }

}