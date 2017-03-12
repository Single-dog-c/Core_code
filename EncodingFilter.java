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
 * 	过滤字符
 * @author Administrator
 *
 */
public class EncodingFilter implements Filter{

	public void destroy() {
	       // TODO Auto-generated method stub
	       
       }

	public void doFilter(ServletRequest req, ServletResponse res, FilterChain chain) throws IOException, ServletException {
		System.out.println("\t\t过滤编码");
		// TODO Auto-generated method stub
		// 转型
		HttpServletRequest request = (HttpServletRequest) req;
	       // 装饰者设计模式
	       MyHttpRequest myRequest = new MyHttpRequest(request);
	       // 解决post方式提交
	       myRequest.setCharacterEncoding("utf-8");
	       // 响应编码
	       res.setCharacterEncoding("text/html;charset=utf-8");
	       // 放行
	       chain.doFilter(myRequest, res);
	       System.out.println("放行过滤好的编码");
	}

	public void init(FilterConfig arg0) throws ServletException {
	       // TODO Auto-generated method stub
	       
       }

}

/**
 * 	装饰者
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
			// 解决get方式提交
			if("GET".equals(request.getMethod())) {
				try {
	                            value = new String(value.getBytes("ISO-8859-1"), "UTF-8");
                            } catch (UnsupportedEncodingException e) {
	                            e.printStackTrace();
	                            // 转换为运行时异常
	                            throw new RuntimeException();
                            }
			}
		}
		return value;
       }
}