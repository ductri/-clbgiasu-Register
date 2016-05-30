

import java.io.File;
import java.io.IOException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;

import Utils.Utils;
import bussiness.NetworkBussiness;
import bussiness.Student;

/**
 * Servlet implementation class Add
 */
@WebServlet("/Add")
@MultipartConfig(fileSizeThreshold=1024*1024*2, // 2MB
maxFileSize=1024*1024*10,      // 10MB
maxRequestSize=1024*1024*50)   // 50MB
public class Add extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private static final String SAVE_DIR = "uploadFiles";
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public Add() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		NetworkBussiness net = new NetworkBussiness();
		
		String avatarUrl = "";//handlerFileUpload(request);
		
		String name = request.getParameter("name");
		String gioitinh = request.getParameter("gioitinh");
		String nam = request.getParameter("nam");
		String khoa = request.getParameter("khoa");
		String ngaysinh = request.getParameter("ngaysinh");
		if (ngaysinh!="") {
			try {
				SimpleDateFormat dt = new SimpleDateFormat("yyyy-mm-dd");
				Date date = dt.parse(ngaysinh);
				dt = new SimpleDateFormat("dd/mm/yyyy");
				ngaysinh = dt.format(date).toString();
			} catch (ParseException e1) {
				// TODO Auto-generated catch block
				//e1.printStackTrace();
			}
		}
		String sdt = request.getParameter("sdt");
		String mssv = request.getParameter("mssv");
		String email = request.getParameter("email");
		String youtube = request.getParameter("youtube");
		String diachi = request.getParameter("diachi");
		String fb = request.getParameter("fb");
		String thanhtich = request.getParameter("thanhtich");
		String quequan = request.getParameter("quequan");
		String daymon = request.getParameter("daymon");
		String giongnoi = request.getParameter("giongnoi");
		String thongtinkhac = request.getParameter("thongtinkhac");
		
		Connection conn = Utils.connectDB();
		if (conn != null) {
			String sql = "SELECT mssv FROM member WHERE mssv='"+mssv+"'";
			try {
				Statement stmt = conn.createStatement();
				
				ResultSet result = stmt.executeQuery(sql);
				
				if (result.next()) {
					int numRow= result.getRow();
					if (numRow == 0) {
						response.getWriter().println("You were not a member");
						return;
					}
				} else {
					response.getWriter().println("No result");
					return;
				}
				stmt.close();
			} catch (SQLException e) {
				e.printStackTrace();
				response.getWriter().println(e.toString());
				return;
			}
			
		} else {
			response.getWriter().println("Connection is null");
			return;
		}
		
		Student student = new Student(name, gioitinh, avatarUrl, nam, khoa, ngaysinh, sdt, 
				mssv, email, youtube, diachi, fb, thanhtich, quequan, daymon, 
				giongnoi, thongtinkhac);
		boolean isSuccess = false;
//		try {
//			isSuccess = net.register(student);
//			if (isSuccess) {
//				response.getWriter().println("Register successfully");
//				Utils.inCreaseNoAccess();
//			} else {
//				response.getWriter().println("Register failure");
//			}
//		} catch (Exception e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//			response.getWriter().println("503");
//		}
		net.login();
		System.out.println("Check login");
		net.checkLogin();
		net.addNewMember(student);
	}
	
	private String handlerFileUpload(HttpServletRequest request) {
		// gets absolute path of the web application
        String appPath = request.getServletContext().getRealPath("");
        // constructs path of the directory to save uploaded file
        String savePath = appPath + File.separator + SAVE_DIR;
         
        // creates the save directory if it does not exists
        File fileSaveDir = new File(savePath);
        if (!fileSaveDir.exists()) {
            fileSaveDir.mkdir();
        }
        
        try {
			Part part = request.getPart("file");
			if (part == null) {
				return "";
			}
			String fileName = extractFileName(part);
			String fileUrl = savePath + File.separator + fileName;
			part.write(fileUrl);
			return fileUrl;
		} catch (IllegalStateException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ServletException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
 
        return "";
	}
	/**
     * Extracts file name from HTTP header content-disposition
     */
    private String extractFileName(Part part) {
        String contentDisp = part.getHeader("content-disposition");
        String[] items = contentDisp.split(";");
        for (String s : items) {
            if (s.trim().startsWith("filename")) {
                return s.substring(s.indexOf("=") + 2, s.length()-1);
            }
        }
        return "";
    }
}
