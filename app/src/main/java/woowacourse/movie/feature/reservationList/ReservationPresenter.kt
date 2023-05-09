package woowacourse.movie.feature.reservationList

import com.example.domain.usecase.GetAllReservationTicketsUseCase
import woowacourse.movie.model.mapper.asPresentation

class ReservationPresenter(
    val view: ReservationListContract.View,
    private val getAllReservationTicketsUseCase: GetAllReservationTicketsUseCase
) : ReservationListContract.Presenter {
    override fun loadTicketsItems() {
        getAllReservationTicketsUseCase(
            onSuccess = {
                val ticketsItems = it.map {
                    it.asPresentation().toItemModel { tickets ->
                        view.navigateReservationConfirm(tickets)
                    }
                }
                view.updateItems(ticketsItems)
            },
            onFailure = { view.errorLoadReservationTicketsData() }
        )
    }
}
