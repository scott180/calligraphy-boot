package com.xu.calligraphy.boot.service.impl;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.xu.calligraphy.boot.common.Result;
import com.xu.calligraphy.boot.common.genshin.*;
import com.xu.calligraphy.boot.dal.params.GenshinParam;
import com.xu.calligraphy.boot.service.GenshinService;
import okhttp3.OkHttpClient;
import okhttp3.Request;
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
//            Response response = okHttpClient.newCall(request).execute();
//            if (!response.isSuccessful()) {
//                return Result.error("API请求失败，状态码：" + response.code());
//            }
//            String responseBody = response.body().string();
            String responseBody="{\"playerInfo\":{\"nickname\":\"星火\",\"level\":60,\"signature\":\"星星之火，可以燎原\",\"worldLevel\":9,\"nameCardId\":210256,\"finishAchievementNum\":1306,\"towerFloorIndex\":12,\"towerLevelIndex\":3,\"showAvatarInfoList\":[{\"avatarId\":10000041,\"level\":90,\"costumeId\":204101,\"talentLevel\":2,\"energyType\":2},{\"avatarId\":10000043,\"level\":80,\"talentLevel\":6,\"energyType\":7},{\"avatarId\":10000125,\"level\":90,\"talentLevel\":2,\"energyType\":2},{\"avatarId\":10000122,\"level\":90,\"energyType\":3},{\"avatarId\":10000054,\"level\":90,\"energyType\":2},{\"avatarId\":10000032,\"level\":90,\"costumeId\":203201,\"talentLevel\":6,\"energyType\":1}],\"profilePicture\":{\"id\":2701},\"isShowAvatarTalent\":true,\"fetterCount\":31,\"towerStarIndex\":36,\"stygianIndex\":5,\"stygianSeconds\":229,\"stygianId\":5269006},\"avatarInfoList\":[{\"avatarId\":10000041,\"propMap\":{\"1001\":{\"type\":1001,\"ival\":\"0\"},\"1002\":{\"type\":1002,\"ival\":\"6\",\"val\":\"6\"},\"1003\":{\"type\":1003,\"ival\":\"0\"},\"1004\":{\"type\":1004,\"ival\":\"0\"},\"4001\":{\"type\":4001,\"ival\":\"90\",\"val\":\"90\"},\"10010\":{\"type\":10010,\"ival\":\"24000\",\"val\":\"24000\"},\"10049\":{\"type\":10049,\"ival\":\"12000\",\"val\":\"12000\"}},\"talentIdList\":[411,412],\"fightPropMap\":{\"1\":10409.0244140625,\"3\":0.3519003987312317,\"4\":688.3076171875,\"7\":653.271240234375,\"20\":0.05000000074505806,\"21\":0,\"22\":0.5,\"23\":1.3199999332427979,\"26\":0,\"27\":0,\"28\":0,\"29\":0,\"30\":0,\"40\":0,\"41\":0,\"42\":0.5279999732971191,\"43\":0,\"44\":0,\"45\":0,\"46\":0,\"50\":0,\"51\":0,\"52\":0,\"53\":0,\"54\":0,\"55\":0,\"56\":0,\"72\":60,\"1002\":56.89194869995117,\"1010\":14071.9638671875,\"2000\":14071.9638671875,\"2001\":688.3076171875,\"2002\":653.271240234375,\"2003\":0,\"2004\":0,\"2020\":0,\"3045\":0,\"3046\":1},\"skillDepotId\":4101,\"inherentProudSkillList\":[412101,412201,412301],\"skillLevelMap\":{\"10411\":1,\"10412\":9,\"10413\":1,\"10415\":10},\"equipList\":[{\"itemId\":14302,\"weapon\":{\"level\":90,\"promoteLevel\":6,\"affixMap\":{\"114302\":4}},\"flat\":{\"nameTextMapHash\":\"3500935003\",\"rankLevel\":3,\"itemType\":\"ITEM_WEAPON\",\"icon\":\"UI_EquipIcon_Catalyst_Pulpfic\",\"weaponStats\":[{\"appendPropId\":\"FIGHT_PROP_BASE_ATTACK\",\"statValue\":401},{\"appendPropId\":\"FIGHT_PROP_HP_PERCENT\",\"statValue\":35.2}]}}],\"fetterInfo\":{\"expLevel\":10},\"costumeId\":204101},{\"avatarId\":10000043,\"propMap\":{\"1001\":{\"type\":1001,\"ival\":\"0\"},\"1002\":{\"type\":1002,\"ival\":\"5\",\"val\":\"5\"},\"1003\":{\"type\":1003,\"ival\":\"0\"},\"1004\":{\"type\":1004,\"ival\":\"0\"},\"4001\":{\"type\":4001,\"ival\":\"80\",\"val\":\"80\"},\"10010\":{\"type\":10010,\"ival\":\"24000\",\"val\":\"24000\"},\"10049\":{\"type\":10049,\"ival\":\"12000\",\"val\":\"12000\"}},\"talentIdList\":[431,432,433,434,435,436],\"fightPropMap\":{\"1\":8192.126953125,\"4\":660.1940307617188,\"7\":623.0250244140625,\"20\":0.05000000074505806,\"21\":0,\"22\":0.5,\"23\":1,\"26\":0,\"27\":0,\"28\":165.38400268554688,\"29\":0,\"30\":0,\"40\":0,\"41\":0,\"42\":0,\"43\":0,\"44\":0.18000000715255737,\"45\":0,\"46\":0,\"50\":0,\"51\":0,\"52\":0,\"53\":0,\"54\":0,\"55\":0,\"56\":0,\"74\":80,\"1004\":75.87271881103516,\"1010\":8192.126953125,\"2000\":8192.126953125,\"2001\":818.9626733398438,\"2002\":623.0250244140625,\"2003\":0,\"2004\":0,\"2020\":0,\"3045\":0,\"3046\":1},\"skillDepotId\":4301,\"inherentProudSkillList\":[432101,432201,432301],\"skillLevelMap\":{\"10431\":1,\"10432\":1,\"10435\":1},\"proudSkillExtraLevelMap\":{\"4332\":3,\"4339\":3},\"equipList\":[{\"itemId\":14416,\"weapon\":{\"level\":90,\"promoteLevel\":6,\"affixMap\":{\"114416\":4}},\"flat\":{\"nameTextMapHash\":\"426363739\",\"rankLevel\":4,\"itemType\":\"ITEM_WEAPON\",\"icon\":\"UI_EquipIcon_Catalyst_Pleroma\",\"weaponStats\":[{\"appendPropId\":\"FIGHT_PROP_BASE_ATTACK\",\"statValue\":510},{\"appendPropId\":\"FIGHT_PROP_ELEMENT_MASTERY\",\"statValue\":165}]}}],\"fetterInfo\":{\"expLevel\":6}},{\"avatarId\":10000125,\"propMap\":{\"1001\":{\"type\":1001,\"ival\":\"0\"},\"1002\":{\"type\":1002,\"ival\":\"6\",\"val\":\"6\"},\"1003\":{\"type\":1003,\"ival\":\"0\"},\"1004\":{\"type\":1004,\"ival\":\"0\"},\"4001\":{\"type\":4001,\"ival\":\"90\",\"val\":\"90\"},\"10010\":{\"type\":10010,\"ival\":\"24000\",\"val\":\"24000\"},\"10049\":{\"type\":10049,\"ival\":\"12000\",\"val\":\"12000\"}},\"talentIdList\":[1251,1252],\"fightPropMap\":{\"1\":14695.09375,\"2\":5586.6298828125,\"3\":0.7166999578475952,\"4\":550.0345458984375,\"5\":344.07000732421875,\"7\":514.9314575195312,\"8\":41.66999816894531,\"20\":0.7417199611663818,\"21\":0,\"22\":2.2020998001098633,\"23\":1.8547999858856201,\"26\":0,\"27\":0,\"28\":175.5800018310547,\"29\":0,\"30\":0,\"40\":0,\"41\":0,\"42\":0,\"43\":0,\"44\":0,\"45\":0,\"46\":0,\"50\":0,\"51\":0,\"52\":0,\"53\":0,\"54\":0,\"55\":0,\"56\":0,\"72\":60,\"1002\":60,\"1010\":20236.09375,\"2000\":30813.6953125,\"2001\":894.1045532226562,\"2002\":556.6014404296875,\"2003\":0,\"2004\":0,\"2005\":0,\"2020\":0,\"3002\":0,\"3006\":0,\"3045\":0,\"3046\":1},\"skillDepotId\":12501,\"inherentProudSkillList\":[1252101,1252201,1252301,1252501],\"skillLevelMap\":{\"11251\":1,\"11252\":10,\"11255\":9},\"equipList\":[{\"itemId\":81544,\"reliquary\":{\"level\":21,\"mainPropId\":14001,\"appendPropIdList\":[501033,501243,501202,501221,501031,501221,501224,501201,501223]},\"flat\":{\"nameTextMapHash\":\"1139606908\",\"rankLevel\":5,\"itemType\":\"ITEM_RELIQUARY\",\"icon\":\"UI_RelicIcon_15007_4\",\"equipType\":\"EQUIP_BRACER\",\"setId\":15007,\"setNameTextMapHash\":\"1751039235\",\"reliquarySubstats\":[{\"appendPropId\":\"FIGHT_PROP_HP_PERCENT\",\"statValue\":9.3},{\"appendPropId\":\"FIGHT_PROP_ELEMENT_MASTERY\",\"statValue\":21},{\"appendPropId\":\"FIGHT_PROP_CRITICAL\",\"statValue\":5.8},{\"appendPropId\":\"FIGHT_PROP_CRITICAL_HURT\",\"statValue\":25.6}],\"reliquaryMainstat\":{\"mainPropId\":\"FIGHT_PROP_HP\",\"statValue\":4780}}},{\"itemId\":43524,\"reliquary\":{\"level\":21,\"mainPropId\":12001,\"appendPropIdList\":[501204,501244,501224,501084,501222,501241,501224,501221,501203]},\"flat\":{\"nameTextMapHash\":\"3219708100\",\"rankLevel\":5,\"itemType\":\"ITEM_RELIQUARY\",\"icon\":\"UI_RelicIcon_15043_2\",\"equipType\":\"EQUIP_NECKLACE\",\"setId\":15043,\"setNameTextMapHash\":\"894629371\",\"reliquarySubstats\":[{\"appendPropId\":\"FIGHT_PROP_CRITICAL\",\"statValue\":7.4},{\"appendPropId\":\"FIGHT_PROP_ELEMENT_MASTERY\",\"statValue\":40},{\"appendPropId\":\"FIGHT_PROP_CRITICAL_HURT\",\"statValue\":27.2},{\"appendPropId\":\"FIGHT_PROP_DEFENSE\",\"statValue\":23}],\"reliquaryMainstat\":{\"mainPropId\":\"FIGHT_PROP_ATTACK\",\"statValue\":311}}},{\"itemId\":43554,\"reliquary\":{\"level\":21,\"mainPropId\":10007,\"appendPropIdList\":[501032,501224,501053,501024,501223,501222,501224,501221,501052]},\"flat\":{\"nameTextMapHash\":\"1674704732\",\"rankLevel\":5,\"itemType\":\"ITEM_RELIQUARY\",\"icon\":\"UI_RelicIcon_15043_5\",\"equipType\":\"EQUIP_SHOES\",\"setId\":15043,\"setNameTextMapHash\":\"894629371\",\"reliquarySubstats\":[{\"appendPropId\":\"FIGHT_PROP_HP_PERCENT\",\"statValue\":4.7},{\"appendPropId\":\"FIGHT_PROP_CRITICAL_HURT\",\"statValue\":34.2},{\"appendPropId\":\"FIGHT_PROP_ATTACK\",\"statValue\":33},{\"appendPropId\":\"FIGHT_PROP_HP\",\"statValue\":299}],\"reliquaryMainstat\":{\"mainPropId\":\"FIGHT_PROP_CHARGE_EFFICIENCY\",\"statValue\":51.8}}},{\"itemId\":43513,\"reliquary\":{\"level\":21,\"mainPropId\":15002,\"appendPropIdList\":[501222,501234,501082,501024,501224,501233,501232,501223]},\"flat\":{\"nameTextMapHash\":\"3054949244\",\"rankLevel\":5,\"itemType\":\"ITEM_RELIQUARY\",\"icon\":\"UI_RelicIcon_15043_1\",\"equipType\":\"EQUIP_RING\",\"setId\":15043,\"setNameTextMapHash\":\"894629371\",\"reliquarySubstats\":[{\"appendPropId\":\"FIGHT_PROP_CRITICAL_HURT\",\"statValue\":21},{\"appendPropId\":\"FIGHT_PROP_CHARGE_EFFICIENCY\",\"statValue\":17.5},{\"appendPropId\":\"FIGHT_PROP_DEFENSE\",\"statValue\":19},{\"appendPropId\":\"FIGHT_PROP_HP\",\"statValue\":299}],\"reliquaryMainstat\":{\"mainPropId\":\"FIGHT_PROP_HP_PERCENT\",\"statValue\":46.6}}},{\"itemId\":43533,\"reliquary\":{\"level\":21,\"mainPropId\":13008,\"appendPropIdList\":[501034,501233,501021,501241,501231,501033,501242,501233]},\"flat\":{\"nameTextMapHash\":\"2469001972\",\"rankLevel\":5,\"itemType\":\"ITEM_RELIQUARY\",\"icon\":\"UI_RelicIcon_15043_3\",\"equipType\":\"EQUIP_DRESS\",\"setId\":15043,\"setNameTextMapHash\":\"894629371\",\"reliquarySubstats\":[{\"appendPropId\":\"FIGHT_PROP_HP_PERCENT\",\"statValue\":11.1},{\"appendPropId\":\"FIGHT_PROP_CHARGE_EFFICIENCY\",\"statValue\":16.2},{\"appendPropId\":\"FIGHT_PROP_HP\",\"statValue\":209},{\"appendPropId\":\"FIGHT_PROP_ELEMENT_MASTERY\",\"statValue\":35}],\"reliquaryMainstat\":{\"mainPropId\":\"FIGHT_PROP_CRITICAL_HURT\",\"statValue\":62.2}}},{\"itemId\":14424,\"weapon\":{\"level\":90,\"promoteLevel\":6,\"affixMap\":{\"114424\":3}},\"flat\":{\"nameTextMapHash\":\"2275710883\",\"rankLevel\":4,\"itemType\":\"ITEM_WEAPON\",\"icon\":\"UI_EquipIcon_Catalyst_Yue\",\"weaponStats\":[{\"appendPropId\":\"FIGHT_PROP_BASE_ATTACK\",\"statValue\":454},{\"appendPropId\":\"FIGHT_PROP_CRITICAL\",\"statValue\":36.8}]}}],\"fetterInfo\":{\"expLevel\":6}},{\"avatarId\":10000122,\"propMap\":{\"1001\":{\"type\":1001,\"ival\":\"0\"},\"1002\":{\"type\":1002,\"ival\":\"6\",\"val\":\"6\"},\"1003\":{\"type\":1003,\"ival\":\"0\"},\"1004\":{\"type\":1004,\"ival\":\"0\"},\"4001\":{\"type\":4001,\"ival\":\"90\",\"val\":\"90\"},\"10010\":{\"type\":10010,\"ival\":\"24000\",\"val\":\"24000\"},\"10049\":{\"type\":10049,\"ival\":\"12000\",\"val\":\"12000\"}},\"fightPropMap\":{\"1\":12703.908203125,\"2\":4780,\"4\":854.0228271484375,\"5\":347.9599914550781,\"6\":0.3614000082015991,\"7\":799.2965698242188,\"8\":18.520000457763672,\"20\":0.4116000235080719,\"21\":0,\"22\":2.74888014793396,\"23\":1.4209001064300537,\"26\":0,\"27\":0,\"28\":592.6300048828125,\"29\":0,\"30\":0,\"40\":0,\"41\":0,\"42\":0,\"43\":0,\"44\":0,\"45\":0,\"46\":0,\"50\":0,\"51\":0,\"52\":0,\"53\":0,\"54\":0,\"55\":0,\"56\":0,\"73\":60,\"1003\":60,\"1010\":16347.15625,\"2000\":17483.908203125,\"2001\":1510.6265869140625,\"2002\":817.8165893554688,\"2003\":0,\"2004\":0,\"2005\":0,\"2020\":0,\"3002\":0,\"3006\":0,\"3045\":0,\"3046\":1},\"skillDepotId\":12201,\"inherentProudSkillList\":[1222101,1222201,1222301,1222501],\"skillLevelMap\":{\"11221\":1,\"11222\":10,\"11225\":9},\"equipList\":[{\"itemId\":41544,\"reliquary\":{\"level\":21,\"mainPropId\":14001,\"appendPropIdList\":[501201,501234,501064,501222,501063,501223,501222,501062,501203]},\"flat\":{\"nameTextMapHash\":\"2285928148\",\"rankLevel\":5,\"itemType\":\"ITEM_RELIQUARY\",\"icon\":\"UI_RelicIcon_15041_4\",\"equipType\":\"EQUIP_BRACER\",\"setId\":15041,\"setNameTextMapHash\":\"3690673363\",\"reliquarySubstats\":[{\"appendPropId\":\"FIGHT_PROP_CRITICAL\",\"statValue\":6.2},{\"appendPropId\":\"FIGHT_PROP_CHARGE_EFFICIENCY\",\"statValue\":6.5},{\"appendPropId\":\"FIGHT_PROP_ATTACK_PERCENT\",\"statValue\":15.7},{\"appendPropId\":\"FIGHT_PROP_CRITICAL_HURT\",\"statValue\":19.4}],\"reliquaryMainstat\":{\"mainPropId\":\"FIGHT_PROP_HP\",\"statValue\":4780}}},{\"itemId\":41524,\"reliquary\":{\"level\":21,\"mainPropId\":12001,\"appendPropIdList\":[501202,501223,501062,501242,501243,501201,501223,501204,501063]},\"flat\":{\"nameTextMapHash\":\"1533553636\",\"rankLevel\":5,\"itemType\":\"ITEM_RELIQUARY\",\"icon\":\"UI_RelicIcon_15041_2\",\"equipType\":\"EQUIP_NECKLACE\",\"setId\":15041,\"setNameTextMapHash\":\"3690673363\",\"reliquarySubstats\":[{\"appendPropId\":\"FIGHT_PROP_CRITICAL\",\"statValue\":9.7},{\"appendPropId\":\"FIGHT_PROP_CRITICAL_HURT\",\"statValue\":14},{\"appendPropId\":\"FIGHT_PROP_ATTACK_PERCENT\",\"statValue\":9.9},{\"appendPropId\":\"FIGHT_PROP_ELEMENT_MASTERY\",\"statValue\":40}],\"reliquaryMainstat\":{\"mainPropId\":\"FIGHT_PROP_ATTACK\",\"statValue\":311}}},{\"itemId\":41553,\"reliquary\":{\"level\":21,\"mainPropId\":10008,\"appendPropIdList\":[501202,501224,501053,501231,501054,501232,501221,501221]},\"flat\":{\"nameTextMapHash\":\"1309661124\",\"rankLevel\":5,\"itemType\":\"ITEM_RELIQUARY\",\"icon\":\"UI_RelicIcon_15041_5\",\"equipType\":\"EQUIP_SHOES\",\"setId\":15041,\"setNameTextMapHash\":\"3690673363\",\"reliquarySubstats\":[{\"appendPropId\":\"FIGHT_PROP_CRITICAL\",\"statValue\":3.1},{\"appendPropId\":\"FIGHT_PROP_CRITICAL_HURT\",\"statValue\":18.7},{\"appendPropId\":\"FIGHT_PROP_ATTACK\",\"statValue\":37},{\"appendPropId\":\"FIGHT_PROP_CHARGE_EFFICIENCY\",\"statValue\":9.7}],\"reliquaryMainstat\":{\"mainPropId\":\"FIGHT_PROP_ELEMENT_MASTERY\",\"statValue\":187}}},{\"itemId\":41513,\"reliquary\":{\"level\":21,\"mainPropId\":15007,\"appendPropIdList\":[501064,501232,501221,501202,501222,501232,501233,501221]},\"flat\":{\"nameTextMapHash\":\"1975575348\",\"rankLevel\":5,\"itemType\":\"ITEM_RELIQUARY\",\"icon\":\"UI_RelicIcon_15041_1\",\"equipType\":\"EQUIP_RING\",\"setId\":15041,\"setNameTextMapHash\":\"3690673363\",\"reliquarySubstats\":[{\"appendPropId\":\"FIGHT_PROP_ATTACK_PERCENT\",\"statValue\":5.8},{\"appendPropId\":\"FIGHT_PROP_CHARGE_EFFICIENCY\",\"statValue\":16.2},{\"appendPropId\":\"FIGHT_PROP_CRITICAL_HURT\",\"statValue\":17.1},{\"appendPropId\":\"FIGHT_PROP_CRITICAL\",\"statValue\":3.1}],\"reliquaryMainstat\":{\"mainPropId\":\"FIGHT_PROP_ELEMENT_MASTERY\",\"statValue\":187}}},{\"itemId\":81533,\"reliquary\":{\"level\":21,\"mainPropId\":13008,\"appendPropIdList\":[501202,501231,501082,501062,501204,501232,501204,501202]},\"flat\":{\"nameTextMapHash\":\"73409068\",\"rankLevel\":5,\"itemType\":\"ITEM_RELIQUARY\",\"icon\":\"UI_RelicIcon_15007_3\",\"equipType\":\"EQUIP_DRESS\",\"setId\":15007,\"setNameTextMapHash\":\"1751039235\",\"reliquarySubstats\":[{\"appendPropId\":\"FIGHT_PROP_CRITICAL\",\"statValue\":14},{\"appendPropId\":\"FIGHT_PROP_CHARGE_EFFICIENCY\",\"statValue\":9.7},{\"appendPropId\":\"FIGHT_PROP_DEFENSE\",\"statValue\":19},{\"appendPropId\":\"FIGHT_PROP_ATTACK_PERCENT\",\"statValue\":4.7}],\"reliquaryMainstat\":{\"mainPropId\":\"FIGHT_PROP_CRITICAL_HURT\",\"statValue\":62.2}}},{\"itemId\":14434,\"weapon\":{\"level\":90,\"promoteLevel\":6,\"affixMap\":{\"114434\":1}},\"flat\":{\"nameTextMapHash\":\"3497155131\",\"rankLevel\":4,\"itemType\":\"ITEM_WEAPON\",\"icon\":\"UI_EquipIcon_Catalyst_Ziedas\",\"weaponStats\":[{\"appendPropId\":\"FIGHT_PROP_BASE_ATTACK\",\"statValue\":510},{\"appendPropId\":\"FIGHT_PROP_CRITICAL_HURT\",\"statValue\":55.1}]}}],\"fetterInfo\":{\"expLevel\":10}},{\"avatarId\":10000054,\"propMap\":{\"1001\":{\"type\":1001,\"ival\":\"0\"},\"1002\":{\"type\":1002,\"ival\":\"6\",\"val\":\"6\"},\"1003\":{\"type\":1003,\"ival\":\"0\"},\"1004\":{\"type\":1004,\"ival\":\"0\"},\"4001\":{\"type\":4001,\"ival\":\"90\",\"val\":\"90\"},\"10010\":{\"type\":10010,\"ival\":\"24000\",\"val\":\"24000\"},\"10049\":{\"type\":10049,\"ival\":\"12000\",\"val\":\"12000\"}},\"fightPropMap\":{\"1\":13470.501953125,\"2\":6901.14990234375,\"3\":2.228652000427246,\"4\":842.469482421875,\"5\":377.1400146484375,\"6\":0.13989999890327454,\"7\":657.114013671875,\"8\":16.200000762939453,\"9\":0.10930000245571136,\"20\":-0.6855999827384949,\"21\":0,\"22\":0.5,\"23\":1.1231000423431396,\"26\":0.7089999914169312,\"27\":0,\"28\":62.94000244140625,\"29\":0,\"30\":0,\"40\":0,\"41\":0,\"42\":0.2879999876022339,\"43\":0,\"44\":0,\"45\":0,\"46\":0,\"50\":0,\"51\":0,\"52\":0,\"53\":0,\"54\":0,\"55\":0,\"56\":0,\"72\":70,\"1002\":52.958412170410156,\"1010\":50392.7109375,\"2000\":50392.7109375,\"2001\":1337.470947265625,\"2002\":745.1365966796875,\"2003\":0,\"2004\":0,\"2020\":0,\"3045\":0,\"3046\":1},\"skillDepotId\":5401,\"inherentProudSkillList\":[542101,542201,542301,542501],\"skillLevelMap\":{\"10541\":1,\"10542\":10,\"10545\":9},\"equipList\":[{\"itemId\":91544,\"reliquary\":{\"level\":21,\"mainPropId\":14001,\"appendPropIdList\":[501054,501034,501092,501061,501064,501061,501034,501034,501091]},\"flat\":{\"nameTextMapHash\":\"481512444\",\"rankLevel\":5,\"itemType\":\"ITEM_RELIQUARY\",\"icon\":\"UI_RelicIcon_15017_4\",\"equipType\":\"EQUIP_BRACER\",\"setId\":15017,\"setNameTextMapHash\":\"1337666507\",\"reliquarySubstats\":[{\"appendPropId\":\"FIGHT_PROP_ATTACK\",\"statValue\":19},{\"appendPropId\":\"FIGHT_PROP_HP_PERCENT\",\"statValue\":17.5},{\"appendPropId\":\"FIGHT_PROP_DEFENSE_PERCENT\",\"statValue\":10.9},{\"appendPropId\":\"FIGHT_PROP_ATTACK_PERCENT\",\"statValue\":14}],\"reliquaryMainstat\":{\"mainPropId\":\"FIGHT_PROP_HP\",\"statValue\":4780}}},{\"itemId\":91523,\"reliquary\":{\"level\":21,\"mainPropId\":12001,\"appendPropIdList\":[501234,501033,501024,501244,501032,501243,501024,501034]},\"flat\":{\"nameTextMapHash\":\"1170797788\",\"rankLevel\":5,\"itemType\":\"ITEM_RELIQUARY\",\"icon\":\"UI_RelicIcon_15017_2\",\"equipType\":\"EQUIP_NECKLACE\",\"setId\":15017,\"setNameTextMapHash\":\"1337666507\",\"reliquarySubstats\":[{\"appendPropId\":\"FIGHT_PROP_CHARGE_EFFICIENCY\",\"statValue\":6.5},{\"appendPropId\":\"FIGHT_PROP_HP_PERCENT\",\"statValue\":15.7},{\"appendPropId\":\"FIGHT_PROP_HP\",\"statValue\":598},{\"appendPropId\":\"FIGHT_PROP_ELEMENT_MASTERY\",\"statValue\":44}],\"reliquaryMainstat\":{\"mainPropId\":\"FIGHT_PROP_ATTACK\",\"statValue\":311}}},{\"itemId\":91554,\"reliquary\":{\"level\":21,\"mainPropId\":10002,\"appendPropIdList\":[501021,501204,501051,501233,501204,501023,501204,501023,501054]},\"flat\":{\"nameTextMapHash\":\"1692021396\",\"rankLevel\":5,\"itemType\":\"ITEM_RELIQUARY\",\"icon\":\"UI_RelicIcon_15017_5\",\"equipType\":\"EQUIP_SHOES\",\"setId\":15017,\"setNameTextMapHash\":\"1337666507\",\"reliquarySubstats\":[{\"appendPropId\":\"FIGHT_PROP_HP\",\"statValue\":747},{\"appendPropId\":\"FIGHT_PROP_CRITICAL\",\"statValue\":11.7},{\"appendPropId\":\"FIGHT_PROP_ATTACK\",\"statValue\":33},{\"appendPropId\":\"FIGHT_PROP_CHARGE_EFFICIENCY\",\"statValue\":5.8}],\"reliquaryMainstat\":{\"mainPropId\":\"FIGHT_PROP_HP_PERCENT\",\"statValue\":46.6}}},{\"itemId\":81513,\"reliquary\":{\"level\":21,\"mainPropId\":15002,\"appendPropIdList\":[501202,501022,501051,501242,501201,501201,501023,501201]},\"flat\":{\"nameTextMapHash\":\"1840901268\",\"rankLevel\":5,\"itemType\":\"ITEM_RELIQUARY\",\"icon\":\"UI_RelicIcon_15007_1\",\"equipType\":\"EQUIP_RING\",\"setId\":15007,\"setNameTextMapHash\":\"1751039235\",\"reliquarySubstats\":[{\"appendPropId\":\"FIGHT_PROP_CRITICAL\",\"statValue\":11.3},{\"appendPropId\":\"FIGHT_PROP_HP\",\"statValue\":508},{\"appendPropId\":\"FIGHT_PROP_ATTACK\",\"statValue\":14},{\"appendPropId\":\"FIGHT_PROP_ELEMENT_MASTERY\",\"statValue\":19}],\"reliquaryMainstat\":{\"mainPropId\":\"FIGHT_PROP_HP_PERCENT\",\"statValue\":46.6}}},{\"itemId\":91533,\"reliquary\":{\"level\":21,\"mainPropId\":13009,\"appendPropIdList\":[501081,501033,501023,501203,501031,501034,501034,501034]},\"flat\":{\"nameTextMapHash\":\"3587145716\",\"rankLevel\":5,\"itemType\":\"ITEM_RELIQUARY\",\"icon\":\"UI_RelicIcon_15017_3\",\"equipType\":\"EQUIP_DRESS\",\"setId\":15017,\"setNameTextMapHash\":\"1337666507\",\"reliquarySubstats\":[{\"appendPropId\":\"FIGHT_PROP_DEFENSE\",\"statValue\":16},{\"appendPropId\":\"FIGHT_PROP_HP_PERCENT\",\"statValue\":26.8},{\"appendPropId\":\"FIGHT_PROP_HP\",\"statValue\":269},{\"appendPropId\":\"FIGHT_PROP_CRITICAL\",\"statValue\":3.5}],\"reliquaryMainstat\":{\"mainPropId\":\"FIGHT_PROP_HEAL_ADD\",\"statValue\":35.9}}},{\"itemId\":14506,\"weapon\":{\"level\":90,\"promoteLevel\":6,\"affixMap\":{\"114506\":0}},\"flat\":{\"nameTextMapHash\":\"1890163363\",\"rankLevel\":5,\"itemType\":\"ITEM_WEAPON\",\"icon\":\"UI_EquipIcon_Catalyst_Kaleido\",\"weaponStats\":[{\"appendPropId\":\"FIGHT_PROP_BASE_ATTACK\",\"statValue\":608},{\"appendPropId\":\"FIGHT_PROP_HP_PERCENT\",\"statValue\":49.6}]}}],\"fetterInfo\":{\"expLevel\":10}},{\"avatarId\":10000032,\"propMap\":{\"1001\":{\"type\":1001,\"ival\":\"0\"},\"1002\":{\"type\":1002,\"ival\":\"6\",\"val\":\"6\"},\"1003\":{\"type\":1003,\"ival\":\"0\"},\"1004\":{\"type\":1004,\"ival\":\"0\"},\"4001\":{\"type\":4001,\"ival\":\"90\",\"val\":\"90\"},\"10010\":{\"type\":10010,\"ival\":\"24000\",\"val\":\"24000\"},\"10049\":{\"type\":10049,\"ival\":\"12000\",\"val\":\"12000\"}},\"talentIdList\":[321,322,323,324,325,326],\"fightPropMap\":{\"1\":12397.404296875,\"2\":6721.8896484375,\"3\":1.2588999271392822,\"4\":799.2321166992188,\"5\":344.07000732421875,\"7\":771.2493286132812,\"8\":64.80999755859375,\"9\":0.20410001277923584,\"20\":0.15610000491142273,\"21\":0,\"22\":0.8417999744415283,\"23\":2.6857800483703613,\"26\":0.35899999737739563,\"27\":0,\"28\":0,\"29\":0,\"30\":0,\"40\":0,\"41\":0,\"42\":0,\"43\":0,\"44\":0,\"45\":0,\"46\":0,\"50\":0,\"51\":0,\"52\":0,\"53\":0,\"54\":0,\"55\":0,\"56\":0,\"70\":60,\"1000\":15.065279960632324,\"1010\":34726.38671875,\"2000\":34726.38671875,\"2001\":1143.3021240234375,\"2002\":993.4713134765625,\"2003\":0,\"2004\":0,\"2020\":0,\"3045\":0,\"3046\":1},\"skillDepotId\":3201,\"inherentProudSkillList\":[322101,322201,322301],\"skillLevelMap\":{\"10321\":1,\"10322\":9,\"10323\":10},\"proudSkillExtraLevelMap\":{\"3232\":3,\"3239\":3},\"equipList\":[{\"itemId\":81544,\"reliquary\":{\"level\":21,\"mainPropId\":14001,\"appendPropIdList\":[501093,501223,501234,501034,501032,501032,501033,501223,501094]},\"flat\":{\"nameTextMapHash\":\"1139606908\",\"rankLevel\":5,\"itemType\":\"ITEM_RELIQUARY\",\"icon\":\"UI_RelicIcon_15007_4\",\"equipType\":\"EQUIP_BRACER\",\"setId\":15007,\"setNameTextMapHash\":\"1751039235\",\"reliquarySubstats\":[{\"appendPropId\":\"FIGHT_PROP_DEFENSE_PERCENT\",\"statValue\":13.9},{\"appendPropId\":\"FIGHT_PROP_CRITICAL_HURT\",\"statValue\":14},{\"appendPropId\":\"FIGHT_PROP_CHARGE_EFFICIENCY\",\"statValue\":6.5},{\"appendPropId\":\"FIGHT_PROP_HP_PERCENT\",\"statValue\":20.4}],\"reliquaryMainstat\":{\"mainPropId\":\"FIGHT_PROP_HP\",\"statValue\":4780}}},{\"itemId\":81524,\"reliquary\":{\"level\":21,\"mainPropId\":12001,\"appendPropIdList\":[501022,501032,501221,501231,501033,501034,501234,501224,501031]},\"flat\":{\"nameTextMapHash\":\"304013292\",\"rankLevel\":5,\"itemType\":\"ITEM_RELIQUARY\",\"icon\":\"UI_RelicIcon_15007_2\",\"equipType\":\"EQUIP_NECKLACE\",\"setId\":15007,\"setNameTextMapHash\":\"1751039235\",\"reliquarySubstats\":[{\"appendPropId\":\"FIGHT_PROP_HP\",\"statValue\":239},{\"appendPropId\":\"FIGHT_PROP_HP_PERCENT\",\"statValue\":19.8},{\"appendPropId\":\"FIGHT_PROP_CRITICAL_HURT\",\"statValue\":13.2},{\"appendPropId\":\"FIGHT_PROP_CHARGE_EFFICIENCY\",\"statValue\":11}],\"reliquaryMainstat\":{\"mainPropId\":\"FIGHT_PROP_ATTACK\",\"statValue\":311}}},{\"itemId\":81554,\"reliquary\":{\"level\":21,\"mainPropId\":10007,\"appendPropIdList\":[501093,501031,501204,501021,501022,501031,501201,501033,501033]},\"flat\":{\"nameTextMapHash\":\"3917377564\",\"rankLevel\":5,\"itemType\":\"ITEM_RELIQUARY\",\"icon\":\"UI_RelicIcon_15007_5\",\"equipType\":\"EQUIP_SHOES\",\"setId\":15007,\"setNameTextMapHash\":\"1751039235\",\"reliquarySubstats\":[{\"appendPropId\":\"FIGHT_PROP_DEFENSE_PERCENT\",\"statValue\":6.6},{\"appendPropId\":\"FIGHT_PROP_HP_PERCENT\",\"statValue\":18.7},{\"appendPropId\":\"FIGHT_PROP_CRITICAL\",\"statValue\":6.6},{\"appendPropId\":\"FIGHT_PROP_HP\",\"statValue\":448}],\"reliquaryMainstat\":{\"mainPropId\":\"FIGHT_PROP_CHARGE_EFFICIENCY\",\"statValue\":51.8}}},{\"itemId\":43514,\"reliquary\":{\"level\":21,\"mainPropId\":15002,\"appendPropIdList\":[501024,501223,501231,501083,501234,501234,501022,501024,501021]},\"flat\":{\"nameTextMapHash\":\"3783056700\",\"rankLevel\":5,\"itemType\":\"ITEM_RELIQUARY\",\"icon\":\"UI_RelicIcon_15043_1\",\"equipType\":\"EQUIP_RING\",\"setId\":15043,\"setNameTextMapHash\":\"894629371\",\"reliquarySubstats\":[{\"appendPropId\":\"FIGHT_PROP_HP\",\"statValue\":1046},{\"appendPropId\":\"FIGHT_PROP_CRITICAL_HURT\",\"statValue\":7},{\"appendPropId\":\"FIGHT_PROP_CHARGE_EFFICIENCY\",\"statValue\":17.5},{\"appendPropId\":\"FIGHT_PROP_DEFENSE\",\"statValue\":21}],\"reliquaryMainstat\":{\"mainPropId\":\"FIGHT_PROP_HP_PERCENT\",\"statValue\":46.6}}},{\"itemId\":81534,\"reliquary\":{\"level\":21,\"mainPropId\":13009,\"appendPropIdList\":[501031,501021,501052,501084,501034,501083,501033,501053,501033]},\"flat\":{\"nameTextMapHash\":\"3498942148\",\"rankLevel\":5,\"itemType\":\"ITEM_RELIQUARY\",\"icon\":\"UI_RelicIcon_15007_3\",\"equipType\":\"EQUIP_DRESS\",\"setId\":15007,\"setNameTextMapHash\":\"1751039235\",\"reliquarySubstats\":[{\"appendPropId\":\"FIGHT_PROP_HP_PERCENT\",\"statValue\":20.4},{\"appendPropId\":\"FIGHT_PROP_HP\",\"statValue\":209},{\"appendPropId\":\"FIGHT_PROP_ATTACK\",\"statValue\":33},{\"appendPropId\":\"FIGHT_PROP_DEFENSE\",\"statValue\":44}],\"reliquaryMainstat\":{\"mainPropId\":\"FIGHT_PROP_HEAL_ADD\",\"statValue\":35.9}}},{\"itemId\":11502,\"weapon\":{\"level\":90,\"promoteLevel\":6,\"affixMap\":{\"111502\":0}},\"flat\":{\"nameTextMapHash\":\"4055003299\",\"rankLevel\":5,\"itemType\":\"ITEM_WEAPON\",\"icon\":\"UI_EquipIcon_Sword_Dvalin\",\"weaponStats\":[{\"appendPropId\":\"FIGHT_PROP_BASE_ATTACK\",\"statValue\":608},{\"appendPropId\":\"FIGHT_PROP_CHARGE_EFFICIENCY\",\"statValue\":55.1}]}}],\"fetterInfo\":{\"expLevel\":10},\"costumeId\":203201}],\"ttl\":54,\"uid\":\"229885048\",\"region\":\"CN\"}";
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
//        character.setEnergyType(avatarNode.get("energyType").asInt(0));
        character.setAvatarUrl("https://enka.network/ui/avatars/" + avatarId + ".png"); // 拼接头像URL

        // 解析战斗属性（fightPropMap）
        JsonNode fightPropNode = avatarNode.get("fightPropMap");
        if (fightPropNode != null) {
            character.setAttack(fightPropNode.get("2001").asDouble(0)); // 实际攻击力
            character.setHp(fightPropNode.get("2000").asDouble(0)); // 最大生命值
            character.setDefense(fightPropNode.get("2002").asDouble(0)); // 实际防御力
            character.setCritRate(fightPropNode.get("20").asDouble(0) * 100); // 暴击率(%)
            character.setCritDamage(fightPropNode.get("21").asDouble(0) * 100); // 暴击伤害(%)
//            character.setElementalMastery(fightPropNode.get("5").asDouble(0)); // 元素精通
            character.setChargeEfficiency(fightPropNode.get("42").asDouble(0) * 100); // 充能效率(%)
            character.setHealAdd(fightPropNode.get("44").asDouble(0) * 100); // 治疗加成(%)
            character.setHpPercent(fightPropNode.get("22").asDouble(0) * 100); // 生命值百分比(%)
            character.setAttackPercent(fightPropNode.get("23").asDouble(0) * 100); // 攻击力百分比(%)
//            character.setDefensePercent(fightPropNode.get("3").asDouble(0) * 100); // 防御百分比(%)
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