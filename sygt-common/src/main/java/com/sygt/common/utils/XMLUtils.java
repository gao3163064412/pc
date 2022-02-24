package com.sygt.common.utils;

import com.alibaba.fastjson.JSONObject;
import org.apache.commons.lang3.StringUtils;
import org.jdom2.Document;
import org.jdom2.Element;
import org.jdom2.JDOMException;
import org.jdom2.input.SAXBuilder;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.LinkedList;
import java.util.List;


public class XMLUtils {
    //可以无视，部分文章链接过来会用到这个
    public static JSONObject XMLReader(byte[] fileByteArray) throws JDOMException, IOException {
        JSONObject json = new JSONObject();
        InputStream is = new ByteArrayInputStream(fileByteArray);
        SAXBuilder sb = new SAXBuilder();
        org.jdom2.Document doc = sb.build(is);
        Element root = doc.getRootElement();
        if(iterateElement(root).size()==0){
            json.put(root.getName(),root.getText());
        }else {
            json.put(root.getName(), iterateElement(root));
        }
        return json;
    }

    //读取xml转换为JSONObject  charsetName：utf-8
    public static JSONObject XMLReader(String xmlStr,String charsetName) throws JDOMException, IOException {
        if (StringUtils.isEmpty(xmlStr)) {
            return null;
        }
        xmlStr = xmlStr.replaceAll("\\\n", "");
        byte[] xml = xmlStr.getBytes(charsetName);
        JSONObject json = new JSONObject();
        InputStream is = new ByteArrayInputStream(xml);
        SAXBuilder sb = new SAXBuilder();
        Document doc = sb.build(is);
        Element root = doc.getRootElement();
        if(iterateElement(root).size()==0){
            json.put(root.getName(),root.getText());
        }else {
            json.put(root.getName(), iterateElement(root));
        }
        return json;
    }

    private static JSONObject iterateElement(Element element) {
        List node = element.getChildren();
        Element et = null;
        JSONObject obj = new JSONObject();
        List list = null;
        for (int i = 0; i < node.size(); i++) {
            list = new LinkedList();
            et = (Element) node.get(i);
            if (et.getTextTrim().equals("")) {
                if (et.getChildren().size() == 0)
                    continue;
                if (obj.containsKey(et.getName())) {
                    list = (List) obj.get(et.getName());
                }
                list.add(iterateElement(et));
                obj.put(et.getName(), list);
            }
            else {
                if (obj.containsKey(et.getName())) {
                    list = (List) obj.get(et.getName());
                }
                list.add(et.getTextTrim());
                obj.put(et.getName(), list);
            }
        }
        return obj;
    }

}
