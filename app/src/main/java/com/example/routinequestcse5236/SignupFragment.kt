package com.example.routinequestcse5236

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.Surface
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import androidx.fragment.app.Fragment


class SignupFragment : Fragment() {

    private var mUsernameEditText: EditText? = null
    private var mPasswordEditText: EditText? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        Log.d("SignupFragment", "onCreateView()")
        var v: View = inflater.inflate(R.layout.fragment_signup, container, false)

        mUsernameEditText = v.findViewById<EditText>(R.id.username_text)
        mPasswordEditText = v.findViewById<EditText>(R.id.password_text)

        val loginButton: Button = v.findViewById<Button>(R.id.login_button)
        val signupButton: Button = v.findViewById<Button>(R.id.signup_button)

        loginButton.setOnClickListener { v ->
            Log.d("SignupFragment", "Name: " + mUsernameEditText?.text.toString())
            Log.d("SignupFragment", "Password: " + mPasswordEditText?.text.toString())
        }

       signupButton.setOnClickListener { v ->
            Log.d("SignupFragment", "Name: " + mUsernameEditText?.text.toString())
            Log.d("SignupFragment", "Password: " + mPasswordEditText?.text.toString())
           val intent = Intent(v.context, AvatarCreationActivity::class.java)
           startActivity(intent)
        }

        return v
    }

    override fun onDestroyView() {
        super.onDestroyView()
        Log.d("SignupFragment", "onDestroyView()")
    }
}

