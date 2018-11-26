package pl.sugl.common.utils;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Color;
import android.support.annotation.ColorInt;
import android.view.Gravity;

import com.flyco.dialog.widget.NormalDialog;

/**
 * author: Hanson
 * date:   2017/8/22
 * describe:
 */

public class DialogUtils {

    public static NormalDialog createTwoButton(NormalDialog dialog,
                                               String content,
                                               @ColorInt int divider,
                                               @ColorInt int titleLine,
                                               @ColorInt int titleText,
                                               @ColorInt int contentText) {
        if (dialog != null) {
            dialog.content(content)
                    .titleTextSize(17)
                    .contentTextSize(14)
                    .btnNum(2)
                    .style(NormalDialog.STYLE_TWO)
                    .widthScale(0.7f)
                    .cornerRadius(5)
                    .dividerColor(divider)
                    .titleLineColor(titleLine)
                    .titleTextColor(titleText)
                    .contentTextColor(contentText);
        }

        return dialog;
    }

    public static NormalDialog createNoTitleDialog(NormalDialog dialog, String content) {
        if (dialog == null) {
            return dialog;
        }
        dialog.isTitleShow(false)
                .cornerRadius(5)
                .contentGravity(Gravity.CENTER)
                .dividerColor(Color.parseColor("#e6e6e6"))
                .btnTextSize(15.5f, 15.5f)
//                .btnPressColor(Color.parseColor("#2B2B2B"))
                .widthScale(0.7f)
                .content(content)
                .contentTextColor(Color.BLACK)
                .btnTextColor(Color.parseColor("#EC3661"), Color.BLACK);
        return dialog;
    }

    public static NormalDialog createCommonTowButtonDialog(Context context,
                                                           String content,
                                                           String leftStr,
                                                           String rightStr) {
        return createCommonTowButtonDialog(context, null, content, leftStr, rightStr);
    }

    public static NormalDialog createCommonTowButtonDialog(Context context,
                                                           String title,
                                                           String content,
                                                           String leftStr,
                                                           String rightStr) {
        NormalDialog dialog = new NormalDialog(context);
        Resources res = context.getResources();
        dialog = createTwoButton(dialog, content,
                Color.parseColor("#E5E5E5"),
                Color.parseColor("#E5E5E5"),
                Color.parseColor("#666666"),
                Color.parseColor("#666666"))
                .title(title)
                .btnText(leftStr, rightStr);
        return dialog;
    }

    public static NormalDialog createNoTitleTwoButtonDialog(Context context, String content, String leftStr, String rightStr) {
        return new NormalDialog(context)
                .isTitleShow(false)
                .cornerRadius(5)
                .contentGravity(Gravity.CENTER)
                .dividerColor(Color.parseColor("#E5E5E5"))
                .btnTextSize(15.5f, 15.5f)
                .style(NormalDialog.STYLE_TWO)
                .btnNum(2)
                .widthScale(0.7f)
                .content(content)
                .contentTextColor(Color.parseColor("#666666"))
                .btnText(leftStr, rightStr)
                .btnTextColor(Color.parseColor("#666666"), Color.parseColor("#666666"));
    }

    public static NormalDialog createNoTitleSingleButtonDialog(Context context, String content) {
        return new NormalDialog(context)
                .isTitleShow(false)
                .cornerRadius(5)
                .contentGravity(Gravity.CENTER)
                .dividerColor(Color.parseColor("#E5E5E5"))
                .btnTextSize(15.5f, 15.5f)
                .style(NormalDialog.STYLE_ONE)
                .btnNum(1)
                .widthScale(0.7f)
                .content(content)
                .contentTextColor(Color.parseColor("#666666"))
                .btnTextColor(Color.parseColor("#666666"));
    }

    public static NormalDialog createNoTitleTowButtonDialog(Context context, String content) {
        return new NormalDialog(context)
                .isTitleShow(false)
                .cornerRadius(5)
                .contentGravity(Gravity.CENTER)
                .dividerColor(Color.parseColor("#E5E5E5"))
                .btnTextSize(15.5f, 15.5f)
                .style(NormalDialog.STYLE_ONE)
                .btnNum(2)
                .widthScale(0.7f)
                .content(content)
                .contentTextColor(Color.parseColor("#666666"))
                .btnTextColor(Color.parseColor("#666666"));
    }
}
