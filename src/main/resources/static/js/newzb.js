/**
 * Created by Administrator on 2021-01-26.
 */
var zbvar={} ;

//setInterval('gethbprice("btcusdt")', 1000);
//setInterval('gethbprice("ethusdt")', 1000);
if ('WebSocket' in window) {
    websocketzb = new WebSocket("ws://localhost:8080/test/zb");
}
else
{
    alert('Not support websocket')
}
websocketzb.onmessage=function(event)
{
   // locateposition('bian',event.data);
    var temp=JSON.parse(event.data.replace('\\',''));
    temp.symbol=temp.symbol.substr(0,temp.symbol.indexOf("_"));
    type=temp.symbol.substr(temp.symbol.length-2,temp.symbol.length);
    switch (type) {
        case'TC':
            var bz=temp.symbol.substr(0,temp.symbol.indexOf('BTC'));
            if('undefined'==typeof (zbvar[bz])) {
                zbvar[bz] = {};
            }
            var symbol =temp.symbol;
            zbvar[bz][symbol]={};
            //btc转人民币
            temp=zbtemprmb(temp,'BTC');
            zbvar[bz][symbol].asks= temp.asks;
            zbvar[bz][symbol].bids= temp.bids;
            putask1to('zb',bz,temp.asks[0][0]);
            break;
        case 'DT':
            var bz=temp.symbol.substr(0,temp.symbol.indexOf('USDT'));
            if('undefined'==typeof (zbvar[bz])) {
                zbvar[bz] = {};
            }
            var symbol =temp.symbol;
            zbvar[bz][symbol]={};
            temp=zbtemprmb(temp,'USDT');
            zbvar[bz][symbol].asks= temp.asks;
            zbvar[bz][symbol].bids= temp.bids;
            putask1to('zb',bz,temp.asks[0][0]);
            break;
        case 'QC':
            var bz=temp.symbol.substr(0,temp.symbol.indexOf('QC'));
            if('undefined'==typeof (zbvar[bz])) {
                zbvar[bz] = {};
            }
            var symbol =temp.symbol;
            zbvar[bz][symbol]={};
            //当币对是qc即为人民币，不做转换
           // temp=zbtemprmb(temp,'qc');
            zbvar[bz][symbol].asks= temp.asks;
            zbvar[bz][symbol].bids= temp.bids;
            putask1to('zb',bz,temp.asks[0][0]);
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