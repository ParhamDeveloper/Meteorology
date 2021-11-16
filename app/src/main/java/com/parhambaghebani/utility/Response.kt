package com.parhambaghebani.utility

data class Response<out T>(val status: Status, val data: T?) {
    companion object {
        fun <T> success(data: T?): Response<T> {
            return Response(Status.SUCCESS, data)
        }

        fun <T> error(): Response<T> {
            return Response(Status.ERROR, null)
        }

        fun <T> loading(): Response<T> {
            return Response(Status.LOADING, null)
        }
    }
}