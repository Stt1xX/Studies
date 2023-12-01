<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html>
<head>
    <title>Meating with WEB</title>
    <link rel="shortcut icon" type="image/x-icon" href="./resources/images/myLogo.ico">
    <link rel="stylesheet" href="./resources/css/defaultStyles.css">
    <meta charset="utf-8">
</head>
<body>
<section>
    <table id="generalTable">
        <caption id="header">
            <% if (request.getParameter("IsHit").equals("Yes")){
                    out.println("That's<br>a hit!");
            } else{
                    out.println("You've<br>missed!");
            }%>
        </caption>
        <tbody>
        <tr>
            <td>
                <div id="tableWrap" class="scallop">
                    <table id="table">
                        <tbody>
                        <tr>
                            <th width="15%">
                                Is Hit
                            </th>
                            <th width="15%">
                                X
                            </th>
                            <th width="15%">
                                Y
                            </th>
                            <th width="25%">
                                Radius
                            </th>
                            <th>
                                Time
                            </th>
                        </tr>
                        <tr>
                            <td>
                                <%= request.getParameter("IsHit") %>
                            </td>
                            <td>
                                <%= request.getParameter("xCoord") %>
                            </td>
                            <td>
                                <%= request.getParameter("yCoord") %>
                            </td>
                            <td>
                                <%= request.getParameter("Radius") %>
                            </td>
                            <td>
                                <%= request.getParameter("Time") %>
                            </td>
                        </tr>
                        </tbody>
                    </table>
                <div>
            </td>
        </tr>
        <tr>
            <td>
                <form method="GET" action="./Controller">
                    <button id="Submit" type="submit">Try again!</button>
                </form>
            </td>
        </tr>
        </tbody>
    </table>
    <table id="footer">
        <tbody>
        <tr>
            <td colspan="4" style="padding: 1vw 13vw 3vw 13vw;">
                Hello everyone!<br> I am very glad that I finally made my first website!
                This site was made as part of laboratory work No. 1 in the ITMO University Web Programming course. I admit it wasn't easy, but it was worth it. See you on my next projects! Good luck!
            </td>
        </tr>
        <tr class="spaceUnder">
            <td width="25%">
                <a id="gitHub" href='https://github.com/Stt1xX' title="my GitHub" target="_blank">
                    <svg xmlns="http://www.w3.org/2000/svg" xmlns:xlink="http://www.w3.org/1999/xlink" width="50px" height="55px" viewBox="0 0 40 40" version="1.1">
                        <g id="surface1">
                            <path style=" stroke:none;fill-rule:evenodd;fill: #F792E3 ;fill-opacity:1;" d="M 19.941406 0 C 8.914062 0 0 9.167969 0 20.507812 C 0 29.570312 5.710938 37.246094 13.632812 39.960938 C 14.625 40.164062 14.988281 39.519531 14.988281 38.976562 C 14.988281 38.5 14.957031 36.871094 14.957031 35.175781 C 9.410156 36.398438 8.253906 32.730469 8.253906 32.730469 C 7.363281 30.351562 6.042969 29.742188 6.042969 29.742188 C 4.226562 28.484375 6.171875 28.484375 6.171875 28.484375 C 8.1875 28.621094 9.242188 30.589844 9.242188 30.589844 C 11.027344 33.714844 13.898438 32.832031 15.054688 32.289062 C 15.21875 30.964844 15.746094 30.046875 16.308594 29.539062 C 11.886719 29.0625 7.230469 27.296875 7.230469 19.421875 C 7.230469 17.179688 8.023438 15.347656 9.277344 13.921875 C 9.078125 13.410156 8.386719 11.304688 9.476562 8.488281 C 9.476562 8.488281 11.160156 7.945312 14.957031 10.59375 C 16.582031 10.144531 18.257812 9.914062 19.941406 9.914062 C 21.625 9.914062 23.339844 10.152344 24.925781 10.59375 C 28.722656 7.945312 30.40625 8.488281 30.40625 8.488281 C 31.496094 11.304688 30.800781 13.410156 30.605469 13.921875 C 31.890625 15.347656 32.652344 17.179688 32.652344 19.421875 C 32.652344 27.296875 27.996094 29.027344 23.539062 29.539062 C 24.265625 30.183594 24.890625 31.40625 24.890625 33.339844 C 24.890625 36.089844 24.859375 38.296875 24.859375 38.976562 C 24.859375 39.519531 25.222656 40.164062 26.214844 39.960938 C 34.136719 37.246094 39.847656 29.570312 39.847656 20.507812 C 39.878906 9.167969 30.933594 0 19.941406 0 Z M 19.941406 0 "/>
                        </g>
                    </svg>
                </a>
            </td>
            <td width="25%">
                <a id="telegram" href='https://t.me/Jrhduch' title="my Telegram" target="_blank">
                    <svg xmlns="http://www.w3.org/2000/svg" xmlns:xlink="http://www.w3.org/1999/xlink" width="55px" height="55px" viewBox="0 0 50 50" version="1.1">
                        <g id="surface1">
                            <path style="fill:none;stroke-width:1;stroke-linecap:butt;stroke-linejoin:round;stroke:#F792E3 ;stroke-opacity:1;stroke-miterlimit:4;" d="M 21.9975 12 C 21.9975 17.521875 17.52 21.999375 11.99625 21.999375 C 6.474375 21.999375 1.996875 17.521875 1.996875 12 C 1.996875 6.47625 6.474375 2.000625 11.99625 2.000625 C 17.52 2.000625 21.9975 6.47625 21.9975 12 Z M 12.354375 9.3825 C 11.383125 9.7875 9.43875 10.62375 6.523125 11.895 C 6.050625 12.0825 5.803125 12.26625 5.77875 12.44625 C 5.74125 12.751875 6.121875 12.871875 6.64125 13.035 C 6.7125 13.055625 6.785625 13.08 6.860625 13.104375 C 7.370625 13.269375 8.056875 13.464375 8.415 13.471875 C 8.739375 13.479375 9.099375 13.344375 9.49875 13.070625 C 12.223125 11.233125 13.629375 10.303125 13.7175 10.2825 C 13.779375 10.269375 13.865625 10.250625 13.92375 10.303125 C 13.981875 10.355625 13.97625 10.453125 13.970625 10.479375 C 13.933125 10.640625 12.436875 12.031875 11.6625 12.751875 C 11.4225 12.975 11.25 13.134375 11.21625 13.171875 C 11.1375 13.2525 11.056875 13.329375 10.98 13.404375 C 10.505625 13.861875 10.149375 14.203125 11.000625 14.76375 C 11.4075 15.03375 11.735625 15.255 12.061875 15.478125 C 12.41625 15.72 12.7725 15.961875 13.231875 16.26375 C 13.348125 16.340625 13.460625 16.419375 13.569375 16.49625 C 13.98375 16.7925 14.355 17.05875 14.81625 17.015625 C 15.0825 16.99125 15.358125 16.74 15.49875 15.99 C 15.830625 14.22 16.48125 10.381875 16.633125 8.80125 C 16.64625 8.6625 16.629375 8.484375 16.61625 8.4075 C 16.60125 8.32875 16.575 8.218125 16.47375 8.135625 C 16.35375 8.04 16.168125 8.019375 16.085625 8.019375 C 15.70875 8.026875 15.133125 8.2275 12.354375 9.3825 Z M 12.354375 9.3825 " transform="matrix(2.083333,0,0,2.083333,0,0)"/>
                        </g>
                    </svg>
                </a>
            </td>
            <td width="25%">
                <a id="worldOfTanks" href='https://tanki.su/ru/community/accounts/169568092-Vz55enjoyer/' title="my tank account" target="_blank">
                    <svg xmlns="http://www.w3.org/2000/svg" xmlns:xlink="http://www.w3.org/1999/xlink" width="40px" height="55px" viewBox="0 0 30 41" version="1.1">
                        <g id="surface1">
                            <path style=" stroke:none;fill-rule:nonzero;fill:#F792E3;fill-opacity:1;" d="M 26.640625 8.699219 L 23.910156 5.933594 L 20.984375 3.042969 L 9.015625 3.042969 L 6.164062 5.933594 L 3.4375 8.699219 L 12.164062 8.699219 L 12.164062 33.671875 L 15.039062 36.515625 L 17.914062 33.671875 L 17.914062 8.699219 Z M 26.640625 8.699219 "/>
                            <path style=" stroke:none;fill-rule:nonzero;fill:#F792E3;fill-opacity:1;" d="M 23.8125 11.402344 L 21.066406 11.402344 L 21.066406 22.546875 L 18.199219 25.375 L 20.21875 27.332031 L 22.257812 29.3125 L 26.648438 24.976562 L 26.648438 11.402344 Z M 9.007812 22.546875 L 9.007812 11.402344 L 3.425781 11.402344 L 3.425781 24.976562 L 7.816406 29.3125 L 9.859375 27.332031 L 11.875 25.375 Z M 9.007812 22.546875 "/>
                            <path style=" stroke:none;fill-rule:nonzero;fill:#F792E3;fill-opacity:1;" d="M 8.585938 1.519531 L 1.523438 8.574219 L 1.523438 25.664062 L 15 38.875 L 28.476562 25.664062 L 28.476562 8.574219 L 21.414062 1.519531 Z M 0.226562 7.722656 L 7.738281 0.226562 L 7.960938 0.00390625 L 22.039062 0.00390625 L 22.261719 0.226562 L 29.773438 7.722656 L 30 7.949219 L 30 26.296875 L 29.773438 26.519531 L 15.53125 40.476562 L 15 40.996094 L 14.46875 40.476562 L 0.230469 26.519531 L 0 26.296875 L 0 7.949219 Z M 0.226562 7.722656 "/>
                        </g>
                    </svg>
                </a>
            </td>
            <td width="25%">
                Rameev Timur P3218<br>
                Variant: 2812<br>
                ISU: 367511
            </td>
        </tr>
        </tbody>
    </table>
</section>
</body>
</html>
