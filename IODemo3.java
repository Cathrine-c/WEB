package java_1113.java;



import java.io.*;

/**
 * 缓冲区的使用效率：通过使用和不使用缓冲区来感受效率
 */
public class IODemo3 {
    public static void main(String[] args) throws IOException {
        //testNoBuffer();
        testBuffer();

    }

    private static void testNoBuffer() throws IOException {
        long beg = System.currentTimeMillis();
        try (FileInputStream fileInputStream = new FileInputStream("d:/test_dir/1/21.jpg")){

            int ch  = -1;
            while ((ch=fileInputStream.read())!=-1){

            }
        }catch (IOException e){
            e.printStackTrace();
        }

        long end = System.currentTimeMillis();
        System.out.println("no buffer:"+ (end-beg)+"ms");
    }

    private static void testBuffer(){
        long beg = System.currentTimeMillis();
        try(BufferedInputStream bufferedInputStream = new BufferedInputStream(new FileInputStream("d:/test_dir/1/21.jpg"))) {
             int ch =-1;
             while ((ch=bufferedInputStream.read())!=-1){

             }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        long end = System.currentTimeMillis();
        System.out.println("has buffer:"+ (end-beg)+"ms");
    }
}
