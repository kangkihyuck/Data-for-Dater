package com.applandeo.materialcalendarsampleapp

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.support.v7.app.ActionBar
import android.support.v7.app.AppCompatActivity
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Spinner
import kotlinx.android.synthetic.main.activity_developer.*
import org.jetbrains.anko.email
import org.jetbrains.anko.startActivity
import org.jetbrains.anko.toast

class DeveloperActivity : AppCompatActivity() {

    private lateinit var spinner: Spinner
    lateinit var DeveloperSpinnerList: ArrayList<String>
    lateinit var DeveloperSpinnerAdapter: ArrayAdapter<String>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_developer)

        DeveloperSpinnerList = ArrayList()
        DeveloperSpinnerList.add("---항목선택---")
        DeveloperSpinnerList.add("조명등 연동하기")
        DeveloperSpinnerList.add("스케줄 관리하기")
        DeveloperSpinnerList.add("개발자 정보")

        DeveloperSpinnerAdapter =
                ArrayAdapter<String>(applicationContext, android.R.layout.simple_spinner_dropdown_item, DeveloperSpinnerList)

        spinner = findViewById(R.id.DeveloperSpinner) as Spinner
        spinner.setAdapter(DeveloperSpinnerAdapter)
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

        if (!helloID.equals("")) {
            MainLoginTextView1.text = helloID +"(님)"
        }

        MainTItleTextView1.setOnClickListener { view ->
            val intent = Intent(this, MainActivity::class.java)
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
            startActivity(intent)
        }

        MainLoginTextView1.setOnClickListener { view ->
            if (MainLoginTextView1.text.equals("로그인")) {
                // 로그인 창으로 이동
                toast("로그인 페이지 이동")
                startActivity<LoginActivity>()
            } else {
                helloID = ""
                MainLoginTextView1.text = "로그인"
                toast("로그아웃 완료!")
            }
        }

        member1Email.setOnClickListener { view ->
            email("oper5032@gmail.com", "문의 사항을 보내주세요~^^")
        }

        member2Email.setOnClickListener { view ->
            email("jamespark0302@naver.com", "문의 사항을 보내주세요~^^")
        }

        member3Email.setOnClickListener { view ->
            email("mk6322@naver.com", "문의 사항을 보내주세요~^^")
        }

        member4Email.setOnClickListener { view ->
            email("bomi5124@naver.com", "문의 사항을 보내주세요~^^")
        }

        member1Blog.setOnClickListener { view ->
            val url = "http://blog.naver.com/oper4062"
            val intent = Intent(Intent.ACTION_VIEW, Uri.parse(url))
            startActivity(intent)
        }

        member2Blog.setOnClickListener { view ->
            val url = "http://blog.naver.com/jamespark0302"
            val intent = Intent(Intent.ACTION_VIEW, Uri.parse(url))
            startActivity(intent)
        }

        member3Blog.setOnClickListener { view ->
            val url = "http://blog.naver.com/mk6322"
            val intent = Intent(Intent.ACTION_VIEW, Uri.parse(url))
            startActivity(intent)
        }

        member4Blog.setOnClickListener { view ->
            val url = "http://blog.naver.com/bomi5124"
            val intent = Intent(Intent.ACTION_VIEW, Uri.parse(url))
            startActivity(intent)
        }
    }

    override fun onBackPressed() {
        finish()
    }
}

