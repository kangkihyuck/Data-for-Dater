package com.applandeo.materialcalendarsampleapp

import android.content.Intent
import android.os.Bundle
import android.support.v7.app.ActionBar
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Spinner
import com.airbnb.lottie.LottieAnimationView
import kotlinx.android.synthetic.main.activity_key_word.*
import org.jetbrains.anko.startActivity
import org.jetbrains.anko.toast
import java.net.URLEncoder

var keyword: String = ""

class KeyWordActivity : AppCompatActivity() {

    private lateinit var spinner4: Spinner
    lateinit var arrayList4: ArrayList<String>
    lateinit var arrayAdapter4: ArrayAdapter<String>

    private lateinit var spinner: Spinner
    lateinit var KeyWordSpinnerList: ArrayList<String>
    lateinit var KeyWordSpinnerAdapter: ArrayAdapter<String>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_key_word)

        val animationView = findViewById(R.id.animation_view) as LottieAnimationView
        animationView.setAnimation("Microinteraction.json")
        animationView.loop(true)
        animationView.playAnimation()

        KeyWordSpinnerList = ArrayList()
        KeyWordSpinnerList.add("---항목선택---")
        KeyWordSpinnerList.add("조명등 연동하기")
        KeyWordSpinnerList.add("스케줄 관리하기")
        KeyWordSpinnerList.add("개발자 정보")

        KeyWordSpinnerAdapter =
                ArrayAdapter<String>(applicationContext, android.R.layout.simple_spinner_dropdown_item, KeyWordSpinnerList)

        spinner = findViewById(R.id.keywordSpinner) as Spinner
        spinner.setAdapter(KeyWordSpinnerAdapter)
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

        mContext = this

        val actionbar: ActionBar? = supportActionBar
        actionbar?.hide() // 액션바 숨김

        if (!helloID.equals("")) {
            MainLoginTextView3.text = helloID +"(님)"
        }

        MainTItleTextView3.setOnClickListener { view ->
            val intent = Intent(this, MainActivity::class.java)
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
            startActivity(intent)
        }

        MainLoginTextView3.setOnClickListener { view ->
            if (MainLoginTextView3.text.equals("로그인")) {
                // 로그인 창으로 이동
                toast("로그인 페이지 이동")
                startActivity<LoginActivity>()
            } else {
                helloID = ""
                MainLoginTextView3.text = "로그인"
                toast("로그아웃 완료!")
            }
        }

        arrayList4 = ArrayList()
        arrayList4.add("-----")
        arrayList4.add("서울")
        arrayList4.add("인천")
        arrayList4.add("대전")
        arrayList4.add("대구")
        arrayList4.add("광주")
        arrayList4.add("부산")
        arrayList4.add("울산")
        arrayList4.add("세종특별자치시")
        arrayList4.add("경기도")
        arrayList4.add("강원도")
        arrayList4.add("충청북도")
        arrayList4.add("충청남도")
        arrayList4.add("경상북도")
        arrayList4.add("경상남도")
        arrayList4.add("전라북도")
        arrayList4.add("전라남도")
        arrayList4.add("제주도")

        arrayAdapter4 =
                ArrayAdapter<String>(applicationContext, android.R.layout.simple_spinner_dropdown_item, arrayList4)

        spinner4 = findViewById(R.id.locationAreaCodeSpinner) as Spinner
        spinner4.setAdapter(arrayAdapter4)
        spinner4.setOnItemSelectedListener(object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(adapterView: AdapterView<*>, view: View, i: Int, l: Long) {
                if (arrayList4[i].equals("-----")) {
                    // 기본값
                } else if (arrayList4[i].equals("서울")) {
                    areaCode = "1"
                } else if (arrayList4[i].equals("인천")) {
                    areaCode = "2"
                } else if (arrayList4[i].equals("대전")) {
                    areaCode = "3"
                } else if (arrayList4[i].equals("대구")) {
                    areaCode = "4"
                } else if (arrayList4[i].equals("광주")) {
                    areaCode = "5"
                } else if (arrayList4[i].equals("부산")) {
                    areaCode = "6"
                } else if (arrayList4[i].equals("울산")) {
                    areaCode = "7"
                } else if (arrayList4[i].equals("세종특별자치시")) {
                    areaCode = "8"
                } else if (arrayList4[i].equals("경기도")) {
                    areaCode = "31"
                } else if (arrayList4[i].equals("강원도")) {
                    areaCode = "32"
                } else if (arrayList4[i].equals("충청북도")) {
                    areaCode = "33"
                } else if (arrayList4[i].equals("충청남도")) {
                    areaCode = "34"
                } else if (arrayList4[i].equals("경상북도")) {
                    areaCode = "35"
                } else if (arrayList4[i].equals("경상남도")) {
                    areaCode = "36"
                } else if (arrayList4[i].equals("전라북도")) {
                    areaCode = "37"
                } else if (arrayList4[i].equals("전라남도")) {
                    areaCode = "38"
                } else if (arrayList4[i].equals("제주도")) {
                    areaCode = "39"
                }
            }

            override fun onNothingSelected(adapterView: AdapterView<*>) {}
        })

        animation_view.setOnClickListener { view ->
            // editText에 입력한 값이 검색되어 RecyclerView에 출력되도록 구현.
            keyword = URLEncoder.encode(keywordEditText.text.toString(), "UTF-8")

            mRecyclerView = findViewById(R.id.recycler_view_keyword) as RecyclerView
            mRecyclerView.setHasFixedSize(true)
            var mLayoutManager = LinearLayoutManager(this)
            mRecyclerView.setLayoutManager(mLayoutManager)

            GetXMLTask3().execute()
        }
    }

    fun noKeyWordSearchMethod() {
        toast("검색된 결과가 없습니다.")
    }
}
