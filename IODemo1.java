package java_1113.java;
/**
 *
 *File的使用：
 * 用来描述文件属性的类，针对文件目录进行各种操作
 */

import com.sun.xml.internal.ws.policy.privateutil.PolicyUtils;

import java.io.File;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

public class IODemo1 {
    //IOException是受查异常,需要显示处理
    public static void main1(String[] args) throws IOException {
//        File file = new File("d:/test.txt");
//        System.out.println("文件是否存在:"+file.exists());
//        System.out.println("文件是否是目录:"+file.isDirectory());
//        System.out.println("文件是否是普通文件:"+file.isFile());
//        System.out.println(file.delete());
//        System.out.println("文件是否存在"+file.exists());
//        file.createNewFile();
//        System.out.println("文件是否存在："+file.exists());

        //创建目录
//        File file = new File("d:/test_dir/1/2/3/4");
//        file.mkdirs();

        File file = new File("d:/test_dir");
        File[] files = file.listFiles();
        for (File f:files) {
            System.out.println(f);
        }


        listAllFiles(file);//常见面试题

    }


    private static void listAllFiles(File f){
        if (f.isDirectory()) {
            File[] files = f.listFiles();
            for (File file:files){
                System.out.println(file);
            }
        }else{
            System.out.println("f");
        }
    }

    //递归地罗列出一个目录中所有的文件
    private static void listAllFiles1(File f){
        if (f.isDirectory()) {
            //如果是目录，就把目录中包含的文件都罗列出来
            File[] files = f.listFiles();
            for (File file:files){
                listAllFiles(file);
            }
        }else{
            //把这个文件的路径直接打印出来
            System.out.println("f");
        }
    }


    /**
     *  * File虽然能进行一些常规的文件操作，但是少了两个核心操作：1.读文件
     *  * 2.写文件
     *  * Java中针对文件操作，又进行了进一步的抽象，流是一组类/一组API，
     *  * 描述了如何来进行读写操作，不同的类进行读写操作方式都会有差异
     *  *流对象的核心操作有三个：
     *  * 1.打开文件
     *  * 2.read：从文件把数据读入到内存中
     *  * 3.write：把数据从内存中写入文件
     *  4.close:需要把文件关闭，不然可能会造成文件资源泄露
     */

    //找一个二进制文件，实现文件的拷贝
    public static void main(String[] args) throws IOException {
        copyFile("d:/test_dir/1/21.jpg","d:/test_dir/1/22.jpg");

    }

    private static void copyFile(String srcPath,String descPath) throws IOException {

        //0.打开文件
        FileInputStream fileInputStream = new FileInputStream(srcPath);
        FileOutputStream fileOutputStream = new FileOutputStream(descPath);

        //1.读取文件内容src
        byte[] buffer = new byte[1024];
        int len =fileInputStream.read(buffer);
        while ((len=fileInputStream.read(buffer))!=-1){
            //2.读取到的内容写入destPath对应的文件中
            fileOutputStream.write(buffer,0,len);
        }
        fileInputStream.close();
        fileOutputStream.close();
    }


         //改进
    private static void copyFile2(String srcPath,String descPath) throws IOException {

        FileInputStream fileInputStream = null;
        FileOutputStream fileOutputStream = null;
        try {
            //0.打开文件
             fileInputStream = new FileInputStream(srcPath);
             fileOutputStream = new FileOutputStream(descPath);

            //1.读取文件内容src
            byte[] buffer = new byte[1024];
            int len = fileInputStream.read(buffer);
            while ((len = fileInputStream.read(buffer)) != -1) {
                //2.读取到的内容写入destPath对应的文件中
                fileOutputStream.write(buffer, 0, len);
            }
        }catch (IOException e){
            e.printStackTrace();
        }finally {
            try {
                //close也需要处理IOException
                if (fileInputStream!=null) {
                    fileInputStream.close();
                }
                if (fileOutputStream!=null) {
                    fileOutputStream.close();
                }
            }catch (IOException e){
                e.printStackTrace();
            }
        }
    }


         //进一步改进
    private static void copyFile3(String srcPath,String descPath) throws IOException {
        try {
            //0.打开文件
          FileInputStream  fileInputStream = new FileInputStream("d:/test_dir/1/21.jpg");
          FileOutputStream  fileOutputStream = new FileOutputStream("d:/test_dir/1/22.jpg");
        byte[] buffer = new byte[1024];
        int len = -1;
        while ((len=fileInputStream.read(buffer))!=-1){
            fileOutputStream.write(buffer);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }



}



