package com.mcdonalds.mcquizapp

interface Platform {
    val name: String
}

expect fun getPlatform(): Platform