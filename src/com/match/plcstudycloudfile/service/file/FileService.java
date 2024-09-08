package com.match.plcstudycloudfile.service.file;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.match.plcstudycloudfile.constant.FileTypeText;
import jakarta.servlet.http.Part;

import java.io.File;
import java.io.IOException;
import java.io.Serial;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

import static com.match.plcstudycloudfile.constant.FileConfig.FILE_SAVE_PATH;

public class FileService implements FileServiceApi{



    /**
     * 保存文件
     * @param path 文件路径
     * @param part 上传的对象
     * @return 是否保存成功，true - 是， false - 否
     */
    @Override
    public boolean saveFile(String path, Part part) {
        String filename = part.getSubmittedFileName(); //获取文件名称

        File file = new File(FILE_SAVE_PATH + path);
        if(!file.exists()){
            boolean success = file.mkdirs();
            if(!success) return false;
        }

        try {
            part.write(FILE_SAVE_PATH + path +"/" + filename); //写入文件
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }

    /**
     * 获取所有文件信息
     *
     * @return Json数据格式，每个数组对应一个日期下的项目
     */
    @Override
    public String getAllFileInfo() {
        StringBuilder readPath;
        File path = new File(FILE_SAVE_PATH);
        if(!path.exists()){
            boolean success = path.mkdirs();
            if(!success) return null;
        }
        String[] rootDir = path.list();
        if(rootDir == null) return null;

        ArrayList<String> rootDirList = new ArrayList<>();
        Collections.addAll(rootDirList, rootDir); //数组里的数据全部添加到列表里
        //排序
        Collections.sort(rootDirList, new Comparator<String>() {
            final DateFormat DATE_FORMAT = new SimpleDateFormat("yyyy年MM月dd日");
            @Override
            public int compare(String o1, String o2) {
                try {
                    return DATE_FORMAT.parse(o1).compareTo(DATE_FORMAT.parse(o2));
                } catch (ParseException e) {
                    throw new RuntimeException(e);
                }
            }
        });

        rootDir = rootDirList.toArray(new String[rootDirList.size()+1]); //重新转换为数组

        String[] projectDir;
        String[] projectFiles;

        JSONObject tempJson;
        JSONArray tempJsonArray;
        JSONArray dateDirFileArray = new JSONArray();


        for(String rootDirFile : rootDir){

            readPath = new StringBuilder(FILE_SAVE_PATH + rootDirFile + "/");
            path = new File(readPath.toString());
            projectDir = path.list();
            if(projectDir == null){
                dateDirFileArray.add("");
                continue;
            }

            for(String projectDirFile : projectDir){
                tempJson = new JSONObject();
                tempJson.put("date", rootDirFile);
                tempJson.put("projectName", projectDirFile);

                int projectPathIndex = readPath.length() - 1;
                readPath.append(projectDirFile).append("/");

                path = new File(readPath.toString());
                projectFiles = path.list();
                if(projectFiles == null){
                    dateDirFileArray.add(tempJson);
                    continue;
                }

                for(String projectFile : projectFiles){
                    int index = readPath.length();
                    readPath.append(projectFile).append("/");
                    path = new File(readPath.toString());
                    String[] tempDir = path.list();
                    if(tempDir == null) continue;
                    tempJsonArray = new JSONArray();

                    String key;
                    if(projectFile.equals(FileTypeText.TOPIC)) key = "topic";
                    else if(projectFile.equals(FileTypeText.LADDER_DIAGRAM)) key = "ladderDiagram";
                    else key = "touchScreen";

                    tempJsonArray.addAll(Arrays.asList(tempDir));
                    tempJson.put(key, tempJsonArray);

                    //删除这次查找的路径
                    readPath.delete(index, readPath.length()-1);
                }
                readPath.delete(projectPathIndex, readPath.length()-1);
                dateDirFileArray.add(tempJson);
            }
        }
        return dateDirFileArray.toJSONString();
    }
}
