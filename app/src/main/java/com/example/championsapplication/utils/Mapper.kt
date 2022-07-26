package com.example.championsapplication.utils

interface Mapper<I, O> {
    fun map(input: I): O
}