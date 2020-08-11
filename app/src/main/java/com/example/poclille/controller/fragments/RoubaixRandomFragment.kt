package com.example.poclille.controller.fragments

import android.graphics.Bitmap
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.poclille.R
import com.example.poclille.model.RoubaixViewModel
import kotlinx.android.synthetic.main.fragment_roubaix_random.view.*
import kotlin.random.Random

class RoubaixRandomFragment : Fragment(),RoubaixViewModel.Callbacks {
    private lateinit var mView:View

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        mView=inflater.inflate(R.layout.fragment_roubaix_random, container, false)
        mView.FragmentRoubaixRandomButton.setOnClickListener { displayRandomImg() }

        if(RoubaixViewModel.list_frescoes.isEmpty()) RoubaixViewModel.execute(this)
        else displayRandomImg()
        return mView
    }

    override fun onPreExecute() {
        mView.FragmentRoubaixRandomProgressBar.visibility=View.VISIBLE
    }

    override fun onPostExecute() {
        displayRandomImg()
    }

    //get image from API of a random fresco
    private fun displayRandomImg(){
        val random_nb= Random.nextInt(0,RoubaixViewModel.list_frescoes.size)
        RoubaixViewModel.getImageFromUrl(this,RoubaixViewModel.list_frescoes[random_nb].urlPhoto)
    }

    override fun onPostExecuteImg(result: Bitmap) {
        mView.FragmentRoubaixRandomProgressBar.visibility=View.GONE
        mView.FragmentRoubaixRandomImage.setImageBitmap(result)
    }
}