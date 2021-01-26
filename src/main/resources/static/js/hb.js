/**
 * Created by Administrator on 2021-01-26.
 */
if ('WebSocket' in window) {
    websockethb = new WebSocket("ws://localhost:8080/test/hb");
    websockethb1 = new WebSocket("ws://localhost:8080/test/hb");
    websockethb2 = new WebSocket("ws://localhost:8080/test/hb");
    websockethb3 = new WebSocket("ws://localhost:8080/test/hb");
}
else
{
    alert('Not support websocket')
}
//连接发生错误的回调方法
websockethb.onerror = function() {
    setMessageInnerHTML("error");
};

//连接成功建立的回调方法
websockethb.onopen = function(event) {
    //setMessageInnerHTML("open");
}
//连接关闭的回调方法
websockethb.onclose = function() {
    setMessageInnerHTML("close");
}
//关闭连接
function closeWebSocket() {
    websockethb.close();
}

//发送消息
function send() {
    var message = document.getElementById('text').value;
    websockethb.send(message);
}

function listsendhb(arr) {
    for(var i=0;i<arr.length;i++ )
    {   var para='{"hb":"'+arr[i]+'"}';
        //JSON.stringify(para)
        websockethb.send(para);//发送之后等待服务器响应
    }
}
function listsendhb1(arr) {
    for(var i=0;i<arr.length;i++ )
    {   var para='{"hb":"'+arr[i]+'"}';
        //JSON.stringify(para)
        websockethb1.send(para);//发送之后等待服务器响应
    }
}
function listsendhb2(arr) {
    for(var i=0;i<arr.length;i++ )
    {   var para='{"hb":"'+arr[i]+'"}';
        //JSON.stringify(para)
        websockethb2.send(para);//发送之后等待服务器响应
    }
}
function listsendhb3(arr) {
    for(var i=0;i<arr.length;i++ )
    {   var para='{"hb":"'+arr[i]+'"}';
        //JSON.stringify(para)
        websockethb3.send(para);//发送之后等待服务器响应
    }
}
hbi=0;hbi1=0;hbi2=0;hbi3=0;
websockethb.onmessage=function(event)
{
    setMessageInnerHTMLhb(event.data);
    //接收消息之后发送另外一个消息跟后台
    //判断是否执行完毕火币列表
    hbi++;
    if(hbi==arr.length)
    {
        hbi=0;//一次循环完成之后重新置标志位
        listsendhb(arr);
    }
}

websockethb1.onmessage=function(event)
{
    setMessageInnerHTMLhb1(event.data);
    //接收消息之后发送另外一个消息跟后台
    //判断是否执行完毕火币列表
    hbi1++;
    if(hbi1==arr1.length)
    {
        hbi1=0;//一次循环完成之后重新置标志位
        listsendhb1(arr1);
    }
}

websockethb2.onmessage=function(event)
{
    setMessageInnerHTMLhb2(event.data);
    //接收消息之后发送另外一个消息跟后台
    //判断是否执行完毕火币列表
    hbi2++;
    if(hbi2==arr2.length)
    {
        hbi2=0;//一次循环完成之后重新置标志位
        listsendhb2(arr2);
    }
}

websockethb3.onmessage=function(event)
{
    setMessageInnerHTMLhb3(event.data);
    //接收消息之后发送另外一个消息跟后台
    //判断是否执行完毕火币列表
    hbi3++;
    if(hbi3==arr3.length)
    {
        hbi3=0;//一次循环完成之后重新置标志位
        listsendhb3(arr3);
    }
}

function setMessageInnerHTMLhb(innerHTML) {
    document.getElementById('messagehb').innerHTML += innerHTML + '<br/>';
}
function setMessageInnerHTMLhb1(innerHTML) {
    document.getElementById('messagehb1').innerHTML += innerHTML + '<br/>';
}
function setMessageInnerHTMLhb2(innerHTML) {
    document.getElementById('messagehb2').innerHTML += innerHTML + '<br/>';
}
function setMessageInnerHTMLhb3(innerHTML) {
    document.getElementById('messagehb3').innerHTML += innerHTML + '<br/>';
}