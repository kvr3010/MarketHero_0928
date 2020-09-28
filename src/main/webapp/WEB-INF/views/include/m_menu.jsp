<%-- <%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%> --%>
<%-- <%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%> --%>
<%@ page language="java" pageEncoding="UTF-8"
contentType="text/html; charset=UTF-8"%> 
<script>

$(function () {
        var url = window.location.pathname,
    urlRegExp = new RegExp(url.replace(/\/$/, '') + "$");  
        $('a.mm').each(function () {
                            if (urlRegExp.test(this.href.replace(/\/$/, ''))) {
                $(this).addClass('menu_active');
            }
        });
    });
</script>
	
<!-- 메뉴 파트 -->
	<div id="menu" v-bind:class="{menu_active: isDesktop}"
		v-bind:style="{height: menuHeight}">
		<!-- 유저정보 파트 -->
		<div id="menu_user">
			<a href="/markethero/"> <img
				src="<c:url value= "/resources/images/menu/menu_ci.png"/>"></a>
			<h1>${merchant.name} 님</h1>
			<h2>안녕하세요!</h2>
			<div id="menu_user_btns">
				<button type="button" class="btn btn-default navbar-btn"
					onClick="location.href='#'">회원정보</button>
				<button type="button"
					class="btn btn-default navbar-btn menu_user_btn_logout"
					onClick="location.href='${pageContext.request.contextPath}/merchant/logout'">로그아웃</button>
			</div>
		</div>
		<!-- 메뉴 목록 -->
		<ul>
		 
			<li class="menu"><a class="mm" href="/markethero/dashboard/merchant"> <i
					class="menu_icon fa fa-bar-chart"></i>대시보드<i
					class="menu_arrow fa fa-angle-right"></i>
			</a></li>
			<li class="menu"><a class="mm" href="/markethero/merchant/order"> <i
					class="menu_icon fa fa-shopping-cart"></i>주문관리<i
					class="menu_arrow fa fa-angle-right"></i>
			</a></li>
			<li class="menu"><a class="mm" href="/markethero/merchant/product"> <i class="menu_icon fa fa-cubes"></i>상품관리<i
					class="menu_arrow fa fa-angle-right"></i>
			</a></li>
		</ul>
	</div>