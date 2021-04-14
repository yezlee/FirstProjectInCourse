package dao;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import controller.Controller;
import util.JDBCUtil;

public class BoardDao {

	// 싱글톤
	private BoardDao() {
	}

	private static BoardDao instance;

	public static BoardDao getInstance() {
		if (instance == null) {
			instance = new BoardDao();
		}
		return instance;
	}

	// JDBCUtil랑 연결하기
	private JDBCUtil jdbc = JDBCUtil.getInstance();

	// 게시판 목록 (리스트) 출력 쿼리- select문
	public List<Map<String, Object>> selectBoardList(){
		String sql = "SELECT b_no, empno, b_title, b_date, j_sign " + "FROM work_board ORDER BY b_no DESC";

		return jdbc.selectList(sql);
	}

	// 게시글 조회(결재 조회) - 게시글 내용 보기 - select문
	public Map<String, Object> select_board(Map<String, Object> p){
		String sql = "SELECT b_no, w.empno, b_title, content, b_date, w.j_sign, ename, job "
				+ " FROM work_board w JOIN information i ON(w.empno = i.empno) WHERE b_no = ?";

		// 리스트를 맵으로 바꾸면서 추가된거
		List<Object> param = new ArrayList<>();
		param.add(p.get("B_NO"));

		return jdbc.selectOne(sql, param);
	}

	// 게시글 수정 - update문
	public int modify_board(Map<String, Object> p){
		String sql = "UPDATE work_board SET b_title = ?, content = ? , b_date = SYSDATE WHERE b_no = ? AND empno =  " + Controller.loginUser.get("EMPNO");

		List<Object> param = new ArrayList<>();
		param.add(p.get("B_TITLE"));
		param.add(p.get("CONTENT"));
		param.add(p.get("B_NO"));

		return jdbc.update(sql, param);
	}

	// 게시글 등록 - insert문
	public int insert_board(Map<String, Object> p){
		int empno = ((BigDecimal) Controller.loginUser.get("EMPNO")).intValue();
		// 게시글 등록할때 사번이 저절로 입력될때 필요한값
		// (BigDecimal) 이걸로 형변환을 해준거임

		String sql = "INSERT INTO work_board VALUES ((SELECT nvl(MAX(b_no),0) +1 FROM work_board), ?, ?, ?, SYSDATE, 'N')";

		List<Object> param = new ArrayList<>();
		param.add(empno);
		param.add(p.get("TITLE"));
		param.add(p.get("CONTENT"));

		return jdbc.update(sql, param);
	}

	// 게시글 삭제 - delete문
	public int delete_board(Map<String, Object> p){
		String sql = " DELETE work_board WHERE b_no = ? AND empno =  "+ Controller.loginUser.get("EMPNO");

		List<Object> param = new ArrayList<>();
		param.add(p.get("B_NO"));

		return jdbc.update(sql, param);
	}

	// 게시글 검색 - select문
	public List<Map<String, Object>> search_board(List<Object> param){
		String sql = "SELECT * FROM work_board WHERE b_title LIKE '%'|| ? ||'%'";
		// 그냥 '%?%' 하면 오류

		return jdbc.selectList(sql, param);
	}
	

	//업무게시판 관리자 권한 	
	//관리자용 - 게시글 수정 - update문
	public int manager_modify_board(Map<String, Object> p){
		String sql = "UPDATE work_board SET b_title = ?, content = ? WHERE b_no = ? ";

		List<Object> param = new ArrayList<>();
		param.add(p.get("B_TITLE"));
		param.add(p.get("CONTENT"));
		param.add(p.get("B_NO"));

		return jdbc.update(sql, param);
	}	

	
	//관리자용 - 게시글 삭제 - delete문
	public int manager_delete_board(Map<String, Object> p){
		String sql = " DELETE work_board WHERE b_no = ? ";
		List<Object> param = new ArrayList<>();
		param.add(p.get("B_NO"));

		return jdbc.update(sql, param);
	}


	//관리자용 - 결재승인 - update문
	public int update_boardJsign(Object j_sign, Object b_no) {
		String sql = "UPDATE work_board SET j_sign = ? "
				   + "WHERE b_no = ?"; 
		List<Object> param = new ArrayList<>(); 
		param.add(j_sign); 
		param.add(b_no); 
		return jdbc.update(sql, param); 
	}


	public int manager_insertBoard(Map<String, Object> p) {
		int empno = ((BigDecimal) Controller.loginUser.get("EMPNO")).intValue();
		// 게시글 등록할때 사번이 저절로 입력될때 필요한값
		// (BigDecimal) 이걸로 형변환을 해준거임

		String sql = "INSERT INTO work_board VALUES (null, ?, ?, ?, SYSDATE, '-')";

		List<Object> param = new ArrayList<>();
		param.add(empno);
		param.add(p.get("TITLE"));
		param.add(p.get("CONTENT"));

		return jdbc.update(sql, param);		
	}

	
}