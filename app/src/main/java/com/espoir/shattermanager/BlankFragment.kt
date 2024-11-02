package com.espoir.shattermanager

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.platform.ViewCompositionStrategy
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.fragment.app.Fragment
import androidx.lifecycle.Lifecycle
import com.espoir.shatter.fragment.FmShatter
import com.espoir.shatter.fragment.FmShatterManager
import com.espoir.shatter.fragment.IShatterFragment
import com.espoir.shattermanager.databinding.FragmentBlankBinding
import com.espoir.shattermanager.databinding.LayoutShatterABinding
import kotlinx.coroutines.launch

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [BlankFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class BlankFragment : Fragment(), IShatterFragment {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null
    private lateinit var shatterManager: FmShatterManager
    private val shatter by lazy { ShatterFM(viewLifecycleOwner.lifecycle) }
    private lateinit var binding: FragmentBlankBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentBlankBinding.inflate(inflater, container, false)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        shatterManager = FmShatterManager(this)
        getShatterManager().addShatter(R.id.shatterALayout, shatter)
            .addShatter(ShatterDF(viewLifecycleOwner.lifecycle)).start()
        binding.hello.setOnClickListener {
            shatter.addData()
        }
        parentFragmentManager.setFragmentResultListener("requestKey",viewLifecycleOwner) { key, bundle ->
            val result = bundle.getString("bundleKey")
            Toast.makeText(activity,result,Toast.LENGTH_SHORT).show()
            // Do something with the result.
        }
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment BlankFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            BlankFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }

    override fun getShatterManager(): FmShatterManager {
        return shatterManager
    }
}


class ShatterFM(lifecycle: Lifecycle) : FmShatter(lifecycle) {
    override fun getLayoutResId(): Int = R.layout.layout_shatter_a
    private lateinit var binding: LayoutShatterABinding
    override fun initView(view: View?, bundle: Bundle?) {
        super.initView(view, bundle)
        binding = LayoutShatterABinding.bind(view!!)
        binding.button.setOnClickListener {
            val bundle: Bundle = Bundle()
            bundle.putString("bundleKey", "ShatterFM")
            setFragmentParentResult("requestKey", bundle)
            //findShatter(IShowToast::class.java)?.showFuckingToast("show fucking toast")
        }
        binding.compose.apply {
            setViewCompositionStrategy(ViewCompositionStrategy.DisposeOnViewTreeLifecycleDestroyed)

            setContent {
                MaterialTheme {
                    ShatterCompose()
                }
            }
        }

    }

    fun addData() {
        lifecycleScope.launch {
            findShatter(ShatterDF::class.java)?.test()
            binding.textView.text = "ShatterFM+${Thread.currentThread()}"
        }

    }
}

class ShatterDF(lifecycle: Lifecycle) : FmShatter(lifecycle) {
    fun test() {
        Toast.makeText(fragment.context, "dfffff", Toast.LENGTH_SHORT).show()
    }
}

@Composable
fun ShatterCompose() {
    var text by remember { mutableStateOf("ShatterCompose") }
    Column(modifier = Modifier.fillMaxWidth().background(colorResource(R.color.teal_700)), horizontalAlignment = Alignment.CenterHorizontally) {
        Text(text, color = colorResource(R.color.purple_500), fontSize = 12.sp)
        Button(modifier = Modifier
            .size(200.dp,100.dp), colors = ButtonDefaults.buttonColors(containerColor = Color.White), shape = RectangleShape, onClick = {
            text = "click"
        }) {
            Text("点击改变ssdfddg",  fontSize = 16.sp, color = Color.Cyan, fontFamily = FontFamily.SansSerif, fontWeight = FontWeight(800))
        }
    }
}

@Preview
@Composable
fun Pre() {
    ShatterCompose()
}