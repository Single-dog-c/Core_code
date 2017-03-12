package com.filter;

import java.io.IOException;
import java.io.UnsupportedEncodingException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;
import javax.servlet.http.HttpServletResponse;


/**
 * 	�����ַ�
 * @author Administrator
 *
 */
public class EncodingFilter implements Filter{

	public void destroy() {
	       // TODO Auto-generated method stub
	       
       }

	public void doFilter(ServletRequest req, ServletResponse res, FilterChain chain) throws IOException, ServletException {
		System.out.println("\t\t���˱���");
		// TODO Auto-generated method stub
		// ת��
		HttpServletRequest request = (HttpServletRequest) req;
	       // װ�������ģʽ
	       MyHttpRequest myRequest = new MyHttpRequest(request);
	       // ���post��ʽ�ύ
	       myRequest.setCharacterEncoding("utf-8");
	       // ��Ӧ����
	       res.setCharacterEncoding("text/html;charset=utf-8");
	       // ����
	       chain.doFilter(myRequest, res);
	       System.out.println("���й��˺õı���");
	}

	public void init(FilterConfig arg0) throws ServletException {
	       // TODO Auto-generated method stub
	       
       }

}

/**
 * 	װ����
 * @author Administrator
 *
 */
class MyHttpRequest extends HttpServletRequestWrapper{
	private HttpServletRequest request;
	
	public MyHttpRequest(HttpServletRequest request) {
	       super(request);
	       this.request = request;
	}

	@Override
       public String getParameter(String name) {

		String value = request.getParameter(name);
		if(value != null) {
			// ���get��ʽ�ύ
			if("GET".equals(request.getMethod())) {
				try {
	                            value = new String(value.getBytes("ISO-8859-1"), "UTF-8");
                            } catch (UnsupportedEncodingException e) {
	                            e.printStackTrace();
	                            // ת��Ϊ����ʱ�쳣
	                            throw new RuntimeException();
                            }
			}
		}
		return value;
       }
}