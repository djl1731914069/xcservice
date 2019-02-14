package com.xuecheng.manage_cms.service.impl;

import com.alibaba.fastjson.JSON;
import com.mongodb.client.gridfs.GridFSBucket;
import com.mongodb.client.gridfs.GridFSDownloadStream;
import com.mongodb.client.gridfs.model.GridFSFile;
import com.xuecheng.framework.domain.cms.CmsConfig;
import com.xuecheng.framework.domain.cms.CmsPage;
import com.xuecheng.framework.domain.cms.CmsTemplate;
import com.xuecheng.framework.domain.cms.request.QueryPageRequest;
import com.xuecheng.framework.domain.cms.response.CmsCode;
import com.xuecheng.framework.domain.cms.response.CmsPageResult;
import com.xuecheng.framework.exception.CastException;
import com.xuecheng.framework.model.response.CommonCode;
import com.xuecheng.framework.model.response.QueryResponseResult;
import com.xuecheng.framework.model.response.QueryResult;
import com.xuecheng.framework.model.response.ResponseResult;
import com.xuecheng.manage_cms.config.RabbitMqConfig;
import com.xuecheng.manage_cms.dao.CmsPageRepository;
import com.xuecheng.manage_cms.dao.CmsTemplateRepository;
import com.xuecheng.manage_cms.service.CmsPageService;
import freemarker.cache.StringTemplateLoader;
import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.StringUtils;
import org.bson.types.ObjectId;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.*;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.gridfs.GridFsResource;
import org.springframework.data.mongodb.gridfs.GridFsTemplate;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.ui.freemarker.FreeMarkerTemplateUtils;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

/**
 * @Auther: 段金良
 * @Date: 2019/1/30 0030 09:13
 * @Description:
 */
@Service
public class CmsPageServiceImpl implements CmsPageService {
    @Autowired
    CmsPageRepository cmsPageRepository;
    @Autowired
    CmsPageService cmsPageService;
    @Autowired
    RestTemplate restTemplate;
    @Autowired
    CmsTemplateRepository cmsTemplateRepository;
    @Autowired
    GridFsTemplate gridFsTemplate;
    @Autowired
    GridFSBucket gridFSBucket;
    @Autowired
    RabbitTemplate rabbitTemplate;

    @Override
    public QueryResponseResult findList(int page, int size, QueryPageRequest queryPageRequest) {
        if (page <= 0) {
            page = 1;
        }
        page = page - 1;//为了适应mongodb的接口将页码减1
        if (size <= 0) {
            size = 20;
        }
        CmsPage cmsPage = new CmsPage();
        if (queryPageRequest != null) {
            if (StringUtils.isNotEmpty(queryPageRequest.getPageName())) {
                cmsPage.setPageName(queryPageRequest.getPageName());
            }
            if (StringUtils.isNotEmpty(queryPageRequest.getPageAliase())) {
                cmsPage.setPageAliase(queryPageRequest.getPageAliase());
            }
            if (StringUtils.isNotEmpty(queryPageRequest.getSiteId())) {
                cmsPage.setSiteId(queryPageRequest.getSiteId());
            }

        }
        ExampleMatcher exampleMatcher = ExampleMatcher.matching();
        exampleMatcher = exampleMatcher.withMatcher("pageName", ExampleMatcher.GenericPropertyMatchers.contains());
        exampleMatcher = exampleMatcher.withMatcher("pageAliase", ExampleMatcher.GenericPropertyMatchers.contains());
        Example<CmsPage> cmsPageExample = Example.of(cmsPage, exampleMatcher);

        Pageable pageable = new PageRequest(page, size);
        Page<CmsPage> pages = cmsPageRepository.findAll(cmsPageExample, pageable);
        QueryResult queryResult = new QueryResult();
        queryResult.setTotal(pages.getTotalElements());
        List<CmsPage> content = pages.getContent();
        for (CmsPage c : content) {
            if ("0".equals(c.getPageType())) {
                c.setPageType("静态");
            } else {
                c.setPageType("动态");
            }
        }
        queryResult.setList(content);
        QueryResponseResult queryResponseResult = new QueryResponseResult(CommonCode.SUCCESS, queryResult);
        return queryResponseResult;
    }

    @Override
    public CmsPageResult add(CmsPage cmsPage) {
        if (cmsPage == null) CastException.cast(CmsCode.CMS_ADDPAGE_ERRPARAMS);
        CmsPage cmsPage1 = cmsPageRepository.findByPageNameAndSiteIdAndPageWebPath(cmsPage.getPageName(), cmsPage.getSiteId(), cmsPage.getPageWebPath());
        if (cmsPage1 != null) CastException.cast(CmsCode.CMS_ADDPAGE_EXISTS);
        CmsPage c = cmsPageRepository.save(cmsPage);
        return new CmsPageResult(CommonCode.SUCCESS, c);
    }

    public ResponseResult del(String id) {
        cmsPageRepository.deleteById(id);
        return new ResponseResult(CommonCode.SUCCESS);
    }

    public CmsPageResult getCmsPageById(String id) {
        Optional<CmsPage> cmsPage = cmsPageRepository.findById(id);
        if (cmsPage.isPresent()) {
            CmsPage page = cmsPage.get();
            return new CmsPageResult(CommonCode.SUCCESS, page);
        }
        return new CmsPageResult(CommonCode.FAIL, null);
    }

    public CmsPageResult update(String id, CmsPage cmsPage) {
        Optional<CmsPage> cmsPage2 = cmsPageRepository.findById(id);
        if (cmsPage2.isPresent()) {
            CmsPage one = cmsPage2.get();
            one.setTemplateId(cmsPage.getTemplateId());
//更新所属站点
            one.setSiteId(cmsPage.getSiteId());
//更新页面别名
            one.setPageAliase(cmsPage.getPageAliase());
//更新页面名称
            one.setPageName(cmsPage.getPageName());
//更新访问路径
            one.setPageWebPath(cmsPage.getPageWebPath());
//更新物理路径
            one.setPagePhysicalPath(cmsPage.getPagePhysicalPath());
            one.setDataUrl(cmsPage.getDataUrl());
//执行更新
            CmsPage save = cmsPageRepository.save(one);
            if (save != null) {
                return new CmsPageResult(CommonCode.SUCCESS, save);
            }
            return new CmsPageResult(CommonCode.FAIL, null);
        }
        return new CmsPageResult(CommonCode.FAIL, null);
    }

    @Override
    public ResponseResult postPage(String pageId) {
        //静态化程序远程请求DataUrl获取数据模型。
        CmsPageResult cmsPageResult = cmsPageService.getCmsPageById(pageId);
        if (cmsPageResult == null) {
            CastException.cast(CommonCode.FAIL);
        }
        CmsPage cmsPage = cmsPageResult.getCmsPage();
        String dataUrl = cmsPage.getDataUrl();
        //获取model
        ResponseEntity<CmsConfig> entity = restTemplate.getForEntity(dataUrl, CmsConfig.class);
        CmsConfig config = entity.getBody();
//      静态化程序获取页面的模板信息
        String templateId = cmsPage.getTemplateId();
        Optional<CmsTemplate> cmsTemplate = cmsTemplateRepository.findById(templateId);
        if (cmsTemplate.isPresent()) {
            CmsTemplate cmsTemplate1 = cmsTemplate.get();
            String templateFileId = cmsTemplate1.getTemplateFileId();
            GridFSFile file = gridFsTemplate.findOne(Query.query(Criteria.where("_id").is(templateFileId)));
            //打开下载流对象
            GridFSDownloadStream gridFSDownloadStream =
                    gridFSBucket.openDownloadStream(file.getObjectId());
            //创建GridFsResource
            GridFsResource gridFsResource = new GridFsResource(file, gridFSDownloadStream);
            String tempHtml = null;
            try {
                tempHtml = IOUtils.toString(gridFsResource.getInputStream(), "utf-8");
            } catch (IOException e) {
                e.printStackTrace();
            }

            Configuration configuration = new Configuration(Configuration.getVersion());
            //模板加载器
            StringTemplateLoader stringTemplateLoader = new StringTemplateLoader();
            stringTemplateLoader.putTemplate("template", tempHtml);
            //配置模板加载器
            configuration.setTemplateLoader(stringTemplateLoader);
            //获取模板
            Template template1 = null;
            try {
                template1 = configuration.getTemplate("template");
            } catch (IOException e) {
                e.printStackTrace();
            }
            try {
                //model + template(ftl) = html   静态化
                String html = FreeMarkerTemplateUtils.processTemplateIntoString(template1, config);

                //存储之前先删除
                String htmlFileId = cmsPage.getHtmlFileId();
                if (StringUtils.isNotEmpty(htmlFileId)) {
                    gridFsTemplate.delete(Query.query(Criteria.where("_id").is(htmlFileId)));
                }
                InputStream inputStream = IOUtils.toInputStream(html, "utf-8");
                ObjectId store = gridFsTemplate.store(inputStream, cmsPage.getPageName());
                String fileId = store.toString();//将文件id存储到cmspage中
                cmsPage.setHtmlFileId(fileId);
                cmsPageRepository.save(cmsPage);
                //发送mq消息
                Map<String,String> m = new HashMap<>(1);
                m.put("pageId",pageId);
                String message = JSON.toJSONString(m);
                rabbitTemplate.convertAndSend(RabbitMqConfig.EX_ROUTING_CMS_POSTPAGE,cmsPage.getSiteId(),message);
                return new ResponseResult(CommonCode.SUCCESS);
            } catch (IOException e) {
                e.printStackTrace();
            } catch (TemplateException e) {
                e.printStackTrace();
            }
        }
        return null;

    }
}