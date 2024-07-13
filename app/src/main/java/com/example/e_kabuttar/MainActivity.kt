package com.example.e_kabuttar

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase

class MainActivity : AppCompatActivity() {

    private lateinit var myDb: FirebaseDatabase
    private lateinit var ref: DatabaseReference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        // Initialize Firebase Database
        myDb = FirebaseDatabase.getInstance()
        ref = myDb.reference.child("users")

        val firstNameEditText: EditText = findViewById(R.id.editTextFirstName)
        val lastNameEditText: EditText = findViewById(R.id.editTextLastName)
        val emailEditText: EditText = findViewById(R.id.editTextEmailAddress)
        val phoneEditText: EditText = findViewById(R.id.editTextPhone)
        val passwordEditText: EditText = findViewById(R.id.editTextPassword)
        val button: Button = findViewById(R.id.button)

        button.setOnClickListener {
            val firstName = firstNameEditText.text.toString()
            val lastName = lastNameEditText.text.toString()
            val email = emailEditText.text.toString()
            val phone = phoneEditText.text.toString()
            val password = passwordEditText.text.toString()

            if (firstName.isNotEmpty() && lastName.isNotEmpty() && email.isNotEmpty() && phone.isNotEmpty() && password.isNotEmpty()) {
                val user = User_Registration_Details(firstName, lastName, email, phone, password)
                saveUserToDatabase(user)
            } else {
                Toast.makeText(this, "Please fill in all fields", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun saveUserToDatabase(user: User_Registration_Details) {
        val userId = ref.push().key
        if (userId != null) {
            ref.child(userId).setValue(user)
                .addOnCompleteListener {
                    Toast.makeText(this, "Registration Successful", Toast.LENGTH_SHORT).show()
                }
                .addOnFailureListener {
                    Toast.makeText(this, "Registration Failed", Toast.LENGTH_SHORT).show()
                }
        }
    }
}
