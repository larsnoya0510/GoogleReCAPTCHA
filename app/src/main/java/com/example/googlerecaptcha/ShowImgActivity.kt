package com.example.googlerecaptcha

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Base64
import com.bumptech.glide.Glide
import com.example.googlerecaptcha.DB.OfflineDBHelper
import kotlinx.android.synthetic.main.activity_show_img.*
import java.io.BufferedReader
import java.io.File
import java.io.FileInputStream
import java.io.InputStreamReader

class ShowImgActivity : AppCompatActivity() {
 var mChapter=-1
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_show_img)
        mChapter=intent.getIntExtra("Chapter",99)

        //資料庫取得圖片
        var SqlConnect= OfflineDBHelper.getInstance(this)
        var fileList= SqlConnect.querySeriesDetailByChapter(mChapter)
        if(fileList.size>0){
            Glide.with(this!!)
                .load(base64DecodeBitmap(getFileContent(fileList[0])))
                .into(imageView)
        }
    }
    fun getFileContent(filename:String) :String{
        val dir = this.getFilesDir()
        val inFile = File(dir, filename)
        val data = readFromFile(inFile)
        return data
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
    fun base64DecodeBitmap(mString:String): Bitmap {
        var realData=mString.trim().split(",")[1]
        val decodedString = Base64.decode(realData, Base64.DEFAULT)
        val decodedByte = BitmapFactory.decodeByteArray(decodedString, 0, decodedString.size)
        return decodedByte
    }
}
