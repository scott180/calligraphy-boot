package com.xu.calligraphy.boot.common.genshin;

import lombok.Data;

import java.util.List;

/**
 * @author xyq
 * @date 2026/2/7 16:15
 */
@Data
public class PlayerInfoWithCharacters {
    private PlayerInfo playerInfo;
    private List<CharacterInfo> characterList;

}
