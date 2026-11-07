package com.deniscerri.ytdl.ui.downloadcard

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.Lifecycle
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.deniscerri.ytdl.database.models.DownloadItem
import com.deniscerri.ytdl.database.models.ResultItem

class DownloadFragmentAdapter (
    fragmentManager : FragmentManager,
    lifecycle : Lifecycle,
    private var result: ResultItem?,
    private var downloadItem: DownloadItem?,
    nonSpecific: Boolean = false,
    var isIncognito: Boolean = false
) : FragmentStateAdapter(fragmentManager, lifecycle) {

    fun setResultItem(res: ResultItem){
        result = res
        kotlin.runCatching { (fragments[0] as DownloadAudioFragment).updateUI(res) }
    }

    // audio only: the video and command fragments are not created at all
    val fragments = listOf<Fragment>(
        DownloadAudioFragment(result, downloadItem, "", nonSpecific)
    )

    override fun getItemCount(): Int {
        return fragments.size
    }

    override fun createFragment(position: Int): Fragment {
        return fragments[0]
    }

    fun updateWhenSwitching(currentViewPagerItem : Int) {
        // nothing to sync, there is only one tab
    }

    fun getDownloadItem(position: Int) : DownloadItem {
        return (fragments[0] as DownloadAudioFragment).downloadItem.apply {
            incognito = isIncognito
        }
    }

}
