package com.example.pictureoftheday.view

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.*
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import coil.load
import com.example.pictureoftheday.MainActivity
import com.example.pictureoftheday.R
import com.example.pictureoftheday.databinding.FragmentPictureOfTheDayMainBinding
import com.example.pictureoftheday.repository.day.PictureOfTheDayResponseDate
import com.example.pictureoftheday.settings.SettingsFragment
import com.example.pictureoftheday.viewmodel.PictureOfTheDayAppState
import com.example.pictureoftheday.viewmodel.PictureOfTheDayViewModel
import com.google.android.material.bottomappbar.BottomAppBar
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.snackbar.Snackbar
import com.google.android.material.tabs.TabLayout


class PictureOfTheDayFragment: Fragment() {

    var isMain = true
    private var _binding: FragmentPictureOfTheDayMainBinding? = null
    private val binding: FragmentPictureOfTheDayMainBinding
        get() = _binding!!


    private val KEY_SP = "sp"
    private val KEY_CURRENT_THEME_LOCAL = "current_theme_local"

    private var callBackOnErrorLoad = object : CallbackFragment {
        override fun onError(messageError: String) {
            context?.let {
                Snackbar.make(it, binding.mainStart, messageError, Snackbar.LENGTH_SHORT).show()
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
//        setRetainInstance(true)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        // Inflate the layout for this fragment
        _binding = FragmentPictureOfTheDayMainBinding.inflate(inflater, container, false)
        return binding.root
    }

    private val viewModel: PictureOfTheDayViewModel by lazy {
        ViewModelProvider(this).get(PictureOfTheDayViewModel::class.java)
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        inflater.inflate(R.menu.menu_bottom_bar, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {

        when (item.itemId) {
            R.id.app_bar_fav -> {
                Log.d("@@@", "app_bar_fav")
            }
            R.id.app_bar_settings -> {
                Log.d("@@@", "app_bar_settings")
                requireActivity().supportFragmentManager.beginTransaction().replace(R.id.container, SettingsFragment.newInstance()) .apply {
                    this.commit()
                    this.addToBackStack("")
                }


                // TODO HW addToBAckstack
            }
            android.R.id.home -> {
                BottomNavigationDrawerFragment.newInstance()
                    .show(requireActivity().supportFragmentManager, "")
            }
        }
        return super.onOptionsItemSelected(item)


    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.getLiveData().observe(viewLifecycleOwner, Observer {
            renderData(it)
        })
        viewModel.sendRequest(callBackOnErrorLoad)

        binding.inputLayout.setEndIconOnClickListener {
            startActivity(Intent(Intent.ACTION_VIEW).apply {
                data =
                    Uri.parse("https://en.wikipedia.org/wiki/${binding.inputEditText.text.toString()}")
            })
        }

        val bottomSheetBehavior = BottomSheetBehavior.from(binding.lifeHackMain.bottomSheetContainer)
        bottomSheetBehavior.isFitToContents = false
        bottomSheetBehavior.state = BottomSheetBehavior.STATE_HALF_EXPANDED
        bottomSheetBehavior.addBottomSheetCallback(object :
            BottomSheetBehavior.BottomSheetCallback() {
            override fun onStateChanged(bottomSheet: View, newState: Int) {
                when (newState) {
                    BottomSheetBehavior.STATE_DRAGGING -> {}
                    BottomSheetBehavior.STATE_COLLAPSED -> {}
                    BottomSheetBehavior.STATE_EXPANDED -> {}
                    BottomSheetBehavior.STATE_HALF_EXPANDED -> {}
                    BottomSheetBehavior.STATE_HIDDEN -> {}
                    BottomSheetBehavior.STATE_SETTLING -> {}
                }
            }

            override fun onSlide(bottomSheet: View, slideOffset: Float) {
                Log.d("@@@", "$slideOffset")

            }

        })

        (requireActivity() as MainActivity).setSupportActionBar(binding.bottomAppBar)
        setHasOptionsMenu(true)

        binding.fab.setOnClickListener {
            if (isMain) {
                binding.bottomAppBar.navigationIcon = null
                binding.bottomAppBar.fabAlignmentMode = BottomAppBar.FAB_ALIGNMENT_MODE_END
                binding.fab.setImageDrawable(
                    ContextCompat.getDrawable(
                        requireContext(),
                        R.drawable.ic_back_fab
                    )
                )
                binding.bottomAppBar.replaceMenu(R.menu.menu_button_appbar)
                // TODO HW  binding.bottomAppBar.replaceMenu(// R.menu. какое-то другое меню)
            } else {
                binding.bottomAppBar.navigationIcon = (ContextCompat.getDrawable(
                    requireContext(),
                    R.drawable.ic_hamburger_menu_bottom_bar
                ))
                binding.bottomAppBar.fabAlignmentMode = BottomAppBar.FAB_ALIGNMENT_MODE_CENTER
                binding.fab.setImageDrawable(
                    ContextCompat.getDrawable(
                        requireContext(),
                        R.drawable.ic_plus_fab
                    )
                )
                binding.bottomAppBar.replaceMenu(R.menu.menu_bottom_bar)
                // TODO HW binding.bottomAppBar.replaceMenu(R.menu.menu_bottom_bar)
            }
            isMain = !isMain
        }

        initTabLayout()
    }

    private fun initTabLayout() {
        binding.tabLayoutDay.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {
            override fun onTabSelected(tab: TabLayout.Tab?) {
                when (tab?.position) {
                    0 -> {
                        viewModel.sendRequestToday(callBackOnErrorLoad)
                    }
                    1 -> {
                        viewModel.sendRequestYT(callBackOnErrorLoad)
                    }
                    2 -> {
                        viewModel.sendRequestTDBY(callBackOnErrorLoad)
                    }
                    else -> {viewModel.sendRequestToday(callBackOnErrorLoad)}
                }
                Toast.makeText(requireContext(), "${tab?.position}", Toast.LENGTH_SHORT).show()
            }

            override fun onTabUnselected(tab: TabLayout.Tab?) {
                //TODO("Not yet implemented")
            }

            override fun onTabReselected(tab: TabLayout.Tab?) {
                //TODO("Not yet implemented")
            }
        })
    }


    private fun renderData(pictureOfTheDayAppState: PictureOfTheDayAppState) {
        when (pictureOfTheDayAppState) {
            is PictureOfTheDayAppState.Error -> {
                binding.imageView.load("file:///android_asset/ic_stat_rocket.xml")

                binding.imageView.visibility = View.VISIBLE
                binding.loadingLayout.visibility = View.GONE

                binding.lifeHackMain.explanation.text = pictureOfTheDayAppState.error
            }
            is PictureOfTheDayAppState.Loading -> {
                binding.imageView.visibility = View.GONE
                binding.loadingLayout.visibility = View.VISIBLE
            }
            is PictureOfTheDayAppState.Success -> {
//                binding.imageView.load(pictureOfTheDayAppState.pictureOfTheDayResponseData.url) {
//                    // TODO HW скрасить ожидание картинки
//                    placeholder(R.drawable.ic_stat_rocket)
//                    error(R.drawable.ic_stat_no_connect)
//                }

                setData(pictureOfTheDayAppState.pictureOfTheDayResponseData)
                binding.imageView.visibility = View.VISIBLE
                binding.loadingLayout.visibility = View.GONE

                binding.lifeHackMain.title.text =
                    pictureOfTheDayAppState.pictureOfTheDayResponseData.title
                binding.lifeHackMain.explanation.text =
                    pictureOfTheDayAppState.pictureOfTheDayResponseData.explanation


            }
        }
    }

    interface CallbackFragment {
        fun onError(messageError: String)
    }

    private fun setData(data: PictureOfTheDayResponseDate)  {
    val mediaType = data.mediaType
    if (mediaType=="image") {
        binding.imageView.load(data.url)
    } else {
        val videoUrl = data.hdurl
        if (videoUrl!=null){
        showAVideoUrl(videoUrl)}
    }
}

private fun showAVideoUrl(videoUrl: String?) = with(binding) {
    binding.imageView.visibility = android.view.View.GONE
    videoOfTheDay.visibility = android.view.View.VISIBLE
    videoOfTheDay.text = "Сегодня у нас без картинки дня, но есть  видео дня! " +
            "${videoUrl.toString()} \n кликни >ЗДЕСЬ< чтобы открыть в новом окне"
    videoOfTheDay.setOnClickListener {
        val i = Intent(android.content.Intent.ACTION_VIEW).apply {
            data = android.net.Uri.parse(videoUrl)
        }
        startActivity(i)
    }
}


    override fun onDestroy() {
        _binding = null
        super.onDestroy()
    }

    companion object {
        @JvmStatic
        fun newInstance() = PictureOfTheDayFragment()
    }

}