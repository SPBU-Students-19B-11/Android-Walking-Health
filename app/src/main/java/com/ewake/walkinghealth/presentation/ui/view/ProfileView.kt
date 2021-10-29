package com.ewake.walkinghealth.presentation.ui.view

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.widget.FrameLayout
import com.ewake.walkinghealth.databinding.ItemProfileBinding
import com.ewake.walkinghealth.presentation.model.SimpleUserModel

/**
 * @author Nikolaevsky Dmitry (@d.nikolaevskiy)
 */
class ProfileView @JvmOverloads constructor(context: Context, attributeSet: AttributeSet? = null) :
    FrameLayout(context, attributeSet) {

    private val binding: ItemProfileBinding by lazy {
        ItemProfileBinding.inflate(LayoutInflater.from(getContext()), this, true)
    }

    var model: SimpleUserModel? = null
        set(value) {
            field = value
            notifyDataChanged()
        }

    private fun notifyDataChanged() {
        binding.apply {
            if (model != null) {
                fullname.text = model!!.fullname
                login.text = model!!.login
            } else {
                fullname.text = ""
                login.text = ""
            }
        }
    }

}