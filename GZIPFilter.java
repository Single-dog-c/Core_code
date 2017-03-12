package com.filter;

import java.io.ByteArrayOutputStream;
import java.io.CharArrayWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.zip.GZIPOutputStream;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpServletResponseWrapper;

/**
 * 	������Ӧ����
 * @author Administrator
 *
 */
public class GZIPFilter implements Filter{

	public void destroy() {
	       // TODO Auto-generated method stub
	       
       }

	public void doFilter(ServletRequest req, ServletResponse res,FilterChain chain) throws IOException, ServletException {
	       // TODO Auto-generated method stub
		// ת��
		MyHttpResponse myResponse = new MyHttpResponse((HttpServletResponse) res);
		// ����
		chain.doFilter(req, myResponse);
		System.out.println("\tGZIP����");
		char[] content = myResponse.getCharArray();

		//gzipѹ��
		ByteArrayOutputStream buf = new ByteArrayOutputStream();
		GZIPOutputStream gzip = new GZIPOutputStream(buf);
		gzip.write(new String(content).getBytes());
		gzip.finish();
		byte[] result = buf.toByteArray();
		
		//����������������ݵ�ѹ����ʽ
		myResponse.setHeader("content-encoding", "gzip");
		
		//���
		myResponse.getOutputStream().write(result);
		//myRresponse.getWriter().write(new String(result,0,result.length));
		System.out.println("\t\t���˺�" + result.length);
		System.out.println("\tGZIP����������");
	}
		
	public void init(FilterConfig arg0) throws ServletException {
	       // TODO Auto-generated method stub
	       
       }

}

class MyHttpResponse extends HttpServletResponseWrapper{
	private HttpServletResponse response;
	// ����
	private CharArrayWriter writer = new CharArrayWriter();;
	
	// ���������ڵ�����
	public char[] getCharArray() {
		return writer.toCharArray();
	}
	
	public MyHttpResponse(HttpServletResponse response) {
	       super(response);
	       this.response = response;
	}

	@Override
       public PrintWriter getWriter() throws IOException {
	       // TODO Auto-generated method stub
	       return new PrintWriter(writer);
       }
	
	
}