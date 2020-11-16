package java_1113.java;


import java.io.*;

/**
 * 字节流的使用
 */

/**
 * BufferedInputStream、BufferedOutputStream 内置了缓存区
 * 提高程序运行效率
 */

 public class IODemo2 {
     public static void main(String[] args) throws IOException {
            copyFile1();
     }

//    private static void copyFile() throws IOException {
//         //需要创建的实例是：BufferedInputStream
//        //要创建这样的实例，需要先创建FileInputStream
//        FileInputStream fileInputStream = new FileInputStream("d:/test_dir/1/21.jpg");
//        FileOutputStream fileOutputStream = new FileOutputStream("d:/test_dir/1/22.jpg");
//        BufferedInputStream bufferedInputStream = new BufferedInputStream(fileInputStream);
//        BufferedOutputStream bufferedOutputStream = new BufferedOutputStream(fileOutputStream);
//
//        byte[] buffer = new byte[1024];
//        int length = -1;
//        while ((length=BufferedInputStream.read(buffer))!=-1){
//
//            bufferedOutputStream.write(buffer,0,length);
//        }
//        BufferedInputStream.close();
//        BufferedOutputStream.close();
//    }


    private static void copyFile1() throws IOException {
        try(BufferedInputStream bufferedInputStream = new BufferedInputStream(new FileInputStream("d:/test_dir/1/21.jpg"));
        BufferedOutputStream bufferedOutputStream = new BufferedOutputStream(new FileOutputStream("d:/test_dir/1/22.jpg"))) {
            int len = -1;
            byte[] buffer = new byte[1024];
            while ((len = bufferedInputStream.read(buffer)) != -1) {
                System.out.println(len);
                bufferedOutputStream.write(buffer, 0, len);
            }
        }catch (IOException e){
            e.printStackTrace();
        }
     }
}



