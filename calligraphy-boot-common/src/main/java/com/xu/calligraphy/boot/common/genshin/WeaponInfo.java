package com.xu.calligraphy.boot.common.genshin;

import lombok.Data;

/**
 * @author xyq
 * @date 2026/2/7 16:12
 */
// 武器信息
@Data
public class WeaponInfo {
    private String name;          // 武器名
    private int level;            // 武器等级
    private int refineLevel;      // 精炼等级
    private int promoteLevel; // 武器突破等级
    private String icon; // 武器图标
}