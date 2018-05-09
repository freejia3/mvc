package board;

import java.io.File;
import java.io.FileInputStream;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import model.Action;
import model.ActionData;

public class FileDown implements Action {

	@Override
	public ActionData execute(HttpServletRequest request, HttpServletResponse response) {
		String fileName = request.getParameter("file");
		String path = "F:\\jia\\mvc\\mvcProj\\WebContent\\up\\";

		try {
			String en = URLEncoder.encode(fileName, "utf-8");
			response.setHeader("Content-Disposition", "attachment;filename="+en);

			File ff = new File(path+fileName);

			ServletOutputStream sos = response.getOutputStream();
			FileInputStream fis = new FileInputStream(ff);
			byte [] buf = new byte[1024];
			if(fis.available()>0) {
				int len = fis.read(buf);
				sos.write(buf,0,len);
			}
			fis.close();
			sos.close();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return null;
	}

}
