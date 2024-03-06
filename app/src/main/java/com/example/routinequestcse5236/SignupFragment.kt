package com.example.routinequestcse5236

//import androidx.databinding.tool.util.StringUtils
import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.LifecycleOwner
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.nio.charset.StandardCharsets
import java.security.MessageDigest
import java.security.NoSuchAlgorithmException
import java.util.concurrent.CopyOnWriteArrayList


class SignupFragment : Fragment() {

    private var mUsernameEditText: EditText? = null
    private var mPasswordEditText: EditText? = null

    private val mUserAccountViewModel: AccountViewModel by viewModels()
    private lateinit var mDataStore: SettingsDataStore
    private var mUserAccountList = CopyOnWriteArrayList<UserAccount>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val activity = requireActivity() as AppCompatActivity

        //***NOT WORKING BUT UPDATE FUNCTION
        // Here's a dummy observer object that indicates when the UserAccounts change in the database.
//        mUserAccountViewModel.allUserAccounts.observe(
//            (activity as LifecycleOwner)
//        ) { userAccounts ->
//            Timber.tag(classTag)
//                .d("The list of UserAccounts just changed; it has %s elements", userAccounts.size)
//            mUserAccountList.clear()
//            mUserAccountList.addAll(userAccounts)
//        }
    }
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        Log.d("SignupFragment", "onCreateView()")
        var v: View = inflater.inflate(R.layout.fragment_signup, container, false)

        val activity: Activity = requireActivity()
        mDataStore = SettingsDataStore(activity.applicationContext)

        mUsernameEditText = v.findViewById<EditText>(R.id.username_text)
        mPasswordEditText = v.findViewById<EditText>(R.id.password_text)

        val loginButton: Button = v.findViewById<Button>(R.id.login_button)
        val signupButton: Button = v.findViewById<Button>(R.id.signup_button)

        loginButton.setOnClickListener { v ->
            Log.d("SignupFragment", "Name: " + mUsernameEditText?.text.toString())
            Log.d("SignupFragment", "Password: " + mPasswordEditText?.text.toString())
            val username = mUsernameEditText?.text.toString()
            val password = mPasswordEditText?.text.toString()
            val digest: MessageDigest
            try {
                digest = MessageDigest.getInstance("SHA-256")
                val sha256HashBytes = digest.digest(password.toByteArray(StandardCharsets.UTF_8))
                val sha256HashStr = ""//StringUtils.bytesToHex(sha256HashBytes)
                val activity: Activity = requireActivity()

                val userAccount = UserAccount(username, sha256HashStr)

                // if (accountList.size > 0 && hasMatchingAccount) {
                if (mUserAccountList.contains(userAccount)) {
                    CoroutineScope(Dispatchers.IO).launch {
                        mDataStore.putString("name", username)
                        Log.d("SignUpFragment","Wrote username successfully to DataStore")
                    }

                    /*  Save username as the name of the player
                    val settings =
                        PreferenceManager.getDefaultSharedPreferences(activity.applicationContext)
                    val editor = settings.edit()
                    editor.putString(OPT_NAME, username)
                    editor.apply() */

                    // Bring up the GameOptions screen
                    //startActivity(Intent(activity, GameOptionsActivity::class.java))
                    activity.finish()
                } else {
                    //showLoginErrorFragment()
                    Log.d("SignUpFragment", "ERROR logging in")
                }
            } catch (e: NoSuchAlgorithmException) {
                e.printStackTrace()
            }
        }

        signupButton.setOnClickListener { v ->
            Log.d("SignupFragment", "Name: " + mUsernameEditText?.text.toString())
            Log.d("SignupFragment", "Password: " + mPasswordEditText?.text.toString())
            val db = DBHelper(this, null)//create DB Helper class and add context to it

            val username = mUsernameEditText?.text.toString()
            val password = mPasswordEditText?.text.toString()

            //calling method to add to database
            //db.addName(name, pswd)
            //Log.d("SignupFragment", "$name added to database")
            //mUsernameEditText?.text?.clear()
            //mPasswordEditText?.text?.clear()
            //val username = mEtUsername.text.toString()
            //val password = mEtPassword.text.toString()
            //val confirm = mEtConfirm.text.toString()
            if (/*password == confirm &&*/ username != "" && password != "") {
                // Old way: use account singleton
                // val singleton = AccountSingleton.get(activity.applicationContext)
                try {
                    val digest = MessageDigest.getInstance("SHA-256")
                    val sha256HashBytes =
                        digest.digest(password.toByteArray(StandardCharsets.UTF_8))
                    val sha256HashStr = ""//StringUtils.bytesToHex(sha256HashBytes)

                    // New way: create new UserAccount, then add it to ViewModel
                    val userAccount = UserAccount(username, sha256HashStr)
                    mUserAccountViewModel.insert(userAccount)
                    Toast.makeText(
                        activity.applicationContext,
                        "New UserAccount added",
                        Toast.LENGTH_SHORT
                    ).show()

                } catch (e: NoSuchAlgorithmException) {
                    Toast.makeText(
                        activity,
                        "Error: No SHA-256 algorithm found",
                        Toast.LENGTH_SHORT
                    )
                        .show()
                    e.printStackTrace()
                }
            } else if (username == "" || password == "" /*|| confirm == ""*/) {
                Toast.makeText(activity.applicationContext, "Missing entry", Toast.LENGTH_SHORT)
                    .show()
            } else {
                Log.d("SignupFragment", "An unknown account creation error occurred.")
            }
        }

        val intent = Intent(v.context, AvatarCreationActivity::class.java)
        startActivity(intent)
    //}

    return v
}


    override fun onDestroyView() {
        super.onDestroyView()
        mUserAccountViewModel.allUserAccounts.removeObservers(activity as LifecycleOwner)
        Log.d("SignupFragment", "onDestroyView()")
    }
}

