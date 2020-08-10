package com.example.poclille.controller.fragments

import android.graphics.Bitmap
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
import com.example.poclille.controller.RoubaixListAdapter
import com.example.poclille.model.RoubaixViewModel


class RoubaixListFragment : Fragment(), RoubaixViewModel.Callbacks {
    private lateinit var mRecyclerView: RecyclerView
    private lateinit var mViewAdapter: RecyclerView.Adapter<*>
    private lateinit var mViewManager: RecyclerView.LayoutManager
    private lateinit var mProgressBar:ProgressBar

    private lateinit var mView: View

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle? ): View? {
        mView=inflater.inflate(R.layout.fragment_roubaix_list, container, false)
        mProgressBar=mView.findViewById(R.id.fragment_roubaix_list_progress_bar)
        if(RoubaixViewModel.list_frescoes.isEmpty()) RoubaixViewModel.execute(this)
        else configureRecyclerView()
        return mView
    }

    override fun onPreExecute() {
        mProgressBar.visibility=View.VISIBLE
    }

    override fun onPostExecute() {
        mProgressBar.visibility=View.GONE
        configureRecyclerView()
    }

    override fun onPostExecuteImg(result: Bitmap) {
    }

    //configure RecyclerView with layout manager & adapter
    private fun configureRecyclerView(){
        mViewManager = LinearLayoutManager(context)
        mViewAdapter = RoubaixListAdapter(RoubaixViewModel.list_frescoes)
        val itemDecor = DividerItemDecoration(context, (mViewManager as LinearLayoutManager).orientation)

        mRecyclerView = mView.findViewById<RecyclerView>(R.id.fragment_roubaix_list_recycler_view).apply {
            setHasFixedSize(true)
            layoutManager = mViewManager
            adapter = mViewAdapter
            addItemDecoration(itemDecor)
        }
    }

}