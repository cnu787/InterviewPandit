package com.testmyinterview.portal.kookoo;

public class CallStatusCodes {

	public static final int CALLING_EVALUATOR = 1;
	public static final int CALLING_APPLICANT = 2;
	public static final int RETRYING_EVALUATOR = 3;
	public static final int RETRYING_APPLICANT = 4;
	public static final int EVALUATOR_NA = 5;
	public static final int APPLICANT_NA = 6;
	public static final int COLLECTING_DTMF = 7;
	public static final int RECONNECTING = 8;
	public static final int SUCCESS = 9;
	public static final int ERROR = 10;
	public static final int NO_DMF_RECEIVED = 11;
	public static final int WRONG_DMF_RECEIVED = 12;
	public static final int HANG_UP = 13;

	public static final String INITIATING_CALL = "Initiated call to evaluator.";
	public static final String EVALUATOR_ANSWERED = "Evaluator answered the call. Now Dialing applicant number.";
	public static final String EVALUATOR_NOT_ANSWERED = "Evaluator not answered, retrying to call evaluator.";
	public static final String EVALUATOR_NOT_AVAILABLE = "Evaluator did not respond to the call.";
	public static final String APPLICANT_NOT_ANSWERED = "Applicant not answered, retrying to call applicant.";
	public static final String APPLICANT_NOT_AVAILABLE = "Applicant did not respond to the call.";
	public static final String COLLECTING_DTMF_RESPONSE = "The conference took place, call disconnected at applicant end, taking confirmation from evaluator.";
	public static final String NO_DMF_RESPONSE = "Not received DMF from evaluator ";
	public static final String SUCCESS_MESSAGE = "The Conference is successfully finished.";
	public static final String RECONNECTING_MESSAGE = "Re-establishing the connection on taking confirmation from evalutor.";
	public static final String WRONG_DMF_RESPONSE = "Received invalid DMF from evaluator ";
	public static final String CALL_HANG_UP = "The call has been hanged up at the evaluator end.";
	public static final String CALL_DISCONNECTING = "The call is being disconnected.";
}
