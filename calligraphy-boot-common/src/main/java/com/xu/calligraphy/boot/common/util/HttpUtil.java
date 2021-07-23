package com.xu.calligraphy.boot.common.util;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.StatusLine;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.URL;
import java.net.URLConnection;
import java.util.List;
import java.util.Map;

/**
 * @author xu
 * @date 2021/7/23 17:59
 */
@Component
public class HttpUtil {
    private static final Logger logger = LoggerFactory.getLogger(HttpUtil.class);

    @Value("${spring.profiles.active}")
    private String profile;

    private final static String APPKEY = "new-background";

    private final static String GETUSERINFOURL = "http://authority-api.hoomi.cn/admin/getAdminInfo";



    /**
     * get请求
     *
     * @param url
     * @return
     */
    public String httpGet(String url) {
        HttpClient httpClient;
        HttpGet httpGet;
        httpClient = HttpClients.createDefault();
        String responseContent = null;
        try {
            logger.info("httpGet url {}", url);
            httpGet = new HttpGet(url);
            HttpResponse response = httpClient.execute(httpGet);
            HttpEntity httpEntity = response.getEntity();
            responseContent = org.apache.http.util.EntityUtils.toString(httpEntity);
            logger.info("httpGet responseContent {}", responseContent);
            EntityUtils.consume(httpEntity);
        } catch (Exception e) {
            logger.error("httpGet Exception", e);
        }
        return responseContent;
    }


    /**
     * 向指定URL发送GET方法的请求
     *
     * @param url   发送请求的URL
     * @param param 请求参数，请求参数应该是 name1=value1&name2=value2 的形式。
     * @return URL 所代表远程资源的响应结果
     */
    public String sendGet(String url, String param) {
        String result = "";
        BufferedReader in = null;
        try {
            String urlNameString = url + "?" + param;
            if (StringUtils.isEmpty(param)) {
                urlNameString = url;
            }
            logger.info("sendGet url={}", urlNameString);
            URL realUrl = new URL(urlNameString);
            // 打开和URL之间的连接
            URLConnection connection = realUrl.openConnection();
            // 设置通用的请求属性
            connection.setRequestProperty("accept", "*/*");
            connection.setRequestProperty("connection", "Keep-Alive");
            connection.setRequestProperty("user-agent",
                    "Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.1;SV1)");
            // 建立实际的连接
            connection.connect();
            // 获取所有响应头字段
            Map<String, List<String>> map = connection.getHeaderFields();
            // 遍历所有的响应头字段
            /*for (String key : map.keySet()) {
                logger.info(key + "--->" + map.get(key));
            }*/
            // 定义 BufferedReader输入流来读取URL的响应
            in = new BufferedReader(new InputStreamReader(
                    connection.getInputStream()));
            String line;
            while ((line = in.readLine()) != null) {
                result += line;
            }
        } catch (Exception e) {
            logger.error("sendGet Exception", e);
        }
        // 使用finally块来关闭输入流
        finally {
            try {
                if (in != null) {
                    in.close();
                }
            } catch (Exception e2) {
                e2.printStackTrace();
            }
        }
        return result;
    }

    /**
     * 向指定 URL 发送POST方法的请求
     *
     * @param url   发送请求的 URL
     * @param param 请求参数，请求参数应该是 name1=value1&name2=value2 的形式。
     * @return 所代表远程资源的响应结果
     */
    public String sendPost(String url, String param) {
        PrintWriter out = null;
        BufferedReader in = null;
        String result = "";
        try {
            logger.info("sendPost url={},param={}", url, param);
            URL realUrl = new URL(url);
            // 打开和URL之间的连接
            URLConnection conn = realUrl.openConnection();
            // 设置通用的请求属性
            conn.setRequestProperty("accept", "*/*");
            conn.setRequestProperty("connection", "Keep-Alive");
            conn.setRequestProperty("user-agent",
                    "Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.1;SV1)");
            // 发送POST请求必须设置如下两行
            conn.setDoOutput(true);
            conn.setDoInput(true);
            // 获取URLConnection对象对应的输出流
            out = new PrintWriter(conn.getOutputStream());
            // 发送请求参数
            out.print(param);
            // flush输出流的缓冲
            out.flush();
            // 定义BufferedReader输入流来读取URL的响应
            in = new BufferedReader(
                    new InputStreamReader(conn.getInputStream()));
            String line;
            while ((line = in.readLine()) != null) {
                result += line;
            }
        } catch (Exception e) {
            logger.error("sendPost Exception", e);
        }
        //使用finally块来关闭输出流、输入流
        finally {
            try {
                if (out != null) {
                    out.close();
                }
                if (in != null) {
                    in.close();
                }
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }
        return result;
    }

    /**
     * 向指定 URL 发送POST方法的请求
     *
     * @param url
     * @param json
     * @return
     */
    public String sendPostWithJSON(String url, String json) {
        // 创建默认的httpClient实例
        CloseableHttpClient httpclient = HttpClients.createDefault();
        try {
            // 创建httppost
            logger.info("sendPostWithJSON url={},param={}", url, json);
            HttpPost httppost = new HttpPost(url);
            httppost.addHeader("Content-type", "application/json; charset=utf-8");

            if ("dev".equals(profile)) {
                httppost.addHeader("x-ca-stage", "TEST");
            }

            if ("release".equals(profile)) {
                httppost.addHeader("x-ca-stage", "pre");
            }
            logger.info("executing request " + httppost.getURI());

            // 向POST请求中添加消息实体
            StringEntity se = new StringEntity(json, "UTF-8");
            httppost.setEntity(se);
            logger.info("request parameters " + json);

            // 执行post请求
            CloseableHttpResponse response = httpclient.execute(httppost);
            try {
                // 获取响应实体
                HttpEntity entity = response.getEntity();
                String result = EntityUtils.toString(entity, "UTF-8");
                logger.info("statusLine={},entity={}", response.getStatusLine(), result);
                if (entity != null) {
                    // 打印响应内容
                    return result;
                }
            } finally {
                response.close();
            }
        } catch (Exception e) {
            logger.error("executing sendPostWithJSON Exception ", e);
        } finally {
            // 关闭连接,释放资源
            try {
                httpclient.close();
            } catch (IOException e) {
                logger.error("executing sendPostWithJSON error: " + e.getMessage());
            }
        }
        return null;
    }

    /**
     * 向指定 URL 发送POST方法的请求/携带token参数
     *
     * @param url
     * @param json
     * @return
     */
    public String sendPostWithJSON(String url, String json,String token) {
        // 创建默认的httpClient实例
        CloseableHttpClient httpclient = HttpClients.createDefault();
        try {
            // 创建httppost
            logger.info("sendPostWithJSON url={},param={}", url, json);
            HttpPost httppost = new HttpPost(url);
            httppost.addHeader("Content-type", "application/json; charset=utf-8");
            httppost.addHeader("Authorization",token);

            if ("dev".equals(profile)) {
                httppost.addHeader("x-ca-stage", "TEST");
            }

            if ("release".equals(profile)) {
                httppost.addHeader("x-ca-stage", "pre");
            }
            logger.info("executing request " + httppost.getURI());

            // 向POST请求中添加消息实体
            StringEntity se = new StringEntity(json, "UTF-8");
            httppost.setEntity(se);
            logger.info("request parameters " + json);

            // 执行post请求
            CloseableHttpResponse response = httpclient.execute(httppost);
            try {
                // 获取响应实体
                HttpEntity entity = response.getEntity();
                String result = EntityUtils.toString(entity, "UTF-8");
                logger.info("statusLine={},entity={}", response.getStatusLine(), result);
                if (entity != null) {
                    // 打印响应内容
                    return result;
                }
            } finally {
                response.close();
            }
        } catch (Exception e) {
            logger.error("executing sendPostWithJSON Exception ", e);
        } finally {
            // 关闭连接,释放资源
            try {
                httpclient.close();
            } catch (IOException e) {
                logger.error("executing sendPostWithJSON error: " + e.getMessage());
            }
        }
        return null;
    }

    /**
     * 调用权限中心获取用户信息
     * @param token
     * @return
     */
    public String getUserInfo(String token) {

        try {
            HttpClient client = HttpClientBuilder.create().build();

            // 发送get请求
            HttpGet request = new HttpGet(GETUSERINFOURL+ "?appKey=" + APPKEY);
            request.addHeader("authorization",token);

            if ("dev".equals(profile)) {
                request.addHeader("x-ca-stage", "TEST");
            }

            if ("release".equals(profile)) {
                request.addHeader("x-ca-stage", "pre");
            }

            HttpResponse response = client.execute(request);

            /** 请求发送成功，并得到响应 **/
            StatusLine status = response.getStatusLine();
            int state = status.getStatusCode();
            if (state == HttpStatus.SC_OK) {
                /** 读取服务器返回过来的json字符串数据 **/
                String strResult = EntityUtils.toString(response.getEntity());

                return strResult;
            } else {
                logger.error("status:" + state + "(" + token + ")");
                return null;
            }
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            return null;
        }

    }

}