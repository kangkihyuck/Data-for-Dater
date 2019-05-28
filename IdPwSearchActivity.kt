package com.applandeo.materialcalendarsampleapp

import android.content.Intent
import android.os.Bundle
import android.os.SystemClock
import android.support.v7.app.ActionBar
import android.support.v7.app.AppCompatActivity
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Spinner
import kotlinx.android.synthetic.main.activity_id_pw_search.*
import org.jetbrains.anko.startActivity
import org.jetbrains.anko.toast
import org.json.JSONArray
import org.json.JSONObject
import java.io.BufferedReader
import java.io.InputStreamReader
import java.net.HttpURLConnection
import java.net.URL
import java.util.*

class IdPwSearchActivity : AppCompatActivity() {

    lateinit var dto: MemberDTO

    lateinit var items: ArrayList<MemberDTO>
    var cnt: Int = 0

    private lateinit var spinner: Spinner
    lateinit var IdPwSearchSpinnerList: ArrayList<String>
    lateinit var IdPwSearchSpinnerAdapter: ArrayAdapter<String>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_id_pw_search)

        IdPwSearchSpinnerList = ArrayList()
        IdPwSearchSpinnerList.add("---항목선택---")
        IdPwSearchSpinnerList.add("조명등 연동하기")
        IdPwSearchSpinnerList.add("스케줄 관리하기")
        IdPwSearchSpinnerList.add("개발자 정보")

        IdPwSearchSpinnerAdapter =
                ArrayAdapter<String>(applicationContext, android.R.layout.simple_spinner_dropdown_item, IdPwSearchSpinnerList)

        spinner = findViewById(R.id.idpwSearchSpinner) as Spinner
        spinner.setAdapter(IdPwSearchSpinnerAdapter)
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

        if(!helloID.equals("")){
            MainLoginTextView2.text = helloID +"(님)"
        }

        MainTItleTextView2.setOnClickListener { view ->
            val intent = Intent(this, MainActivity::class.java)
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
            startActivity(intent)
        }

        MainLoginTextView2.setOnClickListener { view ->
            if(MainLoginTextView2.text.equals("로그인")){
                // 로그인 창으로 이동
                toast("로그인 페이지 이동")
                startActivity<LoginActivity>()
            } else {
                helloID = ""
                MainLoginTextView2.text ="로그인"
                toast("로그아웃 완료!")
            }
        }

        val actionbar: ActionBar? = supportActionBar
        actionbar?.hide() // 액션바 숨김

        idSearchButton.setOnClickListener { view ->
            pwLayout.visibility = View.GONE
            idLayout.visibility = View.VISIBLE
        }

        pwSearchButton.setOnClickListener { view ->
            idLayout.visibility = View.GONE
            pwLayout.visibility = View.VISIBLE
        }

        idSearchButton2.setOnClickListener { view ->
            // id찾기 버튼을 눌렀을 떄
            var idSearchTh = jsonThread1()
            idSearchTh.start()

            SystemClock.sleep(1000)

            if (cnt == 1) {
                toast("ID : " + dto.MEMBER_ID)
            } else if (cnt == 2) {
                toast("일치하는 email이 없습니다.")
            } else {
                toast("일치하는 name이 없습니다.")
            }
        }

        pwSearchButton2.setOnClickListener { view ->
            // pw찾기 버튼을 눌렀을 때
            var pwSearchTh = jsonThread2()
            pwSearchTh.start()

            SystemClock.sleep(1000)

            if (cnt == 1) {
                toast("PW : " + dto.MEMBER_PW)
            } else if (cnt == 2) {
                toast("일치하는 email이 없습니다.")
            } else if (cnt == 3) {
                toast("일치하는 name이 없습니다.")
            } else if (cnt == 4) {
                toast("일치하는 id가 없습니다.")
            }
        }
    }

    inner class jsonThread1 : Thread() {
        override fun run() {
            val sb = StringBuilder()

            try {
                items = ArrayList()
                val page = "http://192.168.0.160:8088/project1/androidLoginService.do"
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
                    dto = MemberDTO()

                    dto.MEMBER_ID = row.getString("MEMBER_ID")
                    dto.MEMBER_NAME = row.getString("MEMBER_NAME")
                    dto.MEMBER_EMAIL = row.getString("MEMBER_EMAIL")

                    if (dto.MEMBER_NAME.equals(nameEditText.text.toString())) {
                        if (dto.MEMBER_EMAIL.equals(emailEditText.text.toString())) {
                            cnt = 1
                            break
                        } else {
                            cnt = 2
                            break;
                        }
                    } else {
                        cnt = 3
                    }

                    items.add(dto)
                }

            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }

    inner class jsonThread2 : Thread() {
        override fun run() {
            val sb = StringBuilder()

            try {
                items = ArrayList()
                val page = "http://192.168.0.160:8088/project1/androidLoginService.do"
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
                    dto = MemberDTO()

                    dto.MEMBER_ID = row.getString("MEMBER_ID")
                    dto.MEMBER_NAME = row.getString("MEMBER_NAME")
                    dto.MEMBER_EMAIL = row.getString("MEMBER_EMAIL")
                    dto.MEMBER_PW = row.getString("MEMBER_PW")

                    items.add(dto)

                    if (dto.MEMBER_ID.equals(idEditText.text.toString())) {
                        if (dto.MEMBER_NAME.equals(nameEditText2.text.toString())) {
                            if (dto.MEMBER_EMAIL.equals(emailEditText2.text.toString())) {
                                cnt == 1
                                break
                            } else {
                                cnt = 2
                                break
                            }
                        } else {
                            cnt = 3
                        }
                    } else {
                        cnt = 4
                    }
                }
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }

    override fun onBackPressed() {
        finish()
    }
}
