package com.applandeo.materialcalendarsampleapp

import android.os.Bundle
import android.os.Handler
import android.support.v7.app.ActionBar
import android.support.v7.app.AppCompatActivity
import android.view.WindowManager
import com.airbnb.lottie.LottieAnimationView
import org.jetbrains.anko.startActivity

class HandlerActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        window.setFlags(
                WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN
        ) // 상태바 숨김
        setContentView(R.layout.activity_handler)

        val actionbar : ActionBar? = supportActionBar
        actionbar?.hide() // 액션바 숨김

//        val animationView = findViewById(R.id.animation_view) as LottieAnimationView
//        animationView.setAnimation("221-infinite-rainbow.json")
//        animationView.loop(true)
//        animationView.playAnimation()

        Handler().postDelayed({
            startActivity<MainActivity>()
        }, 2500) // 2초 대기 후 MainActivity로 전환
    }

    override fun onBackPressed() {
        finish()
    }
}
