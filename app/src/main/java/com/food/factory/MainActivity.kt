//package com.food.factory
//
//import android.content.Intent
//import androidx.appcompat.app.AppCompatActivity
//import android.os.Bundle
//import android.util.Log
//import android.view.View
//import android.widget.Button
//import android.widget.EditText
//import android.widget.Toast
//import com.food.factory.Admin.AdminActivity
//import com.food.factory.Admin.Fragments.Adapter.MenuAdapter
//import com.food.factory.Admin.Fragments.Model.AddHotelModel
//import com.food.factory.Admin.Fragments.Model.AddMenuModel
//import com.food.factory.User.BarCodeScanner
//import com.food.factory.User.Model.UserModel
//import com.google.android.material.textfield.TextInputEditText
//import com.google.firebase.FirebaseException
//import com.google.firebase.auth.*
//import com.google.firebase.database.*
//import java.util.concurrent.TimeUnit
//
//// this stores the phone number of the user
////var number : String =""
////
////// create instance of firebase auth
////lateinit var auth: FirebaseAuth
////
////// we will use this to match the sent otp from firebase
////lateinit var storedVerificationId:String
////lateinit var resendToken: PhoneAuthProvider.ForceResendingToken
//// lateinit var callbacks: PhoneAuthProvider.OnVerificationStateChangedCallbacks
//
//class MainActivity : AppCompatActivity() {
//
//    var number : String =""
//    private lateinit var userList: ArrayList<UserModel>
//
//    private lateinit var dbRef: DatabaseReference
//
//
//    // create instance of firebase auth
//    lateinit var auth: FirebaseAuth
//
//    // we will use this to match the sent otp from firebase
//    lateinit var storedVerificationId:String
//    lateinit var resendToken: PhoneAuthProvider.ForceResendingToken
//    private lateinit var callbacks: PhoneAuthProvider.OnVerificationStateChangedCallbacks
//
//    override fun onCreate(savedInstanceState: Bundle?) {
//        super.onCreate(savedInstanceState)
//        setContentView(R.layout.activity_main)
//        auth=FirebaseAuth.getInstance()
//        dbRef = FirebaseDatabase.getInstance().getReference("User")
//        userList = arrayListOf<UserModel>()
//
//        callbacks = object : PhoneAuthProvider.OnVerificationStateChangedCallbacks() {
//
//            // This method is called when the verification is completed
//            override fun onVerificationCompleted(credential: PhoneAuthCredential) {
//                startActivity(Intent(applicationContext, MainActivity::class.java))
//                finish()
//                Log.d("GFG" , "onVerificationCompleted Success")
//            }
//
//            // Called when verification is failed add log statement to see the exception
//            override fun onVerificationFailed(e: FirebaseException) {
//                Log.d("GFG" , "onVerificationFailed  $e")
//            }
//
//            // On code is sent by the firebase this method is called
//            // in here we start a new activity where user can enter the OTP
//            override fun onCodeSent(
//                verificationId: String,
//                token: PhoneAuthProvider.ForceResendingToken
//            ) {
//                Log.d("GFG","onCodeSent: $verificationId")
//                storedVerificationId = verificationId
//                resendToken = token
//
//                // Start a new activity using intent
//                // also send the storedVerificationId using intent
//                // we will use this id to send the otp back to firebase
////                    val intent = Intent(applicationContext,OtpActivity::class.java)
////                    intent.putExtra("storedVerificationId",storedVerificationId)
////                    startActivity(intent)
////                    finish()
//            }
//        }
//
//        // start verification on click of the button
//        findViewById<Button>(R.id.idBtnGetOtp).setOnClickListener {
//            login()
//}
//        // start verification on click of the button
//        findViewById<Button>(R.id.idBtnVerify).setOnClickListener {
//            verify()
//}
//    }
//
//    private fun verify() {
//        val otp = findViewById<EditText>(R.id.idEdtOtp).text.trim().toString()
//        if(otp.isNotEmpty()){
//            val credential : PhoneAuthCredential = PhoneAuthProvider.getCredential(
//                storedVerificationId.toString(), otp)
//            signInWithPhoneAuthCredential(credential)
//        }else{
//            Toast.makeText(this,"Enter OTP", Toast.LENGTH_SHORT).show()
//        }
//    }
//
//
//
//    private fun login() {
//        number = findViewById<TextInputEditText>(R.id.idEdtPhoneNumber).text?.trim().toString()
//
//        // get the phone number from edit text and append the country cde with it
//        if (number.isNotEmpty()){
//            number = "+91$number"
//            sendVerificationCode(number)
//        }else{
//            Toast.makeText(this,"Enter mobile number", Toast.LENGTH_SHORT).show()
//        }
//         }
//    fun sendVerificationCode(number: String) {
//        val options = PhoneAuthOptions.newBuilder(auth)
//            .setPhoneNumber(number) // Phone number to verify
//            .setTimeout(60L, TimeUnit.SECONDS) // Timeout and unit
//            .setActivity(this) // Activity (for callback binding)
//            .setCallbacks(callbacks) // OnVerificationStateChangedCallbacks
//            .build()
//        PhoneAuthProvider.verifyPhoneNumber(options)
//                Log.d("GFG" , "Auth started")
//    }
//
//
//    private fun signInWithPhoneAuthCredential(credential: PhoneAuthCredential) {
//        auth.signInWithCredential(credential)
//            .addOnCompleteListener(this) { task ->
//                if (task.isSuccessful) {
//                    Log.e("", "signInWithPhoneAuthCredential: "+auth.currentUser.phoneNumber )
//                    fetch()
//
//                } else {
//                    // Sign in failed, display a message and update the UI
//                    if (task.exception is FirebaseAuthInvalidCredentialsException) {
//                        // The verification code entered was invalid
//                        Toast.makeText(this,"Invalid OTP", Toast.LENGTH_SHORT).show()
//                    }
//                }}}
//
//
//    private fun saveUserData() {
//
//
//        val userid = dbRef.push().key!!
//
//        val hotel = UserModel(userid, number, "user")
//
//        dbRef.child(userid).setValue(hotel)
//            .addOnCompleteListener {
////                Toast.makeText(thiscontext, "Data inserted successfully", Toast.LENGTH_LONG).show()
////
////                hotelName.text!!.clear()
////                hotelLocation.text!!.clear()
////                hotelRatting.text!!.clear()
//
//                fetch()
//
//
//            }.addOnFailureListener { err ->
////                Toast.makeText(thiscontext, "Error ${err.message}", Toast.LENGTH_LONG).show()
//            }
//
//    }
//
//    private fun fetch() {
//
//        val databaseReference = FirebaseDatabase.getInstance().getReference("User")
//
//
//        databaseReference.orderByChild("userNumber").equalTo(number).addValueEventListener(object:
//            ValueEventListener {
//            override fun onDataChange(snapshot: DataSnapshot) {
//                if(snapshot.exists()){
////                    userList.clear()
//                    for (i in snapshot.children){
//                        val menuitem = i.getValue(UserModel::class.java)
//                        userList.add(menuitem!!)
//                    }
//                }
//
//                if (userList.isEmpty()){
////                    if (userList.get(0).userType.equals("user"))
////                    {
////                        nextPage()
////                    }else
////                    {
//                        saveUserData()
//
////                    }
//                }else{
//                    if (userList.get(0).userType.equals("user"))
//                    {
//                        nextPage()
//                    }else
//                    {
//newPage()
//
//                    }
//                }
//
//            }
//
//
//
//
//            override fun onCancelled(error: DatabaseError) {
//
//            }
//        })
//    }
//
//    private fun newPage() {
//        val intent = Intent(this , AdminActivity::class.java)
//        startActivity(intent)
//        finish()    }
//
//    private fun nextPage() {
//
//        val intent = Intent(this , Scan::class.java)
//        startActivity(intent)
//        finish()    }
//
//}
//
//
package com.food.factory

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.food.factory.Admin.AdminActivity
import com.google.android.material.textfield.TextInputEditText

class MainActivity : AppCompatActivity() {

    private var number: String = ""
    private val mockVerificationCode = "123456" // Mock verification code for demo

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // Set up button click listeners
        findViewById<Button>(R.id.idBtnGetOtp).setOnClickListener {
            login()
        }
        findViewById<Button>(R.id.idBtnVerify).setOnClickListener {
            verify()
        }
    }

    private fun login() {
        number = findViewById<TextInputEditText>(R.id.idEdtPhoneNumber).text?.trim().toString()

        if (number.isNotEmpty()) {
            number = "+91$number" // Append country code for demo
            sendVerificationCode(number)
        } else {
            Toast.makeText(this, "Enter mobile number", Toast.LENGTH_SHORT).show()
        }
    }

    private fun sendVerificationCode(number: String) {
        // Simulate sending a verification code
        Log.d("Demo", "Verification code sent to $number")
        Toast.makeText(this, "OTP sent to $number (Mock)", Toast.LENGTH_SHORT).show()

        // Mock a delay to simulate network communication
        Handler(Looper.getMainLooper()).postDelayed({
            onCodeSent(mockVerificationCode)
        }, 2000) // 2-second delay for demo
    }

    private fun onCodeSent(code: String) {
        Log.d("Demo", "Mock verification code sent: $code")
        Toast.makeText(this, "Mock OTP: $code", Toast.LENGTH_SHORT).show()
    }

    private fun verify() {
        val otp = findViewById<EditText>(R.id.idEdtOtp).text?.trim().toString()

        if (otp.isNotEmpty()) {
            if (otp == mockVerificationCode) {
                Log.d("Demo", "Verification successful!")
                fetch()
            } else {
                Log.d("Demo", "Verification failed. Incorrect code.")
                Toast.makeText(this, "Invalid OTP", Toast.LENGTH_SHORT).show()
            }
        } else {
            Toast.makeText(this, "Enter OTP", Toast.LENGTH_SHORT).show()
        }
    }

    private fun fetch() {
        Log.d("Demo", "Simulating user data fetch for $number")

        // Simulate checking user type
        if (number == "+911234567890") { // Mock admin number
            newPage()
        } else {
            saveUserData()
        }
    }

    private fun saveUserData() {
        Log.d("Demo", "Simulating saving user data for $number")
        Toast.makeText(this, "User data saved for $number (Mock)", Toast.LENGTH_SHORT).show()
        nextPage()
    }

    private fun newPage() {
        Log.d("Demo", "Navigating to AdminActivity (Mock)")
        val intent = Intent(this, AdminActivity::class.java)
        startActivity(intent)
        finish()
    }

    private fun nextPage() {
        Log.d("Demo", "Navigating to Scan Activity (Mock)")
        val intent = Intent(this, Scan::class.java)
        startActivity(intent)
        finish()
    }
}
