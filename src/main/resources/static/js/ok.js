/**
 * Created by Administrator on 2021-01-26.
 */
if ('WebSocket' in window) {
    websocketok = new WebSocket("ws://localhost:8080/test/ok");
    websocketok1 = new WebSocket("ws://localhost:8080/test/ok");
    websocketok2 = new WebSocket("ws://localhost:8080/test/ok");
    websocketok3 = new WebSocket("ws://localhost:8080/test/ok");
}
else
{
    alert('Not support websocket')
}


function listsendok(arr) {
    for(var i=0;i<arr.length;i++ )
    {   var para='{"ok":"'+arr[i]+'"}';
        //JSON.stringify(para)
        websocketok.send(para);//发送之后等待服务器响应
    }
}
function listsendok1(arr) {
    for(var i=0;i<arr.length;i++ )
    {   var para='{"ok":"'+arr[i]+'"}';
        //JSON.stringify(para)
        websocketok1.send(para);//发送之后等待服务器响应
    }
}
function listsendok2(arr) {
    for(var i=0;i<arr.length;i++ )
    {   var para='{"ok":"'+arr[i]+'"}';
        //JSON.stringify(para)
        websocketok2.send(para);//发送之后等待服务器响应
    }
}
function listsendok3(arr) {
    for(var i=0;i<arr.length;i++ )
    {   var para='{"ok":"'+arr[i]+'"}';
        //JSON.stringify(para)
        websocketok3.send(para);//发送之后等待服务器响应
    }
}
oki=0;oki1=0;oki2=0;oki3=0;
websocketok.onmessage=function(event)
{
    locateposition('ok',event.data);
    //setMessageInnerHTMLok(event.data);
    //接收消息之后发送另外一个消息跟后台
    //判断是否执行完毕火币列表
    oki++;
    if(oki==arr.length)
    {
        oki=0;//一次循环完成之后重新置标志位
        listsendok(arr);
    }
}

websocketok1.onmessage=function(event)
{
    locateposition('ok',event.data);
    //setMessageInnerHTMLok1(event.data);
    //接收消息之后发送另外一个消息跟后台
    //判断是否执行完毕火币列表
    oki1++;
    if(oki1==arr1.length)
    {
        oki1=0;//一次循环完成之后重新置标志位
        listsendok1(arr1);
    }
}

websocketok2.onmessage=function(event)
{
    locateposition('ok',event.data);
    //setMessageInnerHTMLok2(event.data);
    //接收消息之后发送另外一个消息跟后台
    //判断是否执行完毕火币列表
    oki2++;
    if(oki2==arr2.length)
    {
        oki2=0;//一次循环完成之后重新置标志位
        listsendok2(arr2);
    }
}

websocketok3.onmessage=function(event)
{
    locateposition('ok',event.data);
    //setMessageInnerHTMLok3(event.data);
    //接收消息之后发送另外一个消息跟后台
    //判断是否执行完毕火币列表
    oki3++;
    if(oki3==arr3.length)
    {
        oki3=0;//一次循环完成之后重新置标志位
        listsendok3(arr3);
    }
}

function setMessageInnerHTMLok(innerHTML) {
    document.getElementById('messageok').innerHTML += innerHTML + '<br/>';
}
function setMessageInnerHTMLok1(innerHTML) {
    document.getElementById('messageok1').innerHTML += innerHTML + '<br/>';
}
function setMessageInnerHTMLok2(innerHTML) {
    document.getElementById('messageok2').innerHTML += innerHTML + '<br/>';
}
function setMessageInnerHTMLok3(innerHTML) {
    document.getElementById('messageok3').innerHTML += innerHTML + '<br/>';
}