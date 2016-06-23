package com.xiaoshuai.wechat.menu;

import java.net.URLEncoder;

import com.xiaoshuai.wechat.ParamesAPI.AccessToken;
import com.xiaoshuai.wechat.ParamesAPI.ParamesAPI;
import com.xiaoshuai.wechat.ParamesAPI.WeixinUtil;
import com.xiaoshuai.wechat.oauth2.OAuth2Core;

/**
 * 自定义菜单管理器
 * @author zxs
 * @date  2015-8-12上午11:30:45
 */

public class MenuManager {
	OAuth2Core auth2Core =  new OAuth2Core();
	public static void main(String[] args) throws Exception {
		String httpUrl = "http://zxshuai.imwork.net/wx/wx/WeChatAction.do";
		// 企业Id
		String CorpID = ParamesAPI.corpId;
		// 管理组的凭证密钥，每个secret代表了对应用、通讯录、接口的不同权限；不同的管理组拥有不同的secret
		String Secret = ParamesAPI.secret;
		String agentid = "2";
		// 调用接口获取access_token
		AccessToken at = WeixinUtil.getAccessToken(CorpID, Secret);

		if (null != at) {
			// 调用接口创建菜单
			int result = WeixinUtil.createMenu(getMenu(), at.getToken(),agentid);

			// 判断菜单创建结果
			if (0 == result) {
				System.out.println("菜单创建成功！");
			} else
				System.out.println("菜单创建失败！错误代码:"+result);
				
		}
	}

	/**
	 * 组装菜单数据
	 * 
	 * @return
	 * @throws Exception 
	 */
	public static Menu getMenu() throws Exception {
		
//		String url = "http://scdd.weixin.jard.com.cn/wx/wx/userInfoAction.do";//验证的URL
		String url = "http://zxshuai.imwork.net/wx/wx/userInfoAction.do";//验证的URL
		
		ViewButton btn11 = new ViewButton();
		btn11.setName("热源");
		btn11.setType("view");
		String heat = URLEncoder.encode(url,"utf-8");
		String heaturl = "https://open.weixin.qq.com/connect/oauth2/authorize?appid="+ParamesAPI.corpId+"&redirect_uri="+heat+"&response_type=code&scope=snsapi_base&state=HEAT#wechat_redirect";
		btn11.setUrl(heaturl);
		
		ViewButton btn12 = new ViewButton();
		btn12.setName("换热站");
		btn12.setType("view");
		String huanrz = URLEncoder.encode(url,"utf-8");
		String huanrzurl = "https://open.weixin.qq.com/connect/oauth2/authorize?appid="+ParamesAPI.corpId+"&redirect_uri="+huanrz+"&response_type=code&scope=snsapi_base&state=HUANRZ#wechat_redirect";
		btn12.setUrl(huanrzurl);

		ViewButton btn13 = new ViewButton();
		btn13.setName("气象信息");
		btn13.setType("view");
		String weather = URLEncoder.encode(url,"utf-8");
		String weatherurl = "https://open.weixin.qq.com/connect/oauth2/authorize?appid="+ParamesAPI.corpId+"&redirect_uri="+weather+"&response_type=code&scope=snsapi_base&state=WEATHER#wechat_redirect";
		btn13.setUrl(weatherurl);

	

		ViewButton btn21= new ViewButton();
		btn21.setName("能耗统计");
		btn21.setType("view");
		String energytotal = URLEncoder.encode(url,"utf-8");
		String energytotalurl = "https://open.weixin.qq.com/connect/oauth2/authorize?appid="+ParamesAPI.corpId+"&redirect_uri="+energytotal+"&response_type=code&scope=snsapi_base&state=ENERGYTOTAL#wechat_redirect";
		btn21.setUrl(energytotalurl);


		ViewButton btn22= new ViewButton();
		btn22.setName("能耗排名");
		btn22.setType("view");
		String energypm = URLEncoder.encode(url,"utf-8");
		String energypmurl = "https://open.weixin.qq.com/connect/oauth2/authorize?appid="+ParamesAPI.corpId+"&redirect_uri="+energypm+"&response_type=code&scope=snsapi_base&state=ENERGYPM#wechat_redirect";
		btn22.setUrl(energypmurl);


		

//		
		ViewButton btn31 = new ViewButton();
		btn31.setName("连续断传");
		btn31.setType("view");
		String lianxu = URLEncoder.encode(url,"utf-8");
		String lianxuurl = "https://open.weixin.qq.com/connect/oauth2/authorize?appid="+ParamesAPI.corpId+"&redirect_uri="+lianxu+"&response_type=code&scope=snsapi_base&state=LIANXU#wechat_redirect";
		btn31.setUrl(lianxuurl);

		ViewButton btn32 = new ViewButton();
		btn32.setName("两天两度超标");
		btn32.setType("view");
		String over = URLEncoder.encode(url,"utf-8");
		String overurl = "https://open.weixin.qq.com/connect/oauth2/authorize?appid="+ParamesAPI.corpId+"&redirect_uri="+over+"&response_type=code&scope=snsapi_base&state=OVER#wechat_redirect";
		btn32.setUrl(overurl);

		ViewButton btn33 = new ViewButton();
		btn33.setName("能耗指标");
		btn33.setType("view");
		String target = URLEncoder.encode(url,"utf-8");
		String targeturl = "https://open.weixin.qq.com/connect/oauth2/authorize?appid="+ParamesAPI.corpId+"&redirect_uri="+target+"&response_type=code&scope=snsapi_base&state=TARGET#wechat_redirect";
		btn33.setUrl(targeturl);
	

		ComplexButton mainBtn1 = new ComplexButton();
		mainBtn1.setName("实时数据");
		mainBtn1.setSub_button(new Button[] { btn11, btn12, btn13});

		ComplexButton mainBtn2 = new ComplexButton();
		mainBtn2.setName("能耗信息");
		mainBtn2.setSub_button(new Button[] { btn21, btn22});

		ComplexButton mainBtn3 = new ComplexButton();
		mainBtn3.setName("绩效考核");
		mainBtn3.setSub_button(new Button[] { btn31, btn32, btn33});

		/**
		 * 
		 * 在某个一级菜单下没有二级菜单的情况，menu该如何定义呢？<br>
		 * 比如，第三个一级菜单项不是“更多体验”，而直接是“幽默笑话”，那么menu应该这样定义：<br>
		 * menu.setButton(new Button[] { mainBtn1, mainBtn2, btn33 });
		 */
		Menu menu = new Menu();
		menu.setButton(new Button[] { mainBtn1, mainBtn2, mainBtn3 });

		return menu;
	}

	public OAuth2Core getAuth2Core() {
		return auth2Core;
	}

	public void setAuth2Core(OAuth2Core auth2Core) {
		this.auth2Core = auth2Core;
	}
	
}
