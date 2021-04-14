package service;

import managerService.ManagerSalary;
import managerService.ManagerService;
import managerService.ManagerWork;
import service.UserService;
import util.ScanUtil;
import util.View;

public class UserPrivacy {

	private UserPrivacy() {
	}

	private static UserPrivacy instance;

	public static UserPrivacy getInstance() {
		if (instance == null) {
			instance = new UserPrivacy();
		}
		return instance;

	}

	public Object privacyForManager;
	private static UserService userService = UserService.getInstance();
	private static ManagerService mgrService = ManagerService.getInstance();
	private static ManagerSalary mgrSalary = ManagerSalary.getSal();
	private static ManagerWork mgrWork = ManagerWork.getInstance();
	
	public static int privacy() {

		System.out.println("1.개인정보 조회 \t2.개인정보수정 \t3.부서 전화번호 \t 4.돌아가기  "); // 1번
		System.out.println("☞");
		int input = ScanUtil.nextInt();

		switch (input) {
		case 1:
			userService.inforView();
			break;
		case 2:
			userService.UpdateView();
			break;
		case 3:
			userService.departView();
			break;
		case 4:
			return View.HOME;

		}

		return View.USER_INFOR;

		// 회원 개인정보

	}

	// 관리자 창

	public static int managerprivacy() {

		System.out.println("\t1.관리자정보 \t2.전체직원 조회 \t 3.부서별 직원 조회\t 4.직원정보 수정 \t5.승인관리\t 0.종료 ");
		System.out.println("☞");
		
		int input = ScanUtil.nextInt();
		switch (input) {
		case 1:		 
			return privacyForManager();
		case 2:
			return mgrService.ManagerView();
		case 3: 			
			System.out.println("조회하고 싶은 부서를 입력해주세요 < ☞대표이사, ☞개발부, ☞솔루션개발팀, ☞기술지원팀, ☞디자인부, ☞디자인팀 > ");
			System.out.println("부서이름 ☞ ");
			Object input1 = ScanUtil.nextLine();  
			return mgrService.ManagerDepartView(); 			
		case 4:
			return mgrService.ManagerUpdate();
		case 5: 
			return mgrWork.ManagerApprove(); 
		case 6:
			return mgrSalary.mgrSal();
		
		case 0:
			System.out.println("프로그램을 종료합니다.");
		    System.exit(0);
		 
		}

		return View.MGR_MAIN;
	}
	
	

	// 회원 개인정보

	public static int privacyForManager() {

		System.out.println("1.관리자정보 조회 \t2.관리자정보수정 \t3.돌아가기  "); 
		System.out.println("☞");
		int input = ScanUtil.nextInt();

		switch (input) {
		case 1:
			mgrService.Managerview();
			return UserPrivacy.managerprivacy();
		case 2:
			mgrService.UpdateViewforManager();
			return UserPrivacy.managerprivacy();
		case 3:
			return UserPrivacy.managerprivacy();
			
		}
		
		return UserPrivacy.managerprivacy(); 

	}
}
