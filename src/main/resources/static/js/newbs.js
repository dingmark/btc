/**
 * Created by Administrator on 2021-01-26.
 */
var bsvar={} ;

//setInterval('gethbprice("btcusdt")', 1000);
//setInterval('gethbprice("ethusdt")', 1000);
if ('WebSocket' in window) {
    websocketbs = new WebSocket("ws://localhost:8080/test/bs");
}
else
{
    alert('Not support websocket')
}
websocketbs.onmessage=function(event)
{
   // locateposition('bian',event.data);
    var temp=JSON.parse(event.data.replace('\\',''));
    //temp.symbol=temp.symbol.substr(0,temp.symbol.indexOf("_"));
    type=temp.symbol.substr(temp.symbol.length-3,temp.symbol.length);
    switch (type) {
        case'BTC':
            var bz=temp.symbol.substr(0,temp.symbol.indexOf('BTC')-1);
            if('undefined'==typeof (bsvar[bz])) {
                bsvar[bz] = {};
            }
            var symbol =temp.symbol;
            bsvar[bz][symbol]={};
            //btc转人民币
            temp=bstemprmb(temp,'BTC');
            bsvar[bz][symbol].asks= temp.asks;
            bsvar[bz][symbol].bids= temp.bids;
            putask1to('bs',bz,'BTC',Number( temp.asks[0][1]));
            break;
        case 'SDT':
            var bz=temp.symbol.substr(0,temp.symbol.indexOf('USDT')-1);
            if('undefined'==typeof (bsvar[bz])) {
                bsvar[bz] = {};
            }
            var symbol =temp.symbol;
            bsvar[bz][symbol]={};
            temp=bstemprmb(temp,'USDT');
            bsvar[bz][symbol].asks= temp.asks;
            bsvar[bz][symbol].bids= temp.bids;
            putask1to('bs',bz,'USDT',Number(temp.asks[0][1]));
            break;
        case 'CNC':
            var bz=temp.symbol.substr(0,temp.symbol.indexOf('CNC')-1);
            if('undefined'==typeof (bsvar[bz])) {
                bsvar[bz] = {};
            }
            var symbol =temp.symbol;
            bsvar[bz][symbol]={};
            //当币对是cnc即为人民币，不做转换
           // temp=zbtemprmb(temp,'qc');
            bsvar[bz][symbol].asks= temp.asks;
            bsvar[bz][symbol].bids= temp.bids;
            putask1to('bs',bz,'CNC',Number(temp.asks[0][1]));
            break;
    }
}

websocketbs.onclose=function () {

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