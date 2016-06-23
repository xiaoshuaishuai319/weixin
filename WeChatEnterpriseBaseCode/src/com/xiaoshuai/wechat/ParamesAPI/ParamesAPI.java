package com.xiaoshuai.wechat.ParamesAPI;

/**
 * 参数API类
 * 
 * @author zxs
 * @date  2015-8-12上午09:34:55
 */
public class ParamesAPI {
	// token 可以自定义
	public static String token = "WeiXinEnterprises";
	// 随机戳
	public static String encodingAESKey = "y6CCwOgFtCw7UW7CkE35ILQQ7QocG2WzgAKIuytxjbT";
	// 你的企业号ID 也就是CorpID
	public static String corpId = "wx32ceeb1bfb98dc63";
	// 管理组的凭证密钥，每个secret代表了对应用、通讯录、接口的不同权限；不同的管理组拥有不同的secret
	//接口开发 自定义菜单
	public static String secret = "0Mjki-t_kFbsXseI0mTQ3yjfYauA1Kxjcpq9kvA1xtO18dGBPrk1G3FWC3c2dmSt";
	// OAuth2 回调地址
	public static String REDIRECT_URI = "";//需要在企业应用里面配置
}
