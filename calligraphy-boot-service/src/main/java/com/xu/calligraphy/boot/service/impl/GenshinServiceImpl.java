package com.xu.calligraphy.boot.service.impl;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.xu.calligraphy.boot.common.Result;
import com.xu.calligraphy.boot.common.genshin.*;
import com.xu.calligraphy.boot.dal.params.GenshinParam;
import com.xu.calligraphy.boot.service.GenshinService;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.*;

@Service
public class GenshinServiceImpl implements GenshinService {
    private final OkHttpClient okHttpClient = new OkHttpClient();
    private final ObjectMapper objectMapper = new ObjectMapper();
    private static final String ENKA_API_URL = "https://enka.network/api/uid/";

    // ==================== 静态映射表（Java 8 静态代码块初始化，适配JSON返回的ID） ====================
    // 角色ID -> 角色名
    private static final Map<Integer, String> CHARACTER_ID_MAP = new HashMap<>();
    // 属性ID -> 属性名
    private static final Map<String, String> PROP_ID_MAP = new HashMap<>();
    // 圣遗物部位ID -> 部位名
    private static final Map<String, String> ARTIFACT_POS_MAP = new HashMap<>();
    // 元素类型ID -> 元素名
    private static final Map<Integer, String> ENERGY_TYPE_MAP = new HashMap<>();

    static {
        // 初始化角色映射（适配JSON中的avatarId）
        CHARACTER_ID_MAP.put(10000041, "钟离");
        CHARACTER_ID_MAP.put(10000043, "白术");
        CHARACTER_ID_MAP.put(10000125, "纳西妲");
        CHARACTER_ID_MAP.put(10000122, "久岐忍");
        CHARACTER_ID_MAP.put(10000054, "夜兰");
        CHARACTER_ID_MAP.put(10000032, "班尼特");

        // 初始化属性映射（适配JSON中的FIGHT_PROP_*）
        PROP_ID_MAP.put("1", "基础攻击力");
        PROP_ID_MAP.put("2", "生命值");
        PROP_ID_MAP.put("3", "防御百分比");
        PROP_ID_MAP.put("4", "防御力");
        PROP_ID_MAP.put("5", "元素精通");
        PROP_ID_MAP.put("7", "攻击力百分比");
        PROP_ID_MAP.put("20", "暴击率");
        PROP_ID_MAP.put("21", "暴击伤害");
        PROP_ID_MAP.put("22", "生命值百分比");
        PROP_ID_MAP.put("23", "攻击力百分比");
        PROP_ID_MAP.put("28", "元素精通");
        PROP_ID_MAP.put("42", "充能效率");
        PROP_ID_MAP.put("44", "治疗加成");
        PROP_ID_MAP.put("1002", "命座数");
        PROP_ID_MAP.put("2000", "最大生命值");
        PROP_ID_MAP.put("2001", "实际攻击力");
        PROP_ID_MAP.put("2002", "实际防御力");

        // 初始化圣遗物部位映射
        ARTIFACT_POS_MAP.put("EQUIP_BRACER", "花");
        ARTIFACT_POS_MAP.put("EQUIP_NECKLACE", "羽");
        ARTIFACT_POS_MAP.put("EQUIP_SHOES", "沙");
        ARTIFACT_POS_MAP.put("EQUIP_RING", "杯");
        ARTIFACT_POS_MAP.put("EQUIP_DRESS", "冠");

        // 初始化元素类型映射
        ENERGY_TYPE_MAP.put(1, "火");
        ENERGY_TYPE_MAP.put(2, "水");
        ENERGY_TYPE_MAP.put(3, "雷");
        ENERGY_TYPE_MAP.put(7, "草");
    }

    /**
     * 根据UID获取角色信息（完全适配Enka返回的JSON格式）
     */
    @Override
    public Result findCharacterInfo(GenshinParam param) {
        // 1. 调用Enka API
        String uid = param.getUid();
        Request request = new Request.Builder().url(ENKA_API_URL + uid).build();
        try {
            Response response = okHttpClient.newCall(request).execute();
            if (!response.isSuccessful()) {
                return Result.error("API请求失败，状态码：" + response.code());
            }
            String responseBody = response.body().string();
            JsonNode rootNode = objectMapper.readTree(responseBody);

            // 2. 解析玩家基础信息
            PlayerInfo playerInfo = parsePlayerInfo(rootNode, uid);

            // 3. 解析角色列表（精准遍历avatarInfoList）
            List<CharacterInfo> characterList = new ArrayList<>();
            JsonNode avatarNodeList = rootNode.get("avatarInfoList");
            if (avatarNodeList != null && avatarNodeList.isArray()) {
                for (JsonNode avatarNode : avatarNodeList) {
                    CharacterInfo character = parseCharacterInfo(avatarNode, rootNode.get("showAvatarInfoList"));
                    characterList.add(character);
                }
            }

            // 4. 封装返回结果
            PlayerInfoWithCharacters result = new PlayerInfoWithCharacters();
            result.setPlayerInfo(playerInfo);
            result.setCharacterList(characterList);
            return Result.success(result);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return Result.error("解析失败");
    }

    /**
     * 解析玩家基础信息
     */
    private PlayerInfo parsePlayerInfo(JsonNode rootNode, String uid) {
        PlayerInfo playerInfo = new PlayerInfo();
        JsonNode playerNode = rootNode.get("playerInfo");
        if (playerNode != null) {
            playerInfo.setUid(uid);
            playerInfo.setNickname(playerNode.get("nickname").asText("未知玩家"));
            playerInfo.setLevel(playerNode.get("level").asInt(0));
            playerInfo.setSignature(playerNode.get("signature").asText("无签名"));
            // 拓展玩家信息（JSON中含有的字段）
            playerInfo.setWorldLevel(playerNode.get("worldLevel").asInt(0));
            playerInfo.setFinishAchievementNum(playerNode.get("finishAchievementNum").asInt(0));
        }
        return playerInfo;
    }

    /**
     * 解析单个角色信息（核心方法，精准映射JSON所有字段）
     */
    private CharacterInfo parseCharacterInfo(JsonNode avatarNode, JsonNode showAvatarNodeList) {
        CharacterInfo character = new CharacterInfo();
        int avatarId = avatarNode.get("avatarId").asInt(0);

        // 基础信息
        character.setName(CHARACTER_ID_MAP.getOrDefault(avatarId, "未知角色"));
        character.setLevel(avatarNode.get("propMap").get("4001").get("ival").asInt(0));
        character.setConstellation(getConstellation(showAvatarNodeList, avatarId));
        character.setHasSkin(avatarNode.has("costumeId") && avatarNode.get("costumeId").asInt(0) > 0);
        character.setFetterLevel(avatarNode.get("fetterInfo").get("expLevel").asInt(0));
        character.setEnergyType(avatarNode.get("energyType").asInt(0));
        character.setAvatarUrl("https://enka.network/ui/avatars/" + avatarId + ".png"); // 拼接头像URL

        // 解析战斗属性（fightPropMap）
        JsonNode fightPropNode = avatarNode.get("fightPropMap");
        if (fightPropNode != null) {
            character.setAttack(fightPropNode.get("2001").asDouble(0)); // 实际攻击力
            character.setHp(fightPropNode.get("2000").asDouble(0)); // 最大生命值
            character.setDefense(fightPropNode.get("2002").asDouble(0)); // 实际防御力
            character.setCritRate(fightPropNode.get("20").asDouble(0) * 100); // 暴击率(%)
            character.setCritDamage(fightPropNode.get("21").asDouble(0) * 100); // 暴击伤害(%)
            character.setElementalMastery(fightPropNode.get("5").asDouble(0)); // 元素精通
            character.setChargeEfficiency(fightPropNode.get("42").asDouble(0) * 100); // 充能效率(%)
            character.setHealAdd(fightPropNode.get("44").asDouble(0) * 100); // 治疗加成(%)
            character.setHpPercent(fightPropNode.get("22").asDouble(0) * 100); // 生命值百分比(%)
            character.setAttackPercent(fightPropNode.get("23").asDouble(0) * 100); // 攻击力百分比(%)
            character.setDefensePercent(fightPropNode.get("3").asDouble(0) * 100); // 防御百分比(%)
        }

        // 解析武器信息
        character.setWeapon(parseWeaponInfo(avatarNode.get("equipList")));

        // 解析圣遗物信息
        character.setArtifacts(parseArtifactList(avatarNode.get("equipList")));

        return character;
    }

    /**
     * 解析武器信息（从equipList中筛选武器，ITEM_WEAPON）
     */
    private WeaponInfo parseWeaponInfo(JsonNode equipListNode) {
        WeaponInfo weapon = new WeaponInfo();
        if (equipListNode == null || !equipListNode.isArray()) {
            return weapon;
        }
        ;

        for (JsonNode equipNode : equipListNode) {
            if ("ITEM_WEAPON".equals(equipNode.get("flat").get("itemType").asText())) {
                JsonNode weaponNode = equipNode.get("weapon");
                JsonNode flatNode = equipNode.get("flat");
                JsonNode weaponStats = flatNode.get("weaponStats").get(1); // 武器副属性

                weapon.setName(flatNode.get("nameTextMapHash").asText("未知武器")); // 武器名（哈希值，可后续映射）
                weapon.setLevel(weaponNode.get("level").asInt(0));
                weapon.setPromoteLevel(weaponNode.get("promoteLevel").asInt(0));
                weapon.setRefineLevel(weaponNode.get("affixMap").fields().next().getValue().asInt(0)); // 精炼等级
                weapon.setIcon("https://enka.network/ui/" + flatNode.get("icon").asText() + ".png"); // 武器图标
                break; // 只取第一个武器（角色唯一武器）
            }
        }
        return weapon;
    }

    /**
     * 解析圣遗物列表（从equipList中筛选圣遗物，ITEM_RELIQUARY）
     */
    private List<ArtifactInfo> parseArtifactList(JsonNode equipListNode) {
        List<ArtifactInfo> artifactList = new ArrayList<>();
        if (equipListNode == null || !equipListNode.isArray()) {
            return artifactList;
        }
        ;

        for (JsonNode equipNode : equipListNode) {
            if ("ITEM_RELIQUARY".equals(equipNode.get("flat").get("itemType").asText())) {
                ArtifactInfo artifact = new ArtifactInfo();
                JsonNode flatNode = equipNode.get("flat");
                JsonNode reliquaryNode = equipNode.get("reliquary");
                JsonNode mainStatNode = flatNode.get("reliquaryMainstat");
                JsonNode subStatsNode = flatNode.get("reliquarySubstats");

                // 圣遗物基础信息
                artifact.setType(ARTIFACT_POS_MAP.getOrDefault(flatNode.get("equipType").asText(), "未知部位"));
                artifact.setSetName(flatNode.get("setNameTextMapHash").asText("未知套装"));
                artifact.setRankLevel(flatNode.get("rankLevel").asInt(0)); // 星级
                artifact.setIcon("https://enka.network/ui/" + flatNode.get("icon").asText() + ".png"); // 图标
                // 主属性
                artifact.setMainStat(PROP_ID_MAP.getOrDefault(mainStatNode.get("mainPropId").asText(), "未知属性"));
                artifact.setMainStatValue(mainStatNode.get("statValue").asDouble(0));
                // 副属性
                List<String> subStats = new ArrayList<>();
                if (subStatsNode != null && subStatsNode.isArray()) {
                    for (JsonNode subNode : subStatsNode) {
                        String propName = PROP_ID_MAP.getOrDefault(subNode.get("appendPropId").asText(), "未知属性");
                        double propValue = subNode.get("statValue").asDouble(0);
                        // 百分比属性拼接%，数值属性直接展示
                        subStats.add(propName + "：" + (propName.contains("百分比") || propName.contains("率") || propName.contains("加成") ? String.format("%.1f%%", propValue) : String.format("%.0f", propValue)));
                    }
                }
                artifact.setSubStats(subStats);
                artifactList.add(artifact);
            }
        }
        return artifactList;
    }

    /**
     * 从showAvatarInfoList中获取命座数（talentLevel/命座ID列表长度）
     */
    private int getConstellation(JsonNode showAvatarNodeList, int avatarId) {
        if (showAvatarNodeList == null || !showAvatarNodeList.isArray()) {
            return 0;
        }
        for (JsonNode showNode : showAvatarNodeList) {
            if (showNode.get("avatarId").asInt(0) == avatarId) {
                // 命座数=talentLevel 或 talentIdList长度，取实际值
                return showNode.has("talentLevel") ? showNode.get("talentLevel").asInt(0) : 0;
            }
        }
        return 0;
    }

    /**
     * 计算队伍DPS（基于解析后的实际角色属性，优化计算逻辑）
     */
    @Override
    public Result calculateTeamDps(GenshinParam param) {
        double totalDps = 0.0;
        // 实际开发中可将解析后的角色信息存入缓存，此处简化为根据角色名匹配预设倍率（可扩展为从缓存获取实际属性）
        Map<String, Double> skillMultiplierMap = new HashMap<>();
        skillMultiplierMap.put("钟离", 6.5);
        skillMultiplierMap.put("白术", 4.0);
        skillMultiplierMap.put("纳西妲", 9.0);
        skillMultiplierMap.put("久岐忍", 5.5);
        skillMultiplierMap.put("夜兰", 8.5);
        skillMultiplierMap.put("班尼特", 7.0);

        for (String name : param.getCharacterNames()) {
            double attack = getMockAttack(name); // 实际需从缓存取解析后的攻击力
            double critRate = getMockCritRate(name) / 100; // 转为小数
            double critDamage = getMockCritDamage(name) / 100;
            double multiplier = skillMultiplierMap.getOrDefault(name, 4.0);
            // 优化DPS公式：实际攻击力 * (1 + 暴击率*暴击伤害) * 技能倍率 / 技能循环时间(5s)
            double singleDps = attack * (1 + critRate * critDamage) * multiplier / 5;
            totalDps += singleDps;
        }
        return Result.success(Math.round(totalDps * 100) / 100.0); // 保留两位小数
    }

    // ==================== 原有模拟属性方法（Java 8版） ====================
    private double getMockAttack(String name) {
        double attack;
        switch (name) {
            case "钟离":
                attack = 10409.02;
                break;
            case "白术":
                attack = 8192.13;
                break;
            case "纳西妲":
                attack = 14695.09;
                break;
            case "久岐忍":
                attack = 12703.91;
                break;
            case "夜兰":
                attack = 13470.50;
                break;
            case "班尼特":
                attack = 12397.40;
                break;
            default:
                attack = 2000.0;
                break;
        }
        return attack;
    }

    private double getMockCritRate(String name) {
        double critRate;
        switch (name) {
            case "钟离":
                critRate = 5.0;
                break;
            case "白术":
                critRate = 5.0;
                break;
            case "纳西妲":
                critRate = 74.17;
                break;
            case "久岐忍":
                critRate = 41.16;
                break;
            case "夜兰":
                critRate = 68.56;
                break;
            case "班尼特":
                critRate = 15.61;
                break;
            default:
                critRate = 60.0;
                break;
        }
        return critRate;
    }

    private double getMockCritDamage(String name) {
        double critDamage;
        switch (name) {
            case "钟离":
                critDamage = 0.0;
                break;
            case "白术":
                critDamage = 0.0;
                break;
            case "纳西妲":
                critDamage = 0.0;
                break;
            case "久岐忍":
                critDamage = 0.0;
                break;
            case "夜兰":
                critDamage = 0.0;
                break;
            case "班尼特":
                critDamage = 0.0;
                break;
            default:
                critDamage = 150.0;
                break;
        }
        return critDamage;
    }

    private double getSkillMultiplier(String name) {
        double multiplier;
        switch (name) {
            case "钟离":
                multiplier = 5.0;
                break;
            case "雷电将军":
                multiplier = 8.0;
                break;
            default:
                multiplier = 4.0;
                break;
        }
        return multiplier;
    }
}