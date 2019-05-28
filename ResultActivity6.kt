package com.applandeo.materialcalendarsampleapp

import android.content.Intent
import android.os.Bundle
import android.os.SystemClock
import android.support.v7.app.ActionBar
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.util.Log
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.RatingBar
import android.widget.Spinner
import com.bumptech.glide.Glide
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import kotlinx.android.synthetic.main.activity_result6.*
import org.jetbrains.anko.startActivity
import org.jetbrains.anko.toast
import org.json.JSONArray
import org.json.JSONObject
import java.io.BufferedReader
import java.io.InputStreamReader
import java.net.HttpURLConnection
import java.net.URL

class ResultActivity6 : AppCompatActivity(), OnMapReadyCallback {

    private lateinit var mMap: GoogleMap
    var result_title = ""
    var result_contentid = ""
    var result_image1: String = ""
    var result_Latitude = 0.0
    var result_Longitude = 0.0

    lateinit var replydto: ReplyDTO

    lateinit var replyList: java.util.ArrayList<ReplyDTO>

    lateinit var RRecyclerView: RecyclerView

    var rating1: String = ""

    var preUpdateEditText = ""

    private lateinit var spinner: Spinner
    lateinit var ResultSpinnerList4: ArrayList<String>
    lateinit var ResultSpinnerAdapter4: ArrayAdapter<String>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_result6)

        val actionbar: ActionBar? = supportActionBar
        actionbar?.hide() // 액션바 숨김

        ResultSpinnerList4 = ArrayList()
        ResultSpinnerList4.add("---항목선택---")
        ResultSpinnerList4.add("조명등 연동하기")
        ResultSpinnerList4.add("스케줄 관리하기")
        ResultSpinnerList4.add("개발자 정보")

        ResultSpinnerAdapter4 =
                ArrayAdapter<String>(applicationContext, android.R.layout.simple_spinner_dropdown_item, ResultSpinnerList4)

        spinner = findViewById(R.id.resultSpinner4) as Spinner
        spinner.setAdapter(ResultSpinnerAdapter4)
        spinner.setOnItemSelectedListener(object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(adapterView: AdapterView<*>, view: View, i: Int, l: Long) {
                if (i == 0) {
                } else if (i == 1) {
                    toast("조명등 연동하기 페이지 이동")
                } else if (i == 2) {
                    toast("스케줄 관리하기 페이지 이동")
                } else if (i == 3) {
                    toast("개발자 정보 페이지 이동")
                    startActivity<DeveloperActivity>()
                }
            }

            override fun onNothingSelected(adapterView: AdapterView<*>) {}
        })

        resultCnt = 4

        mContext = this

        RRecyclerView = findViewById(R.id.recycler_view_content4) as RecyclerView
        RRecyclerView.setHasFixedSize(true)
        var mLayoutManager = LinearLayoutManager(this)
        RRecyclerView.setLayoutManager(mLayoutManager)

//        var thread2 = replyListThread()
//        thread2.start()

        if (!helloID.equals("")) {
            MainLoginTextView9.text = helloID +"(님)"
            replyLayout4.visibility = View.VISIBLE
            replyAlarmTextView4.visibility = View.INVISIBLE
        } else {
            replyLayout4.visibility = View.GONE
            replyAlarmTextView4.visibility = View.VISIBLE
        }

        MainTItleTextView9.setOnClickListener { view ->
            val intent = Intent(this, MainActivity::class.java)
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
            startActivity(intent)
        }

        MainLoginTextView9.setOnClickListener { view ->
            if (MainLoginTextView9.text.equals("로그인")) {
                // 로그인 창으로 이동
                toast("로그인 페이지 이동")
                startActivity<LoginActivity>()
            } else {
                helloID = ""
                MainLoginTextView9.text = "로그인"
                toast("로그아웃 완료!")
            }
        }

        val rb = findViewById(R.id.ratingBar4) as RatingBar

        rb.setOnRatingBarChangeListener { ratingBar, rating, fromUser ->
            rating1 = rating.toString()
            toast(rating1)
        }

        replyResgierButton4.setOnClickListener { view ->
            if(titleEditText4.text.toString().equals("")){
                if(contentEditText4.text.toString().equals("")){
                    toast("댓글 제목과 내용을 입력하세요.")
                } else {
                    toast("댓글 내용을 입력하세요.")
                }
            }else {
                if(contentEditText4.text.toString().equals("")){
                    toast("댓글 내용을 입력하세요.")
                } else{
                    var replyLimit = 0
                    for (i in 0..replyList.size - 1) {
                        if (helloID.equals(replyList.get(i).member_id)) {
                            toast("이미 등록한 댓글이 있습니다.")
                            replyLimit = 1
                            break;
                        } else {
                            replyLimit = 2
                        }
                    }

                    if (replyLimit == 2 || replyLimit == 0) {
                        var thread = ThreadClass1()

                        thread.start()

                        SystemClock.sleep(100)

                        var thread2 = replyListThread()
                        thread2.start()

                        SystemClock.sleep(100)

                        val intent = intent
                        finish()
                        startActivity(intent)
                    }
                }
            }
        }

        replyAlarmTextView4.setOnClickListener { view ->
            toast("로그인 페이지 이동")
            startActivity<LoginActivity>()
        }

        replyUpdateButton4.setOnClickListener { view ->
            // editText 내용과 Rating 값을 update 진행.
            var updateThread = replyListUpdateThread()
            updateThread.start()

            toast("수정 완료")

            replyUpdateButton4.visibility = View.GONE
            replyResgierButton4.visibility = View.VISIBLE

            SystemClock.sleep(1000)

            val intent = intent
            finish()
            startActivity(intent)

        }

        result_title = intent.getStringExtra("title")
        var result_rating = intent.getStringExtra("rating")
        result_image1 = intent.getStringExtra("image2")
        result_contentid = intent.getStringExtra("contentid")
        var result_contenttype = intent.getStringExtra("contenttypeid")
        result_Latitude = intent.getDoubleExtra("latitude", 0.0)
        result_Longitude = intent.getDoubleExtra("longitude", 0.0)

        SystemClock.sleep(100)

        var s1: String = festivalList.get(0).festival_agelimit.replace("<br>", " ")
        var s2: String = festivalList.get(0).festival_bookingplace.replace("<br>", " ")
        var s3: String = festivalList.get(0).festival_discountinfofestival.replace("<br>", " ")
        var s4: String = festivalList.get(0).festival_usetimefestival.replace("<br>", " ")
        var s5: String = festivalList.get(0).festival_eventstartdate.replace("<br>", " ")
        var s6: String = festivalList.get(0).festival_eventenddate.replace("<br>", " ")
        var s7: String = festivalList.get(0).festival_eventplace.replace("<br>", " ")
        var s8: String = festivalList.get(0).festival_placeinfo.replace("<br>", " ")
        var s9: String = festivalList.get(0).festival_playtime.replace("<br>", " ")
        var s10: String = festivalList.get(0).festival_spendtimefestival.replace("<br>", " ")
        var s11: String = festivalList.get(0).festival_program.replace("<br>", " ")
        var s12: String = festivalList.get(0).festival_subevent.replace("<br>", " ")
        var s13: String = festivalList.get(0).festival_eventhomepage.replace("<br>", " ")

        resultTitleTextView6.text = result_title
        averageRatingTextView6.text = result_rating
        Glide.with(this).load(result_image1).into(resultImageView6)
        resultAgelimitTextView6.text = "관람 가능 연령 : " + s1
        resultBookingplaceTextView6.text = "예매처 : " + s2
        resultDiscountinfofestivalTextView6.text = "할인 정보 : " + s3
        resultUsetimefestivalTextView6.text = "이용요금 : " + s4
        resultEventstartdateTextVIew6.text = "시작일 : " + s5
        resultEventenddateTextView6.text = "종료일 : " + s6
        resultEventplaceTextView6.text = "행사장소 : " + s7
        resultPlaceinfoTextView6.text = "행사장 위치 안내 : " + s8
        resultPlaytimeTextView6.text = "공연 시간 : " + s9
        resultSpendtimefestivalTextView6.text = "관람 소요 시간 : " + s10
        resultProgramTextView6.text = "행사 프로그램 : " + s11
        resultSubeventTextView6.text = "부대행사 : " + s12
        resultEventhomepageTextView6.text = "행사 홈페이지 : " + s13

        if (result_contenttype.equals("39")) {
            contentTypeTextView6.text = "음식"
        } else if (result_contenttype.equals("32")) {
            contentTypeTextView6.text = "숙박"
        } else if (result_contenttype.equals("28")) {
            contentTypeTextView6.text = "액티비티"
        } else if (result_contenttype.equals("12")) {
            contentTypeTextView6.text = "관광지"
        } else if (result_contenttype.equals("14")) {
            contentTypeTextView6.text = "문화시설"
        } else if (result_contenttype.equals("15")) {
            contentTypeTextView6.text = "축제/공연/행사"
        }

        toggleButton6.setOnClickListener { view ->
            if (toggleButton6.isChecked) {
                goneLayout6.visibility = View.VISIBLE
                toggleButton6.setBackgroundDrawable(resources.getDrawable(R.drawable.ic_arrow_drop_up_black_24dp))
            } else {
                goneLayout6.visibility = View.GONE
                toggleButton6.setBackgroundDrawable(resources.getDrawable(R.drawable.ic_arrow_drop_down_black_24dp))
            }
        }

        replyToggleButton6.setOnClickListener { view ->
            if(replyToggleButton6.isChecked){
                recycler_view_content4.visibility = View.VISIBLE
                replyToggleButton6.setBackgroundDrawable(resources.getDrawable(R.drawable.ic_arrow_drop_up_black_24dp))
            } else {
                recycler_view_content4.visibility = View.GONE
                replyToggleButton6.setBackgroundDrawable(resources.getDrawable(R.drawable.ic_arrow_drop_down_black_24dp))
            }
        }

        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        val mapFragment = supportFragmentManager
                .findFragmentById(R.id.map) as SupportMapFragment
        mapFragment.getMapAsync(this)
    }

    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    override fun onMapReady(googleMap: GoogleMap) {
        mMap = googleMap

        val location = LatLng(result_Latitude, result_Longitude)
        mMap.addMarker(MarkerOptions().position(location).title(result_title))
        mMap.moveCamera(CameraUpdateFactory.newLatLng(location))
        mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(location, 17f))
    }

    inner class ThreadClass1 : Thread() { // inner 클래스 선언.
        override fun run() {
            var reply_title = titleEditText4.text.toString()
            var reply_reply = contentEditText4.text.toString()
            var getSql =
                    "http://192.168.0.160:8088/project1/androidReplyRegisterService.do?CONTENTID=${result_contentid}&MEMBER_ID=${helloID}&REPLY_TITLE=${reply_title}&REPLY_REPLY=" + reply_reply + "&REPLY_SCORE=" + ((rating1).toDouble()).toInt();
            var connection: URL = URL(getSql)
            var httpCon: HttpURLConnection = connection.openConnection() as HttpURLConnection
            var in1: BufferedReader = BufferedReader(InputStreamReader(httpCon.inputStream))
            in1.close()
        }
    }

    inner class replyListThread : Thread() {
        override fun run() {
            val sb = StringBuilder()

            try {
                replyList = java.util.ArrayList()
                val page = "http://192.168.0.160:8088/project1/androidReplyListService.do"
                val url = URL(page)
                val conn = url.openConnection() as HttpURLConnection
                if (conn != null) {
                    conn.connectTimeout = 10000 // 10초
                    conn.useCaches = false // 캐시를 쓰지 않겠다.
                    if (conn.responseCode == 200) { // 응답코드가 200이면 접속 성공.
                        val br = BufferedReader(
                                InputStreamReader(conn.inputStream, "UTF-8")
                        )
                        while (true) {
                            val line = br.readLine() ?: break // 한줄 읽기
                            sb.append(line + "\n")
                        }
                        br.close()
                    }
                    conn.disconnect() // 접속 종료.
                }
                //String 을 json 객체로 변환.
                val jObj = JSONObject(sb.toString())
                val jArray = jObj.get("sendData") as JSONArray
                // sendData는 배열.
                for (i in 0 until jArray.length()) {
                    //i 번째 json 객체를 가져와서
                    val row = jArray.getJSONObject(i)
                    replydto = ReplyDTO()

                    replydto.reply_contentid = row.getString("CONTENTID")
                    replydto.member_id = row.getString("MEMBER_ID")
                    replydto.reply_title = row.getString("REPLY_TITLE")
                    replydto.reply_reply = row.getString("REPLY_REPLY")
                    replydto.reply_score = row.getString("REPLY_SCORE")
                    replydto.reply_date = row.getString("REPLY_DATE")

                    if (result_contentid.equals(replydto.reply_contentid)) {
                        replyList.add(replydto)
                    } else {
                        // 아무것도 하지 말아라.
                    }
                }

                if (replyList.size > 0) {
                    Log.d("bbb", replyList.get(0).reply_title + "바보")
                    var myAdapter5: MyAdapter5 = MyAdapter5(replyList)
                    RRecyclerView.setAdapter(myAdapter5)
                }
                // adapter 연결 부분.
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }

    }

    fun updateMethod(s1: String, f1: Float, s0: String) {
        contentEditText4.setText(s1)
        ratingBar4.rating = f1
        titleEditText4.setText(s0)

        replyResgierButton4.visibility = View.GONE
        replyUpdateButton4.visibility = View.VISIBLE

        preUpdateEditText = s1
    }

    fun resetMethod() {
        val intent = intent
        finish()
        startActivity(intent)
    }

    inner class replyListUpdateThread : Thread() {
        override fun run() {
            var update_title = titleEditText4.text.toString()
            var update_reply = contentEditText4.text.toString()
            var getSql =
                    "http://192.168.0.160:8088/project1/androidReplyUpdateService.do?CONTENTID=${result_contentid}&MEMBER_ID=${helloID}&preUpdateEditText=${preUpdateEditText}&REPLY_REPLY=${update_reply}&REPLY_SCORE=${((rating1).toDouble()).toInt()}&REPLY_TITLE=${update_title}";
            var connection: URL = URL(getSql)
            var httpCon: HttpURLConnection = connection.openConnection() as HttpURLConnection
            var in1: BufferedReader = BufferedReader(InputStreamReader(httpCon.inputStream))
            in1.close()
        }
    }
}
