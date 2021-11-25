package com.myapps.githubrepos

import com.google.gson.annotations.SerializedName

class GithubRepos {

    private val id : Int = 0

    @SerializedName("node_id")
    private val nodeId : String = "node_id"

    private val name : String = "name"
    private val private : Boolean = false
    private val owner : Owner = Owner()

    @SerializedName("html_url")
    private val htmlURL : String = "html_url"

    private val description : String? = null
    private val size : Int = 0
    private val language : String = "language"

    // Getters
    fun getId() : Int{ return id }
    fun getNodeId() : String{ return nodeId }
    fun getName() : String{ return name }
    fun getPrivate() : Boolean{ return private }
    fun getOwner() : Owner{ return owner } // Nested JSON object thus we create another class
    fun getHtmlURL() : String{ return htmlURL }
    fun getDescription() : String?{ return description }
    fun getSize() : Int{ return size }
    fun getLanguage() : String{ return language }
}

class Owner{
    private val login : String = "login"
    private val id : Int = 0

    fun getLogin() : String{ return login }
    fun getUserID() : Int{ return id }
}