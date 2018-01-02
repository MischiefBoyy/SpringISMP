package com.inrich.util;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.inrich.dao.LevelTwoDAO;
import com.inrich.model.LevelTwo;
import com.inrich.service.QaService;

@RunWith(SpringRunner.class)
@SpringBootTest
public class ChildTest {
	@Autowired
	private QaService qaService;

	@Autowired
	LevelTwoDAO levelTwoDAO;

	@Test
	public void testOutCallAction() {
		System.out.println(qaService.getIndex());
	}

	@Test
	public void getByClick() {
		System.out.println(qaService.getByClick(11, 1));
	}

	@Test
	public void addLevelTwo() {
		LevelTwo levelBase = new LevelTwo();
		levelBase.setAnswer(null);
		levelBase.setClickNum(0);
		levelBase.setIsBase(0);
		levelBase.setKeyWord("新催收系统");
		levelBase.setLevel(0);
		levelBase.setParentId(0);
		levelBase.setQuestion("新催收系统");
		int levelBaseId = levelTwoDAO.addLevelTwo(levelBase);

		String[] questionA = { "环境要求", "登录问题", "拨打电话问题", "录音获取" };

		for (String question : questionA) {
			LevelTwo level = new LevelTwo();
			level.setLevel(1);
			level.setClickNum(0);
			level.setParentId(levelBaseId);
			level.setQuestion(question);

			if (question.equals("拨打电话问题")) {
				level.setAnswer("确保软电话已经签入</br>刷新页面后需要重新签入软电话，故不宜随意刷新</br>若使用的电话系统不是PolyLink，可换其他浏览器使用，加快速度");
				level.setIsBase(1);
				levelTwoDAO.addLevelTwo(level);
			} else if (question.equals("录音获取")) {
				level.setAnswer("地址 http://172.16.2.12:8098/RecordQuery</br>录音类型:PolyLink录音,潮流录音");
				level.setIsBase(1);
				levelTwoDAO.addLevelTwo(level);
			} else {
				level.setAnswer(null);
				level.setIsBase(0);
				levelTwoDAO.addLevelTwo(level);

				int i = 0;
				switch (question) {
				case "环境要求":
					String[] questionOp = { "操作系统", "浏览器", "分辨率", "软件化插件" };
					String[] answerOp = { "建议操作系统：Windows7、Windows10",
							"Internet Explorer 11以上版本</br>Adobe Flash Player (要求10.0以上版本)</br>允许弹出窗口</br>把本系统加入可信任站点</br>允许Cookie</br>允许活动脚本",
							"1366 x 768 以上像素", "视分支机构实际情况安装" };
					for (String s : questionOp) {
						LevelTwo levelOp = new LevelTwo();
						levelOp.setLevel(2);
						levelOp.setClickNum(0);
						levelOp.setIsBase(1);
						levelOp.setParentId(level.getId());
						System.out.println("question:" + s);
						levelOp.setQuestion(s);
						levelOp.setAnswer(answerOp[i]);
						levelTwoDAO.addLevelTwo(levelOp);
						i++;
					}
					break;
				case "登录问题":
					String[] questionLogin = { "有提示密码账号错误", "没有提示错误信息" };
					String[] answerLogin = { "请先核对账号密码，如确认无误可联系管理员重置。", "按Ctrl+F5清空缓存</br>浏览器设置清空浏览数据" };
					for (String s : questionLogin) {
						LevelTwo levelOp = new LevelTwo();
						levelOp.setLevel(2);
						levelOp.setClickNum(0);
						levelOp.setIsBase(1);
						levelOp.setParentId(level.getId());
						levelOp.setQuestion(s);
						levelOp.setAnswer(answerLogin[i]);
						levelTwoDAO.addLevelTwo(levelOp);
						i++;
					}
					break;
				default:
					break;
				}
			}

		}

	}
	
	@Test
	public void testDoText() {
		System.out.println(qaService.doText("我不会设置催收系统的浏览器"));
	}
	
	@Test
	public void testAddPcHelpdesk() {
		int helpBaseId = 12;
		
		String[] questionA = { "PC端邮箱Exchange配置", "Android邮箱Exchange配置", "IOS邮箱Exchange配置"};
		String[] keyWordA = { "Helpdesk_pc", "Helpdesk_Android", "Helpdesk_IOS"};
		
		int i=0;
		for(String question:questionA) {
			LevelTwo levelOne=new LevelTwo();
			levelOne.setParentId(helpBaseId);
			levelOne.setQuestion(question);
			levelOne.setKeyWord(keyWordA[i]);
			i++;
			levelOne.setLevel(1);
			int levelOneId=levelTwoDAO.addLevelTwo(levelOne);
			switch (question) {
			case "PC端邮箱Exchange配置":
				String[] questionB= {"1、下载根证书certnew.cer(请在公司内网访问)","2、解压后双击certnew.cer，点击“Install Certificate…”","3、选择证书保存位置：Local Machine(本地计算机)","4、自定义选择证书位置：Place all certificates in the following store-Browser","5、选择“Trusted Root Certification Authorities”（受信任的证书颁布机构)","6、点击“Finish”（完成），完成根证书导入。"};
				for(String bQuestion:questionB) {
					LevelTwo blevel=new LevelTwo();
					blevel.setParentId(levelOneId);
					blevel.setQuestion(bQuestion);
					blevel.setIsBase(1);
					blevel.setLevel(2);
					levelTwoDAO.addLevelTwo(blevel);
				}
				break;

			default:
				break;
			}
		}
		
	}

}
