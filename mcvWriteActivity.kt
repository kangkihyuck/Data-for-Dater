package com.applandeo.materialcalendarsampleapp

import android.content.Intent
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.os.Bundle
import android.support.v7.app.ActionBar
import android.support.v7.app.AppCompatActivity
import android.view.View
import android.widget.ListView
import kotlinx.android.synthetic.main.activity_mcv_write.*
import kotlinx.android.synthetic.main.calendar_activity.*
import org.jetbrains.anko.toast

lateinit var date2: String
lateinit var mcvsysdate: String
lateinit var mcvlistitem: String
var cnt5 = 0

var cnt20 = 0

class mcvWriteActivity : AppCompatActivity() {

    private lateinit var keyboardVisiblitiy: KeyboardVisiblitiy

    lateinit var myHelper: CalendarActivity.MyDBHelper // 디비 생성 도우미
    lateinit var sqlDB: SQLiteDatabase
    lateinit var listview: ListView
    lateinit var adapter: ListViewAdapter


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_mcv_write)

        keyboardVisiblitiy = KeyboardVisiblitiy(window,
                onShowKeyboard = { keyboardHeight ->
                    sv_root.run {

                            smoothScrollTo(scrollX, scrollY + keyboardHeight)
                            space2.visibility = View.GONE

                    }
                })
        keyboardVisiblitiy = KeyboardVisiblitiy(window,
                onHideKeyboard = { ->
                    sv_root.run{
                        smoothScrollBy(scrollX, scrollY)
                        space2.visibility = View.VISIBLE
                    }
                })




        SysdateText.visibility=View.GONE

        MainTItleTextView14.setOnClickListener { view ->
            val intent = Intent(this, MainActivity::class.java)
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
            startActivity(intent)
        }
        val actionbar: ActionBar? = supportActionBar
        actionbar?.hide() // 액션바 숨김

        if (cnt5 == 1) {
            //일정 List에서 item을 클릭하여 넘어왔을 때

            date2 = intent.getStringExtra("date2").toString()
            //CalendarActivity.kt에서 date2(선택한 날짜에 대한 저장값)를 intent로 불러옴.
            writeCurrentDay.text = date2
            mcvlistitem = intent.getStringExtra("mcvlistitem")

            myHelper = CalendarActivity.MyDBHelper(this, "writeDB", null, 1)
            //WriteDB 라는 이름을 가진 데이터 베이스 만들기
            sqlDB = myHelper.writableDatabase //데이터베이스 쓰기모드로 변환

            val c: Cursor = sqlDB.rawQuery("select * from writeTBL", null)
            while (c.moveToNext()) {
                if (c.getString(0) == date2) {
                    if (c.getString(2) == mcvlistitem)
                        CalendarEditText.setText(c.getString(1))
                    SysdateText.text = mcvlistitem
                }
            }
            writeSaveBtn.visibility = View.GONE
            upDateBtn.visibility = View.VISIBLE
            writeDeleteBtn.visibility = View.VISIBLE
            c.close()

            CalendarEditText.setOnClickListener {


            }

            writeCancelBtn.setOnClickListener {

                onBackPressed()

            }//작성 취소 버튼을 누른다면 뒤로 가세요.

            upDateBtn.setOnClickListener {

                var updateFlag: Boolean = true


                val intent3 = Intent(this, CalendarActivity::class.java)
                intent3.putExtra("mcvdate", writeCurrentDay.text.toString())
                intent3.putExtra("mcvcontent", CalendarEditText.text.toString())
                intent3.putExtra("mcvsysdate", SysdateText.text.toString())

                intent3.putExtra("selectedSaveDay", date2)
                intent3.putExtra("selectedSaveContent", CalendarEditText.text.toString())
                intent3.putExtra("selectedSaveSysdate", SysdateText.text.toString())

                //MCVcontent 라는 이름을 가진 일정에 대한 내용을 담아서 CalendarActivity로 넘기세요.

                mcvlistitem = intent.getStringExtra("mcvlistitem")
                writeCurrentDay.text = date2
                mcvsysdate = SysdateText.text.toString()

                cnt10 = 1
                myHelper = CalendarActivity.MyDBHelper(this, "writeDB", null, 1)

                val c1: Cursor
                c1 = sqlDB.rawQuery("select * from writeTBL", null)


                while (c1.moveToNext()) {
                    if (c1.getString(2) == mcvsysdate) {

                        sqlDB = myHelper.writableDatabase

                        sqlDB.execSQL("update writeTBL set mcvdate='" + date2 + "', mcvcontent='"
                                + CalendarEditText.text.toString() + "' where ( mcvsysdate ='" + mcvsysdate + "');")


                        val adapter: ListViewAdapter = ListViewAdapter()
                        mcv_ListView?.adapter = adapter

                        toast("일정이 수정되었습니다.")
                        updateFlag = false
                        break

                    }

                }
                c1.close()


                if (updateFlag) {
                    toast("일정이 없어 수정할 수 없습니다.")
                }

                startActivity(intent3)
            }


            writeDeleteBtn.setOnClickListener {
                var deleteFlag: Boolean = true
//                var listview: ListView
                val adapter: ListViewAdapter = ListViewAdapter()
                mcv_ListView?.adapter = adapter



                writeCurrentDay.text = date2
                mcvsysdate = SysdateText.text.toString()

                cnt10 = 1
                cnt20 = 1

                val intent15 = Intent(this, CalendarActivity::class.java)
                intent15.putExtra("selectedSaveDay", date2)
                intent15.putExtra("selectedSaveContent", CalendarEditText.text.toString())
                intent15.putExtra("selectedSaveSysdate", SysdateText.text.toString())

                myHelper = CalendarActivity.MyDBHelper(this, "writeDB", null, 1)
                sqlDB = myHelper.readableDatabase


                val c2: Cursor
                c2 = sqlDB.rawQuery("select * from writeTBL", null)

                while (c2.moveToNext()) {
                    if (c2.getString(0) == date2) {
                        if (c2.getString(2) == mcvsysdate) {
                            sqlDB = myHelper.writableDatabase
                            sqlDB.execSQL("delete from writeTBL where ( mcvsysdate = '" + SysdateText.text.toString() + "');")
                            toast("일정이 삭제되었습니다.")
                            deleteFlag = false
                            mcv_ListView?.removeAllViewsInLayout()
                            mcv_ListView?.adapter = adapter
                            sqlDB = myHelper.readableDatabase

                        }
                    }

                }
                c2.close()


                if (deleteFlag) {
                    toast("해당 일정이 없어 삭제할 수 없습니다")
                }


                adapter.notifyDataSetChanged()
                mcv_ListView?.adapter = adapter




                startActivity(intent15)


            }


        } else if (cnt5 == 2) {
            //작성 버튼 눌러 mcvWriteActivity로 넘어왔을 때
            date2 = intent.getStringExtra("date2").toString()
            //CalendarActivity.kt에서 date2(선택한 날짜에 대한 저장값)를 intent로 불러옴.
            writeCurrentDay.text = date2
            //writeCurrentDay라는 text에 선택한 날짜 나타내기.
            mcvsysdate = intent.getStringExtra("sysdate").toString()
            SysdateText.text = mcvsysdate
            //작성하기 버튼을 눌렀을 때
            writeSaveBtn.visibility = View.VISIBLE
            upDateBtn.visibility = View.GONE
            writeDeleteBtn.visibility = View.GONE

            writeSaveBtn.setOnClickListener {

                val mcvcontent = CalendarEditText.text.toString()
                val mcvdate: String = writeCurrentDay.text.toString()
                mcvsysdate = SysdateText.text.toString()
                //CalendarEditText에 입력한 정보를 MCVcontent라는 변수에 담아냄
//            //인텐트로도 해서 CalendarActivity로 넘기자(2019-05-02 15:28)
                //writeSaveBtn의 맨 끝에 적음(2019-05-02 15:30)

                cnt10 = 1


                val intent13 = Intent(this, CalendarActivity::class.java)
                intent13.putExtra("selectedSaveDay", mcvdate)
                intent13.putExtra("selectedSaveContent", CalendarEditText.text.toString())
                intent13.putExtra("selectedSaveSysdate", SysdateText.text.toString())
                //MCVcontent 라는 이름을 가진 일정에 대한 내용을 담아서 CalendarActivity로 넘기세요.

                myHelper = CalendarActivity.MyDBHelper(this, "writeDB", null, 1)
                //WriteDB 라는 이름을 가진 데이터 베이스 만들기
                sqlDB = myHelper.writableDatabase
                sqlDB.execSQL(
                        "insert into writeTBL values('"
                                + mcvdate
                                + "','" + mcvcontent + "','"
                                + mcvsysdate + "');"
                )
                //WriteTBL에 MCVdate(날짜)와 MCVcontent(작성내용)을 저장.
                toast("저장되었습니다.")
//                var listview: ListView
                val adapter: ListViewAdapter = ListViewAdapter()
                mcv_ListView?.adapter = adapter



                startActivity(intent13)
            }

            writeCancelBtn.setOnClickListener {
                onBackPressed()
            }//작성 취소 버튼을 누른다면 뒤로 가세요.


        }


//            val events1 = ArrayList<EventDay>()
//            val calendarView1 : CalendarView? = findViewById<View>(R.id.mcv_calendarView) as? CalendarView
//
//            val calendar1 = Calendar.getInstance()
//            calendar1.add(Calendar.DAY_OF_MONTH,1)
//            events1.add(EventDay(calendar1, R.drawable.abc_ic_star_black_16dp))

//            calendar1.add(Calendar.DAY_OF_MONTH,3)
//            Log.d("qqq",Calendar.DAY_OF_MONTH.toString())
////            calendar1.add(date2.toInt(),3)
//            events.add(EventDay(calendar1, R.drawable.abc_ic_star_black_16dp))


//            myHelper = CalendarActivity.MyDBHelper(this, "writeDB", null, 1)
//            sqlDB = myHelper.readableDatabase
//
//            val c: Cursor = sqlDB.rawQuery("select * from writeTBL;", null)
//            while (c.moveToNext()) {
//                val savedDate: String = c.getString(0)
//                val savedTitle: String = c.getString(1)
//                val savedSysDate: String = c.getString(2)
//                adapter.addItem(savedTitle, savedDate, savedSysDate)
//            }
//            c.close()


    }

    override fun onDestroy() {
        keyboardVisiblitiy.detachKeyboardListeners()
        super.onDestroy()
    }
}
