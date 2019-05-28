package com.applandeo.materialcalendarsampleapp

import android.content.Context
import android.content.Intent
import android.support.v4.view.PagerAdapter
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.LinearLayout
import com.applandeo.materialcalendarsampleapp.R.drawable.image1

class SlideAdapter(private val context: Context) : PagerAdapter() {

    private val images = intArrayOf(R.drawable.homewhite, R.drawable.listwhite, image1, R.drawable.abc_ab_share_pack_mtrl_alpha)
    private var inflater: LayoutInflater? = null

    override fun getCount(): Int {
        return images.size
    }

    override fun isViewFromObject(view: View, o: Any): Boolean {
        return view === o as LinearLayout
    }

    override fun instantiateItem(container: ViewGroup, position: Int): Any {
        inflater = context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
        val v = inflater!!.inflate(R.layout.mainslider, container, false)
        val button = v.findViewById(R.id.typeButton) as Button

        if(position == 0){
//            button.text = "관광지"
            button.setBackgroundResource(R.drawable.tour)
        } else if(position == 1){
//            button.text = "문화 시설"
            button.setBackgroundResource(R.drawable.culturefacility)
        } else if(position == 2){
//            button.text = "축제 공연 행사"
            button.setBackgroundResource(R.drawable.festival)
        } else if(position == 3){
//            button.text = "쇼핑"
            button.setBackgroundResource(R.drawable.shopping)
        }

        button.setOnClickListener { view ->
            if(position == 0){
                contenttypeid = "12"
                cnt1 = 1

                val openThree = Intent(context, TouristDestinationActivity::class.java)
                openThree.putExtra("tourTitle", "관광지 찾기")
                context.startActivity(openThree)
            } else if(position == 1){
                contenttypeid = "14"
                cnt1 = 1

                val openThree = Intent(context, TouristDestinationActivity::class.java)
                openThree.putExtra("tourTitle", "문화시설 찾기")
                context.startActivity(openThree)
            } else if(position == 2){
                contenttypeid = "15"
                cnt1 = 2

                val openThree = Intent(context, TouristDestinationActivity::class.java)
                openThree.putExtra("tourTitle", "축제, 공연, 행사 찾기")
                context.startActivity(openThree)
            } else if(position == 3){
                contenttypeid = "38"
                cnt1 = 1

                val openThree = Intent(context, TouristDestinationActivity::class.java)
                openThree.putExtra("tourTitle", "쇼핑몰 찾기")
                context.startActivity(openThree)
            }
        }
        container.addView(v)
        return v
    }

    override fun destroyItem(container: ViewGroup, position: Int, `object`: Any) {
        container.invalidate()
    }
}