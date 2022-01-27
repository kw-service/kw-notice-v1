package dev.yjyoon.kwnotice.view.notice.kwhome

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import dev.yjyoon.kwnotice.data.remote.api.Config.Companion.BASE_URL
import dev.yjyoon.kwnotice.data.remote.api.RetrofitService
import dev.yjyoon.kwnotice.data.remote.model.KwHomeNotice
import dev.yjyoon.kwnotice.databinding.FragmentKwHomeNoticeBinding
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory


class KwHomeNoticeFragment : Fragment() {

    private val binding by lazy { FragmentKwHomeNoticeBinding.inflate(layoutInflater) }

    private var noticeList: List<KwHomeNotice>? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        fetchNotices()

        binding.kwHomeNoticeList.layoutManager = LinearLayoutManager(activity)
        binding.kwHomeNoticeList.addItemDecoration(DividerItemDecoration(context, 1))

        return binding.root
    }

    private fun fetchNotices() {

        val retrofit = Retrofit.Builder().baseUrl(BASE_URL).addConverterFactory(GsonConverterFactory.create()).build()

        val retrofitService = retrofit.create<RetrofitService>(RetrofitService::class.java)

        val callGetKwHomeNoticeList = retrofitService.getKwHomeNoticeList()

        callGetKwHomeNoticeList.enqueue(object : Callback<List<KwHomeNotice>>{
            override fun onResponse(
                call: Call<List<KwHomeNotice>>,
                response: Response<List<KwHomeNotice>>
            ) {

                if(response.isSuccessful) binding.kwHomeNoticeList.adapter = KwHomeNoticeListAdapter(response.body()!!)
                else Toast.makeText(activity, "광운대학교 홈페이지 공지사항을 불러올 수 없습니다\n" + response.message(), Toast.LENGTH_LONG).show()

            }
            override fun onFailure(call: Call<List<KwHomeNotice>>, t: Throwable) {
                Toast.makeText(activity, "네트워크 연결 상태를 확인해주세요\n" + t.message, Toast.LENGTH_LONG).show()
            }
        })
    }
}