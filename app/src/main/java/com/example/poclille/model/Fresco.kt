package com.example.poclille.model

import org.json.JSONArray
import org.json.JSONObject

class Fresco(jsonFresco:JSONObject) {
    var city:String=""
    var streetAdress:String=""
    lateinit var location: JSONArray
    var urlPhoto:String = jsonFresco.getJSONObject("fields").getString("url_de_la_photo")
    var description:String=""
    var name:String=""
    var website:String=""

    init{
        if(jsonFresco.getJSONObject("fields").has("city"))
            city = jsonFresco.getJSONObject("fields").getString("city")
        if(jsonFresco.getJSONObject("fields").has("streetaddress"))
            streetAdress=jsonFresco.getJSONObject("fields").getString("streetaddress")
        if(jsonFresco.getJSONObject("fields").has("coordonnees_geographiques"))
            location=jsonFresco.getJSONObject("fields").getJSONArray("coordonnees_geographiques")
        if(jsonFresco.getJSONObject("fields").has("locales_fr_fr_description"))
            description=jsonFresco.getJSONObject("fields").getString("locales_fr_fr_description")
        if(jsonFresco.getJSONObject("fields").has("oeuvre"))
            name=jsonFresco.getJSONObject("fields").getString("oeuvre")
        if(jsonFresco.getJSONObject("fields").has("website"))
            website=jsonFresco.getJSONObject("fields").getString("website")
    }

}