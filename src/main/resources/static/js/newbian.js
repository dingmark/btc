/**
 * Created by Administrator on 2021-01-26.
 */
var bn_btc_usdt;
var bn_eth_usdt;
if ('WebSocket' in window) {
    websocketbian = new WebSocket("ws://localhost:8080/test/bn");
}
else
{
    alert('Not support websocket')
}
websocketbian.onmessage=function(event)
{
   // locateposition('bian',event.data);
    var temp=JSON.stringify(event.data);
    if(temp.stream=="btc_usdt")
    {bn_btc_usdt}
    //alert("111");
}

websocketbian.onclose=function () {

}