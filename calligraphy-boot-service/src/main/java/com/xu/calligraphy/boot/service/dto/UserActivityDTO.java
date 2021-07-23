package com.xu.calligraphy.boot.service.dto;

import com.xu.calligraphy.boot.dal.model.UserActivityDO;
import lombok.Data;

/**
 * @author xu
 * @date 2020/1/1 22:15
 */
@Data
public class UserActivityDTO extends UserActivityDO {
    private String cancelText;
}
