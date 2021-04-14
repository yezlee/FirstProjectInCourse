package service;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import controller.Controller;
import dao.UserDao;
import util.ScanUtil;
import util.View;

// 2.근태관리 클래스
public class UserWork {
	public   void main(String[] args){
		
	}
	
	private UserWork() {}
	private static UserWork instance;
	
	public static UserWork getInstance(){
		if(instance == null){
			instance = new UserWork();
		}
		return instance;
	}
	
	private UserDao userdao = UserDao.getInstance();	
	
	  Calendar cal = Calendar.getInstance();
	  SimpleDateFormat sdf1 = new SimpleDateFormat("MM/dd");
	  Date date = new Date(new java.util.Date().getTime());
	  String dtdate = sdf1.format(date);
	
	public int start() {
		System.out.println();
		System.out.println("1.출퇴근    2.초과근무    3.뒤로가기    0.종료");
		System.out.print("입력> ");
		int input = ScanUtil.nextInt();
		
		switch(input) { 
		case 1:         //출퇴근 선택
			return View.PUNCTUALITY;     //출퇴근 메소드 가져옴
		case 2:   		// 초과근무 선택
			return View.OVERWORK;		 //초과근무 메소드 가져옴
		case 3: 
			if (((BigDecimal) Controller.loginUser.get("EMPNO")).intValue() == 7000) {
				return View.MANAGERHOME; 	// managerhome 으로 연결된다. 
			} else {
				return View.HOME; // 아래 홈으로 들어간다.
			}
		case 0:
			System.out.println("프로그램을 종료합니다.");
		    System.exit(0);
		}
		return View.HOME; 
	}
	
	//1.출퇴근 선택시,
	// 출퇴근 시작 메소드
	public int work() {
		System.out.println();
		System.out.println("1.출근등록    2.퇴근등록    3.출퇴근 전체조회   4.출퇴근 일별조회    5.뒤로가기");
		System.out.print("입력> ");
		int input = ScanUtil.nextInt();
		if (input == 1) {
			return View.WORK_START;
		}else if (input == 2) {
			return View.WORK_END;
		}else if (input == 3) {
			return View.WORK_ALL;
		}else if (input == 4) {
			return View.WORK_DAILY;
		}else if (input == 5) {
			return View.USERWORK_START;
		}
		return View.USERWORK_START;
	}

	// 출근등록 메소드
	public int workstart() {
		Map<String, Object> param = new HashMap<>();
		param.put("EMPNO", Controller.loginUser.get("EMPNO"));
		param.put("DT",dtdate);
		int result = userdao.insertStartW(param);

		if (result > 0) {
			System.out.println("\t출근 완료!");
			System.out.println();

		}

		return  View.PUNCTUALITY;
	}
	
	// 퇴근등록 메소드
	public int workend() {
		Map<String, Object> param = new HashMap<>();
		param.put("EMPNO", Controller.loginUser.get("EMPNO"));
	    int result = userdao.insertEndW(param);
	    
	    if (result > 0) {
			System.out.println("\t퇴근 완료!");
			System.out.println();
		}
		return  View.PUNCTUALITY;
	}

	// 출퇴근 전체조회 메소드 
	public int workall() {		
		List<Map<String, Object>> alllist = UserDao.selectAll();
		System.out.println();
		System.out.println("사원번호\t출근시간\t\t퇴근시간");
		System.out.println("____________________________________");

		for(int i = 0; i < alllist.size(); i++) {
			System.out.println(alllist.get(i).get("EMPNO")+"\t"
					+alllist.get(i).get("S_TIME").toString().substring(5,16)+"\t"
					+alllist.get(i).get("E_TIME").toString().substring(5,16));
		}
		System.out.println("____________________________________");
		System.out.println();

		return  View.PUNCTUALITY;
	}
	
	// 출퇴근  일별조회 메소드 
	public int workdaily() {
		List<Map<String, Object>> dailylist = UserDao.selectDaily();
		System.out.println("조회날짜\t사원번호\t출근시간\t\t퇴근시간");
		System.out.println("__________________________________________");
		for(int i = 0; i < dailylist.size(); i++) {
			System.out.println(dailylist.get(i).get("DT")+"\t"
					+dailylist.get(i).get("EMPNO")+"\t"
					+dailylist.get(i).get("S_TIME").toString().substring(5,16)+"\t"
					+dailylist.get(i).get("E_TIME").toString().substring(5,16));
		}
		System.out.println("__________________________________________");
		System.out.println();
		return  View.PUNCTUALITY;
	}
	
	//2.초과근무 선택시,
	// 초과시간 등록 메소드
	public int overwork() {
		System.out.println();
		System.out.println("1.초과근무 시작    2.초과근무 끝    3.초과근무 전체조회    4.초과근무 일별조회    5.뒤로가기");
		System.out.print("입력> ");
		int input = ScanUtil.nextInt();
		if (input == 1) {
			return View.OVERWORK_START;
		}else if (input == 2) {
			return View.OVERWORK_STOP;
		}else if (input == 3) {
			return View.OVERWORK_ALL;
		}else if (input == 4) {
			return View.OVERWORK_DAILY;
		}else if (input == 5) {
			return work();
		}
		return View.USERWORK_START;
	}
	
	// 초과근무 시작 메소드
	public int overworkstart() {
		Map<String, Object> param = new HashMap<>();
		param.put("EMPNO", Controller.loginUser.get("EMPNO"));
		param.put("W_DATE",dtdate);      // 월/일만 저장
		
	    int result = userdao.insertStartOW(param);
	    if(result > 0) {
	    System.out.println("초과근무를 시작합니다.");
		System.out.println();
	    }
	    
		return View.OVERWORK;
	}      
	
	//초과근무 끝 메소드
	public   int overworkstop() {
		Map<String, Object> param = new HashMap<>();
		param.put("EMPNO", Controller.loginUser.get("EMPNO"));

		int result = userdao.insertStopOW(param);
		int overtime = userdao.selectOW(param);
	    System.out.println(result+overtime+"초과근무를 정상적으로 마칩니다.");
		System.out.println();

		return View.OVERWORK;
	}
	
	//초과근무 전체조회 메소드
	public  int overworkall() {
		List<Map<String, Object>> list = UserDao.selectOverAll();
		System.out.println();
		System.out.println("사원번호\t일자\t시작시간\t종료시간\t총시간");
		System.out.println("_______________________________________________________");
		for(int i = 0; i < list.size(); i++) {
			System.out.println(list.get(i).get("EMPNO")+"\t"
					+list.get(i).get("W_DATE")+"\t"
					+list.get(i).get("OVER_START").toString().substring(10,16)+"\t"
					+list.get(i).get("OVER_END").toString().substring(10,16)+"\t"
					+list.get(i).get("OVER_TIME")+" 초과근무");
		}
		System.out.println("______________________________________________________");
		System.out.println();

		return View.OVERWORK;
	}
	
	//초과근무 일별조회 메소드
	public int overworkdaily() {
		List<Map<String, Object>> dailylist = UserDao.selectOverDaily();
		System.out.println();
		System.out.println("조회날짜\t사원번호\t시작시간\t종료시간\t총시간");
		System.out.println("_______________________________________________________");
			for(int i = 0; i < dailylist.size(); i++) {
			System.out.println(dailylist.get(i).get("W_DATE")+"\t"
					+dailylist.get(i).get("EMPNO")+"\t"
					+dailylist.get(i).get("OVER_START").toString().substring(10,16)+"\t"
					+dailylist.get(i).get("OVER_END").toString().substring(10,16)+"\t"
					+dailylist.get(i).get("OVER_TIME")+" 초과근무");
		}
		System.out.println("______________________________________________________");
		System.out.println();

		return View.OVERWORK;
	}
}