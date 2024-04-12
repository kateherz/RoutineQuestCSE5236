package com.example.routinequestcse5236.ui.fragments

//import android.R
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.example.routinequestcse5236.R
import com.example.routinequestcse5236.ui.activities.AvatarCreationActivity
import com.example.routinequestcse5236.ui.activities.MainMenuActivity
import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException
import com.google.firebase.auth.FirebaseAuthUserCollisionException
import com.google.firebase.auth.FirebaseAuthWeakPasswordException
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.firestore


class SignupFragment : Fragment() {

    private var mEmailEditText: EditText? = null
    private var mPasswordEditText: EditText? = null
    private lateinit var firebaseAuth: FirebaseAuth
    private lateinit var databaseRef: FirebaseFirestore

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        Log.d("SignupFragment", "onCreateView()")
        val v: View = inflater.inflate(R.layout.fragment_signup, container, false)

        mEmailEditText = v.findViewById<EditText>(R.id.email_text)
        mPasswordEditText = v.findViewById<EditText>(R.id.password_text)
        firebaseAuth = FirebaseAuth.getInstance()
        databaseRef = Firebase.firestore

        val loginButton: Button = v.findViewById<Button>(R.id.login_button)
        val signupButton: Button = v.findViewById<Button>(R.id.signup_button)

        loginButton.setOnClickListener { v ->
            Log.d("SignupFragment", "Email: " + mEmailEditText?.text.toString())
            Log.d("SignupFragment", "Password: " + mPasswordEditText?.text.toString())
            firebaseAuth
                .signInWithEmailAndPassword(mEmailEditText?.text.toString(), mPasswordEditText?.text.toString())
                .addOnCompleteListener {
                    if(it.isSuccessful) {
                        Log.d("Firebase", "login successful")
                        val intent = Intent(v.context, MainMenuActivity::class.java)
                        startActivity(intent)
                    }
                    else{
                        try {
                            throw it.getException()!!
                        }
                        catch (e: FirebaseAuthInvalidCredentialsException) {
                            Toast.makeText(context, "Incorrect Username or Password", Toast.LENGTH_SHORT).show()
                        }
                        catch (e: Exception) {
                            Log.d("Sign-up error", e.message!!)
                            Toast.makeText(context, e.message, Toast.LENGTH_SHORT).show()
                        }
                    }
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
                       val user = hashMapOf(
                           "email" to mEmailEditText?.text.toString(),
                           "password" to mPasswordEditText?.text.toString().hashCode(),
                       )
                       databaseRef
                           .collection("users")
                           .document(mEmailEditText?.text.toString())
                           .set(user)
                           .addOnSuccessListener {
                               Log.d("Firebase", "user added to firestore")
                           }
                       onDestroyView()
                       val intent = Intent(v.context, AvatarCreationActivity::class.java)
                       startActivity(intent)
                       activity?.finish()
                   }
                   else{
                       try {
                           throw it.getException()!!
                       } catch (e: FirebaseAuthWeakPasswordException) {
                           Toast.makeText(context, "Invalid Password: Must be at least 6 characters", Toast.LENGTH_SHORT).show()
                       }  catch (e: FirebaseAuthUserCollisionException) {
                           Toast.makeText(context, "A user already exists under that email", Toast.LENGTH_SHORT).show()
                       } catch (e: Exception) {
                           Log.d("Sign-up error", e.message!!)
                           Toast.makeText(context, e.message, Toast.LENGTH_SHORT).show()
                       }
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

