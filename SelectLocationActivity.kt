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
import kotlinx.android.synthetic.main.activity_select_location.*
import org.jetbrains.anko.startActivity
import org.jetbrains.anko.toast

class SelectLocationActivity : AppCompatActivity() {

    private lateinit var spinner1: Spinner
    private lateinit var spinner2: Spinner
    private lateinit var spinner3: Spinner
    lateinit var arrayList1: ArrayList<String>
    lateinit var arrayAdapter1: ArrayAdapter<String>
    lateinit var arrayList2: ArrayList<String>
    lateinit var arrayAdapter2: ArrayAdapter<String>
    lateinit var arrayList3: ArrayList<String>
    lateinit var arrayAdapter3: ArrayAdapter<String>

    private lateinit var spinner: Spinner
    lateinit var SelectLocationSpinnerList: ArrayList<String>
    lateinit var SelectLocationSpinnerAdapter: ArrayAdapter<String>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_select_location)

        val actionbar: ActionBar? = supportActionBar
        actionbar?.hide() // 액션바 숨김

        SelectLocationSpinnerList = ArrayList()
        SelectLocationSpinnerList.add("---항목선택---")
        SelectLocationSpinnerList.add("조명등 연동하기")
        SelectLocationSpinnerList.add("스케줄 관리하기")
        SelectLocationSpinnerList.add("개발자 정보")

        SelectLocationSpinnerAdapter =
                ArrayAdapter<String>(applicationContext, android.R.layout.simple_spinner_dropdown_item, SelectLocationSpinnerList)

        spinner = findViewById(R.id.selectLocationSpinner) as Spinner
        spinner.setAdapter(SelectLocationSpinnerAdapter)
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
            MainLoginTextView11.text = helloID +"(님)"
        }

        MainLoginTextView11.setOnClickListener { view ->
            if (MainLoginTextView11.text.equals("로그인")) {
                // 로그인 창으로 이동
                toast("로그인 페이지 이동")
                startActivity<LoginActivity>()
            } else {
                helloID = ""
                MainLoginTextView11.text = "로그인"
                toast("로그아웃 완료!")
            }
        }

        val title = intent.getStringExtra("title")
        SearchTitleTextView.text = title

        reSearchButton.setOnClickListener { view ->
            SearchLayout.visibility = View.VISIBLE
            SearchResultLayout.visibility = View.GONE
        }

        MainTItleTextView11.setOnClickListener { view ->
            val intent = Intent(this, MainActivity::class.java)
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
            startActivity(intent)
        }

        SearchResultButton.setOnClickListener { view ->
            pageNo = 1
            if (contenttypeid.equals("")) {
                toast("분류를 선택해주세요~")
            } else {
                // 리스트뷰 작업 처리
                mRecyclerView = findViewById(R.id.recycler_view) as RecyclerView
                mRecyclerView.setHasFixedSize(true)
                var mLayoutManager = LinearLayoutManager(this)
                mRecyclerView.setLayoutManager(mLayoutManager)

                GetXMLTask().execute()

                SearchLayout.visibility = View.GONE
                SearchResultLayout.visibility = View.VISIBLE
            }
        }

//        nextButton.setOnClickListener { view ->
//            cnt++
//            pageNo++
//            bm2 = ArrayList()
//
//            GetXMLTask().execute()
//
//            if (dataList.size < 10) {
//                nextButton.visibility = View.INVISIBLE
//            } else {
//                nextButton.visibility = View.VISIBLE
//            }
//
//            if (pageNo == 1) {
//                preButton.visibility = View.INVISIBLE
//            } else {
//                preButton.visibility = View.VISIBLE
//            }
//        }

//        preButton.setOnClickListener { view ->
//            pageNo--
//            cnt++
//            bm2 = ArrayList()
//
//            GetXMLTask().execute()
//
//            if (dataList.size < 10) {
//                nextButton.visibility = View.INVISIBLE
//            } else {
//                nextButton.visibility = View.VISIBLE
//            }
//
//            if (pageNo == 1) {
//                preButton.visibility = View.INVISIBLE
//            } else {
//                preButton.visibility = View.VISIBLE
//            }
//        }

        arrayList2 = ArrayList()
        arrayList2.add("-----")
        arrayList2.add("음식")
        arrayList2.add("숙박")
        arrayList2.add("액티비티")

        arrayList3 = ArrayList()
        arrayList3.add("-----")
        arrayList3.add("조회순")
        arrayList3.add("제목순")

        if (areaCode.equals("1")) {
            arrayList1 = ArrayList()
            arrayList1.add("-----")
            arrayList1.add("강남구")
            arrayList1.add("강동구")
            arrayList1.add("강북구")
            arrayList1.add("강서구")
            arrayList1.add("관악구")
            arrayList1.add("광건구")
            arrayList1.add("구로구")
            arrayList1.add("금천구")
            arrayList1.add("노원구")
            arrayList1.add("도봉구")
            arrayList1.add("동대문구")
            arrayList1.add("동작구")
            arrayList1.add("마포구")
            arrayList1.add("서대문구")
            arrayList1.add("서초구")
            arrayList1.add("성동구")
            arrayList1.add("성북구")
            arrayList1.add("송파구")
            arrayList1.add("양천구")
            arrayList1.add("영등포구")
            arrayList1.add("용산구")
            arrayList1.add("은평구")
            arrayList1.add("종로구")
            arrayList1.add("중구")
            arrayList1.add("중량구")
        } else if (areaCode.equals("2")) {
            arrayList1 = ArrayList()
            arrayList1.add("-----")
            arrayList1.add("강화군")
            arrayList1.add("계양구")
            arrayList1.add("미추홀구")
            arrayList1.add("남동구")
            arrayList1.add("동구")
            arrayList1.add("부평구")
            arrayList1.add("서구")
            arrayList1.add("연수구")
            arrayList1.add("웅진구")
            arrayList1.add("중구")
        } else if (areaCode.equals("3")) {
            arrayList1 = ArrayList()
            arrayList1.add("-----")
            arrayList1.add("대덕구")
            arrayList1.add("동구")
            arrayList1.add("서구")
            arrayList1.add("유성구")
            arrayList1.add("중구")
        } else if (areaCode.equals("4")) {
            arrayList1 = ArrayList()
            arrayList1.add("-----")
            arrayList1.add("남구")
            arrayList1.add("달서구")
            arrayList1.add("달성군")
            arrayList1.add("동구")
            arrayList1.add("북구")
            arrayList1.add("서구")
            arrayList1.add("수성구")
            arrayList1.add("중구")
        } else if (areaCode.equals("5")) {
            arrayList1 = ArrayList()
            arrayList1.add("-----")
            arrayList1.add("광산구")
            arrayList1.add("남구")
            arrayList1.add("동구")
            arrayList1.add("북구")
            arrayList1.add("서구")
        } else if (areaCode.equals("6")) {
            arrayList1 = ArrayList()
            arrayList1.add("-----")
            arrayList1.add("강서구")
            arrayList1.add("금정구")
            arrayList1.add("기장군")
            arrayList1.add("남구")
            arrayList1.add("동구")
            arrayList1.add("동래구")
            arrayList1.add("부산진구")
            arrayList1.add("북구")
            arrayList1.add("사상구")
            arrayList1.add("사하구")
            arrayList1.add("서구")
            arrayList1.add("수영구")
            arrayList1.add("연제구")
            arrayList1.add("영도구")
            arrayList1.add("중구")
            arrayList1.add("해운대구")
        } else if (areaCode.equals("7")) {
            arrayList1 = ArrayList()
            arrayList1.add("-----")
            arrayList1.add("중구")
            arrayList1.add("남구")
            arrayList1.add("동구")
            arrayList1.add("북구")
            arrayList1.add("울주군")
        } else if (areaCode.equals("8")) {
            arrayList1 = ArrayList()
            arrayList1.add("-----")
            arrayList1.add("세종특별자치시")
        } else if (areaCode.equals("31")) {
            arrayList1 = ArrayList()
            arrayList1.add("-----")
            arrayList1.add("가평군")
            arrayList1.add("고양시")
            arrayList1.add("과천시")
            arrayList1.add("광명시")
            arrayList1.add("광주시")
            arrayList1.add("구리시")
            arrayList1.add("군포시")
            arrayList1.add("김포시")
            arrayList1.add("남양주시")
            arrayList1.add("동두천시")
            arrayList1.add("부천시")
            arrayList1.add("성남시")
            arrayList1.add("수원시")
            arrayList1.add("시흥시")
            arrayList1.add("안산시")
            arrayList1.add("안성시")
            arrayList1.add("안양시")
            arrayList1.add("양주시")
            arrayList1.add("양평군")
            arrayList1.add("여주시")
            arrayList1.add("연천군")
            arrayList1.add("오산시")
            arrayList1.add("용인시")
            arrayList1.add("의왕시")
            arrayList1.add("의정부시")
            arrayList1.add("이천시")
            arrayList1.add("파주시")
            arrayList1.add("평택시")
            arrayList1.add("포천시")
            arrayList1.add("하남시")
            arrayList1.add("화성시")
        } else if (areaCode.equals("32")) {
            arrayList1 = ArrayList()
            arrayList1.add("-----")
            arrayList1.add("강릉시")
            arrayList1.add("고성군")
            arrayList1.add("동해시")
            arrayList1.add("삼척시")
            arrayList1.add("속초시")
            arrayList1.add("양구군")
            arrayList1.add("양양군")
            arrayList1.add("영월군")
            arrayList1.add("원주시")
            arrayList1.add("인제군")
            arrayList1.add("정선군")
            arrayList1.add("철원군")
            arrayList1.add("춘천시")
            arrayList1.add("태백시")
            arrayList1.add("평창군")
            arrayList1.add("홍천군")
            arrayList1.add("화천군")
            arrayList1.add("횡성군")
        } else if (areaCode.equals("33")) {
            arrayList1 = ArrayList()
            arrayList1.add("-----")
            arrayList1.add("괴산군")
            arrayList1.add("단양군")
            arrayList1.add("보은군")
            arrayList1.add("영동군")
            arrayList1.add("옥천군")
            arrayList1.add("음성군")
            arrayList1.add("제천시")
            arrayList1.add("진천군")
            arrayList1.add("청원군")
            arrayList1.add("청주시")
            arrayList1.add("충주시")
            arrayList1.add("종평군")
        } else if (areaCode.equals("34")) {
            arrayList1 = ArrayList()
            arrayList1.add("-----")
            arrayList1.add("공주시")
            arrayList1.add("금산군")
            arrayList1.add("논산시")
            arrayList1.add("당진시")
            arrayList1.add("보령시")
            arrayList1.add("부여군")
            arrayList1.add("서산시")
            arrayList1.add("서천군")
            arrayList1.add("아산시")
            arrayList1.add("예산군")
            arrayList1.add("천안시")
            arrayList1.add("청양군")
            arrayList1.add("태안군")
            arrayList1.add("홍성군")
            arrayList1.add("계룡시")
        } else if (areaCode.equals("35")) {
            arrayList1 = ArrayList()
            arrayList1.add("-----")
            arrayList1.add("경산시")
            arrayList1.add("경주시")
            arrayList1.add("고령군")
            arrayList1.add("구미시")
            arrayList1.add("군위군")
            arrayList1.add("김천시")
            arrayList1.add("문경시")
            arrayList1.add("봉화군")
            arrayList1.add("상주시")
            arrayList1.add("성주군")
            arrayList1.add("안동시")
            arrayList1.add("영덕군")
            arrayList1.add("영양군")
            arrayList1.add("영주시")
            arrayList1.add("영천시")
            arrayList1.add("예천군")
            arrayList1.add("을릉군")
            arrayList1.add("울진군")
            arrayList1.add("의성군")
            arrayList1.add("청도군")
            arrayList1.add("청송군")
            arrayList1.add("칠곡군")
            arrayList1.add("포항시")
        } else if (areaCode.equals("36")) {
            arrayList1 = ArrayList()
            arrayList1.add("-----")
            arrayList1.add("거제시")
            arrayList1.add("거창군")
            arrayList1.add("고성군")
            arrayList1.add("김해시")
            arrayList1.add("남해군")
            arrayList1.add("마산시")
            arrayList1.add("밀양시")
            arrayList1.add("사천시")
            arrayList1.add("산청군")
            arrayList1.add("양산시")
            arrayList1.add("의령군")
            arrayList1.add("진주시")
            arrayList1.add("진해시")
            arrayList1.add("창녕군")
            arrayList1.add("창원시")
            arrayList1.add("통영시")
            arrayList1.add("하동군")
            arrayList1.add("함안군")
            arrayList1.add("함양군")
            arrayList1.add("합천군")
        } else if (areaCode.equals("37")) {
            arrayList1 = ArrayList()
            arrayList1.add("-----")
            arrayList1.add("고창군")
            arrayList1.add("군산시")
            arrayList1.add("김제시")
            arrayList1.add("남원시")
            arrayList1.add("무주군")
            arrayList1.add("부안군")
            arrayList1.add("순창군")
            arrayList1.add("완주군")
            arrayList1.add("익산시")
            arrayList1.add("임실군")
            arrayList1.add("장수군")
            arrayList1.add("전주시")
            arrayList1.add("정읍시")
            arrayList1.add("진안군")
        } else if (areaCode.equals("38")) {
            arrayList1 = ArrayList()
            arrayList1.add("-----")
            arrayList1.add("강진군")
            arrayList1.add("고흥군")
            arrayList1.add("곡성군")
            arrayList1.add("광양시")
            arrayList1.add("구례군")
            arrayList1.add("나주시")
            arrayList1.add("담양군")
            arrayList1.add("목포시")
            arrayList1.add("무안군")
            arrayList1.add("보성군")
            arrayList1.add("순천시")
            arrayList1.add("신안군")
            arrayList1.add("여수시")
            arrayList1.add("-----")
            arrayList1.add("-----")
            arrayList1.add("영광군")
            arrayList1.add("영암군")
            arrayList1.add("완도군")
            arrayList1.add("장성군")
            arrayList1.add("장흥군")
            arrayList1.add("진도군")
            arrayList1.add("함평군")
            arrayList1.add("해남군")
            arrayList1.add("화순군")
        } else if (areaCode.equals("39")) {
            arrayList1 = ArrayList()
            arrayList1.add("-----")
            arrayList1.add("남제주군")
            arrayList1.add("북제주군")
            arrayList1.add("서귀포시")
            arrayList1.add("제주시")
        }

        arrayAdapter1 =
                ArrayAdapter<String>(applicationContext, android.R.layout.simple_spinner_dropdown_item, arrayList1)

        spinner1 = findViewById(R.id.sigunguSpinner) as Spinner
        spinner1.setAdapter(arrayAdapter1)
        spinner1.setOnItemSelectedListener(object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(adapterView: AdapterView<*>, view: View, i: Int, l: Long) {
                if (i == 0) {
                    sigunguCode = ""
                } else if (i == 1) {
                    sigunguCode = "1"
                } else if (i == 2) {
                    sigunguCode = "2"
                } else if (i == 3) {
                    sigunguCode = "3"
                } else if (i == 4) {
                    sigunguCode = "4"
                } else if (i == 5) {
                    sigunguCode = "5"
                } else if (i == 6) {
                    sigunguCode = "6"
                } else if (i == 7) {
                    sigunguCode = "7"
                } else if (i == 8) {
                    sigunguCode = "8"
                } else if (i == 9) {
                    sigunguCode = "9"
                } else if (i == 10) {
                    sigunguCode = "10"
                } else if (i == 11) {
                    sigunguCode = "11"
                } else if (i == 12) {
                    sigunguCode = "12"
                } else if (i == 13) {
                    sigunguCode = "13"
                } else if (i == 14) {
                    sigunguCode = "14"
                } else if (i == 15) {
                    sigunguCode = "15"
                } else if (i == 16) {
                    sigunguCode = "16"
                } else if (i == 17) {
                    sigunguCode = "17"
                } else if (i == 18) {
                    sigunguCode = "18"
                } else if (i == 19) {
                    sigunguCode = "19"
                } else if (i == 20) {
                    sigunguCode = "20"
                } else if (i == 21) {
                    sigunguCode = "21"
                } else if (i == 22) {
                    sigunguCode = "22"
                } else if (i == 23) {
                    sigunguCode = "23"
                } else if (i == 24) {
                    sigunguCode = "24"
                } else if (i == 25) {
                    sigunguCode = "25"
                } else if (i == 26) {
                    sigunguCode = "26"
                } else if (i == 27) {
                    sigunguCode = "27"
                } else if (i == 28) {
                    sigunguCode = "28"
                } else if (i == 29) {
                    sigunguCode = "29"
                } else if (i == 30) {
                    sigunguCode = "30"
                } else if (i == 31) {
                    sigunguCode = "31"
                }
            }

            override fun onNothingSelected(adapterView: AdapterView<*>) {}
        })

        arrayAdapter2 =
                ArrayAdapter<String>(applicationContext, android.R.layout.simple_spinner_dropdown_item, arrayList2)

        spinner2 = findViewById(R.id.contentIDSpinner) as Spinner
        spinner2.setAdapter(arrayAdapter2)
        spinner2.setOnItemSelectedListener(object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(adapterView: AdapterView<*>, view: View, i: Int, l: Long) {
                if (i == 0) {
                } else if (i == 1) {
                    contenttypeid = "39"
                } else if (i == 2) {
                    contenttypeid = "32"
                } else if (i == 3) {
                    contenttypeid = "28"
                }
            }

            override fun onNothingSelected(adapterView: AdapterView<*>) {}
        })

        arrayAdapter3 =
                ArrayAdapter<String>(applicationContext, android.R.layout.simple_spinner_dropdown_item, arrayList3)

        spinner3 = findViewById(R.id.arrangeSpinner) as Spinner
        spinner3.setAdapter(arrayAdapter3)
        spinner3.setOnItemSelectedListener(object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(adapterView: AdapterView<*>, view: View, i: Int, l: Long) {
                if (i == 0) {
                    arrange = "R"
                } else if (i == 1) {
                    arrange = "P"
                } else if (i == 2) {
                    arrange = "O"
                }
            }

            override fun onNothingSelected(adapterView: AdapterView<*>) {}
        })
    }


}
