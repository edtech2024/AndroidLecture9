package com.example.myapplicationnine

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.myapplicationnine.databinding.BsdFragmentBinding
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import javax.inject.Inject


class BSDFragment : BottomSheetDialogFragment() {

    // Creates a new fragment given parameters
    // BSDFragment.newInstance()
    companion object {

        private const val TYPE = "Type"

        fun newInstance(type: Int?): BSDFragment {
            val fragmentBSD = BSDFragment()
            val args = Bundle()
            args.putInt(TYPE, type!!)
            fragmentBSD.arguments = args
            return fragmentBSD
        }

    }

    @Inject
    lateinit var listViewModel: ListViewModel

    private var _binding: BsdFragmentBinding? = null
    private val binding get() = _binding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        (activity?.application as ItemApplication).appComponent.inject(this)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = BsdFragmentBinding.inflate( inflater, container, false)
        _binding!!.lifecycleOwner = this
        if (::listViewModel.isInitialized) {
            _binding!!.listViewModel = listViewModel
        }
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
    }

}