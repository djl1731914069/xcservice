<!DOCTYPE html>
<html>
<head>
    <meta charset="utf‐8">
    <title>Hello World!</title>
</head>
<body>
Hello ${name}!111sad123
<#list stus as s>
    ${s.name}
</#list>
<#list stuMap?keys as k>
   <div <#if stuMap[k].name=="小明">style="background: red" </#if>>${stuMap[k].name}</div>
</#list>
</body>
</html>