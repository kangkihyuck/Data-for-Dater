package com.applandeo.materialcalendarsampleapp

import android.content.Intent
import android.os.Bundle
import android.support.v7.app.ActionBar
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.RecyclerView
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Spinner
import kotlinx.android.synthetic.main.activity_location_search.*
import org.jetbrains.anko.startActivity
import org.jetbrains.anko.toast

var areaCode = ""
var sigunguCode = ""
var contenttypeid = ""
var arrange = ""
var pageNo = 1

var dataList: ArrayList<DataDTO> = ArrayList()
var destinationList: ArrayList<DestinationDTO> = ArrayList()
var cultureList: ArrayList<CultureDTO> = ArrayList()
var festivalList: ArrayList<FestivalDTO> = ArrayList()
var shoppingList: ArrayList<ShoppingDTO> = ArrayList()
lateinit var mRecyclerView: RecyclerView

var cnt = 1

class LocationSearchActivity : AppCompatActivity() {

    private lateinit var spinner: Spinner
    lateinit var LocationSearchSpinnerList: ArrayList<String>
    lateinit var LocationSearchSpinnerAdapter: ArrayAdapter<String>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_location_search)

        LocationSearchSpinnerList = ArrayList()
        LocationSearchSpinnerList.add("---항목선택---")
        LocationSearchSpinnerList.add("조명등 연동하기")
        LocationSearchSpinnerList.add("스케줄 관리하기")
        LocationSearchSpinnerList.add("개발자 정보")

        LocationSearchSpinnerAdapter =
                ArrayAdapter<String>(applicationContext, android.R.layout.simple_spinner_dropdown_item, LocationSearchSpinnerList)

        spinner = findViewById(R.id.locationSearchSpinner) as Spinner
        spinner.setAdapter(LocationSearchSpinnerAdapter)
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

        if(!helloID.equals("")){
            MainLoginTextView4.text = helloID +"(님)"
        }

        MainTItleTextView4.setOnClickListener { view ->
            val intent = Intent(this, MainActivity::class.java)
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
            startActivity(intent)
        }

        MainLoginTextView4.setOnClickListener { view ->
            if (MainLoginTextView4.text.equals("로그인")) {
                // 로그인 창으로 이동
                toast("로그인 페이지 이동")
                startActivity<LoginActivity>()
            } else {
                helloID = ""
                MainLoginTextView4.text = "로그인"
                toast("로그아웃 완료!")
            }
        }

        textView1.setOnClickListener { view ->
            //서울
            areaCode = "1"
            startActivity<SelectLocationActivity>(
                    "title" to textView1.text.toString()
            )
        }

        textView2.setOnClickListener { view ->
            //인천
            areaCode = "2"
            startActivity<SelectLocationActivity>(
                    "title" to textView2.text.toString()
            )
        }

        textView3.setOnClickListener { view ->
            //대전
            areaCode = "3"
            startActivity<SelectLocationActivity>(
                    "title" to textView3.text.toString()
            )
        }

        textView4.setOnClickListener { view ->
            //대구
            areaCode = "4"
            startActivity<SelectLocationActivity>(
                    "title" to textView4.text.toString()
            )
        }

        textView5.setOnClickListener { view ->
            //광주
            areaCode = "5"
            startActivity<SelectLocationActivity>(
                    "title" to textView5.text.toString()
            )
        }

        textView6.setOnClickListener { view ->
            //부산
            areaCode = "6"
            startActivity<SelectLocationActivity>(
                    "title" to textView6.text.toString()
            )
        }

        textView7.setOnClickListener { view ->
            //울산
            areaCode = "7"
            startActivity<SelectLocationActivity>(
                    "title" to textView7.text.toString()
            )
        }

        textView8.setOnClickListener { view ->
            //세종
            areaCode = "8"
            startActivity<SelectLocationActivity>(
                    "title" to textView8.text.toString()
            )
        }

        textView9.setOnClickListener { view ->
            //경기
            areaCode = "31"
            startActivity<SelectLocationActivity>(
                    "title" to textView9.text.toString()
            )
        }

        textView10.setOnClickListener { view ->
            //강원
            areaCode = "32"
            startActivity<SelectLocationActivity>(
                    "title" to textView10.text.toString()
            )
        }

        textView11.setOnClickListener { view ->
            //충북
            areaCode = "33"
            startActivity<SelectLocationActivity>(
                    "title" to textView11.text.toString()
            )
        }

        textView12.setOnClickListener { view ->
            //충남
            areaCode = "34"
            startActivity<SelectLocationActivity>(
                    "title" to textView12.text.toString()
            )
        }

        textView13.setOnClickListener { view ->
            //경북
            areaCode = "35"
            startActivity<SelectLocationActivity>(
                    "title" to textView13.text.toString()
            )
        }

        textView14.setOnClickListener { view ->
            //경남
            areaCode = "36"
            startActivity<SelectLocationActivity>(
                    "title" to textView14.text.toString()
            )
        }

        textView15.setOnClickListener { view ->
            //전북
            areaCode = "37"
            startActivity<SelectLocationActivity>(
                    "title" to textView15.text.toString()
            )
        }

        textView16.setOnClickListener { view ->
            //전남
            areaCode = "38"
            startActivity<SelectLocationActivity>(
                    "title" to textView16.text.toString()
            )
        }

        textView17.setOnClickListener { view ->
            //제주
            areaCode = "39"
            startActivity<SelectLocationActivity>(
                    "title" to textView17.text.toString()
            )
        }

    }
}
