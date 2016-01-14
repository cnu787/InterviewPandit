package com.testmyinterview.portal.kookoo;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Enumeration;
import java.util.Locale;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.testmyinterview.portal.dao.ExternalUserDao;
import com.testmyinterview.portal.dao.KookooDao;
import com.testmyinterview.portal.kookoo.Dial.MusicOnHold;

/**
 * Handles requests for the application home page.
 */
@Controller
public class KookooController {

	@Autowired
	private KookooDao kookooDao;
	@Value("${kookooUrl}")
	private String kookooUrl;
	@Autowired
	private ExternalUserDao externalUserDao;
	private static final Logger logger = LoggerFactory
			.getLogger(KookooController.class);

	/**
	 * This Method is used to build Url And Call
	 * @param evaluatorPhone
	 * @param tmiId
	 */
	public void buildUrlAndCall(String evaluatorPhone, String tmiId) {
		String apiKey = "KK02176db8eeaaa15924b29ce79efad986";
		String callBackUrl = kookooUrl + "TMIKookooCallBack?tmiId=" + tmiId;
		String demourl = kookooUrl + "TMIKookoo?tmiId=" + tmiId;
		try {
			String url = "http://www.kookoo.in/outbound/outbound.php?phone_no="
					+ evaluatorPhone + "&api_key=" + apiKey + "&url=" + demourl
					+ "&callback_url=" + callBackUrl + "&outbound_version=2";
			sendGet(url);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * This Method is used to send  url
	 * @param url
	 * @throws Exception
	 */
	private void sendGet(String url) throws Exception {

		URL obj = new URL(url);
		HttpURLConnection con = (HttpURLConnection) obj.openConnection();
		con.setRequestMethod("GET");
		int responseCode = con.getResponseCode();
		BufferedReader in = new BufferedReader(new InputStreamReader(
				con.getInputStream()));
		String inputLine;
		StringBuffer response = new StringBuffer();
		while ((inputLine = in.readLine()) != null) {
			response.append(inputLine);
		}
		in.close();
	}

	/**
	 * This Method is used to connect kookoo
	 * @param locale
	 * @param model
	 * @param request
	 */
	@Secured({ "ROLE_ADMIN", "ROLE_CCR"})
	@RequestMapping(value = "/AdminKookooConnect", method = RequestMethod.GET)
	@ResponseBody
	public void AdminKookooConnect(Locale locale, Model model,
			HttpServletRequest request) {
		String evaluatorPhone = request.getParameter("evaluatorPhone");
		String applicantPhone = request.getParameter("applicantPhone");
		String tmiId = request.getParameter("tmiId");
		buildUrlAndHttpGet(evaluatorPhone, applicantPhone, tmiId);
	}

	/**
	 * This Method is used to kookoo call back
	 * @param locale
	 * @param model
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/TMIKookooCallBack", method = RequestMethod.POST)
	@ResponseBody
	public String kookooCallBack(Locale locale, Model model,
			HttpServletRequest request) {
		Enumeration parameters = request.getParameterNames();
		while (parameters.hasMoreElements()) {
			String parameterName = parameters.nextElement().toString();
			String params = request.getParameter(parameterName);
		}
		String tmiId = request.getParameter("tmiId") == null ? "" : request
				.getParameter("tmiId");
		String sid = request.getParameter("sid");
		String status = request.getParameter("status") == null ? "" : request
				.getParameter("status");
		String number = request.getParameter("phone_no") == null ? "" : request
				.getParameter("phone_no");
		int interviewer_call_count = kookooDao.getEvaluatorNACount(tmiId);
		// If Evaluator not answers, try for one more time.
		if (status.equals("NoAnswer") && interviewer_call_count < 2) {
			// Update status in history table to show that we are retrying the
			// call.
			kookooDao.updateHistoryEvaluatorResponse(tmiId, sid,
					CallStatusCodes.CALLING_EVALUATOR, status,
					interviewer_call_count + 1);
			// Insert a row in audit table to show that evaluator did not answer
			// the call.
			kookooDao.insertEvaluatorResponseAuditRow(tmiId,
					CallStatusCodes.RETRYING_EVALUATOR, sid, null, status);
			buildUrlAndCall(number, tmiId);
		} else if (status.equals("NoAnswer") && interviewer_call_count >= 2) {
			// Update the history row so that the evaluator is not available.
			kookooDao.updateHistoryEvaluatorResponse(tmiId, sid,
					CallStatusCodes.EVALUATOR_NA, status,
					interviewer_call_count + 1);
			// Insert a new row that the call has not been answered by the
			// evaluator.
			kookooDao.insertEvaluatorResponseAuditRow(tmiId,
					CallStatusCodes.EVALUATOR_NA, sid, null, status);
		} else if (kookooDao.getDMFStatus(tmiId) != CallStatusCodes.SUCCESS) {
			Map phoneNumMap = kookooDao.getPhoneNumByTmiId(tmiId);
			buildUrlAndHttpGet(phoneNumMap.get("evaluator_phone").toString(),
					phoneNumMap.get("applicant_phone").toString(), tmiId);

		} else {
			kookooDao.insertEvaluatorResponseAuditRow(tmiId,
					CallStatusCodes.HANG_UP, sid, null, status);
			kookooDao.updateHistoryEvaluatorResponse(tmiId, sid, status, 0);
		}
		return "";
	}

	/**
	 * This Method is used to update kookoo calling information
	 * @param locale
	 * @param model
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/TMIKookoo", method = RequestMethod.GET)
	@ResponseBody
	public String tmiKookoo(Locale locale, Model model,
			HttpServletRequest request) {
		Enumeration parameters = request.getParameterNames();
		while (parameters.hasMoreElements()) {
			String parameterName = parameters.nextElement().toString();
			String params = request.getParameter(parameterName);
		}
		Response resp = new Response();
		String event = request.getParameter("event") == null ? "" : request
				.getParameter("event");
		String tmiId = request.getParameter("tmiId");
		String sid = request.getParameter("sid");

		String status = request.getParameter("status") == null ? "" : request
				.getParameter("status");
		String callDuration = request.getParameter("callduration") == null ? ""
				: request.getParameter("callduration");

		String message = request.getParameter("message");
		String dataUrl = request.getParameter("data");
		String called_number = request.getParameter("called_number");

		if (event.equals("NewCall")) {
			// Update the history table's row showing that the system is trying
			// to connect to the applicant.
			// applicant_call_status
			if (kookooDao.getApplicantCallStatus(tmiId) == 2) {
				collectDMF(resp);
			} else {
				kookooDao.updateHistoryEvaluatorResponse(tmiId, sid,
						CallStatusCodes.CALLING_APPLICANT, status, 0);
				// Insert a row in audit that the evaluator answered the call
				// and
				// applicant is being called.
				kookooDao.insertEvaluatorResponseAuditRow(tmiId,
						CallStatusCodes.CALLING_APPLICANT, sid, event, status);
				resp.addPlayText("Welcome to Interview Pandit, please wait while we transfer your call to the applicant.");
				Map<String, Object> row = kookooDao.getApplicantDetails(tmiId);
				String applicantNumber = row.get("applicant_phone").toString();
				dialApplicantNumber(applicantNumber, resp,
						Dial.MusicOnHold.DEFAULT);
			}
		} else if (event.equals("Dial") || event.equals("Disconnect")) {
			if (event.equals("Disconnect")) {
				String total_call_duration = request
						.getParameter("total_call_duration") == null ? "0"
						: request.getParameter("total_call_duration");
				int duration = Integer.parseInt(total_call_duration);
				// Update the history total time in the row.
				kookooDao.updateHistoryTotalTime(tmiId, sid, duration,
						called_number);
			}
			if (status.equals("not_answered")) {
				Map<String, Object> row = kookooDao.getApplicantDetails(tmiId);
				String applicantNumber = row.get("applicant_phone").toString();
				String count = row.get("applicant_nr_count").toString();
				int applicant_na_count = Integer.parseInt(count);
				if (applicant_na_count < 2) {
					// String message = request.getParameter("message");
					// updating the history table's row that we are retrying the
					// applicant
					// inserting a new row in audit that the applicant not
					// answered the call
					kookooDao.updateHistoryApplicantResponse(tmiId,
							CallStatusCodes.CALLING_APPLICANT, status,
							applicant_na_count + 1, sid, called_number);
					kookooDao.insertApplicantResponseAuditRow(tmiId,
							CallStatusCodes.RETRYING_APPLICANT, event, status,
							message, callDuration, sid, dataUrl);
					resp.addPlayText("Thank you for your patience. Please hold while we are still trying to connect the call");
					dialApplicantNumber(applicantNumber, resp,
							Dial.MusicOnHold.RING);
					request.getSession().setAttribute("next_goto",
							"Dial1_Status");
				} else {
					// updating the history table's row that the applicant did
					// not respond to the call.
					// inserting a new row in audit that the applicant is not
					// available.
					// String message = request.getParameter("message");
					kookooDao.updateHistoryApplicantResponse(tmiId,
							CallStatusCodes.APPLICANT_NA, status,
							applicant_na_count + 1, sid, called_number);
					kookooDao.insertApplicantResponseAuditRow(tmiId,
							CallStatusCodes.APPLICANT_NA, event, status,
							message, callDuration, sid, dataUrl);
					resp.addPlayText("Sorry, the applicant seems to be unavailable at this time. Please try again later. Thank you for being online");
					resp.addHangup();
				}
			} else if (status.equals("answered")) {
				// update the history table's row that call is answered by
				// applicant.
				// insert new row that the system is in the process of taking
				// confirmation from evaluator.
				kookooDao.updateHistoryApplicantResponse(tmiId,
						CallStatusCodes.CALLING_APPLICANT, status, 0, sid,
						called_number);
				kookooDao.insertApplicantResponseAuditRow(tmiId,
						CallStatusCodes.COLLECTING_DTMF, event, status,
						message, callDuration, sid, dataUrl);
				collectDMF(resp);
			}
		} else if (event.equals("GotDTMF")) {
			String dtmfData = request.getParameter("data") == null ? ""
					: request.getParameter("data");
			if (dtmfData.equals("")) {
				int noInputCount = 0;
				try {
					noInputCount = kookooDao.getNoDMFCount(tmiId);
					if (noInputCount < 3) {
						// Updating the history row with no input count, when no
						// input is received from evaluator.
						// Insert a new row in the audit with description,
						// event, sid, status.
						kookooDao
								.updateNoDMFCount(tmiId, noInputCount + 1, sid);
						kookooDao.insertEvaluatorResponseAuditRow(tmiId,
								CallStatusCodes.NO_DMF_RECEIVED, sid, event,
								status);
						resp.addPlayText("Sorry, you have not entered any input. Please try again");
						collectDMF(resp);
					} else {
						// Updating the history row with no input.
						// Insert a new row in the audit with description,
						// event, sid, status and disconnect the call.
						/*
						 * kookooDao.updateHistoryEvaluatorDMF(tmiId, sid,
						 * CallStatusCodes.NO_DMF_RECEIVED);
						 */
						kookooDao.insertEvaluatorResponseAuditRow(tmiId,
								CallStatusCodes.NO_DMF_RECEIVED, sid, event,
								status);
						resp.addPlayText("You have exceeded your number of attempts. Disconnecting the call. Have a good day");
						resp.addHangup();
					}
				} catch (Exception e) {
					e.printStackTrace();
				}
			} else if (dtmfData.equals("0")) {
				// Updating the history row with call status as success, as the
				// evaluator confirmed.
				// Insert a new row in the audit with description, event, sid,
				// status and disconnect the call.
				kookooDao.updateHistoryEvaluatorDMF(tmiId, sid,
						CallStatusCodes.SUCCESS);
				kookooDao.insertEvaluatorResponseAuditRow(tmiId,
						CallStatusCodes.SUCCESS, sid, event, status);
				externalUserDao.updateInterviewStatusOnSuccess(tmiId);
				resp.addPlayText("Thank you for using our services, Have a good day");
				resp.addHangup();
			} else if (dtmfData.equals("1")) {
				// Updating the history row with call status as reconnecting, as
				// the evaluator requested.
				// Insert a new row in the audit with description, event, sid,
				// status and re-connect the call.
				kookooDao.updateHistoryEvaluatorDMF(tmiId, sid,
						CallStatusCodes.CALLING_APPLICANT);
				kookooDao.insertEvaluatorResponseAuditRow(tmiId,
						CallStatusCodes.RECONNECTING, sid, event, status);
				resp.addPlayText("Please wait while we are re-connecting the call.");
				Map<String, Object> row = kookooDao.getApplicantDetails(tmiId);
				String applicantNumber = row.get("applicant_phone").toString();
				dialApplicantNumber(applicantNumber, resp,
						Dial.MusicOnHold.RING);
			} else {
				int invalidInputCount = 0;
				try {
					invalidInputCount = kookooDao.getWrongDMFCount(tmiId);

					invalidInputCount = kookooDao.getWrongDMFCount(tmiId);
					if (invalidInputCount < 3) {
						// Updating the history row with invalid input count.
						// Insert a new row in the audit with description,
						// event, sid, status as invalid dmf.
						kookooDao.updateWrongDMFCount(tmiId,
								invalidInputCount + 1, sid);
						kookooDao.insertEvaluatorResponseAuditRow(tmiId,
								CallStatusCodes.WRONG_DMF_RECEIVED, sid, event,
								status);
						resp.addPlayText("Sorry, that is not a valid entry. Please try again");
						collectDMF(resp);
					} else {
						// Updating the history row with invalid input count.
						// Insert a new row in the audit with description,
						// event, sid, status as invalid dmf and disconnect.
						kookooDao.updateHistoryEvaluatorDMF(tmiId, sid,
								CallStatusCodes.CALLING_APPLICANT);
						kookooDao.insertEvaluatorResponseAuditRow(tmiId,
								CallStatusCodes.WRONG_DMF_RECEIVED, sid, event,
								status);
						resp.addPlayText("Invalid input entered 3 times. Disconnecting the call. Have a good day");
						resp.addHangup();
					}
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		} else if (event.equals("Hangup")) {
			String total_call_duration = request
					.getParameter("total_call_duration") == null ? "0"
					: request.getParameter("total_call_duration");
			int duration = Integer.parseInt(total_call_duration);
			// Update the history total time in the row.
			// Insert a new row in the audit table as the call is hang up.
			kookooDao.updateHistoryTotalTime(tmiId, sid, duration,
					called_number);
			kookooDao.insertApplicantResponseAuditRow(tmiId,
					CallStatusCodes.HANG_UP, event, status, message,
					callDuration, sid, dataUrl);
		}
		return resp.getXML();
	}

	/**
	 * This Method is used to dial Applicant Number
	 * @param applicantNumber
	 * @param resp
	 * @param musicOnHold
	 */
	public void dialApplicantNumber(String applicantNumber, Response resp,
			MusicOnHold musicOnHold) {
		Dial dial = new Dial(true);
		dial.setNumber(applicantNumber);
		dial.setMusicOnHold(musicOnHold);
		dial.setRecord(true);
		dial.setLimitTime(1000);
		dial.setTimeout(30);
		resp.addDial(dial);
	}

	/**
	 * This Method is used to  collect DTMF
	 * @param resp
	 */
	public void collectDMF(Response resp) {
		CollectDtmf cd = new CollectDtmf();
		cd.setMaxDigits(4);
		cd.addPlayText("Please press 0 to confirm that conference is finished, or press 1 to re-connect.");
		resp.addCollectDtmf(cd);
	}

	/**
	 * This Method is used to build url and  send Http get request 
	 * @param evaluatorPhone
	 * @param applicantPhone
	 * @param tmiId
	 */
	public void buildUrlAndHttpGet(String evaluatorPhone,
			String applicantPhone, String tmiId) {

		int count = kookooDao.getInterviewHistoryCount(tmiId);
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
			sendGet(url);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	public void test(){
		
		
	}
	public static void main (String args[]){
		
		KookooController ob=new KookooController();
		
		String apiKey = "KK2eeb86eb985c9ffb94db6814a32f4cbe";
		String callBackUrl = "http://interviewpandit.com/" + "TMIKookooCallBack?tmiId=" + "TMI1452256358770";
		String demourl = "http://interviewpandit.com/" + "TMIKookoo?tmiId=" + "TMI1452256358770";
	
			String url = "http://www.kookoo.in/outbound/outbound.php?phone_no="
					+ "9030189710" + "&api_key=" + apiKey + "&url=" + demourl
					+ "&callback_url=" + callBackUrl + "&outbound_version=2";
			try{
				System.out.println("url"+url);
			URL obj = new URL(url);
			HttpURLConnection con = (HttpURLConnection) obj.openConnection();
			
			System.out.println("con"+con);
			con.setRequestMethod("GET");
			int responseCode = con.getResponseCode();
			System.out.println(responseCode);
			BufferedReader in = new BufferedReader(new InputStreamReader(
					con.getInputStream()));
			String inputLine;
			StringBuffer response = new StringBuffer();
			while ((inputLine = in.readLine()) != null) {
				response.append(inputLine);
			}
			System.out.println("response"+response);
			in.close();
		
			}catch(Exception e){
				e.printStackTrace();
			}
	}
}