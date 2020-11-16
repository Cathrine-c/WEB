package java_1113.java;


import java.io.*;

/**
 * 字符流的使用
 */

public class IODemo4 {
    public static void main(String[] args) {
        copyFile();

    }

    private static void copyFile(){
        try (FileReader fileReader = new FileReader("d:/tes.txt");
            FileWriter fileWriter = new FileWriter("d:/tes1.txt")){
            char[] buffer = new char[1024];
            int len=-1;
            while ((len=fileReader.read(buffer))!=-1){
                fileWriter.write(buffer,0,len);
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    //带缓冲区的方法
    private static void copyFile1(){
        try(BufferedReader bufferedReader = new BufferedReader(new FileReader("d:/tes.txt"));
        BufferedWriter  bufferedWriter = new BufferedWriter(new FileWriter("d:/tes1.txt"))) {
            char[] buffer = new char[1024];
            int len=-1;
            while ((len=bufferedReader.read(buffer))!=-1){
                bufferedWriter.write(buffer,0,len);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    //带缓冲区的字符流有一种特殊的用法，按行读取
    private static void copyFile2(){
        try(BufferedReader bufferedReader = new BufferedReader(new FileReader("d:/tes.txt"));
            BufferedWriter  bufferedWriter = new BufferedWriter(new FileWriter("d:/tes1.txt"))) {
            String line = "";
            while ((line=bufferedReader.readLine())!=null){
                System.out.println("line:"+line);
                bufferedWriter.write(line+"\n");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
