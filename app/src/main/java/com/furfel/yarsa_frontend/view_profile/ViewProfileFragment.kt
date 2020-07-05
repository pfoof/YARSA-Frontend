package com.furfel.yarsa_frontend.view_profile

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.furfel.yarsa_frontend.R

private const val PROFILE_USERNAME = "profile"

class ViewProfileFragment : Fragment() {
    // TODO: Rename and change types of parameters
    private var profile: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            profile = it.getString(PROFILE_USERNAME)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val layout = inflater.inflate(R.layout.view_profile, container, false)



        return layout
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param username Name of the user's profile to view.
         * @return A new instance of fragment ViewProfileFragment.
         */
        @JvmStatic
        fun newInstance(username: String) =
            ViewProfileFragment().apply {
                arguments = Bundle().apply {
                    putString(PROFILE_USERNAME, username)
                }
            }
    }
}