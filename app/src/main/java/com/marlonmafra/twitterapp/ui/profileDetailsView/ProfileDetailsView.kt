package com.marlonmafra.twitterapp.ui.profileDetailsView

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import androidx.constraintlayout.widget.ConstraintLayout
import com.marlonmafra.twitterapp.R
import kotlinx.android.synthetic.main.profile_details_view.view.*

class ProfileDetailsView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : ConstraintLayout(context, attrs, defStyleAttr) {

    init {
        LayoutInflater.from(context).inflate(R.layout.profile_details_view, this, true)
        val typedArray =
            context.obtainStyledAttributes(attrs, R.styleable.ProfileDetailsView, defStyleAttr, 0)

        val label = typedArray.getResourceId(
            R.styleable.ProfileDetailsView_details_label,
            R.string.following
        )
        textLabel.setText(label)
        typedArray.recycle()
    }

    fun setValue(value: String) {
        textValue.text = value
    }
}