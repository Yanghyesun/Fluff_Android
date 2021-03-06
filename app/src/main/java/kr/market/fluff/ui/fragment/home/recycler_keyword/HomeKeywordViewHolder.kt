package kr.market.fluff.ui.fragment.home.recycler_keyword

import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import kr.market.fluff.R
import kr.market.fluff.data.home.HomeKeywordData

class HomeKeywordViewHolder (itemView : View) : RecyclerView.ViewHolder(itemView) {
    val home_recycler_img : ImageView = itemView.findViewById(R.id.img_rv_keyword)
    val home_recycler_title : TextView = itemView.findViewById(R.id.tv_rv_keyword)

    fun bind(data : HomeKeywordData)
    {
        home_recycler_title.text = data.title

    }
}
