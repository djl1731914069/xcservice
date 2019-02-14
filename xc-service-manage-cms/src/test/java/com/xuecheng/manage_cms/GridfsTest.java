package com.xuecheng.manage_cms;

import com.mongodb.client.gridfs.GridFSBucket;
import com.mongodb.client.gridfs.GridFSDownloadStream;
import com.mongodb.client.gridfs.model.GridFSFile;
import org.apache.commons.io.IOUtils;
import org.bson.types.ObjectId;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.gridfs.GridFsResource;
import org.springframework.data.mongodb.gridfs.GridFsTemplate;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.*;

/**
 * @Auther: 段金良
 * @Date: 2019/2/1 0001 21:32
 * @Description:
 */
@SpringBootTest
@RunWith(SpringRunner.class)
public class GridfsTest {
    @Autowired
    GridFsTemplate gridFsTemplate;

    @Autowired
    GridFSBucket gridFSBucket;

    @Test
    public void s() throws Exception {
        InputStream i = new FileInputStream(new File("d:/index_banner.ftl"));
        ObjectId objectId = gridFsTemplate.store(i, "首页banner");
        System.out.println(objectId.toString());
    }
    @Test
    public void s2() throws Exception {
        String fileId = "5c544c65ae93b41b546f3ba4";
//根据id查询文件
        GridFSFile gridFSFile =
                gridFsTemplate.findOne(Query.query(Criteria.where("_id").is(fileId)));
//打开下载流对象
        GridFSDownloadStream gridFSDownloadStream =
                gridFSBucket.openDownloadStream(gridFSFile.getObjectId());
//创建gridFsResource，用于获取流对象
        GridFsResource gridFsResource = new GridFsResource(gridFSFile,gridFSDownloadStream);
//获取流中的数据
        InputStream inputStream = gridFsResource.getInputStream();
        OutputStream f = new FileOutputStream(new File("d:/a.html"));
        IOUtils.copy(inputStream,f);

    }
    //删除文件
    @Test
    public void testDelFile() throws IOException {
//根据文件id删除fs.files和fs.chunks中的记录
        gridFsTemplate.delete(Query.query(Criteria.where("_id").is("5c544c65ae93b41b546f3ba4")));
    }
}
