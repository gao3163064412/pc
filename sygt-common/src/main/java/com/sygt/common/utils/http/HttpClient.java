package com.sygt.common.utils.http;

import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.util.EntityUtils;

/**
 *
 * JAVA ROAD
 * HttpClient远程RPC调用
 */
public class HttpClient {

    /**
     * HttpClient Json 请求接口
     * @return json
     */
    public static String request(String url, String json) {
        RequestConfig config = RequestConfig.custom()
                .setConnectTimeout(8 * 1000) //设置连接超时时间，单位毫秒
                .setSocketTimeout(8 * 1000) //请求获取数据的超时时间，单位毫秒
                .build();
        CloseableHttpClient httpclient = HttpClientBuilder.create().setDefaultRequestConfig(config).build();
        HttpPost post = new HttpPost(url);
        StringEntity params = new StringEntity(json.toString(), "utf-8");
        params.setContentType("text/html;charset=UTF-8");
        params.setContentEncoding("UTF-8");
        post.setEntity(params);
        String result = null;
        try {
            HttpResponse res = httpclient.execute(post);
            if(res.getStatusLine().getStatusCode() == HttpStatus.SC_OK){
                result = EntityUtils.toString(res.getEntity());// 返回json格式：
            }else{
                System.out.println(res.getStatusLine().getStatusCode());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }

}
