package com.example.pictureoftheday.settings

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.*
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.example.pictureoftheday.*
import com.example.pictureoftheday.databinding.FragmentSettingsBinding
import com.example.pictureoftheday.view.BottomNavigationDrawerFragment
import com.google.android.material.tabs.TabLayout

class SettingsFragment : Fragment(), View.OnClickListener {

    var isMain = true
    private var _binding: FragmentSettingsBinding? = null
    private val binding: FragmentSettingsBinding
        get() = _binding!!

    private lateinit var parentActivity:MainActivity

    override fun onAttach(context: Context) {
        super.onAttach(context)
        parentActivity = context as MainActivity
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        _binding = FragmentSettingsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        inflater.inflate(R.menu.menu_bottom_bar, menu)
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.buttonThemeOne.setOnClickListener(this)
        binding.buttonThemeTwo.setOnClickListener(this)
        binding.buttonThemeThree.setOnClickListener(this)

        when(parentActivity.getCurrentTheme()){
            1->binding.radioGroup.check(R.id.button_theme_one)
            2->binding.radioGroup.check(R.id.button_theme_two)
            3->binding.radioGroup.check(R.id.button_theme_three)
        }

    }

    override fun onClick(v: View) {
        when(v.id){
            R.id.button_theme_one->{
              parentActivity.setCurrenyTheme(ThemeOne)
                parentActivity.recreate()
            }
            R.id.button_theme_two->{
                parentActivity.setCurrenyTheme(ThemeSecond)
                parentActivity.recreate()
            }
            R.id.button_theme_three->{
                parentActivity.setCurrenyTheme(ThemeThree)
                parentActivity.recreate()
            }

        }
    }

    override fun onDestroy() {
        _binding = null
        super.onDestroy()
    }

    companion object {
        @JvmStatic
        fun newInstance() = SettingsFragment()
    }


}