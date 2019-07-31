package ru.skillbranch.devintensive.ui.profile

import android.graphics.*
import android.graphics.drawable.BitmapDrawable
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.util.TypedValue
import android.view.View
import android.widget.EditText
import android.widget.TextView
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import kotlinx.android.synthetic.main.activity_profile.*
import ru.skillbranch.devintensive.R
import ru.skillbranch.devintensive.models.Profile
import ru.skillbranch.devintensive.utils.Utils
import ru.skillbranch.devintensive.viewmodels.ProfileViewModel
import kotlin.math.roundToInt

class ProfileActivity : AppCompatActivity() {
    companion object {
        const val IS_EDIT_MODE = "IS_EDIT_MODE"
        const val IS_REPO_VALID = "IS_REPO_VALID"
    }

    var isEditMode = false
    lateinit var viewModel: ProfileViewModel
    lateinit var viewFields: Map<String, TextView>
    lateinit var textWatcher: TextWatcher
    private var isRepoUrlValid = true

    override fun onCreate(savedInstanceState: Bundle?) {
        setTheme(R.style.AppTheme)
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_profile)
        initViews(savedInstanceState)
        initViewModel()
        initTextWatcher()
        Log.d("M_ProfileActivity","onCreate")
    }

    override fun onResume() {
        super.onResume()
        et_repository.addTextChangedListener(textWatcher)
    }

    override fun onPause() {
        super.onPause()
        et_repository.addTextChangedListener(textWatcher)
    }

    private fun initTextWatcher() {
        textWatcher = object : TextWatcher {
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                if (p0 != null && Utils.isValidGithubRepoUrl(p0.toString())) {
                    wr_repository.error = null
                    wr_repository.isErrorEnabled = false

                    isRepoUrlValid = true
                } else {
                    wr_repository.error = "Невалидный адрес репозитория"

                    isRepoUrlValid = false
                }
            }

            override fun afterTextChanged(p0: Editable?) {}
        }
    }


    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putBoolean(IS_EDIT_MODE, isEditMode)
        outState.putBoolean(IS_REPO_VALID, isRepoUrlValid)
    }

    private fun initViewModel() {
        viewModel = ViewModelProviders.of(this).get(ProfileViewModel::class.java)
        viewModel.getProfileData().observe(this, Observer { updateUI(it) })
        viewModel.getTheme().observe(this, Observer { updateTheme(it) })
    }

    private fun updateTheme(mode: Int) {
        Log.d("M_ProfileActivity","update theme")
        delegate.setLocalNightMode(mode)
    }

    private fun updateUI(profile: Profile) {
        profile.toMap().also {
            for ((k, v) in viewFields) {
                v.text = it[k].toString()
            }

            val initials = Utils.toInitials(it["firstName"].toString(), it["lastName"].toString()) ?: ""
            if (initials.isNotEmpty()) {
                val bitmap = getBitmapFromText(initials)
                val drawable = BitmapDrawable(resources, bitmap)

                iv_avatar.setImageDrawable(drawable)
            }
        }
    }

    private fun initViews(savedInstanceState: Bundle?) {
        viewFields = mapOf(
            "nickName" to tv_nick_name,
            "rank" to tv_rank,
            "firstName" to et_first_name,
            "lastName" to et_last_name,
            "about" to et_about,
            "repository" to et_repository,
            "rating" to tv_rating,
            "respect" to tv_respect
        )

        isEditMode = savedInstanceState?.getBoolean(IS_EDIT_MODE, false) ?: false
        showCurrentMode(isEditMode)

        isRepoUrlValid =  savedInstanceState?.getBoolean(IS_REPO_VALID, true) ?: true

        btn_edit.setOnClickListener {
            if (isEditMode) {
                if (!isRepoUrlValid) {
                    et_repository.text.clear()

                    wr_repository.error = null
                    wr_repository.isErrorEnabled = false
                }

                saveProfileInfo()
            }
            isEditMode = !isEditMode
            showCurrentMode(isEditMode)
        }

        btn_switch_theme.setOnClickListener {
            viewModel.switchTheme()
        }
    }

    private fun showCurrentMode(isEdit: Boolean) {
        val info = viewFields.filter { setOf( "firstName", "lastName", "about", "repository").contains(it.key) }

        for ((_, v) in info) {
            v as EditText
            v.isFocusable = isEdit
            v.isFocusableInTouchMode = isEdit
            v.isEnabled = isEdit
            v.background.alpha = if (isEdit) 255 else 0
        }

        ic_eye.visibility = if (isEdit) View.GONE else View.VISIBLE
        wr_about.isCounterEnabled = isEdit

        with(btn_edit) {
            val filter: ColorFilter? = if (isEdit) {
                PorterDuffColorFilter(
                    resources.getColor(R.color.color_accent, theme),
                    PorterDuff.Mode.SRC_IN
                )
            } else {
                null
            }

            val icon = if (isEdit) {
                resources.getDrawable(R.drawable.ic_save_black_24dp, theme)
            } else {
                resources.getDrawable(R.drawable.ic_edit_black_24dp, theme)
            }

            background.colorFilter = filter
            setImageDrawable(icon)
        }
    }

    private fun saveProfileInfo() {
        Profile(
            firstName = et_first_name.text.toString(),
            lastName = et_last_name.text.toString(),
            about = et_about.text.toString(),
            repository = et_repository.text.toString()
        ).apply {
            viewModel.saveProfileData(this)
        }
    }

    private fun getBitmapFromText(text: String) : Bitmap {
        val metrix = resources.displayMetrics.density.roundToInt()

        val paint = Paint(Paint.ANTI_ALIAS_FLAG)
        paint.textSize = 48f*metrix
        paint.color = Color.WHITE
        paint.textAlign = Paint.Align.CENTER

        val bitmap = Bitmap.createBitmap(112 * metrix, 112 * metrix, Bitmap.Config.ARGB_8888)

        val value = TypedValue()
        theme.resolveAttribute(R.attr.colorAccent, value, true)
        bitmap.eraseColor(value.data)

        val canvas = Canvas(bitmap)
        canvas.drawText(text, 56f*metrix, 56f*metrix + paint.textSize/3, paint)

        return bitmap
    }
}