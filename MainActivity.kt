package com.applandeo.materialcalendarsampleapp

import android.annotation.TargetApi
import android.app.Activity
import android.bluetooth.BluetoothAdapter
import android.content.DialogInterface
import android.content.Intent
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.support.annotation.RequiresApi
import android.support.v4.view.ViewPager
import android.support.v7.app.ActionBar
import android.support.v7.app.AlertDialog
import android.support.v7.app.AppCompatActivity
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Spinner
import android.widget.Toast
import app.akexorcist.bluetotohspp.library.BluetoothSPP
import app.akexorcist.bluetotohspp.library.BluetoothState
import app.akexorcist.bluetotohspp.library.DeviceList
import com.annimon.stream.Stream
import com.applandeo.materialcalendarview.listeners.OnSelectDateListener
import kotlinx.android.synthetic.main.activity_main.*
import org.jetbrains.anko.startActivity
import org.jetbrains.anko.toast
import java.util.*
var cnt1 = 1
var helloID = "" // 로그인 시 저장될 전역 변수 (id)

class MainActivity : AppCompatActivity(), OnSelectDateListener {

    companion object {
//        @SuppressLint("StaticFieldLeak")
lateinit var bt: BluetoothSPP
        var AppCount = 0
    }

    private lateinit var spinner: Spinner
    lateinit var MainSpinnerList: ArrayList<String>
    lateinit var MainSpinnerAdapter: ArrayAdapter<String>

    lateinit var adapter : SlideAdapter
    lateinit var viewPager : ViewPager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        viewPager = findViewById(R.id.view) as ViewPager
        adapter = SlideAdapter(this)
        viewPager.adapter = adapter

        MainSpinnerList = ArrayList()
        MainSpinnerList.add("---항목선택---")
        MainSpinnerList.add("조명등 연동하기")
        MainSpinnerList.add("스케줄 관리하기")
        MainSpinnerList.add("개발자 정보")

        MainSpinnerAdapter =
                ArrayAdapter<String>(applicationContext, android.R.layout.simple_spinner_dropdown_item, MainSpinnerList)

        spinner = findViewById(R.id.MainSpinner) as Spinner
        spinner.setAdapter(MainSpinnerAdapter)
        spinner.setOnItemSelectedListener(object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(adapterView: AdapterView<*>, view: View, i: Int, l: Long) {
                if (i == 0) {
                } else if (i == 1) {
                    startActivity<LedActivity>()
                    toast("LED 페이지 이동")
                } else if (i == 2) {
                    startActivity<CalendarActivity>()
                    toast("일정추가 페이지 이동")
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
            MainLoginTextView.text = helloID +"(님)"
        }

        MainLoginTextView.setOnClickListener { view ->

            if (MainLoginTextView.text.equals("로그인")) {
                // 로그인 창으로 이동
                toast("로그인 페이지 이동")
                startActivity<LoginActivity>()
            } else {
                helloID = ""
                MainLoginTextView.text = "로그인"
                toast("로그아웃 완료!")
            }

        }

        MainLocationButton.setOnClickListener { view ->
            // 지역 선택창으로 이동
            startActivity<LocationSearchActivity>()
        }

        MainTitleButton.setOnClickListener { view ->
            // 명칭 검색창으로 이동
            startActivity<KeyWordActivity>()
        }
        MainNaverMapButton.setOnClickListener { view ->
            val url = "http://m.map.naver.com"
            val intent = Intent(Intent.ACTION_VIEW, Uri.parse(url))
            startActivity(intent)
        }

        nearButton.setOnClickListener { view ->
            // 근처 데이트 장소 검색
            // 반경 2km 이내 음식점, 숙소, 액티비티 추천
            startActivity<NearLocationActivity>()
        }

        bluetoothButton.setOnClickListener { view ->
            // 블루투스 Activity로 이동
            if (bt.serviceState != BluetoothState.STATE_CONNECTED) {
                val intent = Intent(applicationContext, DeviceList::class.java)
                startActivityForResult(intent, BluetoothState.REQUEST_CONNECT_DEVICE)
            } else {
                when (AppCount) {
                    3 -> bt.send("+D", true)
                    2 -> bt.send("+C", true)
                    1 -> bt.send("+B", true)
                    4 -> bt.send("+E", true)
                }

                bt.disconnect()
            }

        }

        ledButton.setOnClickListener { view ->
            // LED 제어 Activity로 이동
            startActivity<LedActivity>()

        }

        schedulerButton.setOnClickListener { view ->
            // calendar Activity로 이동
            startActivity<CalendarActivity>()

        }


        bt = BluetoothSPP(this)
        if (!bt.isBluetoothAvailable) { //블루투스 사용 불가
            Toast.makeText(applicationContext, "Bluetooth is not available", Toast.LENGTH_SHORT).show()
            finish()
        }
        bt.setBluetoothConnectionListener(object : BluetoothSPP.BluetoothConnectionListener {
            //연결됐을 때
            override fun onDeviceConnected(name: String, address: String) {

                when (AppCount) {
                    3 -> bt.send("+D", true)
                    2 -> bt.send("+C", true)
                    1 -> bt.send("+B", true)
                    4 -> bt.send("+E", true)
                }
//                //연결이 됐다면 해당 색상을 켜세요(단, 앱 종료 시 데이터 존재하지 않음)

                when {
                    App.prefs.myEditText == "RedLed" -> bt.send("+B", true)
                    App.prefs.myEditText == "BlueLed" -> bt.send("+D", true)
                    App.prefs.myEditText == "GreenLed" -> bt.send("+C", true)
                    App.prefs.myEditText == "PinkLed" -> bt.send("+E", true)
                }
//                //연결이 됐다면 해당 색상을 켜세요(단, 앱 종료 시에도 킬 수 있음)
//                //왜? ==> LedChangeActivty.kt에 있는 Ledtext를 sharedPreference로 읽어들이니깐.

                Toast.makeText(applicationContext, "Connected to $name\n$address", Toast.LENGTH_SHORT).show()
            }

            override fun onDeviceDisconnected() { //연결해제
                when (AppCount) {
                    3 -> bt.send("+D", true)
                    2 -> bt.send("+C", true)
                    1 -> bt.send("+B", true)
                    4 -> bt.send("+E", true)
                }

                when {
                    App.prefs.myEditText == "RedLed" -> bt.send("+B", true)
                    App.prefs.myEditText == "BlueLed" -> bt.send("+D", true)
                    App.prefs.myEditText == "GreenLed" -> bt.send("+C", true)
                    App.prefs.myEditText == "PinkLed" -> bt.send("+E", true)
                }

                Toast.makeText(applicationContext, "Connection lost", Toast.LENGTH_SHORT).show()
            }

            override fun onDeviceConnectionFailed() { //연결실패
                Toast.makeText(applicationContext, "Unable to connect", Toast.LENGTH_SHORT).show()
            }
        })
    }

    @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
    @RequiresApi(Build.VERSION_CODES.JELLY_BEAN)
    override fun onBackPressed() {
        val builder: AlertDialog.Builder = AlertDialog.Builder(this)
        builder.setTitle("알림")
        builder.setMessage("앱을 종료하시겠습니까?")
        builder.setNegativeButton("취소", null)
        builder.setPositiveButton("종료", DialogInterface.OnClickListener { dialog, which ->
            finishAffinity()
            System.runFinalization()
            System.exit(0)
        })
        builder.show()
    }



    override fun onSelect(calendars: List<Calendar>) {
        Stream.of(calendars).forEach { calendar ->
            Toast.makeText(applicationContext,
                    calendar.time.toString(),
                    Toast.LENGTH_SHORT).show()
        }
    }
    public override fun onStart() {
        super.onStart()
        if (!bt.isBluetoothEnabled) { //
            val intent = Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE)
            startActivityForResult(intent, BluetoothState.REQUEST_ENABLE_BT)
        } else {
            if (!bt.isServiceAvailable) {
                bt.setupService()
                bt.startService(BluetoothState.DEVICE_OTHER) //DEVICE_ANDROID는 안드로이드 기기 끼리
            }
        }
    }
    public override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if (requestCode == BluetoothState.REQUEST_CONNECT_DEVICE) {
            if (resultCode == Activity.RESULT_OK)
                bt.connect(data!!)
        } else if (requestCode == BluetoothState.REQUEST_ENABLE_BT) {
            if (resultCode == Activity.RESULT_OK) {
                bt.setupService()
                bt.startService(BluetoothState.DEVICE_OTHER)
            } else {
                Toast.makeText(applicationContext, "Bluetooth was not enabled.", Toast.LENGTH_SHORT).show()
                finish()
            }
        }
    }
    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
    }

}
