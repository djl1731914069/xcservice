package com.xuecheng.manage_cms.service;

import com.xuecheng.framework.domain.cms.CmsPage;
import com.xuecheng.framework.domain.cms.request.QueryPageRequest;
import com.xuecheng.framework.domain.cms.response.CmsPageResult;
import com.xuecheng.framework.model.response.QueryResponseResult;
import com.xuecheng.framework.model.response.ResponseResult;

/**
 * @Auther: 段金良
 * @Date: 2019/1/30 0030 08:45
 * @Description:
 */
public interface CmsPageService {

    /**
     * @Description 分页
     * @Param [page, size, queryPageRequest]
     * @return com.xuecheng.framework.model.response.QueryResponseResult
     **/
    public QueryResponseResult findList(int page, int size, QueryPageRequest queryPageRequest);

    public CmsPageResult add(CmsPage cmsPage);

    ResponseResult del(String id);

    CmsPageResult getCmsPageById(String id);

    CmsPageResult update(String id, CmsPage cmsPage);
    ResponseResult postPage(String pageId);
}
