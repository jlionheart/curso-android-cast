package com.curso_android_cast.cursoandroidcast.util.helper;

import android.content.Context;
import android.util.TypedValue;
import android.view.MotionEvent;
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

    public static boolean isRightIconArea(EditText editText, MotionEvent event){
        final int DRAWABLE_LEFT = 0;
        final int DRAWABLE_TOP = 1;
        final int DRAWABLE_RIGHT = 2;
        final int DRAWABLE_BOTTOM = 3;

        if (event.getAction() == MotionEvent.ACTION_UP) {
            if (event.getRawX() >= (editText.getRight() - editText.getCompoundDrawables()[DRAWABLE_RIGHT].getBounds().width())) {
                return true;
            }
        }
        return false;
    }
}
