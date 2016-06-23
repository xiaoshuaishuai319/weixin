package com.xiaoshuai.wechat.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.xiaoshuai.wechat.msg.MessageUtil;
import com.xiaoshuai.wechat.msg.Resp.Article;
import com.xiaoshuai.wechat.msg.Resp.NewsMessage;
import com.xiaoshuai.wechat.msg.Resp.TextMessage;



/**
 * 核心Service类
 * 
 *@author zxs
 * @date  2015-8-12上午09:34:55
 */
public class CoreService {
	private static Logger log = LoggerFactory.getLogger(CoreService.class);
	/**
	 * 处理微信发来的请求
	 * 
	 * @param msg
	 * @return xml
	 */
	public static String processRequest(String msg) {
		log.info("进来了");
		// xml格式的消息数据
		String respXml = null;
		// 默认返回的文本消息内容
		String respContent = "未知的消息类型！";
		try {
			// 调用parseXml方法解析请求消息
			Map<String, String> requestMap = MessageUtil.parseXml(msg);
			// 发送方帐号
			String fromUserName = requestMap.get("FromUserName");
			// 开发者微信号
			String toUserName = requestMap.get("ToUserName");
			// 消息类型
			String msgType = requestMap.get("MsgType");
			//回复图文信息
			NewsMessage newsMessage = new NewsMessage();
			newsMessage.setToUserName(fromUserName);
			newsMessage.setFromUserName(toUserName);
			newsMessage.setCreateTime(new Date().getTime());
			newsMessage.setMsgType(MessageUtil.RESP_MESSAGE_TYPE_NEWS);
			newsMessage.setFuncFlag(0);
			List<Article> articleList = new ArrayList<Article>();
			// 回复文本消息
			TextMessage textMessage = new TextMessage();
			textMessage.setToUserName(fromUserName);
			textMessage.setFromUserName(toUserName);
			textMessage.setCreateTime(new Date().getTime());
			textMessage.setMsgType(MessageUtil.RESP_MESSAGE_TYPE_TEXT);
			textMessage.setFuncFlag(0);
			// 文本消息
			if (msgType.equals(MessageUtil.REQ_MESSAGE_TYPE_TEXT)) {
				respContent = "您发送的是文本消息！";
			}
			// 图片消息
			else if (msgType.equals(MessageUtil.REQ_MESSAGE_TYPE_IMAGE)) {
				respContent = "您发送的是图片消息！";
			}
			// 语音消息
			else if (msgType.equals(MessageUtil.REQ_MESSAGE_TYPE_VOICE)) {
				respContent = "您发送的是语音消息！";
			}
			// 视频消息
			else if (msgType.equals(MessageUtil.REQ_MESSAGE_TYPE_VIDEO)) {
				respContent = "您发送的是视频消息！";
			}
			// 地理位置消息
			else if (msgType.equals(MessageUtil.REQ_MESSAGE_TYPE_LOCATION)) {
				respContent = "您发送的是地理位置消息！";
			}
			// 链接消息
			else if (msgType.equals(MessageUtil.REQ_MESSAGE_TYPE_LINK)) {
				respContent = "您发送的是链接消息！";
			}
			// 事件推送
			else if (msgType.equals(MessageUtil.REQ_MESSAGE_TYPE_EVENT)) {
				// 事件类型
				String eventType = requestMap.get("Event");
				// 关注
				if (eventType.equals(MessageUtil.EVENT_TYPE_SUBSCRIBE)) {
					respContent = "被关注的感觉真好~开始新鲜到家的体验~嘿，小伙伴，快来体验哦！";
				}// 取消关注
				else if (eventType.equals(MessageUtil.EVENT_TYPE_UNSUBSCRIBE)) {
					// TODO 取消订阅后用户不会再收到公众账号发送的消息，因此不需要回复
				}
				// 扫描带参数二维码
				else if (eventType.equals(MessageUtil.EVENT_TYPE_SCAN)) {
					// TODO 处理扫描带参数二维码事件
				}// 上报地理位置
				else if (eventType
						.equals(MessageUtil.REQ_MESSAGE_TYPE_LOCATION)) {
					// TODO 处理上报地理位置事件
				}// 自定义菜单
				else if (eventType.equals(MessageUtil.EVENT_TYPE_CLICK)) {
					// TODO 处理菜单点击事件
					// 事件KEY值，与创建自定义菜单时指定的KEY值对应
					String eventKey = requestMap.get("EventKey");
					System.out.println("EventKey=" + eventKey);
					respContent = "点击的菜单KEY:" + eventKey;
					if(eventKey.equals("11")){
						respContent = "你点击了 学霸天气".toString();
					}
				}
			}
			if(!"".equals(respContent)&& respContent!=null){
				// 设置文本消息的内容
				textMessage.setContent(respContent);
				// 将文本消息对象转换成xml
				respXml = MessageUtil.textMessageToXml(textMessage);
			}else if(articleList.size()>0){
				
			} else {
				
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return respXml;
	}

}
