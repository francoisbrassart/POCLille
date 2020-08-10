package com.example.poclille.model

import org.json.JSONArray
import org.json.JSONObject

class VLille(jsonVlille: JSONObject) {
    lateinit var name: String
    lateinit var state:String
    var velosDispo:Int=0
    var placesDispo:Int=0
    lateinit var location: JSONArray
    lateinit var address:String
    lateinit var city:String

    init{
        if(jsonVlille.getJSONObject("fields").has("nom"))
            name=jsonVlille.getJSONObject("fields").getString("nom")
        if(jsonVlille.getJSONObject("fields").has("etat"))
            state=jsonVlille.getJSONObject("fields").getString("etat")
        if(jsonVlille.getJSONObject("fields").has("nbvelosdispo"))
            velosDispo=jsonVlille.getJSONObject("fields").getInt("nbvelosdispo")
        if(jsonVlille.getJSONObject("fields").has("nbplacesdispo"))
            placesDispo=jsonVlille.getJSONObject("fields").getInt("nbplacesdispo")
        if(jsonVlille.getJSONObject("fields").has("localisation"))
            location=jsonVlille.getJSONObject("fields").getJSONArray("localisation")
        if(jsonVlille.getJSONObject("fields").has("adresse"))
            address=jsonVlille.getJSONObject("fields").getString("adresse")
        if(jsonVlille.getJSONObject("fields").has("city"))
            city = jsonVlille.getJSONObject("fields").getString("city")
    }
}