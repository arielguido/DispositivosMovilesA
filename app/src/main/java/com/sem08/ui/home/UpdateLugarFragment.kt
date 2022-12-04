package com.sem08.ui.home

import android.app.AlertDialog
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.sem08.R
import com.sem08.databinding.FragmentUpdateLugarBinding
import com.sem08.model.Lugar
import com.sem08.viewModel.HomeViewModel
import io.grpc.NameResolver.Args

class UpdateLugarFragment : Fragment() {
    //recuperar elementos enviados

    private val args by navArgs<UpdateLugarFragmentArgs>()

    private var _binding: FragmentUpdateLugarBinding? = null
    private val binding get() = _binding!!
    private lateinit var homeViewModel: HomeViewModel


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        homeViewModel = ViewModelProvider(this).get(HomeViewModel::class.java)
        _binding = FragmentUpdateLugarBinding.inflate(inflater, container,false)

        //carga de lugar

        binding.etNombre.setText(args.lugarArg.nombre)
        binding.etCorreoLugar.setText(args.lugarArg.correo)
        binding.etTelefono.setText(args.lugarArg.telefono)
        binding.etWeb.setText(args.lugarArg.web)

        binding.btUpdateLugar.setOnClickListener { updateLugar() }

        // Inflate the layout for this fragment
        return binding.root
    }

    private fun updateLugar() {

        val nombre = binding.etNombre.text.toString()
        val correo = binding.etCorreoLugar.text.toString()
        val telefono = binding.etTelefono.text.toString()
        val web = binding.etWeb.text.toString()

        if (nombre.isNotEmpty()) {
            val lugar = Lugar(args.lugarArg.id,nombre, correo, telefono, web,args.lugarArg.rutaAudio, args.lugarArg.rutaImagen)
            homeViewModel.guardarLugar(lugar)
            Toast.makeText(requireContext(), getText(R.string.ms_UpdateLugar), Toast.LENGTH_LONG)
                .show()
            findNavController().navigate(R.id.action_updateLugarFragment_to_nav_home)

        } else {
            Toast.makeText(requireContext(), getString(R.string.ms_FaltaValores), Toast.LENGTH_LONG)
                .show()
        }
    }
    //private fun eliminarLugar() {
    //  val builder = AlertDialog.Builder(requireContext())
    //  builder.setTitle(getString(R.string.bt_delete_lugar))
    //  builder.setMessage(getString(R.string.msg_seguro_borrado)+" ${args.lugarArg.nombre}?")
    //  builder.setNegativeButton(getString(R.string.msg_no)) {_,_ -> }
    //  builder.setPositiveButton(getString(R.string.msg_si)) {_,_ ->
    //      lugarViewModel.eliminarLugar(args.lugarArg)
    //      Toast.makeText(requireContext(),
    //          getString(R.string.msg_lugar_deleted),
    //          Toast.LENGTH_SHORT).show()
    //      findNavController().navigate(R.id.action_updateLugarFragment_to_nav_home)
    //  }

    //  builder.create().show()
    //}
}
