package com.xu.calligraphy.boot.common.genshin;

import lombok.Data;

/**
 * @author xyq
 * @date 2026/2/7 16:11
 */
// 玩家基础信息
@Data
public class PlayerInfo {
    private String nickname;
    private int level;
    private String signature;
    private String uid;
    private int worldLevel; // 世界等级
    private int finishAchievementNum; // 成就数
    private int towerFloorIndex; // 深境螺旋层数
    private int towerLevelIndex; // 深境螺旋间数
}