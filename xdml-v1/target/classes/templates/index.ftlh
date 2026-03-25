
<html lang="en">
<head>
  <meta charset="UTF-8">
  <meta name="viewport" content="width=device-width, initial-scale=1">
  <title>my rank</title>
</head>
<body>
  <h1>排行榜！</h1>
  <table>
    <tr>
      <th>排名</th>
      <th>名字</th>
      <th>分数</th>
    </tr>

    <#list items as item>
    <tr>
      <td>${item?index + 1}</td>
      <td>${item.user.name}</td>
      <td>${item.score}</td>
    </tr>
    </#list>

  </table>

</body>
</html>