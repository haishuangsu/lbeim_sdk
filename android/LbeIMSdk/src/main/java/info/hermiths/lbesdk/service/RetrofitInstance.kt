package info.hermiths.lbesdk.service

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitInstance {
    // sit env： www.im-sit-dreaminglife.cn
    private const val BASE_URL = "http://www.im-sit-dreaminglife.cn/"
    // dev env： www.im-dreaminglife.cn
//     private const val BASE_URL = "http://www.im-dreaminglife.cn/"

    var IM_URL = ""
    var UPLOAD_BASE_URL = ""

    val baseApiService: ApiService by lazy {
        val retrofit =
            Retrofit.Builder().baseUrl(BASE_URL).addConverterFactory(GsonConverterFactory.create())
                .build()
        retrofit.create(ApiService::class.java)
    }

    val imApiService: LbeIMAPiService by lazy {
        val retrofit =
            Retrofit.Builder().baseUrl(IM_URL).addConverterFactory(GsonConverterFactory.create())
                .build()
        retrofit.create(LbeIMAPiService::class.java)
    }

    val uploadService: UploadService by lazy {
        val retrofit = Retrofit.Builder().baseUrl(UPLOAD_BASE_URL)
            .addConverterFactory(GsonConverterFactory.create()).build()
        retrofit.create(UploadService::class.java)
    }

    val downloadService: BaseService by lazy {
        val retrofit = Retrofit.Builder().baseUrl(UPLOAD_BASE_URL).build()
        retrofit.create(BaseService::class.java)
    }
}