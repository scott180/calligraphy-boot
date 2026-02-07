package com.xu.calligraphy.boot.service;

import com.xu.calligraphy.boot.common.Result;
import com.xu.calligraphy.boot.dal.params.GenshinParam;

/**
 * @author xyq
 * @date 2026/2/7 16:14
 */
public interface GenshinService {
    Result findCharacterInfo(GenshinParam param);

    Result calculateTeamDps(GenshinParam param);
}
