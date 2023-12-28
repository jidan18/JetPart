package com.part.computer.di

import com.part.computer.data.PartRepository


object Injection {
    fun provideRepository(): PartRepository {
        return PartRepository.getInstance()
    }
}