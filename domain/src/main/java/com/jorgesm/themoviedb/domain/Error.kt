package com.jorgesm.themoviedb.domain




sealed interface Error {
    class Server(val code: Int): Error
    object Connectivity: Error
    class Unknown(val  message: String): Error
}

