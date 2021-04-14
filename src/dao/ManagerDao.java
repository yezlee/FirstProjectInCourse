package dao;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import controller.Controller;
import util.JDBCUtil;
import util.ScanUtil;

public class ManagerDao {

	private ManagerDao() {
	}

	private static ManagerDao instance;

	public static ManagerDao getInstance() {
		if (instance == null) {
			instance = new ManagerDao();
		}
		return instance;
	}

	private static JDBCUtil jdbc = JDBCUtil.getInstance();

	// 관리자가 관리하는 정보부분

	// 관리자가 전체 직원 테이블 보기
	public List<Map<String, Object>> selectUserForManager() {
		String sql = "SELECT * " + " FROM information i JOIN department d ON(i.deptno = d.deptno)";

		return jdbc.selectList(sql);

	}

	// 직원부서 변경하기
	public int updateDeptno(Object empno, Object dname) {

		String sql = "UPDATE information SET deptno = (SELECT deptno FROM department WHERE dname = ?) "
				+ " WHERE empno = ? ";
		List<Object> param = new ArrayList<>();
		param.add(dname);
		param.add(empno);
		return jdbc.update(sql, param);

	}

	// 직원의 직업을 변경하기
	public int updateJob(Object empno, Object job) {
		String sql = "UPDATE information SET job = ? " + " WHERE empno = ? ";
		List<Object> param = new ArrayList<>();
		param.add(job);
		param.add(empno);
		return jdbc.update(sql, param);
	}

	// 직원의 셀러리 변경하기
	public int updateSal(Object empno, Object sal) {
		String sql = "UPDATE information SET sal = ? " + " WHERE empno = ? ";
		List<Object> param = new ArrayList<>();
		param.add(sal);
		param.add(empno);
		return jdbc.update(sql, param);
	}

	// 업무승인
	public int updateJsign(Object jsign, Object empno_) {
		String sql = "UPDATE information SET j_sign = ? " + " WHERE empno = ? ";
		List<Object> param = new ArrayList<>();
		param.add(jsign);
		param.add(empno_);
		return jdbc.update(sql, param);
	}

	// 관리자테이블에서 부서별로 직원들 조회하기
	public List<Map<String, Object>> selectDepartForManager() {
		System.out.println("\n\t부서별 직원정보 조회");
		String sql = "SELECT i.empno iempno, i.ename iename, i.deptno ideptno , d.dname ddname "
				+ " FROM department d , information i "
				+ " WHERE d.deptno = i.deptno " ;//+ " AND d.dname = ? "
//		List<Object> dept = new ArrayList<>();
//		dept.add(input);

		return jdbc.selectList(sql);

	}

	// 관리자 테이블에서 자기 정보 조회하기
	public List<Map<String, Object>> selectListForManager() {
		String sql = "SELECT *" + " FROM information i JOIN department d ON(i.deptno = d.deptno)" + " WHERE empno = ?";
		List<Object> param = new ArrayList<>();
		param.add(Controller.loginUser.get("EMPNO"));

		return jdbc.selectList(sql, param);

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

	// 번호조회

	public Map<String, Object> selectDepartPhone(String dname) {
		String sql = "SELECT dphon" + " FROM department" + " WHERE dname = ?";
		List<Object> param = new ArrayList<>();
		param.add(dname);

		return jdbc.selectOne(sql, param);
	}

	// 관리자의 Work
	// 근태 전체조회 (사원번호, 날짜, Psign여부)
	public static List<Map<String, Object>> selectPsign() {
		String sql = " SELECT i.empno, p.dt, s_time, e_time, p.p_sign AS P_SIGN2 , i.p_sign"
				+ " FROM information i , punctuality p"
				+ " WHERE i.empno = p.empno" + " AND i.empno NOT IN(7000)";

		return jdbc.selectList(sql);

	}

	// 근태 부서별조회
	public static List<Map<String, Object>> selectDsign() {
		System.out.println("근태정보를 조회하고 싶은 부서의 이름을 입력해 주세요");
		System.out.println("\t대표이사\t개발부\t기획팀\t개발팀 \t디자인부\t디자인팀\t  ");
		System.out.print("☞ ");
		String input = ScanUtil.nextLine();

		String sql = "SELECT d.dname, i.empno, i.ename, s_time, e_time, i.p_sign"
				+ " FROM information i , punctuality p, department d"
				+ " WHERE i.empno = p.empno AND i.deptno = d.deptno"
				+ " AND i.empno NOT IN (7000) AND d.dname = ? ";
		List<Object> list = new ArrayList<>();
		list.add(input);

		return jdbc.selectList(sql, list);
	}

	// 근태승인
	public int updatePsign(Object psign, Object empno) {
		String sql = "UPDATE information SET p_sign = ? " + " WHERE empno = ? ";

		List<Object> param = new ArrayList<>();
		param.add(psign);
		param.add(empno);
		return jdbc.update(sql, param);
	}

	// 관리자 salary페이지
	// 관리자모드
	// 사용자의 전체 급여내역조회
	public List<Map<String, Object>> selectSal() {
		String sql = "SELECT dt, empno ,  NVL(total_sal,0)  , pay_day"
				+ " FROM salary";

		return jdbc.selectList(sql); // 리스트값
	}

	//부서별조회
	public List<Map<String, Object>> deptSal() {
		System.out.println("\t대표이사\t개발부\t기획팀\t개발팀 \t디자인부\t디자인팀\t  ");
		System.out.print("☞ ");
		String input = ScanUtil.nextLine();
		
		String sql = "SELECT dt, dname, sal, over_sal, NVL(total_sal,0), pay_day"
				+ " FROM SALARY" + " WHERE dname = ?" ;
		List<Object> list = new ArrayList<>();
		list.add(input);
		
		return jdbc.selectList(sql, list);
	}

	// 사용자의 급여검색
	public List<Map<String, Object>> searchSal() {

		System.out.println("원하는 날짜의 사원의 급여정보를 조회합니다");
		System.out.println("해당년/월을  입력하세요 (ex.202009)");
		System.out.print("☞ ");;
		String dt2 = ScanUtil.nextLine();
		System.out.println("사원번호를 입력하세요");
		System.out.print("☞ ");
		String empno = ScanUtil.nextLine();

		List<Object> dt = new ArrayList<>();

		String sql = "SELECT DT, EMPNO , SAL, NVL(OVER_TIME,0), NVL(OVER_SAL,0), NVL(TOTAL_SAL, 0), PAY_DAY"
				+ " FROM SALARY" + " WHERE dt = ?" + " AND empno = ?";
		dt.add(dt2); // 첫번째 물음표에 input값을 넣는다.
		dt.add(empno);
		return jdbc.selectList(sql, dt);

	}

	// 사용자의 초과 시간 표현하여 보여주기 -> 업데이트

	// 관리자가 초과시간을 확인하는것
	public List<Map<String, Object>> overtime() {

		List<Object> param = new ArrayList<>();

		System.out.println("사원의  초과근무 시간을 확인하세요");
		System.out.print("사원번호를 입력하세요");
		System.out.print("☞ ");
		int empno = ScanUtil.nextInt();
		System.out.print("해당월을 2자리로  입력하세요 (ex.09) ");
		System.out.print("☞ ");
		String month = ScanUtil.nextLine();

		String sql = "SELECT TRUNC ((A.sum_w/60), 0 )|| '시간' || TRUNC (((A.sum_w/60) - (TRUNC((A.sum_w/60), 0))) * 60  , 0)||'분' OVER_TIME "
				+ " FROM (SELECT SUM(extra_work) sum_w "
				+ " FROM ( SELECT empno , w_date, (TO_CHAR(over_end, 'HH24') - TO_CHAR(over_start,'HH24')) * 60  + TO_CHAR(over_end, 'MI') - TO_CHAR(over_start,'MI') extra_work "
				+ " FROM overwork WHERE empno = ? AND w_date LIKE  ?||'%' )) A ";

		param.add(empno);
		param.add(month);

		return jdbc.selectList(sql, param);
	}

	// 초과근무시간을 업데이트 하는 쿼리
	public static int updateOvertime() {

		System.out.print("업데이트할 사원번호를 입력하세요");
		System.out.print("☞ ");
		int empno = ScanUtil.nextInt();

		System.out.print("해당월을  입력하세요 (ex.09) ");
		System.out.print("☞ ");
		String dt = ScanUtil.nextLine();

		List<Object> param = new ArrayList<>();

		param.add(empno);
		param.add(dt);
		param.add(empno);

		String sql = "UPDATE salary SET OVER_TIME = (SELECT TRUNC ((A.sum_w/60), 0 )|| '시간' || TRUNC (((A.sum_w/60) - (TRUNC((A.sum_w/60), 0))) * 60  , 0)||'분' OVER_TIME "
				+ " FROM (SELECT SUM(extra_work) sum_w "
				+ " FROM ( SELECT empno , w_date, (TO_CHAR(over_end, 'HH24') - TO_CHAR(over_start,'HH24')) * 60  + TO_CHAR(over_end, 'MI') - TO_CHAR(over_start,'MI') extra_work  "
				+ " FROM overwork WHERE empno = ? AND w_date LIKE  ?||'%' )) A ) "
				+ " WHERE empno = ? ";

		return jdbc.update(sql, param);
	}

	// 초과 수당을 업데이트 하는 쿼리
	public int updateOverSal(Map<String, Object> p) {

		String sql = "UPDATE salary SET over_sal = (SELECT SUM(extra_work) * 0.05"
				+ " FROM (SELECT (TO_CHAR(over_end, 'HH24') - TO_CHAR(over_start,'HH24')) * 60  + TO_CHAR(over_end, 'MI') - TO_CHAR(over_start,'MI') extra_work"
				+ " FROM overwork"
				+ " WHERE W_DATE LIKE  '%'||?||'%' AND empno = ?))"
				+ " WHERE DT LIKE '%'||?||'%' AND empno = ?";

		List<Object> param = new ArrayList<>();
		param.add(p.get("DT"));
		param.add(p.get("EMPNO"));
		param.add(p.get("DT"));
		param.add(p.get("EMPNO"));

		return jdbc.update(sql, param);

	}

	// 전체 급여를 업데이트하는 쿼리
	public int updateTotalSal(Map<String, Object> p) {

		String sql = "UPDATE SALARY SET TOTAL_SAL = (SELECT  SAL + NVL(OVER_SAL, 0 ) "
				+ " FROM SALARY "
				+ " WHERE empno = ? AND dt = ? ) "
				+ " WHERE empno = ? AND dt = ? ";
		List<Object> param = new ArrayList<>();

		param.add(p.get("EMPNO"));
		param.add(p.get("DT"));
		param.add(p.get("EMPNO"));
		param.add(p.get("DT"));

		return jdbc.update(sql, param);

	}

}