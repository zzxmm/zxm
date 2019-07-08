package com.shouzan.common.util.qf;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.SortedMap;

import org.jdom.Document;
import org.jdom.Element;
import org.jdom.JDOMException;
import org.jdom.input.SAXBuilder;

/**
 * @description 类描述：
 * @author      作者：LIUZEKUN
 * @create      创建时间：2017年9月7日
 * @update      修改时间：2017年9月7日
 */
public class XmlUtil {

    /**
     * 解析xml,返回第一级元素键值对。如果第一级元素有子节点，则此节点的值是子节点的xml数据。
     * @param strxml
     * @return
     * @throws JDOMException
     * @throws IOException
     */
    @SuppressWarnings("unchecked")
    public static Map<String, Object> doXMLParse(String strxml) throws JDOMException, IOException {
        strxml = strxml.replaceFirst("encoding=\".*\"", "encoding=\"UTF-8\"");

        if(null == strxml || "".equals(strxml)) {
            return null;
        }
        Map<String, Object> m = new HashMap<String, Object>();
        InputStream in = new ByteArrayInputStream(strxml.getBytes("UTF-8"));
        /*
         * 针对微信支付回调通知
         * https://pay.weixin.qq.com/wiki/doc/api/jsapi.php?chapter=23_5
         * 如果有外部引用XML,要对XML外部实体注入漏洞(XML External Entity Injection，简称 XXE)进行防范
         *
         */
        SAXBuilder builder = new SAXBuilder();
        builder.setFeature("http://apache.org/xml/features/disallow-doctype-decl",true);
        Document doc = builder.build(in);
        Element root = doc.getRootElement();
        List<Element> list = root.getChildren();
        Iterator<Element> it = list.iterator();
        while(it.hasNext()) {
            Element e = (Element) it.next();
            String k = e.getName();
            String v = "";
            List<Element> children = e.getChildren();
            if(children.isEmpty()) {
                v = e.getTextNormalize();
            } else {
                v = XmlUtil .getChildrenText(children);
            }
            m.put(k, v);
        }

        //关闭流
        in.close();

        return m;
    }



    /**
     * 获取子结点的xml
     * @param children
     * @return String
     */
    @SuppressWarnings("unchecked")
    public static String getChildrenText(List<Element> children) {
        StringBuffer sb = new StringBuffer();
        if(!children.isEmpty()) {
            Iterator<Element> it = children.iterator();
            while(it.hasNext()) {
                Element e = (Element) it.next();
                String name = e.getName();
                String value = e.getTextNormalize();
                List<Element> list = e.getChildren();
                sb.append("<" + name + ">");
                if(!list.isEmpty()) {
                    sb.append(XmlUtil .getChildrenText(list));
                }
                sb.append(value);
                sb.append("</" + name + ">");
            }
        }
        return sb.toString();
    }

    /**
     * @Description：将请求参数转换为xml格式的string
     */
    public static String getRequestXml(SortedMap<String,String> parameters) {
        StringBuffer sb = new StringBuffer();
        sb.append("<xml>\n");
        Set<Entry<String,String>> es = parameters.entrySet();
        Iterator<Entry<String, String>> it = es.iterator();
        while (it.hasNext()) {
            Entry<String,String> entry = it.next();
            String k = (String) entry.getKey();
            String v = (String) entry.getValue();
                   /* if ("attach".equalsIgnoreCase(k) || "body".equalsIgnoreCase(k) || "sign".equalsIgnoreCase(k)) {
                            sb.append("    <" + k + ">" + "<![CDATA[" + v + "]]></" + k + ">\n");
                    } else {
                            sb.append("    <" + k + ">" + v + "</" + k + ">\n");
                    }*/
            if ("detail".equalsIgnoreCase(k)) {
                sb.append("    <" + k + ">" + "<![CDATA[" + v + "]]></" + k + ">\n");
            } else {
                sb.append("    <" + k + ">" + v + "</" + k + ">\n");
            }
        }
        sb.append("</xml>");
        return sb.toString();
    }
}
