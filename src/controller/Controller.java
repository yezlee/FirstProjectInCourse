package controller;

import java.util.Map;
import java.math.BigDecimal;

import managerService.ManagerBoard;
import managerService.ManagerSalary;
import managerService.ManagerService;
import managerService.ManagerWork;
import dao.UserDao;
import service.BoardService;
import service.UserSalary;
import service.UserService;
import service.UserWork;
import util.ScanUtil;
import util.View;

public class Controller {

	public static void main(String[] args) {
		new Controller().start();
	}

	private UserDao userDao = UserDao.getInstance();
	private UserService userService = UserService.getInstance();
	private UserWork userWork = UserWork.getInstance();
	private UserSalary userSalary = UserSalary.getSal();
	private ManagerService mgrService = ManagerService.getInstance();
	private ManagerWork mgrWork = ManagerWork.getInstance();
	private ManagerSalary mgrSalary = ManagerSalary.getSal();
	private BoardService boardService = BoardService.getInstanse();
	private ManagerBoard mgrBoard = ManagerBoard.getInstance();

	public static Map<String, Object> loginUser;

	private void start() { // 화면 이동을 해주는 start메소드
		int view = View.MAINLOG;

		while (true) {
			switch (view) {
			case View.MAINLOG: // 메인로그인 페이지
				view = mainground();
				break;

			case View.HOME: // 로그인 완료 후 본격적인 시작화면
				view = home();
				break;

			// 1. 개인정보페이지
			case View.PRIVACYPG: // 개인정보 메인 페이지
				view = userService.privacy();
				break;

			case View.USER_INFOR: // 사용자의 개인정보조회
				view = userService.inforView();
				break;
			case View.USER_DEPT: // 부서번호조회
				view = userService.departView();
				break;
			// case View.USER_DPHONE:
			// view = userService.;

			case View.USER_UPDATE: // 개인정보수정
				view = userService.UpdateView();
				break;

			// 2.근태페이지
			case View.USERWORK_START: // 근태관리 main page
				view = userWork.start();
				break;

			case View.PUNCTUALITY: // work
				view = userWork.work();
				break;

			case View.WORK_START: // 출근등록
				view = userWork.workstart();
				break;
			case View.WORK_END: // 퇴근등록
				view = userWork.workend();
				break;
			case View.WORK_ALL: // 출퇴근 전체조회
				view = userWork.workall();
				break;
			case View.WORK_DAILY: // 출퇴근 일별조회
				view = userWork.workdaily();
				break;
			case View.OVERWORK: // 초과근무등록
				view = userWork.overwork();
				break;
			case View.OVERWORK_START: // 초과근무 시작
				view = userWork.overworkstart();
				break;
			case View.OVERWORK_STOP: // 초과근무 끝
				view = userWork.overworkstop();
				break;
			case View.OVERWORK_ALL: // 초과근무 전체조회
				view = userWork.overworkall();
				break;
			case View.OVERWORK_DAILY: // 초과근무 일별조회
				view = userWork.overworkdaily();
				break;

			// 3.급여페이지
			case View.SALARYPG: // 급여 main page
				view = userSalary.salList();
				break;

			case View.SAL_DETAIL: // 급여상세조회
				view = userSalary.saldetail();
				break;

			case View.SAL_SEARCH: // 급여검색
				view = userSalary.salSerach();
				break;

			case View.SAL_CALCULATION: // 급여계산 page
				view = userSalary.salaryCal();
				break;

			case View.SAL_TOTALCAL: // 전체급여계산
				view = userSalary.totalCal();
				break;

			case View.SAL_OVERCAL: // 초과수당계산
				view = userSalary.overCal();
				break;

			// 4.업무게시판(50번대)
			case View.BOARDSTART: // 업무게시판
				view = boardService.boardStart();
				break;
			case View.BOARDLIST: // 게시판 목록 (리스트) 출력
				view = boardService.boardList();
				break;
			case View.SELECTBOARD: // 게시글 조회(결재 조회) - 게시글 내용 보기
				view = boardService.selectBoard();
				break;
			case View.INSERTBOARD: // 게시글 등록
				view = boardService.insertBoard();
				break;
			case View.DELETEBOARD2: // 게시판목록에서 바로 삭제하기
				view = boardService.deleteBoard2();
				break;
			case View.MODIFYBOARD2: // 게시판목록에서 바로 수정하기
				view = boardService.modifyBoard2();
				break;
			case View.SEARCHBOARD: // 게시글 검색(제목 검색만 가능)
				view = boardService.searchBoard();
				break;
			case View.MODIFYBOARD: // 게시글 조회 후 수정(게시글 내용 보고 나서 수정)
				view = boardService.modifyBoard(view);
				break;
			case View.DELETEBOARD: // 게시글 조회 후 삭제(게시글 내용 보고 나서 삭제)
				view = boardService.deleteBoard(view);
				break;

			// 5. 매니저 관리 페이지
			case View.MANAGERHOME: // 매니저 홈
				view = managerhome();
				break;

			case View.MGR_MAIN:
				view = mgrService.mgrMain();
				break; // 관리 메인페이지

			case View.MGR_PRIVACY:
				view = mgrService.privacyForManager();
				break; // 관리자 개인정보페이지

			case View.MGR_INFO:
				view = mgrService.ManagerView(); break;// 직원전체 개인정보 
			
			case View.MGR_MYINFO:
				view = mgrService.Managerview(); break;// 관리자의 개인정보 
			
			case View.MGR_MYUPDATE: // 관리자의 개인정보수정
				view = mgrService.UpdateViewforManager(); break;

			case View.MGR_ALLLIST:
				view = mgrService.MgrAllList(); break; // 전체 직원정보

			case View.MGR_DEPTLIST:
				view = mgrService.ManagerDepartView(); break; // 전체 부서정보

			case View.MGR_DPHONE:
				view = mgrService.departPhone(); break; // 부서전화번호

			case View.MGR_APPROVE:
				view = mgrWork.ManagerApprove(); break; // 결재

			case View.MGR_UPDATE:
				view = mgrService.ManagerUpdate(); break; // 수정

			case View.MGR_PSIGN:
				view = mgrWork.managerPsign(); break; // 사원 전체 근태확인

			 case View.MGR_DSIGN: // 근태 부서별 조회
				 view =  mgrWork.selectdsign(); break;
		
			//관리자 - 급여관리
			case View.MGR_SALPG:
				view = mgrSalary.mgrSal();
				break; // 매니저의 급여관리
			case View.MGR_ALLSAL:
				view = mgrSalary.allSal();
				break; // 전체 사원의 급여조회
			case View.MGR_DEPTSAL:
				view = mgrSalary.deptSal();
				break; // 부서별 급여조회
			case View.MGR_OVERTIME:
				view = mgrSalary.overTime();
				break; // 총 초과시간 조회
			case View.MGR_SALSEARCH : //사원번호-검색
				view = mgrSalary.salSerach();
				break;
			case View.MGR_OVERTIMEUP : //시간업데이트
				view = mgrSalary.updateTime();
				break;
			case View.MGR_OVERSAL:		//초과수당 업데이트
				view = mgrSalary.updateOverSal();
				break;
			case View.MGR_TOTALSAL :	//전체급여 업데이트 
				view = mgrSalary.totalSal();
				break;
				
				// 5-1. 업무게시판 관리자용
			case View.MANAGERBOARD: // 관리자 게시판 시작
				view = mgrBoard.manager_board();
				break;
			case View.MANAGER_BOARDAPPROVAL: // 메인페이지에서 바로 관리자 결재 승인으로 넘어오면
				view = mgrBoard.manager_boardApproval();
				break;
			case View.BOARD_APPROVAL: // 결재승인
				view = mgrBoard.board_approval();
				break;
			case View.MANAGER_SEARCHBOARD: // 관리자용 검색
				view = mgrBoard.manager_searchBoard();
				break;
			case View.MANAGER_BOARDLIST: // 관리자용 업무게시판 리스트
				view = mgrBoard.manager_boardList();
				break;
			case View.MANAGER_DELETEBOARD2: // 관리자용 - 게시판목록에서 바로 삭제하기
				view = mgrBoard.manager_deleteBoard2();
				break;
			case View.MANAGER_MODIFYBOARD2: // 관리자용 - 게시판목록에서 바로 수정하기
				view = mgrBoard.manager_modifyBoard2();
				break;
			case View.MANAGER_SELECTBOARD: // 관리자용 게시글 조회(결재 조회) - 게시글 내용 보기
				view = mgrBoard.manager_selectBoard();
				break;
			case View.MANAGER_BOARDAPPROVAL_NUM: // 관리자용 - 게시글 조회 후 승인(게시글 내용 보고
													// 나서 승인)
				view = mgrBoard.manager_boardApproval_num(view);
				break;
			case View.MANAGER_DELETEBOARD: // 관리자용 - 게시글 조회 후 삭제(게시글 내용 보고 나서
											// 삭제)
				view = mgrBoard.manager_deleteBoard(view);
				break;
			case View.MANAGER_MODIFYBOARD: // 관리자용 - 게시글 조회 후 수정(게시글 내용 보고 나서
											// 수정)
				view = mgrBoard.manager_modifyBoard(view);
				break;
		
				
			}

		}
	}

	private int mainground() {
		System.out.println("□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□");
		System.out.println("□□□□□□□■■■■■■■■■■■■■□□□□□□■■■■■■■■■■■□□□□□□□□□□□□□□□□□■■■■■■■■■■□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□");
		System.out.println("□□□□□□□■■■■■■■■■■■■■■■□□□□■■■■■■■■■■■■■■□□□□□□□□□□□□□■■■■■■■■■■■■■□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□");
		System.out.println("□□□□□□□■■■■■□□□□■■■■■■■□□□■■■■■□□□□■■■■■■■□□□□□□□□□□■■■■■■□□□□■■■■■□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□");
		System.out.println("□□□□□□□■■■■■□□□□□□□■■■■■■□■■■■■□□□□□□□■■■■■■□□□□□□■■■■■□□□□□□□□□□□□□□□■■■■■■■■■■□□□□■■■■■■■■■■□□■■■■■□□□□□□■■■■□■■■■■■□□□□■■■■■■■■■■□□□□□■■■■■■■■■■■■□□□■■■■■□□□□□■■■■■□□□□□□□");
		System.out.println("□□□□□□□■■■■■□□□□□□□■■■■■■□■■■■■□□□□□□□■■■■■■□□□□□□■■■■■□□□□□□□□□□□□□□■■■■■■■■■■■■□□□■■■■■■■■■■■■■■■■■■■□□□□■■■■■■■■■■■■■□□□■■■■■■■■■■■□□□■■■■■■■■■■■■■□□■■■■■□□□□■■■■■□□□□□□□□");
		System.out.println("□□□□□□□■■■■■□□□□□□□■■■■■■□■■■■■□□□□□□□■■■■■■□□□□□□■■■■■□□□□□□□□□□□□□■■■■■□□□□□■■■■□□■■■■■■□□■■■■■□□■■■■■■□□■■■■■□□□■■■■■■□□□■■■■□□□■■■■□□■■■■■□□■■■■■■□□■■■■■□□□■■■■■□□□□□□□□□");
		System.out.println("□□□□□□□■■■■■□□□□□□□■■■■■□□■■■■■□□□□□□□■■■■■□□□□□□□■■■■■□□□□□□□□□□□□■■■■■□□□□□□■■■■■□■■■■■□□□■■■■□□□□■■■■■□□■■■■□□□□□■■■■■□□■■■■■■■■■■■■□□■■■■■□□□□■■■■■□■■■■■□■■■■■□□□□□□□□□□□");
		System.out.println("□□□□□□□■■■■■□□□□□□■■■■■■□□■■■■■□□□□□□■■■■■■□□□□□□□■■■■■■□□□□□□□□□□□■■■■■□□□□□□■■■■■□■■■■■□□□■■■■□□□□■■■■■□□■■■■□□□□□■■■■■□□■■■■□□□□■■■■□□■■■■■□□□□■■■■■□□■■■■■■■■■□□□□□□□□□□□□");
		System.out.println("□□□□□□□■■■■■□□□□□■■■■■■□□□■■■■■□□□□□■■■■■■□□□□□□□□□■■■■■□□□□□□□□□□□■■■■■□□□□□□■■■■□□■■■■■□□□■■■■□□□□■■■■■□□■■■■■□□□□■■■■□□■■■■■□□□■■■■■□□■■■■■□□□□■■■■■□□□■■■■■■■□□□□□□□□□□□□□");
		System.out.println("□□□□□□□■■■■■■■■■■■■■■□□□□□■■■■■■■■■■■■■■□□□□□□□□□□□□□■■■■■■■■■■■■■□□■■■■■■■■■■■■■□□□■■■■■□□□■■■■□□□□■■■■■□□■■■■■■■■■■■■□□□■■■■■■■■■■■■■□□■■■■■□□□□■■■■■□□□□■■■■■□□□□□□□□□□□□□□");
		System.out.println("□□□□□□□■■■■■■■■■■■■□□□□□□□■■■■■■■■■■□□□□□□□□□□□□□□□□□□■■■■■■■■■■□□□□□□■■■■■■■■■□□□□□■■■■■□□□■■■■□□□□■■■■■■□■■■■■■■■■■□□□□□□■■■■■■■■■■■■□□■■■■■□□□□■■■■■□□□□■■■■□□□□□□□□□□□□□□□");
		System.out.println("□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□■■■■□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□■■■■□□□□□□□□□□□□□□□");
		System.out.println("□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□■■■□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□■■■■□□□□□□□□□□□□□□□□□");
		System.out.println("□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□■■■□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□■■■■□□□□□□□□□□□□□□□□□□");
		System.out.println("□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□");
		System.out.println();
		System.out.println("==============================================");
		System.out.println(" \t  DD COMPANY 재택근무시스템  ");
		System.out.println("==============================================");
		System.out.println();
		System.out.println("LOGIN");

		Integer empno = null;
		while (true) {
			try {
				System.out.print(" 사원번호 : ");
				empno = ScanUtil.nextInt(); // id는 사원번호로 되어있다(모두 숫자 int)
				break;
			} catch (Exception e) {
				System.out.println("ID 입력이 잘못되었습니다. (사원번호만 입력하세요.)");
				continue;
			}
		}

		System.out.print(" 비밀번호 : ");
		Object password = ScanUtil.nextLine();

		// 관리자가 로그인했을때랑 직원이 로그인 했을때가 다릅니다. (정)
		Map<String, Object> user = userDao.selectUser(empno, password);
		if (user == null) {
			System.out.println("잘못된 비밀번호입니다.");
			System.out.println("관리자에게 문의하세요");
			System.out.println();
		} else {
			Controller.loginUser = user;
			if (((BigDecimal) Controller.loginUser.get("EMPNO")).intValue() == 7000) {
				System.out.println("\n\t\t<관리자> 로그인이 되었습니다.");
				return View.MANAGERHOME; // 관리자의 페이지로 연결된다.
			} else {
				System.out.println("\n  ---<" + Controller.loginUser.get("ENAME") 
						+ "(" + Controller.loginUser.get("EMPNO") 
						+")-" 
						+ Controller.loginUser.get("JOB")
						+ ">--- 로그인이 되었습니다.");
				return View.HOME; // 사원의 페이지로 들어간다.
			}

		}
		return View.MAINLOG;
	}

	public static int home() {
		System.out.println("\n\t◑HOME◑");
		System.out.println("\n\t1.개인정보\n\t2.근태등록\n\t3.급여관리\n\t4.결재게시판\n\t0.종료");
		System.out.print("\t☞");

		int input = ScanUtil.nextInt();
		switch (input) {
		case 1: // 개인정보
			return View.PRIVACYPG;
		case 2: // 근태관리
			return View.USERWORK_START;
		case 3: // 급여관리
			return View.SALARYPG;
		case 4: // 업무게시판
			return View.BOARDSTART;

		case 0: // 종료
			System.out.println("프로그램을 종료합니다.");
			System.exit(0);
		}
		return View.HOME;

	} // home 의 끝

	// 관리자의 첫번째 home 입니다.

	public static int managerhome() {
		System.out.println("\n\t\t◑ 관리자 페이지 ◑");
		System.out.println("\t1.직원관리\t2.결재게시판관리\t0.종료");
		System.out.print("\t☞");

		int input = ScanUtil.nextInt();
		switch (input) {
		case 1: // 개인정보
			return View.MGR_MAIN; // 변경부분
		case 2:
			return View.MANAGERBOARD;
		case 0: // 종료
			System.out.println("프로그램을 종료합니다.");
			System.exit(0);

		}
		return View.MANAGERHOME;

	} // 관리자 home 의 끝

} // 전체 class 끝