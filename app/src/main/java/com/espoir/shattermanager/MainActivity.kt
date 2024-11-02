package com.espoir.shattermanager

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import com.espoir.shatter.IShatterActivity
import com.espoir.shatter.ShatterManager


class MainActivity : AppCompatActivity(), IShatterActivity {

    private val shatterManager = ShatterManager(this)
    private var f: Fragment? = null;
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        if (savedInstanceState == null) {
            supportFragmentManager.beginTransaction().setReorderingAllowed(true)
                .add(R.id.fmlayout, BlankFragment2.newInstance("", "")).commit()
        }


    }

    override fun getShatterManager(): ShatterManager = shatterManager



    override fun onRestart() {
        super.onRestart()
        getShatterManager().onRestart()
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        getShatterManager().onActivityResult(requestCode, resultCode, data)
    }

    override fun onBackPressed() {
        if (getShatterManager().enableOnBackPressed()) {
            super.onBackPressed()
        }
    }
}

