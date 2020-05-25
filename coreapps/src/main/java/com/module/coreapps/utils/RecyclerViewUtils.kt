package br.com.grupofleury.core.utils

import androidx.core.widget.NestedScrollView
import timber.log.Timber

@Suppress("unused")
object RecyclerViewUtils {
    abstract class InfiniteScrollListener(
        private val layoutManager: androidx.recyclerview.widget.LinearLayoutManager
    ) : androidx.recyclerview.widget.RecyclerView.OnScrollListener() {
        private var previousTotal = 0
        private var visibleThreshold = 5
        private var firstVisibleItem = 0
        private var visibleItemCount = 0
        private var totalItemCount = 0

        abstract val isLoading: Boolean

        abstract val isLastPage: Boolean

        abstract fun loadMore()

        override fun onScrolled(
            recyclerView: androidx.recyclerview.widget.RecyclerView,
            dx: Int,
            dy: Int
        ) {
            super.onScrolled(recyclerView, dx, dy)

            if (!isLastPage && dy > 0) {
                visibleItemCount = recyclerView.childCount
                totalItemCount = layoutManager.itemCount
                firstVisibleItem = layoutManager.findFirstVisibleItemPosition()


                val needsLoad =
                    (totalItemCount - visibleItemCount) <= (firstVisibleItem + visibleThreshold)

                Timber.i("%s", "isLoading = $isLoading")
                Timber.i("%s", "totalItemCount = $totalItemCount")
                Timber.i("%s", "visibleItemCount = $visibleItemCount")
                Timber.i(
                    "%s",
                    "totalItemCount - visibleItemCount <= (firstVisibleItem + visibleThreshold) = $needsLoad"
                )

                if (!isLoading && needsLoad) {
                    Timber.i("%s", "End reached -> loadMore()")
                    loadMore()
                }
            }
        }

    }

    class NestedInfiniteScrollListener(val func: () -> Unit) :
        NestedScrollView.OnScrollChangeListener {
        override fun onScrollChange(
            v: NestedScrollView?,
            scrollX: Int,
            scrollY: Int,
            oldScrollX: Int,
            oldScrollY: Int
        ) {
            if (v?.getChildAt(v.childCount - 1) != null) {
                if (scrollY >= v.getChildAt(v.childCount - 1).measuredHeight - v.measuredHeight && scrollY > oldScrollY) {
                    //code to fetch more data for endless scrolling
                    Timber.i("NestedInfiniteScrollList::%s", "End reached")
                    func()
                }
            }
        }

    }
}