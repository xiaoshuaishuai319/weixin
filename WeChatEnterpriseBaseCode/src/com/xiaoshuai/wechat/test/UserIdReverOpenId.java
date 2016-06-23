package com.xiaoshuai.wechat.test;

import net.sf.json.JSONObject;

import com.xiaoshuai.wechat.ParamesAPI.WeixinUtil;

public class UserIdReverOpenId {
	public  String USERID_TO_OPENID = "https://qyapi.weixin.qq.com/cgi-bin/user/convert_to_openid?access_token=ACCESS_TOKEN";
	/***
	 * 根据userid 换取openid
	 * @param access_token 根据企业号的id 和密钥得到
	 * @param param 需要的json数据。{\"userid\": \"zhangsan\"}
	 * @return
	 */
	public  String getOpenId(String access_token,String param) {
		String OpenId = "";
		USERID_TO_OPENID = USERID_TO_OPENID.replace("ACCESS_TOKEN", access_token);
		JSONObject jsonobject = WeixinUtil.httpRequest(USERID_TO_OPENID, "GET", param);
		System.out.println(jsonobject);
		if (null != jsonobject) {
			OpenId = jsonobject.getString("openid").toString();
			if (!"".equals(OpenId)) {
				System.out.println("获取信息成功，o(∩_∩)o ————OpenId:" + OpenId);
			} else {
				int errorrcode = jsonobject.getInt("errcode");
				String errmsg = jsonobject.getString("errmsg");
				System.out.println("错误码：" + errorrcode + "————" + "错误信息：" + errmsg);
			}
		} else {
			System.out.println("获取OpenId失败了，●﹏●，自己找原因。。。");
		}
		return OpenId;
	}
	public static void main(String[] args) {
		String param = "{\"userid\": \"zongxiaoshuai\"}";
		String access_token = "7qq6tBKNKQ1IsLMuHsgqJmWY-PwExMBIu10mGyVltWHXwdd7YoG01iiMdS6_oqQUFvtCrDuvtVCYWu1qAe7jOQ";
		UserIdReverOpenId id = new UserIdReverOpenId();
		String openid= id.getOpenId(access_token,param);
	}
}
