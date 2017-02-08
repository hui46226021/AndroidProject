package com.jereibaselibrary.tools;

import java.util.HashMap;

/***
 * 数据操作结果类
 * @ClassName: JRDataResult
 * @Description: TODO
 * @author Guo Jingbing
 * @date 2013-2-17 下午2:32:23
 */
public class JRDataResult
{
	private int resultCode;
	private String resultMessage;
	private Object resultObject;
	public JRDataResult(){}
	public JRDataResult(int resultCode, String resultMessage)
	{
		this.resultCode=resultCode;
		this.resultMessage=resultMessage;
	}
	public JRDataResult(int resultCode, String resultMessage, Object resultObject)
	{
		this.resultCode=resultCode;
		this.resultMessage=resultMessage;
		this.resultObject=resultObject;
	}

	public int getResultCode() {
		return resultCode;
	}

	public void setResultCode(int resultCode) {
		this.resultCode = resultCode;
	}

	public String getResultMessage() {
		return resultMessage;
	}
	public void setResultMessage(String resultMessage) {
		this.resultMessage = resultMessage;
	}
	public Object getResultObject() {
		return resultObject;
	}
	public void setResultObject(Object resultObject) {
		this.resultObject = resultObject;
	}
}
