package com.applandeo.materialcalendarsampleapp

import android.os.AsyncTask
import org.w3c.dom.Document
import org.w3c.dom.Element
import org.xml.sax.InputSource
import java.net.URL
import javax.xml.parsers.DocumentBuilderFactory

class CultureXMLTask : AsyncTask<String, Void, Document>() {
    internal var doc: Document? = null

    override fun doInBackground(vararg urls: String): Document? {
        val url: URL
        try {
            url =
                    URL("http://api.visitkorea.or.kr/openapi/service/rest/KorService/detailIntro?ServiceKey=b3W0ZANT1VPYvbjqez0COkQwtfGXJBTyD0SoDy5EdkJUcldZXd5k9TM0qmNIcCdp21wQOA%2BE%2FzttN4hYU7ZvAA%3D%3D&contentId=${contentidDestination}&contentTypeId=14&MobileOS=ETC&MobileApp=AppTest")
            val dbf = DocumentBuilderFactory.newInstance()
            val db = dbf.newDocumentBuilder()
            doc = db.parse(InputSource(url.openStream()))
            doc!!.documentElement.normalize()
        } catch (e: Exception) { }

        return doc
    }

    override fun onPostExecute(doc: Document) {
        val nodeList = doc.getElementsByTagName("item")

        cultureList = ArrayList()

        for (i in 0 until nodeList.length) {
            var cultureData: CultureDTO = CultureDTO()

            val node = nodeList.item(i)
            val fstElmnt = node as Element

            val accomcountculture = fstElmnt.getElementsByTagName("accomcountculture")
            if (accomcountculture?.item(0)?.childNodes?.item(0)?.nodeValue != null) {
                cultureData.culture_accomcountculture = accomcountculture.item(0).childNodes.item(0).nodeValue
            } else {
                cultureData.culture_accomcountculture = "정보 없음"
            }

            val chkcreditcardculture = fstElmnt.getElementsByTagName("chkcreditcardculture")
            if (chkcreditcardculture?.item(0)?.childNodes?.item(0)?.nodeValue != null) {
                cultureData.culture_chkcreditcardculture = chkcreditcardculture.item(0).childNodes.item(0).nodeValue
            } else {
                cultureData.culture_chkcreditcardculture = "정보 없음"
            }

            val discountinfo = fstElmnt.getElementsByTagName("discountinfo")
            if (discountinfo?.item(0)?.childNodes?.item(0)?.nodeValue != null) {
                cultureData.culture_discountinfo = discountinfo.item(0).childNodes.item(0).nodeValue
            } else {
                cultureData.culture_discountinfo = "정보 없음"
            }

            val parkingculture = fstElmnt.getElementsByTagName("parkingculture")
            if (parkingculture?.item(0)?.childNodes?.item(0)?.nodeValue != null) {
                cultureData.culture_parkingculture = parkingculture.item(0).childNodes.item(0).nodeValue
            } else {
                cultureData.culture_parkingculture = "정보 없음"
            }

            val spendtime = fstElmnt.getElementsByTagName("spendtime")
            if (spendtime?.item(0)?.childNodes?.item(0)?.nodeValue != null) {
                cultureData.culture_spendtime = spendtime.item(0).childNodes.item(0).nodeValue
            } else {
                cultureData.culture_spendtime = "정보 없음"
            }

            val usetimeculture = fstElmnt.getElementsByTagName("usetimeculture")
            if (usetimeculture?.item(0)?.childNodes?.item(0)?.nodeValue != null) {
                cultureData.culture_usetimeculture = usetimeculture.item(0).childNodes.item(0).nodeValue
            } else {
                cultureData.culture_usetimeculture = "정보 없음"
            }

            val usefee = fstElmnt.getElementsByTagName("usefee")
            if (usefee?.item(0)?.childNodes?.item(0)?.nodeValue != null) {
                cultureData.culture_usefee = usefee.item(0).childNodes.item(0).nodeValue
            } else {
                cultureData.culture_usefee = "정보 없음"
            }

            val restdateculture = fstElmnt.getElementsByTagName("restdateculture")
            if (restdateculture?.item(0)?.childNodes?.item(0)?.nodeValue != null) {
                cultureData.culture_restdateculture = restdateculture.item(0).childNodes.item(0).nodeValue
            } else {
                cultureData.culture_restdateculture = "정보 없음"
            }

            cultureList.add(cultureData)

            var myAdapter: MyAdapter4 = MyAdapter4(dataList)
            mRecyclerView.setAdapter(myAdapter)

            super.onPostExecute(doc)
        }
    }
}
