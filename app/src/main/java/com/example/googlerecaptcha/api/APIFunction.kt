package com.example.cccmp.api


import com.example.cccmp.data_class.DataModel
import com.example.googlerecaptcha.fake_data.FakePurchaseData
import com.example.googlerecaptcha.fake_data.FakeWorksData
import com.example.googlerecaptcha.api.APIResponseMessage
import com.example.googlerecaptcha.api.ApiClient
import com.example.googlerecaptcha.data_class.ResponseErrorBody
import com.example.googlerecaptcha.viewmodel.WorksResponseDataViewModel
import com.google.gson.Gson
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class APIFunction {
    companion object{
        fun fakeGetWorksData( viewModel: WorksResponseDataViewModel)  {
            viewModel.setWorksResponseDataTrigger(200, FakeWorksData.DefaultWorksData)
        }
//        fun fakeGetPurchaseData( viewModel: PurchaseResponseDataViewModel)  {
//            viewModel.setwPurchaseResponseDataTrigger(200, FakePurchaseData.purchaseResponseData)
//        }
        private fun returnResponseErrorBody(responseErrorBody: String): String {
            var responseMessage : ResponseErrorBody
            var mGson=Gson()
            responseMessage=mGson.fromJson(
                responseErrorBody,
                ResponseErrorBody::class.java
            )
            return responseMessage.message
        }
    }

}