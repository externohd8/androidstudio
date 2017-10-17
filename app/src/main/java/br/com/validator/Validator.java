package br.com.validator;

import android.content.Context;
import android.text.TextUtils;
import android.widget.EditText;

import br.com.trees.R;

/**
 * Created by Fernando on 02/10/2016.
 */
public class Validator {

    public static boolean validateEmptyField(Context context, EditText... editTexts) {
        boolean hasNoValidations = true;
        boolean hasFirst = false;

        for (EditText editText : editTexts) {
            if (TextUtils.isEmpty(editText.getText())) {
                editText.setError(context.getResources().getString(R.string.field_empty));
                hasNoValidations = false;

                if (!hasFirst) {
                    editText.requestFocus();
                    hasFirst = true;
                }
            }
        }

        return hasNoValidations;
    }

}
