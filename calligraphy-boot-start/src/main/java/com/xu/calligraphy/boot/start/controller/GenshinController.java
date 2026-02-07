package com.xu.calligraphy.boot.start.controller;

import com.xu.calligraphy.boot.common.Result;
import com.xu.calligraphy.boot.dal.params.GenshinParam;
import com.xu.calligraphy.boot.service.GenshinService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * @author xyq
 * @date 2026/2/7 16:13
 */
@RestController
@RequestMapping("/api/genshin")
public class GenshinController {

    @Autowired
    private GenshinService genshinService;

    /**
     * 根据UID查询角色信息
     */
    @GetMapping("/character")
    public Result getCharacterInfo(@ModelAttribute GenshinParam param) {
        return Result.success(genshinService.findCharacterInfo(param));
    }

    /**
     * 计算队伍DPS
     */
    @PostMapping("/team/dps")
    public Result<Double> calculateTeamDps(@RequestBody GenshinParam param) {
        return Result.success(genshinService.calculateTeamDps(param));
    }
}
