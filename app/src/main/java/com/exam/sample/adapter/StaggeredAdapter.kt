package com.exam.sample.adapter



import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.exam.sample.R
import com.exam.sample.databinding.GridItemBinding
import com.exam.sample.model.data.TrendingDetail
import com.exam.sample.utils.Const
import com.exam.sample.utils.setCellSize
import com.exam.sample.utils.setRainBowBackgroundColorByPosition
import kotlinx.android.synthetic.main.grid_item.view.*
import java.util.ArrayList



class StaggeredAdapter (private val itemListClick: (TrendingDetail) -> Unit)
    : RecyclerView.Adapter<StaggeredAdapter.ItemViewHolder>() {

    private var recyclerItemList: ArrayList<TrendingDetail> = ArrayList()

    override fun onCreateViewHolder(parent: ViewGroup, position: Int): ItemViewHolder {

        var binding: GridItemBinding = DataBindingUtil.inflate(
            LayoutInflater.from(parent.context),
            R.layout.grid_item,
            parent,
            false
        )

        return ItemViewHolder(binding)
    }

    override fun onBindViewHolder(holderItem: ItemViewHolder, position: Int) {
        recyclerItemList[position].let {
            val width = it.images.fixed_width_small.width?.toFloat()
            val height = it.images.fixed_width_small.height?.toFloat()
            holderItem.itemView.imgTop.layoutParams = holderItem.itemView.imgTop.setCellSize(
                        width ?: Const.SCREEN_WIDTH_HALF,
                        height ?: Const.SCREEN_WIDTH_HALF
                    )
            holderItem.itemView.imgTop.setRainBowBackgroundColorByPosition(position)
            holderItem.bind(it)
        }
    }


    override fun getItemId(position: Int): Long {
        return  (recyclerItemList[position]).id.hashCode().toLong()
    }

    override fun getItemCount(): Int {
        return recyclerItemList.size
    }

    fun initItem(list: ArrayList<TrendingDetail>) {
        recyclerItemList = list
        notifyDataSetChanged()
    }

    fun addItem(list: ArrayList<TrendingDetail>) {
        recyclerItemList.addAll(list)
        notifyDataSetChanged()
    }

    fun clearItem() {
        recyclerItemList.clear()
        notifyDataSetChanged()
    }

     inner class ItemViewHolder(private val binding: GridItemBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(item: TrendingDetail) {
            binding.item = item
            binding.root.setOnClickListener {
                itemListClick(item)
            }

            binding.executePendingBindings()
        }
    }
}