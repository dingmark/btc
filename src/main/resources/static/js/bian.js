/**
 * Created by Administrator on 2021-01-26.
 */
if ('WebSocket' in window) {
    websocketbian = new WebSocket("ws://localhost:8080/test/bian");
    websocketbian1 = new WebSocket("ws://localhost:8080/test/bian");
    websocketbian2 = new WebSocket("ws://localhost:8080/test/bian");
    websocketbian3 = new WebSocket("ws://localhost:8080/test/bian");
}
else
{
    alert('Not support websocket')
}


function listsendbian(arr) {
    for(var i=0;i<arr.length;i++ )
    {   var para='{"bian":"'+arr[i]+'"}';
        //JSON.stringify(para)
        websocketbian.send(para);//发送之后等待服务器响应
    }
}
function listsendbian1(arr) {
    for(var i=0;i<arr.length;i++ )
    {   var para='{"bian":"'+arr[i]+'"}';
        //JSON.stringify(para)
        websocketbian1.send(para);//发送之后等待服务器响应
    }
}
function listsendbian2(arr) {
    for(var i=0;i<arr.length;i++ )
    {   var para='{"bian":"'+arr[i]+'"}';
        //JSON.stringify(para)
        websocketbian2.send(para);//发送之后等待服务器响应
    }
}
function listsendbian3(arr) {
    for(var i=0;i<arr.length;i++ )
    {   var para='{"bian":"'+arr[i]+'"}';
        //JSON.stringify(para)
        websocketbian3.send(para);//发送之后等待服务器响应
    }
}
biani=0;biani1=0;biani2=0;biani3=0;
websocketbian.onmessage=function(event)
{
    locateposition('bian',event.data);
    //setMessageInnerHTMLbian(event.data);
    //接收消息之后发送另外一个消息跟后台
    //判断是否执行完毕火币列表
    biani++;
    if(biani==arr.length)
    {
        biani=0;//一次循环完成之后重新置标志位
        listsendbian(arr);
    }
}

websocketbian1.onmessage=function(event)
{
    locateposition('bian',event.data);
    //setMessageInnerHTMLbian1(event.data);
    //接收消息之后发送另外一个消息跟后台
    //判断是否执行完毕火币列表
    biani1++;
    if(biani1==arr1.length)
    {
        biani1=0;//一次循环完成之后重新置标志位
        listsendbian1(arr1);
    }
}

websocketbian2.onmessage=function(event)
{
    locateposition('bian',event.data);
    //setMessageInnerHTMLbian2(event.data);
    //接收消息之后发送另外一个消息跟后台
    //判断是否执行完毕火币列表
    biani2++;
    if(biani2==arr2.length)
    {
        biani2=0;//一次循环完成之后重新置标志位
        listsendbian2(arr2);
    }
}

websocketbian3.onmessage=function(event)
{
    locateposition('bian',event.data);
    //setMessageInnerHTMLbian3(event.data);
    //接收消息之后发送另外一个消息跟后台
    //判断是否执行完毕火币列表
    biani3++;
    if(biani3==arr3.length)
    {
        biani3=0;//一次循环完成之后重新置标志位
        listsendbian3(arr3);
    }
}

function setMessageInnerHTMLbian(innerHTML) {
    document.getElementById('messagebian').innerHTML += innerHTML + '<br/>';
}
function setMessageInnerHTMLbian1(innerHTML) {
    document.getElementById('messagebian1').innerHTML += innerHTML + '<br/>';
}
function setMessageInnerHTMLbian2(innerHTML) {
    document.getElementById('messagebian2').innerHTML += innerHTML + '<br/>';
}
function setMessageInnerHTMLbian3(innerHTML) {
    document.getElementById('messagebian3').innerHTML += innerHTML + '<br/>';
}