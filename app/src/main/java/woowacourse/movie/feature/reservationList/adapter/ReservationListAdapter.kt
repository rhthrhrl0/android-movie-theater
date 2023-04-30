package woowacourse.movie.feature.reservationList.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import woowacourse.movie.R
import woowacourse.movie.feature.common.itemModel.ItemModel
import woowacourse.movie.feature.common.viewHolder.ItemViewHolder
import woowacourse.movie.feature.reservationList.viewHolder.TicketsViewHolder

class ReservationListAdapter(
    reservations: List<ItemModel>
) : RecyclerView.Adapter<ItemViewHolder>() {

    private var _reservations = reservations.toList()
    val reservations
        get() = _reservations.toList()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemViewHolder {
        val itemView = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_reservation_layout, parent, false)
        return TicketsViewHolder(itemView)
    }

    override fun getItemCount(): Int = _reservations.size

    override fun onBindViewHolder(holder: ItemViewHolder, position: Int) {
        holder.bind(_reservations[position])
    }

    fun setItemChanged(newReservations: List<ItemModel>) {
        _reservations = newReservations.toList()
        notifyDataSetChanged()
    }
}
