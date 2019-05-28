package com.applandeo.materialcalendarsampleapp

import android.os.AsyncTask
import org.w3c.dom.Document
import org.w3c.dom.Element
import org.xml.sax.InputSource
import java.net.URL
import javax.xml.parsers.DocumentBuilderFactory

class FestivalXMLTask : AsyncTask<String, Void, Document>() {
    internal var doc: Document? = null

    override fun doInBackground(vararg urls: String): Document? {
        val url: URL
        try {
            url =
                    URL("http://api.visitkorea.or.kr/openapi/service/rest/KorService/detailIntro?ServiceKey=b3W0ZANT1VPYvbjqez0COkQwtfGXJBTyD0SoDy5EdkJUcldZXd5k9TM0qmNIcCdp21wQOA%2BE%2FzttN4hYU7ZvAA%3D%3D&contentId=${contentidDestination}&contentTypeId=15&MobileOS=ETC&MobileApp=AppTest")
            val dbf = DocumentBuilderFactory.newInstance()
            val db = dbf.newDocumentBuilder()
            doc = db.parse(InputSource(url.openStream()))
            doc!!.documentElement.normalize()

        } catch (e: Exception) { }
        return doc
    }

    override fun onPostExecute(doc: Document) {

        val nodeList = doc.getElementsByTagName("item")

        festivalList = ArrayList()

        for (i in 0 until nodeList.length) {
            var festivalData: FestivalDTO = FestivalDTO()

            val node = nodeList.item(i)
            val fstElmnt = node as Element

            val agelimit = fstElmnt.getElementsByTagName("agelimit")
            if (agelimit?.item(0)?.childNodes?.item(0)?.nodeValue != null) {
                festivalData.festival_agelimit = agelimit.item(0).childNodes.item(0).nodeValue
            } else {
                festivalData.festival_agelimit = "정보 없음"
            }

            val bookingplace = fstElmnt.getElementsByTagName("bookingplace")
            if (bookingplace?.item(0)?.childNodes?.item(0)?.nodeValue != null) {
                festivalData.festival_bookingplace = bookingplace.item(0).childNodes.item(0).nodeValue
            } else {
                festivalData.festival_bookingplace = "정보 없음"
            }

            val discountinfofestival = fstElmnt.getElementsByTagName("discountinfofestival")
            if (discountinfofestival?.item(0)?.childNodes?.item(0)?.nodeValue != null) {
                festivalData.festival_discountinfofestival = discountinfofestival.item(0).childNodes.item(0).nodeValue
            } else {
                festivalData.festival_discountinfofestival = "정보 없음"
            }

            val usetimefestival = fstElmnt.getElementsByTagName("usetimefestival")
            if (usetimefestival?.item(0)?.childNodes?.item(0)?.nodeValue != null) {
                festivalData.festival_usetimefestival = usetimefestival.item(0).childNodes.item(0).nodeValue
            } else {
                festivalData.festival_usetimefestival = "정보 없음"
            }

            val eventstartdate = fstElmnt.getElementsByTagName("eventstartdate")
            if (eventstartdate?.item(0)?.childNodes?.item(0)?.nodeValue != null) {
                festivalData.festival_eventstartdate = eventstartdate.item(0).childNodes.item(0).nodeValue
            } else {
                festivalData.festival_eventstartdate = "정보 없음"
            }

            val eventenddate = fstElmnt.getElementsByTagName("eventenddate")
            if (eventenddate?.item(0)?.childNodes?.item(0)?.nodeValue != null) {
                festivalData.festival_eventenddate = eventenddate.item(0).childNodes.item(0).nodeValue
            } else {
                festivalData.festival_eventenddate = "정보 없음"
            }

            val eventplace = fstElmnt.getElementsByTagName("eventplace")
            if (eventplace?.item(0)?.childNodes?.item(0)?.nodeValue != null) {
                festivalData.festival_eventplace = eventplace.item(0).childNodes.item(0).nodeValue
            } else {
                festivalData.festival_eventplace = "정보 없음"
            }

            val placeinfo = fstElmnt.getElementsByTagName("placeinfo")
            if (placeinfo?.item(0)?.childNodes?.item(0)?.nodeValue != null) {
                festivalData.festival_placeinfo = eventplace.item(0).childNodes.item(0).nodeValue
            } else {
                festivalData.festival_placeinfo = "정보 없음"
            }

            val playtime = fstElmnt.getElementsByTagName("playtime")
            if (playtime?.item(0)?.childNodes?.item(0)?.nodeValue != null) {
                festivalData.festival_playtime = playtime.item(0).childNodes.item(0).nodeValue
            } else {
                festivalData.festival_playtime = "정보 없음"
            }

            val spendtimefestival = fstElmnt.getElementsByTagName("spendtimefestival")
            if (spendtimefestival?.item(0)?.childNodes?.item(0)?.nodeValue != null) {
                festivalData.festival_spendtimefestival = spendtimefestival.item(0).childNodes.item(0).nodeValue
            } else {
                festivalData.festival_spendtimefestival = "정보 없음"
            }

            val program = fstElmnt.getElementsByTagName("program")
            if (program?.item(0)?.childNodes?.item(0)?.nodeValue != null) {
                festivalData.festival_program = program.item(0).childNodes.item(0).nodeValue
            } else {
                festivalData.festival_program = "정보 없음"
            }

            val subevent = fstElmnt.getElementsByTagName("subevent")
            if (subevent?.item(0)?.childNodes?.item(0)?.nodeValue != null) {
                festivalData.festival_subevent = subevent.item(0).childNodes.item(0).nodeValue
            } else {
                festivalData.festival_subevent = "정보 없음"
            }

            val eventhomepage = fstElmnt.getElementsByTagName("eventhomepage")
            if (eventhomepage?.item(0)?.childNodes?.item(0)?.nodeValue != null) {
                festivalData.festival_eventhomepage = eventhomepage.item(0).childNodes.item(0).nodeValue
            } else {
                festivalData.festival_eventhomepage = "정보 없음"
            }

            festivalList.add(festivalData)

            var myAdapter: MyAdapter4 = MyAdapter4(dataList)
            mRecyclerView.setAdapter(myAdapter)

            super.onPostExecute(doc)
        }

    }
}
