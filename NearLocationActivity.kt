package com.applandeo.materialcalendarsampleapp

import android.Manifest
import android.annotation.SuppressLint
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Bundle
import android.support.v4.app.ActivityCompat
import android.support.v4.content.ContextCompat
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
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationCallback
import com.google.android.gms.location.LocationRequest
import com.google.android.gms.location.LocationResult
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.BitmapDescriptorFactory
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import kotlinx.android.synthetic.main.activity_near_location.*
import org.jetbrains.anko.*

var dist = 0
var MyLocationLatitude: Double = 0.0
var MyLocationLongitude: Double = 0.0

class NearLocationActivity : AppCompatActivity(), OnMapReadyCallback {

    private lateinit var contentIdSpinner: Spinner
    private lateinit var locationSpinner: Spinner
    lateinit var arrayList3: ArrayList<String>
    lateinit var arrayAdapter3: ArrayAdapter<String>
    lateinit var arrayList4: ArrayList<String>
    lateinit var arrayAdapter4: ArrayAdapter<String>

    private lateinit var spinner: Spinner
    lateinit var NearLocationSpinnerList: ArrayList<String>
    lateinit var NearLocationSpinnerAdapter: ArrayAdapter<String>

    private lateinit var mMap: GoogleMap
    // 지도 조작을 위한 객체.
    val markerOptions: MarkerOptions = MarkerOptions()
    lateinit var fusedLocationProviderClient: FusedLocationProviderClient
    // 위치값 얻어오기 객체
    lateinit var locationRequest: LocationRequest // 위치 요청
    lateinit var locationCallback: MyLocationCallBack // 내부 클래스, 위치 변경 후 지도에 표시.

    val REQUEST_ACCESS_FINE_LOCATION = 1000

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_near_location)
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.

        val animationView = findViewById(R.id.animation_view) as LottieAnimationView
        animationView.setAnimation("Microinteraction.json")
        animationView.loop(true)
        animationView.playAnimation()

        NearLocationSpinnerList = ArrayList()
        NearLocationSpinnerList.add("---항목선택---")
        NearLocationSpinnerList.add("조명등 연동하기")
        NearLocationSpinnerList.add("스케줄 관리하기")
        NearLocationSpinnerList.add("개발자 정보")

        NearLocationSpinnerAdapter =
                ArrayAdapter<String>(applicationContext, android.R.layout.simple_spinner_dropdown_item, NearLocationSpinnerList)

        spinner = findViewById(R.id.nearLocationSpinner) as Spinner
        spinner.setAdapter(NearLocationSpinnerAdapter)
        spinner.setOnItemSelectedListener(object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(adapterView: AdapterView<*>, view: View, i: Int, l: Long) {
                if (i == 0) {
                } else if (i == 1) {
                    toast("조명등 연동하기 페이지 이동")
                    startActivity<LedActivity>()
                } else if (i == 2) {
                    toast("스케줄 관리하기 페이지 이동")
                    startActivity<CalendarActivity>()
                } else if (i == 3) {
                    toast("개발자 정보 페이지 이동")
                    startActivity<DeveloperActivity>()
                }
            }

            override fun onNothingSelected(adapterView: AdapterView<*>) {}
        })

        val actionbar: ActionBar? = supportActionBar
        actionbar?.hide() // 액션바 숨김

        mContext = this

        if (!helloID.equals("")) {
            MainLoginTextView5.text = helloID + "(님)"
        }

        MainTItleTextView14.setOnClickListener { view ->
            val intent = Intent(this, MainActivity::class.java)
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
            startActivity(intent)
        }

        MainLoginTextView5.setOnClickListener { view ->
            if (MainLoginTextView5.text.equals("로그인")) {
                // 로그인 창으로 이동
                toast("로그인 페이지 이동")
                startActivity<LoginActivity>()
            } else {
                helloID = ""
                MainLoginTextView5.text = "로그인"
                toast("로그아웃 완료!")
            }
        }

        arrayList3 = ArrayList()
        arrayList3.add("-----")
        arrayList3.add("음식")
        arrayList3.add("숙박")
        arrayList3.add("액티비티")

        arrayList4 = ArrayList()
        arrayList4.add("-----")
        arrayList4.add("500m 이내")
        arrayList4.add("1Km 이내")
        arrayList4.add("2km 이내")
        arrayList4.add("5km 이내")

        arrayAdapter3 =
                ArrayAdapter<String>(applicationContext, android.R.layout.simple_spinner_dropdown_item, arrayList3)

        contentIdSpinner = findViewById(R.id.contenttypeidSpinner) as Spinner
        contentIdSpinner.setAdapter(arrayAdapter3)
        contentIdSpinner.setOnItemSelectedListener(object : AdapterView.OnItemSelectedListener {
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

        arrayAdapter4 =
                ArrayAdapter<String>(applicationContext, android.R.layout.simple_spinner_dropdown_item, arrayList4)

        locationSpinner = findViewById(R.id.distanceSpinner) as Spinner
        locationSpinner.setAdapter(arrayAdapter4)
        locationSpinner.setOnItemSelectedListener(object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(adapterView: AdapterView<*>, view: View, i: Int, l: Long) {
                if (i == 0) {

                } else if (i == 1) {
                    dist = 500
                } else if (i == 2) {
                    dist = 1000
                } else if (i == 3) {
                    dist = 2000
                } else if (i == 4) {
                    dist = 5000
                }
            }

            override fun onNothingSelected(adapterView: AdapterView<*>) {}
        })

        animation_view.setOnClickListener { view ->
            mContext = this

            mRecyclerView = findViewById(R.id.recycler_view_map) as RecyclerView
            mRecyclerView.setHasFixedSize(true)
            var mLayoutManager = LinearLayoutManager(this)
            mRecyclerView.setLayoutManager(mLayoutManager)

            GetXMLTask2().execute()
        }

        val mapFragment = supportFragmentManager
                .findFragmentById(R.id.map) as SupportMapFragment
        mapFragment.getMapAsync(this)
        locationInit()

        if (ContextCompat.checkSelfPermission(
                        this,
                        Manifest.permission.ACCESS_FINE_LOCATION // 위치에 대한 권한 요청
                )
                != PackageManager.PERMISSION_GRANTED
        // 사용자 권한 체크로
        // 외부 저장소 읽기가 허용되지 않았다면
        ) {
            if (ActivityCompat.shouldShowRequestPermissionRationale(
                            this,
                            Manifest.permission.ACCESS_FINE_LOCATION
                    )
            ) { // 허용되지 않았다면 다시 확인.
                alert(
                        "위치 정보를 얻으려면 외부 저장소 권한이 필수로 필요합니다.",

                        "권한이 필요한 이유"
                ) {
                    yesButton {
                        // 권한 허용
                        ActivityCompat.requestPermissions(
                                this@NearLocationActivity,
                                arrayOf(Manifest.permission.ACCESS_FINE_LOCATION),
                                REQUEST_ACCESS_FINE_LOCATION
                        )
                    }
                    noButton {
                        // 권한 비허용
                    }
                }.show()
            } else {
                ActivityCompat.requestPermissions(
                        this,
                        arrayOf(Manifest.permission.ACCESS_FINE_LOCATION),
                        REQUEST_ACCESS_FINE_LOCATION
                )
            }
        } else {
            addLocationListener()
        }
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

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        //super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        when (requestCode) {
            REQUEST_ACCESS_FINE_LOCATION -> {
                if (grantResults.isNotEmpty() && grantResults[0] ==
                        PackageManager.PERMISSION_GRANTED
                ) {
// 권한이 승인 됐다면
                    addLocationListener()
                } else {
// 권한이 거부 됐다면
                    toast("권한 거부 됨")
                }
                return
            }
        }
    }

    fun locationInit() {
        fusedLocationProviderClient = FusedLocationProviderClient(this)
        // 현재 사용자 위치를 저장.
        locationCallback = MyLocationCallBack() // 내부 클래스 조작용 객체 생성
        locationRequest = LocationRequest() // 위치 요청.

        locationRequest.priority = LocationRequest.PRIORITY_BALANCED_POWER_ACCURACY
        // 위치 요청의 우선순위 = 높은 정확도 우선.
        locationRequest.interval = 20000 // 내 위치 지도 전달 간격
        locationRequest.fastestInterval = 60000 // 지도 갱신 간격.
    }

    override fun onMapReady(googleMap: GoogleMap) {
        mMap = googleMap

        // Add a marker in Sydney and move the camera
        val sydney = LatLng(37.4388406, 126.6751131) // ICIA 교육원으로 위치 변경
        mMap.addMarker(MarkerOptions().position(sydney).title("ICIA 교육원"))
        mMap.moveCamera(CameraUpdateFactory.newLatLng(sydney))
    }

    override fun onResume() { // 잠깐 쉴 때.
        super.onResume()
        addLocationListener()
    }

    override fun onPause() {
        super.onPause()
    }

    fun removeLocationLister() {
        fusedLocationProviderClient.removeLocationUpdates(locationCallback)
        // 어플이 종료되면 지도 요청 해제.
    }

    @SuppressLint("MissingPermission")
    // 위험 권한 사용시 요청 코드가 호출되어야 하는데,
    // 없어서 발생됨. 요청 코드는 따로 처리 했음.
    fun addLocationListener() {
        fusedLocationProviderClient.requestLocationUpdates(locationRequest, locationCallback, null)
        //위치 권한을 요청해야 함.
        // 액티비티가 잠깐 쉴 때,
        // 자신의 위치를 확인하고, 갱신된 정보를 요청
    }

    inner class MyLocationCallBack : LocationCallback() {
        override fun onLocationResult(p0: LocationResult?) {
            super.onLocationResult(p0)

            val location = p0?.lastLocation
            // 위도 경도를 지도 서버에 전달하면,
            // 위치에 대한 지도 결과를 받아와서 저장.

            location?.run {
                mMap.clear()
                val latLng = LatLng(latitude, longitude) // 위도 경도 좌표 전달.
                mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(latLng, 15f))
                // 지도에 애니메이션 효과로 카메라 이동.
                // 좌표 위치로 이동하면서 배율은 17 (0~19까지 범위가 존재.)

                Log.d("MapsActivity", "위도: $latitude, 경도 : $longitude")
                MyLocationLatitude = latitude
                MyLocationLongitude = longitude

                markerOptions.position(latLng) // 마커를 latLng으로 설정
                markerOptions.title("내 위치")
                markerOptions.icon(BitmapDescriptorFactory.fromResource(R.drawable.couple))
                mMap.addMarker(markerOptions) // googleMap에 marker를 표시.
            }
        }
    }

    fun locationMarker() {
        for (i in 0..dataList.size - 1) {
            var latLng1 = LatLng(dataList.get(i).location_latitude, dataList.get(i).location_longitude)
            markerOptions.position(latLng1) // 마커를 latLng으로 설정
            markerOptions.title(dataList.get(i).location_title)
            if (contenttypeid.equals("39")) {
                markerOptions.icon(BitmapDescriptorFactory.fromResource(R.drawable.cook))
            } else if (contenttypeid.equals("32")) {
                markerOptions.icon(BitmapDescriptorFactory.fromResource(R.drawable.hotel))
            } else {
                markerOptions.icon(BitmapDescriptorFactory.fromResource(R.drawable.activity))
            }
//            markerOptions.icon(BitmapDescriptorFactory.fromResource(R.drawable.icon))
            mMap.addMarker(markerOptions) // googleMap에 marker를 표시.
        }
    }

    fun noNearLocation() {
        toast("검색된 결과가 없습니다.")
    }
}