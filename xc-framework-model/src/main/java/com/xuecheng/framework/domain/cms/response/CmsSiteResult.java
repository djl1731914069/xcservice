package com.xuecheng.framework.domain.cms.response;

import com.xuecheng.framework.domain.cms.CmsSite;
import com.xuecheng.framework.model.response.ResponseResult;
import com.xuecheng.framework.model.response.ResultCode;
import lombok.Data;

/**
 * @Auther: 段金良
 * @Date: 2019/2/3 0003 12:41
 * @Description:
 */
@Data
public class CmsSiteResult extends ResponseResult {
    CmsSite c ;
    public CmsSiteResult(ResultCode resultCode, CmsSite c) {
        super(resultCode);
        this.c = c;
    }
}
