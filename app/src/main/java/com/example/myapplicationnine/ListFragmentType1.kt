package com.example.myapplicationnine

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.domain.Item
import com.example.myapplicationnine.databinding.FragmentListT1Binding
import kotlinx.coroutines.launch


class ListFragmentType1 : Fragment() {

    // Creates a new fragment given parameters
    // ListFragmentType1.newInstance()
    companion object {

        private const val TYPE = "Type"

        fun newInstance(type: Int?): ListFragmentType1 {
            val fragmentListT1 = ListFragmentType1()
            val args = Bundle()
            args.putInt(TYPE, type!!)
            fragmentListT1.arguments = args
            return fragmentListT1
        }
    }

    // Define the events that the fragment will use to communicate
    interface OnItemEditType1Listener{
        // This can be any number of events to be sent to the activity
        fun onEditItemType1(edit: Bundle)
    }

    // Define the listener of the interface type
    // listener will the activity instance containing fragment
    private var listenerEdit: OnItemEditType1Listener? = null

    private lateinit var itemAdapterType1: ItemAdapter

    val listViewModel: ListViewModel by viewModels( ownerProducer = { requireParentFragment() })

    private var _binding: FragmentListT1Binding? = null
    private val binding get() = _binding!!

    override fun onAttach(context: Context) {
        super.onAttach(context)
        listenerEdit = context as OnItemEditType1Listener
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        (activity?.application as ItemApplication).appComponent.inject(this)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?,
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentListT1Binding.inflate( inflater, container, false)
        _binding!!.lifecycleOwner = this
        _binding!!.listViewModel = listViewModel
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.rvListType1.layoutManager = LinearLayoutManager(this.context)
        this.itemAdapterType1 = ItemAdapter(
            { item -> onItemClicked(item) },
            { item -> onButtonClicked(item) }
        )
        binding.rvListType1.adapter = itemAdapterType1
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
    }

    override fun onDetach() {
        super.onDetach()
        listenerEdit  = null
    }

    // Now we can fire the event when the user selects something in the fragment
    private fun onEditClicked(bundle: Bundle){
        listenerEdit?.onEditItemType1(bundle)
    }

    private fun onItemClicked(item: Item){

        val args = Bundle()
        args.putString("Action", "Edit")
        args.putInt("Id", item.id!!)
        args.putString("Title", item.title!!.toString())
        args.putString("Description", item.description!!.toString())
        args.putString("Priority", item.priority!!.toString())
        args.putString("Type", item.type!!.toString())
        args.putString("Count", item.count!!.toString())
        args.putString("Frequency", item.frequency!!.toString())
        args.putString("Uid", item.uid!!.toString())
        args.putString("Date", item.date!!.toString())
        args.putString("Done_dates", item.done_dates!!.toString())
        args.putString("Color", item.color!!.toString())

        onEditClicked(args)
    }

   private fun onButtonClicked(item: Item){
       // Create a new coroutine in the lifecycleScope
       viewLifecycleOwner.lifecycleScope.launch {
           // repeatOnLifecycle launches the block in a new coroutine every time the
           // lifecycle is in the STARTED state (or above) and cancels it when it's STOPPED.
           viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
               // Trigger the flow and start listening for values.
               // This happens when lifecycle is STARTED and stops
               // collecting when the lifecycle is STOPPED
               listViewModel.itemDone(item)

               var warning: String = ""
               var counterClicks: Int = item.count?.minus(1)!!
                   
               if ( counterClicks > 0) {
                   warning = "Можете выполнить еще $counterClicks раз"
               } else {
                   warning = "Хватит это делать"
               }
               var toast: Toast = Toast.makeText(activity?.applicationContext, "$warning", Toast.LENGTH_SHORT)
               toast.show()
           }
       }
   }

}