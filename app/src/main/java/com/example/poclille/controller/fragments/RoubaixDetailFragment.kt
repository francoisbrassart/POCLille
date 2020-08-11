package com.example.poclille.controller.fragments

import android.content.Intent
import android.graphics.Bitmap
import android.net.Uri
import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import com.example.poclille.R
import com.example.poclille.model.Fresco
import com.example.poclille.model.RoubaixViewModel

import kotlinx.android.synthetic.main.fragment_roubaix_detail.view.*
import java.util.*

class RoubaixDetailFragment(fresco: Fresco) : Fragment(),RoubaixViewModel.Callbacks {
    private val mFresco=fresco
    private lateinit var mView:View

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        mView=inflater.inflate(R.layout.fragment_roubaix_detail, container, false)
        setHasOptionsMenu(true)
        updateUI(mView)
        return mView
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
        mView.FragmentRoubaixDetailName.text=mFresco.name
        mView.FragmentRoubaixDetailStreetAddress.text=mFresco.streetAdress
        mView.FragmentRoubaixDetailDescription.text=mFresco.description

        RoubaixViewModel.getImageFromUrl(this,mFresco.urlPhoto)
    }

    //Share Location of current fresco
    private fun sendLocation(){
        val uri: String = java.lang.String.format(Locale.ENGLISH, "geo:%f,%f?q=%f,%f()", mFresco.location[0], mFresco.location[1], mFresco.location[0], mFresco.location[1])
        val intent = Intent(Intent.ACTION_VIEW, Uri.parse(uri))
        context?.startActivity(intent)
    }

    override fun onPreExecute() {
        mView.FragmentRoubaixDetailProgressBar.visibility=View.VISIBLE
    }

    override fun onPostExecute() {
    }

    override fun onPostExecuteImg(result: Bitmap) {
        mView.FragmentRoubaixDetailProgressBar.visibility=View.GONE
        mView.FragmentRoubaixDetailImage.setImageBitmap(result)
    }

}