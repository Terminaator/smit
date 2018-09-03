$(function () {
    var $login = $("#login");
    var $addPerson = $("#addPerson");
    var TOKEN_KEY = "token"

    function getToken() {
        return localStorage.getItem(TOKEN_KEY);
    }

    function setToken(token) {
        localStorage.setItem(TOKEN_KEY, token);
    }
    function removeToken() {
        localStorage.removeItem(TOKEN_KEY);
    }

    function authorizationTokenHeader() {
        var token = getToken();
        if (token) {
            return {"Authorization": "Bearer " + token};
        } else {
            return {};
        }
    }
    function addIntoTable(person) {
        $('#persons > tbody:last-child').append('<tr>' +
            '<td>'+person.secretName+'</td>' +
            '<td>'+person.realName+'</td>' +
            '<td>'+person.number+'</td>' +
            '</tr>'
        );
    }
    function addData(person) {
        $.ajax({
            url: "/addPerson",
            type: "POST",
            data: JSON.stringify(person),
            contentType: "application/json; charset=utf-8",
            dataType: "json",
            headers: authorizationTokenHeader(),
            success: function (data, textStatus, jqXHR) {
                addIntoTable(person);
            }
        });
    }
    function dataToTable() {
        $.ajax({
            url: "/getAll",
            type: "GET",
            dataType: "json",
            headers: authorizationTokenHeader(),
            success: function (data, textStatus, jqXHR) {
                for (var i = 0; i < data.length; i++) {
                    var person = data[i];
                    addIntoTable(person);
                }
            }
        });
    }
    function logout() {
        removeToken();
        $("#persons > tbody > tr").remove();
        $addPerson.hide();
        $login.show();
    }
    function login(loginData) {
        $.ajax({
            url: "/auth",
            type: "POST",
            data: JSON.stringify(loginData),
            contentType: "application/json; charset=utf-8",
            dataType: "json",
            success: function (data, textStatus, jqXHR) {
                setToken(data.token);
                $login.hide();
                $addPerson.show();
                dataToTable()
            },
            error: function (jqXHR, textStatus, errorThrown) {
                $("#siin").text("Error!");
            }
        });
    }
    $("#loginForm").submit(function (event) {
        event.preventDefault();
        var $form = $(this);
        var formData = {
            username: $form.find('input[name="username"]').val(),
            password: $form.find('input[name="password"]').val()
        };

        login(formData);
    });
    $("#addForm").submit(function (event) {
        event.preventDefault();
        var $form = $(this);
        var person = {
            realName: $form.find('input[name="realname"]').val(),
            secretName: $form.find('input[name="secretname"]').val(),
            number: $form.find('input[name="phonenumber"]').val()
        };
        addData(person);
    });
    $("#logout").click(logout);
    if (getToken()) {
        $login.hide();
        $addPerson.show();
        dataToTable();
    }
    else {
        $addPerson.hide();
    }
});
