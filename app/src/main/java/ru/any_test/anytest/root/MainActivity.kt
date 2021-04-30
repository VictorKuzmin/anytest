package ru.any_test.anytest.root

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.navigation.findNavController
import kotlinx.android.synthetic.main.toolbar_basic.*
import kotlinx.android.synthetic.main.toolbar_profile.*
import ru.any_test.anytest.App
import ru.any_test.anytest.R
import ru.any_test.anytest.contract.view.IRootView
import ru.any_test.anytest.profile.FragmentProfile

class MainActivity : AppCompatActivity(), IRootView {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        if(savedInstanceState == null) {
            App.instance?.getAppComponent()?.inject(this)
        }

        handleToolbarOnActivityCreated()
        toolbarBasic.setOnMenuItemClickListener {
            when(it.itemId){
                R.id.item_profile -> {
                    showProfile()
                    true
                }
                else -> false
            }
        }

        toolbarBasic.setNavigationOnClickListener {
            onBackPressed()
        }
        toolbarProfile.setNavigationOnClickListener {
            onBackPressed()
        }
    }

    override fun onBackPressed() {
        if(getForegroundFragment() is FragmentProfile) {
            toolbarProfile.visibility = View.GONE
            toolbarBasic.visibility = View.VISIBLE
        }
        super.onBackPressed()
    }

    override fun showProfile() {
        toolbarProfile.visibility = View.VISIBLE
        toolbarBasic.visibility = View.GONE
        findNavController(R.id.navHostFragment).navigate(R.id.to_fragment_profile_action)
    }

    private fun getForegroundFragment(): Fragment? {
        val navHostFragment = supportFragmentManager.findFragmentById(R.id.navHostFragment)
        return navHostFragment?.childFragmentManager?.fragments?.get(0)
    }

    private fun handleToolbarOnActivityCreated() {
        if(getForegroundFragment() is FragmentProfile) {
            toolbarProfile.visibility = View.VISIBLE
            toolbarBasic.visibility = View.GONE
        } else {
            toolbarProfile.visibility = View.GONE
            toolbarBasic.visibility = View.VISIBLE
        }
    }
}
