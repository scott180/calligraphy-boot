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
}