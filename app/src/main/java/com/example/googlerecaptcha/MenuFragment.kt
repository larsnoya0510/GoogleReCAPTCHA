package com.example.googlerecaptcha


import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.example.googlerecaptcha.DB.DB_SeriesDetialInfo
import com.example.googlerecaptcha.DB.DB_SeriesInfo
import com.example.googlerecaptcha.DB.OfflineDBHelper
import com.example.googlerecaptcha.data_class.ImageLoadDataSetNew
import com.example.googlerecaptcha.data_class.WorksContentItem
import com.example.googlerecaptcha.data_class.WorksResponseData
import com.example.googlerecaptcha.data_class.WorksState
import com.example.googlerecaptcha.fake_data.FakeWorksData
import com.example.googlerecaptcha.viewmodel.ChapterDownloadViewModel
import com.example.googlerecaptcha.viewmodel.WorksResponseDataViewModel
import kotlinx.android.synthetic.main.fragment_menu.view.*
import com.google.gson.Gson
import kotlinx.android.synthetic.main.fragment_menu.*
import kotlinx.android.synthetic.main.fragment_menu.view.clearOfflineButton
import java.io.File
import java.nio.file.Files.delete
import java.nio.file.Files.isDirectory




class MenuFragment : Fragment() {
    //假資料
    var WorkId=2
    lateinit var  mWorksResponseDataViewModel: WorksResponseDataViewModel
    lateinit var mWorksResponseDataObserver: Observer<WorksResponseData>
    lateinit var mWorksMenuRecyclerViewAdapter : WorksMenuRecyclerViewAdapter
    lateinit var mChapterDownloadViewModel: ChapterDownloadViewModel
    lateinit var mChapterImageListDataObserver: Observer<MutableList<ImageLoadDataSetNew>>
    lateinit var mWorkFragmentList: WorkFragmentList
    var orderType=0
    lateinit var menuFragmentRootView: View
    var onlineDataList=mutableListOf<WorksContentItem>()
    var mNowSelectChapter=-1
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
//        mWorkFragmentList=(parentFragment as WorksFragment).mWorkFragmentList
        menuFragmentRootView= inflater.inflate(R.layout.fragment_menu, container, false)

        mWorksResponseDataViewModel= ViewModelProviders.of(activity!!).get(
            WorksResponseDataViewModel::class.java)
        mChapterDownloadViewModel= ViewModelProviders.of(activity!!).get(
            ChapterDownloadViewModel::class.java)
        mWorksMenuRecyclerViewAdapter=WorksMenuRecyclerViewAdapter(context!!, mutableListOf<WorksContentItem>())
        menuFragmentRootView.worksMenuRecyclerView.layoutManager=LinearLayoutManager(this.context)
        menuFragmentRootView.worksMenuRecyclerView.adapter=mWorksMenuRecyclerViewAdapter
        mWorksResponseDataObserver = Observer {
            if(it.data.menuSets.serialSatate==0) {
                menuFragmentRootView.serialStateTextView.text="已完結"
                menuFragmentRootView.serialStateTextView.setTextColor(Color.parseColor("#E69312"))
                menuFragmentRootView.serialStateImageView.setImageResource(R.drawable.ic_ccc_48_product_finish)
            }
            else if(it.data.menuSets.serialSatate==1) {
                menuFragmentRootView.serialStateTextView.text="連載中"
                menuFragmentRootView.serialStateTextView.setTextColor(Color.parseColor("#221815"))
                menuFragmentRootView.serialStateImageView.setImageResource(R.drawable.ic_ccc_48_product_serialize)
            }
            menuFragmentRootView.totalChaptersTextView.text="共${it.data.menuSets.totalNumber}話"
            if(it.data.menuSets.orderByType==0) {
                orderType=0
                menuFragmentRootView.worksListOrderByimageView.setImageResource(R.drawable.ic_ccc_24_serial_order)
                var tempList=it.data.menuSets.worksContent
                mWorksMenuRecyclerViewAdapter.updateWorksListData(tempList)
                onlineDataList=tempList
            }
            else if(it.data.menuSets.orderByType==1) {
                orderType=1
                menuFragmentRootView.worksListOrderByimageView.setImageResource(R.drawable.ic_ccc_24_serial_order_desc)
                var tempList=it.data.menuSets.worksContent.reversed().toMutableList()
                onlineDataList=tempList
                mWorksMenuRecyclerViewAdapter.updateWorksListData(tempList)

            }
//            mWorksMenuRecyclerViewAdapter.updateWorksListData(it.data.menuSets.worksContent)
        }
        mWorksResponseDataViewModel.getWorksResponseData().observe(this, mWorksResponseDataObserver)
        mChapterImageListDataObserver = Observer {
            //                    Toast.makeText(context,"Download",Toast.LENGTH_SHORT).show()
            var SqlConnect= OfflineDBHelper.getInstance(context!!.applicationContext)
            //觸發SQL
            var seriesList= SqlConnect.querySeriesByChapter(mNowSelectChapter)
            if(seriesList.size<=0) {
                //寫入資料
                var mData=mChapterDownloadViewModel.getChapterInfoData().value!!
                var seriesInfoData = DB_SeriesInfo()
                seriesInfoData.workId = WorkId.toLong()
                seriesInfoData.seriesNo = mNowSelectChapter.toLong()
                seriesInfoData.date = mData.date
                seriesInfoData.title = mData.title
                seriesInfoData.imgUrl = mData.imgUrl
                seriesInfoData.like = mData.like.toLong()
                seriesInfoData.seriesDetialList
                seriesInfoData.state = mData.state.toLong()
                var mRead = mData.read
                seriesInfoData.read = mRead.toLong()
                seriesInfoData.rentDate = mData.rentDate
                var DB_SeriesInfoList = mutableListOf<DB_SeriesInfo>()
                DB_SeriesInfoList.add(seriesInfoData)

                var resule = SqlConnect.insertSeries(
                    DB_SeriesInfoList,it
//                    FakeWorksData.ChapterImageDataNew
                )
                if(resule>=1L){
                    Toast.makeText(context, "下載完畢", Toast.LENGTH_SHORT).show()
//                    mWorksResponseDataViewModel.changeDataPurchaseState(mNowSelectChapter)
                }
                else if(resule <=-1L){
                    Toast.makeText(context, "下載失敗", Toast.LENGTH_SHORT).show()
                }
            }
            else{
                Toast.makeText(context, "已下載", Toast.LENGTH_SHORT).show()
//                mWorksResponseDataViewModel.changeDataPurchaseState(mNowSelectChapter)
            }
        }
        mChapterDownloadViewModel.getChapterImageListData().observe(this, mChapterImageListDataObserver)
        menuFragmentRootView.worksListOrderByimageView.setOnClickListener {
            when(orderType){
                0->{
                    mWorksResponseDataViewModel.setOrderByType(1)
                }
                1->{
                    mWorksResponseDataViewModel.setOrderByType(0)
                }
            }

        }
        menuFragmentRootView.clearOfflineButton.setOnClickListener {
            var SqlConnect= OfflineDBHelper.getInstance(context!!.applicationContext)
            var result= SqlConnect.delete(WorkId)
            if(result==1){
                Toast.makeText(context, "刪除完畢", Toast.LENGTH_SHORT).show()
                getOfflineSqlData()
                //清除內部檔案
                val dir = this.context!!.getFilesDir()
                if (dir.isDirectory) {
                    val children = dir.list()
                    for (i in children.indices) {
                        File(dir, children[i]).delete()
                    }
                }
            }
            else{
                Toast.makeText(context, "刪除失敗", Toast.LENGTH_SHORT).show()
            }
        }
        mWorksMenuRecyclerViewAdapter.setOnItemCheckListener(object:WorksMenuRecyclerViewAdapter.OnItemCheckListener{
            override fun download(mChapter: Int, mData: WorksContentItem) {
                if(mData.worksState.purchaseState==1){
                    var mChapterInfo = DB_SeriesInfo()
                    mChapterInfo.workId = WorkId.toLong()
                    mChapterInfo.seriesNo = mData.chapter.toLong()
                    mChapterInfo.date = mData.date
                    mChapterInfo.title = mData.title
                    mChapterInfo.imgUrl = mData.imgUrl
                    mChapterInfo.like = mData.heartsCount.toLong()
                    mChapterInfo.seriesDetialList
                    mChapterInfo.state = mData.worksState.purchaseState.toLong()
                    mChapterDownloadViewModel.setChapterInfoData(mChapterInfo)
                    mChapterDownloadViewModel.RequestImageData()
                    mNowSelectChapter=mChapter
                }

            }

            override fun onCheck(mChapter: Int,mData: WorksContentItem) {
                if(mData.worksState.purchaseState==1 || mData.worksState.purchaseState==2)
                {

                }
                else if(mData.worksState.purchaseState==6){
                    if(menuFragmentRootView.OfflineSwitch.isChecked){
                        var mIntent= Intent(context,ShowImgActivity::class.java)
                        mIntent.putExtra("Chapter",mChapter)
                        startActivity(mIntent)
                    }

                }
            }
        })

        mWorksResponseDataViewModel.RequestWorksData()

        //==offline 判斷
        menuFragmentRootView.OfflineSwitch.setOnCheckedChangeListener { compoundButton, isOn ->
            if(isOn==true){
                //取得離線資料
                getOfflineSqlData()
            }
            else{
                //取得線上資料
                mWorksMenuRecyclerViewAdapter.updateWorksListData(onlineDataList)
                clearOfflineButton.visibility=View.INVISIBLE
            }

        }
        return menuFragmentRootView
    }

    private fun getOfflineSqlData() {
        var offlineDataList = mutableListOf<WorksContentItem>()
        var SqlConnect = OfflineDBHelper.getInstance(context!!.applicationContext)
        //觸發SQL
        var seriesList = SqlConnect.querySeriesAll()
        for (i in 0 until seriesList.size) {
            var mRead = seriesList[i].read == 1L
            var mWorkState = WorksState(
                seriesList[i].state.toInt(),
                mRead,
                seriesList[i].rentDate,
                null, null, null
            )
            var mWorksContentItem = WorksContentItem(
                seriesList[i].seriesNo.toInt(),
                seriesList[i].date,
                seriesList[i].title,
                seriesList[i].imgUrl,
                seriesList[i].like.toInt(),
                mWorkState
            )
            offlineDataList.add(mWorksContentItem)
        }
        mWorksMenuRecyclerViewAdapter.updateWorksListData(offlineDataList)
        clearOfflineButton.visibility = View.VISIBLE
    }

    companion object {
        @JvmStatic
        fun newInstance() = MenuFragment()
    }

    private fun clickBackIcon() {
        if (this.fragmentManager!!.backStackEntryCount > 0) {
            this.fragmentManager!!.popBackStack()
        } else {
            this.activity!!.finish()
        }
    }
}
