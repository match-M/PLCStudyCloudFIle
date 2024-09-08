package com.match.plcstudycloudfile.service.file;

import com.alibaba.fastjson.JSONObject;
import jakarta.servlet.http.Part;



public interface FileServiceApi {

    /**
     * 保存文件
     * @param path 文件路径
     * @param part 上传的对象
     * @return 是否保存成功，true - 是， false - 否
     */
    boolean saveFile(String path, Part part);

    /**
     * 获取所有文件信息
     * @return Json数据格式，每个数组对应一个日期下的项目
     */
    String getAllFileInfo();
}
