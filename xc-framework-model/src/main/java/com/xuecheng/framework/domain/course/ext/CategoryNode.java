package com.xuecheng.framework.domain.course.ext;

import lombok.Data;
import lombok.ToString;

import java.util.List;

/**
 * Created by admin on 2018/2/7.
 */
@Data
@ToString
public class CategoryNode  {
    List<CategoryNode> children;
    String value;
    String label;

}
