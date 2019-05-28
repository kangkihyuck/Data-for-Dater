package com.applandeo.materialcalendarsampleapp

import android.content.Context
import android.content.Intent
import android.support.v7.widget.RecyclerView
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import com.bumptech.glide.Glide
import java.net.URL


lateinit var url: URL

class MyAdapter internal constructor(private val foodInfoArrayList: ArrayList<DataDTO>) :
        RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    class MyViewHolder internal constructor(view: View) : RecyclerView.ViewHolder(view) {
        internal var tvTitle: TextView
        internal var tvAddress: TextView
        internal var tvImageView: ImageView
        internal var tvRatingTextView : TextView

        init {
            tvTitle = view.findViewById(R.id.title)
            tvAddress = view.findViewById(R.id.address1)
            tvImageView = view.findViewById(R.id.image1)
            tvRatingTextView = view.findViewById(R.id.averageRatingTextView1)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val v = LayoutInflater.from(parent.context).inflate(R.layout.recycler_view_row, parent, false)
        return MyViewHolder(v)
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val myViewHolder = holder as MyViewHolder

        Glide.with(mRecyclerView).load(dataList.get(position).location_imageView1).into(myViewHolder.tvImageView)
        myViewHolder.tvTitle.setText(dataList.get(position).location_title)
        myViewHolder.tvAddress.setText(dataList.get(position).location_address)
        myViewHolder.tvRatingTextView.setText((dataList.get(position).location_averageScore.toString()))

        holder.itemView.setOnClickListener { view ->
            var context: Context = view.context
            var intent = Intent(view.getContext(), ResultActivity::class.java)

            Log.d("q1q1", "intent로 값 넘기는 부분 resultActivity에")
            intent.putExtra("title", dataList.get(position).location_title)
            intent.putExtra("rating", ("평점 : "+dataList.get(position).location_averageScore.toString()) + "/5.0")
            intent.putExtra("image1", dataList.get(position).location_imageView1)
            intent.putExtra("contentid", dataList.get(position).location_contentid)
            intent.putExtra("contenttypeid", dataList.get(position).location_contenttype)
            intent.putExtra("address", dataList.get(position).location_address)
            intent.putExtra("telephone", dataList.get(position).location_telephone)
            intent.putExtra("latitude", dataList.get(position).location_latitude)
            intent.putExtra("longitude", dataList.get(position).location_longitude)

            view.getContext().startActivity(intent)
        }
    }

    override fun getItemCount(): Int {
        return foodInfoArrayList.size
    }

    internal inner class ViewHolder(val mView: View)// 레이아웃 객체화 findViewById
        : RecyclerView.ViewHolder(mView)
}

