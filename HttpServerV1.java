import javax.imageio.stream.ImageInputStream;
import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.Executor;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

//Http是基于TCP开发的
public class HttpServerV1 {
    private ServerSocket serverSocket = null;

    public HttpServerV1(int port) throws IOException {
        serverSocket = new ServerSocket(port);
    }

    public void start() throws IOException {
        System.out.println("服务器启动");
        ExecutorService executorService = Executors.newCachedThreadPool();
        while (true){
            //1.获取连接
            Socket clientSocket = serverSocket.accept();
            executorService.execute(new Runnable() {
                @Override
                public void run() {
                    process(clientSocket);
                }
            });

            //2.处理连接(使用短连接的方式来实现）
            process(clientSocket);
        }
    }

    private void process(Socket clientSocket) {
        //由于Http是文本协议，所以仍然使用字符流来处理
        try (BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
             BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(clientSocket.getOutputStream()))) {

            //1.读取请求并解析
            //a)解析首行,三个部分使用空格切分
            String firstLine = bufferedReader.readLine();
            String[] firstLineTokens = firstLine.split("");
            String method = firstLineTokens[0];
            String url = firstLineTokens[1];
            String version = firstLineTokens[2];
            //b)解析header，按行读取，然后按照冒号空格来分割键值对
            Map<String, String> headers = new HashMap<>();
            String line = "";
            //readLine读取的一行内容，会自动去掉换行符，对于空格来说，去掉了换行符，就变成了空字符串
            while ((line = bufferedReader.readLine()) != null) {
                //不能使用：来切分，像referer字段，里面的内容可能包含：.
                String[] headerTokens = line.split("");
                headers.put(headerTokens[0], headerTokens[1]);

            }
            //c)解析body（暂时先不考虑）
            System.out.printf("%s %s %s\n", method, url, version);
            for (Map.Entry<String, String> entry : headers.entrySet()) {
                System.out.println(entry.getKey() + ":" + entry.getValue() + "\n");
            }
            System.out.println();

            //2.根据请求计算响应
            String resp = "";
            if (url.equals("/ok")){
                bufferedWriter.write(version+"200 OK");
                resp = "<h1>hello</h1>";
            }else if(url.equals("/notfound")){
                bufferedWriter.write(version+"404 Not Found\n");
                resp = "<h1>not found</h1>";
            }else if(url.equals("/seeother")){
                bufferedWriter.write(version+"303 See Othern");
                bufferedWriter.write("Location:http://www.sogou.com\n");
                resp = "";
            } else{
                bufferedWriter.write(version+"200 OK\n");
                resp = "<h1>default</h1>";
            }


            //3.把响应写回到客户端
            bufferedWriter.write(version + "200 OK\n");
            bufferedWriter.write("Content-type:text/html\n");
            bufferedWriter.write("Content-Length:" + resp.getBytes().length + "\n");
            //不能写成resp.length(),这样计算的是字符流的长度，而不是字节的长度
            bufferedWriter.write("\n");
            bufferedWriter.write(resp);
            bufferedWriter.flush();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                clientSocket.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public static void main(String[] args) throws IOException {
        HttpServerV1 serverV1 = new HttpServerV1(9090);
        serverV1.start();
    }

    }

