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
    private var currentFragmentTag: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.main)
        chipNavigationBar = findViewById(R.id.bottom_nav_bar)
        chipNavigationBar.setItemSelected(R.id.home, true)

        // Восстановление текущего фрагмента, если он был сохранен
        currentFragmentTag = savedInstanceState?.getString("currentFragmentTag")
        if (currentFragmentTag != null) {
            val fragment = supportFragmentManager.findFragmentByTag(currentFragmentTag)
            if (fragment != null) {
                supportFragmentManager.beginTransaction()
                    .replace(R.id.fragment_container, fragment, currentFragmentTag)
                    .commit()
            }
        } else {
            // Если текущий фрагмент не был сохранен, устанавливаем главный фрагмент
            supportFragmentManager.beginTransaction()
                .replace(R.id.fragment_container, HomeFragment())
                .commit()
            currentFragmentTag = "HomeFragment"
        }

        bottomMenu()
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        // Сохранение текущего фрагмента
        outState.putString("currentFragmentTag", currentFragmentTag)
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
                val transaction = supportFragmentManager.beginTransaction()
                transaction.replace(R.id.fragment_container, it, it.javaClass.simpleName)
                transaction.addToBackStack(null)
                transaction.commit()
                currentFragmentTag = it.javaClass.simpleName
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
