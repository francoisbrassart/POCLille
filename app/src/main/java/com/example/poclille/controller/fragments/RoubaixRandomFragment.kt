package com.example.poclille.controller.fragments

import android.graphics.Bitmap
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.ProgressBar
import androidx.fragment.app.Fragment
import com.example.poclille.R
import com.example.poclille.model.RoubaixViewModel
import kotlin.random.Random

class RoubaixRandomFragment : Fragment(),RoubaixViewModel.Callbacks {
    private lateinit var mProgressBar: ProgressBar
    private lateinit var mImageView: ImageView
    private lateinit var mButton: Button

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view=inflater.inflate(R.layout.fragment_roubaix_random, container, false)
        mProgressBar=view.findViewById(R.id.fragment_roubaix_random_progress_bar)
        mImageView=view.findViewById(R.id.fragment_roubaix_random_img_view)
        mButton=view.findViewById(R.id.fragment_roubaix_random_img_view_button)
        mButton.setOnClickListener { displayRandomImg() }

        if(RoubaixViewModel.list_frescoes.isEmpty()) RoubaixViewModel.execute(this)
        else displayRandomImg()
        return view
    }

    override fun onPreExecute() {
        mProgressBar.visibility=View.VISIBLE
    }

    override fun onPostExecute() {
        //mProgressBar.visibility=View.GONE
        displayRandomImg()
    }

    //get image from API of a random fresco
    private fun displayRandomImg(){
        val random_nb= Random.nextInt(0,RoubaixViewModel.list_frescoes.size)
        RoubaixViewModel.getImageFromUrl(this,RoubaixViewModel.list_frescoes[random_nb].urlPhoto)
    }

    override fun onPostExecuteImg(result: Bitmap) {
        mProgressBar.visibility=View.GONE
        mImageView.setImageBitmap(result)
    }
}