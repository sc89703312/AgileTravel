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

    public static final Integer PAGE_SIZE = 10;
    public static final String KEY = "$*^@!#";
}
