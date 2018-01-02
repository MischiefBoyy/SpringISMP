package com.inrich.service;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Base64;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.http.Consts;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.inrich.dao.LevelTwoDAO;
import com.inrich.model.LevelTwo;
import com.inrich.util.FileOperation;
import com.inrich.util.OutPrintUtil;
import com.inrich.util.StaticValues;

@Service
public class QaService {
	public static  Logger logger=LoggerFactory.getLogger(QaService.class);
	
	@Autowired
	private LevelTwoDAO levelTwoDAO;
	
	/**
	 * 查询一级所有列表
	 * @TODO TODO
	 * @Time 2017年12月22日 上午11:59:46
	 * @author WEQ
	 * @return String
	 */
	public String getIndex() {
		List<LevelTwo> list=levelTwoDAO.selectIndex();
		Map<String,Object> map=new HashMap<>(2);
		map.put("info", list);
		map.put("isBase", false);
		return OutPrintUtil.getJSONString("success", map);
		
	}
	
	
	
	
	
	/**
	 * 通过点击获得的信息 id
	 * @TODO TODO
	 * @Time 2017年12月22日 下午1:43:43
	 * @author WEQ
	 * @return String
	 */
	public String getByClick(int id,int isBase) {
		Map<String,Object> map=new HashMap<>(2);
		if(isBase == 0) {
			map.put("info", levelTwoDAO.selectLevelTwoByPid(id));
			map.put("isBase", false);//为了前台判断是否添加可点击样式，false为添加，true为不添加
		}else {
			map.put("info", levelTwoDAO.selectLevelTwoById(id));
			map.put("isBase", true);
		}
		return OutPrintUtil.getJSONString("success", map);
	}
	
	/**
	 *  用户输入问题  科大讯飞 问答库分析  
	 * @TODO TODO
	 * @Time 2018年1月2日 上午10:24:55
	 * @author WEQ
	 * @return String
	 */
	public String doText(String text) {
		try {
			String result=doText2Value(text,"ismp");
			String keyWord=anaylizeText(result);
			if(StringUtils.isEmpty(keyWord)) {
				return OutPrintUtil.getJSONString("error", "对不起你的问题有误，可以尝试点击快速查询.");
			}
			LevelTwo levelTwo=levelTwoDAO.selectByKeyword(keyWord);
			if(levelTwo != null) {
				Map<String,Object> map=new HashMap<>(2);
				if(levelTwo.getIsBase()==0) {
					List<Map<String,Object>> list=levelTwoDAO.selectLevelTwoByPid(levelTwo.getId());
					map.put("info", list);
					map.put("isBase", false);
				}else {
					map.put("info", levelTwo);
					map.put("isBase", true);
				}
				return OutPrintUtil.getJSONString("success", map);
			}
		} catch (UnsupportedEncodingException e) {
			logger.error("base64转换错误:"+e.getMessage());
		}
		
		return OutPrintUtil.getJSONString("error", "解析错误!");
		
	}
	
	
	/**
	 * 文本语义接口 发送文本到XF服务器
	 * 
	 * @throws UnsupportedEncodingException
	 */
	private String doText2Value(String question, String scene) throws UnsupportedEncodingException {

		String result = "";
		// String question="财安";
		String base64Question = Base64.getEncoder().encodeToString(question.getBytes("utf-8"));

		// 获得 从1970年1月1日0点0 分0 秒开始到现在的秒数(String)
		// 参数二
		java.util.Calendar cal = java.util.Calendar.getInstance();
		String curTime = cal.getTimeInMillis() / 1000 + "";

		// 参数三
		Map<String, Object> paramJson = new HashMap<>();
		paramJson.put("scene", scene);
		paramJson.put("userid", "user_0001");
		String param = JSON.toJSONString(paramJson);
		param = Base64.getEncoder().encodeToString(param.getBytes("utf-8"));

		// 参数四
		StringBuilder s2Md5 = new StringBuilder();
		s2Md5.append(StaticValues.XF_APP_KEY).append(curTime).append(param).append("text=").append(base64Question);

		String checkSum = null;
		checkSum = MD5(s2Md5.toString());

		// 请求设置body
		Map<String, Object> bodyMap = new HashMap<String, Object>();
		bodyMap.put("text", base64Question);

		// 请求设置头部
		Map<String, Object> HeaderMap = new HashMap<String, Object>();
		HeaderMap.put("X-Appid", StaticValues.XF_APP_ID);
		HeaderMap.put("X-CurTime", curTime);
		HeaderMap.put("X-Param", param);
		HeaderMap.put("X-CheckSum", checkSum);
		HeaderMap.put("Content-Type", "application/x-www-form-urlencoded; charset=utf-8");

		try {
			// 发起请求
			result =HttpClientPost(StaticValues.URL_TEXTSEMANTIC_PATH, "utf-8", bodyMap, HeaderMap);
		} catch (Exception e) {
			logger.error("科大讯飞解析文本出错："+e.getMessage());
		}

		return result;
	}
	
	
	private String MD5(String key) {
        char hexDigits[] = {
                '0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'A', 'B', 'C', 'D', 'E', 'F'
        };
        try {
            byte[] btInput = key.getBytes();
            // 获得MD5摘要算法的 MessageDigest 对象
            MessageDigest mdInst = MessageDigest.getInstance("MD5");
            // 使用指定的字节更新摘要
            mdInst.update(btInput);
            // 获得密文
            byte[] md = mdInst.digest();
            // 把密文转换成十六进制的字符串形式
            int j = md.length;
            char str[] = new char[j * 2];
            int k = 0;
            for (int i = 0; i < j; i++) {
                byte byte0 = md[i];
                str[k++] = hexDigits[byte0 >>> 4 & 0xf];
                str[k++] = hexDigits[byte0 & 0xf];
            }
            return new String(str);
        } catch (Exception e) {
            logger.error("生成MD5失败", e);
            return null;
        }
    }
	
	
	public  String HttpClientPost(String url, String charset, Map<String, Object> entity,
			Map<String, Object> headers) throws Exception {
		/**
		 * 返回结果
		 */
		String result = "";
		/**
		 * 请求状态码
		 */
		int status = 200;

		/**
		 * 请求类型
		 */
		HttpPost httpPost;
		/**
		 * 请求返回结果
		 */
		HttpResponse response = null;

		HttpClient httpClient = null;

		httpPost = new HttpPost(url);

		// 设置body
		if (entity != null) {
			List<NameValuePair> params = new ArrayList<NameValuePair>(16);
			entity.forEach((k, v) -> {
				params.add(new BasicNameValuePair(k, v.toString()));
			});
			httpPost.setEntity(new UrlEncodedFormEntity(params, Consts.UTF_8));
		}

		if (headers != null) {
			headers.forEach((k, v) -> {
				httpPost.addHeader(k, v.toString());
			});
		}

		// 获取当前客户端对象
		httpClient = new DefaultHttpClient();
		// 通过请求对象获取响应对象
		try {
			response = httpClient.execute(httpPost);
			// 获取请求返回的结果集
			// 判断请求成功
			if (response.getStatusLine().getStatusCode() == status) {
				result = EntityUtils.toString(response.getEntity(), "utf-8");
				System.out.println(result);
			}else {
				System.out.println("-----访问科大讯飞错误");
			}
		} catch (Exception ex) {
			throw new Exception("通过httpClient进行post提交异常：" + ex.getMessage());
		} finally {
			httpPost.releaseConnection();
		}

		return result;
	}
	
	
	private String anaylizeText(String jsonResult) {
		JSONObject jsonObject=JSONObject.parseObject(jsonResult);
		// 状态吗，为00000 则说明范文讯飞成功
		String code = jsonObject.getString("code");
		if (!"00000".equals(code)) {
			return null;
		}
		// 访问讯飞成功，获得结果集
		jsonObject = jsonObject.getJSONObject("data");

		// 语意解析状态码，为4 则代表语意或者问答库成功
		int rc = jsonObject.getIntValue("rc");

		if (!(rc == 0)) {
			SimpleDateFormat dateFormat=new SimpleDateFormat("yyyy-MM-dd HH:ss:mm");
			String dateSimple=dateFormat.format(new Date());
			String content=dateSimple+"\t\t问题: "+jsonObject.getString("text");
			FileOperation.contentAddTxt(StaticValues.ERROR_FILE_PATH, content);
			return null;
		}
		// 服务类型，可以openQA为问答库，其他的则为语意分析
		String serviceName = jsonObject.getString("service");
		
		String keyWord=jsonObject.getJSONObject("answer").getString("text");
		
		return keyWord;
		
		
	}
	
	
	
}
