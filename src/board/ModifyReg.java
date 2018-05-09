package board;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import model.Action;
import model.ActionData;
import model.DAO;
import model.VO;

public class ModifyReg implements Action {

	@Override
	public ActionData execute(HttpServletRequest request, HttpServletResponse response) {

		//msg
		//url
		//sch(vo)!=null
		//파일이 있다면, request.getParameter("upfile")
		//파일이 없다면, InsertReg().fileUpload(request)
		//modify(vO)
		//close();
		//alert.jsp

		String page = request.getParameter("page");

		DAO dao = new DAO();

		VO vo = new VO();
		vo.setId(Integer.parseInt(request.getParameter("id")));
		vo.setTitle(request.getParameter("title"));
		vo.setPname(request.getParameter("pname"));
		vo.setPw(request.getParameter("pw"));
		vo.setContent(request.getParameter("content"));

		VO res = dao.sch(vo);

		String msg = "암호 인증 실패";
		String url = "ModifyForm?id="+vo.getId()+"&page="+page;

		//id, pw 일치한다면,
		if(res!=null) {
			//이미 업로드된 파일이 있는 경우,
			if(request.getParameter("upfile")!=null) {
				vo.setUpfile(request.getParameter("upfile"));
			}else { //새파일 있다면 업로드
				vo.setUpfile(new InsertReg().fileUpload(request));
			}
			dao.modify(vo);
			msg = "수정되었습니다.";
			url = "Detail?id="+vo.getId()+"&page="+page;
		}
		dao.close();

		request.setAttribute("url", url);
		request.setAttribute("msg", msg);
		request.setAttribute("main", "alert.jsp");
		return new ActionData();
	}

}