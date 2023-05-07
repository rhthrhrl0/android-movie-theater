package woowacourse.movie.feature.detail

import woowacourse.movie.model.CountState
import woowacourse.movie.model.SelectReservationState
import woowacourse.movie.model.SelectTheaterAndMovieState
import java.time.LocalDateTime

interface MovieDetailContract {
    interface View {
        fun navigateSeatSelect(reservationState: SelectReservationState)
    }

    interface Presenter {
        fun clickConfirm(
            theaterMovie: SelectTheaterAndMovieState,
            dateTime: LocalDateTime,
            reservationCount: CountState
        )
    }
}
