package com.testmyinterview.portal.kookoo;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.DateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.testmyinterview.portal.dao.KookooDao;
import com.testmyinterview.portal.kookoo.CallStatusCodes;

@Component("scheduleCallBean")
public class ScheduleCallBean {

	@Autowired
	private KookooDao kookooDao;
	@Value("${kookooUrl}")
	private String kookooUrl;

	/**
	 * This Method is used to get interview details
	 * 
	 */
	public void getInterviewDetails() {
		List<Map<String, Object>> callDet = kookooDao.getInterviewCallDet();
		System.out.println("kookoo scheduler calling" + callDet);
		if (!callDet.isEmpty()) {
			for (Map<String, Object> m : callDet) {
				String applicantnumber = m.get("applicantmob").toString();
				String evalnumber =m.get("evalmob").toString();
				String tmiId = m.get("interviewtmiid").toString();
				System.out.println("before buildUrlAndHttpGet");
				
				buildUrlAndHttpGet(evalnumber, applicantnumber, tmiId);
				
				System.out.println("after buildUrlAndHttpGet");
			}
		}
	}

	/**
	 * This Method is used to  build url and send http get request
	 * @param evaluatorPhone
	 * @param applicantPhone
	 * @param tmiId
	 */
	public void buildUrlAndHttpGet(String evaluatorPhone,
			String applicantPhone, String tmiId) {

		int count = kookooDao.getInterviewHistoryCount(tmiId);
		System.out.println("count " +count);
		if (count == 0) {
			kookooDao.insertNewInterviewHistory(tmiId, evaluatorPhone,
					applicantPhone, CallStatusCodes.CALLING_EVALUATOR);
			kookooDao.insertCallInitiatedAuditRow(tmiId,
					CallStatusCodes.INITIATING_CALL);
		}
		String apiKey = "KK2eeb86eb985c9ffb94db6814a32f4cbe";
		String callBackUrl = kookooUrl + "TMIKookooCallBack?tmiId=" + tmiId;
		String demourl = kookooUrl + "TMIKookoo?tmiId=" + tmiId;
		try {
			String url = "http://www.kookoo.in/outbound/outbound.php?phone_no="
					+ evaluatorPhone + "&api_key=" + apiKey + "&url=" + demourl
					+ "&callback_url=" + callBackUrl + "&outbound_version=2";
			
			System.out.println("Kukoo URL " +url);
			sendGet(url);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * This Method is used to HTTP get request
	 * @param url
	 * @throws Exception
	 */
	private void sendGet(String url) throws Exception {

		URL obj = new URL(url);
		HttpURLConnection con = (HttpURLConnection) obj.openConnection();
		// optional default is GET
		con.setRequestMethod("GET");
		// add request header
		// con.setRequestProperty("User-Agent", USER_AGENT);
		int responseCode = con.getResponseCode();
		BufferedReader in = new BufferedReader(new InputStreamReader(
				con.getInputStream()));
		String inputLine;
		StringBuffer response = new StringBuffer();
		while ((inputLine = in.readLine()) != null) {
			response.append(inputLine);
		}
		in.close();
		// print result
	}
}
