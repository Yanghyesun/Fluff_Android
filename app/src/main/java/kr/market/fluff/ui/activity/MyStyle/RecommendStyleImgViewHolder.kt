package kr.market.fluff.ui.activity.MyStyle

import android.media.Image
import android.view.View
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import kotlinx.android.synthetic.main.item_rv_recomment_style_img.view.*
import kr.market.fluff.R
import kr.market.fluff.data.RecommendStyleImgData

class RecommendStyleImgViewHolder (view: View) :RecyclerView.ViewHolder(view){
    val img_url: ImageView = view.findViewById(R.id.img_recommend_style_img)

    fun bind(data: RecommendStyleImgData){
        Glide.with(itemView).load(data.img_url).into(img_url)
    }
}