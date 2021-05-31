package com.rsschool.android2021

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.core.widget.doOnTextChanged
import androidx.fragment.app.Fragment

class FirstFragment : Fragment() {

    private var generateButton: Button? = null
    private var previousResult: TextView? = null
    private var minValueView: EditText? = null
    private var maxValueView: EditText? = null
    private var listener: GenerateButtonListener? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_first, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        super.onViewCreated(view, savedInstanceState)
        var min: Int? = null
        var max: Int? = null
        previousResult = view.findViewById(R.id.previous_result)
        generateButton = view.findViewById(R.id.generate)
        minValueView = view.findViewById(R.id.min_value)
        maxValueView = view.findViewById(R.id.max_value)
        listener = context as GenerateButtonListener
        val result = arguments?.getInt(PREVIOUS_RESULT_KEY)
        previousResult?.text = "Previous result: ${result.toString()}"
        generateButton?.isEnabled = false;

        minValueView?.doOnTextChanged { text, _, _, _ ->
            if(text!!.length>1&&text[0]=='0') {
                minValueView?.setText(text.subSequence(1, text.length))
                minValueView?.setSelection(text.lastIndex)
            }
            min = checkNumber(text.toString())
            generateButton?.isEnabled = (min != null && max != null && min!! < max!!)
        }

        maxValueView?.doOnTextChanged { text, _, _, _ ->
            if(text!!.length>1&&text[0]=='0') {
                maxValueView?.setText(text.subSequence(1, text.length))
                maxValueView?.setSelection(text.lastIndex)
            }
            max = checkNumber(text.toString())
            generateButton?.isEnabled =  (min != null && max != null && min!! < max!!)
        }

        generateButton?.setOnClickListener {
            listener?.onGenerateButtonClicked(min!!, max!!)
        }
    }

    private fun checkNumber(text: String): Int?{
        return if(text!="")
            Integer.parseInt(text)
        else
            null
    }

    interface GenerateButtonListener
    {
        fun onGenerateButtonClicked(min: Int, max: Int)
    }

    companion object {

        @JvmStatic
        fun newInstance(previousResult: Int): FirstFragment {
            val fragment = FirstFragment()
            val args = Bundle()
            args.putInt(PREVIOUS_RESULT_KEY, previousResult)
            fragment.arguments = args
            return fragment
        }

        private const val PREVIOUS_RESULT_KEY = "PREVIOUS_RESULT"
    }
}