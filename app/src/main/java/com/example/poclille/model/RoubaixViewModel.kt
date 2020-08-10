package com.example.poclille.model

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import org.json.JSONObject
import java.lang.ref.WeakReference
import java.net.URL

object RoubaixViewModel: ViewModel() {

    var list_frescoes= ArrayList<Fresco>()

    interface Callbacks {
        fun onPreExecute()
        fun onPostExecute()
        fun onPostExecuteImg(result: Bitmap)
    }

    //Get List of Frescoes from API
    fun execute(callbacks:Callbacks?)= viewModelScope.launch {
        val callbacksWeakReference: WeakReference<Callbacks> = WeakReference<Callbacks>(callbacks)
        callbacksWeakReference.get()!!.onPreExecute()
        if(list_frescoes.isEmpty()) doInBackground()
        callbacksWeakReference.get()!!.onPostExecute()
    }

    //Call to API
    private suspend fun doInBackground() = withContext(Dispatchers.IO){
        val apiResponse= URL("https://opendata.lillemetropole.fr/api/records/1.0/search/?dataset=pois&q=&rows=120").readText()
        val jsonArray=JSONObject(apiResponse).getJSONArray("records")
        for(i in 0 until jsonArray.length())
            list_frescoes.add(Fresco(jsonArray[i] as JSONObject))
    }

    //Get image from URL
    fun getImageFromUrl(callbacks:Callbacks?,url:String)=viewModelScope.launch{
        val callbacksWeakReference: WeakReference<Callbacks> = WeakReference<Callbacks>(callbacks)
        callbacksWeakReference.get()!!.onPreExecute()
        val result=doInBackgroundImage(url)
        callbacksWeakReference.get()!!.onPostExecuteImg(result)
    }

    //Get image
    private suspend fun doInBackgroundImage(url:String):Bitmap= withContext(Dispatchers.IO){
        val realURL: URL =URL(url)
        return@withContext BitmapFactory.decodeStream(realURL.openConnection().getInputStream())
    }
}