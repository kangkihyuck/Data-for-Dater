package com.applandeo.materialcalendarsampleapp

import android.graphics.Rect
import android.view.ViewTreeObserver
import android.view.Window
import kotlinx.android.synthetic.main.activity_main.view.*

class KeyboardVisiblitiy(

        private val window: Window,
        private val onShowKeyboard: ((keyboardHeight: Int) -> Unit)? = null,
        private val onHideKeyboard: (() -> Unit)? = null

) {
    private val MIN_KEYBOARD_HEIGHT_PX = 150

    private val windowVisiblitiyDisplayFrame = Rect()
    private var lastVisiblitiyDecorViewHeight: Int = 0

    private val onGlobalLayoutListener = ViewTreeObserver.OnGlobalLayoutListener {
        window.decorView.getWindowVisibleDisplayFrame(windowVisiblitiyDisplayFrame)
        val visibleDecorViewHeight = windowVisiblitiyDisplayFrame.height() // 현재 화면의 height 값

        //현재 화면 높이를 변경하여 키보드가 보이는지 여부를 결정한다.
        if(lastVisiblitiyDecorViewHeight !=0){
            if(lastVisiblitiyDecorViewHeight > visibleDecorViewHeight + MIN_KEYBOARD_HEIGHT_PX){
                // 이전에 보여준 화면 height > 현재 화면 height + 최소 키보드 크기 값보다 크면
                // 키보드가 올라온 것으로 보고 키보드가 보였다는 이벤트를 전달하고,

                val curentKeyboardHeight = window.decorView.height - windowVisiblitiyDisplayFrame.bottom
                //키보드 높이 = 현재화면 - 보여지는 맨 마지막 레이아웃의 끝부분

                // 키보드 표시에 대해 알림
                onShowKeyboard?.invoke(curentKeyboardHeight)
            }else if(lastVisiblitiyDecorViewHeight + MIN_KEYBOARD_HEIGHT_PX < visibleDecorViewHeight){
                // 이전에 보여준 화면 height + 최소 키보드 값이 < 현재 화면 height보다 작으면
                // 키보드가 내려간 것으로 보고 키보드가 사라졌다는 이벤트를 전달.

                onHideKeyboard?.invoke()
                // 키보드가 숨어져 있다는 것을 알림
            }
        }
        lastVisiblitiyDecorViewHeight = visibleDecorViewHeight
        // 다음 호출을 위해 현재화면 height 값 저장
    }

    init {
        window.decorView.viewTreeObserver.addOnGlobalLayoutListener(onGlobalLayoutListener)

    }
    fun detachKeyboardListeners(){
        window.decorView.viewTreeObserver.removeOnGlobalLayoutListener(onGlobalLayoutListener)
    }
}