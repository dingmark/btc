/**
 * Created by Administrator on 2021-01-26.
 */
if ('WebSocket' in window) {
    websocketmocha = new WebSocket("ws://localhost:8080/test/mocha");
    websocketmocha1 = new WebSocket("ws://localhost:8080/test/mocha");
    websocketmocha2 = new WebSocket("ws://localhost:8080/test/mocha");
    websocketmocha3 = new WebSocket("ws://localhost:8080/test/mocha");
}
else
{
    alert('Not support websocket')
}


function listsendmocha(arr) {
    for(var i=0;i<arr.length;i++ )
    {   var para='{"mocha":"'+arr[i]+'"}';
        //JSON.stringify(para)
        websocketmocha.send(para);//发送之后等待服务器响应
    }
}
function listsendmocha1(arr) {
    for(var i=0;i<arr.length;i++ )
    {   var para='{"mocha":"'+arr[i]+'"}';
        //JSON.stringify(para)
        websocketmocha1.send(para);//发送之后等待服务器响应
    }
}
function listsendmocha2(arr) {
    for(var i=0;i<arr.length;i++ )
    {   var para='{"mocha":"'+arr[i]+'"}';
        //JSON.stringify(para)
        websocketmocha2.send(para);//发送之后等待服务器响应
    }
}
function listsendmocha3(arr) {
    for(var i=0;i<arr.length;i++ )
    {   var para='{"mocha":"'+arr[i]+'"}';
        //JSON.stringify(para)
        websocketmocha3.send(para);//发送之后等待服务器响应
    }
}
mochai=0;mochai1=0;mochai2=0;mochai3=0;
websocketmocha.onmessage=function(event)
{
    locateposition('mocha',event.data);
    //setMessageInnerHTMLmocha(event.data);
    //接收消息之后发送另外一个消息跟后台
    //判断是否执行完毕火币列表
    mochai++;
    if(mochai==arr.length)
    {
        mochai=0;//一次循环完成之后重新置标志位
        listsendmocha(arr);
    }
}

websocketmocha1.onmessage=function(event)
{
    locateposition('mocha',event.data);
    //setMessageInnerHTMLmocha1(event.data);
    //接收消息之后发送另外一个消息跟后台
    //判断是否执行完毕火币列表
    mochai1++;
    if(mochai1==arr1.length)
    {
        mochai1=0;//一次循环完成之后重新置标志位
        listsendmocha1(arr1);
    }
}

websocketmocha2.onmessage=function(event)
{
    locateposition('mocha',event.data);
    //setMessageInnerHTMLmocha2(event.data);
    //接收消息之后发送另外一个消息跟后台
    //判断是否执行完毕火币列表
    mochai2++;
    if(mochai2==arr2.length)
    {
        mochai2=0;//一次循环完成之后重新置标志位
        listsendmocha2(arr2);
    }
}

websocketmocha3.onmessage=function(event)
{
    locateposition('mocha',event.data);
    //setMessageInnerHTMLmocha3(event.data);
    //接收消息之后发送另外一个消息跟后台
    //判断是否执行完毕火币列表
    mochai3++;
    if(mochai3==arr3.length)
    {
        mochai3=0;//一次循环完成之后重新置标志位
        listsendmocha3(arr3);
    }
}

function setMessageInnerHTMLmocha(innerHTML) {
    document.getElementById('messagemocha').innerHTML += innerHTML + '<br/>';
}
function setMessageInnerHTMLmocha1(innerHTML) {
    document.getElementById('messagemocha1').innerHTML += innerHTML + '<br/>';
}
function setMessageInnerHTMLmocha2(innerHTML) {
    document.getElementById('messagemocha2').innerHTML += innerHTML + '<br/>';
}
function setMessageInnerHTMLmocha3(innerHTML) {
    document.getElementById('messagemocha3').innerHTML += innerHTML + '<br/>';
}