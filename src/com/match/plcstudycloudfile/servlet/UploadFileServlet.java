package com.match.plcstudycloudfile.servlet;

import com.alibaba.fastjson.JSONObject;
import com.match.plcstudycloudfile.constant.FileType;
import com.match.plcstudycloudfile.constant.FileTypeText;
import com.match.plcstudycloudfile.service.file.FileService;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.MultipartConfig;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.*;

import javax.xml.crypto.Data;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.Format;
import java.text.SimpleDateFormat;
import java.util.Date;

@WebServlet(name = "uploadFile", urlPatterns = "/plc/uploadFile")
@MultipartConfig
public class UploadFileServlet extends HttpServlet {

    private static SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy年MM月dd日");
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String date = dateFormat.format(new Date());
        String projectName = request.getParameter("projectName");
        int fileType = Integer.parseInt(request.getParameter("type"));

        String typeText;
        if(fileType == FileType.TOPIC) typeText = FileTypeText.TOPIC;
        else if(fileType == FileType.LADDER_DIAGRAM) typeText = FileTypeText.LADDER_DIAGRAM;
        else typeText = FileTypeText.TOUCH_SCREEN;

        String path = date + "/" + projectName + "/" + typeText;
        boolean success = new FileService().saveFile(path, request.getPart("file"));

        JSONObject responseJson = new JSONObject();
        PrintWriter out = response.getWriter();

        responseJson.put("code", success ? 200 : -1);
        responseJson.put("msg", success ? "success" : "fail");

        out.write(responseJson.toJSONString());
        out.close();

        responseJson.clear();

    }

}
