package com.applandeo.materialcalendarsampleapp

import android.content.Intent
import android.os.Bundle
import android.support.v7.app.ActionBar
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.util.Log
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Spinner
import com.airbnb.lottie.LottieAnimationView
import kotlinx.android.synthetic.main.activity_tourist_destination.*
import org.jetbrains.anko.startActivity
import org.jetbrains.anko.toast

class TouristDestinationActivity : AppCompatActivity() {
    private lateinit var spinner5: Spinner
    lateinit var arrayList5: ArrayList<String>
    lateinit var arrayAdapter5: ArrayAdapter<String>

    private lateinit var spinner: Spinner
    lateinit var TouristSpinnerList: ArrayList<String>
    lateinit var TouristSpinnerAdapter: ArrayAdapter<String>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_tourist_destination)

//        mRecyclerView = findViewById(R.id.recycler_view_destination) as RecyclerView
//        mRecyclerView.setHasFixedSize(true)
//        var mLayoutManager = LinearLayoutManager(this)
//        mRecyclerView.setLayoutManager(mLayoutManager)

        val actionbar: ActionBar? = supportActionBar
        actionbar?.hide() // 액션바 숨김

        val animationView = findViewById(R.id.animation_view) as LottieAnimationView
        animationView.setAnimation("Microinteraction.json")
        animationView.loop(true)
        animationView.playAnimation()



        destinationTitleTextView.text = intent.getStringExtra("tourTitle")

        TouristSpinnerList = ArrayList()
        TouristSpinnerList.add("---항목선택---")
        TouristSpinnerList.add("조명등 연동하기")
        TouristSpinnerList.add("스케줄 관리하기")
        TouristSpinnerList.add("개발자 정보")

        TouristSpinnerAdapter =
                ArrayAdapter<String>(applicationContext, android.R.layout.simple_spinner_dropdown_item, TouristSpinnerList)

        spinner = findViewById(R.id.touristSpinner) as Spinner
        spinner.setAdapter(TouristSpinnerAdapter)
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

        if (!helloID.equals("")) {
            MainLoginTextView12.text = helloID +"(님)"
        }

        MainTItleTextView12.setOnClickListener { view ->
            val intent = Intent(this, MainActivity::class.java)
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
            startActivity(intent)
        }

        MainLoginTextView12.setOnClickListener { view ->
            if (MainLoginTextView12.text.equals("로그인")) {
                // 로그인 창으로 이동
                toast("로그인 페이지 이동")
                startActivity<LoginActivity>()
            } else {
                helloID = ""
                MainLoginTextView12.text = "로그인"
                toast("로그아웃 완료!")
            }
        }
        animation_view.setOnClickListener { view ->
            mRecyclerView = findViewById(R.id.recycler_view_destination) as RecyclerView
            mRecyclerView.setHasFixedSize(true)
            var mLayoutManager = LinearLayoutManager(this)
            mRecyclerView.setLayoutManager(mLayoutManager)

            Log.d("aaa", "GetXMLTask4 실행")
            GetXMLTask4().execute()
        }

        arrayList5 = ArrayList()
        arrayList5.add("-----")
        arrayList5.add("서울")
        arrayList5.add("인천")
        arrayList5.add("대전")
        arrayList5.add("대구")
        arrayList5.add("광주")
        arrayList5.add("부산")
        arrayList5.add("울산")
        arrayList5.add("세종특별자치시")
        arrayList5.add("경기도")
        arrayList5.add("강원도")
        arrayList5.add("충청북도")
        arrayList5.add("충청남도")
        arrayList5.add("경상북도")
        arrayList5.add("경상남도")
        arrayList5.add("전라북도")
        arrayList5.add("전라남도")
        arrayList5.add("제주도")

        arrayAdapter5 =
                ArrayAdapter<String>(applicationContext, android.R.layout.simple_spinner_dropdown_item, arrayList5)

        spinner5 = findViewById(R.id.locationAreaCodeSpinner1) as Spinner
        spinner5.setAdapter(arrayAdapter5)
        spinner5.setOnItemSelectedListener(object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(adapterView: AdapterView<*>, view: View, i: Int, l: Long) {
                if (arrayList5[i].equals("-----")) {
                    // 기본값
                } else if (arrayList5[i].equals("서울")) {
                    areaCode = "1"
                } else if (arrayList5[i].equals("인천")) {
                    areaCode = "2"
                } else if (arrayList5[i].equals("대전")) {
                    areaCode = "3"
                } else if (arrayList5[i].equals("대구")) {
                    areaCode = "4"
                } else if (arrayList5[i].equals("광주")) {
                    areaCode = "5"
                } else if (arrayList5[i].equals("부산")) {
                    areaCode = "6"
                } else if (arrayList5[i].equals("울산")) {
                    areaCode = "7"
                } else if (arrayList5[i].equals("세종특별자치시")) {
                    areaCode = "8"
                } else if (arrayList5[i].equals("경기도")) {
                    areaCode = "31"
                } else if (arrayList5[i].equals("강원도")) {
                    areaCode = "32"
                } else if (arrayList5[i].equals("충청북도")) {
                    areaCode = "33"
                } else if (arrayList5[i].equals("충청남도")) {
                    areaCode = "34"
                } else if (arrayList5[i].equals("경상북도")) {
                    areaCode = "35"
                } else if (arrayList5[i].equals("경상남도")) {
                    areaCode = "36"
                } else if (arrayList5[i].equals("전라북도")) {
                    areaCode = "37"
                } else if (arrayList5[i].equals("전라남도")) {
                    areaCode = "38"
                } else if (arrayList5[i].equals("제주도")) {
                    areaCode = "39"
                }

                if (!arrayList5[i].equals("-----")) {
//                    Toast.makeText(applicationContext, arrayList2[i] + " 선택!!", Toast.LENGTH_LONG).show()
                }
            }

            override fun onNothingSelected(adapterView: AdapterView<*>) {}
        })
    }
}
