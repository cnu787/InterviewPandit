package com.testmyinterview.portal;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.testmyinterview.portal.dao.ExternalUserDao;
import com.testmyinterview.portal.util.TmiUtil;

@Controller
public class MockTestController {

	@Value("${testUrl}")
	private String testUrl;
	@Autowired
	private ExternalUserDao externalUserDao;
	@Autowired
	private TmiUtil tmiUtil;

	/**
	 * This method is to view mock Test
	 * 
	 * @param request
	 * @param model
	 * @return
	 */
	@Secured({ "ROLE_EXTERNAL" })
	@RequestMapping(value = "/mockTest.do")
	public String mockTest(HttpServletRequest request, Model model) {		
		System.out.println("testUrl"+testUrl);
		System.out.println("Before ***********");
		model.addAttribute("emailId", tmiUtil.md5Encode(tmiUtil.getEmailId()));
		System.out.println("emailId"+tmiUtil.getEmailId());
		
		model.addAttribute("testUrl", testUrl);
		return "mockTestView";
	}

	/**
	 * This method is to add mockTestResult based on input like
	 * resultData,exam_results
	 * 
	 * @param request
	 * @param model
	 * @return
	 */
	@Secured({ "ROLE_EXTERNAL" })
	@RequestMapping(value = "/mockTestResult.do")
	public String mockTestResult(HttpServletRequest request, Model model) {
		List<Map<String, Object>> testResultList = null;
		List<Map<String, Object>> examresultsList = null;
		try {
			testResultList = TmiUtil.convertJsonToListOfMap(request
					.getParameter("resultData").toString());
			examresultsList = TmiUtil.convertJsonToListOfMap(testResultList
					.get(0).get("exam_results").toString());
			externalUserDao.addMockTestResult(tmiUtil.getUserId(),testResultList.get(0).get("testRollNo").toString(),request
					.getParameter("resultData").toString());

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		model.addAttribute("testResultList", testResultList);
		model.addAttribute("examresultsList", examresultsList);
		return "redirect:/mockTestResultById.do?testRollNo="+testResultList.get(0).get("testRollNo").toString()+"&type=1";
	}
	/**
	 * This method is to show mockTestResult list based on userid
	 * 
	 * @param request
	 * @param model
	 * @return
	 */
	@Secured({ "ROLE_EXTERNAL" })
	@RequestMapping(value = "/mockTestResultList.do")
	public String mockTestResultList(HttpServletRequest request, Model model) {		
		model.addAttribute("examresultsList", externalUserDao.getMockTestResultList(tmiUtil.getUserId()));		
		return "mockTestResultsList";
	}
	/**
	 * This method is to show mockTestResult
	 * 
	 * @param request
	 * @param model
	 * @return
	 */
	@Secured({ "ROLE_EXTERNAL" })
	@RequestMapping(value = "/mockTestResultById")
	public String mockTestResultById(HttpServletRequest request, Model model) {		
		String testRollNo=request.getParameter("testRollNo");
		List<Map<String, Object>> testResultList = null;
		List<Map<String, Object>> examresultsList = null;
		try {
			testResultList = TmiUtil.convertJsonToListOfMap(externalUserDao.getMockTestResultById(testRollNo));			
			examresultsList = TmiUtil.convertJsonToListOfMap(testResultList
					.get(0).get("exam_results").toString());

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		model.addAttribute("myMockType", request.getParameter("type"));
		model.addAttribute("testResultList", testResultList);
		model.addAttribute("examresultsList", examresultsList);
		return "mockTestResultModel";
	}
	
}