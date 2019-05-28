package com.applandeo.materialcalendarsampleapp


import android.annotation.SuppressLint
import android.os.Bundle
import android.support.v7.app.ActionBar
import android.support.v7.app.AppCompatActivity
import android.view.View
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_led.*
import org.jetbrains.anko.toast

class LedActivity : AppCompatActivity() {


    @SuppressLint("SetTextI18n")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_led)

        val actionbar: ActionBar? = supportActionBar
        actionbar?.hide() // 액션바 숨김

        LedText.visibility = View.INVISIBLE
        LedText2.visibility = View.INVISIBLE

        MainTItleTextView16.setOnClickListener { view ->
//            val intent = Intent(this, MainActivity::class.java)
//            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
//            startActivity(intent)
            onBackPressed()

        }
        LedChangeRadioGroup.visibility = View.GONE
        LedText.text = App.prefs.myEditText

        LedChangeBtn.setOnClickListener {

            LedChangeRadioGroup.visibility = View.VISIBLE
        }

        LedSelectBtn.setOnClickListener {
            when {
                LedChangeBlue.isChecked -> {
                    MainActivity.AppCount = 3
                    LedText.text = "BlueLed"
                    MainActivity.bt.send("+D", true) //Blue 변경
                    toast("Blue")
                }
                LedChangeGreen.isChecked -> {
                    MainActivity.AppCount = 2
                    LedText.text = "GreenLed"
                    MainActivity.bt.send("+C", true) //초록 변경
                    toast("Green")

                }
                LedChangeRed.isChecked -> {
                    MainActivity.AppCount = 1
                    LedText.text = "RedLed"
                    MainActivity.bt.send("+B", true) //Red 변경
                    toast("Red")
                }
                LedChangePink.isChecked -> {
                    MainActivity.AppCount = 4
                    LedText.text = "PinkLed"
                    MainActivity.bt.send("+E", true) //pink 변경
                    toast("Pink")
                }
                /* 값 저장 후 Toast 출력 */
            }

            App.prefs.myEditText = LedText.text.toString()
            val msg = App.prefs.myEditText

            if (msg == "") {
                Toast.makeText(this, "텍스트가 초기화되었습니다.", Toast.LENGTH_LONG).show()
            } else {
                Toast.makeText(this, "저장됨: $msg", Toast.LENGTH_LONG).show()
            }
            /* 값 저장 후 Toast 출력 */
        }
        LedOnBtn.setOnClickListener {

            LedChangeRadioGroup.visibility = View.GONE
            when {
                MainActivity.AppCount == 3 -> MainActivity.bt.send("+D", true)
                MainActivity.AppCount == 2 -> MainActivity.bt.send("+C", true)
                MainActivity.AppCount == 1 -> MainActivity.bt.send("+B", true)
                MainActivity.AppCount == 4 -> MainActivity.bt.send("+E", true)
            }


            when {
                App.prefs.myEditText == "RedLed" -> {
                    MainActivity.bt.send("+B", true)
                }
                App.prefs.myEditText == "BlueLed" -> {
                    MainActivity.bt.send("+D", true)
                }
                App.prefs.myEditText == "GreenLed" -> {
                    MainActivity.bt.send("+C", true)
                }
                App.prefs.myEditText == "PinkLed" -> {
                    MainActivity.bt.send("+E", true)
                }
            }

        }
        LedOffBtn.setOnClickListener {
            LedChangeRadioGroup.visibility = View.GONE
            MainActivity.bt.send("+A", true) // 불 끄기
        }

        backbutton.setOnClickListener {
            LedChangeRadioGroup.visibility = View.GONE
            onBackPressed()
        }

        SpekerOnBtn.setOnClickListener {
            MainActivity.bt.send("+K", true)
        }

        SpekerOffBtn.setOnClickListener {

            MainActivity.bt.send("+J", true)
        }


    }

    override fun onBackPressed() {
        finish()
    }
}
