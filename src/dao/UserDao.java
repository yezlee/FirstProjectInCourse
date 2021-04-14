package dao;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import controller.Controller;
import util.JDBCUtil;
import util.ScanUtil;

public class UserDao {

	private UserDao() {
	}

	private static UserDao instance;

	public static UserDao getInstance() {
		if (instance == null) {
			instance = new UserDao();
		}
		return instance;
	}

	private static JDBCUtil jdbc = JDBCUtil.getInstance();

	// 로그인 쿼리
	public Map<String, Object> selectUser(Integer empno, Object password) {
		String sql = "SELECT empno, ename, job" + " FROM information"
				+ " WHERE empno = ?" + " AND password = ?";
		List<Object> param = new ArrayList<>();
		param.add(empno);
		param.add(password);

		return jdbc.selectOne(sql, param);

	}

	// 1. 개인정보시스템
	// 전체 정보 조회

	public List<Map<String, Object>> selectUserList() {
		String sql = "SELECT *" + " FROM information i JOIN department d ON(i.deptno = d.deptno)" + " WHERE empno = ?";
		List<Object> param = new ArrayList<>();
		param.add(Controller.loginUser.get("EMPNO"));

		return jdbc.selectList(sql, param);

	}

	// 조직도확인에서 부서 검색을 통해 부서 번호 알아내는 쿼리

	public Map<String, Object> selectDepartPhoneList(String input) {
		List<Object> param = new ArrayList<>();
		param.add(input); 

		String sql = "SELECT dphon" + " FROM department" + " WHERE dname = ?";
		
		return jdbc.selectOne(sql, param);
	}
 

	// 비밀번호 변경 쿼리
	public int updateInforPassword(String password) {

		String sql = "UPDATE information SET password = ?" + " WHERE empno = "
				+ Controller.loginUser.get("EMPNO");

		List<Object> param = new ArrayList<>();
		param.add(password);

		return jdbc.update(sql, param);

	}

	// 이메일 변경 쿼리
	public int updateInforEmail(Object email) {

		String sql = "UPDATE information SET email = ?" + " WHERE empno =  "
				+ Controller.loginUser.get("EMPNO");
		List<Object> param = new ArrayList<>();
		param.add(email);

		return jdbc.update(sql, param);

	}

	// 2. 근태시스템

	// 출근등록 쿼리
	public int insertStartW(Map<String, Object> p) {
		String sql = "INSERT INTO punctuality(empno, dt, s_time) VALUES (?,?,SYSDATE)";
		List<Object> param = new ArrayList<>();
		param.add(p.get("EMPNO"));
		param.add(p.get("DT"));

		return jdbc.update(sql, param);

	}

	// 퇴근등록 쿼리
	public int insertEndW(Map<String, Object> p) {
		String sql = "UPDATE punctuality SET e_time=SYSDATE , p_sign = 'Y' WHERE empno=? AND dt=TO_CHAR(sysdate, 'mm/dd')";
		List<Object> param = new ArrayList<>();
		param.add(p.get("EMPNO"));

		return jdbc.update(sql, param);
	}

	// 출퇴근 전체조회 쿼리 (로그인한 회원의 회원번호를 가지고.)
	public static List<Map<String, Object>> selectAll() {
		String sql = "SELECT * FROM punctuality WHERE empno =  "
				+ Controller.loginUser.get("EMPNO");

		return jdbc.selectList(sql);
	}

	// 출퇴근 일별조회 쿼리
	public static List<Map<String, Object>> selectDaily() {
		System.out.println("해당 날짜의 출퇴근 정보를 조회합니다.");
		System.out.print("날짜 입력(월/일) ☞ ");
		String input = ScanUtil.nextLine();

		List<Object> dailylist = new ArrayList<>();
		String sql = "SELECT * FROM punctuality WHERE dt = ? AND empno = "
				+ Controller.loginUser.get("EMPNO");
		dailylist.add(input); // 첫번째 물음표에 input값을 넣는다.

		return jdbc.selectList(sql, dailylist);
	}

	// 초과근무 시작 쿼리
	public int insertStartOW(Map<String, Object> p) {
		String sql = "INSERT INTO overwork(empno, w_date, over_start) VALUES (?,?,SYSDATE)";
		List<Object> param = new ArrayList<>();
		param.add(p.get("EMPNO"));
		param.add(p.get("W_DATE"));

		return jdbc.update(sql, param);
	}

	// 초과근무 끝 쿼리
	public int insertStopOW(Map<String, Object> p) {

		String sql = "UPDATE overwork SET over_end=SYSDATE WHERE empno=? AND w_date=TO_CHAR(sysdate, 'mm/dd')";
		List<Object> param = new ArrayList<>();

		param.add(p.get("EMPNO"));

		return jdbc.update(sql, param);
	}

	// 초과근무 시간(끝-시작)계산 쿼리
	public int selectOW(Map<String, Object> p) {
		String sql = "UPDATE overwork"
				+ " SET over_time = (CONCAT(TO_CHAR(over_end, 'HH24')-TO_CHAR(over_start,'HH24') || '시간' ,TO_CHAR(over_end, 'MI')- TO_CHAR(over_start,'MI') || '분' ))"
				+ " WHERE empno = ?";
		List<Object> param = new ArrayList<>();
		param.add(p.get("EMPNO"));

		return jdbc.update(sql, param);
	}

	// 초과근무 전체조회 쿼리
	public static List<Map<String, Object>> selectOverAll() {
		String sql = "SELECT * FROM overwork WHERE empno = "
				+ Controller.loginUser.get("EMPNO");
		return jdbc.selectList(sql);
	}

	// 초과근무 일별조회 쿼리
	public static List<Map<String, Object>> selectOverDaily() {
		System.out.println("해당 날짜의 초과근무 정보를 조회합니다.");
		System.out.print("날짜 입력(월/일) ☞ ");
		String input = ScanUtil.nextLine();

		List<Object> dailylist = new ArrayList<>();
		String sql = "SELECT * FROM overwork WHERE w_date = ? AND empno = "
				+ Controller.loginUser.get("EMPNO");
		dailylist.add(input);

		return jdbc.selectList(sql, dailylist);
	}

	// 3. 급여시스템
	// 사용자의 전체 급여내역조회
	public List<Map<String, Object>> selectSal() {
		String sql = "SELECT dt, TOTAL_SAL, pay_day" + " FROM salary"
				+ " WHERE empno = " + Controller.loginUser.get("EMPNO");
		return jdbc.selectList(sql);

	}

	// 사용자의 급여 상세조회
	public List<Map<String, Object>> detailSal() {

		String sql = "SELECT dt, sal, NVL(OVER_TIME,0), NVL(OVER_SAL,0) + TOTAL_SAL, PAY_DAY"
				+ " FROM salary "
				+ " WHERE empno = "
				+ Controller.loginUser.get("EMPNO");

		return jdbc.selectList(sql);

	}

	// 사용자의 급여검색
	public List<Map<String, Object>> searchSal() {

		List<Object> dt = new ArrayList<>();
		System.out.println("원하는 날짜의 정보를 조회합니다");
		System.out.print("날짜입력 (년월) ☞ ");
		String input = ScanUtil.nextLine();

		String sql = "SELECT DT,SAL, NVL(OVER_SAL,0), TOTAL_SAL, PAY_DAY"
				+ " FROM SALARY" + " WHERE dt = ?" + " AND empno = "
				+ Controller.loginUser.get("EMPNO");
		dt.add(input); // 첫번째 물음표에 input값을 넣는다.

		return jdbc.selectList(sql, dt);

	}

	// 전체 급여 계산하여 보여주기
	public List<Map<String, Object>> totalSal() {

		List<Object> total = new ArrayList<>();
		System.out.println("계산하려는 날짜 정보를 입력합니다");
		System.out.print("날짜입력 (년월) ☞ ");
		String input = ScanUtil.nextLine();

		String sql = "SELECT dt, sal,  NVL(OVER_SAL,0), TOTAL_SAL, PAY_DAY"
				+ " FROM salary " + " WHERE dt = ? AND empno = "
				+ Controller.loginUser.get("EMPNO");
		total.add(input);

		return jdbc.selectList(sql, total);

	}

	// 초과수당 계산하기(이번달, 현재까지의 초과수당보여주기)
	public List<Map<String, Object>> overSal() {
		String sql = "SELECT dt, NVL(OVER_TIME,0),NVL(OVER_SAL,0) FROM SALARY WHERE EMPNO = "
				+ Controller.loginUser.get("EMPNO");

		return jdbc.selectList(sql);
	}

}