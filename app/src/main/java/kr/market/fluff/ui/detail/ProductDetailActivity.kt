package kr.market.fluff.ui.detail

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.view.WindowManager
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.viewpager.widget.ViewPager
import com.bumptech.glide.Glide
import com.tbuonomo.viewpagerdotsindicator.DotsIndicator
import kotlinx.android.synthetic.main.activity_product_detail.*
import kr.market.fluff.R
import kr.market.fluff.data.App
import kr.market.fluff.data.detail.DetailProductData
import kr.market.fluff.network.RequestInterface
import kr.market.fluff.network.RequestToServer
import kr.market.fluff.network.safeEnqueue
import kr.market.fluff.ui.detail.product_detail_recycler.DetailRecyclerAdapter
import kr.market.fluff.ui.fragment.mypage.cart.CartActivity
import kr.market.fluff.ui.util.drawCustomToast
import kr.market.fluff.ui.util.item_decorator.HorizontalItemDecorator
import kr.market.fluff.ui.util.sendToast

class ProductDetailActivity : AppCompatActivity() {

    lateinit var detailAdapter: DetailRecyclerAdapter
    lateinit var datas : List<DetailProductData>
     var heart_bool: Boolean = true

    var product_item_id : String? = "0"
    var img_datas : ArrayList<String> = ArrayList()



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        window.setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN)
        setContentView(R.layout.activity_product_detail)

        product_item_id = intent.getStringExtra("product_item_id")
        img_detail_my_cart.setOnClickListener {startActivity(Intent(this,CartActivity::class.java))}
        img_detail_heart.setOnClickListener {setHeartEvent()}
        btn_cart_buy.setOnClickListener {

            RequestToServer.service.request_cart_add(
                "application/json",
                App.prefs.local_login_token!!,
                RequestInterface.RequestCartAdd(cartId = product_item_id!!)
                ).safeEnqueue(
                onSuccess = {
                    sendToast("장바구니에 담겼습니다")
                },
                onError = {sendToast("서버 연결이 원활하지 않습니다.")},
                onFail = {_,_->sendToast("장바구니 담는데 실패했습니다.")}
            )
        }
        tv_detail_closet_title.text = intent.getStringExtra("product_name")
        tv_detail_closet_price.text = intent.getStringExtra("product_price")
        img_product_detail_back.setOnClickListener {finish()}
        makeRecycler()

        loadDetailProductData()
    }
    private fun setHeartEvent(){
        var toast = Toast(this )
        if(heart_bool) {
            Glide.with(this)
                .load(R.drawable.ic_check_heart)
                .into(img_detail_heart)
            toast.drawCustomToast(this)
            heart_bool=false
        }
        else {
            Glide.with(this)
                .load(R.drawable.ic_empty_heart)
                .into(img_detail_heart)
            heart_bool = true
        }
    }
    private fun loadDetailProductData(){
        rl_detail_layout.visibility = View.GONE
        RequestToServer.service.requestProductDetail(
            product_item_id!!,
            "application/json",
            App.prefs.local_login_token!!).safeEnqueue(
            onSuccess = {
                rl_detail_layout.visibility = View.VISIBLE
                product_item_id = it._id
                tv_detail_closet_size_content.text = it.size
                tv_detail_closet_state_content.text = it.condition.toString()
                tv_detail_closet_sub_content.text = it.comment
                rb_favorite_market_seller.rating = it.grade.toFloat()
                img_datas = it.img
                makeDetailViewPager()
            },
            onFail = {_,_->sendToast("제품을 조회하는데 실패했습니다")},
            onError = {sendToast("서버 오류입니다")}
        )
    }

    fun makeRecycler(){
        //더미데이터로 처리함
        datas = listOf(
            DetailProductData(
                "https://cdn.pixabay.com/photo/2017/08/06/08/01/people-2590092__340.jpg",
                "32,000원",
                "레이스 스커트"
            ),
            DetailProductData(
                "https://cdn.pixabay.com/photo/2016/03/27/19/31/fashion-1283863__340.jpg",
                "42,000원",
                "고급 울 니트"
            ),
            DetailProductData(
                "https://cdn.pixabay.com/photo/2017/04/16/01/53/girl-2233820__340.jpg",
                "38,000원",
                "봄꽃 무늬 가디건"
            )
        )
        detailAdapter =
            DetailRecyclerAdapter(
                datas
            )
        rv_detail_other.apply {
            layoutManager = LinearLayoutManager(context,LinearLayoutManager.HORIZONTAL,false)
            adapter = detailAdapter
            addItemDecoration(HorizontalItemDecorator(24))
        }
    }

    fun makeDetailViewPager(){
        val dotsIndicator= findViewById<DotsIndicator>(R.id.detail_dots_indicator)
        val adapter = FragmentDetailPagerAdapter(supportFragmentManager,img_datas.size,img_datas)
        val vp_detail_viewpager = findViewById<ViewPager>(R.id.vp_detail_viewpager)
        vp_detail_viewpager.adapter = adapter
        dotsIndicator.setViewPager(vp_detail_viewpager)
        vp_detail_viewpager.offscreenPageLimit=2
    }

}
