package com.curso_android_cast.cursoandroidcast.util.helper;

import android.content.Context;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.view.MotionEvent;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.EditText;

import com.curso_android_cast.cursoandroidcast.R;

public class FormHelper {

    private  FormHelper(){
        super();
    }

    public static boolean requireValidate(Context context, EditText... editTexts){
        boolean valid = true;
        String value;
        Animation shake = AnimationUtils.loadAnimation(context, R.anim.shake);
        Drawable iconError;

        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.LOLLIPOP)
            iconError = context.getDrawable(R.mipmap.ic_alert_error);
        else
            iconError = context.getResources().getDrawable(R.mipmap.ic_alert_error);

        iconError.setBounds(new Rect(0, 0, iconError.getIntrinsicHeight(), iconError.getIntrinsicWidth()));

        for (EditText editText : editTexts) {
            value = editText.getText() == null ? null : editText.getText().toString();
            if(value == null || value.trim().isEmpty()) {
                editText.setError(context.getString(R.string.error_field_required_general), iconError);
                editText.startAnimation(shake);
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
