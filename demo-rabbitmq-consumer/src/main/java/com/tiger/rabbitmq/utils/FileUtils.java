package com.tiger.rabbitmq.utils;

import com.tiger.rabbitmq.dto.AppResultObj;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.Part;
import java.io.*;

public class FileUtils {

    private static final Logger logger = LoggerFactory.getLogger(FileUtils.class);

    /**
     * 上传文件方法
     *
     * @param is
     * @param os
     */
    public static void uploadFile(InputStream is, OutputStream os) {
        // 缓存大小，一次读取1024个字节
        byte[] buff = new byte[1024];
        int len = 0;
        try {
            while ((len = is.read(buff)) != -1) {
                os.write(buff, 0, len);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 获取字节数组
     *
     * @param filePart
     * @return
     */
    public static byte[] getFileBytes(Part filePart) {
        InputStream fileInputStream = null;
        ByteArrayOutputStream byteArrayOutputStream = null;
        try {
            // 将输入流的数据写到byte数组
            fileInputStream = filePart.getInputStream();
            byteArrayOutputStream = new ByteArrayOutputStream();

            // 字节数组
            byte buf[] = new byte[1024];
            int len = -1;
            while ((len = fileInputStream.read(buf)) != -1) {
                byteArrayOutputStream.write(buf, 0, len);
                byteArrayOutputStream.flush();
            }
            return byteArrayOutputStream.toByteArray();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 获取上传文件名
     *
     * @param part
     * @return
     */
    public static String getFileName(Part part) {
        String header = part.getHeader("Content-Disposition");
        String fileName = header.substring(header.indexOf("filename=\"") + 10, header.lastIndexOf("\""));
        return fileName;
    }

    /**
     * 获取文件上传路径
     *
     * @param request HttpServletRequest请求对象
     * @param upload  文件上传的文件夹
     * @return
     */
    public static String getUploadFolder(HttpServletRequest request, String upload) {
        // 获取上传文件的路径名：upload为自己设置
        String uploadFolder = request.getServletContext().getRealPath(File.separator + upload) + File.separator;
        // 以上一步获取的文件路径名创建上传文件夹
        File file = new File(uploadFolder);
        // 如果文件夹不存在，则创建
        if (!file.exists()) {
            file.mkdirs();
        }
        return uploadFolder;
    }

    public static void copyPath(String srcPath, String destPath) throws IOException {
        InputStream is = new BufferedInputStream(new FileInputStream(new File(srcPath)));
        OutputStream os = new BufferedOutputStream(new FileOutputStream(new File(destPath)));
        // 文件拷贝u，-- 循环+读取+写出
        byte[] b = new byte[10];// 缓冲大小
        int len = 0;// 接收长度
        // 读取文件
        while (-1 != (len = is.read(b))) {
            // 写出文件
            os.write(b, 0, len);
        }
        // 强制刷出数据
        os.flush();
        // 关闭流，先开后关
        os.close();
        is.close();
    }

    /**
     * Java文件操作 获取文件扩展名
     *
     * @param filename
     * @return
     */
    public static String getExtensionName(String filename) {
        if ((filename != null) && (filename.length() > 0)) {
            int dot = filename.lastIndexOf('.');
            if ((dot > -1) && (dot < (filename.length() - 1))) {
                return filename.substring(dot + 1);
            }
        }
        return filename;
    }

    /**
     * Java文件操作 获取不带扩展名的文件名
     *
     * @param filename
     * @return
     */
    public static String getFileNameNoEx(String filename) {
        if ((filename != null) && (filename.length() > 0)) {
            int dot = filename.lastIndexOf('.');
            if ((dot > -1) && (dot < (filename.length()))) {
                return filename.substring(0, dot);
            }
        }
        return filename;
    }

    /**
     * 单文件上传
     *
     * @param uploadDir
     * @param file
     * @return
     */
    public static AppResultObj uploadOne(String uploadDir, MultipartFile file) {
        //时间戳
        long time = System.currentTimeMillis();
        //文件名
        String filename = file.getOriginalFilename();
        // 文件存储全路劲（包括文件名）
        String path = uploadDir + time + "_" + filename;
        logger.info("===上传文件的全路径===【{}】", path);
        try {
            //服务器文件夹对象
            File serverFile = new File(path);
            // 服务器文件夹不存在时自动创建
            if (!serverFile.getParentFile().exists()) {
                serverFile.getParentFile().mkdirs();// 新建文件夹
            }
            //将文件写入服务器文件夹内
            file.transferTo(serverFile);
        } catch (IOException e) {
            e.printStackTrace();
            logger.info("文件【" + filename + "】上传失败！");
            return AppResultObj.serverError("文件【" + filename + "】上传失败！" + e.getMessage());
        }
        return AppResultObj.success(path);
    }

}
