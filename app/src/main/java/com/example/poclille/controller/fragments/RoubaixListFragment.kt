package com.example.poclille.controller.fragments

import android.graphics.Bitmap
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.poclille.R
import com.example.poclille.controller.RoubaixListAdapter
import com.example.poclille.model.RoubaixViewModel
import kotlinx.android.synthetic.main.fragment_roubaix_list.view.*


class RoubaixListFragment : Fragment(), RoubaixViewModel.Callbacks {
    private lateinit var mView: View

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle? ): View? {
        mView=inflater.inflate(R.layout.fragment_roubaix_list, container, false)
        if(RoubaixViewModel.list_frescoes.isEmpty()) RoubaixViewModel.execute(this)
        else configureRecyclerView()
        return mView
    }

    override fun onPreExecute() {
        mView.FragmentRoubaixListProgressBar.visibility=View.VISIBLE
    }

    override fun onPostExecute() {
        mView.FragmentRoubaixListProgressBar.visibility=View.GONE
        configureRecyclerView()
    }

    override fun onPostExecuteImg(result: Bitmap) {
    }

    //configure RecyclerView with layout manager & adapter
    private fun configureRecyclerView(){
        val viewManager = LinearLayoutManager(context)
        val viewAdapter = RoubaixListAdapter(RoubaixViewModel.list_frescoes)
        val itemDecor = DividerItemDecoration(context, (viewManager as LinearLayoutManager).orientation)

        mView.FragmentRoubaixListRecyclerView.apply {
            setHasFixedSize(true)
            layoutManager = viewManager
            adapter = viewAdapter
            addItemDecoration(itemDecor)
        }
    }

}