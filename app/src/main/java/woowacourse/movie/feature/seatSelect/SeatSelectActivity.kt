package woowacourse.movie.feature.seatSelect

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.widget.TableRow
import androidx.core.view.children
import androidx.databinding.DataBindingUtil
import woowacourse.movie.R
import woowacourse.movie.data.TicketsRepositoryImpl
import woowacourse.movie.databinding.ActivitySeatSelectBinding
import woowacourse.movie.feature.common.BackKeyActionBarActivity
import woowacourse.movie.feature.common.customView.SeatView
import woowacourse.movie.feature.confirm.AlarmReceiver
import woowacourse.movie.feature.confirm.ReservationConfirmActivity
import woowacourse.movie.model.MoneyState
import woowacourse.movie.model.ReservationState
import woowacourse.movie.model.SeatPositionState
import woowacourse.movie.model.TicketsState
import woowacourse.movie.util.getParcelableArrayListCompat
import woowacourse.movie.util.getParcelableExtraCompat
import woowacourse.movie.util.keyError
import woowacourse.movie.util.setAlarm
import woowacourse.movie.util.showAskDialog
import java.time.LocalDateTime
import kotlin.collections.ArrayList

class SeatSelectActivity : BackKeyActionBarActivity(), SeatSelectContract.View {
    private lateinit var binding: ActivitySeatSelectBinding

    private lateinit var reservationState: ReservationState

    private lateinit var presenter: SeatSelectContract.Presenter

    override fun onCreateView(savedInstanceState: Bundle?) {
        binding = DataBindingUtil.setContentView(this, R.layout.activity_seat_select)
        reservationState =
            intent.getParcelableExtraCompat(KEY_RESERVATION) ?: return keyError(KEY_RESERVATION)
        presenter = SeatSelectPresenter(this, reservationState, TicketsRepositoryImpl)
        binding.movie = reservationState.movieState
        presenter.updateChosenSeats(listOf())
        initClickListener()
    }

    private fun getAllSeatView(): List<SeatView> {
        return binding.seats
            .children
            .filterIsInstance<TableRow>()
            .flatMap { it.children }
            .filterIsInstance<SeatView>()
            .toList()
    }

    private fun initClickListener() {
        binding.reservationConfirm.setOnClickListener { presenter.clickConfirm() }
        getAllSeatView().forEachIndexed { index, seatView ->
            seatView.setOnClickListener {
                presenter.clickSeat(index)
            }
        }
    }

    override fun onRestoreInstanceState(
        savedInstanceState: Bundle
    ) {
        super.onRestoreInstanceState(savedInstanceState)
        val restoreState: ArrayList<SeatPositionState> =
            savedInstanceState.getParcelableArrayListCompat(SEAT_RESTORE_KEY) ?: return keyError(
                SEAT_RESTORE_KEY
            )
        presenter.updateChosenSeats(restoreState)
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putParcelableArrayList(SEAT_RESTORE_KEY, ArrayList(presenter.seats))
    }

    override fun seatToggle(index: Int) {
        getAllSeatView()[index].toggle()
    }

    override fun changePredictMoney(moneyState: MoneyState) {
        binding.money = moneyState
    }

    override fun setConfirmClickable(clickable: Boolean) {
        binding.reservationConfirm.isClickable = clickable
    }

    override fun showDialog() {
        showAskDialog(
            titleId = R.string.reservation_confirm,
            messageId = R.string.ask_really_reservation,
            negativeStringId = R.string.reservation_cancel,
            positiveStringId = R.string.reservation_complete
        ) {
            presenter.clickDialogConfirm()
        }
    }

    override fun navigateReservationConfirm(tickets: TicketsState) {
        val intent = ReservationConfirmActivity.getIntent(this, tickets)
        startActivity(intent)
    }

    override fun setReservationAlarm(
        tickets: TicketsState,
        triggerDateTime: LocalDateTime,
        requestCode: Int
    ) {
        setAlarm(AlarmReceiver.getIntent(this, tickets), triggerDateTime, requestCode)
    }

    companion object {
        fun getIntent(context: Context, reservationState: ReservationState): Intent {
            val intent = Intent(context, SeatSelectActivity::class.java)
            intent.putExtra(KEY_RESERVATION, reservationState)
            return intent
        }

        private const val KEY_RESERVATION = "key_reservation"
        private const val SEAT_RESTORE_KEY = "seat_restore_key"
    }
}
