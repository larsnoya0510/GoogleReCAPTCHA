package com.example.googlerecaptcha

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.google.android.gms.safetynet.SafetyNet
import com.google.android.gms.safetynet.SafetyNetClient
import kotlinx.android.synthetic.main.activity_main.*
import java.io.*


class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val myClient: SafetyNetClient = SafetyNet.getClient(this)
        are_you_human_button.setOnClickListener {
            myClient.verifyWithRecaptcha(resources.getString(R.string.my_app_key))
                .addOnSuccessListener { successEvent ->
                    val token: String = successEvent.tokenResult
//                    Toast.makeText(this,"$token",Toast.LENGTH_SHORT).show()
                    Toast.makeText(this,"Access",Toast.LENGTH_SHORT).show()
                }
                .addOnFailureListener {
                    Toast.makeText(this,"$it",Toast.LENGTH_SHORT).show()
                }
        }
        saveFile.setOnClickListener {
            val dir = this.getFilesDir()
            val outFile = File(dir, "test.txt")
            writeToFile(outFile, "Hello! 大家好");
        }
        readFile.setOnClickListener {
            val dir = this.getFilesDir()
            val inFile = File(dir, "test.txt")
            val data = readFromFile(inFile)
            Toast.makeText(this,data.toString(),Toast.LENGTH_SHORT).show()
        }
        openTestPage.setOnClickListener {
            var intent= Intent(this,WorksActivity::class.java)
            startActivity(intent)
        }
    }
    private fun writeToFile(fout: File, data: String) {
        var osw: FileOutputStream? = null
        try {
            osw = FileOutputStream(fout)
            osw!!.write(data.toByteArray())
            osw!!.flush()
        } catch (e: Exception) {
        } finally {
            try {
                osw!!.close()
            } catch (e: Exception) {
            }

        }
    }
    private fun readFromFile(fin: File): String {
        val data = StringBuilder()
        var reader: BufferedReader? = null
        try {
            reader = BufferedReader(
                InputStreamReader(
                    FileInputStream(fin), "utf-8"
                )
            )
            var line: String
            line = reader!!.readLine()
            while (line!= null) {
                data.append(line)
                line = reader!!.readLine()
            }

        } catch (e: Exception) {
        } finally {
            try {
                reader!!.close()
            } catch (e: Exception) {
            }

        }
        return data.toString()
    }
}
