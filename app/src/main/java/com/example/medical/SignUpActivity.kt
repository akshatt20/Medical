package com.example.medical

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import com.google.firebase.firestore.FirebaseFirestore

class SignUpActivity : AppCompatActivity() {
    private lateinit var logInDirectToSignUpDoctor: TextView
    private lateinit var doctorName: EditText
    private lateinit var doctorMobileNoemail: EditText
    private lateinit var doctorPassword: EditText
    private lateinit var doctorAge: EditText
    private lateinit var doctorGender: EditText
    private lateinit var doctorResidence: EditText

    private lateinit var doctorSignUpButton: Button
    private lateinit var db: FirebaseFirestore
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sign_up)
        logInDirectToSignUpDoctor = findViewById(R.id.logInDirectToSignUpDoctor)
        doctorName = findViewById(R.id.doctorName)
        doctorMobileNoemail = findViewById(R.id.doctorMobileNoemail)
        doctorPassword = findViewById(R.id.doctorPassword)
        doctorAge = findViewById(R.id.doctorAge)
        doctorGender = findViewById(R.id.doctorGender)
        doctorResidence = findViewById(R.id.doctorResidence)

        doctorSignUpButton = findViewById(R.id.doctorSignUpButton)

        db = FirebaseFirestore.getInstance()
        logInDirectToSignUpDoctor.setOnClickListener {
            val intent = Intent(this, LoginActivity::class.java)
            startActivity(intent)
        }

        doctorSignUpButton.setOnClickListener {
            // Get values from EditText fields
            val name = doctorName.text.toString()
            val mobileNoemail = doctorMobileNoemail.text.toString()
            val password = doctorPassword.text.toString()
            val residence = doctorResidence.text.toString()
            val age = doctorAge.text.toString()
            val gender = doctorGender.text.toString()
            if (name.isEmpty() || mobileNoemail.isEmpty() || age.isEmpty() || gender.isEmpty() || password.isEmpty()  || residence.isEmpty()) {
                Toast.makeText(this, "Please fill in all fields", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            // Create a Doctor map
            val doctor = hashMapOf(
                "name" to name,
                "mobileNoemail" to mobileNoemail,
                "password" to password,
                "residence" to residence,
                "age" to age,
                "gender" to gender,

            )
            val documentId = db.collection("Patients").document().id


            // Add a new document with a generated ID
            db.collection("Patients")
                .add(doctor)
                .addOnSuccessListener { documentReference ->
                    Toast.makeText(this, "Saved", Toast.LENGTH_LONG).show()
                    // Navigate to DoctorHomeActivity after successful signup
                    val intent = Intent(this, HomeActivity::class.java)
                    startActivity(intent)
                }
                .addOnFailureListener { e ->
                    Toast.makeText(this, "Error", Toast.LENGTH_LONG).show()
                }
        }
    }
}