package com.applandeo.materialcalendarsampleapp

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.TextView

class ListViewAdapter : BaseAdapter() {

    private var listViewItemList = ArrayList<ListViewItem>()



    override fun getCount(): Int {

        return listViewItemList.size
    }

    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {

        var view = convertView
        val context = parent!!.context

        if (view == null) {

            val inflater = context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
            view = inflater.inflate(R.layout.listview_item, parent, false)

        }

        val titleTextView = view?.findViewById(R.id.itemText1) as TextView
        val dateTextView = view.findViewById(R.id.itemText2) as TextView
        val sysdateTextView = view.findViewById(R.id.itemText3) as TextView
        val listViewItem = listViewItemList[position]

        titleTextView.text = listViewItem.mcvcontent1
        dateTextView.text=listViewItem.mcvdate1
        sysdateTextView.text= listViewItem.mcvsysdate1
        return view
    }

    override fun getItemId(position: Int): Long {
        return position.toLong()
    }

    override fun getItem(position: Int): Any {
        return listViewItemList[position]
    }

    fun addItem(mcvdate: String, mcvcontent: String, mcvsysdate: String){

        val item = ListViewItem()


        item.mcvcontent1 = mcvcontent
        item.mcvdate1 = mcvdate
        item.mcvsysdate1 = mcvsysdate
        listViewItemList.add(item)
    }
}