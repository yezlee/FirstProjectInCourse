package util;

public class View {

	public static final int MAINLOG = 0; // 메인 겸 로그인창
	public static final int HOME = 1; // 로그인 완료 후 본격적인 홈화면
	public static final int PUNCTUALITY = 3; // 근태관리
	public static final int SALARYPG = 4; // 급여관리
	public static final int BOARDSTART = 5; // 업무게시판

	// 근태페이지
	public static final int WORKPG = 17; // 근태 메인페이지
	public static final int WORK_START = 6; // 출근등록
	public static final int WORK_END = 7; // 퇴근등록
	public static final int WORK_ALL = 8; // 출퇴근 전체조회
	public static final int WORK_DAILY = 9; // 출퇴근 일별조회
	public static final int OVERWORK = 10; // 초과근무
	public static final int OVERWORK_START = 11; // 초과근무 시작
	public static final int OVERWORK_STOP = 12; // 초과근무 끝
	public static final int OVERWORK_ALL = 13; // 초과근무 전체조회
	public static final int OVERWORK_DAILY = 14; // 초과근무 일별조회
	public static final int USERWORK_START = 15;
	public static final int MGR_PSIGN = 16; // 전체사원의 출퇴근 확인
	public static final int MGR_DSIGN = 17; // 부서 전체의 출퇴근 확인

	// 개인정보페이지 - 사용자
	public static final int PRIVACYPG = 18; // 직원 개인이 자기 정보 조회
	public static final int USER_INFOR = 19; // 직원의 개인정보
	public static final int USER_DEPT = 20; // 직원의 부서정보
	public static final int USER_DPHONE = 21; // 부서전화번호
	public static final int USER_UPDATE = 22; // 개인정보

	// 개인정보 페이지 - 관리자

	public static final int MANAGERHOME = 23;
	public static final int MGR_MAIN = 24;
	public static final int MGR_PRIVACY = 25;
	public static final int MGR_ALLLIST = 26;
	public static final int MGR_DEPTLIST = 27;
	public static final int MGR_UPDATE = 28;
	public static final int MGR_APPROVE = 29;

	// 급여페이지 -> 사용자
	public static final int SAL_LIST = 30; // 급여목록조회
	public static final int SAL_DETAIL = 31; // 전체급여목록
	public static final int SAL_SEARCH = 32; // 급여 검색
	public static final int SAL_CALCULATION = 33; // 급여 계산
	public static final int SAL_TOTALCAL = 34; // 급여 계산
	public static final int SAL_OVERCAL = 35; // 초과수당 급여 계산
	public static final int SAL_TOTALIST = 36; // 초과수당목록

	// 관리자 급여페이지
	public static final int MGR_SALPG = 37;
	public static final int MGR_ALLSAL = 38;
	public static final int MGR_DEPTSAL = 39;
	public static final int MGR_SALSEARCH = 40;
	public static final int MGR_OVERTIME = 41;
	public static final int MGR_OVERTIMEUP = 42;
	public static final int MGR_TOTALSAL = 43;
	public static final int MGR_OVERSAL = 44;

	public static final int MGR_INFO = 45;
	public static final int MGR_MYUPDATE = 46;
	public static final int MGR_DPHONE = 47;
	public static final int MGR_MYINFO = 48;
	
	// 업무게시판
	public static final int BOARDLIST = 50;
	public static final int SELECTBOARD = 51;
	public static final int INSERTBOARD = 52;
	public static final int DELETEBOARD2 = 53;
	public static final int MODIFYBOARD2 = 54;
	public static final int SEARCHBOARD = 55;
	public static final int MODIFYBOARD = 56;
	public static final int DELETEBOARD = 57;

	// 관리자용 업무게시판(결재승인)
	public static final int MANAGERBOARD = 60;
	public static final int MANAGER_BOARDAPPROVAL = 61;
	public static final int BOARD_APPROVAL = 62;
	public static final int MANAGER_SEARCHBOARD = 63;
	public static final int MANAGER_BOARDLIST = 64;
	public static final int MANAGER_DELETEBOARD2 = 65;
	public static final int MANAGER_MODIFYBOARD2 = 66;
	public static final int MANAGER_SELECTBOARD = 67;
	public static final int MANAGER_BOARDAPPROVAL_NUM = 68;
	public static final int MANAGER_DELETEBOARD = 69;
	public static final int MANAGER_MODIFYBOARD = 70;

}