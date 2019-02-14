package com.xuecheng.manage_cms.config;

import com.mongodb.MongoClient;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.gridfs.GridFSBucket;
import com.mongodb.client.gridfs.GridFSBuckets;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @Auther: 段金良
 * @Date: 2019/2/1 0001 21:58
 * @Description:
 */
@Configuration
public class MongoDbConfig {
    @Value("${spring.data.mongodb.database}")
    String mongdb;
    /**
     * @Description  GridFSBucket用于打开下载流
     * @Param [mongoClient]
     * @return com.mongodb.client.gridfs.GridFSBucket
     **/
    @Bean
    public GridFSBucket getGridFSBucket(MongoClient mongoClient){
        MongoDatabase database = mongoClient.getDatabase(mongdb);
        GridFSBucket bucket = GridFSBuckets.create(database);
        return bucket;
    }

}
