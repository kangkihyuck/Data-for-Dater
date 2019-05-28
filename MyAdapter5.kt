package com.applandeo.materialcalendarsampleapp

import android.content.Context
import android.os.SystemClock
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.RatingBar
import android.widget.TextView
import android.widget.Toast
import java.io.BufferedReader
import java.io.InputStreamReader
import java.net.HttpURLConnection
import java.net.URL


var reply_content = ""
var CONTENTID = ""
var MEMBER_ID = ""
var reply_title1 = ""
var reply_rating = 0.0F

class MyAdapter5 internal constructor(private val replyArrayList: ArrayList<ReplyDTO>) :
        RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    class MyViewHolder internal constructor(view: View) : RecyclerView.ViewHolder(view) {
        internal var tvTitle: TextView
        internal var tvAddress: TextView
        internal var tvTitle2: TextView
        internal var tvRating: RatingBar
        internal var tvDate: TextView
        internal var tvUpdate: TextView
        internal var tvDelete: TextView

        init {
            tvTitle = view.findViewById(R.id.title1)
            tvAddress = view.findViewById(R.id.address1)
            tvTitle2 = view.findViewById(R.id.title2)
            tvRating = view.findViewById(R.id.ratingBar1)
            tvDate = view.findViewById(R.id.date1)
            tvUpdate = view.findViewById(R.id.modifyTextView1)
            tvDelete = view.findViewById(R.id.deleteTextView1)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val v = LayoutInflater.from(parent.context).inflate(R.layout.recycler_view_row_content, parent, false)
        return MyViewHolder(v)
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val myViewHolder = holder as MyViewHolder

        SystemClock.sleep(150)

        myViewHolder.tvTitle.setText(replyArrayList.get(position).member_id)
        myViewHolder.tvTitle2.setText(replyArrayList.get(position).reply_title)
        myViewHolder.tvAddress.setText(replyArrayList.get(position).reply_reply)
        myViewHolder?.tvRating.setRating((replyArrayList.get(position).reply_score).toFloat())
        myViewHolder.tvDate.setText(replyArrayList.get(position).reply_date)
        if (helloID.equals(myViewHolder.tvTitle.text.toString())) {
            myViewHolder.tvUpdate.visibility = View.VISIBLE
            myViewHolder.tvDelete.visibility = View.VISIBLE
        } else {
            myViewHolder.tvUpdate.visibility = View.INVISIBLE
            myViewHolder.tvDelete.visibility = View.INVISIBLE
        }

        holder.tvDelete.setOnClickListener { view ->
            // 삭제 작업 진행.
            reply_content = replyArrayList.get(position).reply_reply
            CONTENTID = replyArrayList.get(position).reply_contentid
            MEMBER_ID = replyArrayList.get(position).member_id

            var delete = replyListDelete()
            delete.start()

            var context: Context = view.context
            Toast.makeText(context, "삭제 완료", Toast.LENGTH_LONG).show()
        }

        holder.tvUpdate.setOnClickListener { view ->
            // 선택한 item을 editText에 뿌려주는 역할을 수행해야 함.
            reply_title1 = replyArrayList.get(position).reply_title
            reply_content = replyArrayList.get(position).reply_reply
            reply_rating = replyArrayList.get(position).reply_score.toFloat()

            if (resultCnt == 1) {
                (mContext as ResultActivity).updateMethod(reply_content, reply_rating, reply_title1)
            } else if (resultCnt == 2) {
                (mContext as ResultActivity4).updateMethod(reply_content, reply_rating, reply_title1)
            } else if (resultCnt == 3) {
                (mContext as ResultActivity5).updateMethod(reply_content, reply_rating, reply_title1)
            } else if (resultCnt == 4) {
                (mContext as ResultActivity6).updateMethod(reply_content, reply_rating, reply_title1)
            } else if (resultCnt == 5) {
                (mContext as ResultActivity7).updateMethod(reply_content, reply_rating, reply_title1)
            }
        }
    }

    inner class replyListDelete : Thread() {
        override fun run() {
            val sb = StringBuilder()

            try {
//                replyList = java.util.ArrayList()
                val page =
                        "http://192.168.0.160:8088/project1/androidReplyDeleteService.do?reply_content=${reply_content}&CONTENTID=${CONTENTID}&MEMBER_ID=${MEMBER_ID}"
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

                if (resultCnt == 1) {
                    (mContext as ResultActivity).resetMethod()
                } else if (resultCnt == 2) {
                    (mContext as ResultActivity4).resetMethod()
                } else if (resultCnt == 3) {
                    (mContext as ResultActivity5).resetMethod()
                } else if (resultCnt == 4) {
                    (mContext as ResultActivity6).resetMethod()
                } else if (resultCnt == 5) {
                    (mContext as ResultActivity7).resetMethod()
                }
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }

    override fun getItemCount(): Int {
        return replyArrayList.size
    }

    internal inner class ViewHolder(val mView: View)// 레이아웃 객체화 findViewById
        : RecyclerView.ViewHolder(mView)
}