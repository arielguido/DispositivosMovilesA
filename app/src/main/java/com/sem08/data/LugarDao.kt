package com.sem08.data

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.DocumentReference
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.FirebaseFirestoreSettings
import com.google.firebase.ktx.Firebase
import com.sem08.model.Lugar
import kotlin.math.log

class LugarDao {
  // Firebase vars
    private  var codigoUsuario:String
    private var firetore:FirebaseFirestore

    init {
        codigoUsuario = Firebase.auth.currentUser?.email.toString()
        firetore = FirebaseFirestore.getInstance()
        firetore.firestoreSettings = FirebaseFirestoreSettings.Builder().build()
    }


    fun getlugares(): MutableLiveData<List<Lugar>> {
        val listaLugares = MutableLiveData<List<Lugar>>()
        firetore
            .collection("lugarviernes")
            .document(codigoUsuario)
            .collection("mislugares")
            .addSnapshotListener{snapshot,e ->
                if (e!= null){
                    return@addSnapshotListener

                }
                if (snapshot != null){
                    val lista = ArrayList<Lugar>()
                    val lugares = snapshot.documents
                    lugares.forEach{
                        val lugar = it .toObject(Lugar::class.java)
                        if (lugar != null){
                            lista.add(lugar)
                        }
                    }
                    listaLugares.value = lista
                }

                }
        return listaLugares
            }




     fun guardarLugar(lugar: Lugar) {
         val document: DocumentReference
         if (lugar.id.isEmpty()) {
             document = firetore
                 .collection("lugarviernes")
                 .document(codigoUsuario)
                 .collection("mislugares")
                 .document()
             lugar.id = document.id

         } else {
             document = firetore
                 .collection("lugarviernes")
                 .document(codigoUsuario)
                 .collection("mislugares")
                 .document(lugar.id)
         }
         document.set(lugar)
             .addOnCompleteListener {
                 Log.d("guardarLugar", "guardado con exito")
             }
             .addOnCompleteListener {
                 Log.e("guardarLugar", "Error al guardar ")
             }
     }

         fun eliminarLugar(lugar: Lugar) {
             if (lugar.id.isEmpty()) {
                 firetore
                     .collection("lugarviernes")
                     .document(codigoUsuario)
                     .collection("mislugares")
                     .document(lugar.id)
                     .delete()
                     .addOnCompleteListener{
                         Log.d("eliminarLugar", "Eliminado con exito")
                     }
                     .addOnCompleteListener {
                         Log.e("eliminarLugar", "Error al eliminar ")
                     }
                     }
             }
}
