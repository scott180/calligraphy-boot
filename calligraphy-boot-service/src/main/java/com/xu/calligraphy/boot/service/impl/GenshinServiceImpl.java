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

import java.util.ArrayList;
import java.util.List;

/**
 * @author xyq
 * @date 2026/2/7 16:14
 */
@Service
public class GenshinServiceImpl implements GenshinService {
    private final OkHttpClient okHttpClient = new OkHttpClient();
    private final ObjectMapper objectMapper = new ObjectMapper();
    // Enka.Network API地址
    private static final String ENKA_API_URL = "https://enka.network/api/uid/";

    /**
     * 根据UID获取角色信息
     */
    @Override
    public Result findCharacterInfo(GenshinParam param) {
        String uid = param.getUid();
        // 1. 调用Enka API
        Request request = new Request.Builder()
                .url(ENKA_API_URL + uid)
                .build();
        try{
            Response response = okHttpClient.newCall(request).execute();
            if (!response.isSuccessful()) {
                return Result.error("API请求失败，状态码：" + response.code());
            }
            String responseBody = response.body().string();
            System.out.println(responseBody);
            JsonNode rootNode = objectMapper.readTree(responseBody);

            // 2. 解析玩家基础信息
            PlayerInfo playerInfo = new PlayerInfo();
            playerInfo.setUid(uid);
            playerInfo.setNickname(rootNode.get("playerInfo").get("nickname").asText());
            playerInfo.setLevel(rootNode.get("playerInfo").get("level").asInt());
            playerInfo.setSignature(rootNode.get("playerInfo").get("signature").asText());

            // 3. 解析角色列表（简化版，实际需映射角色ID到名称）
            List<CharacterInfo> characterList = new ArrayList<>();
            JsonNode charactersNode = rootNode.get("avatarInfoList");
            for (JsonNode characterNode : charactersNode) {
                CharacterInfo character = new CharacterInfo();
                // 角色名（需自行维护角色ID映射表，此处简化为示例）
                character.setName(getCharacterNameById(characterNode.get("avatarId").asInt()));
//                character.setLevel(characterNode.get("level").asInt());
                character.setConstellation(characterNode.get("talentIdList").size());
                character.setHasSkin(characterNode.has("costumeId") && characterNode.get("costumeId").asInt() > 0);
                // 头像URL
                character.setAvatarUrl("https://enka.network/ui/avatars/" + characterNode.get("avatarId") + ".png");

                // 解析基础属性（简化版）
                character.setAttack(characterNode.get("fightPropMap").get("1").asInt());
                character.setHp(characterNode.get("fightPropMap").get("2").asInt());
                character.setDefense(characterNode.get("fightPropMap").get("3").asInt());
                character.setCritRate(characterNode.get("fightPropMap").get("20").asDouble() * 100);
                character.setCritDamage(characterNode.get("fightPropMap").get("21").asDouble() * 100);
                character.setElementalMastery(characterNode.get("fightPropMap").get("28").asInt());

                // 解析武器信息（简化版）
                WeaponInfo weapon = new WeaponInfo();
                JsonNode weaponNode = characterNode.get("weapon");
                weapon.setName(getWeaponNameById(weaponNode.get("itemId").asInt()));
                weapon.setLevel(weaponNode.get("level").asInt());
                weapon.setRefineLevel(weaponNode.get("affixMap").asInt());
                character.setWeapon(weapon);

                // 解析圣遗物（简化版）
                List<ArtifactInfo> artifacts = new ArrayList<>();
                JsonNode relicsNode = characterNode.get("relicList");
                for (JsonNode relicNode : relicsNode) {
                    ArtifactInfo artifact = new ArtifactInfo();
                    artifact.setType(getArtifactTypeById(relicNode.get("pos").asInt()));
                    artifact.setMainStat(getStatNameById(relicNode.get("mainPropId").asInt()));
                    // 副属性（简化）
                    List<String> subStats = new ArrayList<>();
                    JsonNode subPropNode = relicNode.get("appendPropIdList");
                    for (JsonNode propId : subPropNode) {
                        subStats.add(getStatNameById(propId.asInt()));
                    }
                    artifact.setSubStats(subStats);
                    artifacts.add(artifact);
                }
                character.setArtifacts(artifacts);

                characterList.add(character);
            }

            // 4. 封装返回结果
            PlayerInfoWithCharacters result = new PlayerInfoWithCharacters();
            result.setPlayerInfo(playerInfo);
            result.setCharacterList(characterList);
            return Result.success(result);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return Result.error("解析失败");
    }

    /**
     * 计算队伍DPS（简化版逻辑）
     */
    @Override
    public Result calculateTeamDps(GenshinParam param) {
        List<String> characterNames = param.getCharacterNames();
        double totalDps = 0.0;
        // 简化逻辑：每个角色的DPS = 攻击力 * (1 + 暴击率*暴击伤害) * 技能倍率（预设）
        for (String name : characterNames) {
            // 模拟角色属性（实际需从缓存/数据库获取）
            double attack = getMockAttack(name);
            double critRate = getMockCritRate(name);
            double critDamage = getMockCritDamage(name);
            double skillMultiplier = getSkillMultiplier(name); // 技能倍率

            double singleDps = attack * (1 + (critRate / 100) * (critDamage / 100)) * skillMultiplier / 5; // 5秒技能循环
            totalDps += singleDps;
        }
        return Result.success(Math.round(totalDps * 100) / 100.0); // 保留两位小数
    }

    private String getArtifactTypeById(int pos) {
        // 1-5对应花/羽/砂/杯/冠
        String[] types = {"", "花", "羽", "砂", "杯", "冠"};
        return types[pos];
    }

    // -------------------- 辅助方法（Java 8兼容版） --------------------
    private String getCharacterNameById(int id) {
        // 示例映射，实际需维护完整的角色ID-名称映射
        String characterName;
        switch (id) {
            case 10000005:
                characterName = "旅行者(空)";
                break;
            case 10000007:
                characterName = "旅行者(荧)";
                break;
            case 10000041:
                characterName = "钟离";
                break;
            case 10000023:
                characterName = "雷电将军";
                break;
            default:
                characterName = "未知角色";
                break;
        }
        return characterName;
    }

    private String getWeaponNameById(int id) {
        // 示例映射
        String weaponName;
        switch (id) {
            case 112001:
                weaponName = "无工之剑";
                break;
            case 103004:
                weaponName = "薙草之稻光";
                break;
            default:
                weaponName = "未知武器";
                break;
        }
        return weaponName;
    }

    private String getStatNameById(int propId) {
        // 示例映射
        String statName;
        switch (propId) {
            case 1:
                statName = "攻击力";
                break;
            case 2:
                statName = "生命值";
                break;
            case 3:
                statName = "防御力";
                break;
            case 20:
                statName = "暴击率";
                break;
            case 21:
                statName = "暴击伤害";
                break;
            default:
                statName = "未知属性";
                break;
        }
        return statName;
    }

    // 模拟角色属性（实际需从查询结果中获取）
    private double getMockAttack(String name) {
        double attack;
        switch (name) {
            case "钟离":
                attack = 2500;
                break;
            case "雷电将军":
                attack = 3000;
                break;
            default:
                attack = 2000;
                break;
        }
        return attack;
    }

    private double getMockCritRate(String name) {
        double critRate;
        switch (name) {
            case "钟离":
                critRate = 70.0;
                break;
            case "雷电将军":
                critRate = 80.0;
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
                critDamage = 180.0;
                break;
            case "雷电将军":
                critDamage = 200.0;
                break;
            default:
                critDamage = 150.0;
                break;
        }
        return critDamage;
    }

    private double getSkillMultiplier(String name) {
        // 预设技能倍率
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
