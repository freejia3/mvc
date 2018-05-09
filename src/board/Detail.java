package board;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import model.Action;
import model.ActionData;
import model.DAO;

public class Detail implements Action {

	@Override
	public ActionData execute(HttpServletRequest request, HttpServletResponse response) {
		//addCount()
		//detail()
		int id = Integer.parseInt(request.getParameter("id"));
		DAO dao = new DAO();
		dao.addCount(id);
		request.setAttribute("data", dao.detail(id));
		request.setAttribute("main", "detail.jsp");
		dao.close();
		return new ActionData();
	}

}
