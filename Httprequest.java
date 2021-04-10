import com.sun.applet2.AppletParameters;

import java.io.*;
import java.security.Policy;
import java.util.HashMap;
import java.util.Map;

public class Httprequest {
    private String method;
    private String url;
    private Map<String,String> header = new HashMap<>();
    private Map<String,String> parameters = new HashMap<>();
    private String version;
    private AppletParameters headers;



    public static Httprequest build(InputStream inputStream) throws IOException {
        Httprequest request = new Httprequest();
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
        //1.解析首行
        String firstLine = bufferedReader.readLine();
        String[] firstLineTokens = firstLine.split("");
        request.method = firstLineTokens[0];
        request.url = firstLineTokens[1];
        request.version = firstLineTokens[2];

        //2.解析url中的参数
        int pos  = request.url.indexOf("?");
        //pos表示？的下标
        if(pos !=-1){
            String parameters = request.url.substring(pos+1);
            parseKV(parameters,request.parameters);
        }

        //3.解析header
        String line = "";
        while ((line = bufferedReader.readLine())!=null&& line.length()!=0){
            String[] headerTokens = line.split(":");
            request.headers.put(headerTokens[0],headerTokens[1]);
        }

        //4.解析body
        return request;
    }

    private static void parseKV(String input, Map<String, String> output) {
        //1.先按照&切分成若干组键值对
        String[] kvTokens = input.split("&");
        //2.针对切分结果再分别进行按照=切分，就得到了键和值
        for(String kv:kvTokens){
            String[] result = kv.split("=");
            output.put(result[0],result[1]);
        }
    }
    //3.给这个类构造一些getter方法，


    public String getMethod() {
        return method;
    }

    public String getUrl() {
        return url;
    }

    public String getVersion() {
        return version;
    }

    public String getHeader(String key){
        return (String) headers.get(key);
    }

    public String getParameter(String key){
        return parameters.get(key);

    }

    @Override
    public String toString() {
        return "Httprequest{"+
                "method='"+method+'\''+
                ",url='"+url+'\''+
                ",version='"+version+'\''+
                ",headers="+headers+
                ",parameters="+parameters+
                '}';
    }
}
