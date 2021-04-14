package managerService;

import dao.ManagerDao;
import util.ScanUtil;
import util.View;

import java.util.List;
import java.util.Map;

public class ManagerService {
	private ManagerService() {
	}

	private static ManagerService instance;

	public static ManagerService getInstance() {
		if (instance == null) {
			instance = new ManagerService();

		}
		return instance;
	}

	private ManagerDao managerDao = ManagerDao.getInstance();

	// 관리자 창(main)

	public int mgrMain() {
		System.out.println();
		System.out.println(" 1.관리자정보     2.전체직원 조회     3.부서별 직원 조회     4.직원정보 수정     5.승인관리     0.돌아가기 ");
		System.out.print("☞ ");

		int input = ScanUtil.nextInt();
		switch (input) {
		case 1:
			return View.MGR_PRIVACY;
		case 2:
			return  View.MGR_INFO;
		case 3:
			return View.MGR_DEPTLIST;
		case 4:
			return View.MGR_UPDATE;
		case 5:
			return View.MGR_APPROVE;
		case 6:
			return View.MGR_SALPG;

		case 0:
			return View.MANAGERHOME;

		}

		return View.MGR_MAIN;
	}

	// 1.관리자 개인정보

	public int privacyForManager() {
		System.out.println();
		System.out.println(" 1.관리자정보 조회     2.관리자정보 수정     3.돌아가기  ");
		System.out.print("☞ ");
		int input = ScanUtil.nextInt();

		switch (input) {
		case 1:
			return View.MGR_MYINFO;
		case 2:
			return View.MGR_MYUPDATE;
		case 3:
			return View.MGR_MAIN;

		}
		return View.MANAGERHOME;

	}

	// 2.전체직원조회
	public int ManagerView() {
		System.out.println("\n조회하고 싶은 업무를 선택하세요 > ");
		System.out.println("  1.전체 직원 정보조회     2.전체 직원 근태조회     3.전체 직원급여조회     0.돌아가기");
		System.out.print("☞ ");
		int list = ScanUtil.nextInt();

		switch (list) {
		case 1:
			return View.MGR_ALLLIST;

		case 2:
			return View.MGR_PSIGN;

		case 3:
			return View.MGR_ALLSAL;

		case 0:
			return View.MGR_MAIN;
		}
		return View.MGR_MAIN;
	}

	// 2-1전체직원 정보조회

	public int MgrAllList() {
		List<Map<String, Object>> managerview = managerDao
				.selectUserForManager();
		System.out.println("\t ㈜ DD COMPANY \n\t 전체 직원 개인정보");
		System.out.println("--------------------------------------------------------------------------------------");
		for (int i = 0; i < managerview.size(); i++) {
			System.out.println("\n 사원번호 : " + managerview.get(i).get("EMPNO")
					+ "\n 부서번호 : " + managerview.get(i).get("DEPTNO")
					+ "\n 부서 : "+ managerview.get(i).get("DNAME") + "\t"
					+ "\n 사원이름 : " + managerview.get(i).get("ENAME") + "\t"
					+ "\n 직책 : " + managerview.get(i).get("JOB") + "\t"
					+ "\n 월급(기본급): " + managerview.get(i).get("SAL") + "\t"
					+ "\n 입사년도: " + managerview.get(i).get("HIREDATE") + "\t"
					+ "\n e-mail : " + managerview.get(i).get("EMAIL") + "\t"
					+ "\n 근태확인: " + managerview.get(i).get("P_SIGN") + "\t"
					+ "\n 결재확인 : " + managerview.get(i).get("J_SIGN"));
		}
		System.out
				.println("--------------------------------------------------------------------------------------");
		System.out.println();

		return View.MGR_INFO;
	}

	// 관리자가 부서별로 조회하기

	public int ManagerDepartView() {

		List<Map<String, Object>> managerdepartview = managerDao
				.selectDepartForManager();

		System.out.println("\t ㈜ DD COMPANY \n\t 부서 직원 정보");
		System.out.println("사원번호\t사원이름\t부서번호 \t부서이름");
		System.out.println("--------------------------------");
		for (int i = 0; i < managerdepartview.size(); i++) {
			System.out.println(managerdepartview.get(i).get("IEMPNO") + "\t"
					+ managerdepartview.get(i).get("IENAME") + "\t"
					+ managerdepartview.get(i).get("IDEPTNO") + "\t"
					+ managerdepartview.get(i).get("DDNAME") + "\t");
		}
		System.out.println("--------------------------------");

		System.out.println(" 1.부서별 급여조회   2.부서 근태조회   3.부서 전화번호조회   0.메인페이지");
		System.out.print("☞ ");
		int list = ScanUtil.nextInt();

		switch (list) {
		case 1:
			return View.MGR_DEPTSAL; // 부서별급여조회

		case 2:
			return View.MGR_DSIGN; // 부서별근태조회

		case 3:
			return View.MGR_DPHONE; // 부서별전화번호조회

		}
		return View.MANAGERHOME;
	}

	// 관리자가 직원의 정보 수정하기

	public int ManagerUpdate() {
		System.out.println("변경을 원하시는 직원정보에 해당하는 번호를 입력해 주세요");
		System.out.println("1.부서   2.직업   3.급여   0.돌아가기 ");
		System.out.print("☞ ");
		int input = ScanUtil.nextInt();

		switch (input) {

		case 1:
			System.out.println("변경을 원하는 직원의 사번(아이디)을 입력해주세요");
			System.out.print("☞ ");
			String empno = ScanUtil.nextLine();

			System.out
					.println("변경되기 원하는 부서의 이름으로 입력해주세요 < 대표이사, 개발부, 솔루션개발팀, 기술지원팀, 디자인부, 디자인팀> ");
			System.out.print("☞ ");
			String dname = ScanUtil.nextLine();
			int result = managerDao.updateDeptno(empno, dname);
			if (result > 0) {
				System.out.println(empno + "의 부서가 " + dname + "으로 변경되었습니다.");
				break;
			} else {
				System.out.println("다시 입력해주세요 ");
			}
			break;

		case 2:
			System.out.println("변경을 원하는 직원의 사번(아이디)을 입력해주세요");
			System.out.print("☞ ");
			String empno_ = ScanUtil.nextLine();
			
			System.out.println("변경되기 원하는 직업으로 입력해주세요");
			System.out.print("☞ ");
			String job = ScanUtil.nextLine();

			int result_ = managerDao.updateJob(empno_, job);
			if (result_ > 0) {
				System.out.println(empno_ + "의 직업이  " + job + "으로 변경되었습니다.");
				break;
			} else {
				System.out.println("다시 입력해주세요");
			}

			// 셀러리 변경
		case 3: 	// 셀러리 변경
			System.out.println("변경을 원하는 직원의 사번(아이디)을 입력해주세요");
			System.out.print("☞ ");
			Object empno2 = ScanUtil.nextInt();
			
			System.out.println("변경되기 원하는 급여의 값으로 입력해주세요 ");
			System.out.print("☞ ");
			Object sal = ScanUtil.nextInt();

			int result2 = managerDao.updateSal(empno2, sal);
			if (result2 > 0) {
				System.out.println(empno2 + "의 급여가 " + sal + "으로 변경되었습니다.");
				break;
			} else {
				System.out.println("다시 입력해주세요");
			}

		case 4:
			return View.MGR_INFO;

		}
		System.out.println("메인 페이지로 돌아갑니다.");
		return View.MGR_MAIN;

	}

	// 관리자 개인 정보 보기
	public int Managerview() {
		List<Map<String, Object>> managerview = managerDao.selectListForManager();
		System.out.println();
		System.out.println("------------ 관리자 정보 --------------");
				for (int i = 0; i < managerview.size(); i++) {
			System.out.println("\n 사원번호 : " + managerview.get(i).get("EMPNO")
					+ "\n 부서번호 : " + managerview.get(i).get("DEPTNO")
					+ "\n 부서 : "+ managerview.get(i).get("DNAME") + "\t"
					+ "\n 사원이름 : " + managerview.get(i).get("ENAME") + "\t"
					+ "\n 직책 : " + managerview.get(i).get("JOB") + "\t"
					+ "\n 월급(기본급): " + managerview.get(i).get("SAL") + "\t"
					+ "\n 입사년도: " + managerview.get(i).get("HIREDATE") + "\t"
					+ "\n e-mail : " + managerview.get(i).get("EMAIL") + "\t");

			System.out.println("--------------------------------");
			System.out.println();
		}
		return View.MGR_PRIVACY;

	} // information메소드 끝

	// 부서전화번호조회
	public int departPhone() {

		System.out
				.println("---------------------------- 부서 전화번호 ---------------------------");
		System.out.println("전화번호를 조회하고 싶은 부서의 이름을 입력해 주세요");
		System.out.println("\n☎대표이사\n☎개발부\n☎기획팀\n☎개발팀 \n☎디자인부\n☎디자인팀\n");
		System.out
				.println("-------------------------------------------------------------------");
		System.out.print("\n☎: ");
		String input = ScanUtil.nextLine();

		Map<String, Object> result = managerDao.selectDepartPhone(input);
		System.out.print("\t☎ " + input + "  부서 연락처 : ");
		System.out.println(result.get("DPHON"));
		System.out.println();
		return View.MGR_MAIN;

	}

	// 관리자 자기 정보 수정하기
	public int UpdateViewforManager() {
		System.out.println("원하시는 번호를 입력해주세요");
		System.out.println("1.비밀번호변경\t 2.이메일변경 \t 3.돌아가기 ");
		System.out.print("☞ ");
		int input = ScanUtil.nextInt();

		switch (input) {

		case 1:
			System.out.println("변경을 원하는 비밀번호를 입력해주세요 ");
			System.out.print("☞ ");
			String password = ScanUtil.nextLine();

			int result = managerDao.updateInforPassword(password);
			if (result > 0) {
				System.out.println("비밀번호가 성공적으로 변경되었습니다.");
				System.out.println();
				break;
			} else {
				System.out.println("다시 입력해주세요 ");
				System.out.println();
			}
			break;

		case 2:
			System.out.println("변경을 원하는 이메일번호를 입력해주세요");
			System.out.print("☞ ");
			Object email = ScanUtil.nextLine();
			
			int p1 = managerDao.updateInforEmail(email);
			if (p1 > 0) {
				System.out.println("이메일주소가 변경되었습니다.");
				System.out.println();
				break;
			} else {
				System.out.println("다시 입력해주세요 ");
				System.out.println();
			}

		case 3:
			return View.MGR_PRIVACY;
		}
		System.out.println("메인 페이지로 돌아갑니다.");
		return View.MGR_MAIN;
	}

}