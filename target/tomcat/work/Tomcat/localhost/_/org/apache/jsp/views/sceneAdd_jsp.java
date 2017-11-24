/*
 * Generated by the Jasper component of Apache Tomcat
 * Version: Apache Tomcat/7.0.37
 * Generated at: 2017-11-23 09:50:44 UTC
 * Note: The last modified time of this file was set to
 *       the last modified time of the source file after
 *       generation to assist with modification tracking.
 */
package org.apache.jsp.views;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.jsp.*;
import java.util.*;
import com.sjtu.pojo.*;

public final class sceneAdd_jsp extends org.apache.jasper.runtime.HttpJspBase
    implements org.apache.jasper.runtime.JspSourceDependent {

  private static final javax.servlet.jsp.JspFactory _jspxFactory =
          javax.servlet.jsp.JspFactory.getDefaultFactory();

  private static java.util.Map<java.lang.String,java.lang.Long> _jspx_dependants;

  private javax.el.ExpressionFactory _el_expressionfactory;
  private org.apache.tomcat.InstanceManager _jsp_instancemanager;

  public java.util.Map<java.lang.String,java.lang.Long> getDependants() {
    return _jspx_dependants;
  }

  public void _jspInit() {
    _el_expressionfactory = _jspxFactory.getJspApplicationContext(getServletConfig().getServletContext()).getExpressionFactory();
    _jsp_instancemanager = org.apache.jasper.runtime.InstanceManagerFactory.getInstanceManager(getServletConfig());
  }

  public void _jspDestroy() {
  }

  public void _jspService(final javax.servlet.http.HttpServletRequest request, final javax.servlet.http.HttpServletResponse response)
        throws java.io.IOException, javax.servlet.ServletException {

    final javax.servlet.jsp.PageContext pageContext;
    javax.servlet.http.HttpSession session = null;
    final javax.servlet.ServletContext application;
    final javax.servlet.ServletConfig config;
    javax.servlet.jsp.JspWriter out = null;
    final java.lang.Object page = this;
    javax.servlet.jsp.JspWriter _jspx_out = null;
    javax.servlet.jsp.PageContext _jspx_page_context = null;


    try {
      response.setContentType("text/html; charset=UTF-8");
      pageContext = _jspxFactory.getPageContext(this, request, response,
      			null, true, 8192, true);
      _jspx_page_context = pageContext;
      application = pageContext.getServletContext();
      config = pageContext.getServletConfig();
      session = pageContext.getSession();
      out = pageContext.getOut();
      _jspx_out = out;

      out.write('\n');
      out.write('\n');

	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";

      out.write("\n");
      out.write("\n");
      out.write("<!DOCTYPE html>\n");
      out.write("<!--[if lt IE 7 ]><html lang=\"en\" class=\"ie6 ielt7 ielt8 ielt9\"><![endif]--><!--[if IE 7 ]><html lang=\"en\" class=\"ie7 ielt8 ielt9\"><![endif]--><!--[if IE 8 ]><html lang=\"en\" class=\"ie8 ielt9\"><![endif]--><!--[if IE 9 ]><html lang=\"en\" class=\"ie9\"> <![endif]--><!--[if (gt IE 9)|!(IE)]><!--> \n");
      out.write("<html lang=\"en\"><!--<![endif]--> \n");
      out.write("\t<head>\n");
      out.write("\t\t<meta charset=\"utf-8\">\n");
      out.write("\t\t<title>Tenant List</title>\n");
      out.write("\t\t<meta name=\"viewport\" content=\"width=device-width, initial-scale=1.0\">\n");
      out.write("\t\t<link href=\"/bs/css/bootstrap.min.css\" rel=\"stylesheet\">\n");
      out.write("\t\t<link href=\"/bs/css/bootstrap-responsive.min.css\" rel=\"stylesheet\">\n");
      out.write("\t\t<link href=\"/css/site.css\" rel=\"stylesheet\">\n");
      out.write("\n");
      out.write("\t\t<script type=\"text/javascript\" src=\"/js/jquery.min.js\"></script>\n");
      out.write("        <script type=\"text/javascript\" src=\"/js/url.js\"></script>\n");
      out.write("        <script type=\"text/javascript\" src=\"/js/format.js\"></script>\n");
      out.write("        <script type=\"text/javascript\" src=\"/js/ajaxfileupload.js\"></script>\n");
      out.write("        <script type=\"text/javascript\" src=\"/js/myfileupload.js\"></script>\n");
      out.write("        <script type=\"text/javascript\" src=\"/bs/js/bootstrap.min.js\"></script>\n");
      out.write("        <script type=\"text/javascript\" src=\"/js/site.js\"></script>\n");
      out.write("        <script type=\"text/javascript\" src=\"/js/page/sceneAdd.js\"></script>\n");
      out.write("\t</head>\n");
      out.write("\t<script>\n");
      out.write("        var user = \"");
      out.print(((com.sjtu.pojo.User)(session.getAttribute("id"))).getUid());
      out.write("\"\n");
      out.write("        var appId = getQueryString(\"appId\")\n");
      out.write("    </script>\n");
      out.write("\t<body>\n");
      out.write("        <div class=\"container\">\n");
      out.write("\t\t\t<div class=\"navbar\">\n");
      out.write("\t\t\t\t<div class=\"navbar-inner\">\n");
      out.write("\t\t\t\t\t<div class=\"container\">\n");
      out.write("\t\t\t\t\t\t<a class=\"btn btn-navbar\" data-toggle=\"collapse\" data-target=\".nav-collapse\">\n");
      out.write("\t\t\t\t\t\t    <span class=\"icon-bar\"></span>\n");
      out.write("\t\t\t\t\t\t    <span class=\"icon-bar\"></span>\n");
      out.write("\t\t\t\t\t\t    <span class=\"icon-bar\"></span>\n");
      out.write("\t\t\t\t\t\t</a>\n");
      out.write("\t\t\t\t\t\t<a class=\"brand\" href=\"#\">Manager</a>\n");
      out.write("\t\t\t\t\t\t<div class=\"nav-collapse\">\n");
      out.write("\t\t\t\t\t\t\t<ul class=\"nav\">\n");
      out.write("\n");
      out.write("\t\t\t\t\t\t\t\t<li class=\"dropdown\">\n");
      out.write("                                    <a class=\"dropdown-toggle\" data-toggle=\"dropdown\">用户管理<b class=\"caret\"></b></a>\n");
      out.write("                                    <ul class=\"dropdown-menu\">\n");
      out.write("                                \t    <li>\n");
      out.write("                                \t\t    <a href=\"/user/getUsers\">用户列表</a>\n");
      out.write("                                \t    </li>\n");
      out.write("                                \t    <li>\n");
      out.write("                                \t\t    <a href=\"/page/forward?page=userAdd\">添加用户</a>\n");
      out.write("                                \t    </li>\n");
      out.write("                                    </ul>\n");
      out.write("                                </li>\n");
      out.write("                                <li class=\"dropdown\">\n");
      out.write("                                    <a class=\"dropdown-toggle\" data-toggle=\"dropdown\">应用管理<b class=\"caret\"></b></a>\n");
      out.write("                                    <ul class=\"dropdown-menu\">\n");
      out.write("                                \t    <li>\n");
      out.write("                                \t\t    <a href=\"/app/list\">应用列表</a>\n");
      out.write("                                \t    </li>\n");
      out.write("                                \t    <li>\n");
      out.write("                                \t\t    <a href=\"#\">添加应用</a>\n");
      out.write("                                \t    </li>\n");
      out.write("                                    </ul>\n");
      out.write("                                </li>\n");
      out.write("\n");
      out.write("                                <li class=\"dropdown\">\n");
      out.write("                                    <a class=\"dropdown-toggle\" data-toggle=\"dropdown\">设备管理<b class=\"caret\"></b></a>\n");
      out.write("                                    <ul class=\"dropdown-menu\">\n");
      out.write("                                \t    <li>\n");
      out.write("                                \t\t    <a href=\"/page/forward?page=devicelist\">设备码列表</a>\n");
      out.write("                                \t    </li>\n");
      out.write("                                \t    <li>\n");
      out.write("                                \t\t    <a href=\"/page/forward?page=pointerList\">设备列表</a>\n");
      out.write("                                \t    </li>\n");
      out.write("                                    </ul>\n");
      out.write("                                </li>\n");
      out.write("\t\t\t\t\t\t\t</ul>\n");
      out.write("\n");
      out.write("\t\t\t\t\t\t\t<form class=\"navbar-search pull-left\" action=\"\">\n");
      out.write("\t\t\t\t\t\t\t\t<input type=\"text\" class=\"search-query span2\" placeholder=\"搜索\" />\n");
      out.write("\t\t\t\t\t\t\t</form>\n");
      out.write("\n");
      out.write("\t\t\t\t\t\t\t<ul class=\"nav pull-right\">\n");
      out.write("\t\t\t\t\t\t\t\t<li>\n");
      out.write("\t\t\t\t\t\t\t\t\t<a href=\"#\" style = \"color:blue\">");
      out.print(((com.sjtu.pojo.User)(session.getAttribute("id"))).getUnick());
      out.write("</a>\n");
      out.write("\t\t\t\t\t\t\t\t</li>\n");
      out.write("\n");
      out.write("\t\t\t\t\t\t\t\t<li>\n");
      out.write("\t\t\t\t\t\t\t\t\t<a href=\"/login/logout\">Logout</a>\n");
      out.write("\t\t\t\t\t\t\t\t</li>\n");
      out.write("\t\t\t\t\t\t\t</ul>\n");
      out.write("\t\t\t\t\t\t</div>\n");
      out.write("\t\t\t\t\t</div>\n");
      out.write("\t\t\t\t</div>\n");
      out.write("\t\t\t</div>\n");
      out.write("\n");
      out.write("\t\t\t<div class=\"row\">\n");
      out.write("\t\t\t\t<div class=\"span3\">\n");
      out.write("\t\t\t\t\t<div class=\"well\" style=\"padding: 8px 0;\">\n");
      out.write("\t\t\t\t\t\t<ul class=\"nav nav-list\">\n");
      out.write("\t\t\t\t\t\t    <li class=\"nav-header\">\n");
      out.write("                        \t    主页显示\n");
      out.write("                        \t</li>\n");
      out.write("                        \t<li>\n");
      out.write("                        \t    <a href=\"/page/forward?page=manager\"><i class=\"icon-list-alt\"></i>主页显示</a>\n");
      out.write("                        \t</li>\n");
      out.write("\t\t\t\t\t\t\t<li class=\"nav-header\">\n");
      out.write("\t\t\t\t\t\t\t\t用户管理\n");
      out.write("\t\t\t\t\t\t\t</li>\n");
      out.write("\t\t\t\t\t\t\t<li>\n");
      out.write("\t\t\t\t\t\t\t\t<a href=\"/user/getUsers\"><i class=\"icon-user\"></i>用户列表</a>\n");
      out.write("\t\t\t\t\t\t\t</li>\n");
      out.write("\t\t\t\t\t\t\t<li>\n");
      out.write("\t\t\t\t\t\t\t\t<a href=\"#\"><i class=\"icon-plus\"></i>添加用户</a>\n");
      out.write("\t\t\t\t\t\t\t</li>\n");
      out.write("\n");
      out.write("\t\t\t\t\t\t\t<li class=\"nav-header\">\n");
      out.write("\t\t\t\t\t\t\t\t应用管理\n");
      out.write("\t\t\t\t\t\t\t</li>\n");
      out.write("\t\t\t\t\t\t\t<li class=\"active\">\n");
      out.write("\t\t\t\t\t\t\t\t<a href=\"/app/list\"><i class=\"icon-th\"></i>应用列表</a>\n");
      out.write("\t\t\t\t\t\t\t</li>\n");
      out.write("\t\t\t\t\t\t\t<li>\n");
      out.write("\t\t\t\t\t\t\t\t<a href=\"#\"><i class=\"icon-plus\"></i>添加应用</a>\n");
      out.write("\t\t\t\t\t\t\t</li>\n");
      out.write("\n");
      out.write("                            <li class=\"nav-header\">\n");
      out.write("\t\t\t\t\t\t\t    设备管理\n");
      out.write("\t\t\t\t\t\t\t</li>\n");
      out.write("\t\t\t\t\t\t\t<li>\n");
      out.write("\t\t\t\t\t\t\t\t<a href=\"/page/forward?page=devicelist\"><i class=\"icon-barcode\"></i>设备码列表</a>\n");
      out.write("\t\t\t\t\t\t\t</li>\n");
      out.write("\t\t\t\t\t\t\t<li>\n");
      out.write("\t\t\t\t\t\t\t\t<a href=\"/page/forward?page=pointerList\"><i class=\"icon-th-list\"></i>设备列表</a>\n");
      out.write("\t\t\t\t\t\t\t</li>\n");
      out.write("\t\t\t\t\t\t</ul>\n");
      out.write("\t\t\t\t\t</div>\n");
      out.write("\t\t\t\t</div>\n");
      out.write("\n");
      out.write("                <div class=\"span9\" style = \"display\">\n");
      out.write("\t\t\t\t\t<h2>场景列表</h2>\n");
      out.write("\t\t\t\t\t<ul class=\"thumbnails\" id = \"applist-ul\"></ul>\n");
      out.write("\t\t\t\t<a id = \"toggle-a\" href=\"#new-device-code\"><i class=\"icon-plus\"></i>新建场景</a>\n");
      out.write("                <div id=\"new-device-code\" class=\"form-horizontal hidden\">\n");
      out.write("                    <form id=\"addAppForm\" action=\"/app/scene-add\">\n");
      out.write("\t\t\t\t\t\t<fieldset>\n");
      out.write("\t\t\t\t\t\t\t<legend>场景信息</legend>\n");
      out.write("\t\t\t\t\t\t\t<input type=\"text\" class=\"input-xlarge\" id=\"said\" name = \"said\" style = \"display:none\"/>\n");
      out.write("\t\t\t\t\t\t\t<div class=\"control-group\">\n");
      out.write("\t\t\t\t\t\t\t\t<label class=\"control-label\" for=\"input01\">名称</label>\n");
      out.write("\t\t\t\t\t\t\t\t<div class=\"controls\">\n");
      out.write("\t\t\t\t\t\t\t\t\t<input type=\"text\" class=\"input-xlarge\" id=\"input01\" name = \"sname\"/>\n");
      out.write("\t\t\t\t\t\t\t\t</div>\n");
      out.write("\t\t\t\t\t\t\t</div>\n");
      out.write("\t\t\t\t\t\t\t<div class=\"control-group\">\n");
      out.write("\t\t\t\t\t\t\t\t<label class=\"control-label\" for=\"textarea\">描述</label>\n");
      out.write("\t\t\t\t\t\t\t\t<div class=\"controls\">\n");
      out.write("\t\t\t\t\t\t\t\t\t<textarea class=\"input-xlarge\" id=\"textarea\" rows=\"4\" name = \"sdesc\"></textarea>\n");
      out.write("\t\t\t\t\t\t\t\t</div>\n");
      out.write("\t\t\t\t\t\t\t</div>\n");
      out.write("\t\t\t\t\t\t</fieldset>\n");
      out.write("\t\t\t\t\t</form>\n");
      out.write("\t\t\t\t\t<div class=\"control-group\">\n");
      out.write("                        <label class=\"control-label\" for=\"fileInput\">背景</label>\n");
      out.write("                    \t<div class=\"controls\">\n");
      out.write("                    \t    <input class=\"input-file\" accept=\".jpg,.png,.gif,.jpeg\" name = \"file\" id=\"fileUpload\" type=\"file\" onchange=\"uploadFile(this)\"/>\n");
      out.write("                        </div>\n");
      out.write("                    </div>\n");
      out.write("\t\t\t\t\t<div class=\"form-actions\">\n");
      out.write("                        <button type=\"submit\" class=\"btn btn-primary\" onclick = \"addSubmit()\">Save</button><div class=\"myinterval\">&ensp;</div><button class=\"btn\" onclick=\"addCancel()\">Cancel</button>\n");
      out.write("                    </div>\n");
      out.write("                    </div>\n");
      out.write("                    <div class=\"form-actions\" style=\"text-align:center\">\n");
      out.write("                        <button type=\"submit\" class=\"btn\" onclick = \"back()\">Back</button>\n");
      out.write("                    </div>\n");
      out.write("\t\t\t    </div>\n");
      out.write("\t\t\t</div>\n");
      out.write("\t\t</div>\n");
      out.write("\t</body>\n");
      out.write("</html>");
    } catch (java.lang.Throwable t) {
      if (!(t instanceof javax.servlet.jsp.SkipPageException)){
        out = _jspx_out;
        if (out != null && out.getBufferSize() != 0)
          try { out.clearBuffer(); } catch (java.io.IOException e) {}
        if (_jspx_page_context != null) _jspx_page_context.handlePageException(t);
        else throw new ServletException(t);
      }
    } finally {
      _jspxFactory.releasePageContext(_jspx_page_context);
    }
  }
}
