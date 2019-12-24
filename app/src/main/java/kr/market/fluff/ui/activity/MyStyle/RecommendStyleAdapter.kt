package kr.market.fluff.ui.activity.MyStyle

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import kr.market.fluff.R
import kr.market.fluff.data.RecommendStyleData

class RecommendStyleAdapter (private val context: Context):RecyclerView.Adapter<RecommendStyleViewHolder>(){
    var data = listOf<RecommendStyleData>()
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecommendStyleViewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.item_rv_recommend_style,parent,false)
        return RecommendStyleViewHolder(view)
    }

    override fun getItemCount(): Int = data.size

    override fun onBindViewHolder(holder: RecommendStyleViewHolder, position: Int) {
        holder.bind(data[position])
    }
}
