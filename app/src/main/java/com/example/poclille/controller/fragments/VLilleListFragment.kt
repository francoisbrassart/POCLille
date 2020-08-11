package com.example.poclille.controller.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.poclille.R
import com.example.poclille.controller.VLilleListAdapter
import com.example.poclille.model.VLilleViewModel
import kotlinx.android.synthetic.main.fragment_v_lille_list.view.*

class VLilleListFragment : Fragment(),VLilleViewModel.Callbacks {
    private lateinit var mView: View

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        mView=inflater.inflate(R.layout.fragment_v_lille_list, container, false)
        VLilleViewModel.getVlilleList(this)
        return mView
    }

    override fun onPreExecute() {
        mView.FragmentVlilleListProgressBar.visibility=View.VISIBLE
    }

    override fun onPostExecute() {
        mView.FragmentVlilleListProgressBar.visibility=View.GONE
        configureRecyclerView()
    }

    //configure Recycler View with layout manager and adapter
    private fun configureRecyclerView(){
        val viewManager=LinearLayoutManager(context)
        val itemDecor = DividerItemDecoration(context, (viewManager as LinearLayoutManager).orientation)
        mView.FragmentVlilleListRecyclerView.apply {
            setHasFixedSize(true)
            layoutManager = viewManager
            adapter = VLilleListAdapter(VLilleViewModel.list_vlille)
            addItemDecoration(itemDecor)
        }
    }

}