package woowacourse.movie.feature.common

import android.widget.ImageView
import androidx.databinding.BindingAdapter

@BindingAdapter("imgId")
fun setMovieImageId(view: ImageView, id: Int) {
    view.setImageResource(id)
}
