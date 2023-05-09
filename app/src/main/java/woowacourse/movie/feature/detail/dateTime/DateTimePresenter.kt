package woowacourse.movie.feature.detail.dateTime

import woowacourse.movie.model.SelectTheaterAndMovieState
import woowacourse.movie.model.mapper.asDomain
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.LocalTime

class DateTimePresenter(
    val view: DateTimeContract.View,
    theaterMovieState: SelectTheaterAndMovieState
) : DateTimeContract.Presenter {
    private var runningDates: List<LocalDate> = theaterMovieState.movie.asDomain().runningDates.toList()

    private var _selectDate: LocalDate = runningDates.first()
    override val selectDate: LocalDate
        get() = _selectDate

    private var runningTimes: List<LocalTime> = theaterMovieState.allowTimes.toList()

    private var _selectTime: LocalTime = runningTimes.first()
    override val selectTime: LocalTime
        get() = _selectTime

    init {
        view.setDateSpinnerItems(runningDates.toList())
        view.setTimeSpinnerItems(runningTimes.toList())
    }

    override fun setDateTime(dateTime: LocalDateTime) {
        _selectDate = dateTime.toLocalDate()
        _selectTime = dateTime.toLocalTime()
        view.setSelectDate(runningDates.indexOf(selectDate))
        view.setSelectTime(runningTimes.indexOf(selectTime))
    }

    override fun clickDate(position: Int) {
        _selectDate = runningDates[position]
    }

    override fun clickTime(position: Int) {
        _selectTime = runningTimes[position]
    }
}
