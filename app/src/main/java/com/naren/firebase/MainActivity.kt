package com.naren.firebase

import android.content.res.Resources
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.navigation.fragment.NavHostFragment

class MainActivity : AppCompatActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val host : NavHostFragment = supportFragmentManager
            .findFragmentById(R.id.my_nav_host_fragment) as NavHostFragment? ?: return

        val navController = host.navController

        navController?.addOnDestinationChangedListener{_,destination,_->
            val dest : String = try{
                    resources.getResourceName(destination.id)
            }catch (e : Resources.NotFoundException){
                Integer.toString(destination.id)
            }

        }

    }


}