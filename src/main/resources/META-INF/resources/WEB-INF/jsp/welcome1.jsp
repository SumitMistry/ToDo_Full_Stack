<%@ include file="common/header.jspf" %>
<%@ include file="common/navigation.jspf" %>

    <div class="container">

        <style="color: blue;" >
            <pre>${login_auth_success_1}</pre>
            <pre>${login_auth_success_2}</pre>


            <p>
            <p> ToDo App is located:  <a href = "api/todo/listall">  right here </a>
            <p>
            <p>
            <a href="/logout"> >>Logout<< </a>

            <li class="nav-item"><a class="nav-link" href="/logout">Logout(<b style="color: DodgerBlue;" >${uid_email}</b>)</a></li>

        </div>

<input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}" />

<form method="GET" action="/logout">
    <button type="submit">Logout-GET</button>
</form>

<form method="POST" action="/logout">
    <button type="submit">Logout-POST</button>
</form>




<%@ include file="common/footer.jspf" %>
