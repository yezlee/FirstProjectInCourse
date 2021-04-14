package service;

import dao.UserDao;
import util.ScanUtil;
import util.View;
import java.util.List;
import java.util.Map;

public class UserService {

	private UserService() {
	}

	private static UserService instance;

	public static UserService getInstance() {
		if (instance == null) {
			instance = new UserService();
			

		}
		return instance;
	}

	private UserDao userDao = UserDao.getInstance();
	
	
	public int privacy() {

		System.out.println("1.개인정보 조회 \t2.개인정보수정 \t3.부서 전화번호 \t 4.돌아가기  "); // 1번
		System.out.print("☞");
		int input = ScanUtil.nextInt();

		switch (input) {
		
		case 1: return View.USER_INFOR; //개인정보조회
		case 2: return View.USER_UPDATE; //개인정보수정
		case 3: return View.USER_DEPT; //부서 전화번호확인
		case 4:
			return View.HOME;

		}

		return View.PRIVACYPG;

		// 회원 개인정보

	}

	// 직원정보 보기
	public int inforView() {
		List<Map<String, Object>> inforview = userDao.selectUserList();
		System.out.println();
		System.out.println("┌──────────────────────────  INFORMATION  ───────────────────────────────┐");
		
		for (int i = 0; i < inforview.size(); i++) {
			System.out.println("\n 사원번호 : " + inforview.get(i).get("EMPNO") 
					+"\n 부서번호 : "+ inforview.get(i).get("DEPTNO") 
					+"\n 부서 : "+ inforview.get(i).get("DNAME") + "\t"
					+"\n 사원이름 : "+ inforview.get(i).get("ENAME") + "\t"
					+"\n 직책 : "+ inforview.get(i).get("JOB") + "\t"
					+"\n 월급(기본급): "+ inforview.get(i).get("SAL") + "\t"
					+"\n 입사년도: "+ inforview.get(i).get("HIREDATE") + "\t"
					+"\n e-mail : "+ inforview.get(i).get("EMAIL") + "\t"
					+"\n 근태확인: "+ inforview.get(i).get("P_SIGN") + "\t"
					+"\n 결재확인 : "+ inforview.get(i).get("J_SIGN"));
			System.out.println();
			System.out.println("└─────────────────────────────────────────────────────────────────────┘");

		}		
		return View.PRIVACYPG;

	} // infor메소드 끝

	
	public int departView() {
		
		System.out.println();
			
		System.out.println("---------------------------- 부서 전화번호 ---------------------------");
		System.out.println("전화번호를 조회하고 싶은 부서의 이름을 입력해 주세요");
		System.out.println("\n☎대표이사\n☎개발부\n☎기획팀\n☎개발팀 \n☎디자인부\n☎디자인팀\n");
		System.out.println();
		System.out.print("\n\t☎: ");
		String input = ScanUtil.nextLine();
		
		
		
		Map<String, Object> result = userDao.selectDepartPhoneList(input);
		System.out.print("\t☎ "+input + "  부서 연락처 : "  ); 
		System.out.println(result.get("DPHON"));
		System.out.println();
		return View.PRIVACYPG;

	}

	


	// 각자 직원이 자기 정보 수정하기
	public int UpdateView() {
		System.out.println();
		System.out.println("원하시는 번호를 입력해주세요");
		System.out.println("1.비밀번호변경\t2.이메일변경 \t3.메인으로 돌아가기 ");
		System.out.print("☞ ");
				int input = ScanUtil.nextInt();

		switch (input) {

		case 1:

			System.out.println("변경을 원하는 비밀번호를 입력해주세요");
			System.out.print("☞ ");
			String password = ScanUtil.nextLine();

			int result = userDao.updateInforPassword(password);
			if (result > 0) {
				System.out.println("비밀번호가 성공적으로 변경되었습니다.");
			break;
			} else {
				
				System.out.println("다시 입력해주세요 ");
			}
			break;

		case 2:

			System.out.println("변경을 원하는 이메일번호를 입력해주세요");
			System.out.print("☞ ");
			String email = ScanUtil.nextLine();
			int p1 = userDao.updateInforEmail(email);
			if (p1 > 0) {
				System.out.println("이메일주소가 변경되었습니다.");
				break;
			} else {
				System.out.println("다시 입력해주세요 ");
			}
			;
		case 3:
			return View.PRIVACYPG;

		}
		System.out.println("초기화면으로 돌아갑니다.");
		return View.PRIVACYPG;
	}

}// class 끝