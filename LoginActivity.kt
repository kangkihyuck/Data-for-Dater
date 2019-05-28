package com.applandeo.materialcalendarsampleapp

import android.content.Intent
import android.inputmethodservice.Keyboard
import android.net.Uri
import android.os.Bundle
import android.os.SystemClock
import android.support.v7.app.ActionBar
import android.support.v7.app.AppCompatActivity

import kotlinx.android.synthetic.main.activity_login.*

import org.jetbrains.anko.startActivity
import org.jetbrains.anko.toast
import org.json.JSONArray
import org.json.JSONObject
import java.io.BufferedReader
import java.io.InputStreamReader
import java.net.HttpURLConnection
import java.net.URL
import java.util.*

class LoginActivity : AppCompatActivity() {

    private lateinit var keyboardVisiblitiy:KeyboardVisiblitiy

    lateinit var items: ArrayList<MemberDTO>
    var loginCnt: Int = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        keyboardVisiblitiy = KeyboardVisiblitiy(window,
                onShowKeyboard = {keyboardHeight ->
                    sv_root.run {
                        smoothScrollTo(scrollX, scrollY + keyboardHeight)
                    }
                })




        val actionbar : ActionBar? = supportActionBar
        actionbar?.hide() // 액션바 숨김

        MainTItleTextView5.setOnClickListener { view ->
            val intent = Intent(this, MainActivity::class.java)
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
            startActivity(intent)
        }

        RegisterTextView.setOnClickListener { view ->
            toast("회원가입 페이지로 이동")

            var intent: Intent =
                    Intent(Intent.ACTION_VIEW, Uri.parse("http://192.168.0.160:8088/project1/androidMemberJoinService.do"))
            startActivity(intent)
        }

        SearchTextView.setOnClickListener { view ->
            toast("id / pw 찾기 창으로 이동")
            startActivity<IdPwSearchActivity>()
        }

        LoginButton.setOnClickListener { view ->
            var th = jsonThread()
            th.start()

            SystemClock.sleep(1000)

            if (loginCnt == 1) {
                toast("로그인 수행")
                helloID = LoginIdEditText.text.toString()
                finish()
                startActivity<MainActivity>()
            } else if (loginCnt == 2) {
                toast("p/w 오류")
            } else {
                toast("일치하는 아이디가 없습니다.")
            }
        }
    }

    override fun onDestroy() {
        keyboardVisiblitiy.detachKeyboardListeners()
        super.onDestroy()
    }

    inner class jsonThread : Thread() {
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
                    val dto = MemberDTO()

                    dto.MEMBER_ID = row.getString("MEMBER_ID")
                    dto.MEMBER_PW = row.getString("MEMBER_PW")

                    if (dto.MEMBER_ID.equals(LoginIdEditText.text.toString())) {
                        if (dto.MEMBER_PW.equals(LoginPwEditText.text.toString())) {
                            loginCnt = 1 // 정확한 정보
                            break
                        } else {
                            loginCnt = 2 // pw가 틀렸습니다.
                            break
                        }
                    } else {
                        loginCnt = 3 // 아이디 일치 여부 없음
                    }

                    items.add(dto)
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
