/**
 * Created by Administrator on 2021-01-26.
 */
var hbvar={} ;

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
    var temp=JSON.parse(event.data.replace('\\',''));
//将出来的数据单位全部换算成人民币
 //   temp=getcnc(temp)
    type=temp.symbol.substr(temp.symbol.length-3,temp.symbol.length);
    switch (type) {
        case'BTC':
            var bz=temp.symbol.substr(0,temp.symbol.indexOf('BTC'));
            if('undefined'==typeof (hbvar[bz])) {
                hbvar[bz] = {};
            }
            var symbol =temp.symbol;
            hbvar[bz][symbol]={};
            //btc转人民币
            temp=hbtemprmb(temp,'BTC');
            hbvar[bz][symbol].asks= temp.asks;
            hbvar[bz][symbol].bids= temp.bids;
            break;
        case 'SDT':
            var bz=temp.symbol.substr(0,temp.symbol.indexOf('USDT'));
            if('undefined'==typeof (hbvar[bz])) {
                hbvar[bz] = {};
            }
            var symbol =temp.symbol;
            hbvar[bz][symbol]={};
            temp=hbtemprmb(temp,'USDT');
            hbvar[bz][symbol].asks= temp.asks;
            hbvar[bz][symbol].bids= temp.bids;
            break;
        case 'ETH':
            var bz=temp.symbol.substr(0,temp.symbol.indexOf('ETH'));
            if('undefined'==typeof (hbvar[bz])) {
                hbvar[bz] = {};
            }
            var symbol =temp.symbol;
            hbvar[bz][symbol]={};
            temp=hbtemprmb(temp,'ETH');
            hbvar[bz][symbol].asks= temp.asks;
            hbvar[bz][symbol].bids= temp.bids;
            break;
    }
    //alert("111");
}

websockethb.onclose=function () {

}
window.onload=function () {
    // gethbprice("btcusdt");
    //clearInterval(t);
    // gethbprice("ethusdt");
    //clearInterval(t1);
}


function getcnc(temp) {
    var btc=revar.hbbtcusdt*revar.bsusdtcnc;
    var eth=revar.hbethusdt*revar.bsusdtcnc;
    var rmb=revar.bsusdtcnc;

    if(temp.symbol.indexOf("btc")!=-1)
    {
        for(i=0;i<temp.asks.length;i++)
        {
            temp.asks[i][0]=temp.asks[i][0]*btc;
        }
        for(i=0;i<temp.asks.length;i++)
        {
            temp.bids[i][0]=temp.bids[i][0]*btc;
        }
    }
    return temp;
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