package servlets;

import dataBase.UserDao;
import model.User;
import org.apache.commons.fileupload.FileItemIterator;
import org.apache.commons.fileupload.FileItemStream;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.apache.commons.fileupload.util.Streams;
import org.apache.log4j.Logger;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;
import java.sql.SQLException;

@WebServlet("/userImg")
public class UserImgServlet extends HttpServlet {

    private static Logger logger = Logger.getLogger(UserImgServlet.class);

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setCharacterEncoding("UTF-8");
        String id;
        if (req.getParameter("id") != null)
            id = req.getParameter("id");
        else if (req.getParameter("name") != null) {
            String name = req.getParameter("name");
            User user = null;
            try {
                user = UserDao.getByName(name);
            } catch (SQLException e) {
                e.printStackTrace();
                logger.error(e.getMessage());
            }
            id = String.valueOf(user.getId());
        } else
            return;

        ServletContext cntx = req.getServletContext();
        File dir = new File("usersImages");
        String[] fileList = dir.list();
        String ext = null;
        if (fileList == null)
            return;
        for (String s : fileList) {
            if (s.contains(id + "")) {
                String[] split = s.split("\\.");
                ext = split[split.length - 1];
                break;
            }
        }
        String filename = "usersImages\\" + id + "." + ext;
        String mime = cntx.getMimeType(filename);
        if (mime == null) {
            resp.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            logger.error("Error " + resp.getStatus());
            return;
        }

        resp.setContentType(mime);
        File file = new File(filename);
        resp.setContentLength((int) file.length());

        FileInputStream in = new FileInputStream(file);
        OutputStream out = resp.getOutputStream();

        byte[] buf = new byte[1024];
        int count = 0;
        while ((count = in.read(buf)) >= 0) {
            out.write(buf, 0, count);
        }
        out.close();
        in.close();
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setCharacterEncoding("UTF-8");
        if (ServletFileUpload.isMultipartContent(req)) {
            try {
                ServletFileUpload upload = new ServletFileUpload();
                FileItemIterator iter = upload.getItemIterator(req);
                User user = new User();
                String uploadPath = "usersImages";
                File tempDir = new File(uploadPath + File.separator + "temp");
                if(!tempDir.exists()){
                    tempDir.mkdirs();
                }
                String filePathSrc = null;
                while (iter.hasNext()) {
                    FileItemStream item = iter.next();
                    String name = item.getFieldName();
                    InputStream stream = item.openStream();
                    if (item.isFormField()) {
                        String val = Streams.asString(stream, "UTF-8");
                        switch (name) {
                            case "name":
                                user.setName(val);
                                break;
                            case "FIO":
                                user.setFIO(val);
                                break;
                            case "tel":
                                user.setTel(val);
                                break;
                            case "e_mail":
                                user.setE_mail(val);
                                break;
                            case "password":
                                user.setPassword(val);
                                break;
                        }
                    } else {
                        filePathSrc = tempDir + File.separator + item.getName();
                        Streams.copy(stream, new FileOutputStream(filePathSrc), true);
                    }
                }
                try {
                    UserDao.addUser(user);
                } catch (SQLException e) {
                    e.printStackTrace();
                    logger.error(e.getMessage());
                }

                if (filePathSrc != null) {
                    String[] split = filePathSrc.split("\\.");
                    if (split.length > 1) {
                        String extension = split[split.length - 1];
                        Files.move(new File(filePathSrc).toPath(),
                                new File(uploadPath + File.separator
                                        + user.getId() + "." + extension).toPath(), StandardCopyOption.REPLACE_EXISTING);
                    }
                }
                resp.sendRedirect("login.html");
            } catch (FileUploadException e) {
                System.out.println(e.getMessage());
            }
        }
    }
}
