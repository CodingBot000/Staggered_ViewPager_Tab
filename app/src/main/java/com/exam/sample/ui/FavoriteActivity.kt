package com.exam.sample.ui


import android.content.Intent
import android.os.Bundle
import androidx.core.view.ViewCompat
import com.exam.sample.R
import com.exam.sample.adapter.ViewPagerAdapter
import com.exam.sample.common.BaseActivity
import com.exam.sample.databinding.ActivityFavoriteBinding
import com.exam.sample.ui.fragment.FavoriteFragment
import com.exam.sample.ui.fragment.UploadFragment
import com.exam.sample.utils.setImageSize
import com.exam.sample.viewmodel.FavoriteViewModel
import com.google.android.material.appbar.AppBarLayout.OnOffsetChangedListener
import kotlinx.android.synthetic.main.activity_favorite.*
import kotlinx.android.synthetic.main.top_item_favorite.*
import org.koin.android.viewmodel.ext.android.viewModel

import kotlin.math.abs



class FavoriteActivity :   BaseActivity<ActivityFavoriteBinding, FavoriteViewModel>() {
    override val TAG: String
        get() = this.javaClass.name
    override val layoutResID: Int
        get() = R.layout.activity_favorite

    override val viewModel : FavoriteViewModel by viewModel()
    val viewPagerAdapter: ViewPagerAdapter by lazy { ViewPagerAdapter(supportFragmentManager) }
    private val favoriteFragment = FavoriteFragment()
    private val uploadFragment = UploadFragment()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onNewIntent(intent: Intent?) {
        super.onNewIntent(intent)
        favoriteFragment.onNewIntent(intent)
        uploadFragment.onNewIntent(intent)
    }

    override fun init() {
        viewPagerAdapter.addFragment(favoriteFragment, getString(R.string.tabNameFavorite))
        viewPagerAdapter.addFragment(uploadFragment, getString(R.string.tabNameUpload))

        tab_layout.setupWithViewPager(pager);
        pager.adapter = viewPagerAdapter

        var beforeVerticalOffset = 0
        var lastSize = 0
        appbar.addOnOffsetChangedListener(
            OnOffsetChangedListener { appBarLayout, verticalOffset ->

                if (collapsing_toolbar.getHeight() + verticalOffset < 2 * ViewCompat.getMinimumHeight(
                        collapsing_toolbar
                    )
                ) {
                    // collapsed
                    if (beforeVerticalOffset != verticalOffset) {
                        imgAvatar.layoutParams = imgAvatar.setImageSize(lastSize, lastSize)
                        beforeVerticalOffset = verticalOffset
                    }
                } else {
                    // extended
                    lastSize = abs(verticalOffset ) + 150
                    if (beforeVerticalOffset != verticalOffset) {
                        imgAvatar.layoutParams = imgAvatar.setImageSize(lastSize, lastSize)
                        beforeVerticalOffset = verticalOffset
                    }

                }
            }
        )

    }


     override fun initObserver() {
        viewModel.apply {

        }
    }

    override fun onDestroy() {
        super.onDestroy()

    }


}
