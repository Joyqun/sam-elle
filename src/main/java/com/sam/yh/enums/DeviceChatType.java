package com.sam.yh.enums;

public enum DeviceChatType {

    /** 实时获取设备最新信息 */
    LASTEST_INFO("telleme", "实时获取设备最新信息"),
    /** 实时锁定设备 */
    LOCK_DEVICE("lockdevice", "实时锁定设备"),
    /** 实时解锁设备 */
    UNLOCK_DEVICE("unlockdevice", "实时解锁设备");

    private final String chatType;
    private final String desc;

    private DeviceChatType(String chatType, String desc) {
        this.chatType = chatType;
        this.desc = desc;
    }

    public String getChatType() {
        return chatType;
    }

    public String getDesc() {
        return desc;
    }

}
