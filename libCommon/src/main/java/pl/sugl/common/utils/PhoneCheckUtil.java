package pl.sugl.common.utils;

import android.text.TextUtils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * author: Hanson
 * date:   2016/7/11
 * describe:
 */
public class PhoneCheckUtil {
    private static final String[] IPPFXS4 = {"1790", "1791", "1793", "1795",
            "1796", "1797", "1799"};
    private static final String[] IPPFXS5 = {"12583", "12593", "12589",
            "12520", "10193", "11808"};
    private static final String[] IPPFXS6 = {"118321"};
    private static final String PATTERN_PHONE = "^[1][356789][0-9]{9}$";//"^((13[0-9])|(15[^4,\\D])|(18[0-9])|(17[0-9]))\\d{8}$";
    private static final String PATTERN_PASSWD = "^.{6,64}";//"^(?![0-9]+$)(?![a-zA-Z]+$)[0-9A-Za-z]{8,20}$";
    public static final int MIN_PASSWD_LEN = 6;
    public static final int MAX_PASSWD_LEN = 18;
    public static final int SMSCODE_LEN = 6;

    public static boolean checkPhoneNoAvalible(String mobiles) {
        if (TextUtils.isEmpty(mobiles)) {
            return false;
        }
        String temp = mobiles.replace(" ", "");
        temp = temp.replace("-", "");
        Pattern p = Pattern.compile(PATTERN_PHONE);
        Matcher m = p.matcher(temp);
        return m.matches();
    }

    public static boolean checkPasswdAvalible(String pwd) {
        if (TextUtils.isEmpty(pwd)) {
            return false;
        }

        if (pwd.length() < MIN_PASSWD_LEN || pwd.length() > MAX_PASSWD_LEN) {
            return false;
        }

//        Pattern p = Pattern.compile(PATTERN_PASSWD);
//        Matcher m = p.matcher(pwd);
//        return m.matches();
        return true;
    }

    public static boolean checkVerifyCode(String verify) {
        return !TextUtils.isEmpty(verify) && verify.length() == SMSCODE_LEN;
    }

    public static String hiddenPhoneNum(String str) {
        if (TextUtils.isEmpty(str) || str.length() < 8) {
            return "";
        }

        StringBuilder strBuilder = new StringBuilder(str);
        strBuilder.delete(3, 8);
        strBuilder.insert(3, "****");

        return strBuilder.toString();
    }


    /**
     * 消除电话号码中 可能含有的 IP号码、+86、0086等前缀
     *
     * @param telNum
     * @return
     */
    public static String trimTelNum(String telNum) {

        if (telNum == null || "".equals(telNum) || telNum.length() < 7) {
            System.out.println("trimTelNum is null");
            return null;
        }

        String ippfx6 = substring(telNum, 0, 6);
        String ippfx5 = substring(telNum, 0, 5);
        String ippfx4 = substring(telNum, 0, 4);

        if (telNum.length() > 7
                && ("0".equals(substring(telNum, 5, 1))
                || "1".equals(substring(telNum, 5, 1)) || "400".equals(substring(
                telNum, 5, 3)) || "+86".equals(substring(
                telNum, 5, 3)))
                && (inArray(ippfx5, IPPFXS5) || inArray(ippfx4, IPPFXS4))) {
            telNum = substring(telNum, 5);
        } else if (telNum.length() > 8
                && ("0".equals(substring(telNum, 6, 1))
                || "1".equals(substring(telNum, 6, 1)) || "400".equals(substring(
                telNum, 6, 3)) || "+86".equals(substring(
                telNum, 6, 3)))
                && inArray(ippfx6, IPPFXS6)) {
            telNum = substring(telNum, 6);
        }
        // remove ip dial

        telNum = telNum.replace("-", "");
        telNum = telNum.replace(" ", "");

        if ("0086".equals(substring(telNum, 0, 4))) {
            telNum = substring(telNum, 4);
        } else if ("+86".equals(substring(telNum, 0, 3))) {
            telNum = substring(telNum, 3);
        } else if ("00186".equals(substring(telNum, 0, 5))) {
            telNum = substring(telNum, 5);
        }
        return telNum;
    }

    /**
     * 截取字符串
     *
     * @param s
     * @param from
     * @return
     */
    protected static String substring(String s, int from) {
        try {
            return s.substring(from);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "";
    }

    protected static String substring(String s, int from, int len) {
        try {
            return s.substring(from, from + len);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "";
    }

    /**
     * 判断一个字符串是否在一个字符串数组中
     *
     * @param target
     * @param arr
     * @return
     */
    protected static boolean inArray(String target, String[] arr) {
        if (arr == null || arr.length == 0) {
            return false;
        }
        if (target == null) {
            return false;
        }
        for (String s : arr) {
            if (target.equals(s)) {
                return true;
            }
        }
        return false;
    }
}
