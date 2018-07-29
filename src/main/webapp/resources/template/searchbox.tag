<%@tag description="Overall Page template" pageEncoding="UTF-8"%>
<%@attribute name="header" fragment="true" %>
<%@attribute name="footer" fragment="true" %>
<html>
  <body>
    <div id="pageheader">
      <jsp:invoke fragment="header"/>
    </div>
    <div id="body">
      <jsp:doBody/>
    </div>
    <div id="pagefooter">
      <jsp:invoke fragment="footer"/>
    </div>
  </body>
</html>
<span><input id="<jsp:invoke fragment=\"header\"/>" placeholder="输入关键字 搜索内容" class="hiddenBorder" /><i class="magnifier">&#xe652;</i><i class="downTriangle">&#xe630;</i></span>