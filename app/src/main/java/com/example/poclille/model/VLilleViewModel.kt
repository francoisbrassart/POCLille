package com.example.poclille.model

import android.location.Location
import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import org.json.JSONObject
import java.lang.ref.WeakReference
import java.net.URL

object VLilleViewModel: ViewModel() {

    var list_vlille=ArrayList<VLille>()
    var list_vlille_position=ArrayList<VLille>()


    interface Callbacks {
        fun onPreExecute()
        fun onPostExecute()
    }

    // Get VLille list from API
    fun getVlilleList(callbacks: VLilleViewModel.Callbacks?)= viewModelScope.launch {
        val callbacksWeakReference: WeakReference<VLilleViewModel.Callbacks> = WeakReference<VLilleViewModel.Callbacks>(callbacks)
        callbacksWeakReference.get()!!.onPreExecute()
        getVLilleListInBackground()
        callbacksWeakReference.get()!!.onPostExecute()
    }

    //Get VLille List from API in Background
    private suspend fun getVLilleListInBackground()= withContext(Dispatchers.IO){
        val apiResponse= URL("https://opendata.lillemetropole.fr/api/records/1.0/search/?dataset=vlille-realtime&q=&rows=249").readText()
        val jsonArray= JSONObject(apiResponse).getJSONArray("records")
        for(i in 0 until jsonArray.length())
            list_vlille.add(VLille(jsonArray[i] as JSONObject))
        Log.v("VLILLE",list_vlille[0].name)
    }

    //Get nearby Vlille list from API
    fun getVlilleListFromLocation(callbacks: VLilleViewModel.Callbacks?,location: Location,distance:Int)= viewModelScope.launch {
        val callbacksWeakReference: WeakReference<VLilleViewModel.Callbacks> = WeakReference<VLilleViewModel.Callbacks>(callbacks)
        callbacksWeakReference.get()!!.onPreExecute()
        getVLilleListFromLocationInBackground(location,distance)
        callbacksWeakReference.get()!!.onPostExecute()
    }

    //Get nearby Vlille list from API in background
    private suspend fun getVLilleListFromLocationInBackground(location: Location,distance:Int) = withContext(Dispatchers.IO){
        val apiResponse= URL("https://opendata.lillemetropole.fr/api/records/1.0/search/?dataset=vlille-realtime&q=&rows=249&geofilter.distance=${location.latitude}%2C${location.longitude}%2C${distance}").readText()
        val jsonArray= JSONObject(apiResponse).getJSONArray("records")
        for(i in 0 until jsonArray.length())
            list_vlille_position.add(VLille(jsonArray[i] as JSONObject))
    }

}