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

        </div>


<form method="post" action="/logout">
    <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}" />
    <button type="submit">Logout</button>
</form>




<%@ include file="common/footer.jspf" %>
