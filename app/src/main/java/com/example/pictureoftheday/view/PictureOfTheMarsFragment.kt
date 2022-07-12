package com.example.pictureoftheday.view

import android.animation.ObjectAnimator
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.constraintlayout.motion.widget.MotionLayout
import androidx.constraintlayout.motion.widget.MotionScene
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import coil.load
import coil.transform.CircleCropTransformation
import com.example.pictureoftheday.R
import com.example.pictureoftheday.databinding.FragmentMotionStartBinding
import com.example.pictureoftheday.databinding.FragmentPictureOfTheMarsBinding
import com.example.pictureoftheday.viewmodel.PictureOfTheMarsAppState
import com.example.pictureoftheday.viewmodel.PictureOfTheMarsViewModel
import com.google.android.material.snackbar.Snackbar
import com.google.android.material.tabs.TabLayout
import kotlin.random.Random

class PictureOfTheMarsFragment : Fragment() {
    private var _binding: FragmentMotionStartBinding? = null
    private val binding: FragmentMotionStartBinding
        get() = _binding!!

    private val viewModel: PictureOfTheMarsViewModel by lazy {
        ViewModelProvider(this).get(PictureOfTheMarsViewModel::class.java)
    }
    private var callBackOnErrorLoad = object : CallbackOnError {
        override fun onError(messageError: String) {
            context?.let {
                Snackbar.make(it, binding.imageMarsM, messageError, Snackbar.LENGTH_SHORT).show()
            }
        }
    }

    interface CallbackOnError {
        fun onError(messageError: String)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        _binding = FragmentMotionStartBinding.inflate(inflater, container, false)
        return binding.root
    }

    var currstateMotion:Int = 0;
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        if (savedInstanceState == null) {
            binding.imageMarsM.load(R.drawable.bg_mars)
        }

        viewModel.getLiveData().observe(viewLifecycleOwner, Observer {
            renderData(it)
        })
        viewModel.sendRequest(callBackOnErrorLoad)

        binding.marsMotion.setTransitionListener(
            object : MotionLayout.TransitionListener {
                override fun onTransitionChange(
                    motionLayout: MotionLayout?,
                    startId: Int,
                    endId: Int,
                    progress: Float
                ) {
//                    Log.d("@@@", "onTransitionChange: Change")

                }

                override fun onTransitionCompleted(motionLayout: MotionLayout?, currentId: Int) {
                    Log.d("@@@", "onTransitionChange: Completed")
                    if (motionLayout != null) {
                        currstateMotion = currentId
                        if (currentId == motionLayout.endState) {

                        }
                    }
                }

                override fun onTransitionTrigger(
                    motionLayout: MotionLayout?,
                    triggerId: Int,
                    positive: Boolean,
                    progress: Float
                ) {
                    Log.d("@@@", "onTransitionChange: Trigger")
                }

                override fun onTransitionStarted(
                    motionLayout: MotionLayout?,
                    startId: Int,
                    endId: Int
                ) {
                    Log.d("@@@", "onTransitionChange: start")
                }

            }
        )


        initTabLayout()
    }

    private fun initTabLayout() {
        binding.tabLayoutMars.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {
            override fun onTabSelected(tab: TabLayout.Tab?) {
                when (tab?.position) {
                    0 -> {
                        viewModel.sendRequestToday(callBackOnErrorLoad)
//                        if (currstateMotion == binding.marsMotion.endState){
//                            binding.marsMotion.setTransition(R.id.motion_start_scene)
//                        }
//                            binding.marsMotion.transitionToEnd()
                    }
                    1 -> {
                        viewModel.sendRequestYT(callBackOnErrorLoad)
//                        binding.marsMotion.transitionToEnd()
                    }
                    2 -> {
                        viewModel.sendRequestTDBY(callBackOnErrorLoad)
//                        binding.marsMotion.transitionToEnd()
                    }
                    else -> {
                        viewModel.sendRequestToday(callBackOnErrorLoad)
//                        binding.marsMotion.transitionToEnd()
                    }
                }
                Toast.makeText(requireContext(), "${tab?.position}", Toast.LENGTH_SHORT).show()
            }

            override fun onTabUnselected(tab: TabLayout.Tab?) {
                Log.d("@@@", "onTabUnselected: Unselected")
            }

            override fun onTabReselected(tab: TabLayout.Tab?) {
                Log.d("@@@", "onTabUnselected: Reselected")
            }
        })

        binding.tabLayoutMars.selectTab(binding.tabLayoutMars.getTabAt(1))
    }

    private fun renderData(pictureOfTheMarsAppState: PictureOfTheMarsAppState) {

        when (pictureOfTheMarsAppState) {
            is PictureOfTheMarsAppState.Error -> {

                binding.imageMarsM.load(R.drawable.bg_mars)
            }
            is PictureOfTheMarsAppState.Loading -> {
                binding.imageMarsM.load(R.drawable.bg_mars)
            }
            is PictureOfTheMarsAppState.Success -> {
                if (pictureOfTheMarsAppState.pictureOfTheMarsResponseData.photos.isNotEmpty()) {
                    val randomphoto =
                        Random(pictureOfTheMarsAppState.pictureOfTheMarsResponseData.photos.size - 1).nextInt(
                            pictureOfTheMarsAppState.pictureOfTheMarsResponseData.photos.size - 1
                        )
                    val url =
                        pictureOfTheMarsAppState.pictureOfTheMarsResponseData.photos[randomphoto].imgSrc
                            .replace("http", "https", true)
                    binding.imageMarsM.load(url)
                    {
                        transformations(CircleCropTransformation())
                        placeholder(R.drawable.bg_mars)
                        error(R.drawable.ic_stat_no_connect)
                        listener(
                            onSuccess = {_, _, -> Log.d("@@@", "renderData: onSuccess")
                                binding.marsMotion.transitionToEnd()
                            }
                        )
                    }


                    binding.imageMarsM.setOnClickListener {
                        if (binding.marsMotion.getTransition(R.id.start_image) != null) {
                            if (binding.marsMotion.currentState == binding.marsMotion.getTransition(
                                    R.id.start
                                ).id
                            ) {
                                Log.d("@@@", "renderData: ${binding.marsMotion.currentState}}")
                            }
                        }

                    }

//                binding.imgSrc.text = pictureOfTheMarsAppState.pictureOfTheMarsResponseData.photos[randomphoto].imgSrc


                } else {
                    binding.imageMarsM.load(R.drawable.bg_mars)
                    context?.let {
                        Snackbar.make(
                            requireView(),
                            "По вашему запросу данные отсутствуют",
                            Snackbar.LENGTH_SHORT
                        ).show()
                    }
                }

            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

    companion object {
        @JvmStatic
        fun newInstance() = PictureOfTheMarsFragment()
    }

}