package com.applandeo.materialcalendarsampleapp

import android.os.AsyncTask
import android.os.SystemClock
import android.util.Log
import org.json.JSONArray
import org.json.JSONObject
import org.w3c.dom.Document
import org.w3c.dom.Element
import org.xml.sax.InputSource
import java.io.BufferedReader
import java.io.InputStreamReader
import java.net.HttpURLConnection
import java.net.URL
import javax.xml.parsers.DocumentBuilderFactory

class GetXMLTask4 : AsyncTask<String, Void, Document>() {
    internal var doc: Document? = null

    override fun doInBackground(vararg urls: String): Document? {

        val url: URL
        if (cnt1 == 1) {
            try {
                url =
                        URL("http://api.visitkorea.or.kr/openapi/service/rest/KorService/areaBasedList?ServiceKey=b3W0ZANT1VPYvbjqez0COkQwtfGXJBTyD0SoDy5EdkJUcldZXd5k9TM0qmNIcCdp21wQOA%2BE%2FzttN4hYU7ZvAA%3D%3D&contentTypeId=${contenttypeid}&areaCode=${areaCode}&sigunguCode=&cat1=&cat2=&cat3=&listYN=Y&MobileOS=ETC&MobileApp=TourAPI3.0_Guide&arrange=A&numOfRows=200&pageNo=1")
                val dbf = DocumentBuilderFactory.newInstance()
                val db = dbf.newDocumentBuilder()
                doc = db.parse(InputSource(url.openStream()))
                doc!!.documentElement.normalize()
            } catch (e: Exception) {

            }

            return doc
        } else {
            try {
                url =
                        URL("http://api.visitkorea.or.kr/openapi/service/rest/KorService/searchFestival?serviceKey=b3W0ZANT1VPYvbjqez0COkQwtfGXJBTyD0SoDy5EdkJUcldZXd5k9TM0qmNIcCdp21wQOA%2BE%2FzttN4hYU7ZvAA%3D%3D&MobileOS=ETC&MobileApp=AppTest&arrange=${arrange}&listYN=Y&eventStartDate=20190510&areaCode=${areaCode}")
                val dbf = DocumentBuilderFactory.newInstance()

                val db = dbf.newDocumentBuilder()
                doc = db.parse(InputSource(url.openStream()))
                doc!!.documentElement.normalize()
            } catch (e: Exception) {
            }
            return doc
        }
    }

    override fun onPostExecute(doc: Document) {
        var th = replyRatingThread()
        th.start()

        SystemClock.sleep(1000)

        val nodeList = doc.getElementsByTagName("item")

        dataList = ArrayList()
        ratingList = ArrayList()

        for (i in 0 until nodeList.length) {
            totalCnt = 0
            var data: DataDTO = DataDTO()

            val node = nodeList.item(i)
            val fstElmnt = node as Element

            val contentid = fstElmnt.getElementsByTagName("contentid")
            if (contentid?.item(0)?.childNodes?.item(0)?.nodeValue != null) {
                data.location_contentid = contentid.item(0).childNodes.item(0).nodeValue
            } else {
                data.location_contentid = "정보 없음"
            }

            for (i in 0..replyList.size - 1) {
                if (data.location_contentid.equals(replyList.get(i).reply_contentid)) {
                    data.location_averageScore += replyList.get(i).reply_score.toDouble()
                    totalCnt++
                }
            }

            data.location_averageScore = (data.location_averageScore/ totalCnt)*10 / 10.0

            if(totalCnt == 0){
                data.location_averageScore = 0.0
            }

            val image = fstElmnt.getElementsByTagName("firstimage")
            if (image?.item(0)?.childNodes?.item(0)?.nodeValue != null) {
                data.location_imageView1 = image.item(0).childNodes.item(0).nodeValue
            } else {
                data.location_imageView1 = "http://bomi5124.dothome.co.kr/webftp/php/connector.php?host=112.175.184.64&user=bomi5124&pass=ckffkdthsu!!&cmd=file&target=f1_aHRtbC9pbWFnZXgucG5n"
            }
            Log.d("qqq", data.location_imageView1)

            val image2 = fstElmnt.getElementsByTagName("firstimage2")
            if (image2?.item(0)?.childNodes?.item(0)?.nodeValue != null) {
                data.location_imageView2 = image2.item(0).childNodes.item(0).nodeValue
            } else {
                data.location_imageView2 = "http://bomi5124.dothome.co.kr/webftp/php/connector.php?host=112.175.184.64&user=bomi5124&pass=ckffkdthsu!!&cmd=file&target=f1_aHRtbC9pbWFnZXgucG5n"
            }

            val title = fstElmnt.getElementsByTagName("title")
            if (title?.item(0)?.childNodes?.item(0)?.nodeValue != null) {
                data.location_title = title.item(0).childNodes.item(0).nodeValue
            }

            val contenttypeId = fstElmnt.getElementsByTagName("contenttypeid")
            if (contenttypeId?.item(0)?.childNodes?.item(0)?.nodeValue != null) {
                data.location_contenttype = contenttypeId.item(0).childNodes.item(0).nodeValue
            }

            val addr1 = fstElmnt.getElementsByTagName("addr1")
            if (addr1?.item(0)?.childNodes?.item(0)?.nodeValue != null) {
                data.location_address = addr1.item(0).childNodes.item(0).nodeValue
            }

            var mapx = fstElmnt.getElementsByTagName("mapx")
            if (mapx?.item(0)?.childNodes?.item(0)?.nodeValue != null) {
                data.location_longitude = mapx.item(0).childNodes.item(0).nodeValue.toDouble()
            }

            var mapy = fstElmnt.getElementsByTagName("mapy")
            if (mapy?.item(0)?.childNodes?.item(0)?.nodeValue != null) {
                data.location_latitude = mapy.item(0).childNodes.item(0).nodeValue.toDouble()
            }

            var tel = fstElmnt.getElementsByTagName("tel")
            if (tel?.item(0)?.childNodes?.item(0)?.nodeValue != null) {
                data.location_telephone = tel.item(0).childNodes.item(0).nodeValue
            }

            dataList.add(data)
        }

        var myAdapter4: MyAdapter4 = MyAdapter4(dataList)
        mRecyclerView.setAdapter(myAdapter4)

        super.onPostExecute(doc)
    }

    inner class replyRatingThread : Thread() {
        override fun run() {
            val sb = StringBuilder()
            ratingList = ArrayList()

            try {
                replyList = java.util.ArrayList()
                val page = "http://192.168.0.160:8088/project1/androidReplyListService.do"
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

                val jObj = JSONObject(sb.toString())
                val jArray = jObj.get("sendData") as JSONArray

                for (i in 0 until jArray.length()) {
                    val row = jArray.getJSONObject(i)
                    replydto = ReplyDTO()

                    replydto.reply_contentid = row.getString("CONTENTID")
                    replydto.member_id = row.getString("MEMBER_ID")
                    replydto.reply_title = row.getString("REPLY_TITLE")
                    replydto.reply_reply = row.getString("REPLY_REPLY")
                    replydto.reply_score = row.getString("REPLY_SCORE")
                    replydto.reply_date = row.getString("REPLY_DATE")

                    replyList.add(replydto)
                }
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }

}
