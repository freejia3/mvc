package model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;

public class DAO {
	Connection con;
	PreparedStatement ptmt;
	ResultSet rs;
	String sql;

	public DAO() {
		try {

			//안뇽 또 수정이닷
			Context init = new InitialContext();
			DataSource ds = (DataSource)init.lookup("java:comp/env/oooo");
			con = ds.getConnection();
			System.out.println("DB 접속 성공");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	//totalCount() -총글수 반환
	public int totalCount() {
		try {
			sql = "select count(*) from mvcb";
			ptmt = con.prepareStatement(sql);
			rs = ptmt.executeQuery();
			rs.next();
			return rs.getInt(1);

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally {
			close();
		}
		return 0;
	}

	public ArrayList<VO> list(int start, int end){
		ArrayList<VO> res = new ArrayList<>();

		sql = "select * from "
				+"(select rownum rnum, tt.* from "
				+"(select * from mvcb order by gid desc, seq) tt) "
				+"where rnum >= ? and rnum <= ?";
		try {
			ptmt = con.prepareStatement(sql);
			ptmt.setInt(1, start);
			ptmt.setInt(2, end);
			rs = ptmt.executeQuery();

			while(rs.next()) {
				VO vo = new VO();
				vo.setId(rs.getInt("id"));
				vo.setSeq(rs.getInt("seq"));
				vo.setLev(rs.getInt("lev"));
				vo.setPname(rs.getString("pname"));
				vo.setTitle(rs.getString("title"));
				vo.setContent(rs.getString("content"));
				vo.setReg_date(rs.getDate("reg_date"));

				res.add(vo);
			}
			return res;

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return null;
	}

	//detail
	public VO detail(int id) {

		try {
			sql = "select * from mvcb where id = ?";
			ptmt = con.prepareStatement(sql);
			ptmt.setInt(1, id);
			rs = ptmt.executeQuery();

			if(rs.next()) {
				VO vo = new VO();
				vo.setId(id);
				vo.setCnt(rs.getInt("cnt"));
				vo.setReg_date(rs.getDate("date"));
				vo.setTitle(rs.getString("title"));
				vo.setPname(rs.getString("pname"));
				vo.setContent(rs.getString("content"));
				vo.setUpfile(rs.getString("upfile"));
				return vo;
			}

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return null;
	}

	//insert
	public int insert(VO vo) {
		sql = "insert into mvcb "
				+ "(id, gid, seq, lev, cnt, reg_date, pname, pw, title, content, upfile) values"
				+ "(mvcb_seq.nextval, mvcb_seq.nextval, 0, 0, -1, sysdate, ?, ?, ?, ?, ?)";
		try {
			ptmt = con.prepareStatement(sql);
			ptmt.setString(1, vo.getPname());
			ptmt.setString(2, vo.getPw());
			ptmt.setString(3, vo.getTitle());
			ptmt.setString(4, vo.getContent());
			ptmt.setString(5, vo.getUpfile());
			ptmt.executeUpdate();

			sql = "select max(id) from mvcb";
			ptmt = con.prepareStatement(sql);
			rs = ptmt.executeQuery();
			rs.next();
			return rs.getInt(1);

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return 0;
	}

	//addCount -조회수 증가
	public void addCount(int id) {

		try {
			sql = "update mvcb set cnt = cnt+1 where id = ?";
			ptmt = con.prepareStatement(sql);
			ptmt.setInt(1, id);
			ptmt.executeUpdate();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		//close()안해요

	}

	//sch - id, pw 입력받고 upfile 리턴
	public VO sch(VO vo) {
		try {
			sql = "select * from mvcb where id = ? and pw =?";

			ptmt = con.prepareStatement(sql);
			ptmt.setInt(1, vo.getId());
			ptmt.setString(2, vo.getPw());
			rs = ptmt.executeQuery();
			if(rs.next()) {
				VO res = new VO();
				vo.setUpfile(rs.getString("upfile"));
				return res;
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		//close() 안해요
		return null;
	}

	//delete-로우 전체 삭제
	public void delete(int id) {

		try {
			sql = "delete * from mvcb where id = ?";
			ptmt = con.prepareStatement(sql);
			ptmt.setInt(1, id);
			ptmt.executeUpdate();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		//close()안해요

	}

	//modify
	public void modify(VO vo) {
		sql = "update mvcb set pname =?,  title=?, content=?, upfile=? where id =?";
		try {
			ptmt = con.prepareStatement(sql);
			ptmt.setString(1, vo.getPname());
			ptmt.setString(2, vo.getTitle());
			ptmt.setString(3, vo.getContent());
			ptmt.setString(4, vo.getUpfile());
			ptmt.setInt(5, vo.getId());
			ptmt.executeUpdate();

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		//close()안해요

	}


	//fileDelete-파일만 삭제
	public void fileDelete(int id) {
		try {
			sql = "update mvcb set upfile = null where id = ?";
			ptmt = con.prepareStatement(sql);
			ptmt.setInt(1, id);
			ptmt.executeUpdate();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		//close() 안해요

	}

	//reply -3단계.
	public int reply(VO vo) {


		try {
			VO ori = detail(vo.id);
			//기존 답글들에 대한 seq 처리
			sql = "update mvcb set seq = seq + 1 where gid=? and seq > ?";
			ptmt = con.prepareStatement(sql);
			ptmt.setInt(1, ori.getGid());
			ptmt.setInt(2, ori.getSeq());
			ptmt.executeUpdate();

			//insert
			sql = "insert into mvcb "
					+ "(id, gid, seq, lev, cnt, reg_date, pname, title, pw, content) "
					+ "values (mvcb_seq.nextval, ?, ?, ?, -1, sysdate, ?, ?, ?, ?)";
			ptmt = con.prepareStatement(sql);
			ptmt.setInt(1, ori.getGid());
			ptmt.setInt(2, ori.getSeq()+1);
			ptmt.setInt(3, ori.getLev()+1);
			ptmt.setString(4, vo.getPname());
			ptmt.setString(5, vo.getTitle());
			ptmt.setString(6, vo.getPw());
			ptmt.setString(7, vo.getContent());
			ptmt.executeUpdate();

			//select id
			sql = "select max(id) from mvcb";
			ptmt = con.prepareStatement(sql);
			rs = ptmt.executeQuery();
			rs.next();
			return rs.getInt(1);

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return 0;
	}


	public void close() {
		if(rs!=null) try{rs.close(); } catch(Exception e) {e.printStackTrace();}
		if(ptmt!=null) try{ptmt.close(); } catch(Exception e) {e.printStackTrace();}
		if(con!=null) try{con.close(); } catch(Exception e) {e.printStackTrace();}
	}

}
