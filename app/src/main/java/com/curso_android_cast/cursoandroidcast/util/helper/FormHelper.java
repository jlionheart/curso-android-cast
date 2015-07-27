package com.curso_android_cast.cursoandroidcast.util.helper;

import android.content.Context;
import android.util.TypedValue;
import android.widget.EditText;

import com.curso_android_cast.cursoandroidcast.R;

public class FormHelper {

    private  FormHelper(){
        super();
    }

    public static boolean requireValidate(Context context, EditText... editTexts){
        boolean valid = true;
        String value;

        for (EditText editText : editTexts) {
            value = editText.getText() == null ? null : editText.getText().toString();
            if(value == null || value.trim().isEmpty()) {
                editText.setError(context.getString(R.string.error_field_required_general));
                valid = false;
            }
        };

        return valid;
    }
}
