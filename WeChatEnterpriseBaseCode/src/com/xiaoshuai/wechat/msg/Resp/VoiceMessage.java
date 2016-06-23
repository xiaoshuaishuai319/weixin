package com.xiaoshuai.wechat.msg.Resp;


/**
 * 语音消息
 * 
 * @author zxs
 */
public class VoiceMessage extends BaseMessage {
	// 语音
	private Voice voice;

	public Voice getVoice() {
		return voice;
	}

	public void setVoice(Voice voice) {
		this.voice = voice;
	}
}