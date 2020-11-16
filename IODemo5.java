package java_1113.java;

import java.io.*;

/**
 * 序列化/反序列化的实现
 */

class Student implements Serializable{
    public String name;
    public int age;
    public int score;

}
public class IODemo5 {
    public static void main(String[] args) throws IOException, ClassNotFoundException {
        //Student s = new Student();
//        s.name = "张三";
//        s.age = 20;
//        s.score = 90;
//        serializeStudent(s);
        Student s = deserializeStudent();
        System.out.println(s.name);
        System.out.println(s.age);
        System.out.println(s.score);
    }

    private static void serializeStudent(Student s) throws IOException {
        ObjectOutputStream objectOutputStream = new ObjectOutputStream(new FileOutputStream("d:/tes.txt"));
        objectOutputStream.writeObject(s);
        objectOutputStream.close();
    }

    private static Student deserializeStudent() throws IOException, ClassNotFoundException {
        ObjectInputStream objectInputStream = new ObjectInputStream(new FileInputStream("d:/tes.txt"));
        Student s = (Student) objectInputStream.readObject();
        return s;
    }

}
