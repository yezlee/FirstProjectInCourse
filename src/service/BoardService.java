package service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import controller.Controller;
import dao.BoardDao;
import util.ScanUtil;
import util.View;

// 4번.업무게시판 클래스
public class BoardService {
	
	//생성자
	private BoardService(){}
	private static BoardService instance; 
	public static BoardService getInstanse(){
		if(instance == null){
			instance = new BoardService();
		}
		return instance;
	}
	
	//BoardDao랑 연결하기
	private BoardDao boardDao = BoardDao.getInstance();		
	
	
	
	//게시판 시작 
	public int boardStart(){
		System.out.println();
		System.out.println("1.업무 목록\t2.업무 등록\t3.검색\t4.뒤로가기\t0.프로그램 종료");
		System.out.print("☞  ");
		int input = ScanUtil.nextInt();
		System.out.println();
		
		switch(input){
		case 1:  //목록
			return boardList(); 
		case 2:  //등록
			insertBoard(); 
			return boardList();
		case 3:  //검색
			searchBoard();		
		case 4:  //뒤로가기
	    	Controller.home();
		case 0:  //종료
			System.out.println("프로그램을 종료합니다.");
		    System.exit(0);
		}
		
		Controller.loginUser= null;
		return boardStart();
	}
	
		
	//게시판 목록 (리스트) 출력
	public int boardList(){ 
		List<Map<String, Object>> boardList = boardDao.selectBoardList();
		
		System.out.println("======================================================================");
		System.out.println("번호\t사원번호\t업무제목\t\t\t작성일\t\t\t결재여부");
		System.out.println("----------------------------------------------------------------------");
		for(int i = 0; i< boardList.size(); i++){
			System.out.println(boardList.get(i).get("B_NO") + "\t" 
							   +boardList.get(i).get("EMPNO") + "\t" 
							   +boardList.get(i).get("B_TITLE") + "\t" 
							   + boardList.get(i).get("B_DATE").toString().substring(5,16) +"\t\t" 
							   +boardList.get(i).get("J_SIGN"));
		}
		System.out.println("======================================================================");
		System.out.println("1.조회\t2.수정\t3.삭제\t4.뒤로가기\t0.프로그램 종료");
		System.out.print("☞  ");
		
		int input = ScanUtil.nextInt();
		
		switch (input) {
		case 1:  //조회
			selectBoard(); 
		case 2:  //수정
			modifyBoard2(); 
			return boardList();
		case 3:  //삭제
			deleteBoard2();
			return boardList();
		case 4:  //뒤로가기
			boardStart();
		case 0:  //종료
			System.out.println("프로그램을 종료합니다.");
		    System.exit(0);
		}
		
		Controller.loginUser= null;
		return boardList();
	}

	
	//게시글 조회(결재 조회) - 게시글 내용 보기 
	public int selectBoard() {  
		System.out.println();
		System.out.println("조회할 <게시물 번호>를 입력해 주세요.");
		System.out.print("☞  ");
		int boardNo = ScanUtil.nextInt();		
		
		Map<String, Object> param = new HashMap<>();
		param.put("B_NO", boardNo);
		
		Map<String, Object> data = boardDao.select_board(param);
		
		if(data == null){
			return selectBoard();
		}else{
			System.out.println();
			System.out.println("┌─────────────────────────────────────────────────────┐");
			System.out.println();
			System.out.println("    게시물번호  " + data.get("B_NO"));
			System.out.println("    사원번호  " + data.get("EMPNO"));
			System.out.println("    " + data.get("ENAME")+" "+ data.get("JOB") );
			System.out.println();
			System.out.println(" ▶ 업무제목 : " + data.get("B_TITLE"));
			System.out.println();
			System.out.println(" ▶ 업무내용   \n\n" + data.get("CONTENT"));
			System.out.println();
			System.out.println("  [작성일 : " + data.get("B_DATE").toString().substring(5,16)+"]");	
			System.out.println("  [결재여부 : " + data.get("J_SIGN")+"]");
			System.out.println();
		    System.out.println("└─────────────────────────────────────────────────────┘");
		    System.out.println("1.수정\t2.삭제\t3.뒤로가기\t0.프로그램 종료");
		    System.out.print("☞ ");	    
		}
		
	    
	    int input = ScanUtil.nextInt();
	    
	    switch (input){
	    case 1: //수정
	    	modifyBoard(boardNo);
	    	return boardList();
	    case 2: //삭제
	    	deleteBoard(boardNo);
	    case 3: //뒤로가기
	    	boardList();
	    case 0: //종료
			System.out.println("프로그램을 종료합니다.");
		    System.exit(0);
	    }
	    
	    return selectBoard();
	    
	}
	
	
	//게시글 등록
	public int insertBoard() {  
		// 게시물번호 사원번호 제목 내용 날짜 결재승인
		System.out.println("이곳은 <업무게시판>입니다.");
		System.out.println("결재 받을 <업무 제목>을 입력해주세요");
		System.out.print("☞  ");
		String b_title = ScanUtil.nextLine();
		
		System.out.println("결재 받을 <업무 내용>을 입력해주세요");
		System.out.print("☞  ");
		String content = ScanUtil.nextLine();

		Map<String, Object> param = new HashMap<>();
		param.put("TITLE", b_title);
		param.put("CONTENT", content);
			
		int result = boardDao.insert_board(param);
			
		System.out.println(result +"개의 행이 등록되었습니다.");	
		System.out.println();
		return boardList();
	}
		
	
	//게시판목록에서 바로 삭제하기
	public int deleteBoard2() {
		System.out.println();
		System.out.println("삭제할 <게시물 번호>를 입력해 주세요.");
		System.out.print("☞  ");
		int input = ScanUtil.nextInt();
		
		deleteBoard(input);	
		return boardList();
	}

	
	//게시판목록에서 바로 수정하기
	public int modifyBoard2() {
		System.out.println();
		System.out.println("수정할 <게시물 번호>를 입력해 주세요.");
		System.out.print("☞  ");
		int input = ScanUtil.nextInt();
		
		modifyBoard(input);
		return boardList();
	}
	
	
	//게시글 검색(제목 검색만 가능)	
	public int searchBoard() {
		System.out.println();
		System.out.println("검색하고자 하는 <업무 제목>을 입력해 주세요.");
		System.out.println("[검색한 키워드를 포함한 모든 게시물이 출력됩니다]");
		System.out.print("☞  ");
		String input_search = ScanUtil.nextLine();
		
		List<Object> param = new ArrayList<>();
		param.add(input_search);
		
		List<Map<String, Object>> data = boardDao.search_board(param);
		System.out.println("=============================================================");
		System.out.println("번호\t사원번호\t업무 제목\t작성일\t\t\t결재여부");
		System.out.println("-------------------------------------------------------------");
		for(int i = 0; i<data.size(); i++){
		
		System.out.println(data.get(i).get("B_NO")+"\t"+data.get(i).get("EMPNO")
				+"\t"+data.get(i).get("B_TITLE")+"\t"+ data.get(i).get("B_DATE")
				+"\t"+data.get(i).get("J_SIGN"));
		}
	    System.out.println("=============================================================");
	    System.out.println();
	    System.out.println("1.조회\t2.수정\t3.삭제\t4.뒤로가기\t0.프로그램 종료");
		System.out.print("☞ ");		
		int input = ScanUtil.nextInt();
		
		switch (input) {
		case 1:  //조회
			selectBoard(); 
		case 2:  //수정
			modifyBoard2(); 
			return boardList();
		case 3:  //삭제
			deleteBoard2();
			return boardList();
		case 4:  //뒤로가기
			boardStart();
		case 0:  //종료
			System.out.println("프로그램을 종료합니다.");
		    System.exit(0);
		}
		
		return View.BOARDSTART;	
	}

	
	//게시글 조회 후 수정(게시글 내용 보고 나서 수정)
	public int modifyBoard(int boardNo) {
		System.out.println();
		System.out.println("수정할 <업무 제목>을 입력해 주세요.");
		System.out.print("☞  ");
		String title = ScanUtil.nextLine();
		
		System.out.println("수정할 <업무 내용>을 입력해 주세요.");
		System.out.print("☞  ");
		String content = ScanUtil.nextLine();
		
		Map<String, Object> param = new HashMap<>();
		param.put("B_TITLE", title);
		param.put("CONTENT", content);
		param.put("B_NO", boardNo);
		
		int result = boardDao.modify_board(param);
		
		 if (result > 0) {
	         System.out.println(result + "번 게시물이 정상적으로 수정 되었습니다.");
	      }else if (result == 0) {
	    	  System.out.println();
	          System.out.println("❎  수정할 수 있는 권한이 없습니다. 게시물의 사원번호를 확인해 주세요.");
	          System.out.println();
	      }
		
		 return boardList();
	}

	
	//게시글 조회 후 삭제(게시글 내용 보고 나서 삭제)
	public int deleteBoard(int boardNo) {
		Map<String, Object> param = new HashMap<>();
		param.put("B_NO", boardNo);
		param.put("EMPNO", Controller.loginUser.get("EMPNO")); //이건 추가 안해줘도 boardDao에서 쿼리문에where절만 추가해줘도 괜찮음
		
		int result = boardDao.delete_board(param);
		
		 if (result > 0) {
	         System.out.println(result + "번 게시물이 정상적으로 삭제되었습니다.");
	      }else if (result == 0) {
	    	  System.out.println();	         
	    	  System.out.println("❎ 삭제할 수 있는 권한이 없습니다. 게시물의 사원번호를 확인해 주세요.");
	    	  System.out.println();
	      }	   
		 return boardList();
	}
	
}