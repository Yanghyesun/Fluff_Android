package kr.market.fluff.ui.myStyle

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.GridLayoutManager
import kotlinx.android.synthetic.main.activity_my_style.*
import kr.market.fluff.R
import kr.market.fluff.network.RequestToServer
import kr.market.fluff.network.safeEnqueue
import kr.market.fluff.data.App
import kr.market.fluff.data.myStyle.RecommendStyleRequest
import kr.market.fluff.ui.recommendStyle.RecommendStyleActivity
import kr.market.fluff.ui.util.item_decorator.HorizontalItemDecorator
import kr.market.fluff.ui.util.item_decorator.VerticalItemDecorator
import kr.market.fluff.ui.util.sendToast
import kr.market.fluff.network.enqueue
import kr.market.fluff.ui.MainActivity
import kr.market.fluff.ui.intro.WelcomeActivity
import java.lang.StringBuilder

class MyStyleActivity : AppCompatActivity() {
    private lateinit var myStyleAdapter : MyStyleAdapter
    var click_count: Int = 0
    val requestToServer = RequestToServer

    //keywords
    var simple: Int = 0
    var street: Int = 0
    var lovely: Int = 0
    var modernchic: Int = 0
    var unique: Int = 0
    var formal: Int = 0
    var ethnic: Int = 0
    var sporty: Int = 0
    var oldschool: Int = 0
    var hiphop: Int = 0
    var amekaji: Int = 0

    //상위 3개
    private lateinit var first: String
    private lateinit var second: String
    private lateinit var third: String

    //토큰
    private lateinit var token: String

    private lateinit var requestData: RecommendStyleRequest

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_my_style)
        checkIsFirstUser()
    }
    private fun checkIsFirstUser(){
        val isFirst = App.prefs.isFirst
        if(!isFirst!!) {
            startActivity(Intent(this, MainActivity::class.java))
            finish()
        }else{init()}
    }
    private fun init(){
        initMyStyleList()
        btn_my_style_default.setOnClickListener {
            rangeStyle()

            val intent = Intent(this,RecommendStyleActivity::class.java)
            startActivity(intent)
            finish()
        }
        btn_my_style_default.isEnabled = false
    }
    private fun initMyStyleList(){
        myStyleAdapter = MyStyleAdapter(this)

        recycler_my_style.apply {
            layoutManager = GridLayoutManager(this@MyStyleActivity,2)
            adapter = myStyleAdapter
            addItemDecoration(HorizontalItemDecorator(10))
            addItemDecoration(VerticalItemDecorator(10))
            setItemViewCacheSize(40)
        }
        token = App.prefs.local_login_token!!
        requestToServer.service.requestSurvey("application/json",token)
            .safeEnqueue(
                onSuccess = {
                    myStyleAdapter.data=it.surveyList
                    myStyleAdapter.notifyDataSetChanged()
                },
                onFail = { _, _ -> sendToast("서버 연결 오류")}
            )

    }

    private fun rangeStyle(){
        val styles = hashMapOf<String,Int>()
        styles.put("simple",simple)
        styles.put("street",street)
        styles.put("lovely",lovely)
        styles.put("modernchic",modernchic)
        styles.put("unique",unique)
        styles.put("formal",formal)
        styles.put("ethnic",ethnic)
        styles.put("sporty",sporty)
        styles.put("oldschool",oldschool)
        styles.put("hiphop",hiphop)
        styles.put("amekaji",amekaji)

        val result = styles.toList().sortedBy { (_, value) -> value }
        first = result.get(result.size-1).first
        second = result.get(result.size-2).first
        third = result.get(result.size-3).first

        requestData = RecommendStyleRequest(
            listOf(
                first,
                second,
                third
            )
        )
        requestToServer.service.requestRecommendStyle("application/json",token,requestData).enqueue(
            onFailure = {
                sendToast("실패")
            },
            onResponse = {
                if(it.isSuccessful){

                }
            }
        )
    }

    fun changeBtn(checked: Boolean){
        if(checked){
            btn_my_style_default.apply {
                isEnabled = true
                background = resources.getDrawable(R.drawable.btn_black)
                text = "다음"
            }
        }else {
            btn_my_style_default.apply {
                isEnabled = false
                background = resources.getDrawable(R.drawable.my_style_btn_grey_background)
                text = "3개 이상 선택해주세요"
            }
        }
    }
}
