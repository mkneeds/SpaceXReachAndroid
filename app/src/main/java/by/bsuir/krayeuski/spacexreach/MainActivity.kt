package by.bsuir.krayeuski.spacexreach

import HomeFragment
import android.app.Application
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.ismaeldivita.chipnavigation.ChipNavigationBar
import by.bsuir.krayeuski.spacexreach.fragment.LoveFragment
import by.bsuir.krayeuski.spacexreach.fragment.RocketFragment
import by.bsuir.krayeuski.spacexreach.fragment.SettingsFragment
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.GlobalContext.startKoin

class MainActivity : AppCompatActivity() {
    private lateinit var chipNavigationBar: ChipNavigationBar

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.main)
        chipNavigationBar = findViewById(R.id.bottom_nav_bar)
        chipNavigationBar.setItemSelected(R.id.home, true)
        supportFragmentManager.beginTransaction()
            .replace(R.id.fragment_container, HomeFragment())
            .commit()
        bottomMenu()
    }

    private fun bottomMenu() {
        chipNavigationBar.setOnItemSelectedListener { itemId ->
            val fragment: Fragment? = when (itemId) {
                R.id.home -> HomeFragment()
                R.id.activity -> RocketFragment()
                R.id.favorites -> LoveFragment()
                R.id.settings -> SettingsFragment()
                else -> null
            }

            fragment?.let {
                supportFragmentManager.beginTransaction()
                    .replace(R.id.fragment_container, it)
                    .commit()
            }
        }
    }
}

class AplicationSpaceXReach : Application() {
    override fun onCreate() {
        super.onCreate()

        startKoin {
            androidContext(this@AplicationSpaceXReach)
            modules()
        }
    }

    companion object {
        private lateinit var instance: AplicationSpaceXReach

        fun getInstance(): AplicationSpaceXReach {
            return instance
        }
    }
}
