package com.example.kmmproject

interface Platform {
    val name: String
}

expect fun getPlatform(): Platform