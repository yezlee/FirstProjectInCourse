package service;

import java.util.List;
import java.util.Map;

import dao.UserDao;
import util.ScanUtil;
import util.View;

public class UserSalary {

	// 싱글톤 패턴 만들기
	private UserSalary() {
	}

	private static UserSalary sal;

	public static UserSalary getSal() {
		if (sal == null) {
			sal = new UserSalary();
		}
		return sal;
	}

	// 3.급여관리 클래스

	private UserDao userdao = UserDao.getInstance();

	// 급여 조회 (main)
	public int salList() {
	System.out.println();
		System.out.println("1.급여조회    2.검색    3.계산    0.메인페이지");
		System.out.print("☞ ");
		int list = ScanUtil.nextInt();

		switch (list) {
		case 1:
			return View.SAL_DETAIL;

		case 2:
			return View.SAL_SEARCH;
		case 3:
			return View.SAL_CALCULATION;
		case 0:
			return View.HOME;
		}

		return View.SALARYPG;
	}

	// 급여 상세조회
	public int saldetail() {
		List<Map<String, Object>> sallist = userdao.selectSal(); // ()안에 값만 지정
		System.out.println("\t급여 목록");
		System.out.println();
		System.out.println("날짜\t총급여\t지급일");
		System.out.println("__________________________");
		for (int i = 0; i < sallist.size(); i++) {
			System.out.println(sallist.get(i).get("DT") + "\t"
					+ sallist.get(i).get("TOTAL_SAL") + "\t"
					+ sallist.get(i).get("PAY_DAY") + "\t");
		}
		System.out.println();
		System.out.println("============ 상세 보기 =================");
		System.out.println();

		List<Map<String, Object>> detailSal = userdao.detailSal();

		System.out.println();
		System.out.println("┌──────────────────────────────────────────────────────────────┐");
		System.out.println("\t급여 명세서");
		for (int i = 0; i < detailSal.size(); i++) {
			System.out.println("\n\t ▶날짜 : " + detailSal.get(i).get("DT")
					+ " \n\t ▶기본급 : " + detailSal.get(i).get("SAL")
					+ "\n\t ▶추가시간 : "
					+ detailSal.get(i).get("NVL(OVER_TIME,0)")
					+ "\n\t ▶추가수당 : "
					+ detailSal.get(i).get("NVL(OVER_TIME,0)")
					+ "\n\t ▶추가수당 : " + detailSal.get(i).get("TOTAL_SAL+(천원)")
					+ "\n\t ▶지급일 : " + detailSal.get(i).get("PAY_DAY") + "\t");
		System.out.println("└────────────────────────────────────────────────────────────────┘");

		}
		return View.SALARYPG;
	}

	// 급여검색

	public int salSerach() {
		List<Map<String, Object>> searchSal = userdao.searchSal();

		System.out.println();
		System.out.println("\t급여검색");
		System.out.println(); 
		System.out.println("┌───────────────────────────────────────────────┐");
		System.out.println();
		for (int i = 0; i < searchSal.size(); i++) {
			System.out.println("\n\t ▶날짜 : " + searchSal.get(i).get("DT")
					+ "\n\t▶기본급:" + searchSal.get(i).get("SAL")
					+ "\n\t▶추가시간 : "
					+ searchSal.get(i).get("NVL(OVER_TIME,0)")
					+ "\n\t▶추가수당 : " + searchSal.get(i).get("NVL(OVER_SAL,0)+(천원)")
					+ "\n\t▶총 급여 : " + searchSal.get(i).get("TOTAL_SAL+(천원)")
					+ "\n\t▶지급일 : " + searchSal.get(i).get("PAY_DAY") + "\t");
		}
		System.out.println();
		System.out.println("└────────────────────────────────────────────────┘");
		return View.SALARYPG;
	}

	// 급여 계산

	public int salaryCal() {
		System.out.println("1.전체급여계산\t2.추가수당계산\t0.돌아가기");
		System.out.print("☞ ");
		int input = ScanUtil.nextInt();

		switch (input) {
		case 1:
			return View.SAL_TOTALCAL;

		case 2:
			return View.SAL_OVERCAL;
		case 0:
			return View.SALARYPG;

		}
		System.out.println("잘못 누르셨습니다. 초기화면으로 돌아갑니다.");
		return View.HOME;
	}

	// 1. 전체급여 계산
	public int totalCal() {
		List<Map<String, Object>> totalSal = userdao.totalSal();
		System.out.println();
		
		System.out.println("┌─────────────────────────────────────────────────────┐");
		System.out.println("\t이번달 급여계산(현재기준)");
		System.out.println();
		
		for (int i = 0; i < totalSal.size(); i++) {
			System.out.println("\n\t ▶날짜 : " + totalSal.get(i).get("DT")
					+ "\n\t ▶기본급 : " + totalSal.get(i).get("SAL+(천원)") + "\n\t ▶초과수당 : "
					+ totalSal.get(i).get("NVL(OVER_SAL,0)+(천원)") + "\n\t ▶총급여 : "
					+ totalSal.get(i).get("TOTAL_SAL+(천원)") + "\n\t ▶지급일 : "
					+ totalSal.get(i).get("PAY_DAY"));
		}
		System.out.println();
		System.out.println("└─────────────────────────────────────────────────────┘");
		return View.SAL_CALCULATION;
	}

	// 2.초과수당계산
	public int overCal() {
		List<Map<String, Object>> overSal = userdao.overSal();
		System.out.println();
		System.out.println("┌─────────────────────────────────────────────────────┐");
		System.out.println("\t초과수당계산");
		System.out.println("※관리자의 승인결과에 따라 다를 수 있습니다.");
		System.out.println();
			for (int i = 0; i < overSal.size(); i++) {
			System.out.println("\n\t ▶날짜 : " + overSal.get(i).get("DT")
					+ "\n\t ▶초과시간 : " + overSal.get(i).get("NVL(OVER_TIME,0)")
					+ "\n\t ▶초과수당 : " + overSal.get(i).get("NVL(OVER_SAL,0)+(천원)"));

		}
			System.out.println();
			System.out.println("└─────────────────────────────────────────────────────┘");
			return View.SAL_CALCULATION;

	}

}