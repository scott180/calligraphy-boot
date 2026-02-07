package com.xu.calligraphy.boot.common.genshin;

import lombok.Data;

import java.util.List;

/**
 * @author xyq
 * @date 2026/2/7 16:12
 */
// 圣遗物信息
@Data
public class ArtifactInfo {
    private String type;          // 圣遗物部位(花/羽/砂/杯/冠)
    private String mainStat;      // 主属性
    private List<String> subStats;// 副属性

    private String setName; // 圣遗物套装名
    private int rankLevel; // 圣遗物星级
    private String icon; // 圣遗物图标
    private double mainStatValue; // 主属性数值
}