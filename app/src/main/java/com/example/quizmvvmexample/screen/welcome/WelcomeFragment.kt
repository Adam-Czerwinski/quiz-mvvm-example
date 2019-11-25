package com.example.quizmvvmexample.screen.welcome


import android.os.Bundle
import android.text.TextWatcher
import android.util.Log
import android.view.*
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.Fragment
import androidx.databinding.DataBindingUtil
import androidx.navigation.findNavController
import androidx.navigation.ui.NavigationUI
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.quizmvvmexample.R
import com.example.quizmvvmexample.databinding.WelcomeFragmentBinding
import kotlinx.android.synthetic.main.fragment_scores.*


class WelcomeFragment : Fragment() {

    private lateinit var binding: WelcomeFragmentBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(
            inflater,
            R.layout.welcome_fragment,
            container,
            false
        )

        binding.buttonStart.setOnClickListener {
            it.findNavController().navigate(WelcomeFragmentDirections.actionWelcomeFragmentToQuizFragment(binding.name.text.toString()))
        }

        binding.name.addTextChangedListener{
            binding.buttonStart.isEnabled = it!!.isNotEmpty()
        }

        binding.scoresButton.setOnClickListener{
            it.findNavController().navigate(WelcomeFragmentDirections.actionWelcomeFragmentToScoresFragment())
        }

        binding.addButton.setOnClickListener{
            it.findNavController().navigate(WelcomeFragmentDirections.actionWelcomeFragmentToAddQuestion())
        }

        setHasOptionsMenu(true)
        return binding.root
    }

    override fun onResume() {
        super.onResume()
        binding.name.text.clear()

    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        inflater.inflate(R.menu.options_menu, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return NavigationUI.onNavDestinationSelected(item!!, view!!.findNavController()) || super.onOptionsItemSelected(item)
    }

}
