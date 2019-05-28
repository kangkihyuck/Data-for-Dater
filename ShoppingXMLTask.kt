package com.applandeo.materialcalendarsampleapp

import android.os.AsyncTask
import org.w3c.dom.Document
import org.w3c.dom.Element
import org.xml.sax.InputSource
import java.net.URL
import javax.xml.parsers.DocumentBuilderFactory


class ShoppingXMLTask : AsyncTask<String, Void, Document>() {
    internal var doc: Document? = null

    override fun doInBackground(vararg urls: String): Document? {

        val url: URL
        try {
            url =
                    URL("http://api.visitkorea.or.kr/openapi/service/rest/KorService/detailIntro?ServiceKey=b3W0ZANT1VPYvbjqez0COkQwtfGXJBTyD0SoDy5EdkJUcldZXd5k9TM0qmNIcCdp21wQOA%2BE%2FzttN4hYU7ZvAA%3D%3D&contentId=${contentidDestination}&contentTypeId=38&MobileOS=ETC&MobileApp=AppTest")
            val dbf = DocumentBuilderFactory.newInstance()
            val db = dbf.newDocumentBuilder()
            doc = db.parse(InputSource(url.openStream()))
            doc!!.documentElement.normalize()
        } catch (e: Exception) { }

        return doc
    }

    override fun onPostExecute(doc: Document) {

        val nodeList = doc.getElementsByTagName("item")

        shoppingList = ArrayList()

        for (i in 0 until nodeList.length) {
            var shoppingData: ShoppingDTO = ShoppingDTO()

            val node = nodeList.item(i)
            val fstElmnt = node as Element

            val chkbabycarriageshopping = fstElmnt.getElementsByTagName("chkbabycarriageshopping")
            if (chkbabycarriageshopping?.item(0)?.childNodes?.item(0)?.nodeValue != null) {
                shoppingData.shopping_chkbabycarriageshopping =
                        chkbabycarriageshopping.item(0).childNodes.item(0).nodeValue
            } else {
                shoppingData.shopping_chkbabycarriageshopping = "정보 없음"
            }

            val infocentershopping = fstElmnt.getElementsByTagName("infocentershopping")
            if (infocentershopping?.item(0)?.childNodes?.item(0)?.nodeValue != null) {
                shoppingData.shopping_infocentershopping = infocentershopping.item(0).childNodes.item(0).nodeValue
            } else {
                shoppingData.shopping_infocentershopping = "정보 없음"
            }

            val opentime = fstElmnt.getElementsByTagName("opentime")
            if (opentime?.item(0)?.childNodes?.item(0)?.nodeValue != null) {
                shoppingData.shopping_opentime = opentime.item(0).childNodes.item(0).nodeValue
            } else {
                shoppingData.shopping_opentime = "정보 없음"
            }

            val parkingshopping = fstElmnt.getElementsByTagName("parkingshopping")
            if (parkingshopping?.item(0)?.childNodes?.item(0)?.nodeValue != null) {
                shoppingData.shopping_parkingshopping = parkingshopping.item(0).childNodes.item(0).nodeValue
            } else {
                shoppingData.shopping_parkingshopping = "정보 없음"
            }

            val restdateshopping = fstElmnt.getElementsByTagName("restdateshopping")
            if (restdateshopping?.item(0)?.childNodes?.item(0)?.nodeValue != null) {
                shoppingData.shopping_restdateshopping = restdateshopping.item(0).childNodes.item(0).nodeValue
            } else {
                shoppingData.shopping_restdateshopping = "정보 없음"
            }

            val saleitem = fstElmnt.getElementsByTagName("saleitem")
            if (saleitem?.item(0)?.childNodes?.item(0)?.nodeValue != null) {
                shoppingData.shopping_saleitem = saleitem.item(0).childNodes.item(0).nodeValue
            } else {
                shoppingData.shopping_saleitem = "정보 없음"
            }

            shoppingList.add(shoppingData)

            var myAdapter: MyAdapter4 = MyAdapter4(dataList)
            mRecyclerView.setAdapter(myAdapter)

            super.onPostExecute(doc)
        }
    }
}
