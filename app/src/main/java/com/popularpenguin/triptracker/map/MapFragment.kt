package com.popularpenguin.triptracker.map

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.popularpenguin.triptracker.R

class MapFragment: Fragment() {

    companion object {
        fun newInstance(): MapFragment {
            val fragment = MapFragment()

            return fragment
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_map, container, false)

        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        Toast.makeText(requireContext(), "In map fragment", Toast.LENGTH_LONG)
            .show()
    }
}