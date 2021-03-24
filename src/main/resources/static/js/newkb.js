/**
 * Created by Administrator on 2021-01-26.
 */
var hbbtcustd, hbethustd;
var kbvar={} ;
//setInterval('gethbprice("btcusdt")', 1000);
//setInterval('gethbprice("ethusdt")', 1000);
if ('WebSocket' in window) {
    websocketkb = new WebSocket("ws://localhost:8080/test/kb");
}
else
{
    alert('Not support websocket')
}
websocketkb.onmessage=function(event)
{
   // locateposition('bian',event.data);
    var temp=JSON.parse(event.data.replace('\\',''));
    type=temp.symbol.substr(temp.symbol.length-3,temp.symbol.length);
    switch (type) {
        case'BTC':
            var bz=temp.symbol.substr(0,temp.symbol.indexOf('BTC')-1);
            if('undefined'==typeof (kbvar[bz])) {
                kbvar[bz] = {};
            }
            var symbol =temp.symbol;
            kbvar[bz][symbol]={};
            temp=kbtemprmb(temp,'BTC');
            kbvar[bz][symbol].asks= temp.asks;
            kbvar[bz][symbol].bids= temp.bids;
            putask1to('kb',bz,temp.asks[0][0]);
            break;
        case 'SDT':
            var bz=temp.symbol.substr(0,temp.symbol.indexOf('USDT')-1);
            if('undefined'==typeof (kbvar[bz])) {
                kbvar[bz] = {};
            }
            var symbol =temp.symbol;
            kbvar[bz][symbol]={};
            temp=kbtemprmb(temp,'USDT');
            kbvar[bz][symbol].asks= temp.asks;
            kbvar[bz][symbol].bids= temp.bids;
            putask1to('kb',bz,temp.asks[0][0]);
            break;
        case 'ETH':
            var bz=temp.symbol.substr(0,temp.symbol.indexOf('ETH')-1);
            if('undefined'==typeof (kbvar[bz])) {
                kbvar[bz] = {};
            }
            var symbol =temp.symbol;
            kbvar[bz][symbol]={};
            temp=kbtemprmb(temp,'ETH');
            kbvar[bz][symbol].asks= temp.asks;
            kbvar[bz][symbol].bids= temp.bids;
            putask1to('kb',bz,temp.asks[0][0]);
            break;
    }
    //alert("111");
}

websocketkb.onclose=function () {

}
window.onload=function () {
    // gethbprice("btcusdt");
    //clearInterval(t);
    // gethbprice("ethusdt");
    //clearInterval(t1);
}
function gethbprice(symbol) {
    //setTimeout('gethbprice(symbol)',1000);
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
                        setTimeout('gethbprice("btcusdt")',1000);
                        //clearInterval(t);
                        break;
                    case 'ethusdt':
                        hbethustd=result.ethusdt;
                        $('#hbethprice')[0].value=hbethustd;
                        setTimeout('gethbprice("ethusdt")',1000);
                        //clearInterval(t1);
                        break;
                }
            },
            complete: function () {
                $.ajax()
            }
        });
    });
}