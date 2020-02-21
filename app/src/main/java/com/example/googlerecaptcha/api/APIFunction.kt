package com.example.cccmp.api


import com.example.cccmp.data_class.DataModel
import com.example.googlerecaptcha.Application
import com.example.googlerecaptcha.fake_data.FakePurchaseData
import com.example.googlerecaptcha.fake_data.FakeWorksData
import com.example.googlerecaptcha.api.APIResponseMessage
import com.example.googlerecaptcha.api.ApiClient
import com.example.googlerecaptcha.data_class.ImageLoadDataSet
import com.example.googlerecaptcha.data_class.ImageLoadDataSetNew
import com.example.googlerecaptcha.data_class.ResponseErrorBody
import com.example.googlerecaptcha.viewmodel.ChapterDownloadViewModel
import com.example.googlerecaptcha.viewmodel.WorksResponseDataViewModel
import com.google.gson.Gson
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.io.File
import java.io.FileOutputStream
import java.util.*

class APIFunction {
    companion object{
        fun fakeGetWorksData( viewModel: WorksResponseDataViewModel)  {
            viewModel.setWorksResponseDataTrigger(200, FakeWorksData.DefaultWorksData)
        }
        fun fakeGetImageData( viewModel: ChapterDownloadViewModel)  {
            //取得圖片轉成檔案
            val dir = Application.instance.applicationContext.getFilesDir()
            val outFile = File(dir, "test${Random().nextInt()}.txt")
            var filePathList = mutableListOf<ImageLoadDataSetNew>()
            var filecontent=""
            for(i in 0 until FakeWorksData.ChapterImageData.size){
                filecontent+=FakeWorksData.ChapterImageData[i]
            }
            writeToFile(outFile, filecontent) {
                var mData=ImageLoadDataSetNew(1,outFile.name)
                filePathList.add(mData)
            }
            viewModel.setChapterImageListData(filePathList)
        }
        private fun returnResponseErrorBody(responseErrorBody: String): String {
            var responseMessage : ResponseErrorBody
            var mGson=Gson()
            responseMessage=mGson.fromJson(
                responseErrorBody,
                ResponseErrorBody::class.java
            )
            return responseMessage.message
        }
        private fun writeToFile(fout: File, data: String,callback: () -> Unit) {
            var osw: FileOutputStream? = null
            try {
                osw = FileOutputStream(fout)
                osw!!.write(data.toByteArray())
                osw!!.flush()
            } catch (e: Exception) {
            } finally {
                try {
                    osw!!.close()
                    callback.invoke()
                } catch (e: Exception) {
                }

            }
        }
    }

}