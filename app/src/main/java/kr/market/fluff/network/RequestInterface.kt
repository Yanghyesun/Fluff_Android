package kr.market.fluff.network



import com.facebook.login.Login
import kr.market.fluff.data.intro.ResponseLogin
import kr.market.fluff.data.intro.ResponseValidateAndRegisterAndLogin
import kr.market.fluff.data.myStyle.MyStyleResponse
import kr.market.fluff.data.myStyle.RecommendStyleData
import kr.market.fluff.data.myStyle.RequestRecommendStyle
import org.json.JSONArray
import retrofit2.Call
import retrofit2.http.*

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


    @POST("/auth/signUp")
    fun requestRegister(@Body body: RegisterRequest) : Call<BaseResponse<RegisterResponse>> //validate해서 받는 데이터의 형식.

    data class RegisterRequest(
        val email : String,
        val username : String,
        val pwd : String,
        val gender : String
    )
    data class RegisterResponse(
        val message : String
    )

    @GET("magazine/")
    fun request_magazine() : Call<BaseResponseJson<MagazineResponse>>
    data class MagazineResponse(
        val magazine_data : ArrayList<MagazineThumbnail>
    )
    data class MagazineThumbnail(
        val imgUrl : String,
        val _id : String
    )

    @GET("/follow/followList")
    fun request_follow_list() : Call<BaseResponseJson<FollowResponse>>
    data class FollowResponse(
        val _id : String
    )

    @GET("/cart")
    fun request_cart_list() : Call<BaseResponseJson<ArrayList<CartListObject>>>
    data class CartListObject(
        val userName : String,
        val Img : String,
        val goodsId : String,
        val goodsName : String,
        val price : Long
    )

    //TODO 아래 부분 다 수정 필요!
    @FormUrlEncoded
    @POST("/cart")
    fun request_cart_add(
        @Field("goodsIdList")goodsIdList : ArrayList<GoodsAddRequest>
    ) : Call<BaseResponseJson<ArrayList<String>>>
    data class GoodsAddRequest(
        val _id : String
    )

    @DELETE("/cart")
    fun request_cart_delete(
        @Header("Content-Type") content_type : String,
        @Header("x-access-token") token :String,
        @Body body: CartDeleteRequest
    ) : Call<BaseResponseJson<CartDeleteResponse>>
//    request_cart_delete("application/json",token,body)
    data class CartDeleteRequest(
        val deleteId : ArrayList<String>
    )
    data class CartDeleteResponse(
        val data : String
    )

    @POST("order/goodsList")
    fun request_order_add() : Call<BaseResponseJson<AddOrderListResponse>>
    data class AddOrderListResponse(
        val data : String
    )

    @GET("order/goodsList")
    fun request_order_confirm() : Call<BaseResponseJson<ConfirmOrderResponse>>
    data class ConfirmOrderResponse(
        val data : String
    )




    /*
    //회원가입시 이메일 중복체크
    @FormUrlEncoded
    @POST("auth/validate")
    fun requestRegister_appjam(
        @Field("email") userID : String,
    ) : Call<ResponseValidation>


    //회원가입시 데이터 제출
    @FormUrlEncoded
    @POST("auth/signUp")
    fun requestRegister_appjam(
        @Field("email") userID : String,
        @Field("pwd") pwd : String,
        @Field("nickname") nickname : String,
        @Field("gender") gender : String
    ) : Call<ResponseRegister>


    //



     *
     */
}