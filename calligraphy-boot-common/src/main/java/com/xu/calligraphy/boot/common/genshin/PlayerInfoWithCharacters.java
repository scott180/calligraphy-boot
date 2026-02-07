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

    // getter/setter
    public PlayerInfo getPlayerInfo() { return playerInfo; }
    public void setPlayerInfo(PlayerInfo playerInfo) { this.playerInfo = playerInfo; }
    public List<CharacterInfo> getCharacterList() { return characterList; }
    public void setCharacterList(List<CharacterInfo> characterList) { this.characterList = characterList; }
}
