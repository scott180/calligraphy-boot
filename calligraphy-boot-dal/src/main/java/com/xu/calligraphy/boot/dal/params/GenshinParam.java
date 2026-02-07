package com.xu.calligraphy.boot.dal.params;

import lombok.Data;

import java.util.List;

/**
 * @author xyq
 * @date 2026/2/7 16:21
 */
@Data
public class GenshinParam {
    private String uid;
    private List<String> characterNames;
}
