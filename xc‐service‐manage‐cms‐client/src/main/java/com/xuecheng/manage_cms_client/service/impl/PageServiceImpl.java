package com.xuecheng.manage_cms_client.service.impl;

import cn.hutool.core.util.ObjectUtil;
import com.mongodb.client.gridfs.GridFSBucket;
import com.mongodb.client.gridfs.GridFSDownloadStream;
import com.mongodb.client.gridfs.model.GridFSFile;
import com.xuecheng.framework.domain.cms.CmsPage;
import com.xuecheng.framework.domain.cms.CmsSite;
import com.xuecheng.manage_cms_client.dao.CmsPageRepository;
import com.xuecheng.manage_cms_client.dao.CmsSiteRepository;
import com.xuecheng.manage_cms_client.service.PageService;
import org.apache.commons.io.IOUtils;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.gridfs.GridFsResource;
import org.springframework.data.mongodb.gridfs.GridFsTemplate;
import org.springframework.stereotype.Service;

import java.io.*;
import java.util.Optional;

/**
 * @Auther: 段金良
 * @Date: 2019/2/5 0005 14:47
 * @Description:
 */
@Service
public class PageServiceImpl implements PageService {
    @Autowired
    CmsPageRepository cmsPageRepository;
    @Autowired
    CmsSiteRepository cmsSiteRepository;
    @Autowired
    GridFSBucket gridFSBucket;
    @Autowired
    GridFsTemplate gridFsTemplate;
    /**
     * @Description 将页面html保存到页面物理路径
     * @Param [pageId]
     * @return void
     **/
    public void savePageToServerPath(String pageId) {
        //
        Optional<CmsPage> optionalCmsPage = cmsPageRepository.findById(pageId);
        CmsPage cmsPage = null;
        if (optionalCmsPage.isPresent()){
            cmsPage = optionalCmsPage.get();
        }
        String siteId = cmsPage.getSiteId();
        Optional<CmsSite> optionalCmsSite = cmsSiteRepository.findById(siteId);
        CmsSite cmsSite = optionalCmsSite.get();
        //site物理地址（此处就是门户项目物理地址）
        String sitePhysicalPath = cmsSite.getSitePhysicalPath();
        //页面物理地址
        String pagePhysicalPath = cmsPage.getPagePhysicalPath();
        //页面名称
        String pageName = cmsPage.getPageName();
       // html保存到页面物理路径
        String path = sitePhysicalPath + pagePhysicalPath + pageName;
        String htmlFileId = cmsPage.getHtmlFileId();
        GridFSFile  gridFSFile = gridFsTemplate.findOne(Query.query(Criteria.where("_id").is(htmlFileId)));
        ObjectId objectId = gridFSFile.getObjectId();
        GridFSDownloadStream gridFSDownloadStream = gridFSBucket.openDownloadStream(objectId);
        GridFsResource gridFsResource = new GridFsResource(gridFSFile, gridFSDownloadStream);
        InputStream inputStream = null;
        OutputStream outputStream = null;
        try {
            inputStream =  gridFsResource.getInputStream();
            outputStream = new FileOutputStream(new File(path));
            IOUtils.copy(inputStream,outputStream);
        } catch (Exception e) {
            e.printStackTrace();
        }
        finally {
            if (ObjectUtil.isNotNull(outputStream)){
                try {
                    outputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if(ObjectUtil.isNotNull(inputStream)){
                try {
                    inputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

    }
}
