/**
 * Created by Administrator on 2021-01-26.
 */
var hbbtcustd, hbethustd;
//setInterval('gethbprice("btcusdt")', 1000);
//setInterval('gethbprice("ethusdt")', 1000);
if ('WebSocket' in window) {
    websockethb = new WebSocket("ws://localhost:8080/test/hb");
}
else
{
    alert('Not support websocket')
}
websockethb.onmessage=function(event)
{
   // locateposition('bian',event.data);
    var temp=JSON.stringify(event.data);
    if(temp.stream=="btc_usdt")
    {bn_btc_usdt}
    //alert("111");
}

websockethb.onclose=function () {

}
window.onload=function () {
    gethbprice("btcusdt");
    gethbprice("ethusdt")
}
function gethbprice(symbol) {
    setTimeout(gethbprice,1000);
    $(document).ready(function () {
        $.ajax({
            url: "/getNewValue.do",
            //contentType:"application/json",
            type: "GET",
            cache: false,
            async: true,
            dataType: 'json',
            data: {symbol: symbol},
            beforeSend: function (request) {
               // $('#queryForm').hide();
              //  request.setRequestHeader("Connection", "close");
            },
            success: function (result) {
                switch (symbol)
                {
                    case 'btcusdt':
                        hbbtcustd= result.btcusdt;
                        $('#hbbtcprice')[0].value=hbbtcustd;
                        break;
                    case 'ethusdt':
                        hbethustd=result.ethusdt;
                        $('#hbethprice')[0].value=hbethustd;
                        break;
                }
            },
            complete: function () {
                $.ajax()
            }
        });
    });
}