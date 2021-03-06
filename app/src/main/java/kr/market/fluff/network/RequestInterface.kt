package kr.market.fluff.network



import android.os.Parcelable
import com.facebook.login.Login
import kr.market.fluff.data.detail.DetailProductData
import com.google.gson.annotations.SerializedName
import kr.market.fluff.data.FilterRequest
import kr.market.fluff.data.FilterResponse
import kotlinx.android.parcel.Parcelize
import kr.market.fluff.data.intro.ResponseLogin
import kr.market.fluff.data.intro.ResponseValidateAndRegisterAndLogin
import kr.market.fluff.data.myStyle.*
import kr.market.fluff.data.mypage.ToSellerResponse
//import kr.market.fluff.data.mypage.ToSellerRequest
//import kr.market.fluff.data.mypage.ToSellerResponse
import okhttp3.MultipartBody
import org.json.JSONArray
import retrofit2.Call
import retrofit2.http.*
import java.time.LocalDateTime

interface RequestInterface {
    //회원가입 화면의 중복확인 부분
//    @FormUrlEncoded
//    @POST("UserValidate.php")
//    fun getValidation(
//        @Field("userID")userID : String//userID에 String 타입 userID 값 전달.
//    ) : Call<ResponseValidateAndRegisterAndLogin> //validate해서 받는 데이터의 형식.
    //패스워드 암호화 고려해서 MySQL의 행 길이를 더 길게 해줘야 함!!!★★★★★★★★★★★★★
//    @FormUrlEncoded
//    @POST("UserRegister.php")
//    fun requestRegister(
//        @Field("userID") userID : String,
//        @Field("userPassword")userPassword : String,
//        @Field("userPhone")userPhone : String
//    ) : Call<ResponseValidateAndRegisterAndLogin>
//
//    @FormUrlEncoded
//    @POST("UserLogin.php")
//    fun requestLogin(
//        @Field("userID") userID : String,
//        @Field("userPassword")userPassword : String
//    ) : Call<ResponseValidateAndRegisterAndLogin>

    @POST("/auth/login")
    fun requestLogin(@Body body: LoginRequest) : Call<BaseResponse<LoginResponse>> //validate해서 받는 데이터의 형식.
    //@Body-> 객체를 전달 , @Field 사용시 @FormUrlEncoded
    // @Serialized -> Respones 단계시


    @GET("/survey")
    fun requestSurvey(
        @Header("Content-Type") content_type: String,
        @Header("x-access-token") token: String
    ): Call<BaseResponse<MyStyleResponse>>

    @FormUrlEncoded
    @POST("/auth/checkUsername")
    fun requestNickNameValidate(
        @Field("username")username : String
    ):Call<BaseResponse<CheckNickResponse>>

    data class CheckNickResponse(
        val username : String,
        val duplication: Boolean
    )

    @Multipart
    @POST("/shopper/toSeller")
    fun requestToSeller(
        @Header("x-access-token") token: String,
        @Part image : MultipartBody.Part
    ): Call<BaseResponse<List<ToSellerResponse>>>

    @PUT("/recommend/keyDB")
    fun requestRecommendStyle(
        @Header("Content-Type") content_type: String,
        @Header("x-access-token") token: String,
        @Body body: RecommendStyleRequest
    ):Call<RecommendStyleResponse>

    @GET("/recommend/seller")
    fun requestRecommendSeller(
        @Header("Content-Type") content_type: String,
        @Header("x-access-token") token: String,
        @Query("page") page: Int
    ):Call<BaseResponse<ArrayList<RecommendSellerResponse>>>

    @POST("/goods/filter")
    fun requestFilter(
        @Header("Content-Type") content_type: String,
        @Header("x-access-token") token: String,
        @Body body: FilterRequest
    ): Call<BaseResponse<List<FilterResponse>>>


    data class LoginRequest(
        val email: String,
        val pwd: String
    )

    data class LoginResponse(
        val token: String,
        val refresh: String
    )

    //로그인 중복확인 부분
    @POST("/auth/checkEmail")
    fun requestValidate(@Body body:LoginValidateRequest ) : Call<BaseResponse<LoginValidateResponse>>

    data class LoginValidateRequest(
        val email : String
    )

    data class LoginValidateResponse(
        val email : String,
        val duplication : Boolean
    )

    @GET("/goods/{goodsId}")
    fun requestProductDetail(
        @Path("goodsId")goodsId: String,
        @Header("Content-Type") content_type: String,
        @Header("x-access-token") token: String
    ): Call<BaseResponse<DetailProductResponse>>
    data class DetailProductResponse(
        val mainImg: String,
        val img : ArrayList<String>,
        val size : String,
        val condition : Int,
        val comment : String,
        val _id : String,
        val sellerName : String,
        val sellerId : DetailSellerResponse
    )

    data class DetailSellerResponse(
        @SerializedName("_id")
        val sellerId: String,
//        val style :ArrayList<String>,
//        val saleList : ArrayList<String>,
        val grade : Int,
        val sellerImg : String
    )


    @GET("/auction/auctionList")
    fun requestAuctionLIst(
        @Header("Content-Type") content_type: String,
        @Header("x-access-token") token: String
    ):Call<BaseResponse<ArrayList<AuctionItemData>>>

    @Parcelize
    data class AuctionItemData(
        val mainImg : String,
        val auctionName : String,
        val size : String,
        val comment : String,
        val condition : Int,
        val bid : Long,
        val _id : String
    ):Parcelable


    //배포되면 이놈으로 바꾸기
//    data class DetailSellerResponse(
//        val email : String,
//        val pwd : String,
//        val username : String,
//        val gender : String,
//        val address : String,
//        val phone : String,
//        @SerializedName("_id")
//        val sellerId: String,
//        val style :ArrayList<String>,
//        val saleList : ArrayList<String>,
//        val sellerImg : String
//    )




    @POST("/auth/directSignUp")
    fun requestRegister(@Body body: RegisterRequest) : Call<BaseResponse<RegisterResponse>> //validate해서 받는 데이터의 형식.

    data class RegisterRequest(
        val email : String,
        val username : String,
        val pwd : String,
        val gender : String
    )
    data class RegisterResponse(
        val email : String,
        val username : String,
        val pwd : String,
        val gender : String
    )


    @GET("/magazine")
    fun request_magazine(
        @Header("Content-Type") content_type: String,
        @Header("x-access-token") token: String
    ) : Call<BaseResponse<List<MagazineResponse>>>

    data class MagazineResponse(
        val imgUrl : String,
        val _id : String
    )

    // 구현 필요
    @GET("/follow/followList")
    fun request_follow_list() : Call<BaseResponse<FollowResponse>>
    data class FollowResponse(
        val sellerId : String
    )

    // 구현 필요 - 장바구니 목록 불러오기 - CartActivity
    @GET("/cart")
    fun request_cart_list(
        @Header("Content-Type") content_type : String,
        @Header("x-access-token") token :String
    ) : Call<BaseResponse<ArrayList<CartListResponse>>>

    @Parcelize
    data class CartListResponse(
        val userName : String,
        val Img : ArrayList<String>,
        val goodsId : String,
        val goodsName : String,
        val price : Long
    ):Parcelable

    @POST("/cart")
    fun request_cart_add(
        @Header("Content-Type") content_type : String,
        @Header("x-access-token") token :String,
        @Body body: RequestCartAdd
    ) : Call<BaseResponse<ArrayList<ResponseCartAdd>>>
    data class RequestCartAdd(
        val cartId : String
    )
    data class ResponseCartAdd(
        val _id : String
    )


    @HTTP(method = "DELETE",hasBody = true,path = "/cart")
    fun request_cart_delete(
        @Header("Content-Type") content_type : String,
        @Header("x-access-token") token :String,
        @Body body: CartDeleteRequest
    ) : Call<BaseResponse<ArrayList<CartDeleteResponse>>>
    data class CartDeleteRequest(
        val deleteId : ArrayList<String>
    )
    data class CartDeleteResponse(
        val _id : String
    )

    @POST("order/goodsList")
    fun request_order_add(
        @Header("Content-Type") content_type : String,
        @Header("x-access-token") token :String,
        @Body body: RequestOrderedGoodsList
    ) : Call<BaseResponse<ArrayList<AddOrderListResponse>>>


    data class RequestOrderedGoodsList(
        val orderList:ArrayList<String>
    )
    data class AddOrderListResponse(
        val _id: String
    )

    // 구현 필요 - 최종 주문결과 확인(조회) - PurchaseCompleteActivity
    @GET("order/goodsList")
    fun request_order_confirm(
        @Header("Content-Type") content_type : String,
        @Header("x-access-token") token :String
    ) : Call<BaseResponse<ArrayList<ConfirmOrderResponse>>>
    data class ConfirmOrderResponse(
        val sellerName : String,
        val img : ArrayList<String>,
        val goodsName :String,
        val price : Long
    )

    @GET("/recommend/style")
    fun request_recommend_home(
        @Header("Content-Type") content_type : String,
        @Header("x-access-token") token :String
    ) : Call<BaseResponse<ArrayList<HomeDetailData>>>


    data class HomeDetailData(
        @SerializedName("goodsName")
        val closet : String,
        @SerializedName("mainImg")
        val img : String,
        @SerializedName("sellerName")
        val seller : String,
        @SerializedName("price")
        val price : Long,
        @SerializedName("_id")
        val closetId : String,
        @SerializedName("like")
        val like : Boolean,
        @SerializedName("sellerId")
        val sellerId : String
    )

    //홈화면 리사이클러뷰 7개 데이터
    @GET("/recommend/style")
    fun request_home_Thumbnail(
        @Header("Content-Type") content_type : String,
        @Header("x-access-token") token :String,
        @Query("page")  page : Int
    ) : Call<BaseResponse<ArrayList<HomeDetailData>>>


    //최신순 goods데이터
    @GET("/goods")
    fun request_home_Newest(
        @Header("Content-Type") content_type : String,
        @Header("x-access-token") token :String,
        @Query("sort") sort : String,
        @Query("page")  page : Int
    ) : Call<BaseResponse<ArrayList<HomeDetailData>>>

    //카테고리별 goods데이터
    @GET("/goods")
    fun request_home_Category(
        @Header("Content-Type") content_type : String,
        @Header("x-access-token") token :String,
        @Query("category") category : String,
        @Query("page")  page : Int
    ) : Call<BaseResponse<ArrayList<HomeDetailData>>>

    //좋아요 누르기/ 취소
    @POST("/goods/{goodId}/like")
    fun request_product_like(
        @Header("Content-Type") content_type : String,
        @Header("x-access-token") token :String,
        @Path("goodId") goodId: String,
        @Body body: RequestLikeData
    ) : Call<BaseResponse<ResponseLikeData>>
    data class ResponseLikeData(
        val goodsId : String,
        val state : Boolean
    )
    data class RequestLikeData(
        val like : Boolean
    )

    @GET("/goods/seller/{sellerId}")
    fun request_seller_product(
        @Header("Content-Type") content_type : String,
        @Header("x-access-token") token :String,
        @Path("sellerId") sellerId: String
    ): Call<BaseResponse<ArrayList<ResponseSellerData>>>
    data class ResponseSellerData(
        @SerializedName("_id")
        val goodsId : String,
        val goodsName: String,
        val mainImg: String,
        val price : Long
    )

}