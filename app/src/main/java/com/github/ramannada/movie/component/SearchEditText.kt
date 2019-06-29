package com.github.ramannada.movie.component

import android.content.Context
import android.graphics.Canvas
import android.graphics.drawable.Drawable
import android.text.Editable
import android.text.TextWatcher
import android.util.AttributeSet
import android.view.MotionEvent
import android.view.View
import android.widget.EditText
import com.github.ramannada.movie.R

/**
 * Created by labibmuhajir on 2019-06-26.
 * labibmuhajir@yahoo.com
 */
class SearchEditText : EditText {
    private val clearButton: Drawable = resources.getDrawable(R.drawable.ic_close, null)
    private val icSearch: Drawable = resources.getDrawable(R.drawable.ic_search, null)

    constructor(context: Context) : super(context)

    constructor(context: Context, attributes: AttributeSet) : super(context, attributes)

    constructor(context: Context, attributes: AttributeSet, defStyleAttr: Int) : super(
        context,
        attributes,
        defStyleAttr
    )

    init {
        setOnTouchListener { _, event ->
            var clearButtonStart: Float
            var clearButtonEnd: Float
            var isClearBunttonClicked: Boolean = false
            if (compoundDrawablesRelative[2] != null) {
                if (layoutDirection == View.LAYOUT_DIRECTION_RTL) {
                    clearButtonEnd = (clearButton.intrinsicWidth + paddingStart).toFloat()

                    if (event.x < clearButtonEnd) {
                        isClearBunttonClicked = true
                    }
                } else {
                    clearButtonStart = (width - paddingEnd - clearButton.intrinsicWidth).toFloat()

                    if (event.x > clearButtonStart) isClearBunttonClicked = true
                }

                if (isClearBunttonClicked) {
                    when (event.action) {
                        MotionEvent.ACTION_UP -> {
                            hideClearButton()
                            if (text?.isNotEmpty() == true) text?.clear()
                            return@setOnTouchListener true
                        }

                        MotionEvent.ACTION_DOWN -> {
                            showClearButton()
                            return@setOnTouchListener true
                        }

                        else -> return@setOnTouchListener false
                    }
                }

                return@setOnTouchListener false
            } else {
                return@setOnTouchListener false
            }
        }

        addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {

            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {

            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                if (!s.isNullOrEmpty()) showClearButton() else hideClearButton()
            }

        })
    }

    override fun onDraw(canvas: Canvas?) {
        super.onDraw(canvas)
        setCompoundDrawablesRelativeWithIntrinsicBounds(icSearch, null, null, null)
        hint = context.getString(R.string.hint_movie_title)
        textAlignment = View.TEXT_ALIGNMENT_TEXT_START
    }

    private fun showClearButton() {
        setCompoundDrawablesRelativeWithIntrinsicBounds(icSearch, null, clearButton, null)
    }

    private fun hideClearButton() {
        setCompoundDrawablesRelativeWithIntrinsicBounds(icSearch, null, null, null)
    }
}