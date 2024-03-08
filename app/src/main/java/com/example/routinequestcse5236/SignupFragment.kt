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
import com.google.firebase.auth.FirebaseAuth


class SignupFragment : Fragment() {

    private var mEmailEditText: EditText? = null
    private var mPasswordEditText: EditText? = null
    private lateinit var firebaseAuth: FirebaseAuth

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        Log.d("SignupFragment", "onCreateView()")
        var v: View = inflater.inflate(R.layout.fragment_signup, container, false)

        mEmailEditText = v.findViewById<EditText>(R.id.email_text)
        mPasswordEditText = v.findViewById<EditText>(R.id.password_text)
        firebaseAuth = FirebaseAuth.getInstance()

        val loginButton: Button = v.findViewById<Button>(R.id.login_button)
        val signupButton: Button = v.findViewById<Button>(R.id.signup_button)

        loginButton.setOnClickListener { v ->
            Log.d("SignupFragment", "Email: " + mEmailEditText?.text.toString())
            Log.d("SignupFragment", "Password: " + mPasswordEditText?.text.toString())
            firebaseAuth
                .signInWithEmailAndPassword(mEmailEditText?.text.toString(), mPasswordEditText?.text.toString())
                .addOnCompleteListener {
                    Log.d("Firebase", "login successful")
                }
        }

       signupButton.setOnClickListener { v ->
            Log.d("SignupFragment", "Email: " + mEmailEditText?.text.toString())
            Log.d("SignupFragment", "Password: " + mPasswordEditText?.text.toString())
           firebaseAuth
               .createUserWithEmailAndPassword(mEmailEditText?.text.toString(), mPasswordEditText?.text.toString())
               .addOnCompleteListener {
                   if (it.isSuccessful) {
                       Log.d("Firebase", "sign up successful")
                       val intent = Intent(v.context, AvatarCreationActivity::class.java)
                       startActivity(intent)
                   }
               }

        }

        return v
    }

    override fun onDestroyView() {
        super.onDestroyView()
        Log.d("SignupFragment", "onDestroyView()")
    }
}

