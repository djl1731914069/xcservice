package com.xuecheng.framework.domain.cms;

import lombok.Data;

/**
 * @Auther: 段金良
 * @Date: 2019/2/3 0003 12:57
 * @Description:
 */
@Data
public class ResponseData {
    public String msg;
    public Object result;
    public int code;
}
