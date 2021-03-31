/**
 * Created by Administrator on 2021-01-26.
 */
var hbbtcustd, hbethustd;
var okvar={} ;
//setInterval('gethbprice("btcusdt")', 1000);
//setInterval('gethbprice("ethusdt")', 1000);
if ('WebSocket' in window) {
    websocketok = new WebSocket("ws://localhost:8080/test/ok");
}
else
{
    alert('Not support websocket')
}
websocketok.onmessage=function(event)
{
   // locateposition('bian',event.data);
    var temp=JSON.parse(event.data.replace('\\',''));
    type=temp.symbol.substr(temp.symbol.length-3,temp.symbol.length);
    switch (type) {
        case'BTC':
            var bz=temp.symbol.substr(0,temp.symbol.indexOf('BTC')-1);
            if('undefined'==typeof (okvar[bz])) {
                okvar[bz] = {};
            }
            var symbol =temp.symbol;
            okvar[bz][symbol]={};
            temp=oktemprmb(temp,'BTC')
            okvar[bz][symbol].asks= temp.asks;
            okvar[bz][symbol].bids= temp.bids;
            putask1to('ok',bz,'BTC',temp.asks[0][0]);
            var max=findmax(bz);
            symbol=symbol.replace('-','');
            var result=cacule('ok',symbol,temp.bids[0][0],max);
            if(result.percent>=percent_base)
            {
                newedrawtable(bz,'ok',symbol,temp.bids[0][0],result.percent,result.sell_trade,result.sell_symbol,result.sellprice)
            }
            break;
        case 'SDT':
            var bz=temp.symbol.substr(0,temp.symbol.indexOf('USDT')-1);
            if('undefined'==typeof (okvar[bz])) {
                okvar[bz] = {};
            }
            var symbol =temp.symbol;
            okvar[bz][symbol]={};
            temp=oktemprmb(temp,'USDT')
            okvar[bz][symbol].asks= temp.asks;
            okvar[bz][symbol].bids= temp.bids;
            putask1to('ok',bz,'USDT',temp.asks[0][0]);
            var max=findmax(bz);
            symbol=symbol.replace('-','');
            var result=cacule('ok',symbol,temp.bids[0][0],max);
            if(result.percent>=percent_base)
            {
                newedrawtable(bz,'ok',symbol,temp.bids[0][0],result.percent,result.sell_trade,result.sell_symbol,result.sellprice)
            }
            break;
        case 'ETH':
            var bz=temp.symbol.substr(0,temp.symbol.indexOf('ETH')-1);
            if('undefined'==typeof (okvar[bz])) {
                okvar[bz] = {};
            }
            var symbol =temp.symbol;
            okvar[bz][symbol]={};
            temp=oktemprmb(temp,'ETH')
            okvar[bz][symbol].asks= temp.asks;
            okvar[bz][symbol].bids= temp.bids;
            putask1to('ok',bz,'ETH',temp.asks[0][0]);
            var max=findmax(bz);
            symbol=symbol.replace('-','');
            var result=cacule('ok',symbol,temp.bids[0][0],max);
            if(result.percent>=percent_base)
            {
                newedrawtable(bz,'ok',symbol,temp.bids[0][0],result.percent,result.sell_trade,result.sell_symbol,result.sellprice)
            }
            break;
    }
    //alert("111");
}

websocketok.onclose=function () {

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