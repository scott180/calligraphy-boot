package com.xu.calligraphy.boot.common.genshin;

import lombok.Data;

import java.util.List;

/**
 * @author xyq
 * @date 2026/2/7 16:12
 */
// 角色信息
@Data
public class CharacterInfo {
    private String name;          // 角色名
    private int level;            // 角色等级
    private int constellation;    // 命座数
    private boolean hasSkin;      // 是否有皮肤
    private String avatarUrl;     // 头像URL
    private WeaponInfo weapon;    // 武器信息
    private List<ArtifactInfo> artifacts; // 圣遗物列表
    private double attack;           // 攻击力
    private double hp;               // 生命值
    private double defense;          // 防御力
    private double critRate;      // 暴击率(%)
    private double critDamage;    // 暴击伤害(%)
    private double elementalMastery; // 元素精通

    private int energyType; // 元素类型
    private int fetterLevel; // 好感等级
    private double chargeEfficiency; // 充能效率
    private double healAdd; // 治疗加成
    private double defensePercent; // 防御百分比
    private double hpPercent; // 生命值百分比
    private double attackPercent; // 攻击力百分比
}