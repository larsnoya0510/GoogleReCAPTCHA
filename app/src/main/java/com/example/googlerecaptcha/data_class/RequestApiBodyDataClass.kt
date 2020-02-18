package com.example.cccmp.data_class

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

class RequestApiBodyDataClass {
}
object DataModel {
    data class getHomeDataBody(
        val token: String
    )
    data class HomeResponseData(
        @Expose
        @SerializedName("code")
        var code:Int,
        @Expose
        @SerializedName("message")
        var message:String,
        @Expose
        @SerializedName("data")
        var data : HomeResponseDataSets
    )
    data class HomeResponseDataSets(
        var banner :MutableList<BannerItem>,
        var book: MutableList<BooksItem>,
        var special_topics:  MutableList<SpecialTopicsItem>,
        var recommend: MutableList<EditorRecommandItem>,
        var rank : RankItems,
//        var  novelRatingDefaultData: NovelRating,
//        var comicRatingDefaultData: ComicRating,
//        var donateRatingDefaultData: DonateRating,
        var announcement: MutableList<AnnouncementItem>
    )
    data class BannerItem(
        var id: Int,
        var image1 : String,
        var image2 : String,
        var content : String,
        var type : String,
        var value : String
    )
    data class AnnouncementItem(
        var id: Int,
        var title : String,
        var description : String,
        var online_at : String

    )
    data class BooksItem(
        var id: Int,
        var name : String,
        var description : String,
        var image1 : String,
        var image2 : String,
        var image3 : String,
        var updated_at : String,
        var is_collected : Int
    )
    data class SpecialTopicsItem(
        var id: Int,
        var title : String,
        var description : String,
        var image1 : String,
        var image2 : String,
        var online_at : String
    )
    data class EditorRecommandItem(
        var id: Int,
        var book_id: Int,
        var name : String,
        var description : String,
        var image1 : String,
        var image2 : String,
        var image3 : String,
        var updated_at : String,
        var is_collected : Int
    )
    data class RankItems(
        var novel: NovelRatingItem,
        var comic: ComicRatingItem,
        var donate: MutableList<RatingItem>
    )
    data class NovelRatingItem(
        var person_all:MutableList<RatingItem>,
        var person_male:MutableList<RatingItem>,
        var person_female:MutableList<RatingItem>
    )
    data class ComicRatingItem(
        var person_all:MutableList<RatingItem>,
        var person_male:MutableList<RatingItem>,
        var person_female:MutableList<RatingItem>
    )
    data class RatingItem(
        var id: Int,
        var name : String,
        var description : String,
        var image1 : String?,
        var image2 : String?,
        var image3 : String?,
        var author : MutableList<AuthorItem>,
        var is_collected : Int
    )
    data class AuthorItem(
        var id: Long,
        var name : String
    )
}