package com.example.googlerecaptcha.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.cccmp.api.APIFunction
import com.example.googlerecaptcha.data_class.WorksResponseData

class ViewModel {
}
class WorksResponseDataViewModel() : ViewModel() {
    private var worksResponseData = MutableLiveData<WorksResponseData>()
    private var worksResponseDataTrigger = MutableLiveData<Int>()

    private var worksCollectionStateData = MutableLiveData<Boolean>()

    fun RequestWorksData(){
        //TODO 假資料
        APIFunction.fakeGetWorksData(this)
    }
    fun setWorksResponseDataTrigger(result: Int, mData: WorksResponseData) {
        worksResponseDataTrigger.postValue(result)
        setResponse(mData)
    }
    fun setWorksResponseDataTrigger(result: Int) {
        worksResponseDataTrigger.postValue(result)
    }
    //TODO TEMP修改
    fun requestSetWorksCollectionState(mState:Boolean){
        var mWorksResponseData=worksResponseData.value!!
        mWorksResponseData.data.worksBanner.collectState=mState
        setWorksCollectionState(mState)
//        setWorksResponseDataTrigger(200,mWorksResponseData)
    }
    fun setWorksCollectionState(mState:Boolean){
        worksCollectionStateData.value=mState
    }
    fun getWorksCollectionState() : MutableLiveData<Boolean> {
        return worksCollectionStateData
    }
    //TODO TEMP修改
    fun changeDataPurchaseState(chapter:Int){
        var mData=worksResponseData.value!!
        for(i in 0 until mData.data.menuSets.worksContent.size){
            if(mData.data.menuSets.worksContent[i].chapter==chapter){
                mData.data.menuSets.worksContent[i].worksState.purchaseState=6
            }
        }
        worksResponseData.value=mData

    }
    fun setOrderByType(mState:Int){

        var mWorksResponseData=worksResponseData.value!!
        mWorksResponseData.data.menuSets.orderByType=mState
        setWorksResponseDataTrigger(200,mWorksResponseData)
    }
    fun setResponse( mData: WorksResponseData) {
        worksResponseData.value=mData
    }
    fun getWorksResponseData(): MutableLiveData<WorksResponseData> {
        return worksResponseData
    }
    fun getData(): MutableLiveData<Int> {
        return worksResponseDataTrigger
    }

}
class WorksAppbarPositionViewModel: ViewModel(){
    private var worksAppbarPositionData = MutableLiveData<Int>()
    fun setPosition(mPosition:Int){
        worksAppbarPositionData.postValue(mPosition)
    }
    fun getData(): MutableLiveData<Int> {
        return worksAppbarPositionData
    }
}