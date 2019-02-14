package com.xuecheng.framework.domain.cms.request;

import lombok.Data;

/**
 * @Auther: 段金良
 * @Date: 2019/1/29 0029 19:16
 * @Description:封装页面请求参数
 */
@Data
public class QueryPageRequest {
    //站点id
    private String siteId;
    //页面ID
    private String pageId;
    //页面名称
    private String pageName;
    //别名
    private String pageAliase;
    //模版id
    private String templateId;
}
