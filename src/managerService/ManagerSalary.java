package managerService;


import java.util.HashMap;
import java.util.List;
import java.util.Map;

import dao.ManagerDao;
import util.ScanUtil;
import util.View;

public class ManagerSalary {

	private ManagerSalary() {
	}

	private static ManagerSalary mgrSal;

	public static ManagerSalary getSal() {
		if (mgrSal == null) {
			mgrSal = new ManagerSalary();
		}
		return mgrSal;

	}

	private ManagerDao managerDao = ManagerDao.getInstance();

	
	public int mgrSal() {

		System.out.println("1.급여검색   2.초과근무 확인  3.초과수당   4.총급여 합계   0.뒤로가기");
		System.out.print("☞ ");
		int list = ScanUtil.nextInt();

		switch (list) {
	
		case 1:	//검색하기
			return View.MGR_SALSEARCH;    
		
		case 2:	//초과시간 확인 -> 업데이트
			return View.MGR_OVERTIME;     

		case 3: //초과급여 확인 -> 업데이트
			return View.MGR_OVERSAL;     

		case 4:	//전체급여 확인 -> 업데이트
			return View.MGR_TOTALSAL;   

		case 0:
			return View.MANAGERHOME;
		}

		return View.MANAGERHOME;

	}

	public int allSal() {
		List<Map<String, Object>> allList = managerDao.selectSal();

		System.out.println();
		System.out.println("┌─────────────────────────────────────────────────────┐");
		System.out.println("\t전체 사원의 급여목록");
		System.out.println();
		System.out.println("\t날짜 \t사원번호\t총급여 \t지급일");

		for (int i = 0; i < allList.size(); i++) {
			System.out.println("\t"+allList.get(i).get("DT") + "\t"
					+ allList.get(i).get("EMPNO") + "\t"
					+ allList.get(i).get("TOTAL_SAL") + "\t"
					+ allList.get(i).get("PAY_DAY") + "\t");
		}
	
		System.out.println("└─────────────────────────────────────────────────────┘");
		return View.MGR_INFO;

	}

	public int deptSal() {
		List<Map<String, Object>> deptList = managerDao.deptSal();

		
		System.out.println();
		System.out.println("┌─────────────────────────────────────────────────────┐");
		System.out.println("\t부서별 급여목록");
			System.out.println("\t날짜  \t부서이름 \t총급여 \t지급일");

		for (int i = 0; i < deptList.size(); i++) {
			System.out.println("\t"+deptList.get(i).get("DT") + "\t"
					+ deptList.get(i).get("DNAME") + "\t"
					+ deptList.get(i).get("TOTAL_SAL") +"\t"
					+ deptList.get(i).get("PAY_DAY") + "\t");
		}
		System.out.println("└─────────────────────────────────────────────────────┘");
		
		return View.MGR_DEPTLIST;

	}

	public int salSerach() {
		List<Map<String, Object>> searchSal = managerDao.searchSal();

		System.out.println();
		System.out.println("\t급여검색");
		System.out.println("┌─────────────────────────────────────────────────────┐");
			for (int i = 0; i < searchSal.size(); i++) {
			System.out.println("\t▶날짜 : " + searchSal.get(i).get("DT")
					+ " \n\t▶기본급:" + searchSal.get(i).get("SAL")
					+ "\n\t ▶추가시간 : " + searchSal.get(i).get("NVL(OVER_TIME,0)")
					+ "\n\t ▶추가수당 : " + searchSal.get(i).get("NVL(OVER_SAL,0)")
					+ "\n\t▶총 급여 : " + searchSal.get(i).get("TOTAL_SAL")
					+ "\n\t▶지급일 : " + searchSal.get(i).get("PAY_DAY") + "\t");
		}
			System.out.println("└─────────────────────────────────────────────────────┘");
			
		return View.MGR_SALPG;
	}

	// 초과근무시간 확인 -> 업데이트 해주기
	public int overTime() {
		
		List<Map<String, Object>> overtime = managerDao.overtime();

		System.out.println("\t초과근무시간 확인");

		for (int i = 0; i < overtime.size(); i++) {
			System.out.println("해당 월의  입력하신 사원의  초과시간 : " + overtime.get(i).get("OVER_TIME"));
		}

		System.out.println("사원들의 초과근무 시간을 업데이트 하시겠습니까? ");
		System.out.println("\t1.예 \t2.아니오");
		System.out.print("☞ ");
		int input = ScanUtil.nextInt();

		switch (input) {
		case 1:
			return View.MGR_OVERTIMEUP;  //public static int updateTime() 로간다. 밑에있다. 
		case 2:
			System.out.println("취소되었습니다. 관리자의 초기화면으로 돌아갑니다.");
			return View.MGR_MAIN;
		}

		return View.MGR_SALPG;

	}

	
	
	public int updateTime() {

		
		int cnt = managerDao.updateOvertime();
		
		if(cnt>0){
			System.out.println("초과시간이 업데이트 되었습니다");  	// overwork 테이블에서 계산된 데이터가  salary 테이블의 OVER_TIME 에 값으로 넘어간것.
		}
		
		return  View.MGR_SALPG;
		
	}

	
	
	
	public int updateOverSal() {

		System.out.println("사원의  초과근무 수당을 계산하여 업데이트 하겠습니다.");
		System.out.print("해당월을  입력하세요 (ex.09)");
		System.out.print("☞ ");
		String month = ScanUtil.nextLine();
		System.out.print("사원번호를 입력하세요");
		System.out.print("☞ ");
		int empno = ScanUtil.nextInt();
		
		Map<String, Object> param = new HashMap<>();
		param.put("DT", month);
		param.put("EMPNO", empno);
      
		int result = managerDao.updateOverSal(param);

		System.out.println("\t" + result+ " 개의 사원정보가 업데이트 되었습니다.");

		return View.MGR_SALPG;

	}

	public int totalSal() {
		System.out.println("기본급과 초과수당을 합해서 전체급여를 업데이트하겠습니다."); 
		System.out.print("해당년/월을  입력하세요 (ex.202009) ☞ ");
		int dt = ScanUtil.nextInt();
		System.out.print("사원번호를 입력하세요 ☞  ");
		int empno = ScanUtil.nextInt();

		Map<String, Object> p = new HashMap<>();
		p.put("DT", dt);
		p.put("EMPNO", empno);

		int result = managerDao.updateTotalSal(p);
		System.out.println("\t"+empno + " 번의 총 급여 (기본급 + 추가근무수당 ) 가  " +  result +"개 업데이트 되었습니다.");

		return View.MGR_SALPG;
	}

}