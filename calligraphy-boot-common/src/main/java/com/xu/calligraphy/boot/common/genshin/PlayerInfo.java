package com.xu.calligraphy.boot.common.genshin;

import lombok.Data;

/**
 * @author xyq
 * @date 2026/2/7 16:11
 */
// 玩家基础信息
@Data
public class PlayerInfo {
    private String nickname;      // 玩家昵称
    private int level;            // 玩家等级
    private String signature;     // 玩家签名
    private String uid;           // UID
}