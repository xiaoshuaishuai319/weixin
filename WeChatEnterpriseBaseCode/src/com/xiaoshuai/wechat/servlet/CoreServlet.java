package com.xiaoshuai.wechat.servlet;

import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.io.IOUtils;
import org.dom4j.Document;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.xiaoshuai.wechat.ParamesAPI.ParamesAPI;
import com.xiaoshuai.wechat.encryption.AesException;
import com.xiaoshuai.wechat.encryption.WXBizMsgCrypt;
import com.xiaoshuai.wechat.service.CoreService;


/**
 * 核心Servlet
 * @author 宗潇帅
 * @date  2015-8-12上午09:34:55
 */
public class CoreServlet extends HttpServlet {
	private static Logger log = LoggerFactory.getLogger(CoreServlet.class);
	/**
	 * 
	 */
	private static final long serialVersionUID = 4440739483644821986L;
	/**
	 * 验证url  
	 */
	@Override
	public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// 微信加密签名
		String msg_signature = request.getParameter("msg_signature");
		// 时间戳
		String timestamp = request.getParameter("timestamp");
		// 随机数
		String nonce = request.getParameter("nonce");
		// 随机字符串
		String echostr = request.getParameter("echostr");
		// 打印请求地址
		System.out.println("request=" + request.getRequestURL());
		// 流
		PrintWriter out = response.getWriter();
		// 通过检验signature对请求进行校验，若校验成功则原样返回echostr，表示接入成功，否则接入失败
		String result = null;
		try {
			WXBizMsgCrypt wxcpt = new WXBizMsgCrypt(ParamesAPI.token, ParamesAPI.encodingAESKey, ParamesAPI.corpId);
			// 验证URL函数
			result = wxcpt.VerifyURL(msg_signature, timestamp, nonce, echostr);
		} catch (AesException e) {
			e.printStackTrace();
		}
		if (result == null) {
			// result为空，赋予token
			result = ParamesAPI.token;
		}
		// 拼接请求参数
		String str = msg_signature + " " + timestamp + " " + nonce + " " + echostr;
		// 打印参数+地址+result
		System.out.println("Exception:" + result + " " + request.getRequestURL() + " " + "FourParames:" + str);
		String info = "Exception:" + result + " " + request.getRequestURL() + " " + "FourParames:" + str;
		log.info(info);
		out.print(result);
		out.close();
		out = null;
	}
	/**
	 * 处理微信服务器发过来的消息
	 */
	@Override
	public void doPost(HttpServletRequest request, HttpServletResponse response) 
		throws ServletException, IOException {
//		 将请求、响应的编码均设置为UTF-8（防止中文乱码）
		request.setCharacterEncoding("UTF-8");
		response.setCharacterEncoding("UTF-8");

		// 微信加密签名
		String msg_signature = request.getParameter("msg_signature");
		// 时间戳
		String timestamp = request.getParameter("timestamp");
		// 随机数
		String nonce = request.getParameter("nonce");
		
		//从请求中读取整个post数据
		InputStream inputStream = request.getInputStream();
		//commons.io.jar 方法
		String Post = IOUtils.toString(inputStream, "UTF-8");
		// Post打印结果
		System.out.println(Post);
		
		String Msg = "";
		WXBizMsgCrypt wxcpt = null;
		try {
			wxcpt = new WXBizMsgCrypt(ParamesAPI.token,ParamesAPI.encodingAESKey,ParamesAPI.corpId);
			//解密消息
			Msg = wxcpt.DecryptMsg(msg_signature, timestamp, nonce, Post);
		} catch (AesException e) {
			e.printStackTrace();
		}
		// Msg打印结果
		System.out.println("Msg打印结果：" + Msg);
	
		// 调用核心业务类接收消息、处理消息
		String respMessage = CoreService.processRequest(Msg);
		
		// respMessage打印结果
		System.out.println("respMessage打印结果：" + respMessage);
		String encryptMsg = "";
		try {
			//加密回复消息
			encryptMsg = wxcpt.EncryptMsg(respMessage, timestamp, nonce);
		} catch (AesException e) {
			e.printStackTrace();
		}
		
		// 响应消息
		PrintWriter out = response.getWriter();
		out.print(encryptMsg);
		out.close();

	}
	/**
	 * 解析微信发来的请求(XML)
	 * @param request
	 * @return
	 * @throws Exception
	 */
	public static Map<String, String> parseXml(HttpServletRequest request) throws Exception{
		// 将解析结果存储在HashMap中  
	    Map<String, String> map = new HashMap<String, String>();  
	    // 从request中取得输入流  
	    InputStream inputStream = request.getInputStream();  
	    // 读取输入流  
	    SAXReader reader = new SAXReader();  
	    Document document = reader.read(inputStream);  
	    // 得到xml根元素  
	    Element root = document.getRootElement();  
	    // 得到根元素的所有子节点  
	    List<Element> elementList = root.elements();  
	    // 遍历所有子节点  
	    for (Element e : elementList)  
	        map.put(e.getName(), e.getText());  
	    // 释放资源  
	    inputStream.close();  
	    inputStream = null;  
		return map;
	}
}
