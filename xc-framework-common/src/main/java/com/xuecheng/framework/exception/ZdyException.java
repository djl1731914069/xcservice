package com.xuecheng.framework.exception;

import com.xuecheng.framework.model.response.ResultCode;

/**
 * @Auther: 段金良
 * @Date: 2019/1/31 0031 16:37
 * @Description:
 */
public class ZdyException extends RuntimeException {
    ResultCode resultCode;
    public ZdyException(ResultCode resultCode){
        this.resultCode=resultCode;
    }
    public ResultCode getResultCode(){
        return resultCode;
    }
}
