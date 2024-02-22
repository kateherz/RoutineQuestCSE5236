package com.example.routinequestcse5236

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.Surface
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import androidx.fragment.app.Fragment
//import com.example.routinequestcse5236.databinding.FragmentSlideshowBinding

class SignupFragment : Fragment() {

    private var mUsernameEditText: EditText? = null
    private var mPasswordEditText: EditText? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        Log.d("", "Login Fragment onCreateView()")
        var v: View = inflater.inflate(R.layout.fragment_signup, container, false)

        mUsernameEditText = v.findViewById<EditText>(R.id.username_text)
        mPasswordEditText = v.findViewById<EditText>(R.id.password_text)

        val loginButton: Button = v.findViewById<Button>(R.id.login_button)
        val signupButton: Button = v.findViewById<Button>(R.id.signup_button)

        loginButton.setOnClickListener { v ->
            Log.d("", "Name: " + mUsernameEditText?.text.toString())
            Log.d("", "Password: " + mPasswordEditText?.text.toString())
        }

        signupButton.setOnClickListener { v ->
            Log.d("", "Name: " + mUsernameEditText?.text.toString())
            Log.d("", "Password: " + mPasswordEditText?.text.toString())
        }

        return v
    }

    override fun onDestroyView() {
        super.onDestroyView()
        Log.d("", "Login Fragment onDestroyView()")
        Log.d("", "Name: " + mUsernameEditText?.text.toString())
        Log.d("", "Password: " + mPasswordEditText?.text.toString())
    }
}

