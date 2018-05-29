<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <script src="jquery-2.0.3.min.js"></script>
        <style>
            .board {
                border: solid thick;
                margin: 10px;
                width:	50px; 
                height: 50px;
                display:	inline-block;
                text-align: center;
                position:	relative;
            }

            .red-border {
                border-color: red;
            }

            .blue-border {
                border-color: blue;
                bottom: 30px;
            }
        </style>
    </head>

    <body>
        <% int numberOfCards = 5;%>
        <div>
            <%for (int i=1 ; i <= numberOfCards ; i++) {%>
                <div class="board red-border" number="<%=i%>" onclick="borderColorToggle(this)"><%=i%>
                </div>
            <%}%>

            <form id="cards-form" action="getSelection">
                <%for (int i=1 ; i <= numberOfCards ; i++) {%>
                    <%=i%>:<input type="checkbox" name="card<%=i%>" id="card<%=i%>"/>
                <%}%>
                <input type="submit">
            </form>
        </div>


        <script>
            borderColorToggle=function(target){
                var cardNumber = target.getAttribute('number');
                var isChecked = $("#card" + cardNumber).is(":checked");
                
                $(target).toggleClass('blue-border').toggleClass('red-border');
                document.getElementById("card" + cardNumber).checked= isChecked ? '' : 'checked';
            }
        </script>

    </body>
</html>