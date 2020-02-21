package com.example.googlerecaptcha.DB

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.util.Log
import com.example.googlerecaptcha.data_class.ImageLoadDataSet
import com.example.googlerecaptcha.data_class.ImageLoadDataSetNew
import org.jetbrains.anko.db.ManagedSQLiteOpenHelper


class OfflineDBHelper(var context: Context) :
        ManagedSQLiteOpenHelper(context,"offline.db", null, 1) {
        var tableList: List<String> = listOf("Series","SeriesDetial","SeriesState")
        companion object {
            private val TAG = "OfflineDB"
            private var instance: OfflineDBHelper?=null
            @Synchronized
            fun getInstance(ctx: Context, version: Int = 0): OfflineDBHelper {
                if (instance == null) {
                    instance = OfflineDBHelper(ctx.applicationContext)
                }
                Log.d("TAG", "Instance RETURN")
                return instance!!
            }
        }
        override fun onCreate(db: SQLiteDatabase) {
            Log.d("TAG", "onCreate")
            DBInital(tableList,db)
        }

        override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
            Log.d("TAG", "onUpgrade")
        }
        fun DBInital(listTable : List<String>, db: SQLiteDatabase){
            Log.d("TAG", "Inital start")
            //var db: SQLiteDatabase
            for(i : Int in 0 until listTable.size){
                when(listTable[i]){
                    "Series"->{
                        //db = this.context.openOrCreateDatabase(DB_NAME, Context.MODE_PRIVATE, null)
                        var create_sql = "CREATE TABLE IF NOT EXISTS "+listTable[i] +" ("
                        create_sql += "SId INTEGER PRIMARY KEY  AUTOINCREMENT NOT NULL,"
                        create_sql += "workId INTEGER NOT NULL,"
                        create_sql += "seriesNo INTEGER NOT NULL,"
                        create_sql += "title VARCHAR NOT NULL,"
                        create_sql += "date VARCHAR NOT NULL,"
                        create_sql += "imgUrl VARCHAR NOT NULL,"
                        create_sql += "like INTEGER NOT NULL"
                        create_sql += ");"
                        db.execSQL(create_sql)
                    }
                    "SeriesDetial"->{
                        //db = this.context.openOrCreateDatabase(DB_NAME, Context.MODE_PRIVATE, null)
                        var create_sql = "CREATE TABLE IF NOT EXISTS "+listTable[i] +" ("
                        create_sql += "SDId INTEGER PRIMARY KEY  AUTOINCREMENT NOT NULL,"
                        create_sql += "type INTEGER NOT NULL,"
                        create_sql += "SId INTEGER NOT NULL,"
                        create_sql += "dataOrder INTEGER NOT NULL,"
                        create_sql += "Base64Data VARCHAR NOT NULL"
                        create_sql += ");"
                        db.execSQL(create_sql)
                    }
                    "SeriesState"->{
                        //db = this.context.openOrCreateDatabase(DB_NAME, Context.MODE_PRIVATE, null)
                        var create_sql = "CREATE TABLE IF NOT EXISTS "+listTable[i] +" ("
                        create_sql += "SSId INTEGER PRIMARY KEY  AUTOINCREMENT NOT NULL,"
                        create_sql += "SId INTEGER NOT NULL,"
                        create_sql += "state INTEGER NOT NULL,"
                        create_sql += "read INTEGER NOT NULL,"
                        create_sql += "rentDate VARCHAR"
                        create_sql += ");"
                        db.execSQL(create_sql)
                    }
                }
            }
        }
        fun query(tableName :String,condition: String): List<bookmarkInfo> {
            Log.d("TAG", "start query")
            var m_conditionJudge : String =
                if(condition=="") {
                    "1=1"
                }else {
                    condition
                }
            val sql = "select * from $tableName where $m_conditionJudge;"
            Log.d("TAG", "query sql: " + sql)
            var infoArray = mutableListOf<bookmarkInfo>()
            use {
                Log.d("TAG", "start get query")
                val cursor = rawQuery(sql, null)
                Log.d("TAG", "cursor: "+cursor.count)
                if(cursor.count>0) {
                    if (cursor.moveToFirst()) {
                        while (true) {
                            val info = bookmarkInfo()
                            info.id = cursor.getLong(cursor.getColumnIndexOrThrow("id"))
                            info.title = cursor.getString(cursor.getColumnIndexOrThrow("title"))
                            info.html = cursor.getString(cursor.getColumnIndexOrThrow("html"))
                            infoArray.add(info)
                            if (cursor.isLast) {
                                break
                            }
                            cursor.moveToNext()
                        }
                    }
                }
                cursor.close()
            }
            return infoArray
        }
        fun querySeries(workId:Int): List<Long> {
            var SidArray = mutableListOf<Long>()
            val sql = "SELECT * from Series  where workId=$workId"
            var Sid=-1L
            use {
                val cursor = rawQuery(sql, null)
                if(cursor.count>0) {
                    if (cursor.moveToFirst()) {
                        while (true) {
                            Sid = cursor.getLong(cursor.getColumnIndexOrThrow("SId"))
                            SidArray.add(Sid)
                            if (cursor.isLast) {
                                break
                            }
                            cursor.moveToNext()
                        }
                    }
                }
                cursor.close()
            }
            return SidArray
        }
        fun querySeriesAll(): List<DB_OffLineInfo> {
            val sql = "SELECT * from Series left join SeriesState on Series.SId = SeriesState.SId "
            var infoArray = mutableListOf<DB_OffLineInfo>()
            use {
                val cursor = rawQuery(sql, null)
                if(cursor.count>0) {
                    if (cursor.moveToFirst()) {
                        while (true) {
                            val info = DB_OffLineInfo()
                            info.SId = cursor.getLong(cursor.getColumnIndexOrThrow("SId"))
                            info.workId = cursor.getLong(cursor.getColumnIndexOrThrow("workId"))
                            info.seriesNo = cursor.getLong(cursor.getColumnIndexOrThrow("seriesNo"))
                            info.date = cursor.getString(cursor.getColumnIndexOrThrow("date"))
                            info.like = cursor.getLong(cursor.getColumnIndexOrThrow("like"))
                            info.imgUrl= cursor.getString(cursor.getColumnIndexOrThrow("imgUrl"))
//                            info.state = cursor.getLong(cursor.getColumnIndexOrThrow("state"))
                            info.state =6
                            info.rentDate = cursor.getString(cursor.getColumnIndexOrThrow("rentDate"))
                            infoArray.add(info)
                            if (cursor.isLast) {
                                break
                            }
                            cursor.moveToNext()
                        }
                    }
                }
                cursor.close()
            }
            return infoArray
        }
        fun querySeriesByChapter(chapter:Int): List<DB_OffLineInfo> {
            val sql = "SELECT * from Series  where seriesNo=$chapter"
            var infoArray = mutableListOf<DB_OffLineInfo>()
            use {
                val cursor = rawQuery(sql, null)
                if(cursor.count>0) {
                    if (cursor.moveToFirst()) {
                        while (true) {
                            val info = DB_OffLineInfo()
                            info.SId = cursor.getLong(cursor.getColumnIndexOrThrow("SId"))
                            info.workId = cursor.getLong(cursor.getColumnIndexOrThrow("workId"))
                            info.seriesNo = cursor.getLong(cursor.getColumnIndexOrThrow("seriesNo"))
                            info.date = cursor.getString(cursor.getColumnIndexOrThrow("date"))
                            info.like = cursor.getLong(cursor.getColumnIndexOrThrow("like"))
//                            info.state = cursor.getLong(cursor.getColumnIndexOrThrow("state"))
//                            info.rentDate = cursor.getString(cursor.getColumnIndexOrThrow("rentDate"))
                            infoArray.add(info)
                            if (cursor.isLast) {
                                break
                            }
                            cursor.moveToNext()
                        }
                    }
                }
                cursor.close()
        }
        return infoArray
    }
        fun querySeriesDetailByChapter(chapter:Int): List<String> {
            var fileNameList= mutableListOf<String>()
            var querySid=querySeriesByChapter(chapter)
            for( index in 0 until querySid.size) {
                val sql = "SELECT * from SeriesDetial  where SId=${querySid[index].SId} order by dataOrder "
                var infoArray = mutableListOf<DB_SeriesDetialInfo>()
                use {
                    val cursor = rawQuery(sql, null)
                    if (cursor.count > 0) {
                        if (cursor.moveToFirst()) {
                            while (true) {
                                val info = DB_SeriesDetialInfo()
                                fileNameList.add(cursor.getString(cursor.getColumnIndexOrThrow("Base64Data")))
                                if (cursor.isLast) {
                                    break
                                }
                                cursor.moveToNext()
                            }
                        }
                    }
                    cursor.close()
                }
            }
            return fileNameList
        }

        fun insertSeries( infoArray: MutableList<DB_SeriesInfo>,allImageDataList:MutableList<ImageLoadDataSetNew>): Long {
            var result: Long = -1
            for (i in infoArray.indices) {
                val info = infoArray[i]
                //var tempArray: List<bookmarkInfo>
                val cv = ContentValues()
//                cv.put("SId", info.SId)
                cv.put("workId", info.workId)
                cv.put("seriesNo", info.seriesNo)
                cv.put("title", info.title)
                cv.put("date", info.date)
                cv.put("imgUrl", info.imgUrl)
                cv.put("like", info.like)
                use {
                    result = insert("Series", "", cv)
                }
                // 添加成功后返回行号，失败后返回-1
                if (result == -1L) {
                    return result
                }
                else{
                    //取得新建Sid
//                    var querySid=querySeriesByChapter(info.seriesNo.toInt())
                    var querySid=querySeriesByChapter(info.seriesNo.toInt())
                    for( index in 0 until querySid.size) {
                        //取得要匯入圖片資料
                        var mDB_SeriesDetialInfoList = mutableListOf<DB_SeriesDetialInfo>()
                        for (i in 0 until allImageDataList.size) {
                            var mDB_SeriesDetialInfo = DB_SeriesDetialInfo()
                            mDB_SeriesDetialInfo.SId = querySid[index].SId
                            mDB_SeriesDetialInfo.dataOrder = allImageDataList[i].order.toLong()
                            mDB_SeriesDetialInfo.type = 1
//                            var base64ata =""
//                            for (j in 0 until allImageDataList[i].base64Data.size) {
//                                base64ata += allImageDataList[i].base64Data[j]
//                            }
                            var base64ata =allImageDataList[i].base64Data
                            mDB_SeriesDetialInfo.Base64Data = base64ata
                            mDB_SeriesDetialInfoList.add(mDB_SeriesDetialInfo)
                        }
                        insertSeriesDetial("SeriesDetial", mDB_SeriesDetialInfoList)
                        //建立狀態資料
                        var stateDataList = mutableListOf<DB_SeriesStateInfo>()
                        var stateData = DB_SeriesStateInfo()
                        stateData.SId = querySid[index].SId
                        stateData.state = info.state
                        stateData.read = info.read
                        stateData.rentDate = info.rentDate
                        stateDataList.add(stateData)
                        insertSeriesState("SeriesState", stateDataList)
                    }
                }
            }
            return result
        }
    fun insertSeriesNew( infoArray: MutableList<DB_SeriesInfo>,allImageDataList: ImageLoadDataSetNew): Long {
        var result: Long = -1
        for (i in infoArray.indices) {
            val info = infoArray[i]
            //var tempArray: List<bookmarkInfo>
            val cv = ContentValues()
//                cv.put("SId", info.SId)
            cv.put("workId", info.workId)
            cv.put("seriesNo", info.seriesNo)
            cv.put("title", info.title)
            cv.put("date", info.date)
            cv.put("imgUrl", info.imgUrl)
            cv.put("like", info.like)
            use {
                result = insert("Series", "", cv)
            }
            // 添加成功后返回行号，失败后返回-1
            if (result == -1L) {
                return result
            }
            else{
                //取得新建Sid
                var querySid=querySeriesByChapter(info.seriesNo.toInt())
                for( index in 0 until querySid.size) {
                    //取得要匯入圖片資料
                    var mDB_SeriesDetialInfoList = mutableListOf<DB_SeriesDetialInfo>()
//                    for (i in 0 until allImageDataList.size) {
                        var mDB_SeriesDetialInfo = DB_SeriesDetialInfo()
                        mDB_SeriesDetialInfo.SId = querySid[index].SId
                        mDB_SeriesDetialInfo.dataOrder = allImageDataList.order.toLong()
                        mDB_SeriesDetialInfo.type = 1
                        var base64ata = ""
                        base64ata=allImageDataList.base64Data
//                        for (j in 0 until allImageDataList.base64Data.size) {
//                            base64ata += allImageDataList.base64Data[j]
//                        }
                        mDB_SeriesDetialInfo.Base64Data = base64ata
                        mDB_SeriesDetialInfoList.add(mDB_SeriesDetialInfo)
//                    }
                    insertSeriesDetial("SeriesDetial", mDB_SeriesDetialInfoList)
                    //建立狀態資料
                    var stateDataList = mutableListOf<DB_SeriesStateInfo>()
                    var stateData = DB_SeriesStateInfo()
                    stateData.SId = querySid[index].SId
                    stateData.state = info.state
                    stateData.read = info.read
                    stateData.rentDate = info.rentDate
                    stateDataList.add(stateData)
                    insertSeriesState("SeriesState", stateDataList)
                }
            }
        }
        return result
    }
        fun insertSeriesDetial(tableName :String, infoArray: MutableList<DB_SeriesDetialInfo>): Long {
            var result: Long = -1
            for (i in infoArray.indices) {
                val info = infoArray[i]
                //var tempArray: List<bookmarkInfo>
                val cv = ContentValues()
                cv.put("type", info.type)
                cv.put("SId", info.SId)
                cv.put("dataOrder", info.dataOrder)
                cv.put("Base64Data", info.Base64Data)
                use {
                    result = insert(tableName, "", cv)
                }
                // 添加成功后返回行号，失败后返回-1
                if (result == -1L) {
                    return result
                }
            }
            return result
        }
        fun insertSeriesState(tableName :String, infoArray: MutableList<DB_SeriesStateInfo>): Long {
            var result: Long = -1
            for (i in infoArray.indices) {
                val info = infoArray[i]
                //var tempArray: List<bookmarkInfo>
                val cv = ContentValues()
                cv.put("SId", info.SId)
                cv.put("state", info.state)
                cv.put("read", info.read)
                cv.put("rentDate", info.rentDate)
                use {
                    result = insert(tableName, "", cv)
                }
                // 添加成功后返回行号，失败后返回-1
                if (result == -1L) {
                    return result
                }
            }
            return result
        }
        fun delete( workId:Int): Int {
            var count = 0
            use {
                var querySid=querySeriesAll()
                for(i in 0 until querySid.size) {
                    var condition = "SId=${querySid[i].SId}"
                    count = delete("SeriesState", condition, null)
                    count = delete("SeriesDetial", condition, null)
                    count = delete("Series", condition, null)
                }
            }
            return count
        }
        fun update(tableName :String, info: bookmarkInfo, condition: String = "id=${info.id}"): Int {
            val cv = ContentValues()
            cv.put("title", info.title)
            cv.put("html", info.html)
            var count = 0
            use {
                count = update(tableName, cv, condition, null)
            }
            return count
        }

    }
class bookmarkInfo{
    var id :Long = 0
    var title: String = ""
    var html: String = ""
}
class DB_OffLineInfo{
    var SId :Long = 0
    var workId :Long = 0
    var seriesNo :Long = 0
    var title :String = ""
    var date :String = ""
    var imgUrl:String = ""
    var like :Long = 0
    var state :Long = 0
    var read :Long = 0
    var rentDate :String? = ""

}
class DB_SeriesInfo{
    var SId:Long = 0
    var workId :Long = 0
    var seriesNo :Long = 0
    var date :String = ""
    var title :String = ""
    var imgUrl:String = ""
    var like :Long = 0
    var state :Long = 0
    var read :Long = 0
    var rentDate :String? = ""
    var seriesDetialList=mutableListOf<DB_SeriesDetialInfo>()
}
class DB_SeriesDetialInfo{
    var type :Long = 0
    var SId :Long = 0
    var dataOrder :Long = 0
    var Base64Data :String = ""
}
class DB_SeriesStateInfo{
    var SId :Long = 0
    var state :Long? = 0
    var read :Long? = 0
    var rentDate :String? = ""
}
