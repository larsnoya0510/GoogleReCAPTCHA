package com.example.googlerecaptcha.data_class
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
class ResponseErrorBody(
    @Expose
    @SerializedName("message")
    val message: String,
    @Expose
    @SerializedName("error")
    val error: String
)

data class WorksItem(
    var imgUrl : String,
    var workName : String,
    var date : String,
    var introduction : String,
    var collectionState : Boolean,
    var worksId: Int,
    var arthor:String?
)
data class DeviceConfig(
    var spanCountByDevice : Int,
    var mLargeSpanSize: Int,
    var mSmallSpanSize: Int,
    var innerSpace:Int,
//    var innerSpaceV: Int,
//    var innerSpaceH:Int,
    var largeItemNumber :Int
)
//==
data class WorksResponseData(
    var isSuccess:Boolean,
    var errorMessage:String,
    var data : WorksResponseDataSets
)
data class WorksResponseDataSets(
    var worksBanner : WorksBannerItem,
    var workId : Int,
    var menuSets : MenuSets,
    var worksInformation: WorksInformation,
    var support: Support
)
data class WorksBannerItem(
    var worksName:String,
    var mobileImgUrl:String,
    var tabletImgUrl:String,
    var autherorName:String,
    var shareLink:String,
    var collectState:Boolean,
    var categoryName:String
)
data class MenuSets(
    var serialSatate : Int,
    var totalNumber :Int,
    var orderByType: Int,
    var worksContent : MutableList<WorksContentItem>
)
data class WorksContentItem(
    var chapter : Int,
    var date : String,
    var title:String,
    var imgUrl :String,
    var heartsCount : Int,
    var worksState : WorksState

)
data class WorksState(
    var purchaseState:Int, //0 未購買 1 已購買  2 已租閱 3 未租閱 4 免費使用 5 可使用
    var readed :Boolean,
    var rentExpirationDate:String?,
    var rentNeedPCoins:Int?,
    var rentNeedCCoins:Int?,
    var purchaseNeedCCoin:Int?
)
data class WorksInformation(
    var worksAbstract :String,
    var authorList : MutableList<WorksAuthor>,
    var categoryList: MutableList<String>,
    var tagList: MutableList<String>,
    var otherPurchaseWorkList: MutableList<WorksItem>,
    var recentViewList: MutableList<WorksItem>,
    var theBestHitList: MutableList<WorksItem>
)
data class WorksAuthor(
    var authorIcon:String,
    var authorName:String,
    var authorInstruvtion:String,
    var authorWorksPageId:Int
)
data class WorksCategory(
    var worksCategoryName:String
)
data class WorksTag(
    var worksTagName:String
)
data class Support(
    var readerSupport : ReaderSupport,
    var sponsorDonate : SponsorDonate,
    var donateRecordList : MutableList<DonateRecord>

)
data class ReaderSupport(
    var readCount:Int,
    var recommandCount:Int,
    var favoriteCount : Int,
    var messageCount :Int
)
data class SponsorDonate(
    var donateCount :Int,
    var totalMoney : Int
)
data class DonateRecord(
    var donateSerialNumber:Int,
    var name:String,
    var iconImgUrl:String,
    var money:Int,
    var message :String,
    var messageDate:String,
    var authorName:String,
    var authorImgUrl:String,
    var authorReply:String
)


//==
data class PurchaseResponseData(
    var isSuccess:Boolean,
    var errorMessage:String,
    var data : PurchaseResponseDataSets
)
data class PurchaseResponseDataSets(
    var worksSeries: Int,
    var worksSeriesTitle : String,
    var worksSeriesImageUrl : String,
    var userPoint : Int,
    var userCoin : Int,
    var RentPrice : Int?,
    var RentCoin : Int?,
    var PurchaseCoin : Int?
)
data class ImageLoadDataSet(
    var order:Int,
    var base64Data:MutableList<String>

)
data class ImageLoadDataSetNew(
    var order:Int,
    var base64Data:String
)
