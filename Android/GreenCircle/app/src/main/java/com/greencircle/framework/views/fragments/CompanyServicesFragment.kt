package com.greencircle.framework.views.fragments

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.greencircle.R
import com.greencircle.databinding.FragmentCompanyServicesBinding
import com.greencircle.domain.model.ServiceItem
import com.greencircle.framework.ui.adapters.ServiceAdapter
import com.greencircle.framework.viewmodel.CompanyServicesViewModel

/*
* Fragmento que muestra los servicios de la empresa
*/
class CompanyServicesFragment : Fragment() {

    private var _binding: FragmentCompanyServicesBinding? = null

    private val binding get() = _binding!!
    private lateinit var viewModel: CompanyServicesViewModel
    private lateinit var adapter: ServiceAdapter
    private lateinit var recyclerView: RecyclerView

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        viewModel = ViewModelProvider(this)[CompanyServicesViewModel::class.java]

        _binding = FragmentCompanyServicesBinding.inflate(inflater, container, false)
        val root: View = binding.root
        initializeComponents(root)

        val data = viewModel.createMOckServicesList()
        initializeObservers()
        Log.d("Salida", data.results.toString())
        setUpRecyclerView(data.results)

        return root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    /*
    * Inicializa los componentes de la vista
    *
    * @param root Vista raíz
     */
    private fun initializeComponents(root: View) {
        recyclerView = root.findViewById(R.id.RVServices)
    }

    /*
    * Inicializa los observadores de la vista
     */
    private fun initializeObservers() {
        viewModel.servicesObjectLiveData.observe(viewLifecycleOwner) { servicesObject ->
            Log.d("CompanyServicesFragment", "Observer called")
            Log.d("CompanyServicesFragment", servicesObject.toString())
            setUpRecyclerView(servicesObject.results)
        }
    }

    /*
    * Inicializa el RecyclerView con los datos mediante el adaptador
    *
    * @param dataForList Datos para el RecyclerView
     */
    private fun setUpRecyclerView(dataForList: ArrayList<ServiceItem>) {
        Log.d("CompanyServicesFragment", "Setting up RecyclerView with data: $dataForList")
        recyclerView.setHasFixedSize(true)
        recyclerView.layoutManager = LinearLayoutManager(requireContext())
        adapter = ServiceAdapter() // Initialize the adapter
        adapter.initServiceAdapter(dataForList, requireContext())
        recyclerView.adapter = adapter
    }
}