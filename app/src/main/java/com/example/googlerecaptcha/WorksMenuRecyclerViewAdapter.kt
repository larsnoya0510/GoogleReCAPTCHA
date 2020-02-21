package com.example.googlerecaptcha

import android.content.Context
import android.graphics.Color
import android.util.Base64
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.googlerecaptcha.data_class.WorksContentItem
import java.text.DecimalFormat
import android.graphics.BitmapFactory
import android.graphics.Bitmap




class WorksMenuRecyclerViewAdapter(
    private val context: Context,
    private val mOutList: MutableList<WorksContentItem>
) :
    RecyclerView.Adapter<WorksMenuRecyclerViewAdapter.ViewHolder>() {
    var selectPosition: Int = -1
    lateinit var mInList: MutableList<WorksContentItem>
    val inflater: LayoutInflater = LayoutInflater.from(context)
    var mOnItemCheckListener: OnItemCheckListener? = null

    init {
        mInList = mOutList
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view: View = inflater.inflate(R.layout.viewholder_works_menu_recyclerview_item, parent, false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int {
        return mInList.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(position)
    }

    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        var workIconImageView = view.findViewById<ImageView>(R.id.workIconImageView)
        var chapterTextView = view.findViewById<TextView>(R.id.chapterTextView)
        var worksTitleTextView = view.findViewById<TextView>(R.id.worksTitleTextView)
        var dateTextView = view.findViewById<TextView>(R.id.dateTextView)
        var heartCountTextView = view.findViewById<TextView>(R.id.heartCountTextView)
        var rentDateTextView = view.findViewById<TextView>(R.id.rentDateTextView)
        var payFreeTextView = view.findViewById<TextView>(R.id.payFreeTextView)
        var payTypeImageView = view.findViewById<ImageView>(R.id.payTypeImageView)
        var rentConstraintLayout =
            view.findViewById<ConstraintLayout>(R.id.rentConstraintLayout)
        var worksCardView = view.findViewById<CardView>(R.id.worksChapterCardView)
        fun bind(position: Int) {
            Glide.with(context!!)
//                .load(base64DecodeBitmap(mInList[position].imgUrl))
                .load(mInList[position].imgUrl)
                .into(workIconImageView)
            chapterTextView.text = mInList[position].chapter.toString()
            worksTitleTextView.text = mInList[position].title
            dateTextView.text = mInList[position].date
            //過萬判斷
            val mDecimalFormat = DecimalFormat("#,###.##")
            if(mInList[position].heartsCount>10000){
                heartCountTextView.text = mDecimalFormat.format(mInList[position].heartsCount/10000F)
            }
            else {
                heartCountTextView.text = mDecimalFormat.format(mInList[position].heartsCount)
            }
            rentDateTextView.text = "${mInList[position].date}"
            //判斷型態
            if(mInList[position].worksState.readed){
                worksCardView.setCardBackgroundColor(Color.parseColor("#EEEEEE"))
            }
            else{
                worksCardView.setCardBackgroundColor(Color.parseColor("#FFFFFF"))
            }
            //0 未購買 1 已購買  2 已租閱 3 未租閱 4 免費使用 5 可使用 6 已下載
            when(mInList[position].worksState.purchaseState){
                0->{
                    rentConstraintLayout.visibility=View.GONE
                    payTypeImageView.visibility=View.VISIBLE
                    payFreeTextView.visibility=View.VISIBLE
                    payTypeImageView.setImageResource(R.drawable.ic_ccc_48_btn_gift_red)
                    payFreeTextView.setTextColor(Color.parseColor("#BF442F"))
                }
                1->{
                    rentConstraintLayout.visibility=View.GONE
//                    payTypeImageView.visibility=View.GONE
                    payFreeTextView.visibility=View.GONE

                    payTypeImageView.visibility=View.VISIBLE
                    payFreeTextView.visibility=View.GONE
                    payTypeImageView.setImageResource(R.drawable.ic_ccc_48_btn_download)
                }
                2->{
                    rentConstraintLayout.visibility=View.VISIBLE
                    payTypeImageView.visibility=View.GONE
                    payFreeTextView.visibility=View.GONE
                    rentDateTextView.text = "${mInList[position].worksState.rentExpirationDate}到期"
                }
                3->{
                    rentConstraintLayout.visibility=View.GONE
                    payTypeImageView.visibility=View.VISIBLE
                    payFreeTextView.visibility=View.GONE
                    payTypeImageView.setImageResource(R.drawable.ic_ccc_48_btn_point)
                }
                4->{
                    rentConstraintLayout.visibility=View.GONE
                    payTypeImageView.visibility=View.VISIBLE
                    payFreeTextView.visibility=View.VISIBLE
                    payTypeImageView.setImageResource(R.drawable.ic_ccc_48_btn_point)
                    payFreeTextView.setTextColor(Color.parseColor("#176C6A"))
                }
                5->{
                    rentConstraintLayout.visibility=View.GONE
                    payTypeImageView.visibility=View.VISIBLE
                    payFreeTextView.visibility=View.GONE
                    payTypeImageView.setImageResource(R.drawable.ic_ccc_48_btn_point)
                }
                6->{
                    rentConstraintLayout.visibility=View.GONE
                    payTypeImageView.visibility=View.GONE
                    payFreeTextView.visibility=View.GONE
                }
            }

            worksCardView.setOnClickListener {
                mOnItemCheckListener!!.onCheck(mInList[position].chapter,mInList[position])
            }
            payTypeImageView.setOnClickListener {
                mOnItemCheckListener!!.download(mInList[position].chapter,mInList[position])
                //更改下載狀態
//                mInList[position].worksState.purchaseState=6
//                updateWorksListData(mInList)
            }
        }
    }
    fun updateWorksListData(mList:MutableList<WorksContentItem>) {
        mInList=mList
        this.notifyDataSetChanged()
    }
    interface OnItemCheckListener {
        fun onCheck(mChapter:Int,mData: WorksContentItem)
        fun download(mChapter:Int,mData: WorksContentItem)
    }

    fun setOnItemCheckListener(mOnItemCheckListener: OnItemCheckListener) {
        this.mOnItemCheckListener = mOnItemCheckListener
    }
    fun base64DecodeBitmap(mString:String):Bitmap{
        var realData=mString.trim().replace("data:image/png;base64,","")
        val decodedString = Base64.decode(realData, Base64.DEFAULT)
        val decodedByte = BitmapFactory.decodeByteArray(decodedString, 0, decodedString.size)
        return decodedByte
    }
}