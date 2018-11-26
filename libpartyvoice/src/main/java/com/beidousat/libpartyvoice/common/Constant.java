package com.beidousat.libpartyvoice.common;

import android.os.Environment;
import android.util.SparseArray;

/**
 * author: Lism
 * date:   2018/11/12
 * describe:
 */

public class Constant {
//    //内网测试环境
//    public static final String BASE_URL = "http://192.168.1.99:5000/";
//    //外网网测试环境
//    public static final String BASE_URL = "http://test.beidousat.com:5000/";
    //生产环境地址
    public static final String BASE_URL = "http://e.beidousat.com/";

    public static final String PLATFORM = "bestarmedia";
    public static final String SERVICE = "singing_service";
    public static final int MAX_PICTURE_COUNT = 30;
    public static final int MIN_PICTURE_COUNT = 2;
    public static final int ACTIVITY_YCYH = 1;

    public static final String AUDIO_SUFFIX = ".mp3";
    public static final String VIDEO_SUFFIX = ".mp4";
    public static final String PICTURE_SUFFIX = ".jpg";

    public static class File {
        public static final String APP_DIR = "/PartyVoice";
        public static final String VIDEO = "/Video/";
        public static final String VIDEO_COVER = "/Video/.Covers/";
        public static final String MUSIC = "/Music/";
        public static final String LRC = "/Music/Lrc/";
        public static final String WEBVIEW_CACHE = "/.WebViewCache/";
        public static final String PIC_COMPRESS = "/.Cache/compress/";
        public static final String ANIM = "/.Anim/";
        public static String VIDEO_FILE_NAME = null;

        static {
            java.io.File home = new java.io.File(getAppDir());
            if (!home.exists()) {
                home.mkdir();
            }
            java.io.File video = new java.io.File(getVideoDir());
            if (!video.exists()) {
                video.mkdir();
            }
            java.io.File videoCover = new java.io.File(getVideoCoverDir());
            if (!videoCover.exists()) {
                videoCover.mkdirs();
            }
            java.io.File music = new java.io.File(getMusicDir());
            if (!music.exists()) {
                music.mkdir();
            }
            java.io.File lrc = new java.io.File(getLrcDir());
            if (!lrc.exists()) {
                lrc.mkdir();
            }
            java.io.File webviewCache = new java.io.File(getWebviewCacheDir());
            if (!webviewCache.exists()) {
                webviewCache.mkdir();
            }
            java.io.File compress = new java.io.File(getPicCompressCacheDir());
            if (!compress.exists()) {
                compress.mkdirs();
            }
            java.io.File anim = new java.io.File(getAnimDir());
            if (!anim.exists()) {
                anim.mkdirs();
            }
        }

        public static String getAppDir() {
            return Environment.getExternalStorageDirectory() + APP_DIR;
        }

        public static String getVideoDir() {
            return getAppDir() + VIDEO;
        }

        public static String getMusicDir() {
            return getAppDir() + MUSIC;
        }

        public static String getWebviewCacheDir() {
            return getAppDir() + WEBVIEW_CACHE;
        }

        public static String getPicCompressCacheDir() {
            return getAppDir() + PIC_COMPRESS;
        }

        public static String getAnimDir() {
            return getAppDir() + ANIM;
        }

        public static String getVideoCoverDir() {
            return getAppDir() + VIDEO_COVER ;
        }

        public static String getLrcDir() {
            return getAppDir() + LRC;
        }
    }

    public class AlbumType {
        public static final int ALBUM_TYPE_SONGLIST = 0;
        public static final int ALBUM_TYPE_TOPIC = 1;
    }

    public class CategoryType {
        public static final int CATEGORY_CHILDREN = 0;
        public static final int CATEGORY_CHORUS = 1;
        public static final int CATEGORY_DANCE = 2;
        public static final int CATEGORY_DRAMA = 3;
        public static final int CATEGORY_FOLKSONG = 4;
        public static final int CATEGORY_MILITARY = 5;
        public static final int CATEGORY_POP = 6;
        public static final int CATEGORY_SCHOOL = 7;
        public static final int CATEGORY_VARIETY = 8;
    }

    public class Sex {
        public static final int SECRETE = 0;
        public static final int MALE = 1;
        public static final int FEMALE = 2;
    }

    public class MsgComment {
        public static final int RECEIVED = 0;
        public static final int SENT = 1;
    }

    public class MsgLikes {
        public static final int RECEIVED = 0;
        public static final int SENT = 1;
    }

    public class Contacts {
        public static final int FOLLOW = 0;
        public static final int FANS = 1;
    }

    public class ContactStatus {
        /**
         * 等待
         */
        public static final int WAITING = 1;
        /**
         * 接受
         */
        public static final int ACCEPT = 2;
        /**
         * 拒绝
         */
        public static final int REJECT = 3;
        /**
         * 封禁
         */
        public static final int FORBIDDEN = 4;
    }

    public static class ContentType {
        public static final int TEXT = 1;
        public static final int PICTURE = 2;
        public static final int VOICE = 3;
    }

    public static class WorkType {
        /**
         * MV
         */
        public static final int SYSTEM_MV = 1;
        /**
         * 用户作品
         */
        public static final int USER_WORK = 2;
        /**
         * 用户文章
         */
        public static final int USER_ARTICLE = 3;
        /**
         * 用户评论
         */
        public static final int USER_COMMENT = 4;
        /**
         * 其他
         */
        public static final int OTHERS = 5;

        /**
         * 歌曲
         */
        public static final int SONG = 6;

        /**
         * 粤唱粤好
         */
        public static final int YCYH = 7;
    }

    public static class InformType {
        /**
         * 侵权
         */
        public static final int TORT = 1;
        /**
         * 色情
         */
        public static final int EROTICISM = 2;
        /**
         * 违法
         */
        public static final int BREAK_LAW = 3;
        /**
         * 违规广告
         */
        public static final int ILLEGAL_ADVERTISING = 4;

        /**
         * 人身攻击
         */
        public static final int PERSONAL_ATTACK = 5;

        /**
         * 其他
         */
        public static final int OTHER = 6;
    }

    public class Channel {
        /**
         * 随便看看
         */
        public static final String CHANNEL_RANDOM = "channel_random";
        /**
         * 关注
         */
        public static final String CHANNEL_FOLLOW = "channel_follow";
        /**
         * 大赛
         */
        public static final String CHANNEL_MATCH = "channel_match";
        /**
         * 人气作品
         */
        public static final String CHANNEL_HOT = "channel_hot";
        /**
         * 精美MV
         */
        public static final String CHANNEL_CHOICEST = "channel_choicest";
        /**
         * 频道key
         */
        public static final String KEY_CHANNEL = "isingsing.show.channel";

        /**
         * 大赛预热
         */
        public static final String CHANNEL_YCYH = "channel_singing";

        /**
         * MV欣赏
         */
        public static final String CHANNEL_ADMIRE = "channel_admire";
    }

    public static class BannerType {
        /**
         * banner 轮播
         */
        public static final int BANNER = 1;
        /**
         * 活动
         */
        public static final int ACTIVITIES = 2;
        /**
         * 精选
         */
        public static final int CHOICENESS = 3;
        /**
         * 荔枝台
         */
        public static final int LIZHITAI = 4;
    }

    public static class RaceArea {
        public static final SparseArray<String> mAreas;

        static {
            mAreas = new SparseArray<>();
            //0,广州 1,佛山 2,深圳 3,肇庆 4,汕头 5,湛江 6,南宁 7,海口
            mAreas.put(0, "广州");
            mAreas.put(1, "佛山");
            mAreas.put(2, "深圳");
            mAreas.put(3, "肇庆");
            mAreas.put(4, "汕头");
            mAreas.put(5, "湛江");
            mAreas.put(6, "南宁");
            mAreas.put(7, "海口");
        }
    }

    public static class PayTool {
        public static String WECHAT_APP = "WECHAT_APP"; //微信APP
        public static String WECHAT_H5 = "WECHAT_H5"; //微信H5
        public static String ALIPAY_APP = "ALIPAY_APP"; //支付宝APP
    }

    public static class UploadFolder {
        public static final String SINGING = "singing";
        public static final String BASE = "base";
        public static final String AD = "ad";
    }

    public static class MatchStatus {
        /**
         * 报名
         */
        public static final int SIGNUP = 1;
        /**
         * 资格赛
         */
        public static final int PRIME = 2;
        /**
         * 后续赛事资格
         */
        public static final int TICKET = 3;
        /**
         * 海选
         */
        public static final int AUDITION = 4;
        /**
         * 分赛区
         */
        public static final int PARTITION = 5;
    }


}
