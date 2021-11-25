package com.myapps.githubrepos

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path

interface GithubClient {

    // Use only relative url

    // Get all repositories based on given username
    @GET("users/{user}/repos")
    fun reposDetailsFromUser(@Path("user") user : String) : Call<List<GithubRepos>>
}