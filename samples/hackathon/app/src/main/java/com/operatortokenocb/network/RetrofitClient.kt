package com.operatortokenocb.network

class RetrofitClient : BaseRetrofitClient() {

    companion object {
        private const val RECOMMENDER_URL = "http://lbl-recommender.superdtaglb.cf/debug/"
    }

    override fun recommenderUrl(): String {
        return RECOMMENDER_URL
    }
}
