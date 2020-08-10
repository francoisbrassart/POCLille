package com.example.poclille.controller.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ProgressBar
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.poclille.R
import com.example.poclille.controller.VLilleListAdapter
import com.example.poclille.model.VLilleViewModel

class VLilleListFragment : Fragment(),VLilleViewModel.Callbacks {

    private lateinit var mRecyclerView: RecyclerView
    private lateinit var mProgressBar: ProgressBar
    private lateinit var mView: View


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        mView=inflater.inflate(R.layout.fragment_v_lille_list, container, false)
        mProgressBar=mView.findViewById(R.id.fragment_vlille_list_progress_bar)
        VLilleViewModel.getVlilleList(this)

        return mView
    }

    override fun onPreExecute() {
        mProgressBar.visibility=View.VISIBLE
    }

    override fun onPostExecute() {
        mProgressBar.visibility=View.GONE
        configureRecyclerView()
    }

    //configure Recycler View with layout manager and adapter
    private fun configureRecyclerView(){
        val viewManager=LinearLayoutManager(context)
        val itemDecor = DividerItemDecoration(context, (viewManager as LinearLayoutManager).orientation)
        mRecyclerView = mView.findViewById<RecyclerView>(R.id.fragment_vlille_list_recycler_view).apply {
            setHasFixedSize(true)
            layoutManager = viewManager
            adapter = VLilleListAdapter(VLilleViewModel.list_vlille)
            addItemDecoration(itemDecor)
        }
    }

}