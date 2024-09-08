package com.match.plcstudycloudfile.servlet;

import com.match.plcstudycloudfile.service.file.FileService;
import com.match.plcstudycloudfile.service.file.FileServiceApi;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.*;
import java.io.IOException;
import java.io.PrintWriter;

@WebServlet(name = "getFileInfo", urlPatterns = "/plc/getFileInfo")
public class GetFileInfoServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        PrintWriter out = response.getWriter();
        out.write(new FileService().getAllFileInfo());
        out.close();
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    }
}
