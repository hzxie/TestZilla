    <header class="ui page stackable grid">
        <div id="assistive-menu" class="row">
            <div class="column">
                <div class="ui secondary menu right floated">
                <c:choose>
                <c:when test="${isLogin}">
                    <a class="item" href="<c:url value="/accounts/dashboard" />">Dashboard</a>
                </c:when>
                <c:otherwise>
                    <a class="item" href="<c:url value="/accounts/login" />">Sign In</a>
                    <a class="item" href="<c:url value="/accounts/join" />">Sign Up</a>
                </c:otherwise>
                </c:choose>
                    <a class="item" href="<c:url value="/help" />">Help Center</a>
                    <a class="item" href="<c:url value="/search" />"><i class="fa fa-search"></i> Search</a>
                    <div class="ui language floating dropdown link item" id="languages">
                        <i class="world icon"></i>
                        <div class="text">English</div> <!-- .text -->
                        <div class="menu">
                            <div class="item active selected" data-value="en-US" data-english="English">English</div>
                            <div class="item" data-value="zh-CN" data-english="Chinese">简体中文</div>
                        </div> <!-- .menu -->
                    </div> <!-- .dropdown -->
                </div> <!-- .menu -->
            </div> <!-- .column -->
        </div> <!-- .row -->
        <div class="row">
            <div id="logo" class="four wide column">
                <a href="<c:url value="/" />">
                    <img src="<c:url value="/assets/img/logo.png" />" alt="Logo">
                </a>
            </div> <!-- #logo -->
            <div id="navigation" class="twelve wide column">
                <nav class="ui secondary menu right floated">
                    <a class="item" href="<c:url value="/" />">Home</a>
                    <a class="item" href="<c:url value="/products" />">Products Board</a>
                    <a class="item" href="<c:url value="/contests" />">Contests</a>
                    <a class="item" href="<c:url value="/leaderboard" />">Leaderboard</a>
                </nav>
            </div> <!-- #navigation -->
        </div> <!-- .row -->
    </header>