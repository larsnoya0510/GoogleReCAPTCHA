//package com.example.googlerecaptcha
//
//
//import android.animation.Animator
//import android.content.Context
//import android.content.Intent
//import android.graphics.Color
//import android.graphics.Rect
//import android.net.Uri
//import android.os.Bundle
//import android.util.DisplayMetrics
//import android.view.*
//import android.widget.Button
//import androidx.appcompat.app.AlertDialog
//import androidx.fragment.app.Fragment
//import androidx.coordinatorlayout.widget.CoordinatorLayout
//import androidx.lifecycle.Observer
//import androidx.lifecycle.ViewModelProviders
//import com.bumptech.glide.Glide
//import com.example.googlerecaptcha.data_class.WorksBannerItem
//import com.example.googlerecaptcha.viewmodel.WorksResponseDataViewModel
//import com.google.android.material.appbar.AppBarLayout
//import android.widget.FrameLayout
//import android.widget.Toast
//import androidx.cardview.widget.CardView
//import com.example.googlerecaptcha.viewmodel.WorksAppbarPositionViewModel
//import com.google.android.material.appbar.CollapsingToolbarLayout
//import kotlinx.android.synthetic.main.fragment_works.view.appbar
//import kotlinx.android.synthetic.main.fragment_works.view.backCollapseImageView
//import kotlinx.android.synthetic.main.fragment_works.view.donateButtonConstraintLayout
//import kotlinx.android.synthetic.main.fragment_works.view.shareCollapseImageView
//import kotlinx.android.synthetic.main.fragment_works.view.shareExpandImageView
//import kotlinx.android.synthetic.main.fragment_works.view.toolNestedScrollView
//import kotlinx.android.synthetic.main.fragment_works.view.toolbarCollapseConstraintLayout
//import kotlinx.android.synthetic.main.fragment_works.view.toolbarConstraintLayout
//import kotlinx.android.synthetic.main.fragment_works.view.toolbarExpandConstraintLayout
//import kotlinx.android.synthetic.main.fragment_works.view.toolbarImageView
//import kotlinx.android.synthetic.main.fragment_works.view.worksCategoryNameTextView
//import kotlinx.android.synthetic.main.fragment_works.view.worksCollectStateCollapseImageView
//import kotlinx.android.synthetic.main.fragment_works.view.worksCollectStateCollapseLottieAnimationView
//import kotlinx.android.synthetic.main.fragment_works.view.worksCollectStateExpandImageView
//import kotlinx.android.synthetic.main.fragment_works.view.worksCollectStateExpandLottieAnimationView
//import kotlinx.android.synthetic.main.fragment_works.view.worksTabLayout
//import kotlinx.android.synthetic.main.fragment_works.view.worksViewPager
//import kotlinx.android.synthetic.main.fragment_works_fragment_new.view.*
//import kotlinx.android.synthetic.main.viewholder_announcement_recyclerview_item.view.*
//import java.net.URLEncoder
//
//
//class WorksFragment : Fragment() {
//    enum class State {
//        EXPANDED,
//        COLLAPSED,
//        IDLE
//    }
//    lateinit var  mWorksAppbarPositionViewModel: WorksAppbarPositionViewModel
//    lateinit var mWorksAppbarPositionViewModelObserver: Observer<Int>
//    lateinit var  mWorksResponseDataViewModel: WorksResponseDataViewModel
//    lateinit var mWorksResponseDataTriggerObserver: Observer<Int>
//    lateinit var mWorksResponseDataCollectStateObserver: Observer<Boolean>
//    lateinit var mWorkFragmentList: WorkFragmentList
//    private var mCurrentState = State.IDLE
//    lateinit var workFragmentRootView:View
//    var workId=0
//    lateinit var mTablayoutPageTitleList:ArrayList<String>
//    lateinit var mTablayoutFragmentList:ArrayList<Fragment>
//    lateinit var mWorksViewPagerAdapter: WorksViewPagerAdapter
//    var mCollectState=false
//    var nowToolbarPosition=0
//
//    override fun onCreateView(
//        inflater: LayoutInflater, container: ViewGroup?,
//        savedInstanceState: Bundle?
//    ): View? {
////        workFragmentRootView= inflater.inflate(R.layout.fragment_works, container, false)
//        workFragmentRootView= inflater.inflate(R.layout.fragment_works_fragment_new, container, false)
//        activity!!.getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS)
//        //watchAppBarPosition
//        mWorksAppbarPositionViewModel= ViewModelProviders.of(activity!!).get(
//            WorksAppbarPositionViewModel::class.java)
//        mWorksAppbarPositionViewModelObserver = Observer {
//
//        }
//        mWorksAppbarPositionViewModel.getData().observe(this, mWorksAppbarPositionViewModelObserver)
//        //viewmodel
//        mWorksResponseDataViewModel= ViewModelProviders.of(activity!!).get(
//            WorksResponseDataViewModel::class.java)
//        mWorksResponseDataTriggerObserver = Observer {
//            when(it){
//                200-> {
//                    mWorksResponseDataViewModel.setWorksResponseDataTrigger(0)
//                    settingBanner(mWorksResponseDataViewModel.getWorksResponseData().value!!.data.worksBanner)
//                }
//                else ->{
//
//                }
//            }
//        }
//        mWorksResponseDataViewModel.getData().observe(this, mWorksResponseDataTriggerObserver)
//        mWorksResponseDataCollectStateObserver= Observer {
//            mCollectState=it
//            checkCollectState(it)
//        }
//        mWorksResponseDataViewModel.getWorksCollectionState().observe(this, mWorksResponseDataCollectStateObserver)
//        //TODO 假資料
//        mWorksResponseDataViewModel.RequestWorksData()
//
//        mWorkFragmentList=WorkFragmentList()
//        workId=arguments?.getInt("WorksId") ?:0
//        //設定Appbar動畫
//        initAppbarView()
//        //設定tablayout
//        mTablayoutPageTitleList=ArrayList<String>()
//        mTablayoutPageTitleList.add(context!!.resources.getString(R.string.works_tablayout_title_menu))
//        mTablayoutPageTitleList.add(context!!.resources.getString(R.string.works_tablayout_title_informaion))
//        mTablayoutPageTitleList.add(context!!.resources.getString(R.string.works_tablayout_title_support))
//        mTablayoutFragmentList=ArrayList<Fragment>()
//        mTablayoutFragmentList.add(mWorkFragmentList.mMenuFragment)
//        mTablayoutFragmentList.add(mWorkFragmentList.mInformationFragment)
//        mTablayoutFragmentList.add(mWorkFragmentList.mSupportFragment)
//        mWorksViewPagerAdapter=WorksViewPagerAdapter(mTablayoutFragmentList,mTablayoutPageTitleList,this.childFragmentManager)
//        workFragmentRootView.worksViewPager.adapter=mWorksViewPagerAdapter
//        workFragmentRootView.worksViewPager.offscreenPageLimit=2
////        workFragmentRootView.worksViewPager.setCurrentItem(0)
//
//        workFragmentRootView.backCollapseImageView.setOnClickListener { clickBackIcon() }
//        workFragmentRootView.backExpandImageView.setOnClickListener { clickBackIcon() }
//        workFragmentRootView.shareExpandImageView.setOnClickListener {
//            createShareDialog()
//        }
//        workFragmentRootView.shareCollapseImageView.setOnClickListener {
//            createShareDialog()
//        }
//
//        return workFragmentRootView
//    }
//    private fun checkCollectState(mState:Boolean) {
//
//        workFragmentRootView.worksCollectStateExpandImageView.isSelected=mState
//        workFragmentRootView.worksCollectStateCollapseImageView.isSelected=mState
//
//        if (mCurrentState==State.EXPANDED) {
//            if (mState == false) {
//                workFragmentRootView.worksCollectStateExpandLottieAnimationView.setAnimation("ccc-32-btn-star-animated.json")
//                workFragmentRootView.worksCollectStateExpandLottieAnimationView.playAnimation()
//                (activity as WorksActivity).snackbarSetting(false)
//            } else if (mState == true) {
//                workFragmentRootView.worksCollectStateExpandLottieAnimationView.setAnimation("ccc-32-btn-star-on-animated.json")
//                workFragmentRootView.worksCollectStateExpandLottieAnimationView.playAnimation()
//                (activity as WorksActivity).snackbarSetting(true)
//            }
//        }
//        else if (mCurrentState==State.COLLAPSED) {
//            if (mState == false) {
//                workFragmentRootView.worksCollectStateCollapseLottieAnimationView.setAnimation("ccc-24-nav-star-animated.json")
//                workFragmentRootView.worksCollectStateCollapseLottieAnimationView.playAnimation()
//                (activity as WorksActivity).snackbarSetting(false)
//            } else if (mState == true) {
//                workFragmentRootView.worksCollectStateCollapseLottieAnimationView.setAnimation("ccc-24-card-star-gold-animated-bounce.json")
//                workFragmentRootView.worksCollectStateCollapseLottieAnimationView.playAnimation()
//                (activity as WorksActivity).snackbarSetting(true)
//            }
//        }
//        else{
//            if (mState == false) {
//                workFragmentRootView.worksCollectStateExpandLottieAnimationView.setAnimation("ccc-32-btn-star-animated.json")
//                workFragmentRootView.worksCollectStateExpandLottieAnimationView.playAnimation()
//                (activity as WorksActivity).snackbarSetting(false)
//            } else if (mState == true) {
//                workFragmentRootView.worksCollectStateExpandLottieAnimationView.setAnimation("ccc-32-btn-star-on-animated.json")
//                workFragmentRootView.worksCollectStateExpandLottieAnimationView.playAnimation()
//                (activity as WorksActivity).snackbarSetting(true)
//            }
//        }
//
//    }
//    private fun createShareDialog() {
//        var str=mWorksResponseDataViewModel.getWorksResponseData().value!!.data.worksBanner.shareLink
//        var shareAlertDialogBuilder: AlertDialog.Builder
//        shareAlertDialogBuilder = AlertDialog.Builder(this.context!!, R.style.DialogTheme)
//
//        val inflater = LayoutInflater.from(this.context)
//        val mView = inflater.inflate(R.layout.alertdialog_share, null)
//
//        shareAlertDialogBuilder.setView(mView)
//        var shareAlertDialog = shareAlertDialogBuilder.create()
//        var shareLinkCardView= mView.findViewById<CardView>(R.id.shareLinkCardView)
//        shareLinkCardView.setOnClickListener {
//            var clipboard =  context!!.getSystemService(Context.CLIPBOARD_SERVICE)  as android.content.ClipboardManager
//
//            var clip = android.content.ClipData.newPlainText("text label",str)
//            clipboard.setPrimaryClip(clip)
//            Toast.makeText(context,context!!.resources.getText(R.string.copyed_to_clipboardmanager),Toast.LENGTH_SHORT).show()
//        }
//        var shareFacebookCardView= mView.findViewById<CardView>(R.id.shareFacebookCardView)
//        shareFacebookCardView.setOnClickListener {
//            var intent = Intent(Intent.ACTION_SEND)
//            intent.setType("text/plain")
//            intent.putExtra(Intent.EXTRA_TEXT, str)
//            var facebookAppFound = false
//            val matches = context!!.getPackageManager().queryIntentActivities(intent, 0)
//            for (info in matches)
//            {
//                if (info.activityInfo.packageName.toLowerCase().startsWith("com.facebook.katana"))
//                {
//                    intent.setPackage(info.activityInfo.packageName)
//                    facebookAppFound = true
//                    break
//                }
//            }
//            if (!facebookAppFound)
//            {
//                val sharerUrl = "https://www.facebook.com/sharer/sharer.php?u=" + str
//                intent = Intent(Intent.ACTION_VIEW, Uri.parse(sharerUrl))
//            }
//            startActivity(intent)
//        }
//        var shareLineCardView= mView.findViewById<CardView>(R.id.shareLineCardView)
//        shareLineCardView.setOnClickListener {
//            var intent = Intent(Intent.ACTION_SEND)
//            intent.setType("text/plain")
//            intent.putExtra(Intent.EXTRA_TEXT, str)
//            var lineAppFound = false
//            val matches = context!!.getPackageManager().queryIntentActivities(intent, 0)
//            for (info in matches)
//            {
//                if (info.activityInfo.packageName.toLowerCase().startsWith("jp.naver.line"))
//                {
//                    intent.setPackage(info.activityInfo.packageName)
//                    lineAppFound = true
//                    break
//                }
//            }
//            if (!lineAppFound)
//            {
//                val sharerUrl = "http://line.naver.jp/R/msg/text/?" + URLEncoder.encode(str,"utf-8")
//                intent = Intent(Intent.ACTION_VIEW, Uri.parse(sharerUrl))
//            }
//            startActivity(intent)
//        }
//        var shareMoreCardView= mView.findViewById<CardView>(R.id.shareMoreCardView)
//        shareMoreCardView.setOnClickListener {
//
//            var shareIntent = Intent(Intent.ACTION_SEND)
//            shareIntent.setType("text/plain")
//            shareIntent.putExtra(Intent.EXTRA_TEXT, str)
//            startActivity(shareIntent)
//        }
//        var shareCancelButton = mView.findViewById<Button>(R.id.workShareCancelButton)
//        shareCancelButton.setOnClickListener {
//            shareAlertDialog.dismiss()
//        }
//        shareAlertDialog.show()
//
//        val metrics = DisplayMetrics()
//        val windowManager =
//            this.context!!.getSystemService(Context.WINDOW_SERVICE) as WindowManager
//        windowManager.defaultDisplay.getMetrics(metrics)
//        var layoutParams = mView.getLayoutParams() as FrameLayout.LayoutParams
//        layoutParams.width = metrics.widthPixels
//        mView.setLayoutParams(layoutParams)
//        shareAlertDialog.window!!.setGravity(Gravity.BOTTOM)
//        var dialogWindow=shareAlertDialog.window!!
//        dialogWindow.setWindowAnimations(R.style.Dialog_anim)
//    }
//
//    private fun settingBanner(worksBanner: WorksBannerItem) {
//        var imgUrl=""
//        when(Global.isPad){
//            true->{
//                imgUrl=worksBanner.tabletImgUrl
//            }
//            false ->{
//                imgUrl=worksBanner.mobileImgUrl
//            }
//        }
//        workFragmentRootView.worksTabLayout.setupWithViewPager(workFragmentRootView.worksViewPager)
//        workFragmentRootView.worksTabLayout.getTabAt(0)!!.select()
//        Glide.with(context!!)
//            .load(imgUrl)
//            .into( workFragmentRootView.toolbarImageView)
//        workFragmentRootView.worksCollectStateExpandImageView.isSelected=worksBanner.collectState
//        workFragmentRootView.worksCategoryNameTextView.text = worksBanner.categoryName
//        //設定收藏狀態
//        mCollectState=worksBanner.collectState
//        workFragmentRootView.worksCollectStateExpandImageView.isSelected=mCollectState
//        workFragmentRootView.worksCollectStateExpandImageView.setOnClickListener {
//            if(mCollectState==true){
//                workFragmentRootView.worksCollectStateExpandLottieAnimationView.visibility=View.VISIBLE
//                mWorksResponseDataViewModel.requestSetWorksCollectionState(false)
//            }
//            else if(mCollectState==false){
//                workFragmentRootView.worksCollectStateExpandLottieAnimationView.visibility=View.VISIBLE
//                mWorksResponseDataViewModel.requestSetWorksCollectionState(true)
//            }
//        }
//        workFragmentRootView.worksCollectStateExpandLottieAnimationView.addAnimatorListener(object : Animator.AnimatorListener{
//            override fun onAnimationRepeat(p0: Animator?) {
//            }
//            override fun onAnimationEnd(p0: Animator?) {
//                workFragmentRootView.worksCollectStateExpandLottieAnimationView.visibility=View.INVISIBLE
//                workFragmentRootView.worksCollectStateExpandImageView.visibility=View.VISIBLE
//                workFragmentRootView.worksCollectStateExpandLottieAnimationView.cancelAnimation()
////                workFragmentRootView.worksCollectStateExpandLottieAnimationView.removeAnimatorListener(this)
//            }
//            override fun onAnimationCancel(p0: Animator?) {
//                workFragmentRootView.worksCollectStateExpandLottieAnimationView.visibility=View.INVISIBLE
//                workFragmentRootView.worksCollectStateExpandImageView.visibility=View.VISIBLE
//            }
//            override fun onAnimationStart(p0: Animator?) {
//                workFragmentRootView.worksCollectStateExpandImageView.visibility=View.INVISIBLE
//            }
//        })
//
//        workFragmentRootView.worksCollectStateCollapseImageView.isSelected=mCollectState
//        workFragmentRootView.worksCollectStateCollapseImageView.setOnClickListener {
//            if(mCollectState==true){
//                workFragmentRootView.worksCollectStateCollapseLottieAnimationView.visibility=View.VISIBLE
//                mWorksResponseDataViewModel.requestSetWorksCollectionState(false)
//            }
//            else if(mCollectState==false){
//                workFragmentRootView.worksCollectStateCollapseLottieAnimationView.visibility=View.VISIBLE
//                mWorksResponseDataViewModel.requestSetWorksCollectionState(true)
//            }
//        }
//        workFragmentRootView.worksCollectStateCollapseLottieAnimationView.addAnimatorListener(object : Animator.AnimatorListener{
//            override fun onAnimationRepeat(p0: Animator?) {
//            }
//            override fun onAnimationEnd(p0: Animator?) {
//                workFragmentRootView.worksCollectStateCollapseLottieAnimationView.visibility=View.INVISIBLE
//                workFragmentRootView.worksCollectStateCollapseImageView.visibility=View.VISIBLE
//                workFragmentRootView.worksCollectStateCollapseLottieAnimationView.cancelAnimation()
////                workFragmentRootView.worksCollectStateCollapseLottieAnimationView.removeAnimatorListener(this)
//
//            }
//            override fun onAnimationCancel(p0: Animator?) {
//                workFragmentRootView.worksCollectStateCollapseLottieAnimationView.visibility=View.INVISIBLE
//                workFragmentRootView.worksCollectStateCollapseImageView.visibility=View.VISIBLE
//            }
//            override fun onAnimationStart(p0: Animator?) {
//                workFragmentRootView.worksCollectStateCollapseImageView.visibility=View.INVISIBLE
//            }
//        })
//
//    }
//
//
//    companion object {
//        @JvmStatic
//        fun newInstance() = WorksFragment()
//    }
//    private fun clickBackIcon() {
//        if (this.fragmentManager!!.backStackEntryCount > 0) {
//            this.fragmentManager!!.popBackStack()
//        } else {
//            this.activity!!.finish()
//        }
//    }
//
//    fun initAppbarView(){
////        activity!!.getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS)
//        workFragmentRootView.appbar.post(Runnable {
//            var frame = Rect()
//            activity!!.getWindow().getDecorView().getWindowVisibleDisplayFrame(frame)
//            var statusBarHeight = frame.top
//            val metrics = DisplayMetrics()
//            val windowManager = this.context!!.getSystemService(Context.WINDOW_SERVICE) as WindowManager
//            windowManager.defaultDisplay.getMetrics(metrics)
//            //設定標題列寬高與狀態列距離
//            val width = metrics.widthPixels
////            println("### metrics widthPixels ${metrics.widthPixels}")
//            var mParams = workFragmentRootView.appbar.layoutParams as CoordinatorLayout.LayoutParams
//            mParams.width = width
////            mParams.topMargin = statusBarHeight
//            if(Global.isPad)  {
//                mParams.height = (width *1F /1.9).toInt()
//            }
//            else{
//                mParams.height = width
//            }
//            workFragmentRootView.appbar.setLayoutParams(mParams)
//        })
//        workFragmentRootView.appbar.addOnOffsetChangedListener(object : AppBarLayout.OnOffsetChangedListener{
//            fun onStateChanged(appBarLayout: AppBarLayout, state: State){
//                when(state){
////                    State.EXPANDED->{
////                        activity!!.getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS)
////                        workFragmentRootView.toolbarCollapseConstraintLayout.visibility=View.GONE
////                        workFragmentRootView.toolbarExpandConstraintLayout.visibility=View.VISIBLE
////                        workFragmentRootView.toolbarExpandConstraintLayout.setBackgroundResource(R.drawable.shape_works_toolbar_gradient)
////                        workFragmentRootView.worksCollectStateCollapseImageView.setImageResource(R.drawable.works_collection_toolbar_expand_selector)
////                        setToolLinearViewHeight(false)
////                    }
////                    State.COLLAPSED->{
////                        activity!!.getWindow().clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS)
////                        workFragmentRootView.toolbarCollapseConstraintLayout.visibility=View.VISIBLE
////                        workFragmentRootView.toolbarExpandConstraintLayout.visibility=View.GONE
////                        workFragmentRootView.toolbarCollapseConstraintLayout.setBackgroundColor(Color.parseColor("#FFFFFF"))
////                        workFragmentRootView.worksCollectStateCollapseImageView.setImageResource(R.drawable.works_collection_toolbar_collpse_state_selector)
////                        setToolLinearViewHeight(false)
////                    }
//                    //===
//                    State.EXPANDED->{
//                        workFragmentRootView.toolbarExpandConstraintLayout.setBackgroundResource(R.drawable.shape_works_toolbar_gradient)
//                        workFragmentRootView.toolbar.setBackgroundColor(Color.TRANSPARENT)
//
//                        workFragmentRootView.toolbarCollapseConstraintLayout.visibility=View.GONE
//                        workFragmentRootView.toolbarConstraintLayout.visibility=View.VISIBLE
//                    }
//                    State.COLLAPSED->{
//                        workFragmentRootView.toolbar.setBackgroundColor(Color.WHITE)
//                        workFragmentRootView.toolbarCollapseConstraintLayout.visibility=View.VISIBLE
//                        workFragmentRootView.toolbarConstraintLayout.visibility=View.GONE
//                    }
//                }
//            }
//            override fun onOffsetChanged(p0: AppBarLayout?, i: Int) {
//                mWorksAppbarPositionViewModel.setPosition(i)
////                println("### Math.abs(i) ${Math.abs(i)}  workFragmentRootView.appbar.getTotalScrollRange() ${workFragmentRootView.appbar.getTotalScrollRange()}")
////                println("### mCurrentState ${mCurrentState} ")
//                if (Math.abs(i) <=0)
//                {
//                    if (mCurrentState !== State.EXPANDED)
//                    {
//                        onStateChanged(workFragmentRootView.appbar, State.EXPANDED)
//                    }
//                    mCurrentState = State.EXPANDED
//                }
//                else if (Math.abs(i) >= workFragmentRootView.appbar.getTotalScrollRange())
//                {
//
//                    if (mCurrentState !== State.COLLAPSED)
//                    {
//                        onStateChanged(workFragmentRootView.appbar, State.COLLAPSED)
//                    }
//                    mCurrentState = State.COLLAPSED
//                }
//                else
//                {
//                    if (mCurrentState !== State.IDLE)
//                    {
//                        onStateChanged(workFragmentRootView.appbar, State.IDLE)
//                    }
//                    mCurrentState = State.IDLE
//
//                    //計算淡出淡入
//                    //取得狀態列高度
//                    var frame = Rect()
//                    activity!!.getWindow().getDecorView().getWindowVisibleDisplayFrame(frame)
//                    var statusBarHeight = frame.top
////                    println("##### TotalScroll ${workFragmentRootView.appbar.getTotalScrollRange()}")
////                    println("##### nowToolbarPosition $nowToolbarPosition")
////                    println("#####  Math.abs(i) ${Math.abs(i)}")
////                    var scale = ((Math.abs(i)) * 1F+statusBarHeight)/workFragmentRootView.appbar.getTotalScrollRange()
//                    var scale = ((Math.abs(i)) * 1F)/workFragmentRootView.appbar.getTotalScrollRange()
//                    var alpha = (255 * scale).toInt()
////                    var red= 255-((255-53) * (1-scale)).toInt()
////                    var green= 255-((255-47)*  (1-scale)).toInt()
////                    var blue=  255-((255-46) *  (1-scale)).toInt()
//                    var gray = (255 * scale).toInt()
////                    println("###  alpha ${alpha} red ${red}  green ${green} blue ${blue} scale ${scale}")
////                    println("### Math.abs(i) ${Math.abs(i)}  nowToolbarPosition ${nowToolbarPosition}")
////                    println("### workFragmentRootView.toolbar. ${workFragmentRootView.toolbar.background} ")
//                    if(Math.abs(i)>nowToolbarPosition && mCurrentState == State.IDLE) {
////                        workFragmentRootView.toolbarExpandConstraintLayout.setBackgroundColor(Color.argb(alpha, gray, gray, gray))
//                        workFragmentRootView.toolbar.setBackgroundColor(Color.argb(alpha, gray, gray, gray))
//                    }
//                    else if(Math.abs(i)<=nowToolbarPosition && mCurrentState == State.IDLE) {
//
////                        workFragmentRootView.toolbarExpandConstraintLayout.setBackgroundColor(Color.argb(alpha, red, green, blue))
////                        workFragmentRootView.toolbar.setBackgroundColor(Color.argb(alpha, red, green, blue))
//                        workFragmentRootView.toolbar.setBackgroundColor(Color.argb(alpha, gray, gray, gray))
//                        workFragmentRootView.toolbarCollapseConstraintLayout.visibility=View.GONE
//                        workFragmentRootView.toolbarConstraintLayout.visibility=View.VISIBLE
//                    }
//                    nowToolbarPosition=Math.abs(i)
//
//                }
//            }
//        })
//    }
//    private fun setToolLinearViewHeight(mType:Boolean) {
//        workFragmentRootView.toolNestedScrollView.post(Runnable {
//            //取得狀態列高度
//            var frame = Rect()
//            activity!!.getWindow().getDecorView().getWindowVisibleDisplayFrame(frame)
//            var statusBarHeight = frame.top
//            //設定標題列寬高與狀態列距離
//            val width = workFragmentRootView.toolNestedScrollView.getWidth()
////            val height = (width * 0.14).toInt() //scale : 64/364
//            var mLayoutParams =
//                workFragmentRootView.toolNestedScrollView.layoutParams as CoordinatorLayout.LayoutParams
//            mLayoutParams.width = width
////            mLayoutParams.height = height
//            when (mType) {
//                true ->{
////                    mLayoutParams.topMargin = statusBarHeight
//                    mLayoutParams.bottomMargin=statusBarHeight
//                }
//                false->{
//                    mLayoutParams.bottomMargin=0
////                    mLayoutParams.height
//                }
//            }
//            workFragmentRootView.toolNestedScrollView.setLayoutParams(mLayoutParams)
//
//        })
//    }
//    fun convertPixelToDp(pixel: Int): Float {
//        val metrics = DisplayMetrics()
//        val windowManager =
//            context!!.getSystemService(Context.WINDOW_SERVICE) as WindowManager
//        windowManager.defaultDisplay.getMetrics(metrics)
//
//        var DEVICE_DENSITY_DPI = metrics.densityDpi
//        return (pixel / (DEVICE_DENSITY_DPI / 160f)).toFloat()
//    }
//    fun getAppBarAndTabLayoutHeight():Int{
//        var appBarHeight=workFragmentRootView.appbar.height
//        var tabLayoutHeight=workFragmentRootView.worksTabLayout.height
//        return appBarHeight+tabLayoutHeight
//    }
//    fun setDonateSupportBottonVisible(mType:Boolean){
//        when(mType){
//            true->{
//                workFragmentRootView.donateButtonConstraintLayout.visibility=View.VISIBLE
//
//            }
//            false ->{
//                workFragmentRootView.donateButtonConstraintLayout.visibility=View.GONE
//            }
//        }
//    }
//}
//
