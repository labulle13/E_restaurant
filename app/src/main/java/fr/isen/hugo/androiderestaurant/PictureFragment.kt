package fr.isen.hugo.androiderestaurant

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.squareup.picasso.Picasso
import fr.isen.hugo.androiderestaurant.databinding.FragmentPictureBinding


private const val ARG_PICTURE = "picture"
private lateinit var binding: FragmentPictureBinding

class PictureFragment : Fragment() {
    // TODO: Rename and change types of parameters
    private var picture: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            picture = it.getString(ARG_PICTURE)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentPictureBinding.inflate(inflater, container, false)
        return binding.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        Picasso.get().load(picture).into(binding.pictureView)
    }

    companion object {

        @JvmStatic
        fun newInstance(picture: String) =
            PictureFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PICTURE, picture)
                }
            }
    }
}


