/**
 * Created by Administrator on 2021-01-26.
 */
if ('WebSocket' in window) {
    websocketbter = new WebSocket("ws://localhost:8080/test/bt");
}
else
{
    alert('Not support websocket')
}
websocketbter.onmessage=function(event)
{
   // locateposition('bian',event.data);
}

websocketbter.onclose=function () {

}