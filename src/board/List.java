package board;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import model.Action;
import model.ActionData;
import model.DAO;

public class List implements Action {

	@Override
	public ActionData execute(HttpServletRequest request, HttpServletResponse response) {
		//page
		//limit, pageLimit
		//start, end
		//startPage, endPage
		//total , totalCount()
		//totalPage
		//endPage = totalPage

		int page = 1;
		int limit = 3, pageLimit = 4;

		if(request.getParameter("page")!=null ||
				!request.getParameter("page").equals("")) {
			page = Integer.parseInt(request.getParameter("page"));
		}

		int start = (page-1)*limit+1;
		int end = page*limit-1;

		int startPage = (page-1)/pageLimit*pageLimit+1;
		int endPage = startPage+pageLimit-1;


		DAO dao = new DAO();
		int total = dao.totalCount();

		int totalPage = total/limit;

		if(total%limit != 0) {
			totalPage++;
		}

		if(endPage>totalPage) {
			endPage = totalPage;
		}

		request.setAttribute("main", "list.jsp");
		request.setAttribute("data", dao.list(start, end));
		request.setAttribute("page", page);
		request.setAttribute("start", start);
		request.setAttribute("startPage", startPage);
		request.setAttribute("endPage", endPage);
		request.setAttribute("totalPage", totalPage);

		return new ActionData();
	}

}