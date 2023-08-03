package app.mvc.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import dto.ItemDTO;
import dto.MemberDTO;
import dto.OrderDTO;
import exception.DMLException;
import exception.SearchWrongException;

public class ManagerDAOImpl implements ManagerDAO {
	private static ManagerDAO instance = new ManagerDAOImpl();
	
	private ManagerDAOImpl() {}
	
	public static ManagerDAO getInstance() {
		return instance;
	}

	@Override // 1. 모든 주문 검색
	public List<OrderDTO> selectOrderAll() throws SearchWrongException {
		Connection con = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		List<OrderDTO> list = new ArrayList<>();
		String sql="select order_no, member_no, order_date, payment from orders";
		
	}
 
	@Override // 2. 기간별 주문 검색(매출액)
	public int selectTotalSalesByPeriod(int period) throws SearchWrongException {
		Connection con = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		List<OrderDTO> list = new ArrayList<>();
		// 3. 1년 동안의 매출 (기본)
		int viewDays = 365;
		// 1. 하루 동안의 매출
		if (period == 1) {
			viewDays = 1;
		// 2. 일주일 동안의 매출
		} else if (period == 2) {
			viewDays = 7;
		}
		
		String sql="select sum(payment) "
				+ "from (select order_no, member_no, order_date, payment from orders "
				+ "where order_date > sysdate - ? "
				+ "order by order_date desc)";
		// ?  viewDays
	}

	@Override // 3. 모든 아이템 검색
	public ItemDTO selectItemAll() throws SearchWrongException {
		Connection con = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		ItemDTO itemDTO = null;
		String sql= "select item_no, item_name, price, stock, info from items";
	}
	
	@Override // 4. 인기 아이템 검색(top3)
	public List<ItemDTO> selectItemTop3() throws SearchWrongException {
		Connection con = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		List<ItemDTO> list = new ArrayList<>();
		String sql = "select item_name from items "
				+ "where item_no = "
				+ "(select item_no from "
				+ "(select item_no from order_detail group by item_no order by sum(qty) desc) "
				+ "where rownum <= 3)";
	}

	@Override // 5. 아이템 추가
	public int insertItem(ItemDTO itemDTO) throws DMLException {
		Connection con = null;
		PreparedStatement ps = null;
		int result = 0;
		String sql="insert into items (item_no, item_name, price, stock, info) "
				+ "values (?, ?, ?, ?, ?)";
		// 1. itemDTO.getItemNo();
		// 2. itemDTO.getItemName();
		// 3. itemDTO.getPrice();
		// 4. itemDTO.getStock();
		// 5. itemDTO.getInfo();
	}

	@Override // 6. 아이템 이름으로 삭제
	public int deleteItemByItemName(String itemName) throws DMLException {
		Connection con = null;
		PreparedStatement ps = null;
		int result = 0;
		String sql="delete from items where item_name = ?";
		// ? itemName
	}

	@Override // 7. 아이템 아이템번호로 선택한 후 수정(재고관리)
	public int updateItemStock(ItemDTO itemDTO) throws DMLException {
		Connection con = null;
		PreparedStatement ps = null;
		int result = 0;
		String sql="update items set stock = ? where item_no = ? ";
	}

	@Override // 8. 전체 멤버 검색
	public List<MemberDTO> selectMemberAll() throws SearchWrongException{
		Connection con = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		List<MemberDTO> list = new ArrayList<>();
		String sql="select member_no, phone, point, grade from member";
	}

	@Override // 9. 전화번호로 해당하는 멤버 검색
	public MemberDTO selectMemberByPhone(String phone) throws SearchWrongException{
		Connection con = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		MemberDTO memberDTO = null;
		String sql="select member_no, phone, point, grade from member where phone = ?";
		// ? phone
	}

	@Override // 10. 멤버 추가
	public int insertMember(MemberDTO memberdto) throws DMLException {
		Connection con = null;
		PreparedStatement ps = null;
		int result = 0;
		String sql="insert into member (member_no, phone, point, grade) "
				+ "values (?, ?, ?, ?, ?)";
	}

	@Override // 11. 전화번호로 해당하는 멤버 삭제
	public int deleteMemberByPhone(String phone) throws DMLException {
		Connection con = null;
		PreparedStatement ps = null;
		int result = 0;
		String sql="delete from member where phone = ?";
		// ? phone
	}

	@Override // 12. 멤버 등급 수정, 수정이 완료된 멤버의 수 return
	public int updateMemberGrade() throws DMLException {
		Connection con = null;
		PreparedStatement ps = null;
		int result = 0;
		String sql="update member set grade = ?";
		// ? selectMemberGrade의 결과

	}
	
	/**
	 * 멤버당 총 결제금액 가져오기
	 */
	private int selectMemberTotalPayment(Connection con, int memberNo) throws SQLException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		int totalPayment = 0;
		String sql = "select sum(payment) from order group by member_no having member_no=?";
		// ? memberNo
	}
	
	/**
	 * 결제금액에 해당하는 등급 가져오기
	 */
	private String selectMemberGrade(Connection con,  int totalPayment) throws SQLException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		String memberGrade = null;
		String sql = "select grade_name from grade where standard between ? - 50000  and ?  + 50000";
		// ? totalPayment
	}
	

}
