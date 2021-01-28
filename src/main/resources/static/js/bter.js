/**
 * Created by Administrator on 2021-01-26.
 */
if ('WebSocket' in window) {
    websocketbter = new WebSocket("ws://localhost:8080/test/bter");
    websocketbter1 = new WebSocket("ws://localhost:8080/test/bter");
    websocketbter2 = new WebSocket("ws://localhost:8080/test/bter");
    websocketbter3 = new WebSocket("ws://localhost:8080/test/bter");
}
else
{
    alert('Not support websocket')
}


function listsendbter(arr) {
    for(var i=0;i<arr.length;i++ )
    {   var para='{"bt":"'+arr[i]+'"}';
        //JSON.stringify(para)
        websocketbter.send(para);//发送之后等待服务器响应
    }
}
function listsendbter1(arr) {
    for(var i=0;i<arr.length;i++ )
    {   var para='{"bt":"'+arr[i]+'"}';
        //JSON.stringify(para)
        websocketbter1.send(para);//发送之后等待服务器响应
    }
}
function listsendbter2(arr) {
    for(var i=0;i<arr.length;i++ )
    {   var para='{"bt":"'+arr[i]+'"}';
        //JSON.stringify(para)
        websocketbter2.send(para);//发送之后等待服务器响应
    }
}
function listsendbter3(arr) {
    for(var i=0;i<arr.length;i++ )
    {   var para='{"bt":"'+arr[i]+'"}';
        //JSON.stringify(para)
        websocketbter3.send(para);//发送之后等待服务器响应
    }
}
bteri=0;bteri1=0;bteri2=0;bteri3=0;
websocketbter.onmessage=function(event)
{
    locateposition('bter',event.data);
    //setMessageInnerHTMLbter(event.data);
    //接收消息之后发送另外一个消息跟后台
    //判断是否执行完毕火币列表
    bteri++;
    if(bteri==arr.length)
    {
        bteri=0;//一次循环完成之后重新置标志位
        listsendbter(arr);
    }
}

websocketbter1.onmessage=function(event)
{
    locateposition('bter',event.data);
    //setMessageInnerHTMLbter1(event.data);
    //接收消息之后发送另外一个消息跟后台
    //判断是否执行完毕火币列表
    bteri1++;
    if(bteri1==arr1.length)
    {
        bteri1=0;//一次循环完成之后重新置标志位
        listsendbter1(arr1);
    }
}

websocketbter2.onmessage=function(event)
{
    locateposition('ok',event.data);
    //setMessageInnerHTMLbter2(event.data);
    //接收消息之后发送另外一个消息跟后台
    //判断是否执行完毕火币列表
    bteri2++;
    if(bteri2==arr2.length)
    {
        bteri2=0;//一次循环完成之后重新置标志位
        listsendbter2(arr2);
    }
}

websocketbter3.onmessage=function(event)
{
    locateposition('ok',event.data);
    //setMessageInnerHTMLbter3(event.data);
    //接收消息之后发送另外一个消息跟后台
    //判断是否执行完毕火币列表
    bteri3++;
    if(bteri3==arr3.length)
    {
        bteri3=0;//一次循环完成之后重新置标志位
        listsendbter3(arr3);
    }
}

function setMessageInnerHTMLbter(innerHTML) {
    document.getElementById('messagebter').innerHTML += innerHTML + '<br/>';
}
function setMessageInnerHTMLbter1(innerHTML) {
    document.getElementById('messagebter1').innerHTML += innerHTML + '<br/>';
}
function setMessageInnerHTMLbter2(innerHTML) {
    document.getElementById('messagebter2').innerHTML += innerHTML + '<br/>';
}
function setMessageInnerHTMLbter3(innerHTML) {
    document.getElementById('messagebter3').innerHTML += innerHTML + '<br/>';
}