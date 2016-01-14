package com.testmyinterview.portal.util;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.TimeZone;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import javax.servlet.http.HttpServletRequest;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.apache.velocity.app.VelocityEngine;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.mail.javamail.MimeMessagePreparator;
import org.springframework.scheduling.annotation.Async;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.ui.Model;
import org.springframework.ui.velocity.VelocityEngineUtils;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import com.testmyinterview.portal.bean.AdminProfileBean;
import com.testmyinterview.portal.bean.CustomTemplateBean;
import com.testmyinterview.portal.bean.InterViewBean;
import com.testmyinterview.portal.bean.PaymentBean;
import com.testmyinterview.portal.bean.ProfileBean;
import com.testmyinterview.portal.dao.AdminDAO;
import com.testmyinterview.portal.dao.ExternalUserDao;
import com.testmyinterview.portal.dao.ProfileDAO;

public class TmiUtil {

	@Autowired
	private ExternalUserDao externalUserDao;
	@Autowired
	private ProfileDAO profileDAO;
	@Autowired
	private AdminDAO adminDAO;

	/**
	 * This method is used to get the current time in GMT format
	 * 
	 * @throws ParseException
	 * @return :gmtFormat.format(postedDate)
	 */
	public String getCurrentGmtTime() {
		Date today = Calendar.getInstance().getTime();
		SimpleDateFormat formatter = new SimpleDateFormat(
				"MM-dd-yyyy hh:mm:ss aa Z");
		String newdate = formatter.format(today);
		Date postedDate = new Date();
		try {
			postedDate = new SimpleDateFormat("MM-dd-yyyy hh:mm:ss aa Z")
					.parse(newdate);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		DateFormat gmtFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		TimeZone gmtTime = TimeZone.getTimeZone("GMT");
		gmtFormat.setTimeZone(gmtTime);
		return gmtFormat.format(postedDate);
	}

	/**
	 * This method is used to get the current Date in GMT format
	 * 
	 * @throws ParseException
	 * @return :gmtFormat.format(postedDate)
	 */
	public String getCurrentGmtDate() {
		Date today = Calendar.getInstance().getTime();
		SimpleDateFormat formatter = new SimpleDateFormat(
				"MM-dd-yyyy hh:mm:ss aa Z");
		String newdate = formatter.format(today);
		Date postedDate = new Date();
		try {
			postedDate = new SimpleDateFormat("MM-dd-yyyy hh:mm:ss aa Z")
					.parse(newdate);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		DateFormat gmtFormat = new SimpleDateFormat("yyyy-MM-dd");
		TimeZone gmtTime = TimeZone.getTimeZone("GMT");
		gmtFormat.setTimeZone(gmtTime);
		return gmtFormat.format(postedDate);
	}

	/**
	 * This method is used to get the current time in GMT format
	 * 
	 * @param slotid
	 * @param scheduledate
	 * @throws ParseException
	 * @return :gmtFormat.format(postedDate)
	 */
	public String changeUserTimeZoneToGmt(String scheduledate, int slotid) {
		SimpleDateFormat formatter1 = new SimpleDateFormat(
				"yyyy-MM-dd HH:mm:ss Z");
		String dateInString = scheduledate + " 17:48:00 +0530";
		Date date;
		Date postedDate = new Date();
		try {
			date = formatter1.parse(dateInString);
			String newdate = formatter1.format(date);
			postedDate = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss Z")
					.parse(newdate);

		} catch (ParseException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		DateFormat gmtFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		TimeZone gmtTime = TimeZone.getTimeZone("GMT");
		gmtFormat.setTimeZone(gmtTime);
		return gmtFormat.format(postedDate);
	}

	/**
	 * this method is used to get login useid
	 * 
	 * @return pb.getUserId()
	 */
	public int getUserId() {

		Authentication auth = SecurityContextHolder.getContext()
				.getAuthentication();
		String loggedUserName = auth.getName();
		ProfileBean pb = new ProfileBean();
		pb.setEmailaddress(loggedUserName);
		pb.setUserId(profileDAO.getUserId(pb));
		return pb.getUserId();

	}

	/**
	 * this method is used to get login user name
	 * 
	 * @return auth.getName()
	 */
	public String getEmailId() {
		Authentication auth = SecurityContextHolder.getContext()
				.getAuthentication();
		return auth.getName();
	}

	/**
	 * this method is used to get login AdminUserId
	 * 
	 * @return abp.getAdminId()
	 */
	public int getAdminUserId() {

		Authentication auth = SecurityContextHolder.getContext()
				.getAuthentication();
		String loggedUserName = auth.getName();
		AdminProfileBean abp = new AdminProfileBean();
		abp.setEmailaddress(loggedUserName);
		abp.setAdminId(adminDAO.getAdminId(abp));
		return abp.getAdminId();

	}

	/**
	 * this method is used to get interview id based on login user name and the
	 * interview Status is Zero
	 * 
	 * @return interviewId
	 */
	public int getInterviewId() {

		Authentication auth = SecurityContextHolder.getContext()
				.getAuthentication();
		String loggedUserName = auth.getName();
		ProfileBean profileBean = new ProfileBean();
		profileBean.setEmailaddress(loggedUserName);
		int userId = profileDAO.getUserId(profileBean);
		InterViewBean ib = new InterViewBean();
		ib.setUserid(userId);
		return externalUserDao.getInterViewID(ib);

	}

	/**
	 * this method is used to get hash code and amount of the interview
	 * 
	 * @param model
	 *            :amount,hash
	 * @param PaymentBean
	 *            pb
	 * @return
	 */
	public Model paymentDetails(Model model, PaymentBean pb) {
		String amountFl = pb.getAmount();
		String stringToHash = pb.getKey() + "|" + pb.getTxnId() + "|"
				+ amountFl + "|" + pb.getProductInfo() + "|"
				+ pb.getFirstName() + "|" + pb.getEmailId() + "|"
				+ pb.getActualAmount() + "|" + pb.getEvalAmount() + "|||||||||"
				+ pb.getSalt();

		MessageDigest mda;
		String hashedString = "";
		try {
			mda = MessageDigest.getInstance("SHA-512");
			byte[] digesta = mda.digest(stringToHash.getBytes());
			StringBuffer sb = new StringBuffer();
			for (int i = 0; i < digesta.length; i++) {
				sb.append(Integer.toString((digesta[i] & 0xff) + 0x100, 16)
						.substring(1));
			}
			hashedString = sb.toString(); // byte to
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		}

		model.addAttribute("amount", amountFl);
		model.addAttribute("hash", hashedString);
		return model;
	}

	/**
	 * this method is used to convert byte code to String
	 * 
	 * @param bytes
	 * @return:sb.toString()
	 */
	public String getString(byte[] bytes) {
		StringBuffer sb = new StringBuffer();
		for (int i = 0; i < bytes.length; i++) {
			byte b = bytes[i];
			String hex = Integer.toHexString((int) 0x00FF & b);
			if (hex.length() == 1) {
				sb.append("0");
			}
			sb.append(hex);
		}
		return sb.toString();
	}

	/**
	 * this method is used to convert String to byte code
	 * 
	 * @param :input
	 * @return:getString(bytes)
	 */
	public String md5Encode(String input) {
		try {
			MessageDigest md = MessageDigest.getInstance("MD5");
			byte[] bytes = md.digest(new String(input).getBytes("UTF-8"));
			return getString(bytes);
		} catch (Exception e) {
			return "";
		}
	}

	/**
	 * This method is used to upload the .xlsx files to local system
	 * 
	 * @param req
	 * @param fileType
	 * @param fileName
	 * @return AllUserList
	 */
	public static ArrayList parseUploadedFile(HttpServletRequest req,
			String fileType, String fileName) {
		ArrayList AllUserList = new ArrayList();
		try {
			MultipartHttpServletRequest multipartRequest = (MultipartHttpServletRequest) req;
			Enumeration mrEnum = multipartRequest.getParameterNames();
			MultipartFile file = multipartRequest.getFile(fileType);
			FileUploader fileUploader = new FileUploader();
			if (file.getSize() > 0
					&& file.getSize() < fileUploader.maxFileSize()) {
				// data was uploaded
				if (fileName != null && fileName.contains("xlsx")) {
					AllUserList = readExcelFile(file.getInputStream());
				}
			} else {
				if (file.getSize() > fileUploader.maxFileSize()) {
					throw new org.springframework.web.multipart.MaxUploadSizeExceededException(
							fileUploader.maxFileSize());
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return AllUserList;
	}

	/**
	 * This method is used to read the data from excel file (xls,xlsx format
	 * only)
	 * 
	 * @param fileName
	 * @return :array AllUserList
	 */
	public static ArrayList readExcelFile(InputStream inputStream) {
		ArrayList AllUserList = new ArrayList();
		try {
			// this object used for upload one formats (xlsx)
			// XSSFWorkbook workbook = new XSSFWorkbook(inputStream);
			// this object used for upload two formats (xls,xlsx)
			org.apache.poi.ss.usermodel.Workbook workbook = WorkbookFactory
					.create(inputStream);
			// Get first/desired sheet from the workbook
			// this object used for upload one formats (xlsx)
			// XSSFSheet sheet = workbook.getSheetAt(0);
			// this object used for upload two formats (xls,xlsx)
			org.apache.poi.ss.usermodel.Sheet sheet = workbook.getSheetAt(0);
			// Iterate through each rows one by one
			Iterator<Row> rowIterator = sheet.iterator();
			while (rowIterator.hasNext()) {
				Row row = rowIterator.next();
				ArrayList userList = new ArrayList();
				// For each row, iterate through all the columns
				Iterator<Cell> cellIterator = row.cellIterator();
				while (cellIterator.hasNext()) {
					Cell cell = cellIterator.next();
					// Check the cell type and format accordingly
					switch (cell.getCellType()) {
					case Cell.CELL_TYPE_STRING:
						if (cell.getStringCellValue() != null
								&& !cell.getStringCellValue().isEmpty()) {
							userList.add(cell.getStringCellValue());
						}
						break;
					}
				}
				AllUserList.add(userList);
			}
			// file.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return AllUserList;
	}

	/**
	 * this method is used to HTTP GET request
	 * 
	 * @param url
	 * @return :convertJsonToListOfMap
	 * @throws Exception
	 */
	public List<Map<String, Object>> sendGet(String url) throws Exception {

		URL obj = new URL(url);
		HttpURLConnection con = (HttpURLConnection) obj.openConnection();
		con.setRequestMethod("GET");
		BufferedReader in = new BufferedReader(new InputStreamReader(
				con.getInputStream()));
		String inputLine;
		StringBuffer response = new StringBuffer();
		inputLine = in.readLine();
		while ((inputLine = in.readLine()) != null) {
			response.append(inputLine);
		}
		in.close();
		return convertJsonToListOfMap(response.toString());
	}

	/**
	 * this method is used to convert Json object to Listofmap
	 * 
	 * @param json
	 * @return list
	 * @throws Exception
	 */
	public static List<Map<String, Object>> convertJsonToListOfMap(String json)
			throws Exception {
		JSONArray jArray = new JSONArray(json);
		List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
		try {
			for (int i = 0; i < jArray.length(); i++) {
				JSONObject obj;
				Map<String, Object> map = new HashMap<String, Object>();
				obj = jArray.getJSONObject(i);
				Iterator iterator = obj.keys();
				while (iterator.hasNext()) {
					String key = iterator.next().toString();
					String value = obj.getString(key);
					map.put(key, value);
				}
				list.add(map);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return list;
	}

	/**
	 * This method is used to send email with velocity template
	 * 
	 * @param customTemplate
	 * @param mailSender
	 * @param player
	 * @param velocityEngine
	 */
	public void postEmail(final CustomTemplateBean customTemplate,
			JavaMailSender mailSender, final VelocityEngine velocityEngine) {

		MimeMessagePreparator preparator = new MimeMessagePreparator() {
			public void prepare(MimeMessage mimeMessage) {
				MimeMessageHelper message = new MimeMessageHelper(mimeMessage);
				if (customTemplate.getRecepients() != null) {
					try {
						message.setBcc(customTemplate.getRecepients());
					} catch (MessagingException e) {

						e.printStackTrace();
					}
				} else {
					try {
						message.setBcc(customTemplate.getEmailAddress());
					} catch (MessagingException e) {

						e.printStackTrace();
					}
				}

				try {
					message.setFrom(customTemplate.getSenderEmailId());
				} catch (MessagingException e) {

					e.printStackTrace();
				}
				try {
					message.setSubject(customTemplate.getMailSubject());
				} catch (MessagingException e) {

					e.printStackTrace();
				}
				Map model = new HashMap();
				model.put("customTemplate", customTemplate);
				String notificationMsg = VelocityEngineUtils
						.mergeTemplateIntoString(velocityEngine,
								"com/testmyinterview/portal/template/"
										+ customTemplate.getTemplateName(),
								"UTF-8", model);
				try {
					message.setText(notificationMsg, true);

				} catch (MessagingException e) {
					e.printStackTrace();
				}

			}
		};
		mailSender.send(preparator);
	}

}