import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.Executor;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class HttpServerV3 {
    private ServerSocket serverSocket = null;

    public HttpServerV3(int port) throws IOException {
        serverSocket = new ServerSocket(port);
    }

    public void start() throws IOException {
        System.out.println("服务器启动");
        ExecutorService executorService = Executors.newCachedThreadPool();
        while (true){
            Socket clientSocket = serverSocket.accept();
            executorService.execute(new Runnable() {
                @Override
                public void run() {
                    process(clientSocket);
                }
            });
        }
    }

    private void process(Socket clientSocket) {
        try{
            //1.读取请求并解析
            HttpRequest request = HttpRequest.build(clientSocket.getInputStream());
            HttpResponse response = HttpResponse.build(clientSocket.getOutputStream());
        } catch (IOException e) {
            e.printStackTrace();
        }finally {
            try{
                clientSocket.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private void doGet(HttpRequest request,HttpResponse response) throws IOException {
        if (request.getUrl().startsWith("/index.html")) {
            String userName = request.getCookie("userName");
            if (userName == null) {
                //说明当前用户尚未登录，就返回一个登录页面即可
                response.setStatus(200);
                response.setMessage("OK");
                response.setHeader("Content-Type", "text/html;charset=utf-8");

                //让代码读取一个index.html文件
                //要想读取文件，就得知道文件路径，现在只知道文件名
                //这种html文件所属路径，可以自己约定
                //把文件内容写到响应的body中


                InputStream inputStream = HttpServerV3.class.getClassLoader().getResourceAsStream("index.html");//获取一个类的“类对象”，并且再获取到当前类的类加载器,根据文件名，在resourses目录中找到对应的文件并打开，返回这个文件的inputStream对象
                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
                //按行读取内容，把数据写入到response
                String line = null;
                while ((line = bufferedReader.readLine()) != null) {
                    response.writeBody(line + "\n");
                }
                bufferedReader.close();
            } else {
                //用户已经登录，无需再登录
                response.setStatus(200);
                response.setMessage("OK");
                response.setHeader("Content-Type", "text/html;charset=utf-8");
                response.writeBody("<html>");
                response.writeBody("您已经登录了！用户名：" + userName);
                response.writeBody("</html>");
            }
        }
    }


    private void doPost(HttpRequest request,HttpResponse response){
        if(request.getUrl().startsWith("/login")){
            String userName = request.getParameters("username");
            String password = request.getParameters("password");
            System.out.println("userName"+userName);
            System.out.println("password"+password);
        }

    }

    public static void main(String[] args) throws IOException {
        HttpServerV3 serverV3 = new HttpServerV3(9090);
        serverV3.start();
    }
}
