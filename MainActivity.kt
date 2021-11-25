package com.myapps.githubrepos

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.widget.AdapterView.OnItemClickListener
import android.widget.ArrayAdapter
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.snackbar.Snackbar
import com.myapps.githubrepos.databinding.ActivityMainBinding
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory


private lateinit var binding: ActivityMainBinding

private const val githubURL  =  "https://api.github.com/"

private lateinit var githubClient : GithubClient

private lateinit var requestAdapter : ArrayAdapter<String> // Contains JSON object
private var htmlURLList : ArrayList<String> = arrayListOf() // Contains HTML URL for each repository (indexes are the same as the listView)

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        actionBar?.hide()

        // Initialize listView adapter
        val initialList : List<String> = ArrayList()
        requestAdapter =  ArrayAdapter(this, android.R.layout.simple_list_item_1, initialList)
        binding.lvRepositories.adapter = requestAdapter

        // When clicking on list item/repository, redirect to that repository url
        binding.lvRepositories.onItemClickListener = OnItemClickListener { _, _, position, _ ->

            val viewIntent = Intent("android.intent.action.VIEW",
                Uri.parse(htmlURLList[position])
            )
            startActivity(viewIntent)
        }

        // Search for all repositories of user typed
        binding.bSearch.setOnClickListener{

            requestAdapter.clear()
            htmlURLList.clear()
            getRepositories(binding.etUsername.text.toString())
        }

    }

    private fun getRepositories(username : String){

        try {
            val builder: Retrofit.Builder = Retrofit.Builder()
                .baseUrl(githubURL)
                .addConverterFactory(GsonConverterFactory.create())

            val retrofit: Retrofit = builder.build()
            githubClient = retrofit.create(GithubClient::class.java)

            val call : Call<List<GithubRepos>> = githubClient.reposDetailsFromUser(username)
            call.enqueue(object : Callback<List<GithubRepos>>{
                override fun onResponse(call: Call<List<GithubRepos>>, response: Response<List<GithubRepos>>) {

                    if (!response.isSuccessful){
                        Log.e("Request", response.code().toString())
                        Snackbar.make(findViewById(android.R.id.content), "Error code: ${response.code()}", Snackbar.LENGTH_SHORT).show()
                        return
                    }
                    // Else if request is a success
                    val repos : List<GithubRepos> = response.body()

                    for(repo in repos){
                        //var reposData = ""
                        //reposData += "ID: " + repo.getId() + "\n"
                        //reposData += "NODE ID: " + repo.getNodeId() + "\n"
                        //reposData += "NAME: " + repo.getName() + "\n"
                        //reposData += "PRIVATE: " + repo.getPrivate() + "\n"
                        //reposData += "OWNER NAME: " + repo.getOwner().getLogin() + "\n"
                        //reposData += "OWNER ID: " + repo.getOwner().getUserID() + "\n"
                        //reposData += "HTML URL: " + repo.getHtmlURL() + "\n"
                        //reposData += "DESCRIPTION: " + repo.getDescription() + "\n"
                        //reposData += "SIZE: " + repo.getSize() + "\n"
                        //reposData += "LANGUAGE: " + repo.getLanguage() + "\n\n"

                        //myAdapter.add(reposData)
                        htmlURLList.add(repo.getHtmlURL())
                        requestAdapter.add(repo.getName())
                    }
                }
                override fun onFailure(call: Call<List<GithubRepos>>, t: Throwable) {
                    Log.e("Request", t.message.toString())
                    Snackbar.make(findViewById(android.R.id.content), "Oops, something went wrong.", Snackbar.LENGTH_SHORT).show()
                }
            })
        }
        catch (e: Exception){
            Log.e("Request", e.toString())
            Snackbar.make(findViewById(android.R.id.content), "Oops, something went wrong.", Snackbar.LENGTH_SHORT).show()
        }
    }
}
























