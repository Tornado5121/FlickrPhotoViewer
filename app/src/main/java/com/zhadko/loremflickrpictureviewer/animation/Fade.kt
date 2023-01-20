package com.zhadko.loremflickrpictureviewer.animation

import android.view.animation.Animation
import android.view.animation.AnimationUtils
import android.view.animation.LayoutAnimationController
import androidx.fragment.app.Fragment
import com.zhadko.loremflickrpictureviewer.R

fun Fragment.fadeIn(): Animation =
    AnimationUtils.loadAnimation(requireContext(), R.anim.fade_in)

fun Fragment.fadeOut(): Animation =
    AnimationUtils.loadAnimation(requireContext(), R.anim.fade_out)

fun Fragment.fadeGroupIn(): LayoutAnimationController =
    AnimationUtils.loadLayoutAnimation(
        requireContext(),
        R.anim.view_group_move_in
    )