package br.com.grupofleury.core.utils

import android.text.Editable
import android.text.TextWatcher

object InputTextUtils {
    class MaskWatcher(private val mask: String, private val onlyNumbers: Boolean = false) :
        TextWatcher {
        private val numberSeparators = mask.replace(Regex("[0-9]"), "")
        private var isRunning = false
        private var isDeleting = false
        override fun beforeTextChanged(
            charSequence: CharSequence,
            start: Int,
            count: Int,
            after: Int
        ) {
            isDeleting = count > after
        }

        override fun onTextChanged(
            charSequence: CharSequence,
            start: Int,
            before: Int,
            count: Int
        ) {
        }

        override fun afterTextChanged(editable: Editable) {
            if (isRunning || isDeleting) {
                return
            }
            isRunning = true
            val editableLength = editable.length
            if (editableLength < mask.length) {

                if ((onlyNumbers && mask[editableLength].toString().toIntOrNull() !in 0..9) || (!onlyNumbers && mask[editableLength] != '#')) {
                    editable.append(mask[editableLength])
                } else if ((onlyNumbers && mask[editableLength - 1].toString().toIntOrNull() !in 0..9) || (!onlyNumbers && mask[editableLength - 1] != '#')) {
                    editable.insert(editableLength - 1, mask, editableLength - 1, editableLength)
                }
            }
            isRunning = false
        }

        companion object {
            fun buildCpf(): MaskWatcher {
                return MaskWatcher("999.999.999-99", true)
            }

            fun buildDate(): MaskWatcher {
                return MaskWatcher("99/99/9999", true)
            }
        }
    }
}