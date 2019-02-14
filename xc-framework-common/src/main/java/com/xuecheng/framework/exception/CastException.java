package com.xuecheng.framework.exception;

import com.xuecheng.framework.model.response.ResultCode;

/**
 * @Auther: 段金良
 * @Date: 2019/1/31 0031 16:39
 * @Description:
 */
public class CastException {
    public static void cast(ResultCode resultCode){
        throw new ZdyException(resultCode);
    }
}
