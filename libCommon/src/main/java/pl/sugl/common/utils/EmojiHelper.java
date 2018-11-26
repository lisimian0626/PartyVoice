package pl.sugl.common.utils;


import android.text.TextUtils;

public class EmojiHelper {
    public static final boolean isEmojiAtStringEnd(String str) {
        if (TextUtils.isEmpty(str)) {
            return false;
        }

        int len = str.length();
        return isEmoji(str.charAt(len - 1));
    }

    public static final boolean isEmoji(char codePoint) {
        return !((codePoint == 0x0) ||
                (codePoint == 0x9) ||
                (codePoint == 0xA) ||
                (codePoint == 0xD) ||
                ((codePoint >= 0x20) && (codePoint <= 0xD7FF)) ||
                ((codePoint >= 0xE000) && (codePoint <= 0xFFFD)) ||
                ((codePoint >= 0x10000) && (codePoint <= 0x10FFFF)));
    }

    public static String unicode2String(String unicode) {
        StringBuffer string = new StringBuffer();
        String[] hex = unicode.split("\\\\u");

        for (int i = 1; i < hex.length; i++) {
            try {
                // 转换出每一个代码点
                int data = Integer.parseInt(hex[i], 16);

                // 追加成string
                string.append((char) data);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        return string.length() == 0 ? unicode : string.toString();
    }

    public static String string2Unicode(String string) {
        StringBuffer unicode = new StringBuffer();

        for (int i = 0; i < string.length(); i++) {

            // 取出每一个字符
            char c = string.charAt(i);
            if (c < 256)//ASC11表中的字符码值不够4位,补00
            {
                unicode.append("\\u00");
            } else {
                unicode.append("\\u");
            }
            // 转换为unicode
            unicode.append(Integer.toHexString(c));
        }

        return unicode.length() == 0 ? string : unicode.toString();
    }

    public static String encodeEmojiToUnicode(String string) {
        StringBuffer unicode = new StringBuffer();
        for (int i = 0; i < string.length(); i++) {

            // 取出每一个字符
            char c = string.charAt(i);

            if (isEmoji(c)) {
                if (c < 256)//ASC11表中的字符码值不够4位,补00
                {
                    unicode.append("\\u00");
                } else {
                    unicode.append("\\u");
                }
                // 转换为unicode
                unicode.append(Integer.toHexString(c));
            }
            unicode.append(c);
        }

        return unicode.length() == 0 ? string : unicode.toString();
    }
}
