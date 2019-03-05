package nju.agile.travel.util;

/**
 * Created by echo on 2019/1/9.
 */
public class Constants {
    public static final Integer ACCOUNT_ON = 1;
    public static final Integer ACCOUNT_OFF = 0;
    public static final Integer ACTIVITY_ON = 1;
    public static final Integer ACTIVITY_OFF = 0;
    public static final Integer ACTIVITY_PUBLIC = 0;
    public static final Integer ACTIVITY_PRIVATE = 1;

    // remember to modify UserEntity's @WhereJoinTable when modifying below constants
    public static final Integer MEMBER_APPLYING = 0;
    public static final Integer MEMBER_APPROVED = 1;
    public static final Integer MEMBER_CREATOR = 2;
    public static final Integer MEMBER_NONE = 3;

    // message types
    public static final Integer ACTIVITY_CHECKED = 0;
    public static final Integer ACTIVITY_BANNED = 1;
    public static final Integer USER_CHECKED = 2;
    public static final Integer USER_BANNED = 3;
    public static final Integer APPLY_APPROVED = 4;
    public static final Integer APPLY_REFUSED = 5;
    public static final Integer MEMBER_EXIT = 6;

    public static final Integer PAGE_SIZE = 10;
    public static final String KEY = "$*^@!#";

    public static final String defaultAvatarUrl = "https://agile-travel.oss-cn-shanghai.aliyuncs.com/avatar/IMG_2273.JPG";
    public static final String defaultCoverUrl = "http://agile-travel.oss-cn-shanghai.aliyuncs.com/images/yYTfFTefFF.jpg";
}
