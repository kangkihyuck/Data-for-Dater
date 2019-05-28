package com.applandeo.materialcalendarsampleapp

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.os.Bundle
import android.support.v7.app.ActionBar
import android.support.v7.app.AppCompatActivity
import android.util.Log
import android.view.View
import android.widget.AdapterView
import android.widget.Toast
import com.applandeo.materialcalendarview.CalendarView
import com.applandeo.materialcalendarview.EventDay
import kotlinx.android.synthetic.main.activity_led.*
import kotlinx.android.synthetic.main.calendar_activity.*
import kotlinx.android.synthetic.main.listview_item.view.*
import org.jetbrains.anko.toast
import java.util.*

val events = ArrayList<EventDay>()
var cnt10 = 0

@Suppress("DEPRECATION")
class CalendarActivity : AppCompatActivity() {
    lateinit var myHelper: MyDBHelper
    lateinit var sqlDB: SQLiteDatabase


    var savedCurrentDateDBCheck = ""
    var todayCutCDDBMonth = ""
    var todayCutCDDBDate = ""
    var cutCDDBMonth = ""
    var cutCDDBDate = ""
    var savedDate = ""
    var savedDateSecretToday = ""
    var year1 = ""
    var month1 = ""
    var date1 = ""



    @SuppressLint("SetTextI18n")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.calendar_activity)




        MainTItleTextView15.setOnClickListener { view ->
//            val intent = Intent(this, MainActivity::class.java)
//            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
//            startActivity(intent)
            onBackPressed()
        }


        val adapter = ListViewAdapter()
        mcv_ListView.adapter = adapter

        myHelper = MyDBHelper(this, "writeDB", null, 1)
        sqlDB = myHelper.readableDatabase
//        myHelper.onUpgrade(sqlDB, 1, 2) //drop table기능.
        val actionbar: ActionBar? = supportActionBar
        actionbar?.hide()
        //상단 액션바 숨기기

        val calendarView = findViewById<View>(R.id.mcv_calendarView) as CalendarView
        //커스텀캘린더뷰에 대한 객체 생성

        val min = Calendar.getInstance()
        min.add(Calendar.YEAR, -10)
        //달력의 최소년도는 -10년까지.

        val max = Calendar.getInstance()
        max.add(Calendar.YEAR, 10)
        //달력의 최대 년도는 +10년까지.

        calendarView.setMinimumDate(min)
        //최소년도 적용
        calendarView.setMaximumDate(max)
        //최대년도 적용
        calendarView.setEvents(events)
        //이벤트 적용

        val instance1= Calendar.getInstance()
        //Calendar클래스의 객체 생성
        year1 = instance1.get(Calendar.YEAR).toString()
        //Calendar 객체의 연도를 변수에 담음
        month1 = ((instance1.get(Calendar.MONTH)) + 1).toString()
        //Calendar 객체의 월을 변수에 담음
        date1 = instance1.get(Calendar.DATE).toString()
        //Calendar 객체의 일을 변수데 담음
        if (month1.toInt() < 10) {
            month1 = "0$month1"
        }//만약 1월~10월이라면 앞에 0을 붙이기
        if (date1.toInt() < 10) {
            date1 = "0$date1"
        }//만약 1일~10일이라면 앞에 0을 붙이기

//        events.add(EventDay(instance1, DrawableUtils.getCircleDrawableWithText(this, "오늘")))
        //오늘 날짜에 오늘이란 마크 남기기
        todayCutCDDBMonth = month1
        //todayCutCDDBMonth에 month1을 담아냄.
        todayCutCDDBDate = date1
        //todayCutCDDBDate에 date1을 담아냄.





        if (cnt10 == 1) {
            myHelper = MyDBHelper(this, "writeDB", null, 1)
            sqlDB = myHelper.readableDatabase
            //sqlDB 읽기 모드

            val d50: Cursor = sqlDB.rawQuery("select * from writeTBL where mcvdate like '%${year1}${month1}${date1}' order by 1;", null)
            //캘린더를 시작하면 d11 변수를 생성한다.(현재 날짜의 DB체크 sql쿼리)
            while (d50.moveToNext()) {
                //d11을 로우 하나 하나 넘겨가며 확인하는 while.
                savedDateSecretToday = d50.getString(0)
                //날짜를 담아냄




            }

            d50.close()
            //사용을 마친 d11를 닫는다.
            if ((year1 + month1 + date1) == savedDateSecretToday) {
                MainActivity.AppCount = 1
                LedText2?.text = "RedLed"
                App.prefs.myEditText = "RedLed"
                MainActivity.bt?.send("+B", true) //Red 변경
                Log.d("asd", "RedRedRed")
                LedText2?.text = App.prefs.myEditText

            } else {
                Log.d("asd", "GreenGreenGreen")
                MainActivity.AppCount = 2
                LedText2?.text = "GreenLed"
                App.prefs.myEditText = "GreenLed"
                MainActivity.bt.send("+C", true) //초록 변경

                LedText2?.text = App.prefs.myEditText
            }

            App.prefs.myEditText = LedText2?.text.toString()


            val msg = App.prefs.myEditText
//
            if (msg == "") {
                Toast.makeText(this, "텍스트가 초기화되었습니다.", Toast.LENGTH_LONG).show()
            } else {
//                Toast.makeText(this, "저장됨: $msg", Toast.LENGTH_LONG).show()
            }
            /* 값 저장 후 Toast 출력 */




            var loadSelectedSaveDate1 = intent.getStringExtra("selectedSaveDay").toString()
            //loadSelectedSaveDate1에 "selectedSaveDay"라는 name을 가진 변수를 intent로 받기 ==>>date를 나타낸 String형식( ex)20190510 )

            var cutLoadDate2Year = loadSelectedSaveDate1.substring(0, 4)
            //불러온 date를 앞에서부터 4자리 끊어 읽어오기 ( ex) 20190510에서 2019 만을 저장함 ) ==>> 연도를 나타냄
            var cutLoadDate2Month = loadSelectedSaveDate1.substring(4, 6)
            // 불러온 date를 4자리의 다음부터 6번째까지 끊어 읽어오기 ( ex) 20190510에서 05 만을 저장함) ==>> 월을 나타냄
            var cutLoadDate2Date = loadSelectedSaveDate1.substring(6, 8)
            // 불러온 date를 6자리 다음부터인 7번째부터 8번째까지 끊어 읽어오기 ( ex) 20190510에서 10만을 저장함 ) ==>> 일을 나타냄

            var calendar20 = Calendar.getInstance()
            //캘린더의 현재 시간(오늘)을 받아온 calendar20을 생성
            calendar20.set(cutLoadDate2Year.toInt() + 0, cutLoadDate2Month.toInt() - 1, cutLoadDate2Date.toInt() + 0)
            //calendar20의 연, 월, 일을 set하여 지정함. substring을 통해 끊어 읽은 date를 연, 월, 일에 각각 넣어줌.
            calendarView.setDate(calendar20)
            //지정한 연, 월, 일을 수행하여 적용

            myHelper = MyDBHelper(this, "writeDB", null, 1)
            sqlDB = myHelper.readableDatabase
            //sqlDB 읽기 모드

            val d12: Cursor = sqlDB.rawQuery("select * from writeTBL where mcvdate like '%${cutLoadDate2Year}${cutLoadDate2Month}${cutLoadDate2Date}' order by 1;", null)
            //캘린더를 시작하면 d12 변수를 생성한다.(작성 날짜의 DB체크 sql쿼리)
            while (d12.moveToNext()) {
                //d12를 로우 하나 하나 넘겨가며 확인하는 while.
                val savedDate1 = d12.getString(0)
                //날짜를 담아냄
                val savedTitle: String = d12.getString(1)
                //내용을 담아냄
                val savedSysdate: String = d12.getString(2)
                //sysdate를 담아냄
                adapter.addItem(savedDate1, savedTitle, savedSysdate)
                //adapter를 통해 읽은 DB를 ListView에 담아내기


            }
            d12.close()
            //사용한 d12 닫기.


            mcv_writeBtn.setOnClickListener {

                val intent12 = Intent(this, mcvWriteActivity::class.java)
                //intent12 생성(mcvWriteActivity라는 일정을 작성하는 액티비티로 값을 넘겨주는 것으로 설정함)
                intent12.putExtra("date2", "${cutLoadDate2Year}${cutLoadDate2Month}${cutLoadDate2Date}")
                intent12.putExtra("sysdate", System.currentTimeMillis().toString())
                cnt5 = 2
                //"date2"라는 name을 가진 연월일을 intent로 넘겨줌.


                startActivity(intent12)
                //intent12로 값을 넘기며 mcvWriteActivity 시작.


            }

            mcv_ListView.onItemClickListener = AdapterView.OnItemClickListener { _, view, position, id ->
                //리스트뷰의 아이템을 클릭한다면
                val intent11 = Intent(this, mcvWriteActivity::class.java)
                //intent 생성(mcvWriteActivity라는 일정을 작성하는 액티비티로 값을 넘겨주는 것으로 설정함)
                intent11.putExtra("mcvlistitem", view!!.itemText3.text.toString())
                //"mcvlistitem"이라는 name을 가진 view의 item3를 intent로 넘겨줌. (시스템시간을 저장한 sysdate에 해당함)
                intent11.putExtra("date2", "${cutLoadDate2Year}${cutLoadDate2Month}${cutLoadDate2Date}")
                //"date2"라는 name을 가진 연월일을 intent로 넘겨줌.
                cnt5 = 1
                //cnt를 1로 지정.

                startActivity(intent11)
                //intent로 값을 넘기며 mcvWriteActivity 시작.

            }
            if (cnt20 == 1) {

                events.clear()

                cnt20 = 0

            }
            cnt10 = 100

        } else if (cnt10 == 0) {
            Log.d("asd","넘어옴??")
            myHelper = MyDBHelper(this, "writeDB", null, 1)
            sqlDB = myHelper.readableDatabase
            //sqlDB 읽기 모드
            val d500: Cursor = sqlDB.rawQuery("select * from writeTBL where mcvdate like '%${year1}${month1}${date1}' order by 1;", null)
            //캘린더를 시작하면 d500 변수를 생성한다.(현재 날짜의 DB체크 sql쿼리)
            while (d500.moveToNext()) {
                Log.d("asd","이거도?넘어옴?")
                //d11을 로우 하나 하나 넘겨가며 확인하는 while.
                savedDate = d500.getString(0)
                //날짜를 담아냄
                val savedTitle: String = d500.getString(1)
                //내용을 담아냄
                val savedSysdate: String = d500.getString(2)
                //sysdate를 담아냄
                adapter.addItem(savedDate, savedTitle, savedSysdate)
                Log.d("asd",savedDate+" //savedDate담아냄?")
            }

            d500.close()
            //사용을 마친 d11를 닫는다.
            if ((year1 + month1 + date1) == savedDate) {
                MainActivity.AppCount = 1
                LedText2?.text = "RedLed"
                App.prefs.myEditText = "RedLed"
                MainActivity.bt?.send("+B", true) //Red 변경
                Log.d("asd", "RedRedRed")
                LedText2?.text = App.prefs.myEditText

            } else {
                Log.d("asd", "GreenGreenGreen")
                MainActivity.AppCount = 2
                LedText2?.text = "GreenLed"
                App.prefs.myEditText = "GreenLed"
                MainActivity.bt.send("+C", true) //초록 변경

                LedText2?.text = App.prefs.myEditText
            }

            App.prefs.myEditText= LedText2?.text.toString()


            val msg = App.prefs.myEditText
//
            if (msg == "") {
                Toast.makeText(this, "텍스트가 초기화되었습니다.", Toast.LENGTH_LONG).show()
            } else {
//                Toast.makeText(this, "저장됨: $msg", Toast.LENGTH_LONG).show()
            }
            /* 값 저장 후 Toast 출력 */
            savedDate =""


            mcv_writeBtn.setOnClickListener {

                val intent = Intent(this, mcvWriteActivity::class.java)
                //intent 생성(mcvWriteActivity라는 일정을 작성하는 액티비티로 값을 넘겨주는 것으로 설정함)
                intent.putExtra("date2", "${year1}${month1}${date1}")
                intent.putExtra("sysdate", System.currentTimeMillis().toString())
                cnt5 = 2

                //"date2"라는 name을 가진 연월일을 intent로 넘겨줌.


                startActivity(intent)
                //intent로 값을 넘기며 mcvWriteActivity 시작.


            }

            mcv_ListView.onItemClickListener = AdapterView.OnItemClickListener { _, view, position, id ->
                val intent = Intent(this, mcvWriteActivity::class.java)


                intent.putExtra("mcvlistitem", view!!.itemText3.text.toString())
                intent.putExtra("date2", "${year1}${month1}${date1}")
                cnt5 = 1


                startActivity(intent)
            }
            cnt10 = 100
        }


//
        val dbCheck1: Cursor = sqlDB.rawQuery("select * from writeTBL where mcvdate like '%${year1}%' order by 1", null)
        while (dbCheck1.moveToNext()) {
            savedCurrentDateDBCheck = dbCheck1.getString(0)
            //저장된 올 해의 mcvdate 확인하기 (1년치 date 저장)
            Log.d("qqq", savedCurrentDateDBCheck + " //이번 달의 모든 mcvdate")
            if (cutCDDBDate == "") {
                cutCDDBMonth = savedCurrentDateDBCheck.substring(4, 6)
                cutCDDBDate = savedCurrentDateDBCheck.substring(6, 8)
                Log.d("qqq", cutCDDBMonth + " //month만 //!!!!!!!!!")
                Log.d("qqq", cutCDDBDate + " //끝 2자리만(date만)")


                val calendar10 = Calendar.getInstance()
                val calendar11 = Calendar.getInstance()
                if (todayCutCDDBMonth.equals(cutCDDBMonth)) {
                    calendar10.add(Calendar.DAY_OF_MONTH, cutCDDBDate.toInt() - todayCutCDDBDate.toInt())
                    events.add(EventDay(calendar10, R.drawable.calendarbar3))
                    //현재 날짜로부터 뺀 값 후에 해당 이미지 마크를 띄워라.
                } else if (!todayCutCDDBMonth.equals(cutCDDBMonth)) {
                    calendar11.set(year1.toInt() + 0, month1.toInt() - 1 + (cutCDDBMonth.toInt() - todayCutCDDBMonth.toInt()), date1.toInt() + cutCDDBDate.toInt() - todayCutCDDBDate.toInt())
                    events.add(EventDay(calendar11, R.drawable.calendarbar3))

                }

                cutCDDBMonth = ""
                cutCDDBDate = ""


            }

        }

        dbCheck1.close()




        calendarView.setOnDayClickListener { eventDay ->
            toast("${eventDay.calendar.time.year + 1900}년 ${eventDay.calendar.time.month + 1}월 ${eventDay.calendar.time.date}일")

            myHelper = MyDBHelper(this, "writeDB", null, 1)
            sqlDB = myHelper.readableDatabase
//            var listview: ListView
            val adapter = ListViewAdapter()

            mcv_ListView.adapter = adapter

            val cc1: Cursor = sqlDB.rawQuery("select * from writeTBL where mcvdate like '%${eventDay.calendar.time.year + 1900}${eventDay.calendar.time.month + 1}${eventDay.calendar.time.date}';", null)

            while (cc1.moveToNext()) {
                val savedDate: String = cc1.getString(0)
                val savedTitle: String = cc1.getString(1)
                val savedSysDate: String = cc1.getString(2)
                adapter.addItem(savedDate, savedTitle, savedSysDate)
            }
            cc1.close()

            val cc2: Cursor = sqlDB.rawQuery("select * from writeTBL where mcvdate like '%${eventDay.calendar.time.year + 1900}0${eventDay.calendar.time.month + 1}0${eventDay.calendar.time.date}';", null)
            while (cc2.moveToNext()) {
                val savedDate: String = cc2.getString(0)
                val savedTitle: String = cc2.getString(1)
                val savedSysDate: String = cc2.getString(2)
                adapter.addItem(savedDate, savedTitle, savedSysDate)
            }
            cc2.close()

            val cc3: Cursor = sqlDB.rawQuery("select * from writeTBL where mcvdate like '%${eventDay.calendar.time.year + 1900}0${eventDay.calendar.time.month + 1}${eventDay.calendar.time.date}';", null)
            while (cc3.moveToNext()) {
                val savedDate: String = cc3.getString(0)
                val savedTitle: String = cc3.getString(1)
                val savedSysDate: String = cc3.getString(2)
                adapter.addItem(savedDate, savedTitle, savedSysDate)
            }
            cc3.close()

            val cc4: Cursor = sqlDB.rawQuery("select * from writeTBL where mcvdate like '%${eventDay.calendar.time.year + 1900}${eventDay.calendar.time.month + 1}0${eventDay.calendar.time.date}';", null)
            while (cc4.moveToNext()) {
                val savedDate: String = cc4.getString(0)
                val savedTitle: String = cc4.getString(1)
                val savedSysDate: String = cc4.getString(2)
                adapter.addItem(savedDate, savedTitle, savedSysDate)
            }
            cc4.close()


            mcv_writeBtn.setOnClickListener {

                val intent = Intent(this, mcvWriteActivity::class.java)
                if (eventDay.calendar.time.month + 1 < 10) {

                    if (eventDay.calendar.time.date < 10) {
                        intent.putExtra("date2", "${eventDay.calendar.time.year + 1900}0${eventDay.calendar.time.month + 1}0${eventDay.calendar.time.date}")
                        intent.putExtra("sysdate", System.currentTimeMillis().toString())
                        cnt5 = 2
                    } else {
                        intent.putExtra("date2", "${eventDay.calendar.time.year + 1900}0${eventDay.calendar.time.month + 1}${eventDay.calendar.time.date}")
                        intent.putExtra("sysdate", System.currentTimeMillis().toString())
                        cnt5 = 2
                    }

                } else {

                    if (eventDay.calendar.time.date < 10) {
                        intent.putExtra("date2", "${eventDay.calendar.time.year + 1900}${eventDay.calendar.time.month + 1}0${eventDay.calendar.time.date}")
                        intent.putExtra("sysdate", System.currentTimeMillis().toString())
                        cnt5 = 2
                    } else {
                        intent.putExtra("date2", "${eventDay.calendar.time.year + 1900}${eventDay.calendar.time.month + 1}${eventDay.calendar.time.date}")
                        intent.putExtra("sysdate", System.currentTimeMillis().toString())
                        cnt5 = 2
                    }
                }
                startActivity(intent)
            }

            mcv_ListView.onItemClickListener = AdapterView.OnItemClickListener { _, view, position, id ->
                val intent = Intent(this, mcvWriteActivity::class.java)
                Log.d("aaa", view!!.itemText3.text.toString() + "aksdjflk")

                intent.putExtra("mcvlistitem", view!!.itemText3.text.toString())

                if (eventDay.calendar.time.month + 1 < 10) {

                    if (eventDay.calendar.time.date < 10) {
                        intent.putExtra("date2", "${eventDay.calendar.time.year + 1900}0${eventDay.calendar.time.month + 1}0${eventDay.calendar.time.date}")

                        cnt5 = 1
                    } else {
                        intent.putExtra("date2", "${eventDay.calendar.time.year + 1900}0${eventDay.calendar.time.month + 1}${eventDay.calendar.time.date}")

                        cnt5 = 1
                    }

                } else {

                    if (eventDay.calendar.time.date < 10) {
                        intent.putExtra("date2", "${eventDay.calendar.time.year + 1900}${eventDay.calendar.time.month + 1}0${eventDay.calendar.time.date}")

                        cnt5 = 1
                    } else {
                        intent.putExtra("date2", "${eventDay.calendar.time.year + 1900}${eventDay.calendar.time.month + 1}${eventDay.calendar.time.date}")

                        cnt5 = 1
                    }
                }
                startActivity(intent)
            }
        }
    }




    class MyDBHelper(context: Context?, name: String?, factory: SQLiteDatabase.CursorFactory?, version: Int) :
            SQLiteOpenHelper(context, name, factory, version) {
        override fun onCreate(db: SQLiteDatabase?) {
            db?.execSQL("create table writeTBL(mcvdate varchar2(1000) , mcvcontent varchar2(1000), mcvsysdate varchar2(1000))")// ListView 테이블 구조를 생성
        }

        override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
            //아래 코드는 ListViewTBL 테이블이 존재하면 테이블을 없애는 부분인데 다시 앱을 실행했을 때 테이블에 저장된 내용이
            //있어야 해서 아래 코드는 주석처리 했다.
            db?.execSQL("drop table if exists writeTBL")
            onCreate(db)
        }
    }

    override fun onBackPressed() {
        finish()
    }
}
