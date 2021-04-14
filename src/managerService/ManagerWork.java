package managerService;

import java.util.List;
import java.util.Map;

import dao.ManagerDao;
import util.ScanUtil;
import util.View;

public class ManagerWork {

	private static ManagerWork instance;

	public static ManagerWork getInstance() {
		if (instance == null) {
			instance = new ManagerWork();
		}
		return instance;
	}

	private static ManagerDao managerDao = ManagerDao.getInstance();

	// 전체직원 근태 조회하기
	public int managerPsign() {
		System.out.println();
		List<Map<String, Object>> list = managerDao.selectPsign();

		System.out.println("\t사원번호\t날짜\t  출근시간     퇴근시간\t     승인여부");
		System.out.println("----------------------------------------------------------");

		for (int i = 0; i < list.size(); i++) {
			System.out.println("\t"+list.get(i).get("EMPNO") + "\t" + list.get(i).get("DT") + "\t"
					+ list.get(i).get("S_TIME").toString().substring(10, 16) + " ~"
					+ list.get(i).get("E_TIME").toString().substring(10, 16) + "    " + list.get(i).get("P_SIGN2")
					+ "  " + list.get(i).get("P_SIGN"));
		}
		System.out.println("----------------------------------------------------------");
		System.out.println();

		return View.MGR_INFO;
	}

	// 부서별 직원근태 조회하기
	public int selectdsign() {
		List<Map<String, Object>> list = managerDao.selectDsign();

		System.out.println("부서이름\t\t사원번호\t사원이름\t 출근시간\t\t 퇴근시간\t\t승인여부");
		System.out.println("----------------------------------------------------------");
		for (int i = 0; i < list.size(); i++) {
			System.out.println(list.get(i).get("DNAME") + "\t" + list.get(i).get("EMPNO") + "\t"
					+ list.get(i).get("ENAME") + "\t" + list.get(i).get("S_TIME").toString().substring(5, 16) + " ~ "
					+ list.get(i).get("E_TIME").toString().substring(5, 16) + "\t" + list.get(i).get("P_SIGN") + "\t");
		}
		System.out.println("----------------------------------------------------------");
		System.out.println();
		return View.MGR_DEPTLIST;

	}

	public int ManagerApprove() {
		System.out.println("결재 관리를 원하는 번호를 입력하세요 ");
		System.out.println(" 1.근태관리    2.업무관리    3.급여관리     0.돌아가기 ");
		System.out.print("☞ ");
		int input = ScanUtil.nextInt();

		switch (input) {

		case 1:
			System.out.println("근태 승인을 변경할 직원의 사번을 입력해주세요");
			System.out.print("☞ ");
			Object empno = ScanUtil.nextInt();
			System.out.println("근태승인을 원하면 Y를 비승인을 원하면 N을 입력해주세요");
			System.out.print("☞ ");
			Object psign = ScanUtil.nextLine();
			int result = managerDao.updatePsign(psign, empno);
			if (result > 0) {
				System.out.println(empno + "의 근태승인이" + psign + "으로 변경되었습니다.");

			} else {
				System.out.println("다시 입력해주세요");
			}
			break;
		case 2:
			System.out.println("업무 승인을 변경할 직원의 사번을 입력해주세요");
			System.out.print("☞ ");
			Object empno_ = ScanUtil.nextInt();
			System.out.println("업무승인을 원하면 Y를 비승인을 원하면 N을 입력해주세요");
			System.out.print("☞ ");
			Object jsign = ScanUtil.nextLine();
			int result_ = managerDao.updateJsign(jsign, empno_);
			if (result_ > 0) {
				System.out.println(empno_ + "의 업무승인이" + jsign + "으로 변경되었습니다.");

			} else {
				System.out.println("다시 입력해주세요");
			}
			break;
		case 3:
			return View.MGR_SALPG;

		case 0:
			return View.MGR_MAIN;

		}
		return View.MGR_MAIN;

	}

}
