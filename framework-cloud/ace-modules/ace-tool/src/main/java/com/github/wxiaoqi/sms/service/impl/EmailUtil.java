package com.github.wxiaoqi.sms.service.impl;

import com.github.wxiaoqi.config.MailConfig;
import com.github.wxiaoqi.security.common.util.BeanUtils;
import com.github.wxiaoqi.security.common.util.StringUtils;
import lombok.extern.slf4j.Slf4j;

import javax.activation.*;
import javax.mail.*;
import javax.mail.internet.*;
import java.net.URL;
import java.util.Properties;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @Author: Lzx
 * @Description: 邮件发送工具类
 * @Date: Created in 10:03 2018/11/21
 * @Modified By:
 */
@Slf4j
public class EmailUtil {
	private MimeMessage mimeMsg; // MIME邮件对象
	private Session session; // 邮件会话对象
	private Properties props; // 系统属性
	@SuppressWarnings("unused")
	private boolean needAuth = false; // smtp是否需要认证
	// smtp认证用户名和密码
	private static String username;
	private static String password;
	private Multipart mp; // Multipart对象,邮件内容,标题,附件等内容均添加到其中后再生成MimeMessage对象
	private static String smtp;
	private static String port;
	private static String fromEmailAddr;
	private static String fromEmailAddrNickName;

	static{

//		ResourceBundle p = PropertyResourceBundle.getBundle("main");
		try {
//			smtp = p.getString("mail.smtp");
//			fromEmailAddr = p.getString("mail.fromEmailAddr");
//			username = p.getString("mail.username");
//			password = p.getString("mail.password");
//			port = p.getString("mail.port");
			MailConfig mailConfig = BeanUtils.getBean(MailConfig.class);
			smtp = mailConfig.getSmtp();
			fromEmailAddr = mailConfig.getFromEmailAddr();
			fromEmailAddrNickName = mailConfig.getFromEmailAddrNickName();
			username = mailConfig.getUsername();
			password = mailConfig.getPassword();
			port = mailConfig.getPort();
		} catch (Exception e) {
			log.error("初始化邮件发送属性异常!" , e);
		}
	}

	public EmailUtil() {
		super();
	}

	/**
	 * 检测邮箱地址是否合法
	 *
	 * @param email
	 * @return true合法 false不合法
	 */
	public static boolean isEmail(String email) {
		if (null == email || "".equals(email))
			return false;
		// Pattern p = Pattern.compile("\\w+@(\\w+.)+[a-z]{2,3}"); //简单匹配
		Pattern p = Pattern
				.compile("\\w+([-+.]\\w+)*@\\w+([-.]\\w+)*\\.\\w+([-.]\\w+)*");// 复杂匹配
		Matcher m = p.matcher(email);
		return m.matches();
	}

	/**
	 * Constructor
	 *
	 * @param smtp
	 *            邮件发送服务器
	 */
	public EmailUtil(String smtp) {
		setSmtpHost(smtp);
		createMimeMessage();
	}

	/**
	 * 设置邮件发送服务器
	 *
	 * @param hostName
	 *            String
	 */
	public void setSmtpHost(String hostName) {
		log.info("设置系统属性：mail.smtp.host = " + hostName);
		if (props == null)
			props = System.getProperties(); // 获得系统属性对象
		props.put("mail.smtp.host", hostName); // 设置SMTP主机
		props.put("mail.smtp.port", port);
		props.put("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");
	}

	/**
	 * 创建MIME邮件对象
	 *
	 * @return
	 */
	public boolean createMimeMessage() {
		try {
			log.info("准备获取邮件会话对象！");
			session = Session.getDefaultInstance(props, null); // 获得邮件会话对象
		} catch (Exception e) {
			log.error("获取邮件会话对象时发生错误！" + e);
			return false;
		}

		log.info("准备创建MIME邮件对象！");
		try {
			mimeMsg = new MimeMessage(session); // 创建MIME邮件对象
			mp = new MimeMultipart();

			return true;
		} catch (Exception e) {
			log.error("创建MIME邮件对象失败！" + e);
			return false;
		}
	}

	/**
	 * 设置SMTP是否需要验证
	 *
	 * @param need
	 */
	public void setNeedAuth(boolean need) {
		log.info("设置smtp身份认证：mail.smtp.auth = " + need);
		if (props == null)
			props = System.getProperties();
		if (need) {
			props.put("mail.smtp.auth", "true");
			props.put("mail.debug", "true");
		} else {
			props.put("mail.smtp.auth", "false");
		}
	}

	/**
	 * 设置用户名和密码
	 *
	 * @param name
	 * @param pass
	 */
	public void setNamePass(String name, String pass) {
		username = name;
		password = pass;
	}

	/**
	 * 设置邮件主题
	 *
	 * @param mailSubject
	 * @return
	 */
	public boolean setSubject(String mailSubject) {
		log.info("设置邮件主题！");
		try {
			mimeMsg.setSubject(mailSubject);
			return true;
		} catch (Exception e) {
			log.error("设置邮件主题发生错误！");
			return false;
		}
	}

	/**
	 * 设置邮件正文
	 *
	 * @param mailBody
	 *            String
	 */
	public boolean setBody(String mailBody) {
		try {
			BodyPart bp = new MimeBodyPart();
			bp.setContent("" + mailBody, "text/html;charset=UTF-8");
			mp.addBodyPart(bp);

			return true;
		} catch (Exception e) {
			log.error("设置邮件正文时发生错误！" + e);
			return false;
		}
	}

	/**
	 * 设置邮件正文
	 *
	 * @param mailBody
	 *            String
	 */
	public boolean setBodyWithImg(String mailBody, String file) {
		try {
			// 创建邮件的正文
			BodyPart text = new MimeBodyPart();
			// setContent(“邮件的正文内容”,”设置邮件内容的编码方式”)
			text.setContent(mailBody + "<br/><img src='cid:a'>", "text/html;charset=UTF-8");

			MailcapCommandMap mc = (MailcapCommandMap) CommandMap
					.getDefaultCommandMap();
			mc.addMailcap("text/html;; x-java-content-handler=com.sun.mail.handlers.text_html");
			mc.addMailcap("text/xml;; x-java-content-handler=com.sun.mail.handlers.text_xml");
			mc.addMailcap("text/plain;; x-java-content-handler=com.sun.mail.handlers.text_plain");
			mc.addMailcap("multipart/*;; x-java-content-handler=com.sun.mail.handlers.multipart_mixed");
			mc.addMailcap("message/rfc822;; x-java-content-handler=com.sun.mail.handlers.message_rfc822");
			CommandMap.setDefaultCommandMap(mc);

			// 创建图片
			MimeBodyPart img = new MimeBodyPart();
			/*
			 * JavaMail API不限制信息只为文本,任何形式的信息都可能作茧自缚MimeMessage的一部分.
			 * 除了文本信息,作为文件附件包含在电子邮件信息的一部分是很普遍的. JavaMail
			 * API通过使用DataHandler对象,提供一个允许我们包含非文本BodyPart对象的简便方法.
			 */
			DataHandler dh = new DataHandler(new URL(file));//图片路径
			img.setDataHandler(dh);
			// 创建图片的一个表示用于显示在邮件中显示
			img.setContentID("a");

			// 关系 正文和图片的
			MimeMultipart mm = new MimeMultipart();
			mm.addBodyPart(text);
			mm.addBodyPart(img);
			mm.setSubType("related");// 设置正文与图片之间的关系
			// 图班与正文的 body
			MimeBodyPart all = new MimeBodyPart();
			all.setContent(mm);
			// 附件与正文（text 和 img）的关系
			MimeMultipart mm2 = new MimeMultipart();
			mm2.addBodyPart(all);
			mm2.setSubType("mixed");// 设置正文与附件之间的关系

			mimeMsg.setContent(mm2);
			mimeMsg.saveChanges();

			return true;
		} catch (Exception e) {
			log.error("设置邮件正文时发生错误！" + e);
			return false;
		}
	}

	/**
	 * 添加附件
	 *
	 * @param file
	 *            String
	 */
	public boolean addFileAffix(String file) {
		return addFileAffix(file,"");
	}

	/**
	 * 添加附件
	 *
	 * @param filename
	 *            String
	 */
	public boolean addFileAffix(String file,String filename) {

		log.info("增加邮件附件：" + filename);
		try {
			BodyPart bp = new MimeBodyPart();

			if(file.indexOf("http")!=-1) {
				//远程资源
				URLDataSource fileds=new URLDataSource(new URL(file));
				bp.setDataHandler(new DataHandler(fileds));
				if(StringUtils.isEmpty(filename)) {
					filename = fileds.getName();
				}
			}else {
				FileDataSource fileds = new FileDataSource(file);
				bp.setDataHandler(new DataHandler(fileds));
				if(StringUtils.isEmpty(filename)) {
					filename = fileds.getName();
				}
			}
			bp.setFileName(MimeUtility.encodeWord(filename));
			mp.addBodyPart(bp);
			return true;
		} catch (Exception e) {
			log.error("增加邮件附件：" + filename + "发生错误！" + e);
			return false;
		}
	}


	/**
	 * 设置发信人
	 *
	 * @param from
	 *            String
	 */
	public boolean setFrom(String from) {
		log.info("设置发信人！");
		try {
			if(fromEmailAddrNickName!=null && !"".equals(fromEmailAddrNickName)) {
				String nick=javax.mail.internet.MimeUtility.encodeText(fromEmailAddrNickName);
				from = nick+" <"+from+">";
			}
			mimeMsg.setFrom(new InternetAddress(from)); // 设置发信人
			return true;
		} catch (Exception e) {
			return false;
		}
	}

	/**
	 * 设置收信人
	 *
	 * @param to
	 *            String
	 */
	public boolean setTo(String to) {
		if (to == null)
			return false;
		try {
			mimeMsg.setRecipients(Message.RecipientType.TO,
					InternetAddress.parse(to));
			return true;
		} catch (Exception e) {
			return false;
		}
	}


	/**
	 * 设置收信人
	 *
	 * @param to
	 *            Address[]
	 */
	public boolean setTo(String[] to) {
		Address[] tos = null;
		if (to == null)
			return false;
		try {
			// 为每个邮件接收者创建一个地址
			tos = new InternetAddress[to.length];
			for (int i=0; i<to.length; i++){
				tos[i] = new InternetAddress(to[i]);
			}
			mimeMsg.setRecipients(Message.RecipientType.TO, tos);
			return true;
		} catch (Exception e) {
			return false;
		}
	}

	/**
	 * 设置抄送人
	 *
	 * @param copyto
	 *            String
	 */
	public boolean setCopyTo(String copyto) {
		if (copyto == null)
			return false;
		try {
			mimeMsg.setRecipients(Message.RecipientType.CC,
					InternetAddress.parse(copyto));
			return true;
		} catch (Exception e) {
			return false;
		}
	}

	/**
	 * 发送邮件
	 */
	public boolean sendOut() {
		try {

			MailcapCommandMap mc = (MailcapCommandMap) CommandMap
					.getDefaultCommandMap();
			mc.addMailcap("text/html;; x-java-content-handler=com.sun.mail.handlers.text_html");
			mc.addMailcap("text/xml;; x-java-content-handler=com.sun.mail.handlers.text_xml");
			mc.addMailcap("text/plain;; x-java-content-handler=com.sun.mail.handlers.text_plain");
			mc.addMailcap("multipart/*;; x-java-content-handler=com.sun.mail.handlers.multipart_mixed");
			mc.addMailcap("message/rfc822;; x-java-content-handler=com.sun.mail.handlers.message_rfc822");
			CommandMap.setDefaultCommandMap(mc);

			mimeMsg.setContent(mp);
			mimeMsg.saveChanges();
			log.info("正在发送邮件....");

			Session mailSession = Session.getInstance(props, null);
			Transport transport = mailSession.getTransport("smtp");
			transport.connect((String) props.get("mail.smtp.host"), username,
					password);

			if (mimeMsg.getRecipients(Message.RecipientType.TO) != null)
				transport.sendMessage(mimeMsg,
						mimeMsg.getRecipients(Message.RecipientType.TO));
			if (mimeMsg.getRecipients(Message.RecipientType.CC) != null)
				transport.sendMessage(mimeMsg,
						mimeMsg.getRecipients(Message.RecipientType.CC));
			// transport.send(mimeMsg);

			log.info("发送邮件成功！");
			transport.close();

			return true;
		} catch (Exception e) {
			log.error("邮件发送失败！" + e);
			return false;
		}
	}

	/**
	 * 发送邮件
	 */
	public boolean sendOutImg() {
		try {
			Session mailSession = Session.getInstance(props, null);
			Transport transport = mailSession.getTransport("smtp");
			transport.connect((String) props.get("mail.smtp.host"), username,
					password);

			if (mimeMsg.getRecipients(Message.RecipientType.TO) != null)
				transport.sendMessage(mimeMsg,
						mimeMsg.getRecipients(Message.RecipientType.TO));
			if (mimeMsg.getRecipients(Message.RecipientType.CC) != null)
				transport.sendMessage(mimeMsg,
						mimeMsg.getRecipients(Message.RecipientType.CC));

			log.info("发送邮件成功！");
			transport.close();

			return true;
		} catch (Exception e) {
			log.error("邮件发送失败！" + e);
			return false;
		}
	}

	/**
	 * 调用sendOut方法完成邮件发送
	 *
	 * @param smtp
	 * @param from
	 * @param to
	 * @param subject
	 * @param content
	 * @param username
	 * @param password
	 * @return boolean
	 */
	public static boolean send(String smtp, String from, String to,
							   String subject, String content, String username, String password) {
		EmailUtil theMail = new EmailUtil(smtp);
		theMail.setNeedAuth(true); // 需要验证

		if (!theMail.setSubject(subject))
			return false;
		if (!theMail.setBody(content))
			return false;
		if (!theMail.setTo(to))
			return false;
		if (!theMail.setFrom(from))
			return false;
		theMail.setNamePass(username, password);

		if (!theMail.sendOut())
			return false;
		return true;
	}

	/**
	 * 调用sendOut方法完成邮件发送
	 *
	 * @param smtp
	 * @param from
	 * @param to
	 * @param subject
	 * @param content
	 * @param username
	 * @param password
	 * @return boolean
	 */
	public static boolean send(String smtp, String from, String [] to,
							   String subject, String content, String username, String password) {
		EmailUtil theMail = new EmailUtil(smtp);
		theMail.setNeedAuth(true); // 需要验证

		if (!theMail.setSubject(subject))
			return false;
		if (!theMail.setBody(content))
			return false;
		if (!theMail.setTo(to))
			return false;
		if (!theMail.setFrom(from))
			return false;
		theMail.setNamePass(username, password);

		if (!theMail.sendOut())
			return false;
		return true;
	}

	public static void main(String[] args) {
		//emailSend("huangxiaolong@virgindatax.com", "O+测试", "O+");
		emailSendWithFileAffix("huangxiaolong@virgindatax.com", "O+测试", "O+","https://invtest.jss.com.cn/group1/M00/07/38/wKjScVvH8neAHzOdAABrSOHrVcE332.pdf","thebridge 电子发票");
		//emailSendWithFileAffix("huangxiaolong@virgindatax.com", "O+测试", "O+","D:\\logs\\spring.log.4");
	}

	/**
	 * 调用sendImg方法完成邮件发送
	 *
	 * @param smtp
	 * @param from
	 * @param to
	 * @param subject
	 * @param content
	 * @param username
	 * @param password
	 * @return boolean
	 */
	public static boolean sendImg(String smtp, String from, String to,
								  String subject, String content, String file, String username, String password) {
		EmailUtil theMail = new EmailUtil(smtp);
		theMail.setNeedAuth(true); // 需要验证

		if (!theMail.setSubject(subject))
			return false;
		if (!theMail.setBodyWithImg(content, file))
			return false;
		if (!theMail.setTo(to))
			return false;
		if (!theMail.setFrom(from))
			return false;
		theMail.setNamePass(username, password);

		if (!theMail.sendOutImg())
			return false;
		return true;
	}

	/**
	 * 调用sendOut方法完成邮件发送,带抄送
	 *
	 * @param smtp
	 * @param from
	 * @param to
	 * @param copyto
	 * @param subject
	 * @param content
	 * @param username
	 * @param password
	 * @return boolean
	 */
	public static boolean sendAndCc(String smtp, String from, String to,
									String copyto, String subject, String content, String username,
									String password) {
		EmailUtil theMail = new EmailUtil(smtp);
		theMail.setNeedAuth(true); // 需要验证

		if (!theMail.setSubject(subject))
			return false;
		if (!theMail.setBody(content))
			return false;
		if (!theMail.setTo(to))
			return false;
		if (!theMail.setCopyTo(copyto))
			return false;
		if (!theMail.setFrom(from))
			return false;
		theMail.setNamePass(username, password);

		if (!theMail.sendOut())
			return false;
		return true;
	}

	/**
	 * 调用sendOut方法完成邮件发送,带附件
	 *
	 * @param smtp
	 * @param from
	 * @param to
	 * @param subject
	 * @param content
	 * @param username
	 * @param password
	 * @param file
	 * @param fileName
	 *            附件路径
	 * @return
	 */
	public static boolean send(String smtp, String from, String to,
							   String subject, String content, String username, String password,
							   String file, String fileName) {
		EmailUtil theMail = new EmailUtil(smtp);
		theMail.setNeedAuth(true); // 需要验证

		if (!theMail.setSubject(subject))
			return false;
		if (!theMail.setBody(content))
			return false;
		if (!theMail.addFileAffix(file,fileName))
			return false;
		if (!theMail.setTo(to))
			return false;
		if (!theMail.setFrom(from))
			return false;
		theMail.setNamePass(username, password);

		if (!theMail.sendOut())
			return false;
		return true;
	}

	/**
	 * 调用sendOut方法完成邮件发送,带附件和抄送
	 *
	 * @param smtp
	 * @param from
	 * @param to
	 * @param copyto
	 * @param subject
	 * @param content
	 * @param username
	 * @param password
	 * @param filename
	 * @return
	 */
	public static boolean sendAndCc(String smtp, String from, String to,
									String copyto, String subject, String content, String username,
									String password, String filename) {
		EmailUtil theMail = new EmailUtil(smtp);
		theMail.setNeedAuth(true); // 需要验证

		if (!theMail.setSubject(subject))
			return false;
		if (!theMail.setBody(content))
			return false;
		if (!theMail.addFileAffix(filename))
			return false;
		if (!theMail.setTo(to))
			return false;
		if (!theMail.setCopyTo(copyto))
			return false;
		if (!theMail.setFrom(from))
			return false;
		theMail.setNamePass(username, password);

		if (!theMail.sendOut())
			return false;
		return true;
	}

	/**
	 * 发送注册验证码
	 * @param email
	 *            收件箱
	 * @return true 表示发送成功 false 表示发送失败
	 */
	public static boolean emailSend(String email, String title , String content) {
		//status：true 表示发送成功    false 表示发送失败
		return send(smtp, fromEmailAddr, email, title, content, username, password);
	}

	/**
	 * 发送邀请访客图片
	 * @param email 收件箱
	 * @param file 邀请访客图片
	 * @return
	 */
	public static boolean emailSendWithImg( String email , String title , String file) {
		String content = "";
		//status：true 表示发送成功    false 表示发送失败
		return sendImg(smtp, fromEmailAddr, email, title, content, file, username, password);
	}

	/**
	 * 发送邮件
	 * @param receivers 收件箱列表
	 * @param title 标题
	 * @return content 邮件内容
	 */
	public static boolean massEmailSend(String[] receivers, String title , String content) {
		//status：true 表示发送成功    false 表示发送失败
		return send(smtp, fromEmailAddr, receivers, title, content, username, password);
	}

	/**
	 * 发送邮件含附件
	 * @param email 收件箱
	 * @param title 标题
	 * @param content 内容
	 * @param file 附件文件
	 * @return
	 */
	public static boolean emailSendWithFileAffix( String email , String title , String content, String file, String fileName) {
		//status：true 表示发送成功    false 表示发送失败
		return send(smtp, fromEmailAddr, email, title, content, username, password,file,fileName);
	}
}
