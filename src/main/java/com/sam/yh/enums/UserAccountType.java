package com.sam.yh.enums;

public enum UserAccountType {

    /** 手机号码 */
    MOBILE_PHONE("1", "手机号码"),
    /** 电子邮箱 */
    EMAIL("2", "电子邮箱");

    private final String type;
    private final String desc;

    private UserAccountType(String type, String desc) {
        this.type = type;
        this.desc = desc;
    }

    public String getType() {
        return type;
    }

    public String getDesc() {
        return desc;
    }

    public static UserAccountType getUserAccount(String accountType) {
        UserAccountType[] values = UserAccountType.values();
        for (UserAccountType userAccountType : values) {
            if (userAccountType.getType().equals(accountType)) {
                return userAccountType;
            }
        }

        return null;
    }

}
