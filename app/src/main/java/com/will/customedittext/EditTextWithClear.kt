package com.will.customedittext

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.Rect
import android.graphics.Typeface
import android.graphics.drawable.Drawable
import android.os.Build
import android.util.AttributeSet
import android.view.MotionEvent
import android.widget.EditText
import androidx.annotation.RequiresApi
import androidx.core.content.ContextCompat
@SuppressLint("Recycle")
class EditTextWithClear @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0
) : androidx.appcompat.widget.AppCompatEditText(context, attrs, defStyleAttr) {

    private var iconDrawable : Drawable? = null

     init {

        context.obtainStyledAttributes(attrs,R.styleable.EditTextWithClear,0,0)
            .apply {
                try {
                    val iconId = getResourceId(R.styleable.EditTextWithClear_clearIcon,0)
                    if (iconId != 0){
                        iconDrawable = ContextCompat.getDrawable(context,iconId)
                    }
                } finally {
                    recycle()
                }
            }
    }
    @RequiresApi(Build.VERSION_CODES.JELLY_BEAN_MR1)
    override fun onTextChanged(
        text: CharSequence?,
        start: Int,
        lengthBefore: Int,
        lengthAfter: Int
    ) {
        super.onTextChanged(text, start, lengthBefore, lengthAfter)
        toggleClearIcon()
    }

    @RequiresApi(Build.VERSION_CODES.JELLY_BEAN_MR1)
    override fun onFocusChanged(focused: Boolean, direction: Int, previouslyFocusedRect: Rect?) {
        super.onFocusChanged(focused, direction, previouslyFocusedRect)
        toggleClearIcon()
    }

    override fun onTouchEvent(event: MotionEvent?): Boolean {
        event?.let { e ->
            iconDrawable?.let {
                if ((event.action == MotionEvent.ACTION_UP
                            && e.x > width - it.intrinsicWidth + 20
                            && e.x < width - 20
                            && e.y > height / 2 - it.intrinsicHeight / 2 - 20
                            && e.y < height / 2 + it.intrinsicHeight / 2 + 20)
                ) {
                    text?.clear()
                }
            }
        }

        performClick()
        return super.onTouchEvent(event)
    }

    override fun performClick(): Boolean {//语言输入可以触发这个点击事件
        return super.performClick()
    }


    @RequiresApi(Build.VERSION_CODES.JELLY_BEAN_MR1)
    private fun toggleClearIcon() {
        val icon = if (isFocused && text?.isEmpty() != true) iconDrawable else null
        setCompoundDrawablesRelativeWithIntrinsicBounds(null, null, icon, null)
    }


}