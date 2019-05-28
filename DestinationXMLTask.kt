package com.applandeo.materialcalendarsampleapp

import android.os.AsyncTask
import org.w3c.dom.Document
import org.w3c.dom.Element
import org.xml.sax.InputSource
import java.net.URL
import javax.xml.parsers.DocumentBuilderFactory


class DestinationXMLTask : AsyncTask<String, Void, Document>() {
    internal var doc: Document? = null

    override fun doInBackground(vararg urls: String): Document? {

        val url: URL
        try {
            url =
                    URL("http://api.visitkorea.or.kr/openapi/service/rest/KorService/detailIntro?ServiceKey=b3W0ZANT1VPYvbjqez0COkQwtfGXJBTyD0SoDy5EdkJUcldZXd5k9TM0qmNIcCdp21wQOA%2BE%2FzttN4hYU7ZvAA%3D%3D&contentId=${contentidDestination}&contentTypeId=12&MobileOS=ETC&MobileApp=AppTest")
            val dbf = DocumentBuilderFactory.newInstance()
            val db = dbf.newDocumentBuilder()
            doc = db.parse(InputSource(url.openStream()))
            doc!!.documentElement.normalize()
        } catch (e: Exception) { }

        return doc
    }

    override fun onPostExecute(doc: Document) {

        val nodeList = doc.getElementsByTagName("item")

        destinationList = ArrayList()

        for (i in 0 until nodeList.length) {
            var destinationData: DestinationDTO = DestinationDTO()

            val node = nodeList.item(i)
            val fstElmnt = node as Element

            val chkcreditcard = fstElmnt.getElementsByTagName("chkcreditcard")
            if (chkcreditcard?.item(0)?.childNodes?.item(0)?.nodeValue != null) {
                destinationData.destination_chkcreditcard = chkcreditcard.item(0).childNodes.item(0).nodeValue
            } else {
                destinationData.destination_chkcreditcard = "정보 없음"
            }

            val expguide = fstElmnt.getElementsByTagName("expguide")
            if (expguide?.item(0)?.childNodes?.item(0)?.nodeValue != null) {
                destinationData.destination_expguide = expguide.item(0).childNodes.item(0).nodeValue
            } else {
                destinationData.destination_expguide = "정보 없음"
            }

            val parking = fstElmnt.getElementsByTagName("parking")
            if (parking?.item(0)?.childNodes?.item(0)?.nodeValue != null) {
                destinationData.destination_parking = parking.item(0).childNodes.item(0).nodeValue
            } else {
                destinationData.destination_parking = "정보 없음"
            }

            val restdate = fstElmnt.getElementsByTagName("restdate")
            if (restdate?.item(0)?.childNodes?.item(0)?.nodeValue != null) {
                destinationData.destination_restdate = restdate.item(0).childNodes.item(0).nodeValue
            } else {
                destinationData.destination_restdate = "정보 없음"
            }

            val usetime = fstElmnt.getElementsByTagName("usetime")
            if (usetime?.item(0)?.childNodes?.item(0)?.nodeValue != null) {
                destinationData.destination_usetime = usetime.item(0).childNodes.item(0).nodeValue
            } else {
                destinationData.destination_usetime = "정보 없음"
            }

            destinationList.add(destinationData)
        }

        var myAdapter: MyAdapter4 = MyAdapter4(dataList)
        mRecyclerView.setAdapter(myAdapter)

        super.onPostExecute(doc)
    }
}
