import com.sun.deploy.uitoolkit.impl.fx.AppletStageManager;
import com.sun.java.swing.plaf.windows.WindowsTabbedPaneUI;

import javax.imageio.stream.ImageInputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.Executor;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class HttpServerV2 {
    private ServerSocket serverSocket = null;

    public HttpServerV2(int port) throws IOException {
        serverSocket = new ServerSocket(port);
    }

    public  void start() throws IOException {
        System.out.println("服务器启动");
        ExecutorService executorService = Executors.newCachedThreadPool();
        while (true){
            Socket clientSocket = serverSocket.accept();
            executorService.execute(new Runnable() {
                @Override
                public void run() {
                    try {
                        process(clientSocket);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            });
        }
    }

    private void process(Socket clientSocket) throws IOException {
        try {
            //1.读取并解析请求
            Httprequest request = Httprequest.build(clientSocket.getInputStream());
            System.out.println("request:"+request);
            HttpResponse response = HttpResponse.build(clientSocket.getOutputStream());
            response.flush();
            response.setHeader("Content-Type","text/html");

            //2.根据请求计算响应
            if(request.getUrl().startsWith("/hello")){
                response.setStatus(200);
                response.setMessage("OK");
                response.writeBody("<h1>hello</h1>");
            }else if(request.getUrl().startsWith("/calc")){
                //根据参数的内容进行计算
                //先获取到a和b两个参数的值
                String aStr = request.getParameter("a");
                String bStr = request.getParameter("b");
                int a = Integer.parseInt(aStr);
                int b = Integer.parseInt(bStr);
                int result = a+b;
                response.setStatus(200);
                response.setMessage("OK");
                response.writeBody("<h1> result = "+result+"</h1>");

            }else{
                response.setStatus(200);
                response.setMessage("OK");
                response.writeBody("<h1>default</h1>");
            }

            //3.把响应写回客户端
            response.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }finally {
            try {
                //这个操作会同时关闭getInputStream和getOutputStream对象
                clientSocket.close();
            }catch (IOException e){
                e.printStackTrace();
            }
        }
    }

    public static void main(String[] args) throws IOException {
        HttpServerV2 server = new HttpServerV2(9090);
        server.start();
    }


}
