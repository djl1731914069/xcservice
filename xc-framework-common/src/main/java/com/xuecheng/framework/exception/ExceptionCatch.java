package com.xuecheng.framework.exception;

import com.xuecheng.framework.model.response.CommonCode;
import com.xuecheng.framework.model.response.ResponseResult;
import com.xuecheng.framework.model.response.ResultCode;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

/**
 * @Auther: 段金良
 * @Date: 2019/1/31 0031 16:42
 * @Description:
 */
//控制层通知
@ControllerAdvice
public class ExceptionCatch {
 public static final Logger logger = LoggerFactory.getLogger(ExceptionCatch.class);


 //使用EXCEPTIONS存放异常类型和错误代码的映射，ImmutableMap的特点的一旦创建不可改变，并且线程安全
 public  static ConcurrentMap<Class<?extends Exception>,ResultCode> exceptMap = new ConcurrentHashMap();
    static{
//在这里加入一些基础的异常类型判断
        exceptMap.put(HttpMessageNotReadableException.class, CommonCode.INVALID_PARAM);
    }
    //抓取异常
    @ExceptionHandler(ZdyException.class)
    @ResponseBody
    public ResponseResult responseResult(ZdyException e){
        logger.error("------捕获异常："+e.getMessage());
        ResultCode resultCode = e.getResultCode();
        return new ResponseResult(resultCode);
    }
    //抓取异常
    @ExceptionHandler(Exception.class)
    @ResponseBody
    public ResponseResult responseResult(Exception e){
        logger.error("------捕获异常："+e.getMessage());
        ResultCode resultCode = exceptMap.get(e.getClass());
        ResponseResult responseResult;
        if (resultCode != null) {
            responseResult = new ResponseResult(resultCode);
        } else {
            responseResult = new ResponseResult(CommonCode.SERVER_ERROR);
        }
        return responseResult;
    }
}
