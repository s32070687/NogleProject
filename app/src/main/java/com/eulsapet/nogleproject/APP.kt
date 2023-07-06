package com.eulsapet.nogleproject

import android.app.Application
import android.content.Context

class APP: Application() {
    companion object {
        private lateinit var context: Application

        fun getContext(): Context {
            return context
        }
    }

    override fun onCreate() {
        super.onCreate()
        context = this
    }
}