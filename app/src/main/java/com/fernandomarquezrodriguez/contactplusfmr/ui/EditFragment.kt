package com.fernandomarquezrodriguez.contactplusfmr.ui

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import com.fernandomarquezrodriguez.contactplusfmr.R
import com.fernandomarquezrodriguez.contactplusfmr.databinding.FragmentEditBinding

class EditFragment  : Fragment(R.layout.fragment_edit) {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        FragmentEditBinding.bind(view).apply {



        }
    }
}