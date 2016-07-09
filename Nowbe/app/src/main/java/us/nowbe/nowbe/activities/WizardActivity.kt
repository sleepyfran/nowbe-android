package us.nowbe.nowbe.activities

import android.os.Bundle
import android.support.v4.app.FragmentActivity
import us.nowbe.nowbe.R
import us.nowbe.nowbe.fragments.LogInFragment
import us.nowbe.nowbe.fragments.SignUpFragment
import us.nowbe.nowbe.fragments.WelcomeFragment

class WizardActivity : FragmentActivity() {

    enum class FragmentsEnum {
        LOGIN, SIGNUP
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_wizard)

        // Set the welcome fragment as the default
        supportFragmentManager.beginTransaction()
                .add(R.id.fgWizard, WelcomeFragment())
                .addToBackStack(null)
                .commit()
    }

    fun switchTo(option: FragmentsEnum) {
        // Start a fragment transaction with custom animations
        val transaction = supportFragmentManager.beginTransaction()
                .setCustomAnimations(R.anim.slide_in_right, R.anim.slide_out_left,
                        R.anim.slide_in_left, R.anim.slide_out_right)
                .addToBackStack(null)

        when (option) {
            FragmentsEnum.LOGIN -> transaction.replace(R.id.fgWizard, LogInFragment())
            FragmentsEnum.SIGNUP -> transaction.replace(R.id.fgWizard, SignUpFragment())
        }

        // Commit the transaction
        transaction.commit()
    }
}
