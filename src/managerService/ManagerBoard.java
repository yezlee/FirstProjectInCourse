package managerService;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import util.ScanUtil;
import util.View;
import controller.Controller;
import dao.BoardDao;

public class ManagerBoard { // 업무게시판 관리자용

	//생성자
	private ManagerBoard() {}

	private static ManagerBoard instance;

	public static ManagerBoard getInstance() {
		if (instance == null) {
			instance = new ManagerBoard();
		}
		return instance;
	}

	private BoardDao boardDao = BoardDao.getInstance();

	// 관리자 게시판 시작
	public int manager_board() {
		System.out.println();
		System.out.println("1.업무 목록\n2.게시글검색\n3.업무 결재\n4.뒤로가기\n0.프로그램 종료");
		System.out.println("☞ ");
		int input = ScanUtil.nextInt();
		System.out.println();

		switch (input) {
		case 1: // 업무 목록
			return manager_boardList();
		case 2: // 검색
			manager_searchBoard();
		case 3: // 업무 승인
			manager_boardApproval();
		case 4: // 뒤로가기
			Controller.home();
		case 0: // 프로그램 종료
			System.out.println("프로그램을 종료합니다.");
			System.exit(0);
		}
		return View.MANAGERHOME;
	}

	// 메인페이지에서 바로 관리자 결재 승인으로 넘어오면
	public int manager_boardApproval() {

		List<Map<String, Object>> boardList = boardDao.selectBoardList();

		System.out.println("======================================================================");
		System.out.println("번호\t사원번호\t업무 제목\t\t\t작성일\t\t\t결재여부");
		System.out.println("----------------------------------------------------------------------");
		for (int i = 0; i < boardList.size(); i++) {
			System.out.println(boardList.get(i).get("B_NO") + "\t" + boardList.get(i).get("EMPNO") + "\t"
					+ boardList.get(i).get("B_TITLE") + "\t"
					+ boardList.get(i).get("B_DATE").toString().substring(5, 16) + "\t\t"
					+ boardList.get(i).get("J_SIGN"));
		}
		System.out.println("======================================================================");

		board_approval();
		return manager_board(); // 관리자용 업무관리로 리턴
	}

	// 결재승인
	public int board_approval() {
		System.out.println("결재승인 할 <게시물 번호>를 입력해주세요.");
		System.out.print("☞ ");
		Object b_no = ScanUtil.nextInt();
		System.out.println("업무승인을 원하면 Y를 입력해주세요");
		System.out.print("☞ ");
		Object j_sign = ScanUtil.nextLine();
		int result = boardDao.update_boardJsign(j_sign, b_no);
		if (result > 0) {
			System.out.println("<" + b_no + "번 게시물>의 결재승인이 완료되었습니다.");
		} else {
			System.out.println("다시 입력해주세요");
		}
		return manager_board(); // 관리자용 업무관리로 리턴
	}

	// 관리자용 검색
	public int manager_searchBoard() {
		System.out.println();
		System.out.println("검색하고자 하는 <업무 제목>을 입력해 주세요.");
		System.out.print("☞ ");
		String input_search = ScanUtil.nextLine();

		List<Object> param = new ArrayList<>();
		param.add(input_search);

		List<Map<String, Object>> data = boardDao.search_board(param);
		System.out.println("======================================================================");
		System.out.println("번호\t사원번호\t업무 제목\t\t\t작성일\t\t\t결재여부");
		System.out.println("----------------------------------------------------------------------");
		for (int i = 0; i < data.size(); i++) {

			System.out.println(data.get(i).get("B_NO") + "\t" + data.get(i).get("EMPNO") + "\t"
					+ data.get(i).get("B_TITLE") + "\t" + data.get(i).get("B_DATE") + "\t" + data.get(i).get("J_SIGN"));
		}

		System.out.println("======================================================================");
		System.out.println();
		System.out.println("1.조회\t 2.수정\t 3.삭제\t 4.결재\t 5.뒤로가기\t 0.프로그램 종료");
		System.out.print("☞ ");
		int input = ScanUtil.nextInt();

		switch (input) {
		case 1: // 조회
			manager_selectBoard();
		case 2: // 수정
			manager_modifyBoard2();
			return manager_boardList();
		case 3: // 삭제
			manager_deleteBoard2();
			return manager_boardList();
		case 4: // 게시물 번호 받고 승인
			board_approval();
			return manager_boardList();
		case 5: // 뒤로가기
			manager_board();
		case 0: // 종료
			System.out.println("프로그램을 종료합니다.");
			System.exit(0);
		}
		return manager_board(); // 관리자용 업무관리로 리턴
	}

	// 관리자용 업무게시판 리스트
	public int manager_boardList() {

		List<Map<String, Object>> boardList = boardDao.selectBoardList();

		System.out.println("======================================================================");
		System.out.println("번호\t사원번호\t업무 제목\t\t\t작성일\t\t\t결재여부");
		System.out.println("----------------------------------------------------------------------");
		for (int i = 0; i < boardList.size(); i++) {
			System.out.println(boardList.get(i).get("B_NO") + "\t" + boardList.get(i).get("EMPNO") + "\t"
					+ boardList.get(i).get("B_TITLE") + "\t"
					+ boardList.get(i).get("B_DATE").toString().substring(5, 16) + "\t\t"
					+ boardList.get(i).get("J_SIGN"));
		}
		System.out.println("======================================================================");
		System.out.println("1.조회\t 2.수정\t 3.삭제\t 4.결재\t 5.뒤로가기\t 0.프로그램 종료");
		System.out.print("☞ ");

		int input = ScanUtil.nextInt();

		switch (input) {
		case 1: // 조회
			manager_selectBoard();
		case 2: // 수정
			manager_modifyBoard2();
			return manager_boardList();
		case 3: // 삭제
			manager_deleteBoard2();
			return manager_boardList();
		case 4: // 승인
			board_approval();
			return manager_boardList();
		case 5: // 뒤로가기
			manager_board();
		case 0: // 종료
			System.out.println("프로그램을 종료합니다.");
			System.exit(0);
		}
		return manager_board(); // 관리자용 업무관리로 리턴

	}

	// 관리자용 - 게시판목록에서 바로 삭제하기
	public int manager_deleteBoard2() {
		System.out.println();
		System.out.println("삭제할 <게시물 번호>를 입력해 주세요.");
		System.out.print("☞ ");
		int input = ScanUtil.nextInt();

		manager_deleteBoard(input);
		return manager_board();
	}

	// 관리자용 - 게시판목록에서 바로 수정하기
	public int manager_modifyBoard2() {
		System.out.println();
		System.out.println("수정할 <게시물 번호>를 입력해 주세요.");
		System.out.print("☞ ");
		int input = ScanUtil.nextInt();

		manager_modifyBoard(input);
		return manager_board();
	}

	// 관리자용 게시글 조회(결재 조회) - 게시글 내용 보기
	public int manager_selectBoard() {
		System.out.println();
		System.out.println("조회할 <게시물 번호>를 입력해 주세요.");
		System.out.print("☞ ");
		int boardNo = ScanUtil.nextInt();

		Map<String, Object> param = new HashMap<>();
		param.put("B_NO", boardNo);

		Map<String, Object> data = boardDao.select_board(param);

		if (data == null) {
			return manager_selectBoard();
		} else {
			System.out.println();
			System.out.println("┌─────────────────────────────────────────────────────┐");
			System.out.println();
			System.out.println("    게시물번호  " + data.get("B_NO"));
			System.out.println("    사원번호  " + data.get("EMPNO"));
			System.out.println("    " + data.get("ENAME") + " " + data.get("JOB"));
			System.out.println();
			System.out.println(" ▶ 업무제목 : " + data.get("B_TITLE"));
			System.out.println();
			System.out.println(" ▶ 업무내용   \n\n" + data.get("CONTENT"));
			System.out.println();
			System.out.println("  [작성일 : " + data.get("B_DATE").toString().substring(5, 16) + "]");
			System.out.println("  [결재여부 : " + data.get("J_SIGN") + "]");
			System.out.println();
			System.out.println("└─────────────────────────────────────────────────────┘");
			System.out.println("1.수정\t 2.삭제\t 3.결재\t 4.뒤로가기\t 0.프로그램 종료");
			System.out.print("☞ ");
		}
		int input = ScanUtil.nextInt();

		switch (input) {
		case 1: // 수정
			manager_modifyBoard(boardNo);
			return manager_boardList();
		case 2: // 삭제
			manager_deleteBoard(boardNo);
		case 3: // 결재
			manager_boardApproval_num(boardNo);
		case 4: // 뒤로가기
			manager_boardList();
		case 0: // 종료
			System.out.println("프로그램을 종료합니다.");
			System.exit(0);
		}
		return manager_board(); // 관리자용 업무관리로 리턴
	}

	// 관리자용 - 게시글 조회 후 승인(게시글 내용 보고 나서 승인)
	public int manager_boardApproval_num(int boardNo) {

		System.out.println("결재승인을 원하면 Y를 입력해주세요");
		System.out.print("☞ ");
		Object j_sign = ScanUtil.nextLine();
		int result = boardDao.update_boardJsign(j_sign, boardNo);
		if (result > 0) {
			System.out.println("<" + boardNo + "번 게시물>의 결재승인이 완료되었습니다.");
			System.out.println();
		} else {
			System.out.println("다시 입력해주세요");
		}
		return manager_boardList(); // 완료 후 게시물리스트

	}

	// 관리자용 - 게시글 조회 후 삭제(게시글 내용 보고 나서 삭제)
	public int manager_deleteBoard(int boardNo) {
		Map<String, Object> param = new HashMap<>();
		param.put("B_NO", boardNo);

		System.out.println("<" + boardNo + "번 게시물>이 정상적으로 삭제되었습니다.");
		return manager_boardList();
	}

	// 관리자용 - 게시글 조회 후 수정(게시글 내용 보고 나서 수정)
	public int manager_modifyBoard(int boardNo) {
		System.out.println();
		System.out.println("수정할 <업무 제목>을 입력해 주세요.");
		System.out.print("☞ ");
		String title = ScanUtil.nextLine();

		System.out.println("수정할 <업무 내용>을 입력해 주세요.");
		System.out.print("☞ ");
		String content = ScanUtil.nextLine();

		Map<String, Object> param = new HashMap<>();
		param.put("B_TITLE", title);
		param.put("CONTENT", content);
		param.put("B_NO", boardNo);

		// int result = boardDao.manager_modify_board(param);

		System.out.println("<" + boardNo + "번 게시물>이 정상적으로 수정 되었습니다.");

		return manager_boardList(); // 수정 완료 후 게시물리스트
	}

}
