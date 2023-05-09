package woowacourse.movie.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize
import woowacourse.movie.feature.reservationList.itemModel.TicketsItemModel
import java.time.LocalDateTime

@Parcelize
data class TicketsState(
    val theater: TheaterState,
    val movie: MovieState,
    val dateTime: LocalDateTime,
    val totalDiscountedMoneyState: MoneyState,
    val tickets: List<TicketState>
) : Parcelable {
    fun toItemModel(onClick: (tickets: TicketsState) -> Unit): TicketsItemModel {
        return TicketsItemModel(this, onClick)
    }
}
