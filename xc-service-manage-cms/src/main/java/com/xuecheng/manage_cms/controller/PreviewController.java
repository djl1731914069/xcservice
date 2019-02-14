package com.xuecheng.manage_cms.controller;

import com.mongodb.client.gridfs.GridFSBucket;
import com.mongodb.client.gridfs.GridFSDownloadStream;
import com.mongodb.client.gridfs.model.GridFSFile;
import com.xuecheng.framework.domain.cms.CmsConfig;
import com.xuecheng.framework.domain.cms.CmsPage;
import com.xuecheng.framework.domain.cms.CmsTemplate;
import com.xuecheng.framework.domain.cms.response.CmsPageResult;
import com.xuecheng.framework.exception.CastException;
import com.xuecheng.framework.model.response.CommonCode;
import com.xuecheng.manage_cms.dao.CmsTemplateRepository;
import com.xuecheng.manage_cms.service.CmsPageService;
import freemarker.cache.StringTemplateLoader;
import freemarker.template.Configuration;
import freemarker.template.Template;
import org.apache.commons.io.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.gridfs.GridFsResource;
import org.springframework.data.mongodb.gridfs.GridFsTemplate;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.freemarker.FreeMarkerTemplateUtils;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.client.RestTemplate;

import javax.servlet.http.HttpServletResponse;
import java.util.Optional;

/**
 * @Auther: 段金良
 * @Date: 2019/2/3 0003 18:40
 * @Description:
 */
@Controller
@RequestMapping("/cms")
public class PreviewController {
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
    @RequestMapping("/preview/{id}")
    public void preview(@PathVariable("id") String id, HttpServletResponse response) throws Exception {
        //静态化程序远程请求DataUrl获取数据模型。
        CmsPageResult cmsPageResult = cmsPageService.getCmsPageById(id);
        if (cmsPageResult==null){
            CastException.cast(CommonCode.FAIL);
        }
        CmsPage cmsPage = cmsPageResult.getCmsPage();
        String dataUrl = cmsPage.getDataUrl();
        ResponseEntity<CmsConfig> entity = restTemplate.getForEntity(dataUrl, CmsConfig.class);
        CmsConfig config = entity.getBody();
//      静态化程序获取页面的模板信息
        String templateId = cmsPage.getTemplateId();
        Optional<CmsTemplate> cmsTemplate = cmsTemplateRepository.findById(templateId);
        if (cmsTemplate.isPresent()){
            CmsTemplate cmsTemplate1 = cmsTemplate.get();
            String templateFileId = cmsTemplate1.getTemplateFileId();
            GridFSFile file = gridFsTemplate.findOne(Query.query(Criteria.where("_id").is(templateFileId)));
            //打开下载流对象
            GridFSDownloadStream gridFSDownloadStream =
                    gridFSBucket.openDownloadStream(file.getObjectId());
            //创建GridFsResource
            GridFsResource gridFsResource = new GridFsResource(file,gridFSDownloadStream);
            String tempHtml = IOUtils.toString(gridFsResource.getInputStream(), "utf-8");

            Configuration configuration = new Configuration(Configuration.getVersion());
            //模板加载器
            StringTemplateLoader stringTemplateLoader = new StringTemplateLoader();
            stringTemplateLoader.putTemplate("template",tempHtml);
            //配置模板加载器
            configuration.setTemplateLoader(stringTemplateLoader);
            //获取模板
            Template template1 = configuration.getTemplate("template");
            String html = FreeMarkerTemplateUtils.processTemplateIntoString(template1, config);
            response.getOutputStream().write(html.getBytes("utf-8"));
        }
    }


}
