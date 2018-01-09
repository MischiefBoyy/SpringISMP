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
		System.out.println(qaService.getByClick(13, 0));
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
			levelTwoDAO.addLevelTwo(levelOne);
			int levelOneId=levelOne.getId();
			switch (question) {
			case "PC端邮箱Exchange配置":
				String[] questionBA= {"步骤一：证书的导入和安装","步骤二：配置OUTLOOK","步骤三：验证配置完成"};
				
				for(String baQuestion:questionBA) {
					LevelTwo balevel=new LevelTwo();
					balevel.setParentId(levelOneId);
					balevel.setQuestion(baQuestion);
					balevel.setIsBase(0);
					balevel.setLevel(2);
					levelTwoDAO.addLevelTwo(balevel);
					int resultId=balevel.getId();
					switch (baQuestion) {
					case "步骤一：证书的导入和安装":
						String[] questionB= {"1、下载根证书certnew.cer(请在公司内网访问)","2、解压后双击certnew.cer，点击“Install Certificate…”","3、选择证书保存位置：Local Machine(本地计算机)","4、自定义选择证书位置：Place all certificates in the following store-Browser","5、选择“Trusted Root Certification Authorities”（受信任的证书颁布机构)","6、点击“Finish”（完成），完成根证书导入。"};
						for(String bQuestion:questionB) {
							LevelTwo blevel=new LevelTwo();
							blevel.setParentId(resultId);
							blevel.setQuestion(bQuestion);
							blevel.setIsBase(1);
							blevel.setLevel(3);
							levelTwoDAO.addLevelTwo(blevel);
						}
						break;
					case "步骤二：配置OUTLOOK":
						String[] questionC= {"1、打开【计算机】 - 【控制面板】–【邮件】"
								,"2、选择 【手动设置或其他服务器类型】 ，然后点击 【下一步】"
								,"3、默认选择【Microsoft Exchange Server或兼容的服务】，选择后点击【下一步】"
								,"4、在服务器设置界面中：【服务器：】文本框中输入  ca-exchange.in-rich.com\t【用户名：】文本框中输入自己的邮件地址，例： XXX@in-rich.com\t勾选“使用缓存Exchange模式”\t输入后点击 【其他设置】"
								,"5、在弹窗的选项卡中： 1）点击【连接】2）勾选【使用HTP连接到Microsoft Exchange】3）点击【Exchange 代理服务器设置】"
								,"6、在弹出框中，（1）“连接设置”框中，输入服务器地址 emal.in-rich.com （2）勾选【在快速网络中，首先……】与 【在低速网络中，使用…..】（3）修改代理服务器验证设置中的验证 选择 【基本身份验证】（4）点击【确定】"
								,"7、返回上一级，点击【确定】"
								,"8、返回“添加账户”界面，点击【检查姓名】"
								,"9、在“Windows Security”界面确认用户名和密码信息（1）勾选“Remember my credentials”（记住我的用户信息）（2）点击 【确定】"
								,"10、验证账号密码，如果该界面一直出现则为账号或密码错误"
								,"11、验证成功后，不会再有验证提示，且服务器和用户会下划线标示，点击【下一步】"
								,"12、点击【完成】"};
						for(String cQuestion:questionC) {
							LevelTwo blevel=new LevelTwo();
							blevel.setParentId(resultId);
							blevel.setQuestion(cQuestion);
							blevel.setIsBase(1);
							blevel.setLevel(3);
							levelTwoDAO.addLevelTwo(blevel);
						}
						break;
					case "步骤三：验证配置完成":
						String[] questionD= {"1、打开OUTLOOK客户端，第一次使用OUTLOOK 会提示输入用户名密码。"
											,"2、以下错误，是由于没有安装 CA根证书， 请参照文档中下载 并且安装根证书"};
						for(String dQuestion:questionD) {
							LevelTwo blevel=new LevelTwo();
							blevel.setParentId(resultId);
							blevel.setQuestion(dQuestion);
							blevel.setIsBase(1);
							blevel.setLevel(3);
							levelTwoDAO.addLevelTwo(blevel);
						}
						break;

					default:
						break;
					}
				}
				
				
				break;

			default:
				break;
			}
		}
		
		System.out.println("-------------SUCCESS");
		
	}
	
	
	@Test
	public void testGetTab() {
		qaService.getTable();
	}
	
	@Test
	public void testUpdateState() {
		qaService.delete(49);
	}


}
