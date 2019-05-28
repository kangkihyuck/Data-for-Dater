package com.applandeo.materialcalendarsampleapp

import android.content.Intent
import android.graphics.Bitmap
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
import kotlinx.android.synthetic.main.activity_result5.*
import org.jetbrains.anko.startActivity
import org.jetbrains.anko.toast
import org.json.JSONArray
import org.json.JSONObject
import java.io.BufferedReader
import java.io.InputStreamReader
import java.net.HttpURLConnection
import java.net.URL

class ResultActivity5 : AppCompatActivity(), OnMapReadyCallback {

    private lateinit var mMap: GoogleMap
    lateinit var bm4: ArrayList<Bitmap>
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
    lateinit var ResultSpinnerList3: ArrayList<String>
    lateinit var ResultSpinnerAdapter3: ArrayAdapter<String>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_result5)
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.

        ResultSpinnerList3 = ArrayList()
        ResultSpinnerList3.add("---항목선택---")
        ResultSpinnerList3.add("조명등 연동하기")
        ResultSpinnerList3.add("스케줄 관리하기")
        ResultSpinnerList3.add("개발자 정보")

        ResultSpinnerAdapter3 =
                ArrayAdapter<String>(applicationContext, android.R.layout.simple_spinner_dropdown_item, ResultSpinnerList3)

        spinner = findViewById(R.id.resultSpinner3) as Spinner
        spinner.setAdapter(ResultSpinnerAdapter3)
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

        val actionbar: ActionBar? = supportActionBar
        actionbar?.hide() // 액션바 숨김

        resultCnt = 3

        mContext = this

        RRecyclerView = findViewById(R.id.recycler_view_content3) as RecyclerView
        RRecyclerView.setHasFixedSize(true)
        var mLayoutManager = LinearLayoutManager(this)
        RRecyclerView.setLayoutManager(mLayoutManager)

//        var thread2 = replyListThread()
//        thread2.start()

        if(!helloID.equals("")){
            MainLoginTextView8.text = helloID +"(님)"
            replyLayout3.visibility = View.VISIBLE
            replyAlarmTextView3.visibility = View.INVISIBLE
        } else {
            replyLayout3.visibility = View.GONE
            replyAlarmTextView3.visibility = View.VISIBLE
        }

        MainTItleTextView8.setOnClickListener { view ->
            val intent = Intent(this, MainActivity::class.java)
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
            startActivity(intent)
        }

        MainLoginTextView8.setOnClickListener {view ->
            if (MainLoginTextView8.text.equals("로그인")) {
                // 로그인 창으로 이동
                toast("로그인 페이지 이동")
                startActivity<LoginActivity>()
            } else {
                helloID = ""
                MainLoginTextView8.text = "로그인"
                toast("로그아웃 완료!")
            }
        }

        result_title = intent.getStringExtra("title")
        var result_rating = intent.getStringExtra("rating")
        result_image1 = intent.getStringExtra("image2")
        var result_Address = intent.getStringExtra("address")
        result_contentid = intent.getStringExtra("contentid")
        var result_Telephone = intent.getStringExtra("telephone")
        var result_contenttype = intent.getStringExtra("contenttypeid")
        result_Latitude = intent.getDoubleExtra("latitude", 0.0)
        result_Longitude = intent.getDoubleExtra("longitude", 0.0)

        SystemClock.sleep(1000)

        var s1: String = cultureList.get(0).culture_accomcountculture.replace("<br />", "")
        var s2 : String = cultureList.get(0).culture_chkcreditcardculture.replace("<br />", "")
        var s3 : String = cultureList.get(0).culture_discountinfo.replace("<br />", "")
        var s4 : String = cultureList.get(0).culture_parkingculture.replace("<br />", "")
        var s5 : String = cultureList.get(0).culture_spendtime.replace("<br />", "")
        var s6 : String = cultureList.get(0).culture_usetimeculture.replace("<br />", "")
        var s7 : String = cultureList.get(0).culture_usefee.replace("<br />", "")
        var s8 : String = cultureList.get(0).culture_restdateculture.replace("<br />", "")


        resultTitleTextView5.text = result_title
        averageRatingTextView5.text = result_rating
        Glide.with(this).load(result_image1).into(resultImageView5)
        resultAddressTextView5.text = "주소 : " + result_Address
        resultTelephoneTextView5.text = "전화번호 : " + result_Telephone + "(클릭)"
        resultAccomcountcultureTextView5.text = "수용인원 : " + s1
        resultChkcreditcardcultureTextView5.text = "카드사용여부 : " + s2
        resultDiscountinfoTextView5.text = "할인정보 : " + s3
        resultParkingcultureTextView5.text = "주차 여부 : " + s4
        resultSpendtimeTextView5.text = "관람소요시간 : " + s5
        resultUsetimecultureTextView5.text = "이용시간 : " + s6
        resultUsefeeTextView5.text = "이용요금 : " + s7
        resultRestdatecultureTextView5.text = "휴무일 : " + s8

        if (result_contenttype.equals("39")) {
            contentTypeTextView5.text = "음식"
        } else if (result_contenttype.equals("32")) {
            contentTypeTextView5.text = "숙박"
        } else if (result_contenttype.equals("28")) {
            contentTypeTextView5.text = "액티비티"
        } else if (result_contenttype.equals("12")) {
            contentTypeTextView5.text = "관광지"
        } else if(result_contenttype.equals("14")){
            contentTypeTextView5.text = "문화시설"
        }

        toggleButton5.setOnClickListener { view ->
            if (toggleButton5.isChecked) {
                goneLayout5.visibility = View.VISIBLE
                toggleButton5.setBackgroundDrawable(resources.getDrawable(R.drawable.ic_arrow_drop_up_black_24dp))
            } else {
                goneLayout5.visibility = View.GONE
                toggleButton5.setBackgroundDrawable(resources.getDrawable(R.drawable.ic_arrow_drop_down_black_24dp))
            }
        }

        replyToggleButton5.setOnClickListener { view ->
            if(replyToggleButton5.isChecked){
                recycler_view_content3.visibility = View.VISIBLE
                replyToggleButton5.setBackgroundDrawable(resources.getDrawable(R.drawable.ic_arrow_drop_up_black_24dp))
            } else {
                recycler_view_content3.visibility = View.GONE
                replyToggleButton5.setBackgroundDrawable(resources.getDrawable(R.drawable.ic_arrow_drop_down_black_24dp))
            }
        }

        val rb = findViewById(R.id.ratingBar3) as RatingBar

        rb.setOnRatingBarChangeListener { ratingBar, rating, fromUser ->
            rating1 = rating.toString()
            toast(rating1)
        }

        replyResgierButton3.setOnClickListener {view ->
            if(titleEditText3.text.toString().equals("")){
                if(contentEditText3.text.toString().equals("")){
                    toast("댓글 제목과 내용을 입력하세요.")
                } else {
                    toast("댓글 내용을 입력하세요.")
                }
            }else {
                if(contentEditText3.text.toString().equals("")){
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

        replyAlarmTextView3.setOnClickListener { view ->
            toast("로그인 페이지 이동")
            startActivity<LoginActivity>()
        }

        replyUpdateButton3.setOnClickListener { view ->
            // editText 내용과 Rating 값을 update 진행.
            var updateThread = replyListUpdateThread()
            updateThread.start()

            toast("수정 완료")

            replyUpdateButton3.visibility = View.GONE
            replyResgierButton3.visibility = View.VISIBLE

            SystemClock.sleep(100)

            val intent = intent
            finish()
            startActivity(intent)

        }

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
            var reply_title = titleEditText3.text.toString()
            Log.d("aaa", reply_title)
            var reply_reply = contentEditText3.text.toString()
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
                Log.d("qqq", "1.1~")
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

                    Log.d("qqq", replydto.reply_contentid + "멍청이")

                    if(result_contentid.equals(replydto.reply_contentid)){
                        replyList.add(replydto)
                    } else {
                        // 아무것도 하지 말아라.
                    }
                }

                if(replyList.size > 0){
                    var myAdapter5: MyAdapter5 = MyAdapter5(replyList)
                    RRecyclerView.setAdapter(myAdapter5)
                }
                // adapter 연결 부분.

            } catch (e: Exception) {
                e.printStackTrace()
            }
        }

    }

    fun updateMethod(s1 : String, f1 : Float, s0 : String){
        contentEditText3.setText(s1)
        ratingBar3.rating = f1
        titleEditText3.setText(s0)

        replyResgierButton3.visibility = View.GONE
        replyUpdateButton3.visibility = View.VISIBLE

        preUpdateEditText = s1
    }

    fun resetMethod(){
        val intent = intent
        finish()
        startActivity(intent)
    }

    inner class replyListUpdateThread : Thread(){
        override fun run() {
            var update_title = titleEditText3.text.toString()
            var update_reply = contentEditText3.text.toString()
            var getSql =
                    "http://192.168.0.160:8088/project1/androidReplyUpdateService.do?CONTENTID=${result_contentid}&MEMBER_ID=${helloID}&preUpdateEditText=${preUpdateEditText}&REPLY_REPLY=${update_reply}&REPLY_SCORE=${((rating1).toDouble()).toInt()}&REPLY_TITLE=${update_title}";
            var connection: URL = URL(getSql)
            var httpCon: HttpURLConnection = connection.openConnection() as HttpURLConnection
            var in1: BufferedReader = BufferedReader(InputStreamReader(httpCon.inputStream))
            in1.close()
        }
    }
}