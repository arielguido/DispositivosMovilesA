package com.sem08.viewModel

import android.app.Application
import androidx.lifecycle.*
import com.sem08.data.LugarDao
import com.sem08.model.Lugar
import com.sem08.repository.LugarRepository



class HomeViewModel(application: Application) : AndroidViewModel(application) {
    private var repository: LugarRepository = LugarRepository(LugarDao())
    val obtenerLugares: MutableLiveData<List<Lugar>>


    init {

        obtenerLugares = repository.obtenerLugares

    }

    fun guardarLugar(lugar: Lugar) {
     repository.guardarLugar(lugar)
    }

    fun eliminarLugar(lugar: Lugar) {
       repository.eliminarLugar(lugar)

    }
}