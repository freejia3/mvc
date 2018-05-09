package board;

import java.io.File;
import java.io.IOException;
import java.util.Collection;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;

import model.Action;
import model.ActionData;
import model.DAO;
import model.VO;

public class InsertReg implements Action {

	@Override
	public ActionData execute(HttpServletRequest request, HttpServletResponse response) {
		VO vo = new VO();
		DAO dao = new DAO();

		try {
			Collection<Part> parts = request.getParts();
			for(Part part: parts) {
				if(!part.getName().equals("upfile")) {
					switch(part.getName()) {
					case "title":
						vo.setTitle(request.getParameter("title"));
						break;
					case "pname":
						vo.setPname(request.getParameter("pname"));
						break;
					case "content":
						vo.setContent(request.getParameter("content"));
						break;
					case "pw":
						vo.setPw(request.getParameter("pw"));
						break;
					}
				}else {
					vo.setUpfile(fileUpload(request));
				}
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}


		int id = dao.insert(vo);

		ActionData data = new ActionData();

		data.setRedirect(true);
		data.setPath("Detail?id="+id);

		return data;
	}

	public String fileUpload(HttpServletRequest request) {
		String fileName ="";
		try {
			Part pp = request.getPart("upfile");
			if(pp.getContentType()!=null) {
				for(String hh:pp.getHeader("Content-Disposition").split(";")) {
					if(hh.trim().startsWith("filename")) {
						fileName =
							hh.substring(hh.indexOf("=")+1).trim().replaceAll("\"", "");
					}
				}
				if(!fileName.equals("")) {
					fileSave(pp, fileName, request);
				}
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return "";

	}

	public String fileSave(Part pp, String fileName, HttpServletRequest request) {

		int pos = fileName.lastIndexOf(".");
		String fileDo = fileName.substring(0, pos);
		String exp = fileName.substring(pos);

		String path = "F:\\jia\\mvc\\mvcProj\\WebContent\\up";

		File ff = new File(path+"\\"+fileName);

		int cnt = 0;

		if(ff.exists()) {
			fileName = fileDo+"_"+(cnt++)+exp;
			ff = new File(path+"\\"+fileName);
		}
		try {
			pp.write(path+"\\"+fileName);
			pp.delete();

		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return fileName;
	}
}
